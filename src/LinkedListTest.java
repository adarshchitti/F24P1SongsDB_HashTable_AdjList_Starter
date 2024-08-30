import student.TestCase;

// -------------------------------------------------------------------------
/**
 * This is the test class for {@link LinkedList}.
 *
 * Note that it is <b>not</b> a complete set of tests, but it is laid out
 * to show how to make it easier to write tests for such a class.
 *
 * @author Stephen Edwards
 * @version 2023.09.15
 */
public class LinkedListTest extends TestCase {
    // ~ Fields ................................................................

    private LinkedList<Integer> list;

    // ~ Public Methods ........................................................

    // ----------------------------------------------------------
    /**
     * Creates a fresh list for each test case.
     */
    public void setUp() {
        // Here, we're using the varargs version of the constructor to
        // make it easy to create list values with arbitrary contents
        // for testing
        list = new LinkedList<>();
        list.addToRear(1);
        list.addToRear(2);
        list.addToRear(3);
        list.addToRear(4);
        list.addToRear(5);
    }


    // ----------------------------------------------------------
    /**
     * Test that the constructor initialized the object properly.
     */
    public void testNewList() {
        assertEquals(list.getSize(), 5);
    }


    // ----------------------------------------------------------
    /**
     * We can test addToFront() in a similar way.
     */
    public void testAddItem() {
        list.addToFront(1);
        assertEquals(list.getSize(), 6);
        assertEquals((int)list.get(0), 1);
    }


    // ----------------------------------------------------------
    /**
     * Tests the get() method for position-based access.
     */
    public void testGetItem() {
        assertEquals((int)list.get(2), 3);
        assertEquals((int)list.get(list.getSize() - 1), 5);
    }


    // ----------------------------------------------------------
    /**
     * Testing removeFromFront() is similar to testing addToFront().
     */
    public void testRemoveFromFront() {
        list.removeFromFront();
        assertEquals(list.getSize(), 4);
    }

    // Other test methods are left as an exercise for the reader
}
