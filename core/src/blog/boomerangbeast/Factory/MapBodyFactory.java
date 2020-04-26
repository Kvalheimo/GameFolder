package blog.boomerangbeast.Factory;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import blog.boomerangbeast.BoomerangBeast;


public class MapBodyFactory {

    private World world;
    private static MapBodyFactory thisInstance;
    private static BodyFactory bodyFactory;


   private MapBodyFactory(World world){
       this.world = world;
       bodyFactory = BodyFactory.getInstance(world);

   }

    public static MapBodyFactory getInstance(World world){
        if (thisInstance == null){
            thisInstance = new MapBodyFactory(world);
        }else{
            thisInstance.world = world;
        }
        return thisInstance;
    }


    public static Array<Body> buildShapes(TiledMap map, World world, String layer, BodyDef.BodyType bodyType, int material) {

       MapObjects objects = new MapObjects();
       try {
           objects = map.getLayers().get(layer).getObjects();

       } catch (Exception e){

       }
        Array<Body> bodies = new Array<Body>();

        for (MapObject object : objects) {
            Vector2 position = new Vector2(0, 0);


            if (object instanceof TextureMapObject) {
                continue;
            }

            Shape shape;


            if (object instanceof RectangleMapObject) {
                shape = getRectangle((RectangleMapObject) object);
                Rectangle rectangle = ((RectangleMapObject) object).getRectangle();

                BodyDef bodyDef = new BodyDef();
                bodyDef.type = bodyType;
                Body body = world.createBody(bodyDef);

                FixtureDef fixtureDef = bodyFactory.makeFixture(material, shape);
                body.createFixture(fixtureDef);


                //setting the position of the body's origin. In this case with zero rotation
                body.setTransform(rectangle.getCenter(new Vector2()).scl(1/ BoomerangBeast.PPT), 0);

                bodies.add(body);

            } else if (object instanceof PolygonMapObject) {
                shape = getPolygon((PolygonMapObject) object);

                BodyDef bodyDef = new BodyDef();
                bodyDef.type = bodyType;
                Body body = world.createBody(bodyDef);

                FixtureDef fixtureDef = bodyFactory.makeFixture(material, shape);
                body.createFixture(fixtureDef);

                bodies.add(body);
            } else if (object instanceof PolylineMapObject) {
                shape = getPolyline((PolylineMapObject) object);

                BodyDef bodyDef = new BodyDef();
                bodyDef.type = bodyType;
                Body body = world.createBody(bodyDef);

                FixtureDef fixtureDef = bodyFactory.makeFixture(material, shape);
                body.createFixture(fixtureDef);


                bodies.add(body);
            } else if (object instanceof EllipseMapObject) {

                shape = getCircle((EllipseMapObject) object);
                Ellipse ellipse = ((EllipseMapObject) object).getEllipse();

                position.set((ellipse.x + ellipse.width/2) / BoomerangBeast.PPM, (ellipse.y + ellipse.width/2)/ BoomerangBeast.PPM);


                BodyDef bodyDef = new BodyDef();
                bodyDef.type = bodyType;
                bodyDef.position.set(position);
                Body body = world.createBody(bodyDef);

                FixtureDef fixtureDef = bodyFactory.makeFixture(material, shape);
                body.createFixture(fixtureDef);

                bodies.add(body);
            }
        }
        return bodies;
    }


        private static PolygonShape getRectangle (RectangleMapObject rectangleObject){
            Rectangle rectangle = rectangleObject.getRectangle();
            PolygonShape polygon = new PolygonShape();

            polygon.setAsBox(rectangle.getWidth() * 0.5f / BoomerangBeast.PPT,
                    rectangle.getHeight() * 0.5f / BoomerangBeast.PPT);
            return polygon;
        }


        private static PolygonShape getPolygon (PolygonMapObject polygonObject){
            PolygonShape polygon = new PolygonShape();
            float[] vertices = polygonObject.getPolygon().getTransformedVertices();

            float[] worldVertices = new float[vertices.length];

            for (int i = 0; i < vertices.length; ++i) {
                worldVertices[i] = vertices[i] / BoomerangBeast.PPT;
            }

            polygon.set(worldVertices);
            return polygon;
        }


        private static CircleShape getCircle (EllipseMapObject ellipseMapObject){
            Ellipse ellipse = ellipseMapObject.getEllipse();
            CircleShape circleShape = new CircleShape();

            circleShape.setRadius((ellipse.width/2f)/ BoomerangBeast.PPT);

            return circleShape;
        }


        private static ChainShape getPolyline (PolylineMapObject polylineObject){
            float[] vertices = polylineObject.getPolyline().getTransformedVertices();
            Vector2[] worldVertices = new Vector2[vertices.length / 2];

            for (int i = 0; i < worldVertices.length; ++i) {
                worldVertices[i] = new Vector2();
                worldVertices[i].x = vertices[i * 2] / BoomerangBeast.PPT;
                worldVertices[i].y = vertices[i * 2 + 1] / BoomerangBeast.PPT;
            }

            ChainShape chain = new ChainShape();
            chain.createChain(worldVertices);
            return chain;
        }
    }





