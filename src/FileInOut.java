import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class FileInOut {

    /**
     * Write a program using object-oriented programming concepts,Command line arguments,
     * File Handling, and Exception Handling
     *
     * @author Connor Norris
     * @edu.uwp.cs.242.course CSCI 242 -Computer Science II
     * @edu.uwp.cs.242.section 001
     * @edu.uwp.cs.242.assignment 01
     * @bugs None.
     *
     */

    /**
     * A String constant that holds the name of the default input file.
     */
    private final String DEFAULTINFILENAME = "default_in.txt";

    /**
     * A String constant that holds the name of the default output file.
     */
    private final String DEFAULTOUTFILENAME = "default_out.txt";

    /**
     * A String variable that holds the name of the input file.
     */
    private String inFilename = "";

    /**
     * A String variable that holds the name of the output file.
     */
    private String outFilename = "";

    /**
     * The Scanner for the input file for reading.
     */
    protected Scanner inputFileScanner;

    /**
     * The PrintWriter for the output file for writing.
     */
    private PrintWriter outputFileWriter;

    /**
     * Indicates whether the input file's Scanner is open or not.
     */
    private boolean inputFileOpen = false;

    /**
     * Indicates whether the output file's PrintWriter is open or not.
     */
    private boolean outputFileOpen = false;


    /**
     * Meta-method that opens both the input file and the output file.
     */
    public void openFiles() throws FileNotFoundException {
        openInFile();
        openOutFile();
    }

    /**
     * Opens the input file for input using a Scanner.
     * <p>
     * Checks if inFilename has content, otherwise
     * DEFAULTINFILENAME  is used as the filename.
     *
     * @throws FileNotFoundException - if a file is not found
     */
    public void openInFile() throws FileNotFoundException {
        File inputFile;
        if (inFilename.length() > 0) {
            inputFile = new File(inFilename);
        } else {
            inputFile = new File(DEFAULTINFILENAME);
        }

        // Ensure Scanner is closed before assigning new object.
        if (inputFileOpen) {
            inputFileScanner.close();
        }

        try {
            inputFileScanner = new Scanner(inputFile);
        } catch (FileNotFoundException ex) {
            System.out.println("File does not exist");
        }
        inputFileOpen = true;
    }

    /**
     * Opens the output file for output using a PrintWriter.
     * <p>
     * Checks if outFilename has content, otherwise
     * DEFAULTOUTFILENAME  is used as the filename.
     *
     * @throws FileNotFoundException - if a file is not found
     */
    public void openOutFile() throws FileNotFoundException {
        File outputFile;
        if (outFilename.length() > 0) {
            outputFile = new File(outFilename);
        } else {
            outputFile = new File(DEFAULTOUTFILENAME);
        }

        // Ensure the Writer is closed before assigning new object.
        if (outputFileOpen) {
            outputFileWriter.close();
        }

        try {
            outputFileWriter = new PrintWriter(outputFile);
        } catch (FileNotFoundException ex) {
            System.out.println("File does not exist");
        }
        outputFileOpen = true;
    }

    /**
     * This constructor uses the provided input and output file names
     * to set the objects internal input and output file names.
     * The files can also be opened by passing TRUE as the pOpenFlag parameter.
     *
     * @param pIn       - String value for the name of the input file.
     * @param pOut      - String value for the name of the output file.
     * @param pOpenFlag - Flag that determined whether the files will be opened or not.
     *                  TRUE means that the files should be opened; FALSE otherwise.
     * @throws FileNotFoundException - Thrown if the input file does not exist or cannot be written
     */
    public FileInOut(String pIn, String pOut, boolean pOpenFlag) throws FileNotFoundException {

        try {
            inFilename = pIn;
            outFilename = pOut;
            if (pOpenFlag) {
                openFiles();
            }
        } catch (FileNotFoundException e) {
            System.out.println("File does not exist");
        }
    }

    // Set and Get Region

    /**
     * This method returns the name of the constant DEFAULTINFILENAME.
     *
     * @return DEFAULTFINFILENAME
     */
    public String getDEFAULTINFILENAME() {
        return DEFAULTINFILENAME;
    }

    /**
     * This method returns the name of the constant DEFAULTOUTFILENAME.
     *
     * @return DEFAULTOUTFILENAME
     */
    public String getDEFAULTOUTFILENAME() {
        return DEFAULTOUTFILENAME;
    }

    /**
     * Retrieves the input file name.
     *
     * @return String value of the input file name.
     */
    public String getInFilename() {
        return inFilename;
    }

    /**
     * Sets the input file name.
     *
     * @param inFilename - String value of the input file name.
     */
    public void setInFilename(String inFilename) {
        this.inFilename = inFilename;
    }

    /**
     * Retrieves the output file name.
     *
     * @return outFilename
     */
    public String getOutFilename() {
        return outFilename;
    }

    /**
     * Sets the output file name.
     *
     * @param outFilename - String value of the output file name.
     */
    public void setOutFilename(String outFilename) {
        this.outFilename = outFilename;
    }

    /**
     * Retrievesut Scanner.
     *      *
     *      * @return Scanner object for performing input on the opened file. the inp
     */
    public Scanner getInFile() {
        return inputFileScanner;
    }

    /**
     * Retrieves the output PrintWriter.
     *
     * @return PrintWriter object for performing output on the opened file.
     */
    public PrintWriter getOutFile() {
        return outputFileWriter;
    }

    /**
     * Meta-method that closes all the open files.
     */
    public void closeFiles() {
        closeInFile();
        closeOutFile();

    }

    /**
     * Closes the input file's Scanner.
     */
    public void closeInFile() {
        inputFileScanner.close();
        inputFileOpen = false;
    }

    /**
     * Closes the output file's PrintWriter.
     */
    public void closeOutFile() {
        outputFileWriter.close();
        outputFileOpen = false;
    }
}
