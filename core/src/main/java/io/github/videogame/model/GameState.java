package io.github.videogame.model;

import java.util.ArrayList;

public class GameState {
    private float playerX;
    private float playerY;
    private String currentMap;
    private ArrayList<String> inventory;
    private int idCurrentTask;

    // Costruttore per inizializzare il memento
    public GameState(float playerX, float playerY, String currentMap, ArrayList<String> itemInventory, int idCurrentTask) {
        this.playerX = playerX;
        this.playerY = playerY;
        this.currentMap = currentMap;
        // Creare una copia immutabile della lista per proteggere l'inventario
        this.idCurrentTask = idCurrentTask;
    }

    public GameState() {

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

    public void setPlayerY(float playerY) {

        this.currentMap = currentMap;
    }


    public ArrayList<String> getInventory() {
        return inventory;
    }
}
