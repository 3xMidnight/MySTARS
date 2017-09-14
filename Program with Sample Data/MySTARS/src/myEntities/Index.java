package myEntities;

/**
 * Represents an index a Course has. An Index has one WaitList and can have many
 * Lessons.
 * 
 * @author
 *
 */
public class Index {
	/**
	 * The index number of this Index.
	 */
	private String courseIndex;
	/**
	 * The course code which this Index belongs to.
	 */
	private String courseCode;
	/**
	 * The total number of students this Index can have.
	 */
	private String size;
	/**
	 * The number of empty slot available for students in this Index.
	 */
	private String vacancy;
	/**
	 * The number of students of have added this Index to their account.
	 */
	private String numberOfStudent;
	/**
	 * All the Lessons this Index have.
	 */
	private Lesson[] lessons;
	/**
	 * The WaitList which this Index has.
	 */
	private WaitList waitlist;
	/**
	 * The Course which this Index belongs to.
	 */
	private Course course;

	/**
	 * Creates a new Index.
	 */
	public Index() {
	}

	/**
	 * Gets the Course which this Index belongs to.
	 * 
	 * @return this Index's Course.
	 */
	public Course getCourse() {
		return course;
	}

	/**
	 * Gets the course code which this Index belongs to.
	 * 
	 * @return this Index's course code.
	 */
	public String getCourseCode() {
		return courseCode;
	}

	/**
	 * Gets the index number of this Index.
	 * 
	 * @return this Index's index number.
	 */
	public String getCourseIndex() {
		return courseIndex;
	}

	/**
	 * Gets the lessons this Index have.
	 * 
	 * @return this Index's Lessons.
	 */
	public Lesson[] getLessons() {
		return lessons;
	}

	/**
	 * Gets the number of students who have added this Index in their account.
	 * 
	 * @return this Index's number of students.
	 */
	public String getNumberOfStudent() {
		return numberOfStudent;
	}

	/**
	 * Gets the total number of students this Index can have.
	 * 
	 * @return this Index's size.
	 */
	public String getSize() {
		return size;
	}

	/**
	 * Gets the number of empty slot available for students in this Index.
	 * 
	 * @return this Index's Vacancy.
	 */
	public String getVacancy() {
		return vacancy;
	}

	/**
	 * Gets the WaitList of this Index.
	 * 
	 * @return this Index's WaitList.
	 */
	public WaitList getWaitlist() {
		return waitlist;
	}

	/**
	 * Changes the Course of this Index.
	 * 
	 * @param course
	 *            this Index's new Course.
	 */
	public void setCourse(Course course) {
		this.course = course;
	}

	/**
	 * Changes the course code of this Index.
	 * 
	 * @param courseCode
	 *            this Index's new course code.
	 */
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	/**
	 * Changes the index number of this Index.
	 * 
	 * @param courseIndex
	 *            this Index's new index number.
	 */
	public void setCourseIndex(String courseIndex) {
		this.courseIndex = courseIndex;
	}

	/**
	 * Changes the Lessons of this Index.
	 * 
	 * @param lessons
	 *            this Index's new Lessons.
	 */
	public void setLessons(Lesson[] lessons) {
		this.lessons = lessons;
	}

	/**
	 * Changes the number of students who have added this Index.
	 * 
	 * @param numberOfStudent
	 *            this Index's new number of students.
	 */
	public void setNumberOfStudent(String numberOfStudent) {
		this.numberOfStudent = numberOfStudent;
	}

	/**
	 * Changes the number of student this Index can have.
	 * 
	 * @param size
	 *            this Index's new size.
	 */
	public void setSize(String size) {
		this.size = size;
	}

	/**
	 * Changes the number of empty slot of this Index.
	 * 
	 * @param vacancy
	 *            this Index's new vacancy.
	 */
	public void setVacancy(String vacancy) {
		this.vacancy = vacancy;
	}

	/**
	 * Changes the WaitList of this Index.
	 * 
	 * @param waitlist
	 *            this Index's new WaitList.
	 */
	public void setWaitlist(WaitList waitlist) {
		this.waitlist = waitlist;
	}
}
