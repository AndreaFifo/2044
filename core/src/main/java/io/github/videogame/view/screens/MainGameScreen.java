package io.github.videogame.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import io.github.videogame.controller.CollisionDebugger;
import io.github.videogame.controller.MapManager;
import io.github.videogame.controller.ScreenManager;
import io.github.videogame.model.*;
import io.github.videogame.controller.MovementController;
import io.github.videogame.view.ElevatorDecisionBox;
import io.github.videogame.view.PlayerView;
import io.github.videogame.view.TaskView;

import java.util.ArrayList;
import java.util.Objects;


//Lo scopo di questa classe è gestire la finestra della sessione di gioco

public class MainGameScreen implements Screen {
    private Gioco game;
    private Player player;
    private PlayerView playerView;
    private int stateDirection;
    private MovementController movementController;
    private ScreenManager screenManager;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private MagneticKey magneticKey;
    private FlashDriveInnocent flashDriveInnocent;
    private FlashDriveKiller flashDriveKiller;
    private JosephPhone josephPhone;
    private MapManager mapManager;
    private GameState gameState;
    String mapFile = "Mappa-prova/uffici.tmx";

    //NPC
    private NpcKiller NpcKiller;
    private NpcInnocent NpcInnocent;
    private NpcChiefOfPolicie NpcChiefOfPolicie;
    private NpcDeadBody NpcDeadBody;
    private io.github.videogame.model.NpcAurora NpcAurora;
    private CollisionDebugger collisionDebugger;
    private ElevatorDecisionBox elevatorDecisionBox;

    //Salvataggio degli indici di dialogo per gli NPC
    private int indicePolice;
    private int indiceKiller;
    private int indiceDead;
    private int indiceInnocent;
    private int indiceAurora;

    //
    static boolean f_map=false;
    static String m;
    //Task
    private TaskView taskView;

    // Costruttore
    public MainGameScreen(Gioco game) {
        this.game = game;
        this.gameState = GameState.getInstance();

        this.mapManager = MapManager.getInstance();
        this.camera = mapManager.getCamera();

        this.player = Player.getInstance();
        this.movementController = MovementController.getInstance();

        this.playerView = new PlayerView(player, movementController);
        this.screenManager = ScreenManager.getInstance();

        this.collisionDebugger = new CollisionDebugger();
        this.elevatorDecisionBox = new ElevatorDecisionBox();
    }

    @Override
    public void show() {
        player.setSpawn(gameState.getPlayerX(), gameState.getPlayerY());

        Gdx.input.setInputProcessor(elevatorDecisionBox.getStage());
        this.batch = game.getBatch();
        this.stateDirection=0;

        //this.mapManager = new MapManager(mapFile,camera);
        this.magneticKey = new MagneticKey(350, 526);
        this.flashDriveInnocent = new FlashDriveInnocent(420, 305);
        this.flashDriveKiller = new FlashDriveKiller(1430,715);
        this.josephPhone = new JosephPhone(443,516);

        this.NpcKiller = new NpcKiller(610,90, movementController);
        this.NpcInnocent = new NpcInnocent(610, 130, movementController);
        this.NpcChiefOfPolicie = new NpcChiefOfPolicie(710,100,movementController);
        this.NpcDeadBody = new NpcDeadBody(150,200,movementController);
        this.NpcAurora = new NpcAurora(168,158,movementController);

        //Setto gli indici correttamente (li salva anche se cambia schermata)
        this.NpcChiefOfPolicie.setDialogIndex(indicePolice);
        this.NpcKiller.setDialogIndex(indiceKiller);
        this.NpcInnocent.setDialogIndex(indiceInnocent);
        this.NpcDeadBody.setDialogIndex(indiceDead);
        this.NpcAurora.setDialogIndex(indiceAurora);

        //TASK
        this.taskView = new TaskView();

        //Aggiunta osservatore
        NpcChiefOfPolicie.addObserver(taskView);
        NpcKiller.addObserver(taskView);
        NpcInnocent.addObserver(taskView);
        NpcDeadBody.addObserver(taskView);
        NpcAurora.addObserver(taskView);
    }

    //metodo utilizzato per il menù di pausa, viene chiamato durante il metodo render per matentenere visibile lo stato attuale del gioco
    public void renderStaticState(SpriteBatch batch) {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        mapManager.render();

        playerView.render(batch, 0);

        drawObjects();

        drawNpc();

        batch.end();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.12f, 0.37f, 0.50f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Controlla se il tasto ESCAPE è stato premuto
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            screenManager.showScreen(ScreenManager.ScreenType.PAUSE); // Mostra il menu pausa
            return;
        }

        movementController.changeStateDirection(delta);

        camera.position.set(player.getX(), player.getY(), 0f);
        camera.update();

        mapManager.render();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        player.getInventory().drawInventory(batch);

        drawObjects();
        drawNpc();
        playerView.render(batch, delta);

        batch.end();

        if(NpcDeadBody.getDialogIndex() == 4){
            NpcDeadBody.notifyObservers();
            NpcDeadBody.setDialogIndex(5);
        }

        if(mapManager.isNearElevators(player.getX(), player.getY()))
            elevatorDecisionBox.show();
        else
            elevatorDecisionBox.hide();

        elevatorDecisionBox.handleInput();

        elevatorDecisionBox.render();



        //I due metodo gestiranno il dialogo del NPC
        drawNpcDialogue();
        setIndexNpc();

        //Disegno le task
        taskView.draw();

        collisionDebugger.render(
            mapManager.getRectangleCollisions(),
            player.getX()+ 3.5f,
            player.getY() + 4.f,
            7,9f
        );
    }

    public void setIndexNpc(){
        //Salvataggio degli indici dei dialoghi degli NPC
        if(indicePolice <= NpcChiefOfPolicie.getDialogIndex()){
            indicePolice = NpcChiefOfPolicie.getDialogIndex();
        }
        if(indiceDead <= NpcDeadBody.getDialogIndex()){
            indiceDead = NpcDeadBody.getDialogIndex();
        }
        if(indiceInnocent <= NpcInnocent.getDialogIndex()){
            indiceInnocent = NpcInnocent.getDialogIndex();
        }
        if(indiceKiller <= NpcKiller.getDialogIndex()){
            indiceKiller = NpcKiller.getDialogIndex();
        }
        if(indiceAurora <= NpcAurora.getDialogIndex()){
            indiceAurora = NpcAurora.getDialogIndex();
        }
    }


    public void drawNpcDialogue(){
        //Disegna dialoghi degli NPC piano terra
        if(Objects.equals(mapFile, "Mappe/ingresso.tmx")) {
            NpcKiller.drawDialogue();
            NpcInnocent.drawDialogue();
            NpcChiefOfPolicie.drawDialogue();
        }
        //Disegna dialoghi degli NPC primo piano
        if(Objects.equals(mapFile, "Mappe/sopra.tmx")) {
            NpcDeadBody.drawDialogue();
            NpcAurora.drawDialogue();
        }
    }


    public void drawNpc(){
        if(Objects.equals(mapFile, "Mappe/ingresso.tmx")) {
            batch.draw(NpcKiller.getTexture(), NpcKiller.getSpawn_x(), NpcKiller.getSpawn_y(), 32, 32);
            batch.draw(NpcInnocent.getTexture(), NpcInnocent.getSpawn_x(), NpcInnocent.getSpawn_y(), 32, 32);
            batch.draw(NpcChiefOfPolicie.getTexture(), NpcChiefOfPolicie.getSpawn_x(), NpcChiefOfPolicie.getSpawn_y(), 32, 32);
        }
        if(Objects.equals(mapFile,"Mappe/sopra.tmx")){batch.draw(NpcDeadBody.getTexture(), NpcDeadBody.getSpawn_x(), NpcDeadBody.getSpawn_y(), 32, 32);}
    }

    //Disegna gli oggetti, SOLO SE IL LORO STATO TAKEN è FALSO
    private void drawObjects() {
        if(mapManager.getCurrentMap().equals("Mappa-prova/atrio-mensa.tmx")){
            if (!magneticKey.isTaken()) {
                if(!player.getInventory().getItemInventory().contains("MagneticKey"))
                    batch.draw(magneticKey.getTexture(), magneticKey.getX(), magneticKey.getY(), 16, 16);

                magneticKey.pickUp();
            }

            if (!flashDriveKiller.isTaken()) {
                if(!player.getInventory().getItemInventory().contains("MagneticKey"))
                    batch.draw(flashDriveKiller.getTexture(), flashDriveKiller.getX(), flashDriveKiller.getY(), 6, 6);

                flashDriveKiller.pickUp();
            }

            if (!flashDriveInnocent.isTaken()) {
                if (Objects.equals(mapFile, "Mappe/sopra.tmx")) {
                    if(!player.getInventory().getItemInventory().contains("MagneticKey")) {
                        batch.draw(flashDriveInnocent.getTexture(), flashDriveInnocent.getX(), flashDriveInnocent.getY(), 6, 6);
                    }
                }
                flashDriveInnocent.pickUp();
            }
        } else
            if (!josephPhone.isTaken()) {
                if(!player.getInventory().getItemInventory().contains("MagneticKey"))
                    batch.draw(josephPhone.getTexture(), josephPhone.getX(), josephPhone.getY(), 6, 6);

                josephPhone.pickUp();
            }
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        if (player != null) {
            player.dispose();
        }
        if (mapManager != null) {
            mapManager.dispose();
        }

        // Rilascia le risorse del giocatore
        if (player != null) {
            player.dispose(); // Assicurati che la classe Player gestisca il proprio dispose
        }

        // Rilascia le risorse della mappa
        if (mapManager != null) {
            mapManager.dispose(); // Rilascia le risorse della mappa
        }

        // Rilascia le risorse degli oggetti raccolti
        if (magneticKey != null) {
            magneticKey.dispose(); // Assicurati che la classe MagneticKey gestisca il proprio dispose
        }
        if (flashDriveInnocent != null) {
            flashDriveInnocent.dispose(); // Assicurati che la classe FlashDriveInnocent gestisca il proprio dispose
        }
        if (flashDriveKiller != null) {
            flashDriveKiller.dispose(); // Assicurati che la classe FlashDriveKiller gestisca il proprio dispose
        }
        if (josephPhone != null) {
            josephPhone.dispose(); // Assicurati che la classe JosephPhone gestisca il proprio dispose
        }
        // Rilascia il batch
        if (batch != null) {
            batch.dispose(); // Rilascia il batch se è stato creato
        }

    }
}
