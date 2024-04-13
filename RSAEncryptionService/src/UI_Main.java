public class UI_Main {
    /** UI_Main is the main function for the user interface. It uses one class, argument handler, to accept arguments.
     * If an error is thrown at any point, and it isn't handled in another class, it simply restarts the program.*/
    public static void main(String args[]){
        System.out.println("Welcome to Sophia Spaner's RSA Encryption Service!\nThis is a basic application for generating public-private key pairs, as well as encrypting & decrypting files.\nAs a basic application, I do not take any responsibility for information leaks that come as a result of the use of this application.\nPlease type --help for an abridged documentation file and an argument list");
        while(true){
            ArgumentHandler argumentHandler = new ArgumentHandler();
            try {
                argumentHandler.getArgument();
            }catch (Exception e){}
        }
    }
}
