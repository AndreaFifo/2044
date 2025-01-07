package io.github.videogame.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import io.github.videogame.controller.MovementController;

public class NpcDeadBody extends NpcCreator{

    public NpcDeadBody(float spawn_x, float spawn_y, MovementController movementControllerPlayer){
        super(spawn_x, spawn_y, movementControllerPlayer);
        //Setto il nome del Npc
        this.setNpcName("DeadBody");
        //Setto la texture del Npc
        this.setTexture(new Texture("NPC/DeadBody.png"));
        //Setto il dialogo del Npc
        this.setDialogue(InitDialog(this.getDialogue()));
    }


    //Inizializza il dialogo del killer
    @Override
    public String[] InitDialog(String[] dialogue) {
        dialogue[0] = "Oh no... Questo non può essere vero...";
        dialogue[1] = "C'è un corpo qui! È morto...";
        dialogue[2] = "Chi può aver fatto una cosa del genere?";
        dialogue[3] = "Devo mantenere la calma... Ma è terribile...";
        dialogue[4] = "Non posso fare altro... Devo chiamare la polizia.";
        return dialogue;
    }


    //Conseguenza dell'interazione, cambio di stato del dialogo
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

    //Controllare se il player può interagire
    @Override
    public boolean canBeInteracted() {
        return getMovementControllerPlayer().getX() <= this.getSpawn_x() + 30 &&
            getMovementControllerPlayer().getX() >= this.getSpawn_x() - 30 &&
            getMovementControllerPlayer().getY() <= this.getSpawn_y() + 30 &&
            getMovementControllerPlayer().getY() >= this.getSpawn_y() - 30;
    }


}
