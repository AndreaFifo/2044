package io.github.videogame.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import io.github.videogame.controller.MovementController;

public class NpcPoliceOfficer extends NpcCreator implements PrototypePoliceOfficer {

    public NpcPoliceOfficer(float spawn_x, float spawn_y) {
        super(spawn_x, spawn_y, 5);
        //Setto il nome del Npc
        this.setNpcName("Poliziotto");
        //Setto la texture del Npc
        this.setTexture(new Texture("NPC/polizia.png"));
        //Setto il primo dialogo con l'agente
        this.setDialogueAct1(InitDialogAct1(this.getDialogueAct1())); // ATTO I

    }

    @Override
    public void drawDialogueAct1() {
        if (canBeInteracted()) {
            this.getDialogManager().draw();
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                if (this.getDialogIndexAct1() < this.getDialogueAct1().length) {
                    this.getDialogManager().setDialog(this.getDialogueAct1()[this.getDialogIndexAct1()]); // Mostra la linea corrente
                    this.setDialogIndexAct1(this.getDialogIndexAct1() + 1);
                } else {
                    this.getDialogManager().setDialog(""); // Pulisce il testo del dialogo
                    this.setDialogIndexAct1(0); // Resetta il dialogo
                }
            }
        }
    }

    // Inizializza il dialogo del poliziotto
    public String[] InitDialogAct1(String[] dialogue) {
        dialogue[0] = "Officer: Hello, I'm here to assist with the investigation.";
        dialogue[1] = "Officer: The chief told me to keep an eye on the crime scene.";
        dialogue[2] = "Officer: We haven't found anything suspicious yet.";
        dialogue[3] = "Officer: If you discover anything, let me know.";
        dialogue[4] = "Officer: I will make sure no one enters the crime scene.";
        return dialogue;
    }

    @Override
    public void drawDialogue() {

    }

    @Override
    public String[] initDialogues(String[] dialogues) {
        return new String[0];
    }


    @Override
    public NpcPoliceOfficer clone(float newSpawnX, float newSpawnY) {
        // Crea una copia dell'oggetto NpcPoliceOfficer con il nuovo punto di spawn
        NpcPoliceOfficer clone = new NpcPoliceOfficer(newSpawnX, newSpawnY);

        // Copia il dialogo (Array di stringhe)
        clone.setDialogueAct1(this.getDialogueAct1()); // Clona l'array di dialogo

        // (Facoltativo) Se hai bisogno di copiare anche altri attributi o risorse complesse,
        // ad esempio una texture, assicurati di gestirli correttamente (es. fare una copia profonda)
        // clone.setTexture(new Texture(this.getTexture()));  // Esempio se la texture è modificabile

        return clone;
    }
}
