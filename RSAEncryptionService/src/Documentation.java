import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
/** Documentation is used to call files in the documentation folder to help users understand how to use
 * the program. */
public class Documentation {

    private String documentation = "Documentation\\RSAHelp.txt"; //--help
    private String KeyFileDocumentation = "Documentation\\CreatingKeyFile.txt"; //--KeyFileFormat

    /** two methods which are responsible for getting the requested documentation from the user **/
    public void getDocumentation(){
        readLines(documentation);
    }
    public void getKeyFileDocumentation(){
        readLines(KeyFileDocumentation);
    }
    /** Java file reader method, prints the lines of the files. **/
    private void readLines(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
