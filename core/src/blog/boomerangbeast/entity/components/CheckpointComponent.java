package blog.boomerangbeast.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

public class CheckpointComponent implements Component, Pool.Poolable {
    public Vector2 checkpointPos = new Vector2();

    @Override
    public void reset() {
        checkpointPos = new Vector2();
    }

}

