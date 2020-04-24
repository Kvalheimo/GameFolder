package blog.gamedevelopmentbox2dtutorial.entity.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;


import java.util.Map;

import blog.gamedevelopmentbox2dtutorial.Factory.LevelFactory;
import blog.gamedevelopmentbox2dtutorial.ParticleEffectManager;
import blog.gamedevelopmentbox2dtutorial.entity.components.B2dBodyComponent;
import blog.gamedevelopmentbox2dtutorial.entity.components.CollisionComponent;
import blog.gamedevelopmentbox2dtutorial.entity.components.EnemyComponent;
import blog.gamedevelopmentbox2dtutorial.entity.components.Mapper;
import blog.gamedevelopmentbox2dtutorial.entity.components.PlatformComponent;
import blog.gamedevelopmentbox2dtutorial.entity.components.PlayerComponent;
import blog.gamedevelopmentbox2dtutorial.entity.components.TypeComponent;
import blog.gamedevelopmentbox2dtutorial.views.Hud;

public class CollisionSystem extends IteratingSystem {
    private LevelFactory levelFactory;
    private Hud hud;

    @SuppressWarnings("unchecked")
    public CollisionSystem(LevelFactory levelFactory, Hud hud) {
        super(Family.all(CollisionComponent.class).get());
        this.levelFactory = levelFactory;
        this.hud = hud;

    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

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
                            levelFactory.makeParticleEffect(ParticleEffectManager.BLOOD, Mapper.b2dCom.get(entity).body.getPosition().x, Mapper.b2dCom.get(entity).body.getPosition().y);
                            Mapper.playerCom.get(entity).isDead = true;
                            break;

                        case TypeComponent.SUPER_SPEED:
                            player.superspeedDisplayed = true;
                            player.particleEffect = levelFactory.makeParticleEffect(ParticleEffectManager.POWERUP_SPEED, body);
                            player.superSpeed = true;
                            levelFactory.removeSuperSpeedTile(Mapper.b2dCom.get(collidedEntity).body);
                            Mapper.powerCom.get(collidedEntity).isDead = true;
                            break;

                        case TypeComponent.GUN:
                            player.boomerangDisplayed = true;
                            player.particleEffect = levelFactory.makeParticleEffect(ParticleEffectManager.POWER_UP, body);

                            if (player.boomerangCount < 4) {
                                player.boomerangCount += 1;
                            }
                            player.hasGun = true;

                            player.particleEffect = levelFactory.makeParticleEffect(ParticleEffectManager.POWERUP_GUN, body);
                            levelFactory.removeGunTile(Mapper.b2dCom.get(collidedEntity).body);
                            Mapper.powerCom.get(collidedEntity).isDead = true;
                            break;

                        case TypeComponent.GROUND:
                            player.onGround = true;
                            break;

                        case TypeComponent.SPRING:
                            player.onSpring = true;
                            break;

                        case TypeComponent.BULLET:
                            break;

                        case TypeComponent.WALL:
                            player.onWall = true;
                            break;
                        case TypeComponent.DESTROYABLE_TILE:
                            player.onGround = true;
                            break;

                        case TypeComponent.PLATFORM:
                            break;

                        case TypeComponent.SPEED_X:
                            player.speedX = true;
                            break;

                        case TypeComponent.SPEED_Y:
                            player.speedY = true;
                            break;

                        case TypeComponent.WATER:
                            levelFactory.makeParticleEffect(ParticleEffectManager.SPLASH, Mapper.b2dCom.get(entity).body.getPosition().x, Mapper.b2dCom.get(entity).body.getPosition().y);
                            break;

                        case TypeComponent.OTHER:
                            break;

                        case TypeComponent.SPIKES:
                            levelFactory.makeParticleEffect(ParticleEffectManager.BLOOD, Mapper.b2dCom.get(entity).body.getPosition().x, Mapper.b2dCom.get(entity).body.getPosition().y);
                            Mapper.playerCom.get(entity).isDead = true;
                            break;

                        case TypeComponent.CHECKPOINT:
                            player.checkPointPos = Mapper.cpComp.get(collidedEntity).checkpointPos;
                            break;

                        default:
                            System.out.println("No matching type found");
                            levelFactory.makeParticleEffect(ParticleEffectManager.BLOOD, body);

                    }
                    cc.collisionEntity = null; // collision handled reset component
                } else {
                    System.out.println("type == null");
                }
            }

        //Handle bullet collision
        }else if(thisType.type == TypeComponent.BULLET) {
            if (collidedEntity != null) {
                TypeComponent type = collidedEntity.getComponent(TypeComponent.class);
                if (type != null) {
                    switch (type.type) {
                        case TypeComponent.GROUND:
                            //levelFactory.makeParticleEffect(ParticleEffectManager.EXPLOSION, Mapper.b2dCom.get(entity).body.getPosition().x, Mapper.b2dCom.get(entity).body.getPosition().y);
                            Mapper.bulletCom.get(entity).isDead = true;
                            break;

                        case TypeComponent.WALL:
                            //levelFactory.makeParticleEffect(ParticleEffectManager.EXPLOSION, Mapper.b2dCom.get(entity).body.getPosition().x, Mapper.b2dCom.get(entity).body.getPosition().y);
                            Mapper.bulletCom.get(entity).isDead = true;
                            break;
                        case TypeComponent.DESTROYABLE_TILE:
                            levelFactory.removeDestroyableTile(Mapper.b2dCom.get(collidedEntity).body);
                            Mapper.destCom.get(collidedEntity).isDead = true;
                            break;

                    }
                    cc.collisionEntity = null; // collision handled reset component
                }
            }

        //Handle enemy collsions
        }else if(thisType.type == TypeComponent.ENEMY) {
            if (collidedEntity != null) {
                TypeComponent type = collidedEntity.getComponent(TypeComponent.class);
                if (type != null) {
                    switch (type.type) {
                        case TypeComponent.PLAYER:  //Denne slår inn når player treffer en spider
                            levelFactory.makeParticleEffect(ParticleEffectManager.BLOOD, Mapper.b2dCom.get(collidedEntity).body.getPosition().x, Mapper.b2dCom.get(collidedEntity).body.getPosition().y);
                            Mapper.playerCom.get(collidedEntity).isDead = true;
                            break;

                        case TypeComponent.GROUND:
                            Mapper.enemyCom.get(entity).onGround = true;
                            break;

                        case TypeComponent.BULLET:
                            Mapper.enemyCom.get(entity).isDead = true;
                            hud.giveExtraPoints();
                            levelFactory.makeParticleEffect(ParticleEffectManager.BLOOD, Mapper.b2dCom.get(entity).body.getPosition().x, Mapper.b2dCom.get(entity).body.getPosition().y);
                            Mapper.bulletCom.get(collidedEntity).isDead = true;
                            break;

                        case TypeComponent.WALL:
                            EnemyComponent enemyComponent = Mapper.enemyCom.get(entity);

                            if (enemyComponent.movingRight){
                                enemyComponent.movingRight = false;
                            }else if(!enemyComponent.movingRight) {
                                enemyComponent.movingRight = true;
                            }
                            break; //technically this isn't needed

                        case TypeComponent.SPIKES:
                            //do enemy hit spike thing
                            //    System.out.println("enemy hit wall");
                            enemyComponent = Mapper.enemyCom.get(entity);

                            if (enemyComponent.movingRight){
                                enemyComponent.movingRight = false;
                            }else if(!enemyComponent.movingRight) {
                                enemyComponent.movingRight = true;
                            }
                            break; //technically this isn't needed

                        default:
                            System.out.println("No matching type found");

                            break;
                    }
                    cc.collisionEntity = null; // collision handled reset component
                }
            }
        // handle platformCollision
        }else if(thisType.type == TypeComponent.PLATFORM){
            if (collidedEntity != null) {
                TypeComponent type = collidedEntity.getComponent(TypeComponent.class);

                B2dBodyComponent body = Mapper.b2dCom.get(entity);
                if (type != null) {
                    switch (type.type) {
                        case TypeComponent.PLAYER:

                            //PlayerComponent player = Mapper.playerCom.get(collidedEntity);
                            Mapper.playerCom.get(collidedEntity).onPlatform = true;
                            Mapper.playerCom.get(collidedEntity).onGround = false;
                            Mapper.stateCom.get(collidedEntity).set(0);


                        //.setLinearVelocity(
                          //          Mapper.platCom.get(entity).movingRight ?  Mapper.platCom.get(entity).velocity_x
                            //                                               : -Mapper.platCom.get(entity).velocity_x, 0);
                            break;

                        case TypeComponent.GROUND:
                            System.out.println("Platform collied with ground");
                            PlatformComponent platformComponent = Mapper.platCom.get(entity);

                            if (platformComponent.movingRight) {
                                platformComponent.movingRight = false;
                            } else if (!platformComponent.movingRight) {
                                platformComponent.movingRight = true;
                            }
                            break;

                        case TypeComponent.BULLET:
                            break;

                        case TypeComponent.WALL:
                            platformComponent = Mapper.platCom.get(entity);

                            System.out.println("Platform collied with wall");

                            if (platformComponent.movingRight) {
                                platformComponent.movingRight = false;
                            } else if (!platformComponent.movingRight) {
                                platformComponent.movingRight = true;
                            }
                            break; //technically this isn't needed

                        case TypeComponent.SPIKES:
                            //do enemy hit spike thing
                            //    System.out.println("enemy hit wall");
                            platformComponent = Mapper.platCom.get(entity);

                            if (platformComponent.movingRight) {
                                platformComponent.movingRight = false;
                            } else if (!platformComponent.movingRight) {
                                platformComponent.movingRight = true;
                            }
                            break; //technically this isn't needed

                        default:
                            System.out.println("No matching type found for platform");

                            break;
                    }
                    cc.collisionEntity = null; // collision handled reset component
                }
            }

        }
    }
}