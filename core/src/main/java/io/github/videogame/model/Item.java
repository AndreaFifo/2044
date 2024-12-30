package io.github.videogame.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import io.github.videogame.controller.MovementController;
import io.github.videogame.view.screens.MainGameScreen;

//Factory Method

public class Item {

    // Attributi dell'oggetto
    private Texture texture;                 // Texture dell'oggetto
    private Sound pickUpSound;              // Suono emesso quando l'oggetto viene raccolto
    private float x, y;                     // Posizione dell'oggetto
    private boolean taken;                  // Stato dell'oggetto (raccolto o meno)
    private MovementController movementController; // Controller del movimento del giocatore
    private Player player;                  // Riferimento al giocatore
    private String name;                    // Nome dell'oggetto
    private String description;             // Descrizione dell'oggetto


    public MainGameScreen getMainGameScreen() {
        return mainGameScreen;
    }

    private MainGameScreen mainGameScreen;

    private DialogManager dialogManager;

    // Costruttore con valori di default
    public Item(float x, float y, MovementController movementController, Player player, MainGameScreen mainGameScreen) {
        this.x = x;
        this.y = y;
        this.taken = false;
        this.movementController = movementController;
        this.player = player;
        this.dialogManager = new DialogManager();
        this.dialogManager.setDialog("Add a description");
        this.mainGameScreen = mainGameScreen;
    }

    // Metodo per raccogliere l'oggetto e aggiungerlo all'inventario
    public void pickUp() {

            if (canBePickedUp()) {
                if (Gdx.input.isKeyPressed(Input.Keys.F)) {
                    this.taken = true;
                    this.emitSound();
                    //Aggiungi l'oggetto all'inventario
                    player.getInventory().addItemToInventory(this);
                    //Disegna il dialogo della raccolta dell'oggetto
                    this.getDialogManager().draw();

                }
            }
    }


    //Controlla se l'oggetto può essere raccolto in base alla posizione del giocatore.
    private boolean canBePickedUp() {
        return !taken &&
            movementController.getX() <= x + 100 &&
            movementController.getX() >= x - 100 &&
            movementController.getY() <= y + 100 &&
            movementController.getY() >= y - 100;
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
