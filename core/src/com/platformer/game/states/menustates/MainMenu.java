package com.platformer.game.states.menustates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.platformer.game.PlatformerGame;
import com.platformer.game.states.GameState;
import com.platformer.game.states.GameStateManager;
import com.platformer.game.states.MenuState;
import com.platformer.game.states.gamestates.Singleplayer;

public class MainMenu extends MenuState {

    private Texture background;

    public MainMenu(GameStateManager gsm) {
        super(gsm);
        background = new Texture("main_menu_temp.png");

    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){
            gsm.set(new Singleplayer(gsm));
            dispose();
        }
    }

    @Override
    public void update(float dt) {
        super.update(dt);
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(background, 0, 0, PlatformerGame.WIDTH, PlatformerGame.HEIGHT);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
    }
}
