package com.platformer.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.platformer.game.states.GameStateManager;
import com.platformer.game.states.menustates.MainMenu;

public class PlatformerGame extends ApplicationAdapter {
	SpriteBatch batch;
	private GameStateManager gsm;
	public static final int HEIGHT = 800;
	public static final int WIDTH = 480;

	@Override
	public void create () {
		batch = new SpriteBatch();
		gsm = gsm.getInstance();
		gsm.push(new MainMenu(gsm));
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
