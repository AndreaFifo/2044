package io.github.videogame.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import io.github.videogame.controller.MovementController;

public class NpcKiller extends NpcCreator {

    private String[] dialogueAct2;

    public int getDialogIndexAct2() {
        return dialogIndexAct2;
    }

    public void setDialogIndexAct2(int dialogIndexAct2) {
        this.dialogIndexAct2 = dialogIndexAct2;
    }

    private int dialogIndexAct2 =0;

    public NpcKiller(float spawn_x, float spawn_y) {
        super(spawn_x, spawn_y);
        // Setto il nome del Npc
        this.setNpcName("Nome_Killer");
        // Setto la texture del Npc
        this.setTexture(new Texture("NPC/ryan.png"));
        // Setto il dialogo del Npc
        this.setDialogueAct1(InitDialogAct1(this.getDialogueAct1())); // ATTO I
        this.dialogueAct2 = InitDialogAct2(new String[30]); // ATTO II
    }

    // Inizializza il dialogo del killer
    @Override
    public String[] InitDialogAct1(String[] dialogue) {
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
        dialogue[13] = "";

        return dialogue;
    }

    private String[] InitDialogAct2(String[] dialogue){
        dialogue[0] = "Si sono stato io";
        dialogue[1] = "Come hai potuto farlo....";
        dialogue[2] = "........";
        dialogue[3] = "...";
        return  dialogue;
    }


    // Conseguenza dell'interazione, cambio di stato del dialogo
    @Override
    public void drawDialogueAct1() {
        StoryState storyState = StoryState.getInstance();

        if (storyState.getDialogueState("NPC_CHIEF_OF_POLICE_ACT2")) {

            if (canBeInteracted()) {
                this.getDialogManager().draw();
                if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                    if (this.getDialogIndexAct1() < this.getDialogueAct1().length) {
                        this.getDialogManager().setDialog(this.getDialogueAct1()[this.getDialogIndexAct1()]); // Mostra la linea corrente
                        this.setDialogIndexAct1(this.getDialogIndexAct1() + 1);
                        storyState.setDialogueCompleted("NPC_KILLER_ACT1");
                        this.notifyObservers(8);
                    } else {
                        this.getDialogManager().setDialog(""); // Pulisce il testo del dialogo
                        this.setDialogIndexAct1(0); // Resetta il dialogo
                    }
                }
            }
        }
    }



    //Disegna il secondo atto
    public void drawDialogueAct2() {
        StoryState storyState = StoryState.getInstance();

        if (storyState.getDialogueState("NPC_AURORA_ACT3")) {

            if (canBeInteracted()) {
                this.getDialogManager().draw();
                if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                    if (this.dialogIndexAct2 < this.dialogueAct2.length) {
                        this.getDialogManager().setDialog(this.dialogueAct2[dialogIndexAct2]); // Mostra la linea corrente
                        this.dialogIndexAct2 = dialogIndexAct2 + 1;
                        storyState.setDialogueCompleted("NPC_KILLER_ACT2");
                        this.notifyObservers(10);

                    } else {
                        this.getDialogManager().setDialog(""); // Pulisce il testo del dialogo
                        this.dialogIndexAct2 = 0; // Resetta il dialogo
                    }
                }
            }
        }
    }
}
