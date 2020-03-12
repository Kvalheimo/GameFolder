package blog.gamedevelopmentbox2dtutorial.views;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

import blog.gamedevelopmentbox2dtutorial.BodyFactory;
import blog.gamedevelopmentbox2dtutorial.Box2dContactListener;
import blog.gamedevelopmentbox2dtutorial.entity.components.B2dBodyComponent;
import blog.gamedevelopmentbox2dtutorial.entity.components.CollisionComponent;
import blog.gamedevelopmentbox2dtutorial.entity.components.PlayerComponent;
import blog.gamedevelopmentbox2dtutorial.entity.components.StateComponent;
import blog.gamedevelopmentbox2dtutorial.entity.components.TextureComponent;
import blog.gamedevelopmentbox2dtutorial.entity.components.TransformComponent;
import blog.gamedevelopmentbox2dtutorial.entity.components.TypeComponent;

import blog.gamedevelopmentbox2dtutorial.Box2dTutorial;
import blog.gamedevelopmentbox2dtutorial.controller.KeyboardController;
import blog.gamedevelopmentbox2dtutorial.entity.systems.AnimationSystem;
import blog.gamedevelopmentbox2dtutorial.entity.systems.CollisionSystem;
import blog.gamedevelopmentbox2dtutorial.entity.systems.PhysicsDebugSystem;
import blog.gamedevelopmentbox2dtutorial.entity.systems.PhysicsSystem;
import blog.gamedevelopmentbox2dtutorial.entity.systems.PlayerControlSystem;
import blog.gamedevelopmentbox2dtutorial.entity.systems.RenderingSystem;

public class MainScreen implements Screen {

    private Box2dTutorial parent;
    private OrthographicCamera camera;
    private KeyboardController controller;
    private SpriteBatch sb;
    private TextureAtlas atlas;
    private World world;
    private BodyFactory bodyFactory;
    private Sound ping, boing;
    private PooledEngine engine;
    //private TiledMap map;
    //private OrthogonalTiledMapRenderer renderer;


    public MainScreen(Box2dTutorial box2dTutorial){
        parent = box2dTutorial;
        controller = new KeyboardController();
        world = new World(new Vector2(0,-10f), true);
        world.setContactListener(new Box2dContactListener());
        bodyFactory = BodyFactory.getInstance(world);

        parent.assMan.queueAddSounds();
        parent.assMan.manager.finishLoading();
        atlas = parent.assMan.manager.get("input/game/images/game.atlas");
        ping = parent.assMan.manager.get("input/game/sounds/ping.wav", Sound.class);
        boing = parent.assMan.manager.get("input/game/sounds/boing.wav", Sound.class);

        sb = new SpriteBatch();

        // Create our new rendering system
        RenderingSystem renderingSystem = new RenderingSystem(sb);
        camera = renderingSystem.getCamera();
        sb.setProjectionMatrix(camera.combined);

        //create a pooled engine
        engine = new PooledEngine();

        engine.addSystem(new AnimationSystem());
        engine.addSystem(renderingSystem);
        engine.addSystem(new PhysicsSystem(world));
        engine.addSystem(new PhysicsDebugSystem(world, renderingSystem.getCamera()));
        engine.addSystem(new CollisionSystem());
        engine.addSystem(new PlayerControlSystem(controller));


        // create some game objects
        createPlayer();
        createPlatform(2,2);
        createPlatform(2,7);
        createPlatform(7,2);
        createPlatform(7,7);

        createFloor();

    }

    private void createPlayer(){

        // Create the Entity and all the components that will go in the entity
        Entity entity = engine.createEntity();
        B2dBodyComponent b2dbody = engine.createComponent(B2dBodyComponent.class);
        TransformComponent position = engine.createComponent(TransformComponent.class);
        TextureComponent texture = engine.createComponent(TextureComponent.class);
        PlayerComponent player = engine.createComponent(PlayerComponent.class);
        CollisionComponent colComp = engine.createComponent(CollisionComponent.class);
        TypeComponent type = engine.createComponent(TypeComponent.class);
        StateComponent stateCom = engine.createComponent(StateComponent.class);

        // create the data for the components and add them to the components
        b2dbody.body = bodyFactory.makeCirclePolyBody(10, 10, 1, BodyFactory.STONE, BodyDef.BodyType.DynamicBody, true);
        // set object position (x,y,z) z used to define draw order 0 first drawn
        position.position.set(10, 10,0);
        texture.region = atlas.findRegion("player");
        type.type = TypeComponent.PLAYER;
        stateCom.set(StateComponent.STATE_NORMAL);

        b2dbody.body.setUserData(entity);

        // add the components to the entity
        entity.add(b2dbody);
        entity.add(position);
        entity.add(texture);
        entity.add(player);
        entity.add(colComp);
        entity.add(type);
        entity.add(stateCom);

        // add the entity to the engine
        engine.addEntity(entity);

    }


    private void createPlatform(float x, float y){

        // Create the Entity and all the components that will go in the entity
        Entity entity = engine.createEntity();
        B2dBodyComponent b2dbody = engine.createComponent(B2dBodyComponent.class);
        TextureComponent texture = engine.createComponent(TextureComponent.class);
        TypeComponent type = engine.createComponent(TypeComponent.class);

        // create the data for the components and add them to the components
        b2dbody.body = bodyFactory.makeBoxPolyBody(x, y, 3, 0.2f, BodyFactory.STONE, BodyDef.BodyType.StaticBody, true);
        // set object position (x,y,z) z used to define draw order 0 first drawn
        texture.region = atlas.findRegion("player");
        type.type = TypeComponent.SCENERY;

        b2dbody.body.setUserData(entity);

        // add the components to the entity
        entity.add(b2dbody);
        entity.add(texture);
        entity.add(type);

        // add the entity to the engine
        engine.addEntity(entity);

    }

    private void createFloor(){
        // Create the Entity and all the components that will go in the entity
        Entity entity = engine.createEntity();
        B2dBodyComponent b2dbody = engine.createComponent(B2dBodyComponent.class);
        TextureComponent texture = engine.createComponent(TextureComponent.class);
        TypeComponent type = engine.createComponent(TypeComponent.class);

        // create the data for the components and add them to the components
        b2dbody.body = bodyFactory.makeBoxPolyBody(0, 0, 100, 0.2f, BodyFactory.STONE, BodyDef.BodyType.StaticBody, true);
        // set object position (x,y,z) z used to define draw order 0 first drawn
        texture.region = atlas.findRegion("player");
        type.type = TypeComponent.SCENERY;

        b2dbody.body.setUserData(entity);

        // add the components to the entity
        entity.add(b2dbody);
        entity.add(texture);
        entity.add(type);

        // add the entity to the engine
        engine.addEntity(entity);
    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(controller);

        //Load map
        //map = new TmxMapLoader().load("input/game/maps/map.tmx");
        //Load renderer
        //renderer = new OrthogonalTiledMapRenderer(map);

    }

    @Override
    public void render(float dt) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //renderer.setView(camera);
        //renderer.render();

        engine.update(dt);
    }


    @Override
    public void resize(int width, int height) {
        //camera.viewportWidth = width;
        //camera.viewportHeight = height;
        //camera.update();

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
        //map.dispose();
        //renderer.dispose();

    }
}
