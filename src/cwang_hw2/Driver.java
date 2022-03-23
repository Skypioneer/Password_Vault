package cwang_hw2;
import encrypt.CaesarCipherTest; //Needed for CaesarCipherTest object
import exceptions.*;		// Needed for exception object

/**
 * The program is a simple password vault. A user is the client for the vault.
 * He/she has a username and a vault password, and uses the vault to store
 * (site / password) pairs for a variety of sites. In a real vault a username
 * might be a long string like an email address and a site might be a long
 * URL, but for our example we will use short usernames like "josie" and
 * passwords like "whocares" and short site names like "amazon", "google",
 * or "netflix."
 *
 * @author Jason Wang
 * @version 56.0
 */
public class Driver {

	/**
	 * The main method calls one function to test if program can be execute
	 * successfully, one function to test if Encryptor can execute successfully
	 * and four functions to test if exceptions can execute successfully.
	 *
	 * @param args A string array containing the command line arguments.
	 * @throws InvalidUsernameException The supplied username is invalid
	 * @throws DuplicateUserException The username is already in the vault
	 * @throws InvalidPasswordException The supplied password is invalid
	 * @throws UserNotFoundException There is no such user in the vault
	 * @throws UserLockedOutException The user has been locked out due to too
	 *								  many incorrect password attempts
	 * @throws PasswordMismatchException The password supplied does not match
	 * 									 the user's vault password
	 * @throws InvalidSiteException The site name supplied is invalid
	 * @throws DuplicateSiteException The username is already in the vault
	 * @throws SiteNotFoundException The user has no password associated with
	 *                               this site
	 */
	public static void main(String[] args) throws InvalidUsernameException,
			DuplicateUserException, InvalidPasswordException,
			UserNotFoundException, UserLockedOutException,
			PasswordMismatchException, InvalidSiteException,
			DuplicateSiteException, SiteNotFoundException {

		PasswordVault passwordVault = new PasswordVault();

		testingSuccessCase(passwordVault);

		testingExceptionsForAddNewUser(passwordVault);

		testingExceptionsForAddNewSite(passwordVault);

		testingExceptionsForRetrieveSitePassword(passwordVault);

		testingExceptionsForUpdateSitePassword(passwordVault);

		testingIfPasswordIsSameBeforeAndAfterEncryptor();
	}

	/**
	 * The testingSuccessCase function proves that the program execute properly.
	 *
	 * @param passwordVault The password vault that store users, websites, and
	 *						passwords of the websites.
	 * @throws InvalidUsernameException The supplied username is invalid
	 * @throws DuplicateUserException The username is already in the vault
	 * @throws InvalidPasswordException The supplied password is invalid
	 * @throws UserNotFoundException There is no such user in the vault
	 * @throws UserLockedOutException The user has been locked out due to too
	 * 								  many incorrect password attempts
	 * @throws PasswordMismatchException The password supplied does not match
	 * 									 the user's vault password
	 * @throws InvalidSiteException The site name supplied is invalid
	 * @throws DuplicateSiteException There is already a site stored for this
	 * 								  user
	 * @throws SiteNotFoundException The user has no password associated with
	 * 								 this site
	 */
	public static void testingSuccessCase(PasswordVault passwordVault)
			throws InvalidUsernameException, DuplicateUserException,
			InvalidPasswordException, UserNotFoundException,
			UserLockedOutException, PasswordMismatchException,
			InvalidSiteException, DuplicateSiteException,
			SiteNotFoundException {

		String user1 = "arpita", password = "mypass123!", website = "amazon";

		System.out.println("** 1. Testing success case: **\n");

		passwordVault.addNewUser(user1, password);
		System.out.printf("Attempting to add user '%s' and password '%s!'\n",
				user1, password);
		System.out.printf("Added user '%s'\n\n", user1);

		System.out.printf("Attempting to add site '%s' for user '%s!'\n",
				website, user1);
		System.out.printf("Added site '%s' for user '%s!' ", website, user1);
		System.out.println("=> generated site password: " +
				passwordVault.addNewSite(user1, password, website) + "\n");

		System.out.printf("Attempting to retrieve '%s' site password for " +
				"user '%s!'\n", website, user1);
		System.out.printf("Retrieved site '%s' for user '%s!' ", website,
				user1);
		System.out.println("=> retrieved password: " +
				passwordVault.retrieveSitePassword(user1, password, website)
				+ "\n");

		System.out.printf("Attempting to update '%s' site password for user " +
				"'%s!'\n", website, user1);
		System.out.printf("Updated site '%s' for user '%s!' ", website, user1);
		System.out.println("=> updated password: " +
				passwordVault.updateSitePassword(user1, password, website) +
				"\n");
	}

	/**
	 * The testingExceptionsForAddNewUser function tests DuplicateSiteException,
	 * InvalidPasswordException, InvalidUsernameException when adding a new
	 * user.
	 *
	 * @param passwordVault The password vault that store users, websites, and
	 * 		  passwords of the websites.
	 * @throws InvalidPasswordException The supplied password is invalid
	 * @throws InvalidUsernameException The supplied username is invalid
	 * @throws DuplicateUserException The username is already in the vault
	 */
	public static void testingExceptionsForAddNewUser(PasswordVault
															  passwordVault)
			throws InvalidPasswordException, InvalidUsernameException,
			DuplicateUserException {

		String user1 = "arpita", user2 = "bob", user3 = "bobismyname",
				password1 = "mypass123!", password2 = "mypass123";

		System.out.println("\n** 2. Testing exceptions for addNewUser: **\n");

		helperOfTestingExceptionsForAddNewUser(passwordVault, user2, password1);

		helperOfTestingExceptionsForAddNewUser(passwordVault, user3, password2);

		helperOfTestingExceptionsForAddNewUser(passwordVault, user1, password2);
	}

	/**
	 * The helperOfTestingExceptionsForAddNewUser function helps to test those
	 * exceptions.
	 *
	 * @param passwordVault The password vault that store users, websites, and
	 * 		  passwords of the websites.
	 * @param user The user's name.
	 * @param password The user's password.
	 * @throws DuplicateUserException The username is already in the vault
	 * @throws InvalidPasswordException The supplied password is invalid
	 * @throws InvalidUsernameException The supplied username is invalid
	 */
	public static void helperOfTestingExceptionsForAddNewUser
			(PasswordVault passwordVault, String user, String password)
			throws DuplicateUserException, InvalidPasswordException,
			InvalidUsernameException{

		try {
			System.out.printf("Attempting to add user '%s' and password " +
					"'%s'\n", user, password);
			passwordVault.addNewUser(user, password);
		} catch (DuplicateUserException | InvalidPasswordException |
				InvalidUsernameException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * The testingExceptionsForAddNewSite function tests
	 * PasswordMismatchException, DuplicateSiteException, InvalidSiteException,
	 * UserNotFoundException, UserLockedOutException when adding a new site.
	 *
	 * @param passwordVault The password vault that store users, websites, and
	 * 	      passwords of the websites.
	 * @throws PasswordMismatchException The password supplied does not match
	 * 									 the user's vault password
	 * @throws DuplicateSiteException There is already a site stored for this
	 * 								  user
	 * @throws InvalidSiteException The site name supplied is invalid
	 * @throws UserNotFoundException There is no such user in the vault
	 * @throws UserLockedOutException The user has been locked out due to too
	 * 								  many incorrect password attempts
	 */
	public static void testingExceptionsForAddNewSite(PasswordVault
															  passwordVault)
	throws PasswordMismatchException, DuplicateSiteException,
		   InvalidSiteException, UserLockedOutException,
		   UserNotFoundException{
		String user1 = "arpita", user2 = "maryismyname", website1 = "amazon",
				website2 = "amazon.com", password = "mypass123!";

		System.out.println("\n** 3. Testing exceptions for addNewSite:**\n");

		helperOfTestingExceptionsForAddNewSite
				(passwordVault, user1, password, website1);

		helperOfTestingExceptionsForAddNewSite
				(passwordVault, user2, password, website1);

		helperOfTestingExceptionsForAddNewSite
				(passwordVault, user1, password, website2);
	}

	/**
	 * The helperOfTestingExceptionsForAddNewSite function helps to test those
	 * exceptions.
	 *
	 * @param passwordVault The password vault that store users, websites, and
	 *  	  passwords of the websites.
	 * @param user The user's name.
	 * @param password The user's password.
	 * @param website The new website that the user wanna add.
	 * @throws PasswordMismatchException The password supplied does not match
	 *                                   the user's vault password
	 * @throws DuplicateSiteException There is already a site stored for this
	 *                                user
	 * @throws InvalidSiteException The site name supplied is invalid
	 * @throws UserNotFoundException There is no such user in the vault
	 * @throws UserLockedOutException The user has been locked out due to too
	 * 	 							  many incorrect password attempts
	 */
	public static void helperOfTestingExceptionsForAddNewSite
			(PasswordVault passwordVault, String user, String password,
			 String website) throws PasswordMismatchException,
			 DuplicateSiteException, InvalidSiteException,
			 UserNotFoundException, UserLockedOutException{

		try {
			System.out.printf("Attempting to add site '%s' for user '%s'\n",
					website, user);
			passwordVault.addNewSite(user, password, website);
		} catch (PasswordMismatchException | UserLockedOutException |
				UserNotFoundException | InvalidSiteException |
				DuplicateSiteException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * The testingExceptionsForRetrieveSitePassword function tests
	 * PasswordMismatchException, SiteNotFoundException, UserNotFoundException,
	 * UserLockedOutException when retrieving a site password.
	 *
	 * @param passwordVault The password vault that store users, websites, and
	 * 	 	  passwords of the websites.
	 *
	 * @throws PasswordMismatchException The password supplied does not match
	 *                                   the user's vault password
	 * @throws SiteNotFoundException The user has no password associated with
	 * 		                         this site
	 * @throws UserNotFoundException There is no such user in the vault
	 * @throws UserLockedOutException The user has been locked out due to too
	 *                                many incorrect password attempts
	 */
	public static void testingExceptionsForRetrieveSitePassword
			(PasswordVault passwordVault) throws PasswordMismatchException,
			SiteNotFoundException, UserNotFoundException,
			UserLockedOutException {
		String user = "arpita", password = "mypass123!", website = "amazon.com";

		System.out.println("\n** 4. Testing exceptions for " +
				"retrieveSitePassword: **\n");

		try{
			System.out.printf("Attempting to retrieve '%s' site password for " +
					"user '%s'\n", website, user);
			passwordVault.retrieveSitePassword(user, password, website);
		} catch (PasswordMismatchException | SiteNotFoundException |
				UserNotFoundException | UserLockedOutException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * The testingExceptionsForUpdateSitePassword function tests
	 * UserNotFoundException, UserLockedOutException, PasswordMismatchException,
	 * InvalidSiteException, DuplicateSiteException when updating a site
	 * password.
	 *
	 * @param passwordVault The password vault that store users, websites, and
	 * 	      passwords of the websites.
	 * @throws UserNotFoundException There is no such user in the vault
	 * @throws UserLockedOutException The user has been locked out due to too
	 * 	                              many incorrect password attempts
	 * @throws PasswordMismatchException The password supplied does not match
	 * 									 the user's vault password
	 * @throws InvalidSiteException	The site name supplied is invalid
	 * @throws DuplicateSiteException There is already a site stored for this
	 * 						          user
	 */
	public static void testingExceptionsForUpdateSitePassword
			(PasswordVault passwordVault)
			throws UserNotFoundException, UserLockedOutException,
			PasswordMismatchException, InvalidSiteException,
			DuplicateSiteException {
		String user = "arpita", password1 = "mypass123!",
				password2 = "mypass123", website1 = "amazon",
				website2 = "amazon.com";
		final int FOUR = 4;

		System.out.println("\n** 5. Testing exceptions for " +
				"updateSitePassword: **\n");

		try{
			System.out.printf("Attempting to update '%s' site password for " +
					"user '%s'\n", website2, user);
			passwordVault.updateSitePassword(user, password1, website2);
		} catch (PasswordMismatchException | UserNotFoundException |
				SiteNotFoundException | UserLockedOutException e) {
			System.out.println(e.getMessage());
		}

		for (int i = 0; i < FOUR; i++){
			try {
				System.out.printf("Attempting to update '%s' site password " +
						"for user '%s'\n", website1, user);
				passwordVault.addNewSite(user, password2, website1);
			} catch (DuplicateSiteException | UserNotFoundException |
					PasswordMismatchException | UserLockedOutException |
					InvalidSiteException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	/**
	* The testingIfPasswordIsSameBeforeAndAfterEncryptor function tests if
	* password is same before and after the encryptor.
	*/
	private static void testingIfPasswordIsSameBeforeAndAfterEncryptor() {
		String originalCode = "Jason@1995";

		CaesarCipherTest caesarCipherTest = new CaesarCipherTest();

		System.out.println("\n** 6. Testing if password is same before and " +
				"after encryptor **\n");

		caesarCipherTest.test(originalCode);

		System.out.printf("The original code: %s\n", originalCode);

		System.out.printf("The encryptedCode code: %s\n",
				caesarCipherTest.getEncryptedCode());

		System.out.printf("The decrypted code: %s\n",
				caesarCipherTest.getDecryptedCode());

		System.out.printf("Is decrypted code as same as original code?: %s\n",
				caesarCipherTest.isSame());
	}
}