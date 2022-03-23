package exceptions;

/**
 * The UserNotFoundException class executes if username does not exist.
 */
public class UserNotFoundException extends Exception{
    public UserNotFoundException(){
        super("Error: Username does not exist.\n");
    }
}
