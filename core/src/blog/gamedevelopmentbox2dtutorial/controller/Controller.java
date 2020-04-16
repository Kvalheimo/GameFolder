package blog.gamedevelopmentbox2dtutorial.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import blog.gamedevelopmentbox2dtutorial.Box2dTutorial;
import blog.gamedevelopmentbox2dtutorial.loader.B2dAssetManager;
import blog.gamedevelopmentbox2dtutorial.views.GameScreen;
import blog.gamedevelopmentbox2dtutorial.views.MainScreen;


public class Controller implements Disposable {
    private Viewport viewport;
    public Stage stage;
    boolean leftPressed, rightPressed, aPressed, bPressed, xPressed, yPressed;
    private OrthographicCamera camera;
    private Box2dTutorial parent;
    private TextureAtlas atlas;
    private Skin skin1, skin2, skin3, skin4;
    private GameScreen gs;

    public Controller(SpriteBatch sb, Box2dTutorial parent, GameScreen gameScreen){
        camera = new OrthographicCamera();
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        stage = new Stage(viewport, sb);

        gs = gameScreen;

        this.parent = parent;

        atlas = parent.assMan.manager.get("images/game.atlas");


        skin1 = parent.assMan.manager.get("skin/shade/uiskin.json");
        skin2 = parent.assMan.manager.get("skin/glassy/glassy-ui.json");
        skin3 = parent.assMan.manager.get("skin/clean/clean-crispy-ui.json");
        skin4 = parent.assMan.manager.get("skin/buttons/buttons.json");


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

        final Button rightImg = new Button(skin4, "right");

        rightImg.addListener(new InputListener() {

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                rightImg.setChecked(true);
                rightPressed = true;

            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                rightImg.setChecked(false);
                rightPressed = false;
            }




        });




        final Button leftImg = new Button(skin4, "left");

        leftImg.addListener(new InputListener() {

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                leftImg.setChecked(true);
                leftPressed = true;
            }


            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                leftImg.setChecked(false);
                leftPressed = false;
            }

        });

        final Button AImg = new Button(skin4, "green");


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





        final Button XImg = new Button(skin4, "red");


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




        final Button YImg = new Button(skin4, "blue");


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


        final TextButton pauseButton = new TextButton("Pause", skin2, "small");

        pauseButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                gs.pauseGame(true);
            }
        });


        Table table1 = new Table();

        table1.left().bottom();
        table1.add();
        table1.pad(0, 30, 20, 0);
        table1.add(leftImg).size(leftImg.getWidth(), leftImg.getHeight());
        table1.add().padLeft(30);
        table1.add(rightImg).size(rightImg.getWidth(), rightImg.getHeight());



        Table table2 = new Table();

        table2.right().bottom().padRight(30).padBottom(15);
        table2.add();
        table2.add(YImg).size(YImg.getWidth(), YImg.getHeight());
        table2.add();
        table2.row().pad(7, 7, 1, 7);
        table2.add(XImg).size(XImg.getWidth(), XImg.getHeight());
        table2.add();
        table2.add(AImg).size(AImg.getWidth(), AImg.getHeight());
        table2.row().padBottom(5);
        //table2.add();
        //table2.add(AImg).size(AImg.getWidth(), AImg.getHeight());
        //table2.add();

        Table table3 = new Table();

        table3.center().bottom();
        table3.padBottom(20);
        table3.add(pauseButton);

        //table1.debug();
        //table2.debug();
        //table3.debug();

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

}



