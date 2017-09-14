package myEntities;

/**
 * Represents an user of MySTARS. A user can be Admin or Student.
 * 
 * @author
 *
 */
public class User {
	/**
	 * The full name of this User.
	 */
	private String name;
	/**
	 * The username of this User which will be used for login.
	 */
	private String username;
	/**
	 * The account password of this User which will be used for login.
	 */
	private String password;
	/**
	 * The account type of this User, it can be 'S' for Student or 'A' for
	 * Admin.
	 */
	private char accountType;
	/**
	 * The gender of this User.
	 */
	private String gender;

	/**
	 * Creates a User.
	 */
	public User() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Gets the account type of this User.
	 * 
	 * @return this user's account type.
	 */
	public char getAccountType() {
		return accountType;
	}

	/**
	 * Gets the gender of this User.
	 * 
	 * @return this user's gender.
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * Gets the full name of this User.
	 * 
	 * @return this User's full name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the password of this User.
	 * 
	 * @return this User's password.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Gets the username of this User.
	 * 
	 * @return this User's username.
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Changes the account type of this User.
	 * 
	 * @param accountType
	 *            this User's new account type.
	 */
	public void setAccountType(char accountType) {
		this.accountType = accountType;
	}

	/**
	 * Changes the gender of this User.
	 * 
	 * @param gender
	 *            this User's new gender.
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * Changes the full name of this User.
	 * 
	 * @param name
	 *            this User's new name.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Changes the password of this User.
	 * 
	 * @param password
	 *            this User's new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Changes the username of this User.
	 * 
	 * @param username
	 *            this User's new username.
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Validate if the password entered in is the same as this User's password. Return
	 * true if same else false.
	 * 
	 * @param passWord
	 *            the password entered by physical user.
	 * @return true or false.
	 */
	public boolean validate(String passWord) {
		if (this.password.equals(passWord))
			return true;
		else
			return false;
	}
}
