package myBoundaries;

import java.util.Scanner;

import myControllers.DBController;
import myControllers.StudentController;
import myEntities.AccessPeriod;
import myEntities.Student;
import myEntities.User;

/**
 * Represents a boundary class for displaying student options. It implements
 * Menu.
 * 
 * @author
 *
 */
public class StudentMenu implements Menu {
	/**
	 * Scanner to take in option.
	 */
	private static Scanner scanner = new Scanner(System.in);
	/**
	 * Choice selected by user.
	 */
	private static int choice;

	/**
	 * A method called to display student options.
	 * 
	 * @param user
	 *            User who called this method.
	 */
	public static void display(User user) {
		if (user != null) {
			Student student = DBController.getStudentByUsername(user.getUsername());
			AccessPeriod accessperiod = DBController.getAccessPeriod(student.getSchool().getSchoolInitial());
			if (accessperiod.isAccessPeriod()) {
				System.out.println(
						"------------------------------------------------------------------------------------------------------------------------------");
				System.out.format("%-5s %-45s\t%-14s %-9s\t%n", "Name:", student.getName(), "Matric Number:",
						student.getMatricNo());
				System.out.format("%-18s %-32s\t%-11s %-1s\t%n", "Current Programme:", student.getProgram(),
						"Study Year:", student.getYear());
				System.out.println("Specialisation: " + student.getSpecialization());
				System.out.println("");
				if (student != null) {
					do {
						System.out.println("Please select one of the options below: ");
						System.out.println("1. Register Course");
						System.out.println("2. Drop Course");
						System.out.println("3. Check / Print Courses Registered");
						System.out.println("4. Check Vacancies Available");
						System.out.println("5. Change Index Number of Course");
						System.out.println("6. Swop Index Number with Another Student");
						System.out.println("7. Change notification mode");
						System.out.println("8. Change Number or Email");
						System.out.println("9. Log out");
						while (!scanner.hasNextInt()) {
							scanner.next();
							System.out.println("Please enter valid option:");
						}
						choice = scanner.nextInt();

						switch (choice) {
						case 1:
							StudentController.addCourse(student.getMatricNo());
							break;
						case 2:
							StudentController.dropCourse(student.getMatricNo());
							break;
						case 3:
							StudentController.printRegistered(student.getMatricNo());
							break;
						case 4:
							StudentController.checkVacancy();
							break;
						case 5:
							StudentController.changeIndex(student.getMatricNo());
							break;
						case 6:
							StudentController.swopIndex(student.getMatricNo());
							break;
						case 7:
							StudentController.changeNotification(student.getMatricNo());
							break;
						case 8:
							StudentController.changeNumOrEmail(student.getMatricNo());
							break;
						case 9:
							STARSApp.logout();
							break;
						default:
							System.out.println("Please enter valid option:");
							break;
						}
					} while (choice < 0 || choice > 0);
				}
			} else {
				System.out.println("You are not allowed to access MySTARS during this period.");
				STARSApp.logout();
			}
		} else
			System.out.println("Error. You are not allowed to access this student menu.");
	}

}
