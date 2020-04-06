package blog.gamedevelopmentbox2dtutorial.loader;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.ParticleEffectLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.assets.loaders.SkinLoader.SkinParameter;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class B2dAssetManager {
    ParticleEffectLoader.ParticleEffectParameter pep;


    public final AssetManager manager = new AssetManager();


    // Sounds
    public final String boingSound = "sounds/boing.wav";
    public final String pingSound = "sounds/ping.wav";

    // Music
    public final String playingSong = "music/music1.mp3";

    // Skin
    public final String skin1 = "skin/shade/uiskin.json";
    public final String skin2 = "skin/glassy/glassy-ui.json";
    public final String skin3 = "skin/clean/clean-crispy-ui.json";



    // Textures
    public final String gameImages = "images/game.atlas";
    public final String loadingImages = "images/loading.atlas";

    // Maps
    public final String map = "maps/level1.tmx";

    // Particle Effects
    public final String smokeEffect = "particles/smoke.p";
    public final String dustEffect = "particles/dust.p";
    public final String explosionEffect = "particles/explosion.p";
    public final String bloodEffect = "particles/blood.p";
    public final String waterEffect = "particles/water.p";
    public final String splashEffect = "particles/splash.p";
    public final String lazerEffect = "particles/lazer.p";


    public B2dAssetManager(){
        pep = new ParticleEffectLoader.ParticleEffectParameter();
    }


    public void queueAddSounds(){
        manager.load(boingSound, Sound.class);
        manager.load(pingSound,Sound.class);
    }

    public void queueAddMusic(){
        manager.load(playingSong, Music.class);
    }


    public void queueAddSkin(){
        SkinParameter skinParameter1 = new SkinParameter("skin/shade/uiskin.atlas");
        SkinParameter skinParameter2 = new SkinParameter("skin/glassy/glassy-ui.atlas");
        SkinParameter skinParameter3 = new SkinParameter("skin/clean/clean-crispy-ui.atlas");

        manager.load(skin1, Skin.class, skinParameter1);
        manager.load(skin2, Skin.class, skinParameter2);
        manager.load(skin3, Skin.class, skinParameter3);

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
        pep.atlasFile = gameImages;
        manager.load(smokeEffect, ParticleEffect.class, pep);
        manager.load(dustEffect, ParticleEffect.class, pep);
        manager.load(explosionEffect, ParticleEffect.class, pep);
        manager.load(bloodEffect, ParticleEffect.class, pep);
        manager.load(waterEffect, ParticleEffect.class, pep);
        manager.load(splashEffect, ParticleEffect.class, pep);
        manager.load(lazerEffect, ParticleEffect.class, pep);

    }

    public void resetParticleEffects(){
        //unload particle effects
        manager.unload(smokeEffect);
        manager.unload(dustEffect);
        manager.unload(explosionEffect);
        manager.unload(bloodEffect);
        manager.unload(waterEffect);
        manager.unload(splashEffect);

        //load particle effects
        pep = new ParticleEffectLoader.ParticleEffectParameter();
        pep.atlasFile = gameImages;
        manager.load(smokeEffect, ParticleEffect.class, pep);
        manager.load(dustEffect, ParticleEffect.class, pep);
        manager.load(explosionEffect, ParticleEffect.class, pep);
        manager.load(bloodEffect, ParticleEffect.class, pep);
        manager.load(waterEffect, ParticleEffect.class, pep);
        manager.load(splashEffect, ParticleEffect.class, pep);
        manager.load(lazerEffect, ParticleEffect.class, pep);
        manager.finishLoading();

    }


}

