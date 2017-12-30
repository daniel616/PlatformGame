package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.Map;

/**
 * Created by Danel on 12/30/17.
 */

public class PlayerData extends Sprite {
    public int health;

    public Vector2 velocity;
    Vector2 pos = new Vector2();
    Vector2 accel = new Vector2();
    float jumpVelocity=10;
    float runAcceleration=20;
    static float GRAVITY = 20.0f;
    static float MAX_VEL = 6f;
    static final float DAMP = 0.90f;

    static final int IDLE = 0;
    static final int RUN = 1;
    static final int JUMP = 2;
    static final int SPAWN = 3;
    static final int DYING = 4;
    static final int DEAD = 5;
    static final int LEFT = -1;
    static final int RIGHT = 1;


    int state = SPAWN;
    float stateTime = 0;
    int dir = LEFT;
    Map map;


    public PlayerData(TextureRegion region){
        super(region);
    }

    public void update(float deltaTime){
        handleInputs();

        accel.y=-GRAVITY;
        accel.scl(deltaTime);
        velocity.add(accel.x,accel.y);

        velocity.x*=DAMP;
        if (velocity.x > MAX_VEL) velocity.x = MAX_VEL;
        if (velocity.x < -MAX_VEL) velocity.x = -MAX_VEL;

        velocity.scl(deltaTime);
        tryMove();
        velocity.scl(1/deltaTime);
    }

    private void handleInputs(){
        /*for touchscreens
        boolean leftButton = (Gdx.input.isTouched(0) && x0 < 70) || (Gdx.input.isTouched(1) && x1 < 70);
        boolean rightButton = (Gdx.input.isTouched(0) && x0 > 70 && x0 < 134) || (Gdx.input.isTouched(1) && x1 > 70 && x1 < 134);
        boolean jumpButton = (Gdx.input.isTouched(0) && x0 > 416 && x0 < 480 && y0 < 64)
                || (Gdx.input.isTouched(1) && x1 > 416 && x1 < 480 && y0 < 64);*/

        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            velocity.y=jumpVelocity;
            state=JUMP;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            state=RUN;
            dir=LEFT;
            accel.x=dir*runAcceleration;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            state=RUN;
            dir=RIGHT;
            accel.x=dir*runAcceleration;
        }
    }

    private void tryMove(){

    }
}
