
/**
 * Test class for the Hash class, which uses quadratic probing for collision
 * resolution.
 * Tests various scenarios including insertion, removal, expansion, and
 * hashFinding elements.
 * This test class assumes the use of the custom hash function provided in the
 * Hash class.
 * 
 * @author Brantson
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
     * Tests insertion of a record into an empty hash table.
     * Verifies that the record is inserted at the correct index.
     */
    public void testInsertIntoEmptyTable() {
        Record record = new Record("testKey", 1);
        hashTable.insert(record);

        int expectedIndex = Hash.h("testKey", 10);
        assertEquals(record, hashTable.getAllRecords()[expectedIndex]);
        assertEquals(1, hashTable.getNumberOfRecords());
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
        assertEquals("TOMBSTONE", hashTable.getAllRecords()[index].key);
        assertEquals(-1, hashTable.hashFind("removeMe"));
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

        assertTrue(hashTable.getTotalSize() > 10);
        assertEquals(Hash.h("key1", 20), hashTable.hashFind("key1"));
        assertEquals(Hash.h("key2", 20), hashTable.hashFind("key2"));
        assertEquals(Hash.h("key3", 20), hashTable.hashFind("key3"));
        assertEquals(Hash.h("key4", 20), hashTable.hashFind("key4"));
        assertEquals(Hash.h("key5", 20), hashTable.hashFind("key5"));
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

        Hash hashTable = new Hash(5);
        String expected;

        expected = "";
        assertEquals(expected, hashTable.print());

        Record record1 = new Record("record1", 1);
        Record record2 = new Record("record2", 2);
        Record record3 = new Record("record3", 3);

        hashTable.insert(record1);
        hashTable.insert(record2);
        hashTable.insert(record3);

        expected = "0: |record3|\n4: |record2|\n8: |record1|\n";
        assertEquals(expected, hashTable.print());

        hashTable.remove("record2");
        expected = "0: |record3|\n4: TOMBSTONE\n8: |record1|\n";
        assertEquals(expected, hashTable.print());
        hashTable.remove("record1");
        expected = "0: |record3|\n4: TOMBSTONE\n8: TOMBSTONE\n";
        assertEquals(expected, hashTable.print());
    }

}
