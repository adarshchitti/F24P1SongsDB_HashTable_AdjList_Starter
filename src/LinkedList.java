import java.util.Iterator;
import java.util.NoSuchElementException;

// -------------------------------------------------------------------------
/**
 * A generic version of a list, represented using a singly-linked chain of
 * nodes. This implementation includes enough operations to solve this
 * problem, but other useful operations needed for a complete data structure
 * (including more flexible remove() support, a proper definition of
 * hashCode(), etc.) are omitted to keep this example shorter.
 *
 * This class provides an implementation of equals() for use in testing,
 * an implementation of toString() for use in debugging, and an
 * implementation of iterator() to support for-each-style loops for
 * list traversals.
 *
 * @param <T>
 *            The type of element that will be stored in the list.
 *
 * @author Stephen Edwards
 * @version 2023.09.15
 */
public class LinkedList<T> implements Iterable<T> {
    // ~ Fields ................................................................

    // This representation uses a pair of sentinel nodes at each end of
    // the chain to make it easier to insert at either end in constant time.
    private Node<T> head;
    private Node<T> tail;
    private int size;

    // ~ Constructor ...........................................................

    // ----------------------------------------------------------
    /**
     * Create a new, empty list.
     */
    public LinkedList() {
        this.clear();
    }

    // ~ Public Methods ........................................................


    // ----------------------------------------------------------
    /**
     * Resets this list to be empty by removing all its values.
     */
    public void clear() {
        // throws away any existing chain of nodes and replaces it with
        // a new empty chain containing just the sentinel nodes at start
        // and end.
        this.tail = new Node<>(null, null);
        this.head = new Node<>(null, this.tail);
        this.size = 0;
    }


    // ----------------------------------------------------------
    /**
     * Add a new value to the front of this list.
     *
     * @param value
     *            The new value to add.
     */
    public void addToFront(T value) {
        // inserts immediately after the sentinel node at the start
        this.head.setNext(new Node<>(value, this.head.getNext()));
        ++this.size;
    }


    // ----------------------------------------------------------
    /**
     * Add a new value to the rear of this list.
     *
     * @param value
     *            The new value to add.
     */
    public void addToRear(T value) {
        // Use the tail sentinel node to hold the data
        this.tail.setData(value);

        // then create a new tail sentinel node after it
        this.tail.setNext(new Node<>(null, null));
        this.tail = this.tail.getNext();
        ++this.size;
    }


    // ----------------------------------------------------------
    /**
     * Remove and return the front element of the list (at index position 0).
     *
     * @return The value removed from the front of the list.
     * @throws NoSuchElementException
     *             if the list is empty.
     */
    public T removeFromFront() {
        if (this.head.getNext() == this.tail) {
            // the list is empty
            throw new NoSuchElementException(
                "removeFromFront() called on empty list");
        }

        T value = this.head.getNext().getData();
        this.head = this.head.getNext();
        --this.size;
        return value;
    }
    
    /**
     * Runs through the list to see if the list contains the value
     *
     * @return true or false
     * @param value - value to be found
     */
    public boolean contains(T value) {
        Node<T> temp = head;
        while(temp.getData() != null) {
            if (temp.getData().equals(value)) {
                return true;
            }
            temp = temp.getNext();
        }
        return false;
    }


    // ----------------------------------------------------------
    /**
     * Return the value at the specified position/index within the list.
     * Index values start at zero at the front of the list.
     *
     * @param index
     *            The position to examine.
     * @return The value currently stored at the specified index.
     * @throws IllegalArgumentException
     *             if the specified index is not a
     *             legal position.
     */
    public T get(int index) {
        if (index >= this.size) {
            throw new IllegalArgumentException("Index " + index
                + "out of bounds. There are only " + this.size
                + " elements in the list.");
        }

        Node<T> current = this.head.getNext();
        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }

        return current.getData();
    }


    // ----------------------------------------------------------
    /**
     * Return the number of elements currently stored in the list.
     *
     * @return The list's size, or the number of elements it contains.
     */
    public int getSize() {
        return this.size;
    }

    // Other operations, like various other remove() methods, or inserting
    // at a specified position, are left as an exercise for the reader.


    // ----------------------------------------------------------
    /**
     * Supports iteration over the list in natural order (i.e., front to
     * back).
     *
     * @return An iterator that can be used to loop over the list's contents.
     */
    @Override
    public Iterator<T> iterator() {
        return new ListIterator();
    }


    // ----------------------------------------------------------
    /**
     * The internal Iterator class that represents a traversal over the
     * linked chain. Note that this class is not static, since it needs
     * to access the head and tail fields of the list.
     */
    private class ListIterator implements Iterator<T> {
        private Node<T> current;

        private ListIterator() {
            current = head.getNext();
        }


        @Override
        public boolean hasNext() {
            return current != tail;
        }


        @Override
        public T next() {
            T value = current.getData();
            current = current.getNext();
            return value;
        }

    }
}
