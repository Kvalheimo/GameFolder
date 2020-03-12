package blog.gamedevelopmentbox2dtutorial.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import blog.gamedevelopmentbox2dtutorial.Box2dTutorial;

public class MenuScreen implements Screen {

    private Box2dTutorial parent;
    private Stage stage;
    private Skin skin;
    private TextureAtlas.AtlasRegion background;
    private TextureAtlas atlas;

    public MenuScreen(Box2dTutorial box2dTutorial){
        parent = box2dTutorial;
        stage = new Stage(new ScreenViewport());

        loadAssets();



    }

    private void loadAssets(){
        parent.assMan.queueAddSkin();
        parent.assMan.manager.finishLoading();

        // Get images to display loading progress
        atlas = parent.assMan.manager.get("input/game/images/loading.atlas");
        skin = parent.assMan.manager.get("input/game/skin/glassy-ui.json");
        background = atlas.findRegion("flamebackground");

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        //Create a table that fills the screen. Everything else will go inside this table
        Table table = new Table();
        table.setFillParent(true);
        table.setBackground(new TiledDrawable(background));
        table.setDebug(true);
        stage.addActor(table);

        // Create text buttons
        final TextButton newGame = new TextButton("New Game", skin);
        final TextButton preferences = new TextButton("Preferences", skin);
        final TextButton exit = new TextButton("Exit", skin);

        // Add buttons to table
        table.add(newGame).fillX().uniformX();
        table.row().pad(10,0,10,0);
        table.add(preferences).fillX().uniformX();
        table.row();
        table.add(exit).fillX().uniformX();


        // Create button listeners
        exit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
            Gdx.app.exit();
            }
        });

        newGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                parent.changeScreen(Box2dTutorial.APPLICATION);
            }
        });

        preferences.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                parent.changeScreen(Box2dTutorial.PREFERENCES);
            }
        });



    }

    @Override
    public void render(float dt) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Tell our stage to do actions and draw itself
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        // Change the stage's viewport when the screen size is changed
        stage.getViewport().update(width, height, true);

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
        stage.dispose();
    }


}