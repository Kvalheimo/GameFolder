package blog.boomerangbeast.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import blog.boomerangbeast.BoomerangBeast;
import blog.boomerangbeast.HighScore.Save;

public class MenuScreen implements Screen {
    private static final int PADDING = 30;

    private BoomerangBeast parent;
    private Stage stage;
    private Skin skin;
    private TextureAtlas.AtlasRegion background;
    private TextureAtlas atlas;


    public MenuScreen(BoomerangBeast boomerangBeast){
        parent = boomerangBeast;
        stage = new Stage(new ScreenViewport());

        atlas = parent.assMan.manager.get("images/game.atlas");
        skin = parent.assMan.manager.get("skin/game/game.json");

        background = atlas.findRegion("background");

        //Load highscore list
        Save.load();

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        if (BoomerangBeast.DEBUG) {
           table.setDebug(true);
        }

        table.setFillParent(true);
        table.setBackground(new TiledDrawable(background));
        stage.addActor(table);

        // Create text buttons
        final TextButton multiplayer = new TextButton("Multiplayer", skin, "blue-menu");
        final TextButton newGame = new TextButton("Singleplayer", skin, "blue-menu");
        final TextButton preferences = new TextButton("Preferences", skin, "blue-menu");
        final TextButton highScore = new TextButton("High Score", skin, "blue-menu");
        final TextButton exit = new TextButton("Exit", skin, "blue-menu");


        // Add buttons to table
        table.add(multiplayer).fillX().uniformX();
        table.row().padTop(PADDING);
        table.add(newGame).fillX().uniformX();
        table.row().padTop(PADDING);
        table.add(preferences).fillX().uniformX();
        table.row().padTop(PADDING);
        table.add(highScore).fillX().uniformX();
        table.row().padTop(PADDING);
        table.add(exit).fillX().uniformX();


        // Create button listeners
        exit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
            Gdx.app.exit();
            }
        });

        multiplayer.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                if (parent.isOnline()){
                    parent.changeScreen(BoomerangBeast.CHARACTER_SELECTION, true, 1, 1);
                }
            }
        });

        newGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                parent.changeScreen(BoomerangBeast.CHARACTER_SELECTION);
            }
        });

        preferences.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                parent.changeScreen(BoomerangBeast.PREFERENCES);
            }
        });

        highScore.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                parent.changeScreen(BoomerangBeast.HIGHSCORE, 1);
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
