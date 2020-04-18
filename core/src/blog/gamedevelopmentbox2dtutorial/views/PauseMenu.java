

package blog.gamedevelopmentbox2dtutorial.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import blog.gamedevelopmentbox2dtutorial.Box2dTutorial;

public class PauseMenu implements Disposable{

    public final Box2dTutorial parent;
    private Stage stage;
    private Skin skin1, skin2, skin3;
    private TextureAtlas atlas;
    private Viewport viewport;
    private GameScreen ms;



    public PauseMenu(SpriteBatch sb, final Box2dTutorial parent, GameScreen gameScreen){
        this.parent = parent;

        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera());
        stage = new Stage(viewport, sb);

        //Gdx.input.setInputProcessor(stage);

        ms = gameScreen;

        atlas = parent.assMan.manager.get("images/loading.atlas");
        skin1 = parent.assMan.manager.get("skin/shade/uiskin.json");
        skin2 = parent.assMan.manager.get("skin/glassy/glassy-ui.json");
        skin3 = parent.assMan.manager.get("skin/clean/clean-crispy-ui.json");

        //Create a table that fills the screen. Everything else will go inside this table
        Table table = new Table();

        if (Box2dTutorial.DEBUG) {
            table.setDebug(true);
        }

        table.setFillParent(true);
        stage.addActor(table);

        // Create text buttons
        final TextButton resume= new TextButton("Resume", skin2);
        final TextButton preferences = new TextButton("Preferences", skin2);
        final TextButton back = new TextButton("Exit", skin2);

        // Add buttons to table
        table.add(resume).fillX().uniformX().pad(25,0,25,0);
        table.row().pad(25,0,25,0);
        table.add(preferences).fillX().uniformX();
        table.row().pad(25,0,25,0);
        table.add(back).fillX().uniformX().pad(25,0,25,0);


        // Create button listeners
        back.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent changeEvent, Actor actor) { parent.changeScreen(Box2dTutorial.MENU);
                }
            });

        resume.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent changeEvent, Actor actor) { ms.pauseGame(false); ;
                }
        });

        preferences.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent changeEvent, Actor actor) { parent.changeScreen(Box2dTutorial.PREFERENCES);
                }
        });


    }


    public void update(float dt){

    }

    public void draw(){
        stage.draw();
    }

    public void resize(int width, int height){
        viewport.update(width, height);
    }

    public Stage getStage(){
        return stage;
    }


    @Override
    public void dispose() {
        stage.dispose();
    }
}


