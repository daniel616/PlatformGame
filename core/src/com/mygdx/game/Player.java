package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Danel on 1/8/18.
 */

public class Player extends AdvancedSprite {
    public Player(TextureRegion region, GameLevel gameLevel) {
        super(region, gameLevel);
        this.team=TEAM.TEAM1;
    }

    @Override
    public void update(float deltaTime){
        handleInputs();
        super.update(deltaTime);
    }

    private void handleInputs(){
        /*for touchscreens
        boolean leftButton = (Gdx.input.isTouched(0) && x0 < 70) || (Gdx.input.isTouched(1) && x1 < 70);
        boolean rightButton = (Gdx.input.isTouched(0) && x0 > 70 && x0 < 134) || (Gdx.input.isTouched(1) && x1 > 70 && x1 < 134);
        boolean jumpButton = (Gdx.input.isTouched(0) && x0 > 416 && x0 < 480 && y0 < 64)
                || (Gdx.input.isTouched(1) && x1 > 416 && x1 < 480 && y0 < 64);*/

        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            jump();
        }

        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            leftAccel();
        }

        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            rightAccel();
        }

        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
            attack();
        }
    }

    private void attack(){
        TextureRegion region = new TextureRegion(new Texture("data/mininicular (1).png"));
        Attack attack = new Attack(region, this);
        attack.setSize(50,80);
        int intDir= dir ? 1:-1;
        attack.setPosition(getX()+intDir*20,getY());
        getLevel().addAttack(attack);
    }


}
