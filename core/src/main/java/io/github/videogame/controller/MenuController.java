package io.github.videogame.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import io.github.videogame.model.CareTaker;
import io.github.videogame.model.Gioco;
import io.github.videogame.model.Utility;
import io.github.videogame.view.screens.MainGameScreen;
import io.github.videogame.view.screens.MenuScreen;
import io.github.videogame.view.screens.VideoIntroScreen;
import io.github.videogame.model.GameState;

import java.util.ArrayList;

public class MenuController {
    private final Gioco game;
    private final MenuScreen view;
    private ScreenManager screenManager;
    private AudioController audioController;
    private Sound buttonClickSound;

    public MenuController(Gioco game, MenuScreen view) {
        this.game = game;
        this.view = view;
        this.screenManager = ScreenManager.getInstance();

        this.audioController = AudioController.getInstance();
    }

    public void setup() {
        this.buttonClickSound = Utility.getAsset("menu/button-click.mp3", Sound.class);

        view.getPlayBtn().addListener(new ChangeListener() {
            final Preferences preferences = Gdx.app.getPreferences("game_preferences");
            final boolean isFirstGame = preferences.getBoolean("first_game", true);

            @Override
            public void changed(ChangeEvent event, Actor actor) {
                buttonClickSound.play(audioController.getSoundsVolume());
                audioController.getMusic("menu/main-menu-music.mp3").stop();

                if(isFirstGame) {
                    preferences.putBoolean("first_game", false);
                    preferences.flush();
                    game.setScreen(new VideoIntroScreen(game));
                    return;
                }

                screenManager.showScreen(ScreenManager.ScreenType.GAME);
            }
        });

        view.getLoadBtn().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                buttonClickSound.play(audioController.getSoundsVolume());
                loadGameState();
            }
        });

        view.getSettingsBtn().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                buttonClickSound.play(audioController.getSoundsVolume());
                screenManager.showScreen(ScreenManager.ScreenType.SETTINGS);
            }
        });

        view.getExitBtn().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });
    }
    public void loadGameState() {
        try {
            FileHandle file = Gdx.files.internal("saves/game_save.json");
            String jsonString = file.readString();

            GameState gameState = new Json().fromJson(GameState.class, jsonString);

            CareTaker.saveMemento(gameState);

            // Ripristina lo stato del gioco usando il Memento
            MainGameScreen mainGameScreen = (MainGameScreen) ScreenManager.getInstance().createScreen(ScreenManager.ScreenType.GAME);
            mainGameScreen.restoreState(gameState);

            mainGameScreen.loadInventory(gameState.getInventory());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Errore durante il caricamento dello stato del gioco.");
        }
    }

//    public void loadGameState() {
//        try {
//            // Leggi il contenuto del file JSON
//            BufferedReader reader = new BufferedReader(new FileReader("saves/game_save.json"));
//            StringBuilder content = new StringBuilder();
//            String line;
//            while ((line = reader.readLine()) != null) {
//                content.append(line.trim());
//            }
//            reader.close();
//
//            System.out.println(content.toString());
//
//            //per trovare riga inventario
//            // Trova la riga che contiene "iteminventory"
//            String inventoryLine = "";
//            String jsonContent = content.toString();
//            int startIndex = jsonContent.indexOf("\"iteminventory\":"); // Trova l'inizio della sezione "iteminventory"
//            if (startIndex != -1) {
//                // Trova la posizione dell'inizio dell'array
//                int arrayStartIndex = jsonContent.indexOf("[", startIndex);  // Trova l'inizio dell'array
//                if (arrayStartIndex != -1) {
//                    // Trova la fine dell'array
//                    int arrayEndIndex = jsonContent.indexOf("]", arrayStartIndex);
//                    if (arrayEndIndex != -1) {
//                        // Estrai l'array senza le virgolette
//                        inventoryLine = jsonContent.substring(arrayStartIndex, arrayEndIndex + 1);  // Estrarre la parte che contiene l'inventario
//                    }
//                }
//            }
//
//            // Usa Gson per deserializzare il JSON nel GameState
//            Gson gson = new Gson();
//            Gamestate gameState = gson.fromJson(content.toString(), Gamestate.class);
//
//
//            if (gameState == null) {
//                System.out.println("Errore nel parsing del JSON. Il contenuto potrebbe non corrispondere alla struttura attesa.");
//                return;
//            }
//
//
//            // Aggiungi il Memento al Caretaker
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("Errore durante il caricamento dello stato del gioco.");
//        }
//    }

}
