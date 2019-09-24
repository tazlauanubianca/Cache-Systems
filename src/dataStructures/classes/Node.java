package dataStructures.classes;

import java.sql.Timestamp;

/**
 * This class is used to represent a node form the doubly-linked list.
 * @param <K>
 * @param <V>
 */
public class Node<K, V> {
    public Node<K, V> nextNode;
    public Node<K, V> prevNode;
    private Timestamp timestamp;
    private Pair<K, V> value;

    public Node(Pair<K, V> value) {
        this.value = value;
        nextNode = null;
        prevNode = null;
        timestamp = new Timestamp(System.currentTimeMillis());
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setValue(Pair<K, V> value) {
        this.value = value;
    }

    public Pair<K, V> getValue() {
        return value;
    }
}
