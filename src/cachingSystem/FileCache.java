package cachingSystem;

import cachingSystem.classes.ObservableCache;
import cachingSystem.classes.ObservableFIFOCache;
import cachingSystem.classes.LRUCache;
import cachingSystem.classes.TimeAwareCache;
import cachingSystem.interfaces.CacheStalePolicy;
import dataStructures.classes.Pair;
import observerPattern.classes.BroadcastListener;
import observerPattern.interfaces.CacheListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * FileCache generates the type of cache that is used. This class is also used to
 * get and put entries in cache.
 */
public final class FileCache {
    private ObservableCache<String, String> dataCache;
    private BroadcastListener<String, String> broadcastListener;

    public enum Strategy {
        FIFO,
        LRU,
    }

    public static cachingSystem.FileCache createCacheWithCapacity(
            cachingSystem.FileCache.Strategy strategy, int capacity) {
        ObservableCache<String, String> dataCache;

        switch (strategy) {

            case FIFO:
                dataCache = new ObservableFIFOCache<>();
                break;
            case LRU:
                dataCache = new LRUCache<>();
                break;
            default:
                throw new IllegalArgumentException("Unsupported cache strategy: " + strategy);
        }

        dataCache.setStalePolicy(new CacheStalePolicy<String, String>() {
            @Override
            public boolean shouldRemoveEldestEntry(Pair<String, String> entry) {
                return dataCache.size() > capacity;
            }
        });

        return new cachingSystem.FileCache(dataCache);
    }

    public static cachingSystem.FileCache createCacheWithExpiration(long millisToExpire) {
        TimeAwareCache<String, String> dataCache = new TimeAwareCache<>();

        dataCache.setExpirePolicy(millisToExpire);

        return new cachingSystem.FileCache(dataCache);
    }

    private FileCache(ObservableCache<String, String> dataCache) {
        this.dataCache = dataCache;
        this.broadcastListener = new BroadcastListener<>();

        this.dataCache.setCacheListener(broadcastListener);

        broadcastListener.addListener(createCacheListener());
    }

    private CacheListener<String, String> createCacheListener() {
        return new CacheListener<String, String>() {
            @Override
            public void onHit(String key) {

            }

            @Override
            public void onMiss(String key) {
                String text = "";
                try {
                    text = Files.readAllLines(Paths.get(key)).toString();
                    text = text.substring(1, text.length() - 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                putFileContents(key, text);
            }

            @Override
            public void onPut(String key, String value) {

            }
        };
    }

    public String getFileContents(String path) {
        String fileContents;

        do {
            fileContents = dataCache.get(path);
        } while (fileContents == null);

        return fileContents;
    }

    public void putFileContents(String path, String contents) {
        dataCache.put(path, contents);
    }

    public void addListener(CacheListener<String, String> listener) {
        broadcastListener.addListener(listener);
    }
}
