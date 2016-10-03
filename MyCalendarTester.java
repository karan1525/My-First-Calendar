import java.util.*;
import java.io.*;

//Declare the months
enum MONTHS
{
	Jan, Feb, March, Apr, May, June, July, Aug, Sep, Oct, Nov, Dec;
}

//Declare the days
enum DAYS
{
	Sun, Mon, Tue, Wed, Thur, Fri, Sat ;
}

/**
 * A Class representing a calendar
 * @author Karan Bhargava
 * @version 1.2016.36210
 *
 */
public class MyCalendarTester {

	//instance variables
	private final List<Integer> DAYS_IN_A_MONTH = Arrays.asList(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
	private boolean isLeapYear;
	private Scanner reader;
	private TreeMap<String, TreeSet<Event>> Calendar;

	/**
	 * A constructor to construct the calendar
	 * Also checks if current year is a leap year or not
	 */
	public MyCalendarTester() {
		GregorianCalendar cal = new GregorianCalendar();
		isLeapYear = cal.isLeapYear(cal.get(java.util.Calendar.YEAR)); //check if current year is a leap year
		this.Calendar = new TreeMap<>();
		reader = new Scanner(System.in);
		printCal(cal, true);
		System.out.println("Select one of the following options:\n" + "[L]oad, [V]iew by, [C]reate, [G]o to, [E]vent list, [D]elete, [Q]uit: ");
		System.out.println("This is the first run of this program");

	}

	/**
	 * A simple method to print the calendar of the month
	 * @param calendar - the calendar we're working with
	 * @param check - the boolean to check if it's the first run or not
	 */
	public void printCal(Calendar calendar, boolean check) {
		//initalize list of months
		List<String> months = Arrays.asList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");

		//get current month and year
		System.out.printf("%12s%s\n","", months.get(calendar.get(java.util.Calendar.MONTH)) + " " + calendar.get(GregorianCalendar.YEAR));
		//print the week
		System.out.println(" Sun  Mon  Tue  Wed  Thu  Fri  Sat ");
		//initialize temporary calendar for altering
		GregorianCalendar temporary = new GregorianCalendar(calendar.get(java.util.Calendar.YEAR), calendar.get(java.util.Calendar.MONTH), 1);
		int year = temporary.get(java.util.Calendar.YEAR);
		int month = temporary.get(java.util.Calendar.MONTH);
		int days = DAYS_IN_A_MONTH.get(calendar.get(java.util.Calendar.MONTH));

		int firstDayOfWeek = temporary.get(java.util.Calendar.DAY_OF_WEEK) - 1;

		//if it's a leap year, increment number of days by 1
		if (isLeapYear && month == 1) {
			days++;
		}

		int counter = 1;
		//while loop to add date to treeSet
		while (counter <= days) {
			String calDate = counter + "";
			String key = "";

			if (counter == calendar.get(java.util.Calendar.DAY_OF_MONTH) && check) {
				calDate = "[" + counter + "]";
			} else {
				key = String.format("%02d/%02d/%d", year, (month + 1) , counter);
			}

			if (Calendar.containsKey(key)) {
				calDate = counter + "*";
			}

			//print the dates (in specified order)
			if (counter == 1) {
				int x = temporary.get(java.util.Calendar.DAY_OF_WEEK) - 1;
				for (int a = 0; a < x; a++) {
					System.out.printf("%5s", "");
				}
				System.out.printf("%4s\n", calDate);
			} else if ((counter + firstDayOfWeek) % 7 == 0) {
				System.out.printf("%5s\n", calDate);
			} else if ((counter + firstDayOfWeek) % 7 == 1) {
				System.out.printf("%4s", calDate);
			} else {
				System.out.printf("%5s", calDate);
			}
			counter++;
		}
		//Empty line for easier viewing
		System.out.println();

	}

	/**
	 * A method to view the main menu of the calendar class
	 * @param bool - a boolean to check if quit has been performed
	 */
		public void viewMain(boolean bool) {
			System.out.println("Select one of the following options:\n" + "[L]oad, [V]iew by, [C]reate, [G]o to, [E]vent list, [D]elete, [Q]uit: ");
			
			while (!bool) {
				//read the input
				String read = reader.nextLine();
				
				switch(read.toUpperCase()) { //make it all UpperCase
				case "L": //load
					load(); 
					break;
				case "V": //view (month or day)
					System.out.println("[D]ay view or [M]onth view? ");
					read = reader.nextLine().toUpperCase();
					if(read.equals("M")) {
						viewMonth();
					} else {
						viewDay(new GregorianCalendar());
					}
					break;
				case "C":  //create an event
					create();
					break;
				case "G": //goTo an event
					goTo();
					break;
				case "E": //list all the events
					listEvents();
					break;
				case "D": //delete an event
					delete();
					break;
				case "Q": //quit the calendar
					bool = true;
					quit();
					System.exit(0);
				}
			}	
		}

	/**
	 * A method to view the calendar of the current month
	 */
	public void viewMonth() {
		//make a new calendar
		GregorianCalendar calendar = new GregorianCalendar();
		boolean check = false; //initialize boolean as false

		//while the boolean is false
		do {

			printCal(calendar, check); //print calendar
			System.out.println("[P]revious or [N]ext or [M]ain menu? "); //what would you like next?
			String read = reader.nextLine().toUpperCase();

			if(read.equals("N")){ //next
				calendar.add(java.util.Calendar.MONTH, 1);

			} else if(read.equals("M")){ //main menu
				check = true;

			} else if(read.equals("P")){ //previous
				calendar.add(java.util.Calendar.MONTH, -1);
			} 

		} while (!check);
		viewMain(false);
	}

	/**
	 * A method to display the day and the events scheduled for that day
	 * @param cal - the calendar we're working with
	 */
	public void viewDay(Calendar cal) {
		DAYS[] daysOfTheWeek = DAYS.values(); //from enum 
		MONTHS[] monthsOfYear = MONTHS.values(); //from enum

		boolean check = false;

		do { //while boolean is false

			//print the calendar
			System.out.println(daysOfTheWeek[cal.get(java.util.Calendar.DAY_OF_WEEK)-1] + ", " 
					+ monthsOfYear[cal.get(java.util.Calendar.MONTH)] 
							+ " " + cal.get(java.util.Calendar.DAY_OF_MONTH) + ", " + cal.get(java.util.Calendar.YEAR));

			String d = String.format("%02d/%02d/%4d", cal.get(java.util.Calendar.YEAR), 
					cal.get(java.util.Calendar.MONTH)+1, cal.get(java.util.Calendar.DAY_OF_MONTH));

			TreeSet<Event> temp = Calendar.get(d);

			//print the event on the specific day
			if(temp != null) {
				for(Event x: temp) {
					if(!(x.getEventEndTime().equals(""))) {
						System.out.println(x.getEventDescription() + " " + x.getEventStartTime() + " - " + x.getEventEndTime());
					} else {
						System.out.println(x.getEventDescription() + " " + x.getEventStartTime());
					}
				}
			}

			//What would you like after the calendar is printed?
			System.out.println("[P]revious or [N]ext or [M]ain menu? ");
			String read = reader.nextLine().toUpperCase();

			if (read.equals("N")) {
				cal.add(java.util.Calendar.DAY_OF_MONTH, 1);
			} else if (read.equals("M")) {
				check = true;
			} else if (read.equals("P")) {
				cal.add(java.util.Calendar.DAY_OF_MONTH, -1);
			}


		} while (!check);
		viewMain(false);

	}

	/**
	 * A method to handle event creation for the calendar
	 */
	public void create() {
		System.out.println("Title: "); //ask for title
		String eventDescription = reader.nextLine();
		System.out.println("Enter date in MM/DD/YYYY format: "); //ask for date
		String eventDate = reader.nextLine();
		System.out.println("Enter a starting time: "); //ask for starting time
		String eventStart = reader.nextLine();
		System.out.println("Enter an ending time: "); //ask for ending time
		String eventEnd = reader.nextLine();
		
		
		
		Event e = new Event(eventStart, eventEnd, eventDescription, eventDate); //create event using event constructor
		TreeSet<Event> temporary = new TreeSet<>();

		if(Calendar.containsKey(eventDate)) { //if there's already an event on specified date
			temporary = Calendar.get(eventDate); //add this event
			temporary.add(e);
		} else {
			temporary.add(e); //if there's no event on the specified date
		}

		String key = e.getEventYear() +  e.getEventMonth() + e.getEventDate();
		Calendar.put(key, temporary); //add to the treeMap of events
	}

	/**
	 * A method to delete an event from the calendar
	 * Either all events or a selected event can be deleted
	 */
	public void delete() {
		//Either delete one event or all
		System.out.println("[S]elected or [A]ll? ");
		String read = reader.nextLine().toUpperCase();
		if(read.equals("A")) { //reinitialize treeMap if all events are deleted
			Calendar = new TreeMap<>();

		} else if (read.equals("S")) { //else ask for a specific date
			System.out.println("Enter the date (MM/DD/YYYY): ");
			read = reader.nextLine();
			String eventDate = "";
			int separate = read.indexOf("/"); //get month, date and year
			eventDate += read.substring(0, separate);
			eventDate += read.substring(separate + 1, read.indexOf("/", separate + 1));
			separate = read.indexOf("/", separate + 1);
			eventDate = read.substring(separate + 1) + eventDate; 

			Calendar.remove(eventDate); //delete from treeMap
		} 

	}

	/**
	 * A simple method to save all the information in the treeSet
	 * to the designated .txt file
	 */
	public void quit() {

		//try to save the info
		try {
			PrintStream output = new PrintStream("events.txt"); //specified name
			for (String keyLoop: Calendar.keySet()) { //get all the events from Calendar
				TreeSet<Event> allEvents = Calendar.get(keyLoop);

				for (Event x: allEvents) { //print all events to the file
					output.println(x); 
				}
			}

			output.close(); //close the printer

			//catch if there's an exception while trying to save to file
		} catch (IOException e) {
			e.printStackTrace();

		}
	}

	/**
	 * A simple method to load all info from events.txt
	 * If it already exists
	 */
	public void load() {
		//try to read the file
		try {
			Scanner fileReader = new Scanner(new File("events.txt"));

			//while there's more lines in the file
			while(fileReader.hasNextLine()) {
				String read = fileReader.nextLine();

				//initialize variables
				String eventStartTime = "";
				String eventEndTime = "";
				int start = 0;
				int end = read.indexOf(":");
				String eventDate = read.substring(start, end).trim();
				start = end + 2;

				//if there's an -
				if (read.contains("-")) {
					end = read.indexOf("-");
					eventStartTime = read.substring(start, end).trim();
					start = end + 2;
					end = read.indexOf(" ", start);
					eventEndTime = read.substring(start, end).trim();
				} else {
					end = read.indexOf(" ", start);
					eventStartTime = read.substring(start, end).trim();
				}

				//add event to Calendar
				String eventDescription = read.substring(end + 1).trim();
				Event event = new Event(eventStartTime, eventEndTime, eventDescription, eventDate);
				TreeSet<Event> temporary = new TreeSet<>();
				if (Calendar.containsKey(eventDate)){
					temporary = Calendar.get(eventDate);

				}

				temporary.add(event);
				String key = event.getEventYear() +  event.getEventMonth() + event.getEventDate();
				Calendar.put(key, temporary);
			}

			//close the scanner
			fileReader.close();

			//catch exception if reading the file failed
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * A simple method to goTo the specified date the user enters
	 * Date is entered in MM/DD/YYYY
	 */
	public void goTo() {
		//enter a date to goTo that event
		System.out.println("Enter a date in MM/DD/YYYY format: ");
		String read = reader.nextLine();
		//get the date from the format that was entered
		int separate = read.indexOf("/");
		int eventMonth = Integer.parseInt(read.substring(0, separate)) - 1;
		int eventDate = Integer.parseInt(read.substring(separate + 1, read.indexOf("/", separate + 1)));
		separate = read.indexOf("/", separate+ 1);
		int eventYear = Integer.parseInt(read.substring(separate + 1));

		//get months and days from the enum 
		MONTHS[] monthsOfYear = MONTHS.values();
		DAYS[] daysOfTheWeek = DAYS.values();

		//declare a calendar of the specified date
		GregorianCalendar cal = new GregorianCalendar(eventYear, eventMonth, eventDate);

		//print out the date
		System.out.println(daysOfTheWeek[cal.get(java.util.Calendar.DAY_OF_WEEK) - 1] + ", "
				+ monthsOfYear[cal.get(java.util.Calendar.MONTH)] 
						+ " " + cal.get(java.util.Calendar.DAY_OF_MONTH) + ", " + cal.get(java.util.Calendar.YEAR));

		//get the date of the event
		String dateOfEvent = String.format("%04d%02d%02d", cal.get(java.util.Calendar.YEAR), cal.get(java.util.Calendar.MONTH) 
				+ 1, cal.get(java.util.Calendar.DAY_OF_MONTH));

		//temporary storage of event(s)
		TreeSet<Event> temporary = Calendar.get(dateOfEvent);

		//loop over the temporary treeSet
		if(temporary != null) {

			for (Event x: temporary)

				if(x.getEventEndTime().equals("")) {

					System.out.println(x.getEventDescription() + " " + x.getEventStartTime());

				} else {
					System.out.println(x.getEventDescription() + " " + x.getEventStartTime() + " - " + x.getEventEndTime());
				}
		}
	}

	/**
	 * A method to list the events in the treeSet
	 */
	public void listEvents() {
		//get the values from the enum declared above
		MONTHS[] monthsOfYear = MONTHS.values();
		DAYS[] daysOfTheWeek = DAYS.values();

		//initialize variables
		int current = 0; 
		String read = "";

		//loop over the treeSet calendar
		for(String keySet : Calendar.keySet()) {

			for(Event x : Calendar.get(keySet)) {

				int eventDate = Integer.parseInt(x.getEventDate()); //date
				int eventMonth = Integer.parseInt(x.getEventMonth()) - 1; //month
				int eventYear = Integer.parseInt(x.getEventYear()); //year

				//create a temporary calendar
				GregorianCalendar cal = new GregorianCalendar(eventYear, eventMonth, eventDate);

				//Check if it's not an empty event
				if(eventYear != current) {
					read += "\n" + x.getEventYear() + "\n" ;
					current = eventYear;
				}

				//get the eventDescription
				String eventDescription = x.toString();

				//add the event to the returning string
				read += daysOfTheWeek[cal.get(java.util.Calendar.DAY_OF_WEEK) - 1] + ", " 
						+ monthsOfYear[cal.get(java.util.Calendar.MONTH)] 
								+ " " + cal.get(java.util.Calendar.DAY_OF_MONTH) + " " + 
								eventDescription.substring(eventDescription.indexOf(":") + 2) + "\n";
			}
		}
		
		//print out the returning string
		System.out.println(read);
	}

	//MyFirstCalendar Tester
	public static void main (String[] args) {
		MyCalendarTester cal = new MyCalendarTester();
		cal.viewMain(false);

	}
}
