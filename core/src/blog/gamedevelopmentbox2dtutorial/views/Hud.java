package blog.gamedevelopmentbox2dtutorial.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
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

    private  float speedDuration;
    private float gameTime;
    private boolean speedBoost; //True if the speed boost icon should be displayed.
    private boolean speedBoostActive; //True if the speed boost is active
    private Integer score;
    private float timeCountB;
    private int mapPixelWidth;
    private float percentage;
    private float minimapWidth;
    private TextureAtlas atlas_boosts;

    private Table table;  //Table for the text
    private Table table2; //table containing the ball for minimap
    private Table table3; //table for speedBoostActive
    private Image player;
    private Image boost;
    private Label scoreLabel;
    private Label timeLabel;
    private Label timeHeadingLabel;
    private Label scoreHeadingLabel;

    private SpriteBatch sb;



    private int counter = 0;
    private float tempTimer = -10;

    public Hud(SpriteBatch sb, int mapPixelWidth, Box2dTutorial parent){
        speedBoost = false;
        speedBoostActive = false;
        speedDuration = (float) .3;  //Assign how long the duration of the speed boost icon is displayed.
        gameTime = 0;
        score = 0;
        timeCountB = 0;
        percentage = 0;
        this.sb = sb;
        this.mapPixelWidth = mapPixelWidth;


        atlas_boosts = parent.assMan.manager.get("minimap/boosts.atlas");

        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera());
        stage = new Stage(viewport, sb);


        float minutes = 0;
        float seconds = 0;


        //Controls the labeling for the score and time used
        scoreHeadingLabel = new Label("SCORE", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel = new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeHeadingLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel = new Label(String.format("%.0fm%.0fs", minutes, seconds), new Label.LabelStyle(new BitmapFont(), Color.WHITE));



        //Images used to generate the minimap.
        Image miniMap = new Image((atlas_boosts.findRegion("minimap")));
        player = new Image((atlas_boosts.findRegion("ball")));
        boost  = new Image(atlas_boosts.findRegion("boost0"));







        minimapWidth = miniMap.getWidth();

        table = new Table(); //Table for the text
        table2 = new Table(); //table containing the ball for minimap
        table3 = new Table(); //table for speedBoostActive

        //Format on how the table should look like.
        table3.top();
        table3.left();
        table2.top();
        table2.right();
        table.top();
        table.left();
        table.setFillParent(true);
        table2.setFillParent(true);
        table3.setFillParent(true);
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
        timeCountB += dt;
        gameTime += dt;

        if(speedBoostActive){
            speedBoost = false;
            if (tempTimer < 0 + dt/4){     //because dt is not static, a small tolerance is added to be sure next frame of speed boost animation is displayed in time.
                tempTimer = (float) Math.floor(speedDuration/dt/22)*dt;  //How much time each frame of the speed animation is supposed to be displayed, in order to have a continuously smooth animation lasting approx. as long as wanted.
                speedBoostActive();
            }
            else  {
                tempTimer -= dt;
            }
        }
        if (speedBoost){
            speedBoostDisplay();
        }

        addScore(timeCountB);
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


    private void speedBoostActive(){
        if(counter < 22) {  //Loops through the speed boost animation.
            boost  = new Image(atlas_boosts.findRegion("boost"+String.valueOf(counter)));
            table3.clear();
            table3.add(boost).padTop(20);
            stage.addActor(table3);
            counter += 1;
        }
        else{
            counter = 0;
            tempTimer = -10;
            speedBoostActive = false;
            table3.clear();
        }
    }

    private  void speedBoostDisplay(){
        table3.clear();
        boost  = new Image(atlas_boosts.findRegion("boost0"));
        table3.add(boost).padTop(20);
        stage.addActor(table3);
    }


    private void addTime(float gameTime){
         float minutes = (float)Math.floor(gameTime / 60.0f);
         float seconds = gameTime - minutes * 60.0f;
         timeLabel.setText(String.format("%.0fm%.0fs", minutes, seconds));
    }

    public int getScore(){
        return score;
    }
    private void addPlayerPos(int playerPosition){
        //How far the player is away from the finish line, and draws the player in the correct position.
        percentage = (float) playerPosition/mapPixelWidth;
        table2.clear();
        table2.add(player).padTop(30+player.getHeight()).padRight(minimapWidth-player.getWidth()+20-(minimapWidth-player.getWidth()+10)*percentage);
    }

    public void setSpeedBoost(){
        speedBoost = true;
    }
    public void setSpeedBoostActive(){
        speedBoostActive = true;
    }
    @Override
    public void dispose() {
        stage.dispose();
    }
}
