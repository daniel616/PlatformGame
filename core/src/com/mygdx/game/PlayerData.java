package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Danel on 12/30/17.
 */

public class PlayerData extends Sprite {
    public int health;

    public Vector2 vel=new Vector2();
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

    GameLevel gameLevel;


    public PlayerData(TextureRegion region, GameLevel gameLevel){
        super(region);
        this.gameLevel=gameLevel;
    }

    public void update(float deltaTime){
        handleInputs();

        accel.y=-GRAVITY;
        accel.scl(deltaTime);
        vel.add(accel.x,accel.y);

        vel.x*=DAMP;
        if (vel.x > MAX_VEL) vel.x = MAX_VEL;
        if (vel.x < -MAX_VEL) vel.x = -MAX_VEL;

        vel.scl(deltaTime);
        tryMove();
        vel.scl(1/deltaTime);
    }

    private void handleInputs(){
        /*for touchscreens
        boolean leftButton = (Gdx.input.isTouched(0) && x0 < 70) || (Gdx.input.isTouched(1) && x1 < 70);
        boolean rightButton = (Gdx.input.isTouched(0) && x0 > 70 && x0 < 134) || (Gdx.input.isTouched(1) && x1 > 70 && x1 < 134);
        boolean jumpButton = (Gdx.input.isTouched(0) && x0 > 416 && x0 < 480 && y0 < 64)
                || (Gdx.input.isTouched(1) && x1 > 416 && x1 < 480 && y0 < 64);*/

        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            vel.y=jumpVelocity;
            state=JUMP;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            state=RUN;
            dir=LEFT;
            accel.x=dir*runAcceleration;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            state=RUN;
            dir=RIGHT;
            accel.x=dir*runAcceleration;
        }

        tryMove();
    }

    /**
     * find bounding rectangle, adjust values of rectangle, and then copy x and y onto sprite.
     */
    private void tryMove(){
        Rectangle bounds = this.getBoundingRectangle();

        bounds.x += vel.x;

        Array<Rectangle> terrainArray=gameLevel.getTerrain();

        for (int i = 0; i < terrainArray.size; i++) {
            Rectangle terrain = terrainArray.get(i);
            if (bounds.overlaps(terrain)) {
                if (vel.x < 0) {
                    bounds.x = terrain.x + terrain.width + 0.01f;
                    System.out.println("overlap occured and we did sth about it");
                }
                else
                    bounds.x = terrain.x - bounds.width - 0.01f;
                vel.x = 0;
            }
        }

        bounds.y += vel.y;

        for (int i = 0; i < terrainArray.size; i++) {
            Rectangle terrain=terrainArray.get(i);
            if (bounds.overlaps(terrain)) {
                if (vel.y < 0) {
                    bounds.y = terrain.y + terrain.height + 0.01f;
                    System.out.println("vertical overlap");
                    if (state != DYING && state != SPAWN) state = Math.abs(accel.x) > 0.1f ? RUN : IDLE;
                } else
                    bounds.y = terrain.y - bounds.height - 0.01f;
                vel.y = 0;
            }
        }


        this.setX(bounds.x);
        this.setY(bounds.y);
        /**
        pos.x = bounds.x - 0.2f;
        pos.y = bounds.y;**/

    }
}
