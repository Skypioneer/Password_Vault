package exceptions;

/**
 * The InvalidSiteException class executes if the site name is invalid:
 * 6-12 lower-case letters.
 */
public class InvalidSiteException extends Exception{
    public InvalidSiteException(){
        super("Error: The site name is invalid; enter 6-12 lower-case " +
                "letters.\n");
    }
}
