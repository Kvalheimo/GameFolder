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

    private int boomerangCount;
    private  float speedDuration;
    private float gameTime;
    private boolean speedBoost; //True if the speed boost icon should be displayed.
    private boolean newDisplay; //True if the icon for boomerangs neeeds to be updated
    private boolean speedBoostActive; //True if the speed boost is active
    private boolean extraPoints;
    private float timeCountB;
    private int mapPixelWidth;
    private float percentage;
    private float minimapWidth;
    private TextureAtlas atlas_boosts;

    private Table table;  //Table for the text
    private Table table2; //Table containing the ball for minimap
    private Table table3; //Table for speedBoostActive
    private Table table4; //Table containing the boomerangs.
    private Image player;
    private Image boost;
    private Label scoreLabel;
    private Label timeLabel;
    private Label timeHeadingLabel;

    private SpriteBatch sb;

    private int extraSecondsForKill;

    private int counter = 0;
    private float tempTimer = -10;

    public Hud(SpriteBatch sb, int mapPixelWidth, Box2dTutorial parent){
        speedBoost = false;
        extraPoints = false; //True if one should subtract time
        newDisplay = false;
        speedBoostActive = false;
        speedDuration = (float) .3;  //Assign how long the duration of the speed boost icon is displayed.
        boomerangCount = 0;
        gameTime = 0;
        timeCountB = 0;
        percentage = 0;
        extraSecondsForKill = 10; //How many seconds one gets if there is a kill.
        this.sb = sb;
        this.mapPixelWidth = mapPixelWidth;


        atlas_boosts = parent.assMan.manager.get("minimap/boosts.atlas");

        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera());
        stage = new Stage(viewport, sb);


         float minutes = 0;
         float seconds = 0;


        //Controls the labeling for time used
        timeHeadingLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel = new Label(String.format("%.0fm%.0fs", minutes, seconds), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel = new Label(String.format("-%d", extraSecondsForKill), new Label.LabelStyle(new BitmapFont(), Color.GREEN));



        //Images used to generate the minimap.
        Image miniMap = new Image((atlas_boosts.findRegion("minimap")));
        player = new Image((atlas_boosts.findRegion("ballSmaller")));
        boost  = new Image(atlas_boosts.findRegion("boost0"));







        minimapWidth = miniMap.getWidth();

        table = new Table(); //Table for the text
        table2 = new Table(); //table containing the ball for minimap
        table3 = new Table(); //table for speedBoostActive
        table4 = new Table(); //Table for boomerang
        Table table5 = new Table();

        //Format on how the table should look like.
        table3.top();
        table4.top();
        table3.left();
        table4.left();
        table2.top();
        table2.right();
        table.center();
        table.top();
        table5.top();
        table5.right();

        table.setFillParent(true);
        table2.setFillParent(true);
        table3.setFillParent(true);
        table4.setFillParent(true);
        table5.setFillParent(true);
        table.add(timeHeadingLabel).padTop(10);
        table5.add(miniMap).padRight(10).padTop(30);
        table.row();
        table.add(timeLabel);
        table.add().padLeft(5);
        stage.addActor(table);
        table2.add(player).padRight(minimapWidth-player.getWidth()+35).padTop(38+player.getHeight());
        stage.addActor(table2);
        stage.addActor(table5);



    }

    public void update(float dt, int playerPosition){
        gameTime += dt;

        if(speedBoostActive){
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
        if(newDisplay){
            boomerangDisplay();
        }

        addTime(gameTime);

        if(extraPoints){
            hideExtraPoints();
            timeCountB += dt;
        }
        //updates the players position on the minimap
        addPlayerPos(playerPosition);


    }

    private void hideExtraPoints(){
        if(timeCountB>5){
            table.getCell(scoreLabel).clearActor();
            extraPoints = false;
            timeCountB = 0;
            if(gameTime-extraSecondsForKill <0) {
                gameTime =0;
            }
            else {
                gameTime -= extraSecondsForKill;
            }
        }
    }

    public void draw(){
        stage.draw();
    }

    public void resize(int width, int height){
        viewport.update(width, height);
    }



    private void speedBoostActive(){
        if(counter < 22) {  //Loops through the speed boost animation.
            boost  = new Image(atlas_boosts.findRegion("boost"+String.valueOf(counter)));
            table3.clear();
            table3.add(boost).padTop(10).padLeft(5);
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
        table3.add(boost).padTop(10).padLeft(5);
        stage.addActor(table3);
        speedBoost = false;
    }

    private  void boomerangDisplay(){
        table4.clear();
        newDisplay = false;
        Image boomerang;
        switch (boomerangCount){
            case 0:
                return;
            case 1:
                 boomerang  = new Image(atlas_boosts.findRegion("boomerang_1x_40x25px"));
                 table4.add(boomerang).padTop(boost.getDrawable().getMinHeight()+35).padLeft(15);
                break;
            case 2:
                 boomerang  = new Image(atlas_boosts.findRegion("boomerang_2x_40x25px"));
                 table4.add(boomerang).padTop(boost.getDrawable().getMinHeight()+35).padLeft(10);
                 break;
            case 3:
                 boomerang  = new Image(atlas_boosts.findRegion("boomerang_3x_40x25px"));
                 table4.add(boomerang).padTop(boost.getDrawable().getMinHeight()+35).padLeft(8);
                 break;
            case 4:
                 boomerang  = new Image(atlas_boosts.findRegion("boomerang_4x_40x25px"));
                 table4.add(boomerang).padTop(boost.getDrawable().getMinHeight()+35).padLeft(5);
                 break;
             default:
                 System.out.println("A value not  supposed to be accessed was accessed in the Hud");
                 return;
        }
        stage.addActor(table4);
    }


    private void addTime(float gameTime){
          float minutes = (float)Math.floor(gameTime / 60.0f);
          float seconds = gameTime - minutes * 60.0f;
         timeLabel.setText(String.format("%.0fm%.0fs", minutes, seconds));
    }

    public int getScore(){
        return (int) gameTime;
    }
    private void addPlayerPos(int playerPosition){
        //How far the player is away from the finish line, and draws the player in the correct position.
        percentage = (float) playerPosition/mapPixelWidth;
        table2.clear();
        table2.add(player).padTop(38+player.getHeight()).padRight(minimapWidth-player.getWidth()+20-(minimapWidth-player.getWidth()+10)*percentage);
    }

    public void setSpeedBoost(){
        speedBoost = true;
        System.out.println("hei");
    }
    public void setSpeedBoostActive(){
        speedBoostActive = true;

    }
    public void setBoomerangCount(int boomerangCount){
        newDisplay = true;
        this.boomerangCount = boomerangCount;
    }
    public void useBoomerang(){
        newDisplay = true;
        boomerangCount -= 1;
    }


    public void giveExtraPoints(){
        extraPoints = true;
        if(table.getCell(scoreLabel) == null){
            table.add(scoreLabel);
        }
        else{
        if(gameTime-extraSecondsForKill <0) {
            gameTime =0;
        }
        else {
            gameTime -= extraSecondsForKill;
        }
        }
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
