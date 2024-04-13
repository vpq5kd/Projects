
import java.io.*;
import java.math.BigInteger;
import java.util.List;
import java.util.Scanner;

/** Encryption program which can both encrypt a message and a file. This program interacts with methods in RSAEncryption's
 * logic to execute encryption. **/
public class Encrypt {
    private String message = "";
    private String argument = "0";
    private List<BigInteger> key = null;

    /** Function that runs the program, prompts users for input to figure out which service they would like to use. **/
    public void run(){
        System.out.println("Welcome to my encryption service, enter 1 to write in your own message or enter 2 to encrypt a file:");

        //continuously scans until the user presses 1 or 2
        while(!argument.equals("1")&&!argument.equals("2")) {
            Scanner scanner = new Scanner(System.in);
            String toArgument = scanner.nextLine();
            boolean NotOneOrTwo = !toArgument.equals("1") && !toArgument.equals("2");
            if (NotOneOrTwo) {
                System.out.println(toArgument + " is not 1 or 2, please try again:\n");
            }else{
                argument = toArgument;
                break;
            }
        }

        if (argument.equals("1")){
            writeInMessage(); //allows the user to write in a message from system.in for encryption
        }else if(argument.equals("2")){
            useFile(); //allows the user to specify a pre-written file for encryption.
        }
    }
    /** Method that allows the user to write in their own message. **/
    private void writeInMessage(){
        KeyHandler keyHandler = new KeyHandler(); //calls the keyHandler class, which formats the keys for use in the RSAEncryption logic class.
        key = keyHandler.handleKeys();
        RSAEncryption rsaEncryption = new RSAEncryption(key.get(0),key.get(1),key.get(2));
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your message:");
        message = scanner.nextLine(); //saves the users message
        List<Object> publicKey = rsaEncryption.getPublicKey(); //formats the constructed values from the RSAEncryption constructor for use in the RSAEncryption.ENCRYPT method.
        List<BigInteger> cipherText = rsaEncryption.ENCRYPT(message,publicKey); //encrypts the message with the public key, returns a list<BigInteger>
        System.out.println("Message encrypted, cipher text is as follows:");
        System.out.println(cipherText.toString()); //prints the cipher text of the user's message.
    }

    /** Method that allows the user to encrypt a pre-written file. **/
    private void useFile(){
        KeyHandler keyHandler = new KeyHandler();
        key = keyHandler.handleKeys();
        RSAEncryption rsaEncryption = new RSAEncryption(key.get(0),key.get(1),key.get(2));
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the name of the file you want to encrypt:");

        //Continuously runs until the user enters an accepted file name. Once they do, the entire file is turned into one string.
        String fileName = "";
        while(message.equals("")) {
            try {
                fileName = scanner.nextLine();
                message = fileToString(fileName);
            } catch (IOException e) {
                System.out.println("File not found, please try again. Make sure your file is inside the RSAEncryptionService Folder.");
                continue;
            }
        }

        //Since this method overwrites the file the user wants to encrypt, I warn the user prior, so they understand the risk of loosing their data.  "
        System.out.println("WARNING: Encryption will overwrite "+fileName+"... press enter to acknowledge your file will be overwritten or enter quit to return to safety:");
        //throws an exception (caught in UI_Main) which restarts the program.
        String toQuitOrNotToQuit = scanner.nextLine();
        if (toQuitOrNotToQuit.equals("quit")){
            throw new IllegalArgumentException();
        }
        //Uses RSAEncryption to encrypt the file
        List<Object> publicKey = rsaEncryption.getPublicKey();
        List<BigInteger> cipherText = rsaEncryption.ENCRYPT(message,publicKey);
        //Writes the cipher text to the file
        encryptFile(fileName,cipherText.toString());
        System.out.println("File encrypted, cipher text is as follows:");
        System.out.println(cipherText.toString()); //prints the cipher text
    }

    /** Method which writes the cipher text to the file using basic java file handling principles. **/
    private void encryptFile(String fileName, String cipherText){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(cipherText);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Converts a file to a string for use in the RSAEncryption class **/

    private String fileToString(String fileName) throws IOException{
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                stringBuilder.append(line).append(System.lineSeparator());
            }
        } catch (IOException e) {
            throw new IOException();
        }

        String fileContent = stringBuilder.toString();
        return fileContent;
    }

//    private void handleKeys() {
//        String hasKey = "";
//        System.out.println("Do you have a key? (yes/no)");
//        while(!hasKey.equals("yes")&&!hasKey.equals("no")){
//            Scanner scanner = new Scanner(System.in);
//            String toHasKey = scanner.nextLine();
//            boolean notYesOrNo = !toHasKey.equals("yes")&&!toHasKey.equals("no");
//            if(notYesOrNo){
//                System.out.println(toHasKey+ " is not yes or no, please try again");
//            }
//            else{
//                hasKey = toHasKey;
//                break;
//            }
//        }
//        if(hasKey.equals("yes")){
//            System.out.println("If you have already used --getKeys or included your key file in Keys as KeyFile.txt enter 1. Otherwise, enter your key file name:");
//            Scanner scanner = new Scanner(System.in);
//            String fileName = scanner.nextLine();
//            if (fileName.equals("1")){
//                KeyHandler keyHandler = new KeyHandler();
//                keyHandler.getKeysFromFile();
//                key = keyHandler.returnKeys();
//
//            }else {
//                KeyHandler keyHandler = new KeyHandler(fileName);
//                keyHandler.getKeysFromFile();
//                key = keyHandler.returnKeys();
//            }
//        }else if(hasKey.equals("no")){
//            KeyGenerator keyGenerator = new KeyGenerator();
//            KeyHandler keyHandler = new KeyHandler();
//            keyGenerator.generateKeys();
//            keyHandler.getKeysFromFile();
//            key = keyHandler.returnKeys();
//        }
//    }
}
