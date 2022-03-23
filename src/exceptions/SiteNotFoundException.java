package exceptions;

/**
 * The SiteNotFoundException class executes if site name does not exist for
 * this user.
 */
public class SiteNotFoundException extends Exception{
    public SiteNotFoundException(){
        super("Error: Site name does not exist for this user.\n");
    }
}
