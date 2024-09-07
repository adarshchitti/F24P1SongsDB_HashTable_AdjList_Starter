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
    private DLList<Record>[] alist; // Adjacency list representation
    private int totalLength; // Total number of nodes in the graph
    private int[] parents; // Array to keep track of node parents for union-find
    private int[] weight; // Array to keep track of the size of each tree in the
    // union-find
    private int numOfRecords;

    /**
     * Constructs a Graph with a specified length.
     *
     * @param length
     *            the number of nodes in the graph
     */
    @SuppressWarnings("unchecked")
    public Graph(int length) {
        setAlist(new DLList[length]);
        setTotalLength(length);
        setParents(new int[length]);
        weight = new int[length];
        numOfRecords = 0;
        for (int i = 0; i < length; i++) {
            getParents()[i] = -1; // Initialize as root
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
        if (record.index == getTotalLength())
            expandCapacity();
        for (int i = 0; i < getTotalLength(); i++) 
        {
            if (getAlist()[i] == null) 
            {
                getAlist()[record.index] = new DLList<>();
                getAlist()[record.index].add(record);
                numOfRecords++;
                break;
            }
        }
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
        getAlist()[src].add(getAlist()[dst].get(0));
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
        for (Record node : getAlist()[src]) {
            if (node.equals(getAlist()[dst].get(0))) {
                return true;
            }
        }
        return false;
    }


    /**
     * Prints the adjacency list representation of the graph.
     */
    public void print() {
        for (int i = 0; i < getTotalLength(); i++) {
            if (getAlist()[i] != null) {
                for (Record node : getAlist()[i]) {
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
        getAlist()[src].remove(getAlist()[dst].get(0));
        getAlist()[dst].remove(getAlist()[src].get(0));
    }


    /**
     * Removes a record from the graph by its key.
     * Updates the union-find structure accordingly.
     *
     * @param record
     *            the key of the record to be removed
     */
    public void removeRecord(String record) {
        for (int i = 0; i < getTotalLength(); i++) {
            if (getAlist()[i] != null && getAlist()[i].get(0).key.equals(
                record)) {
                int parent = find(i);
                weight[parent] -= getAlist()[i].size();
                for (int j = getAlist()[i].size() - 1; j > 0; j--) {
                    if (getAlist()[getAlist()[i].get(1).index].size() == 2) {
                        getParents()[getAlist()[i].get(1).index] = -1;
                    }
                    else {
                        weight[parent]++;
                    }
                    removeEdge(i, getAlist()[i].get(1).index);
                }
                getAlist()[i] = null;
                numOfRecords--;
                getParents()[i] = -1;
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
        if (getParents()[a] == -1) {
            return a; // Node is its own root
        }
        getParents()[a] = find(getParents()[a]); // Path compression
        return getParents()[a];
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
                getParents()[r1] = r2;
                weight[r2] += weight[r1];
            }
            else {
                getParents()[r2] = r1;
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
        for (int i = 0; i < getParents().length; i++) {
            if (getAlist()[i] != null) {
                temp = find(i);
                if (curr != temp && getParents()[i] != -1) {
                    curr = temp;
                    components++;
                }
            }
        }
        return components;
    }


    /**
     * Expands the capacity of the adjacent list to double if the previous list
     * is full
     */
    @SuppressWarnings("unchecked")
    public void expandCapacity() {
        DLList<Record>[] temp = new DLList[totalLength * 2];
        int[] tempParent = new int[totalLength * 2];
        int[] tempWeight = new int[totalLength * 2];
        for (int i = 0; i < totalLength; i++) {
            temp[i] = alist[i];
            tempParent[i] = parents[i];
            tempWeight[i] = weight[i];
        }
        totalLength = totalLength * 2;
        parents = tempParent;
        weight = tempWeight;
        alist = temp;
    }


    /**
     * Gets the array of doubly linked lists (DLList) of Records.
     * 
     * @return an array of DLLists containing Records
     */
    public DLList<Record>[] getAlist() {
        return alist;
    }


    /**
     * Sets the array of doubly linked lists (DLList) of Records.
     * 
     * @param alist
     *            the array of DLLists containing Records to set
     */
    public void setAlist(DLList<Record>[] alist) {
        this.alist = alist;
    }


    /**
     * Gets the array of parent indices.
     * 
     * @return an array representing the parent indices
     */
    public int[] getParents() {
        return parents;
    }


    /**
     * Sets the array of parent indices.
     * 
     * @param parents
     *            the array representing the parent indices to set
     */
    public void setParents(int[] parents) {
        this.parents = parents;
    }


    /**
     * Gets the total length of the structure.
     * 
     * @return the total length as an integer
     */
    public int getTotalLength() {
        return totalLength;
    }


    /**
     * Sets the total length of the structure.
     * 
     * @param totalLength
     *            the total length to set
     */
    public void setTotalLength(int totalLength) {
        this.totalLength = totalLength;
    }
}
