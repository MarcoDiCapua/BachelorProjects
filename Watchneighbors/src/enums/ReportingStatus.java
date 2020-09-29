package enums;

/**
 * An Enum keeps reporting status informations
 * 
 * @author Marco Di Capua
 * @author Filippo Pelosi
 * @author Riccardo Zorzi
 * @version 1.0
 */
public enum ReportingStatus {
	// camps
	/**
	 * 
	 */
	TO_BE_VERIFIED,

	/**
	 * 
	 */
	TAKING_CHARGE,

	/**
	 * 
	 */
	VERIFIED;

	// methods
	/**
	 * Get an Italian string representation of the reporting status
	 * 
	 * @return a string representation of this enum
	 */
	@Override
	public String toString() {
		switch (this) {
		case TO_BE_VERIFIED:
			return "Da verificare";
		case TAKING_CHARGE:
			return "In corso di accertamento";
		case VERIFIED:
			return "Verificato";
		default:
			return null;
		}
	}

	/**
	 * create an array with the different reporting status
	 * 
	 * @return strings array with the different reporting status
	 */
	public static String[] createReportingsStatusArray() {
		ReportingStatus[] arrayReportingsStatus = ReportingStatus.values();
		String[] stringsReportingsSatus = new String[arrayReportingsStatus.length];
		int count = 0;

		for (ReportingStatus reportingStatus : arrayReportingsStatus) {
			stringsReportingsSatus[count++] = reportingStatus.toString();
		}

		return stringsReportingsSatus;
	}

	/**
	 * Convert the Italian string in input in the corresponding reporting status
	 * 
	 * @param reportingStatus
	 *            is the <code>String</code> to convert
	 * @return <code>ReportingStatus</code> is the corresponding
	 *         <code>ReportingStatus</code> enum
	 */
	public static ReportingStatus stringToEnum(String reportingStatus) {
		switch (reportingStatus) {
		case "Da verificare":
			return TO_BE_VERIFIED;
		case "In corso di accertamento":
			return TAKING_CHARGE;
		case "Verificato":
			return VERIFIED;
		default:
			return null;
		}
	}
}