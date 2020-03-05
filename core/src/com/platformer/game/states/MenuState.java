package com.platformer.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.platformer.game.PlatformerGame;

public class MenuState extends State {
    private Texture background;


    public MenuState(GameStateManager gsm) {
        super(gsm);
    }

    @Override
    public void handleInput() {
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        //sb.draw(....
        sb.end();
    }

    @Override
    public void dispose() {
    }

}