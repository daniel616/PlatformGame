package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Danel on 12/30/17.
 */

public class GameLevel {
    /**
     * Tracks contents of a level and updates them when told to do so.
     */
    private TiledMap map;
    private PlayerData player;
    private Array<Sprite> spriteArray;
    private TextureAtlas atlas;

    /**could put this in constrcutor**/
    public GameLevel(String mapFileLocation){
        map=new TmxMapLoader().load(mapFileLocation);
        atlas= new TextureAtlas(Gdx.files.internal("data/zombies.txt"));
        processMapMetadata();
    }

    public Array<Rectangle> getTerrain(){
        MapObjects terrain=map.getLayers().get("collisionTerrain").getObjects();
        Array<Rectangle> terrainArray=new Array<Rectangle>();
        for(MapObject object: terrain){
            terrainArray.add(((RectangleMapObject)object).getRectangle());
        }
        System.out.println("terrainArray size"+terrainArray.size);
        return terrainArray;
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

        MapObjects special = map.getLayers().get("special").getObjects();


        for (MapObject object : special) {
            String name = object.getName();
            String[] parts = name.split("[.]");
            RectangleMapObject rectangleObject = (RectangleMapObject)object;
            Rectangle rectangle = rectangleObject.getRectangle();

            System.out.println("Object found");
            System.out.println("- name: " + name);
            System.out.println("- position: (" + rectangle.x + ", " + rectangle.y + ")");
            System.out.println("- size: (" + rectangle.width + ", " + rectangle.height + ")");


            if (name.equals("player")) {
                player = new PlayerData(atlas.findRegion("male/Attack (6)"),this);
                player.setPosition(rectangle.x, rectangle.y);
            }
        }
    }

    public void update(float deltaTime){
        player.update(deltaTime);
    }

    public LevelRenderer generateRenderer(){
        spriteArray=new Array<Sprite>();
        spriteArray.add(player);
        return new LevelRenderer(map,spriteArray);
    }

    public void dispose() {
        atlas.dispose();
        map.dispose();
    }
}
