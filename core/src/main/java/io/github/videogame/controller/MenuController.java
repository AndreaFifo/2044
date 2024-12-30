package io.github.videogame.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import io.github.videogame.model.Gioco;
import io.github.videogame.view.screens.MainGameScreen;
import io.github.videogame.model.Utility;
import io.github.videogame.view.screens.MenuScreen;
import io.github.videogame.view.screens.VideoIntroScreen;

public class MenuController {
    private final Gioco game;
    private final MenuScreen view;
    private final Sound buttonClickSound;

    public MenuController(Gioco game, MenuScreen view) {
        this.game = game;
        this.view = view;
        this.buttonClickSound = Utility.getAsset("menu/button-click.mp3", Sound.class);

        setupListeners();
    }

    private void setupListeners() {
        view.getPlayBtn().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                buttonClickSound.play();
                game.setScreen(new VideoIntroScreen(game));
            }
        });

        view.getLoadBtn().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                buttonClickSound.play();
            }
        });

        view.getSettingsBtn().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                buttonClickSound.play();
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
