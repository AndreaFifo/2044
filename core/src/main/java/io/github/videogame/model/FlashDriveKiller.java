package io.github.videogame.model;

import io.github.videogame.controller.MovementController;

public class FlashDriveKiller extends Item {

    private String owner;

    public FlashDriveKiller(float x, float y) {
        super(x, y);
        this.setTexture("Oggetti/FlashDriveKiller.png");
        this.setPickUpSound("Oggetti/flashDrivePickUpSound.mp3");
        this.setName("FlashDriveKiller");
        this.setDescription("Hai raccolto la chiavetta USB del killer");
        this.getDialogManager().setDialog(getDescription());
        this.owner = "Killer";
    }





}
