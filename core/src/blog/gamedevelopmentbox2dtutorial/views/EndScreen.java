package blog.gamedevelopmentbox2dtutorial.views;

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
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import blog.gamedevelopmentbox2dtutorial.Box2dTutorial;
import blog.gamedevelopmentbox2dtutorial.DFUtils;
import blog.gamedevelopmentbox2dtutorial.HighScore.Save;

public class EndScreen implements Screen {

    private Box2dTutorial parent;
    private Skin skin;
    private Stage stage;
    private TextureAtlas atlas;
    private AtlasRegion background;
    private boolean newHighScore;
    private TextField txtfName;
    private int level;

    public EndScreen(Box2dTutorial box2dTutorial, int level){
        this.level = level;
        parent = box2dTutorial;

    }

    @Override
    public void show() {
        // get skin
        skin = parent.assMan.manager.get("skin/game/game.json");

        atlas = parent.assMan.manager.get("images/game.atlas");
        background = atlas.findRegion("background");

        // create buttons
        TextButton menuButton = new TextButton("Back", skin, "blue-small");
        TextButton saveButton = new TextButton("Save", skin, "blue-small");



        // create stage and set it as input processor
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // create table to layout iitems we will add
        Table table = new Table();

        if(Box2dTutorial.DEBUG){
            table.setDebug(true);
        }


        table.setFillParent(true);
        table.setBackground(new TiledDrawable(background));

        //Get boolean for check if it is new highscore
        newHighScore = Save.hsd.get(level).isHighScore(Save.hsd.get(level).getTentativeScore());

        if (newHighScore){

            Label scoreLabel = new Label("NEW HIGH SCORE! ", skin, "highscore");
            Label score = new Label(String.valueOf(Save.hsd.get(level).getTentativeScore()), skin, "big");
            Label nameLabel = new Label("ENTER YOUR NAME:", skin, "highscore");

            table.add(scoreLabel).colspan(2);
            table.row().padTop(10);
            table.add(score).colspan(2);
            table.row().padTop(10);
            table.add(nameLabel).colspan(2);
            table.row().padTop(10);

            txtfName = new TextField("", skin);
            txtfName.setSize(300, 40);

            table.add(txtfName).colspan(2);

        }else{
            Label scoreLabel = new Label("SCORE "+ Save.hsd.get(level).getTentativeScore(), skin, "highscore");
            Label score = new Label(String.valueOf(Save.hsd.get(level).getTentativeScore()), skin, "big");


            table.add(scoreLabel).colspan(2);
            table.row().padTop(10);
            table.add(score).colspan(2);
        }




        Label labelCredits = new Label("Credits:", skin, "small");
        Label labelCredits1 = new Label("Game Design by", skin, "small");
        Label labelCredits2 = new Label("gamedevelopment.blog", skin,"small");
        Label labelCredits3 = new Label("Art Design by", skin, "small");
        Label labelCredits4 = new Label("Random stuff off the internet", skin, "small");

        table.row().padTop(10);
        table.add(labelCredits).colspan(2);
        table.row().padTop(10);
        table.add(labelCredits1).uniformX().align(Align.left);
        table.add(labelCredits2).uniformX().align(Align.left);
        table.row().padTop(10);
        table.add(labelCredits3).uniformX().align(Align.left);
        table.add(labelCredits4).uniformX().align(Align.left);
        table.row().padTop(50);

        if(newHighScore){
            table.add(menuButton);
            table.add(saveButton);
        }else{
            table.add(menuButton);
        }

        stage.addActor(table);

        //Create button listeners
        menuButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(Box2dTutorial.MENU);
            }
        });

        saveButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                DFUtils.log("HighScore saved!");
                if (newHighScore) {
                    Save.hsd.get(level).addHighScore(
                            Save.hsd.get(level).getTentativeScore(),
                            (txtfName.getText())
                    );
                    Save.publish();
                }
                parent.changeScreen(Box2dTutorial.HIGHSCORE, level);
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

