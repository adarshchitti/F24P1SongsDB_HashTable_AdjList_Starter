import student.TestCase;

/**
 * @author <Put something here>
 * @version <Put something here>
 */
public class HashTest extends TestCase {
    private Record rec1;
    private Record rec2;
    private Hash toTest;

    /**
     * Sets up the tests that follow. In general, used for initialization
     */
    public void setUp() {
        rec1 = new Record("hey", 0);
        rec2 = new Record("hi", 0);
        toTest = new Hash(2);
    }


    /**
     * Checks out the Find method
     */
    public void testFind() {
        toTest.insert(rec1);
        assertTrue(toTest.find("hey") != -1);

        assertEquals(-1, toTest.find("bye"));
    }


    /**
     * Checks out the hashFind method
     */
    public void testHashFind() {
        toTest.insert(rec1);
        assertTrue(toTest.find("hey") != -1);

        assertEquals(-1, toTest.find("bye"));
    }


    /**
     * Tests the insert method
     */
    public void testInsert() {
        toTest.insert(rec1);
        assertEquals(1, toTest.getNumberOfRecords());
        toTest.insert(rec1);
        assertEquals(1, toTest.getNumberOfRecords());
        toTest.insert(rec2);
        assertEquals(4, toTest.getTotalSize());
        assertTrue(toTest.find(rec2.key) != -1);
        assertTrue(toTest.find(rec1.key) != -1);

    }


    /**
     * Tests remove method
     */
    public void testRemove() {
        toTest.insert(rec1);
        toTest.insert(rec2);
        int index = toTest.hashFind("hi");
        assertTrue(toTest.remove("hi"));
        assertEquals(1, toTest.getNumTombstone());
        assertFalse(toTest.remove("hhh"));
        Record[] test = toTest.getAllRecords();
        assertEquals(test[index].key, "TOMBSTONE");
        assertEquals(1, toTest.getNumTombstone());
    }


    /**
     * Tests print method
     */
    public void testPrint() {
        toTest.insert(rec1);
        toTest.insert(rec2);

        String ans = toTest.print();

        String expected = "0: |hey|\n" + "1: |hi|\n";
        assertEquals(expected, ans);

    }


    /**
     * Check out the sfold method
     *
     * @throws Exception
     *             either a IOException or FileNotFoundException
     */
    public void testSfold() throws Exception {
        assertTrue(Hash.h("a", 10000) == 97);
        assertTrue(Hash.h("b", 10000) == 98);
        assertTrue(Hash.h("aaaa", 10000) == 1873);
        assertTrue(Hash.h("aaab", 10000) == 9089);
        assertTrue(Hash.h("baaa", 10000) == 1874);
        assertTrue(Hash.h("aaaaaaa", 10000) == 3794);
        assertTrue(Hash.h("Long Lonesome Blues", 10000) == 4635);
        assertTrue(Hash.h("Long   Lonesome Blues", 10000) == 4159);
        assertTrue(Hash.h("long Lonesome Blues", 10000) == 4667);
    }
}
