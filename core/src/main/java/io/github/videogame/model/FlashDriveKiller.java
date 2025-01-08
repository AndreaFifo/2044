package io.github.videogame.model;

import io.github.videogame.controller.MovementController;
import io.github.videogame.view.screens.MainGameScreen;

public class FlashDriveKiller extends Item {

    private String owner;

    public FlashDriveKiller(float x, float y, MovementController movementController, Player player, MainGameScreen mainGameScreen) {
        super(x, y, movementController, player,mainGameScreen);
        this.setTexture("Oggetti/FlashDriveKiller.png");
        this.setPickUpSound("Oggetti/flashDrivePickUpSound.mp3");
        this.setName("FlashDriveKiller");
        this.setDescription("Hai raccolto la chiavetta USB del killer");
        this.getDialogManager().setDialog(getDescription());
        this.owner = "Killer";
    }





}
