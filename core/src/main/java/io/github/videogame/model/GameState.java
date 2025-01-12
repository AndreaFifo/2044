package io.github.videogame.model;

import java.util.ArrayList;

public class GameState {
    private static GameState instance;
    private float playerX;
    private float playerY;
    private String currentMap;
    private ArrayList<String> inventory;
    private int idCurrentTask;

    public GameState(){
        initialState();
    }

    public void initialState() {
        playerX = 356;
        playerY = 400;
        currentMap = "Mappa-prova/atrio-mensa.tmx";
        inventory = new ArrayList<>();
        idCurrentTask = 1;
    }

    public static GameState getInstance() {
        if (instance == null) {
            instance = new GameState();
        }
        return instance;
    }

    public void init(float playerX, float playerY, String currentMap, ArrayList<String> itemInventory, int idCurrentTask){
        this.playerX = playerX;
        this.playerY = playerY;
        this.currentMap = currentMap;
        this.inventory = itemInventory;
        this.idCurrentTask = idCurrentTask;
    }


    // Getter e setter
    public float getPlayerX() {
        return playerX;
    }

    public float getPlayerY() {
        return playerY;
    }

    public void setItemInventory(ArrayList<String> itemInventory) {
        this.inventory = itemInventory;
    }

    public void setPlayerX(float playerX) {
        this.playerX = playerX;
    }
    public void setPlayerY(float playerY) {
        this.playerY = playerY;
    }

    public void setCurrentMap(String currentMap) {
        this.currentMap = currentMap;
    }

    public ArrayList<String> getInventory() {
        return inventory;
    }

    public String getCurrentMap() {
        return currentMap;
    }

    public int getIdCurrentTask() {
        return idCurrentTask;
    }

    public void setIdCurrentTask(int idCurrentTask) {
        this.idCurrentTask = idCurrentTask;
    }

    @Override
    public String toString() {
        return "Player x: " + playerX + " Y: " + playerY + " Map: " + currentMap + " Inventory: " + inventory + " ID: " + idCurrentTask;
    }
}
