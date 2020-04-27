package blog.boomerangbeast.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Pool;

public class DestroyableTileComponent implements Component, Pool.Poolable {
    public boolean isDead = false;
    public Entity particleEffect;

    @Override
    public void reset() {
        isDead = false;
        particleEffect = null;
    }
}