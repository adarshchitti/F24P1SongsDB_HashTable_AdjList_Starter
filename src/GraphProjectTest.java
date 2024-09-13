import student.TestCase;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * This class was designed to test the GraphProject
 *
 * @author Brantson and Adarsh
 * @version 09.11.2024
 */
public class GraphProjectTest extends TestCase {
    // ----------------------------------------------------------
    /**
     * Read contents of a file into a string
     * 
     * @param path
     *            File name
     * @return the string
     * @throws IOException
     */
    static String readFile(String path) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded);
    }


    /**
     * Set up the tests that follow.
     */
    public void setUp() { // Nothing needed yet

    }


    /**
     * This method is simply to get code coverage of the class declaration.
     */
    public void testQInit() {
        GraphProject it = new GraphProject();
        assertNotNull(it);
    }


    /**
     * Example 2: This method runs on a command sample IO file
     * You will write similar test cases
     * using different text files
     *
     * @throws Exception
     */
    public void testSampleIO() throws Exception {
        // Setting up all the parameters
        String[] args = new String[2];
        args[0] = "10";
        args[1] = "solutionTestData/P1_sampleInput.txt";

        // Invoke main method of our Graph Project
        GraphProject.main(args);

        // Actual output from your System console
        String actualOutput = systemOut().getHistory();

        // Expected output from file
        String expectedOutput = readFile(
            "solutionTestData/P1_sampleOutput.txt");

        // Compare the two outputs
        assertFuzzyEquals(expectedOutput, actualOutput);

    }
    
    /**
     * Example 2: This method runs on a command sample IO file
     * You will write similar test cases
     * using different text files
     *
     * @throws Exception
     */
    public void testBadInputTypes() throws Exception {
        // Setting up all the parameters
        String[] args = new String[2];
        args[0] = "10";
        args[1] = "solutionTestData/input";

        // Invoke main method of our Graph Project
        GraphProject.main(args);

        // Actual output from your System console
        String actualOutput = systemOut().getHistory();

        // Expected output from file
        String expectedOutput = readFile(
            "solutionTestData/output");

        // Compare the two outputs
        assertFuzzyEquals(expectedOutput, actualOutput);
        Exception myEx = new Exception();
        try
        {
            args[1]="noneExistent";
            GraphProject.main(args);
        }
        catch(Exception ex)
        {
            ex.printStackTrace(System.out);
            myEx = ex;
        }
        
        assertNotNull(myEx);
    }
    
    /**
     * Tests the case where there are no more lines
     */
    public void testNoMoreLines() throws Exception {

        GraphProject.beginParsingByLine("solutionTestData/empty");

        String actualOutput = systemOut().getHistory();
        String expectedOutput = "";  

        assertFuzzyEquals(expectedOutput, actualOutput);

    }
    
}
