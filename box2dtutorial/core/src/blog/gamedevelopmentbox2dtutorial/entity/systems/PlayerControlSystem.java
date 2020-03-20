package blog.gamedevelopmentbox2dtutorial.entity.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;


import blog.gamedevelopmentbox2dtutorial.Factory.LevelFactory;
import blog.gamedevelopmentbox2dtutorial.controller.Controller;
import blog.gamedevelopmentbox2dtutorial.entity.components.B2dBodyComponent;
import blog.gamedevelopmentbox2dtutorial.entity.components.Mapper;
import blog.gamedevelopmentbox2dtutorial.entity.components.PlayerComponent;
import blog.gamedevelopmentbox2dtutorial.entity.components.StateComponent;

public class PlayerControlSystem extends IteratingSystem{

    private Controller controller;
    private LevelFactory levelFactory;


    @SuppressWarnings("unchecked")
    public PlayerControlSystem(Controller controller, LevelFactory levelFactory) {
        super(Family.all(PlayerComponent.class).get());
        this.controller = controller;
        this.levelFactory = levelFactory;

    }


    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        B2dBodyComponent b2body = Mapper.b2dCom.get(entity);
        StateComponent state = Mapper.stateCom.get(entity);
        PlayerComponent player = Mapper.playerCom.get(entity);

        player.camera.position.x = b2body.body.getPosition().x;
        player.camera.update();


        //Handle states
        if(b2body.body.getLinearVelocity().y < 0  && state.get() != StateComponent.STATE_FALLING){
            state.set(StateComponent.STATE_FALLING);
        }

        if(b2body.body.getLinearVelocity().y == 0){
            if(state.get() == StateComponent.STATE_FALLING){
                state.set(StateComponent.STATE_NORMAL);

            }
            if(b2body.body.getLinearVelocity().x != 0 && state.get() != StateComponent.STATE_MOVING){  // NEW
                state.set(StateComponent.STATE_MOVING);
            }
        }

        //Control max speed and set value if too high (superspeed)
        if(b2body.body.getLinearVelocity().x > 40){
            b2body.body.setLinearVelocity(MathUtils.lerp(b2body.body.getLinearVelocity().x, 20f, 0.1f), b2body.body.getLinearVelocity().y);

        }

        if(b2body.body.getLinearVelocity().x < -40){
            b2body.body.setLinearVelocity(MathUtils.lerp(b2body.body.getLinearVelocity().x, 20f, 0.1f), b2body.body.getLinearVelocity().y);
        }


        //Movement
        if(controller.isLeftPressed()) {
            if(b2body.body.getLinearVelocity().x > -15){
                b2body.body.applyLinearImpulse(-20, 0, b2body.body.getWorldCenter().x, b2body.body.getWorldCenter().y, true);
            }
            if(b2body.body.getLinearVelocity().x > -20){
                b2body.body.applyForceToCenter(-125, 0, true);
            }


        }

        if(controller.isRightPressed()) {
            if(b2body.body.getLinearVelocity().x < 15){
                b2body.body.applyLinearImpulse(20, 0, b2body.body.getWorldCenter().x, b2body.body.getWorldCenter().y, true);
                //b2body.body.applyForceToCenter(70, 0, true);

            }

            if(b2body.body.getLinearVelocity().x < 20){
                b2body.body.applyForceToCenter(125, 0, true);
            }

        }


        if(!controller.isLeftPressed() && !controller.isRightPressed()){
            b2body.body.setLinearVelocity(MathUtils.lerp(b2body.body.getLinearVelocity().x, 0, 0.1f),b2body.body.getLinearVelocity().y);
        }


        //Jumping
        if(controller.isAPressed() && (state.get() == StateComponent.STATE_NORMAL || state.get() == StateComponent.STATE_MOVING || state.get() == StateComponent.STATE_FALLING)){

            if(player.jumpCounter < 2) {
                b2body.body.applyLinearImpulse(0, 20f * b2body.body.getMass(), b2body.body.getWorldCenter().x, b2body.body.getWorldCenter().y, true);
                state.set(StateComponent.STATE_JUMPING);
                player.jumpCounter += 1;
            }

            player.onGround = false;
            player.onSpring = false;
        }


        if(player.onGround){
            player.jumpCounter = 0;
        }


        //Spring
        if(player.onSpring){
            b2body.body.applyLinearImpulse(0, 70f, b2body.body.getWorldCenter().x, b2body.body.getWorldCenter().y, true);
            state.set(StateComponent.STATE_JUMPING);
            player.onSpring = false;
        }



        if (controller.isYPressed() && player.superSpeed){
            b2body.body.applyLinearImpulse(5000f, 0f, b2body.body.getWorldCenter().x, b2body.body.getWorldCenter().y, true);
            player.superSpeed = false;


        }

        //Shooting
        if (controller.isXPressed() && player.hasGun){

            float velX = 20f;  // set the speed of the bullet
            float velY = -0.2f;
            float shooterX = b2body.body.getPosition().x; // get player location
            float shooterY = b2body.body.getPosition().y + 1f; // get player location
            levelFactory.createBullet(shooterX, shooterY, velX, velY);
            player.hasGun = false;


        }



        if (b2body.body.getPosition().y < -2){
            player.isDead = true;

        }

    }


}