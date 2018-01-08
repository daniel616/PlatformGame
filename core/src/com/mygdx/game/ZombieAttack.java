package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Danel on 1/8/18.
 */

public class ZombieAttack extends Sprite {
    private Rectangle boundingRectangle;
    private Fighter attackSource;
    private float decayTime=1.0f;
    private Fighter.TEAM team;
    private boolean shouldRemove=false;

    public ZombieAttack(TextureRegion region,float x, float y, float width, float height){
        super(region);
        boundingRectangle=new Rectangle(x,y, width,height);
    }

    public ZombieAttack(TextureRegion region, Fighter attackSource){
        this(region,attackSource.getX(),attackSource.getY(),region.getRegionWidth(),region.getRegionHeight());
        this.team=attackSource.team;
    }

    public Rectangle getRectangle(){
        return boundingRectangle;
    }

    public Fighter.TEAM getTeam(){
        return team;
    }

    public boolean shouldRemove(){
        return shouldRemove;
    }

    public void update(float deltaTime){
        decayTime-=deltaTime;
        if(decayTime<=0){
            shouldRemove=true;
        }
    }


}
