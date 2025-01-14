package io.github.videogame.model;

import io.github.videogame.controller.MovementController;

public class FlashDriveKiller extends Item {

    private String owner;

    public FlashDriveKiller(float x, float y) {
        super(x, y);
        this.setTexture("Oggetti/FlashDriveKiller.png");
        this.setPickUpSound("Oggetti/flashDrivePickUpSound.mp3");
        this.setName("FlashDriveKiller");
        this.setDescription("You have picked up Ryan's flash drive.");
        this.getDialogManager().setDialog(getDescription());
        this.owner = "Killer";
    }





}
