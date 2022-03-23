package exceptions;

/**
 * The InvalidPasswordException class execute if the password is invalid:
 * 6-15 characters and at least one letter, one number, and one special
 * character (!@#$%^&).
 */
public class InvalidPasswordException extends Exception{
    public InvalidPasswordException(){
        super("Error: The password is invalid; enter 6-15 characters and at " +
                "least\none letter, one number, and one special character " +
                "(!@#$%^&).\n");
    }
}
