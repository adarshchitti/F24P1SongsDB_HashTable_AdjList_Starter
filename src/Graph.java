
public class Graph {
    DLList<String>[] alist;
    int totalLength;

    public Graph(int length) {
        alist = new DLList[length];
        totalLength = length;
    }


    public void addRecord(Record record) {
        alist[record.index] = new DLList<String>();
        alist[record.index].add(record.key);
    }


    public void addEdge(int src, int dst) {
        alist[src].add(alist[dst].get(0));
    }


    public boolean checkEdge(int src, int dst) {
        for (String node : alist[src]) {
            if (node.equals(alist[dst].get(0))) {
                return true;
            }
        }
        return false;
    }


    public void print() {
        for (int i = 0; i < totalLength; i++) {
            if (alist[i] != null) {
                for (String node : alist[i]) {
                    System.out.print(node + " -> ");
                }
                System.out.println();
            }
        }
    }
    
    public void removeEdge(int src, int dst) {
        
    }
    
    public void removeRecord(Record record) {
        
    }

}
