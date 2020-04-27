package blog.boomerangbeast.entity.systems;


import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

import blog.boomerangbeast.entity.components.Mapper;
import blog.boomerangbeast.entity.components.ParticleEffectComponent;

public class ParticleEffectSystem extends IteratingSystem{

    private static final boolean shouldRender = true;

    private Array<Entity> renderQueue;
    private SpriteBatch batch;
    private OrthographicCamera camera;


    @SuppressWarnings("unchecked")
    public ParticleEffectSystem(SpriteBatch sb, OrthographicCamera cam) {
        super(Family.all(ParticleEffectComponent.class).get());
        renderQueue = new Array<Entity>();
        batch = sb;
        camera = cam;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        batch.setProjectionMatrix(camera.combined);
        batch.enableBlending();
        // Render PE
        if(shouldRender){
            batch.begin();
            for (Entity entity : renderQueue) {
                ParticleEffectComponent paCom = Mapper.paCom.get(entity);
                paCom.particleEffect.draw(batch, deltaTime);
            }
            batch.end();
        }
        renderQueue.clear();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        ParticleEffectComponent paCom = Mapper.paCom.get(entity);
        if(paCom.isDead){
            paCom.timeTilDeath -= deltaTime;
        }

        // Move PE if attached
        if(paCom.isAttached){
            paCom.particleEffect.setPosition(
                    paCom.attachedBody.getPosition().x + paCom.xOffset,
                    paCom.attachedBody.getPosition().y + paCom.yOffset);
        }
        // free PE if completed
        if(paCom.particleEffect.isComplete() || paCom.timeTilDeath <= 0){
            getEngine().removeEntity(entity);
        }else{
            renderQueue.add(entity);
        }
    }
}