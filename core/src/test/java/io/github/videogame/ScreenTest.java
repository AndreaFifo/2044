package io.github.videogame;

import io.github.videogame.controller.ScreenManager;
import io.github.videogame.model.Gioco;
import junit.framework.TestCase;

public class ScreenTest extends TestCase {
    private ScreenManager screenManager;
    private Gioco game;

    @Override
    public void setUp() throws Exception {
        game = new Gioco();
        screenManager = ScreenManager.getInstance();
        screenManager.init(game);
    }

    //verifica che tutte le chiamate al metodo getinstance restituiscano la stessa istanza unica dell'oggetto.
    public void testSingletonInstance() {
        ScreenManager anotherInstance = ScreenManager.getInstance();
        assertSame(screenManager, anotherInstance);
    }

    //verifica che screenmanager e game siano correttamente inizializzati e pronti per l'uso
    public void testInitialization() {
        assertNotNull(screenManager);
        assertNotNull(game);
    }

    //verifica che il metodo showscreen di screenmanager aggiorni correttamente lo stato della schermata attuale quando viene chiamato
    public void testShowScreenUpdatesCurrentScreen() {
        screenManager.showScreen(ScreenManager.ScreenType.MAIN_MENU);
        assertEquals(ScreenManager.ScreenType.MAIN_MENU, screenManager.getCurrentScreen());

        screenManager.showScreen(ScreenManager.ScreenType.GAME);
        assertEquals(ScreenManager.ScreenType.GAME, screenManager.getCurrentScreen());
    }

    //controlla che il sistema di gestione delle schermate funzioni correttamente
    public void testReturnToPreviousScreen() {
        screenManager.showScreen(ScreenManager.ScreenType.MAIN_MENU);
        screenManager.showScreen(ScreenManager.ScreenType.GAME);

        assertEquals(ScreenManager.ScreenType.GAME, screenManager.getCurrentScreen());
        assertEquals(ScreenManager.ScreenType.MAIN_MENU, screenManager.getPreviousScreen());

        screenManager.returnToPreviousScreen();

        assertEquals(ScreenManager.ScreenType.MAIN_MENU, screenManager.getCurrentScreen());
        assertEquals(ScreenManager.ScreenType.GAME, screenManager.getPreviousScreen());
    }

    //verifica che le schermate vengano correttamente rimosse quando viene chiamato dispose su screenmanager
    public void testDisposeScreens() {
        screenManager.showScreen(ScreenManager.ScreenType.MAIN_MENU);
        screenManager.showScreen(ScreenManager.ScreenType.GAME);

        screenManager.dispose();

        assertNull(screenManager.getGameScreen()); // Verifica che le schermate siano state rimosse
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }
}
