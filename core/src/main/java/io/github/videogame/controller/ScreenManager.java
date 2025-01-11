package io.github.videogame.controller;

import com.badlogic.gdx.Screen;
import io.github.videogame.model.Gioco;
import io.github.videogame.view.screens.MainGameScreen;
import io.github.videogame.view.screens.MenuPause;
import io.github.videogame.view.screens.MenuScreen;
import io.github.videogame.view.screens.SettingsScreen;

import java.util.HashMap;
import java.util.Map;

public class ScreenManager {
    public enum ScreenType {
        MAIN_MENU,
        GAME,
        PAUSE,
        SETTINGS
    }

    private static ScreenManager instance;
    private Gioco game;
    private final Map<ScreenType, Screen> screens;
    private ScreenType currentScreen;
    private ScreenType previousScreen;

    // Costruttore privato
    private ScreenManager() {
        screens = new HashMap<>();
    }

    public static ScreenManager getInstance() {
            synchronized (ScreenManager.class) {
                if (instance == null) {
                    instance = new ScreenManager();
                }
            }

        return instance;
    }

    /**
     * Inizializza il ScreenManager con l'istanza del gioco.
     *
     * @param game Istanza del gioco.
     */
    public void init(Gioco game) {
        if (game == null) {
            throw new IllegalArgumentException("Game instance cannot be null.");
        }
        this.game = game;
        // Imposta la schermata iniziale, ad esempio il menu principale
        showScreen(ScreenType.MAIN_MENU);
    }

    /**
     * Mostra la schermata specificata.
     *
     * @param screenType Tipo di schermata da mostrare.
     */
    public void showScreen(ScreenType screenType) {
        if (game == null) {
            throw new IllegalStateException("ScreenManager must be initialized with a Game instance before showing screens.");
        }

        if (screenType == null) {
            throw new IllegalArgumentException("ScreenType cannot be null.");
        }

        if (screenType == currentScreen) {
            System.out.println("La schermata " + screenType + " è già attiva.");
            return;
        }

        // Aggiorna previousScreen solo se c'è una schermata attuale
        if (currentScreen != null) {
            previousScreen = currentScreen;
            System.out.println("Impostato previousScreen a: " + previousScreen);
        }

        currentScreen = screenType;
        System.out.println("Impostato currentScreen a: " + currentScreen);

        // Ottieni o crea la schermata
        Screen screen = screens.get(screenType);
        if (screen == null) {
            screen = createScreen(screenType);
            screens.put(screenType, screen);
            System.out.println("Creata nuova schermata: " + screenType);
        }

        // Imposta la nuova schermata nel gioco
        game.setScreen(screen);
        log();
    }

    /**
     * Torna alla schermata precedente, se disponibile.
     */
    public void returnToPreviousScreen() {
        System.out.println("Cambiando schermata");
        if (previousScreen == null) {
            System.out.println("Non c'è nessuna schermata precedente a cui tornare.");
            return;
        }

        if (previousScreen == currentScreen) {
            System.out.println("La schermata precedente è la stessa di quella attuale. Nessuna azione eseguita.");
            return;
        }

        ScreenType targetScreen = currentScreen;
        currentScreen = previousScreen;
        previousScreen = targetScreen;
        System.out.println("Scambio avvenuto diopo");
        log();

        System.out.println("Tornando alla schermata precedente: " + currentScreen);

        // Ottieni la schermata target
        Screen screen = screens.get(currentScreen);
        if (screen == null) {
            screen = createScreen(currentScreen);
            screens.put(currentScreen, screen);
            System.out.println("Creata nuova schermata: " + currentScreen);
        }

        // Imposta la schermata target nel gioco
        game.setScreen(screen);
        log();
    }

    /**
     * Crea una schermata in base al tipo specificato.
     *
     * @param screenType Tipo di schermata da creare.
     * @return Istanza della schermata creata.
     */
    private Screen createScreen(ScreenType screenType) {
        switch (screenType) {
            case MAIN_MENU:
                return new MenuScreen(game);
            case GAME:
                return new MainGameScreen(game);
            case PAUSE:
                return new MenuPause(game);
            case SETTINGS:
                return new SettingsScreen(game);
            default:
                throw new IllegalArgumentException("Tipo di schermata non supportato: " + screenType);
        }
    }

    /**
     * Dispone tutte le schermate gestite.
     */
    public void dispose() {
        for (Map.Entry<ScreenType, Screen> entry : screens.entrySet()) {
            Screen screen = entry.getValue();
            if (screen != null) {
                screen.dispose();
                System.out.println("Disposto schermata: " + entry.getKey());
            }
        }
        screens.clear();
    }

    /**
     * Restituisce la schermata attuale.
     *
     * @return Tipo di schermata attuale.
     */
    public ScreenType getCurrentScreen() {
        return currentScreen;
    }

    /**
     * Restituisce la schermata precedente.
     *
     * @return Tipo di schermata precedente.
     */
    public ScreenType getPreviousScreen() {
        return previousScreen;
    }

    /**
     * Restituisce la schermata di gioco attuale.
     *
     * @return Istanza di MainGameScreen.
     */
    public MainGameScreen getGameScreen() {
        Screen screen = screens.get(ScreenType.GAME);
        if (screen instanceof MainGameScreen) {
            return (MainGameScreen) screen;
        }
        return null;
    }

    /**
     * Logga lo stato attuale e precedente delle schermate.
     */
    public void log() {
        String current = (currentScreen != null) ? currentScreen.toString() : "None";
        String previous = (previousScreen != null) ? previousScreen.toString() : "None";
        System.out.println("Current: " + current + " | Previous: " + previous + "\n");
    }

}
