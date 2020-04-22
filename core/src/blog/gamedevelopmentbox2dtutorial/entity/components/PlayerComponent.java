package blog.gamedevelopmentbox2dtutorial.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

public class PlayerComponent implements Component, Pool.Poolable {
    public OrthographicCamera camera = null;
    public boolean superSpeed = false;
    public boolean onSpring = false;
    public boolean hasGun = false;
    public boolean isDead = false;
    public boolean isFinished = false;
    public boolean onGround = false;
    public boolean runningRight = true;
    public boolean onPlatform = false;
    public boolean onWall = false;
    public boolean speedX = false;
    public boolean speedY = false;
    public boolean superspeedDisplayed = false;
    public boolean boomerangDisplayed = false;
    public int jumpCounter = 0;
    public int boomerangCount = 0;
    public Entity particleEffect;
    public Vector2 checkPointPos = new Vector2();




    @Override
    public void reset() {
        camera = null;
        superSpeed = false;
        onSpring = false;
        hasGun = false;
        onPlatform = false;
        boomerangCount= 0;
        superspeedDisplayed = false;
        boomerangDisplayed = false;
        isDead = false;
        isFinished = false;
        onGround = false;
        runningRight = true;
        onWall = false;
        speedX = false;
        speedY = false;
        jumpCounter = 0;
        particleEffect = null;
        checkPointPos = new Vector2();
    }
}

