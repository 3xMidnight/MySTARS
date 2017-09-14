package myEntities;

/**
 * Represents an administrator which is an User of MySTARS.
 * 
 * @author
 *
 */
public class Admin extends User {
	/**
	 * The staff number or ID of this Admin.
	 */
	private String staffNo;

	/**
	 * Creates an Admin
	 */
	public Admin() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Gets the staff number or ID of this Admin.
	 * 
	 * @return this Admin's staff number or ID.
	 */
	public String getStaffNo() {
		return staffNo;
	}

	/**
	 * Changes the staff number or ID of this Admin.
	 * 
	 * @param staffNo
	 *            this Admin's new staff number or ID.
	 */
	public void setStaffNo(String staffNo) {
		this.staffNo = staffNo;
	}

}
