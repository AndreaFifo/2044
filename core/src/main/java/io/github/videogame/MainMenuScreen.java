package io.github.videogame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;


//Avvia il menu di gioco

public class MainMenuScreen implements Screen {


    //Grandezza dei bottoni
    private static final int BUTTONS_WIDTH = 300;
    private static final int BUTTONS_HEIGHT = 150;

    //Permetterà di centrare i bottoni nello schermo
    private static final int X = (SpaceGame.WIDTH / 2)-(BUTTONS_WIDTH / 2);

    //Altezza dei bottoni
    private static final int PLAY_BUTTON_Y = 500;
    private static final int LOAD_BUTTON_Y = 300;
    private static final int EXIT_BUTTON_Y = 100;

    //Componenti del MainMenuScreen
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

    //Costruttore
    public MainMenuScreen(SpaceGame game){
        this.game = game;

        //Inizializzo le texture dei bottoni
        playButtonInactive = new Texture("play_button_active.png"); //Mettere button play
        playButtonActive = new Texture("libgdx.png");
        loadButtonInactive = new Texture("load_button_active.png");
        loadButtonActive = new Texture("libgdx.png");
        exitButtonInactive = new Texture("exit_button_active.png");
        exitButtonActive = new Texture("libgdx.png");

        //Inizializzo la texture dello sfondo del menu
        background = new Texture("Sfondo menu principale.png");

        //Inizializzo la musica di sottofondo
        menuMusic = Gdx.audio.newMusic(Gdx.files.internal("main_menu_music_dbd.mp3")); // Sostituisci con il nome del tuo file audio
        menuMusic.setLooping(true); // La musica riparte automaticamente una volta finita
        menuMusic.setVolume(0.5f);  // Imposta il volume (da 0.0 a 1.0)
        menuMusic.play();           // Inizia a riprodurre la musica

        //Inizializzo l'audio dei bottoni
        buttonClickSound = Gdx.audio.newSound(Gdx.files.internal("button_click.mp3")); // Sostituisci con il nome del file audio
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float v) {

        //Ripulisco lo schermo fra i vari frame
        Gdx.gl.glClearColor(1,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //inizio il disegno
        game.batch.begin();

        //Imposto la grandezza dello sfondo
        game.batch.draw(background, 0, 0, SpaceGame.WIDTH, SpaceGame.HEIGHT);



        /*Disegno il bottone PLAY:
            -Se il cursore è sui pixel del tasto la texture disegnata sarà playButtonActive
            -Se il cursore non è sui pixel del tasto la textura disegnara sarà playButtonInactive
        */
        if(Gdx.input.getX()< X+ BUTTONS_WIDTH && Gdx.input.getX() > X && SpaceGame.HEIGHT - Gdx.input.getY() < PLAY_BUTTON_Y + BUTTONS_HEIGHT && SpaceGame.HEIGHT - Gdx.input.getY() > PLAY_BUTTON_Y  ) {
            game.batch.draw(playButtonActive, X, PLAY_BUTTON_Y, BUTTONS_WIDTH, BUTTONS_HEIGHT);
            if(Gdx.input.isTouched()) {
                this.dispose();
                buttonClickSound.play(1.0f);
                game.setScreen(new MainGameScreen(game));
            }
        }
        else
            game.batch.draw(playButtonInactive, X, PLAY_BUTTON_Y, BUTTONS_WIDTH, BUTTONS_HEIGHT);




        /*Disegno il bottone LOAD:
            -Se il cursore è sui pixel del tasto la texture disegnata sarà playButtonActive
            -Se il cursore non è sui pixel del tasto la textura disegnara sarà playButtonInactive
        */
        if(Gdx.input.getX()< X+ BUTTONS_WIDTH && Gdx.input.getX() > X && SpaceGame.HEIGHT - Gdx.input.getY() < LOAD_BUTTON_Y + BUTTONS_HEIGHT && SpaceGame.HEIGHT - Gdx.input.getY() > LOAD_BUTTON_Y  ) {
            game.batch.draw(loadButtonActive, X, LOAD_BUTTON_Y, BUTTONS_WIDTH, BUTTONS_HEIGHT);
            if (Gdx.input.isTouched()) {
                buttonClickSound.play(1.0f);
            }
        }
        else
            game.batch.draw(loadButtonInactive, X,LOAD_BUTTON_Y, BUTTONS_WIDTH, BUTTONS_HEIGHT);




        /*Disegno il bottone EXIT:
            -Se il cursore è sui pixel del tasto la texture disegnata sarà playButtonActive
            -Se il cursore non è sui pixel del tasto la textura disegnara sarà playButtonInactive
        */
        if(Gdx.input.getX()< X+ BUTTONS_WIDTH && Gdx.input.getX() > X && SpaceGame.HEIGHT - Gdx.input.getY() < EXIT_BUTTON_Y + BUTTONS_HEIGHT && SpaceGame.HEIGHT - Gdx.input.getY() > EXIT_BUTTON_Y  ) {
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
