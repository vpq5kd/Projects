import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/** Class which interacts with RSAEncryption to decrypt an inputted cipher text or file. **/

public class Decrypt {
    private String message = "";
    private String argument = "0";
    private List<BigInteger> key;

    /** Allows the user to choose how they want to run the Decrypt program, then runs the program. **/
    public void run() {
        System.out.println("Welcome to my decryption service! As a general note, this service only accepts encrypted files/messages in the format [number,number,etc]. Anything else will be rejected.");
        System.out.println("\nEnter 1 to write in your own cipher text or enter 2 to decrypt a file:");
        while (!argument.equals("1") && !argument.equals("2")) {
            Scanner scanner = new Scanner(System.in);
            String toArgument = scanner.nextLine();
            boolean NotOneOrTwo = !toArgument.equals("1") && !toArgument.equals("2");
            if (NotOneOrTwo) {
                System.out.println(toArgument + " is not 1 or 2, please try again:\n");
            } else {
                argument = toArgument;
                break;
            }
        }
        if (argument.equals("1")) {
            writeInMessage();
        } else if (argument.equals("2")) {
            useFile();
        }
    }

    /** Method that handles a user inputted encrypted file. **/
    private void useFile() {
        //exact same as Encrypt, see that method for explanation.
        KeyHandler keyHandler = new KeyHandler();
        key = keyHandler.handleKeys();
        RSAEncryption rsaEncryption = new RSAEncryption(key.get(0),key.get(1),key.get(2));
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the name of the file you want to decrypt:");
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

        List<BigInteger> cipherText = stringToBigInt(); //Turns the string back into a BigInteger.
        List<Object> privateKey = rsaEncryption.getPrivateKey();
        String decryptedMessage = rsaEncryption.DECRYPT(cipherText,privateKey);
        decryptFile(fileName,decryptedMessage); //Same as encryptFile in Encrypt.java, overwrites the file with the decrypted message.
        System.out.println("File Decrypted. Decrypted Message:");
        System.out.println(decryptedMessage);

    }
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
    private void decryptFile(String fileName, String message){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(message);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Method that decrypts a user inputted cipher text. This uses the exact same logic as Encrypt.writeInMessage() except
     * it calls RSAEncryption.DECRYPT instead of RSAEncryption.ENCRYPT . **/

    private void writeInMessage() {
        KeyHandler keyHandler = new KeyHandler();
        key = keyHandler.handleKeys();
        RSAEncryption rsaEncryption = new RSAEncryption(key.get(0),key.get(1),key.get(2));
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your cipher text:");
        message = scanner.nextLine();
        List<BigInteger> cipherText = stringToBigInt();
        List<Object> privateKey = rsaEncryption.getPrivateKey();
        try {
            String decryptedMessage = rsaEncryption.DECRYPT(cipherText, privateKey);
            System.out.println("Cipher text decrypted, message is as follows:");
            System.out.println(decryptedMessage);
        }catch (Exception e){
            System.out.println("Something went wrong with your decryption, most likely the way you formatted your cipher text was not proper for this application.\n program restarting...");
            throw new IllegalArgumentException();
        }
    }

    /** Method that takes in a string, checks its format, and the returns a list<BigInteger> to be used in RSAEncryption.DECRYPT . **/

    private List<BigInteger> stringToBigInt() {
        int lastIndex = message.lastIndexOf(']');
        String elementsStr = message.substring(1, lastIndex);
        String[] elements = elementsStr.split(",\\s*");

        List<BigInteger> cipherText = new ArrayList<>();

        for (String element : elements) {
            cipherText.add(new BigInteger(element.trim()));
        }

        return cipherText;
//        String[] elements = message.substring(1, message.length() - 3).split(", ");
//        List<BigInteger> cipherText = new ArrayList<>();
//        for (String element : elements) {
//            cipherText.add(new BigInteger(element));
//        }
//        return cipherText;
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
