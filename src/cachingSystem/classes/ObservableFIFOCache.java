package cachingSystem.classes;

import dataStructures.classes.Pair;

/**
 * Class that adapts the FIFOCache class to the ObservableCache abstract class.
 */
public class ObservableFIFOCache<K, V> extends ObservableCache<K, V> {
    private FIFOCache<K, V> fifoCache;

    public ObservableFIFOCache() {
        fifoCache = new FIFOCache();
    }

    @Override
    public V get(K key) {
        V value = fifoCache.get(key);

        if (value == null) {
            cacheListener.onMiss(key);
            return null;
        } else {
            cacheListener.onHit(key);
            return fifoCache.get(key);
        }
    }

    @Override
    public void put(K key, V value) {
        fifoCache.put(key, value);

        cacheListener.onPut(key, value);
        this.clearStaleEntries();
    }

    @Override
    public int size() {
        return fifoCache.size();
    }

    @Override
    public boolean isEmpty() {
        return fifoCache.isEmpty();
    }

    @Override
    public V remove(K key) {
        return fifoCache.remove(key);
    }

    @Override
    public void clearAll() {
        fifoCache.clearAll();
    }

    @Override
    public Pair<K, V> getEldestEntry() {
        return fifoCache.getEldestEntry();
    }
}
