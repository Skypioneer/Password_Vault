package exceptions;

/**
 * The InvalidUsernameException class executes if the username is invalid:
 * 6-12 lower-case letters.
 */
public class InvalidUsernameException extends Exception {
    public InvalidUsernameException(){
        super("Error: The username is invalid; enter 6-12 lower-case " +
                "letters.\n");
    }
}
