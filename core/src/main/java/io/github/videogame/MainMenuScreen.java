package io.github.videogame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

public class MainMenuScreen implements Screen {

    //La grandezza di apertura del menu è definita in SpaceGame.java

    //Variabili per la grandezza dei bottoni
    private static final int EXIT_BUTTON_WIDTH = 300;
    private static final int EXIT_BUTTON_HEIGHT = 150;
    private static final int PLAY_BUTTON_WIDTH = 330;
    private static final int PLAY_BUTTON_HEIGHT = 150;
    private static final int LOAD_BUTTON_WIDTH = 330;
    private static final int LOAD_BUTTON_HEIGHT = 150;

    private static final int PLAY_BUTTON_Y = 500;
    private static final int LOAD_BUTTON_Y = 300;
    private static final int EXIT_BUTTON_Y = 100;

    //Attributi
    SpaceGame game;
    Texture background;
    Texture playButtonActive;
    Texture playButtonInactive;
    Texture loadButtonActive;
    Texture loadButtonInactive;
    Texture exitButtonActive;
    Texture exitButtonInactive;
    Music menuMusic;
    Sound buttonClickSound;


    //Costruttore del Menu prevede: background, tasti, e musica
    public MainMenuScreen(SpaceGame game){
        this.game = game;
        playButtonInactive = new Texture("play_button_active.png"); //Mettere button play
        playButtonActive = new Texture("libgdx.png");
        loadButtonInactive = new Texture("load_button_active.png");
        loadButtonActive = new Texture("libgdx.png");
        exitButtonInactive = new Texture("exit_button_active.png");
        exitButtonActive = new Texture("libgdx.png");
        background = new Texture("Sfondo menu principale.png");
        menuMusic = Gdx.audio.newMusic(Gdx.files.internal("main_menu_music_dbd.mp3")); // Sostituisci con il nome del tuo file audio
        menuMusic.setLooping(true); // La musica riparte automaticamente una volta finita
        menuMusic.setVolume(0.5f);  // Imposta il volume (da 0.0 a 1.0)
        menuMusic.play();           // Inizia a riprodurre la musica
        buttonClickSound = Gdx.audio.newSound(Gdx.files.internal("button_click.mp3")); // Sostituisci con il nome del file audio


    }

    @Override
    public void show() {

    }

    @Override
    public void render(float v) {

        Gdx.gl.glClearColor(1,0,0,1); //Ripulisco lo schermo
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();

        game.batch.draw(background, 0, 0, SpaceGame.WIDTH, SpaceGame.HEIGHT);



        int x = SpaceGame.WIDTH / 2 - EXIT_BUTTON_WIDTH / 2;

        if(Gdx.input.getX()< x+PLAY_BUTTON_WIDTH && Gdx.input.getX() > x && SpaceGame.HEIGHT - Gdx.input.getY() < PLAY_BUTTON_Y + PLAY_BUTTON_HEIGHT && SpaceGame.HEIGHT - Gdx.input.getY() > PLAY_BUTTON_Y  ) {
            game.batch.draw(playButtonActive, x, PLAY_BUTTON_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
            if(Gdx.input.isTouched()) {
                this.dispose();
                buttonClickSound.play(1.0f);
                game.setScreen(new MainGameScreen(game));
            }
        }
        else {
            game.batch.draw(playButtonInactive, x, PLAY_BUTTON_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
            if (Gdx.input.isTouched()) {
                buttonClickSound.play(1.0f);
            }
        }
        if(Gdx.input.getX()< x+LOAD_BUTTON_WIDTH && Gdx.input.getX() > x && SpaceGame.HEIGHT - Gdx.input.getY() < LOAD_BUTTON_Y + LOAD_BUTTON_HEIGHT && SpaceGame.HEIGHT - Gdx.input.getY() > LOAD_BUTTON_Y  ) {
            game.batch.draw(loadButtonActive, x, LOAD_BUTTON_Y, LOAD_BUTTON_WIDTH, LOAD_BUTTON_HEIGHT);
            if (Gdx.input.isTouched()) {
                buttonClickSound.play(1.0f);
            }
        }
        else
            game.batch.draw(loadButtonInactive, x,LOAD_BUTTON_Y, LOAD_BUTTON_WIDTH, LOAD_BUTTON_HEIGHT);
             if (Gdx.input.isTouched()) {
                 buttonClickSound.play(1.0f);
             }


        if(Gdx.input.getX()< x+EXIT_BUTTON_WIDTH && Gdx.input.getX() > x && SpaceGame.HEIGHT - Gdx.input.getY() < EXIT_BUTTON_Y + EXIT_BUTTON_HEIGHT && SpaceGame.HEIGHT - Gdx.input.getY() > EXIT_BUTTON_Y  ) {
            game.batch.draw(exitButtonActive, x, EXIT_BUTTON_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
            if(Gdx.input.isTouched()) {
                buttonClickSound.play();
                Gdx.app.exit();
            }
        }
        else
            game.batch.draw(exitButtonInactive, x,EXIT_BUTTON_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);

        game.batch.end();

    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        playButtonActive.dispose();
        playButtonInactive.dispose();
        loadButtonActive.dispose();
        loadButtonInactive.dispose();
        exitButtonActive.dispose();
        exitButtonInactive.dispose();
        background.dispose();
        menuMusic.stop();
        menuMusic.dispose();
        buttonClickSound.dispose();

    }
}
