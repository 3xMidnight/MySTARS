package myControllers;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.MessagingException;

import myBoundaries.STARSApp;
import myEntities.Course;
import myEntities.Index;
import myEntities.Lesson;
import myEntities.Student;

/**
 * Represents a controller which will be used by a student. It contains
 * functions which student can do on MySTARS.
 * 
 * @author
 *
 */
public class StudentController {
	private static Scanner sc;

	/**
	 * A method called by an student to add course.
	 * 
	 * @param accountID
	 *            Student's matriculation number.
	 */
	public static void addCourse(String accountID) {
		String courseType = null;
		String courseIndex;
		String wCourseRegFile = "Course_Registration";
		sc = new Scanner(System.in);

		System.out.print("Enter index of course to add: ");
		courseIndex = sc.nextLine();
		Index courseIndexObj = new Index();
		courseIndexObj = DBController.getCourseIndexByIndex(courseIndex);
		if (courseIndexObj == null) {
			System.out.println("Error. Course index does not exist.\n" + "Returning to main menu...");
			System.out.println(
					"------------------------------------------------------------------------------------------------------------------------------");
			return;
		}

		Course courseObj = new Course();
		courseObj = DBController.getCourseByCourseCode(courseIndexObj.getCourseCode());
		try {
			//Get the list of index for the course and check whether the student is registered in any of it
			ArrayList<String[]> listOfIndex = new ArrayList<String[]>();
			listOfIndex = DBController.getIndexListByCourseCode(courseIndexObj.getCourseCode());
			for (int i = 0; i < listOfIndex.size(); i++) {
				ArrayList<String[]> listOfStudent = new ArrayList<String[]>();
				listOfStudent = DBController.getStudentListByIndex(listOfIndex.get(i)[0]);
				for (int j = 0; j < listOfStudent.size(); j++) {
					if ((listOfStudent.get(j)[0].equals(accountID))) {
						System.out.println(
								"Error. You are already registered for the course.\n" + "Returning to main menu...");
						System.out.println(
								"------------------------------------------------------------------------------------------------------------------------------");
						return;
					}
				}
			}
			//check time clash for new course
			boolean isclash = false;
			ArrayList<String[]> studentCourseList = DBController.getCourseListByStudentID(accountID);
			if (studentCourseList != null) {
				if (studentCourseList.size() != 0) {
					for (int i = 0; i < studentCourseList.size(); i++) {
						String tempindex = studentCourseList.get(i)[0];
						isclash = isTimeClashBetweenIndexes(tempindex, courseIndex);
						if (isclash) {
							System.out.println("You can't add this course index because of day/time clash. \n"
									+ "Returning to main menu...");
							System.out.println(
									"------------------------------------------------------------------------------------------------------------------------------");
							return;
						}
					}
				}

			}
			System.out.println("Select appropriate course type for " + courseIndex);
			System.out.println(
					"1. Core\n" + "2. Prescribed\n" + "3. Unrestricted\n" + "4. Cancel and return to main menu");
			int choice;
			do {
				choice = sc.nextInt();
				switch (choice) {
				case 1:
					courseType = "Core";
					break;
				case 2:
					courseType = "Prescribed";
					break;
				case 3:
					courseType = "Unrestricted";
					break;
				case 4:
					return;
				default:
					System.out.println("Please enter a valid choice");
					break;
				}
			} while (choice < 0 || choice > 4);
			//Check if the course is full
			if (courseIndexObj.getVacancy().equals("0")) {
				System.out.println("Class is full, added to waiting list.\n" + "Returning to main menu...");
				System.out.println(
						"------------------------------------------------------------------------------------------------------------------------------");
				DBController.addToWL(accountID, courseIndex, courseType);
				return;
			}

			String dataLine = accountID + "," + courseIndex + "," + courseType + "," + "NEW";
			String[] dataLineArray = dataLine.split(",");
			DBController.addOneline(wCourseRegFile, dataLineArray);
			DBController.editVacByIndex(courseIndex, '-');

			System.out.println("Registered successfully!");

			System.out.println("Course Name: " + courseObj.getCourseName());
			System.out.println("Course Type: " + courseType);
			System.out.println("Index number: " + courseIndex);
			/*print added Course Index Details. */
			ArrayList<Lesson> indexdetails = DBController.getIndexDetailsByIndex(courseIndex);
			if (indexdetails != null) {
				if (indexdetails.size() != 0) {
					System.out.println("+--------------------------------------------------+");
					System.out.format("|%-11s|%-4s|%-10s|%-7s|%-8s|%-5s|%n", "Type", "Day", "Time", "Venue", "Week",
							"Group");
					System.out.println("+--------------------------------------------------+");
					for (int i = 0; i < indexdetails.size(); i++) {
						System.out.format("|%-11s|%-4s|%-4s-%-5s|%-7s|%-8s|%-5s|%n", indexdetails.get(i).getClassType(),
								indexdetails.get(i).getDay(), indexdetails.get(i).getStarttime(),
								indexdetails.get(i).getEndtime(), indexdetails.get(i).getVenue(),
								indexdetails.get(i).getWeek(), indexdetails.get(i).getGroup());
					}
					System.out.println("+--------------------------------------------------+");
				}
			}
			System.out.println(
					"------------------------------------------------------------------------------------------------------------------------------");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * A method called by an student to change index number of course.
	 * 
	 * @param accountID
	 *            Student's matriculation number.
	 */
	public static void changeIndex(String accountID) {
		boolean registered = false;
		String oldIndex, newIndex;
		String wCourseRegFile = "Course_Registration";

		ArrayList<String[]> studentCourseList = new ArrayList<String[]>();

		studentCourseList = DBController.getCourseListByStudentID(accountID);
		sc = new Scanner(System.in);

		System.out.print("Enter old index number: ");
		oldIndex = sc.nextLine();
		Index oldIndexObj = new Index();
		oldIndexObj = DBController.getCourseIndexByIndex(oldIndex);
		if (oldIndexObj == null) {
			System.out.println("Error. Course index does not exist.\nReturning to main menu...");
			return;
		}
		for (int i = 0; i < studentCourseList.size(); i++) {
			if ((studentCourseList.get(i)[0].equals(oldIndex)) && studentCourseList.get(i)[1].equals("Registered"))
				registered = true;
		}
		if (registered == false) {
			System.out.println("Error. You are not registerd for this index.\nReturning to main menu...");
			return;
		}
		System.out.print("Enter new index number: ");
		newIndex = sc.nextLine();
		Index newIndexObj = new Index();
		newIndexObj = DBController.getCourseIndexByIndex(newIndex);
		if (newIndexObj == null) {
			System.out.println("Error. Course index does not exist.\nReturning to main menu...");
			return;
		} else if (newIndexObj.getVacancy().equals("0")) {
			System.out.println("Error. Course index is full.\nReturning to main menu...");
			return;
		} else if (!oldIndexObj.getCourseCode().equals(newIndexObj.getCourseCode())) {
			System.out.println("Error. Different course code between the two indexes.\nReturning to main menu...");
			return;
		} else if (oldIndex.equals(newIndex)) {
			System.out.println("Error. Entered the same index.\nReturning to main menu...");
			return;
		}
		//check time clash for new index
		boolean isclash = false;
		if (studentCourseList != null) {
			if (studentCourseList.size() != 0) {
				for (int i = 0; i < studentCourseList.size(); i++) {
					String tempindex = studentCourseList.get(i)[0];
					if (!tempindex.equals(oldIndex)) {
						isclash = isTimeClashBetweenIndexes(tempindex, newIndex);
						if (isclash) {
							System.out.println(
									"You can't change to this course index because of day/time clash with the new index. \n"
											+ "Returning to main menu...");
							return;
						}
					}
				}
			}
		}
		String dataLine = accountID + "," + newIndex + "," + DBController.getCourseType(accountID, oldIndex) + ","
				+ "NEW";
		DBController.removeRegCourse(accountID, oldIndex);
		DBController.editVacByIndex(oldIndex, '+');
		String[] dataLineArray = dataLine.split(",");
		DBController.addOneline(wCourseRegFile, dataLineArray);
		DBController.editVacByIndex(newIndex, '-');
		System.out.println("Course index " + oldIndex + " has been changed to " + newIndex + " successfully.");
	}

	/**
	 * A method called by an student to change notification mode.
	 * 
	 * @param accountID
	 *            Student's matriculation number.
	 */
	public static void changeNotification(String accountID) {
		int choice = 0;
		String notiType = null;
		boolean successful;
		sc = new Scanner(System.in);

		System.out.println("Select your notification mode:");
		System.out.println("1. SMS\n" + "2. Email\n" + "3. SMS and Email\n" + "4. Cancel and return to main menu");
		choice = sc.nextInt();
		do {
			switch (choice) {
			case 1:
				notiType = "T1";
				break;
			case 2:
				notiType = "T2";
				break;
			case 3:
				notiType = "T3";
				break;
			case 4:
				System.out.println("Returning to main menu...");
				return;
			default:
				System.out.println("Please enter a valid choice");
				choice = sc.nextInt();
			}
		} while (choice < 0 || choice > 4);
		successful = DBController.changeNotificationDB(accountID, notiType);
		if (successful)
			System.out.println("Changes made successfully!");
		else
			System.out.println("Please try again");
		return;
	}

	/**
	 * A method called by an student to change notification email address or
	 * mobile phone number.
	 * 
	 * @param accountID
	 *            Student's matriculation number.
	 */
	public static void changeNumOrEmail(String accountID) {
		int choice = 0;
		String number, email = null;
		boolean vEmail = false;
		sc = new Scanner(System.in);

		System.out.println("Select the item that you want to change.");
		System.out
				.println("1. Number\n" + "2. Email\n" + "3. Number and Email\n" + "4. Cancel and return to main menu");

		choice = sc.nextInt();

		do {
			switch (choice) {
			case 1:
				System.out.print("Enter your new number: ");
				number = sc.next();
				do {
					if (!number.matches("\\b\\d{8}\\b")) {
						System.out.println("Invalid phone number! Please enter a 8 digit number.");
						System.out.print("Enter your new number: ");
						number = sc.next();
					}

				} while (!number.matches("\\b\\d{8}\\b"));
				DBController.changeNumOrEmailDB(accountID, number, "-");
				System.out.println("Number updated successfully.");
				break;
			case 2:
				do {
					System.out.print("Enter your new email: ");
					email = sc.next();
					vEmail = validEmail(email);
					if (!vEmail) {
						System.out.println("Invalid email address! Please enter a valid email address.");
					}
				} while (!vEmail);
				DBController.changeNumOrEmailDB(accountID, "-", email);
				System.out.println("Email updated successfully.");
				break;
			case 3:
				System.out.print("Enter your new number: ");
				number = sc.next();
				do {
					if (!number.matches("\\b\\d{8}\\b")) {
						System.out.println("Invalid phone number! Please enter a 8 digit number.");
						System.out.print("Enter your new number: ");
						number = sc.next();
					}

				} while (!number.matches("\\b\\d{8}\\b"));
				do {
					System.out.print("Enter your new email: ");
					email = sc.next();
					vEmail = validEmail(email);
					if (!vEmail) {
						System.out.println("Invalid email address! Please enter a valid email address.");
					}

				} while (!vEmail);
				DBController.changeNumOrEmailDB(accountID, number, email);
				System.out.println("Number and Email updated successfully.");
				break;
			case 4:
				System.out.println("Returning to main menu...");
				return;
			default:
				System.out.println("Please enter a valid choice");
				choice = sc.nextInt();
			}
		} while (choice < 0 || choice > 4);

	}

	/**
	 * A method called by an student to check vacancy available by entering an
	 * index number.
	 */
	public static void checkVacancy() {
		sc = new Scanner(System.in);
		String index;
		System.out.print("Enter index number: ");
		index = sc.next();
		Index courseIndexObj = new Index();
		courseIndexObj = DBController.getCourseIndexByIndex(index);
		if (courseIndexObj == null) {
			System.out.println("Invalid course index.\nReturning to main menu...");
			return;
		} else {
			Course courseObj = DBController.getCourseByCourseCode(courseIndexObj.getCourseCode());
			ArrayList<Lesson> indexdetails = DBController.getIndexDetailsByIndex(index);
			if (indexdetails != null) {
				if (indexdetails.size() != 0) {
					System.out.println("Course Name: " + courseObj.getCourseName());
					System.out.println("+--------------------------------------------------+");
					System.out.format("|%-11s|%-4s|%-10s|%-7s|%-8s|%-5s|%n", "Type", "Day", "Time", "Venue", "Week",
							"Group");
					System.out.println("+--------------------------------------------------+");
					for (int i = 0; i < indexdetails.size(); i++) {
						System.out.format("|%-11s|%-4s|%-4s-%-5s|%-7s|%-8s|%-5s|%n", indexdetails.get(i).getClassType(),
								indexdetails.get(i).getDay(), indexdetails.get(i).getStarttime(),
								indexdetails.get(i).getEndtime(), indexdetails.get(i).getVenue(),
								indexdetails.get(i).getWeek(), indexdetails.get(i).getGroup());
					}
					System.out.println("+--------------------------------------------------+");
				}
			}
			System.out.println();
			System.out.println(
					"Total number of slots for " + courseIndexObj.getCourseIndex() + ": " + courseIndexObj.getSize());
			System.out.println("Number of vacancies available for " + courseIndexObj.getCourseIndex() + ": "
					+ courseIndexObj.getVacancy());
			System.out.println(
					"------------------------------------------------------------------------------------------------------------------------------");
		}
	}

	/**
	 * A method called by an student to drop course.
	 * 
	 * @param accountID
	 *            Student's matriculation number.
	 */
	public static void dropCourse(String accountID) {
		String wCourseRegFile = "Course_Registration";
		ArrayList<String[]> studentCourseList = new ArrayList<String[]>();
		studentCourseList = DBController.getCourseListByStudentID(accountID);
		int choice;
		sc = new Scanner(System.in);

		if (studentCourseList.size() != 0) {
			do {
				System.out.println("Select course to drop by entering choice (e.g 1)");
				System.out.println(
						"+----------------------------------------------------------------------------------------------------+");
				System.out.format("|%-7s|%-6s|%-12s|%-60s|%-11s|%n", "Choice", "Index", "Course Code", "Course Name",
						"Status");
				System.out.println(
						"+----------------------------------------------------------------------------------------------------+");
				for (int i = 0; i < studentCourseList.size(); i++) {
					Index temp = new Index();
					temp = DBController.getCourseIndexByIndex(studentCourseList.get(i)[0]);
					Course tempcourse = DBController.getCourseByCourseCode(temp.getCourseCode());
					System.out.format("|%-7s|%-6s|%-12s|%-60s|%-11s|%n", i + 1, temp.getCourseIndex(),
							temp.getCourseCode(), tempcourse.getCourseName(), studentCourseList.get(i)[1]);
				}
				System.out.println(
						"+----------------------------------------------------------------------------------------------------+");
				//if (i == studentCourseList.size() - 1)
				System.out.println("Enter " + (studentCourseList.size() + 1) + " to cancel and return to main menu");
				choice = sc.nextInt();
				if (choice <= 0 || choice > studentCourseList.size() + 1) {
					System.out.println(
							"Please enter a valid choice or press " + (studentCourseList.size() + 1) + " to exit.");
					try {
						TimeUnit.SECONDS.sleep(2);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				if (choice == studentCourseList.size() + 1) {
					System.out.println("Returning to main menu...");
					System.out.println(
							"------------------------------------------------------------------------------------------------------------------------------");
					return;
				}
			} while (choice <= 0 || choice > (studentCourseList.size() + 1));

			if (studentCourseList.get(choice - 1)[1].equals("Registered")) {
				DBController.removeRegCourse(accountID, studentCourseList.get(choice - 1)[0]);
				DBController.editVacByIndex(studentCourseList.get(choice - 1)[0], '+');
				System.out.println("Successfully dropped " + studentCourseList.get(choice - 1)[0] + ".");
				System.out.println(
						"------------------------------------------------------------------------------------------------------------------------------");
				String nextUser;
				nextUser = DBController.firstFromWLByIndex(studentCourseList.get(choice - 1)[0]);
				if (nextUser != null) {
					String dataLine = nextUser.split(":")[0] + "," + studentCourseList.get(choice - 1)[0] + ","
							+ nextUser.split(":")[1] + "," + "NEW";
					String[] dataLineArray = dataLine.split(",");
					DBController.addOneline(wCourseRegFile, dataLineArray);
					DBController.editVacByIndex(studentCourseList.get(choice - 1)[0], '-');

					/* Send email and mobile notifications to next person in the Waitlist*/
					Student stu = DBController.getStudentByMatric(nextUser.split(":")[0]);
					if (stu != null) {
						Index indexobj = DBController.getCourseIndexByIndex(studentCourseList.get(choice - 1)[0]);
						try {
							if (stu.getNotificationType().equals("T1"))
								NotificationController.notifyEmail(stu.getEmail(), indexobj.getCourseCode());
							else if (stu.getNotificationType().equals("T2"))
								NotificationController.notifySMS(stu.getMobileNo(), indexobj.getCourseCode());
							else if (stu.getNotificationType().equals("T3")) {
								NotificationController.notifySMS(stu.getMobileNo(), indexobj.getCourseCode());
								NotificationController.notifyEmail(stu.getEmail(), indexobj.getCourseCode());
							}
						} catch (MessagingException e) {
							e.printStackTrace();
						}
					}
				}
			} else {
				DBController.removeFromWLByID(accountID, studentCourseList.get(choice - 1)[0]);
				System.out.println(
						"Successfully removed " + studentCourseList.get(choice - 1)[0] + " from waiting list.");
				System.out.println(
						"------------------------------------------------------------------------------------------------------------------------------");
			}
		} else
			System.out.println("You currently got no course registered under your account.");

	}

	/**
	 * A method used to check the time clash between two Indexes, return true if
	 * there's a clash if not return false.
	 * 
	 * @param oldindex
	 *            old index number
	 * @param newindex
	 *            new index number
	 * @return true or false.
	 */
	public static boolean isTimeClashBetweenIndexes(String oldindex, String newindex) {
		boolean isClash = false;
		ArrayList<Lesson> list = DBController.getIndexDetailsByIndex(oldindex);
		ArrayList<Lesson> newlist = DBController.getIndexDetailsByIndex(newindex);
		if (list != null & newlist != null) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).isTimeClashBLesson(newlist))
					return true;
			}
		}
		if (isClash)
			return true;
		else
			return false;
	}

	/**
	 * A method called by an student to check/print courses registered.
	 * 
	 * @param accountID
	 *            Student's matriculation number.
	 */
	public static void printRegistered(String accountID) {
		ArrayList<String[]> studentCourseList = new ArrayList<String[]>();
		studentCourseList = DBController.getCourseListByStudentID(accountID);
		int aucount = 0;
		if (studentCourseList != null) {
			if (studentCourseList.size() != 0) {
				System.out.println(
						"+--------------------------------------------------------------------------------------------------------+");
				System.out.format("|%-7s|%-60s|%-3s|%-7s|%-12s|%-10s|%n", "Course", "Course Name", "AU", "Index",
						"Course Type", "Status");
				System.out.println(
						"+--------------------------------------------------------------------------------------------------------+");
				for (int i = 0; i < studentCourseList.size(); i++) {
					Index temp = new Index();
					temp = DBController.getCourseIndexByIndex(studentCourseList.get(i)[0]);
					if (temp != null) {
						Course coursetemp = DBController.getCourseByCourseCode(temp.getCourseCode());
						System.out.format("|%-7s|%-60s|%-3s|%-7s|%-12s|%-10s|%n", temp.getCourseCode(),
								coursetemp.getCourseName(), coursetemp.getAu(), temp.getCourseIndex(),
								studentCourseList.get(i)[2], studentCourseList.get(i)[1]);
						if (studentCourseList.get(i)[1].equals("Registered"))
							aucount += Integer.parseInt(coursetemp.getAu());
					}
				}
				System.out.println(
						"+--------------------------------------------------------------------------------------------------------+");
				System.out.println();
				System.out.println("Total AU registered: " + aucount);
				System.out.println(
						"------------------------------------------------------------------------------------------------------------------------------");
			} else
				System.out.println("You currently got no course registered under your account.");
		}
	}

	/**
	 * A method called by an student to swop index number with another student.
	 * 
	 * @param accountID
	 *            Student's matriculation number.
	 */
	public static void swopIndex(String accountID) {
		sc = new Scanner(System.in);
		String currentUserIndex, otherUserIndex;
		String userName;
		String wCourseRegFile = "Course_Registration";

		boolean registered = false, otherRegistered = false;

		ArrayList<String[]> studentCourseList = new ArrayList<String[]>();

		studentCourseList = DBController.getCourseListByStudentID(accountID);

		System.out.println("Current user Student ID: " + accountID);
		System.out.println("Enter your course index number: ");
		currentUserIndex = sc.nextLine();
		Index currentUserIndexObj = new Index();
		currentUserIndexObj = DBController.getCourseIndexByIndex(currentUserIndex);
		for (int i = 0; i < studentCourseList.size(); i++) {
			if ((studentCourseList.get(i)[0].equals(currentUserIndex))
					&& studentCourseList.get(i)[1].equals("Registered"))
				registered = true;
		}
		if (currentUserIndexObj == null) {
			System.out.println("Error. Course index does not exist.\nReturning to main menu...");
			return;
		} else if (registered == false) {
			System.out.println("Error. You are not registerd for this index.\nReturning to main menu...");
			return;
		}
		System.out.println("Please login to the other student to verify");
		userName = STARSApp.loginReturnUserName();
		if (userName == null) {
			System.out.println("Unable to verify student.\nReturning to main menu...");
			return;
		} else {
			Student otherUser = DBController.getStudentByUsername(userName);
			if (otherUser.getMatricNo().equals(accountID)) {
				System.out.println("Sorry, you cannot swop index with yourself.");
				return;
			}
			ArrayList<String[]> otherStudentCourseList = new ArrayList<String[]>();
			otherStudentCourseList = DBController.getCourseListByStudentID(otherUser.getMatricNo());

			System.out.print("Enter second student course index number: ");
			otherUserIndex = sc.nextLine();
			Index otherUserIndexObj = new Index();
			otherUserIndexObj = DBController.getCourseIndexByIndex(otherUserIndex);
			for (int i = 0; i < otherStudentCourseList.size(); i++) {
				if ((otherStudentCourseList.get(i)[0].equals(otherUserIndex))
						&& otherStudentCourseList.get(i)[1].equals("Registered"))
					otherRegistered = true;
			}
			if (otherUserIndexObj == null) {
				System.out.println("Error. Course index does not exist.\nReturning to main menu...");
				return;
			} else if (otherRegistered == false) {
				System.out
						.println("Error. Second student is not registered for this index.\nReturning to main menu...");
				return;
			} else if (!currentUserIndexObj.getCourseCode().equals(otherUserIndexObj.getCourseCode())) {
				System.out.println("Error. Different course code between the two indexes.\nReturning to main menu...");
				return;
			}

			//check time clash for new index from other student
			boolean isclash = false;
			if (studentCourseList != null) {
				if (studentCourseList.size() != 0) {
					for (int i = 0; i < studentCourseList.size(); i++) {
						String tempindex = studentCourseList.get(i)[0];
						if (!tempindex.equals(currentUserIndex)) {
							isclash = isTimeClashBetweenIndexes(tempindex, otherUserIndex);
							if (isclash) {
								System.out.println(
										"You can't change to this course index because of day/time clash with the index from other student. \n"
												+ "Returning to main menu...");
								return;
							}
						}
					}
				}
			}
			if (otherStudentCourseList != null) {
				if (otherStudentCourseList.size() != 0) {
					for (int i = 0; i < otherStudentCourseList.size(); i++) {
						String tempindex = otherStudentCourseList.get(i)[0];
						if (!tempindex.equals(otherUserIndex)) {
							isclash = isTimeClashBetweenIndexes(tempindex, currentUserIndex);
							if (isclash) {
								System.out.println(
										"You can't change to this course index because the other student has a day/time clash with your index. \n"
												+ "Returning to main menu...");
								return;
							}
						}
					}
				}
			}

			String firstDataLine = accountID + "," + otherUserIndex + ","
					+ DBController.getCourseType(accountID, currentUserIndex) + "," + "NEW";
			String[] firstDataLineArray = firstDataLine.split(",");

			String secondDataLine = otherUser.getMatricNo() + "," + currentUserIndex + ","
					+ DBController.getCourseType(otherUser.getMatricNo(), otherUserIndex) + "," + "NEW";
			String[] secondDataLineArray = secondDataLine.split(",");

			DBController.removeRegCourse(accountID, currentUserIndex);
			DBController.editVacByIndex(currentUserIndex, '+');
			DBController.removeRegCourse(otherUser.getMatricNo(), otherUserIndex);
			DBController.editVacByIndex(otherUserIndex, '+');

			DBController.addOneline(wCourseRegFile, firstDataLineArray);
			DBController.editVacByIndex(otherUserIndex, '-');

			DBController.addOneline(wCourseRegFile, secondDataLineArray);
			DBController.editVacByIndex(currentUserIndex, '-');

			System.out.println("Swap indexes successfully!");
		}

	}

	/**
	 * A method used to validate email address is in correct format. Return true
	 * if valid if not return false.
	 * 
	 * @param email
	 *            email address to be checked.
	 * @return true or false.
	 */
	public static boolean validEmail(String email) {
		Pattern pattern;
		Matcher matcher;
		String email_pattern = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
		pattern = Pattern.compile(email_pattern, Pattern.CASE_INSENSITIVE);
		matcher = pattern.matcher(email);
		return matcher.find();
	}
}
