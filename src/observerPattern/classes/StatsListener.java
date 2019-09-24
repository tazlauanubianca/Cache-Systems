package observerPattern.classes;

import observerPattern.interfaces.CacheListener;

/**
 * The StatsListener collects hit / miss / update stats for a cache.
 *
 * @param <K>
 * @param <V>
 */
public class StatsListener<K, V> implements CacheListener<K, V> {
    private int numberOfUpdates;
    private int numberOfHits;
    private int numberOfMisses;

    public StatsListener() {
        numberOfUpdates = 0;
        numberOfMisses = 0;
        numberOfHits = 0;
    }

    /**
     * Get the number of hits for the cache.
     *
     * @return number of hits
     */
    public int getHits() {
        return numberOfHits;
    }

    /**
     * Get the number of misses for the cache.
     *
     * @return number of misses
     */
    public int getMisses() {
        return numberOfMisses;
    }

    /**
     * Get the number of updates (put operations) for the cache.
     *
     * @return number of updates
     */
    public int getUpdates() {
        return numberOfUpdates;
    }

    /**
     * Counts the number of total hits.
     * @param key
     */
    @Override
    public void onHit(K key) {
        numberOfHits++;
    }

    /**
     * Counts the number of total misses.
     * @param key
     */
    @Override
    public void onMiss(K key) {
        numberOfMisses++;
    }

    /**
     * Counts the number of total updates.
     * @param key
     * @param value
     */
    @Override
    public void onPut(K key, V value) {
        numberOfUpdates++;
    }
}
