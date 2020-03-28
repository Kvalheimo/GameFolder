package blog.gamedevelopmentbox2dtutorial.entity.systems;


import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;

import blog.gamedevelopmentbox2dtutorial.Box2dTutorial;
import blog.gamedevelopmentbox2dtutorial.entity.components.B2dBodyComponent;
import blog.gamedevelopmentbox2dtutorial.entity.components.EnemyComponent;
import blog.gamedevelopmentbox2dtutorial.entity.components.Mapper;
import blog.gamedevelopmentbox2dtutorial.entity.components.PlayerComponent;
import blog.gamedevelopmentbox2dtutorial.entity.components.StateComponent;

public class EnemySystem extends IteratingSystem{
    private OrthographicCamera camera;

    @SuppressWarnings("unchecked")
    public EnemySystem(OrthographicCamera camera){
        super(Family.all(EnemyComponent.class).get());
        this.camera = camera;

    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        EnemyComponent enemyCom = Mapper.enemyCom.get(entity);
        StateComponent state = Mapper.stateCom.get(entity);
        B2dBodyComponent bodyCom = Mapper.b2dCom.get(entity);
        //PlayerComponent player = Mapper.playerCom.get(entity);

        //Handle states
        if(bodyCom.body.getLinearVelocity().y < 0  && state.get() != StateComponent.STATE_FALLING){
            state.set(StateComponent.STATE_FALLING);
        }

        if(enemyCom.onGround){
            if(bodyCom.body.getLinearVelocity().x == 0 && state.get() !=  StateComponent.STATE_NORMAL){
                state.set(StateComponent.STATE_NORMAL);
            }

            if(bodyCom.body.getLinearVelocity().x != 0 && state.get() != StateComponent.STATE_MOVING) {
                state.set(StateComponent.STATE_MOVING);
            }

        }


        //Change direction
        if (enemyCom.runningRight) {
            bodyCom.body.setLinearVelocity(3f, 0);
        }
        else if (!enemyCom.runningRight) {
            bodyCom.body.setLinearVelocity(-3f, 0);
        }


        if(enemyCom.isDead){
            bodyCom.isDead =true;
        }

        //Wake up objects when player gets close
        if (bodyCom.body.getPosition().x < camera.position.x + 336/Box2dTutorial.PPM){
            bodyCom.body.setActive(true);
        }

    }
}