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
    private SettingsScreen settingsScreen;
    private final Settings settings;
    private ScreenManager screenManager;
    private Sound sound;

    private MySlider musicSlider;
    private MySlider soundSlider;
    private CheckBox vSyncCheckBox;
    private TextButton backButton;

    public SettingsController(Gioco game, SettingsScreen settingsScreen) {
        this.game = game;
        this.settingsScreen = settingsScreen;
        this.screenManager = ScreenManager.getInstance();

        this.musicSlider = settingsScreen.getMusicSlider();
        this.soundSlider = settingsScreen.getSoundSlider();
        this.vSyncCheckBox = settingsScreen.getvSyncCheckBox();
        this.backButton = settingsScreen.getBackButton();

        this.settings = Settings.getInstance();
        this.sound = Utility.getAsset("menu/button-click.mp3", Sound.class);
    }

    public void setupValues() {
        musicSlider.setValue(settings.getMusicVolume());
        soundSlider.setValue(settings.getSoundVolume());
        vSyncCheckBox.setChecked(settings.isvSync());

        setSettingsListeners();
    }

    private void setSettingsListeners(){
        musicSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                settings.setMusicVolume(musicSlider.getValue());
            }
        });

        soundSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                settings.setSoundVolume(soundSlider.getValue());
            }
        });

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
                ScreenManager.getInstance().returnToPreviousScreen();
            }
        });
    }
}
