
public class Graph {
    LinkedList<Node<String>>[] alist;
    int totalLength;

    public Graph(int length) {
        alist = new LinkedList[length];
        totalLength = length;
    }


    public void addRecord(Record record) {
        alist[record.index] = new LinkedList<Node<String>>();
        alist[record.index].addToFront(new Node<String>(record.key, null));
    }


    public void addEdge(int src, int dst) {
        alist[src].addToRear(alist[dst].get(0));
    }
    
    public boolean checkEdge(int src, int dst) {
        for (Node<String> node : alist[src]) {
            if (node.getData().equals(alist[dst].get(0).getData())) {
                return true;
            }
        }
        return false;
    }
    
    public void print() {
        for (LinkedList<Node<String>> list : alist) {
            for (Node<String> node : list) {
                System.out.print(node.getData() + " -> ");
            }
            System.out.println();
        }
    }

}
