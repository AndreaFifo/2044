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
    public static float movementControllerStateX=350, movementControllerStateY=526;
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

        this.player = Player.getInstance();
        player.setSpawn(movementControllerStateX, movementControllerStateY);
        this.movementController = MovementController.getInstance();

        this.playerView = new PlayerView(player, movementController);

        this.screenManager = ScreenManager.getInstance();
        this.camera = new OrthographicCamera();
        collisionDebugger = new CollisionDebugger();
        this.elevatorDecisionBox = new ElevatorDecisionBox();
    }

    //creazione del memento
    public Gamestate createMemento() {
        if(!f_map)
            return new Gamestate(movementControllerStateX, movementControllerStateY, mapFile, player.getInventory().getIteminventary(), TaskModel.getInstance().getIdCurrentTask());
        else
            return new Gamestate(movementControllerStateX, movementControllerStateY, m, player.getInventory().getIteminventary(), TaskModel.getInstance().getIdCurrentTask());

    }

    // Metodo per ripristinare lo stato da un memento
    public void restoreState(Gamestate memento) {
        movementControllerStateX = memento.getPlayerX();
        movementControllerStateY = memento.getPlayerY();
        m = memento.getCurrentMap();
        ArrayList<String> inventario=player.getInventory().getIteminventary();
        inventario= new ArrayList<>(memento.getIteminventary());
        System.out.println(memento.getIdCurrentTask());
        TaskModel.getInstance().init(memento.getIdCurrentTask());

    }

    // Metodo che carica l'inventario dal salvataggio (array di nomi degli oggetti)
    public void loadInventory(ArrayList<String> inventario) {
        // Pulisci l'inventario corrente
        Inventory inventory = Inventory.getInventoryInstance();
        inventory.getItemList().clear();  // Pulisce l'inventario per evitare duplicati

        // Carica gli oggetti nell'inventario in base ai nomi
        for (String itemName : inventario) {
            if (itemName.equals("Magnetic Key")) {
                inventory.addItemToInventory(new MagneticKey(420, 100, movementController, player, this));
            } else if (itemName.equals("FlashDriveInnocente")) {
                inventory.addItemToInventory(new FlashDriveInnocent(500, 500, movementController, player, this));
            } else if (itemName.equals("JosephPhone")) {
                inventory.addItemToInventory(new JosephPhone(420, 100, movementController, player, this));
            }
            // Aggiungi altre condizioni per altri oggetti se necessario
        }
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(elevatorDecisionBox.getStage());
        this.batch = game.getBatch();
        this.stateDirection=0;

        if(f_map==false)//mappa di default, prima giocata
        {
            m=mapFile;
            this.mapManager = new MapManager();
            f_map=true;
        }
        else if(f_map==true)//mappa attuale(dove è possibile fare spostamenti tra varie mappe)
        {

            this.mapManager = new MapManager();
        }

        //this.mapManager = new MapManager(mapFile,camera);
        this.magneticKey = new MagneticKey(350, 526, movementController, player,this);
        this.flashDriveInnocent = new FlashDriveInnocent(420, 305, movementController, player,this);
        this.flashDriveKiller = new FlashDriveKiller(1430,715, movementController, player,this);
        this.josephPhone = new JosephPhone(443,516, movementController, player, this);

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
        // Pulisci il batch solo all'inizio
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        // 1. Rendi lo stato della mappa
        mapManager.render();

        // 2. Disegna il personaggio
//        TextureRegion currentFrame = player.getCurrentFrame(
//            movementController.getStateDirection(),
//            movementController.isPlayerMoving(),
//            0
//        );
//        batch.draw(currentFrame, player.getX(), player.getY());
        playerView.render(batch, 0);

        // 3. Disegna gli oggetti
        drawObjects();

        // 4. Disegna gli NPC
        drawNpc();


        batch.end();
    }
//    private void drawElevatorMenu() {
//        for (Rectangle elevator : mapManager.getElevatorRectangles()) {
//            if ((Gdx.input.isKeyJustPressed(Input.Keys.E)) && movementController.isColliding2(
//                player.getX(), player.getY(), List.of(elevator))) {
//                String targetMap = mapManager.getElevatorTargetMaps().get(elevator);
//                String targetMap2 = mapManager.getElevatorTargetMap2().get(elevator);
//                game.setScreen(new ElevatorMenu(game, this, targetMap,targetMap2));
//                break;
//            }
//        }
//    }
    public void savePlayerState() {
        movementControllerStateX = player.getX();
        movementControllerStateY = player.getY();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.15f, 0.47f, 0.62f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Controlla se il tasto ESCAPE è stato premuto
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            savePlayerState();
            screenManager.showScreen(ScreenManager.ScreenType.PAUSE); // Mostra il menu pausa
            return;
        }

        movementController.changeStateDirection(delta);

        mapManager.getCamera().position.set(player.getX(), player.getY(), 0f);
        mapManager.getCamera().update();

        batch.begin();

        mapManager.render();
        batch.setProjectionMatrix(mapManager.getCamera().combined);


        player.getInventory().drawInventory(batch);
        //Disegna gli oggetti, li inserisce nell'inventario ed altro
        drawObjects();
        //drawElevatorMenu();
        drawNpc();
        playerView.render(batch, delta);
        //batch.draw(currentFrame, player.getX(), player.getY());
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
        if (!magneticKey.isTaken()) {
            if(Objects.equals(mapFile, "Mappe/ingresso.tmx")) {
                String inventario = player.getInventory().getInventoryAsString();
                if(!inventario.contains("Magnetic Key")) {
                    batch.draw(magneticKey.getTexture(), magneticKey.getX(), magneticKey.getY(), 16, 16);
                }
            }
            magneticKey.pickUp();
        }

        if (!flashDriveKiller.isTaken()) {
            if (Objects.equals(mapFile, "Mappe/ingresso.tmx")) {
                String inventario = player.getInventory().getInventoryAsString();
                if (!inventario.contains("FlashDriveKiller")) {
                    batch.draw(flashDriveKiller.getTexture(), flashDriveKiller.getX(), flashDriveKiller.getY(), 6, 6);
                }
            }
            flashDriveKiller.pickUp();
        }

        if (!flashDriveInnocent.isTaken()) {
            if (Objects.equals(mapFile, "Mappe/sopra.tmx")) {
                String inventario = player.getInventory().getInventoryAsString();
                if (!inventario.contains("FlashDriveInnocente")) {
                    batch.draw(flashDriveInnocent.getTexture(), flashDriveInnocent.getX(), flashDriveInnocent.getY(), 6, 6);
                }
            }
            flashDriveInnocent.pickUp();
        }

        if (!josephPhone.isTaken()) {
            if (Objects.equals(mapFile, "Mappa-prova/uffici.tmx")) {
                String inventario = player.getInventory().getInventoryAsString();
                if (!inventario.contains("JosephPhone")) {
                    batch.draw(josephPhone.getTexture(), josephPhone.getX(), josephPhone.getY(), 6, 6);
                }
            }
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
        if(f_map==false)//controllo se è la prima giocata
        {
            mapFile = mapFilePath;
            m=mapFile;
        }
        else
            m=mapFilePath;

        //mapFile = mapFilePath;
        //System.out.println(mapFile);
        this.mapManager = new MapManager(); // Carica la nuova mappa
        if(Objects.equals(m, "Mappe/sopra.tmx")){
            movementControllerStateX = 200;
            movementControllerStateY = 426;
        } else if (Objects.equals(m,"Mappe/ingresso.tmx")) {
            movementControllerStateX = 835;
            movementControllerStateY = 520;
        } else if (Objects.equals(m,"Mappe/garage.tmx")) {
            movementControllerStateX = 379;
            movementControllerStateY = 270;
        }
        movementController = MovementController.getInstance(); // Reimposta il giocatore
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

    public OrthographicCamera getCamera() {
        return camera;
    }
}
