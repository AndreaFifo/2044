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
    private List<RectangleMapObject> doorCollisions;
    private List<TiledMapTile> elevatorClosedTiles;
    private List<TiledMapTile> elevatorOpenedTiles;
    private List<TiledMapTile> frontDoorClosedTiles;
    private List<TiledMapTile> backDoorClosedTiles;
    private List<TiledMapTile> frontDoorOpenedTiles;
    private List<TiledMapTile> backDoorOpenedTiles;

    private List<Vector2> coordinateAscensore;
    private List<Vector2> coordinateAscensoreDestra;

    private List<Vector2> coordinatePortaCaleeb;
    private List<Vector2> coordinatePortaBryan;
    private List<Vector2> coordinatePortaRyan;

    private String currentMap;

    private MapManager() {
        this.currentMap = GameState.getInstance().getCurrentMap();
        this.renderer = new OrthogonalTiledMapRenderer(null);
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, 720, 405);
        this.elevatorOpenedTiles = new ArrayList<>();
        this.elevatorClosedTiles = new ArrayList<>();
        coordinateAscensore = new ArrayList<>();
        coordinatePortaBryan = new ArrayList<>();
        coordinatePortaRyan = new ArrayList<>();
        coordinatePortaCaleeb = new ArrayList<>();

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

        if(currentMap.equals("Mappa-prova/uffici.tmx")) {
            fetchDoorTiles();
            fetchDoorCollision();
            setCaleebDoorCoordinates();
            setBryanDoorCoordinates();
            setRyanDoorCoordinates();
        }
    }

    private void setRyanDoorCoordinates() {
        coordinatePortaRyan.clear();
        coordinatePortaRyan.add(new Vector2(36, 22));
        coordinatePortaRyan.add(new Vector2(37, 22));
        coordinatePortaRyan.add(new Vector2(36, 21));
        coordinatePortaRyan.add(new Vector2(37, 21));
    }

    private void setBryanDoorCoordinates() {
        coordinatePortaBryan.clear();
        coordinatePortaBryan.add(new Vector2(18, 22));
        coordinatePortaBryan.add(new Vector2(19, 22));
        coordinatePortaBryan.add(new Vector2(18, 21));
        coordinatePortaBryan.add(new Vector2(19, 21));
    }

    private void setCaleebDoorCoordinates() {
        coordinatePortaCaleeb.clear();
        coordinatePortaCaleeb.add(new Vector2(27, 16));
        coordinatePortaCaleeb.add(new Vector2(28, 16));
        coordinatePortaCaleeb.add(new Vector2(27, 15));
        coordinatePortaCaleeb.add(new Vector2(28, 15));
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
        frontDoorClosedTiles = new ArrayList<>(4);
        backDoorClosedTiles = new ArrayList<>(4);
        frontDoorOpenedTiles = new ArrayList<>(4);
        backDoorOpenedTiles = new ArrayList<>(4);

        TiledMapTileSet tileset = map.getTileSets().getTileSet("tileset-principale");

        frontDoorClosedTiles.add(tileset.getTile(214));
        frontDoorClosedTiles.add(tileset.getTile(215));
        frontDoorClosedTiles.add(tileset.getTile(254));
        frontDoorClosedTiles.add(tileset.getTile(255));

        backDoorClosedTiles.add(tileset.getTile(216));
        backDoorClosedTiles.add(tileset.getTile(217));
        backDoorClosedTiles.add(tileset.getTile(256));
        backDoorClosedTiles.add(tileset.getTile(257));

        backDoorOpenedTiles.add(tileset.getTile(706));
        backDoorOpenedTiles.add(tileset.getTile(707));
        backDoorOpenedTiles.add(tileset.getTile(746));
        backDoorOpenedTiles.add(tileset.getTile(747));

        frontDoorOpenedTiles.add(tileset.getTile(704));
        frontDoorOpenedTiles.add(tileset.getTile(705));
        frontDoorOpenedTiles.add(tileset.getTile(744));
        frontDoorOpenedTiles.add(tileset.getTile(745));

    }

    private void fetchDoorCollision() {
        doorCollisions = new ArrayList<>();
        for(MapObject object : map.getLayers().get("porte").getObjects()) {
            if(object instanceof RectangleMapObject) {
                doorCollisions.add((RectangleMapObject) object);
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

    public void isNearDoors(float x, float y){
        Rectangle playerRect = new Rectangle(x + 3.5f, y + 4.5f, 14, 9);

        for(RectangleMapObject rectangle : doorCollisions) {
            String rectangleProp = rectangle.getProperties().get("owner", String.class);
            Rectangle newRectangle = rectangle.getRectangle();
            if (rectangleProp.equals("bryan")){
                if(newRectangle.overlaps(playerRect))
                    changeDoorTiles(coordinatePortaBryan, frontDoorOpenedTiles);
                else
                    changeDoorTiles(coordinatePortaBryan, frontDoorClosedTiles);
            }
            else if(rectangleProp.equals("ryan")){
                if(newRectangle.overlaps(playerRect))
                    changeDoorTiles(coordinatePortaRyan, frontDoorOpenedTiles);
                else
                    changeDoorTiles(coordinatePortaRyan, frontDoorClosedTiles);
            }
            else if(rectangleProp.equals("caleeb"))
                if(newRectangle.overlaps(playerRect))
                    changeDoorTiles(coordinatePortaCaleeb, backDoorOpenedTiles);
                else
                    changeDoorTiles(coordinatePortaCaleeb, backDoorClosedTiles);
        }
    }

    public void changeDoorTiles(List<Vector2> coordinatePorta, List<TiledMapTile> tiles){
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get("Mura");

        for(int i = 0; i < coordinatePorta.size(); i++){
            TiledMapTile tile = tiles.get(i);
            Vector2 pos = coordinatePorta.get(i);
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
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get("muraprova"); // Sostituisci "Mura" con il nome del tuo layer

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
                    if (tile != null && (tile.getId() == 214 || tile.getId() == 215 || tile.getId() == 254 || tile.getId() == 255)) {
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
