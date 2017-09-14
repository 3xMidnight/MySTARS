package myEntities;

/**
 * Represents a school. A school can have many Students and many Courses.
 * 
 * @author
 *
 */
/**
 * @author
 *
 */
public class School {

	/**
	 * The full name of this School.
	 */
	private String schoolName;

	/**
	 * The initial of this School that is unique which can be used to identify
	 * the School.
	 */
	private String schoolInitial;

	/**
	 * Students enrolled in this School.
	 */
	private Student[] students;

	/**
	 * Creates a new School.
	 */
	public School() {

	}

	/**
	 * Gets the initial of this School.
	 * 
	 * @return this School's initial
	 */
	public String getSchoolInitial() {
		return schoolInitial;
	}

	/**
	 * Gets the full name of this School.
	 * 
	 * @return this School's full name
	 */
	public String getSchoolName() {
		return schoolName;
	}

	/**
	 * Gets Students rolled in this School.
	 * 
	 * @return this School's students
	 */
	public Student[] getStudents() {
		return students;
	}

	/**
	 * Changes the initial of this School.
	 * 
	 * @param schoolInitial
	 *            This School's new initial.
	 */
	public void setSchoolInitial(String schoolInitial) {
		this.schoolInitial = schoolInitial;
	}

	/**
	 * Changes the full name of this School.
	 * 
	 * @param schoolName
	 *            This Schools's new full name.
	 */
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	/**
	 * Changes the Students in this School.
	 * 
	 * @param students
	 *            This Schools's new students.
	 */
	public void setStudents(Student[] students) {
		this.students = students;
	}

}
