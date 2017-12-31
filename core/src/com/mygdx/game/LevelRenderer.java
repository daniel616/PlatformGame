package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Danel on 12/30/17.
 */

public class LevelRenderer {
    private OrthographicCamera camera;
    private OrthogonalTiledMapRenderer renderer;
    private Array<Sprite> spriteArray;
    SpriteBatch batch = new SpriteBatch();

    public LevelRenderer(TiledMap map, Array<Sprite> spriteArray){
        camera= new OrthographicCamera();
        camera.setToOrtho(false,900,900);
        this.spriteArray=spriteArray;
        renderer=new OrthogonalTiledMapRenderer(map);
    }

    public void render(float delta) {
        Gdx.gl.glClearColor(0.8f,0.8f,0.8f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        renderer.setView(camera);
        renderer.render();

        batch.begin();
        batch.setProjectionMatrix(camera.combined);
        for(Sprite sprite: spriteArray){
            Rectangle r =sprite.getBoundingRectangle();
            batch.draw(new Texture("data/mininicular (1).png"),r.getX(),r.getY(),r.getWidth(),r.getHeight());
            sprite.draw(batch);
        }
        batch.end();
    }

    public void dispose(){
        renderer.dispose();
    }
}
