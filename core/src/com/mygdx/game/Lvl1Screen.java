package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Danel on 12/29/17.
 */

public class Lvl1Screen implements Screen {

    private static final float VIRTUAL_WIDTH = 384.0f;
    private static final float VIRTUAL_HEIGHT=216.0f;
    private static final float CAMERA_SPEED=100.0f;

    private OrthographicCamera camera;
    private Viewport viewport;

    private TextureAtlas atlas;
    private TiledMap map;
    private TmxMapLoader loader;
    private OrthogonalTiledMapRenderer renderer;
    private SpriteBatch batch;
    private Vector2 direction;

    private Sprite player;
    private Sprite goal;

    /**could put this in constrcutor**/
    @Override
    public void show() {
        camera = new OrthographicCamera();

        batch = new SpriteBatch();
        atlas= new TextureAtlas(Gdx.files.internal("data/zombies.txt"));
        //player = new Sprite(atlas.findRegion("female/Attack (6)"));
        //goal = new Sprite(atlas.findRegion("male/Attack (6)"));
        camera.setToOrtho(false, 900,900);
        //viewport= new FitViewport(900,900,camera);


        loader=new TmxMapLoader();
        map=loader.load("data/great_map.tmx");
        renderer=new OrthogonalTiledMapRenderer(map);
        processMapMetadata();

        direction =new Vector2();



    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.8f,0.8f,0.8f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        camera.update();
        renderer.setView(camera);
        renderer.render();

        batch.begin();
        player.draw(batch);
        goal.draw(batch);
        batch.end();



    }

    private void processMapMetadata() {
        // Load music
        /*
        String songPath = map.getProperties().get("music", String.class);
        song = Gdx.audio.newMusic(Gdx.files.internal(songPath));
        song.setLooping(true);
        song.play();*/

        // Load entities
        System.out.println("Searching for game entities...\n");

        MapObjects objects = map.getLayers().get("objects").getObjects();

        for (MapObject object : objects) {
            String name = object.getName();
            String[] parts = name.split("[.]");
            RectangleMapObject rectangleObject = (RectangleMapObject)object;
            Rectangle rectangle = rectangleObject.getRectangle();

            System.out.println("Object found");
            System.out.println("- name: " + name);
            System.out.println("- position: (" + rectangle.x + ", " + rectangle.y + ")");
            System.out.println("- size: (" + rectangle.width + ", " + rectangle.height + ")");

            if (name.equals("player")) {
                player = new Sprite(atlas.findRegion("male/Attack (6)"));
                player.setPosition(rectangle.x, rectangle.y);
            }
            else if (parts.length > 0 && parts[0].equals("trigger")) {
                goal = new Sprite(atlas.findRegion("female/Attack (6)"));
                goal.setPosition(rectangle.x,rectangle.y);
            }
        }
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

        map.dispose();
        renderer.dispose();

    }
}
