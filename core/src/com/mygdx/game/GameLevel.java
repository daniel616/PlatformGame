package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
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
    private Array<Fighter> fighters;
    private Array<Sprite> spriteArray;
    private Array<ZombieAttack> attacks;
    private TextureAtlas atlas;

    /**could put this in constructor**/
    public GameLevel(String mapFileLocation){
        attacks = new Array<ZombieAttack>();
        fighters=new Array<Fighter>();
        map=new TmxMapLoader().load(mapFileLocation);
        atlas= new TextureAtlas(Gdx.files.internal("data/zombies.txt"));
        processMapMetadata();

    }

    public LevelRenderer generateRenderer(){
        spriteArray=new Array<Sprite>();
        spriteArray.addAll(fighters);
        return new LevelRenderer(map,spriteArray);
    }

    public Array<Rectangle> getTerrain(){
        MapObjects terrain=map.getLayers().get("collisionTerrain").getObjects();
        Array<Rectangle> terrainArray=new Array<Rectangle>();
        for(MapObject object: terrain){
            terrainArray.add(((RectangleMapObject)object).getRectangle());
        }
        return terrainArray;
    }

    public void addAttack(ZombieAttack attack){
        spriteArray.add(attack);
        attacks.add(attack);
    }

    public void update(float deltaTime){
        for(Fighter fighter:fighters){
            fighter.update(deltaTime);
        }
        for (ZombieAttack attack:attacks){
            attack.update(deltaTime);
        }
        checkAttacks();
        checkRemovable();
    }

    public void dispose() {
        atlas.dispose();
        map.dispose();
    }

    private void checkRemovable(){
        for(ZombieAttack attack: attacks){
            if(attack.shouldRemove()){
                attacks.removeValue(attack,true);
                spriteArray.removeValue(attack,true);
            }
        }

        for(Fighter fighter: fighters){
            if(fighter.health<=0){
                fighters.removeValue(fighter,true);
                spriteArray.removeValue(fighter,true);

            }
        }
    }

    private void checkAttacks(){
        for(ZombieAttack attack:attacks){
            for(Fighter fighter:fighters){
                if((attack.getRectangle().overlaps(fighter.getBoundingRectangle()))
                        &&(attack.getTeam()!=(fighter.team))){
                    fighter.takeDamage(3);
                }
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
                Fighter fighter = new Player(atlas.findRegion("male/Attack (6)"),this);
                fighter.setPosition(rectangle.x, rectangle.y);
                fighters.add(fighter);
            }
        }
    }
}
