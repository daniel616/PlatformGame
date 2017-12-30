package com.mygdx.game;

import com.badlogic.gdx.Game;

/**
 * Created by Danel on 12/29/17.
 */

public class PlatformGame extends Game {
    @Override
    public void create() {
        setScreen(new Level1());
    }
}
