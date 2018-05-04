package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.gameEntities.AdvancedSprite;
import com.mygdx.game.gameEntities.Attack;
import com.mygdx.game.gameEntities.Player;

/**
 * Created by Danel on 12/30/17.
 */

public class GameLevel {
    /**
     * Tracks contents of a level and updates them when told to do so.
     */
    private TiledMap map;
    private Array<AdvancedSprite> fighters;
    private Array<Attack> attacks;
    private TextureAtlas atlas;

    /**could put this in constructor**/
    public GameLevel(String mapFileLocation){
        fighters=new Array<AdvancedSprite>();
        attacks= new Array<Attack>();
        map=new TmxMapLoader().load(mapFileLocation);
        atlas= new TextureAtlas(Gdx.files.internal("data/zombies.txt"));
        processMapMetadata();

    }

    public LevelRenderer generateRenderer(){
        return new LevelRenderer(map,fighters);
    }

    public Array<Rectangle> getTerrain(){
        MapObjects terrain=map.getLayers().get("collisionTerrain").getObjects();
        Array<Rectangle> terrainArray=new Array<Rectangle>();
        for(MapObject object: terrain){
            terrainArray.add(((RectangleMapObject)object).getRectangle());
        }
        return terrainArray;
    }

    public void update(float deltaTime){
        for(AdvancedSprite advancedSprite :fighters){
            advancedSprite.update(deltaTime);
        }
        removeRemovables();
    }

    public void dispose() {
        atlas.dispose();
        map.dispose();
    }


    public Array<Attack> getAttacks(){
        return attacks;
    }

    public void addAttack(Attack attack){
        fighters.add(attack);
        attacks.add(attack);
    }

    private void removeRemovables(){
        for(AdvancedSprite advancedSprite : fighters){
            if(advancedSprite.shouldRemove){
                fighters.removeValue(advancedSprite,true);
            }
        }
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
                TextureRegion region=atlas.findRegion("male/Attack (6)");

                AdvancedSprite advancedSprite = new Player(region,this);
                advancedSprite.setPosition(rectangle.x, rectangle.y);
                fighters.add(advancedSprite);
            }

            if (name.equals("enemy")) {
                AdvancedSprite advancedSprite = new AdvancedSprite(atlas.findRegion("female/Attack (6)"),this);
                advancedSprite.setPosition(rectangle.x, rectangle.y);
                advancedSprite.setSize(50,50);
                fighters.add(advancedSprite);
            }
        }
    }
}
