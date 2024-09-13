import student.TestCase;

public class ControllerTest extends TestCase 
{
    private Controller toTest;
    public void setUp()
    {
        toTest = new Controller(4);
    }
    
    public void testInsert()
    {
        toTest.insert("Khalid", "Talk");
        toTest.insert("Ma Rainey", "Talk");
        toTest.insert("Ma", "Talk");
        String actualOutput = systemOut().getHistory();
        String expectedOutput = "|Khalid| is added to the Artist database.\n"+
                                "|Talk| is added to the Song database.\n"+    
                                "|Ma Rainey| is added to the Artist database.\n"+
                                "Artist hash table size doubled.\n"+
                                "|Ma| is added to the Artist database.\n";
        assertFuzzyEquals(expectedOutput,actualOutput);
                                
                                
        
        
    }

}
