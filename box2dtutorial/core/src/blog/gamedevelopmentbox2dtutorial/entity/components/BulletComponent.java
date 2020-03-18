package blog.gamedevelopmentbox2dtutorial.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class BulletComponent implements Component, Pool.Poolable {
    public float xVel;
    public float yVel;
    public boolean isDead = false;


    @Override
    public void reset() {
        xVel = 0;
        yVel = 0;
        isDead = false;

    }



}
