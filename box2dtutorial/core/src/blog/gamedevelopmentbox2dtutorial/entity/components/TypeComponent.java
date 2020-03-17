package blog.gamedevelopmentbox2dtutorial.entity.components;

import com.badlogic.ashley.core.Component;

public class TypeComponent implements Component {
    public static final int PLAYER = 0;
    public static final int ENEMY = 1;
    public static final int SCENERY = 2;
    public static final int SUPER_SPEED = 3;
    public static final int GROUND = 4;
    public static final int GUN = 5;
    public static final int SPRING = 6;

    public static final int OTHER = 7;

    public int type = OTHER;
}
