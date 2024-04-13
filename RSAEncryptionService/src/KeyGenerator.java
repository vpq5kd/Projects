import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/** Class that allows the user to generate keys. This class writes the keys to two separate files for user
 * privacy. More information can be found with --help **/
public class KeyGenerator {
    private String KeyFile = "Keys\\KeyFile.txt";
    private String PersonalKeyFile = "Keys\\PersonalKeyFile.txt";
    private RSAEncryption rsaEncyrption = new RSAEncryption(); //uses the RSAEncryption class to generate keys.

    /** Methods to generate keys, one of which also shows the mathematical values used for calculation. **/
    public void generateKeys(){
        writeKeyFile();
        writePersonalKeyFile();
        System.out.println("Keys have been generated and placed in the Keys folder.");
    }
    public void generateKeys_seeValues(){
        writeKeyFile();
        writePersonalKeyFile();
        printValues();
        System.out.println("\nKeys have been generated and placed in the Keys folder.");

    }
    private void printValues(){
        System.out.println("p = "+rsaEncyrption.p+"\n"+"q = "+rsaEncyrption.q+"\n"+"e = "+rsaEncyrption.e+"\n"+"d = "+rsaEncyrption.d+"\n"+"n = "+rsaEncyrption.n);
    }

    private void writePersonalKeyFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PersonalKeyFile))) {
            writer.write("Public Key:\n");
            writer.write("your e value (public key) is "+ rsaEncyrption.e.toString()+" and your modulus is " +rsaEncyrption.n.toString()+"\n");
            writer.write("Private Key:\n");
            writer.write("your d value (private key) is "+rsaEncyrption.d.toString()+" and your modulus is " +rsaEncyrption.n.toString()+"\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeKeyFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(KeyFile))) {
            writer.write(rsaEncyrption.e.toString()+" "+rsaEncyrption.n.toString()+"\n");
            writer.write(rsaEncyrption.d.toString()+" "+rsaEncyrption.n.toString()+"\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
