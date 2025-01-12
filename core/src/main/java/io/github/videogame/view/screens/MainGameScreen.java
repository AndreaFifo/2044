package io.github.videogame.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.videogame.controller.CollisionDebugger;
import io.github.videogame.controller.MapManager;
import io.github.videogame.controller.ScreenManager;
import io.github.videogame.model.*;
import io.github.videogame.controller.MovementController;
import io.github.videogame.view.ElevatorDecisionBox;
import io.github.videogame.view.PlayerView;
import io.github.videogame.view.TaskView;


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
    private NpcChiefOfPolice NpcChiefOfPolicie;
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
        this.magneticKey = new MagneticKey(210, 425); //OK
        this.flashDriveInnocent = new FlashDriveInnocent(440, 530); //OK
        this.flashDriveKiller = new FlashDriveKiller(675,525); //OK
        this.josephPhone = new JosephPhone(193,395); //OK

        this.NpcKiller = new NpcKiller(470,322, movementController); //OK
        this.NpcInnocent = new NpcInnocent(664, 315, movementController); //OK
        this.NpcChiefOfPolicie = new NpcChiefOfPolice(500,240,movementController); //OK
        this.NpcDeadBody = new NpcDeadBody(550,300,movementController); //OK
        this.NpcAurora = new NpcAurora(540,240,movementController); //OK

       /* //Setto gli indici correttamente (li salva anche se cambia schermata)
        this.NpcChiefOfPolicie.setDialogIndex(indicePolice);
        this.NpcKiller.setDialogIndex(indiceKiller);
        this.NpcInnocent.setDialogIndex(indiceInnocent);
        this.NpcDeadBody.setDialogIndex(indiceDead);
        this.NpcAurora.setDialogIndex(indiceAurora);*/

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
/*
        if(NpcDeadBody.getDialogIndex() == 4){
            NpcDeadBody.notifyObservers();
            NpcDeadBody.setDialogIndex(5);
        }*/

        if(mapManager.isNearElevators(player.getX(), player.getY()))
            elevatorDecisionBox.show();
        else
            elevatorDecisionBox.hide();

        elevatorDecisionBox.handleInput();

        elevatorDecisionBox.render();



        //I due metodo gestiranno il dialogo del NPC
        drawNpcDialogue();
        drawItemDialogue();
        //setIndexNpc();

        //Disegno le task
        taskView.draw();

        collisionDebugger.render(
            mapManager.getRectangleCollisions(),
            player.getX()+ 3.5f,
            player.getY() + 4.f,
            7,9f
        );


     //  System.out.println(player.getX() + "    " + player.getY());

    }

   /* public void setIndexNpc(){
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
    }*/


    public void drawNpcDialogue(){

        if(mapManager.getCurrentMap().equals("Mappa-prova/atrio-mensa.tmx")) {
            NpcChiefOfPolicie.drawDialogueAct1();
            NpcChiefOfPolicie.drawDialogueAct2();
            NpcInnocent.drawDialogueAct1();
            NpcKiller.drawDialogueAct1();
        }

        if(mapManager.getCurrentMap().equals("Mappa-prova/uffici.tmx")){
            NpcDeadBody.drawDialogueAct1();
            NpcAurora.drawDialogueAct1();

        }
    }


    public void drawNpc(){
        if(mapManager.getCurrentMap().equals("Mappa-prova/atrio-mensa.tmx")){
            batch.draw(NpcKiller.getTexture(), NpcKiller.getSpawn_x(), NpcKiller.getSpawn_y(), 16, 36);
            batch.draw(NpcInnocent.getTexture(), NpcInnocent.getSpawn_x(), NpcInnocent.getSpawn_y(), 16, 36);
            batch.draw(NpcChiefOfPolicie.getTexture(), NpcChiefOfPolicie.getSpawn_x(), NpcChiefOfPolicie.getSpawn_y(), 16, 36);
        }
        if(mapManager.getCurrentMap().equals("Mappa-prova/uffici.tmx")){batch.draw(NpcDeadBody.getTexture(), NpcDeadBody.getSpawn_x(), NpcDeadBody.getSpawn_y(), 32, 32);}
    }




    //Disegna gli oggetti, SOLO SE IL LORO STATO TAKEN è FALSO
    private void drawObjects() {

        //OGGETTI DEL PIANO TERRA
        if(mapManager.getCurrentMap().equals("Mappa-prova/atrio-mensa.tmx")){

                //OGGETTO CHIAVE MAGNETICA
                if(!player.getInventory().getItemInventory().contains("MagneticKey")) {
                    batch.draw(magneticKey.getTexture(), magneticKey.getX(), magneticKey.getY(), 16, 16);
                    magneticKey.pickUp();
                }

            }

        //OGGETTI DEL PRIMO PIANO
        if(mapManager.getCurrentMap().equals("Mappa-prova/uffici.tmx")){

            //OGGETTO CHIAVETTA DEL KILLER
            if(!player.getInventory().getItemInventory().contains("FlashDriveKiller")){
                batch.draw(flashDriveKiller.getTexture(), flashDriveKiller.getX(), flashDriveKiller.getY(), 6, 6);
                flashDriveKiller.pickUp();
            }

            //OGGETTO CHIAVETTA DELL'INNOCENTE
            if(!player.getInventory().getItemInventory().contains("FlashDriveInnocente")) {
                batch.draw(flashDriveInnocent.getTexture(), flashDriveInnocent.getX(), flashDriveInnocent.getY(), 6, 6);
                flashDriveInnocent.pickUp();
            }

            //OGGETTO TELEFONO DI JOSEPH
            if(!player.getInventory().getItemInventory().contains("JosephPhone")){
                batch.draw(josephPhone.getTexture(), josephPhone.getX(),josephPhone.getY(), 6,6);
                josephPhone.pickUp();
            }


        }


    }

    private void drawItemDialogue(){
        josephPhone.drawDialogue();
        flashDriveKiller.drawDialogue();
        flashDriveInnocent.drawDialogue();
        magneticKey.drawDialogue();
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
