package exceptions;

/**
 * The DuplicateSiteException class executes if the site name already exists
 * for this user.
 */
public class DuplicateSiteException extends Exception{
    public DuplicateSiteException(){
        super("Error: The site name already exists for this user.\n");
    }
}
