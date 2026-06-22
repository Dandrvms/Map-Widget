package com.ceos.map.server;

import com.gluonhq.maps.tile.TileRetriever;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.scene.image.Image;

/**
 *
 * @author Daniel
 * 
 * Setup the tile server 
 * Point host at the server url
 * 
 */
public class LocalTileRetriever implements TileRetriever {

    private static final String host = "http://172.28.41.114/hot/";
    private static final ExecutorService executor = Executors.newFixedThreadPool(8, r -> {
        Thread t = new Thread(r);
        t.setDaemon(true);
        t.setName("LocalTileRetriever-Pool");
        return t;
    });

    static String buildImageUrlString(int zoom, long i, long j) {
        return host + zoom + "/" + i + "/" + j + ".png";
    }

    @Override
    public CompletableFuture<Image> loadTile(int zoom, long i, long j) {
        String urlString = buildImageUrlString(zoom, i, j);
        return CompletableFuture.supplyAsync(() -> new Image(urlString, false), executor);
    }

}
