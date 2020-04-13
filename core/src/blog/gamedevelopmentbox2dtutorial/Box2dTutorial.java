package blog.gamedevelopmentbox2dtutorial;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;

import javax.naming.ldap.Control;

import blog.gamedevelopmentbox2dtutorial.controller.Controller;
import blog.gamedevelopmentbox2dtutorial.loader.B2dAssetManager;
import blog.gamedevelopmentbox2dtutorial.views.EndScreen;
import blog.gamedevelopmentbox2dtutorial.views.HighScoreScreen;
import blog.gamedevelopmentbox2dtutorial.views.Hud;
import blog.gamedevelopmentbox2dtutorial.views.LevelSelectionScreen;
import blog.gamedevelopmentbox2dtutorial.views.LoadingScreen;
import blog.gamedevelopmentbox2dtutorial.views.MainScreen;
import blog.gamedevelopmentbox2dtutorial.views.MenuScreen;
import blog.gamedevelopmentbox2dtutorial.views.PreferenceScreen;

public class Box2dTutorial extends Game {

    public static final float PPM = 64f; //Pixels per meter in box2dWorld
	public static final float PPT = 64f; //Pixels per tile in Tieldmap

	private LoadingScreen loadingScreen;
	private PreferenceScreen preferencesScreen;
	private MenuScreen menuScreen;
	private MainScreen mainScreen;
	private EndScreen endScreen;
	private LevelSelectionScreen levelSelectionScreen;
	private HighScoreScreen highScoreScreen;
	public B2dAssetManager assMan;
	private AppPreferences preferences;
	private Music playingSong;

	public final static int MENU = 0;
	public final static int PREFERENCES = 1;
	public final static int APPLICATION = 2;
	public final static int ENDGAME = 3;
	public final static int LEVEL_SELECTION = 4;
	public final static int HIGHSCORE = 5;
	public final static int MULTIPLAYER = 6;


	public Box2dTutorial() {
		super();
		assMan = new B2dAssetManager();
	}

	public void changeScreen(int screen) {
		changeScreen(screen, false, 0 );
	}


	public void changeScreen(int screen, boolean newGame, int level){
		switch(screen){
			case MENU:
				if(menuScreen == null) menuScreen = new MenuScreen(this);
				mainScreen = null;
				this.setScreen(menuScreen);
				break;
			case PREFERENCES:
				if(preferencesScreen == null) preferencesScreen = new PreferenceScreen(this);
				this.setScreen(preferencesScreen);
				break;
			case APPLICATION:
				if (newGame) {
					assMan.resetParticleEffects();
					mainScreen = new MainScreen(this, level);
				}
				setScreen(mainScreen);
				break;
			case MULTIPLAYER:
				if (newGame) {
					assMan.resetParticleEffects();
					mainScreen = new MainScreen(this, level);
				}
				setScreen(mainScreen);
				break;
			case ENDGAME:
				endScreen = new EndScreen(this, level);
				this.setScreen(endScreen);
				break;
			case LEVEL_SELECTION:
				if(levelSelectionScreen == null) levelSelectionScreen = new LevelSelectionScreen(this);
				this.setScreen(levelSelectionScreen);
				break;
			case HIGHSCORE:
				highScoreScreen = new HighScoreScreen(this);
				this.setScreen(highScoreScreen);
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
		playingSong = assMan.manager.get("music/music1.mp3");
		///playingSong.play();


	}

	public MainScreen getMainScreen(){
		return mainScreen;
	}


	@Override
	public void dispose() {
		playingSong.dispose();
		assMan.manager.dispose();
		super.dispose();
	}

}

