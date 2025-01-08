package io.github.videogame.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import io.github.videogame.controller.AudioController;

public class Settings {
    private static Settings instance ;
    private AudioController audioController;

    private boolean vSync;

    final Preferences preferences = Gdx.app.getPreferences("game_settings");

    private Settings() {
        this.audioController = AudioController.getInstance();

        initalSettings();
    }

    private void initalSettings() {
        if(preferences.contains("music_volume"))
            audioController.setMusicVolume(preferences.getFloat("music_volume"));
        else {
            audioController.setMusicVolume(1.0f);
            preferences.putFloat("music_volume", 1.0f);
        }

        if(preferences.contains("sound_volume"))
            audioController.setSoundsVolume(preferences.getFloat("sound_volume"));
        else {
            audioController.setSoundsVolume(1.0f);
            preferences.putFloat("sound_volume", 1.0f);
        }

        if(preferences.contains("vSync"))
            vSync = preferences.getBoolean("vSync");
        else {
            vSync = false;
            preferences.putBoolean("vSync", vSync);
        }

        preferences.flush();
    }

    public static Settings getInstance() {
        if(instance == null)
            synchronized (Settings.class) {
                if (instance == null) {
                    instance = new Settings();
                }
            }

        return instance;
    }

    public boolean isvSync() {
        return vSync;
    }

    public float getSoundVolume() {
        return audioController.getSoundsVolume();
    }

    public float getMusicVolume() {
        return audioController.getMusicsVolume();
    }

    public void setMusicVolume(float musicVolume) {
        audioController.setMusicVolume(musicVolume);
        preferences.putFloat("music_volume", musicVolume);
        preferences.flush();
    }

    public void setSoundVolume(float soundVolume) {
        audioController.setSoundsVolume(soundVolume);
        preferences.putFloat("sound_volume", soundVolume);
        preferences.flush();
    }

    public void setVSync(boolean vSync) {
        this.vSync = vSync;
        preferences.putBoolean("vSync", vSync);
        preferences.flush();
    }
}
