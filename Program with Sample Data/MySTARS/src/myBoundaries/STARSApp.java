package myBoundaries;

import java.io.Console;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import myControllers.DBController;
import myEntities.User;

/**
 * Represents a main boundary class for MySTARS.
 * 
 * @author
 *
 */
public class STARSApp {
	/**
	 * SALT used for password hashing.
	 */
	private static final String SALT = "mySTARSSALT";
	/**
	 * Flag for user authentication.
	 */
	private static boolean isAuthenticated = false;
	/**
	 * Flag for second user authentication used in swop index function.
	 */
	private static boolean isSecondUserAuthenticated = false;
	/**
	 * Variable used to store username and password.
	 */
	private static String userName = "", passWord = "";
	/**
	 * Scanner to read input from user.
	 */
	private static Scanner scanner = new Scanner(System.in);
	/**
	 * Variable to store User object who logged in.
	 */
	private static User user = null;
	/**
	 * Variable to store User object for second user who logged in in swop index
	 * function.
	 */
	private static User userTwo = null;

	/**
	 * Main program method.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		displayWelcome();
		System.out.println("\t\t       Please login to continue");
		if (login()) {
			if (user.getAccountType() == 'S') {
				StudentMenu.display(user);
			} else {
				AdminMenu.display(user);
			}
		}
	}

	/**
	 * Display Welcome Message.
	 */
	private static void displayWelcome() {
		System.out.println("#########################################################################");
		System.out.println("#\t\t___  ___      _____ _____ ___  ______  _____ \t\t#");
		System.out.println("#\t\t|  \\/  |     /  ___|_   _/ _ \\ | ___ \\/  ___|\t\t#");
		System.out.println("#\t\t| .  . |_   _\\ `--.  | |/ /_\\ \\| |_/ /\\ `--. \t\t#");
		System.out.println("#\t\t| |\\/| | | | |`--. \\ | ||  _  ||    /  `--. \\\t\t#");
		System.out.println("#\t\t| |  | | |_| /\\__/ / | || | | || |\\ \\ /\\__/ /\t\t#");
		System.out.println("#\t\t\\_|  |_/\\__, \\____/  \\_/\\_| |_/\\_| \\_|\\____/ \t\t#");
		System.out.println("#\t\t         __/ |                               \t\t#");
		System.out.println("#\t\t        |___/                                \t\t#");
		System.out.println("#\t    Welcome to My Student Automated Registration System    \t#");
		System.out.println("#\t        All connections are monitored and recorded      \t#");
		System.out.println("#\t DISCONNECT IMMEDIATELY if you are not an authorized user.\t#");
		System.out.println("#########################################################################");
	}

	/**
	 * For user login. Returns true if login successfully if not return false.
	 * 
	 * @return true or false.
	 */
	private static boolean login() {
		Console cs = System.console();
		while (!isAuthenticated) {// while not authenticated
			System.out.print("Username : ");
			userName = scanner.nextLine().toUpperCase();
			System.out.print("Password : ");
			if (cs != null) {
				char[] passString = cs.readPassword();
				passWord = new String(passString);
			} else {
				passWord = scanner.nextLine();
			}

			if (!userName.isEmpty() & !passWord.isEmpty()) {
				String hashedPassword = generateHashedPassword(passWord);
				user = DBController.validateUser(userName, hashedPassword);
				if (user != null) {
					isAuthenticated = true;
					return isAuthenticated;
				} else
					System.out.println("\t	Invalid username or password. Please try again. ");
			} else
				System.out.println("Please enter username and password.");
		}
		return false;
	}

	/**
	 * For second user login used in swop index function. Returns true if login
	 * successfully if not return false.
	 * 
	 * @return Username of second user.
	 */
	public static String loginReturnUserName() {
		Console cs = System.console();
		while (!isSecondUserAuthenticated) {// while not authenticated
			System.out.print("Username : ");
			userName = scanner.nextLine().toUpperCase();
			System.out.print("Password : ");
			if (cs != null) {
				char[] passString = cs.readPassword();
				passWord = new String(passString);
			} else {
				passWord = scanner.nextLine();
			}

			String hashedPassword = generateHashedPassword(passWord);
			userTwo = DBController.validateUser(userName, hashedPassword);
			if (userTwo != null) {
				isAuthenticated = true;
				return userTwo.getUsername();
			} else
				System.out.println("\t	Invalid username or password. Please try again. ");
		}
		return null;
	}

	/**
	 * Log out user.
	 */
	protected static void logout() {
		user = null;
		userTwo = null;
		isAuthenticated = false;
		isSecondUserAuthenticated = false;
		main(null);
	}

	/**
	 * Turns plain text into SHA-256 hash.
	 * 
	 * @param input
	 *            plain text.
	 * @return hash.
	 */
	private static String generateHash(String input) {
		StringBuilder hash = new StringBuilder();

		try {
			MessageDigest sha = MessageDigest.getInstance("SHA-256"); // use SHA-256 Algorithm
			byte[] hashedBytes = sha.digest(input.getBytes());
			char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
			for (int idx = 0; idx < hashedBytes.length; idx++) {
				byte b = hashedBytes[idx];
				hash.append(digits[(b & 0xf0) >> 4]);
				hash.append(digits[b & 0x0f]);
			}
		} catch (NoSuchAlgorithmException e) {
			// failed to generate hash
		}

		return hash.toString();
	}

	/**
	 * Turns plain password into hashed password.
	 * 
	 * @param password
	 *            plain password.
	 * @return hashed password.
	 */
	public static String generateHashedPassword(String password) {
		String saltedPassword = SALT + password;
		String hashedPassword = generateHash(saltedPassword);
		return hashedPassword;
	}

}
