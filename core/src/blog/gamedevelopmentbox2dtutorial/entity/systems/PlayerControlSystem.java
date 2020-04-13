package blog.gamedevelopmentbox2dtutorial.entity.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;


import blog.gamedevelopmentbox2dtutorial.Factory.LevelFactory;
import blog.gamedevelopmentbox2dtutorial.ParticleEffectManager;
import blog.gamedevelopmentbox2dtutorial.controller.Controller;
import blog.gamedevelopmentbox2dtutorial.entity.components.B2dBodyComponent;
import blog.gamedevelopmentbox2dtutorial.entity.components.Mapper;
import blog.gamedevelopmentbox2dtutorial.entity.components.PlayerComponent;
import blog.gamedevelopmentbox2dtutorial.entity.components.StateComponent;
import blog.gamedevelopmentbox2dtutorial.views.Hud;

public class PlayerControlSystem extends IteratingSystem{

    private Controller controller;
    private LevelFactory levelFactory;
    private Hud hud;


    @SuppressWarnings("unchecked")
    public PlayerControlSystem(Controller controller, LevelFactory levelFactory, Hud hud) {
        super(Family.all(PlayerComponent.class).get());
        this.controller = controller;
        this.levelFactory = levelFactory;
        this.hud = hud;

    }


    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        B2dBodyComponent b2body = Mapper.b2dCom.get(entity);
        StateComponent state = Mapper.stateCom.get(entity);
        PlayerComponent player = Mapper.playerCom.get(entity);

        player.camera.position.x = b2body.body.getPosition().x;
        player.camera.position.y = b2body.body.getPosition().y;
        player.camera.update();

        if (player.superSpeed){
            hud.setSpeedBoost();
        }

        //Handle states
        if(b2body.body.getLinearVelocity().y < 0  && state.get() != StateComponent.STATE_FALLING){
            state.set(StateComponent.STATE_FALLING);
        }



        if(player.onGround){
            if(b2body.body.getLinearVelocity().x == 0 && state.get() !=  StateComponent.STATE_NORMAL){
                state.set(StateComponent.STATE_NORMAL);
            }

            if(b2body.body.getLinearVelocity().x != 0 && state.get() != StateComponent.STATE_MOVING) {
                state.set(StateComponent.STATE_MOVING);
            }

        }


        /*
        if (b2body.body.getLinearVelocity().y == 0  && state.get() == StateComponent.STATE_MOVING){
            state.set(StateComponent.STATE_NORMAL);
        }
         */


        //Control max speed and set value if too high (superspeed)
        if(b2body.body.getLinearVelocity().x > 15){
            b2body.body.setLinearVelocity(MathUtils.lerp(b2body.body.getLinearVelocity().x, 15f, 0.1f), b2body.body.getLinearVelocity().y);

        }

        if(b2body.body.getLinearVelocity().x < -15){
            b2body.body.setLinearVelocity(MathUtils.lerp(b2body.body.getLinearVelocity().x, 15f, 0.1f), b2body.body.getLinearVelocity().y);
        }


        if(controller.isRightPressed() && player.onWall) {
            b2body.body.setLinearVelocity(0f, b2body.body.getLinearVelocity().y);
            player.onWall = false;
        }


        //Movement
        if(controller.isLeftPressed()) {
            if (player.onWall){
                b2body.body.setLinearVelocity(0f, b2body.body.getLinearVelocity().y);
                player.onWall = false;
            }

            /*
            else if (b2body.body.getLinearVelocity().x > -5){
                b2body.body.applyLinearImpulse(-6, 0, b2body.body.getWorldCenter().x, b2body.body.getWorldCenter().y, true);
            }
             */
            else if(b2body.body.getLinearVelocity().x > -7){
                b2body.body.applyForceToCenter(-80, 0, true);
            }


        }

        if(controller.isRightPressed()) {
            if (player.onWall){
                b2body.body.setLinearVelocity(0f, b2body.body.getLinearVelocity().y);
                player.onWall = false;
            }

            /*
            else if(b2body.body.getLinearVelocity().x < 5){
                b2body.body.applyLinearImpulse(6, 0, b2body.body.getWorldCenter().x, b2body.body.getWorldCenter().y, true);
            }
             */



            else if(b2body.body.getLinearVelocity().x < 7){
                b2body.body.applyForceToCenter(80, 0, true);
            }

        }


        if(!controller.isLeftPressed() && !controller.isRightPressed()){
            b2body.body.setLinearVelocity(MathUtils.lerp(b2body.body.getLinearVelocity().x, 0, 1.0f),b2body.body.getLinearVelocity().y);
        }


        //Jumping
        if(controller.isAPressed() && (state.get() == StateComponent.STATE_NORMAL || state.get() == StateComponent.STATE_MOVING || state.get() == StateComponent.STATE_FALLING)){

            if(player.jumpCounter < 2) {
                b2body.body.applyLinearImpulse(0, 8 * b2body.body.getMass(), b2body.body.getWorldCenter().x, b2body.body.getWorldCenter().y, true);
                state.set(StateComponent.STATE_JUMPING);
                player.jumpCounter += 1;
            }

            player.onGround = false;
            player.onSpring = false;
            controller.setAPressed(false);
        }


        if(player.onGround || b2body.body.getLinearVelocity().y == 0){
            player.jumpCounter = 0;
        }


        //Spring
        if(player.onSpring){
            b2body.body.applyLinearImpulse(0, 15f, b2body.body.getWorldCenter().x, b2body.body.getWorldCenter().y, true);
            state.set(StateComponent.STATE_JUMPING);
            player.onSpring = false;

        }



        if (controller.isYPressed() && player.superSpeed){
            if (player.runningRight){
                b2body.body.applyLinearImpulse(50f, 0f, b2body.body.getWorldCenter().x, b2body.body.getWorldCenter().y, true);
                player.particleEffect = levelFactory.makeParticleEffect(ParticleEffectManager.SUPERSPEED_RIGHT, b2body);
                hud.setSpeedBoostActive();
            }else{
                b2body.body.applyLinearImpulse(-50f, 0f, b2body.body.getWorldCenter().x, b2body.body.getWorldCenter().y, true);
                player.particleEffect = levelFactory.makeParticleEffect(ParticleEffectManager.SUPERSPEED_LEFT, b2body);
                hud.setSpeedBoostActive();
            }
            player.superSpeed = false;

        }

        //Shooting
        if (controller.isXPressed() && player.hasGun){

            float shooterX = b2body.body.getPosition().x; // get player location
            float shooterY = b2body.body.getPosition().y + 0.2f; // get player location
            float velY = -0.1f;
            float velX;

            if (player.runningRight){
                velX = 7f;
            }else{
                velX = -7f;
            }

            levelFactory.createBullet(shooterX, shooterY, velX, velY);
            player.hasGun = false;
        }


        if (player.speedX){
            //b2body.body.setLinearVelocity(100f, 0f);
            b2body.body.applyLinearImpulse(50, 0, b2body.body.getWorldCenter().x, b2body.body.getWorldCenter().y, true);
            player.particleEffect = levelFactory.makeParticleEffect(ParticleEffectManager.SPEED, b2body);
            player.speedX = false;
        }

        if (player.speedY){
            //b2body.body.setLinearVelocity(0f, 100f);
            b2body.body.applyLinearImpulse(0, 50, b2body.body.getWorldCenter().x, b2body.body.getWorldCenter().y, true);
            player.speedY = false;
        }



        //Denne må også tas med når enemey dreper player
        if (b2body.body.getPosition().y < -2){
            if (player.particleEffect != null && Mapper.paCom.get(player.particleEffect) != null)
                Mapper.paCom.get(player.particleEffect).isDead = true;
            player.isDead = true;
        }

    }


}