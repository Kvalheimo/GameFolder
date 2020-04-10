package blog.gamedevelopmentbox2dtutorial.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import blog.gamedevelopmentbox2dtutorial.Box2dTutorial;
import blog.gamedevelopmentbox2dtutorial.LoadingBarPart;

public class LoadingScreen implements Screen{
    public final int IMAGE = 0;		// loading images
    public final int FONT = 1;		// loading fonts
    public final int PARTY = 2;		// loading particle effects
    public final int SOUND = 3;		// loading sounds
    public final int MUSIC = 4;		// loading music

    // timer for exiting loading screen
    public float countDown = 1f; // 5 seconds of waiting before menu screen

    private Box2dTutorial parent;
    private TextureAtlas atlas;
    private SpriteBatch sb;
    private Animation<TextureRegion> flameAnimation;
    private TextureAtlas.AtlasRegion title, dash, background, copyright;
    private float stateTime;
    private Stage stage;
    private Image titleImage, copyrightImage;
    private Table table, loadingTable;

    private int currentLoadingStage = 0;

    public LoadingScreen(Box2dTutorial box2dTutorial){
        parent = box2dTutorial;
        stage = new Stage(new ScreenViewport());

        loadAssets();
        // initiate queueing of images but don't start loading
        parent.assMan.queueAddImages();
        System.out.println("Loading images....");

        sb = new SpriteBatch();
        sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
    }

    private void loadAssets(){
        // load loading images and wait until finished
        parent.assMan.queueAddLoadingImages();
        parent.assMan.manager.finishLoading();

        //Load map
        parent.assMan.queueAddMaps();
        parent.assMan.manager.finishLoading();

        //Load skins
        parent.assMan.queueAddSkin();
        parent.assMan.manager.finishLoading();

        //Load HUD
        parent.assMan.queueHUDImages();
        parent.assMan.manager.finishLoading();

        // Get images to display loading progress
        atlas = parent.assMan.manager.get("images/loading.atlas");
        title = atlas.findRegion("staying-alight-logo");
        dash = atlas.findRegion("loading-dash");
        background = atlas.findRegion("flamebackground");
        copyright = atlas.findRegion("copyright");

        flameAnimation = new Animation(0.07f, atlas.findRegions("flames/flames"), Animation.PlayMode.LOOP);
    }



    @Override
    public void show() {
        titleImage = new Image(title);
        copyrightImage = new Image(copyright);

        table = new Table();
        table.setFillParent(true);
        table.setBackground(new TiledDrawable(background));
        table.setDebug(false);

        loadingTable = new Table();
        loadingTable.add(new LoadingBarPart(dash, flameAnimation));
        loadingTable.add(new LoadingBarPart(dash, flameAnimation));
        loadingTable.add(new LoadingBarPart(dash, flameAnimation));
        loadingTable.add(new LoadingBarPart(dash, flameAnimation));
        loadingTable.add(new LoadingBarPart(dash, flameAnimation));
        loadingTable.add(new LoadingBarPart(dash, flameAnimation));
        loadingTable.add(new LoadingBarPart(dash, flameAnimation));
        loadingTable.add(new LoadingBarPart(dash, flameAnimation));
        loadingTable.add(new LoadingBarPart(dash, flameAnimation));
        loadingTable.add(new LoadingBarPart(dash, flameAnimation));

        table.add(titleImage).align(Align.center).pad(10, 0, 0 ,0).colspan(10);
        table.row();
        table.add(loadingTable).width(600);
        table.row();
        table.add(copyrightImage);

        stage.addActor(table);


    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1); //  clear the screen
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        // check if the asset manager has finished loading
        if (parent.assMan.manager.update()) { // Load some, will return true if done loading
            currentLoadingStage+= 1;

            if (currentLoadingStage <= 5){
                loadingTable.getCells().get((currentLoadingStage-1)*2).getActor().setVisible(true);
                loadingTable.getCells().get((currentLoadingStage-1)*2+1).getActor().setVisible(true);
            }

            switch(currentLoadingStage){
                case FONT:
                    System.out.println("Loading fonts....");
                    parent.assMan.queueAddFonts(); // first load done, now start fonts
                    break;
                case PARTY:
                    System.out.println("Loading Particle Effects....");
                    parent.assMan.queueAddParticleEffects(); // fonts are done now do party effects
                    break;
                case SOUND:
                    System.out.println("Loading Sounds....");
                    parent.assMan.queueAddSounds();
                    break;
                case MUSIC:
                    System.out.println("Loading music....");
                    parent.assMan.queueAddMusic();
                    break;
                case 5:
                    System.out.println("Finished"); // all done
                    break;
            }
            if (currentLoadingStage >5){
                countDown -= delta;  // timer to stay on loading screen for short preiod once done loading
                currentLoadingStage = 5;  // cap loading stage to 5 as will use later to display progress bar anbd more than 5 would go off the screen
                if(countDown < 0){ // countdown is complete
                    parent.changeScreen(Box2dTutorial.MENU);  /// go to menu screen
                }
            }
        }

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int i, int i1) {

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
