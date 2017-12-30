package com.mygdx.game;

import com.badlogic.gdx.Screen;

/**
 * Created by Danel on 12/30/17.
 */

public class Level1 implements Screen {
    GameLevel levelData;
    LevelRenderer levelRenderer;

    public Level1(){
        levelData=new GameLevel("data/great_map.tmx");
        levelRenderer=levelData.generateRenderer();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        levelData.update(delta);
        levelRenderer.render(delta);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        levelData.dispose();
        levelRenderer.dispose();
    }
}
