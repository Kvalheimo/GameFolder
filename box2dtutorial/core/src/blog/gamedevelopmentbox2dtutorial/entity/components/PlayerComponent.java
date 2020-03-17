package blog.gamedevelopmentbox2dtutorial.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class PlayerComponent implements Component {
    public OrthographicCamera camera = null;
    public boolean superSpeed = false;
    public boolean onSpring = false;
    public boolean hasGun = false;
}
