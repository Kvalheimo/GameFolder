package blog.boomerangbeast.views.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import blog.boomerangbeast.BoomerangBeast;

public class CharacterSelectionScreen implements Screen {
    private static final int IMG_WIDTH = 600;
    private static final int IMG_HEIGHT = 600;


    private BoomerangBeast parent;
    private Stage stage;
    private Skin skin;
    private TextureAtlas.AtlasRegion background;
    private TextureAtlas loadingAtlas, gameAtlas;
    private int characterSelected;
    private Image characterImage;
    private IntMap<Image> characterImages;
    private Table innerTable;
    private Table outerTable;
    private Table previewTable;


    public CharacterSelectionScreen(BoomerangBeast boomerangBeast) {
        parent = boomerangBeast;
        stage = new Stage(new ScreenViewport());
        characterSelected = 1;

        // Get images to display loading progress
        gameAtlas = parent.assMan.manager.get("images/game.atlas");
        skin = parent.assMan.manager.get("skin/game/game.json");
        background = gameAtlas.findRegion("background");

        //Load level preview images
        characterImages = new IntMap<>();
        characterImages.put(1, new Image(new Texture("preview/char1.png")));
        characterImages.put(2, new Image(new Texture("preview/char2.png")));

    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        characterSelected = 1;

        // Create text buttons, labels etc.
        final TextButton c1 = new TextButton("Jim Green", skin, "toggle2");
        final TextButton c2 = new TextButton("Jam Red", skin, "toggle2");

        c1.setChecked(true);

        ButtonGroup buttonGroup = new ButtonGroup(c1, c2);
        buttonGroup.setMaxCheckCount(1);

        Label headerLabel = new Label("SELECT CHARACTER", skin, "big");

        final TextButton backButton = new TextButton("Back", skin, "blue-small");
        final TextButton nextButton = new TextButton("Next", skin, "blue-small");


        characterImage = characterImages.get(characterSelected);
        characterImage.setSize(IMG_WIDTH,IMG_HEIGHT);


        innerTable = new Table();
        outerTable = new Table();
        previewTable = new Table();

        if (BoomerangBeast.DEBUG) {
            innerTable.setDebug(true);
            outerTable.setDebug(true);
            previewTable.setDebug(true);
        }

        previewTable.setFillParent(true);
        outerTable.setFillParent(true);

        previewTable.setBackground(new TiledDrawable(background));

        previewTable.center().padLeft(Gdx.graphics.getWidth()/6);
        previewTable.add(characterImage).size(characterImage.getWidth(), characterImage.getHeight()).expandX();

        innerTable.add(c1).padTop(30).fillX();
        innerTable.row();
        innerTable.add(c2).padTop(30).padBottom(30).fillX();


        ScrollPane scrollPane = new ScrollPane(innerTable, skin);

        outerTable.add(headerLabel).colspan(3).padTop(30);
        outerTable.row().expandX();
        outerTable.add(scrollPane).fillY().expandY().pad(Gdx.graphics.getBackBufferHeight()/6, 0, Gdx.graphics.getBackBufferHeight()/6, 0);

        outerTable.row().expandX();
        outerTable.add(backButton).pad(20,0,30,0);
        outerTable.add();
        outerTable.add(nextButton).pad(20,0,30,0);


        stage.addActor(previewTable);
        stage.addActor(outerTable);



        // Create button listeners
        c1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                characterSelected = 1;
            }
        });

        c2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                characterSelected = 2;            }
        });


        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                parent.changeScreen(BoomerangBeast.MENU);

            }
        });

        nextButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                parent.changeScreen(BoomerangBeast.LEVEL_SELECTION, false, 0, characterSelected);
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

