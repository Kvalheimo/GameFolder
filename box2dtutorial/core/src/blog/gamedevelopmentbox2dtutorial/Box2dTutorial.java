package blog.gamedevelopmentbox2dtutorial;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;

import blog.gamedevelopmentbox2dtutorial.loader.B2dAssetManager;
import blog.gamedevelopmentbox2dtutorial.views.EndScreen;
import blog.gamedevelopmentbox2dtutorial.views.LoadingScreen;
import blog.gamedevelopmentbox2dtutorial.views.MainScreen;
import blog.gamedevelopmentbox2dtutorial.views.MenuScreen;
import blog.gamedevelopmentbox2dtutorial.views.PreferenceScreen;

public class Box2dTutorial extends Game {
	public static final int V_WIDTH = 400;
	public static final int V_HEIGHT = 208;

	private LoadingScreen loadingScreen;
	private PreferenceScreen preferencesScreen;
	private MenuScreen menuScreen;
	private MainScreen mainScreen;
	private EndScreen endScreen;
	private AppPreferences preferences;
	public B2dAssetManager assMan = new B2dAssetManager();
	private Music playingSong;

	public final static int MENU = 0;
	public final static int PREFERENCES = 1;
	public final static int APPLICATION = 2;
	public final static int ENDGAME = 3;


	public Box2dTutorial() {
		super();
	}

	public void changeScreen(int screen) {
		switch (screen) {
			case MENU:
				if (menuScreen == null) menuScreen = new MenuScreen(this);
					this.setScreen(menuScreen);
					break;

			case PREFERENCES:
				if (preferencesScreen == null) preferencesScreen = new PreferenceScreen(this);
					this.setScreen(preferencesScreen);
					break;

			case APPLICATION:
				if (mainScreen == null) mainScreen = new MainScreen(this);
					this.setScreen(mainScreen);
					break;

			case ENDGAME:
				if (endScreen == null) endScreen = new EndScreen(this);
					this.setScreen(endScreen);
					break;
		}

	}

	public AppPreferences getPreferences(){
		return this.preferences;
	}

	@Override
	public void create() {
		loadingScreen = new LoadingScreen(this);
		preferences = new AppPreferences();
		setScreen(loadingScreen);

		//Load and start music
		assMan.queueAddMusic();
		assMan.manager.finishLoading();
		playingSong = assMan.manager.get("input/game/music/music1.mp3");
		playingSong.play();


	}


	@Override
	public void dispose() {
		playingSong.dispose();
		assMan.manager.dispose();
		super.dispose();
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void setScreen(Screen screen) {
		super.setScreen(screen);
	}

	@Override
	public Screen getScreen() {
		return super.getScreen();
	}
}

