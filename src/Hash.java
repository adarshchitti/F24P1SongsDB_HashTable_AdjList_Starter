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
    
    public Hash(int size) {
        this.allRecords = new Record[size];
        this.setNumberOfRecords(0);
        this.setTotalSize(size);
        this.setNumTombstone(0);
    }

    public int find(String value) {
        
        int index = h(value,getTotalSize());
        int i = 0;
        while(allRecords[index]!=null)
        {
            if(allRecords[index].key.equals(value))
            {
                return index;
            }
            i++;
            index = (index + (i*i))%getTotalSize();
        }
        return -1;
        
        /*
        for (int i = 0; i < totalSize; i++) {
            if (allRecords[i] != null) {
                if (allRecords[i].key.equals(value)) {
                    return allRecords[i].index;
                }
            }
        }
        return -1;
        */
    }
    public boolean remove(String value)
    {
        int index = find(value);
        if(index==-1)
        {
            return false;
        }
        allRecords[index].key = "TOMBSTONE";
        numTombstone++;
        return true;
        
    }
    
    public void insert(Record record) {
        if(find(record.key) == -1)
        {
            int index = h(record.key, getTotalSize());
            int count = 0;
            while (allRecords[index] != null)
            {
                count++;
                index = (index + count * count) % getTotalSize();
            }
            allRecords[index] = record;
            numberOfRecords++;
        }
        if (numberOfRecords > (totalSize / 2)) {
            expandCapacity();
        }
    }


    private void expandCapacity() {
        Record[] temp = allRecords;
        setTotalSize(getTotalSize() * 2);
        allRecords = new Record[getTotalSize()];
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

}
