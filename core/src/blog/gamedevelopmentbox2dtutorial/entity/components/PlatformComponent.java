package blog.gamedevelopmentbox2dtutorial.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class PlatformComponent implements Component, Pool.Poolable {
    public static final int MOVEABLE = 0;

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
