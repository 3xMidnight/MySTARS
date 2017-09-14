package myControllers;

import java.util.ArrayList;
import java.util.Scanner;

import myEntities.Course;
import myEntities.Index;
import myEntities.Lesson;
import myEntities.WaitList;

/**
 * Represents a controller which take care of Course operation. It consists of
 * course-related function which will be called by AdminController.
 * 
 * @author
 *
 */
public class CourseController {
	private static Scanner sc;

	/**
	 * Add Course to MySTARS.
	 */
	public static void addCourse() {
		Course newCourse = new Course();
		Course tempCourse = new Course();
		sc = new Scanner(System.in);
		int option;

		System.out.println("Add Course");
		System.out.println("---------------------");
		System.out.print("Enter Course Code: ");
		String newCourseCode = sc.nextLine().toUpperCase();
		newCourseCode = newCourseCode.replaceAll("\\s", "");

		tempCourse = DBController.getCourseByCourseCode(newCourseCode);

		if (tempCourse != null) {
			System.out.println("Error. Course already exist.\n" + "Returning to main menu...\n");
			return;
		}

		newCourse.setCourseCode(newCourseCode);

		System.out.print("\nEnter Course Name: ");
		newCourse.setCourseName(sc.nextLine().toUpperCase());

		ArrayList<String> allSchoolCode = new ArrayList<String>();
		allSchoolCode = DBController.getAllSchoolCode();

		do {
			System.out.println("\nChoose School Code: ");

			for (int i = 0; i < allSchoolCode.size(); i++) {
				if (i != 0) {
					System.out.println(i + ". " + allSchoolCode.get(i));
				}
			}

			while (!sc.hasNextInt()) {
				sc.next();
				System.out.print("Please enter valid option:");
			}

			option = sc.nextInt();

			if (option < 1 || option > allSchoolCode.size() - 1)
				System.out.println("Please enter a valid choice");

		} while (option < 1 || option > allSchoolCode.size() - 1);

		newCourse.setSchool(DBController.getSchoolByInitial(allSchoolCode.get(option).toUpperCase()));

		do {
			System.out.println("\nChoose Lesson Type: ");
			System.out.println(
					"1. L1 = Lecture\n" + "2. L2 = Lecture + Tutorial\n" + "3. L3 = Lecture + Tutorial + Laboratory");
			while (!sc.hasNextInt()) {
				sc.next();
				System.out.print("Please enter valid option:");
			}
			option = sc.nextInt();

			switch (option) {
			case 1:
				newCourse.setLessonType("L1");
				break;
			case 2:
				newCourse.setLessonType("L2");
				break;
			case 3:
				newCourse.setLessonType("L3");
				break;
			default:
				System.out.println("Please enter a valid choice");
				break;
			}
		} while (option < 0 || option > 3);

		sc.nextLine();

		System.out.print("\nEnter Acadamic Credits: ");
		while (!sc.hasNextInt()) {
			sc.next();
			System.out.println("Please enter valid option:");
		}
		int au = sc.nextInt();
		newCourse.setAu(Integer.toString(au));

		DBController.addCourse(newCourse);

		option = 1;
		do {
			if (option != 1) {
				System.out.println("Do you want to add another index?");
				System.out.println("1. YES\n" + "2. NO");
				option = sc.nextInt();
			}
			switch (option) {
			case 1:
				CourseController.addCourseIndex(newCourse.getCourseCode());
				break;
			case 2:
				CourseController.printAllCourses();
				return;
			default:
				System.out.println("Please enter a valid choice");
				break;
			}
			option = 0;
		} while (option < 2 || option > 2);
	}

	/**
	 * Add Index for a Course to MySTARS.
	 * 
	 * @param courseCode
	 *            course code of Index to be added.
	 */
	public static void addCourseIndex(String courseCode) {
		Index newIndexCapacity = new Index();
		Lesson newIndexDetails = new Lesson();
		WaitList newWaitList = new WaitList();

		ArrayList<String> temp = new ArrayList<String>();

		sc = new Scanner(System.in);

		System.out.println("\nAdd Course Index");
		System.out.println("---------------------");

		boolean exist = true;
		String newIndex;

		do {
			System.out.print("Enter Course Index: ");
			newIndex = sc.nextLine().toUpperCase();

			temp = DBController.getAllIndexes();

			if (temp.contains(newIndex)) {
				System.out.println("Error. Course already exist.\n" + "Please re-enter...\n");
			} else
				exist = false;

		} while (exist != false);

		// Add Index to Wait List
		newWaitList.setCourseIndex(newIndex);
		newWaitList.setNumberOfStudentWaitList("0");
		DBController.addIndexInWaitList(newWaitList);

		// Add Index to Index Capacity
		newIndexCapacity.setCourseIndex(newIndex);
		newIndexCapacity.setCourseCode(courseCode);

		int size = 0;
		do {
			System.out.print("\nEnter Size: ");
			while (!sc.hasNextInt()) {
				sc.next();
				System.out.print("Please enter valid option:");
			}
			size = sc.nextInt();
		} while (size <= 0);

		newIndexCapacity.setSize(Integer.toString(size));
		newIndexCapacity.setVacancy(Integer.toString(size));
		newIndexCapacity.setNumberOfStudent("0");
		DBController.addIndexCapacity(newIndexCapacity);

		// Add Index to Index Details
		newIndexDetails.setCourseIndex(newIndex);

		String tempLessonType = DBController.getLessonTypeByCourseCode(courseCode);
		int times = 0;

		if (tempLessonType.equals("L1"))
			times = 1;
		else if (tempLessonType.equals("L2"))
			times = 2;
		else if (tempLessonType.equals("L3"))
			times = 3;

		boolean cont = true;
		int optionClass = 1;

		while (cont) {
			if (optionClass <= times) {
				// Choose Class Type
				switch (optionClass) {
				case 1:
					System.out.println("\nClass: LEC/STUDIO");
					newIndexDetails.setClassType("LEC/STUDIO");
					break;
				case 2:
					System.out.println("\nClass: TUT");
					newIndexDetails.setClassType("TUT");
					break;
				case 3:
					System.out.println("\nClass: LAB");
					newIndexDetails.setClassType("LAB");
					break;
				default:
					break;
				}
				optionClass++;
			} else {
				int option = 0;
				do {
					System.out.println("\nDo you want to add another LEC/STUDIO?");
					System.out.println("1. YES\n" + "2. NO");
					while (!sc.hasNextInt()) {
						sc.next();
						System.out.print("Please enter valid option:");
					}
					option = sc.nextInt();
					if (option == 1) {
						newIndexDetails.setClassType("LEC/STUDIO");
					} else if (option == 2) {
						cont = false;
						return;
					} else {
						System.out.println("Please enter a valid choice");
					}
				} while (option < 1 || option > 2);
			}

			newIndexDetails = CourseController.chooseDay(newIndexDetails);

			newIndexDetails.setStarttime(CourseController.chooseTimeSlot(true, ""));
			newIndexDetails.setEndtime(CourseController.chooseTimeSlot(false, newIndexDetails.getStarttime()));

			sc.nextLine();
			System.out.print("\nEnter Venue: ");
			String venue = sc.nextLine().toUpperCase();
			venue = venue.replaceAll("\\s", "");
			newIndexDetails.setVenue(venue);

			newIndexDetails = CourseController.chooseWeek(newIndexDetails);

			sc.nextLine();
			System.out.print("\nEnter Group: ");
			String group = sc.nextLine().toUpperCase();
			group = group.replaceAll("\\s", "");
			newIndexDetails.setGroup(group);

			DBController.addIndexDetails(newIndexDetails);
		}
	}

	/**
	 * Set day of a Lesson.
	 * 
	 * @param newIndexDetails
	 *            Lesson object to be updated.
	 * @return Updated Lesson object.
	 */
	public static Lesson chooseDay(Lesson newIndexDetails) {
		int optionDay;
		sc = new Scanner(System.in);
		do {
			System.out.println("\nChoose Day: ");
			System.out.println("1. Monday\n" + "2. Tuesday\n" + "3. Wednesday\n" + "4. Thursday\n" + "5. Friday");
			while (!sc.hasNextInt()) {
				sc.next();
				System.out.print("Please enter valid option:");
			}
			optionDay = sc.nextInt();

			switch (optionDay) {
			case 1:
				newIndexDetails.setDay("MON");
				break;
			case 2:
				newIndexDetails.setDay("TUE");
				break;
			case 3:
				newIndexDetails.setDay("WED");
				break;
			case 4:
				newIndexDetails.setDay("THU");
				break;
			case 5:
				newIndexDetails.setDay("FRI");
				break;
			default:
				System.out.println("Please enter a valid choice");
				break;
			}
		} while (optionDay < 0 || optionDay > 5);

		return newIndexDetails;
	}

	/**
	 * Set start time or end time of a Lesson.
	 * 
	 * @param startend
	 *            true or false to indicate if it's start time or end time. True
	 *            for start and false for end.
	 * @param time
	 *            "" for choosing start time or selected start time if choosing
	 *            for end time.
	 * @return selected time.
	 */
	public static String chooseTimeSlot(boolean startend, String time) {
		int optionTime;
		sc = new Scanner(System.in);

		String[] timeSlot = { "0830", "0930", "1030", "1130", "1230", "1330", "1430", "1530", "1630", "1730", "1830",
				"1930", "2030" };

		int temp = -1;

		for (int i = 0; i < timeSlot.length; i++) {
			if (timeSlot[i].equals(time))
				temp = i;
		}

		temp++;
		int k = 1;
		do {
			if (startend)
				System.out.println("\nChoose Start Time: ");
			else {
				System.out.println("\nChoose End Time: ");
			}
			k = 1;
			for (int j = temp; j < timeSlot.length; j++) {
				System.out.println((k) + ". " + timeSlot[j]);
				k++;
			}

			while (!sc.hasNextInt()) {
				sc.next();
				System.out.print("Please enter valid option:");
			}
			optionTime = sc.nextInt();

			if (optionTime < 0 || optionTime > k - 1)
				System.out.println("Please enter a valid choice");
		} while (optionTime < 0 || optionTime > k - 1);

		return timeSlot[optionTime - 1 + temp];
	}

	/**
	 * Set week of a Lesson.
	 * 
	 * @param newIndexDetails
	 *            Lesson object to be updated.
	 * @return Updated Lesson object.
	 */
	public static Lesson chooseWeek(Lesson newIndexDetails) {
		int optionWeek;
		sc = new Scanner(System.in);

		do {
			System.out.println("\nChoose Week: ");
			System.out.println("1. ALL\n" + "2. ODD\n" + "3. EVEN\n" + "4. Wk2-13");
			while (!sc.hasNextInt()) {
				sc.next();
				System.out.print("Please enter valid option:");
			}
			optionWeek = sc.nextInt();

			switch (optionWeek) {
			case 1:
				newIndexDetails.setWeek("ALL");
				break;
			case 2:
				newIndexDetails.setWeek("ODD");
				break;
			case 3:
				newIndexDetails.setWeek("EVEN");
				break;
			case 4:
				newIndexDetails.setWeek("Wk2-13");
				break;
			default:
				System.out.println("Please enter a valid choice");
				break;
			}
		} while (optionWeek < 0 || optionWeek > 4);

		return newIndexDetails;
	}

	/**
	 * Delete a course from MySTARS.
	 */
	public static void deleteCourse() {
		Course tempCourse = new Course();
		sc = new Scanner(System.in);
		int option;

		System.out.println("Delete Course");
		System.out.println("---------------------");
		System.out.print("Enter course code of the course you wish to delete: ");
		String courseCode = sc.nextLine().toUpperCase();

		tempCourse = DBController.getCourseByCourseCode(courseCode);

		if (tempCourse != null) {
			CourseController.printCourseByCourseCode(courseCode);
		} else {
			System.out.println("Error. Course (" + courseCode + ") does not exist.\n" + "Returning to main menu...\n");
			return;
		}

		do {
			System.out.println("\nAre you sure you want to delete?");
			System.out.println("1. YES\n" + "2. NO");
			while (!sc.hasNextInt()) {
				sc.next();
				System.out.print("Please enter valid option:");
			}
			option = sc.nextInt();

			switch (option) {
			case 1:
				ArrayList<String> indexes = new ArrayList<String>();
				indexes = DBController.getIndexesByCourseCode(courseCode);

				boolean gotStudent = false;

				for (int i = 0; i < indexes.size(); i++) {
					String noofstud = DBController.getNoOfStudetbyIndex(indexes.get(i));
					if (!noofstud.equals("0"))
						gotStudent = true;
				}

				if (gotStudent == false) {
					for (int i = 0; i < indexes.size(); i++) {
						DBController.deleteWaitListByIndex(indexes.get(i));
						DBController.deleteIndexDetailsByIndex(indexes.get(i));
						DBController.deleteIndexCapacityByIndex(indexes.get(i));

						System.out.println("Course index (" + indexes.get(i) + ") under course (" + courseCode
								+ ") has been deleted.");
					}
					if (DBController.deleteCourse(courseCode))
						System.out.println("Course (" + courseCode + ") has been deleted.");
					else
						System.out.println("Error. Course (" + courseCode + ") cannot be deleted.\n"
								+ "Returning to main menu...\n");
				} else
					System.out.println("Error. Course indexes under course (" + courseCode
							+ ") cannot be deleted as there is still students.\n" + "Returning to main menu...\n");
				break;
			case 2:
				return;
			default:
				System.out.println("Please enter a valid choice");
				break;
			}
		} while (option < 1 || option > 2);
	}

	/**
	 * Print all Courses in MySTARS.
	 */
	public static void printAllCourses() {
		ArrayList<Course> courseList = new ArrayList<Course>();
		courseList = DBController.getAllCourses();

		System.out.println(
				"+-----------------------------------------------------------------------------------------------+");
		System.out.format("|%-12s|%-40s|%15s|%11s|%13s|\n", "Course Code", "Course Name", "School Initial",
				"Lesson Type", "Academic Unit");
		System.out.println(
				"+-----------------------------------------------------------------------------------------------+");

		if (courseList.size() != 0) {
			for (int i = 0; i < courseList.size(); i++) {
				if (i != 0) {
					Course temp = new Course();
					temp = courseList.get(i);
					System.out.format("|%-12s|%-40s|%15s|%11s|%13s|\n", temp.getCourseCode(), temp.getCourseName(),
							temp.getSchool().getSchoolInitial(), temp.getLessonType(), temp.getAu());
				}
			}
			System.out.println(
					"+-----------------------------------------------------------------------------------------------+");
			System.out.println(
					"\nLesson Type\nL1 = Lecture\nL2 = Lecture + Tutorial\nL3 = Lecture + Tutorial + Laboratory\n");
		} else {
			System.out.println("Error. Courses does not exist.\n" + "Returning to main menu...\n");
			return;
		}
	}

	/**
	 * Print Course details of a given course code.
	 * 
	 * @param courseCode
	 *            course code of course to be printed.
	 */
	public static void printCourseByCourseCode(String courseCode) {
		Course course = new Course();
		course = DBController.getCourseByCourseCode(courseCode);

		System.out.println(
				"\nCourse Code\t" + "Course Name\t\t\t\t" + "School Code\t" + "Lesson Type\t" + "Academic Unit");
		System.out.println(
				"-------------------------------------------------------------------------------------------------------------");

		if (course != null) {
			System.out.format("%-16s%-40s%11s\t%11s\t%13s\n", course.getCourseCode(), course.getCourseName(),
					course.getSchool().getSchoolInitial(), course.getLessonType(), course.getAu());
		} else {
			System.out.println("Error. Course (" + courseCode + ") does not exist.\n" + "Returning to main menu...\n");
			return;
		}
	}

	/**
	 * Print Course details under a given school.
	 * 
	 * @param schoolCode
	 *            school initial used to print Courses under it.
	 */
	/*public static void printCourseBySchoolCode(String schoolCode) {
		ArrayList<Course> courseList = new ArrayList<Course>();
		courseList = DBController.getCourseBySchoolCode(schoolCode);
	
		System.out.println(
				"\nCourse Code\t" + "Course Name\t\t\t\t" + "School Code\t" + "Lesson Type\t" + "Academic Unit");
		System.out.println(
				"-------------------------------------------------------------------------------------------------------------");
	
		if (courseList.size() != 0) {
			for (int i = 0; i < courseList.size(); i++) {
				Course temp = new Course();
				temp = courseList.get(i);
				System.out.format("%-16s%-40s%11s\t%11s\t%13s\n", temp.getCourseCode(), temp.getCourseName(),
						temp.getSchool().getSchoolInitial(), temp.getLessonType(), temp.getAu());
			}
		} else {
			System.out.println("Error. Courses does not exist under school code (" + schoolCode + ").\n"
					+ "Returning to main menu...\n");
			return;
		}
	}*/

	/**
	 * Update Academic Unit of a Course.
	 * 
	 * @param courseCode
	 *            course code of Course to be updated.
	 */
	public static void updateAcademicUnit(String courseCode) {
		sc = new Scanner(System.in);

		System.out.print("\nEnter Acadamic Credits: ");
		while (!sc.hasNextInt()) {
			sc.next();
			System.out.println("Please enter valid option:");
		}
		int au = sc.nextInt();

		if (DBController.updateAUInCourse(courseCode, Integer.toString(au))) {
			System.out.println("Academic Unit updated.\n");
		} else {
			System.out.println("Error. Unable to update academic unit.\n");
		}
	}

	/**
	 * Main update Course method.
	 */
	public static void updateCourse() {
		Course tempCourse = new Course();
		sc = new Scanner(System.in);
		int choice;

		System.out.println("Update Course");
		System.out.println("---------------------");
		System.out.print("Enter course code of the course you wish to update: ");
		String courseCode = sc.nextLine().toUpperCase();

		tempCourse = DBController.getCourseByCourseCode(courseCode);

		if (tempCourse != null) {
			CourseController.printCourseByCourseCode(courseCode);

			do {
				System.out.println("\nWhat would you like do update:");
				System.out.println("1. Course Details\n" + "2. Course Indexes\n" + "3. Cancel and return to main menu");
				while (!sc.hasNextInt()) {
					sc.next();
					System.out.print("Please enter valid option:");
				}
				choice = sc.nextInt();

				switch (choice) {
				case 1:
					courseCode = CourseController.updateCourseDetails(courseCode);
					break;
				case 2:
					CourseController.updateCourseIndex(courseCode);
					break;
				case 3:
					return;
				default:
					System.out.println("Please enter a valid choice");
					break;
				}
			} while (choice < 3 || choice > 3);
		} else {
			System.out.println("Error. Course (" + courseCode + ") does not exist.\n" + "Returning to main menu...\n");
			return;
		}
	}

	/**
	 * Update course code of a course.
	 * 
	 * @param courseCode
	 *            old course code to be updated.
	 * @return new course code.
	 */
	public static String updateCourseCode(String courseCode) {
		sc = new Scanner(System.in);

		System.out.print("Enter Course Code: ");
		String newCourseCode = sc.nextLine().toUpperCase();
		newCourseCode = newCourseCode.replaceAll("\\s", "");

		if (courseCode.equals(newCourseCode)) {
			System.out.println("Same course code entered.\n" + "Returning to main menu...\n");
			return courseCode;
		}

		Course tempCourse = DBController.getCourseByCourseCode(newCourseCode);

		if (tempCourse != null) {
			System.out.println("Error. Course code already exist in another course.\n" + "Returning to main menu...\n");
			return courseCode;
		}

		if (DBController.updateCourseCodeInCourse(courseCode, newCourseCode)) {
			if (DBController.updateCourseCodeInIndexCapacity(courseCode, newCourseCode)) {
				System.out.println("Coure code updated.\n");
			} else {
				System.out.println("Error. Unable to update course code under index capacity.\n");
			}
		} else {
			System.out.println("Error. Unable to update course code under course.\n");
		}
		return newCourseCode;
	}

	/**
	 * Update Course details.
	 * 
	 * @param courseCode
	 *            course code of Course to be updated.
	 * @return course code.
	 */
	public static String updateCourseDetails(String courseCode) {
		sc = new Scanner(System.in);
		int option;

		CourseController.printCourseByCourseCode(courseCode);

		do {
			System.out.println("\nWhat would you like do update:");
			System.out.println("1. Course Code\n" + "2. Course Name\n" + "3. School Code\n" + "4. Lesson Type\n"
					+ "5. Academic Unit\n" + "6. Cancel and return to main menu");
			while (!sc.hasNextInt()) {
				sc.next();
				System.out.print("Please enter valid option:");
			}
			option = sc.nextInt();
			switch (option) {
			case 1:
				courseCode = CourseController.updateCourseCode(courseCode);
				break;
			case 2:
				CourseController.updateCourseName(courseCode);
				break;
			case 3:
				CourseController.updateSchoolCode(courseCode);
				break;
			case 4:
				CourseController.updateLessonType(courseCode);
				break;
			case 5:
				CourseController.updateAcademicUnit(courseCode);
				break;
			case 6:
				return courseCode;
			default:
				System.out.println("Please enter a valid choice");
				break;
			}
		} while (option < 6 || option > 6);

		return courseCode;
	}

	/**
	 * Update Index details.
	 * 
	 * @param courseCode
	 *            course code of Index to be updated.
	 */
	public static void updateCourseIndex(String courseCode) {
		sc = new Scanner(System.in);
		int option;
		String newIndex;
		String oldIndex;
		ArrayList<String> temp = new ArrayList<String>();

		do {
			System.out.println("\nWhat would you like do update:");
			System.out.println("1. Course Index\n" + "2. Size\n" + "3. Cancel and return to main menu");
			while (!sc.hasNextInt()) {
				sc.next();
				System.out.print("Please enter valid option:");
			}
			option = sc.nextInt();
			sc.nextLine();
			switch (option) {
			case 1:
				System.out.print("\nEnter Current Course Index: ");
				oldIndex = sc.nextLine().toUpperCase();

				temp = DBController.getAllIndexes();

				if (temp.contains(oldIndex)) {
					System.out.print("\nEnter New Course Index: ");
					newIndex = sc.nextLine().toUpperCase();

					if (newIndex.equals(oldIndex)) {
						System.out.println("Same course code entered.\n" + "Returning to main menu...\n");
						return;
					} else {
						if (DBController.updateIndexInIndexCapacity(oldIndex, newIndex)
								&& DBController.updateIndexInIndexDetails(oldIndex, newIndex)
								&& DBController.updateIndexInWaitList(oldIndex, newIndex)
								&& DBController.updateIndexInCourseRegistration(oldIndex, newIndex))
							System.out.println("Course Index Updated.");
						else
							System.out.println("Error. Unable to update course index.\n");
					}
				} else {
					System.out.println("Error. Course index does not exist.");
				}
				return;
			case 2:
				System.out.print("\nEnter Current Course Index: ");
				oldIndex = sc.nextLine().toUpperCase();

				temp = DBController.getIndexesByCourseCode(courseCode);

				if (temp.contains(oldIndex)) {
					int noOfStudent = Integer.parseInt(DBController.getNoOfStudetbyIndex(oldIndex));

					int size = 0;
					do {
						System.out.print("\nEnter New Course Size: ");
						while (!sc.hasNextInt()) {
							sc.next();
							System.out.print("Please enter valid option:");
						}
						size = sc.nextInt();
					} while (size <= noOfStudent);

					String newSize = Integer.toString(size);
					String newVacancy = Integer.toString(size - noOfStudent);

					if (DBController.updateSizeInIndexCapacity(oldIndex, newSize, newVacancy)) {
						System.out.println("Course Size Updated.");
					} else {
						System.out.println("Error. Unable to update course size.\n");
					}
				} else {
					System.out.println("Error. Course index does not exist.");
				}
				return;
			case 3:
				return;
			default:
				System.out.println("Please enter a valid choice");
				break;
			}
		} while (option < 3 || option > 3);
	}

	/**
	 * Update full name of a Course.
	 * 
	 * @param courseCode
	 *            course code of Course to be updated.
	 */
	public static void updateCourseName(String courseCode) {
		sc = new Scanner(System.in);

		System.out.print("\nEnter new course name: ");
		String newCourseName = sc.nextLine().toUpperCase();

		if (DBController.updateCourseNameInCourse(courseCode, newCourseName)) {
			System.out.println("Course name updated.\n");
		} else {
			System.out.println("Error. Unable to update course name.\n");
		}
	}

	/**
	 * Update Lesson type of a Course.
	 * 
	 * @param courseCode
	 *            course code of Course to be updated.
	 */
	public static void updateLessonType(String courseCode) {
		sc = new Scanner(System.in);
		int option;
		String newLessonType = "";

		do {
			System.out.println("\nChoose Lesson Type: ");
			System.out.println(
					"1. L1 = Lecture\n" + "2. L2 = Lecture + Tutorial\n" + "3. L3 = Lecture + Tutorial + Laboratory");
			while (!sc.hasNextInt()) {
				sc.next();
				System.out.print("Please enter valid option:");
			}
			option = sc.nextInt();

			switch (option) {
			case 1:
				newLessonType = "L1";
				break;
			case 2:
				newLessonType = "L2";
				break;
			case 3:
				newLessonType = "L3";
				break;
			default:
				System.out.println("Please enter a valid choice");
				break;
			}
		} while (option < 0 || option > 3);

		if (DBController.updateLessonTypeInCourse(courseCode, newLessonType)) {
			System.out.println("Lesson Type updated.\n");
		} else {
			System.out.println("Error. Unable to update lesson type.\n");
		}
	}

	/**
	 * Update school code of a course.
	 * 
	 * @param courseCode
	 *            course code of Course to be updated.
	 */
	public static void updateSchoolCode(String courseCode) {
		sc = new Scanner(System.in);
		int option;

		ArrayList<String> allSchoolCode = new ArrayList<String>();
		allSchoolCode = DBController.getAllSchoolCode();

		do {
			System.out.println("\nChoose School Code: ");

			for (int i = 0; i < allSchoolCode.size(); i++) {
				if (i != 0) {
					System.out.println(i + ". " + allSchoolCode.get(i));
				}
			}

			while (!sc.hasNextInt()) {
				sc.next();
				System.out.print("Please enter valid option:");
			}

			option = sc.nextInt();

			if (option < 1 || option > allSchoolCode.size() - 1)
				System.out.println("Please enter a valid choice");

		} while (option < 1 || option > allSchoolCode.size() - 1);

		String newSchoolCode = allSchoolCode.get(option).toUpperCase();

		if (DBController.updateSchoolCodeInCourse(courseCode, newSchoolCode)) {
			System.out.println("School code updated.\n");
		} else {
			System.out.println("Error. Unable to update school code.\n");
		}
	}

}