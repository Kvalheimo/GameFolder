package blog.gamedevelopmentbox2dtutorial.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class TypeComponent implements Component, Pool.Poolable {
    public static final int PLAYER = 0;
    public static final int ENEMY = 1;
    public static final int SCENERY = 2;
    public static final int SUPER_SPEED = 3;
    public static final int GROUND = 4;
    public static final int GUN = 5;
    public static final int SPRING = 6;
    public static final int BULLET = 7;
    public static final int WALL = 8;
    public static final int SPEED_X = 9;
    public static final int SPEED_Y = 10;
    public static final int WATER = 11;
    public static final int OTHER = 12;
    public static final int SPIKES = 13;
    public static final int CHECKPOINT = 14;
    public static final int DESTROYABLE_TILE = 15;
    public static final int PLATFORM = 16;

    public int type = OTHER;

    @Override
    public void reset() {
        type = OTHER;
    }


}
