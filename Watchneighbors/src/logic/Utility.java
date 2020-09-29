package logic;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * This class contains static methods for general purpose in the Watchneighbors application
 * 
 * @author Marco Di Capua
 * @author Filippo Pelosi
 * @author Riccardo Zorzi
 * @version 1.0
 */

public class Utility {
	// methods
	/**
	 * Convert the String in input in the corresponding boolean
	 * 
	 * @param string
	 *            is the String to convert
	 * @return true if string is equals to "true" or "Sì", false otherwise
	 */
	public static boolean stringToBoolean(String string) {
		if (string.equals("Sì") || string.equals("true")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Convert the boolean in input in the corresponding Italian String
	 * representation
	 * 
	 * @param bln
	 *            is the boolean to convert
	 * @return String "Si" if bln is equals to "true", String "No" if bln is
	 *         equals to "false"
	 */

	public static String booleanToString(boolean bln) {
		return bln ? "Sì" : "No";
	}

	/**
	 * Convert the String (in the format "DD/MM/YYYY hh:mm") in input in the
	 * corresponding GregorianCalendar object
	 * 
	 * @param timeStamp
	 *            is the String, in the format "DD/MM/YYYY hh:mm", to convert
	 * @return a GregorianCalendar object representing the String in input
	 */
	public static GregorianCalendar stringToTimeStamp(String timeStamp) {
		String[] tok = timeStamp.split("/");
		int day = Integer.parseInt(tok[0]);
		int month = Integer.parseInt(tok[1]) - 1;
		String[] tok1 = tok[2].split(" - ");
		int year = Integer.parseInt(tok1[0]);
		String[] tok2 = tok1[1].split(":");
		int hour = Integer.parseInt(tok2[0]);
		int minute = Integer.parseInt(tok2[1]);

		return new GregorianCalendar(year, month, day, hour, minute);
	}

	/**
	 * Convert the GregorianCalendar object in input in the corresponding String
	 * in the format "DD/MM/YYYY hh:mm"
	 * 
	 * @param gc
	 *            GregorianCalendar object to convert
	 * @return a String representation of the GregorianCalendar in input
	 */
	public static String timeStampToString(GregorianCalendar gc) {
		String hour;
		String minute;
		String day;
		String month;
		int year = gc.get(Calendar.YEAR);
		int monthNumber = gc.get(Calendar.MONTH) + 1;
		int dayNumber = gc.get(Calendar.DATE);
		int hourNumber = gc.get(Calendar.HOUR);

		int minuteNumber = gc.get(Calendar.MINUTE);
		if (gc.get(Calendar.AM_PM) == 1) {
			hourNumber += 12;
		}

		if (hourNumber < 10) {
			hour = "0" + hourNumber;
		} else {
			hour = "" + hourNumber;
		}

		if (minuteNumber < 10) {
			minute = "0" + minuteNumber;
		} else {
			minute = "" + minuteNumber;
		}

		if (dayNumber < 10) {
			day = "0" + dayNumber;
		} else {
			day = "" + dayNumber;
		}

		if (monthNumber < 10) {
			month = "0" + monthNumber;
		} else {
			month = "" + monthNumber;
		}

		return day + "/" + month + "/" + year + " - " + hour + ":" + minute;
	}

	/**
	 * Round the double in input to one decimal place
	 * 
	 * @param x
	 *            is the double to round
	 * @return the double in input rounded to one decimal place
	 */
	public static double round(double x) {
		return Math.floor(x * 10) / 10;
	}
}