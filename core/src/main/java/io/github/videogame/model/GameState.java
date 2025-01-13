package io.github.videogame.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameState {
    private static GameState instance;
    private float playerX;
    private float playerY;
    private String currentMap;
    private ArrayList<String> inventory;
    private int idCurrentTask;
    private HashMap<String, Boolean> storyState;

    public GameState(){
        initialState();
    }

    public void initialState() {
        playerX = 456;
        playerY = 100;
        currentMap = "Mappa-prova/atrio-mensa.tmx";
        inventory = new ArrayList<>();
        idCurrentTask = 1;
        storyState = StoryState.getInstance().getDialogueStates();
    }

    public static GameState getInstance() {
        if (instance == null) {
            instance = new GameState();
        }
        return instance;
    }

    public void init(float playerX, float playerY, String currentMap, ArrayList<String> itemInventory, int idCurrentTask, HashMap<String, Boolean> storyState) {
        this.playerX = playerX;
        this.playerY = playerY;
        this.currentMap = currentMap;
        this.inventory = itemInventory;
        this.idCurrentTask = idCurrentTask;
        this.storyState = storyState;
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

    public HashMap<String, Boolean> getStoryState() {
        return storyState;
    }

    public void setStoryState(HashMap<String, Boolean> storyState) {
        this.storyState = storyState;
    }

    @Override
    public String toString() {
        return "Player x: " + playerX + " Y: " + playerY + " Map: " + currentMap + " Inventory: " + inventory + " ID: " + idCurrentTask;
    }
}
