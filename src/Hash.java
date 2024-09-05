/**
 * Hash table class
 *
 * @author Brantson and Adarsh
 * @version <Put Something Here>
 */

public class Hash {

    private Record[] allRecords;
    private int numberOfRecords;
    private int totalSize;
    private int numTombstone;

    /*
     * Constructor for a Hash Object
     */
    public Hash(int size) {
        this.setAllRecords(new Record[size]);
        this.setNumberOfRecords(0);
        this.setTotalSize(size);
        this.setNumTombstone(0);
    }


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


    /*
     * Find method to find the index of a record stored in the hash
     * If not found returns -1
     * If found return Hash Index
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


    /*
     * Removes a record with the specified key from the hash table by setting it
     * as a tombstone.
     * Returns true if removed
     * Return false if was not removed
     */
    public boolean remove(String value) {
        int index = hashFind(value);
        if (index == -1) {
            return false;
        }
        getAllRecords()[index].key = "TOMBSTONE";
        numTombstone++;
        return true;

    }


    /*
     * Inserts a new record into the hash table.
     * Uses quadratic probing to resolve collisions.
     * If the table becomes more than 50% full, expands its capacity.
     */
    public void insert(Record record) {
        if (hashFind(record.key) == -1) {
            int index = h(record.key, getTotalSize());
            int count = 0;
            while (getAllRecords()[index] != null) {
                count++;
                index = (index + count * count) % getTotalSize();
            }
            getAllRecords()[index] = record;
            numberOfRecords++;
        }
        if (numberOfRecords > (totalSize / 2)) {
            expandCapacity();
        }
    }


    /*
     * Helper method for insert.
     * Doubles the total size of the Hash table and rehashes the data.
     */
    private void expandCapacity() {
        Record[] temp = getAllRecords();
        setTotalSize(getTotalSize() * 2);
        setAllRecords(new Record[getTotalSize()]);
        setNumberOfRecords(0);

        for (int i = 0; i < temp.length; i++) {
            if (temp[i] != null) {
                insert(temp[i]);
            }
        }
    }


    /**
     * Compute the hash function
     * 
     * @param s
     *            The string that we are hashing
     * @param length
     *            Length of the hash table (needed because this method is
     *            static)
     * @return
     *         The hash function value (the home slot in the table for this key)
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


    /*
     * Prints the contents of the hash table, showing each record's index and
     * key.
     * Returns a string representation of the table.
     */
    public String print() {
        String result = "";
        for (int i = 0; i < totalSize; i++) {
            if (getAllRecords()[i] != null) {
                result = result + "Index: " + i + " Data: "
                    + getAllRecords()[i].key + "\n";
            }
        }

        return result;
    }


    // Getter and setter methods for the class attributes
    public int getNumberOfRecords() {
        return numberOfRecords;
    }


    public void setNumberOfRecords(int numberOfRecords) {
        this.numberOfRecords = numberOfRecords;
    }


    public int getTotalSize() {
        return totalSize;
    }


    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }


    public int getNumTombstone() {
        return numTombstone;
    }


    public void setNumTombstone(int numTombstone) {
        this.numTombstone = numTombstone;
    }


    public Record[] getAllRecords() {
        return allRecords;
    }


    public void setAllRecords(Record[] allRecords) {
        this.allRecords = allRecords;
    }

}
