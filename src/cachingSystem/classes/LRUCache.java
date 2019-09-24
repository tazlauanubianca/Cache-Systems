package cachingSystem.classes;

import dataStructures.classes.DoublyLinkedList;
import dataStructures.classes.Pair;
import dataStructures.classes.Node;
import java.util.HashMap;
import java.util.Map;

/**
 * This cache is very similar to the FIFOCache, but guarantees O(1) complexity for the get, put and
 * remove operations.
 */
public class LRUCache<K, V> extends ObservableCache<K, V> {
    public Map<K, Node<K, V>> cacheMap;
    public DoublyLinkedList<K, V> cache;

    public LRUCache() {
        cacheMap = new HashMap<K, Node<K, V>>();
        cache = new DoublyLinkedList<>();
    }

    @Override
    public V get(K key) {
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

    @Override
    public void put(K key, V value) {
        Node<K, V> newNode = cache.addNode(new Pair<>(key, value));

        if (cacheMap.get(key) != null) {
            cache.removeNode(cacheMap.get(key));
            cacheMap.remove(key);
        }

        cacheMap.put(key, newNode);
        cacheListener.onPut(key, value);

        this.clearStaleEntries();
    }

    @Override
    public int size() {
        return cache.getSize();
    }

    @Override
    public boolean isEmpty() {
        return cache.isEmpty();
    }

    @Override
    public V remove(K key) {
        Node<K, V> oldNode = cacheMap.get(key);

        cache.removeNode(oldNode);
        cacheMap.remove(key);

        return oldNode.getValue().getValue();
    }

    @Override
    public void clearAll() {
        cacheMap.clear();
        cache.clearAll();
    }

    @Override
    public Pair<K, V> getEldestEntry() {
        Node<K, V> lastNode = cache.getLastNode();

        if (lastNode == null) {
            return null;
        }

        return lastNode.getValue();
    }
}
