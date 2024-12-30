package io.github.videogame.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import io.github.videogame.model.Gioco;
import io.github.videogame.model.Utility;
import io.github.videogame.view.screens.MenuScreen;
import io.github.videogame.view.screens.SettingsScreen;

public class SettingsController {
    private final Gioco game;
    private final SettingsScreen view;
    private Screen prevScreen;
    private Sound sound;

    public SettingsController(Gioco game, SettingsScreen view, Screen prevScreen) {
        this.game = game;
        this.view = view;
        this.prevScreen = prevScreen;
    }

    public void setup() {
        this.sound = Utility.getAsset("menu/button-click.mp3", Sound.class);

        view.getMusicSlider().setValue(1);
        view.getMusicSlider().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

            }
        });

        view.getSoundSlider().setValue(1);
        view.getSoundSlider().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

            }
        });

        view.getvSyncCheckBox().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.graphics.setVSync(view.getvSyncCheckBox().isChecked());
            }
        });

        view.getBackButton().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(prevScreen);
            }
        });

    }
}
