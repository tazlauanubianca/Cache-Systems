package observerPattern.classes;

import observerPattern.interfaces.CacheListener;
import java.util.ArrayList;

/**
 * The BroadcastListener broadcasts cache events to other listeners that have been added to it.
 */
public class BroadcastListener<K, V> implements CacheListener<K, V> {

    /**
     * Add a listener to the broadcast list.
     *
     * @param listener the listener
     */
    private ArrayList<CacheListener<K, V>> listeners;

    public BroadcastListener() {
        listeners = new ArrayList<CacheListener<K, V>>();
    }

    public void addListener(CacheListener<K, V> listener) {
        listeners.add(listener);
    }

    /**
     * Calls onHit for each listener.
     * @param key
     */
    @Override
    public void onHit(K key) {
        for (CacheListener cacheListener: listeners) {
            cacheListener.onHit(key);
        }
    }

    /**
     * Calls onMiss for each listener.
     * @param key
     */
    @Override
    public void onMiss(K key) {
        for (CacheListener cacheListener: listeners) {
            cacheListener.onMiss(key);
        }
    }

    /**
     * Calls onPut for each listener.
     * @param key
     * @param value
     */
    @Override
    public void onPut(K key, V value) {
        for (CacheListener cacheListener: listeners) {
            cacheListener.onPut(key, value);
        }
    }
}
