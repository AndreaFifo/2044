package io.github.videogame.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import io.github.videogame.controller.MovementController;

public class NpcKiller extends NpcCreator {

    public NpcKiller(float spawn_x, float spawn_y, MovementController movementControllerPlayer) {
        super(spawn_x, spawn_y, movementControllerPlayer);
        // Setto il nome del Npc
        this.setNpcName("Nome_Killer");
        // Setto la texture del Npc
        this.setTexture(new Texture("NPC/killer.png"));
        // Setto il dialogo del Npc
        this.setDialogue(InitDialog(this.getDialogue()));
    }

    // Inizializza il dialogo del killer
    @Override
    public String[] InitDialog(String[] dialogue) {
        dialogue[0] = "Joseph:\nRyan, posso parlarti un momento?";
        dialogue[1] = "Ryan:\nCerto, Joseph. Di cosa si tratta?";
        dialogue[2] = "Joseph:\nSto indagando sull'omicidio di Caleb e vorrei sapere dove ti trovavi e cosa stavi facendo la notte in cui è successo.";
        dialogue[3] = "Ryan:\nEro nel mio ufficio, ho passato tutta la notte lavorando al computer su alcuni compiti arretrati. Non sono uscito da lì fino alla mattina.";
        dialogue[4] = "Joseph:\nCapisco. Quindi non hai visto nulla di sospetto o sentito rumori strani durante la notte?";
        dialogue[5] = "Ryan:\nNo, niente di tutto ciò. Era tutto tranquillo. Ho lavorato fino a tardi, poi mi sono anche addormentato sopra la scrivania, ahahahah";
        dialogue[6] = "Ryan:\n..........";
        dialogue[7] = "Joseph:\nGrazie, Ryan. Questo mi aiuta a capire meglio cosa è successo.";
        dialogue[8] = "Ryan:\nSpero che tu riesca a risolvere tutto. Se hai bisogno di qualsiasi cosa, fammelo sapere.";
        dialogue[9] = "Joseph\nAH!! Un ultima cosa....potresti darmi la tua chiavetta?";
        dialogue[10] = "Ryan:\n E nel mio ufficio, ma a cosa ti dovrebbe servire...? ";
        dialogue[11] = "Joseph:\n Devo solo controllare una cosa, non ti preoccupare non cancello nulla";
        dialogue[12] = "Ryan:\n Buona fortuna allora";

        return dialogue;
    }


    // Conseguenza dell'interazione, cambio di stato del dialogo
    @Override
    public void drawDialogue() {
        if (canBeInteracted()) {
                this.getDialogManager().draw();
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                if (this.getDialogIndex() < this.getDialogue().length) {
                    this.getDialogManager().setDialog(this.getDialogue()[this.getDialogIndex()]); // Mostra la linea corrente
                    this.setDialogIndex(this.getDialogIndex() + 1);
                } else {
                    this.getDialogManager().setDialog(""); // Pulisce il testo del dialogo
                    this.setDialogIndex(0); // Resetta il dialogo
                }
            }
        }
    }


    // Controllare se il player può interagire
    @Override
    public boolean canBeInteracted() {
        return getMovementControllerPlayer().getX() <= this.getSpawn_x() + 30 &&
            getMovementControllerPlayer().getX() >= this.getSpawn_x() - 30 &&
            getMovementControllerPlayer().getY() <= this.getSpawn_y() + 30 &&
            getMovementControllerPlayer().getY() >= this.getSpawn_y() - 30;
    }

}
