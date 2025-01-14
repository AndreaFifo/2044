package io.github.videogame.model;

import io.github.videogame.controller.MovementController;
import io.github.videogame.model.Item;
import io.github.videogame.model.Player;

public class JosephPhone extends Item {

    private String[] dialogue;

    public JosephPhone(float x, float y) {
        super(x, y);
        this.setTexture("Oggetti/JosephPhone.png");
        this.setPickUpSound("Oggetti/Phone Dial and Ring Sound.mp3");
        this.setName("JosephPhone");
        this.setDescription("You called the police, they are on their way");
        this.getDialogManager().setDialog(getDescription());
    }
}
