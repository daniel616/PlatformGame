package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Danel on 1/8/18.
 */

public class Attack extends AdvancedSprite {
    private Rectangle boundingRectangle;
    private AdvancedSprite attackSource;
    private float decayTime=0.2f;
    private AdvancedSprite.TEAM team;

    public Attack(TextureRegion region, GameLevel gameLevel, float x, float y, float width, float height){
        super(region, gameLevel);
        boundingRectangle=new Rectangle(x,y, width,height);
    }

    public Attack(TextureRegion region, AdvancedSprite attackSource){
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
        shouldRemove=true;
    }


    public void update(float deltaTime){
        decayTime-=deltaTime;
        if(decayTime<=0){
            shouldRemove=true;
        }
    }


}
