package blog.boomerangbeast.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import blog.boomerangbeast.BoomerangBeast;
import blog.boomerangbeast.HighScore.Save;

public class HighScoreScreen implements Screen {
    private long[] highScores;
    private String[] names;
    private BoomerangBeast parent;
    private Skin skin;
    private TextureAtlas atlas;
    private Stage stage;
    private TextureAtlas.AtlasRegion background;
    private int levelSelected;
    private Table outerTable, innerTable, highscoreTable;


    public HighScoreScreen(BoomerangBeast parent, int level){
        this.parent = parent;
        stage = new Stage(new ScreenViewport());
        levelSelected = level;
        highScores = Save.hsd.get(levelSelected).getHighScores();

        atlas = parent.assMan.manager.get("images/game.atlas");
        skin = parent.assMan.manager.get("skin/game/game.json");
        background = atlas.findRegion("background");

        Save.load();

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        highScores = Save.hsd.get(levelSelected).getHighScores();
        names = Save.hsd.get(levelSelected).getNames();


        // Create text buttons, labels etc.
        final TextButton l1 = new TextButton("Homecoming", skin, "toggle2");
        final TextButton l2 = new TextButton("Enrique's World", skin, "toggle2");
        final TextButton l3 = new TextButton("Mountain Jam", skin, "toggle2");
        final TextButton l4 = new TextButton("Platform Plooza", skin, "toggle2");

        switch (levelSelected){
            case 1:
                l1.setChecked(true);
                break;
            case 2:
                l2.setChecked(true);
                break;
            case 3:
                l3.setChecked(true);
                break;
            case 4:
                l4.setChecked(true);
                break;
        }

        ButtonGroup buttonGroup = new ButtonGroup(l1, l2, l3, l4);
        buttonGroup.setMaxCheckCount(1);

        Label headerLabel = new Label("HIGH SCORE", skin, "big");

        final TextButton backButton = new TextButton("Back", skin, "blue-small");


        innerTable = new Table();
        outerTable = new Table();
        highscoreTable = new Table();


        if (BoomerangBeast.DEBUG){
            innerTable.setDebug(true);
            outerTable.setDebug(true);
            highscoreTable.setDebug(true);
        }

        outerTable.setFillParent(true);
        highscoreTable.setFillParent(true);

        highscoreTable.setBackground(new TiledDrawable(background));

        highscoreTable.clear();
        highscoreTable.center().padLeft(Gdx.graphics.getWidth()/8);

        //Add highscores to table
        for (int i = 0; i < highScores.length; i++) {
            Label num = new Label(String.format("%2d.", i + 1), skin, "highscore");
            Label score = new Label(String.format("%7s", highScores[i]), skin, "highscore");
            Label name = new Label(String.format("%s", names[i].split("-")[0]), skin, "highscore");

            highscoreTable.add(num).padRight(200).right();
            highscoreTable.add(name).padRight(90).left();
            highscoreTable.add(score).left();

            highscoreTable.row();
        }

        innerTable.add(l1).padTop(30).fillX().expandX();
        innerTable.row();
        innerTable.add(l2).padTop(30).fillX().expandX();
        innerTable.row();
        innerTable.add(l3).padTop(30).fillX().expandX();
        innerTable.row();
        innerTable.add(l4).padTop(30).padBottom(30).fillX().expandX();


        ScrollPane scrollPane = new ScrollPane(innerTable, skin);

        outerTable.add(headerLabel).center().colspan(3).padTop(30);
        outerTable.row().expandX();
        outerTable.add(scrollPane).fillY().expandY().left().pad(Gdx.graphics.getBackBufferHeight()/6, Gdx.graphics.getWidth()/8, Gdx.graphics.getBackBufferHeight()/6, 0);

        outerTable.row().expandX();
        outerTable.add(backButton).pad(0,0,50,0);
        stage.addActor(highscoreTable);
        stage.addActor(outerTable);


        // Create button listeners
        l1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                levelSelected = 1;
            }
        });

        l2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                levelSelected = 2;            }
        });

        l3.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                levelSelected = 3;            }
        });

        l4.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                levelSelected = 4;            }
        });

        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                parent.changeScreen(BoomerangBeast.MENU);

            }
        });





    }



    @Override
    public void render(float delta) {
        // clear the screen ready for next set of images to be drawn
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update();

        stage.act();
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        // Change the stage's viewport when the screen size is changed
        stage.getViewport().update(width, height, true);
    }

    public void update(){

        //highscorePane.clear();
        highscoreTable.clear();

        highScores = Save.hsd.get(levelSelected).getHighScores();
        names = Save.hsd.get(levelSelected).getNames();


        //Add highscores to table
        for (int i = 0; i < highScores.length; i++) {
            Label num = new Label(String.format("%2d.", i + 1), skin, "highscore");
            Label score = new Label(String.format("%7s", highScores[i]), skin, "highscore");
            Label name = new Label(String.format("%s", names[i].split("-")[0]), skin, "highscore");

            highscoreTable.add(num).padRight(200).right();
            highscoreTable.add(name).padRight(90).left();
            highscoreTable.add(score).left();

            highscoreTable.row();
        }

        //highscorePane.setActor(innerHighScoreTable);

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



