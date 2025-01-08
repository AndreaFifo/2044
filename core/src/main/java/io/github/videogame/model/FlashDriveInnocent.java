package io.github.videogame.model;

import io.github.videogame.controller.MovementController;
import io.github.videogame.view.screens.MainGameScreen;

public class FlashDriveInnocent extends Item {

    private String owner;

    public FlashDriveInnocent(float x, float y, MovementController movementController, Player player, MainGameScreen mainGameScreen) {
        super(x, y, movementController, player,mainGameScreen);
        this.setTexture("Oggetti/FlashDrive2.png");
        this.setPickUpSound("Oggetti/flashDrivePickUpSound.mp3");
        this.setName("FlashDriveInnocente");
        this.setDescription("Hai raccolto la chiavetta USB di innocente");
        this.getDialogManager().setDialog(getDescription());
        this.owner = "Innocente";
    }
}
