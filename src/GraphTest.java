/**
 * Test Class for Graph class
 * 
 * @author Brantson Bui
 * @version 09.05.2024
 */
public class GraphTest extends student.TestCase {
    private Graph graph;
    private Record recordA;
    private Record recordB;
    private Record recordC;

    /**
     * Sets up for the test cases
     */
    public void setUp() {
        graph = new Graph(3);
        recordA = new Record("A", 0);
        recordB = new Record("B", 1);
        recordC = new Record("C", 2);
    }


    /**
     * Tests the addRecord method to ensure records are correctly added.
     */

    public void testAddRecord() {
        graph.addRecord(recordA);
        graph.addRecord(recordB);
        assertEquals(recordA, graph.getAlist()[0].get(0));
        assertEquals(recordB, graph.getAlist()[1].get(0));
        assertEquals(2, graph.getNumOfRecords());
    }


    /**
     * Tests the addEdge method by adding an edge between two nodes.
     */

    public void testAddEdge() {
        graph.addRecord(recordA);
        graph.addRecord(recordB);
        graph.addEdge(0, 1);
        assertTrue(graph.checkEdge(0, 1));
    }


    /**
     * Tests the checkEdge method by verifying whether an edge exists between
     * nodes.
     */

    public void testCheckEdge() {
        graph.addRecord(recordA);
        graph.addRecord(recordB);
        graph.addRecord(recordC);
        graph.addEdge(0, 1);
        assertTrue(graph.checkEdge(0, 1));
        assertFalse(graph.checkEdge(1, 2));
    }


    /**
     * Tests the removeEdge method by removing an edge between nodes.
     */

    public void testRemoveEdge() {
        graph.addRecord(recordA);
        graph.addRecord(recordB);
        graph.addEdge(0, 1);
        assertTrue(graph.checkEdge(0, 1));
        graph.removeEdge(0, 1);
        assertFalse(graph.checkEdge(0, 1));
    }


    /**
     * Tests the removeRecord method to ensure records and their edges are
     * correctly removed.
     */

    public void testRemoveRecord() {
        graph.addRecord(recordA);
        graph.addRecord(recordB);
        graph.addEdge(0, 1);
        graph.removeRecord("A");
        assertNull(graph.getAlist()[0]);
        assertEquals(-1, graph.getParents()[0]);
    }


    /**
     * Tests the union and find methods by performing union operations and
     * verifying root.
     */

    public void testUnionAndFind() {
        graph.addRecord(recordA);
        graph.addRecord(recordB);
        graph.union(0, 1);
        assertEquals(graph.find(0), graph.find(1));
        
    }
    /**
     * Tests the union and find methods by performing union operations and
     * verifying root.
     */

    public void testUnion() {
        graph.addRecord(recordA);
        graph.addRecord(recordB);
        graph.addRecord(recordC);
        graph.union(1, 2);
        graph.union(0,1);
        assertEquals(graph.find(0), graph.find(1));
        
    }


    /**
     * Tests the connectedElements method by checking the largest connected
     * component size.
     */

    public void testConnectedElements() {
        assertEquals(0,graph.connectedElements());
        graph.addRecord(recordA);
        graph.addRecord(recordB);
        graph.addRecord(recordC);
        graph.addRecord(new Record("recordD",3));
        graph.addRecord(new Record("recordE",4));
        graph.addRecord(new Record("recordF",5));

        graph.union(0, 1);
        graph.union(2, 3);
        graph.union(3, 4);
        graph.union(4, 5);
        assertEquals(4, graph.connectedElements());
    }


    /**
     * Tests the connectedComponents method to ensure the number of components
     * is correct.
     */

    public void testConnectedComponents() {
        graph.addRecord(recordA);
        graph.addRecord(recordB);
        graph.addRecord(recordC);
        graph.addEdge(0, 1);
        assertEquals(2, graph.connectedComponents());
        graph.addEdge(1, 2);
        assertEquals(1, graph.connectedComponents());
        graph.removeRecord("recordA");
        assertEquals(1, graph.connectedComponents());
    }


    /**
     * Tests edge cases like empty graph and single node without connections.
     */

    public void testEdgeCases() {
        // Empty graph
        Graph emptyGraph = new Graph(0);
        assertEquals(0, emptyGraph.connectedComponents());

        // Single node without connections
        graph.addRecord(recordA);
        assertEquals(1, graph.connectedElements());
    }


    /**
     * Tests graphs expand capacity.
     */
    public void testExpandCapacity() {
        Record recordD = new Record("D", 3);
        graph.addRecord(recordA);
        graph.addRecord(recordB);
        graph.addRecord(recordC);
        graph.addRecord(recordD);
        assertEquals(6, graph.getTotalLength());

    }
}
