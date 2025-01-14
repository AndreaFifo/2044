package io.github.videogame.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

// SINGLETON
public class Inventory {

    // Variabile statica per l'istanza singleton
    private static Inventory instance;
    private Map<String, Texture> itemList;

    // Costruttore privato per impedire l'istanza diretta
    private Inventory() {
        this.itemList = new TreeMap<>();
    }

    // Ottieni l'istanza dell'inventario (metodo singleton)
    public static Inventory getInventoryInstance() {
        if (instance == null) {
            instance = new Inventory();
        }
        return instance;
    }


    public ArrayList<String> getItemInventory() {
        ArrayList<String> nomi = new ArrayList<String>();

        for (Map.Entry<String, Texture> item : itemList.entrySet()) {
            nomi.add(item.getKey());
        }
        return nomi;
    }

    public void setInventory(ArrayList<String> itemList) {
        this.itemList.clear();
        for (String itemName : itemList) {
            switch (itemName) {
                case "MagneticKey":
                    this.itemList.put(itemName, new Texture("Oggetti/MagneticCard.png"));
                    break;
                case "FlashDriveInnocente":
                    this.itemList.put(itemName, new Texture("Oggetti/FlashDrive.png"));
                    break;
                case "JosephPhone":
                    this.itemList.put(itemName, new Texture("Oggetti/JosephPhone.png"));
                    break;
            }
        }
    }
    // Aggiunge un oggetto all'inventario
    public void addItemToInventory(String name, Texture texture) {
        itemList.put(name, texture);
    }


    //CAMBIARE LA POSIZIONE DI DISEGNO
    public void drawInventory(Batch batch) {
        if (Gdx.input.isKeyPressed(Input.Keys.I)) {
            // Posizione relativa al MovementController
            float baseX = Player.getInstance().getX(); // Usa la posizione X del controller
            float baseY = Player.getInstance().getY(); // Usa la posizione Y del controller
            int offsetX = -300; // Offset per spostare l'inventario rispetto al controller
            int offsetY = 180; // Offset verticale

            // Spaziatura tra gli oggetti
            int spacing = 30;
            int columns = 5; // Numero massimo di colonne

            int i = 0;
            for (Map.Entry<String, Texture> entry : itemList.entrySet()) {
                int column = i % columns; // Colonna corrente
                int row = i / columns; // Riga corrente

                batch.draw(entry.getValue(),
                    baseX + offsetX + column * spacing, // Posizione X
                    baseY + offsetY - row * spacing, // Posizione Y
                    16, 16); // Dimensione degli oggetti
                i++;
            }
        }
    }

    public void clearInventory(){
        this.itemList.clear();
    }
}
