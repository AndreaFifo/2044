package io.github.videogame.model;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Gamestate {
    private float playerX;
    private float playerY;
    private String currentMap;
    private ArrayList<String> iteminventary;
    private int idCurrentTask;

    // Costruttore per inizializzare il memento
    public Gamestate(float playerX, float playerY, String currentMap, ArrayList<String> itemInventory, int idCurrentTask) {
        this.playerX = playerX;
        this.playerY = playerY;
        this.currentMap = currentMap;
        // Creare una copia immutabile della lista per proteggere l'inventario
        this.iteminventary = new ArrayList<String>();
        this.idCurrentTask = idCurrentTask;
    }
    // Getter e setter
    public float getPlayerX() {
        return playerX;
    }

    public int getIdCurrentTask() {
        return idCurrentTask;
    }

    public void setIdCurrentTask(int idCurrentTask) {
        this.idCurrentTask = idCurrentTask;
    }

    public void setPlayerX(float playerX) {
        this.playerX = playerX;
    }

    public float getPlayerY() {
        return playerY;
    }

    public void setIteminventary(ArrayList<String> iteminventary) {
        this.iteminventary = iteminventary;
    }

    public ArrayList<String> getIteminventary()
    {
        ArrayList<String> elementi=new ArrayList<String>();
        for (Item item : Inventory.getInventoryInstance().getItemList()) {
            elementi.add(item.getName());
        }
        return elementi;
    }

    public void setPlayerY(float playerY) {
        this.playerY = playerY;
    }

    public String getCurrentMap() {
        return currentMap;
    }

    public void setCurrentMap(String currentMap) {
        this.currentMap = currentMap;
    }

    public static ArrayList<String> parseInventory(String input) {
        ArrayList<String> result = new ArrayList<>();

        // Rimuovi le parentesi quadre all'inizio e alla fine della stringa
        if (input.startsWith("[") && input.endsWith("]")) {
            input = input.substring(1, input.length() - 1).trim();
        }

        // Dividi la stringa in base alla virgola
        String[] items = input.split(",");

        // Aggiungi ogni elemento alla lista, rimuovendo eventuali spazi extra
        for (int i = 0; i < items.length; i++) {
            result.add(items[i].trim());
        }

        return result;
    }

}
