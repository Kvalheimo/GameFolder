package com.platformer.game.states;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

public final class GameStateManager {

    private static GameStateManager INSTANCE = null;

    private Stack<State> stateStack;

    private GameStateManager() {
        stateStack = new Stack<State>();
    }

    public static GameStateManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GameStateManager();
        }
        return INSTANCE;
    }

    public void push(State state) {
        stateStack.push(state);
    }

    public void pop() {
        stateStack.pop();
    }

    public void set(State state) {
        stateStack.pop();
        stateStack.push(state);
    }

    public void update(float dt) {
        stateStack.peek().update(dt);
    }

    public void render(SpriteBatch sb) {
        stateStack.peek().render(sb);
    }
}