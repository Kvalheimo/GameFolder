package blog.gamedevelopmentbox2dtutorial.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import blog.gamedevelopmentbox2dtutorial.Box2dTutorial;

public class LevelSelectionScreen implements Screen {


    private Box2dTutorial parent;
    private Stage stage;
    private Skin skin1, skin2, skin3;
    private TextureAtlas.AtlasRegion background;
    private TextureAtlas atlas;

    public LevelSelectionScreen(Box2dTutorial box2dTutorial) {
        parent = box2dTutorial;
        stage = new Stage(new ScreenViewport());

        // Get images to display loading progress
        atlas = parent.assMan.manager.get("images/loading.atlas");

        skin1 = parent.assMan.manager.get("skin/shade/uiskin.json");
        skin2 = parent.assMan.manager.get("skin/glassy/glassy-ui.json");
        skin3 = parent.assMan.manager.get("skin/clean/clean-crispy-ui.json");

        background = atlas.findRegion("flamebackground");

    }




    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);


        // Create text buttons
        final TextButton l1 = new TextButton("1", skin1);
        final TextButton l2 = new TextButton("2", skin1);
        final TextButton l3 = new TextButton("3", skin1);
        final TextButton l4 = new TextButton("4", skin1);
        final TextButton l5 = new TextButton("5", skin1);
        final TextButton l6 = new TextButton("6", skin1);
        final TextButton backButton = new TextButton("Back", skin2, "small");

        Table table = new Table();
        table.setFillParent(true);
        table.setBackground(new TiledDrawable(background));

        table.add(l1).fillX().uniformX();
        table.add(l2).fillX().uniformX().padLeft(100);
        table.add(l3).fillX().uniformX().padLeft(100);
        table.row().padTop(70);
        table.add(l4).fillX().uniformX();
        table.add(l5).uniformX().padLeft(100);
        table.add(l6).uniformX().padLeft(100);
        table.row();
        table.add(backButton).colspan(3).padTop(20);

        table.debug();
        stage.addActor(table);





        // Create button listeners
        l1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                parent.changeScreen(Box2dTutorial.APPLICATION, true, 1);
            }
        });

        l2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                parent.changeScreen(Box2dTutorial.APPLICATION, true, 2);
            }
        });

        l3.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                parent.changeScreen(Box2dTutorial.APPLICATION, true, 3);
            }
        });

        l4.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                parent.changeScreen(Box2dTutorial.APPLICATION, true, 4);
            }
        });

        l5.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                parent.changeScreen(Box2dTutorial.APPLICATION, true, 5);
            }
        });

        l6.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                parent.changeScreen(Box2dTutorial.APPLICATION, true, 6);
            }
        });


        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                parent.changeScreen(Box2dTutorial.MENU);

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

