package myControllers;

import java.io.FileReader;
import java.util.Date;
import java.util.Properties;

import javax.mail.internet.*;

import com.opencsv.CSVReader;

import myEntities.Course;

import javax.mail.*;

/**
 * Represents a controller which take care of notifying students on course
 * successfully registered.
 * 
 * @author
 *
 */
public class NotificationController {
	/**
	 * A method used to send email to student.
	 * 
	 * @param email
	 *            student's email address.
	 * @param courseCode
	 *            course code of the course registered.
	 * @throws AddressException
	 * @throws MessagingException
	 */
	public static void notifyEmail(String email, String courseCode) throws AddressException, MessagingException {
		Course course = DBController.getCourseByCourseCode(courseCode);// create course object from coursecode
		try {
			CSVReader reader = new CSVReader(new FileReader("cfg/Email_Cfg.csv"));// read from DB
			String[] authenticator;
			authenticator = reader.readNext();// storing the details
			Properties properties = new Properties();// email setup here
			properties.put("mail.smtp.auth", "true");
			properties.put("mail.smtp.host", authenticator[0]);
			properties.put("mail.smtp.port", "587");
			properties.put("mail.smtp.starttls.enable", "true");
			Session emailSession = Session.getInstance(properties, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(authenticator[1], authenticator[2]);
				}
			});
			MimeMessage msg = new MimeMessage(emailSession);// create email messaging object
			msg.setFrom(new InternetAddress(authenticator[1]));// setting of email content and parameters here
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
			msg.setSubject("[" + course.getCourseCode() + "] Waitlist notification");
			msg.setSentDate(new Date());
			msg.setText("You have been registered to " + course.getCourseCode() + " " + course.getCourseName() + " in "
					+ course.getSchool().getSchoolInitial());
			Transport.send(msg);// sending of message
			course = null;// closing the course object
			reader.close();// closing of CSVReader
			System.out.println("A email has been sent to " + email);
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * A method used to send sms to student. Full functionality to be added in
	 * the future.
	 * 
	 * @param mobileNo
	 *            student's phone number.
	 * @param courseCode
	 *            course code of the course registered.
	 */
	public static void notifySMS(String mobileNo, String courseCode) {
		Course course = DBController.getCourseByCourseCode(courseCode);// create
																		// course
																		// object
		System.out.println("A SMS is sent to " + mobileNo);// sending the sms
		System.out.println("SMS Content : " + "You have been registered to " + course.getCourseCode() + " "
				+ course.getCourseName() + " in " + course.getSchool().getSchoolInitial());// sms
																																																// content
		course = null;// closing of student object
	}
}
