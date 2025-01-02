package io.github.videogame.model;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;

import java.util.*;

public class MapManager {
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private List<Rectangle> wallRectangles;
    private List<Rectangle> elevatorRectangles;
    private Map<Rectangle, String> elevatorTargetMaps;


    public MapManager(String mapFilePath, OrthographicCamera camera) {
        this.map = new TmxMapLoader().load(mapFilePath);
        this.renderer = new OrthogonalTiledMapRenderer(map, 1f);
        this.camera = camera;
        elevatorRectangles = new ArrayList<>();
        elevatorTargetMaps = new HashMap<>();

        // Carica gli oggetti "wall" dall'Object Layer
        wallRectangles = new ArrayList<>();
        for (MapObject object : map.getLayers().get("wall").getObjects()) {
            if (object instanceof RectangleMapObject) {
                Rectangle rect = ((RectangleMapObject) object).getRectangle();
                wallRectangles.add(rect);
            }
        }
        elevatorRectangles = new ArrayList<>();
        for (MapObject object : map.getLayers().get("elevator").getObjects()) {
            if (object instanceof RectangleMapObject) {
                Rectangle rect = ((RectangleMapObject) object).getRectangle();
                elevatorRectangles.add(rect);
                String targetMap = (String) object.getProperties().get("targets");
                elevatorTargetMaps.put(rect, targetMap);
            }
        }
    }

    public void render() {
        renderer.setView(camera);
        renderer.render();
    }

    public List<Rectangle> getWallRectangles() {
        return wallRectangles;
    }

    public List<Rectangle> getElevatorRectangles() {
        return elevatorRectangles;
    }

    public Map<Rectangle, String> getElevatorTargetMaps() {
        return elevatorTargetMaps;
    }
    public void dispose() {
        if (map != null) {
            map.dispose();
        }
        if (renderer != null) {
            renderer.dispose();
        }
    }

}
