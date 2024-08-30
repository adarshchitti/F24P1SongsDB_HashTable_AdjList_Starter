
public class Controller {
    Hash songs;
    Hash artists;
    Graph fullGraph;
    int index = 0;
    int src;
    int dst;
    
    public Controller(int length) {
        songs = new Hash(length);
        artists = new Hash(length);
        fullGraph = new Graph(length);
    }
    
    public void insert(String artist, String song) {
        if (artists.find(artist) == -1) {
            src = index;
            Record artistRecord = new Record(artist, index++);
            artists.insert(artistRecord);
            fullGraph.addRecord(artistRecord);
        }
        else {
            src = artists.find(artist);
        }
            
        if (songs.find(song) == -1) {
            dst = index;
            Record songRecord = new Record(song, index++);
            artists.insert(songRecord);
            fullGraph.addRecord(songRecord);
        }
        else {
            dst = songs.find(song);
        }
        
        if(!fullGraph.checkEdge(src, dst)) {
            fullGraph.addEdge(src, dst);
        }
        
    }
    
    public void print() {
        fullGraph.print();
    }
}
