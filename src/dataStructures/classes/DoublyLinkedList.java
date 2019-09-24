package dataStructures.classes;

/**
 * This class is used to implement the functionality of a doubly-linked list.
 * The list will start from the head node. The class also has a pointer to the last node and
 * keeps the size of the list.
 * @param <K>
 * @param <V>
 */
public class DoublyLinkedList<K, V> {
    private Node<K, V> head;
    private Node<K, V> last;
    private int size;

    public DoublyLinkedList() {
        head = null;
        last = null;
        size = 0;
    }

    /**
     * A new node is addes to the front of the list.
     * @param value
     * @return
     */
    public Node<K, V> addNode(Pair<K, V> value) {
        Node<K, V> newNode = new Node(value);

        if (head == null) {
            head = newNode;
            last = newNode;
        } else {
            newNode.nextNode = head;
            head.prevNode = newNode;
            head = newNode;
        }

        size++;

        return newNode;
    }

    /**
     * A node is removed from the list.
     * @param node
     */
    public void removeNode(Node<K, V> node) {
        if (node == last && node == head) {
            last = null;
            head = null;
        }

        if (node == head && node != last) {
            head = node.nextNode;
        }

        if (node == last && node != head) {
            last = node.prevNode;
        }

        if (node.prevNode != null) {
            node.prevNode.nextNode = node.nextNode;
        }

        if (node.nextNode != null) {
            node.nextNode.prevNode = node.prevNode;
        }

        size--;
    }

    /**
     * A node is moved to the front of the list.
     * @param node
     */
    public void updateNode(Node<K, V> node) {
        if (node == head) {
            return;
        }

        if (node == last) {
            last = node.prevNode;
        }

        if (node.prevNode != null) {
            node.prevNode.nextNode = node.nextNode;
        }

        if (node.nextNode != null) {
            node.nextNode.prevNode = node.prevNode;
        }

        node.nextNode = head;
        node.prevNode = null;
        head.prevNode = node;
        head = node;
    }

    /**
     * The last node of the list is returned.
     * @return
     */
    public Node<K, V> getLastNode() {
        return last;
    }

    /**
     * The size of the list is returned.
     * @return
     */
    public int getSize() {
        return size;
    }

    /**
     * Is returned true if the list is empty and false otherwise.
     * @return
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * The whole list is deleted.
     */
    public void clearAll() {
        Node<K, V> startNode = head;

        while (head != null) {
            removeNode(startNode);
        }
    }
}
