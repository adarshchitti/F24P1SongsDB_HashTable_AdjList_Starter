//Represents a node in the graph
public class Node {
    private String data;
    private int index;

    public Node(String data, int index) {
        this.data = data;
        this.index = index;
    }

    public String getData() {
        return data;
    }

    public int getIndex() {
        return index;
    }

}
