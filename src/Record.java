/**
 * Record class to contains either a song or artist
 * 
 * @author Brantson and Adarsh
 * @version 09.11.2024
 */
public class Record {
    private String key;
    private int index;

    /**
     * Constructor class for record
     * 
     * @param key
     *            string data value
     * @param index
     *            for the graph
     */
    public Record(String key, int index) {
        this.setKey(key);
        this.setIndex(index);
    }


    /**
     * Sets the new index
     * 
     * @param index2
     *            - index to be set
     */
    public void setIndex(int index2) {
        this.index = index2;

    }


    /**
     * Sets the new key
     * 
     * @param key2
     *            - key to be set
     */
    public void setKey(String key2) {
        this.key = key2;

    }


    /**
     * Returns the data in the record
     * 
     * @return record data
     */
    public String getKey() {
        return key;
    }


    /**
     * Returns the graph index of the record
     * 
     * @return graph index
     */
    public int getIndex() {
        return index;
    }

}
