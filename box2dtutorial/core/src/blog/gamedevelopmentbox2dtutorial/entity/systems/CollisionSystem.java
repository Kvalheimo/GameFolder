package blog.gamedevelopmentbox2dtutorial.entity.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import blog.gamedevelopmentbox2dtutorial.entity.components.CollisionComponent;
import blog.gamedevelopmentbox2dtutorial.entity.components.PlayerComponent;
import blog.gamedevelopmentbox2dtutorial.entity.components.TransformComponent;
import blog.gamedevelopmentbox2dtutorial.entity.components.TypeComponent;

public class CollisionSystem extends IteratingSystem {
    ComponentMapper<CollisionComponent> cm;
    ComponentMapper<PlayerComponent> pm;

    @SuppressWarnings("unchecked")
    public CollisionSystem() {
        // only need to worry about player collisions
        super(Family.all(CollisionComponent.class,PlayerComponent.class, TransformComponent.class).get());

        cm = ComponentMapper.getFor(CollisionComponent.class);
        pm = ComponentMapper.getFor(PlayerComponent.class);

    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        // get player collision component
        CollisionComponent cc = cm.get(entity);

        Entity collidedEntity = cc.collisionEntity;
        if(collidedEntity != null){
            TypeComponent type = collidedEntity.getComponent(TypeComponent.class);
            if(type != null){
                switch(type.type){
                    case TypeComponent.ENEMY:
                        //do player hit enemy thing
                        System.out.println("player hit enemy");
                        break;
                    case TypeComponent.SUPER_SPEED:
                        //do player hit other thing
                        System.out.println("player hit superSpeed");
                        pm.get(entity).superSpeed = true;
                        break; //technically this isn't needed
                    case TypeComponent.GUN:
                        //do player hit other thing
                        System.out.println("player hit gun");
                        pm.get(entity).hasGun = true;
                        break; //technically this isn't needed
                    case TypeComponent.GROUND:
                        //do player hit other thing
                        System.out.println("player hit ground");
                        break; //technically this isn't needed
                    case TypeComponent.SPRING:
                        //do player hit other thing
                        System.out.println("player hit gun");
                        pm.get(entity).onSpring = true;
                        break; //technically this isn't needed
                    case TypeComponent.OTHER:
                        //do player hit scenery thing
                        System.out.println("player hit other");
                        break;
                }
                cc.collisionEntity = null; // collision handled reset component
            }
        }

    }

}