/**
 * A class representing a possible event for the calendar
 * @author Karan Bhargava
 * @version 1.2016.36210
 *
 */
public class Event implements Comparable<Event> {

	//instance variables for time and description of an event
	private String eventStartTime;
	private String eventEndTime;
	private String eventDescription;
	private String eventDay;
	private String eventMonth;
	private String eventYear;

	/**
	 * A constructor to create a new event
	 * @param start - the starting time of the event
	 * @param end - the ending time of the event
	 * @param description - the description of the event
	 * @param date - the date of the event
	 */
	public Event(String start, String end, String description, String date) {
		eventStartTime = start;
		eventEndTime = end;
		eventDescription = description;

		//separate by / as the date is entered in MM/DD/YYYY
		int separate = date.indexOf("/");
		eventMonth = date.substring(0, separate);
		eventDay = date.substring(separate + 1, date.indexOf("/", separate + 1));
		separate = date.indexOf("/", separate + 1);
		eventYear = date.substring(separate + 1);
	}

	/**
	 * An overridden compareTo method from the library
	 * Checks if two events are similar (whether to accept or not)
	 * @param other - the other event
	 * @return result - the result after comparing the two events
	 */
	public int compareTo(Event other) {
		int result = this.eventYear.compareTo(other.getEventYear());

		if (result == 0) {
				String one = this.getEventMonth() + " " + this.getEventDate() + " " + this.getEventStartTime() + " " 
						+ this.getEventEndTime() + " " + this.getEventDescription();
				String two = other.getEventMonth() + " " + other.getEventDate() + " " + other.getEventStartTime() + " " 
						+ other.getEventEndTime() + " " + other.getEventDescription();
				result = one.compareTo(two);
			}
		return result;

		}

	/**
	 * A simple getter method to get the starting event time
	 * @return eventStartTime - the time at which the event starts
	 */
	public String getEventStartTime() {
		return eventStartTime;
	}

	/**
	 * A simple getter method to get the description of the event
	 * @return eventDescrption - the description of the event
	 */
	public String getEventDescription() {
		return eventDescription;
	}

	/**
	 * A simple getter method to get the ending time of the event
	 * @return eventEndTime - the time at which the end ends
	 */
	public String getEventEndTime() {
		return eventEndTime;
	}

	/**
	 * A simple getter method to get the day of the event
	 * @return eventDay - the day on which the event is
	 */
	public String getEventDate() {
		return eventDay;
	}

	/**
	 * A simple getter method to get the month of the event
	 * @return eventMonth - the month during which the event is
	 */
	public String getEventMonth() {
		return eventMonth;
	}

	/**
	 * A simple getter method to get the year of the event
	 * @return eventYear - the year when the event occurs
	 */
	public String getEventYear() {
		return eventYear;
	}
	
	public String toString() {
		String returnStr = "";
		
		if (eventEndTime.equals("")) {
			returnStr = eventMonth + "/" + eventDay + "/" + eventYear + ": " + eventStartTime + " " + eventDescription;
		}
		
		returnStr = eventMonth + "/" + eventDay + "/" + eventYear + ": " + eventStartTime + " - " + eventEndTime + " " + eventDescription;
		
		return returnStr;
	}


}
