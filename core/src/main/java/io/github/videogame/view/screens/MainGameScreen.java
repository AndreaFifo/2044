package io.github.videogame.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import io.github.videogame.model.*;
import io.github.videogame.controller.MovementController;

import java.util.List;


//Lo scopo di questa classe è gestire la finestra della sessione di gioco

public class MainGameScreen implements Screen {
    private Gioco game;
    private Player player;
    private int stateDirection;
    private MovementController movementController;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private MagneticKey magneticKey;
    private FlashDrive chiavettaUSB;
    private DialogManager dialogoUSB;
    private MapManager mapManager;

    String mapFile = "Mappe/mappa-prova.tmx";

    // Costruttore
    public MainGameScreen(Gioco game)
    {
        this.game = game;
        this.show();
    }


    @Override
    public void show() {
        this.batch = game.getBatch();
        this.stateDirection=0;
        this.player = Player.getInstance("player/player-spritesheet.png");  // Inizializza l'entità con la sprite sheet
        this.movementController = new MovementController(400, 105);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 960, 540);
        this.mapManager = new MapManager(mapFile,camera);
        this.dialogoUSB = new DialogManager();
        this.magneticKey = new MagneticKey(420, 100, movementController, player,this);
        this.chiavettaUSB = new FlashDrive(500, 500, movementController, player,this);
    }

    //metodo utilizzato per il menù di pausa, viene chiamato durante il metodo render per matentenere visibile lo stato attuale del gioco
    public void renderStaticState(SpriteBatch batch) {
        TextureRegion currentFrame = player.getCurrentFrame(//si utilizza per determinare quale frame della sprite sheet del personaggio diseganre
            movementController.getStateDirection(),
            movementController.isPlayerMoving(),
            0
        );


        batch.draw(currentFrame, movementController.getX(), movementController.getY());
    }
    private void drawElevatorMenu() {
        for (Rectangle elevator : mapManager.getElevatorRectangles()) {
            if ((Gdx.input.isKeyJustPressed(Input.Keys.E)) && movementController.isColliding2(
                movementController.getX(), movementController.getY(), List.of(elevator))) {
                String targetMap = mapManager.getElevatorTargetMaps().get(elevator);
                game.setScreen(new ElevatorMenu(game, this, targetMap));
                break;
            }
        }
    }


    @Override
    public void render(float delta) {

        //modifica
        // Controlla se il tasto ESCAPE è stato premuto
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen(new MenuPausa(game, this)); // Mostra il menu pausa
            return;
        }
        //


        movementController.changeStateDirection(delta, mapManager.getWallRectangles());// Aggiorna la posizione in base all'input

        // Ottieni la direzione corrente
        int currentDirection = movementController.getStateDirection();

        // Verifica se il personaggio è in movimento
        boolean isMoving = movementController.isPlayerMoving();

        // Ottieni il fotogramma attuale in base alla direzione e allo stato di movimento
        TextureRegion currentFrame = player.getCurrentFrame(currentDirection, isMoving, delta);

        // Pulisci lo schermo
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.position.set(movementController.getX(), movementController.getY(), 0f);
        camera.update();

        mapManager.render();
        batch.setProjectionMatrix(camera.combined);

        // Disegna il personaggio
        batch.begin();
        player.getInventory().drawInventory(batch,movementController);
        drawObjects();
        drawElevatorMenu();
        // batch.draw(Utility.getAsset("menu/bg-menu.png", Texture.class), 0, 0, 1920, 1080);
        batch.draw(currentFrame, movementController.getX(), movementController.getY());
        batch.end();
    }


    //Disegna gli oggetti, SOLO SE IL LORO STATO TAKEN è FALSO
    private void drawObjects() {
        if (!magneticKey.isTaken()) {
            batch.draw(magneticKey.getTexture(), magneticKey.getX(), magneticKey.getY(), 16, 16);
            magneticKey.pickUp();
        }

        if (!chiavettaUSB.isTaken()) {
            batch.draw(chiavettaUSB.getTexture(), chiavettaUSB.getX(), chiavettaUSB.getY(), 16, 16);
            chiavettaUSB.pickUp();

        }
    }


    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        if (player != null) {
            player.dispose();
        }
        if (mapManager != null) {
            mapManager.dispose();
        }
    }

    public void updateMap(String mapFilePath) {
        if (mapManager != null) {
            mapManager.dispose(); // Rilascia risorse della vecchia mappa
        }
        mapFile = mapFilePath;
        System.out.println(mapFile);
        this.mapManager = new MapManager( mapFilePath, camera); // Carica la nuova mappa
        //movementController = new MovementController(400, 105); // Reimposta il giocatore
        camera.position.set(400, 105, 0); // Reimposta la posizione della telecamera
        camera.update();
        System.out.println("Mappa aggiornata a: " + mapFilePath);
    }
    /*private boolean isCollisionWithMapLayer(Rectangle boundingBox) {
        //Ottengo il layer delle collisioni
        MapLayer mapCollisionLayer = mapManager.getCollisionLayer();

        //se non esiste restituisco falso
        if(mapCollisionLayer == null)
            return false;

        Rectangle rectangle = null;

        //Per ogni oggetto presente nel collision layer itero
        for(MapObject object : mapCollisionLayer.getObjects()) {
            //controllando se esso è un'instanza di RectangleMapObject
            if(object instanceof RectangleMapObject) {
                //se lo è lo assegno a rectangle effettuando un casting
                rectangle = ((RectangleMapObject) object).getRectangle();

                //se il player overlappa il rettangolo allo restituisco true.
                if(boundingBox.overlaps(rectangle)) {
                    return true;
                }
            }
        }
        return false;
    }
*/
}
