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
import io.github.videogame.model.*;
import io.github.videogame.view.screens.MainGameScreen;
import io.github.videogame.view.screens.MenuScreen;
import io.github.videogame.view.screens.VideoIntroScreen;

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

        view.getNewGameBtn().addListener(new ChangeListener() {
            final Preferences preferences = Gdx.app.getPreferences("game_preferences");
            final boolean isFirstGame = preferences.getBoolean("first_game", true);

            @Override
            public void changed(ChangeEvent event, Actor actor) {
                buttonClickSound.play(audioController.getSoundsVolume());
                audioController.getMusic("menu/main-menu-music.mp3").stop();
                GameStateController.getInstance().initialGameState();

                game.setScreen(new VideoIntroScreen(game));
            }
        });

        view.getContinueeBtn().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(!view.getContinueFlag())
                    return;

                buttonClickSound.play(audioController.getSoundsVolume());
                audioController.getMusic("menu/main-menu-music.mp3").stop();
                GameStateController.getInstance().loadGameState();
                screenManager.showScreen(ScreenManager.ScreenType.GAME);
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

}
