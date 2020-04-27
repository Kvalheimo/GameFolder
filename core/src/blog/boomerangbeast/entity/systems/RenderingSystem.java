package blog.boomerangbeast.entity.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Comparator;

import blog.boomerangbeast.BoomerangBeast;
import blog.boomerangbeast.entity.components.TextureComponent;
import blog.boomerangbeast.entity.components.TransformComponent;

public class RenderingSystem extends SortedIteratingSystem {
    //Debug
    private boolean shouldRender = true;


    // get the ratio for converting pixels to metres
    public static final float PIXELS_TO_METRES = 1.0f / BoomerangBeast.PPM;

    // static method to get screen width in metres
    private static Vector2 meterDimensions = new Vector2();
    private static Vector2 pixelDimensions = new Vector2();

    public static Vector2 getScreenSizeInMeters(){
        meterDimensions.set(Gdx.graphics.getWidth()* BoomerangBeast.PPM,
                Gdx.graphics.getHeight()* BoomerangBeast.PPM);
        return meterDimensions;
    }

    // static method to get screen size in pixels
    public static Vector2 getScreenSizeInPixesl() {
        pixelDimensions.set(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        return pixelDimensions;

    }

    // convenience method to convert pixels to meters
    public static float pixelsToMeters(float pixelValue) {
        return pixelValue * PIXELS_TO_METRES;
    }



    private SpriteBatch sb;
    private Array<Entity> renderQueue;
    private Comparator<Entity> comparator;
    private OrthographicCamera camera;
    private Viewport viewport;

    //Component mappers to get components from entitis
    private ComponentMapper<TextureComponent> textureM;
    private ComponentMapper<TransformComponent> transformM;

    @SuppressWarnings("unchecked")
    public RenderingSystem(SpriteBatch sb){
        // gets all entities with a TransofmComponent and TextureComponent
        super(Family.all(TransformComponent.class, TextureComponent.class).get(), new ZComparator());

        comparator = new ZComparator();

        //creates out componentMappers
        textureM = ComponentMapper.getFor(TextureComponent.class);
        transformM = ComponentMapper.getFor(TransformComponent.class);

        // create the array for sorting entities
        renderQueue = new Array<Entity>();

        this.sb = sb;

        // set up the camera to match our screen size
        camera = new OrthographicCamera();



        viewport = new FitViewport(Gdx.graphics.getWidth()/(BoomerangBeast.PPM*2),Gdx.graphics.getHeight()/(BoomerangBeast.PPM*2), camera);
        viewport.getCamera().update();


        camera.position.set(0, 0,0);
        camera.update();

    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        renderQueue.sort(comparator);

        camera.update();
        sb.setProjectionMatrix(camera.combined);
        sb.enableBlending();
        if(shouldRender){
            sb.begin();

            for (Entity entity : renderQueue) {
                TextureComponent tex = textureM.get(entity);
                TransformComponent t = transformM.get(entity);

                if (tex.region == null || t.isHidden) {
                    continue;
                }

                float width = tex.region.getRegionWidth();
                float height = tex.region.getRegionHeight();

                float originX = width/2f;
                float originY = height/2f;

                sb.draw(tex.region,
                        t.position.x - originX, t.position.y - originY,
                        originX, originY,
                        width, height,
                        pixelsToMeters(t.scale.x), pixelsToMeters(t.scale.y),
                        t.rotation);
            }
            sb.end();
        }
        renderQueue.clear();
    }



    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        renderQueue.add(entity);
    }
    // convenience method to get camera
    public OrthographicCamera getCamera() {
        return camera;
    }

    public Viewport getViewport(){
        return viewport;
    }
}
