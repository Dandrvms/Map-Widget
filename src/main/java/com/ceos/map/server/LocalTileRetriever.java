package com.ceos.map.server;

import com.gluonhq.maps.tile.TileRetriever;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.scene.image.Image;

public class LocalTileRetriever implements TileRetriever {

    private static final String host = "http://172.28.41.114/hot/";
    private static final File cacheRoot = new File(System.getProperty("java.io.tmpdir"), ".gluonmaps");

    static {
        cacheRoot.mkdirs();
    }

    private static final ExecutorService executor = Executors.newFixedThreadPool(4, r -> {
        Thread t = new Thread(r);
        t.setDaemon(true);
        return t;
    });

    static String buildImageUrlString(int zoom, long i, long j) {
        return host + zoom + "/" + i + "/" + j + ".png";
    }

    @Override
    public CompletableFuture<Image> loadTile(int zoom, long i, long j) {
        File cached = new File(cacheRoot, zoom + "/" + i + "/" + j + ".png");
        if (cached.exists()) {
            return CompletableFuture.completedFuture(new Image(cached.toURI().toString(), true));
        }
        return CompletableFuture.supplyAsync(() -> {
            return downloadTile(zoom, i, j, cached);
        }, executor);
    }

    private Image downloadTile(int zoom, long i, long j, File cached) {
        try {
            URL url = new URL(buildImageUrlString(zoom, i, j));
            cached.getParentFile().mkdirs();
            try (InputStream in = url.openStream();
                 FileOutputStream out = new FileOutputStream(cached)) {
                byte[] buf = new byte[4096];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            }
            return new Image(cached.toURI().toString(), true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
