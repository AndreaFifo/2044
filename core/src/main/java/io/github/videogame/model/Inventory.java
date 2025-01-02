package io.github.videogame.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import io.github.videogame.controller.MovementController;

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


    //CAMBIARE LA POSIZIONE DI DISEGNO



// Metodo per disegnare l'inventario relativo al MovementController

    public void drawInventory(Batch batch, MovementController movementController) {
        if (Gdx.input.isKeyPressed(Input.Keys.I)) {
            // Posizione relativa al MovementController
            float baseX = movementController.getX(); // Usa la posizione X del controller
            float baseY = movementController.getY(); // Usa la posizione Y del controller
            int offsetX = -480; // Offset per spostare l'inventario rispetto al controller
            int offsetY = 240; // Offset verticale

            // Spaziatura tra gli oggetti
            int spacing = 30;

            // Disegna gli oggetti in una griglia
            for (int i = 0; i < itemList.size(); i++) {
                batch.draw(itemList.get(i).getTexture(),
                    baseX + offsetX + (i % 5) * spacing, // Colonna
                    baseY + offsetY - (i / 5) * spacing, // Riga
                    16, 16); // Dimensione degli oggetti
            }
        }
    }




}
