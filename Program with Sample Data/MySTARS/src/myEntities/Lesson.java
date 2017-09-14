package myEntities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Represents a Lesson which an Index has.
 * 
 * @author
 *
 */
public class Lesson {
	/**
	 * The index number which this Lesson belongs to.
	 */
	private String courseIndex;
	/**
	 * The class type of this Lesson. It can be lecture, tutorial or laboratory.
	 */
	private String classType;
	/**
	 * The day of this Lesson is held.
	 */
	private String day;
	/**
	 * The start time of this Lesson.
	 */
	private String starttime;
	/**
	 * The end time of this Lesson.
	 */
	private String endtime;
	/**
	 * The venue of this Lesson is held.
	 */
	private String venue;
	/**
	 * The week of this Lesson is held. A Lesson can be set to held on every
	 * week, odd/even week or from Week2-13.
	 */
	private String week;
	/**
	 * The group code given to this Lesson.
	 */
	private String group;
	/**
	 * The Index which this Lesson belongs to.
	 */
	private Index index;

	/**
	 * Gets the class type of this Lesson.
	 * 
	 * @return this Lesson's class type.
	 */
	public String getClassType() {
		return classType;
	}

	/**
	 * Gets the index number of this Lesson belongs to.
	 * 
	 * @return this Lesson's index number.
	 */
	public String getCourseIndex() {
		return courseIndex;
	}

	/**
	 * Gets the day which this lesson is held.
	 * 
	 * @return this Lesson's day.
	 */
	public String getDay() {
		return day;
	}

	/**
	 * Gets the end time of this Lesson.
	 * 
	 * @return this Lesson's end time.
	 */
	public String getEndtime() {
		return endtime;
	}

	/**
	 * Gets the group code which is give to this Lesson.
	 * 
	 * @return this Lesson's group code.
	 */
	public String getGroup() {
		return group;
	}

	/**
	 * Gets Index of this Lesson belongs to.
	 * 
	 * @return this Lesson's Index.
	 */
	public Index getIndex() {
		return index;
	}

	/**
	 * Gets the start time of this Lesson.
	 * 
	 * @return this Lesson's start time.
	 */
	public String getStarttime() {
		return starttime;
	}

	/**
	 * Gets the venue of this Lesson is held.
	 * 
	 * @return this Lesson's venue.
	 */
	public String getVenue() {
		return venue;
	}

	/**
	 * Gets the week this Lesson is held.
	 * 
	 * @return this Lesson's week.
	 */
	public String getWeek() {
		return week;
	}

	/**
	 * Checks against Lesson object array to determine if any time clash. If
	 * there is a time clash return true if not return false.
	 * 
	 * @param newlist
	 *            the Lesson array that want to compare with.
	 * @return true or false.
	 */
	public boolean isTimeClashBLesson(ArrayList<Lesson> newlist) {
		boolean isClash = false;
		try {
			Date thisstarttime = new SimpleDateFormat("HHmm").parse(this.getStarttime());
			Date thisendtime = new SimpleDateFormat("HHmm").parse(this.getEndtime());

			for (int i = 0; i < newlist.size(); i++) {
				if (this.day.equals(newlist.get(i).getDay())) {
					if ((this.week.equals("EVEN") & newlist.get(i).getWeek().equals("ODD"))
							| (this.week.equals("ODD") & newlist.get(i).getWeek().equals("EVEN"))) {
						continue;
					}
					Date newstarttime = new SimpleDateFormat("HHmm").parse(newlist.get(i).getStarttime());
					Date newendtime = new SimpleDateFormat("HHmm").parse(newlist.get(i).getEndtime());

					if (newstarttime.equals(thisstarttime)
							| (newstarttime.before(thisendtime) & newstarttime.after(thisstarttime)))
						isClash = true;
					else if (newendtime.equals(thisendtime)
							| (newendtime.after(thisstarttime) & newendtime.before(thisendtime)))
						isClash = true;
				}
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (isClash)
			return true;
		else
			return false;
	}

	/**
	 * Changes the class type of this Lesson.
	 * 
	 * @param classType
	 *            this Lesson's new class type.
	 */
	public void setClassType(String classType) {
		this.classType = classType;
	}

	/**
	 * Changes the index number of this Lesson.
	 * 
	 * @param courseIndex
	 *            this Lesson's new index number.
	 */
	public void setCourseIndex(String courseIndex) {
		this.courseIndex = courseIndex;
	}

	/**
	 * Changes the day of this Lesson is held.
	 * 
	 * @param day
	 *            this Lesson's new day.
	 */
	public void setDay(String day) {
		this.day = day;
	}

	/**
	 * Changes the end time of this Lesson.
	 * 
	 * @param endtime
	 *            this Lesson's new end time.
	 */
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	/**
	 * Changes the group code of this Lesson.
	 * 
	 * @param group
	 *            this Lesson's new group code.
	 */
	public void setGroup(String group) {
		this.group = group;
	}

	/**
	 * Changes the index which this Lesson belongs to.
	 * 
	 * @param index
	 *            this Lesson's new index.
	 */
	public void setIndex(Index index) {
		this.index = index;
	}

	/**
	 * Changes the start time of this Lesson.
	 * 
	 * @param starttime
	 *            this Lesson's start time.
	 */
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	/**
	 * Changes the venue which this Lesson is held.
	 * 
	 * @param venue
	 *            this Lesson's new venue.
	 */
	public void setVenue(String venue) {
		this.venue = venue;
	}

	/**
	 * Changes the week which this Lesson is held.
	 * 
	 * @param week
	 *            this Lesson's new week.
	 */
	public void setWeek(String week) {
		this.week = week;
	}

}
