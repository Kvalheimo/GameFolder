package blog.gamedevelopmentbox2dtutorial.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
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
    private int mapPixelWidth;
    private float percentage;
    private float minimapWidth;
    private TextureAtlas atlas;
    private TextureAtlas atlas_boosts;

    private Table table2; //table containing the player
    private Image player;
    private Image boost;
    private Label countdownLabel;
    private Label scoreLabel;
    private Label timeLabel;
    private Label timeHeadingLabel;
    private Label scoreHeadingLabel;

    private SpriteBatch sb;

    private float minutes;
    private float seconds;

    public Hud(SpriteBatch sb, int mapPixelWidth, Box2dTutorial parent){
        gameTime = 0;
        score = 0;
        worldTimer = 300;
        timeCountA = 0;
        timeCountB = 0;
        percentage = 0;
        this.sb = sb;
        this.mapPixelWidth = mapPixelWidth;

        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera());
        stage = new Stage(viewport, sb);


        minutes = 0;
        seconds = 0;

        countdownLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel = new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel = new Label(String.format("%.0fm%.0fs", minutes, seconds), new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        scoreHeadingLabel = new Label("SCORE", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeHeadingLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        atlas_boosts = parent.assMan.manager.get("minimap/boosts.atlas");
        //atlas = parent.assMan.manager.get("minimap/test/boosts.atlas");


        Image miniMap = new Image((atlas_boosts.findRegion("minimap")));
        player = new Image((atlas_boosts.findRegion("ball")));
        boost  = new Image(atlas_boosts.findRegion("boost0"));







        minimapWidth = miniMap.getWidth();

        Table table = new Table();
        table2 = new Table();
        table2.top();
        table2.right();
        table.top();
        table.left();
        table.setFillParent(true);
        table2.setFillParent(true);
        table.add().padTop(10).expandX().padLeft(50);
        table.add(scoreHeadingLabel).expandX().padTop(10);
        table.add(timeHeadingLabel).expandX().padTop(10);
        table.add(miniMap).padRight(10).padTop(30);
        table.row();
        table.add().padTop(10).padLeft(50).expandX();
        table.add(scoreLabel).expandX();
        table.add(timeLabel).expandX();
        stage.addActor(table);

        table2.add(player).padRight(minimapWidth-player.getWidth()+35).padTop(30+player.getHeight());
        stage.addActor(table2);



    }

    public void update(float dt, int playerPosition){
        timeCountA += dt;
        timeCountB += dt;
        gameTime += dt;
        System.out.println(seconds);
        if(5 == seconds){
            System.out.println("he");
            speedBoost();
        }

        addScore(timeCountB);
        countDown(timeCountA);
        addTime(gameTime);

        //updates the players position on the minimap
        addPlayerPos(playerPosition);


    }

    public void draw(){
        stage.draw();
    }

    public void resize(int width, int height){
        viewport.update(width, height);
    }



    private void addScore(float count){
        if(count >= 0.5){
            score += 1;
            scoreLabel.setText(String.format("%01d", score));
            timeCountB = 0;
        }

    }


    private void speedBoost(){
        boost  = new Image(atlas_boosts.findRegion("boost0"));
        table2.add(boost);

    }


    private void addTime(float gameTime){
         minutes = (float)Math.floor(gameTime / 60.0f);
         seconds = gameTime - minutes * 60.0f;
        timeLabel.setText(String.format("%.0fm%.0fs", minutes, seconds));
    }

    private void countDown(float count){
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

    public int getScore(){
        return score;
    }
    private void addPlayerPos(int playerPosition){
        //How far the player is away from the finish line.
        percentage = (float) playerPosition/mapPixelWidth;
        table2.clear();
        table2.add(player).padTop(30+player.getHeight()).padRight(minimapWidth-player.getWidth()+20-(minimapWidth-player.getWidth()+10)*percentage);
    }


    @Override
    public void dispose() {
        stage.dispose();
    }
}
