package blog.gamedevelopmentbox2dtutorial.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import blog.gamedevelopmentbox2dtutorial.Box2dTutorial;
import blog.gamedevelopmentbox2dtutorial.HighScore.Save;

public class HighScoreScreen implements Screen {
    private long[] highScores;
    private String[] names;
    private Box2dTutorial parent;
    private Skin skin;
    private TextureAtlas atlas;
    private Stage stage;
    private TextureAtlas.AtlasRegion background;
    private int levelSelected;
    private Table outerTable, innerTable, highScoreTable;
    private BitmapFont highscoreFont, titleFont;
    private Label.LabelStyle labelStyle1, labelStyle2;


    public HighScoreScreen(Box2dTutorial parent){
        this.parent = parent;
        stage = new Stage(new ScreenViewport());
        levelSelected = 1;
        highScores = Save.hsd.get(levelSelected).getHighScores();

        atlas = parent.assMan.manager.get("images/game.atlas");
        skin = parent.assMan.manager.get("skin/game/game.json");


        background = atlas.findRegion("background");

        Save.load();

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        levelSelected = 1;

        highScores = Save.hsd.get(levelSelected).getHighScores();
        names = Save.hsd.get(levelSelected).getNames();


        // Create text buttons, labels etc.
        final TextButton l1 = new TextButton("1", skin, "toggle");
        final TextButton l2 = new TextButton("2", skin, "toggle");
        final TextButton l3 = new TextButton("3", skin, "toggle");
        final TextButton l4 = new TextButton("4", skin, "toggle");
        final TextButton l5 = new TextButton("5", skin, "toggle");
        final TextButton l6 = new TextButton("6", skin, "toggle");

        l1.setChecked(true);

        ButtonGroup buttonGroup = new ButtonGroup(l1, l2, l3, l4, l5, l6);
        buttonGroup.setMaxCheckCount(1);

        Label headerLabel = new Label("High Score", skin, "big");

        final TextButton backButton = new TextButton("Back", skin, "blue-small");


        innerTable = new Table();
        outerTable = new Table();
        highScoreTable = new Table();

        outerTable.setFillParent(true);
        highScoreTable.setFillParent(true);
        highScoreTable.setBackground(new TiledDrawable(background));

        highScoreTable.clear();

        innerTable.debug();
        outerTable.debug();
        highScoreTable.debug();

        highScoreTable.center().padLeft(Gdx.graphics.getWidth()/8);

        //Add highscores to table
        for (int i = 0; i < highScores.length; i++) {
            Label num = new Label(String.format("%2d.", i + 1), skin);
            Label score = new Label(String.format("%7s", highScores[i]), skin);
            Label name = new Label(String.format("%s", names[i]), skin);

            highScoreTable.add(num).padRight(200).right();
            highScoreTable.add(name).padRight(90).left();
            highScoreTable.add(score).left();

            highScoreTable.row();
        }


        innerTable.add(l1).padTop(30).fillX().expandX();
        innerTable.row();
        innerTable.add(l2).padTop(30).fillX().expandX();
        innerTable.row();
        innerTable.add(l3).padTop(30).fillX().expandX();
        innerTable.row();
        innerTable.add(l4).padTop(30).fillX().expandX();
        innerTable.row();
        innerTable.add(l5).padTop(30).fillX().expandX();
        innerTable.row();
        innerTable.add(l6).padTop(30).padBottom(30).fillX().expandX();

        ScrollPane scrollPane = new ScrollPane(innerTable, skin);

        outerTable.add(headerLabel).center().padTop(10).colspan(3);
        outerTable.row().expandX();
        outerTable.add(scrollPane).fillY().expandY().padTop(40).left().padLeft(Gdx.graphics.getWidth()/8);

        outerTable.row().expandX();
        outerTable.add(backButton).pad(20,0,10,0);

        stage.addActor(highScoreTable);
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

        l5.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                levelSelected = 5;            }
        });

        l6.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                levelSelected = 6;            }
        });


        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                parent.changeScreen(Box2dTutorial.MENU);

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

        highScoreTable.clear();

        highScores = Save.hsd.get(levelSelected).getHighScores();
        names = Save.hsd.get(levelSelected).getNames();

        //Add highscores to table
        for (int i = 0; i < highScores.length; i++) {
            Label num = new Label(String.format("%2d.", i + 1), skin);
            Label score = new Label(String.format("%7s", highScores[i]), skin);
            Label name = new Label(String.format("%s", names[i]), skin);

            highScoreTable.add(num).padRight(200).right();
            highScoreTable.add(name).padRight(90).left();
            highScoreTable.add(score).left();

            highScoreTable.row();
        }


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



