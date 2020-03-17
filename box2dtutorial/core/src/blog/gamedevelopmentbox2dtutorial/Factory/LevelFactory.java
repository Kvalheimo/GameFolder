package blog.gamedevelopmentbox2dtutorial.Factory;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import blog.gamedevelopmentbox2dtutorial.Box2dContactListener;
import blog.gamedevelopmentbox2dtutorial.entity.components.B2dBodyComponent;
import blog.gamedevelopmentbox2dtutorial.entity.components.CollisionComponent;
import blog.gamedevelopmentbox2dtutorial.entity.components.PlayerComponent;
import blog.gamedevelopmentbox2dtutorial.entity.components.StateComponent;
import blog.gamedevelopmentbox2dtutorial.entity.components.TextureComponent;
import blog.gamedevelopmentbox2dtutorial.entity.components.TransformComponent;
import blog.gamedevelopmentbox2dtutorial.entity.components.TypeComponent;
import blog.gamedevelopmentbox2dtutorial.loader.B2dAssetManager;

public class LevelFactory {

    private PooledEngine engine;
    private BodyFactory bodyFactory;
    private World world;
    private MapBodyFactory mapBodyFactory;
    private TiledMap map;
    private TextureAtlas atlas;
    private TextureRegion floorTex;
    private TextureRegion enemyTex;
    private TextureRegion platformTex;
    private TextureRegion playerTex;




    public LevelFactory(PooledEngine engine, B2dAssetManager assMan){
        this.engine = engine;
        world = new World(new Vector2(0,-10f), true);
        world.setContactListener(new Box2dContactListener());

        bodyFactory = BodyFactory.getInstance(world);
        mapBodyFactory = MapBodyFactory.getInstance(world);

        atlas = assMan.manager.get("input/game/images/game.atlas");
        map = assMan.manager.get("input/game/maps/map.tmx", TiledMap.class);

        playerTex = atlas.findRegion("player");
        floorTex = atlas.findRegion("player");
        enemyTex = atlas.findRegion("enemy");
        platformTex = atlas.findRegion("player");


    }



    public void createPlayer(OrthographicCamera camera){

        // Create the Entity and all the components that will go in the entity
        Entity entity = engine.createEntity();
        B2dBodyComponent bodyCom = engine.createComponent(B2dBodyComponent.class);
        TransformComponent position = engine.createComponent(TransformComponent.class);
        TextureComponent texture = engine.createComponent(TextureComponent.class);
        PlayerComponent player = engine.createComponent(PlayerComponent.class);
        CollisionComponent colComp = engine.createComponent(CollisionComponent.class);
        TypeComponent type = engine.createComponent(TypeComponent.class);
        StateComponent stateCom = engine.createComponent(StateComponent.class);

        // create the data for the components and add them to the components
        bodyCom.body = bodyFactory.makeCirclePolyBody(20, 2, 1f, BodyFactory.WOOD, BodyDef.BodyType.DynamicBody, true);

        // set object position (x,y,z) z used to define draw order 0 first drawn
        position.position.set(bodyCom.body.getPosition().x, bodyCom.body.getPosition().y,0);
        texture.region = playerTex;
        type.type = TypeComponent.PLAYER;
        stateCom.set(StateComponent.STATE_NORMAL);

        bodyCom.body.setUserData(entity);

        player.camera = camera;


        // add the components to the entity
        entity.add(bodyCom);
        entity.add(position);
        entity.add(texture);
        entity.add(player);
        entity.add(colComp);
        entity.add(type);
        entity.add(stateCom);

        // add the entity to the engine
        engine.addEntity(entity);

    }




    public void createPlatform(float x, float y){

        // Create the Entity and all the components that will go in the entity
        Entity entity = engine.createEntity();
        B2dBodyComponent bodyCom = engine.createComponent(B2dBodyComponent.class);
        TextureComponent texture = engine.createComponent(TextureComponent.class);
        TypeComponent type = engine.createComponent(TypeComponent.class);

        // create the data for the components and add them to the components
        bodyCom.body = bodyFactory.makeBoxPolyBody(x, y, 52, 0.2f, BodyFactory.STONE, BodyDef.BodyType.StaticBody, true);

        // set object position (x,y,z) z used to define draw order 0 first drawn
        texture.region = platformTex;
        type.type = TypeComponent.SCENERY;

        bodyCom.body.setUserData(entity);

        // add the components to the entity
        entity.add(bodyCom);
        entity.add(texture);
        entity.add(type);

        // add the entity to the engine
        engine.addEntity(entity);

    }

    public void createFloor(){
        // Create the Entity and all the components that will go in the entity
        Entity entity = engine.createEntity();
        B2dBodyComponent bodyCom = engine.createComponent(B2dBodyComponent.class);
        TextureComponent texture = engine.createComponent(TextureComponent.class);
        TypeComponent type = engine.createComponent(TypeComponent.class);

        // create the data for the components and add them to the components
        bodyCom.body = bodyFactory.makeBoxPolyBody(0, -3, 500, 1f, BodyFactory.STONE, BodyDef.BodyType.StaticBody, true);

        // set object position (x,y,z) z used to define draw order 0 first drawn
        texture.region = floorTex;
        type.type = TypeComponent.SCENERY;

        bodyCom.body.setUserData(entity);

        // add the components to the entity
        entity.add(bodyCom);
        entity.add(texture);
        entity.add(type);

        // add the entity to the engine
        engine.addEntity(entity);
    }



    public void createTiledMapEntities(String layer, int type) {

        Array<Body> mapBodies = mapBodyFactory.buildShapes(map, world, layer);

        for (Body body : mapBodies) {
            System.out.println(body.getPosition());
            Entity entity = engine.createEntity();
            TransformComponent position = engine.createComponent(TransformComponent.class);
            B2dBodyComponent bodyCom = engine.createComponent(B2dBodyComponent.class);
            TypeComponent typeCom = engine.createComponent(TypeComponent.class);

            bodyCom.body = body;
            position.position.set(body.getPosition().x, body.getPosition().y, 0);
            body.setUserData(entity);
            typeCom.type = type;

            entity.add(position);
            entity.add(bodyCom);
            entity.add(typeCom);


        }
    }

    public TiledMap getMap(){
        return map;
    }

    public World getWorld(){
        return world;
    }

}
