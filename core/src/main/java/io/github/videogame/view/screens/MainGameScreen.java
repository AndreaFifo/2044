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
import io.github.videogame.controller.ScreenManager;
import io.github.videogame.model.*;
import io.github.videogame.controller.MovementController;
import io.github.videogame.view.TaskView;

import java.util.List;
import java.util.Objects;


//Lo scopo di questa classe è gestire la finestra della sessione di gioco

public class MainGameScreen implements Screen {
    private Gioco game;
    private Player player;
    private int stateDirection;
    private MovementController movementController;
    private ScreenManager screenManager;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private MagneticKey magneticKey;
    private FlashDrive chiavettaUSB;
    private MapManager mapManager;
    public float movementControllerStateX=200,movementControllerStateY=426;
    String mapFile = "Mappe/sopra.tmx";
    private NpcKiller NpcKiller;
    private NpcInnocent NpcInnocent;
    private NpcChiefOfPolicie NpcChiefOfPolicie;
    private NpcDeadBody NpcDeadBody;

    private TaskView task;
    private TaskModel taskModel;

    // Costruttore
    public MainGameScreen(Gioco game)
    {
        this.game = game;
        this.show();
        this.screenManager = ScreenManager.getInstance();
    }


    @Override
    public void show() {
        this.batch = game.getBatch();
        this.stateDirection=0;
        this.player = Player.getInstance("player/player-spritesheet.png");  // Inizializza l'entità con la sprite sheet
        if (movementController == null) {
            this.movementController = new MovementController(400, 105); // Posizione di spawn
        } else {
            this.movementController = new MovementController(movementControllerStateX, movementControllerStateY);
        }
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 960, 540);
        this.mapManager = new MapManager(mapFile,camera);
        this.magneticKey = new MagneticKey(420, 100, movementController, player,this);
        this.chiavettaUSB = new FlashDrive(500, 500, movementController, player,this);

        this.NpcKiller = new NpcKiller(610,90, movementController);
        this.NpcInnocent = new NpcInnocent(610, 130, movementController);
        this.NpcChiefOfPolicie = new NpcChiefOfPolicie(710,100,movementController);
        this.NpcDeadBody = new NpcDeadBody(150,200,movementController);

        task = new TaskView();
        taskModel = TaskModel.getInstance();
    }

    //metodo utilizzato per il menù di pausa, viene chiamato durante il metodo render per matentenere visibile lo stato attuale del gioco
    public void renderStaticState(SpriteBatch batch) {
        // Pulisci il batch solo all'inizio
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        // 1. Rendi lo stato della mappa
        mapManager.render();

        // 2. Disegna il personaggio
        TextureRegion currentFrame = player.getCurrentFrame(
            movementController.getStateDirection(),
            movementController.isPlayerMoving(),
            0
        );
        batch.draw(currentFrame, movementController.getX(), movementController.getY());

        // 3. Disegna gli oggetti
        drawObjects();

        // 4. Disegna gli NPC
        drawNpc();

        // Aggiungi altri oggetti o elementi che desideri renderizzare

        batch.end();
    }
    private void drawElevatorMenu() {
        for (Rectangle elevator : mapManager.getElevatorRectangles()) {
            if ((Gdx.input.isKeyJustPressed(Input.Keys.E)) && movementController.isColliding2(
                movementController.getX(), movementController.getY(), List.of(elevator))) {
                String targetMap = mapManager.getElevatorTargetMaps().get(elevator);
                String targetMap2 = mapManager.getElevatorTargetMap2().get(elevator);
                game.setScreen(new ElevatorMenu(game, this, targetMap,targetMap2));
                break;
            }
        }
    }
    public void savePlayerState() {
        movementControllerStateX = movementController.getX();
        movementControllerStateY = movementController.getY();
    }
    @Override
    public void render(float delta) {

        //modifica
        // Controlla se il tasto ESCAPE è stato premuto
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            savePlayerState();
            screenManager.showScreen(ScreenManager.ScreenType.PAUSE); // Mostra il menu pausa
            return;
        }

        movementController.changeStateDirection(delta, mapManager.getWallRectangles());// Aggiorna la posizione in base all'input

        // Ottieni la direzione corrente
        int currentDirection = movementController.getStateDirection();

        // Verifica se il personaggio è in movimento
        boolean isMoving = movementController.isPlayerMoving();

        // Ottieni il fotogramma attuale in base alla direzione e allo stato di movimento
        TextureRegion currentFrame = player.getCurrentFrame(currentDirection, isMoving, delta);

        // Pulisci lo schermo
        Gdx.gl.glClearColor(0, 0, 0, 1);
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
        drawNpc();
        // batch.draw(Utility.getAsset("menu/bg-menu.png", Texture.class), 0, 0, 1920, 1080);
        batch.draw(currentFrame, movementController.getX(), movementController.getY());
        batch.end();

        magneticKey.pickUp();
        chiavettaUSB.pickUp();

        //Dialoghi
        NpcKiller.drawDialogue();
        NpcInnocent.drawDialogue();
        NpcChiefOfPolicie.drawDialogue();
        NpcDeadBody.drawDialogue();

        magneticKey.drawDialogue();
        chiavettaUSB.drawDialogue();

        task.draw();

        if(NpcDeadBody.getDialogIndex() == 4)
            taskModel.setNextTask();

    }


    public void drawNpc(){
        batch.draw(NpcKiller.getTexture(), NpcKiller.getSpawn_x(), NpcKiller.getSpawn_y(), 32,32);
        batch.draw(NpcInnocent.getTexture(), NpcInnocent.getSpawn_x(), NpcInnocent.getSpawn_y(), 32, 32);
        batch.draw(NpcChiefOfPolicie.getTexture(), NpcChiefOfPolicie.getSpawn_x(), NpcChiefOfPolicie.getSpawn_y(), 32,32);
        batch.draw(NpcDeadBody.getTexture(),NpcDeadBody.getSpawn_x(), NpcDeadBody.getSpawn_y(),32,32);
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
        if(Objects.equals(mapFile, "Mappe/sopra.tmx")){
            movementControllerStateX = 200;
            movementControllerStateY = 426;
        } else if (Objects.equals(mapFile,"Mappe/ingresso.tmx")) {
            movementControllerStateX = 835;
            movementControllerStateY = 520;
        } else if (Objects.equals(mapFile,"Mappe/garage.tmx")) {
            movementControllerStateX = 379;
            movementControllerStateY = 270;
        }
        movementController = new MovementController(movementControllerStateX,movementControllerStateY); // Reimposta il giocatore
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
