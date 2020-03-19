package blog.gamedevelopmentbox2dtutorial.entity.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import blog.gamedevelopmentbox2dtutorial.entity.components.BulletComponent;
import blog.gamedevelopmentbox2dtutorial.entity.components.CollisionComponent;
import blog.gamedevelopmentbox2dtutorial.entity.components.Mapper;
import blog.gamedevelopmentbox2dtutorial.entity.components.PlayerComponent;
import blog.gamedevelopmentbox2dtutorial.entity.components.TransformComponent;
import blog.gamedevelopmentbox2dtutorial.entity.components.TypeComponent;

public class CollisionSystem extends IteratingSystem {


    @SuppressWarnings("unchecked")
    public CollisionSystem() {
        // only need to worry about player collisions
        super(Family.all(CollisionComponent.class).get());


    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        // get player collision component
        CollisionComponent cc = Mapper.collisionCom.get(entity);

        Entity collidedEntity = cc.collisionEntity;

        TypeComponent thisType = entity.getComponent(TypeComponent.class);


        //Handle player collison
        if(thisType.type == TypeComponent.PLAYER) {
            if (collidedEntity != null) {
                TypeComponent type = collidedEntity.getComponent(TypeComponent.class);
                if (type != null) {
                    switch (type.type) {
                        case TypeComponent.ENEMY:
                            //do player hit enemy thing
                            System.out.println("player hit enemy");
                            break;
                        case TypeComponent.SUPER_SPEED:
                            //do player hit superspeed thing
                            System.out.println("player picked up superSpeed");
                            Mapper.playerCom.get(entity).superSpeed = true;
                            break; //technically this isn't needed
                        case TypeComponent.GUN:
                            //do player hit gun thing
                            System.out.println("player picked up gun");
                            Mapper.playerCom.get(entity).hasGun = true;
                            break; //technically this isn't needed
                        case TypeComponent.GROUND:
                            //do player hit ground thing
                            Mapper.playerCom.get(entity).onGround = true;
                            System.out.println("player hit ground");
                            break; //technically this isn't needed
                        case TypeComponent.SPRING:
                            //do player hit spring thing
                            System.out.println("player on spring: bounce up");
                            Mapper.playerCom.get(entity).onSpring = true;
                            break; //technically this isn't needed
                        case TypeComponent.BULLET:
                            System.out.println("Player just shot. bullet in player atm");
                            break;
                        case TypeComponent.OTHER:
                            //do player hit scenery thing
                            System.out.println("player hit other");
                            break;
                        default:
                            System.out.println("No matching type found");
                    }
                    cc.collisionEntity = null; // collision handled reset component
                } else {
                    System.out.println("type == null");
                }
            }
        }


        //handle shooting
        if(thisType.type == TypeComponent.BULLET) {
            if (collidedEntity != null) {
                TypeComponent type = collidedEntity.getComponent(TypeComponent.class);
                if (type.shootable) {
                    System.out.println("Shootable object just shot");
                    BulletComponent bullet = Mapper.bulletCom.get(entity);
                    bullet.isDead = true;
                    }
                    cc.collisionEntity = null; // collision handled reset component

            }
        }
    }
}