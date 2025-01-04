package io.github.videogame.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import io.github.videogame.model.Gioco;
import io.github.videogame.model.Settings;
import io.github.videogame.model.Utility;
import io.github.videogame.view.screens.MenuScreen;
import io.github.videogame.view.screens.MySlider;
import io.github.videogame.view.screens.SettingsScreen;

public class SettingsController {
    private final Gioco game;
    private final Settings settings;
    private Screen prevScreen;
    private Sound sound;

    private MySlider musicSlider;
    private MySlider soundSlider;
    private CheckBox vSyncCheckBox;
    private TextButton backButton;

    public SettingsController(Gioco game, SettingsScreen view, Screen prevScreen) {
        this.game = game;
        this.prevScreen = prevScreen;

        this.musicSlider = view.getMusicSlider();
        this.soundSlider = view.getSoundSlider();
        this.vSyncCheckBox = view.getvSyncCheckBox();
        this.backButton = view.getBackButton();

        this.settings = Settings.getInstance();
    }

    public void setup() {
        this.sound = Utility.getAsset("menu/button-click.mp3", Sound.class);

        musicSlider.setValue(settings.getMusicVolume());
        musicSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                settings.setMusicVolume(musicSlider.getValue());
            }
        });

        soundSlider.setValue(settings.getSoundVolume());
        soundSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                settings.setSoundVolume(soundSlider.getValue());
            }
        });

        vSyncCheckBox.setChecked(settings.isvSync());
        vSyncCheckBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.graphics.setVSync(vSyncCheckBox.isChecked());
                settings.setVSync(vSyncCheckBox.isChecked());
            }
        });

        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(prevScreen);
            }
        });

    }
}
