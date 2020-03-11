package com.platformer.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.platformer.game.Screens.menustates.MainMenuScreen;

public class PlatformerGame extends Game {
	public static final int V_WIDTH = 400;
	public static final int V_HEIGHT = 208;

	public	SpriteBatch batch;



	@Override
	public void create () {
		batch = new SpriteBatch();
		//Skifter til hvilken state/screen som vises ved oppstart.
		setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		super.dispose();
		batch.dispose();
	}
}
