package io.github.videogame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

//Crea il menu principale del gioco
public class MainMenuScreen implements Screen {

    //Grandezza dei bottoni
    private static final int BUTTONS_WIDTH = 500;
    private static final int BUTTONS_HEIGHT = 120;
    //Imposta la posizione dei tasti
    private static final int X = 200;
    //Altezza dei bottoni
    private static final int PLAY_BUTTON_Y = (Gioco.HEIGHT / 2);
    private static final int LOAD_BUTTON_Y = PLAY_BUTTON_Y - (BUTTONS_HEIGHT + 10);
    private static final int SETTINGS_BUTTON_Y = LOAD_BUTTON_Y - (BUTTONS_HEIGHT + 10);
    private static final int EXIT_BUTTON_Y = SETTINGS_BUTTON_Y - (BUTTONS_HEIGHT + 10);


    private static final int GAME_TITLE_Y =  750;


    //Componenti del MainMenuScreen
    Gioco game;
    Texture background;
    Texture playButtonActive;
    Texture playButtonInactive;
    Texture loadButtonActive;
    Texture loadButtonInactive;
    Texture settingsButtonInactive;
    Texture settingsButtonActive;
    Texture exitButtonActive;
    Texture exitButtonInactive;
    Texture gameTitle;
    Music menuMusic;
    Sound buttonClickSound;

    //Costruttore
    public MainMenuScreen(Gioco game){
        this.game = game;
    }

    //Viene chiamato quando screen è attivato per la prima volta, viene utilizzato per inizializzare risorse, impostare stati ecc...
    @Override
    public void show() {
        this.playButtonInactive = new Texture("play_button_inactive.png"); //Mettere button play
        this.playButtonActive = new Texture("play_button_active.png");
        this.loadButtonInactive = new Texture("load_button_inactive.png");
        this.loadButtonActive = new Texture("load_button_active.png");
        this.settingsButtonInactive = new Texture("settings_button_inactive.png");
        this.settingsButtonActive = new Texture("settings_button_active.png");
        this.exitButtonInactive = new Texture("exit_button_inactive.png");
        this.exitButtonActive = new Texture("exit_button_active.png");
        this.gameTitle = new Texture("text-2044.png");
        this.background = new Texture("bg-menu.png");
        this.menuMusic = Gdx.audio.newMusic(Gdx.files.internal("main_menu_music_dbd.mp3"));
        this.buttonClickSound = Gdx.audio.newSound(Gdx.files.internal("button_click.mp3"));
        menuMusic.setLooping(true); // La musica riparte automaticamente una volta finita
        menuMusic.setVolume(0.5f);  // Imposta il volume (da 0.0 a 1.0)
        menuMusic.play();           // Inizia a riprodurre la musica
    }

    //Viene chiamato ad ogni frame per aggiornare la schermata di gioco
    @Override
    public void render(float v) {

        //Ripulisco lo schermo fra i vari frame
        Gdx.gl.glClearColor(1,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //inizio il disegno
        game.batch.begin();

        //Imposto la grandezza dello sfondo
        game.batch.draw(background, 0, 0, Gioco.WIDTH, Gioco.HEIGHT);

        game.batch.draw(gameTitle, X - 50, GAME_TITLE_Y, 600, 220);

        //Imposto il sistema dei bottoni
        if(Gdx.input.getX()< X+ BUTTONS_WIDTH && Gdx.input.getX() > X && Gioco.HEIGHT - Gdx.input.getY() < PLAY_BUTTON_Y + BUTTONS_HEIGHT && Gioco.HEIGHT - Gdx.input.getY() > PLAY_BUTTON_Y  ) {
            game.batch.draw(playButtonActive, X, PLAY_BUTTON_Y, BUTTONS_WIDTH, BUTTONS_HEIGHT);
            if(Gdx.input.isTouched()) {
                this.dispose();
                buttonClickSound.play(1.0f);
                game.setScreen(new MainGameScreen(game));
            }
        }
        else
            game.batch.draw(playButtonInactive, X, PLAY_BUTTON_Y, BUTTONS_WIDTH, BUTTONS_HEIGHT);


        if(Gdx.input.getX()< X+ BUTTONS_WIDTH && Gdx.input.getX() > X && Gioco.HEIGHT - Gdx.input.getY() < LOAD_BUTTON_Y + BUTTONS_HEIGHT && Gioco.HEIGHT - Gdx.input.getY() > LOAD_BUTTON_Y  ) {
            game.batch.draw(loadButtonActive, X, LOAD_BUTTON_Y, BUTTONS_WIDTH, BUTTONS_HEIGHT);
            if (Gdx.input.isTouched()) {
                buttonClickSound.play(1.0f);
            }
        }
        else
            game.batch.draw(loadButtonInactive, X,LOAD_BUTTON_Y, BUTTONS_WIDTH, BUTTONS_HEIGHT);

        if(Gdx.input.getX()< X+ BUTTONS_WIDTH && Gdx.input.getX() > X && Gioco.HEIGHT - Gdx.input.getY() < SETTINGS_BUTTON_Y + BUTTONS_HEIGHT && Gioco.HEIGHT - Gdx.input.getY() > SETTINGS_BUTTON_Y  ) {
            game.batch.draw(settingsButtonActive, X, SETTINGS_BUTTON_Y, BUTTONS_WIDTH, BUTTONS_HEIGHT);
            if (Gdx.input.isTouched()) {
                buttonClickSound.play(1.0f);
            }
        }
        else
            game.batch.draw(settingsButtonInactive, X, SETTINGS_BUTTON_Y, BUTTONS_WIDTH, BUTTONS_HEIGHT);


        if(Gdx.input.getX()< X+ BUTTONS_WIDTH && Gdx.input.getX() > X && Gioco.HEIGHT - Gdx.input.getY() < EXIT_BUTTON_Y + BUTTONS_HEIGHT && Gioco.HEIGHT - Gdx.input.getY() > EXIT_BUTTON_Y  ) {
            game.batch.draw(exitButtonActive, X, EXIT_BUTTON_Y, BUTTONS_WIDTH, BUTTONS_HEIGHT);
            if(Gdx.input.isTouched()) {
                buttonClickSound.play();
                Gdx.app.exit();
            }
        }
        else
            game.batch.draw(exitButtonInactive, X,EXIT_BUTTON_Y, BUTTONS_WIDTH, BUTTONS_HEIGHT);


        game.batch.end();

    }

    //Viene chiamato quando la finestra di gioco viene ridimensionata
    @Override
    public void resize(int width, int height) {

    }

    //Viene chiamato quando il gioco viene messo in pausa/ si minimizza la finestra/si naviga fuori dal gioco
    @Override
    public void pause() {
        menuMusic.pause();
    }


    //Viene chiamato quando il gioco riprende dopo essere stato messo in pausa (pausa quando clicchi settings?)
    @Override
    public void resume() {
        menuMusic.play();
    }

    //Viene chiamato quando lo screen sta per essere nascoto o cambiato
    @Override
    public void hide() {
    }

    //Viene chiamato quando lo screen non è più necessario e sta per esseere distrutto
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
