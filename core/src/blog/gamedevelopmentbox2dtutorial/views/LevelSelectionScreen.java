package blog.gamedevelopmentbox2dtutorial.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
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

public class LevelSelectionScreen implements Screen {
    private static final int IMG_WIDTH = 600;
    private static final int IMG_HEIGHT = 500;


    private Box2dTutorial parent;
    private Stage stage;
    private Skin skin1, skin2, skin3;
    private TextureAtlas.AtlasRegion background;
    private TextureAtlas loadingAtlas, gameAtlas;
    private int levelSelected = 1;
    private Image levelImage;
    private IntMap<Image> levelPreviewImages;
    private Table innerTable;
    private Table outerTable;
    private Table previewTable;
    private int characterSelected;


    public LevelSelectionScreen(Box2dTutorial box2dTutorial, int character) {
        parent = box2dTutorial;
        stage = new Stage(new ScreenViewport());
        characterSelected = character;

        // Get images to display loading progress
        loadingAtlas = parent.assMan.manager.get("images/loading.atlas");


        skin1 = parent.assMan.manager.get("skin/shade/uiskin.json");
        skin2 = parent.assMan.manager.get("skin/glassy/glassy-ui.json");
        skin3 = parent.assMan.manager.get("skin/clean/clean-crispy-ui.json");

        background = loadingAtlas.findRegion("flamebackground");

        //Load level preview images
        levelPreviewImages = new IntMap<>();
        levelPreviewImages.put(1, new Image(new Texture("preview/level1.png")));
        levelPreviewImages.put(2, new Image(new Texture("preview/level2.png")));
        levelPreviewImages.put(3, new Image(new Texture("preview/level3.png")));
        levelPreviewImages.put(4, new Image(new Texture("preview/level4.png")));
        levelPreviewImages.put(5, new Image(new Texture("preview/level5.png")));
        levelPreviewImages.put(6, new Image(new Texture("preview/level6.png")));





    }




    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        levelSelected = 1;

        // Create text buttons, labels etc.
        final TextButton l1 = new TextButton("1", skin3, "toggle");
        final TextButton l2 = new TextButton("2", skin3, "toggle");
        final TextButton l3 = new TextButton("3", skin3, "toggle");
        final TextButton l4 = new TextButton("4", skin3, "toggle");
        final TextButton l5 = new TextButton("5", skin3, "toggle");
        final TextButton l6 = new TextButton("6", skin3, "toggle");

        l1.setChecked(true);

        ButtonGroup buttonGroup = new ButtonGroup(l1, l2, l3, l4, l5, l6);
        buttonGroup.setMaxCheckCount(1);

        Label headerLabel = new Label("SELECT LEVEL", skin2, "big");

        final TextButton backButton = new TextButton("Back", skin2, "small");
        final TextButton playButton = new TextButton("Play", skin2, "small");


        levelImage = levelPreviewImages.get(levelSelected);
        levelImage.setSize(IMG_WIDTH,IMG_HEIGHT);


        innerTable = new Table();
        outerTable = new Table();
        previewTable = new Table();

        //innerTable.debug();
        //outerTable.debug();
        //previewTable.debug();
        outerTable.setFillParent(true);
        previewTable.setFillParent(true);
        previewTable.setBackground(new TiledDrawable(background));


        previewTable.center().padLeft(Gdx.graphics.getWidth()/4);
        previewTable.add(levelImage).size(levelImage.getWidth(), levelImage.getHeight()).expandX();



        innerTable.add(l1).padTop(10).fillX().expandX();
        innerTable.row();
        innerTable.add(l2).padTop(30).fillX().expandX();
        innerTable.row();
        innerTable.add(l3).padTop(30).fillX().expandX();
        innerTable.row();
        innerTable.add(l4).padTop(30).fillX().expandX();
        innerTable.row();
        innerTable.add(l5).padTop(30).fillX().expandX();
        innerTable.row();
        innerTable.add(l6).padTop(30).padBottom(10).fillX().expandX();

        ScrollPane scrollPane = new ScrollPane(innerTable, skin1);

        outerTable.add(headerLabel).colspan(3);
        outerTable.row().expandX();
        outerTable.add(scrollPane).fillY().expandY().padTop(20);

        outerTable.row().expandX();
        outerTable.add(backButton).pad(20,0,10,0);
        outerTable.add();
        outerTable.add(playButton).pad(20,0,10,0);


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
                parent.changeScreen(Box2dTutorial.CHARACTER_SELECTION);

            }
        });

        playButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                parent.changeScreen(Box2dTutorial.APPLICATION, true, levelSelected, characterSelected);
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

