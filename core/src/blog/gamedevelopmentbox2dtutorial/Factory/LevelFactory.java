package blog.gamedevelopmentbox2dtutorial.Factory;


import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.Map;
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
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntMap;

import java.util.ArrayList;
import com.badlogic.gdx.utils.ObjectMap;

import blog.gamedevelopmentbox2dtutorial.Box2dContactListener;
import blog.gamedevelopmentbox2dtutorial.Box2dTutorial;
import blog.gamedevelopmentbox2dtutorial.DFUtils;
import blog.gamedevelopmentbox2dtutorial.ParticleEffectManager;
import blog.gamedevelopmentbox2dtutorial.entity.components.AnimationComponent;
import blog.gamedevelopmentbox2dtutorial.entity.components.B2dBodyComponent;
import blog.gamedevelopmentbox2dtutorial.entity.components.BulletComponent;
import blog.gamedevelopmentbox2dtutorial.entity.components.CollisionComponent;
import blog.gamedevelopmentbox2dtutorial.entity.components.EnemyComponent;
import blog.gamedevelopmentbox2dtutorial.entity.components.OpponentComponent;
import blog.gamedevelopmentbox2dtutorial.entity.components.ParticleEffectComponent;
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
    private IntMap<TiledMap> maps;
    private TextureAtlas atlas;
    private Box2dTutorial parent;
    private ParticleEffectManager peMan;
    private Animation runAnim, normalAnim, jumpAnim, slideAnim, fallAnim;
    private Animation runAnimB, normalAnimB, jumpAnimB, slideAnimB, fallAnimB;
    private float finishPosition;




    public LevelFactory(PooledEngine engine, Box2dTutorial parent){
        this.engine = engine;
        this.parent = parent;

        world = new World(new Vector2(0,-Box2dTutorial.GRAVITY), true);
        world.setContactListener(new Box2dContactListener());

        bodyFactory = BodyFactory.getInstance(world);
        mapBodyFactory = MapBodyFactory.getInstance(world);

        atlas = parent.assMan.manager.get("images/game.atlas");

        loadParticleEffects();
        loadMaps();


        TiledMap map = maps.get(1);

        //finds the position of finish line
        MapLayers var1 = map.getLayers();
        MapLayer var2 = var1.get(10); //Gets the object layer "Finish"
        for (MapObject mapObject : var2.getObjects()){
            if (mapObject instanceof RectangleMapObject) {
                    if(mapObject.getName().equals("finished")){
                        finishPosition = ((RectangleMapObject) mapObject).getRectangle().getX() ;
                        break;}
                }
            else{
                System.out.println("No finish added in tiled map.");
            }
        }






    }

    private void loadMaps(){
        maps = new IntMap<TiledMap>();
        maps.put(1, parent.assMan.manager.get("maps/level1.tmx", TiledMap.class));

    }

    private void loadParticleEffects(){
        peMan = new ParticleEffectManager();

        peMan.addParticleEffect(ParticleEffectManager.SMOKE, parent.assMan.manager.get("particles/smoke.p",ParticleEffect.class),1f/Box2dTutorial.PPM);
        peMan.addParticleEffect(ParticleEffectManager.EXPLOSION, parent.assMan.manager.get("particles/explosion.p", ParticleEffect.class),1f/Box2dTutorial.PPM);
        peMan.addParticleEffect(ParticleEffectManager.BLOOD, parent.assMan.manager.get("particles/blood.p",ParticleEffect.class),1f/Box2dTutorial.PPM);
        peMan.addParticleEffect(ParticleEffectManager.SPLASH, parent.assMan.manager.get("particles/splash.p",ParticleEffect.class),1f/Box2dTutorial.PPM);
        peMan.addParticleEffect(ParticleEffectManager.SPEED, parent.assMan.manager.get("particles/speed.p",ParticleEffect.class),1f/Box2dTutorial.PPM);
        peMan.addParticleEffect(ParticleEffectManager.POWER_UP, parent.assMan.manager.get("particles/powerup.p",ParticleEffect.class),1f/Box2dTutorial.PPM);
        peMan.addParticleEffect(ParticleEffectManager.TEST, parent.assMan.manager.get("particles/test.p",ParticleEffect.class),1f/Box2dTutorial.PPM);

        peMan.addParticleEffect(ParticleEffectManager.BULLET_RIGHT, parent.assMan.manager.get("particles/bullet_right.p",ParticleEffect.class),1f/Box2dTutorial.PPM);
        peMan.addParticleEffect(ParticleEffectManager.BULLET_LEFT, parent.assMan.manager.get("particles/bullet_left.p",ParticleEffect.class),1f/Box2dTutorial.PPM);

        peMan.addParticleEffect(ParticleEffectManager.SUPERSPEED_RIGHT, parent.assMan.manager.get("particles/superspeed_right.p",ParticleEffect.class),1f/Box2dTutorial.PPM);
        peMan.addParticleEffect(ParticleEffectManager.SUPERSPEED_LEFT, parent.assMan.manager.get("particles/superspeed_left.p",ParticleEffect.class),1f/Box2dTutorial.PPM);
        peMan.addParticleEffect(ParticleEffectManager.POWERUP_SPEED, parent.assMan.manager.get("particles/powerup_speed.p",ParticleEffect.class),1f/Box2dTutorial.PPM);
        peMan.addParticleEffect(ParticleEffectManager.POWERUP_GUN, parent.assMan.manager.get("particles/powerup_gun.p",ParticleEffect.class),1f/Box2dTutorial.PPM);



    }



    public Entity createPlayer(OrthographicCamera camera, int character){

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
        bodyCom.body = bodyFactory.makeCirclePolyBody(2, 10, 0.20f, BodyFactory.WOOD, BodyDef.BodyType.DynamicBody, true);

        switch (character) {
            case 1:
                //Animation anim = new Animation(0.1f,atlas.findRegions("flame_a"));
                runAnim = new Animation(0.05f, DFUtils.spriteSheetToFrames(atlas.findRegion("p1_running"), 9, 1));
                jumpAnim = new Animation(0.1f, DFUtils.spriteSheetToFrames(atlas.findRegion("p1_jumping"), 1, 1));
                normalAnim = new Animation(0.1f, DFUtils.spriteSheetToFrames(atlas.findRegion("p1_normal"), 1, 1));
                slideAnim = new Animation(0.1f, DFUtils.spriteSheetToFrames(atlas.findRegion("p1_sliding"), 1, 1));
                fallAnim = new Animation(0.1f, DFUtils.spriteSheetToFrames(atlas.findRegion("p1_falling"), 1, 1));

                runAnimB = new Animation(0.05f, DFUtils.spriteSheetToFrames(atlas.findRegion("p1_running_b"), 9, 1));
                jumpAnimB = new Animation(0.1f, DFUtils.spriteSheetToFrames(atlas.findRegion("p1_jumping_b"), 1, 1));
                normalAnimB = new Animation(0.1f, DFUtils.spriteSheetToFrames(atlas.findRegion("p1_normal_b"), 1, 1));
                slideAnimB = new Animation(0.1f, DFUtils.spriteSheetToFrames(atlas.findRegion("p1_sliding_b"), 1, 1));
                fallAnimB = new Animation(0.1f, DFUtils.spriteSheetToFrames(atlas.findRegion("p1_falling_b"), 1, 1));
                break;
            case 2:
                runAnim = new Animation(0.05f, DFUtils.spriteSheetToFrames(atlas.findRegion("p2_running"), 9, 1));
                jumpAnim = new Animation(0.1f, DFUtils.spriteSheetToFrames(atlas.findRegion("p2_jumping"), 1, 1));
                normalAnim = new Animation(0.1f, DFUtils.spriteSheetToFrames(atlas.findRegion("p2_normal"), 1, 1));
                slideAnim = new Animation(0.1f, DFUtils.spriteSheetToFrames(atlas.findRegion("p2_sliding"), 1, 1));
                fallAnim = new Animation(0.1f, DFUtils.spriteSheetToFrames(atlas.findRegion("p2_falling"), 1, 1));


                runAnimB = new Animation(0.05f, DFUtils.spriteSheetToFrames(atlas.findRegion("p2_running_b"), 9, 1));
                jumpAnimB = new Animation(0.1f, DFUtils.spriteSheetToFrames(atlas.findRegion("p2_jumping_b"), 1, 1));
                normalAnimB = new Animation(0.1f, DFUtils.spriteSheetToFrames(atlas.findRegion("p2_normal_b"), 1, 1));
                slideAnimB = new Animation(0.1f, DFUtils.spriteSheetToFrames(atlas.findRegion("p2_sliding_b"), 1, 1));
                fallAnimB = new Animation(0.1f, DFUtils.spriteSheetToFrames(atlas.findRegion("p2_falling_b"), 1, 1));
                break;
            case 3:
                runAnim = new Animation(0.05f, DFUtils.spriteSheetToFrames(atlas.findRegion("p1_running"), 9, 1));
                jumpAnim = new Animation(0.1f, DFUtils.spriteSheetToFrames(atlas.findRegion("p1_jumping"), 1, 1));
                normalAnim = new Animation(0.1f, DFUtils.spriteSheetToFrames(atlas.findRegion("p1_normal"), 1, 1));
                slideAnim = new Animation(0.1f, DFUtils.spriteSheetToFrames(atlas.findRegion("p1_sliding"), 1, 1));
                slideAnim = new Animation(0.1f, DFUtils.spriteSheetToFrames(atlas.findRegion("p1_falling"), 1, 1));

                runAnimB = new Animation(0.05f, DFUtils.spriteSheetToFrames(atlas.findRegion("p1_running_b"), 9, 1));
                jumpAnimB = new Animation(0.1f, DFUtils.spriteSheetToFrames(atlas.findRegion("p1_jumping_b"), 1, 1));
                normalAnimB = new Animation(0.1f, DFUtils.spriteSheetToFrames(atlas.findRegion("p1_normal_b"), 1, 1));
                slideAnimB = new Animation(0.1f, DFUtils.spriteSheetToFrames(atlas.findRegion("p1_sliding_b"), 1, 1));
                slideAnim = new Animation(0.1f, DFUtils.spriteSheetToFrames(atlas.findRegion("p1_falling_b"), 1, 1));
                break;
        }


        runAnim.setPlayMode(Animation.PlayMode.LOOP);
        normalAnim.setPlayMode(Animation.PlayMode.LOOP);
        jumpAnim.setPlayMode(Animation.PlayMode.LOOP);
        fallAnim.setPlayMode(Animation.PlayMode.LOOP);
        slideAnim.setPlayMode(Animation.PlayMode.LOOP);

        runAnimB.setPlayMode(Animation.PlayMode.LOOP);
        normalAnimB.setPlayMode(Animation.PlayMode.LOOP);
        jumpAnimB.setPlayMode(Animation.PlayMode.LOOP);
        fallAnimB.setPlayMode(Animation.PlayMode.LOOP);
        slideAnimB.setPlayMode(Animation.PlayMode.LOOP);


        animCom.animationsN.put(StateComponent.STATE_NORMAL, normalAnim);
        animCom.animationsN.put(StateComponent.STATE_MOVING, runAnim);
        animCom.animationsN.put(StateComponent.STATE_JUMPING, jumpAnim);
        animCom.animationsN.put(StateComponent.STATE_FALLING, fallAnim);
        animCom.animationsN.put(StateComponent.STATE_SLIDING, slideAnim);

        animCom.animationsB.put(StateComponent.STATE_NORMAL, normalAnimB);
        animCom.animationsB.put(StateComponent.STATE_MOVING, runAnimB);
        animCom.animationsB.put(StateComponent.STATE_JUMPING, jumpAnimB);
        animCom.animationsB.put(StateComponent.STATE_FALLING, fallAnimB);
        animCom.animationsB.put(StateComponent.STATE_SLIDING, slideAnimB);



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

    public Entity createOpponent(Vector3 pos, int character){
        Entity entity = engine.createEntity();
//        B2dBodyComponent bodyCom = engine.createComponent(B2dBodyComponent.class);
        TransformComponent position = engine.createComponent(TransformComponent.class);
        TextureComponent texture = engine.createComponent(TextureComponent.class);
        TypeComponent type = engine.createComponent(TypeComponent.class);
        StateComponent stateCom = engine.createComponent(StateComponent.class);
        AnimationComponent animCom = engine.createComponent(AnimationComponent.class);
        OpponentComponent opponentCom = engine.createComponent(OpponentComponent.class);

//        bodyCom.body = bodyFactory.makeCirclePolyBody(pos.x, pos.y, 0.20f, BodyFactory.WOOD, BodyDef.BodyType.DynamicBody, true);

        switch (character) {
            case 1:
                //Animation anim = new Animation(0.1f,atlas.findRegions("flame_a"));
                runAnim = new Animation(0.05f, DFUtils.spriteSheetToFrames(atlas.findRegion("p1_running"), 9, 1));
                jumpAnim = new Animation(0.1f, DFUtils.spriteSheetToFrames(atlas.findRegion("p1_jumping"), 1, 1));
                normalAnim = new Animation(0.1f, DFUtils.spriteSheetToFrames(atlas.findRegion("p1_normal"), 1, 1));
                slideAnim = new Animation(0.1f, DFUtils.spriteSheetToFrames(atlas.findRegion("p1_sliding"), 1, 1));
                fallAnim = new Animation(0.1f, DFUtils.spriteSheetToFrames(atlas.findRegion("p1_falling"), 1, 1));

                runAnimB = new Animation(0.05f, DFUtils.spriteSheetToFrames(atlas.findRegion("p1_running_b"), 9, 1));
                jumpAnimB = new Animation(0.1f, DFUtils.spriteSheetToFrames(atlas.findRegion("p1_jumping_b"), 1, 1));
                normalAnimB = new Animation(0.1f, DFUtils.spriteSheetToFrames(atlas.findRegion("p1_normal_b"), 1, 1));
                slideAnimB = new Animation(0.1f, DFUtils.spriteSheetToFrames(atlas.findRegion("p1_sliding_b"), 1, 1));
                fallAnimB = new Animation(0.1f, DFUtils.spriteSheetToFrames(atlas.findRegion("p1_falling_b"), 1, 1));
                break;
            case 2:
                runAnim = new Animation(0.05f, DFUtils.spriteSheetToFrames(atlas.findRegion("p2_running"), 9, 1));
                jumpAnim = new Animation(0.1f, DFUtils.spriteSheetToFrames(atlas.findRegion("p2_jumping"), 1, 1));
                normalAnim = new Animation(0.1f, DFUtils.spriteSheetToFrames(atlas.findRegion("p2_normal"), 1, 1));
                slideAnim = new Animation(0.1f, DFUtils.spriteSheetToFrames(atlas.findRegion("p2_sliding"), 1, 1));
                fallAnim = new Animation(0.1f, DFUtils.spriteSheetToFrames(atlas.findRegion("p2_falling"), 1, 1));


                runAnimB = new Animation(0.05f, DFUtils.spriteSheetToFrames(atlas.findRegion("p2_running_b"), 9, 1));
                jumpAnimB = new Animation(0.1f, DFUtils.spriteSheetToFrames(atlas.findRegion("p2_jumping_b"), 1, 1));
                normalAnimB = new Animation(0.1f, DFUtils.spriteSheetToFrames(atlas.findRegion("p2_normal_b"), 1, 1));
                slideAnimB = new Animation(0.1f, DFUtils.spriteSheetToFrames(atlas.findRegion("p2_sliding_b"), 1, 1));
                fallAnimB = new Animation(0.1f, DFUtils.spriteSheetToFrames(atlas.findRegion("p2_falling_b"), 1, 1));
                break;
            case 3:
                runAnim = new Animation(0.05f, DFUtils.spriteSheetToFrames(atlas.findRegion("p1_running"), 9, 1));
                jumpAnim = new Animation(0.1f, DFUtils.spriteSheetToFrames(atlas.findRegion("p1_jumping"), 1, 1));
                normalAnim = new Animation(0.1f, DFUtils.spriteSheetToFrames(atlas.findRegion("p1_normal"), 1, 1));
                slideAnim = new Animation(0.1f, DFUtils.spriteSheetToFrames(atlas.findRegion("p1_sliding"), 1, 1));
                slideAnim = new Animation(0.1f, DFUtils.spriteSheetToFrames(atlas.findRegion("p1_falling"), 1, 1));

                runAnimB = new Animation(0.05f, DFUtils.spriteSheetToFrames(atlas.findRegion("p1_running_b"), 9, 1));
                jumpAnimB = new Animation(0.1f, DFUtils.spriteSheetToFrames(atlas.findRegion("p1_jumping_b"), 1, 1));
                normalAnimB = new Animation(0.1f, DFUtils.spriteSheetToFrames(atlas.findRegion("p1_normal_b"), 1, 1));
                slideAnimB = new Animation(0.1f, DFUtils.spriteSheetToFrames(atlas.findRegion("p1_sliding_b"), 1, 1));
                slideAnim = new Animation(0.1f, DFUtils.spriteSheetToFrames(atlas.findRegion("p1_falling_b"), 1, 1));
                break;
        }

        runAnim.setPlayMode(Animation.PlayMode.LOOP);
        normalAnim.setPlayMode(Animation.PlayMode.LOOP);
        jumpAnim.setPlayMode(Animation.PlayMode.LOOP);
        fallAnim.setPlayMode(Animation.PlayMode.LOOP);
        slideAnim.setPlayMode(Animation.PlayMode.LOOP);

        runAnimB.setPlayMode(Animation.PlayMode.LOOP);
        normalAnimB.setPlayMode(Animation.PlayMode.LOOP);
        jumpAnimB.setPlayMode(Animation.PlayMode.LOOP);
        fallAnimB.setPlayMode(Animation.PlayMode.LOOP);
        slideAnimB.setPlayMode(Animation.PlayMode.LOOP);

        animCom.animationsN.put(StateComponent.STATE_NORMAL, normalAnim);
        animCom.animationsN.put(StateComponent.STATE_MOVING, runAnim);
        animCom.animationsN.put(StateComponent.STATE_JUMPING, jumpAnim);
        animCom.animationsN.put(StateComponent.STATE_FALLING, jumpAnim);

        animCom.animationsB.put(StateComponent.STATE_NORMAL, normalAnim);
        animCom.animationsB.put(StateComponent.STATE_MOVING, runAnim);
        animCom.animationsB.put(StateComponent.STATE_JUMPING, jumpAnim);
        animCom.animationsB.put(StateComponent.STATE_FALLING, jumpAnim);
//        position.position.set(bodyCom.body.getPosition().x/Box2dTutorial.PPM, bodyCom.body.getPosition().y/Box2dTutorial.PPM,0);
        position.position.set(pos.x, pos.y, 0);
        opponentCom.setPos(pos);

        type.type = TypeComponent.PLAYER;
        stateCom.set(StateComponent.STATE_NORMAL);

//        bodyCom.body.setUserData(entity);

        // add the components to the entity
//        entity.add(bodyCom);
        entity.add(position);
        entity.add(texture);
        entity.add(type);
        entity.add(stateCom);
        entity.add(animCom);
        entity.add(opponentCom);

        // add the entity to the engine
        engine.addEntity(entity);
        return entity;
    }

    public void createBats(int level){
        Array<Body> enemyBodies  = mapBodyFactory.buildShapes(maps.get(1), world, "Bats", BodyDef.BodyType.DynamicBody, bodyFactory.STEEL);

        for (Body enemyBody: enemyBodies) {

            // Create the Entity and all the components that will go in the entity
            Entity entity = engine.createEntity();
            B2dBodyComponent bodyCom = engine.createComponent(B2dBodyComponent.class);
            TransformComponent position = engine.createComponent(TransformComponent.class);
            TextureComponent texture = engine.createComponent(TextureComponent.class);
            EnemyComponent enemyCom = engine.createComponent(EnemyComponent.class);
            CollisionComponent colComp = engine.createComponent(CollisionComponent.class);
            TypeComponent type = engine.createComponent(TypeComponent.class);
            StateComponent stateCom = engine.createComponent(StateComponent.class);
            AnimationComponent animCom = engine.createComponent(AnimationComponent.class);

            // create the data for the components and add them to the components
            enemyBody.setActive(false);
            enemyCom.type = enemyCom.BAT;
            bodyCom.body = enemyBody;

            //Animation anim = new Animation(0.1f,atlas.findRegions("flame_a"));
            Animation movingAnim = new Animation(0.1f, DFUtils.spriteSheetToFrames(atlas.findRegion("bat"), 5, 1));
            Animation normalAnim = new Animation(0.1f, DFUtils.spriteSheetToFrames(atlas.findRegion("bat_normal"), 1, 1));


            movingAnim.setPlayMode(Animation.PlayMode.LOOP);
            normalAnim.setPlayMode(Animation.PlayMode.LOOP);

            animCom.animationsN.put(StateComponent.STATE_NORMAL, normalAnim);
            animCom.animationsN.put(StateComponent.STATE_MOVING, movingAnim);


            // set object position (x,y,z) z used to define draw order 0 first drawn
            //position.position.set(bodyCom.body.getPosition().x / Box2dTutorial.PPM, bodyCom.body.getPosition().y / Box2dTutorial.PPM, 0);

            position.position.set(bodyCom.body.getPosition().x, bodyCom.body.getPosition().y, 0);
            type.type = TypeComponent.ENEMY;
            stateCom.set(StateComponent.STATE_MOVING);

            //enemyCom.particleEffect = makeParticleEffect(ParticleEffectManager.TEST, bodyCom);



            bodyCom.body.setUserData(entity);


            // add the components to the entity
            entity.add(bodyCom);
            entity.add(position);
            entity.add(texture);
            entity.add(enemyCom);
            entity.add(colComp);
            entity.add(type);
            entity.add(stateCom);
            entity.add(animCom);

            // add the entity to the engine
            engine.addEntity(entity);
        }

    }



    public void createSpiders(int level){
        Array<Body> enemyBodies  = mapBodyFactory.buildShapes(maps.get(1), world, "Spiders", BodyDef.BodyType.DynamicBody, BodyFactory.RUBBER);

        for (Body enemyBody: enemyBodies) {

            // Create the Entity and all the components that will go in the entity
            Entity entity = engine.createEntity();
            B2dBodyComponent bodyCom = engine.createComponent(B2dBodyComponent.class);
            TransformComponent position = engine.createComponent(TransformComponent.class);
            TextureComponent texture = engine.createComponent(TextureComponent.class);
            EnemyComponent enemyCom = engine.createComponent(EnemyComponent.class);
            CollisionComponent colComp = engine.createComponent(CollisionComponent.class);
            TypeComponent type = engine.createComponent(TypeComponent.class);
            StateComponent stateCom = engine.createComponent(StateComponent.class);
            AnimationComponent animCom = engine.createComponent(AnimationComponent.class);

            // create the data for the components and add them to the components
            enemyBody.setActive(false);
            enemyCom.type = enemyCom.SPIDER;
            bodyCom.body = enemyBody;

            //Animation anim = new Animation(0.1f,atlas.findRegions("flame_a"));
            Animation movingAnim = new Animation(0.05f, DFUtils.spriteSheetToFrames(atlas.findRegion("spider"), 4, 1));
            Animation normalAnim = new Animation(0.1f, DFUtils.spriteSheetToFrames(atlas.findRegion("spider_normal"), 1, 1));


            movingAnim.setPlayMode(Animation.PlayMode.LOOP);
            normalAnim.setPlayMode(Animation.PlayMode.LOOP);

            animCom.animationsN.put(StateComponent.STATE_NORMAL, normalAnim);
            animCom.animationsN.put(StateComponent.STATE_MOVING, movingAnim);


            // set object position (x,y,z) z used to define draw order 0 first drawn
            //position.position.set(bodyCom.body.getPosition().x / Box2dTutorial.PPM, bodyCom.body.getPosition().y / Box2dTutorial.PPM, 0);

            position.position.set(bodyCom.body.getPosition().x, bodyCom.body.getPosition().y, 0);
            type.type = TypeComponent.ENEMY;

            stateCom.set(StateComponent.STATE_MOVING);
            //enemyCom.particleEffect = makeParticleEffect(ParticleEffectManager.TEST, bodyCom);



            bodyCom.body.setUserData(entity);


            // add the components to the entity
            entity.add(bodyCom);
            entity.add(position);
            entity.add(texture);
            entity.add(enemyCom);
            entity.add(colComp);
            entity.add(type);
            entity.add(stateCom);
            entity.add(animCom);

            // add the entity to the engine
            engine.addEntity(entity);
        }

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
        type.type = TypeComponent.SCENERY;

        bodyCom.body.setUserData(entity);

        // add the components to the entity
        entity.add(bodyCom);
        entity.add(texture);
        entity.add(type);

        // add the entity to the engine
        engine.addEntity(entity);

    }





    public void createTiledMapEntities(String layer, int type, int level) {


            Array<Body> mapBodies = mapBodyFactory.buildShapes(maps.get(1), world, layer, BodyDef.BodyType.StaticBody, BodyFactory.STONE);

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

            if (type == TypeComponent.WATER){
                //makeParticleEffect(ParticleEffectManager.WATER,body.getPosition().x, body.getPosition().y);
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

        Animation anim = new Animation(0.1f,DFUtils.spriteSheetToFrames(atlas.findRegion("boomerang"), 8, 1));        anim.setPlayMode(Animation.PlayMode.LOOP);
        anim.setPlayMode(Animation.PlayMode.LOOP);


        animCom.animationsN.put(StateComponent.STATE_NORMAL, anim);

        stateCom.set(StateComponent.STATE_NORMAL);
        type.type = TypeComponent.BULLET;

        bul.xVel = xVel;
        bul.yVel = yVel;

        if (xVel > 0) {
            bul.particleEffect = makeParticleEffect(ParticleEffectManager.BULLET_RIGHT, b2dbody);
        }else{
            bul.particleEffect = makeParticleEffect(ParticleEffectManager.BULLET_LEFT, b2dbody);
        }


        b2dbody.body.setUserData(entity);



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


    public Entity makeParticleEffect(int type, float x, float y){
        Entity entPE = engine.createEntity();
        ParticleEffectComponent pec = engine.createComponent(ParticleEffectComponent.class);
        pec.particleEffect = peMan.getPooledParticleEffect(type);
        pec.particleEffect.setPosition(x, y);
        entPE.add(pec);
        engine.addEntity(entPE);
        return entPE;
    }


    public Entity makeParticleEffect(int type, B2dBodyComponent b2dbody){
        return makeParticleEffect(type,b2dbody,0,0);
    }


    public Entity makeParticleEffect(int type, B2dBodyComponent b2dbody, float xOffset, float yOffset){
        Entity entPE = engine.createEntity();
        ParticleEffectComponent pec = engine.createComponent(ParticleEffectComponent.class);
        pec.particleEffect = peMan.getPooledParticleEffect(type);
        pec.particleEffect.setPosition(b2dbody.body.getPosition().x, b2dbody.body.getPosition().y);
        pec.particleEffect.getEmitters().first().setAttached(true); //manually attach for testing
        pec.xOffset = xOffset;
        pec.yOffset = yOffset;
        pec.isAttached = true;
        pec.attachedBody = b2dbody.body;
        entPE.add(pec);
        engine.addEntity(entPE);
        return entPE;
    }

    public TiledMap getMap(int level){
        return maps.get(1);
    }

    public World getWorld(){
        return world;
    }

    public float getFinishPosition() {
        return finishPosition;
    }


}
