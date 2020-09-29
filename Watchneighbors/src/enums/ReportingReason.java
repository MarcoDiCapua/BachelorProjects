package enums;

/**
 * An Enum keeps reporting reason informations
 *  
 * @author Marco Di Capua
 * @author Filippo Pelosi
 * @author Riccardo Zorzi
 * @version 1.0
 */
public enum ReportingReason {
	// camps
	/**
	 * 
	 */
	THEFT,

	/**
	 * 
	 */
	FIRE,

	/**
	 * 
	 */
	MURDER,

	/**
	 * 
	 */
	MUGGING,

	/**
	 * 
	 */
	ALLARM,

	/**
	 * 
	 */
	SUSPICIOUS_PERSON;

	// methods
	/**
	 * Get an Italian string representation of the reporting reason
	 * 
	 * @return a string representation of this enum
	 */
	@Override
	public String toString() {
		switch (this) {
		case THEFT:
			return "Furto";
		case FIRE:
			return "Incendio";
		case MURDER:
			return "Omicidio";
		case MUGGING:
			return "Scippo";
		case ALLARM:
			return "Allarme";
		case SUSPICIOUS_PERSON:
			return "Persona sospetta";
		default:
			return null;
		}
	}

	/**
	 * create an array with the different reporting reason
	 * 
	 * @return strings array with the different reporting reason
	 */
	public static String[] createArrayReportingsReason() {
		ReportingReason[] arrayReportingsReason = ReportingReason.values();
		String[] stringArrayReportingsReason = new String[arrayReportingsReason.length];
		int count = 0;

		for (ReportingReason reportingReason : arrayReportingsReason) {
			stringArrayReportingsReason[count++] = reportingReason.toString();
		}

		return stringArrayReportingsReason;
	}

	/**
	 * Convert the Italian string in input in the corresponding reporting reason
	 * 
	 * @param reportingReason
	 *            is the <code>String</code> to convert
	 * @return <code>ReportingReason</code> is the corresponding
	 *         <code>ReportingReason</code> enum
	 */
	public static ReportingReason stringToEnum(String reportingReason) {
		switch (reportingReason) {
		case "Furto":
			return THEFT;
		case "Incendio":
			return FIRE;
		case "Omicidio":
			return MURDER;
		case "Scippo":
			return MUGGING;
		case "Allarme":
			return ALLARM;
		case "Persona sospetta":
			return SUSPICIOUS_PERSON;
		default:
			return null;
		}
	}
}