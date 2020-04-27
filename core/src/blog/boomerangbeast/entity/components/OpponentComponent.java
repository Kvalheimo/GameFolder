package blog.boomerangbeast.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Pool;

public class OpponentComponent implements Component, Pool.Poolable{
    public Boolean movingRight = true;
    private Vector3 prevPos = new Vector3(0,0,0);
    @Override
    public void reset() {
        movingRight = true;
        prevPos = new Vector3(0,0,0);
    }

    public void setPos(Vector3 pos) {
        if (pos.x > prevPos.x) {
            movingRight = true;
        }
        else if (pos.x < prevPos.x) {
            movingRight = false;
        }
        prevPos = pos;
    }
}
