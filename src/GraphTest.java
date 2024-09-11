import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for the Graph class.
 * This class contains unit tests for all methods of the Graph class,
 * including edge cases and normal cases.
 * 
 * @author Brantson
 * @version 09.11.2024
 */
public class GraphTest {
    private Graph graph;
    private Record record1;
    private Record record2;
    private Record record3;

    /**
     * Sets up the test environment before each test.
     * Initializes a graph and some records to be used in the tests.
     */
    @Before
    public void setUp() {
        graph = new Graph(5);
        record1 = new Record("A", 0);
        record2 = new Record("B", 1);
        record3 = new Record("C", 2);
    }

    /**
     * Test for adding records to the graph.
     * Ensures records are correctly added.
     */
    @Test
    public void testAddRecord() {
        graph.addRecord(record1);
        assertNotNull(graph.getAlist()[0]);
        
        graph.addRecord(record2);
        assertNotNull(graph.getAlist()[1]);
    }

    /**
     * Test for adding and checking edges between nodes.
     * Ensures that edges between records are correctly added.
     */
    @Test
    public void testAddEdgeAndCheckEdge() {
        graph.addRecord(record1);
        graph.addRecord(record2);
        graph.addEdge(0, 1);
        
        assertTrue(graph.checkEdge(0, 1));
    }

    /**
     * Test for removing an edge between nodes.
     * Ensures the edge is removed correctly.
     */
    @Test
    public void testRemoveEdge() {
        graph.addRecord(record1);
        graph.addRecord(record2);
        graph.addEdge(0, 1);
        graph.removeEdge(0, 1);
        
        assertFalse(graph.checkEdge(0, 1));
    }

    /**
     * Test for removing records from the graph.
     * Ensures that records are correctly removed and updates the graph structure.
     */
    @Test
    public void testRemoveRecord() {
        graph.addRecord(record1);
        graph.addRecord(record2);
        graph.removeRecord("A");
        
        assertNull(graph.getAlist()[0]);
    }

    /**
     * Test for the union-find structure to ensure it correctly connects components.
     */
    @Test
    public void testUnionAndFind() {
        graph.addRecord(record1);
        graph.addRecord(record2);
        graph.union(0, 1);
        
        assertEquals(graph.find(0), graph.find(1));
    }

    /**
     * Test for expanding the capacity of the graph.
     * Ensures the adjacency list size is doubled when full.
     */
    @Test
    public void testExpandCapacity() {
        for (int i = 0; i < 5; i++) {
            graph.addRecord(new Record("R" + i, i));
        }
        graph.expandCapacity();
        assertEquals(10, graph.getTotalLength());
    }

    /**
     * Test for connected elements method.
     * Ensures the largest connected component is correctly returned.
     */
    @Test
    public void testConnectedElements() {
        graph.addRecord(record1);
        graph.addRecord(record2);
        graph.addRecord(record3);
        graph.addEdge(record1.index, record2.index);
        
        assertEquals(1, graph.connectedElements());
    }

    /**
     * Test for connected components method.
     * Ensures the number of connected components is correctly calculated.
     */
    @Test
    public void testConnectedComponents() {
        graph.addRecord(record1);
        graph.addRecord(record2);
        graph.addRecord(record3);
        graph.addEdge(0, 1);
        
        assertEquals(2, graph.connectedComponents());
    }
}
