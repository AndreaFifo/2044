package io.github.videogame.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;

import java.util.ArrayList;

// SINGLETON
public class Inventory {

    // Variabile statica per l'istanza singleton
    private static Inventory instance;
    private ArrayList<Item> itemList;



    // Costruttore privato per impedire l'istanza diretta
    private Inventory() {
        this.itemList = new ArrayList<>();
    }

    // Ottieni l'istanza dell'inventario (metodo singleton)
    public static Inventory getInventoryInstance() {
        if (instance == null) {
            instance = new Inventory();
        }
        return instance;
    }

    // Restituisce la lista degli oggetti nell'inventario
    public ArrayList<Item> getItemList() {
        return itemList;
    }

    // Aggiunge un oggetto all'inventario
    public void addItemToInventory(Item item) {
        this.itemList.add(item);
    }

    // Restituisce una stringa che rappresenta il contenuto dell'inventario
    public String getInventoryAsString() {
        if (itemList.isEmpty()) {
            return "L'inventario è vuoto.";
        } else {
            StringBuilder sb = new StringBuilder("Contenuto dell'inventario:\n");
            for (int i = 0; i < itemList.size(); i++) {
                Item item = itemList.get(i);
                sb.append(i + 1).append(". ")
                    .append(item.getName()).append(" - ")
                    .append(item.getDescription()).append("\n");
            }
            return sb.toString();
        }
    }

    // Metodo per disegnare l'inventario alla pressione del tasto 'I'
    public void drawInventory(Batch batch) {
        if (Gdx.input.isKeyPressed(Input.Keys.I)) {
            // Disegna gli oggetti dell'inventario in una griglia
            int x = 20; // Posizione in x, vicino al bordo sinistro
            int y = Gdx.graphics.getHeight() - 80; // Posizione iniziale in y
            int spacing = 30; // Spazio tra gli oggetti

            for (int i = 0; i < itemList.size(); i++)
                batch.draw(itemList.get(i).getTexture(), x + (i % 5) * spacing, y - (i / 5) * spacing, 32, 32);


        }
    }


}
