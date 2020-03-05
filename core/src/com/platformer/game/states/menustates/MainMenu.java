package com.platformer.game.states.menustates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.platformer.game.states.GameState;
import com.platformer.game.states.GameStateManager;
import com.platformer.game.states.State;

public class MainMenu extends State {
    protected MainMenu(GameStateManager gsm) {
        super(gsm);
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()){
            gsm.set(new GameState(gsm));
            dispose();
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        //sb.draw();
        sb.end();
    }

    @Override
    public void dispose() {

    }
}
