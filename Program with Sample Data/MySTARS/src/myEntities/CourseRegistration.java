package myEntities;

/**
 * Represents a CourseRegistation which belong to a Student. It stores an Index
 * along with a Student.
 * 
 * @author
 *
 */
public class CourseRegistration {

	/**
	 * The matriculation number of this CourseRegistation belongs to.
	 */
	private String accountID;
	/**
	 * The index number which this CourseRegistation belongs to.
	 */
	private String courseIndex;
	/**
	 * The course type of this CourseRegistation which a Student has selected.
	 * It can be core, unrestricted or prescribed.
	 */
	private String courseType;
	/**
	 * The Student of this CourseRegistation belongs to.
	 */
	private Student student;

	/**
	 * The Index of this CourseRegistation.
	 */
	private Index index;

	/**
	 * Creates a new CourseRegistration.
	 */
	public CourseRegistration() {

	}

	/**
	 * Gets matriculation number of this CourseRegistation belongs to.
	 * 
	 * @return this CourseRegistration's matriculation number.
	 */
	public String getAccountID() {
		return accountID;
	}

	/**
	 * Gets the index number which this CourseRegistation belongs to.
	 * 
	 * @return this CourseRegistration's index number.
	 */
	public String getCourseIndex() {
		return courseIndex;
	}

	/**
	 * Gets the course type of this CourseRegistation.
	 * 
	 * @return this CourseRegistration's course type.
	 */
	public String getCourseType() {
		return courseType;
	}

	/**
	 * Gets Index of this CourseRegistation.
	 * 
	 * @return this CourseRegistration's Index.
	 */
	public Index getIndexes() {
		return index;
	}

	/**
	 * Gets the Student of this CourseRegistation belongs to.
	 * 
	 * @return this CourseRegistration's Student.
	 */
	public Student getStudent() {
		return student;
	}

	/**
	 * Changes the matriculation number of this CourseRegistation belongs to.
	 * 
	 * @param accountID
	 *            this CourseRegistration's new matriculation number.
	 */
	public void setAccountID(String accountID) {
		this.accountID = accountID;
	}

	/**
	 * Changes the index number which this CourseRegistation belongs to.
	 * 
	 * @param courseIndex
	 *            this CourseRegistration's new index number.
	 */
	public void setCourseIndex(String courseIndex) {
		this.courseIndex = courseIndex;
	}

	/**
	 * Changes the course type of this CourseRegistation.
	 * 
	 * @param courseType
	 *            this CourseRegistration's new course type.
	 */
	public void setCourseType(String courseType) {
		this.courseType = courseType;
	}

	/**
	 * Changes Index of this CourseRegistation.
	 * 
	 * @param indexes
	 *            this CourseRegistration's new Index.
	 */
	public void setIndexes(Index index) {
		this.index = index;
	}

	/**
	 * Changes the Student of this CourseRegistation belongs to.
	 * 
	 * @param student
	 *            this CourseRegistration's new Student.
	 */
	public void setStudent(Student student) {
		this.student = student;
	}

}
