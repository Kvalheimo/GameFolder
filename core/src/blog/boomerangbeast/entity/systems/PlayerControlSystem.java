package blog.boomerangbeast.entity.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;


import blog.boomerangbeast.factory.LevelFactory;
import blog.boomerangbeast.ParticleEffectManager;
import blog.boomerangbeast.controller.Controller;
import blog.boomerangbeast.entity.components.B2dBodyComponent;
import blog.boomerangbeast.entity.components.EnemyComponent;
import blog.boomerangbeast.entity.components.Mapper;
import blog.boomerangbeast.entity.components.PlatformComponent;
import blog.boomerangbeast.entity.components.PlayerComponent;
import blog.boomerangbeast.entity.components.StateComponent;
import blog.boomerangbeast.screens.overlays.Hud;

import static blog.boomerangbeast.DFUtils.PLATFORM_VELOCITY_X;

public class PlayerControlSystem extends IteratingSystem{
    private Controller controller;
    private LevelFactory levelFactory;
    private Hud hud;
    private float platf_vel_x;
    private boolean platDir;



    @SuppressWarnings("unchecked")
    public PlayerControlSystem(Controller controller, LevelFactory levelFactory, Hud hud) {
        super(Family.all(PlayerComponent.class).get());
        this.controller = controller;
        this.levelFactory = levelFactory;
        this.hud = hud;
        this.platf_vel_x = PLATFORM_VELOCITY_X;

    }


    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        B2dBodyComponent b2body = Mapper.b2dCom.get(entity);
        StateComponent state = Mapper.stateCom.get(entity);
        PlayerComponent player = Mapper.playerCom.get(entity);
        EnemyComponent enemy = Mapper.enemyCom.get(entity);
        PlatformComponent platform = Mapper.platCom.get(entity);

        player.camera.position.x = b2body.body.getPosition().x;
        player.camera.position.y = b2body.body.getPosition().y;
        player.camera.update();

        if (player.superSpeed && player.superspeedDisplayed){
            hud.setSpeedBoost();
            player.superspeedDisplayed = false;
        }
        if (player.boomerangCount > 0 && player.boomerangDisplayed){
            hud.setBoomerangCount(player.boomerangCount);
            player.boomerangDisplayed = false;
        }




        // Handle states

        if(b2body.body.getLinearVelocity().y < -1  && state.get() != StateComponent.STATE_FALLING){
            player.onGround = false;
            state.set(StateComponent.STATE_FALLING);
        }

        if (state.get() == StateComponent.STATE_FALLING && b2body.body.getLinearVelocity().y == 0){
            player.onGround = true;
        }

        if(player.onGround){
            if(b2body.body.getLinearVelocity().x == 0 && state.get() !=  StateComponent.STATE_NORMAL){
                state.set(StateComponent.STATE_NORMAL);
            }

            if(b2body.body.getLinearVelocity().x != 0 && state.get() != StateComponent.STATE_MOVING) {
                state.set(StateComponent.STATE_MOVING);
            }

        }


        if (b2body.body.getLinearVelocity().x < 1 && b2body.body.getLinearVelocity().x > -1 && state.get() == StateComponent.STATE_MOVING){
            state.set(StateComponent.STATE_NORMAL);
        }





        // Control max speed and set value if too high (superspeed)

        if(b2body.body.getLinearVelocity().x > 15){
            b2body.body.setLinearVelocity(MathUtils.lerp(b2body.body.getLinearVelocity().x, 15f, 0.1f), b2body.body.getLinearVelocity().y);

        }

        if(b2body.body.getLinearVelocity().x < -15){
            b2body.body.setLinearVelocity(MathUtils.lerp(b2body.body.getLinearVelocity().x, 15f, 0.1f), b2body.body.getLinearVelocity().y);
        }




        // Movement

        if(controller.isLeftPressed()) {
            if (player.onWall){
                b2body.body.setLinearVelocity(0f, b2body.body.getLinearVelocity().y);
                player.onWall = false;
            }
            else if(player.onPlatform && b2body.body.getLinearVelocity().x > -8){
                b2body.body.applyLinearImpulse(-6,0,b2body.body.getWorldCenter().x, b2body.body.getWorldCenter().y, true);
                player.runningRight = false;
            }

            else if(b2body.body.getLinearVelocity().x > -8){
                b2body.body.applyForceToCenter(-50*controller.getVelScale(), 0, true);
            }


        }


        if(controller.isRightPressed()) {
            if (player.onWall){
                b2body.body.setLinearVelocity(0f, b2body.body.getLinearVelocity().y);
                player.onWall = false;
            }
            else if(player.onPlatform && b2body.body.getLinearVelocity().x < 8){
                b2body.body.applyLinearImpulse(6,0,b2body.body.getWorldCenter().x, b2body.body.getWorldCenter().y, true);
                player.runningRight = true;
            }


            else if(b2body.body.getLinearVelocity().x < 8){
                b2body.body.applyForceToCenter(50*controller.getVelScale(), 0, true);
            }

        }


        if(!controller.isLeftPressed() && !controller.isRightPressed()){
            b2body.body.setLinearVelocity(MathUtils.lerp(b2body.body.getLinearVelocity().x, 0, 1.0f),b2body.body.getLinearVelocity().y);
        }




        //jumpwall

        if (player.onJumpWall) {
            b2body.body.setLinearVelocity(0,0);

            if (player.timeOnWall >= 0.3f) {
                player.onJumpWall = false;

            }else if(!(player.runningRight && controller.isRightPressed()) && !(!player.runningRight && controller.isLeftPressed())) {
                        player.onJumpWall = false;
            }

            if (!player.onJumpWall){
                player.timeOnWall = 0;
                player.jumpWallpos = 0;
                player.jumpTime = 0;
            }

            player.timeOnWall += deltaTime;
        }

        if (player.jumpTime <= 0.2 && controller.isAPressed()){
            b2body.body.applyLinearImpulse(0, 11 * b2body.body.getMass(), b2body.body.getWorldCenter().x, b2body.body.getWorldCenter().y, true);
            controller.setAPressed(false);
        }

        player.jumpTime += deltaTime;




        //Jumping

        if(controller.isAPressed() && (state.get() == StateComponent.STATE_NORMAL || state.get() == StateComponent.STATE_MOVING || state.get() == StateComponent.STATE_FALLING)) {

            if (player.jumpCounter < 2) {
                if (!player.onJumpWall) {
                    b2body.body.applyLinearImpulse(0, 8 * b2body.body.getMass(), b2body.body.getWorldCenter().x, b2body.body.getWorldCenter().y, true);
                    state.set(StateComponent.STATE_JUMPING);
                    player.jumpCounter += 1;
                }

            }

            player.onGround = false;
            player.onSpring = false;
            player.onPlatform = false;
            controller.setAPressed(false);
        }



        if(player.onGround || (b2body.body.getLinearVelocity().y == 0 && !player.onPlatform)){
            player.onPlatform = false;
            player.jumpCounter = 0;
        }




        //Spring

        if(player.onSpring){
            b2body.body.applyLinearImpulse(0, 18f, b2body.body.getWorldCenter().x, b2body.body.getWorldCenter().y, true);
            state.set(StateComponent.STATE_JUMPING);
            player.onSpring = false;

        }



        if (controller.isYPressed() && player.superSpeed){
            if (player.runningRight){
                b2body.body.applyLinearImpulse(60f, 0f, b2body.body.getWorldCenter().x, b2body.body.getWorldCenter().y, true);
                player.particleEffect = levelFactory.makeParticleEffect(ParticleEffectManager.SUPERSPEED_RIGHT, b2body);
                hud.setSpeedBoostActive();
            }else{
                b2body.body.applyLinearImpulse(-60f, 0f, b2body.body.getWorldCenter().x, b2body.body.getWorldCenter().y, true);
                player.particleEffect = levelFactory.makeParticleEffect(ParticleEffectManager.SUPERSPEED_LEFT, b2body);
                hud.setSpeedBoostActive();
            }
            player.superSpeed = false;

        }




        //Shooting

        if (controller.isXPressed() && player.boomerangCount > 0){
            Vector2 direction = controller.getBulletDirection();


            float shooterX = b2body.body.getPosition().x; // get player location

            float shooterY = b2body.body.getPosition().y; // get player location

            float velY = 7f*direction.y;
            float velX = 7f*direction.x ;


            if (player.runningRight && direction.x == 0f && direction.y == 0f){
                velX = 7f;
                velY = -0.1f;
            }else if (!player.runningRight && direction.x == 0f && direction.y == 0f){
                velX = -7f;
                velY = -0.1f;
            }


            levelFactory.createBullet(shooterX, shooterY, velX, velY);
            hud.useBoomerang();
            //player.hasGun = false;
            player.boomerangCount -= 1;
            controller.setXPressed(false);
        }




        //Speed fetures

        if (controller.isYPressed() && player.superSpeed){
            if (player.runningRight){
                b2body.body.applyLinearImpulse(60f, 0f, b2body.body.getWorldCenter().x, b2body.body.getWorldCenter().y, true);
                player.particleEffect = levelFactory.makeParticleEffect(ParticleEffectManager.SUPERSPEED_RIGHT, b2body);
                hud.setSpeedBoostActive();
            }else{
                b2body.body.applyLinearImpulse(-60f, 0f, b2body.body.getWorldCenter().x, b2body.body.getWorldCenter().y, true);
                player.particleEffect = levelFactory.makeParticleEffect(ParticleEffectManager.SUPERSPEED_LEFT, b2body);
                hud.setSpeedBoostActive();
            }
            player.superSpeed = false;

        }

        if (player.speedX){
            //b2body.body.setLinearVelocity(100f, 0f);
            b2body.body.applyLinearImpulse(60, 0, b2body.body.getWorldCenter().x, b2body.body.getWorldCenter().y, true);
            player.particleEffect = levelFactory.makeParticleEffect(ParticleEffectManager.SPEED, b2body);
            player.speedX = false;
        }

        if (player.speedY){
            //b2body.body.setLinearVelocity(0f, 100f);
            b2body.body.applyLinearImpulse(0, 30, b2body.body.getWorldCenter().x, b2body.body.getWorldCenter().y, true);
            player.speedY = false;
            player.jumpCounter = 0;
        }




        //Kill player if he falls down

        if (b2body.body.getPosition().y < -2){
            if (player.particleEffect != null && Mapper.paCom.get(player.particleEffect) != null)
                Mapper.paCom.get(player.particleEffect).isDead = true;
            player.isDead = true;
        }






        // Alter player movement on platform

        if (player.onPlatform){
            if(b2body.body.getLinearVelocity().x == 0 && state.get() !=  StateComponent.STATE_NORMAL){
            state.set(StateComponent.STATE_NORMAL);
        }

            if((b2body.body.getLinearVelocity().x != 0) && state.get() != StateComponent.STATE_MOVING) {
                state.set(StateComponent.STATE_MOVING);
            }
            player.jumpCounter = 0;


        }




        //Send back to checkpoint if player is dead

        if (player.isDead){
            b2body.body.setTransform(player.checkPointPos,0);
            b2body.body.setLinearVelocity(0, 0);
            player.runningRight = true;
            player.isDead = false;
        }

        if(hud.getPercentage() >= 1){
            player.isFinished = true;
        }


    }


}