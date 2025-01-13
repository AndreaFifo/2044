package io.github.videogame.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;

public class NpcDeadBody extends NpcCreator{
    public NpcDeadBody(float spawn_x, float spawn_y){
        super(spawn_x, spawn_y, 5);
        //Setto il nome del Npc
        this.setNpcName("DeadBody");
        //Setto la texture del Npc
        this.setTexture(new Texture("Oggetti/nobackground.png"));
        //Setto il dialogo del Npc
        this.setDialogueAct1(InitDialogAct1(this.getDialogueAct1()));
    }

    //Inizializza il dialogo del killer
    @Override
    public String[] InitDialogAct1(String[] dialogue) {
        dialogue[0] = "Oh no... Questo non può essere vero...";
        dialogue[1] = "C'è un corpo qui! È morto...";
        dialogue[2] = "Chi può aver fatto una cosa del genere?";
        dialogue[3] = "Devo mantenere la calma... Ma è terribile...";
        dialogue[4] = "Non posso fare altro... Devo chiamare la polizia.";
        return dialogue;
    }

    @Override
    public void setDialogueAct1(String[] dialogueAct1) {

    }


    //Conseguenza dell'interazione, cambio di stato del dialogo
    @Override
    public void drawDialogueAct1() {
        StoryState storyState = StoryState.getInstance();

        if(storyState.getDialogueState("NPC_DEADBODY_ACT1"))
            return;

        if (canBeInteracted()) {
            this.getDialogManager().draw();
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                System.out.println(this.getDialogueAct1().length);
                if (this.getDialogIndexAct1() < this.getDialogueAct1().length) {
                    this.getDialogManager().setDialog(this.getDialogueAct1()[this.getDialogIndexAct1()]); // Mostra la linea corrente
                    this.setDialogIndexAct1(this.getDialogIndexAct1() + 1);
                } else {
                    storyState.setDialogueCompleted("NPC_DEADBODY_ACT1");
                    this.notifyObservers(2);
                    this.getDialogManager().setDialog(""); // Pulisce il testo del dialogo
                    this.setDialogIndexAct1(0); // Resetta il dialogo
                    this.getDialogManager().hide();
                }
            }
        }
    }

    @Override
    public void drawDialogue() {

    }

    @Override
    public String[] initDialogues(String[] dialogues) {
        return new String[0];
    }
}
