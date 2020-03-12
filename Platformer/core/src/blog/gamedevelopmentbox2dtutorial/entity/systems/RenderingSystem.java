package blog.gamedevelopmentbox2dtutorial.entity.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.Comparator;

import blog.gamedevelopmentbox2dtutorial.entity.components.TextureComponent;
import blog.gamedevelopmentbox2dtutorial.entity.components.TransformComponent;

public class RenderingSystem extends SortedIteratingSystem {

    // sets the amount of pixels each metre of box2d objects contains
    static final float PPM = 32.0f;

    // this gets the height and width of our camera frustrum based off the width and height of the screen and our pixel per meter ratio
    static final float FRUSTUM_WIDTH = Gdx.graphics.getWidth()/PPM;
    static final float FRUSTUM_HEIGHT = Gdx.graphics.getHeight()/PPM;

    // get the ratio for converting pixels to metres
    public static final float PIXELS_TO_METRES = 1.0f / PPM;

    // static method to get screen width in metres
    private static Vector2 meterDimensions = new Vector2();
    private static Vector2 pixelDimensions = new Vector2();

    public static Vector2 getScreenSizeInMeters(){
        meterDimensions.set(Gdx.graphics.getWidth()*PIXELS_TO_METRES,
                Gdx.graphics.getHeight()*PIXELS_TO_METRES);
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
        camera = new OrthographicCamera(FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
        camera.position.set(FRUSTUM_WIDTH / 2f, FRUSTUM_HEIGHT / 2f, 0);

        // update camera and sprite batch
        camera.update();
        sb.setProjectionMatrix(camera.combined);
        sb.enableBlending();
        sb.begin();

        // loop through each entity in our render queue
        for (Entity entity: renderQueue){
            TextureComponent tex = textureM.get(entity);
            TransformComponent t = transformM.get(entity);

            if(tex == null || t.isHidden){
                continue;
            }

            float width = tex.region.getRegionWidth();
            float height = tex.region.getRegionHeight();

            float orginX = width/2f;
            float orginY = height/2f;

            sb.draw(tex.region,
                    t.position.x-orginX, t.position.y-orginX,
                    orginX, orginY,
                    width, height,
                    pixelsToMeters(t.scale.x), pixelsToMeters(t.scale.y),
                    t.rotation);
        }

        sb.end();
        renderQueue.clear();


    }

    @Override
    public void update(float dt){
        super.update(dt);
        // sort the renderQueue based on z index
        renderQueue.sort(comparator);



    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        renderQueue.add(entity);
    }
    // convenience method to get camera
    public OrthographicCamera getCamera() {
        return camera;
    }
}
