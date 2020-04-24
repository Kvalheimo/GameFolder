package blog.gamedevelopmentbox2dtutorial.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

import static blog.gamedevelopmentbox2dtutorial.DFUtils.PLATFORM_VELOCITY_X;
import static blog.gamedevelopmentbox2dtutorial.DFUtils.PLATFORM_VELOCITY_Y;

public class PlatformComponent implements Component, Pool.Poolable {
    public static final int MOVEABLE_HOR = 0;
    public static final int MOVEABLE_VER = 1;

    public float x_position = 0;
    public float y_position = 0;
    public float start_position_x;
    public float start_position_y;
    public float turn_distance = 5;
    public float velocity_x = PLATFORM_VELOCITY_X;
    public float velocity_y = PLATFORM_VELOCITY_Y;
    public int type = 0;
    public boolean movingRight = true;
    public boolean isMovingUp = false;
    public boolean activated = false;

    @Override
    public void reset() {
        movingRight = true;
        isMovingUp = false;
        type = MOVEABLE_HOR;
        turn_distance = 5;
        x_position = 0;
    }
}
