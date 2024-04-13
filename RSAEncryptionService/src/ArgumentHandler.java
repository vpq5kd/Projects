import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
/** Argument handler gets the argument, verifies if it's a real argument, and then calls the appropriate function.*/
public class ArgumentHandler {
    private String argument; //what the user inputs
    private List<String> argumentList = new ArrayList<>(); //list of accepted arguments
    public ArgumentHandler(){
        argumentList.add("--help");
        argumentList.add("--getKeys");
        argumentList.add("--KeyFileFormat");
        argumentList.add("--Encrypt");
        argumentList.add("--Decrypt");
        argumentList.add("--getKeys --seeValues");
    }
    /** getArgument takes in the argument from system.in*/
    public void getArgument(){
        TagText(); //SRSA>>
        Scanner scanner = new Scanner(System.in);
        argument = scanner.nextLine(); //user input
        if (!argumentList.contains(argument)){ //If the argument isn't valid, prints a help statement & restarts the program.
            System.out.println(argument + " is not a valid argument, please use --help to see a list of arguments");
            throw new IllegalArgumentException();
        }
        System.out.println(); //prints a new line for readability
        executeArgument();
    }
    /** method responsible for executing arguments */
    private void executeArgument(){
        boolean help = argument.equals(argumentList.get(0));
        boolean getKeys = argument.equals(argumentList.get(1));
        boolean creatingKeyFile = argument.equals(argumentList.get(2));
        boolean encryptStart = argument.equals(argumentList.get(3));
        boolean decryptStart = argument.equals(argumentList.get(4));
        boolean getKeysSeeValues = argument.equals(argumentList.get(5));
        if (help){
            Documentation documentation = new Documentation();
            documentation.getDocumentation();
        }else if(getKeys){
            KeyGenerator keyGenerator = new KeyGenerator();
            keyGenerator.generateKeys();
        }else if(getKeysSeeValues){
            KeyGenerator keyGenerator = new KeyGenerator();
            keyGenerator.generateKeys_seeValues();
        }else if(creatingKeyFile){
            Documentation documentation = new Documentation();
            documentation.getKeyFileDocumentation();
        }else if(encryptStart){
            Encrypt encrypt = new Encrypt();
            try { //old try catch statements from an earlier addition, functionality isn't changed by leaving them.
                encrypt.run();
            }catch (IllegalArgumentException e){}
        }else if(decryptStart){
            Decrypt decrypt = new Decrypt();
            try {
                decrypt.run();
            }catch (IllegalArgumentException e){}
        }
    }

    private void TagText() {
        System.out.print("\nSRSA>> ");
    }
}
