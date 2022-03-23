package exceptions;

/**
 * The DuplicateUserException class executes if the username already exists.
 */
public class DuplicateUserException extends Exception{
    public DuplicateUserException(){
        super("Error: The username already exists.\n");
    }
}
