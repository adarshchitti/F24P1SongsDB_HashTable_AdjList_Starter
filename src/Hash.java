/**
 * Hash table class
 *
 * @author <Put Something Here>
 * @version <Put Something Here>
 */

public class Hash {

    private Record[] allRecords;
    private int numberOfRecords;
    int totalSize;
    public Hash(int size)
    {
        this.allRecords = new Record[size];
        this.numberOfRecords = 0;
        this.totalSize = size;
    }
    public void insert(Record record)
    {
        int index = h(record.key,totalSize);
        int count=0;
        while (allRecords[index] != null && !allRecords[index].key.equals(record.key)) {
            count++;
            index = (index + count * count) % totalSize; 
        }
        allRecords[index] = record;
        numberOfRecords++;
        if(numberOfRecords>(totalSize/2))
        {
            expandCapacity();            
        }
    }
    private void expandCapacity() 
    {
        Record[] temp = allRecords;           
        totalSize = totalSize * 2;            
        allRecords = new Record[totalSize];   
        numberOfRecords = 0;                  
        
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
    
}
