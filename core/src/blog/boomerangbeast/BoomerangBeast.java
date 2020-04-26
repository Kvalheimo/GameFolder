package blog.boomerangbeast;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.audio.Music;

import blog.boomerangbeast.highscore.Save;
import blog.boomerangbeast.loader.B2dAssetManager;
import blog.boomerangbeast.views.menus.CharacterSelectionScreen;
import blog.boomerangbeast.views.menus.EndScreen;
import blog.boomerangbeast.views.menus.HighScoreScreen;
import blog.boomerangbeast.views.menus.LevelSelectionScreen;
import blog.boomerangbeast.views.menus.LoadingScreen;
import blog.boomerangbeast.views.SingleplayerScreen;
import blog.boomerangbeast.views.menus.MenuScreen;
import blog.boomerangbeast.views.MultiplayerScreen;
import blog.boomerangbeast.views.menus.PreferenceScreen;

public class BoomerangBeast extends Game {

    public static final float PPM = 64f; //Pixels per meter in box2dWorld
	public static final float PPT = 64f; //Pixels per tile in Tieldmap
	public static final float GRAVITY = 20f;
	public static final boolean DEBUG = false;

	private LoadingScreen loadingScreen;
	private PreferenceScreen preferencesScreen;
	private MenuScreen menuScreen;
	private SingleplayerScreen singleplayerScreen;
	private EndScreen endScreen;
	private LevelSelectionScreen levelSelectionScreen;
	private CharacterSelectionScreen characterSelectionScreen;
	private HighScoreScreen highScoreScreen;
	private MultiplayerScreen multiplayerScreen;
	public B2dAssetManager assMan;
	private AppPreferences preferences;
	private Music playingSong;
	private Boolean isOnline;

	public final static int MENU = 0;
	public final static int PREFERENCES = 1;
	public final static int APPLICATION = 2;
	public final static int ENDGAME = 3;
	public final static int LEVEL_SELECTION = 4;
	public final static int CHARACTER_SELECTION = 5;
	public final static int HIGHSCORE = 6;
	public final static int MULTIPLAYER = 7;



	public BoomerangBeast() {
		super();
		assMan = new B2dAssetManager();
	}


	public void changeScreen(int screen, int level){
		changeScreen(screen, false, level, 0);
	}

	public void changeScreen(int screen) {
		changeScreen(screen, false, 0 , 0);
	}

	public void changeScreen(int screen, boolean newGame, int level, int character){
		switch(screen){
			case MENU:
			    if(menuScreen == null) menuScreen = new MenuScreen(this);
				singleplayerScreen = null;
				this.setScreen(menuScreen);
				break;
			case PREFERENCES:
				if(preferencesScreen == null) preferencesScreen = new PreferenceScreen(this);
				this.setScreen(preferencesScreen);
				break;
			case APPLICATION:
				if (newGame) {
					assMan.resetParticleEffects();
					assMan.resetMaps(level);
					singleplayerScreen = new SingleplayerScreen(this, level, character);
				}
				this.setScreen(singleplayerScreen);
				break;
			case ENDGAME:
				endScreen = new EndScreen(this, level);
				this.setScreen(endScreen);
				break;
			case LEVEL_SELECTION:
				levelSelectionScreen = new LevelSelectionScreen(this, character);
				this.setScreen(levelSelectionScreen);
				break;
			case CHARACTER_SELECTION:
				if(characterSelectionScreen == null) characterSelectionScreen = new CharacterSelectionScreen(this);
				this.setScreen(characterSelectionScreen);
				break;
			case HIGHSCORE:
				highScoreScreen = new HighScoreScreen(this, level);
				this.setScreen(highScoreScreen);
				break;
			case MULTIPLAYER:
				if (newGame) {
					assMan.resetParticleEffects();
					assMan.resetMaps(level);
					multiplayerScreen = new MultiplayerScreen(this, level, character);
				}
				this.setScreen(multiplayerScreen);
				break;
		}

	}

	public Boolean isOnline() {
		return isOnline;
	}

	public void setOnline(Boolean online) {
		isOnline = online;
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
		playingSong = assMan.manager.get("music/music.wav");
		setPreferences();
        Save.load();
	}

	public SingleplayerScreen getSingleplayerScreen(){
		return singleplayerScreen;
	}


	public Music getPlayingSong(){
		return playingSong;
	}


	private void setPreferences() {

		playingSong.setVolume(getPreferences().getMusicVolume());

		if (getPreferences().isMusicEnabled()) {
			playingSong.play();
			playingSong.setLooping(true);

		}
	}


	@Override
	public void dispose() {
		playingSong.dispose();
		assMan.manager.dispose();
		super.dispose();
	}

}

