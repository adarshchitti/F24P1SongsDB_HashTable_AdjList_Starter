
/**
 * Test class for the Hash class, which uses quadratic probing for collision
 * resolution.
 * Tests various scenarios including insertion, removal, expansion, and
 * hashFinding elements.
 * This test class assumes the use of the custom hash function provided in the
 * Hash class.
 * 
 * @author Brantson and adarsh
 * @version 09.05.2024
 */
public class HashTest extends student.TestCase {

    private Hash hashTable;

    /**
     * Sets up a new Hash table before each test case.
     */
    public void setUp() {
        hashTable = new Hash(10);
    }


    /**
     * Tests Find method
     */
    public void testFind() {
        assertEquals(-1, hashTable.find("hey"));
        hashTable.insert(new Record("hey", 1));
        assertEquals(1, hashTable.find("hey"));
        hashTable.insert(new Record("hey2", 2));
        assertEquals(2, hashTable.find("hey2"));
        hashTable.insert(new Record("hey3", 3));
        assertEquals(3, hashTable.find("hey3"));
        hashTable.insert(new Record("hey4", 4));
        assertEquals(4, hashTable.find("hey4"));
        hashTable.insert(new Record("hey5", 5));
        assertEquals(5, hashTable.find("hey5"));
    }


    /**
     * Tests find with collisions involved
     */
    public void testFindWithCollisions() {

        assertEquals(-1, hashTable.find("nonexistent"));
        hashTable.insert(new Record("hey", 1));
        assertEquals(1, hashTable.find("hey"));
        hashTable.insert(new Record("heyCollision", 2));
        assertEquals(2, hashTable.find("heyCollision"));
        hashTable.insert(new Record("hey3", 3));
        assertEquals(3, hashTable.find("hey3"));
        hashTable.insert(new Record("hey4", 4));
        assertEquals(4, hashTable.find("hey4"));
        hashTable.insert(new Record("collideAgain", 5));
        assertEquals(5, hashTable.find("collideAgain"));
        hashTable.insert(new Record("wrapAround", 6));
        assertEquals(6, hashTable.find("wrapAround"));
        assertEquals(1, hashTable.find("hey"));
    }


    /**
     * Tests insertion of a record into an empty hash table.
     * Verifies that the record is inserted at the correct index.
     */
    public void testInsert() {
        hashTable = new Hash(10);
        Record record1 = new Record("A", 1);
        Record record2 = new Record("B", 2);
        Record record3 = new Record("C", 3);
        Record record4 = new Record("TOMBSTONE", -1);

        hashTable.insert(record1);
        assertNotNull(hashTable.hashFind(record1.getKey()));
        assertEquals(record1, hashTable.getAllRecords()[hashTable.hashFind(
            record1.getKey())]);

        hashTable.insert(record2);
        assertNotNull(hashTable.hashFind(record2.getKey()));
        assertEquals(record2, hashTable.getAllRecords()[hashTable.hashFind(
            record2.getKey())]);

        hashTable.getAllRecords()[hashTable.hashFind(record1.getKey())] =
            record4;
        hashTable.insert(record3);
        assertNotNull(hashTable.hashFind(record3.getKey()));
        assertEquals(record3, hashTable.getAllRecords()[hashTable.hashFind(
            record3.getKey())]);

        for (int i = 0; i < 6; i++) {
            hashTable.insert(new Record("R" + i, i));
        }
        assertTrue(hashTable.getTotalSize() > 10);
        assertEquals(8, hashTable.getNumberOfRecords());

        hashTable.insert(record1);
        assertEquals(9, hashTable.getNumberOfRecords());

        hashTable = new Hash(10);
        Record record = new Record("testKey", 1);
        hashTable.insert(record);

        int expectedIndex = Hash.h("testKey", 10);
        assertEquals(record, hashTable.getAllRecords()[expectedIndex]);
        assertEquals(1, hashTable.getNumberOfRecords());
        hashTable.insert(new Record("hello", 2));
        hashTable.remove("hello");
        hashTable.insert(new Record("hello", 2));
        assertEquals("hello", hashTable.getAllRecords()[Hash.h("hello", 10)]
            .getKey());
        hashTable.insert(new Record("tetKey", 3));
        int eind = (Hash.h("tetKey", 10) + (1)) % 10;
        assertEquals(eind, hashTable.hashFind("tetKey"));
        hashTable.insert(record);
        assertEquals(3, hashTable.getNumberOfRecords());

    }


    /**
     * Tests removal of a record from the hash table.
     * Verifies that the record is marked as a tombstone and can no longer be
     * found.
     */

    public void testRemoveRecord() {
        assertFalse(hashTable.remove("aaaaa"));
        Record record = new Record("removeMe", 1);
        hashTable.insert(record);

        boolean removed = hashTable.remove("removeMe");
        assertTrue(removed);
        assertEquals(1, hashTable.getNumTombstone());

        int index = Hash.h("removeMe", 10);
        assertEquals("TOMBSTONE", hashTable.getAllRecords()[index].getKey());
        assertEquals(-1, hashTable.hashFind("removeMe"));
        hashTable.insert(new Record("helloIll", 2));
        hashTable.remove("helloIll");
        assertEquals(2, hashTable.getNumTombstone());

    }


    /**
     * Tests the expansion of the hash table when more than 50% full.
     * Verifies that all records are rehashed and no records are lost.
     */

    public void testExpandCapacity() {

        hashTable.insert(new Record("key1", 1));
        hashTable.insert(new Record("key2", 2));
        hashTable.insert(new Record("key3", 3));
        hashTable.insert(new Record("key4", 4));
        hashTable.insert(new Record("key5", 5));
        hashTable.remove("key2");
        hashTable.insert(new Record("key6", 6));
        hashTable.insert(new Record("key7", 7));
        assertEquals(hashTable.getTotalSize(), 20);
        assertEquals(6, hashTable.getNumberOfRecords());
        assertEquals(Hash.h("key1", 20), hashTable.hashFind("key1"));
        assertEquals(Hash.h("key3", 20), hashTable.hashFind("key3"));
        assertEquals(Hash.h("key4", 20), hashTable.hashFind("key4"));
        assertEquals(Hash.h("key5", 20), hashTable.hashFind("key5"));
        hashTable.insert(new Record("key8", 8));
        int eInd1 = Hash.h("key8", 20);
        System.out.println(eInd1);
        int count = 0;
        while (hashTable.hashFind("key8") != eInd1) {
            count++;
            eInd1 = (eInd1 + count * count) % 20;
        }
        assertEquals(eInd1, hashTable.hashFind("key8"));

        hashTable.insert(new Record("key9", 9));

        int eInd = Hash.h("key9", 20);
        System.out.println(eInd);
        int count2 = 0;
        while (hashTable.hashFind("key9") != eInd) {
            count2++;
            eInd = (eInd + count2 * count2) % 20;
        }
        assertEquals(eInd, hashTable.hashFind("key9"));

    }


    /**
     * Tests that inserting a record with a duplicate key does not create a new
     * entry.
     */

    public void testInsertDuplicateKey() {
        Record record1 = new Record("duplicateKey", 1);
        Record record2 = new Record("duplicateKey", 2);

        hashTable.insert(record1);
        hashTable.insert(record2);

        int expectedIndex = Hash.h("duplicateKey", 10);
        assertEquals(record1, hashTable.getAllRecords()[expectedIndex]);
        assertNotSame(record2, hashTable.getAllRecords()[expectedIndex]);
    }


    /**
     * Tests the print() method of the Hash class.
     * It ensures that all possible scenarios in the print method are correctly
     * handled.
     */
    public void testPrint() {

        Hash toTest = new Hash(5);
        String expected;

        expected = "";
        assertEquals(expected, toTest.print());

        Record record1 = new Record("record1", 1);
        Record record2 = new Record("record2", 2);
        Record record3 = new Record("record3", 3);

        toTest.insert(record1);
        toTest.insert(record2);
        toTest.insert(record3);

        expected = "0: |record3|\n4: |record2|\n8: |record1|\n";
        assertEquals(expected, toTest.print());

        toTest.remove("record2");
        expected = "0: |record3|\n4: TOMBSTONE\n8: |record1|\n";
        assertEquals(expected, toTest.print());
        toTest.remove("record1");
        expected = "0: |record3|\n4: TOMBSTONE\n8: TOMBSTONE\n";
        assertEquals(expected, toTest.print());
    }


    /**
     * Test method for Hash.h(String s, int length).
     * This method tests all possible cases for the hash function.
     */
    public void testHashFunction() {
        int length = 100;

        assertEquals(0, Hash.h("", length));

        assertEquals(Hash.h("a", length), Hash.h("a", length));
        assertEquals(Hash.h("abc", length), Hash.h("abc", length));

        assertEquals(Hash.h("abcd", length), Hash.h("abcd", length));

        assertEquals(Hash.h("abcdefgh", length), Hash.h("abcdefgh", length));

        assertEquals(Hash.h("@#%&", length), Hash.h("@#%&", length));

        assertEquals(Hash.h("1234567890", length), Hash.h("1234567890",
            length));

        assertEquals(Hash.h("a1b2c3d4", length), Hash.h("a1b2c3d4", length));

        String largeString =
            "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        assertEquals(Hash.h(largeString, length), Hash.h(largeString, length));

        int maxLength = Integer.MAX_VALUE % length;
        StringBuilder maxString = new StringBuilder();
        for (int i = 0; i < maxLength; i++) {
            maxString.append('a');
        }
        assertEquals(Hash.h(maxString.toString(), length), Hash.h(maxString
            .toString(), length));

        assertEquals(Hash.h("abcdefgh", 50), Hash.h("abcdefgh", 50));
        assertEquals(Hash.h("abcdefgh", 200), Hash.h("abcdefgh", 200));

        int highModulus = 10000;
        assertEquals(Hash.h("hashstring", highModulus), Hash.h("hashstring",
            highModulus));

        assertNotSame(Hash.h("abcdefg", length), Hash.h("abcdefh", length));
    }

}
