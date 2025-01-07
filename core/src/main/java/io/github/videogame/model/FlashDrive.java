package io.github.videogame.model;

import io.github.videogame.controller.MovementController;
import io.github.videogame.view.screens.MainGameScreen;

public class FlashDrive extends Item {


    public FlashDrive(float x, float y, MovementController movementController, Player player, MainGameScreen mainGameScreen) {
        super(x, y, movementController, player,mainGameScreen);
        this.setTexture("Oggetti/FlashDrive2.png");
        this.setPickUpSound("Oggetti/flashDrivePickUpSound.mp3");
        this.setName("FlashDrive");
        this.setDescription("A flash drive, probably contains important algorithms");
        this.getDialogManager().setDialog(getDescription());
    }





}
