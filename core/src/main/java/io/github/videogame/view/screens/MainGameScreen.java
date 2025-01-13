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

    //NPC
    private NpcKiller NpcKiller;
    private NpcInnocent NpcInnocent;
    private NpcChiefOfPolice NpcChiefOfPolicie;
    private NpcDeadBody NpcDeadBody;
    private NpcAurora NpcAurora;
    private NpcAnastasia NpcAnastasia;
    private NpcPoliceOfficer NpcPoliceOfficerOriginal;
    private NpcPoliceOfficer NpcPoliceOfficer1;
    private NpcPoliceOfficer NpcPoliceOfficer2;
    private NpcPoliceOfficer NpcPoliceOfficer3;
    private NpcPoliceOfficer NpcPoliceOfficer4;

    private CollisionDebugger collisionDebugger;
    private ElevatorDecisionBox elevatorDecisionBox;

    private TaskView taskView;

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
        System.out.println(mapManager.getCurrentMap());
        player.setSpawn(gameState.getPlayerX(), gameState.getPlayerY());

        Gdx.input.setInputProcessor(elevatorDecisionBox.getStage());
        this.batch = game.getBatch();

        //this.mapManager = new MapManager(mapFile,camera);
        this.magneticKey = new MagneticKey(95, 328); //OK
        this.flashDriveInnocent = new FlashDriveInnocent(325, 430); //OK
        this.flashDriveKiller = new FlashDriveKiller(565,430); //OK
        this.josephPhone = new JosephPhone(80,300); //OK

        this.NpcKiller = new NpcKiller(380,190); //OK
        this.NpcInnocent = new NpcInnocent(530, 170); //OK
        this.NpcChiefOfPolicie = new NpcChiefOfPolice(440,140); //OK
        this.NpcDeadBody = new NpcDeadBody(430,180); //OK
        this.NpcAurora = new NpcAurora(540,240); //OK
        this.NpcAnastasia = new NpcAnastasia(700,510); //OK
        this.NpcPoliceOfficerOriginal = new NpcPoliceOfficer(715,415); //OK

        this.NpcPoliceOfficer1 = NpcPoliceOfficerOriginal.clone(220,480); //OK
        this.NpcPoliceOfficer2 = NpcPoliceOfficerOriginal.clone(340,517); //OK
        this.NpcPoliceOfficer3 = NpcPoliceOfficerOriginal.clone(775,413); //OK
        this.NpcPoliceOfficer4 = NpcPoliceOfficerOriginal.clone(464,316); //OK

        this.taskView = new TaskView();

        //Aggiunta osservatore
        NpcChiefOfPolicie.addObserver(taskView);
        NpcKiller.addObserver(taskView);
        NpcInnocent.addObserver(taskView);
        NpcDeadBody.addObserver(taskView);
        NpcAurora.addObserver(taskView);
        //NpcAnastasia.addObserver(taskView);
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
        if(NpcAurora.getDialogIndexAct4() > 5){game.setScreen(new VideoOutroScreen(game));}
        if(NpcAnastasia.getDialogIndexAct1() > 22 & Gdx.input.isKeyJustPressed(Input.Keys.Y) & NpcAnastasia.canBeInteracted()){game.setScreen(new VideoOutro2Screen(game));}



        if(NpcAurora.getDialogIndexAct4() > 5){game.setScreen(new VideoOutroScreen(game));}
        if(NpcAnastasia.getDialogIndexAct1() > 22 & Gdx.input.isKeyJustPressed(Input.Keys.Y) & NpcAnastasia.canBeInteracted()){game.setScreen(new VideoOutro2Screen(game));}

        movementController.changeStateDirection(delta);

        camera.position.set(player.getX(), player.getY(), 0f);
        camera.update();

        mapManager.render();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        player.getInventory().drawInventory(batch, player.getX(), player.getY());

        drawObjects();
        drawNpc();
        playerView.render(batch, delta);

        batch.end();

        if(mapManager.isNearElevators(player.getX(), player.getY()))
            elevatorDecisionBox.show();
        else
            elevatorDecisionBox.hide();
        elevatorDecisionBox.handleInput();
        elevatorDecisionBox.render();


        drawNpcDialogue();
        drawItemDialogue();

        taskView.draw();

        collisionDebugger.render(
            mapManager.getRectangleCollisions(),
            player.getX()+ 3.5f,
            player.getY() + 4.f,
            7,9f
        );
    }



    public void drawNpcDialogue(){
        StoryState storyState1 = StoryState.getInstance();

        //NPC PIANO TERRA
        if(mapManager.getCurrentMap().equals("Mappa-prova/atrio-mensa.tmx") & storyState1.getDialogueState("NPC_DEADBODY_ACT1")  & player.getInventory().getItemInventory().contains("JosephPhone")) {
            NpcChiefOfPolicie.drawDialogueAct1();
            NpcChiefOfPolicie.drawDialogueAct2();
            NpcInnocent.drawDialogueAct1();
            NpcKiller.drawDialogueAct1();
            NpcKiller.drawDialogueAct2();
            NpcChiefOfPolicie.drawDialogueAct3();
            NpcPoliceOfficerOriginal.drawDialogueAct1();
            NpcPoliceOfficer1.drawDialogueAct1();
        }

        //NPC UFFICI
        if(mapManager.getCurrentMap().equals("Mappa-prova/uffici.tmx")){
            NpcDeadBody.drawDialogueAct1();
            NpcAurora.drawDialogueAct1();
            NpcAurora.drawDialogueAct2();
            NpcAurora.drawDialogueAct3();
            NpcChiefOfPolicie.drawDialogueAct3();
            NpcAurora.drawDialogueAct4();
            NpcAnastasia.drawDialogueAct1();

            StoryState storyState = StoryState.getInstance();
            if(storyState.getDialogueState("NPC_CHIEF_OF_POLICE_ACT1")){
                NpcPoliceOfficer2.drawDialogueAct1();
                NpcPoliceOfficer3.drawDialogueAct1();
                NpcPoliceOfficer4.drawDialogueAct1();
            }

        }
    }


    public void drawNpc(){
        StoryState storyState = StoryState.getInstance();
        //NPC NEL PIANO TERRA
        if(mapManager.getCurrentMap().equals("Mappa-prova/atrio-mensa.tmx") & storyState.getDialogueState("NPC_DEADBODY_ACT1")  & player.getInventory().getItemInventory().contains("JosephPhone")){
            batch.draw(NpcKiller.getTexture(), NpcKiller.getSpawn_x(), NpcKiller.getSpawn_y(), 16, 36);
            batch.draw(NpcInnocent.getTexture(), NpcInnocent.getSpawn_x(), NpcInnocent.getSpawn_y(), 16, 36);
            batch.draw(NpcChiefOfPolicie.getTexture(), NpcChiefOfPolicie.getSpawn_x(), NpcChiefOfPolicie.getSpawn_y(), 16, 36);
            batch.draw(NpcPoliceOfficerOriginal.getTexture(), NpcPoliceOfficerOriginal.getSpawn_x(), NpcPoliceOfficerOriginal.getSpawn_y(), 16,36);
            batch.draw(NpcPoliceOfficer1.getTexture(),NpcPoliceOfficer1.getSpawn_x(),NpcPoliceOfficer1.getSpawn_y(),16,36);
        }
        //NPC NEL PRIMO PIANO
        if(mapManager.getCurrentMap().equals("Mappa-prova/uffici.tmx")){
            batch.draw(NpcDeadBody.getTexture(), NpcDeadBody.getSpawn_x(), NpcDeadBody.getSpawn_y(), 32, 32);


            if(storyState.getDialogueState("NPC_CHIEF_OF_POLICE_ACT1")){
                batch.draw(NpcPoliceOfficer2.getTexture(),NpcPoliceOfficer2.getSpawn_x(),NpcPoliceOfficer2.getSpawn_y(),16,36);
                batch.draw(NpcPoliceOfficer3.getTexture(),NpcPoliceOfficer3.getSpawn_x(),NpcPoliceOfficer3.getSpawn_y(),16,36);
                batch.draw(NpcPoliceOfficer4.getTexture(),NpcPoliceOfficer4.getSpawn_x(),NpcPoliceOfficer4.getSpawn_y(),16,36);
            }
        }
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
