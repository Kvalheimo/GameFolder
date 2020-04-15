package blog.gamedevelopmentbox2dtutorial.entity.systems;


import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;

import blog.gamedevelopmentbox2dtutorial.Box2dTutorial;
import blog.gamedevelopmentbox2dtutorial.Factory.LevelFactory;
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


        //Handle bats fly
        if (enemyCom.type == enemyCom.BAT){
            bodyCom.body.applyForceToCenter(0, bodyCom.body.getMass()* Box2dTutorial.GRAVITY, true);

        }


        //Handle states
        if(bodyCom.body.getLinearVelocity().x != 0  && state.get() != StateComponent.STATE_MOVING){
            state.set(StateComponent.STATE_MOVING);
        }



       if (bodyCom.body.getLinearVelocity().x == 0  && state.get() != StateComponent.STATE_NORMAL){
           state.set(StateComponent.STATE_NORMAL);
       }



        //Change direction
        if (enemyCom.movingRight) {
            bodyCom.body.setLinearVelocity(3f, bodyCom.body.getLinearVelocity().y);
        }
        else if (!enemyCom.movingRight) {
            bodyCom.body.setLinearVelocity(-3f, bodyCom.body.getLinearVelocity().y);
        }

        //Wake up objects when player gets close
        if (bodyCom.body.getPosition().x < camera.position.x + 336/Box2dTutorial.PPM){
            bodyCom.body.setActive(true);
        }


        if(enemyCom.isDead){
            if (enemyCom.particleEffect != null && Mapper.paCom.get(enemyCom.particleEffect) != null)
                Mapper.paCom.get(enemyCom.particleEffect).isDead = true;
            bodyCom.isDead =true;
        }




    }
}