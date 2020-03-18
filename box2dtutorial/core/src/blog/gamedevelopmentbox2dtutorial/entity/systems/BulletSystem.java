package blog.gamedevelopmentbox2dtutorial.entity.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;


import blog.gamedevelopmentbox2dtutorial.entity.components.B2dBodyComponent;
import blog.gamedevelopmentbox2dtutorial.entity.components.BulletComponent;
import blog.gamedevelopmentbox2dtutorial.entity.components.Mapper;


public class BulletSystem extends IteratingSystem {
    //private Entity player;

    @SuppressWarnings("unchecked")
    public BulletSystem(){
        super(Family.all(BulletComponent.class).get());
        //this.player = player;
    }


    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        //get box 2d body and bullet components
        B2dBodyComponent b2body = Mapper.b2dCom.get(entity);
        BulletComponent bullet = Mapper.bulletCom.get(entity);

        // apply bullet velocity to bullet body
        b2body.body.setLinearVelocity(bullet.xVel, bullet.yVel);

        //check if bullet is dead
        if(bullet.isDead){
            b2body.isDead = true;
        }
    }
}