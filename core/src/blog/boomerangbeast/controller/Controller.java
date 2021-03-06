package blog.boomerangbeast.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import blog.boomerangbeast.BoomerangBeast;
import blog.boomerangbeast.screens.GameScreen;


public class Controller implements Disposable {
    public static final float deadzoneRadius = 5f;

    private Viewport viewport;
    public Stage stage;
    boolean leftPressed, rightPressed, aPressed, bPressed, xPressed, yPressed;
    private OrthographicCamera camera;
    private BoomerangBeast parent;
    private TextureAtlas atlas;
    private Skin skin;

    private GameScreen gs;
    private Vector2 bulletDirection;
    private float velScale;


    public Controller(SpriteBatch sb, BoomerangBeast parent, GameScreen gameScreen){
        camera = new OrthographicCamera();
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        stage = new Stage(viewport, sb);

        bulletDirection = new Vector2(0.0f, 0.0f);
        velScale = 1.0f;

        gs = gameScreen;

        this.parent = parent;

        atlas = parent.assMan.manager.get("images/game.atlas");
        skin = parent.assMan.manager.get("skin/game/game.json");



        stage.addListener(new InputListener(){

            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                switch(keycode){
                    case Input.Keys.UP:
                        aPressed = true;
                        break;
                    case Input.Keys.LEFT:
                        leftPressed = true;
                        break;
                    case Input.Keys.RIGHT:
                        rightPressed = true;
                        break;
                    case Input.Keys.B:
                        bPressed = true;
                        break;
                    case Input.Keys.X:
                        xPressed = true;
                        break;
                    case Input.Keys.SPACE:
                        yPressed = true;
                        break;
                }
                return true;
            }

            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                switch(keycode){
                    case Input.Keys.UP:
                        aPressed = false;
                        break;
                    case Input.Keys.LEFT:
                        leftPressed = false;
                        break;
                    case Input.Keys.RIGHT:
                        rightPressed = false;
                        break;
                    case Input.Keys.B:
                        bPressed = false;
                        break;
                    case Input.Keys.X: //Shooting
                        xPressed = false;
                        break;
                    case Input.Keys.SPACE: //Superspeed
                        yPressed = false;
                        break;
                }
                return true;
            }
        });


        final Touchpad touchpad = new Touchpad(deadzoneRadius, skin, "blue1");

        touchpad.addListener(new ChangeListener() {

            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // This is run when anything is changed on this actor.
                float deltaX = ((Touchpad) actor).getKnobPercentX();
                float deltaY = ((Touchpad) actor).getKnobPercentY();
                velScale = Math.abs(deltaX);

                if(deltaX > 0){
                    rightPressed = true;
                    leftPressed = false;
                    bulletDirection.set(deltaX, deltaY);
                }
                else if(deltaX < 0){
                    leftPressed = true;
                    rightPressed = false;
                    bulletDirection.set(deltaX, deltaY);
                }

                else{
                    leftPressed = false;
                    rightPressed = false;
                    bulletDirection.set(0.0f, 0.0f);
                    velScale = 1.0f;
                }



            }
        });


        final Button AImg = new Button(skin, "green2");


        // Create button listeners
        AImg.addListener(new InputListener(){


            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                aPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                aPressed = false;
            }
        });





        final Button XImg = new Button(skin, "red2");


        // Create button listeners
        XImg.addListener(new InputListener(){


            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                xPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                xPressed = false;
            }
        });




        final Button YImg = new Button(skin, "yellow2");


        // Create button listeners
        YImg.addListener(new InputListener(){


            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                yPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                yPressed = false;
            }
        });


        final TextButton pauseButton = new TextButton("Pause", skin, "blue-small");

        pauseButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                gs.pauseGame(true);
            }
        });


        Table table1 = new Table();
        Table table2 = new Table();
        Table table3 = new Table();

        if (BoomerangBeast.DEBUG) {
            table1.setDebug(true);
            table2.setDebug(true);
            table3.setDebug(true);
        }

        table1.left().bottom();
        table1.add();
        table1.pad(0, 80, 20, 0);

        table1.add(touchpad);


        table2.right().bottom().padRight(40).padBottom(15);
        table2.add();
        table2.add(YImg);
        table2.add();
        table2.row().pad(15, 10, 0, 10);
        table2.add(XImg);
        table2.add();
        table2.add(AImg).size(AImg.getWidth(), AImg.getHeight());
        table2.row().padBottom(5);

        table3.center().bottom();
        table3.padBottom(20);
        table3.add(pauseButton);

        if (BoomerangBeast.DEBUG) {
            table1.debug();
            table2.debug();
            table3.debug();
        }

        Stack stack = new Stack(table2, table1, table3);
        stack.setFillParent(true);

        stage.addActor(stack);

    }



    public void draw(){
        stage.act();
        stage.draw();
    }



    public boolean isLeftPressed() {
        return leftPressed;
    }

    public boolean isBPressed() {
        return bPressed;
    }

    public boolean isAPressed() {
        return aPressed;
    }


    public boolean isRightPressed() {
        return rightPressed;
    }
    public boolean isXPressed() {
        return xPressed;
    }

    public boolean isYPressed() {
        return yPressed;
    }

    public void resize(int width, int height){
        viewport.update(width, height);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public Stage getStage(){
        return stage;
    }


    //Function used by playerControlSystem to make it impossible to hold jump button
    public void setAPressed(boolean aPressed){
        this.aPressed = aPressed;
    }

    //Function used by playerControlSystem to make it impossible to hold gun button
    public void setXPressed(boolean xPressed){
        this.xPressed = xPressed;
    }

    public Vector2 getBulletDirection(){
        return bulletDirection;
    }

    public float getVelScale(){
        return velScale;
    }

}



