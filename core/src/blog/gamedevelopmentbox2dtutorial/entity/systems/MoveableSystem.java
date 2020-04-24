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

import static java.lang.Math.abs;
import static java.lang.Math.floor;

public class MoveableSystem extends IteratingSystem {
    private OrthographicCamera camera;

    public MoveableSystem(OrthographicCamera camera){
        super(Family.all(PlatformComponent.class).get());
        this.camera = camera;

    }
    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PlatformComponent platform = Mapper.platCom.get(entity);
        StateComponent state = Mapper.stateCom.get(entity);
        B2dBodyComponent bodyCom = Mapper.b2dCom.get(entity);

      //  platform.y_position = bodyCom.body.getPosition().y;
        if (bodyCom.body.getPosition().x < camera.position.x + 336/ Box2dTutorial.PPM){
            bodyCom.body.setActive(true);
            platform.activated = true;
        }

        if(platform.activated){
            platform.x_position += bodyCom.body.getLinearVelocity().x*deltaTime;
            platform.y_position += bodyCom.body.getLinearVelocity().y*deltaTime;
        }

        if (platform.type == platform.MOVEABLE_HOR){

            //Change direction
            if (platform.movingRight) {
                bodyCom.body.setLinearVelocity(platform.velocity_x, 0f);
                bodyCom.body.setAngularVelocity(0f);
            }
            else if (!platform.movingRight) {
                bodyCom.body.setLinearVelocity(-platform.velocity_x, 0f);
                bodyCom.body.setAngularVelocity(0f);
            }

            if((platform.x_position >= platform.turn_distance) && platform.movingRight){
                 platform.movingRight = false;
                 platform.x_position = 0;
            }
            else if((bodyCom.body.getPosition().x <= platform.start_position_x) && !platform.movingRight){
                platform.movingRight = true;
                platform.x_position = 0;
            }

        }if (platform.type == platform.MOVEABLE_VER){

            //Change direction vertical movement
            if (platform.isMovingUp) {
                bodyCom.body.setLinearVelocity(0, platform.velocity_y);
                bodyCom.body.setAngularVelocity(0f);
            }
            else if (!platform.isMovingUp) {
                bodyCom.body.setLinearVelocity(0, -platform.velocity_y);
                bodyCom.body.setAngularVelocity(0f);
            }

            if((platform.y_position >= platform.turn_distance) && platform.isMovingUp){
                platform.isMovingUp = false;
                platform.y_position = 0;
            }
            else if((bodyCom.body.getPosition().y <= platform.start_position_y) && !platform.isMovingUp){
                platform.isMovingUp = true;
                platform.y_position = 0;
            }

        }
    }
}
