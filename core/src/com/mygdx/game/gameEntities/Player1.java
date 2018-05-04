package com.mygdx.game.gameEntities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

/**
 * Created by danielli on 4/19/18.
 */

public class Player1 implements Actor{
    private float jumpVel=10;
    private float runAccel=3.0f;
    private boolean shouldRemove;

    private PhysicsTracker physicsSprite;

    @Override
    public void update(float deltaTime) {
        handleInputs();
    }

    @Override
    public boolean shouldRemove() {
        return shouldRemove;
    }

    @Override
    public void setRemove(boolean remove) {
        shouldRemove=remove;
    }

    private void handleInputs(){
        /*for touchscreens
        boolean leftButton = (Gdx.input.isTouched(0) && x0 < 70) || (Gdx.input.isTouched(1) && x1 < 70);
        boolean rightButton = (Gdx.input.isTouched(0) && x0 > 70 && x0 < 134) || (Gdx.input.isTouched(1) && x1 > 70 && x1 < 134);
        boolean jumpButton = (Gdx.input.isTouched(0) && x0 > 416 && x0 < 480 && y0 < 64)
                || (Gdx.input.isTouched(1) && x1 > 416 && x1 < 480 && y0 < 64);*/

        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            physicsSprite.jump(jumpVel);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            physicsSprite.leftAccel(runAccel);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            physicsSprite.rightAccel(runAccel);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
            //attack();
        }
    }
}
