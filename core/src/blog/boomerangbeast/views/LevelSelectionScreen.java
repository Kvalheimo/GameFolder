package blog.boomerangbeast.views;

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

public class LevelSelectionScreen implements Screen {
    private static final int IMG_WIDTH = 700;
    private static final int IMG_HEIGHT = 600;


    private BoomerangBeast parent;
    private Stage stage;
    private Skin skin;
    private TextureAtlas.AtlasRegion background;
    private TextureAtlas gameAtlas;
    private int levelSelected = 1;
    private Image levelImage;
    private IntMap<Image> levelPreviewImages;
    private Table innerTable;
    private Table outerTable;
    private Table previewTable;
    private int characterSelected;


    public LevelSelectionScreen(BoomerangBeast boomerangBeast, int character) {
        parent = boomerangBeast;
        stage = new Stage(new ScreenViewport());
        characterSelected = character;

        // Get images to display loading progress
        gameAtlas = parent.assMan.manager.get("images/game.atlas");

        skin = parent.assMan.manager.get("skin/game/game.json");
        background = gameAtlas.findRegion("background");

        //Load level preview images
        levelPreviewImages = new IntMap<>();
        levelPreviewImages.put(1, new Image(new Texture("preview/level1.png")));
        levelPreviewImages.put(2, new Image(new Texture("preview/level2.png")));
        levelPreviewImages.put(3, new Image(new Texture("preview/level3.png")));
        levelPreviewImages.put(4, new Image(new Texture("preview/level4.png")));
    }




    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        levelSelected = 1;

        // Create text buttons, labels etc.
        final TextButton l1 = new TextButton("Homecoming", skin, "toggle2");
        final TextButton l2 = new TextButton("Enrique's World", skin, "toggle2");
        final TextButton l3 = new TextButton("Mountain Jam", skin, "toggle2");
        final TextButton l4 = new TextButton("Platform Plooza", skin, "toggle2");

        l1.setChecked(true);

        ButtonGroup buttonGroup = new ButtonGroup(l1, l2, l3, l4);
        buttonGroup.setMaxCheckCount(1);

        Label headerLabel = new Label("SELECT LEVEL", skin, "big");

        final TextButton backButton = new TextButton("Back", skin, "blue-small");
        final TextButton playButton = new TextButton("Play", skin, "blue-small");


        levelImage = levelPreviewImages.get(levelSelected);
        levelImage.setSize(IMG_WIDTH,IMG_HEIGHT);


        innerTable = new Table();
        outerTable = new Table();
        previewTable = new Table();

        if (BoomerangBeast.DEBUG) {
            innerTable.setDebug(true);
            outerTable.setDebug(true);
            previewTable.setDebug(true);
        }

        outerTable.setFillParent(true);
        previewTable.setFillParent(true);
        previewTable.setBackground(new TiledDrawable(background));


        previewTable.center().padLeft(Gdx.graphics.getWidth()/4);
        previewTable.add(levelImage).size(levelImage.getWidth(), levelImage.getHeight()).expandX();



        innerTable.add(l1).padTop(30).fillX().expandX();
        innerTable.row();
        innerTable.add(l2).padTop(30).fillX().expandX();
        innerTable.row();
        innerTable.add(l3).padTop(30).fillX().expandX();
        innerTable.row();
        innerTable.add(l4).padTop(30).padBottom(30).fillX().expandX();;

        ScrollPane scrollPane = new ScrollPane(innerTable, skin);

        outerTable.add(headerLabel).colspan(3).padTop(30);
        outerTable.row().expandX();
        outerTable.add(scrollPane).fillY().expandY().pad(Gdx.graphics.getBackBufferHeight()/6,0,Gdx.graphics.getBackBufferHeight()/6,0);

        outerTable.row().expandX();
        outerTable.add(backButton).pad(20,0,30,0);
        outerTable.add();
        outerTable.add(playButton).pad(20,0,30,0);


        stage.addActor(previewTable);
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
                parent.changeScreen(BoomerangBeast.CHARACTER_SELECTION);

            }
        });

        playButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                if (parent.isOnline()) {
                    parent.changeScreen(BoomerangBeast.MULTIPLAYER, true, levelSelected, characterSelected);

                } else {
                    parent.changeScreen(BoomerangBeast.APPLICATION, true, levelSelected, characterSelected);
                }
                System.out.println(levelSelected);
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
        levelImage = levelPreviewImages.get(levelSelected);
        levelImage.setSize(IMG_WIDTH,IMG_HEIGHT);
        previewTable.add(levelImage).size(levelImage.getWidth(), levelImage.getHeight()).expandX();
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

