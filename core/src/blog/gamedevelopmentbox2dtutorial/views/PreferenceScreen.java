package blog.gamedevelopmentbox2dtutorial.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import blog.gamedevelopmentbox2dtutorial.Box2dTutorial;

public class PreferenceScreen implements Screen {
    private Box2dTutorial parent;
    private Stage stage;
    private Skin skin;
    private TextureAtlas.AtlasRegion background;
    private TextureAtlas gameAtlas;

    // fields for sliders and checkbox
    private Label titleLabel;
    private Label volumeMusicLabel;
    private Label volumeSoundLabel;
    private Label musicOnOffLabel;
    private Label soundOnOffLabel;

    public PreferenceScreen(Box2dTutorial box2dTutorial){
        parent = box2dTutorial;

        gameAtlas = parent.assMan.manager.get("images/game.atlas");
        skin = parent.assMan.manager.get("skin/game/game.json");

        background = gameAtlas.findRegion("background");


    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();

        if (Box2dTutorial.DEBUG) {
            table.setDebug(true);
        }

        table.setFillParent(true);
        table.setBackground(new TiledDrawable(background));


        // Create sliders and set values
        final Slider volumeMusicSlider = new Slider(0f, 1f, 0.1f, false, skin);
        volumeMusicSlider.setValue(parent.getPreferences().getMusicVolume());

        final Slider volumeSoundSlider = new Slider(0f, 1f, 0.1f, false, skin);
        volumeSoundSlider.setValue(parent.getPreferences().getSoundVolume());


        // Create checkbox and set values
        final CheckBox musicCheckBox = new CheckBox(null, skin);
        musicCheckBox.setChecked(parent.getPreferences().isMusicEnabled());

        final CheckBox soundCheckBox = new CheckBox(null, skin);
        soundCheckBox.setChecked(parent.getPreferences().isSoundEffectsEnabled());


        //Create text button
        final TextButton backButton = new TextButton("Back", skin, "blue-small");



        // Create labels
        titleLabel = new Label("PREFERENCES", skin, "big");
        volumeMusicLabel = new Label("Music Volume", skin);
        volumeSoundLabel = new Label("Sound Volume", skin);
        musicOnOffLabel = new Label("Music on/off", skin);
        soundOnOffLabel = new Label("Sound on/off", skin);


        // Put labels, checkboxes, sliders and buttons to table
        table.add(titleLabel).colspan(2);
        table.row().pad(50,0,0,40);
        table.add(volumeMusicLabel).left();
        table.add(volumeMusicSlider);
        table.row().pad(30,0,0,40);
        table.add(volumeSoundLabel).left();
        table.add(volumeSoundSlider);
        table.row().pad(30,0,0,40);
        table.add(musicOnOffLabel).left();
        table.add(musicCheckBox);
        table.row().pad(30,0,0,40);
        table.add(soundOnOffLabel).left();
        table.add(soundCheckBox);
        table.row().pad(50,0,0,40);
        table.add(backButton).colspan(2);


        stage.addActor(table);

        // Create listeners
        volumeMusicSlider.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                parent.getPreferences().setMusicVolume(volumeMusicSlider.getValue());
                parent.getPlayingSong().setVolume(volumeMusicSlider.getValue());
                return false;
            }
        });

        volumeSoundSlider.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                parent.getPreferences().setSoundVolume(volumeSoundSlider.getValue());
                return false;
            }
        });

        musicCheckBox.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                boolean enabled = musicCheckBox.isChecked();
                parent.getPreferences().setMusicEffectsEnabled(enabled);

                if (parent.getPreferences().isMusicEnabled()) {
                    parent.getPlayingSong().play();

                    if (!parent.getPlayingSong().isLooping()){
                        parent.getPlayingSong().setLooping(true);
                    }
                }else{
                    parent.getPlayingSong().pause();
                    System.out.println("pause song");
                }
                return false;
            }
        });

        soundCheckBox.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                boolean enabled = soundCheckBox.isChecked();
                parent.getPreferences().setSoundEffectsEnabled(enabled);
                return false;
            }
        });

        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                if (parent.getMainScreen() == null){
                    parent.changeScreen(Box2dTutorial.MENU);
                }else{
                    parent.changeScreen(Box2dTutorial.APPLICATION);
                }

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