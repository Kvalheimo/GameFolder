package blog.gamedevelopmentbox2dtutorial.entity.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import blog.gamedevelopmentbox2dtutorial.entity.components.B2dBodyComponent;
import blog.gamedevelopmentbox2dtutorial.entity.components.DestroyableTileComponent;
import blog.gamedevelopmentbox2dtutorial.entity.components.Mapper;


public class DestroyableTileSystem extends IteratingSystem {

    @SuppressWarnings("unchecked")
    public DestroyableTileSystem(){
        super(Family.all(DestroyableTileComponent.class).get());
    }


    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        DestroyableTileComponent destComp = Mapper.destCom.get(entity);
        B2dBodyComponent bodyCom = Mapper.b2dCom.get(entity);

        //check if bullet is dead
        if(destComp.isDead){
            if (destComp.particleEffect != null && Mapper.paCom.get(destComp.particleEffect) != null)
                Mapper.paCom.get(destComp.particleEffect).isDead = true;
            bodyCom.isDead = true;
        }
        if(destComp.isDead){
            if (destComp.particleEffect != null && Mapper.paCom.get(destComp.particleEffect) != null)
                Mapper.paCom.get(destComp.particleEffect).isDead = true;
            bodyCom.isDead = true;
        }

    }
}