package io.github.videogame.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import io.github.videogame.controller.MovementController;
import io.github.videogame.view.screens.MainGameScreen;

//Factory Method

public class Item implements ItemInterface {
    // Attributi dell'oggetto
    private Texture texture;                 // Texture dell'oggetto
    private Sound pickUpSound;              // Suono emesso quando l'oggetto viene raccolto
    private float x, y;                     // Posizione dell'oggetto
    private boolean taken;                  // Stato dell'oggetto (raccolto o meno)
    private Player player;                  // Riferimento al giocatore
    private String name;                    // Nome dell'oggetto
    private String description;             // Descrizione dell'oggetto
    private int idTask;                     // Id della task dove spunta nella mappa l'item

    private DialogManager dialogManager;

    // Costruttore con valori di default
    public Item(float x, float y) {
        this.x = x;
        this.y = y;
        this.taken = false;
        this.player = Player.getInstance();
        this.dialogManager = new DialogManager();
        //this.dialogManager.setDialog(description);
    }

    private boolean isDrawing = true;

    // Metodo per raccogliere l'oggetto e aggiungerlo all'inventario
    public void pickUp() {
        if (canBePickedUp()) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.F)) {
                this.taken = true;
                this.emitSound();
                // Aggiungi l'oggetto all'inventario
                player.getInventory().addItemToInventory(this.name, this.texture);

                // Reimposta il flag per abilitare il dialogo per questo oggetto
                this.isDrawing = true;
            }
        }
    }

    public void drawDialogue() {
        if (this.isTaken() && isDrawing) {
            this.getDialogManager().draw();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            this.isDrawing = false; // Nasconde il dialogo per questo oggetto
        }
    }

    //Controlla se l'oggetto può essere raccolto in base alla posizione del giocatore.
    public boolean canBePickedUp() {
        return !taken &&
            player.getX() <= x + 30 &&
            player.getX() >= x - 30 &&
            player.getY() <= y + 30 &&
            player.getY() >= y - 30;
    }

    // Metodo per emettere il suono di raccolta
    public void emitSound() {
        if (pickUpSound != null) {
            pickUpSound.play();
        }
    }

    // Controlla se l'oggetto è stato raccolto
    public boolean isTaken() {
        return taken;
    }

    // Rilascia le risorse utilizzate dall'oggetto
    public void dispose() {
        if (texture != null) texture.dispose();
        if (pickUpSound != null) pickUpSound.dispose();
    }

    // Setter per texture e suono
    public void setTexture(String path) {
        this.texture = new Texture(path);
    }
    public DialogManager getDialogManager() {
        return dialogManager;
    }

    public void setPickUpSound(String path) {
        this.pickUpSound = Gdx.audio.newSound(Gdx.files.internal(path));
    }

    // Getter per posizione e texture
    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Texture getTexture() {
        return texture;
    }

    // Getter e setter per nome e descrizione
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
