package blog.gamedevelopmentbox2dtutorial.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

import static blog.gamedevelopmentbox2dtutorial.DFUtils.PLATFORM_VELOCITY_X;

public class PlatformComponent implements Component, Pool.Poolable {
    public static final int MOVEABLE = 0;
    public static final int SYMBIOSE = 1;

    public float velocity_x = PLATFORM_VELOCITY_X;
    public int type = 0;
    public boolean movingRight = true;
    public boolean isMovingUp = false;

    @Override
    public void reset() {
        movingRight = true;
        isMovingUp = false;
        type = MOVEABLE;
    }
}
