/**
 * A class representing a single node in a linked chain.
 * Each node contains a data element and a reference to the next node in the
 * chain.
 *
 * @param <T>
 *            the type of the data stored in the node
 */
public class Node<T> {

    /** The data stored in this node. */
    private T data;

    /** The reference to the next node in the chain. */
    private Node<T> next;

    /**
     * Constructs a new node with the specified data and next node reference.
     *
     * @param data
     *            the data to be stored in the node
     * @param next
     *            the reference to the next node in the chain
     */
    public Node(T data, Node<T> next) {
        this.data = data;
        this.next = next;
    }


    /**
     * Retrieves the data stored in this node.
     *
     * @return the data stored in this node
     */
    public T getData() {
        return this.data;
    }


    /**
     * Updates the data stored in this node.
     *
     * @param value
     *            the new value to be stored in this node
     */
    public void setData(T value) {
        this.data = value;
    }


    /**
     * Retrieves the reference to the next node in the chain.
     *
     * @return the reference to the next node, or null if this is the last node
     */
    public Node<T> getNext() {
        return this.next;
    }


    /**
     * Updates the reference to the next node in the chain.
     *
     * @param next
     *            the new reference to the next node
     */
    public void setNext(Node<T> next) {
        this.next = next;
    }
}
