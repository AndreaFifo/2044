package io.github.videogame;

import static org.junit.Assert.*;

import io.github.videogame.controller.MapManager;
import io.github.videogame.controller.MovementController;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class MovementControllerTest {
    private MovementController movementController;
    private MapManager mapManager;

    @Before
    public void setUp() {
        // Inizializza il controller con coordinate di partenza e uno stato di direzione iniziale
        movementController = MovementController.getInstance();
        mapManager = MapManager.getInstance();
    }

    //Test della Direzione di Movimento:
    //Ogni metodo simula una pressione di un tasto direzionale (W, A, S, D) in modo che restituisca true per il tasto giusto.
    //Dopodiché, verifica che la velocità e la direzione vengano impostate correttamente e che la posizione cambi di conseguenza.

    @Test
    public void testInitialPosition() {
        assertEquals(0f, movementController.getX(), 0.001);
        assertEquals(0f, movementController.getY(), 0.001);
    }

//    @Test
//    public void testInitialVelocity() {
//        assertEquals(0f, controller.getVelocityX(), 0.001);
//        assertEquals(0f, controller.getVelocityY(), 0.001);
//    }

    @Test
    public void testInitialStateDirection() {
        assertEquals(0, movementController.getStateDirection());
    }

    @Test
    public void testMoveUp() {
        // Simula il tasto 'W' premuto
        Gdx.input = Mockito.mock(Input.class);
        Mockito.when(Gdx.input.isKeyPressed(Input.Keys.W)).thenReturn(true);

        movementController.changeStateDirection(0.1f);

        assertEquals(150f, movementController.getY(), 0.01);
        assertEquals(0f, movementController.getX(), 0.01);
        assertTrue(movementController.isPlayerMoving());
    }

//    @Test
//    public void testMoveDown() {
//        // Simula il tasto 'S' premuto
//        Gdx.input = Mockito.mock(Input.class);
//        Mockito.when(Gdx.input.isKeyPressed(Input.Keys.S)).thenReturn(true);
//
//        controller.changeStateDirection(0.1f, Arrays.asList());
//
//        assertEquals(-150f, controller.getVelocityY(), 0.001);
//        assertEquals(0, controller.getStateDirection());
//    }
//
//    @Test
//    public void testMoveLeft() {
//        // Simula il tasto 'A' premuto
//        Gdx.input = Mockito.mock(Input.class);
//        Mockito.when(Gdx.input.isKeyPressed(Input.Keys.A)).thenReturn(true);
//
//        controller.changeStateDirection(0.1f, Arrays.asList());
//
//        assertEquals(-150f, controller.getVelocityX(), 0.001);
//        assertEquals(2, controller.getStateDirection());
//    }
//
//    @Test
//    public void testMoveRight() {
//        // Simula il tasto 'D' premuto
//        Gdx.input = Mockito.mock(Input.class);
//        Mockito.when(Gdx.input.isKeyPressed(Input.Keys.D)).thenReturn(true);
//
//        controller.changeStateDirection(0.1f, Arrays.asList());
//
//        assertEquals(150f, controller.getVelocityX(), 0.001);
//        assertEquals(3, controller.getStateDirection());
//    }
//
//    //Verifico se la variabile isMoving viene aggiornata correttamente in base alla pressione dei tasti direzionali.
//
//    @Test
//    public void testIsPlayerMovingTrue() {
//        // Simula un movimento verso destra
//        Gdx.input = Mockito.mock(Input.class);
//        Mockito.when(Gdx.input.isKeyPressed(Input.Keys.D)).thenReturn(true);
//
//        controller.changeStateDirection(0.1f, Arrays.asList());
//
//        assertTrue(controller.isPlayerMoving());
//    }
//
//    @Test
//    public void testIsPlayerMovingFalse() {
//        // Nessuna direzione premuta, quindi il giocatore non si sta muovendo
//        Gdx.input = Mockito.mock(Input.class);
//
//        controller.changeStateDirection(0.1f, Arrays.asList());
//
//        assertFalse(controller.isPlayerMoving());
//    }
//
//    //Test della Collisione: Simulo una collisione con una lista di muri
//    //e verifico se la funzione di collisione lavora correttamente impedendo il movimento del personaggio quando si trova in una posizione di conflitto.
//
//
//    @Test
//    public void testCollisionWithWall() {
//        // Crea una lista di rettangoli di muro
//        Rectangle wall = new Rectangle(16, 16, 16, 32);
//        List<Rectangle> walls = Arrays.asList(wall);
//
//        // Simula il movimento verso destra
//        Gdx.input = Mockito.mock(Input.class);
//        Mockito.when(Gdx.input.isKeyPressed(Input.Keys.D)).thenReturn(true);
//
//        // Il movimento verso destra dovrebbe essere bloccato dalla collisione
//        controller.changeStateDirection(0.1f, walls);
//
//        assertEquals(0f, controller.getX(), 0.001); // La posizione X non dovrebbe cambiare
//    }
//
//    @Test
//    public void testCollisionWithElevator() {
//        // Crea una lista di rettangoli per l'ascensore
//        Rectangle elevator = new Rectangle(50, 50, 16, 32);
//        List<Rectangle> elevators = Arrays.asList(elevator);
//
//        // Simula il movimento verso destra
//        Gdx.input = Mockito.mock(Input.class);
//        Mockito.when(Gdx.input.isKeyPressed(Input.Keys.D)).thenReturn(true);
//
//        // Il movimento verso destra dovrebbe essere possibile se non c'è collisione
//        controller.changeStateDirection(0.1f, elevators);
//
//        assertTrue(controller.isColliding2(50, 50, elevators)); // La collisione con l'ascensore dovrebbe restituire true
//    }
}
