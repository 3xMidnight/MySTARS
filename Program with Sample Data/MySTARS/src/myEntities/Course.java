package myEntities;

/**
 * Represents a course in the school. A Course can have many Indexes.
 * 
 * @author
 *
 */
public class Course {
	/**
	 * The unique code of this Course.
	 */
	private String courseCode;
	/**
	 * The full course name of this Course.
	 */
	private String courseName;
	/**
	 * The lesson type of this Course. The lesson type can be "T1" lecture only,
	 * "T2" lecture and tutorial, "T3" lecture, tutorial and laboratory.
	 */
	private String lessonType;
	/**
	 * The academic unit of this Course.
	 */
	private String au;
	/**
	 * The Indexes under this Course.
	 */
	private Index[] indexes;
	/**
	 * The school which this Course belongs to.
	 */
	private School school;

	/**
	 * Creates a Course.
	 */
	public Course() {
		super();
	}

	/**
	 * Gets the academic unit of this Course.
	 * 
	 * @return this Course academic unit.
	 */
	public String getAu() {
		return au;
	}

	/**
	 * Gets the course code of this Course.
	 * 
	 * @return this Course's course code.
	 */
	public String getCourseCode() {
		return courseCode;
	}

	/**
	 * Gets the name of this Course.
	 * 
	 * @return this Course's full name.
	 */
	public String getCourseName() {
		return courseName;
	}

	/**
	 * Gets Indexes that this Course have.
	 * 
	 * @return this Course's Indexes.
	 */
	public Index[] getIndexes() {
		return indexes;
	}

	/**
	 * Gets the lesson type of this Course.
	 * 
	 * @return this Course's lesson type.
	 */
	public String getLessonType() {
		return lessonType;
	}

	/**
	 * Gets School which this Course belong to.
	 * 
	 * @return this Course's School.
	 */
	public School getSchool() {
		return school;
	}

	/**
	 * Changes the academic unit of this Course.
	 * 
	 * @param au
	 *            this Course's new academic unit.
	 */
	public void setAu(String au) {
		this.au = au;
	}

	/**
	 * Changes the code of this Course.
	 * 
	 * @param courseCode
	 *            this Course's new code.
	 */
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	/**
	 * Changes the full course name of this Course.
	 * 
	 * @param courseName
	 *            this Course's new course name.
	 */
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	/**
	 * Changes Indexes that this Course have.
	 * 
	 * @param indexes
	 *            this Course's new Indexes.
	 */
	public void setIndexes(Index[] indexes) {
		this.indexes = indexes;
	}

	/**
	 * Changes the lesson type of this Course.
	 * 
	 * @param lessonType
	 *            this Course's new lesson type.
	 */
	public void setLessonType(String lessonType) {
		this.lessonType = lessonType;
	}

	/**
	 * Changes the School of this Course.
	 * 
	 * @param school
	 *            this Course's new School.
	 */
	public void setSchool(School school) {
		this.school = school;
	}
}
