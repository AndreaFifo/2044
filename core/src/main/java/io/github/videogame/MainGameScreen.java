package io.github.videogame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MainGameScreen implements Screen {

    //Variabili e Costanti
    public static final float SPEED = 150;
    public static final float SHIP_ANIMATION_SPEED = 0.5f;
    public static final int SHIP_WIDTH_PIXEL =40;
    public static final int SHIP_HEIGHT_PIXEL =80;
    public static final int SHIP_WIDTH = SHIP_WIDTH_PIXEL*3;
    public static final int SHIP_HEIGHT = SHIP_HEIGHT_PIXEL*3;

    Animation<TextureRegion>[] rolls; //Contiene le animazioni della navicella
    float [] positions;
    float x, y; //Coordinate della navicella sullo schermo
    SpaceGame game; //Riferimento alla classe principale del gioco
    int roll; //Identifica l'animazione attuale
    float stateTime;


    // Costruttore
    public MainGameScreen(SpaceGame game) {
        this.game = game; // Assegna il riferimento al gioco
        y=15;
        x = (float) SpaceGame.WIDTH /2 - (float) SHIP_WIDTH /2;

        roll = 2;
        rolls = new Animation[5];

//        rolls =  new Animation[5];
        positions = new float[5];

        TextureRegion[][] rollSpriteSheet = TextureRegion.split(new Texture("orc1_walk_full.png"), SHIP_WIDTH_PIXEL, SHIP_HEIGHT_PIXEL);

        rolls[roll] = new Animation(SHIP_ANIMATION_SPEED, rollSpriteSheet[0]);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP))
        //    y = y + 4;
            y += SPEED * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN))
        //    y = y - 4;
            y -= SPEED * Gdx.graphics.getDeltaTime();

        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT))
        //    x = x - 4;
            x -= SPEED * Gdx.graphics.getDeltaTime();

        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT))
        //    x = x + 4;
            x += SPEED * Gdx.graphics.getDeltaTime();

        stateTime += delta;
        Gdx.gl.glClearColor(1,0,0,1); //Ripulisco lo schermo
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();

 //       game.batch.draw(rolls[roll].getKeyFrame(stateTime, true), x, y, SHIP_WIDTH, SHIP_HEIGHT);

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

    }
}
