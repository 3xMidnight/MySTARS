package myBoundaries;

import java.util.Scanner;

import myControllers.AdminController;
import myControllers.DBController;
import myEntities.Admin;
import myEntities.User;

/**
 * Represents a boundary class for displaying administrator options. It implements Menu.
 * 
 * @author
 *
 */
public class AdminMenu implements Menu {
	/**
	 * Scanner to take in option.
	 */
	private static Scanner scanner = new Scanner(System.in);
	/**
	 * Choice selected by user.
	 */
	private static int choice;

	/**
	 * A method called to display administrator options.
	 * 
	 * @param user
	 *            User who called this method.
	 */
	public static void display(User user) {
		if (user != null) {
			Admin admin = DBController.getAdminByUsername(user.getUsername());
			if (admin != null) {
				System.out.println(
						"------------------------------------------------------------------------------------------------------------------------------");
				System.out.println("Welcome " + admin.getName() + " (" + admin.getStaffNo() + ")");
				System.out.println();
			}
			do {
				System.out.println("Please select one of the options below: ");
				System.out.println("1. Edit Student Access Period");
				System.out.println("2. Add a Student");
				System.out.println("3. Add/Update a course");
				System.out.println("4. Check available slot for an index number");
				System.out.println("5. Print student list by index number");
				System.out.println("6. Print student list by course");
				System.out.println("7. Log out");
				while (!scanner.hasNextInt()) {
					scanner.next();
					System.out.println("Please enter valid option:");
				}
				choice = scanner.nextInt();
				switch (choice) {
				case 1:
					AdminController.editAccessPeriodAdmin();
					break;
				case 2:
					AdminController.addStudentAdmin();
					break;
				case 3:
					AdminController.addUpdateCourseAdmin();
					break;
				case 4:
					AdminController.checkVacancyAdmin();
					break;
				case 5:
					AdminController.printStudentListByIndexAdmin();
					break;
				case 6:
					AdminController.printStudentListByCourseAdmin();
					break;
				case 7:
					STARSApp.logout();
					break;
				default:
					System.out.println("Please enter valid option");
					break;
				}
			} while (choice < 7 || choice > 7);
		} else
			System.out.println("Error. You are not allowed to access this admin menu.");
	}

}
