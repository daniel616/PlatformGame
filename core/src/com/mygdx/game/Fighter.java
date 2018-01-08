package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Danel on 12/30/17.
 */

public class Fighter extends Sprite {
    public int health=10;

    public Vector2 vel=new Vector2();
    Vector2 accel = new Vector2();
    float jumpVelocity=10;
    float runAcceleration=20;
    static float GRAVITY = 20.0f;
    static float MAX_VEL = 6f;
    static final float DAMP = 0.90f;
    final float attackHeight;
    final float attackWidth;

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
    public TEAM team;

    public enum TEAM{
        TEAM1,TEAM2
    }


    public Fighter(TextureRegion region, GameLevel gameLevel){
        super(region);
        this.gameLevel=gameLevel;
        this.attackHeight=this.getBoundingRectangle().getHeight();
        this.attackWidth=this.getBoundingRectangle().getWidth();
    }

    /***
     * TODO: Right now this does nothing
     * @param deltaTime
     */
    public void update(float deltaTime){
        tryMove();

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

    public void takeDamage(int damage){
        health-=damage;
    }

    protected void jump(){
        vel.y=jumpVelocity;
        state=JUMP;
    }

    protected void leftAccel(){
        state=RUN;
        dir=LEFT;
        accel.x=dir*runAcceleration;
    }

    protected void rightAccel(){
        state=RUN;
        dir=RIGHT;
        accel.x=dir*runAcceleration;
    }

    protected void attack(){
        TextureRegion region = new TextureRegion(new Texture("data/mininicular (1).png"));
        ZombieAttack attack = new ZombieAttack(region,this);
        attack.setPosition(getX(),getY());
        gameLevel.addAttack(attack);
    }

    /**
     * find bounding rectangle, adjust values of rectangle, and then copy x and y onto sprite.
     */
    protected void tryMove(){
        Rectangle bounds = this.getBoundingRectangle();

        bounds.x += vel.x;

        Array<Rectangle> terrainArray=gameLevel.getTerrain();

        for (int i = 0; i < terrainArray.size; i++) {
            Rectangle terrain = terrainArray.get(i);
            if (bounds.overlaps(terrain)) {
                if (vel.x < 0) {
                    bounds.x = terrain.x + terrain.width + 0.01f;
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
