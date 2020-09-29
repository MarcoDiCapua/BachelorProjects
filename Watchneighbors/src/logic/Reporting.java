package logic;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import javax.swing.JLabel;

import enums.City;
import enums.District;
import enums.ReportingOutcome;
import enums.ReportingReason;
import enums.ReportingStatus;

/**
 * An object of the Reporting class keeps reporting information
 * 
 * @author Marco Di Capua
 * @author Filippo Pelosi
 * @author Riccardo Zorzi
 * @version 1.0
 */
public class Reporting {
	// camps
	private String userId;
	private String userInCharge;
	private GregorianCalendar timeStamp;
	private City city;
	private District district;
	private ReportingReason reportingReason;
	private ReportingStatus reportingStatus;
	private ReportingOutcome outcomeReporting;
	private JLabel positionMark;
	private boolean inCharge;
	private int reportingId;
	private double reportingLatitude;
	private double reportingLongitude;
	private double userLatitude;
	private double userLongitude;

	/**
	 * ArrayList of objects <code>Reporting</code> references to the ArrayList
	 * of the current city selected
	 */
	public static ArrayList<Reporting> reportings;

	/**
	 * ArrayList of objects <code>Reporting</code> contains the active
	 * reporting of Como
	 */
	public static ArrayList<Reporting> reportingComo;

	/**
	 * ArrayList of objects <code>Reporting</code> contains the active
	 * reporting of Lecco
	 */
	public static ArrayList<Reporting> reportingLecco;

	/**
	 * ArrayList of objects <code>Reporting</code> contains the active
	 * reporting of Varese
	 */
	public static ArrayList<Reporting> reportingVarese;

	/**
	 * ArrayList of objects <code>Reporting</code> contains the closed
	 * reporting of all cities
	 */
	public static ArrayList<Reporting> closedReporting;

	// constructors
	/**
	 * Create a new object <code>Reporting</code> with the information in input
	 * 
	 * @param city
	 *            is the city of the reporting
	 * @param district
	 *            is the district of the reporting
	 * @param reportingId
	 *            is the Id of the reporting
	 * @param userId
	 *            is the user Id of the reporting
	 * @param userLatitude
	 *            is the user latitude of the reporting
	 * @param userLongitude
	 *            is the user longitude of the reporting
	 * @param reportingLatitude
	 *            is the latitude of the reporting
	 * @param reportingLongitude
	 *            is the longitude of the reporting
	 * @param timeStamp
	 *            is the time stamp of the reporting
	 * @param reportingReason
	 *            is the reason of the reporting
	 * @param reportingStatus
	 *            is the status of the reporting
	 * @param outcomeReporting
	 *            is the outcome of the reporting
	 * @param inCharge
	 *            is true if the reporting is in charge, false otherwise
	 * @param userInCharge
	 *            is the user Id of the reporting
	 */
	public Reporting(City city, District district, int reportingId, String userId, double userLatitude,
			double userLongitude, double reportingLatitude, double reportingLongitude, GregorianCalendar timeStamp,
			ReportingReason reportingReason, ReportingStatus reportingStatus, ReportingOutcome outcomeReporting,
			boolean inCharge, String userInCharge) {
		this.city = city;
		this.district = district;
		this.reportingId = reportingId;
		this.userId = userId;
		this.userLatitude = userLatitude;
		this.userLongitude = userLongitude;
		this.reportingLatitude = reportingLatitude;
		this.reportingLongitude = reportingLongitude;

		this.timeStamp = timeStamp;
		this.reportingReason = reportingReason;
		this.reportingStatus = reportingStatus;
		this.outcomeReporting = outcomeReporting;
		this.inCharge = inCharge;
		this.userInCharge = userInCharge;
		this.positionMark = new JLabel();
		this.positionMark.setName("" + this.reportingId);
	}

	/**
	 * Create a new object <code>Reporting</code> with the informations in the
	 * file row in input
	 * 
	 * @param fileRow
	 *            is the file .csv row
	 */
	public Reporting(String fileRow) {
		String[] tok = fileRow.split(";");
		this.city = City.stringToEnum(tok[0]);
		this.district = District.stringToEnum(tok[1]);
		this.reportingId = Integer.parseInt(tok[2]);
		this.userId = tok[3];
		this.userLatitude = Double.parseDouble(tok[4]);
		this.userLongitude = Double.parseDouble(tok[5]);
		this.reportingLatitude = Double.parseDouble(tok[6]);

		this.reportingLongitude = Double.parseDouble(tok[7]);
		this.timeStamp = Utility.stringToTimeStamp(tok[8]);
		this.reportingReason = ReportingReason.stringToEnum(tok[9]);
		this.reportingStatus = ReportingStatus.stringToEnum(tok[10]);
		this.outcomeReporting = ReportingOutcome.stringToEnum(tok[11]);
		this.inCharge = Utility.stringToBoolean(tok[12]);
		this.userInCharge = tok[13];
		this.positionMark = new JLabel();
		this.positionMark.setName("" + this.reportingId);
	}

	// methods
	/**
	 * Get the user Id of the reporting
	 * 
	 * @return a String representation of the user Id of the reporting
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * Set the user Id of the reporting
	 * 
	 * @param userId
	 *            is the user Id to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * Get the city of the reporting
	 * 
	 * @return an object <code>City</code> representing the city of the
	 *         reporting
	 */
	public City getCity() {
		return city;
	}

	/**
	 * Set the city of the reporting
	 * 
	 * @param city
	 *            is the city to set
	 */
	public void setCity(City city) {
		this.city = city;
	}

	/**
	 * Get if the reporting is in charge or not
	 * 
	 * @return the boolean value true if the reporting is in charge, false
	 *         otherwise
	 */
	public boolean getInCharge() {
		return inCharge;
	}

	/**
	 * Set if the reporting is in charge
	 * 
	 * @param inCharge
	 *            is the boolean value to set, true if the reporting is in
	 *            charge, false otherwise
	 */
	public void setInCharge(boolean inCharge) {
		this.inCharge = inCharge;
	}

	/**
	 * Get the GregorianCalendar object representing the time stamp of the
	 * reporting
	 * 
	 * @return a GregorianCalendar object representing the time stamp of the
	 *         reporting
	 */
	public GregorianCalendar getTimeStamp() {
		return timeStamp;
	}

	/**
	 * Set the time stamp of the reporting
	 * 
	 * @param timeStamp
	 *            is the GregorianCalendar object to set
	 */
	public void setTimeStamp(GregorianCalendar timeStamp) {
		this.timeStamp = timeStamp;
	}

	/**
	 * Get the user Id in charge of the reporting
	 * 
	 * @return a String representing the user Id in charge of the reporting
	 */
	public String getUserInCharge() {
		return userInCharge;
	}

	/**
	 * Set the user Id in charge of the reporting
	 * 
	 * @param userInCharge
	 *            is a String representing the user Id in charge of the
	 *            reporting to set
	 */
	public void setUserInCharge(String userInCharge) {
		this.userInCharge = userInCharge;
	}

	/**
	 * Get the object <code>District</code> representing the district of the
	 * reporting
	 * 
	 * @return an object <code>District</code> representing the district of the
	 *         reporting
	 */
	public District getDistrict() {
		return district;
	}

	/**
	 * Set the object <code>District</code> representing the district of the
	 * reporting
	 * 
	 * @param district
	 *            is the object <code>District</code> to set
	 */
	public void setDistrict(District district) {
		this.district = district;
	}

	/**
	 * Get the object <code>ReportingReason</code> representing the reason of
	 * the reporting
	 * 
	 * @return an object <code>ReportingReason</code> representing the reason of
	 *         the reporting
	 */
	public ReportingReason getReportingReason() {
		return reportingReason;
	}

	/**
	 * Set the object <code>ReportingReason</code> representing the reason of
	 * the reporting
	 * 
	 * @param reportingReason
	 *            is the object <code>ReportingReason</code> to set
	 */
	public void setReportingReason(ReportingReason reportingReason) {
		this.reportingReason = reportingReason;
	}

	/**
	 * Get the object <code>ReportingStatus</code> representing the status of
	 * the reporting
	 * 
	 * @return an object <code>ReportingStatus</code> representing the status of
	 *         the reporting
	 */
	public ReportingStatus getReportingStatus() {
		return reportingStatus;
	}

	/**
	 * Set the object <code>ReportingStatus</code> representing the status of
	 * the reporting
	 * 
	 * @param reportingStatus
	 *            is the object <code>ReportingStatus</code> to set
	 */
	public void setReportingStatus(ReportingStatus reportingStatus) {
		this.reportingStatus = reportingStatus;
	}

	/**
	 * Get the object <code>ReportingOutcome</code> representing the outcome of
	 * the reporting
	 * 
	 * @return an object <code>ReportingOutcome</code> representing the outcome
	 *         of the reporting
	 */
	public ReportingOutcome getOutcomeReporting() {
		return outcomeReporting;
	}

	/**
	 * Set the object <code>ReportingOutcome</code> representing the outcome of
	 * the reporting
	 * 
	 * @param outcomeReporting
	 *            is the object <code>ReportingOutcome</code> to set
	 */
	public void setOutcomeReporting(ReportingOutcome outcomeReporting) {
		this.outcomeReporting = outcomeReporting;
	}

	/**
	 * Get the object JLabel representing the position mark of the reporting
	 * 
	 * @return the position mark of the reporting
	 */
	public JLabel getPositionMark() {
		return positionMark;
	}

	/**
	 * Set the object JLabel representing the position mark of the reporting
	 * 
	 * @param positionMark
	 *            is the position mark to set
	 */
	public void setPositionMark(JLabel positionMark) {
		this.positionMark = positionMark;
	}

	/**
	 * Get the Id of the reporting
	 * 
	 * @return the Id of the reporting
	 */
	public int getReportingId() {
		return reportingId;
	}

	/**
	 * Set the Id of the reporting
	 * 
	 * @param reportingId
	 *            is the Id of the reporting to set
	 */
	public void setReportingId(int reportingId) {
		this.reportingId = reportingId;
	}

	/**
	 * Get the latitude of the reporting
	 * 
	 * @return the latitude of the reporting
	 */
	public double getReportingLatitude() {
		return reportingLatitude;
	}

	/**
	 * Set the latitude of the reporting
	 * 
	 * @param reportingLatitude
	 *            is the latitude of the reporting to set
	 */
	public void setReportingLatitude(double reportingLatitude) {
		this.reportingLatitude = reportingLatitude;
	}

	/**
	 * Get the longitude of the reporting
	 * 
	 * @return the longitude of the reporting
	 */
	public double getReportingLongitude() {
		return reportingLongitude;
	}

	/**
	 * Set the longitude of the reporting
	 * 
	 * @param reportingLongitude
	 *            is the longitude of the reporting to set
	 */
	public void setReportingLongitude(double reportingLongitude) {
		this.reportingLongitude = reportingLongitude;
	}

	/**
	 * Get the user latitude of the reporting
	 * 
	 * @return the user latitude of the reporting
	 */
	public double getUserLatitude() {
		return userLatitude;
	}

	/**
	 * Set the user latitude of the reporting
	 * 
	 * @param userLatitude
	 *            is the user latitude of the reporting to set
	 */
	public void setUserLatitude(double userLatitude) {
		this.userLatitude = userLatitude;
	}

	/**
	 * Get the user longitude of the reporting
	 * 
	 * @return the user longitude of the reporting
	 */
	public double getUserLongitude() {
		return userLongitude;
	}

	/**
	 * Set the user longitude of the reporting
	 * 
	 * @param userLongitude
	 *            is the user longitude of the reporting to set
	 */
	public void setUserLongitude(double userLongitude) {
		this.userLongitude = userLongitude;
	}

	/**
	 * Get a String representation of the object <code>Reporting</code>
	 * 
	 * @return a String representation of the <code>Reporting</code>
	 */
	@Override
	public String toString() {
		return this.getCity() + ";" + this.getDistrict() + ";" + this.getReportingId() + ";" + this.getUserId() + ";"
				+ this.getUserLatitude() + ";" + this.getUserLongitude() + ";" + this.getReportingLatitude() + ";"
				+ this.getReportingLongitude() + ";" + Utility.timeStampToString(this.getTimeStamp()) + ";"
				+ this.getReportingReason() + ";" + this.getReportingStatus() + ";" + this.getOutcomeReporting() + ";"
				+ this.getInCharge() + ";" + this.getUserInCharge() + ";";
	}

	/**
	 * Remove the reporting form the ArrayList, set the id of the reporting
	 * equals to 0, set invisible the position mark, add the reporting to the
	 * ArrayList of the closed reporting, reset the id and the position mark
	 * name of the active reporting and update the file .csv
	 */
	public void closeReporting() {
		reportings.remove(this);
		this.setReportingId(0);
		this.getPositionMark().setVisible(false);
		closedReporting.add(this);

		for (int i = 0; i < reportings.size(); i++) {
			reportings.get(i).setReportingId(i + 1);
			reportings.get(i).getPositionMark().setName("" + reportings.get(i).getReportingId());
		}
		FileManager.writeReportingsFile();
	}

	/**
	 * Create all the reporting lists
	 */
	public static void createReportingsLists() {
		reportings = new ArrayList<Reporting>();
		reportingComo = new ArrayList<Reporting>();
		reportingLecco = new ArrayList<Reporting>();
		reportingVarese = new ArrayList<Reporting>();
		closedReporting = new ArrayList<Reporting>();
	}

	/**
	 * Create a new object <code>Reporting</code> with the informations in the
	 * file row in input and add it to the corresponding ArrayList
	 * 
	 * @param fileRow
	 *            is the file .csv row
	 */
	public static void loadReporting(String fileRow) {
		Reporting loadedReporting = new Reporting(fileRow);
		if (loadedReporting.getReportingId() == 0) {
			closedReporting.add(loadedReporting);
		} else if (loadedReporting.getCity().toString().equals("Como")) {
			reportingComo.add(loadedReporting);
		} else if (loadedReporting.getCity().toString().equals("Lecco")) {
			reportingLecco.add(loadedReporting);
		} else if (loadedReporting.getCity().toString().equals("Varese")) {
			reportingVarese.add(loadedReporting);
		}
	}

	/**
	 * Create a new object <code>Reporting</code> with the informations in input
	 * and set time stamp, Id, user in charge and outcome of the reporting, add
	 * it to the ArrayList and update the file .csv
	 * 
	 * @param city
	 *            is the object <code>City</code> representing the city of the
	 *            reporting
	 * @param district
	 *            is the object <code>District</code> representing the district
	 *            of the reporting
	 * @param reportingLatitude
	 *            is the latitude of the reporting
	 * @param reportingLongitude
	 *            is the longitude of the reporting
	 * @param reportingReason
	 *            is the object <code>ReportingReason</code> representing the
	 *            reason of the reporting
	 * @param reportingStatus
	 *            is the object <code>ReportingStatus</code> representing the
	 *            status of the reporting
	 * @param inCharge
	 *            is a boolean value representing if the reporting is in charge
	 *            or not
	 * @param userInCharge
	 *            is a String representing the user in charge of the reporting
	 * @return the new object <code>Reporting</code>
	 */
	public static Reporting createNewReporting(City city, District district, double reportingLatitude,
			double reportingLongitude, ReportingReason reportingReason, ReportingStatus reportingStatus,
			String inCharge, String userInCharge) {
		GregorianCalendar gc = new GregorianCalendar();
		Reporting newReporting = new Reporting(city, district, (reportings.size() + 1), User.userLogged.getUserId(),
				User.userLogged.getLatitude(), User.userLogged.getLongitude(), Utility.round(reportingLatitude),
				Utility.round(reportingLongitude), gc, reportingReason, reportingStatus,
				ReportingOutcome.NOT_YET_SOLVED, Utility.stringToBoolean(inCharge), userInCharge);

		reportings.add(newReporting);
		FileManager.writeReportingsFile();

		return newReporting;
	}

	/**
	 * Create a String matrix with the informations of all active reporting of
	 * the current city selected with reason of the reporting equals to the
	 * String in input
	 * 
	 * @param reportingsReason
	 *            is the String representing the reason of the reporting. If
	 *            this String is equals to "Tutti", all the active reporting
	 *            will be selected
	 * @return the matrix with the informations of all active reporting of the
	 *         current city selected with reason of the reporting equals to the
	 *         String in input
	 */
	public static String[][] createReportingsMatrix(String reportingsReason) {
		String[][] reportingsMatrix;
		if (reportingsReason.equals("Tutti")) {
			reportingsMatrix = new String[reportings.size()][13];
			for (int i = 0; i < reportings.size(); i++) {
				fullMatrixRow(reportingsMatrix, i, i);
			}
		} else {
			int count = 0;
			for (int i = 0; i < reportings.size(); i++) {
				if (reportings.get(i).getReportingReason().equals(ReportingReason.stringToEnum(reportingsReason))) {
					count++;
				}
			}
			reportingsMatrix = new String[count][13];
			int k = 0;

			for (int i = 0; i < reportings.size(); i++) {
				if (reportings.get(i).getReportingReason().equals(ReportingReason.stringToEnum(reportingsReason))) {
					fullMatrixRow(reportingsMatrix, k, i);
					k++;
				}
			}
		}

		return reportingsMatrix;
	}

	/**
	 * Create a String matrix with the informations of all active reporting of
	 * the current city selected with reason and district of the reporting
	 * equals to the values in input
	 * 
	 * @param district
	 *            is the object <code>District</code> representing the district
	 *            of the reporting
	 * @param reportingsReason
	 *            is the String representing the reason of the reporting. If
	 *            this String is equals to "Tutti", all the active reporting
	 *            will be selected
	 * @return the matrix with the informations of all active reporting of the
	 *         current city selected with reason and district of the reporting
	 *         equals to the values in input
	 */
	public static String[][] createReportingsMatrix(District district, String reportingsReason) {
		String[][] reportingsMatrix;
		if (reportingsReason.equals("Tutti")) {
			int count = 0;
			for (int i = 0; i < reportings.size(); i++) {
				if (reportings.get(i).getDistrict().compareTo(district) == 0) {
					count++;
				}
			}
			reportingsMatrix = new String[count][13];
			int k = 0;

			for (int i = 0; i < reportings.size(); i++) {
				if (reportings.get(i).district.equals(district)) {
					fullMatrixRow(reportingsMatrix, k, i);
					k++;
				}
			}
		} else {
			int count = 0;
			for (int i = 0; i < reportings.size(); i++) {
				if (reportings.get(i).getReportingReason().equals(ReportingReason.stringToEnum(reportingsReason))
						&& reportings.get(i).district.equals(district)) {
					count++;
				}
			}
			reportingsMatrix = new String[count][13];
			int k = 0;

			for (int i = 0; i < reportings.size(); i++) {
				if (reportings.get(i).getReportingReason().equals(ReportingReason.stringToEnum(reportingsReason))
						&& reportings.get(i).district.equals(district)) {
					fullMatrixRow(reportingsMatrix, k, i);
					k++;
				}
			}
		}

		return reportingsMatrix;
	}

	/**
	 * Create a String matrix with the informations of all reporting closed
	 * 
	 * @return the matrix with the informations of all reporting closed
	 */
	public static String[][] createClosedReportingsMatrix() {
		String[][] closedReportingsMatrix = new String[closedReporting.size()][13];
		for (int i = 0; i < closedReporting.size(); i++) {
			closedReportingsMatrix[i][0] = closedReporting.get(i).getCity().toString();
			closedReportingsMatrix[i][1] = closedReporting.get(i).getDistrict().toString();
			closedReportingsMatrix[i][2] = closedReporting.get(i).getUserId();
			closedReportingsMatrix[i][3] = Double.toString(closedReporting.get(i).getUserLatitude());
			closedReportingsMatrix[i][4] = Double.toString(closedReporting.get(i).getUserLongitude());
			closedReportingsMatrix[i][5] = Double.toString(closedReporting.get(i).getReportingLatitude());

			closedReportingsMatrix[i][6] = Double.toString(closedReporting.get(i).getReportingLongitude());
			closedReportingsMatrix[i][7] = Utility.timeStampToString(closedReporting.get(i).getTimeStamp());
			closedReportingsMatrix[i][8] = closedReporting.get(i).getReportingReason().toString();
			closedReportingsMatrix[i][9] = closedReporting.get(i).getReportingStatus().toString();
			closedReportingsMatrix[i][10] = closedReporting.get(i).getOutcomeReporting().toString();
			closedReportingsMatrix[i][11] = Utility.booleanToString(closedReporting.get(i).getInCharge());
			closedReportingsMatrix[i][12] = closedReporting.get(i).getUserInCharge();
		}
		return closedReportingsMatrix;
	}

	// Fill the row at the index in input of the matrix in input with the
	// informations of the reporting at the index in input
	private static String[][] fullMatrixRow(String[][] matrix, int rowIndex, int reportingIndex) {

		matrix[rowIndex][0] = reportings.get(reportingIndex).getCity().toString();
		matrix[rowIndex][1] = reportings.get(reportingIndex).getDistrict().toString();
		matrix[rowIndex][2] = Integer.toString(reportings.get(reportingIndex).getReportingId());
		matrix[rowIndex][3] = reportings.get(reportingIndex).getUserId();
		matrix[rowIndex][4] = Double.toString(reportings.get(reportingIndex).getUserLatitude());
		matrix[rowIndex][5] = Double.toString(reportings.get(reportingIndex).getUserLongitude());

		matrix[rowIndex][6] = Double.toString(reportings.get(reportingIndex).getReportingLatitude());
		matrix[rowIndex][7] = Double.toString(reportings.get(reportingIndex).getReportingLongitude());
		matrix[rowIndex][8] = Utility.timeStampToString(reportings.get(reportingIndex).getTimeStamp());
		matrix[rowIndex][9] = reportings.get(reportingIndex).getReportingReason().toString();
		matrix[rowIndex][10] = reportings.get(reportingIndex).getReportingStatus().toString();
		matrix[rowIndex][11] = Utility.booleanToString(reportings.get(reportingIndex).getInCharge());
		matrix[rowIndex][12] = reportings.get(reportingIndex).getUserInCharge();

		return matrix;
	}
}