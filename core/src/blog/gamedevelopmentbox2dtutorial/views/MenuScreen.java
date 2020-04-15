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
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import javax.swing.Box;

import blog.gamedevelopmentbox2dtutorial.Box2dTutorial;
import blog.gamedevelopmentbox2dtutorial.HighScore.Save;

public class MenuScreen implements Screen {

    private Box2dTutorial parent;
    private Stage stage;
    private Skin skin1, skin2, skin3;
    private TextureAtlas.AtlasRegion background;
    private TextureAtlas atlas;


    public MenuScreen(Box2dTutorial box2dTutorial){
        parent = box2dTutorial;
        stage = new Stage(new ScreenViewport());

        atlas = parent.assMan.manager.get("images/loading.atlas");
        skin1 = parent.assMan.manager.get("skin/shade/uiskin.json");
        skin2 = parent.assMan.manager.get("skin/glassy/glassy-ui.json");
        skin3 = parent.assMan.manager.get("skin/clean/clean-crispy-ui.json");
        background = atlas.findRegion("flamebackground");


    }

    @Override
    public void show() {
        //Load highscore list
//        for (int i = 1; i < 7; i++){
            Save.load(1);
//        }

        Gdx.input.setInputProcessor(stage);

        //Create a table that fills the screen. Everything else will go inside this table
        Table table = new Table();
        table.setFillParent(true);
        table.setBackground(new TiledDrawable(background));
        table.setDebug(true);
        stage.addActor(table);

        // Create text buttons
        final TextButton newGame = new TextButton("New Game", skin2);
        final TextButton preferences = new TextButton("Preferences", skin2);
        final TextButton highScore = new TextButton("High Score", skin2);
        final TextButton exit = new TextButton("Exit", skin2);



        // Add buttons to table
        table.add(newGame).fillX().uniformX();
        table.row().padTop(10);
        table.add(preferences).fillX().uniformX();
        table.row().padTop(10);
        table.add(highScore).fillX().uniformX();
        table.row().padTop(10);
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
                parent.changeScreen(Box2dTutorial.CHARACTER_SELECTION);
            }
        });

        preferences.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                parent.changeScreen(Box2dTutorial.PREFERENCES);
            }
        });

        highScore.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                parent.changeScreen(Box2dTutorial.HIGHSCORE);
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
