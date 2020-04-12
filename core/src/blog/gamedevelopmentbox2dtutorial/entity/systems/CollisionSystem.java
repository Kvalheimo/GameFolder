package blog.gamedevelopmentbox2dtutorial.entity.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import blog.gamedevelopmentbox2dtutorial.Factory.LevelFactory;
import blog.gamedevelopmentbox2dtutorial.ParticleEffectManager;
import blog.gamedevelopmentbox2dtutorial.entity.components.B2dBodyComponent;
import blog.gamedevelopmentbox2dtutorial.entity.components.BulletComponent;
import blog.gamedevelopmentbox2dtutorial.entity.components.CollisionComponent;
import blog.gamedevelopmentbox2dtutorial.entity.components.EnemyComponent;
import blog.gamedevelopmentbox2dtutorial.entity.components.Mapper;
import blog.gamedevelopmentbox2dtutorial.entity.components.PlayerComponent;
import blog.gamedevelopmentbox2dtutorial.entity.components.TransformComponent;
import blog.gamedevelopmentbox2dtutorial.entity.components.TypeComponent;

public class CollisionSystem extends IteratingSystem {
    private LevelFactory levelFactory;

    @SuppressWarnings("unchecked")
    public CollisionSystem(LevelFactory levelFactory) {
        super(Family.all(CollisionComponent.class).get());
        this.levelFactory = levelFactory;


    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        // get player collision component
        CollisionComponent cc = Mapper.collisionCom.get(entity);

        Entity collidedEntity = cc.collisionEntity;

        TypeComponent thisType = entity.getComponent(TypeComponent.class);


        //Handle player collison
        if(thisType.type == TypeComponent.PLAYER) {
            PlayerComponent player = Mapper.playerCom.get(entity);
            B2dBodyComponent body = Mapper.b2dCom.get(entity);

            if (collidedEntity != null) {
                TypeComponent type = collidedEntity.getComponent(TypeComponent.class);
                if (type != null) {
                    switch (type.type) {
                        case TypeComponent.ENEMY:
                            //do player hit enemy thing
                            System.out.println("player hit enemy");

                            //player.isDead = true;
                            break;
                        case TypeComponent.SUPER_SPEED:
                            //do player hit superspeed thing
                            System.out.println("player picked up superSpeed");

                            player.particleEffect = levelFactory.makeParticleEffect(ParticleEffectManager.POWERUP_SPEED, body);
                            player.superSpeed = true;
                            break; //technically this isn't needed
                        case TypeComponent.GUN:
                            //do player hit gun thing
                            System.out.println("player picked up gun");

                            player.particleEffect = levelFactory.makeParticleEffect(ParticleEffectManager.POWERUP_GUN, body);
                            player.hasGun = true;
                            break; //technically this isn't needed
                        case TypeComponent.GROUND:
                            //do player hit ground thing
                            player.onGround = true;
                            System.out.println("player hit ground");
                            break; //technically this isn't needed
                        case TypeComponent.SPRING:
                            //do player hit spring thing
                            System.out.println("player on spring: bounce up");
                            player.onSpring = true;
                            break; //technically this isn't needed
                        case TypeComponent.BULLET:
                            System.out.println("Player just shot. bullet in player atm");
                            break;
                        case TypeComponent.WALL:
                            player.onWall = true;
                            System.out.println("player hit wall");
                            break;
                        case TypeComponent.SPEED_X:
                            player.speedX = true;
                            System.out.println("player hit speedX");
                            break;
                        case TypeComponent.SPEED_Y:
                            player.speedY = true;
                            System.out.println("player hit speedY");
                            break;
                        case TypeComponent.WATER:
                            System.out.println("player hit water");
                            levelFactory.makeParticleEffect(ParticleEffectManager.SPLASH, Mapper.b2dCom.get(entity).body.getPosition().x, Mapper.b2dCom.get(entity).body.getPosition().y);
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

        }else if(thisType.type == TypeComponent.BULLET) {
            if (collidedEntity != null) {
                TypeComponent type = collidedEntity.getComponent(TypeComponent.class);
                if (type != null) {
                    switch (type.type) {
                        case TypeComponent.GROUND:
                            //do player hit ground thing
                            System.out.println("bullet hit ground");
                            //levelFactory.makeParticleEffect(ParticleEffectManager.EXPLOSION, Mapper.b2dCom.get(entity).body.getPosition().x, Mapper.b2dCom.get(entity).body.getPosition().y);
                            Mapper.bulletCom.get(entity).isDead = true;
                            break; //technically this isn't needed
                        case TypeComponent.WALL:
                            //do player hit wall thing
                            System.out.println("bullet hit wall");
                            //levelFactory.makeParticleEffect(ParticleEffectManager.EXPLOSION, Mapper.b2dCom.get(entity).body.getPosition().x, Mapper.b2dCom.get(entity).body.getPosition().y);
                            Mapper.bulletCom.get(entity).isDead = true;
                            break; //technically this isn't needed
                        default:
                            System.out.println("No matching type found");
                    }
                    cc.collisionEntity = null; // collision handled reset component
                }
            }

        }else if(thisType.type == TypeComponent.ENEMY) {
            if (collidedEntity != null) {
                TypeComponent type = collidedEntity.getComponent(TypeComponent.class);
                if (type != null) {
                    switch (type.type) {
                        case TypeComponent.GROUND:
                            //do player hit enemy thing
                            System.out.println("Bullet hit enemy");
                            Mapper.enemyCom.get(entity).onGround = true;
                            break;
                        case TypeComponent.BULLET:
                            //do player hit enemy thing
                            System.out.println("Bullet hit enemy");
                            Mapper.enemyCom.get(entity).isDead = true;

                            levelFactory.makeParticleEffect(ParticleEffectManager.BLOOD, Mapper.b2dCom.get(entity).body.getPosition().x, Mapper.b2dCom.get(entity).body.getPosition().y);
                            //levelFactory.makeParticleEffect(ParticleEffectManager.EXPLOSION, Mapper.b2dCom.get(entity).body.getPosition().x, Mapper.b2dCom.get(entity).body.getPosition().y);

                            Mapper.bulletCom.get(collidedEntity).isDead = true;
                            break;
                        case TypeComponent.WALL:
                            //do enemy hit wall thing
                            System.out.println("enemy hit wall");
                            EnemyComponent enemyComponent = Mapper.enemyCom.get(entity);

                            if (enemyComponent.runningRight){
                                enemyComponent.runningRight = false;
                            }else if(!enemyComponent.runningRight) {
                                enemyComponent.runningRight = true;
                            }
                            break; //technically this isn't needed
                        default:
                            System.out.println("No matching type found");
                    }
                    cc.collisionEntity = null; // collision handled reset component
                }
            }
        }

    }
}