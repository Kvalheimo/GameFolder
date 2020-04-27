package blog.boomerangbeast.entity.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;


import blog.boomerangbeast.BoomerangBeast;
import blog.boomerangbeast.entity.components.B2dBodyComponent;
import blog.boomerangbeast.entity.components.BulletComponent;
import blog.boomerangbeast.entity.components.Mapper;


public class BulletSystem extends IteratingSystem {

    @SuppressWarnings("unchecked")
    public BulletSystem(){
        super(Family.all(BulletComponent.class).get());
    }


    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        //get box 2d body and bullet components
        B2dBodyComponent b2body = Mapper.b2dCom.get(entity);
        BulletComponent bullet = Mapper.bulletCom.get(entity);


        b2body.body.applyForceToCenter(0, b2body.body.getMass()* BoomerangBeast.GRAVITY, true);

        // apply bullet velocity to bullet body
        b2body.body.setLinearVelocity(bullet.xVel, bullet.yVel);

        //check if bullet is dead
        if(bullet.isDead){
            if (Mapper.paCom.get(bullet.particleEffect) != null)
                Mapper.paCom.get(bullet.particleEffect).isDead = true;
            b2body.isDead = true;
        }
    }
}