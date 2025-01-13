package io.github.videogame.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import io.github.videogame.controller.MovementController;

public class NpcAurora extends NpcCreator {

    //Ulteriori dialoghi
    private String[] dialogueAct2;
    private String[] dialogueAct3;
    private String[] dialogueAct4;

    public int getDialogIndexAct2() {
        return dialogIndexAct2;
    }

    public void setDialogIndexAct2(int dialogIndexAct2) {
        this.dialogIndexAct2 = dialogIndexAct2;
    }

    private int dialogIndexAct2 =0;

    public void setDialogIndexAct3(int dialogIndexAct3) {
        this.dialogIndexAct3 = dialogIndexAct3;
    }

    public int getDialogIndexAct3() {
        return dialogIndexAct3;
    }

    private int dialogIndexAct3 =0;

    public void setDialogIndexAct4(int dialogIndexAct4) {
        this.dialogIndexAct4 = dialogIndexAct4;
    }

    public int getDialogIndexAct4() {
        return dialogIndexAct4;
    }

    private int dialogIndexAct4 =0;

    public NpcAurora(float spawn_x, float spawn_y) {
        super(spawn_x, spawn_y);
        // Setto il nome del Npc
        this.setNpcName("Aurora");
        // Setto la texture del Npc
        this.setTexture(new Texture("Oggetti/nobackground.png"));
        // Setto i dialoghi del Npc
        this.setDialogueAct1(InitDialogAct1(this.getDialogueAct1())); // ATTO I
        this.dialogueAct2 = InitDialogAct2(new String[30]); // ATTO II
        this.dialogueAct3 = InitDialogAct3(new String[30]); // ATTO III
        this.dialogueAct4 = InitDialogAct4(new String[30]); // ATTO IV
    }

    public String[] InitDialogAct1(String[] dialogue) {
        dialogue[0] = "Aurora:\nCiao sono Aurora, l'intelligenza artificiale che assiste il dott. Caleb nelle sue ricerche, come posso esserle utile dott. Forest?";
        dialogue[1] = "Joseph:\nCaleb è stato assassinato, mi servirebbe una mano per scoprire il colpevole.";
        dialogue[2] = "Aurora:\nQuesto è terribile. Ha già avvisato le autorità?";
        dialogue[3] = "Joseph:\nSì, la polizia è già stata informata, ma ho bisogno di accedere ai dati e ai messaggi su cui Caleb stava lavorando.";
        dialogue[4] = "Aurora:\nPosso aiutarti, il dott. Caleb sembrerebbe aver terminato il suo algoritmo di decriptazione.";
        dialogue[5] = "Joseph:\n... È dal 2039 che cerchiamo invano di creare un algoritmo adatto alla decifrazione dei messaggi...";
        dialogue[6] = "Joseph:\nFinalmente è riuscito nell'impresa...";
        dialogue[7] = "Joseph:\nForza, mostrami il file dove è contenuto l'algoritmo, devo subito condividerlo con le autorità!";
        dialogue[8] = "Aurora:\nTemo che non sia possibile dott. Forest... sembrerebbe che il file contenente l'algoritmo sia stato prelevato da una chiavetta questa notte.";
        dialogue[9] = "Joseph:\nÈ terribile... questo significa che qualcuno del team è in realtà una spia russa che ci ha ostacolato nelle ricerche tutto questo tempo.";
        dialogue[10] = "Aurora:\nNon si preoccupi dott. Forest, il dott. Caleb era solito fare degli scherzi a chi copiava le proprie ricerche.";
        dialogue[11] = "Aurora:\nEcco dott. Forest... all'interno del computer vi è un software che inserisce un file nei dispositivi che copiano i dati del computer.";
        dialogue[12] = "Aurora:\nSarà sufficiente trovare un dispositivo con all'interno il file e controllare se la data di creazione di quest'ultimo coincide.";
        dialogue[13] = "Joseph:\nSei fantastica, vado a riferire agli agenti. Grazie mille.";
        dialogue[14] = "Aurora:\nÈ sempre un piacere aiutarla dott. Forest. A presto.";
        dialogue[15] = "";
        return dialogue;
    }

    public String[] InitDialogAct2(String[] dialogue) {
        dialogue[0] = "Inserimento chiavetta del dott. Cooper nel computer.";
        dialogue[1] = "Aurora:\nBuongiorno dott. Forest, di cosa ha bisogno?";
        dialogue[2] = "Joseph:\nAnalizza la chiavetta, dimmi se è quella incriminata contenente il file che stiamo cercando.";
        dialogue[3] = "Aurora:\nAnalizzando la chiavetta...";
        dialogue[4] = "Aurora:\nSembrerebbe che questa non corrisponda. Vi sono diversi file del software interessato...";
        dialogue[5] = "Aurora:\nNessuno però corrisponde alla data che stiamo cercando.";
        dialogue[6] = "Joseph:\nVa bene, grazie Aurora.";
        dialogue[7] = "Aurora:\nA presto dott. Forest.";
        dialogue[8] = "De-inserimento della chiavetta.";
        dialogue[9] = "";
        return dialogue;
    }

    public String[] InitDialogAct3(String[] dialogue) {
        dialogue[0] = "Inserimento chiavetta del dott. Pierce nel computer.";
        dialogue[1] = "Aurora:\nBuongiorno dott. Forest, di cosa ha bisogno?";
        dialogue[2] = "Joseph:\nAnalizza la chiavetta, dimmi se è quella incriminata contenente il file che stiamo cercando.";
        dialogue[3] = "Aurora:\nAnalizzando la chiavetta...";
        dialogue[4] = "Aurora:\nMi spiace dott. Forest, sembra esserci una corrispondenza. Stimo che al 99.7% il dott. Pierce sia il killer che stava cercando.";
        dialogue[5] = "Joseph:\nNon posso crederci! Dopo tutto questo tempo... Non mi sono accorto di chi avevo davanti.";
        dialogue[6] = "De-inserimento della chiavetta.";
        dialogue[7] = "";
        return dialogue;
    }

    public String[] InitDialogAct4(String[] dialogue) {
        dialogue[0] = "Joseph:\nNon posso credere a quanto sia accaduto.";
        dialogue[1] = "Aurora:\nCondoglianze per la sua perdita dott. Forest.";
        dialogue[2] = "Joseph:\nGrazie, adesso facciamo in modo che la sua perdita non sia stata vana.";
        dialogue[3] = "Aurora:\nPubblicare il file 30_e_lode contenente l'algoritmo richiesto sui server governativi?";
        dialogue[4] = "Joseph:\nSì.";
        dialogue[5] = "Aurora:\nPubblicazione avvenuta con successo. È sempre un piacere lavorare con lei dott. & detective Forest ;)";
        dialogue[6] = "";
        return dialogue;
    }

    // Restante codice per il drawDialogue() e canBeInteracted() rimane invariato

    //Conseguenza dell'interazione, cambio di stato del dialogo
    @Override
    public void drawDialogueAct1() {
        StoryState storyState = StoryState.getInstance();

        if(storyState.getDialogueState("NPC_CHIEF_OF_POLICE_ACT1")) {
            if (canBeInteracted()) {
                this.getDialogManager().draw();
                if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                    if (this.getDialogIndexAct1() < this.getDialogueAct1().length) {
                        this.getDialogManager().setDialog(this.getDialogueAct1()[this.getDialogIndexAct1()]); // Mostra la linea corrente
                        this.setDialogIndexAct1(this.getDialogIndexAct1() + 1);
                        storyState.setDialogueCompleted("NPC_AURORA_ACT1");
                        this.notifyObservers(4);
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
        Inventory inventory = Inventory.getInventoryInstance();

        if (storyState.getDialogueState("NPC_INNOCENT_ACT1") &
            inventory.getItemInventory().contains("MagneticKey") &
            inventory.getItemInventory().contains("FlashDriveInnocente")) {
            if (canBeInteracted()) {
                this.getDialogManager().draw();
                if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                    if (this.dialogIndexAct2 < this.dialogueAct2.length) {
                        this.getDialogManager().setDialog(this.dialogueAct2[dialogIndexAct2]); // Mostra la linea corrente
                        this.dialogIndexAct2 = dialogIndexAct2 + 1;
                        storyState.setDialogueCompleted("NPC_AURORA_ACT2");
                        this.notifyObservers(7);
                    } else {
                        this.getDialogManager().setDialog(""); // Pulisce il testo del dialogo
                        this.dialogIndexAct2 = 0; // Resetta il dialogo
                    }
                }
            }
        }
    }

    public void drawDialogueAct3() {
        StoryState storyState = StoryState.getInstance();
        Inventory inventory = Inventory.getInventoryInstance();

        if( storyState.getDialogueState("NPC_KILLER_ACT1") &
            inventory.getItemInventory().contains("FlashDriveKiller")) {

            if (canBeInteracted()) {
                this.getDialogManager().draw();
                if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                    if (this.dialogIndexAct3 < this.dialogueAct3.length) {
                        this.getDialogManager().setDialog(this.dialogueAct3[dialogIndexAct3]); // Mostra la linea corrente
                        this.dialogIndexAct3 = dialogIndexAct3 + 1;
                        storyState.setDialogueCompleted("NPC_AURORA_ACT3");
                        this.notifyObservers(9);
                    } else {
                        this.getDialogManager().setDialog(""); // Pulisce il testo del dialogo
                        this.dialogIndexAct3 = 0; // Resetta il dialogo
                    }
                }
            }
        }
    }


    public void drawDialogueAct4() {
        StoryState storyState = StoryState.getInstance();
        if (storyState.getDialogueState("NPC_CHIEF_OF_POLICE_ACT3")) {
            if (canBeInteracted()) {
                this.getDialogManager().draw();
                if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                    if (this.dialogIndexAct4 < this.dialogueAct4.length) {
                        this.getDialogManager().setDialog(this.dialogueAct4[dialogIndexAct4]); // Mostra la linea corrente
                        this.dialogIndexAct4 = dialogIndexAct4 + 1;
                        storyState.setDialogueCompleted("NPC_AURORA_ACT4");
                    } else {
                        this.getDialogManager().setDialog(""); // Pulisce il testo del dialogo
                        this.dialogIndexAct4 = 0; // Resetta il dialogo
                    }
                }
            }
        }
    }
}
