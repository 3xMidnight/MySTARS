package myEntities;

/**
 * Represents a WaitList an Index has.
 * 
 * @author
 *
 */
public class WaitList {

	/**
	 * The index number of this WaitList belongs to.
	 */
	private String courseIndex;

	/**
	 * The number of students in this WaitList.
	 */
	private String numberOfStudentWaitList;

	/**
	 * The list of students under this WaitList. Stores matriculation number of
	 * Students and course type.
	 */
	private String[] studentWaitList;
	/**
	 * The Index which this WaitList belongs to.
	 */
	private Index Index;

	/**
	 * Creates a new WaitList.
	 */
	public WaitList() {
	}

	/**
	 * Gets the index number which this WaitList belongs to.
	 * 
	 * @return this WaitList's index number.
	 */
	public String getCourseIndex() {
		return courseIndex;
	}

	/**
	 * Gets the Index which this WaitList belongs to.
	 * 
	 * @return this WaitList's Index.
	 */
	public Index getIndex() {
		return Index;
	}

	/**
	 * Gets the number of students in this WaitList.
	 * 
	 * @return this WaitList's number of students.
	 */
	public String getNumberOfStudentWaitList() {
		return numberOfStudentWaitList;
	}

	/**
	 * Gets the list of students under this WaitList.
	 * 
	 * @return this WaitList's students list.
	 */
	public String[] getStudentWaitList() {
		return studentWaitList;
	}

	/**
	 * Changes the index number which this WaitList belongs to.
	 * 
	 * @param courseIndex
	 *            this WaitList's new index number.
	 */
	public void setCourseIndex(String courseIndex) {
		this.courseIndex = courseIndex;
	}

	/**
	 * Changes the Index which this WaitList belongs to.
	 * 
	 * @param index
	 *            this WaitList's new Index.
	 */
	public void setIndex(Index index) {
		Index = index;
	}

	/**
	 * Changes the number of students in this WaitList.
	 * 
	 * @param numberOfStudentWaitList
	 *            this WaitList's new number of students.
	 */
	public void setNumberOfStudentWaitList(String numberOfStudentWaitList) {
		this.numberOfStudentWaitList = numberOfStudentWaitList;
	}

	/**
	 * Changes the list of students this WaitList have.
	 * 
	 * @param studentWaitList
	 *            this WaitList's new students list.
	 */
	public void setStudentWaitList(String[] studentWaitList) {
		this.studentWaitList = studentWaitList;
	}

}
