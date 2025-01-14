package io.github.videogame.model;

public interface NpcCreatorInterface {
    //FUNZIONI PER GESTIRE I DIALOGHI

    //Inizializza l'atto 1 del personaggio: (Imposta l'array di stringhe)
    String[] InitDialogAct1(String[] dialogue);

    //Impostare il dialogo
    void setDialogueAct1(String[] dialogueAct1);

    //Imposta se può essere interagibile
    boolean canBeInteracted();
}
