package blog.boomerangbeast.screens.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import blog.boomerangbeast.BoomerangBeast;
import blog.boomerangbeast.DFUtils;
import blog.boomerangbeast.highscore.HighscoreSync;

public class EndScreen implements Screen {

    private BoomerangBeast parent;
    private Skin skin;
    private Stage stage;
    private TextureAtlas atlas;
    private AtlasRegion background;
    private boolean newHighScore;
    private TextField txtfName;
    private int level;

    public EndScreen(BoomerangBeast boomerangBeast, int level){
        this.level = level;
        parent = boomerangBeast;

    }

    @Override
    public void show() {
        // get skin
        skin = parent.assMan.manager.get("skin/game/game.json");

        atlas = parent.assMan.manager.get("images/game.atlas");
        background = atlas.findRegion("background");

        // create buttons
        TextButton menuButton = new TextButton("Back", skin, "blue-small");
        TextButton saveButton = new TextButton("HighscoreSync", skin, "blue-small");



        // create stage and set it as input processor
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // create table to layout iitems we will add
        Table table = new Table();

        if(BoomerangBeast.DEBUG){
            table.setDebug(true);
        }


        table.setFillParent(true);
        table.setBackground(new TiledDrawable(background));

        table.top();
        table.padTop(Gdx.graphics.getHeight()/4);

        //Get boolean for check if it is new highscore
        newHighScore = HighscoreSync.hsd.get(level).isHighScore(HighscoreSync.hsd.get(level).getTentativeScore());

        if (newHighScore){

            Label scoreLabel = new Label("NEW HIGH SCORE! ", skin, "highscore");
            Label score = new Label(String.valueOf(HighscoreSync.hsd.get(level).getTentativeScore()), skin, "big");
            Label nameLabel = new Label("ENTER YOUR NAME:", skin, "highscore");

            table.add(scoreLabel).colspan(2);
            table.row().padTop(20);
            table.add(score).colspan(2);
            table.row().padTop(20);
            table.add(nameLabel).colspan(2);
            table.row().padTop(20);

            txtfName = new TextField("", skin);
            txtfName.setSize(300, 40);
            txtfName.setMaxLength(12);

            table.add(txtfName).colspan(2);

        }else{
            Label scoreLabel = new Label("SCORE "+ HighscoreSync.hsd.get(level).getTentativeScore(), skin, "highscore");
            Label score = new Label(String.valueOf(HighscoreSync.hsd.get(level).getTentativeScore()), skin, "big");


            table.add(scoreLabel).colspan(2);
            table.row().padTop(20);
            table.add(score).colspan(2);
        }


        table.row().padTop(50);

        if(newHighScore){
            table.add(menuButton).padRight(30);
            table.add(saveButton).padLeft(30);
        }else{
            table.add(menuButton);
        }

        stage.addActor(table);

        //Create button listeners
        menuButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(BoomerangBeast.MENU);
            }
        });

        saveButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                DFUtils.log("HighScore saved!");
                if (newHighScore) {
                    String name = txtfName.getText();
                    if (name == ""){
                        name = "Noname";
                    }
                    HighscoreSync.hsd.get(level).addHighScore(HighscoreSync.hsd.get(level).getTentativeScore(),
                                                     (name),
                                        false);
                    HighscoreSync.publish();
                }
                parent.changeScreen(BoomerangBeast.HIGHSCORE, level);
            }
        });

    }

    @Override
    public void render(float delta) {
        // clear the screen ready for next set of images to be drawn
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }


    @Override
    public void resize(int width, int height) {
        // change the stage's viewport when teh screen size is changed
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {}


}

