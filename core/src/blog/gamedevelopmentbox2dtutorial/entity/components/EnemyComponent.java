package blog.gamedevelopmentbox2dtutorial.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Pool;

public class EnemyComponent implements Component, Pool.Poolable {
        public boolean isDead = false;
        public boolean runningRight = true;
        public boolean isMovingUp = true;
        public boolean onGround = false;
        public Entity particleEffect;


    @Override
    public void reset() {
        isDead = false;
        runningRight = true;
        isMovingUp = true;
        onGround = false;

    }
}
