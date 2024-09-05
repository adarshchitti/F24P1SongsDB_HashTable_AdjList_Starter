/**
 * The Controller class manages a graph structure representing artists and
 * songs.
 * It supports inserting artists and songs, printing the graph, and removing
 * artists or songs.
 * 
 * @author Brantson and Adarsh
 * @version 09.05.2024
 */
public class Controller {
    private Hash songs;
    private Hash artists;
    private Graph fullGraph;
    private int index = 0;
    private int src;
    private int dst;
    private int length;

    /**
     * Constructs a Controller with the given length for hash tables and graph.
     *
     * @param length
     *            the initial size of the hash tables and the graph
     */
    public Controller(int length) {
        songs = new Hash(length);
        artists = new Hash(length);
        fullGraph = new Graph(length);
        this.length = length;
    }


    /**
     * Inserts an artist and song into the database and updates the graph.
     * If the artist or song is already present, the method will handle
     * duplicates.
     * Also checks and doubles the size of the hash table if necessary.
     *
     * @param artist
     *            the name of the artist to insert
     * @param song
     *            the name of the song to insert
     */
    public void insert(String artist, String song) {
        if (artists.find(artist) == -1) {
            src = index;
            Record artistRecord = new Record(artist, index++);
            artists.insert(artistRecord);
            fullGraph.addRecord(artistRecord);
            if (artists.getTotalSize() == (length * 2)) {
                System.out.println("Artist hash table size doubled.");
                
                length = artists.getTotalSize();

            }
            System.out.println("|" + artist
                + "| is added to the Artist database");
        }
        else {
            src = artists.find(artist);
        }

        if (songs.find(song) == -1) {
            dst = index;
            Record songRecord = new Record(song, index++);
            songs.insert(songRecord);
            fullGraph.addRecord(songRecord);
            if (songs.getTotalSize() == (length * 2)) {
                System.out.println("Song hash table size doubled");
                length = songs.getTotalSize();
            }
            System.out.println("|" + song + "| is added to the Song database.");
        }
        else {
            dst = songs.find(song);
        }

        if (!fullGraph.checkEdge(src, dst)) {
            fullGraph.addEdge(src, dst);
        }
        else {
            System.out.println("|" + artist + "<SEP>" + song
                + "| duplicates a record already in the database.");
        }

        if (!fullGraph.checkEdge(dst, src)) {
            fullGraph.addEdge(dst, src);
        }

    }


    /**
     * Prints the current state of the graph, including the number of connected
     * components
     * and the size of the largest connected component.
     */
    public void printGraph() {
        System.out.println("There are " + fullGraph.connectedComponents()
            + " connected components");
        System.out.println("The largest connected component has " + fullGraph
            .connectedElements() + " elements");
    }


    /**
     * Prints the list of all artists in the database and the total number of
     * artists.
     */
    public void printArtist() {
        System.out.print(artists.print());
        System.out.println("total artists: " + artists.getNumberOfRecords());
    }


    /**
     * Prints the list of all songs in the database and the total number of
     * songs.
     */
    public void printSong() {
        System.out.print(songs.print());
        System.out.println("total songs: " + songs.getNumberOfRecords());
    }


    /**
     * Removes an artist from the database and the graph.
     * If the artist does not exist, an appropriate message is printed.
     *
     * @param value
     *            the name of the artist to remove
     */
    public void removeArtist(String value) {
        if (artists.find(value) != -1) {
            fullGraph.removeRecord(value);
            artists.remove(value);
            System.out.println("|" + value
                + "| is removed from the Artist database.");
        }
        else {
            System.out.println("|" + value
                + "| does not exist in the Artist database.");
        }
    }


    /**
     * Removes a song from the database and the graph.
     * If the song does not exist, an appropriate message is printed.
     *
     * @param value
     *            the name of the song to remove
     */
    public void removeSong(String value) {
        if (songs.find(value) != -1) {
            fullGraph.removeRecord(value);
            songs.remove(value);
            System.out.println("|" + value
                + "| is removed from the Song database.");
        }
        else {
            System.out.println("|" + value
                + "| does not exist in the Song database.");
        }
    }
}
