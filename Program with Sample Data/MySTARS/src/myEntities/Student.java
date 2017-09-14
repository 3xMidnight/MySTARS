package myEntities;

/**
 * Represents a Student enrolled in the School which is an User of MySTARS. A
 * Student can have multiple CourseRegistation.
 * 
 * @author
 *
 */
public class Student extends User {
	/**
	 * The country which this Student is born.
	 */
	private String nationality;
	/**
	 * The current study year which this Student is in.
	 */
	private String year;
	/**
	 * The current programme which this Student is in.
	 */
	private String program;
	/**
	 * The specialization which this Student is taking.
	 */
	private String specialization;
	/**
	 * The matriculation number of this Student.
	 */
	private String matricNo;
	/**
	 * The email address of this Student used for Course registered email
	 * notification.
	 */
	private String email;
	/**
	 * The mobile phone number of this Student used for Course registered SMS
	 * notification.
	 */
	private String mobileNo;
	/**
	 * Indicate the type of notifications this Student would want to receive.
	 * Value "T1" for email only, "T2" for SMS only, "T3" for both email and SMS
	 * notification.
	 */
	private String notificationType;
	/**
	 * CourseRegistration belong to this Student.
	 */
	private CourseRegistration[] courseRegistrations;
	/**
	 * School where this Student is enrolled in.
	 */
	private School school;

	/**
	 * Creates a new Student.
	 */
	public Student() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Gets the CourseRegistrations of this Student.
	 * 
	 * @return this Student's CourseRegistations
	 */
	public CourseRegistration[] getCourseRegistration() {
		return courseRegistrations;
	}

	/**
	 * Gets the email address of this Student.
	 * 
	 * @return this Student's email.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Gets the matriculation number of this Student.
	 * 
	 * @return this Students matriculation number.
	 */
	public String getMatricNo() {
		return matricNo;
	}

	/**
	 * Gets the mobile phone number of this Student.
	 * 
	 * @return this Student's mobile phone number
	 */
	public String getMobileNo() {
		return mobileNo;
	}

	/**
	 * Gets the country which this Students is born.
	 * 
	 * @return this Student's nationality.
	 */
	public String getNationality() {
		return nationality;
	}

	/**
	 * Gets the notification type of this Student.
	 * 
	 * @return this Student's notification type.
	 */
	public String getNotificationType() {
		return notificationType;
	}

	/**
	 * Gets the programme that this Student is currently taking.
	 * 
	 * @return this Student's programme.
	 */
	public String getProgram() {
		return program;
	}

	/**
	 * Gets the School which this Student is enrolled in.
	 * 
	 * @return this Student's school.
	 */
	public School getSchool() {
		return school;
	}

	/**
	 * Gets the specialization this Student is take on.
	 * 
	 * @return this Student's specialization.
	 */
	public String getSpecialization() {
		return specialization;
	}

	/**
	 * Gets the current study year of this Student.
	 * 
	 * @return this Student's study year.
	 */
	public String getYear() {
		return year;
	}

	/**
	 * Changes the CourseRegistration of this Student
	 * 
	 * @param courseRegistration
	 *            this Student's new CourseRegistration.
	 */
	public void setCourseRegistrations(CourseRegistration[] courseRegistrations) {
		this.courseRegistrations = courseRegistrations;
	}

	/**
	 * Changes the email of this Student.
	 * 
	 * @param email
	 *            this Student's new email.
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Changes the matriculation number of this Student.
	 * 
	 * @param matricNo
	 *            this Student's new matriculation number.
	 */
	public void setMatricNo(String matricNo) {
		this.matricNo = matricNo;
	}

	/**
	 * Changes the mobile phone number of this Student.
	 * 
	 * @param mobileNo
	 *            this Student's new mobile phone number.
	 */
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	/**
	 * Changes the nationality of this Student.
	 * 
	 * @param nationality
	 *            this Student's new nationality.
	 */
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	/**
	 * Changes the notification type of this Student.
	 * 
	 * @param notificationType
	 *            this Student's new notification type.
	 */
	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}

	/**
	 * Changes the programme that this Student's is taking.
	 * 
	 * @param program
	 *            this Student's new programme.
	 */
	public void setProgram(String program) {
		this.program = program;
	}

	/**
	 * Changes the School of this Student.
	 * 
	 * @param school
	 *            this Student's new School.
	 */
	public void setSchool(School school) {
		this.school = school;
	}

	/**
	 * Changes the specialization of this Student.
	 * 
	 * @param specialization
	 *            this Student's new specialization.
	 */
	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}

	/**
	 * Changes the current study year of this Student.
	 * 
	 * @param year
	 *            this Student's new study year.
	 */
	public void setYear(String year) {
		this.year = year;
	}
}
