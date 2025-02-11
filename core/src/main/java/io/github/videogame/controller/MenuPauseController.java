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
                GameStateController.getInstance().saveGameState();
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
