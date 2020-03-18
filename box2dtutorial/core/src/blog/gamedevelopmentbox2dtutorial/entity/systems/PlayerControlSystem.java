package blog.gamedevelopmentbox2dtutorial.entity.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;

import blog.gamedevelopmentbox2dtutorial.controller.KeyboardController;
import blog.gamedevelopmentbox2dtutorial.entity.components.B2dBodyComponent;
import blog.gamedevelopmentbox2dtutorial.entity.components.PlayerComponent;
import blog.gamedevelopmentbox2dtutorial.entity.components.StateComponent;

public class PlayerControlSystem extends IteratingSystem{

    ComponentMapper<PlayerComponent> pm;
    ComponentMapper<B2dBodyComponent> bodm;
    ComponentMapper<StateComponent> sm;
    KeyboardController controller;


    @SuppressWarnings("unchecked")
    public PlayerControlSystem(KeyboardController keyCon) {
        super(Family.all(PlayerComponent.class).get());
        controller = keyCon;
        pm = ComponentMapper.getFor(PlayerComponent.class);
        bodm = ComponentMapper.getFor(B2dBodyComponent.class);
        sm = ComponentMapper.getFor(StateComponent.class);

    }


    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        B2dBodyComponent b2body = bodm.get(entity);
        StateComponent state = sm.get(entity);
        PlayerComponent player = pm.get(entity);

        player.camera.position.x = b2body.body.getPosition().x;
        player.camera.update();


        //Handle states
        if(b2body.body.getLinearVelocity().y < 0){
            state.set(StateComponent.STATE_FALLING);
        }

        if(b2body.body.getLinearVelocity().y == 0){
            if(state.get() == StateComponent.STATE_FALLING){
                state.set(StateComponent.STATE_NORMAL);
            }
            if(b2body.body.getLinearVelocity().x != 0){
                state.set(StateComponent.STATE_MOVING);
            }
        }


        //Controller
        if(controller.left){
            b2body.body.setLinearVelocity(MathUtils.lerp(b2body.body.getLinearVelocity().x, -10f, 0.2f),b2body.body.getLinearVelocity().y);
        }
        if(controller.right){
            b2body.body.setLinearVelocity(MathUtils.lerp(b2body.body.getLinearVelocity().x, 10f, 0.2f),b2body.body.getLinearVelocity().y);
        }

        if(!controller.left && ! controller.right){
            b2body.body.setLinearVelocity(MathUtils.lerp(b2body.body.getLinearVelocity().x, 0, 0.2f),b2body.body.getLinearVelocity().y);
        }

        if(controller.up && (state.get() == StateComponent.STATE_NORMAL || state.get() == StateComponent.STATE_MOVING)){
                b2body.body.applyForceToCenter(0, 100f, true);
                b2body.body.applyLinearImpulse(0, 50, b2body.body.getWorldCenter().x, b2body.body.getWorldCenter().y, true);
                state.set(StateComponent.STATE_JUMPING);
        }

        /*
        if(controller.up && state.get() == StateComponent.STATE_FALLING) {
            b2body.body.applyForceToCenter(0, 1, true);
            b2body.body.applyLinearImpulse(0, 5, b2body.body.getWorldCenter().x, b2body.body.getWorldCenter().y, true);
        }
         */

        if(player.onSpring){
            //b2body.body.applyForceToCenter(0, 50, true);
            b2body.body.applyLinearImpulse(0, 70f, b2body.body.getWorldCenter().x, b2body.body.getWorldCenter().y, true);
            state.set(StateComponent.STATE_JUMPING);
            player.onSpring = false;
        }

        if (controller.space && player.superSpeed){
            //b2body.body.setLinearVelocity(MathUtils.lerp(b2body.body.getLinearVelocity().x, 5000, 0.01f),b2body.body.getLinearVelocity().y);
            b2body.body.applyLinearImpulse(50000f, 0f, b2body.body.getWorldCenter().x, b2body.body.getWorldCenter().y, true);
            player.superSpeed = false;


        }

    }
}