package blog.gamedevelopmentbox2dtutorial.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import blog.gamedevelopmentbox2dtutorial.Box2dTutorial;

public class Hud implements Disposable {
    public Stage stage;
    private Viewport viewport;


    private float gameTime;
    private Integer worldTimer;
    private boolean timeUp; // true when the world timer reaches 0
    private Integer score;
    private float timeCountA;
    private float timeCountB;


    Label countdownLabel;
    Label scoreLabel;
    Label timeLabel;
    Label rightLabel;
    Label middleLabel;
    Label leftLabel;


    public Hud(SpriteBatch sb){
       gameTime = 0;
       score = 0;
       worldTimer = 300;
       timeCountA = 0;
       timeCountB = 0;

        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        float minutes = 0;
        float seconds = 0;

        countdownLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel = new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel = new Label(String.format("%.0fm%.0fs", minutes, seconds), new Label.LabelStyle(new BitmapFont(), Color.WHITE));



        leftLabel = new Label("SCORE", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        middleLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        rightLabel = new Label("REMAINING", new Label.LabelStyle(new BitmapFont(), Color.WHITE));


        table.add(leftLabel).expandX().padTop(10);
        table.add(middleLabel).expandX().padTop(10);
        table.add(rightLabel).expandX().padTop(10);
        table.row();
        table.add(scoreLabel).expandX();
        table.add(timeLabel).expandX();
        table.add(countdownLabel).expandX();


        stage.addActor(table);
    }

    public void update(float dt){
        timeCountA += dt;
        timeCountB += dt;
        gameTime += dt;

        addScore(timeCountB);
        countDown(timeCountA);
        addTime(gameTime);

    }

    public void draw(){
        stage.draw();
    }

    public void resize(int width, int height){
        viewport.update(width, height);
    }


    public void addScore(float count){
        if(count >= 0.5){
            score += 1;
            scoreLabel.setText(String.format("%01d", score));
            timeCountB = 0;
        }

    }


    public void addTime(float gameTime){
        float minutes = (float)Math.floor(gameTime / 60.0f);
        float seconds = gameTime - minutes * 60.0f;
        timeLabel.setText(String.format("%.0fm%.0fs", minutes, seconds));
    }

    public void countDown(float count){
        if(count >= 1){
            if (worldTimer > 0) {
                worldTimer--;
            } else {
                timeUp = true;
            }
            countdownLabel.setText(String.format("%03d", worldTimer));
            timeCountA = 0;
        }

    }


    @Override
    public void dispose() {
        stage.dispose();
    }
}
