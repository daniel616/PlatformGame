package com.mygdx.game.gameEntities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.GameLevel;

/**
 * Created by Danel on 12/30/17.
 *
 * If this was my 308 class, I'd probably make this class only concerned with tracking physics and directions, and
 * put the health and attack functionality somewhere else. But this works for me.
 *
 */

public class PhysicsTracker extends Sprite {
    public Vector2 vel=new Vector2();
    Vector2 accel = new Vector2();
    static float GRAVITY = 20.0f;
    static float MAX_VEL = 6f;
    static final float DAMP = 0.90f;

    private Array<Rectangle> terrainArray;

    public boolean dir=true;

    public PhysicsTracker(TextureRegion region, Array<Rectangle> terrain){
        super(region);
        this.terrainArray=terrain;
        setSize(50,80);
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

    protected void jump(float velocity){
        vel.y=velocity;
    }

    protected void leftAccel(float accelIncrement){
        boolean prevDir=dir;
        dir=false;
        int intDir=dir ? 1 : -1 ;
        accel.x= intDir*accelIncrement;
        flip((prevDir!=dir),false);
    }

    protected void rightAccel(float accelIncrement){
        boolean prevDir=dir;
        dir=true;
        int intDir=dir ? 1 : -1 ;
        accel.x= intDir*accelIncrement;
        flip((prevDir!=dir),false);
    }

    /**
     * find bounding rectangle, adjust values of rectangle, and then copy x and y onto sprite.
     */
    protected void tryMove(){
        Rectangle bounds = this.getBoundingRectangle();

        bounds.x += vel.x;

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