package io.github.videogame.model;

import java.util.HashMap;
import java.util.Map;

public class StoryState {
    private static StoryState instance;
    private Map<String, Boolean> dialogueStates;

    private StoryState() {
        init();
    }

    public static StoryState getInstance() {
        if (instance == null) {
            instance = new StoryState();
        }
        return instance;
    }

    public void init() {
        dialogueStates = new HashMap<>();

        dialogueStates.put("NPC_DEADBODY_ACT1",false);
        dialogueStates.put("NPC_AURORA_ACT1",false);
        dialogueStates.put("NPC_AURORA_ACT2",false);
        dialogueStates.put("NPC_AURORA_ACT3",false);
        dialogueStates.put("NPC_AURORA_ACT4",false);
        dialogueStates.put("NPC_CHIEF_OF_POLICE_ACT1",false);
        dialogueStates.put("NPC_CHIEF_OF_POLICE_ACT2",false);
        dialogueStates.put("NPC_CHIEF_OF_POLICE_ACT3",false);
        dialogueStates.put("NPC_INNOCENT_ACT1",false);
        dialogueStates.put("NPC_KILLER_ACT1",false);
        dialogueStates.put("NPC_KILLER_ACT2",false);
        //AGGIUNGERE QUELLO CON ANASTASIA
    }

    // Metodo per impostare un dialogo come completato
    public void setDialogueCompleted(String dialogueKey) {
        dialogueStates.put(dialogueKey, true);
    }

    // Metodo per ottenere il valore associato a una chiave
    public boolean getDialogueState(String dialogueKey) {
        return dialogueStates.getOrDefault(dialogueKey, false);
    }

    public void restoreStoryState(int id){

    }

    public void saveStoryState(){

    }
}
