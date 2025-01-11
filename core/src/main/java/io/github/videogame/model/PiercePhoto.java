package io.github.videogame.model;


import io.github.videogame.controller.MovementController;
import io.github.videogame.view.screens.MainGameScreen;

public class PiercePhoto extends Item{

    public PiercePhoto(float x, float y, MovementController movementController, Player player, MainGameScreen mainGameScreen) {
        super(x, y, movementController, player,mainGameScreen);
        this.setTexture("Oggetti/PiercePhoto.png");
        this.setPickUpSound("Oggetti/flashDrivePickUpSound.mp3"); //CAMBIARE AUDIO DI RACCOLTA
        this.setName("PiercePhoto");
        this.setDescription("Hai raccolto una strana foto");
        this.getDialogManager().setDialog(getDescription());

    }


}
