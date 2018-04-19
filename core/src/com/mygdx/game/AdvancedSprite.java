package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Danel on 12/30/17.
 *
 * If this was my 308 class, I'd probably make this class only concerned with tracking physics and directions, and
 * put the health and attack functionality somewhere else. But this works for me.
 */

public class AdvancedSprite extends Sprite {
    public int health=10;
    public boolean shouldRemove=false;

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
    boolean dir=true;

    private GameLevel gameLevel;
    public TEAM team;

    public enum TEAM{
        TEAM1,TEAM2
    }


    public AdvancedSprite(TextureRegion region, GameLevel gameLevel){
        super(region);
        this.gameLevel=gameLevel;

        this.attackHeight=this.getBoundingRectangle().getHeight();
        this.attackWidth=this.getBoundingRectangle().getWidth();
        setSize(50,80);
    }

    /***
     * TODO: Right now this does nothing
     * @param deltaTime
     */
    public void update(float deltaTime){
        applyPhysics(deltaTime);
        checkAttacks();
        checkHealth();
    }

    public void takeDamage(int damage){
        health-=damage;
    }

    public void checkHealth(){
        if(health<=0){
            shouldRemove=true;
        }
    }

    private void applyPhysics(float deltaTime){
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

    protected void jump(){
        vel.y=jumpVelocity;
        state=JUMP;
    }

    protected void leftAccel(){
        state=RUN;
        boolean prevDir=dir;
        dir=false;
        int intDir=dir ? 1 : -1 ;
        accel.x= intDir*runAcceleration;
        flip((prevDir!=dir),false);
    }

    protected void rightAccel(){
        state=RUN;
        boolean prevDir=dir;
        dir=true;
        int intDir=dir ? 1 : -1 ;
        accel.x= intDir*runAcceleration;
        flip((prevDir!=dir),false);
    }

    protected GameLevel getLevel(){
        return  gameLevel;
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



    private void checkAttacks(){
        for(Attack attack:getLevel().getAttacks()){
            if((attack.getRectangle().overlaps(getBoundingRectangle()))
                    &&(attack.getTeam()!=(team)))
            {
                attack.affectSprite(this);
            }
        }
    }
}
