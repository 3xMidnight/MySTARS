package myEntities;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Represents the access period of MySTARS. An AccessPeriod is tied to a School
 * as the access period for each School is different.
 * 
 * @author
 *
 */
public class AccessPeriod {
	/**
	 * The start date and time of this AccessPeriod.
	 */
	private String accessstartdate;
	/**
	 * The end date and time of this AccessPeriod.
	 */
	private String accessenddate;
	/**
	 * The School which this AccessPeriod is tied to.
	 */
	private School school;

	/**
	 * Gets the end date and time of this AccessPeriod.
	 * 
	 * @return this AccessPeriod's end date and time.
	 */
	public String getAccessenddate() {
		return accessenddate;
	}

	/**
	 * Gets the start date and time of this AccessPeriod.
	 * 
	 * @return this AccessPeriod's start date and time.
	 */
	public String getAccessstartdate() {
		return accessstartdate;
	}

	/**
	 * Gets School of this AccessPeriod.
	 * 
	 * @return this AccessPeriod's School.
	 */
	public School getSchool() {
		return school;
	}

	/**
	 * Checks if current date and time is within this AccessPeriod's start and
	 * end time. Returns true if within this period if not return false.
	 * 
	 * @return true or false.
	 */
	public boolean isAccessPeriod() {
		if (this.accessstartdate != null & this.accessenddate != null) {
			Date dateaccessend = null, dateaccessstart = null;
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy HHmm");
			Date dateobj = new Date(); //for getting current date
			try {
				dateaccessstart = df.parse(this.accessstartdate);
				dateaccessend = df.parse(this.accessenddate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (dateobj.compareTo(dateaccessstart) > 0 && dateobj.compareTo(dateaccessend) < 0) { // Current Date is after startdate and current date is before enddate
				return true;
			} else if (dateobj.compareTo(dateaccessstart) == 0 && dateobj.compareTo(dateaccessend) < 0) { // Current Date is equal to startdate and is before enddate
				return true;
			} else { // not within the access period
				return false;
			}
		} else {
			System.out.println("Sorry, could not get access period for the school. ");
			return false;
		}
	}

	/**
	 * Changes the end date and time of this AccessPeriod.
	 * 
	 * @param accessenddate
	 *            this AccessPeriod's new end date and time.
	 */
	public void setAccessenddate(String accessenddate) {
		this.accessenddate = accessenddate;
	}

	/**
	 * Changes the start date and time of this AccessPeriod.
	 * 
	 * @param accessstartdate
	 *            this AccessPeriod's new start date and time.
	 */
	public void setAccessstartdate(String accessstartdate) {
		this.accessstartdate = accessstartdate;
	}

	/**
	 * Changes the School of this AccessPeriod.
	 * 
	 * @param school
	 *            this AccessPeriod's new School.
	 */
	public void setSchool(School school) {
		this.school = school;
	}

}
