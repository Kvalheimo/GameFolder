package blog.gamedevelopmentbox2dtutorial.loader;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.assets.loaders.SkinLoader.SkinParameter;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class B2dAssetManager {

    public final AssetManager manager = new AssetManager();

    // Textures
    public final String playerImage = "images/player.png";
    public final String enemyImage = "images/enemy.png";

    // Sounds
    public final String boingSound = "input/game/sounds/boing.wav";
    public final String pingSound = "input/game/sounds/ping.wav";

    // Music
    public final String playingSong = "input/game/music/music1.mp3";

    // Skin
    public final String skin = "input/game/skin/glassy-ui.json";

    // Textures
    public final String gameImages = "input/game/images/game.atlas";
    public final String loadingImages = "input/game/images/loading.atlas";



    public void queueAddSounds(){
        manager.load(boingSound, Sound.class);
        manager.load(pingSound,Sound.class);
    }

    public void queueAddMusic(){
        manager.load(playingSong, Music.class);
    }


    public void queueAddSkin(){
        SkinParameter skinParameter = new SkinParameter("input/game/skin/glassy-ui.atlas");
        manager.load(skin, Skin.class, skinParameter);
    }

    public void queueAddImages(){
        manager.load(gameImages, TextureAtlas.class);
    }


    public void queueAddLoadingImages(){
        manager.load(loadingImages, TextureAtlas.class);
    }

    public void queueAddFonts(){
    }

    public void queueAddParticleEffects(){
    }

}
