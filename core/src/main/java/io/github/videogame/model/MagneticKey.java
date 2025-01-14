package io.github.videogame.model;

import io.github.videogame.controller.MovementController;

public class MagneticKey extends Item {

   /*I creatori concreti sovrascrivono il metodo di fabbrica di base,
   in modo che restituisca un tipo di prodotto diverso.*/

    public MagneticKey(float x, float y) {
        super(x, y);
        this.setTexture("Oggetti/MagneticCard.png");
        this.setPickUpSound("Oggetti/keyPickUpSound.mp3");
        this.setName("MagneticKey");
        this.setDescription("A magnetic card of Bryan's office");
    }

}
