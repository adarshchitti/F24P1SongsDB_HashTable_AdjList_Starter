/**
 * The Graph class represents a graph structure using an adjacency list
 * implemented as an array of doubly linked lists (DLList). It includes methods
 * for adding and removing records and edges, finding connected components,
 * and performing union-find operations.
 * 
 * @author Brantson Bui and Adarsh Chittimori
 * @version 09.05.2024
 */
public class Graph {
    DLList<Record>[] alist; // Adjacency list representation
    int totalLength; // Total number of nodes in the graph
    int[] parents; // Array to keep track of node parents for union-find
    int[] weight; // Array to keep track of the size of each tree in the
                  // union-find

    /**
     * Constructs a Graph with a specified length.
     *
     * @param length
     *            the number of nodes in the graph
     */
    @SuppressWarnings("unchecked")
    public Graph(int length) {
        alist = new DLList[length];
        totalLength = length;
        parents = new int[length];
        weight = new int[length];
        for (int i = 0; i < length; i++) {
            parents[i] = -1; // Initialize as root
            weight[i] = 1; // Initialize each node with weight 1
        }
    }


    /**
     * Adds a record to the graph at the specified index.
     *
     * @param record
     *            the record to be added
     */
    public void addRecord(Record record) {
        alist[record.index] = new DLList<>();
        alist[record.index].add(record);
    }


    /**
     * Adds an edge between two nodes in the graph.
     * Also performs a union operation on the two nodes.
     *
     * @param src
     *            the source node index
     * @param dst
     *            the destination node index
     */
    public void addEdge(int src, int dst) {
        alist[src].add(alist[dst].get(0));
        union(src, dst);
    }


    /**
     * Checks if an edge exists between two nodes.
     *
     * @param src
     *            the source node index
     * @param dst
     *            the destination node index
     * @return true if an edge exists between src and dst, false otherwise
     */
    public boolean checkEdge(int src, int dst) {
        for (Record node : alist[src]) {
            if (node.equals(alist[dst].get(0))) {
                return true;
            }
        }
        return false;
    }


    /**
     * Prints the adjacency list representation of the graph.
     */
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


    /**
     * Removes an edge between two nodes in the graph.
     *
     * @param src
     *            the source node index
     * @param dst
     *            the destination node index
     */
    public void removeEdge(int src, int dst) {
        alist[src].remove(alist[dst].get(0));
        alist[dst].remove(alist[src].get(0));
    }


    /**
     * Removes a record from the graph by its key.
     * Updates the union-find structure accordingly.
     *
     * @param record
     *            the key of the record to be removed
     */
    public void removeRecord(String record) {
        for (int i = 0; i < totalLength; i++) {
            if (alist[i] != null && alist[i].get(0).key.equals(record)) {
                int parent = find(i);
                weight[parent] -= alist[i].size();
                for (int j = alist[i].size() - 1; j > 0; j--) {
                    if (alist[alist[i].get(1).index].size() == 2) {
                        parents[alist[i].get(1).index] = -1;
                    }
                    else {
                        weight[parent]++;
                    }
                    removeEdge(i, alist[i].get(1).index);
                }
                alist[i] = null;
                parents[i] = -1;
                break;
            }
        }
    }


    /**
     * Finds the root of a node using the union-find structure.
     *
     * @param a
     *            the node index
     * @return the root of the node
     */
    public int find(int a) {
        if (parents[a] == -1) {
            return a; // Node is its own root
        }
        parents[a] = find(parents[a]); // Path compression
        return parents[a];
    }


    /**
     * Performs the union operation on two nodes in the graph.
     * Connects the nodes by size, ensuring smaller trees are attached to larger
     * ones.
     *
     * @param a
     *            the first node index
     * @param b
     *            the second node index
     */
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


    /**
     * Returns the size of the largest connected component in the graph.
     *
     * @return the size of the largest connected component, or 0 if all
     *         components are size 1
     */
    public int connectedElements() {
        int max = weight[0];
        for (int i = 1; i < weight.length; i++) {
            if (weight[i] > max) {
                max = weight[i];
            }
        }
        return max == 1 ? 0 : max;
    }


    /**
     * Returns the number of connected components in the graph.
     *
     * @return the number of connected components
     */
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
