package blog.gamedevelopmentbox2dtutorial.loader;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.assets.loaders.SkinLoader.SkinParameter;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class B2dAssetManager {

    public final AssetManager manager = new AssetManager();


    // Sounds
    public final String boingSound = "sounds/boing.wav";
    public final String pingSound = "sounds/ping.wav";

    // Music
    public final String playingSong = "music/music1.mp3";

    // Skin
    public final String skin = "skin/glassy-ui.json";

    // Textures
    public final String gameImages = "images/game.atlas";
    public final String loadingImages = "images/loading.atlas";

    // Maps
    public final String map = "maps/level1.tmx";



    public void queueAddSounds(){
        manager.load(boingSound, Sound.class);
        manager.load(pingSound,Sound.class);
    }

    public void queueAddMusic(){
        manager.load(playingSong, Music.class);
    }


    public void queueAddSkin(){
        SkinParameter skinParameter = new SkinParameter("skin/glassy-ui.atlas");
        manager.load(skin, Skin.class, skinParameter);
    }

    public void queueAddImages(){
        manager.load(gameImages, TextureAtlas.class);
    }


    public void queueAddLoadingImages(){
        manager.load(loadingImages, TextureAtlas.class);
    }

    public void queueAddMaps(){
        manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        manager.load(map, TiledMap.class);
    }

    public void queueAddFonts(){
    }

    public void queueAddParticleEffects(){
    }



}

