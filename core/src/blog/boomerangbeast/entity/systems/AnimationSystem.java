package blog.boomerangbeast.entity.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import blog.boomerangbeast.entity.components.AnimationComponent;
import blog.boomerangbeast.entity.components.B2dBodyComponent;
import blog.boomerangbeast.entity.components.EnemyComponent;
import blog.boomerangbeast.entity.components.Mapper;
import blog.boomerangbeast.entity.components.OpponentComponent;
import blog.boomerangbeast.entity.components.PlatformComponent;
import blog.boomerangbeast.entity.components.PlayerComponent;
import blog.boomerangbeast.entity.components.StateComponent;
import blog.boomerangbeast.entity.components.TextureComponent;

import static blog.boomerangbeast.DFUtils.PLATFORM_VELOCITY_X;

public class AnimationSystem extends IteratingSystem {

    @SuppressWarnings("unchecked")
    public AnimationSystem(){
        super(Family.all(TextureComponent.class,
                AnimationComponent.class,
                StateComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        AnimationComponent ani = Mapper.animCom.get(entity);
        StateComponent state = Mapper.stateCom.get(entity);
        B2dBodyComponent b2body = Mapper.b2dCom.get(entity);
        PlayerComponent player = Mapper.playerCom.get(entity);
        EnemyComponent enemy = Mapper.enemyCom.get(entity);
        OpponentComponent opponent = Mapper.opponentCom.get(entity);
        PlatformComponent platform = Mapper.platCom.get(entity);


        if(ani.animationsN.containsKey(state.get())) {
            TextureComponent tex = Mapper.texCom.get(entity);
            tex.region = (TextureRegion) ani.animationsN.get(state.get()).getKeyFrame(state.time, state.isLooping);

            //Handle player animation
            if(player != null) {
                if (player.boomerangCount >= 1){
                    tex.region = (TextureRegion) ani.animationsB.get(state.get()).getKeyFrame(state.time, state.isLooping);
                }

                //if boomerang beast is running left and the texture isnt facing left... flip it.
                if ((b2body.body.getLinearVelocity().x < -1 || !player.runningRight) && !tex.region.isFlipX()) {
                    tex.region.flip(true, false);
                    player.runningRight = false;
                }

                //if boomerang beast is running right and the texture isnt facing right... flip it.
                else if ((b2body.body.getLinearVelocity().x > 1 && !player.onPlatform || player.runningRight) && tex.region.isFlipX()) {
                    tex.region.flip(true, false);
                    player.runningRight = true;
                }
                else if ((((b2body.body.getLinearVelocity().x > PLATFORM_VELOCITY_X)  && player.onPlatform) || player.runningRight) && tex.region.isFlipX()) {
                    tex.region.flip(true, false);
                    player.runningRight = true;
                }
            }


            //Handle enemy animation
            else if(enemy != null) {
                //if mario is running left and the texture isnt facing left... flip it.
                if ((b2body.body.getLinearVelocity().x < -2 || !enemy.movingRight) && !tex.region.isFlipX()) {
                    tex.region.flip(true, false);
                }

                //if mario is running right and the texture isnt facing right... flip it.
                else if ((b2body.body.getLinearVelocity().x > 2 || enemy.movingRight) && tex.region.isFlipX()) {
                    tex.region.flip(true, false);
                }
            }

            //Handle opponent animation
            else if(opponent != null) {
                //if mario is running left and the texture isnt facing left... flip it.
                if (!opponent.movingRight && !tex.region.isFlipX()) {
                    System.out.println("MOOOOOOOOVE");
                    tex.region.flip(true, false);
                }

                //if mario is running right and the texture isnt facing right... flip it.
                else if (opponent.movingRight && tex.region.isFlipX()) {
                    tex.region.flip(true, false);
                }
            }
        }
        state.time += deltaTime;
    }
}