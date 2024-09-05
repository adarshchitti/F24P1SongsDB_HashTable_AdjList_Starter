
public class Controller {
    public Hash songs;
    public Hash artists;
    public Graph fullGraph;
    private int index = 0;
    private int src;
    private int dst;
    private int length;

    public Controller(int length) {
        songs = new Hash(length);
        artists = new Hash(length);
        fullGraph = new Graph(length);
        this.length = length;
    }


    public void insert(String artist, String song) {
        if (artists.find(artist) == -1) {
            src = index;
            Record artistRecord = new Record(artist, index++);
            artists.insert(artistRecord);
            fullGraph.addRecord(artistRecord);
            if (artists.getTotalSize() == (length * 2)) {
                System.out.println("Artist hash table size doubled");
                length = artists.getTotalSize();
            }
            System.out.println("|" + artist
                + "| is added to the Artist Database");
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
            System.out.println("|" + song + "| is added to the Song Database");
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


    public void printGraph() {

        System.out.println("There are " + fullGraph.connectedComponents()
            + " connected components");
        System.out.println("The largest connected component has " + fullGraph
            .connectedElements() + " elements");
        /*
         * fullGraph.print();
         * System.out.println(fullGraph.connectedElements());
         * System.out.println(fullGraph.connectedComponents());
         */
    }


    public void printArtist() {
        System.out.print(artists.print());
        System.out.println("total artists: " + artists.getNumberOfRecords());

    }


    public void printSong() {
        System.out.print(songs.print());
        System.out.println("total songs: " + songs.getNumberOfRecords());

    }


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
