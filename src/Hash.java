/**
 * A class representing a Hash Table that stores records using quadratic probing
 * for collision resolution.
 * It automatically expands its capacity when more than 50% full.
 * 
 * Authors: Brantson and Adarsh
 * 
 * @version 1.0
 */
public class Hash {

    private Record[] allRecords;
    private int numberOfRecords;
    private int totalSize;
    private int numTombstone;

    /**
     * Constructor for creating a Hash table with a specified initial size.
     * 
     * @param size
     *            the initial size of the hash table
     */
    public Hash(int size) {
        this.setAllRecords(new Record[size]);
        this.setNumberOfRecords(0);
        this.setTotalSize(size);
        this.setNumTombstone(0);
    }


    /**
     * Finds the index of a record with the specified key in the hash table.
     * Uses quadratic probing to handle collisions.
     * 
     * @param value
     *            the key to search for
     * @return the index of the record if found, -1 otherwise
     */
    public int find(String value) {
        int index = h(value, getTotalSize());
        int i = 0;
        while (getAllRecords()[index] != null) {
            if (getAllRecords()[index].key.equals(value)) {
                return getAllRecords()[index].index;
            }
            i++;
            index = (index + (i * i)) % getTotalSize();
        }
        return -1;
    }


    /**
     * Helper method that finds the index where a key is stored in the hash
     * table.
     * Uses quadratic probing for collision resolution.
     * 
     * @param value
     *            the key to search for
     * @return the index of the record if found, -1 otherwise
     */
    public int hashFind(String value) {
        int index = h(value, getTotalSize());
        int i = 0;
        while (getAllRecords()[index] != null) {
            if (getAllRecords()[index].key.equals(value)) {
                return index;
            }
            i++;
            index = (index + (i * i)) % getTotalSize();
        }
        return -1;
    }


    /**
     * Removes a record with the specified key from the hash table by setting it
     * as a tombstone.
     * 
     * @param value
     *            the key of the record to be removed
     * @return true if the record was successfully removed, false otherwise
     */
    public boolean remove(String value) {
        int index = hashFind(value);
        if (index == -1) {
            return false;
        }
        getAllRecords()[index].key = "TOMBSTONE";
        numTombstone++;
        numberOfRecords--;
        return true;
    }


    /**
     * Inserts a new record into the hash table. If the table is more than 50%
     * full,
     * it will expand its capacity and rehash all existing records.
     * 
     * @param record
     *            the record to be inserted
     */
    public void insert(Record record) {
        if (numberOfRecords >= (totalSize / 2)) {
            expandCapacity();
        }
        if (hashFind(record.key) == -1) {
            int index = h(record.key, totalSize);
            int count = 0;
            while (allRecords[index] != null) {
                count++;
                index = (index + count * count) % totalSize;
            }
            allRecords[index] = record;
            numberOfRecords++;
        }
    }


    /**
     * Helper method that doubles the size of the hash table and rehashes all
     * existing records.
     * This method is called when the table becomes more than 50% full.
     */
    private void expandCapacity() {
        Record[] temp = new Record[allRecords.length];
        for (int i = 0; i < temp.length; i++) {
            if (allRecords[i] != null) {
                Record rec = new Record(allRecords[i].key, allRecords[i].index);
                temp[i] = rec;
            }
        }
        totalSize = totalSize * 2;
        allRecords = new Record[totalSize];
        numberOfRecords = 0;
        for (int i = 0; i < temp.length; i++) {
            if (temp[i] != null) {
                int index = h(temp[i].key, totalSize);
                int count = 0;
                while (allRecords[index] != null) {
                    count++;
                    index = (index + count * count) % totalSize;
                }
                allRecords[index] = temp[i];
                numberOfRecords++;
            }
        }
    }


    /**
     * Computes the hash value for a given string key using a custom hashing
     * algorithm.
     * 
     * @param s
     *            the string key to hash
     * @param length
     *            the size of the hash table
     * @return the computed hash value
     */
    public static int h(String s, int length) {
        int intLength = s.length() / 4;
        long sum = 0;
        for (int j = 0; j < intLength; j++) {
            char[] c = s.substring(j * 4, (j * 4) + 4).toCharArray();
            long mult = 1;
            for (int k = 0; k < c.length; k++) {
                sum += c[k] * mult;
                mult *= 256;
            }
        }

        char[] c = s.substring(intLength * 4).toCharArray();
        long mult = 1;
        for (int k = 0; k < c.length; k++) {
            sum += c[k] * mult;
            mult *= 256;
        }

        return (int)(Math.abs(sum) % length);
    }


    /**
     * Prints the current state of the hash table, showing the index and key of
     * each record.
     * 
     * @return a string representation of the hash table
     */
    public String print() {
        String result = "";
        for (int i = 0; i < totalSize; i++) {
            if (allRecords[i] != null) {
                result = result + i + ":" + " |" + allRecords[i].key
                    + "|\n";
            }
        }

        return result;
    }


    // Getter and setter methods for the class attributes
    /**
     * Gets the current number of records in the hash table.
     * 
     * @return the number of records
     */
    public int getNumberOfRecords() {
        return numberOfRecords;
    }


    /**
     * Sets the number of records in the hash table.
     * 
     * @param numberOfRecords
     *            the new number of records
     */
    public void setNumberOfRecords(int numberOfRecords) {
        this.numberOfRecords = numberOfRecords;
    }


    /**
     * Gets the total size of the hash table.
     * 
     * @return the total size of the table
     */
    public int getTotalSize() {
        return totalSize;
    }


    /**
     * Sets the total size of the hash table.
     * 
     * @param totalSize
     *            the new total size of the table
     */
    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }


    /**
     * Gets the number of tombstones (removed records) in the hash table.
     * 
     * @return the number of tombstones
     */
    public int getNumTombstone() {
        return numTombstone;
    }


    /**
     * Sets the number of tombstones (removed records) in the hash table.
     * 
     * @param numTombstone
     *            the new number of tombstones
     */
    public void setNumTombstone(int numTombstone) {
        this.numTombstone = numTombstone;
    }


    /**
     * Gets the array of records in the hash table.
     * 
     * @return the array of records
     */
    public Record[] getAllRecords() {
        return allRecords;
    }


    /**
     * Sets the array of records in the hash table.
     * 
     * @param allRecords
     *            the new array of records
     */
    public void setAllRecords(Record[] allRecords) {
        this.allRecords = allRecords;
    }
}
