/**
 * Record class to contains either a song or artist
 * 
 * @author Brantson and Adarsh
 * @version 09.11.2024
 */
public class Record {
    String key;
    int index;

    /**
     * Constructor class for record
     * 
     * @param key
     *            string data value
     * @param index
     *            for the graph
     */
    public Record(String key, int index) {
        this.key = key;
        this.index = index;
    }

}
