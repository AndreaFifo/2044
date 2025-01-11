package io.github.videogame.controller;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.*;
import io.github.videogame.model.Player;
import io.github.videogame.model.Utility;

import java.util.*;

public class MapManager {
    private static MapManager instance;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private List<Rectangle> rectangleCollisions;
    private List<Rectangle> elevator;

    private String currentMap = "Mappa-prova/uffici.tmx";


    public MapManager() {
        Utility.loadMapAsset(currentMap);
        this.map = Utility.getAsset(currentMap, TiledMap.class);
        this.renderer = new OrthogonalTiledMapRenderer(map, 1f);
        this.camera = new OrthographicCamera();
        camera.setToOrtho(false, 960, 540);

        fetchCollision();
        fetchElevators();
    }

    public static MapManager getInstance() {
        synchronized (MapManager.class) {
            if (instance == null) {
                instance = new MapManager();
            }
        }

        return instance;
    }

    private void fetchCollision(){
        rectangleCollisions = new ArrayList<>();
        for(MapObject object : map.getLayers().get("collisioni").getObjects()) {
            if(object instanceof RectangleMapObject) {
                Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
                rectangleCollisions.add(rectangle);
            }
        }
    }

    private void fetchElevators() {
        elevator = new ArrayList<>();
        for(MapObject object : map.getLayers().get("ascensori").getObjects()) {
            if(object instanceof RectangleMapObject) {
                Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
                elevator.add(rectangle);
            }
        }
    }

    public boolean isNearElevators(float x, float y){
        Rectangle playerRect = new Rectangle(x + 3.5f, y + 4.5f, 7, 9);
        for(Rectangle rectangle : elevator) {
            if(rectangle.overlaps(playerRect)) {
                System.out.println(true);
                return true;
            }
        }

        return false;
    }

    public boolean isColliding(float x, float y) {
        Rectangle playerRect = new Rectangle(x + 3.5f, y + 4.5f, 7, 9); // Dimensioni del giocatore

        for (Rectangle rectangle : rectangleCollisions) {
            if (playerRect.overlaps(rectangle)) {
                return false;
            }
        }

        return true;
    }

    public void loadNewMap(){
        if(currentMap.equals("Mappa-prova/atrio-mensa.tmx"))
            currentMap = "Mappa-prova/uffici.tmx";
        else
            currentMap = "Mappa-prova/atrio-mensa.tmx";

        Utility.loadMapAsset(currentMap);
        this.map = Utility.getAsset(currentMap, TiledMap.class);
        renderer = new OrthogonalTiledMapRenderer(map, 1f);
    }

    public void render() {
        renderer.setView(camera);

        renderer.render();
    }

    public List<Rectangle> getRectangleCollisions() {
        return rectangleCollisions;
    }

    public String getCurrentMap() {
        return currentMap;
    }

    public void dispose() {
        if (map != null) {
            map.dispose();
        }
        if (renderer != null) {
            renderer.dispose();
        }
    }

    public OrthographicCamera getCamera() {
        return camera;
    }
}
