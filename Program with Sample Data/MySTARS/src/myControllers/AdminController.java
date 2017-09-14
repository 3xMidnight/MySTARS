package myControllers;

import java.io.Console;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import myEntities.Course;
import myEntities.School;
import myEntities.Student;
import myEntities.User;

/**
 * Represents a controller which will be used by an administrator. It contains
 * functions which admin can do on MySTARS.
 * 
 * @author
 *
 */
public class AdminController {
	private static Scanner sc;
	private static int choice;

	/**
	 * A method called by an admin to add student to MySTARS.
	 */
	public static void addStudentAdmin() {
		Student newstudent = new Student();
		String number, email, username, pass1, pass2;
		boolean vEmail, vPassword = false;
		newstudent.setAccountType('S');
		sc = new Scanner(System.in);
		User user;
		do {
			System.out.print("Please enter the username for the Student:");
			username = sc.nextLine().toUpperCase().trim();
			user = DBController.getUserByUsername(username);
			if (user != null)
				System.out.println("Username already exist. Please enter a new username.");
		} while (user != null);
		newstudent.setUsername(username);

		Console cs = System.console();
		do {
			if (cs != null) {
				System.out.print("Please enter the password for the Student:");
				char[] passString = cs.readPassword();
				pass1 = new String(passString);
				System.out.print("Please confirm the password:");
				passString = cs.readPassword();
				pass2 = new String(passString);

				if (pass1.equals(pass2))
					vPassword = true;
				else
					System.out.println("Error. The passwords you entered were not same. ");
			} else {
				System.out.print("Please enter the password for the Student:");
				pass1 = sc.nextLine().trim();
				System.out.print("Please confirm the password:");
				pass2 = sc.nextLine().trim();
				if (pass1.equals(pass2))
					vPassword = true;
				else
					System.out.println("Error. The passwords you entered were not same. ");
			}
		} while (vPassword != true);

		newstudent.setPassword(pass2);
		System.out.print("Please enter name of the Student:");
		newstudent.setName(sc.nextLine().toUpperCase().trim());
		System.out.print("Please enter gender (1 for Male/2 for Female) of the Student:");

		do {
			choice = Integer.parseInt(sc.nextLine());
			switch (choice) {
			case 1:
				newstudent.setGender("Male");
				break;
			case 2:
				newstudent.setGender("Female");
				break;
			default:
				System.out.println("Please enter a valid choice");
			}
		} while (choice < 0 || choice > 2);

		System.out.print("Please enter matriculation number of the Student:");
		newstudent.setMatricNo(sc.nextLine().trim());
		System.out.print("Please enter nationality of the Student:");
		newstudent.setNationality(sc.nextLine().trim());
		System.out.print("Please enter the year of the Student is in:");
		newstudent.setYear(sc.nextLine().trim());
		System.out.print("Please enter the programme which the Student is taking:");
		newstudent.setProgram(sc.nextLine().trim());
		System.out.print("Please enter the specialization which the Student is take on:");
		newstudent.setSpecialization(sc.nextLine().trim());
		School[] schools = DBController.getAllSchoolArray();
		System.out.println("Please select the school which the Student belongs:");
		for (int i = 0; i < schools.length; i++) {
			System.out.println(i + 1 + " : " + schools[i].getSchoolName());
		}
		do {
			choice = Integer.parseInt(sc.nextLine());
			if (choice < 0 || choice > schools.length)
				System.out.println("Please select a valid school from the list.");
		} while (choice < 0 || choice > schools.length);

		newstudent.setSchool(schools[choice - 1]);

		do {
			System.out.print("Please enter email address of the Student:");
			email = sc.nextLine().trim();
			vEmail = StudentController.validEmail(email);
			if (!vEmail) {
				System.out.println("Invalid email address! Please enter a valid email address.");
			}

		} while (!vEmail);
		newstudent.setEmail(email);

		do {
			System.out.print("Please enter mobile phone numbr of the Student:");
			number = sc.nextLine().trim();
			if (!number.matches("\\b\\d{8}\\b")) {
				System.out.println("Invalid phone number! Please enter a 8 digit number.");
				System.out.print("Enter your new number: ");
			}

		} while (!number.matches("\\b\\d{8}\\b"));
		newstudent.setMobileNo(number);

		System.out.println("Please enter select notification mode for the Student:");
		System.out.println("1. SMS\n" + "2. Email\n" + "3. SMS and Email\n");
		choice = Integer.parseInt(sc.nextLine());

		switch (choice) {
		case 1:
			newstudent.setNotificationType("T1");
			break;
		case 2:
			newstudent.setNotificationType("T2");
			break;
		case 3:
			newstudent.setNotificationType("T3");
			break;
		default:
			System.out.println("Please enter a valid choice");
			choice = Integer.parseInt(sc.nextLine());
		}
		while (choice < 0 || choice > 3)
			;

		boolean isAdded = DBController.addStudentToDB(newstudent);
		if (isAdded)
			System.out.println("Student added successfully.");
		else
			System.out.println("Failed to add student. Make sure all the fields are entered correctly.");
		printAllStudents();
	}

	/**
	 * A method called by an admin to add or update Course.
	 */
	public static void addUpdateCourseAdmin() {
		sc = new Scanner(System.in);

		CourseController.printAllCourses();

		do {
			System.out.println("\nWhat would you like do to:");
			System.out.println("1. Print All Courses\n" + "2. Add Course\n" + "3. Update Existing Course\n"
					+ "4. Delete Course\n" + "5. Cancel and return to main menu");
			while (!sc.hasNextInt()) {
				sc.next();
				System.out.print("Please enter valid option:");
			}
			choice = sc.nextInt();

			switch (choice) {
			case 1:
				CourseController.printAllCourses();
				break;
			case 2:
				CourseController.addCourse();
				break;
			case 3:
				CourseController.updateCourse();
				break;
			case 4:
				CourseController.deleteCourse();
				break;
			case 5:
				return;
			default:
				System.out.println("Please enter a valid choice");
				break;
			}
		} while (choice < 5 || choice > 5);
	}

	/**
	 * A method called by an admin to check vacancy of a entered index number.
	 */
	public static void checkVacancyAdmin() {
		StudentController.checkVacancy();
	}

	/**
	 * A method called by an admin to edit student access period from different
	 * school.
	 */
	public static void editAccessPeriodAdmin() {
		boolean isValid = false, isValidEnd = false, isValidCompare = false;
		String startdate, enddate;
		sc = new Scanner(System.in);
		System.out.println("Select a school to edit access period for student by entering choice:");
		List<String[]> alldata = DBController.getAll("Access_Period");
		System.out.println(
				"+--------------------------------------------------------------------------------------------+");
		System.out.format("|%-7s|%-52s|%-15s|%-15s|%n", "Choice", "School", "Start Date", "End Date");
		System.out.println(
				"+--------------------------------------------------------------------------------------------+");
		for (int i = 1; i < alldata.size(); i++) {
			School sch = DBController.getSchoolByInitial(alldata.get(i)[0]);
			System.out.format("|%-7s|%-52s|%-15s|%-15s|%n", i, sch.getSchoolName(), alldata.get(i)[1],
					alldata.get(i)[2]);
		}
		System.out.println(
				"+--------------------------------------------------------------------------------------------+");
		System.out.println("Enter " + alldata.size() + " to cancel and return to main menu");

		do {
			while (!sc.hasNextInt()) {
				sc.next();
				System.out.println("Please enter valid option:");
			}
			choice = sc.nextInt();
			if (choice <= 0 || choice > alldata.size()) {
				System.out
						.println("Please enter a valid choice or enter " + alldata.size() + " to return to main menu:");
			}
		} while (choice <= 0 || choice > alldata.size());

		if (choice == alldata.size()) {
			System.out.println("Returning to main menu...");
			System.out.println(
					"------------------------------------------------------------------------------------------------------------------------------");
			return;
		} else {
			School sch = DBController.getSchoolByInitial(alldata.get(choice)[0]);
			System.out.println("Editing for " + sch.getSchoolName() + ":");

			sc.nextLine();
			do {

				do {
					System.out.print("Enter new start date and time (dd/MM/yyyy HHmm): ");
					startdate = sc.nextLine();
					isValid = isValidDateFormat(startdate);
				} while (!isValid);

				do {
					System.out.print("Enter new end date and time (dd/MM/yyyy HHmm): ");
					enddate = sc.nextLine();
					isValidEnd = isValidDateFormat(enddate);
				} while (!isValidEnd);
				isValidCompare = isBothValid(startdate, enddate);
			} while (!isValidCompare);
			if (startdate != null & enddate != null) {
				if (DBController.UpdateAccessPeriod(alldata.get(choice)[0], startdate, enddate)) {
					System.out.println("Access period for " + sch.getSchoolName() + " updated successfully.");
					System.out.println(
							"------------------------------------------------------------------------------------------------------------------------------");
				} else {
					System.out.println("Failed to update access period for " + sch.getSchoolName());
					System.out.println(
							"------------------------------------------------------------------------------------------------------------------------------");
				}
			}
		}
	}

	/**
	 * A method used to validate admin's entered start and end date. It will
	 * check if end date is later than start date. Returns true if later if not
	 * return false.
	 * 
	 * @param startdate
	 *            start date.
	 * @param enddate
	 *            end date
	 * @return true or false.
	 */
	public static boolean isBothValid(String startdate, String enddate) {
		DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HHmm");
		try {
			Date dateaccessstart = sdf.parse(startdate);
			Date dateaccesssend = sdf.parse(enddate);
			if (dateaccessstart.compareTo(dateaccesssend) > 0) {
				System.out.println("End date and time must be after start date and time.");
				return false;
			} else
				return true;
		} catch (ParseException e) {
			System.out.println("Plese enter valid date and time in correct format.");
			return false;
		}

	}

	/**
	 * A method used to validate entered data is in correct format. Return true
	 * if valid if not return false.
	 * 
	 * @param value
	 *            entered date.
	 * @return true or false.
	 */
	public static boolean isValidDateFormat(String value) {
		Date dateobj = new Date(); //for getting current date
		try {
			DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HHmm");
			Date date = sdf.parse(value);
			if (dateobj.compareTo(date) < 0) {
				return true;
			} else {
				System.out.println("Plese enter valid date and time that is after current date and time.");
				return false;
			}

		} catch (ParseException ex) {
			System.out.println("Plese enter valid date and time in correct format.");
			return false;
		}
	}

	/**
	 * A method called by an admin to print student list by course.
	 */
	public static void printStudentListByCourseAdmin() {
		Course tempCourse = new Course();
		Student tempStudent = new Student();
		sc = new Scanner(System.in);

		System.out.println("Print Student List By Course");
		System.out.println("---------------------");
		System.out.print("Enter course you wish to view students: ");
		String courseCode = sc.nextLine().toUpperCase();

		tempCourse = DBController.getCourseByCourseCode(courseCode);

		if (tempCourse != null) {
			ArrayList<String> tempIndexes = new ArrayList<String>();
			tempIndexes = DBController.getIndexesByCourseCode(courseCode);

			System.out.println(
					"\nCourse Code\t" + "Course Name\t\t\t\t" + "School Code\t" + "Lesson Type\t" + "Academic Unit");
			System.out.println(
					"-------------------------------------------------------------------------------------------------------------");
			System.out.format("%-16s%-40s%11s\t%11s\t%13s\n", tempCourse.getCourseCode(), tempCourse.getCourseName(),
					tempCourse.getSchool().getSchoolInitial(), tempCourse.getLessonType(), tempCourse.getAu());
			System.out.println(
					"-------------------------------------------------------------------------------------------------------------");

			if (tempIndexes.size() != 0) {
				for (int i = 0; i < tempIndexes.size(); i++) {
					ArrayList<String[]> students = new ArrayList<String[]>();
					students = DBController.getStudentListByIndex(tempIndexes.get(i));

					System.out.println("\nIndex: " + tempIndexes.get(i));

					if (students.size() != 0) {
						System.out.println("+-----------------------------------------------------------+");
						System.out.format("|%-30s|%-7s|%-20s|\n", "Name", "Gender", "Nationality");
						System.out.println("+-----------------------------------------------------------+");
						for (int j = 0; j < students.size(); j++) {
							String[] temp = students.get(j);
							tempStudent = DBController.getStudentByMatric(temp[0]);
							System.out.format("|%-30s|%-7s|%-20s|\n", tempStudent.getName(), tempStudent.getGender(),
									tempStudent.getNationality());
						}
						System.out.println("+-----------------------------------------------------------+");
					} else {
						System.out.println(tempIndexes.get(i) + " does not have any students.");
					}
				}
			} else {
				System.out.println("Error. There is no course index under this course (" + courseCode + ").\n"
						+ "Returning to main menu...\n");
				return;
			}

		} else {
			System.out.println("Error. Course (" + courseCode + ") does not exist.\n" + "Returning to main menu...\n");
			return;
		}
	}

	/**
	 * A method called by an admin to print student list by index number.
	 */
	public static void printStudentListByIndexAdmin() {
		Student tempStudent = new Student();
		sc = new Scanner(System.in);

		System.out.println("Print Student List By Index");
		System.out.println("---------------------");
		System.out.print("Enter index you wish to view students: ");
		String index = sc.nextLine().toUpperCase();

		ArrayList<String[]> students = new ArrayList<String[]>();
		students = DBController.getStudentListByIndex(index);

		if (students.size() != 0) {
			System.out.println(
					"+------------------------------------------------------------------------------------------+");
			System.out.format("|%-14s|%-30s|%-7s|%-30s|%5s|\n", "Matric Number", "Name", "Gender", "Programme", "Year");
			System.out.println(
					"+------------------------------------------------------------------------------------------+");

			for (int i = 0; i < students.size(); i++) {
				String[] temp = students.get(i);
				tempStudent = DBController.getStudentByMatric(temp[0]);

				System.out.format("|%-14s|%-30s|%-7s|%-30s|%5s|\n", tempStudent.getMatricNo(), tempStudent.getName(),
						tempStudent.getGender(), tempStudent.getProgram(), tempStudent.getYear());
			}
			System.out.println(
					"+------------------------------------------------------------------------------------------+");

		} else {
			System.out.println("Error. Course Index (" + index + ") does not have any students.\n"
					+ "Returning to main menu...\n");
			return;
		}
	}

	/**
	 * A method called by AdminController to print students list after adding a
	 * new Student.
	 */
	public static void printAllStudents() {
		ArrayList<Student> students = DBController.getAllStudents();
		System.out.println(
				"+---------------------------------------------------------------------------------------------------------------+");
		System.out.format("|%-10s|%-14s|%-30s|%-7s|%-15s|%-30s|%n", "Username", "Matric Number", "Name", "Gender",
				"Nationality", "Program");
		System.out.println(
				"+---------------------------------------------------------------------------------------------------------------+");
		for (int i = 1; i < students.size(); i++) {
			System.out.format("|%-10s|%-14s|%-30s|%-7s|%-15s|%-30s|%n", students.get(i).getUsername(),
					students.get(i).getMatricNo(), students.get(i).getName(), students.get(i).getGender(),
					students.get(i).getNationality(), students.get(i).getProgram());
		}
		System.out.println(
				"+---------------------------------------------------------------------------------------------------------------+");
	}

}
