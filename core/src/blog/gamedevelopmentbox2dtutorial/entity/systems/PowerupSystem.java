package blog.gamedevelopmentbox2dtutorial.entity.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;


import java.util.Map;

import blog.gamedevelopmentbox2dtutorial.entity.components.B2dBodyComponent;
import blog.gamedevelopmentbox2dtutorial.entity.components.Mapper;
import blog.gamedevelopmentbox2dtutorial.entity.components.PowerupComponent;


public class PowerupSystem extends IteratingSystem {

    @SuppressWarnings("unchecked")
    public PowerupSystem(){
        super(Family.all(PowerupComponent.class).get());
    }


    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        PowerupComponent powerComp = Mapper.powerCom.get(entity);
        B2dBodyComponent bodyCom = Mapper.b2dCom.get(entity);

        //check if bullet is dead
        if(powerComp.isDead){
            if (powerComp.particleEffect != null && Mapper.paCom.get(powerComp.particleEffect) != null)
                Mapper.paCom.get(powerComp.particleEffect).isDead = true;
            bodyCom.isDead = true;
        }

    }
}