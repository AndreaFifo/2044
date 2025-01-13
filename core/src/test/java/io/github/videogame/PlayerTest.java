package io.github.videogame;

import io.github.videogame.controller.MovementController;
import io.github.videogame.model.Gioco;
import io.github.videogame.model.Inventory;
import io.github.videogame.model.Item;
import io.github.videogame.model.Player;
import io.github.videogame.view.screens.MainGameScreen;
import junit.framework.TestCase;
import org.mockito.Mockito;

import java.util.ArrayList;

public class PlayerTest extends TestCase {
    private Player player;
    private Inventory inventory;
    @Override
    public void setUp() throws Exception {
        // Creazione di un'istanza reale di Inventory
        inventory = Inventory.getInventoryInstance();

        // Inizializzazione del singleton Player
        player = Player.getInstance();
    }

    // Verifica che venga restituita sempre la stessa istanza
    public void testSingletonInstance() {
        // Verifica che venga restituita sempre la stessa istanza
        Player anotherInstance = Player.getInstance();
        assertSame(player,anotherInstance);
    }

    //verifica  che il metodo updatestatetime di player aggiorni correttamente il valore di statetime
    public void testStateTimeUpdate() {
        float initialTime = 0f;
        float deltaTime = 0.5f;

        player.updateStateTime(deltaTime);

        // Verifica che lo stateTime sia aggiornato correttamente
        float expectedTime = initialTime + deltaTime;
        assertEquals("Lo stateTime deve essere aggiornato correttamente",expectedTime, player.getStateTime());
    }

    //verifica che il metodo getinventory di player restituisca un'istanza valida dell'oggetto inventory
    public void testGetInventory() {
        // Verifica che il metodo getInventory restituisca un'istanza valida di Inventory
        assertNotNull("Il metodo getInventory deve restituire un'istanza valida di Inventory",player.getInventory());
    }

    //verifica che il tempo dello stato del giocatore non diminuisca quando deltatime è uguale a 0
    public void testStateTimeDoesNotDecrease() {
        float deltaTime = 0.0f;
        float initialTime = player.getStateTime();

        player.updateStateTime(deltaTime);

        // Verifica che lo stateTime non diminuisca
        assertEquals("Lo stateTime non deve diminuire con deltaTime negativo",initialTime, player.getStateTime());
    }



    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }
}
