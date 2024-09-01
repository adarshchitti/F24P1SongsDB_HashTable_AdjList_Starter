
public class Graph {
    DLList<Record>[] alist;
    int totalLength;

    public Graph(int length) {
        alist = new DLList[length];
        totalLength = length;
    }


    public void addRecord(Record record) {
        alist[record.index] = new DLList<Record>();
        alist[record.index].add(record);
    }


    public void addEdge(int src, int dst) {
        alist[src].add(alist[dst].get(0));
    }


    public boolean checkEdge(int src, int dst) {
        for (Record node : alist[src]) {
            if (node.equals(alist[dst].get(0))) {
                return true;
            }
        }
        return false;
    }


    public void print() {
        for (int i = 0; i < totalLength; i++) {
            if (alist[i] != null) {
                for (Record node : alist[i]) {
                    System.out.print(node.key + " -> ");
                }
                System.out.println();
            }
        }
    }
    
    public void removeEdge(int src, int dst) {
        alist[src].remove(alist[dst].get(0));
    }
    
    public void removeRecord(String record) {
        for (int i = 0; i < totalLength; i++) {
            if (alist[i] != null && alist[i].get(0).key.equals(record)) {
                for (Record node : alist[i]) {
                    removeEdge(node.index, i);
                }
                alist[i].remove(0);
            }
        }
    }

}
