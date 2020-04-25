package blog.gamedevelopmentbox2dtutorial.Factory;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

import blog.gamedevelopmentbox2dtutorial.Box2dTutorial;

public class BodyFactory {
    public static final int STEEL = 0;
    public  static final int WOOD = 1;
    public static final int RUBBER = 2;
    public  static final int STONE = 3;
    public  static final int WATER = 4;
    public static final int SYRUP = 5;
    private final float DEGTORAD = 0.0174533f;


    private World world;
    private static BodyFactory thisInstance;

    private BodyFactory(World world){
        this.world = world;
    }


    public static BodyFactory getInstance(World world){
        if (thisInstance == null){
            thisInstance = new BodyFactory(world);
        }else{
            thisInstance.world = world;
        }
        return thisInstance;
    }



    public FixtureDef makeFixture(int material, Shape shape){
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;

        switch(material){
            case 0:
                fixtureDef.density = 30.0f;
                fixtureDef.friction = 0.0f;
                fixtureDef.restitution = 0.0f;
                break;
            case 1:
                fixtureDef.density = 10f;
                fixtureDef.friction = 1f;
                fixtureDef.restitution = 0.0f;
                break;
            case 2:
                fixtureDef.density = 20.0f;
                fixtureDef.friction = 1.0f;
                fixtureDef.restitution = 0.0f;
                break;
            case 3:
                fixtureDef.density = 1.0f;
                fixtureDef.friction = 0.0f;
                fixtureDef.restitution = 0.0f;
                break;
            case 4:
                fixtureDef.density = 20f;
                fixtureDef.friction = 0.0f;
                fixtureDef.restitution = 0.0f;
                break;

            case 5:
                fixtureDef.density = 1f;
                fixtureDef.friction = 100f;
                fixtureDef.restitution = 0f;
                break;
            default:
                fixtureDef.density = 7f;
                fixtureDef.friction = 0.0f;
                fixtureDef.restitution = 0.0f;
                break;
        }
        return fixtureDef;
    }

    public Body makeCirclePolyBody(float posx, float posy, float radius, int material, BodyDef.BodyType bodyType){
        return makeCirclePolyBody( posx,  posy,  radius,  material,  bodyType,  false);
    }

    public Body makeCirclePolyBody(float posx, float posy, float radius, int material){
        return makeCirclePolyBody( posx,  posy,  radius,  material,  BodyDef.BodyType.DynamicBody,  false);
    }

    public Body makeCirclePolyBody(float posX, float posY, float radius, int material, BodyDef.BodyType bodyType, boolean fixedRotation){

        // Create definition
        BodyDef boxBodyDef = new BodyDef();
        boxBodyDef.type = bodyType;
        boxBodyDef.position.x = posX;
        boxBodyDef.position.y = posY;
        boxBodyDef.fixedRotation = fixedRotation;

        //Create body to attach said definition
        Body boxBody = world.createBody(boxBodyDef);

        //boxBody.setAngularDamping(1f);
        boxBody.setLinearDamping(2f);

        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(radius);
        boxBody.createFixture(makeFixture(material, circleShape));
        circleShape.dispose();
        return boxBody;
    }

    public Body makeBoxPolyBody(float posx, float posy, float width, float height,int material, BodyDef.BodyType bodyType){
        return makeBoxPolyBody(posx, posy, width, height, material, bodyType, false);
    }

    public Body makeBoxPolyBody(float posx, float posy, float width, float height,int material, BodyDef.BodyType bodyType, boolean fixedRotation){

        // create a definition
        BodyDef boxBodyDef = new BodyDef();
        boxBodyDef.type = bodyType;
        boxBodyDef.position.x = posx;
        boxBodyDef.position.y = posy;
        boxBodyDef.fixedRotation = fixedRotation;

        //create the body to attach said definition
        Body boxBody = world.createBody(boxBodyDef);
        PolygonShape polyShape = new PolygonShape();
        polyShape.setAsBox(width/2, height/2);
        boxBody.createFixture(makeFixture(material,polyShape));
        polyShape.dispose();

        return boxBody;
    }

    public Body makePolygonShapebody(Vector2[] verices, float posX, float posY, int material, BodyDef.BodyType bodyType){
        BodyDef boxBodyDef = new BodyDef();
        boxBodyDef.type = bodyType;
        boxBodyDef.position.x = posX;
        boxBodyDef.position.y = posY;

        //create the body to attach said definition
        Body boxBody = world.createBody(boxBodyDef);
        PolygonShape polygon = new PolygonShape();
        polygon.set(verices);
        boxBody.createFixture(makeFixture(material, polygon));
        polygon.dispose();

        return boxBody;
    }

    public void makeConeSensor(Body body, float size){

        FixtureDef fixtureDef = new FixtureDef();
        //fixtureDef.isSensor = true;

        PolygonShape polygon = new PolygonShape();

        float radius = size;
        Vector2[] vertices = new Vector2[5];
        vertices[0] = new Vector2(0,0);

        for (int i = 2; i < 6; i++) {
            float angle = (float) (i  / 6.0 * 145 * DEGTORAD); // convert degrees to radians
            vertices[i-1] = new Vector2( radius * ((float)Math.cos(angle)), radius * ((float)Math.sin(angle)));
        }
        polygon.set(vertices);
        fixtureDef.shape = polygon;
        body.createFixture(fixtureDef);
        polygon.dispose();
    }


    public void makeAllFixturesSensors(Body body) {
        for (Fixture fix : body.getFixtureList()) {
            fix.setSensor(true);
        }
    }

}
