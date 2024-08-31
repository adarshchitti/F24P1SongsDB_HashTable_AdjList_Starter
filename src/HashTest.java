import student.TestCase;

/**
 * @author <Put something here>
 * @version <Put something here>
 */
public class HashTest extends TestCase {
    private Record<String> rec1;
    private Record<String> rec2;
    private Record<String> rec3;
    private Hash toTest;
    /**
     * Sets up the tests that follow. In general, used for initialization
     */
    public void setUp() {
        rec1 = new Record<String>("hey",new Node<String>(null, null));
        rec2 = new Record<String>("hi",new Node<String>(null, null));
        rec3 = new Record<String>("hello",new Node<String>(null, null));
        toTest = new Hash(2);
    }

    public void testFind()
    {
        toTest.insert(rec1);
        assertTrue(toTest.find("hey") != -1);
        assertEquals(-1,toTest.find("bye"));        
    }
    public void testInsert()
    {
        toTest.insert(rec1);
        assertEquals(1,toTest.getNumberOfRecords()); 
        toTest.insert(rec2);
        assertEquals(4,toTest.getTotalSize());
    }
    public void testRemove()
    {
        toTest.insert(rec1);
        toTest.insert(rec2);
        int index = toTest.find("hi");
        assertTrue(toTest.remove("hi"));
        assertEquals(1,toTest.getNumTombstone());
        assertFalse(toTest.remove("hhh"));
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
