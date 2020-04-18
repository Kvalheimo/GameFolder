package blog.gamedevelopmentbox2dtutorial.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import blog.gamedevelopmentbox2dtutorial.Box2dTutorial;
import blog.gamedevelopmentbox2dtutorial.DFUtils;

public class CountdownView implements Disposable {
    private static final int IMG_WIDTH = 400;
    private static final int IMG_HEIGHT = 400;


    public Stage stage;
    private Viewport viewport;

    private Table table;
    private IntMap<Image> countdownImages;

    private Image one, two, threee, go;
    private SpriteBatch sb;
    private TextureAtlas gameAtlas;
    private Image currentCountImage;
    private int countDown;
    private float countTime;

    public CountdownView(SpriteBatch sb, Box2dTutorial parent) {
        this.sb = sb;
        countDown = 4;
        countTime = 0;

        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera());
        stage = new Stage(viewport, sb);
        gameAtlas = parent.assMan.manager.get("images/game.atlas");


        TextureRegion[] frames =  DFUtils.spriteSheetToFrames(gameAtlas.findRegion("countdown"), 4, 2);
        countdownImages = new IntMap<>();
        countdownImages.put(4, new Image(frames[4]));
        countdownImages.put(3, new Image(frames[5]));
        countdownImages.put(2, new Image(frames[6]));
        countdownImages.put(1, new Image(frames[7]));

        currentCountImage = countdownImages.get(4);

        table = new Table();
        table.setDebug(true);
        table.setFillParent(true);
        table.center();
        table.add(currentCountImage).size(currentCountImage.getWidth(), currentCountImage.getHeight());

        stage.addActor(table);

    }


    public void update(float dt) {
        countTime += dt;

        if (countTime >= 1){
            countDown -= 1;
            countTime = 0;
        }

        table.clear();

        if (countTime <= 0.9 && countDown > 0) {
            currentCountImage = countdownImages.get(countDown);
            currentCountImage.setSize(IMG_WIDTH,IMG_HEIGHT);
            table.add(currentCountImage).size(currentCountImage.getWidth(), currentCountImage.getHeight());
        }

    }

    public void draw() {
        stage.act();
        stage.draw();
    }

    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    public Stage getStage(){
        return stage;
    }

    public boolean isCountdownOver(){
        return countDown <= 0;
    }

    public void reset(){
        countDown = 4;
        countTime = 0;
        table.clear();
        currentCountImage = countdownImages.get(4);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}




