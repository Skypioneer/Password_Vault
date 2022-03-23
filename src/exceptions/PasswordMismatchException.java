package exceptions;

/**
 * The PasswordMismatchException class executes if attempted to login with
 * incorrect credentials
 */
public class PasswordMismatchException extends Exception{
    public PasswordMismatchException(){
        super("Error: Attempted to login with incorrect credentials.\n");
    }
}
