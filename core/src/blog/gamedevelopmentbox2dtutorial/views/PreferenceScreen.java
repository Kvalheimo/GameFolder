package blog.gamedevelopmentbox2dtutorial.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
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
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import blog.gamedevelopmentbox2dtutorial.Box2dTutorial;

public class PreferenceScreen implements Screen {
    private Box2dTutorial parent;
    private Stage stage;
    private Skin skin1, skin2, skin3;

    // fields for sliders and checkbox
    private Label titleLabel;
    private Label volumeMusicLabel;
    private Label volumeSoundLabel;
    private Label musicOnOffLabel;
    private Label soundOnOffLabel;

    public PreferenceScreen(Box2dTutorial box2dTutorial){
        parent = box2dTutorial;
        skin1 = parent.assMan.manager.get("skin/shade/uiskin.json");
        skin2 = parent.assMan.manager.get("skin/glassy/glassy-ui.json");
        skin3 = parent.assMan.manager.get("skin/clean/clean-crispy-ui.json");

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
        stage.addActor(table);



        // Create sliders and set values
        final Slider volumeMusicSlider = new Slider(0f, 1f, 0.1f, false, skin2);
        volumeMusicSlider.setValue(parent.getPreferences().getMusicVolume());

        final Slider volumeSoundSlider = new Slider(0f, 1f, 0.1f, false, skin2);
        volumeSoundSlider.setValue(parent.getPreferences().getSoundVolume());


        // Create checkbox and set values
        final CheckBox musicCheckBox = new CheckBox(null, skin2);
        musicCheckBox.setChecked(parent.getPreferences().isMusicEnabled());

        final CheckBox soundCheckBox = new CheckBox(null, skin2);
        soundCheckBox.setChecked(parent.getPreferences().isSoundEffectsEnabled());


        //Create text button
        final TextButton backButton = new TextButton("Back", skin2, "small");



        // Create labels
        titleLabel = new Label("Preferences", skin2);
        volumeMusicLabel = new Label("Music Volume", skin2);
        volumeSoundLabel = new Label("Sound Volume", skin2);
        musicOnOffLabel = new Label("Music on/off", skin2);
        soundOnOffLabel = new Label("Sound on/off", skin2);


        // Put labels, checkboxes, sliders and buttons to table
        table.add(titleLabel).colspan(2);
        table.row().pad(10,0,0,10);
        table.add(volumeMusicLabel).left();
        table.add(volumeMusicSlider);
        table.row().pad(10,0,0,10);
        table.add(volumeSoundLabel).left();
        table.add(volumeSoundSlider);
        table.row().pad(10,0,0,10);
        table.add(musicOnOffLabel).left();
        table.add(musicCheckBox);
        table.row().pad(10,0,0,10);
        table.add(soundOnOffLabel).left();
        table.add(soundCheckBox);
        table.row().pad(10,0,0,10);
        table.add(backButton).colspan(2);



        // Create listeners
        volumeMusicSlider.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                parent.getPreferences().setMusicVolume(volumeMusicSlider.getValue());
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