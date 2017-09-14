package myControllers;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import myBoundaries.STARSApp;
import myEntities.AccessPeriod;
import myEntities.Admin;
import myEntities.Course;
import myEntities.Index;
import myEntities.Lesson;
import myEntities.School;
import myEntities.Student;
import myEntities.User;
import myEntities.WaitList;

/**
 * Represents a controller which interact with the database csv file. The
 * operation can be gets, add, update, delete to/from database.
 * 
 * @author
 *
 */
public class DBController {
	/**
	 * Add new Course into the Course database.
	 * 
	 * @param newCourse
	 *            new Course to be added.
	 */
	public static void addCourse(Course newCourse) {
		String[] dataLineArray = new String[5];
		dataLineArray[0] = newCourse.getCourseCode();
		dataLineArray[1] = newCourse.getCourseName();
		dataLineArray[2] = newCourse.getSchool().getSchoolInitial();
		dataLineArray[3] = newCourse.getLessonType();
		dataLineArray[4] = newCourse.getAu();

		if (addOneline("Course", dataLineArray)) {
			System.out.println("Course added sucessfully.");
		} else
			System.out.println("Failed to add course. ");
	}

	/**
	 * Add new Index into the Index_Capacity database.
	 * 
	 * @param newIndexCapacity
	 *            new Index to be added.
	 */
	public static void addIndexCapacity(Index newIndexCapacity) {
		String[] dataLineArray = new String[5];
		dataLineArray[0] = newIndexCapacity.getCourseIndex();
		dataLineArray[1] = newIndexCapacity.getCourseCode();
		dataLineArray[2] = newIndexCapacity.getSize();
		dataLineArray[3] = newIndexCapacity.getNumberOfStudent();
		dataLineArray[4] = newIndexCapacity.getVacancy();

		if (addOneline("Index_Capacity", dataLineArray)) {
			System.out.println("Index " + newIndexCapacity.getCourseIndex() + " added sucessfully.");
		} else
			System.out.println("Failed to add index " + newIndexCapacity.getCourseIndex() + ". ");
	}

	/**
	 * Add new Lesson into the Index_Details database.
	 * 
	 * @param newIndexDetails
	 *            new Lesson to be added.
	 */
	public static void addIndexDetails(Lesson newIndexDetails) {
		String[] dataLineArray = new String[8];
		dataLineArray[0] = newIndexDetails.getCourseIndex();
		dataLineArray[1] = newIndexDetails.getClassType();
		dataLineArray[2] = newIndexDetails.getDay();
		dataLineArray[3] = newIndexDetails.getStarttime();
		dataLineArray[4] = newIndexDetails.getEndtime();
		dataLineArray[5] = newIndexDetails.getVenue();
		dataLineArray[6] = newIndexDetails.getWeek();
		dataLineArray[7] = newIndexDetails.getGroup();

		if (addOneline("Index_Details", dataLineArray)) {
			System.out.println(newIndexDetails.getClassType() + " for " + newIndexDetails.getCourseIndex()
					+ " added sucessfully.");
		} else
			System.out.println("Failed to add " + newIndexDetails.getClassType() + " for "
					+ newIndexDetails.getCourseIndex() + ".");
	}

	/**
	 * Add new WaitList into the Wait_List database.
	 * 
	 * @param newWaitList
	 *            new WaitList to be added.
	 */
	public static void addIndexInWaitList(WaitList newWaitList) {
		String[] dataLineArray = new String[2];
		dataLineArray[0] = newWaitList.getCourseIndex();
		dataLineArray[1] = newWaitList.getNumberOfStudentWaitList();

		if (addOneline("Wait_List", dataLineArray)) {
			// System.out.println("Wait List added sucessfully.");
		} else
			System.out.println("Failed to add Wait List.");
	}

	/**
	 * Append one new line to a database by inputting database filename and
	 * String[] of the line to insert.Returns true if successfully added if not
	 * return false.
	 * 
	 * @param filename
	 *            file name of the datebase.
	 * @param dataLineArray
	 *            entry of content to be added to the database.
	 * @return true or false.
	 */
	public static boolean addOneline(String filename, String[] dataLineArray) {
		try {
			CSVWriter writer = new CSVWriter(new FileWriter("res/" + filename + ".csv", true));
			writer.writeNext(dataLineArray, false);
			writer.close();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {

			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Add new Student into the Student database. Returns true if added
	 * successfully if not return false.
	 * 
	 * @param newStudent
	 *            new Student to be added
	 * @return true or false.
	 */
	public static boolean addStudentToDB(Student newStudent) {
		boolean isUserAdded = addToUserDB(newStudent.getUsername(), newStudent.getPassword(),
				newStudent.getAccountType());
		if (isUserAdded) {
			String[] dataline = new String[12];
			dataline[0] = newStudent.getUsername();
			dataline[1] = newStudent.getMatricNo();
			dataline[2] = newStudent.getName();
			dataline[3] = newStudent.getGender();
			dataline[4] = newStudent.getNationality();
			dataline[5] = newStudent.getYear();
			dataline[6] = newStudent.getProgram();
			dataline[7] = newStudent.getSpecialization();
			dataline[8] = newStudent.getEmail();
			dataline[9] = newStudent.getMobileNo();
			dataline[10] = newStudent.getNotificationType();
			dataline[11] = newStudent.getSchool().getSchoolInitial();

			boolean isStudentAdded = addOneline("Student", dataline);
			if (isStudentAdded)
				return true;
			else
				return false;
		} else
			return false;

	}

	/**
	 * Add new User into the User database. Returns true if added successfully
	 * if not return false.
	 * 
	 * @param username
	 *            new User's username.
	 * @param password
	 *            new User's password
	 * @param role
	 *            new User's role.
	 * @return true or false.
	 */
	public static boolean addToUserDB(String username, String password, char role) {
		String hashedPassword = STARSApp.generateHashedPassword(password);
		String[] dataLineArray = new String[3];
		dataLineArray[0] = username;
		dataLineArray[1] = hashedPassword;
		dataLineArray[2] = Character.toString(role);

		if (addOneline("User", dataLineArray)) {
			return true;
		} else
			return false;
	}

	/**
	 * Add a student into the WaitList database. Returns true if added
	 * successfully if not return false.
	 * 
	 * @param accountID
	 *            Studen's matriculation number.
	 * @param index
	 *            Index number
	 * @param courseType
	 *            Course Type selected
	 * @return true or false.
	 */
	public static boolean addToWL(String accountID, String index, String courseType) {
		CSVReader reader = makeReader("Wait_List");
		ArrayList<String[]> waitList = new ArrayList<String[]>();
		String[] row, temp = null;
		try {
			while ((row = reader.readNext()) != null)
				waitList.add(row);
			for (int i = 0; i < waitList.size(); i++) {
				if (waitList.get(i)[0].equals(index)) {
					int k = 0;
					String[] dataLineArray;
					if (waitList.get(i).length > 2) {
						// Get the entire row and store only all the student indexes in temp
						temp = waitList.get(i)[2].split(";");
						// Used to store all the student indexes other than the one you want to remove
						dataLineArray = new String[temp.length + 1];
						for (int j = 0; j < temp.length; j++) {
							dataLineArray[k] = temp[j];
							k++;
						}
						dataLineArray[k] = accountID + ":" + courseType;
						String[] newDataLine = new String[3];
						newDataLine[0] = waitList.get(i)[0];
						newDataLine[1] = Integer.toString((Integer.parseInt(waitList.get(i)[1])) + 1);
						StringBuilder stringBuilder = new StringBuilder();
						for (int l = 0; l < dataLineArray.length; l++) {
							stringBuilder.append(dataLineArray[l]);
							if (l != dataLineArray.length - 1)
								stringBuilder.append(';');
						}
						newDataLine[2] = stringBuilder.toString();
						waitList.add(newDataLine);
						waitList.remove(i);
						break;
					} else {
						String[] newDataLine = new String[3];
						newDataLine[0] = waitList.get(i)[0];
						newDataLine[1] = Integer.toString((Integer.parseInt(waitList.get(i)[1])) + 1);
						newDataLine[2] = accountID + ":" + courseType;
						waitList.add(newDataLine);
						waitList.remove(i);
						break;
					}
				}
			}
			writeAll("Wait_List", waitList);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Update notification mode for student in the Student database.
	 * 
	 * @param accountID
	 *            Student's matriculation number.
	 * @param newNoti
	 *            The new selected notification type.
	 * @return
	 */
	public static boolean changeNotificationDB(String accountID, String newNoti) {
		CSVReader reader = makeReader("Student");
		String[] row;
		ArrayList<String[]> studentList = new ArrayList<>();
		try {
			while ((row = reader.readNext()) != null) {
				if (row[1].equals(accountID)) {
					String[] temp = new String[12];
					temp[0] = row[0];
					temp[1] = row[1];
					temp[2] = row[2];
					temp[3] = row[3];
					temp[4] = row[4];
					temp[5] = row[5];
					temp[6] = row[6];
					temp[7] = row[7];
					temp[8] = row[8];
					temp[9] = row[9];
					temp[10] = newNoti;
					temp[11] = row[11];
					studentList.add(temp);
				} else
					studentList.add(row);
			}
			writeAll("Student", studentList);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Update student's phone number or email or both in the Student database.
	 * Pass in '-' if do not want to be updated.
	 * 
	 * @param accountID
	 *            Student's matriculation number.
	 * @param number
	 *            Student's new mobile phone number.
	 * @param email
	 *            Student's new email.
	 * @return
	 */
	public static boolean changeNumOrEmailDB(String accountID, String number, String email) {
		CSVReader reader = makeReader("Student");
		String[] row;
		ArrayList<String[]> studentList = new ArrayList<>();
		if ((number.equals("-")) || (email.equals("-"))) {
			if (number.equals("-")) {
				try {
					while ((row = reader.readNext()) != null) {
						if (row[1].equals(accountID)) {
							String[] temp = new String[12];
							temp[0] = row[0];
							temp[1] = row[1];
							temp[2] = row[2];
							temp[3] = row[3];
							temp[4] = row[4];
							temp[5] = row[5];
							temp[6] = row[6];
							temp[7] = row[7];
							temp[8] = email;
							temp[9] = row[9];
							temp[10] = row[10];
							temp[11] = row[11];
							studentList.add(temp);
						} else
							studentList.add(row);
					}
					writeAll("Student", studentList);
					return true;
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				try {
					while ((row = reader.readNext()) != null) {
						if (row[1].equals(accountID)) {
							String[] temp = new String[12];
							temp[0] = row[0];
							temp[1] = row[1];
							temp[2] = row[2];
							temp[3] = row[3];
							temp[4] = row[4];
							temp[5] = row[5];
							temp[6] = row[6];
							temp[7] = row[7];
							temp[8] = row[8];
							temp[9] = number;
							temp[10] = row[10];
							temp[11] = row[11];
							studentList.add(temp);
						} else
							studentList.add(row);
					}
					writeAll("Student", studentList);
					return true;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			try {
				while ((row = reader.readNext()) != null) {
					if (row[1].equals(accountID)) {
						String[] temp = new String[12];
						temp[0] = row[0];
						temp[1] = row[1];
						temp[2] = row[2];
						temp[3] = row[3];
						temp[4] = row[4];
						temp[5] = row[5];
						temp[6] = row[6];
						temp[7] = row[7];
						temp[8] = email;
						temp[9] = number;
						temp[10] = row[10];
						temp[11] = row[11];
						studentList.add(temp);
					} else
						studentList.add(row);
				}
				writeAll("Student", studentList);
				return true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	/**
	 * Delete a course in the Course database with a given course code. Returns
	 * true if successfully deleted if not return false.
	 * 
	 * @param courseCode
	 *            course code to be deleted.
	 * @return true or false.
	 */
	public static boolean deleteCourse(String courseCode) {
		CSVReader reader = makeReader("Course");
		ArrayList<String[]> courseList = new ArrayList<String[]>();
		String[] row;
		try {
			while ((row = reader.readNext()) != null) {
				if (row[0].equals(courseCode))
					continue;
				else {
					courseList.add(row);
				}
			}
			CSVWriter writer = new CSVWriter(new FileWriter("res/Course.csv"));
			writer.writeAll(courseList, false);
			;
			writer.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Delete an Index in the Index_Capacity database with a given Index number.
	 * Returns true if successfully deleted if not return false.
	 * 
	 * @param courseIndex
	 *            The index number to be deleted.
	 * @return true or false.
	 */
	public static boolean deleteIndexCapacityByIndex(String courseIndex) {
		CSVReader reader = makeReader("Index_Capacity");
		ArrayList<String[]> indexCapacity = new ArrayList<String[]>();
		String[] row;
		try {
			while ((row = reader.readNext()) != null) {
				if (row[0].equals(courseIndex))
					continue;
				else {
					indexCapacity.add(row);
				}
			}
			CSVWriter writer = new CSVWriter(new FileWriter("res/Index_Capacity.csv"));
			writer.writeAll(indexCapacity, false);
			writer.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Delete Lessons in the Index_Details database with a give Index number.
	 * Returns true if successfully deleted if not return false.
	 * 
	 * @param courseIndex
	 *            The index number of lessons to be deleted.
	 * @return true or false.
	 */
	public static boolean deleteIndexDetailsByIndex(String courseIndex) {
		CSVReader reader = makeReader("Index_Details");
		ArrayList<String[]> indexDetails = new ArrayList<String[]>();
		String[] row;
		try {
			while ((row = reader.readNext()) != null) {
				if (row[0].equals(courseIndex))
					continue;
				else {
					indexDetails.add(row);
				}
			}
			CSVWriter writer = new CSVWriter(new FileWriter("res/Index_Details.csv"));
			writer.writeAll(indexDetails, false);
			writer.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Delete WaitList entry in the Wait_List database with a give Index number.
	 * Returns true if successfully deleted if not return false.
	 * 
	 * @param courseIndex
	 *            The index number of WaitList entry to be deleted.
	 * @return true or false.
	 */
	public static boolean deleteWaitListByIndex(String courseIndex) {
		CSVReader reader = makeReader("Wait_List");
		ArrayList<String[]> waitList = new ArrayList<String[]>();
		String[] row;
		try {
			while ((row = reader.readNext()) != null) {
				if (row[0].equals(courseIndex))
					continue;
				else {
					waitList.add(row);
				}
			}
			CSVWriter writer = new CSVWriter(new FileWriter("res/Wait_List.csv"));
			writer.writeAll(waitList, false);
			writer.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Update number of a student and vacancy in an Index in the Index_Capacity
	 * database. Returns true if successfully updated if not return false.
	 * 
	 * @param index
	 *            index number of vacancy to be updated.
	 * @param sign
	 *            indicate add or remove number of a student. '-' to add a
	 *            student or '+' to remove a student from index.
	 * @return true or false.
	 */
	public static boolean editVacByIndex(String index, char sign) {
		try {
			String tempCourseIndex = null, tempCourseCode = null, tempSize = null, tempNoOfStud = null, tempVac = null;
			CSVReader reader = makeReader("Index_Capacity");
			ArrayList<String[]> courseIndexList = new ArrayList<String[]>();
			String[] row;
			while ((row = reader.readNext()) != null) {
				if (row[0].equals(index)) {
					tempCourseIndex = row[0];
					tempCourseCode = row[1];
					tempSize = row[2];
					tempNoOfStud = row[3];
					tempVac = row[4];
				} else {
					courseIndexList.add(row);
				}
			}
			reader.close();

			if (sign == '-') {
				int temp = Integer.parseInt(tempVac);
				temp--;
				tempVac = Integer.toString(temp);
				temp = Integer.parseInt(tempNoOfStud);
				temp++;
				tempNoOfStud = Integer.toString(temp);
			} else {
				int temp = Integer.parseInt(tempVac);
				temp++;
				tempVac = Integer.toString(temp);
				temp = Integer.parseInt(tempNoOfStud);
				temp--;
				tempNoOfStud = Integer.toString(temp);
			}

			String dataLine = tempCourseIndex + "," + tempCourseCode + "," + tempSize + "," + tempNoOfStud + ","
					+ tempVac;
			String[] dataLineArray = dataLine.split(",");
			courseIndexList.add(dataLineArray);
			writeAll("Index_Capacity", courseIndexList);
			return true;

		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Gets and delete the next person in the WaitList in the Wait_List
	 * database.
	 * 
	 * @param index
	 *            The index number of the WaitList that want to be deleted.
	 * @return The matriculation number of next person.
	 */
	public static String firstFromWLByIndex(String index) {
		CSVReader reader = makeReader("Wait_List");
		String firstID = null;
		ArrayList<String[]> waitList = new ArrayList<String[]>();
		String[] row;
		try {
			while ((row = reader.readNext()) != null)
				waitList.add(row);
			for (int i = 0; i < waitList.size(); i++) {
				if (waitList.get(i)[0].equals(index)) {

					if (waitList.get(i).length > 2) {
						// Get the entire row and store only all the student indexes in temp
						String[] temp = waitList.get(i)[2].split(";");
						// Used to store all the student indexes other than the one you want to remove
						String[] dataLineArray = new String[temp.length - 1];
						int k = 0;
						for (int j = 0; j < temp.length; j++) {
							if (j == 0) {
								firstID = temp[j];
								continue;
							} else {
								dataLineArray[k] = temp[j];
								k++;
							}
						}
						if (k == 0) {
							String[] newDataLine = new String[2];
							newDataLine[0] = waitList.get(i)[0];
							newDataLine[1] = Integer.toString((Integer.parseInt(waitList.get(i)[1])) - 1);
							waitList.add(newDataLine);
							waitList.remove(i);
							break;
						} else {
							String[] newDataLine = new String[3];
							newDataLine[0] = waitList.get(i)[0];
							newDataLine[1] = Integer.toString((Integer.parseInt(waitList.get(i)[1])) - 1);
							StringBuilder stringBuilder = new StringBuilder();
							for (int l = 0; l < dataLineArray.length; l++) {
								stringBuilder.append(dataLineArray[l]);
								if (l != dataLineArray.length - 1)
									stringBuilder.append(';');
							}
							newDataLine[2] = stringBuilder.toString();
							waitList.add(newDataLine);
							waitList.remove(i);
							break;
						}
					}
				}
			}
			writeAll("Wait_List", waitList);
			return firstID;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return firstID;
	}

	/**
	 * Gets AccessPeriod object for a given school from Access_Period database.
	 * 
	 * @param schoolinitial
	 *            the school initial for the WaitList to retrieve.
	 * @return AccessPeriod object for a given school.
	 */
	public static AccessPeriod getAccessPeriod(String schoolinitial) {
		CSVReader reader = makeReader("Access_Period");
		AccessPeriod periodobj = new AccessPeriod();
		String[] row;
		try {
			while ((row = reader.readNext()) != null) {
				if (row[0].equals(schoolinitial)) {
					periodobj.setSchool(getSchoolByInitial(row[0]));
					periodobj.setAccessstartdate(row[1]);
					periodobj.setAccessenddate(row[2]);
					reader.close();
					return periodobj;
				}
			}
			if (row == null) {
				return null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Gets Admin object for a given username from Admin database.
	 * 
	 * @param username
	 *            Username of Admin to retrieve.
	 * @return Admin object of a given username.
	 */
	public static Admin getAdminByUsername(String username) {
		CSVReader reader = makeReader("Admin");
		Admin admin = new Admin();
		String[] row;
		try {
			while ((row = reader.readNext()) != null) {
				if (row[0].equals(username)) {
					admin.setUsername(row[0]);
					admin.setStaffNo(row[1]);
					admin.setName(row[2]);
					reader.close();
					return admin;
				}
			}
			if (row == null) {
				return null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Gets all content from a database with given database csv filename.
	 * 
	 * @param filename
	 *            file name of database to retrieve from.
	 * @return List of String[] of content.
	 */
	public static List<String[]> getAll(String filename) {
		CSVReader reader = makeReader(filename);
		try {
			List<String[]> output = reader.readAll();
			reader.close();
			return output;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Gets the code of all Courses from the Course database.
	 * 
	 * @return Array list of string of course code.
	 */
	public static ArrayList<String> getAllCourseCode() {
		CSVReader reader = makeReader("Course");
		ArrayList<String> courseCodes = new ArrayList<String>();
		String[] row;
		try {
			while ((row = reader.readNext()) != null) {
				courseCodes.add(row[0]);
			}
			if (row == null) {
				return courseCodes;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Gets all Course objects from the Course database.
	 * 
	 * @return ArrayList of Course objects.
	 */
	public static ArrayList<Course> getAllCourses() {
		CSVReader reader = makeReader("Course");
		ArrayList<Course> list = new ArrayList<Course>();
		String[] row;
		try {
			while ((row = reader.readNext()) != null) {
				Course course = new Course();
				course.setCourseCode(row[0]);
				course.setCourseName(row[1]);
				course.setSchool(getSchoolByInitial(row[2]));
				course.setLessonType(row[3]);
				course.setAu(row[4]);
				list.add(course);
			}
			if (row == null) {
				return list;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Gets all index numbers from the Index_Capacity database.
	 * 
	 * @return ArrayList of index number from all Indexes.
	 */
	public static ArrayList<String> getAllIndexes() {
		CSVReader reader = makeReader("Index_Capacity");
		ArrayList<String> list = new ArrayList<String>();
		String[] row;
		try {
			while ((row = reader.readNext()) != null) {
				list.add(row[0]);
			}
			if (row == null) {
				return list;
			}
		} catch (IOException e) {

			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Gets all School objects from the School database.
	 * 
	 * @return Array of Schools.
	 */
	public static School[] getAllSchoolArray() {
		List<String[]> allSchools = getAll("School");
		School[] schools = new School[allSchools.size() - 1];
		for (int i = 1; i < allSchools.size(); i++) {
			schools[i - 1] = new School();
			schools[i - 1].setSchoolName(allSchools.get(i)[0]);
			schools[i - 1].setSchoolInitial(allSchools.get(i)[1]);
		}
		return schools;

	}

	/**
	 * Gets all the school initial from the School database.
	 * 
	 * @return ArrayList of all school initial.
	 */
	public static ArrayList<String> getAllSchoolCode() {
		CSVReader reader = makeReader("School");
		ArrayList<String> schoolCodes = new ArrayList<String>();
		String[] row;
		try {
			while ((row = reader.readNext()) != null) {
				schoolCodes.add(row[1]);
			}
			if (row == null) {
				return schoolCodes;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Get list of All Student objects from Student database.
	 * 
	 * @return ArrayList of Student objects.
	 */
	public static ArrayList<Student> getAllStudents() {
		CSVReader reader = makeReader("Student");
		ArrayList<Student> students = new ArrayList<Student>();
		String[] row;
		try {
			while ((row = reader.readNext()) != null) {
				Student student = new Student();
				student.setUsername(row[0]);
				student.setMatricNo(row[1]);
				student.setName(row[2]);
				student.setGender(row[3]);
				student.setNationality(row[4]);
				student.setYear(row[5]);
				student.setProgram(row[6]);
				student.setSpecialization(row[7]);
				student.setEmail(row[8]);
				student.setMobileNo(row[9]);
				student.setNotificationType(row[10]);
				student.setSchool(getSchoolByInitial(row[11]));
				students.add(student);
			}
			if (row == null) {
				reader.close();
				return students;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Gets a Course object with given course code from the Course database.
	 * 
	 * @param courseCode
	 *            course code of Course that want to retrieve.
	 * @return Course object for a given course code.
	 */
	public static Course getCourseByCourseCode(String courseCode) {
		CSVReader reader = makeReader("Course");
		Course course = new Course();
		String[] row;
		try {
			while ((row = reader.readNext()) != null) {
				if (row[0].equals(courseCode)) {
					course.setCourseCode(row[0]);
					course.setCourseName(row[1]);
					course.setSchool(getSchoolByInitial(row[2]));
					course.setLessonType(row[3]);
					course.setAu(row[4]);
					return course;
				}
			}
			if (row == null) {
				return null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Gets all Course objects from a given school initial in the Course
	 * database.
	 * 
	 * @param schoolCode
	 *            The school initial of School that want to retrieve Courses
	 *            from.
	 * @return ArrayList of Course objects.
	 */
	public static ArrayList<Course> getCourseBySchoolCode(String schoolCode) {
		CSVReader reader = makeReader("Course");
		Course course = new Course();
		ArrayList<Course> list = new ArrayList<Course>();
		String[] row;
		try {
			while ((row = reader.readNext()) != null) {
				if (row[2].equals(schoolCode)) {
					course.setCourseCode(row[0]);
					course.setCourseName(row[1]);
					course.setSchool(getSchoolByInitial(row[2]));
					course.setLessonType(row[3]);
					course.setAu(row[4]);
					list.add(course);
				}
			}
			if (row == null) {
				return list;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Gets Index objects with a given index number from the Index_Capacity
	 * database.
	 * 
	 * @param courseIndex
	 *            the index number of Index that want to retrieve.
	 * @return Index object of a given index number.
	 */
	public static Index getCourseIndexByIndex(String courseIndex) {
		Index courseindex = new Index();
		CSVReader reader = makeReader("Index_Capacity");
		String[] row;
		try {
			while ((row = reader.readNext()) != null) {
				if (row[0].equals(courseIndex)) {
					courseindex.setCourseIndex(row[0]);
					courseindex.setCourseCode(row[1]);
					courseindex.setSize(row[2]);
					courseindex.setNumberOfStudent(row[3]);
					courseindex.setVacancy(row[4]);
					reader.close();
					return courseindex;
				}
			}
			if (row == null) {
				return null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Gets all the course details which Student has registered and on the
	 * WaitList from the Course_Registration database.
	 * 
	 * @param studentID
	 *            Student's matriculation number.
	 * @return All course details of a given student.
	 */
	public static ArrayList<String[]> getCourseListByStudentID(String studentID) {
		CSVReader reader = makeReader("Course_Registration");
		ArrayList<String[]> studentCourseListDB = new ArrayList<String[]>();
		ArrayList<String[]> studentCourseList = new ArrayList<String[]>();
		String[] row;
		try {
			while ((row = reader.readNext()) != null) {
				if (row[0].equals(studentID))
					studentCourseListDB.add(row);
			}
			reader = makeReader("Wait_List");
			while ((row = reader.readNext()) != null) {
				if (row.length > 2) {
					String[] temp = row[2].split(";");
					for (int i = 0; i < temp.length; i++) {
						if (temp[i].split(":")[0].equals(studentID)) {
							studentCourseListDB.add(row);
						}
					}
				}
			}
			for (int i = 0; i < studentCourseListDB.size(); i++) {
				if (studentCourseListDB.get(i)[0].equals(studentID)) {
					String[] temp = { studentCourseListDB.get(i)[1], "Registered", studentCourseListDB.get(i)[2] };
					studentCourseList.add(temp);
				} else {
					String courseType = null;
					String[] userIDs;
					userIDs = studentCourseListDB.get(i)[2].split(";");
					for (int u = 0; u < userIDs.length; u++) {
						if (userIDs[u].split(":")[0].equals(studentID))
							courseType = userIDs[u].split(":")[1];
					}
					String[] temp = { studentCourseListDB.get(i)[0], "Waitlist", courseType };
					studentCourseList.add(temp);
				}
			}
			return studentCourseList;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Gets course type of a Student's Index from Course_Registration database.
	 * 
	 * @param accountID
	 *            Student's matriculation number.
	 * @param courseIndex
	 *            Student Index.
	 * @return course type.
	 */
	public static String getCourseType(String accountID, String courseIndex) {
		CSVReader reader = makeReader("Course_Registration");
		String[] row;
		try {
			while ((row = reader.readNext()) != null) {
				if ((row[0].equals(accountID)) && (row[1].equals(courseIndex))) {
					return row[2];
				}
			}
		} catch (IOException e) {

			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Gets Lesson objects of a given index number from the Index_Details
	 * database.
	 * 
	 * @param indexNumber
	 *            index number of lessons to retrieve.
	 * @return ArrayList of Lesson objects of a given index.
	 */
	public static ArrayList<Lesson> getIndexDetailsByIndex(String indexNumber) {
		CSVReader reader = makeReader("Index_Details");
		ArrayList<Lesson> indexdetailsarray = new ArrayList<Lesson>();
		String[] row;
		try {
			while ((row = reader.readNext()) != null) {
				if (row[0].equals(indexNumber)) {
					Lesson indexdetails = new Lesson();
					indexdetails.setCourseIndex(row[0]);
					indexdetails.setClassType(row[1]);
					indexdetails.setDay(row[2]);
					indexdetails.setStarttime(row[3]);
					indexdetails.setEndtime(row[4]);
					indexdetails.setVenue(row[5]);
					indexdetails.setWeek(row[6]);
					indexdetails.setGroup(row[7]);
					indexdetailsarray.add(indexdetails);
				}
			}
			if (row == null) {
				reader.close();
				return indexdetailsarray;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Gets a list of index numbers of a given Course from Index_Capacity
	 * database.
	 * 
	 * @param courseCode
	 *            course code of the Course to retrieve index number from.
	 * @return ArrayList of index numbers.
	 */
	public static ArrayList<String> getIndexesByCourseCode(String courseCode) {
		CSVReader reader = makeReader("Index_Capacity");
		ArrayList<String> list = new ArrayList<String>();
		String[] row;
		try {
			while ((row = reader.readNext()) != null) {
				if (row[1].equals(courseCode)) {
					list.add(row[0]);
				}
			}
			if (row == null) {
				return list;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Gets a list of index details of a given Course from Index_Capacity
	 * database.
	 * 
	 * @param courseCode
	 *            course code of the Course to retrieve index details from.
	 * @return ArrayList of Index details of a given course code.
	 */
	public static ArrayList<String[]> getIndexListByCourseCode(String courseCode) {
		CSVReader reader = makeReader("Index_Capacity");
		ArrayList<String[]> list = new ArrayList<String[]>();
		String row[];
		try {
			while ((row = reader.readNext()) != null) {
				if (row[1].equals(courseCode)) {
					list.add(row);
				}
			}
			if (row == null)
				return list;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Gets a list of index details of what a Student is taking from
	 * Course_Registration database.
	 * 
	 * @param accountId
	 *            Student's Matriculation number.
	 * @return ArrayList of Index details of a given Student Matriculation
	 *         Number.
	 */
	public static ArrayList<String[]> getIndexListByStudentID(String accountId) {
		CSVReader reader = makeReader("Course_Registration");
		ArrayList<String[]> list = new ArrayList<String[]>();
		String[] row;
		try {
			while ((row = reader.readNext()) != null) {
				if (row[0].equals(accountId)) {
					list.add(row);
				}
			}
			if (row == null) {
				return list;
			}
		} catch (IOException e) {

			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Get lesson type of a given Course from Course database.
	 * 
	 * @param courseCode
	 *            Course's code.
	 * @return Lesson type of a given Course.
	 */
	public static String getLessonTypeByCourseCode(String courseCode) {
		CSVReader reader = makeReader("Course");
		String[] row;
		try {
			while ((row = reader.readNext()) != null) {
				if (row[0].equals(courseCode)) {
					return row[3];
				}
			}
			if (row == null) {
				return null;
			}
		} catch (IOException e) {

			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Gets number of student of a given Index from Index_Capacity database.
	 * 
	 * @param courseIndex
	 *            index number.
	 * @return number of student of a given index number.
	 */
	public static String getNoOfStudetbyIndex(String courseIndex) {
		CSVReader reader = makeReader("Index_Capacity");
		String[] row;
		try {
			while ((row = reader.readNext()) != null) {
				if (row[0].equals(courseIndex)) {
					return row[3];
				}
			}
			if (row == null) {
				return null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Gets School object of a given school initial from School database.
	 * 
	 * @param schoolInitials
	 *            School's Initials to retrieve
	 * @return School object of a given school initial.
	 */
	public static School getSchoolByInitial(String schoolInitials) {
		School school = new School();
		CSVReader reader = makeReader("School");
		String[] row;
		try {
			while ((row = reader.readNext()) != null) {
				if (row[1].equals(schoolInitials)) {
					school.setSchoolName(row[0]);
					school.setSchoolInitial(row[1]);
					reader.close();
					return school;
				}
			}
			if (row == null) {
				return null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Gets Student object of a given Student matriculation number from Student
	 * database.
	 * 
	 * @param matricno
	 *            Student's matric number.
	 * @return Student object of a given matriculation number.
	 */
	public static Student getStudentByMatric(String matricno) {
		CSVReader reader = makeReader("Student");
		Student student = new Student();
		String[] row;
		try {
			while ((row = reader.readNext()) != null) {
				if (row[1].equals(matricno)) {
					student.setUsername(row[0]);
					student.setMatricNo(row[1]);
					student.setName(row[2]);
					student.setGender(row[3]);
					student.setNationality(row[4]);
					student.setYear(row[5]);
					student.setProgram(row[6]);
					student.setSpecialization(row[7]);
					student.setEmail(row[8]);
					student.setMobileNo(row[9]);
					student.setNotificationType(row[10]);
					student.setSchool(getSchoolByInitial(row[11]));
					reader.close();
					return student;
				}
			}
			if (row == null) {
				return null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Gets Student object of a given Student username from Student database.
	 * 
	 * @param username
	 *            Student's username.
	 * @return Student object of a given username.
	 */
	public static Student getStudentByUsername(String username) {
		CSVReader reader = makeReader("Student");
		Student student = new Student();
		String[] row;
		try {
			while ((row = reader.readNext()) != null) {
				if (row[0].equals(username)) {
					student.setUsername(row[0]);
					student.setMatricNo(row[1]);
					student.setName(row[2]);
					student.setGender(row[3]);
					student.setNationality(row[4]);
					student.setYear(row[5]);
					student.setProgram(row[6]);
					student.setSpecialization(row[7]);
					student.setEmail(row[8]);
					student.setMobileNo(row[9]);
					student.setNotificationType(row[10]);
					student.setSchool(getSchoolByInitial(row[11]));
					reader.close();
					return student;
				}
			}
			if (row == null) {
				return null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Gets list of Students and details taking an Index by a given index number
	 * from Course_Registration database.
	 * 
	 * @param courseIndex
	 *            Index number to retrieve Students.
	 * @return ArrayList of Students and details taking an Index.
	 */
	public static ArrayList<String[]> getStudentListByIndex(String courseIndex) {
		CSVReader reader = makeReader("Course_Registration");
		ArrayList<String[]> list = new ArrayList<String[]>();
		String[] row;
		try {
			while ((row = reader.readNext()) != null) {
				if (row[1].equals(courseIndex)) {
					list.add(row);
				}
			}
			if (row == null) {
				return list;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Gets User object of a given username from User database.
	 * 
	 * @param username
	 *            User's username.
	 * @return User object of a given username.
	 */
	public static User getUserByUsername(String username) {
		CSVReader reader = makeReader("User");
		User user = new User();
		String[] row;
		try {
			while ((row = reader.readNext()) != null) {
				if (row[0].equals(username)) {
					user.setUsername(row[0]);
					user.setPassword(row[1]);
					user.setAccountType(row[2].charAt(0));
					reader.close();
					return user;
				}
			}
			if (row == null) {
				return null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Gets WaitList object for a given index from Wait_List database.
	 * 
	 * @param indexNumber
	 *            Index's index number to retrieve.
	 * @return WaitList object of a given index number.
	 */
	public static WaitList getWaitListByIndex(String indexNumber) {
		WaitList waitlist = new WaitList();
		CSVReader reader = makeReader("Wait_List");
		String[] row;
		try {
			while ((row = reader.readNext()) != null) {
				if (row[0].equals(indexNumber)) {
					waitlist.setCourseIndex(row[0]);
					waitlist.setNumberOfStudentWaitList(row[1]);
					waitlist.setStudentWaitList(row[2].split(";"));
					reader.close();
					return waitlist;
				}
			}
			if (row == null) {
				return null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Create an instance of CSVReader object for a given database filename.
	 * 
	 * @param filename
	 *            file name of the csv database to create.
	 * @return CSVReader object for a given database filename.
	 */
	public static CSVReader makeReader(String filename) {
		try {
			// create csvobject and feed in the directory to open stream
			CSVReader csvReader = new CSVReader(new FileReader("res/" + filename + ".csv"));
			return csvReader;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Delete a Student from Wait_List database with a given Student's
	 * matriculation number and index number. Return true if deleted
	 * successfully if not return false.
	 * 
	 * @param accountID
	 *            Student's matriculation number to delete from.
	 * @param index
	 *            Index number to delete from.
	 * @return true or false.
	 */
	public static boolean removeFromWLByID(String accountID, String index) {
		CSVReader reader = makeReader("Wait_List");
		ArrayList<String[]> waitList = new ArrayList<String[]>();
		String[] row;
		try {
			while ((row = reader.readNext()) != null)
				waitList.add(row);
			for (int i = 0; i < waitList.size(); i++) {
				if (waitList.get(i)[0].equals(index)) {
					// Get the entire row and store only all the student indexes in temp
					String[] temp = waitList.get(i)[2].split(";");
					// Used to store all the student indexes other than the one you want to remove
					String[] dataLineArray = new String[temp.length - 1];
					int k = 0;
					for (int j = 0; j < temp.length; j++) {
						if ((temp[j]).split(":")[0].equals(accountID))
							continue;
						else {
							dataLineArray[k] = temp[j];
							k++;
						}
					}
					if (k == 0) {
						String[] newDataLine = new String[2];
						newDataLine[0] = waitList.get(i)[0];
						newDataLine[1] = Integer.toString((Integer.parseInt(waitList.get(i)[1])) - 1);
						waitList.add(newDataLine);
						waitList.remove(i);
						break;
					} else {
						String[] newDataLine = new String[3];
						newDataLine[0] = waitList.get(i)[0];
						newDataLine[1] = Integer.toString((Integer.parseInt(waitList.get(i)[1])) - 1);
						StringBuilder stringBuilder = new StringBuilder();
						for (int l = 0; l < dataLineArray.length; l++) {
							stringBuilder.append(dataLineArray[l]);
							if (l != dataLineArray.length - 1)
								stringBuilder.append(';');
						}
						newDataLine[2] = stringBuilder.toString();
						waitList.add(newDataLine);
						waitList.remove(i);
						break;
					}
				}
			}
			writeAll("Wait_List", waitList);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Delete a CourseRegistation from Course_Registration database with a given
	 * Student's matriculation number and corresponding Index number. Return
	 * true if deleted successfully if not return false.
	 * 
	 * @param accountID
	 *            Student's matriculation number to delete.
	 * @param index
	 *            Index number to delete.
	 * @return true or false.
	 */
	public static boolean removeRegCourse(String accountID, String index) {
		CSVReader reader = makeReader("Course_Registration");
		ArrayList<String[]> courseRegList = new ArrayList<String[]>();
		String[] row;
		try {
			while ((row = reader.readNext()) != null) {
				if (row[0].equals(accountID) && row[1].equals(index))
					continue;
				else {
					courseRegList.add(row);
				}

			}
			writeAll("Course_Registration", courseRegList);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Update start date or end date of AccessPeriod for a school in the
	 * Access_Period database. Return true if updated successfully if not return
	 * false.
	 * 
	 * @param schoolInitial
	 *            School's initial to update.
	 * @param startdate
	 *            new start date.
	 * @param enddate
	 *            new end date.
	 * @return true or false.
	 */
	public static boolean UpdateAccessPeriod(String schoolInitial, String startdate, String enddate) {
		CSVReader reader = makeReader("Access_Period");
		ArrayList<String[]> accessPeriodArray = new ArrayList<String[]>();
		String[] row;
		try {
			while ((row = reader.readNext()) != null) {
				if (row[0].equals(schoolInitial)) {
					String updatedrowstring = schoolInitial + "," + startdate + "," + enddate;
					String[] updatedrowarray = updatedrowstring.split(",");
					accessPeriodArray.add(updatedrowarray);
				} else {
					accessPeriodArray.add(row);
				}
			}
			reader.close();
			return writeAll("Access_Period", accessPeriodArray);
		} catch (IOException e) {
			return false;
		}
	}

	/**
	 * Update academic unit of a Course in the Course database. Returns true if
	 * updated successfully if not return false.
	 * 
	 * @param courseCode
	 *            course code to update.
	 * @param newAU
	 *            new academic unit.
	 * @return true or false.
	 */
	public static boolean updateAUInCourse(String courseCode, String newAU) {
		CSVReader reader = makeReader("Course");
		ArrayList<String[]> list = new ArrayList<String[]>();
		String[] row;
		try {
			while ((row = reader.readNext()) != null) {
				String[] course = new String[5];
				if (row[0].equals(courseCode)) {
					course[4] = newAU;
				} else {
					course[4] = row[4];
				}
				course[0] = row[0];
				course[1] = row[1];
				course[2] = row[2];
				course[3] = row[3];
				list.add(course);
			}
			writeAll("Course", list);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Update course code of a Course in the Course database. Returns true if
	 * updated successfully if not return false.
	 * 
	 * @param courseCode
	 *            old course code.
	 * @param newCourseCode
	 *            new course code.
	 * @return true or false.
	 */
	public static boolean updateCourseCodeInCourse(String courseCode, String newCourseCode) {
		CSVReader reader = makeReader("Course");
		ArrayList<String[]> list = new ArrayList<String[]>();
		String[] row;
		try {
			while ((row = reader.readNext()) != null) {
				String[] course = new String[5];
				if (row[0].equals(courseCode)) {
					course[0] = newCourseCode;
				} else {
					course[0] = row[0];
				}
				course[1] = row[1];
				course[2] = row[2];
				course[3] = row[3];
				course[4] = row[4];
				list.add(course);
			}
			writeAll("Course", list);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Update course code of a Course in the Index_Capacity database. Returns
	 * true if updated successfully if not return false.
	 * 
	 * @param courseCode
	 *            old course code.
	 * @param newCourseCode
	 *            new course code.
	 * @return true or false.
	 */
	public static boolean updateCourseCodeInIndexCapacity(String courseCode, String newCourseCode) {
		CSVReader reader = makeReader("Index_Capacity");
		ArrayList<String[]> list = new ArrayList<String[]>();
		String[] row;
		try {
			while ((row = reader.readNext()) != null) {
				String[] indexCapacity = new String[5];
				if (row[1].equals(courseCode)) {
					indexCapacity[1] = newCourseCode;
				} else {
					indexCapacity[1] = row[1];
				}
				indexCapacity[0] = row[0];
				indexCapacity[2] = row[2];
				indexCapacity[3] = row[3];
				indexCapacity[4] = row[4];
				list.add(indexCapacity);
			}
			writeAll("Index_Capacity", list);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Update full name of a Course in the Course database. Returns true if
	 * updated successfully if not return false.
	 * 
	 * @param courseCode
	 *            course code to update
	 * @param newCourseName
	 *            new course full name.
	 * @return true or false.
	 */
	public static boolean updateCourseNameInCourse(String courseCode, String newCourseName) {
		CSVReader reader = makeReader("Course");
		ArrayList<String[]> list = new ArrayList<String[]>();
		String[] row;
		try {
			while ((row = reader.readNext()) != null) {
				String[] course = new String[5];
				if (row[0].equals(courseCode)) {
					course[1] = newCourseName;
				} else {
					course[1] = row[1];
				}
				course[0] = row[0];
				course[2] = row[2];
				course[3] = row[3];
				course[4] = row[4];
				list.add(course);
			}
			writeAll("Course", list);
			return true;
		} catch (IOException e) {

			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Update Index number in the Course_Registration database. Returns true if
	 * updated successfully if not return false.
	 * 
	 * @param index
	 *            old index number.
	 * @param newIndex
	 *            new index number.
	 * @return true or false.
	 */
	public static boolean updateIndexInCourseRegistration(String index, String newIndex) {
		CSVReader reader = makeReader("Course_Registration");
		ArrayList<String[]> list = new ArrayList<String[]>();
		String[] row;
		try {
			while ((row = reader.readNext()) != null) {
				String[] courseRegistration = new String[3];
				if (row[1].equals(index)) {
					courseRegistration[1] = newIndex;
				} else {
					courseRegistration[1] = row[1];
				}
				courseRegistration[0] = row[0];
				courseRegistration[2] = row[2];
				list.add(courseRegistration);
			}
			writeAll("Course_Registration", list);
			return true;
		} catch (IOException e) {

			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Update Index number in the Index_Capacity database. Returns true if
	 * updated successfully if not return false.
	 * 
	 * @param index
	 *            old index number.
	 * @param newIndex
	 *            new index number.
	 * @return true or false.
	 */
	public static boolean updateIndexInIndexCapacity(String index, String newIndex) {
		CSVReader reader = makeReader("Index_Capacity");
		ArrayList<String[]> list = new ArrayList<String[]>();
		String[] row;
		try {
			while ((row = reader.readNext()) != null) {
				String[] indexCapacity = new String[5];
				if (row[0].equals(index)) {
					indexCapacity[0] = newIndex;
				} else {
					indexCapacity[0] = row[0];
				}
				indexCapacity[1] = row[1];
				indexCapacity[2] = row[2];
				indexCapacity[3] = row[3];
				indexCapacity[4] = row[4];
				list.add(indexCapacity);
			}
			writeAll("Index_Capacity", list);
			return true;
		} catch (IOException e) {

			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Update Index number in the Index_Details database. Returns true if
	 * updated successfully if not return false.
	 * 
	 * @param index
	 *            old index number.
	 * @param newIndex
	 *            new index number.
	 * @return true or false.
	 */
	public static boolean updateIndexInIndexDetails(String index, String newIndex) {
		CSVReader reader = makeReader("Index_Details");
		ArrayList<String[]> list = new ArrayList<String[]>();
		String[] row;
		try {
			while ((row = reader.readNext()) != null) {
				String[] indexDetails = new String[7];
				if (row[0].equals(index)) {
					indexDetails[0] = newIndex;
				} else {
					indexDetails[0] = row[0];
				}
				indexDetails[1] = row[1];
				indexDetails[2] = row[2];
				indexDetails[3] = row[3];
				indexDetails[4] = row[4];
				indexDetails[5] = row[5];
				indexDetails[6] = row[6];
				indexDetails[7] = row[7];
				list.add(indexDetails);
			}
			writeAll("Index_Details", list);
			return true;
		} catch (IOException e) {

			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Update Index number in the Wait_List database. Returns true if updated
	 * successfully if not return false.
	 * 
	 * @param index
	 *            old index number.
	 * @param newIndex
	 *            new index number.
	 * @return true or false.
	 */
	public static boolean updateIndexInWaitList(String index, String newIndex) {
		CSVReader reader = makeReader("Wait_List");
		ArrayList<String[]> list = new ArrayList<String[]>();
		String[] row;
		try {
			while ((row = reader.readNext()) != null) {
				String[] waitList = new String[2];
				if (row[0].equals(index)) {
					waitList[0] = newIndex;
				} else {
					waitList[0] = row[0];
				}
				waitList[1] = row[1];
				list.add(waitList);
			}
			writeAll("Wait_List", list);
			return true;
		} catch (IOException e) {

			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Update lesson type in the Course database. Returns true if updated
	 * successfully if not return false.
	 * 
	 * @param courseCode
	 *            course code to be updated.
	 * @param newLessonType
	 *            new lesson type.
	 * @return true or false.
	 */
	public static boolean updateLessonTypeInCourse(String courseCode, String newLessonType) {
		CSVReader reader = makeReader("Course");
		ArrayList<String[]> list = new ArrayList<String[]>();
		String[] row;
		try {
			while ((row = reader.readNext()) != null) {
				String[] course = new String[5];
				if (row[0].equals(courseCode)) {
					course[3] = newLessonType;
				} else {
					course[3] = row[3];
				}
				course[0] = row[0];
				course[1] = row[1];
				course[2] = row[2];
				course[4] = row[4];
				list.add(course);
			}
			writeAll("Course", list);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Update school initial in the Course database. Returns true if updated
	 * successfully if not return false.
	 * 
	 * @param courseCode
	 *            course code to be updated.
	 * @param newSchoolCode
	 *            new school initial.
	 * @return true or false.
	 */
	public static boolean updateSchoolCodeInCourse(String courseCode, String newSchoolCode) {
		CSVReader reader = makeReader("Course");
		ArrayList<String[]> list = new ArrayList<String[]>();
		String[] row;
		try {
			while ((row = reader.readNext()) != null) {
				String[] course = new String[5];
				if (row[0].equals(courseCode)) {
					course[2] = newSchoolCode;
				} else {
					course[2] = row[2];
				}
				course[0] = row[0];
				course[1] = row[1];
				course[3] = row[3];
				course[4] = row[4];
				list.add(course);
			}
			writeAll("Course", list);
			return true;
		} catch (IOException e) {

			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Update size and number of empty slot available for an Index in the
	 * Index_Capacity database.
	 * 
	 * @param index
	 *            Index number to be updated.
	 * @param newSize
	 *            new size.
	 * @param newVacancy
	 *            new vacancy.
	 * @return true or false.
	 */
	public static boolean updateSizeInIndexCapacity(String index, String newSize, String newVacancy) {
		CSVReader reader = makeReader("Index_Capacity");
		ArrayList<String[]> list = new ArrayList<String[]>();
		String[] row;
		try {
			while ((row = reader.readNext()) != null) {
				String[] indexCapacity = new String[5];
				if (row[0].equals(index)) {
					indexCapacity[2] = newSize;
					indexCapacity[4] = newVacancy;
				} else {
					indexCapacity[2] = row[2];
					indexCapacity[4] = row[4];
				}
				indexCapacity[0] = row[0];
				indexCapacity[1] = row[1];
				indexCapacity[3] = row[3];
				list.add(indexCapacity);
			}
			writeAll("Index_Capacity", list);
			return true;
		} catch (IOException e) {

			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Validate if given username and password is the same as the one in the
	 * database. It will first try to retrieve User from the database. Then it
	 * will ask the User object to check the password entered by user is
	 * correct. Returns the User object if successfully validated else return
	 * null.
	 * 
	 * @param userName
	 *            User entered username.
	 * @param passWord
	 *            User entered password.
	 * @return User object of the given username.
	 */
	public static User validateUser(String userName, String passWord) {
		User user = DBController.getUserByUsername(userName);
		if (user != null) {
			if (user.validate(passWord))
				return user;
			else
				return null;
		} else
			return null;
	}

	/**
	 * Replace all old content in a database. Returns true if successfully
	 * written else return false.
	 * 
	 * @param filename
	 *            file name of the csv databse to write to.
	 * @param dataLineArray
	 *            new content to be written to the database.
	 * @return true or false.
	 */
	public static boolean writeAll(String filename, List<String[]> dataLineArray) {
		try {
			CSVWriter writer = new CSVWriter(new FileWriter("res/" + filename + ".csv"));
			writer.writeAll(dataLineArray, false);
			writer.close();
			return true;
		} catch (FileNotFoundException e) {
			return false;
		} catch (IOException e) {
			return false;
		}
	}
}
