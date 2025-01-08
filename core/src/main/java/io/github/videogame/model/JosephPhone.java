package io.github.videogame.model;

import io.github.videogame.controller.MovementController;
import io.github.videogame.model.Item;
import io.github.videogame.model.Player;
import io.github.videogame.view.screens.MainGameScreen;

public class JosephPhone extends Item {

    private String[] dialogue;

    public JosephPhone(float x, float y, MovementController movementController, Player player, MainGameScreen mainGameScreen) {
        super(x, y, movementController, player, mainGameScreen);
        this.setTexture("Oggetti/JosephPhone.png");
        this.setPickUpSound("Oggetti/Phone Dial and Ring Sound.mp3");
        this.setName("JosephPhone");
        this.setDescription("Hai raccolto il tuo telefono");

        // Inizializza l'array di dialogo prima di usarlo
        dialogue = new String[6];
        dialogue = InitDialog(dialogue);
        this.getDialogManager().setDialog(dialogue);
    }

    public String[] InitDialog(String[] dialogue) {
        // Discussione iniziale post-chiamata polizia
        dialogue[0] = "Capo della Polizia: Sono stato informato della situazione. Puoi spiegarmi cosa è successo?";
        dialogue[1] = "Joseph Forest: Ho trovato un corpo... È lì, non so cosa sia successo.";
        dialogue[2] = "Capo della Polizia: Hai toccato qualcosa? Hai visto qualcuno sospetto nei paraggi?";
        dialogue[3] = "Joseph Forest: No, non ho toccato niente. Non ho visto nessuno, era già così quando sono arrivato.";
        dialogue[4] = "Capo della Polizia: Bene, hai fatto la cosa giusta chiamandomi. Ora lascia fare a noi.";
        dialogue[5] = "Capo della Polizia: Resta nei paraggi, potremmo avere bisogno di farti qualche domanda.";
        return dialogue;
    }
}
