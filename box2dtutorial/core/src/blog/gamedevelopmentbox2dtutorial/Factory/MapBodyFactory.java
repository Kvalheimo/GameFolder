package blog.gamedevelopmentbox2dtutorial.Factory;

import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.CircleMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import blog.gamedevelopmentbox2dtutorial.Box2dTutorial;


public class MapBodyFactory {
    private World world;

    private static MapBodyFactory thisInstance;


   private MapBodyFactory(World world){
       this.world = world;

   }

    public static MapBodyFactory getInstance(World world){
        if (thisInstance == null){
            thisInstance = new MapBodyFactory(world);
        }
        return thisInstance;
    }


    public static Array<Body> buildShapes(Map map, World world, String layer) {

        MapObjects objects = map.getLayers().get(layer).getObjects();

        System.out.println(objects.getCount());


        Array<Body> bodies = new Array<Body>();

        for(MapObject object : objects) {
            Vector2 position = new Vector2(0, 0);

            if (object instanceof TextureMapObject) {
                continue;
            }

            Shape shape;

            //Kun rectangleMapObject som blir rendret på riktig sted
            //Så fungerer kun å lage rectangle onjects på mappet

            if (object instanceof RectangleMapObject) {
                System.out.println("making rect shape");
                shape = getRectangle((RectangleMapObject)object);
                Rectangle rectangle = ((RectangleMapObject) object).getRectangle();

                position.set((rectangle.getX() + rectangle.getWidth() / 2)/ Box2dTutorial.PPT, (rectangle.getY() + rectangle.getHeight() / 2)/Box2dTutorial.PPT);

                BodyDef bodyDef = new BodyDef();
                bodyDef.type = BodyDef.BodyType.StaticBody;
                bodyDef.position.set(position);
                Body body = world.createBody(bodyDef);

                Fixture fixture = body.createFixture(shape, 1f);
                fixture.setFriction(0.1f);

                //setting the position of the body's origin. In this case with zero rotation
                body.setTransform(rectangle.getCenter(new Vector2()).scl(1/Box2dTutorial.PPT), 0);

                bodies.add(body);

            }

            /////////////////////
            ////////////////////Fungerer ikke ikke lage disse shapene. Posisjonen blir feil
            ///////////////////

            else if (object instanceof PolygonMapObject) {
                System.out.println("making polygon shape");
                shape = getPolygon((PolygonMapObject)object);
                Polygon polygon = ((PolygonMapObject) object).getPolygon();
                position.set(polygon.getX()/Box2dTutorial.PPT, polygon.getY()/Box2dTutorial.PPT);

                BodyDef bodyDef = new BodyDef();
                bodyDef.type = BodyDef.BodyType.StaticBody;
                bodyDef.position.set(position);
                Body body = world.createBody(bodyDef);

                Fixture fixture = body.createFixture(shape, 1f);
                fixture.setFriction(0.1f);

                //setting the position of the body's origin. In this case with zero rotation
                body.setTransform(polygon.getX()/Box2dTutorial.PPT, polygon.getY()/Box2dTutorial.PPT, 0);

                bodies.add(body);
            }
            else if (object instanceof PolylineMapObject) {
                System.out.println("making line shape");
                shape = getPolyline((PolylineMapObject)object);
                Polyline line = ((PolylineMapObject) object).getPolyline();
                position.set(line.getX(), line.getY());

                BodyDef bodyDef = new BodyDef();
                bodyDef.type = BodyDef.BodyType.StaticBody;
                bodyDef.position.set(position);
                Body body = world.createBody(bodyDef);

                Fixture fixture = body.createFixture(shape, 1f);
                fixture.setFriction(0.1f);

                //setting the position of the body's origin. In this case with zero rotation
                body.setTransform(line.getX()/Box2dTutorial.PPT, line.getY()/Box2dTutorial.PPT, 0);

                bodies.add(body);
            }
            else if (object instanceof CircleMapObject) {
                System.out.println("making circle shape");
                shape = getCircle((CircleMapObject) object);
                Circle circle = ((CircleMapObject) object).getCircle();
                position.set(circle.x, circle.y);

                BodyDef bodyDef = new BodyDef();
                bodyDef.type = BodyDef.BodyType.StaticBody;
                bodyDef.position.set(position);
                Body body = world.createBody(bodyDef);

                Fixture fixture = body.createFixture(shape, 1f);
                fixture.setFriction(0.1f);

                //setting the position of the body's origin. In this case with zero rotation
                body.setTransform(circle.x/Box2dTutorial.PPT, circle.y/Box2dTutorial.PPT, 0);

                bodies.add(body);
            }
            else {
                continue;
            }

        }
        return bodies;
    }


    private static PolygonShape getRectangle(RectangleMapObject rectangleObject) {
        Rectangle rectangle = rectangleObject.getRectangle();
        PolygonShape polygon = new PolygonShape();

        polygon.setAsBox(rectangle.getWidth() * 0.5f / Box2dTutorial.PPT,
                rectangle.getHeight() * 0.5f / Box2dTutorial.PPT);
        return polygon;
    }



    private static CircleShape getCircle(CircleMapObject circleObject) {
        Circle circle = circleObject.getCircle();
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(circle.radius / Box2dTutorial.PPT);
        circleShape.setPosition(new Vector2(circle.x / Box2dTutorial.PPT, circle.y / Box2dTutorial.PPT));
        return circleShape;
    }

    private static PolygonShape getPolygon(PolygonMapObject polygonObject) {
        PolygonShape polygon = new PolygonShape();
        float[] vertices = polygonObject.getPolygon().getTransformedVertices();

        float[] worldVertices = new float[vertices.length];

        for (int i = 0; i < vertices.length; ++i) {
            worldVertices[i] = vertices[i] / Box2dTutorial.PPT;
        }

        polygon.set(worldVertices);
        return polygon;
    }

    private static ChainShape getPolyline(PolylineMapObject polylineObject) {
        float[] vertices = polylineObject.getPolyline().getTransformedVertices();
        Vector2[] worldVertices = new Vector2[vertices.length / 2];

        for (int i = 0; i < vertices.length / 2; ++i) {
            worldVertices[i] = new Vector2();
            worldVertices[i].x = vertices[i * 2] / Box2dTutorial.PPT;
            worldVertices[i].y = vertices[i * 2 + 1] / Box2dTutorial.PPT;
        }

        ChainShape chain = new ChainShape();
        chain.createChain(worldVertices);
        return chain;
    }







}
