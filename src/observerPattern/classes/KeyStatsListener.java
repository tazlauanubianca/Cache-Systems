package observerPattern.classes;

import java.util.Map;
import java.util.HashMap;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.List;
import java.util.ArrayList;
import dataStructures.classes.Pair;
import observerPattern.interfaces.CacheListener;

/**
 * The KeyStatsListener collects key-level stats for cache operations.
 *
 * @param <K>
 * @param <V>
 */
public class KeyStatsListener<K, V> implements CacheListener<K, V> {
    private Map<K, Integer> keyMissesMap;
    private Map<K, Integer> keyHitsMap;
    private Map<K, Integer> keyUpdatesMap;
    private static Comparator<Pair> comparator;

    public KeyStatsListener() {
        keyMissesMap = new HashMap<K, Integer>();
        keyHitsMap = new HashMap<K, Integer>();
        keyUpdatesMap = new HashMap<K, Integer>();
        comparator = new Comparator<Pair>() {
            @Override
            public int compare(Pair firstPair, Pair secondPair) {
                return (Integer) secondPair.getValue() - (Integer) firstPair.getValue();
            }
        };
    }

    /**
     * Get the number of hits for a key.
     *
     * @param key the key
     * @return number of hits
     */
    public int getKeyHits(K key) {
        return keyHitsMap.get(key);
    }

    /**
     * Get the number of misses for a key.
     *
     * @param key the key
     * @return number of misses
     */
    public int getKeyMisses(K key) {
        return keyMissesMap.get(key);
    }

    /**
     * Get the number of updates for a key.
     *
     * @param key the key
     * @return number of updates
     */
    public int getKeyUpdates(K key) {
        return keyUpdatesMap.get(key);
    }

    /**
     * Get the @top most hit keys.
     *
     * @param top number of top keys
     * @return the list of keys
     */
    public List<K> getTopHitKeys(int top) {
        PriorityQueue<Pair<K, Integer>> topKeyHits =
                new PriorityQueue<Pair<K, Integer>>(comparator);

        for (K currentKey: keyHitsMap.keySet()) {
            topKeyHits.add(new Pair(currentKey, keyHitsMap.get(currentKey)));
        }

        Pair<K, Integer> currentKey = topKeyHits.poll();
        List<K> topHitsKeys = new ArrayList<K>();

        while (top != 0) {
            topHitsKeys.add(currentKey.getKey());
            currentKey = topKeyHits.poll();
            top--;
        }

        return topHitsKeys;
    }

    /**
     * Get the @top most missed keys.
     *
     * @param top number of top keys
     * @return the list of keys
     */
    public List<K> getTopMissedKeys(int top) {
        PriorityQueue<Pair<K, Integer>> topKeyMisses =
                new PriorityQueue<Pair<K, Integer>>(comparator);

        for (K currentKey: keyMissesMap.keySet()) {
            topKeyMisses.add(new Pair(currentKey, keyMissesMap.get(currentKey)));
        }

        Pair<K, Integer> currentKey = topKeyMisses.poll();
        List<K> topMissesKeys = new ArrayList<K>();

        while (top != 0) {
            topMissesKeys.add(currentKey.getKey());
            currentKey = topKeyMisses.poll();
            top--;
        }

        return topMissesKeys;
    }

    /**
     * Get the @top most updated keys.
     *
     * @param top number of top keys
     * @return the list of keys
     */
    public List<K> getTopUpdatedKeys(int top) {
        PriorityQueue<Pair<K, Integer>> topKeyUpdates =
                new PriorityQueue<Pair<K, Integer>>(comparator);

        for (K currentKey: keyUpdatesMap.keySet()) {
            topKeyUpdates.add(new Pair(currentKey, keyUpdatesMap.get(currentKey)));
        }

        Pair<K, Integer> currentKey = topKeyUpdates.poll();
        List<K> topUpdatesKeys = new ArrayList<K>();

        while (top != 0) {
            topUpdatesKeys.add(currentKey.getKey());
            currentKey = topKeyUpdates.poll();
            top--;
        }

        return topUpdatesKeys;
    }

    /**
     * Counts the number of hits for a particular key.
     * @param key
     */
    @Override
    public void onHit(K key) {
        if (keyHitsMap.get(key) != null) {
            keyHitsMap.put(key, keyHitsMap.get(key) + 1);
        } else {
            keyHitsMap.put(key, 1);
        }
    }

    /**
     * Counts the number of misses for a particular key.
     * @param key
     */
    @Override
    public void onMiss(K key) {
        if (keyMissesMap.get(key) != null) {
            keyMissesMap.put(key, keyMissesMap.get(key) + 1);
        } else {
            keyMissesMap.put(key, 1);
        }
    }

    /**
     * Counts the number of updates for a particular key.
     * @param key
     * @param value
     */
    @Override
    public void onPut(K key, V value) {
        if (keyUpdatesMap.get(key) != null) {
            keyUpdatesMap.put(key, keyUpdatesMap.get(key) + 1);
        } else {
            keyUpdatesMap.put(key, 1);
        }
    }
}
