package io.github.videogame.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Json;
import io.github.videogame.model.*;
import io.github.videogame.view.screens.MainGameScreen;
import io.github.videogame.view.screens.MenuPause;

public class MenuPauseController {
    private static MenuPauseController instance;
    private final Gioco game;
    private final MenuPause view;
    private ScreenManager screenManager;
    private AudioController audioController;
    private Sound buttonClickSound;

    public MenuPauseController(Gioco game, MenuPause view) {
        this.game = game;
        this.view = view;
        this.screenManager = ScreenManager.getInstance();

        this.audioController = AudioController.getInstance();
        this.buttonClickSound = Utility.getAsset("menu/button-click.mp3", Sound.class);
    }

    public static MenuPauseController getInstance(Gioco game, MenuPause view) {
        if(instance == null)
            synchronized (MenuPauseController.class) {
                if (instance == null) {
                    instance = new MenuPauseController(game, view);
                }
            }

        return instance;
    }

    public void setUpBtnsListeners() {
        view.getResumeButton().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
            buttonClickSound.play(audioController.getSoundsVolume());
            screenManager.showScreen(ScreenManager.ScreenType.GAME);
            }
        });

        view.getSaveButton().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
            try {
                MainGameScreen mainGameScreen=screenManager.getGameScreen();
                // Usa il metodo createMemento per ottenere lo stato corrente del gioco
                GameState memento = mainGameScreen.createMemento();
                memento.setItemInventory(Player.getInstance().getInventory().getItemInventory());

                CareTaker.saveMemento(memento);

                System.out.println(memento.getInventory());

                Json json = new Json();

                String jsonString = json.toJson(memento);

                FileHandle file = Gdx.files.local("saves/game_save.json");
                file.writeString(jsonString, false);

            } catch (Exception e) {
                e.printStackTrace();
            }
    //                try {
    //                    MainGameScreen mainGameScreen=screenManager.getGameScreen();
    //                    // Usa il metodo createMemento per ottenere lo stato corrente del gioco
    //                    GameState memento = mainGameScreen.createMemento();
    //                    //creo il caretaker che conterrà l'ultimo salvataggio svolto
    //
    //                    CareTaker.saveMemento(memento);
    //                    // Costruisci una rappresentazione dello stato del gioco
    //                    Map<String, Object> gameState = new HashMap<>();
    //                    ArrayList<String> iteminventory=new ArrayList<String>();
    //                    iteminventory=memento.getIteminventary();
    //                    System.out.println(iteminventory);
    //
    //
    //                    gameState.put("playerX", memento.getPlayerX());
    //                    gameState.put("playerY", memento.getPlayerY());
    //                    //if(MainGameScreen.f_map==false)
    //                    gameState.put("currentMap", memento.getCurrentMap());
    //                    //else
    //                    //    gameState.put("currentMap", mainGameScreen.m);
    //                    gameState.put("iteminventary",iteminventory);
    //
    //                    // Converti lo stato in JSON
    //                    StringBuilder json = new StringBuilder();
    //                    json.append("{\n");
    //                    json.append("  \"playerX\": ").append(memento.getPlayerX()).append(",\n");
    //                    json.append("  \"playerY\": ").append(memento.getPlayerY()).append(",\n");
    //                    json.append("  \"currentMap\": \"").append(memento.getCurrentMap()).append("\",\n");
    //                    json.append("  \"currentTask\": \"").append(memento.getIdCurrentTask()).append("\",\n");
    //                    json.append("  \"iteminventory\": \"").append(memento.getIteminventary()).append("\"\n");
    //                    json.append("}");
    //
    //                    // Salva lo stato su file
    //                    FileWriter fileWriter = new FileWriter("saves/game_save.json");
    //                    fileWriter.write(json.toString());
    //                    fileWriter.close();
    //
    //                    System.out.println("Stato del gioco salvato correttamente!");
    //                } catch (Exception e) {
    //                    e.printStackTrace();
    //                }
            }
        });

        view.getSettingsButton().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
            buttonClickSound.play(audioController.getSoundsVolume()); // Riproduce il suono
            screenManager.showScreen(ScreenManager.ScreenType.SETTINGS);
            }
        });

        view.getMainmenuButton().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
            buttonClickSound.play(audioController.getSoundsVolume());
            screenManager.showScreen(ScreenManager.ScreenType.MAIN_MENU);
            }
        });
    }

}
