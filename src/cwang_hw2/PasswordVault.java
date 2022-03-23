package cwang_hw2;

import exceptions.*;            // Needed for exception object
import java.util.*;             // Needed for HashMap object
import java.util.regex.*;       // Needed for Pattern and Matcher objects

/**
 * The PasswordVault implements Vault class and adds new user or new site,
 * update site password, retrieve site password, and their helper functions.
 */
public class PasswordVault implements Vault {
    private HashMap<String, UserSiteAndSitePassword> userVault;
    private final Encryptor encryptor;
    private static final String username_Pattern = "^[a-z]+$";
    private static final String password_Pattern =
            "^[a-zA-Z]+[0-9]+[!@#$%^&]+$";

    /**
     * Constructor.
     */
    public PasswordVault() {
        userVault = new HashMap<>();
        encryptor = new CaesarCipher();
    }

    /**
     * constructor.
     *
     * @param e Encryptor object
     */
    public PasswordVault(Encryptor e) {
        encryptor = e;
    }

    /**
     * The validateName function validates if input name matches requirements.
     *
     * @param str The input name.
     * @return if input name matches requirements
     */
    private boolean validateName (String str) {
        Pattern usernamePattern = Pattern.compile(username_Pattern);
        Matcher usernameMatcher = usernamePattern.matcher(str);
        return usernameMatcher.matches();
    }

    /**
     * The validatePassword function validates if input password matches
     * requirements.
     *
     * @param str input password.
     * @return if input password matches requirements
     */
    private boolean validatePassword (String str) {
        Pattern passwordPattern = Pattern.compile(password_Pattern);
        Matcher passwordMatcher = passwordPattern.matcher(str);
        return passwordMatcher.matches();
    }

    /**
     * Check if input name matches the requirements.
     *
     * @param str The input name.
     * @return if input name matches the requirements.
     */
    private boolean isStringLowerCase(String str) {

        // Convert String to char
        char[] charArray = str.toCharArray();

        // Check if each char is lower case
        for(int i = 0; i < charArray.length; i++) {
            if(Character.isLetter(charArray[i]) ){
                if(Character.isUpperCase(charArray[i]))
                    return false;
            }
        }
        return true;
    }

    /**
     * The addNewUser function adds the input name into HashMap and password
     * into its UserSiteAndSitePassword class.
     *
     * @param username The username to be added
     * @param password The password to be associated with this user
     * @throws exceptions.InvalidUsernameException The supplied username is
     *                                             invalid
     * @throws exceptions.InvalidPasswordException The supplied password is
     *                                             invalid
     * @throws exceptions.DuplicateUserException The username is already in the
     *                                           vault
     */
    public void addNewUser(String username, String password)
            throws exceptions.InvalidUsernameException,
            exceptions.InvalidPasswordException,
            exceptions.DuplicateUserException {

        // Check if the name is duplicated
        if(!userVault.isEmpty()) {
            if(userVault.containsKey(username)){
                throw new DuplicateUserException();
            }
        }

        // Check if the name and password are valid
        if(username.length() > 5 && username.length() < 16 &&
                validateName(username) && isStringLowerCase(username)) {
            if(!validatePassword(password)) {
                throw new InvalidPasswordException();
            }
            else {
                userVault.put(username, new UserSiteAndSitePassword(password));
            }
        } else {
            throw new InvalidUsernameException();
        }
    }

    /**
     * The addNewSite function adds the new site into the user's
     * UserSiteAndSitePassword class.
     *
     * @param username The username requesting the new site password
     * @param password Password for the username
     * @param sitename Name of the site for which the user is requesting
     *                 a password
     * @return The generated password
     * @throws exceptions.DuplicateSiteException The username is already in the
     *                                           vault
     * @throws exceptions.UserNotFoundException The user has been locked out
     *                                          due to too many incorrect
     *                                          password attempts
     * @throws exceptions.UserLockedOutException The user has been locked out
     *                                           due to too many incorrect
     *                                           password attempts
     * @throws exceptions.PasswordMismatchException The password supplied does
     *                                              not match the user's vault
     *                                              password
     * @throws exceptions.InvalidSiteException The site name supplied is invalid
     */
    public String addNewSite(String username, String password, String sitename)
            throws exceptions.DuplicateSiteException,
            exceptions.UserNotFoundException,
            exceptions.UserLockedOutException,
            exceptions.PasswordMismatchException,
            exceptions.InvalidSiteException{
        String tempPassword;    // The variable to hold random password

        // Check if the username exists
        if (!userVault.containsKey(username))
            throw new UserNotFoundException();

        // Call the user's UserSiteAndSitePassword class
        UserSiteAndSitePassword siteAndSitePassword = userVault.get(username);

        // Check if the user is blocked
        if(!siteAndSitePassword.isBlocked()) {

            // Check if the password is valid
            if(!siteAndSitePassword.getUserPassword().equals(password)) {
                siteAndSitePassword.setLogInAttemptAddOneTime();
                throw new PasswordMismatchException();
            }
        } else
            throw new UserLockedOutException();

        // Set login attempt to zero
        siteAndSitePassword.setLogInAttemptToZero();

        // Check if the site is duplicated
        if(siteAndSitePassword.containSiteName(sitename))
            throw new DuplicateSiteException();

        // Check if the site name is valid
        if(sitename.length() > 5 && sitename.length() < 16 &&
                validateName(sitename)) {

            // Generated a random password
            String randomPassword = Passwaord.generateRandomPassword();
            tempPassword = randomPassword;

            // Encrypt the password
            String encryptPass = encryptor.encrypt(randomPassword);

            // Set the password into the user's siteAndSitePassword class
            siteAndSitePassword.setSiteVault(sitename, encryptPass);

        } else {
            throw new InvalidSiteException();
        }

        return tempPassword;
    }

    /**
     * The updateSitePassword function updates the site password and then return
     * it.
     * @param username The username requesting the new site password
     * @param password Password for the username
     * @param sitename Name of the site for which the user is requesting a
     *                 password
     * @return the new generated password
     * @throws exceptions.SiteNotFoundException There is no such user in the
     *                                          vault
     * @throws exceptions.UserNotFoundException There is no such user in the
     *                                          vault
     * @throws exceptions.UserLockedOutException The user has been locked out
     *                                           due to too many incorrect
     *                                           password attempts
     * @throws exceptions.PasswordMismatchException The password supplied does
     *                                              not match the user's vault
     *                                              password
     */
    public String updateSitePassword(String username, String password,
                                     String sitename)
            throws exceptions.SiteNotFoundException,
            exceptions.UserNotFoundException,
            exceptions.UserLockedOutException,
            exceptions.PasswordMismatchException{

        // Check if the username exists
        if (!userVault.containsKey(username)){
            throw new UserNotFoundException();
        }

        // Call the user's UserSiteAndSitePassword class
        UserSiteAndSitePassword siteAndSitePassword = userVault.get(username);

        // Check if the user is blocked
        if(!siteAndSitePassword.isBlocked()) {

            // Check if the password is valid
            if(!siteAndSitePassword.getUserPassword().equals(password)) {
                siteAndSitePassword.setLogInAttemptAddOneTime();
                throw new PasswordMismatchException();
            }
        } else
            throw new UserLockedOutException();

        // Set login attempt to zero
        siteAndSitePassword.setLogInAttemptToZero();

        // Check if the site name exits
        if (!siteAndSitePassword.containSiteName(sitename)){
            throw new SiteNotFoundException();
        }

        // Generated a random password
        String tmpPassword = Passwaord.generateRandomPassword();

        // Encrypt the password
        String encryptPass = encryptor.encrypt(tmpPassword);

        // Replace the old password with the new
        siteAndSitePassword.replaceSitePassword(sitename, encryptPass);

        return tmpPassword;
    }

    /**
     * The retrieveSitePassword function retrieves the site password.
     *
     * @param username The username requesting the site password
     * @param password Password for the username
     * @param sitename Name of the site for which the user is requesting a
     *                 password
     * @return the site password
     * @throws exceptions.SiteNotFoundException The user has no password
     *                                          associated with this site
     * @throws exceptions.UserNotFoundException There is no such user in the
     *                                          vault
     * @throws exceptions.UserLockedOutException The user has been locked out
     *                                           due to too many incorrect
     *                                           password attempts
     * @throws exceptions.PasswordMismatchException The password supplied does
     *                                              not match the user's vault
     *                                              password
     */
    public String retrieveSitePassword(String username, String password,
                                       String sitename)
            throws exceptions.SiteNotFoundException,
            exceptions.UserNotFoundException,
            exceptions.UserLockedOutException,
            exceptions.PasswordMismatchException{

        // Check if the username exists
        if (!userVault.containsKey(username)){
            throw new UserNotFoundException();
        }

        // Call the user's UserSiteAndSitePassword class
        UserSiteAndSitePassword siteAndSitePassword = userVault.get(username);

        // Check if the user is blocked
        if(!siteAndSitePassword.isBlocked()) {

            // Check if the password is valid
            if(!siteAndSitePassword.getUserPassword().equals(password)) {
                siteAndSitePassword.setLogInAttemptAddOneTime();
                throw new PasswordMismatchException();
            }
        } else
            throw new UserLockedOutException();

        // Set login attempt to zero
        siteAndSitePassword.setLogInAttemptToZero();

        // Check if the site name exits
        if (!siteAndSitePassword.containSiteName(sitename)){
            throw new SiteNotFoundException();
        }

        return encryptor.decrypt(siteAndSitePassword.getSitePassword(sitename));
    }

    /**
     * The UserSiteAndSitePassword class stores user password, site, and site
     * password.
     */
    private static class UserSiteAndSitePassword {
        private final HashMap<String, String> siteVault;
        private String userPassword;
        private int logInAttempt;

        /**
         * Constructor.
         */
        public UserSiteAndSitePassword() {
            siteVault = new HashMap<>();
            logInAttempt = 0;
        }

        /**
         * Constructor.
         *
         * @param userPassword The input password.
         */
        public UserSiteAndSitePassword(String userPassword) {
            siteVault = new HashMap<>();
            logInAttempt = 0;
            this.userPassword = userPassword;
        }

        /**
         * The setSiteVault function sets the new site password.
         *
         * @param siteName The site that is given new pass word.
         * @param sitePassword The new password.
         */
        public void setSiteVault(String siteName, String sitePassword) {
            siteVault.put(siteName, sitePassword);
        }

        /**
         * The containSiteName function checks if the site exits.
         *
         * @param siteName The input site name.
         * @return if the site exits
         */
        public boolean containSiteName (String siteName) {
            return siteVault.containsKey(siteName);
        }

        /**
         * The setUserPassword function sets user's password.
         *
         * @param userPassword The user's password.
         */
        public void setUserPassword(String userPassword) {
            this.userPassword = userPassword;
        }

        /**
         * The getUserPassword returns the user's password.
         *
         * @return The user's password
         */
        public String getUserPassword() {
            return userPassword;
        }

        /**
         * The getSitePassword function returns the site password.
         *
         * @param siteName The site name.
         * @return The site password
         */
        public String getSitePassword(String siteName){
            return siteVault.get(siteName);
        }

        /**
         * The replaceSitePassword functions updates the site password.
         *
         * @param siteName The site name.
         * @param sitePassword The new site password.
         */
        public void replaceSitePassword(String siteName, String sitePassword) {
            siteVault.replace(siteName, sitePassword);
        }

        /**
         * The setLogInAttemptToZero functions sets logInAttempt to zero.
         */
        public void setLogInAttemptToZero(){
            logInAttempt = 0;
        }

        /**
         * The setLogInAttemptAddOneTime functions adds one time to logInAttempt.
         */
        public void setLogInAttemptAddOneTime(){
            logInAttempt++;
        }

        /**
         * The isBlocked function returns if the user is blocked.
         *
         * @return If the user is blocked
         */
        public boolean isBlocked() {
            return logInAttempt == 3;
        }

        /**
         * The getLogInAttempt functions returns the logInAttempt.
         *
         * @return the logInAttempt
         */
        public int getLogInAttempt(){
            return logInAttempt;
        }
    }
}


