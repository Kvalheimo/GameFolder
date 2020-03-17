package blog.gamedevelopmentbox2dtutorial.entity.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import blog.gamedevelopmentbox2dtutorial.entity.components.B2dBodyComponent;
import blog.gamedevelopmentbox2dtutorial.entity.components.CollisionComponent;
import blog.gamedevelopmentbox2dtutorial.entity.components.PlayerComponent;
import blog.gamedevelopmentbox2dtutorial.entity.components.TypeComponent;

public class CollisionSystem extends IteratingSystem {
    ComponentMapper<CollisionComponent> cm;
    ComponentMapper<PlayerComponent> pm;

    @SuppressWarnings("unchecked")
    public CollisionSystem() {
        // only need to worry about player collisions
        super(Family.all(CollisionComponent.class,PlayerComponent.class).get());

        cm = ComponentMapper.getFor(CollisionComponent.class);
        pm = ComponentMapper.getFor(PlayerComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        // get player collision component
        CollisionComponent cc = cm.get(entity);

        Entity collidedEntity = cc.collisionEntity;
        if(collidedEntity != null){
            TypeComponent type = collidedEntity.getComponent(TypeComponent.class);
            if(type != null){
                switch(type.type){
                    case TypeComponent.ENEMY:
                        //do player hit enemy thing
                        System.out.println("player hit enemy");
                        break;
                    case TypeComponent.OTHER:
                        //do player hit scenery thing
                        System.out.println("player hit other");
                        break;
                    case TypeComponent.POWER_UP:
                        //do player hit other thing
                        System.out.println("player hit power up");
                        break; //technically this isn't needed
                    case TypeComponent.SCENERY:
                        //do player hit other thing
                        System.out.println("player hit scenery");
                        System.out.println(entity.getComponent(B2dBodyComponent.class).body.getPosition());
                        break; //technically this isn't needed
                }
                cc.collisionEntity = null; // collision handled reset component
            }
        }

    }

}