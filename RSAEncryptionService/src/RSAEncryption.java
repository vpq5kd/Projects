import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/** Class that handles the encryption logic. This class is the "backend" for the rest of this program's "frontend" **/
public class RSAEncryption {
    public int p;
    public int q;
    public BigInteger e;
    public BigInteger d;
    public BigInteger t;
    public BigInteger n;

    /** First constructor generates the values for you from scratch. **/
    public RSAEncryption(){
        p = generatePrime();
        q = generatePrime();
        n = calculateN(p,q);
        t = calculateT(p,q);
        e = calculateE(t);
        d = calculateD(e,t);
//        System.out.println("p = " +p+"\nq = " +q+"\ne = "+e+"\nd = "+d);
    }
    public RSAEncryption(BigInteger d, BigInteger e, BigInteger n){
        this.d = d;
        this.e = e;
        this.n = n;
    }
    public List<BigInteger> ENCRYPT(String message, List<Object> publicKey){
        BigInteger e = (BigInteger)publicKey.get(0);
        BigInteger n = (BigInteger)publicKey.get(1);
        List<BigInteger> messageArray = convertMessage(message);
        List<BigInteger> encryptedMessageArray = new ArrayList<>();
        for(int i = 0; i <messageArray.size(); i++){
            BigInteger temp = messageArray.get(i);
            BigInteger encyrptedChar = temp.modPow(e,n);
            encryptedMessageArray.add(encyrptedChar);

        }
        return encryptedMessageArray;
    }
    public String DECRYPT(List<BigInteger> cipherText, List<Object> privateKey){
        BigInteger d = (BigInteger) privateKey.get(0);
        BigInteger n = (BigInteger) privateKey.get(1);
        List<BigInteger> decryptedMessageArray = new ArrayList<>();
        for (int i = 0; i<cipherText.size();i++){
            BigInteger temp = cipherText.get(i);
            BigInteger decryptedMessage = temp.modPow(d,n);
            decryptedMessageArray.add(decryptedMessage);

        }
        String decryptedMessage = convertNumber(decryptedMessageArray);
        return decryptedMessage;
    }
    public List<Object> getPublicKey(){
        List<Object> publicKey = new ArrayList<>();
        publicKey.add(e);
        publicKey.add(n);
        return publicKey;
    }
    public List<Object> getPrivateKey(){
        List<Object> privateKey = new ArrayList<>();
        privateKey.add(d);
        privateKey.add(n);
        return privateKey;
    }


    /** generatePrime(), checkPrime(), and getFactors() are Three functions which work in conjunction
     *  to generate prime numbers with 100% accuracy. **/

    //generatePrime() -> generates a random number, checks if it's prime, generates a new one if it isn't.
    private  int generatePrime(){
        Random random = new Random();
        Boolean primeTest = false;
        int possible = 0;
        while(!primeTest &&possible!=1) {
            possible = random.nextInt(1,1000000); //maximum value of 1 million.
            primeTest = checkPrime(possible);
        }
        return possible;
    }

    //checkPrime() -> takes in an int, gets its factors, and checks to make sure they are only 1 and itself.
    private  boolean checkPrime(int possible){
        List<Integer> factors = getFactors(possible);
        int iteslf = possible;
        if (factors.contains(iteslf) && factors.contains(1) && factors.size()==2){
            return true;
        }
        return false;
    }

    //getFactors() -> goes through each integer from 1 to the sqrt (everything else is found using multiplication logic), and checks to see if they are a factor.

    private List<Integer> getFactors(int possible) {
        List<Integer> factors = new ArrayList<>();
        for (int i = 1; i <= Math.sqrt(possible); i++){
            if (possible % i == 0){
                factors.add(i);
                if (i != possible / i ){ //Gets whatever the factor needs to be multiplied with to create the number.
                    factors.add(possible /i);
                }
            }
        }
        return factors; //returns the factors.
    }
    private List<BigInteger> getBigFactors(BigInteger possible){
        List<BigInteger> factors = new ArrayList<>();
        for(BigInteger i = BigInteger.ONE; i.compareTo(possible.sqrt()) <= 0;i = i.add(BigInteger.ONE)){
            if (possible.mod(i).equals(BigInteger.ZERO)){
                factors.add(i);
                if (!i.equals(possible.divide(i))){
                    factors.add(possible.divide(i));
                }
            }
        }
        return factors;
    }
    /** Uses a while loop to find coprimes, calculates E **/

    private BigInteger calculateE(BigInteger t){
        Random random = new Random();
        BigInteger possible = BigInteger.ZERO;
        boolean testCoPrime = false;
        while(!testCoPrime) {
            boolean innerTrigger = false;
            possible = new BigInteger(t.bitLength(), random);
            if (possible.compareTo(t)>0){
                continue;
            }
            if (possible.compareTo(BigInteger.ZERO)<0){
                possible = possible.negate();
            }
            List<BigInteger> tFactors = getBigFactors(t);
            List<BigInteger> possibleFactors = getBigFactors(possible);
            for (BigInteger eachT : tFactors) {
                for (BigInteger eachPos : possibleFactors) {
                    if (!eachT.equals(BigInteger.ONE) && !eachPos.equals(BigInteger.ONE)) {
                        if (eachT.equals(eachPos)) {
                            innerTrigger = true;
                            break;
                        }
                    }
                }
                if (innerTrigger){
                    break;
                }
            }
            if(!innerTrigger) {
                testCoPrime = true;
            }
        }
        return possible;

    }
    /** Takes the int values p and q, turns them into BigIntegers, and then multiplies them. **/
    private BigInteger calculateN(int p, int q) {
        BigInteger n = (BigInteger.valueOf(p)).multiply(BigInteger.valueOf(q));
        return n;
    }
    /** Calculates the totient value **/
    private BigInteger calculateT(int p, int q) {
        BigInteger t = BigInteger.valueOf((p - 1)).multiply(BigInteger.valueOf(q-1));
        return t;
    }

    /** Calculates D using the BigInteger Mod Inverse function. */

    private BigInteger calculateD(BigInteger e, BigInteger t){
        d = e.modInverse(t);
        return d;
    }

    /** Turns the cipher text into a BigInteger array. **/
    private List<BigInteger> convertMessage(String message){
        List<BigInteger> messageArray = new ArrayList<>();
        for (char each : message.toCharArray()){
            BigInteger temp = BigInteger.valueOf(each);
            messageArray.add(temp);

        }
        return messageArray;
    }

    /** Takes the decrypted BigInteger list and turns it back into a string. **/
    private String convertNumber(List<BigInteger> decryptedMessageArray){
        char [] decryptedMessage = new char[decryptedMessageArray.size()];
        for(int i = 0; i < decryptedMessageArray.size();i++){
            decryptedMessage[i] = (char) (decryptedMessageArray.get(i).intValue());
        }
        String decryptedMessageString = new String(decryptedMessage);
        return decryptedMessageString;
    }





}
