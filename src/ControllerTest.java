import student.TestCase;

/**
 * Test Class for the Controller class
 * 
 * @author Brantson and Adarsh
 * @version 09.11.2024
 */
public class ControllerTest extends TestCase {
    private Controller toTest;

    /**
     * Called before every method
     */
    public void setUp() {
        toTest = new Controller(4);
    }


    /**
     * Tests the insert methods of the project
     */
    public void testInsert() {

        toTest.insert("Khalid", "Talk");
        toTest.insert("Ma Rainey", "Talk");
        toTest.insert("Ma", "Talk");

        // Test removing a non-existent song
        toTest.removeSong("None Existent");

        String actualOutput = systemOut().getHistory();
        String expectedOutput = "|Khalid| is added to the Artist database.\n"
            + "|Talk| is added to the Song database.\n"
            + "|Ma Rainey| is added to the Artist database.\n"
            + "Artist hash table size doubled.\n"
            + "|Ma| is added to the Artist database.\n"
            + "|None Existent| does not exist in the Song database.\n";
        assertFuzzyEquals(expectedOutput, actualOutput);

        toTest.getGraph().addEdge(1, 0);

        toTest.insert("A", "B");

        String actual = systemOut().getHistory();
        String expected = "|Khalid| is added to the Artist database.\n"
            + "|Talk| is added to the Song database.\n"
            + "|Ma Rainey| is added to the Artist database.\n"
            + "Artist hash table size doubled.\n"
            + "|Ma| is added to the Artist database.\n"
            + "|None Existent| does not exist in the Song database.\n"
            + "|A| is added to the Artist database.\n"
            + "|B| is added to the Song database.\n";
        assertFuzzyEquals(actual, expected);
    }


    /**
     * Tests the removal of songs, focusing on the false case for finding a
     * song.
     */
    public void testRemoveSong() {
        toTest.insert("Ed Sheeran", "Photograph");
        toTest.removeSong("Non Existent Song");

        String actualOutput = systemOut().getHistory();
        String expectedOutput =
            "|Ed Sheeran| is added to the Artist database.\n"
                + "|Photograph| is added to the Song database.\n"
                + "|Non Existent Song| does not exist in the Song database.\n";
        assertFuzzyEquals(expectedOutput, actualOutput);

        toTest.removeSong("Photograph");

        String actualAfterRemove = systemOut().getHistory();
        String expectedAfterRemove =
            "|Ed Sheeran| is added to the Artist database.\n"
                + "|Photograph| is added to the Song database.\n"
                + "|Non Existent Song| does not exist in the Song database.\n"
                + "|Photograph| is removed from the Song database.\n";
        assertFuzzyEquals(expectedAfterRemove, actualAfterRemove);
    }

}
