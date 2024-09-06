
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
public class HashTestTwo extends student.TestCase {

    private Hash hashTable;

    /**
     * Sets up a new Hash table before each test case.
     */
    public void setUp() {
        hashTable = new Hash(10); // Initial size of 10
    }


    /**
     * Tests insertion of a record into an empty hash table.
     * Verifies that the record is inserted at the correct index.
     */

    public void testInsertIntoEmptyTable() {
        Record record = new Record("testKey", 1);
        hashTable.insert(record);

        int expectedIndex = Hash.h("testKey", 10);
        assertEquals("The record should be inserted at the hashed index.",
            record, hashTable.getAllRecords()[expectedIndex]);
    }


    /**
     * Tests insertion of multiple records into the hash table,
     * ensuring that collisions are handled via quadratic probing.
     */

    public void testInsertWithCollision() {
        Record record1 = new Record("key1", 1);
        Record record2 = new Record("key2", 2);

        hashTable.insert(record1);
        hashTable.insert(record2);

        // Check both records are inserted correctly
        assertNotNull("Record 1 should be inserted.", hashTable.hashFind(
            "key1"));
        assertNotNull("Record 2 should be inserted.", hashTable.hashFind(
            "key2"));

        // Verify that collisions were handled properly
        int index1 = Hash.h("key1", 10);
        int index2 = Hash.h("key2", 10);

        // They should not be at the same index due to collision handling
        assertNotSame("Collision should be resolved, indices should differ.",
            index1, index2);
    }


    /**
     * Tests removal of a record from the hash table.
     * Verifies that the record is marked as a tombstone and can no longer be
     * found.
     */

    public void testRemoveRecord() {
        Record record = new Record("removeMe", 1);
        hashTable.insert(record);

        boolean removed = hashTable.remove("removeMe");
        assertTrue("The record should be removed successfully.", removed);

        int index = Hash.h("removeMe", 10);
        assertEquals("The record should be replaced with a tombstone.",
            "TOMBSTONE", hashTable.getAllRecords()[index].key);
        assertEquals("After removal, record should not be found.", -1, hashTable
            .hashFind("removeMe"));
    }


    /**
     * Tests handling of collisions during removal, ensuring that quadratic
     * probing
     * continues to work for hashFinding elements after removal of an
     * intermediate record.
     */

    public void testRemoveWithCollision() {
        Record record1 = new Record("key1", 1);
        Record record2 = new Record("key2", 2);
        hashTable.insert(record1);
        hashTable.insert(record2);

        boolean removed = hashTable.remove("key1");
        assertTrue("The first record should be removed successfully.", removed);

        // Ensure the second record is still accessible
        assertEquals("Record 2 should still be hashFindable.", Hash.h("key2",
            10), hashTable.hashFind("key2"));
    }


    /**
     * Tests the expansion of the hash table when more than 50% full.
     * Verifies that all records are rehashed and no records are lost.
     */

    public void testExpandCapacity() {
        // Insert enough records to trigger expansion
        hashTable.insert(new Record("key1", 1));
        hashTable.insert(new Record("key2", 2));
        hashTable.insert(new Record("key3", 3));
        hashTable.insert(new Record("key4", 4));
        hashTable.insert(new Record("key5", 5));

        // Table should have expanded
        assertTrue("Hash table should expand when more than 50% full.",
            hashTable.getTotalSize() > 10);

        // All records should still be hashFindable after expansion
        assertEquals("Record 1 should be hashFindable after expansion.", Hash.h(
            "key1", 20), hashTable.hashFind("key1"));
        assertEquals("Record 2 should be hashFindable after expansion.", Hash.h(
            "key2", 20), hashTable.hashFind("key2"));
        assertEquals("Record 3 should be hashFindable after expansion.", Hash.h(
            "key3", 20), hashTable.hashFind("key3"));
        assertEquals("Record 4 should be hashFindable after expansion.", Hash.h(
            "key4", 20), hashTable.hashFind("key4"));
        assertEquals("Record 5 should be hashFindable after expansion.", Hash.h(
            "key5", 20), hashTable.hashFind("key5"));
    }


    /**
     * Tests that inserting a record with a duplicate key does not create a new
     * entry.
     */

    public void testInsertDuplicateKey() {
        Record record1 = new Record("duplicateKey", 1);
        Record record2 = new Record("duplicateKey", 2);

        hashTable.insert(record1);
        hashTable.insert(record2); // This should not replace record1

        int expectedIndex = Hash.h("duplicateKey", 10);
        assertEquals("The original record should remain in place.", record1,
            hashTable.getAllRecords()[expectedIndex]);
        assertNotSame("Record 2 should not overwrite Record 1.", record2,
            hashTable.getAllRecords()[expectedIndex]);
    }


    /**
     * Tests the print() method of the Hash class.
     * It ensures that all possible scenarios in the print method are correctly
     * handled.
     */
    public void testPrint() {
        // Initialize the hash table with size 5
        Hash hashTable = new Hash(5);
        String expected;

        // 1. Case: Empty hash table
        expected = ""; // No records should be printed
        assertEquals("Empty table should return an empty string.", expected,
            hashTable.print());

        // Insert records
        Record record1 = new Record("record1", 1);
        Record record2 = new Record("record2", 2);
        Record record3 = new Record("record3", 3);

        hashTable.insert(record1); // Hash it to position 0
        hashTable.insert(record2); // Hash it to position 1
        hashTable.insert(record3); // Hash it to position 2

        // 2. Case: Valid records
        expected = "0: |record3|\n4: |record2|\n8: |record1|\n";
        assertEquals("Should print valid records.", expected, hashTable
            .print());

        // 3. Case: Record marked as tombstone
        hashTable.remove("record2"); // Mark as tombstone
        expected = "0: |record3|\n4: TOMBSTONE\n8: |record1|\n";
        assertEquals("Should print tombstone at position 1.", expected,
            hashTable.print());

        // 4. Case: Mix of records, tombstones, and nulls
        // Now the table has a tombstone at position 1 and a valid record at 0
        hashTable.remove("record1"); // Mark position 0 as tombstone as well
        expected = "0: |record3|\n4: TOMBSTONE\n8: TOMBSTONE\n";
        assertEquals("Both positions should print tombstones.", expected,
            hashTable.print());
    }

}
