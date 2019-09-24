package cachingSystem.classes;

import java.sql.Timestamp;
import cachingSystem.interfaces.CacheStalePolicy;
import dataStructures.classes.Node;
import dataStructures.classes.Pair;

/**
 * The TimeAwareCache offers the same functionality as the LRUCache, but also stores a timestamp for
 * each element. The timestamp is updated after each get / put operation for a key. This
 * functionality allows for time based cache stale policies (e.g. removing entries that are older
 * than 1 second).
 */
public class TimeAwareCache<K, V> extends LRUCache<K, V> {
    private long expirePolicyTime;

    /**
     * Get the timestamp associated with a key, or null if the key is not stored in the cache.
     *
     * @param key the key
     * @return the timestamp, or null
     */
    public Timestamp getTimestampOfKey(K key) {
        Node<K, V> node = cacheMap.get(key);

        if (node != null) {
            return node.getTimestamp();
        }

        return null;
    }

    /**
     * Set a cache stale policy that should remove all elements older than @millisToExpire
     * milliseconds. This is a convenience method for setting a time based policy for the cache.
     *
     * @param millisToExpire the expiration time, in milliseconds
     */
    public void setExpirePolicy(long millisToExpire) {
        expirePolicyTime = millisToExpire;

        cacheStalePolicy = new CacheStalePolicy<K, V>() {
            @Override
            public boolean shouldRemoveEldestEntry(Pair<K, V> entry) {
                if (isEmpty()) {
                    return false;
                }

                Timestamp currentTime = new Timestamp(System.currentTimeMillis());
                Timestamp keyTime = getTimestampOfKey(entry.getKey());

                return currentTime.getTime() - keyTime.getTime() > expirePolicyTime;
            }
        };
    }

    @Override
    public V get(K key) {
        this.clearStaleEntries();
        Node<K, V> returnedNode = cacheMap.get(key);

        if (returnedNode != null) {
            cache.updateNode(returnedNode);
            cacheListener.onHit(key);
            return returnedNode.getValue().getValue();
        } else {
            cacheListener.onMiss(key);
            return null;
        }
    }
}
