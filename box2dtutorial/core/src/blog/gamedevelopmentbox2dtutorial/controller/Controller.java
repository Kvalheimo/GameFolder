package blog.gamedevelopmentbox2dtutorial.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import blog.gamedevelopmentbox2dtutorial.loader.B2dAssetManager;


public class Controller implements Disposable {
    private Viewport viewport;
    public Stage stage;
    boolean leftPressed, rightPressed, aPressed, bPressed, xPressed, yPressed;
    private OrthographicCamera camera;
    private B2dAssetManager assMan;
    private TextureAtlas atlas;

    public Controller(SpriteBatch sb, B2dAssetManager assMan){
        camera = new OrthographicCamera();
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        stage = new Stage(viewport, sb);
        this.assMan = assMan;

        atlas = assMan.manager.get("images/game.atlas");

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


        Image rightImg = new Image(atlas.findRegion("right"));
        rightImg.setSize(50, 50);
        rightImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                rightPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                rightPressed = false;
            }
        });

        Image leftImg = new Image(atlas.findRegion("left"));
        leftImg.setSize(50, 50);
        leftImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                leftPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                leftPressed = false;
            }
        });


        Image BImg = new Image(atlas.findRegion("B"));
        BImg.setSize(35,35);
        BImg.addListener(new InputListener(){

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                bPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                bPressed = false;
            }
        });

        Image AImg = new Image(atlas.findRegion("A"));
        AImg.setSize(35, 35);
        AImg.addListener(new InputListener() {

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

        Image XImg = new Image(atlas.findRegion("X"));
        XImg.setSize(35, 35);
        XImg.addListener(new InputListener() {

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

        Image YImg = new Image(atlas.findRegion("Y"));
        YImg.setSize(35, 35);
        YImg.addListener(new InputListener() {

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

        Table table1 = new Table();

        table1.left().bottom();
        table1.add();
        table1.pad(0, 30, 30, 0);
        table1.add(leftImg).size(leftImg.getWidth(), leftImg.getHeight());
        table1.add().padLeft(30);
        table1.add(rightImg).size(rightImg.getWidth(), rightImg.getHeight());



        Table table2 = new Table();

        table2.right().bottom().padRight(30);
        table2.add();
        table2.add(YImg).size(YImg.getWidth(), YImg.getHeight());
        table2.add();
        table2.row().pad(1, 1, 1, 1);
        table2.add(XImg).size(XImg.getWidth(), XImg.getHeight());
        table2.add();
        table2.add(BImg).size(BImg.getWidth(), BImg.getHeight());
        table2.row().padBottom(5);
        table2.add();
        table2.add(AImg).size(AImg.getWidth(), AImg.getHeight());
        table2.add();

        //table1.debug();
        //table2.debug();

        Stack stack = new Stack(table2, table1);
        stack.setFillParent(true);

        stage.addActor(stack);


    }



    public void draw(){
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

}



