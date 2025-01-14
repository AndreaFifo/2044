package io.github.videogame.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import io.github.videogame.model.Utility;

import java.util.HashMap;

/*
* Per poter cambiare il volume dei suoni, è necessario passare l'id del suono che
* si ottiene solamente dopo aver invocato il metodo play del suono in questione.
* Quindi si preferisce usare l'istanza di AudioController per farsi passare
* il volume dei suoni e passarlo al metodo play.
* */
public class AudioController {
    private static AudioController instance;

    private HashMap<String, Music> musics;
    private HashMap<String, Sound> sounds;
    private float musicsVolume;
    private float soundsVolume;

    final Preferences preferences = Gdx.app.getPreferences("game_settings");

    public AudioController() {
        this.musics = new HashMap<>();
        this.sounds = new HashMap<>();
        this.musicsVolume = preferences.getFloat("music_volume", 1.0f);
        this.soundsVolume = preferences.getFloat("sound_volume", 1.0f);
    }

    public static AudioController getInstance() {
        if(instance == null)
            synchronized (AudioController.class) {
                if (instance == null) {
                    instance = new AudioController();
                }
            }

        return instance;
    }

    public void addNewMusic(String musicPath, Music newMusic){
        newMusic.setVolume(musicsVolume);
        musics.put(musicPath,newMusic);
    }

    public void addNewSound(String soundPath, Sound newSound){
        sounds.put(soundPath, newSound);
    }

    public Music getMusic(String musicPath){
        return musics.get(musicPath);
    }

    public float getMusicsVolume() {
        return musicsVolume;
    }

    public float getSoundsVolume() {
        return soundsVolume;
    }

    public void setMusicVolume(float musicVolume){
        this.musicsVolume = musicVolume;
        for(Music music : musics.values())
            music.setVolume(musicVolume);
    }

    public void setSoundsVolume(float soundsVolume) {
        this.soundsVolume = soundsVolume;
    }

    public void stopAllSounds(){
        for(Sound sound : sounds.values())
            sound.stop();
    }
}
