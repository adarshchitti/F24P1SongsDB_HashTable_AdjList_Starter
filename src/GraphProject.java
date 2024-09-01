import java.util.*;
import java.io.*;
// -------------------------------------------------------------------------
/**
 * Main for Graph project (CS3114/CS5040 Fall 2023 Project 4).
 * Usage: java GraphProject <init-hash-size> <command-file>
 *
 * @author {Your Name Here}
 * @version {Put Something Here}
 *
 */

// On my honor:
// - I have not used source code obtained from another current or
// former student, or any other unauthorized source, either
// modified or unmodified.
//
// - All source code and documentation used in my program is
// either my original work, or was derived by me from the
// source code published in the textbook for this course.
//
// - I have not discussed coding details about this project with
// anyone other than my partner (in the case of a joint
// submission), instructor, ACM/UPE tutors or the TAs assigned
// to this course. I understand that I may discuss the concepts
// of this program with other students, and that another student
// may help me debug my program so long as neither of us writes
// anything during the discussion or modifies any computer file
// during the discussion. I have violated neither the spirit nor
// letter of this restriction.

public class GraphProject {
    int length = 10;
    /**
     * @param args
     *            Command line parameters
     */
    public static void main(String[] args) {
        // This is the main file for the program.
    }


    public void beginParsingByLine(String filename) {
        try {
            Controller control = new Controller(length);
            Scanner sc = new Scanner(new File(filename));
            Scanner scancmd;// Declare two scanners one to read the file and one
                            // to read the text pulled from the file
            while (sc.hasNextLine()) {// While we have text to read
                String line = sc.nextLine();// Get our next line
                scancmd = new Scanner(line);// Create a scanner from this line
                String cmd = scancmd.next();// Get the first word (the command)
                                            // on each line
                String type;
                switch (cmd) {
                    case "insert":// In the case of insert change our delimiter
                                  // from white space to <SEP>
                        scancmd.useDelimiter("<SEP>");
                        String artist = scancmd.next();// Get the artist since
                                                       // it is before <SEP>
                        String song = scancmd.next();// Get the song title that
                                                     // follows <SEP>
                        control.insert(artist, song);
                        break;
                    case "remove":
                        type = scancmd.next();// Get the mode of deletion
                                              // artist/song
                        String token = scancmd.nextLine();
                        // Since both artist titles and song titles have spaces
                        // get the rest of the line for the song/artist name
                        switch (type) {
                            case "artist":
                                System.out.println("Artist Delete: " + token);
                                control.remove(token);
                                break;
                            case "song":
                                System.out.println("Song Delete: " + token);
                                break;
                            default:// Error bad token
                                System.out.println("Error bad remove type "
                                    + type);
                                break;
                        }
                        break;
                    case "print":// Print command
                        type = scancmd.next();// get the type of print command
                        switch (type) {
                            case "artist":
                                System.out.println("Print artist mode");
                                break;
                            case "song":
                                System.out.println("Print song mode");
                                break;
                            case "graph":
                                control.print();
                                break;
                            default:
                                System.out.println("Error bad print type"
                                    + type);
                                break;
                        }
                        break;
                    default:
                        System.out.println("Unrecognized input");
                        break;
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
