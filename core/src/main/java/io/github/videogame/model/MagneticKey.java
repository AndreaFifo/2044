package io.github.videogame.model;

import io.github.videogame.controller.MovementController;
import io.github.videogame.view.screens.MainGameScreen;

public class MagneticKey extends Item {

   /*I creatori concreti sovrascrivono il metodo di fabbrica di base,
   in modo che restituisca un tipo di prodotto diverso.*/

    public MagneticKey(float x, float y, MovementController movementController, Player player, MainGameScreen mainGameScreen) {
        super(x, y,movementController, player, mainGameScreen);
        this.setTexture("Oggetti/MagneticCard.png");
        this.setPickUpSound("Oggetti/keyPickUpSound.mp3");
        this.setName("Magnetic Key");
        this.setDescription("A magnetic card of an office");
        this.getDialogManager().setDialog(getDescription());
    }

}
