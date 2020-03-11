package com.platformer.game.Screens.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.platformer.game.PlatformerGame;
import com.platformer.game.Scenes.Controller;


public class SingleplayerGameScreen implements Screen{
    private PlatformerGame game;
    private Viewport gamePort;
    private OrthographicCamera gamecam;
    private Controller controller;


    public SingleplayerGameScreen(PlatformerGame game) {
        this.game = game;

        //create cam used to follow player through cam world
        gamecam = new OrthographicCamera();

        //create a FitViewport to maintain virtual aspect ratio despite screen size
        gamePort = new FitViewport(PlatformerGame.V_WIDTH , PlatformerGame.V_HEIGHT, gamecam);

        controller = new Controller(game.batch);



    }



    public void update(float dt){
        handleInput(dt);
        gamecam.update();
        controller.update(dt);
    }


    public void handleInput(float dt){
            if((Gdx.input.isKeyPressed(Input.Keys.RIGHT) | controller.isRightPressed())) {
                Gdx.app.log("rightkey", "rightkey is pressed");
            }



    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.batch.draw(new Texture("badlogic.jpg"),0,0);
        game.batch.end();
        controller.draw();


    }


    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }


}
