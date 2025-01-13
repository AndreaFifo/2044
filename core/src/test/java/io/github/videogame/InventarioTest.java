package io.github.videogame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import io.github.videogame.model.Inventory;
import junit.framework.TestCase;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

public class InventarioTest extends TestCase {

    private Inventory inventory;
    private Batch mockBatch;

    @Override
    public void setUp() throws Exception {
        // Ottieni l'istanza singleton dell'inventario
        inventory = Inventory.getInventoryInstance();

        // Mock delle dipendenze
        mockBatch = mock(Batch.class);

        inventory.setInventory(new ArrayList<>());
    }

    public void testGetItemInventory_EmptyInventory()
    {
        ArrayList<String> itemNames = inventory.getItemInventory();

        assertNotNull("La lista dei nomi non dovrebbe essere null.", itemNames);

        assertTrue("L'inventario è vuoto.", itemNames.isEmpty());
    }

    //test per draw inventary
    //questo test verifica il comportamento di drawinventary di inventary quando viene cliccato I,
    //in particolare ci accertiamo che vengano disegnati correttamente tutti gli oggetti cliccando I
    public void testDrawInventory_WhenKeyIsPressed() {
        // Mock del tasto premuto
        Gdx.input = mock(Input.class);
        when(Gdx.input.isKeyPressed(Input.Keys.I)).thenReturn(true);

        Texture mockTexture = mock(Texture.class);
        inventory.addItemToInventory("item1", mockTexture);
        inventory.addItemToInventory("item2", mockTexture);
        inventory.addItemToInventory("item3", mockTexture);

        // Chiama il metodo drawInventory
        float mockPlayerX = 100f;
        float mockPlayerY = 200f;
        inventory.drawInventory(mockBatch, mockPlayerX, mockPlayerY);

        // Verifica che il metodo draw del batch sia stato chiamato 3 volte (una per ogni oggetto)
        verify(mockBatch, times(3)).draw(
            any(Texture.class),
            anyFloat(),
            anyFloat(),
            eq(16f),
            eq(16f)
        );
    }

    //test per drawinventary
    //questo metodo verifica il comportamento di drawinventary quando il tasto I non viene premuto
    public void testDrawInventory_WhenKeyIsNotPressed() {
        // Mock del tasto non premuto
        Gdx.input = mock(Input.class);
        when(Gdx.input.isKeyPressed(Input.Keys.I)).thenReturn(false);

        // Aggiungi un oggetto all'inventario
        Texture mockTexture = mock(Texture.class);
        inventory.addItemToInventory("item1", mockTexture);

        // Chiama il metodo drawInventory
        float mockPlayerX = 100f;
        float mockPlayerY = 200f;
        inventory.drawInventory(mockBatch, mockPlayerX, mockPlayerY);

        // Verifica che il metodo draw del batch non sia stato chiamato
        verify(mockBatch, never()).draw(any(Texture.class), anyFloat(), anyFloat(), anyFloat(), anyFloat());
    }

    //test che verifica il comportamento del metodo quando l'inventario contiene degli oggetti
    public void testGetItemInventory() {
        inventory.clearInventory();

        // Crea e aggiungi oggetti mock all'inventario
        String itemName1 = "FlashDriveKiller";
        String itemName2 = "MagneticCard";
        Texture mockTexture = mock(Texture.class);

        inventory.addItemToInventory(itemName1, mockTexture);
        inventory.addItemToInventory(itemName2, mockTexture);


        // Chiamata al metodo da testare
        ArrayList<String> itemNames = inventory.getItemInventory();

        // Verifica che la lista contenga i nomi corretti
        assertNotNull("La lista dei nomi non dovrebbe essere nulla.", itemNames);
        assertEquals("La lista dei nomi dovrebbe contenere 2 elementi.",2, itemNames.size());
        assertTrue("La lista dovrebbe contenere 'FlashDriveKiller'.",itemNames.contains("FlashDriveKiller"));
        assertTrue("La lista dovrebbe contenere 'MagneticCard'.",itemNames.contains("MagneticCard"));
    }

    @Override
    public void tearDown() throws Exception {
        inventory.setInventory(new ArrayList<>());
        super.tearDown();
    }
}
