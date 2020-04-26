package blog.gamedevelopmentbox2dtutorial;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class AppPreferences {


    private static final String PREF_MUSIC_VOLUME = "volume";
    private static final String PREF_MUSIC_ENABLED = "music.enabled";
    private static final String PREF_SOUND_ENABLED = "sound.enabled";
    private static final String PREF_SOUND_VOL = "sound";
    private static final String PREF_NAME = "BoomerangBeast";

    protected Preferences getPreferences(){
        return Gdx.app.getPreferences(PREF_NAME);
    }

    public boolean isSoundEffectsEnabled(){
        return getPreferences().getBoolean(PREF_SOUND_ENABLED, true);
    }

    public void setSoundEffectsEnabled(boolean soundEffectsEnabled){
        getPreferences().putBoolean(PREF_SOUND_ENABLED, soundEffectsEnabled);
        getPreferences().flush();
    }

    public boolean isMusicEnabled(){
        return getPreferences().getBoolean(PREF_MUSIC_ENABLED, true);
    }

    public void setMusicEffectsEnabled(boolean musicEnabled){
        getPreferences().putBoolean(PREF_MUSIC_ENABLED, musicEnabled);
        getPreferences().flush();
    }

    public float getMusicVolume(){
        return getPreferences().getFloat(PREF_MUSIC_VOLUME, 0.5f);
    }

    public void setMusicVolume(float volume){
        getPreferences().putFloat(PREF_MUSIC_VOLUME, volume);
        getPreferences().flush();

    }

    public float getSoundVolume() {
        return getPreferences().getFloat(PREF_SOUND_VOL, 0.5f);
    }

    public void setSoundVolume(float volume) {
        getPreferences().putFloat(PREF_SOUND_VOL, volume);
        getPreferences().flush();
    }





}
