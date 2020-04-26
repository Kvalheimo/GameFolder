package blog.boomerangbeast.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Pool;


public class ParticleEffectComponent implements Component, Pool.Poolable {
    public ParticleEffectPool.PooledEffect particleEffect;
    public boolean isAttached = false;
    public float xOffset = 0;
    public float yOffset = 0;
    public float timeTilDeath = 0.001f;
    public boolean isDead = false;
    public Body attachedBody;

    @Override
    public void reset() {
        particleEffect.free();
        particleEffect = null;
        xOffset = 0;
        yOffset = 0;
        isAttached = false;
        isDead = false;
        attachedBody = null;
        timeTilDeath = 0.001f;
    }
}


