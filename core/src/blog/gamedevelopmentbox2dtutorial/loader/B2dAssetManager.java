package blog.gamedevelopmentbox2dtutorial.loader;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.ParticleEffectLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.assets.loaders.SkinLoader.SkinParameter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
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
    public final String skin = "skin/game/game.json";

    // Textures
    public final String gameImages = "images/game.atlas";
    public final String HUDImages = "minimap/boosts.atlas";

    // Maps
    public final String level1 = "maps/level1.tmx";
    public final String level2 =  "maps/Henriks_verden.tmx";
    public final String level3 =  "maps/level3.tmx";

    // Particle Effects
    public final String smokeEffect = "particles/smoke.p";
    public final String explosionEffect = "particles/explosion.p";
    public final String bloodEffect = "particles/blood.p";
    public final String waterEffect = "particles/trail.p";
    public final String splashEffect = "particles/splash.p";
    public final String speedEffect = "particles/speed.p";
    public final String powerUpEffect = "particles/powerup.p";
    public final String ssRightEffect = "particles/superspeed_right.p";
    public final String ssLeftEffect = "particles/superspeed_left.p";
    public final String testEffect = "particles/test.p";
    public final String bulletRightEffect = "particles/bullet_right.p";
    public final String bulletLeftEffect = "particles/bullet_left.p";
    public final String puSpeedEffect = "particles/powerup_speed.p";
    public final String puGunEffect = "particles/powerup_gun.p";



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
        SkinParameter skinParameter = new SkinParameter("skin/game/game.atlas");
        manager.load(skin, Skin.class, skinParameter);

    }

    public void queueAddImages(){
        manager.load(gameImages, TextureAtlas.class);
    }

    public void queueHUDImages(){
        manager.load(HUDImages, TextureAtlas.class);
    }

    public void queueAddMaps(){
        manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        manager.load(level1, TiledMap.class);
        manager.load(level2, TiledMap.class);
        manager.load(level3, TiledMap.class);


    }

    public void queueAddFonts(){
    }

    public void queueAddParticleEffects(){
        pep.atlasFile = gameImages;
        manager.load(smokeEffect, ParticleEffect.class, pep);
        manager.load(explosionEffect, ParticleEffect.class, pep);
        manager.load(bloodEffect, ParticleEffect.class, pep);
        manager.load(waterEffect, ParticleEffect.class, pep);
        manager.load(splashEffect, ParticleEffect.class, pep);
        manager.load(speedEffect, ParticleEffect.class, pep);
        manager.load(powerUpEffect, ParticleEffect.class, pep);
        manager.load(ssRightEffect, ParticleEffect.class, pep);
        manager.load(ssLeftEffect, ParticleEffect.class, pep);
        manager.load(testEffect, ParticleEffect.class, pep);
        manager.load(bulletRightEffect, ParticleEffect.class, pep);
        manager.load(bulletLeftEffect, ParticleEffect.class, pep);
        manager.load(puSpeedEffect, ParticleEffect.class, pep);
        manager.load(puGunEffect, ParticleEffect.class, pep);



    }

    public void resetParticleEffects(){
        //unload particle effects
        manager.unload(smokeEffect);
        manager.unload(explosionEffect);
        manager.unload(bloodEffect);
        manager.unload(waterEffect);
        manager.unload(splashEffect);
        manager.unload(speedEffect);
        manager.unload(powerUpEffect);
        manager.unload(ssRightEffect);
        manager.unload(ssLeftEffect);
        manager.unload(testEffect);
        manager.unload(bulletRightEffect);
        manager.unload(bulletLeftEffect);
        manager.unload(puSpeedEffect);
        manager.unload(puGunEffect);


        //load particle effects
        pep = new ParticleEffectLoader.ParticleEffectParameter();
        pep.atlasFile = gameImages;
        manager.load(smokeEffect, ParticleEffect.class, pep);
        manager.load(explosionEffect, ParticleEffect.class, pep);
        manager.load(bloodEffect, ParticleEffect.class, pep);
        manager.load(waterEffect, ParticleEffect.class, pep);
        manager.load(splashEffect, ParticleEffect.class, pep);
        manager.load(speedEffect, ParticleEffect.class, pep);
        manager.load(powerUpEffect, ParticleEffect.class, pep);
        manager.load(testEffect, ParticleEffect.class, pep);
        manager.load(bulletRightEffect, ParticleEffect.class, pep);
        manager.load(bulletLeftEffect, ParticleEffect.class, pep);
        manager.load(ssRightEffect, ParticleEffect.class, pep);
        manager.load(ssLeftEffect, ParticleEffect.class, pep);
        manager.load(puSpeedEffect, ParticleEffect.class, pep);
        manager.load(puGunEffect, ParticleEffect.class, pep);

        manager.finishLoading();

    }

    public void resetMaps(int level){
        switch (level){
            case 1:
                manager.unload(level1);
                manager.load(level1, TiledMap.class);
                manager.finishLoading();
                break;
            case 2:
                manager.unload(level2);
                manager.load(level2, TiledMap.class);
                manager.finishLoading();
                break;

            case 3:
                manager.unload(level3);
                manager.load(level3, TiledMap.class);
                manager.finishLoading();
                break;

        }
    }


}

