package com.platformer.game.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.platformer.game.PlatformerGame;

public class Controller {
    Viewport viewport;
    Stage stage;
    boolean upPressed, leftPressed, rightPressed;
    OrthographicCamera cam;

    public Controller(SpriteBatch sb){
        cam = new OrthographicCamera();
        viewport = new FitViewport(PlatformerGame.V_WIDTH, PlatformerGame.V_HEIGHT, cam);
        stage = new Stage(viewport, sb);
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.left().bottom();


        //Opp pil
        Image upImage = new Image(new Texture("flatDark25.png"));
        upImage.setSize(25,25);
        upImage.addListener(new InputListener(){

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                upPressed= true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                upPressed = false;

            }
        });

        //Venstre pil
        Image leftImage = new Image(new Texture("flatDark23.png"));
        leftImage.setSize(25,25);
        leftImage.addListener(new InputListener(){

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

        //høyre pil
        Image rightImage = new Image(new Texture("flatDark24.png"));
        rightImage.setSize(25,25);
        rightImage.addListener(new InputListener(){

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

        table.add();
        table.add(upImage).size(upImage.getWidth(), upImage.getHeight());
        table.add();
        table.row().pad(2, 2, 2, 2);
        table.add(leftImage).size(leftImage.getWidth(), leftImage.getHeight());
        table.add();
        table.add(rightImage).size(rightImage.getWidth(), rightImage.getHeight());
        table.row().padBottom(1);

        stage.addActor(table);

    }

    public void draw(){
        stage.draw();
    }

    public boolean isUpPressed() {
        return upPressed;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public void resize(int width, int height){
        viewport.update(width,height);
    }



    public void update(float dt){
        rightPressed = false;
    }
}
