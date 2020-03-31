package blog.gamedevelopmentbox2dtutorial.Factory;


import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.TiledMapTileSets;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

import blog.gamedevelopmentbox2dtutorial.Box2dContactListener;
import blog.gamedevelopmentbox2dtutorial.Box2dTutorial;
import blog.gamedevelopmentbox2dtutorial.DFUtils;
import blog.gamedevelopmentbox2dtutorial.entity.components.AnimationComponent;
import blog.gamedevelopmentbox2dtutorial.entity.components.B2dBodyComponent;
import blog.gamedevelopmentbox2dtutorial.entity.components.BulletComponent;
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
    private B2dAssetManager assMan;
    private TextureRegion floorTex;
    private TextureRegion enemyTex;
    private TextureRegion platformTex;
    private TextureRegion playerTex;
    private TextureRegion bulletTex;




    private int mapPixelWidth;
    private int mapTileWidth;


    private float finishPosition;




    public LevelFactory(PooledEngine engine, B2dAssetManager assMan){
        this.engine = engine;
        world = new World(new Vector2(0,-20f), true);
        world.setContactListener(new Box2dContactListener());
        this.assMan = assMan;

        bodyFactory = BodyFactory.getInstance(world);
        mapBodyFactory = MapBodyFactory.getInstance(world);

        atlas = assMan.manager.get("images/game.atlas");
        map = assMan.manager.get("maps/level_test.tmx", TiledMap.class);

        //finds the position of finish line
        MapLayers var1 = map.getLayers();
        MapLayer var2 = var1.get(10); //Gets the object layer "Finish"
        for (MapObject mapObject : var2.getObjects()){
            if (mapObject instanceof RectangleMapObject) {
                    if(mapObject.getName().equals("Finish")){
                        finishPosition = ((RectangleMapObject) mapObject).getRectangle().getX() ;
                        break;}
                }
            else{
                System.out.println("No finish added in tiled map.");
            }
        }








        //playerTex = atlas.findRegion("running");
        //floorTex = atlas.findRegion("player");
        //enemyTex = atlas.findRegion("enemy");
        //platformTex = atlas.findRegion("platform");;
        //bulletTex = DFUtils.makeTextureRegion(10,10,"444444FF");



    }



    public Entity createPlayer(OrthographicCamera camera){

        // Create the Entity and all the components that will go in the entity
        Entity entity = engine.createEntity();
        B2dBodyComponent bodyCom = engine.createComponent(B2dBodyComponent.class);
        TransformComponent position = engine.createComponent(TransformComponent.class);
        TextureComponent texture = engine.createComponent(TextureComponent.class);
        PlayerComponent player = engine.createComponent(PlayerComponent.class);
        CollisionComponent colComp = engine.createComponent(CollisionComponent.class);
        TypeComponent type = engine.createComponent(TypeComponent.class);
        StateComponent stateCom = engine.createComponent(StateComponent.class);
        AnimationComponent animCom = engine.createComponent(AnimationComponent.class);

        // create the data for the components and add them to the components
        bodyCom.body = bodyFactory.makeCirclePolyBody(2, 10, 0.25f, BodyFactory.WOOD, BodyDef.BodyType.DynamicBody, true);

        //Animation anim = new Animation(0.1f,atlas.findRegions("flame_a"));
        Animation runAnim = new Animation(0.05f,DFUtils.spriteSheetToFrames(atlas.findRegion("running"), 9, 1));
        Animation jumpAnim = new Animation(0.01f,DFUtils.spriteSheetToFrames(atlas.findRegion("jumping"), 1, 1));
        Animation normalAnim = new Animation(0.01f,DFUtils.spriteSheetToFrames(atlas.findRegion("normal"), 1, 1));


        runAnim.setPlayMode(Animation.PlayMode.LOOP);
        normalAnim.setPlayMode(Animation.PlayMode.LOOP);
        jumpAnim.setPlayMode(Animation.PlayMode.LOOP);

        animCom.animations.put(StateComponent.STATE_NORMAL, normalAnim);
        animCom.animations.put(StateComponent.STATE_MOVING, runAnim);
        animCom.animations.put(StateComponent.STATE_JUMPING, jumpAnim);
        animCom.animations.put(StateComponent.STATE_FALLING, jumpAnim);
        //animCom.animations.put(StateComponent.STATE_HIT, anim);




        // set object position (x,y,z) z used to define draw order 0 first drawn
        position.position.set(bodyCom.body.getPosition().x/Box2dTutorial.PPM, bodyCom.body.getPosition().y/Box2dTutorial.PPM,0);
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
        entity.add(animCom);

        // add the entity to the engine
        engine.addEntity(entity);

        return entity;

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
            Entity entity = engine.createEntity();
            TransformComponent position = engine.createComponent(TransformComponent.class);
            B2dBodyComponent bodyCom = engine.createComponent(B2dBodyComponent.class);
            TypeComponent typeCom = engine.createComponent(TypeComponent.class);


            bodyCom.body = body;


            //Make superspeed and gun objects sensors
            if (type == TypeComponent.SUPER_SPEED || type == TypeComponent.GUN || type == TypeComponent.SPEED_X || type == TypeComponent.SPEED_Y){
                bodyFactory.makeAllFixturesSensors(body);
            }


            //Sett hvilke type som er shootable
            if (type == TypeComponent.GROUND){
                typeCom.shootable = true;
            }

            position.position.set(body.getPosition().x, body.getPosition().y, 0);
            body.setUserData(entity);
            typeCom.type = type;

            entity.add(position);
            entity.add(bodyCom);
            entity.add(typeCom);
        }
    }

    public Entity createBullet(float x, float y, float xVel, float yVel){
        Entity entity = engine.createEntity();
        B2dBodyComponent b2dbody = engine.createComponent(B2dBodyComponent.class);
        TransformComponent position = engine.createComponent(TransformComponent.class);
        TextureComponent texture = engine.createComponent(TextureComponent.class);
        TypeComponent type = engine.createComponent(TypeComponent.class);
        CollisionComponent colComp = engine.createComponent(CollisionComponent.class);
        BulletComponent bul = engine.createComponent(BulletComponent.class);
        AnimationComponent animCom = engine.createComponent(AnimationComponent.class);
        StateComponent stateCom = engine.createComponent(StateComponent.class);

        b2dbody.body = bodyFactory.makeCirclePolyBody(x,y,0.25f, BodyFactory.STONE, BodyDef.BodyType.DynamicBody,true);
        b2dbody.body.setBullet(true); // increase physics computation to limit body travelling through other objects
        bodyFactory.makeAllFixturesSensors(b2dbody.body); // make bullets sensors so they don't move player
        position.position.set(x,y,0);

        //texture.region = bulletTex;
        Animation anim = new Animation(0.1f,DFUtils.spriteSheetToFrames(atlas.findRegion("boomerang"), 8, 1));        anim.setPlayMode(Animation.PlayMode.LOOP);
        anim.setPlayMode(Animation.PlayMode.LOOP);


        animCom.animations.put(StateComponent.STATE_NORMAL, anim);

        stateCom.set(StateComponent.STATE_NORMAL);
        type.type = TypeComponent.BULLET;
        b2dbody.body.setUserData(entity);
        bul.xVel = xVel;
        bul.yVel = yVel;

        entity.add(bul);
        entity.add(colComp);
        entity.add(b2dbody);
        entity.add(position);
        entity.add(texture);
        entity.add(type);
        entity.add(stateCom);
        entity.add(animCom);

        engine.addEntity(entity);
        return entity;
    }

    public TiledMap getMap(){
        return map;
    }

    public World getWorld(){
        return world;
    }

    public float getFinishPosition() {
        return finishPosition;
    }


}
