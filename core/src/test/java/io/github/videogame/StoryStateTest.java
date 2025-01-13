package io.github.videogame;

import io.github.videogame.model.StoryState;
import junit.framework.TestCase;

public class StoryStateTest extends TestCase {
    private StoryState storyState;

    @Override
    public void setUp() throws Exception {
        storyState=StoryState.getInstance();
        storyState.init();
    }

    //verifica che ci sia una sola istanza della classe
    public void testSingletonInstance() {
        // Verifica che il pattern Singleton funzioni correttamente
        StoryState instance1 = StoryState.getInstance();
        StoryState instance2 = StoryState.getInstance();

        assertSame("StoryState non rispetta il pattern Singleton",instance1, instance2);
    }

    //verifica che tutti i dialoghi in storystate siano inizializzati correttamente
    public void testInitialDialogueStates() {
        // Verifica che i dialoghi siano inizializzati correttamente come "false"
        assertFalse(storyState.getDialogueState("NPC_DEADBODY_ACT1"));
        assertFalse(storyState.getDialogueState("NPC_AURORA_ACT1"));
        assertFalse(storyState.getDialogueState("NPC_AURORA_ACT2"));
    }

    //verifica che si aggiornino correttamente lo stato dei dialoghi
    public void testSetDialogueCompleted() {
        // Impostare un dialogo come completato e verificarne lo stato
        String dialogueKey = "NPC_AURORA_ACT1";
        storyState.setDialogueCompleted(dialogueKey);

        assertTrue("Il dialogo dovrebbe essere completato",storyState.getDialogueState(dialogueKey));
    }

    public void testGetDialogueStateWithInvalidKey() {
        // Verifica il comportamento quando si accede a una chiave non esistente
        String invalidKey = "INVALID_KEY";
        assertFalse("Il dialogo non valido dovrebbe restituire false",storyState.getDialogueState(invalidKey));
    }

    //verifica che la chiamata al metodo init reimposti correttamente lo stato della storia
    public void testReinitializationClearsStates() {
        // Verifica che la chiamata a init() reimposti correttamente lo stato
        String dialogueKey = "NPC_AURORA_ACT1";
        storyState.setDialogueCompleted(dialogueKey);

        assertTrue("Il dialogo dovrebbe essere completato prima della reinizializzazione",storyState.getDialogueState(dialogueKey));

        storyState.init();

        assertFalse(storyState.getDialogueState(dialogueKey));
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }
}
