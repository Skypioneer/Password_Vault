package exceptions;

/**
 * The UserLockedOutException class executes if attempted to login with
 * incorrect credentials 3 times.
 */
public class UserLockedOutException extends Exception{
    public UserLockedOutException(){
        super("Error: Error: Attempted to login with incorrect credentials 3 times\n" +
                "user is locked out of the system.\n");
    }
}
