package com.mygdx.game.gameEntities;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.GameLevel;
import com.mygdx.game.gameEntities.AdvancedSprite;

/**
 * Created by Danel on 1/8/18.
 */

public class MovingAttack implements Actor{
    private Rectangle boundingRectangle;
    private AdvancedSprite attackSource;
    private float decayTime=0.2f;
    private boolean shouldRemove;
    private AdvancedSprite.TEAM team;

    public MovingAttack(TextureRegion region, GameLevel gameLevel, float x, float y, float width, float height){
        boundingRectangle=new Rectangle(x,y, width,height);
    }

    public MovingAttack(TextureRegion region, AdvancedSprite attackSource){
        this(region,attackSource.getLevel(), attackSource.getX(),attackSource.getY(),
                region.getRegionWidth(),region.getRegionHeight());
        this.team=attackSource.team;
    }

    public Rectangle getRectangle(){
        return boundingRectangle;
    }

    public AdvancedSprite.TEAM getTeam(){
        return team;
    }

    public void affectSprite(AdvancedSprite sprite){
        sprite.takeDamage(3);
    }


    public void update(float deltaTime){
        decayTime-=deltaTime;
        if(decayTime<=0){
            shouldRemove=true;
        }
    }

    @Override
    public boolean shouldRemove() {
        return shouldRemove;
    }

    @Override
    public void setRemove(boolean remove) {
        shouldRemove=remove;
    }
}
