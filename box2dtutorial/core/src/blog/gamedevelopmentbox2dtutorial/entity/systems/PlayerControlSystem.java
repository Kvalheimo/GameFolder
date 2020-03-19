package blog.gamedevelopmentbox2dtutorial.entity.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;

import blog.gamedevelopmentbox2dtutorial.Factory.LevelFactory;
import blog.gamedevelopmentbox2dtutorial.controller.KeyboardController;
import blog.gamedevelopmentbox2dtutorial.entity.components.B2dBodyComponent;
import blog.gamedevelopmentbox2dtutorial.entity.components.Mapper;
import blog.gamedevelopmentbox2dtutorial.entity.components.PlayerComponent;
import blog.gamedevelopmentbox2dtutorial.entity.components.StateComponent;

public class PlayerControlSystem extends IteratingSystem{

    KeyboardController controller;
    private LevelFactory levelFactory;


    @SuppressWarnings("unchecked")
    public PlayerControlSystem(KeyboardController keyCon, LevelFactory levelFactory) {
        super(Family.all(PlayerComponent.class).get());
        controller = keyCon;
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
        if(b2body.body.getLinearVelocity().y < 0 && state.get() != StateComponent.STATE_FALLING){
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


        //Controller
        if(controller.LEFT){
            b2body.body.setLinearVelocity(MathUtils.lerp(b2body.body.getLinearVelocity().x, -10f, 0.2f),b2body.body.getLinearVelocity().y);
        }
        if(controller.RIGHT){
            b2body.body.setLinearVelocity(MathUtils.lerp(b2body.body.getLinearVelocity().x, 10f, 0.2f),b2body.body.getLinearVelocity().y);
        }

        if(!controller.LEFT && ! controller.RIGHT){
            b2body.body.setLinearVelocity(MathUtils.lerp(b2body.body.getLinearVelocity().x, 0, 0.2f),b2body.body.getLinearVelocity().y);
        }

        if(controller.UP && (state.get() == StateComponent.STATE_NORMAL || state.get() == StateComponent.STATE_MOVING || state.get() == StateComponent.STATE_FALLING)){
            player.onGround = false;
            player.onSpring = false;
                if(player.jumpCounter < 2) {
                    System.out.println(player.jumpCounter);
                    //b2body.body.applyForceToCenter(0, 100f, true);
                    b2body.body.applyLinearImpulse(0, 12f * b2body.body.getMass(), b2body.body.getWorldCenter().x, b2body.body.getWorldCenter().y, true);
                    state.set(StateComponent.STATE_JUMPING);
                    player.jumpCounter += 1;
                }
        }

        if(player.onGround){
            player.jumpCounter = 0;
        }


        if(player.onSpring){
            //b2body.body.applyForceToCenter(0, 50, true);
            b2body.body.applyLinearImpulse(0, 70f, b2body.body.getWorldCenter().x, b2body.body.getWorldCenter().y, true);
            state.set(StateComponent.STATE_JUMPING);
            player.onSpring = false;
        }

        if (controller.SPACE && player.superSpeed){
            //b2body.body.setLinearVelocity(MathUtils.lerp(b2body.body.getLinearVelocity().x, 5000, 0.01f),b2body.body.getLinearVelocity().y);
            b2body.body.applyLinearImpulse(50000f, 0f, b2body.body.getWorldCenter().x, b2body.body.getWorldCenter().y, true);
            player.superSpeed = false;


        }

        if (controller.A && player.hasGun){

            float velX = 20f;  // set the speed of the bullet
            float velY = 0.2f;
            float shooterX = b2body.body.getPosition().x + 0.1f; // get player location
            float shooterY = b2body.body.getPosition().y + 0.1f; // get player location
            levelFactory.createBullet(shooterX, shooterY, velX, velY);
            player.hasGun = false;


        }

    }
}