package io.github.videogame.controller;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.*;
import io.github.videogame.model.GameState;
import io.github.videogame.model.Utility;

import java.util.*;
import java.util.Vector;

public class MapManager {
    private static MapManager instance;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private List<Rectangle> rectangleCollisions;
    private List<RectangleMapObject> elevatorCollisions;
    private List<TiledMapTile> elevatorClosedTiles;
    private List<TiledMapTile> elevatorOpenedTiles;
    private List<StaticTiledMapTile> frontDoorTiles;
    private List<StaticTiledMapTile> backDoorTiles;

    private List<Vector2> coordinateAscensore;
    private List<Vector2> coordinateAscensoreDestra;

    private String currentMap;

    private MapManager() {
        this.currentMap = GameState.getInstance().getCurrentMap();
        this.renderer = new OrthogonalTiledMapRenderer(null);
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, 960, 540);
        this.elevatorOpenedTiles = new ArrayList<>();
        this.elevatorClosedTiles = new ArrayList<>();
        coordinateAscensore = new ArrayList<>();

        loadMap();
    }

    public static MapManager getInstance() {
        synchronized (MapManager.class) {
            if (instance == null) {
                instance = new MapManager();
            }
        }

        return instance;
    }

    public void loadMap(){
        if(map != null)
            map.dispose();

        Utility.loadMapAsset(currentMap);
        this.map = Utility.getAsset(currentMap, TiledMap.class);
        this.renderer.setMap(map);
        fetchCollision();
        fetchElevators();


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
        elevatorCollisions = new ArrayList<>();
        for(MapObject object : map.getLayers().get("ascensori").getObjects()) {
            elevatorCollisions.add((RectangleMapObject) object);
        }

        TiledMapTileSet tileSet = map.getTileSets().getTileSet("tileset-principale");
        elevatorClosedTiles.add(tileSet.getTile(210));
        elevatorClosedTiles.add(tileSet.getTile(211));
        elevatorClosedTiles.add(tileSet.getTile(250));
        elevatorClosedTiles.add(tileSet.getTile(251));
        elevatorOpenedTiles.add(tileSet.getTile(212));
        elevatorOpenedTiles.add(tileSet.getTile(213));
        elevatorOpenedTiles.add(tileSet.getTile(252));
        elevatorOpenedTiles.add(tileSet.getTile(253));
    }

    private void fetchDoorTiles(){
        frontDoorTiles = new ArrayList<>(8);
        backDoorTiles = new ArrayList<>(8);

        for (TiledMapTile tile : map.getTileSets().getTileSet("tileset-principale")) {
            if (tile.getProperties().containsKey("animation")) {
                if (tile.getProperties().get("animation", String.class).equals("front"))
                    frontDoorTiles.add((StaticTiledMapTile) tile);
                if (tile.getProperties().get("animation", String.class).equals("back"))
                    backDoorTiles.add((StaticTiledMapTile) tile);
            }
        }
    }

    public boolean isNearElevators(float x, float y){
        Rectangle playerRect = new Rectangle(x + 3.5f, y + 4.5f, 14, 9);
        for(RectangleMapObject rectangle : elevatorCollisions) {
            String rectangleLato = rectangle.getProperties().get("lato", String.class);
            Rectangle newRectangle = rectangle.getRectangle();
            if(newRectangle.overlaps(playerRect)) {
                //printMapCells();
                changeElevatorTiles(rectangleLato, elevatorOpenedTiles);

                return true;
            }
            else
                changeElevatorTiles(rectangleLato, elevatorClosedTiles);
        }


        return false;
    }

    private void changeElevatorTiles(String lato, List<TiledMapTile> elevatorOpenedTiles) {
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get("Mura");

        setCoordinateAscensore(lato);

        for(int i = 0; i < coordinateAscensore.size(); i++){
            TiledMapTile tile = elevatorOpenedTiles.get(i);
            Vector2 pos = coordinateAscensore.get(i);
            TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
            cell.setTile(tile);
            layer.setCell((int) pos.x, (int) pos.y, cell);
        }
    }

    private void setCoordinateAscensore(String lato){
        coordinateAscensore.clear();

        if(lato.equals("sinistra")) {
            coordinateAscensore.add(new Vector2(25, 22));
            coordinateAscensore.add(new Vector2(26, 22));
            coordinateAscensore.add(new Vector2(25, 21));
            coordinateAscensore.add(new Vector2(26, 21));
        }

        if(lato.equals("destra")){
            coordinateAscensore.add(new Vector2(29, 22));
            coordinateAscensore.add(new Vector2(30, 22));
            coordinateAscensore.add(new Vector2(29, 21));
            coordinateAscensore.add(new Vector2(30, 21));
        }
    }

    public void printMapCells() {
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get("Mura"); // Sostituisci "Mura" con il nome del tuo layer

        // Ottieni le dimensioni del layer
        int width = layer.getWidth();
        int height = layer.getHeight();

        // Itera attraverso le celle
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                TiledMapTileLayer.Cell cell = layer.getCell(x , y);
                if (cell != null) {
                    // Se la cella esiste, stampa informazioni
                    TiledMapTile tile = cell.getTile();
                    if (tile != null && (tile.getId() == 290 || tile.getId() == 291 || tile.getId() == 330 || tile.getId() == 331)) {
                        System.out.println("Cella (" + x + ", " + y + ") contiene il tile: " + tile.getId());
                    }
                }
            }
        }
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

    public void changeMap(){
        map.dispose();
        if(currentMap.equals("Mappa-prova/atrio-mensa.tmx"))
            currentMap = "Mappa-prova/uffici.tmx";
        else
            currentMap = "Mappa-prova/atrio-mensa.tmx";

        loadMap();
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

    public void setCurrentMap(String currentMap){
        this.currentMap = currentMap;
        loadMap();
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
