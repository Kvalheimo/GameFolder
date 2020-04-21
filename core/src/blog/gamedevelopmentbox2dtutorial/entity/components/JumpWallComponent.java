package blog.gamedevelopmentbox2dtutorial.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

public class JumpWallComponent implements Component, Pool.Poolable {
    public float jumpWallPos = -0;

    @Override
    public void reset() {
        jumpWallPos = -0;
    }

}

