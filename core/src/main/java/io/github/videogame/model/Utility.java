package io.github.videogame.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

public final class Utility {
    public static final AssetManager assetManager = new AssetManager();

    private static InternalFileHandleResolver filePathResolver = new InternalFileHandleResolver();

    //questo metodo serve per rimuovere gli assets dalla memoria quando non sono più necessari.
    // Viene usato per gestire meglio la memoria
    public static void unloadAssets(String assetsPath){
        //se l'asset è presente nell'assetmanager e non serve più
        if(assetManager.isLoaded(assetsPath))
            //allora lo rimuovo
            assetManager.unload(assetsPath);
    }

    public static void loadAssetsFromJSON(String jsonPath, String screen){
        if(jsonPath == null || jsonPath.isEmpty()) return;

        if(!filePathResolver.resolve(jsonPath).exists()) return;

        JsonReader jsonReader = new JsonReader();
        JsonValue rootType = jsonReader.parse(Gdx.files.internal(jsonPath)).get(screen);

        for(JsonValue category : rootType) {
            //System.out.println("a");
            for (JsonValue asset : category){
                String path = asset.asString();
                switch (category.name()){
                    case "textures":
                        loadAsset(path, Texture.class);
                        break;
                    case "musics":
                        loadAsset(path, Music.class);
                        break;
                    case "sounds":
                        loadAsset(path, Sound.class);
                        break;
                }
            }
        }
    }

    //Restituisce il progresso del caricamento delle risorse gestite dall'assetmanager.
    //Può essere usato per creare una schermata di caricamento.
    public static float loadCompleted(){
        return assetManager.getProgress();
    }

    //Restituisce il numero di assets presenti nella coda di caricamento del gestore delle risorse.
    public static int numberAssetsQueued(){
        return assetManager.getQueuedAssets();
    }

    //Aggiorna lo stato di caricamento delle risorse nel gestore. Restituisce true se tutte le risorse sono state caricate.
    //Utile per implementare un caricamento non bloccante, in maniera da tenere il thread principale libero.
    public static boolean updateAssetLoading(){
        return assetManager.update();
    }

    //Verifica se un asset specifico è stato caricato nel gestore.
    public static boolean isAssetLoaded(String assetPath){
        return assetManager.isLoaded(assetPath);
    }

    //Carica una mappa in formato .tmx usando il gestore delle risorse.
    public static void loadMapAsset(String mapPath){
        //controllo che il path non sia vuoto.
        if(mapPath == null || mapPath.isEmpty())
            return;

        //se esiste il file con quel path
        if(filePathResolver.resolve(mapPath).exists()){
            //allora inizialmente istruisco il gestore a caricare gli oggetti TiledMap attraverso un TmxMapLoader.
            assetManager.setLoader(TiledMap.class, new TmxMapLoader(filePathResolver));

            //dopo di ché carico nel gestore la mappa, specificando che si tratta di una TiledMap.
            assetManager.load(mapPath, TiledMap.class);

            //qui invece aspetto che il caricamento della mappa finisca.
            // Questa è un'operazione bloccante, quindi il thread principale viene bloccato
            assetManager.finishLoadingAsset(mapPath);
        }
    }


    public static <T> void loadAsset(String path, Class<T> type){
        if(path == null || path.isEmpty()) return;

        if(filePathResolver.resolve(path).exists() && !assetManager.isLoaded(path)){
            assetManager.load(path, type);
            assetManager.finishLoadingAsset(path);
        }
    }

    public static <T> T getAsset(String path, Class<T> type){
        if(path == null || path.isEmpty()) return null;

        if(!assetManager.isLoaded(path)){
            return null;
        }

        return assetManager.get(path, type);
    }
}
