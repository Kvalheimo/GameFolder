package blog.gamedevelopmentbox2dtutorial.entity.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;

import blog.gamedevelopmentbox2dtutorial.Box2dTutorial;
import blog.gamedevelopmentbox2dtutorial.entity.components.B2dBodyComponent;
import blog.gamedevelopmentbox2dtutorial.entity.components.Mapper;
import blog.gamedevelopmentbox2dtutorial.entity.components.PlatformComponent;
import blog.gamedevelopmentbox2dtutorial.entity.components.StateComponent;

import static java.lang.Math.floor;

public class MoveableSystem extends IteratingSystem {
    private OrthographicCamera camera;
    private float time;

    public MoveableSystem(OrthographicCamera camera){
        super(Family.all(PlatformComponent.class).get());
        this.camera = camera;
        time = 0;

    }
    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PlatformComponent platform = Mapper.platCom.get(entity);
        StateComponent state = Mapper.stateCom.get(entity);
        B2dBodyComponent bodyCom = Mapper.b2dCom.get(entity);

        if (bodyCom.body.getPosition().x < camera.position.x + 336/ Box2dTutorial.PPM){
            bodyCom.body.setActive(true);
            platform.activated = true;
        }

        if(platform.activated){
            time += deltaTime;
        }

        if (platform.type == platform.MOVEABLE_HOR){
            bodyCom.body.applyForceToCenter(0, bodyCom.body.getMass()* Box2dTutorial.GRAVITY, true);
            bodyCom.body.setLinearVelocity(bodyCom.body.getLinearVelocity().x,0f);
            bodyCom.body.setFixedRotation(true);

        }

        //Change direction
        if (platform.movingRight) {
            bodyCom.body.setLinearVelocity(platform.velocity_x, 0f);
            bodyCom.body.setAngularVelocity(0f);
        }
        else if (!platform.movingRight) {
            bodyCom.body.setLinearVelocity(-platform.velocity_x, 0f);
            bodyCom.body.setAngularVelocity(0f);
        }

        if((floor(time) == 3)){
            if(platform.movingRight){
                platform.movingRight = false;
            }
            else{
                platform.movingRight = true;
            }
            time = 0;
        }

    }
}
