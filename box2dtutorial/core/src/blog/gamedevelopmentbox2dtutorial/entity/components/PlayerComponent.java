package blog.gamedevelopmentbox2dtutorial.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Pool;

public class PlayerComponent implements Component, Pool.Poolable {
    public OrthographicCamera camera = null;
    public boolean superSpeed = false;
    public boolean onSpring = false;
    public boolean hasGun = false;
    public boolean isDead = false;
    public boolean onGround = false;
    public int jumpCounter = 0; //For double jump


    @Override
    public void reset() {
        camera = null;
        superSpeed = false;
        onSpring = false;
        hasGun = false;
        isDead = false;
    }
}

