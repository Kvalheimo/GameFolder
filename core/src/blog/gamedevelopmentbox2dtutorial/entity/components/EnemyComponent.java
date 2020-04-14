package blog.gamedevelopmentbox2dtutorial.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Pool;

public class EnemyComponent implements Component, Pool.Poolable {
    public static final int BAT = 0;
    public static final int SPIDER = 1;

    public int type = 0;
    public boolean isDead = false;
    public boolean movingRight = true;
    public boolean isMovingUp = true;
    public boolean onGround = false;
    public Entity particleEffect;


    @Override
    public void reset() {
        isDead = false;
        movingRight = true;
        isMovingUp = true;
        onGround = false;
        type = BAT;

    }
}
