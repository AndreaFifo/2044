package io.github.videogame.model;

import io.github.videogame.controller.MovementController;

public class FlashDriveInnocent extends Item {

    private String owner;

    public FlashDriveInnocent(float x, float y) {
        super(x, y);
        this.setTexture("Oggetti/FlashDrive2.png");
        this.setPickUpSound("Oggetti/flashDrivePickUpSound.mp3");
        this.setName("FlashDriveInnocente");
        this.setDescription("Hai raccolto la chiavetta USB di innocente");
        this.getDialogManager().setDialog(getDescription());
        this.owner = "Innocente";
    }
}
