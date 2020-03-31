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

import blog.gamedevelopmentbox2dtutorial.DFUtils;
import blog.gamedevelopmentbox2dtutorial.Factory.LevelFactory;


import blog.gamedevelopmentbox2dtutorial.Box2dTutorial;
import blog.gamedevelopmentbox2dtutorial.controller.Controller;
import blog.gamedevelopmentbox2dtutorial.entity.components.PlayerComponent;
import blog.gamedevelopmentbox2dtutorial.entity.components.TypeComponent;
import blog.gamedevelopmentbox2dtutorial.entity.systems.AnimationSystem;
import blog.gamedevelopmentbox2dtutorial.entity.systems.BulletSystem;
import blog.gamedevelopmentbox2dtutorial.entity.systems.CollisionSystem;
import blog.gamedevelopmentbox2dtutorial.entity.systems.PhysicsDebugSystem;
import blog.gamedevelopmentbox2dtutorial.entity.systems.PhysicsSystem;
import blog.gamedevelopmentbox2dtutorial.entity.systems.PlayerControlSystem;
import blog.gamedevelopmentbox2dtutorial.entity.systems.RenderingSystem;

public class MainScreen implements Screen {


    private Box2dTutorial parent;
    private OrthographicCamera camera;
    private Viewport viewport;
    private Controller controller;
    private SpriteBatch sb;
    private PooledEngine engine;
    private OrthogonalTiledMapRenderer renderer;
    private LevelFactory levelFactory;
    private Hud hud;
    private Entity player;


    public MainScreen(Box2dTutorial box2dTutorial){
        parent = box2dTutorial;
        engine = new PooledEngine();
        levelFactory = new LevelFactory(engine, parent.assMan);

        sb = new SpriteBatch();

        controller = new Controller(sb, parent.assMan);
        hud = new Hud(sb, (int) levelFactory.getFinishPosition());

        // Create our new rendering system
        RenderingSystem renderingSystem = new RenderingSystem(sb);

        camera = renderingSystem.getCamera();
        viewport = renderingSystem.getViewport();

        renderer = new OrthogonalTiledMapRenderer(levelFactory.getMap(),1/Box2dTutorial.PPT, sb);
        renderer.setView(camera);
        sb.setProjectionMatrix(camera.combined);


        engine.addSystem(new AnimationSystem());
        engine.addSystem(renderingSystem);
        engine.addSystem(new PhysicsSystem(levelFactory.getWorld()));
        engine.addSystem(new PhysicsDebugSystem(levelFactory.getWorld(), renderingSystem.getCamera()));
        engine.addSystem(new CollisionSystem());
        engine.addSystem(new PlayerControlSystem(controller, levelFactory));
        engine.addSystem(new BulletSystem());


        // create some game objects
        player = levelFactory.createPlayer(camera);
        //levelFactory.createFloor();
        levelFactory.createTiledMapEntities("Ground", TypeComponent.GROUND);
        levelFactory.createTiledMapEntities("SuperSpeed", TypeComponent.SUPER_SPEED);
        levelFactory.createTiledMapEntities("Spring", TypeComponent.SPRING);
        levelFactory.createTiledMapEntities("Gun", TypeComponent.GUN);
        levelFactory.createTiledMapEntities("Wall", TypeComponent.WALL);
        levelFactory.createTiledMapEntities("Water", TypeComponent.GROUND);
        levelFactory.createTiledMapEntities("SpeedX", TypeComponent.SPEED_X);
        levelFactory.createTiledMapEntities("SpeedY", TypeComponent.SPEED_Y);


    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(controller.stage);

    }

    @Override
    public void render(float dt) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        renderer.setView(camera);
        renderer.render();

        engine.update(dt);

        sb.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.update(dt, (int) camera.position.x*4*16);
        hud.draw();
        controller.draw();

       PlayerComponent pc = (player.getComponent(PlayerComponent.class));
        if(pc.isDead) {
            DFUtils.log("YOU DIED : back to menu you go!");
            parent.lastScore = (int) pc.camera.position.x;
            parent.changeScreen(Box2dTutorial.ENDGAME);
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height,false);
        hud.resize(width, height);
        controller.resize(width, height);

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
    public void dispose() {
        renderer.dispose();
        engine.clearPools();
        hud.dispose();
        controller.dispose();

    }
}
