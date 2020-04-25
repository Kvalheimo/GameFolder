package blog.gamedevelopmentbox2dtutorial.views;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.HashMap;

import blog.gamedevelopmentbox2dtutorial.DFUtils;
import blog.gamedevelopmentbox2dtutorial.DatabaseHandler;
import blog.gamedevelopmentbox2dtutorial.Factory.LevelFactory;
import blog.gamedevelopmentbox2dtutorial.Box2dTutorial;
import blog.gamedevelopmentbox2dtutorial.HighScore.Save;
import blog.gamedevelopmentbox2dtutorial.controller.Controller;
import blog.gamedevelopmentbox2dtutorial.entity.components.PlayerComponent;
import blog.gamedevelopmentbox2dtutorial.entity.components.TypeComponent;
import blog.gamedevelopmentbox2dtutorial.entity.systems.AnimationSystem;
import blog.gamedevelopmentbox2dtutorial.entity.systems.BulletSystem;
import blog.gamedevelopmentbox2dtutorial.entity.systems.CollisionSystem;
import blog.gamedevelopmentbox2dtutorial.entity.systems.DestroyableTileSystem;
import blog.gamedevelopmentbox2dtutorial.entity.systems.EnemySystem;
import blog.gamedevelopmentbox2dtutorial.entity.systems.MoveableSystem;
import blog.gamedevelopmentbox2dtutorial.entity.systems.ParticleEffectSystem;
import blog.gamedevelopmentbox2dtutorial.entity.systems.PhysicsDebugSystem;
import blog.gamedevelopmentbox2dtutorial.entity.systems.PhysicsSystem;
import blog.gamedevelopmentbox2dtutorial.entity.systems.PlayerControlSystem;
import blog.gamedevelopmentbox2dtutorial.entity.systems.PowerupSystem;
import blog.gamedevelopmentbox2dtutorial.entity.systems.RenderingSystem;

public class MultiplayerScreen implements Screen, GameScreen {


    private Box2dTutorial parent;
    private OrthographicCamera camera;
    private Viewport viewport;
    private Controller controller;
    private SpriteBatch sb;
    private PooledEngine engine;
    private OrthogonalTiledMapRenderer renderer;
    private LevelFactory levelFactory;
    private Hud hud;
    private PauseMenu pauseMenu;
    private Entity player;
    private boolean isPaused;
    private int level;
    private int character;
    private CountdownView countdownView;
    private boolean countdownMode;

    private DatabaseHandler dbHandler;
    private String uniqueID;
    private HashMap<String,Entity> opponents;

    public MultiplayerScreen(Box2dTutorial box2dTutorial, int level, int character){
        this.level = level;
        this.character = character;
        isPaused = false;
        parent = box2dTutorial;

        engine = new PooledEngine();
        levelFactory = new LevelFactory(engine, parent, level);

        sb = new SpriteBatch();

        isPaused = true;
        countdownView = new CountdownView(sb, parent);
        countdownMode = true;

        controller = new Controller(sb, parent, this);
        pauseMenu = new PauseMenu(sb, parent, this);
        hud = new Hud(sb, (int) levelFactory.getFinishPosition(), parent);


        // Create our new rendering system
        RenderingSystem renderingSystem = new RenderingSystem(sb);
        camera = renderingSystem.getCamera();
        ParticleEffectSystem particleSystem = new ParticleEffectSystem(sb,camera);

        viewport = renderingSystem.getViewport();

        renderer = new OrthogonalTiledMapRenderer(levelFactory.getMap(level),1/Box2dTutorial.PPT, sb);
        renderer.setView(camera);

        sb.setProjectionMatrix(camera.combined);

        engine.addSystem(new AnimationSystem());
        engine.addSystem(renderingSystem);
        engine.addSystem(particleSystem);
        engine.addSystem(new PhysicsSystem(levelFactory.getWorld()));
        engine.addSystem(new PhysicsDebugSystem(levelFactory.getWorld(), renderingSystem.getCamera()));
        engine.addSystem(new MoveableSystem(camera));
        engine.addSystem(new CollisionSystem(levelFactory, hud));
        engine.addSystem(new PlayerControlSystem(controller, levelFactory, hud));
        engine.addSystem(new BulletSystem());
        engine.addSystem(new EnemySystem(camera));
        engine.addSystem(new PowerupSystem());
        engine.addSystem(new DestroyableTileSystem());



        // create some game objects
        player = levelFactory.createPlayer(camera, character);

        opponents = new HashMap<>();
        dbHandler = new DatabaseHandler();
        dbHandler.getDb().publishPlayer(player);
        dbHandler.getDb().addPlayerEventListener(opponents, levelFactory, engine);

        levelFactory.createBats(level);
        levelFactory.createSpiders(level);
        levelFactory.createPlatformHor(level);
        levelFactory.createJumpWall("JumpWall", TypeComponent.JUMPWALL, level);

        levelFactory.createPowerups("SuperSpeed", TypeComponent.SUPER_SPEED, level);
        levelFactory.createPowerups("Gun", TypeComponent.GUN, level);
        levelFactory.loadCheckpoint(level);
        levelFactory.createDestroyableTiles("DestroyableTile", TypeComponent.DESTROYABLE_TILE, level);

        levelFactory.createTiledMapEntities("Ground", TypeComponent.GROUND, level);
        levelFactory.createTiledMapEntities("Spring", TypeComponent.SPRING, level);
        levelFactory.createTiledMapEntities("Wall", TypeComponent.WALL, level);
        levelFactory.createTiledMapEntities("Water", TypeComponent.WATER, level);
        levelFactory.createTiledMapEntities("SpeedX", TypeComponent.SPEED_X, level);
        levelFactory.createTiledMapEntities("SpeedY", TypeComponent.SPEED_Y, level);
        levelFactory.createTiledMapEntities("Spikes", TypeComponent.SPIKES, level);
        levelFactory.createTiledMapEntities("Other", TypeComponent.OTHER, level);


    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(controller.stage);


    }

    @Override
    public void render(float dt) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (!isPaused) {
            Gdx.input.setInputProcessor(controller.getStage());
            camera.update();
            renderer.setView(camera);
            renderer.render();

            engine.update(dt);
            setProcessing(true);

            sb.setProjectionMatrix(hud.stage.getCamera().combined);
            hud.update(dt, (int)(camera.position.x*Box2dTutorial.PPM));
            hud.draw();

            sb.setProjectionMatrix(controller.stage.getCamera().combined);
            controller.draw();

            PlayerComponent pc = (player.getComponent(PlayerComponent.class));
            if (pc.isFinished) {
                DFUtils.log("YOU DIED : back to menu you go!");
                Save.hsd.get(level).setTentativeScore(hud.getScore());
                parent.changeScreen(Box2dTutorial.ENDGAME, false, level, 0);
            }

        } else {
            Gdx.input.setInputProcessor(pauseMenu.getStage());
            camera.update();

            renderer.setView(camera);
            renderer.render();

            engine.update(dt);
            setProcessing(false);

            sb.setProjectionMatrix(hud.stage.getCamera().combined);
            hud.draw();

            sb.setProjectionMatrix(controller.stage.getCamera().combined);
            controller.draw();

            if (countdownMode){
                countdownView.update(dt);
                countdownView.draw();

                if(countdownView.isCountdownOver()) {
                    isPaused = false;
                    countdownMode = false;
                    countdownView.reset();
                }

            }else{
                Gdx.input.setInputProcessor(pauseMenu.getStage());
                pauseMenu.draw();
            }
        }
        dbHandler.getDb().publishPlayer(player);
    }


    @Override
    public void resize(int width, int height) {
        viewport.update(width,height,false);
        hud.resize(width, height);
        controller.resize(width, height);
        pauseMenu.resize(width, height);
        countdownView.resize(width, height);
    }



    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void pauseGame(Boolean pause){
        this.isPaused = pause;
    }

    public void setProcessing(boolean flag){
        engine.getSystem(AnimationSystem.class).setProcessing(flag);
        engine.getSystem(ParticleEffectSystem.class).setProcessing(flag);
        engine.getSystem(PhysicsSystem.class).setProcessing(flag);
        engine.getSystem(PlayerControlSystem.class).setProcessing(flag);
        engine.getSystem(EnemySystem.class).setProcessing(flag);
        engine.getSystem(CollisionSystem.class).setProcessing(flag);
        engine.getSystem(BulletSystem.class).setProcessing(flag);
        engine.getSystem(PowerupSystem.class).setProcessing(flag);

    }

    @Override
    public void dispose() {
        renderer.dispose();
        engine.clearPools();
        hud.dispose();
        controller.dispose();
        pauseMenu.dispose();

    }
}