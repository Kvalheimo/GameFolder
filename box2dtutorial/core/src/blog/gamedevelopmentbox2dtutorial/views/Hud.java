package blog.gamedevelopmentbox2dtutorial.views;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import blog.gamedevelopmentbox2dtutorial.Box2dTutorial;

public class Hud {
    public Stage stage;
    private Viewport viewport;

    private Integer worldTimer;
    private float timeCount;
    private Integer score;

    Label countdownLabel;
    Label scoreLabel;
    Label timeLabel;
    Label worldLabel;
    Label platformerLabel;
    Label levelLabel;

    public Hud(SpriteBatch sb){
        worldTimer = 300;
        timeCount = 0;
        score = 0;

        viewport = new FitViewport(Box2dTutorial.V_WIDTH, Box2dTutorial.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        //countdownLabel = new Label(String.format("%03", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        //scoreLabel = new Label(String.format("%06", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));;
        timeLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        worldLabel = new Label("WORLD", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        platformerLabel = new Label("PLATFORMER", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        levelLabel = new Label("1-1", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(platformerLabel).expandX().padTop(2);
        table.add(worldLabel).expandX().padTop(2);
        table.add(timeLabel).expandX().padTop(2);
        table.row();



        /*
        table.add(scoreLabel).expandX();
        table.add(levelLabel).expandX();
        table.add(countdownLabel).expand();
         */

        stage.addActor(table);


    }

}
