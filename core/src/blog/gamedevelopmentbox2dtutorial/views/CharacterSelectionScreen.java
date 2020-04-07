package blog.gamedevelopmentbox2dtutorial.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import blog.gamedevelopmentbox2dtutorial.Box2dTutorial;
import blog.gamedevelopmentbox2dtutorial.HighScore.Save;

public class CharacterSelectionScreen implements Screen {
    private static final int IMG_WIDTH = 250;
    private static final int IMG_HEIGHT = 250;


    private Box2dTutorial parent;
    private Stage stage;
    private Skin skin1, skin2, skin3;
    private TextureAtlas.AtlasRegion background;
    private TextureAtlas loadingAtlas, gameAtlas;
    private int characterSelected = 1;
    private Image characterImage;
    private IntMap<Image> characterImages;
    private Table innerTable;
    private Table outerTable;
    private Table previewTable;


    public CharacterSelectionScreen(Box2dTutorial box2dTutorial) {
        parent = box2dTutorial;
        stage = new Stage(new ScreenViewport());


        // Get images to display loading progress
        loadingAtlas = parent.assMan.manager.get("images/game.atlas");


        skin1 = parent.assMan.manager.get("skin/shade/uiskin.json");
        skin2 = parent.assMan.manager.get("skin/glassy/glassy-ui.json");
        skin3 = parent.assMan.manager.get("skin/clean/clean-crispy-ui.json");

        background = loadingAtlas.findRegion("flamebackground");

        //Load level preview images
        characterImages = new IntMap<>();
        characterImages.put(1, new Image(new Texture("preview/char1.png")));
        characterImages.put(2, new Image(new Texture("preview/char2.png")));
        characterImages.put(3, new Image(new Texture("preview/char3.png")));






    }




    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        // Create text buttons, labels etc.
        final TextButton l1 = new TextButton("Jan-Vidar", skin1);
        final TextButton l2 = new TextButton("Kniven", skin1);
        final TextButton l3 = new TextButton("Gunnar", skin1);


        Label headerLabel = new Label("SELECT CHARACTER", skin2, "big");

        final TextButton backButton = new TextButton("Back", skin2, "small");
        final TextButton nextButton = new TextButton("Next", skin2, "small");


        characterImage = characterImages.get(characterSelected);
        characterImage.setSize(IMG_WIDTH,IMG_HEIGHT);


        innerTable = new Table();
        outerTable = new Table();
        previewTable = new Table();
        Table buttonTable = new Table();

        innerTable.debug();
        outerTable.debug();
        previewTable.debug();

        previewTable.center().padLeft(Gdx.graphics.getWidth()/4);
        previewTable.add(characterImage).size(characterImage.getWidth(), characterImage.getHeight()).expandX();

        previewTable.setFillParent(true);
        outerTable.setFillParent(true);

        innerTable.add(l1).padTop(30);
        innerTable.row();
        innerTable.add(l2).padTop(30);
        innerTable.row();
        innerTable.add(l3).padTop(30);


        ScrollPane scrollPane = new ScrollPane(innerTable, skin1);

        outerTable.add(headerLabel).colspan(3);
        outerTable.row().expandX();
        outerTable.add(scrollPane).fillY().expandY().padTop(40);

        outerTable.row().expandX();
        outerTable.add(backButton).pad(20,0,10,0);
        outerTable.add();
        outerTable.add(nextButton).pad(20,0,10,0);


        stage.addActor(previewTable);
        stage.addActor(outerTable);



        // Create button listeners
        l1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                characterSelected = 1;
            }
        });

        l2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                characterSelected = 2;            }
        });

        l3.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                characterSelected = 3;            }
        });


        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                parent.changeScreen(Box2dTutorial.MENU);

            }
        });

        nextButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                parent.changeScreen(Box2dTutorial.LEVEL_SELECTION, false, 0, characterSelected);
            }
        });


    }

    @Override
    public void render(float dt) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update();
        // Tell our stage to do actions and draw itself
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        // Change the stage's viewport when the screen size is changed
        stage.getViewport().update(width, height, true);

    }

    private void update(){
        previewTable.clear();
        characterImage = characterImages.get(characterSelected);
        characterImage.setSize(IMG_WIDTH,IMG_HEIGHT);
        previewTable.add(characterImage).size(characterImage.getWidth(), characterImage.getHeight()).expandX();
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

