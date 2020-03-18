package blog.gamedevelopmentbox2dtutorial.controller;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class KeyboardController implements InputProcessor {

    public boolean LEFT, RIGHT, UP, DOWN, SPACE, A;

    // Mouse flags
    public boolean isMouse1Down, isMouse2Down, isMouse3Down;
    public boolean isDragged;
    public Vector2 mouseLocation = new Vector2();



    @Override
    public boolean keyDown(int keycode) {
        boolean keyProcessed = false;
        switch (keycode) {
            case Input.Keys.LEFT:
                LEFT = true;
                keyProcessed = true;
                break;
            case Input.Keys.RIGHT:
                RIGHT = true;
                keyProcessed = true;
                break;
            case Input.Keys.UP:
                UP = true;
                keyProcessed = true;
                break;
            case Input.Keys.DOWN:
                DOWN = true;
                keyProcessed = true;
                break;
            case Input.Keys.SPACE:
                SPACE = true;
                keyProcessed = true;
                break;
            case Input.Keys.A:
                A = true;
                keyProcessed = true;

        }
        return keyProcessed;
    }


    @Override
    public boolean keyUp(int keycode) {
        boolean keyProcessed = false;
        switch (keycode) {
            case Input.Keys.LEFT:
                LEFT = false;
                keyProcessed = true;
                break;
            case Input.Keys.RIGHT:
                RIGHT = false;
                keyProcessed = true;
                break;
            case Input.Keys.UP:
                UP = false;
                keyProcessed = true;
                break;
            case Input.Keys.DOWN:
                DOWN = false;
                keyProcessed = true;
                break;
            case Input.Keys.SPACE:
                SPACE = false;
                keyProcessed = true;
                break;
            case Input.Keys.A:
                A = false;
                keyProcessed = true;
        }
        return keyProcessed;
    }




    @Override
    public boolean keyTyped(char c) {
        return false;
    }



    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(button == 0){
            isMouse1Down = true;
        }else if(button == 1){
            isMouse2Down = true;
        }else if(button == 2){
            isMouse3Down = true;
        }

        mouseLocation.x = screenX;
        mouseLocation.y = screenY;
        return false;

    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        isDragged = false;
        //System.out.println(button);
        if (button == 0) {
            isMouse1Down = false;
        } else if (button == 1) {
            isMouse2Down = false;
        } else if (button == 2) {
            isMouse3Down = false;
        }
        mouseLocation.x = screenX;
        mouseLocation.y = screenY;
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        isDragged = true;
        mouseLocation.x = screenX;
        mouseLocation.y = screenY;
        return false;
    }



    @Override
    public boolean mouseMoved(int i, int i1) {
        return false;
    }

    @Override
    public boolean scrolled(int i) {
        return false;
    }
}
