import java.math.BigInteger;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String args[]){
        RSAEncryption rsaEncyrption = new RSAEncryption();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Message: ");
        String message = scanner.nextLine();
        List<Object> publicKey = rsaEncyrption.getPublicKey();
        List<BigInteger> cipherText = rsaEncyrption.ENCRYPT(message,publicKey);
        System.out.println("CipherText (as a List<BigInteger>): "+ cipherText.toString());
        List<Object> privateKey = rsaEncyrption.getPrivateKey();
        String decryptedMessage = rsaEncyrption.DECRYPT(cipherText,privateKey);
        System.out.println("decryptedMessage: "+ decryptedMessage);
    }
}
