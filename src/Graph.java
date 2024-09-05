
public class Graph {
    DLList<Record>[] alist;
    int totalLength;
    int[] parents;
    int[] weight;

    @SuppressWarnings("unchecked")
    public Graph(int length) {
        alist = new DLList[length];
        totalLength = length;
        parents = new int[length];
        weight = new int[length];
        for (int i = 0; i < length; i++) {
            parents[i] = -1;
            weight[i] = 1;
        }
    }


    public void addRecord(Record record) {
        alist[record.index] = new DLList<Record>();
        alist[record.index].add(record);
    }


    public void addEdge(int src, int dst) {
        alist[src].add(alist[dst].get(0));
        union(src, dst);
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
        alist[dst].remove(alist[src].get(0));
    }


    public void removeRecord(String record) {
        for (int i = 0; i < totalLength; i++) {
            if (alist[i] != null && alist[i].get(0).key.equals(record)) {
                int parent = find(i);
                weight[parent] = weight[parent] - alist[i].size();
                for (int j = alist[i].size() - 1; j > 0; j--) {
                    if (alist[alist[i].get(1).index].size() == 2) {
                        parents[alist[i].get(1).index] = -1;
                    }
                    else {
                        weight[parent]++;
                    }
                    removeEdge(i, alist[i].get(1).index);
                }
                alist[i].remove(0);
                parents[i] = -1;
                break;
            }
        }
    }


    public int find(int a) {
        if (parents[a] == -1)
            return a; // At root
        parents[a] = find(parents[a]);
        return parents[a];
    }


    public void union(int a, int b) {
        int r1 = find(a);
        int r2 = find(b);

        if (r1 != r2) {
            if (weight[r2] > weight[r1]) {
                parents[r1] = r2;
                weight[r2] += weight[r1];
            }
            else {
                parents[r2] = r1;
                weight[r1] += weight[r2];
            }
        }

    }


    public int connectedElements() {
        int max = weight[0];
        for (int i = 1; i < weight.length; i++) {
            if (weight[i] > max) {
                max = weight[i];
            }
        }

        if(max == 1)
        {
            return 0;
        }
        return max;
    }


    public int connectedComponents() {
        int temp;
        int curr = -1;
        int components = 0;
        for (int i = 0; i < parents.length; i++) {
            if (alist[i] != null) {
                temp = find(i);
                if (curr != temp && parents[i] != -1) {
                    curr = temp;
                    components++;
                }
            }
        }
        return components;
    }

}
