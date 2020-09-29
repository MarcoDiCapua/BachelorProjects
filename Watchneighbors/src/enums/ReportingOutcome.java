package enums;

/**
 * An Enum keeps reporting outcome informations
 * 
 * @author Marco Di Capua
 * @author Filippo Pelosi
 * @author Riccardo Zorzi
 * @version 1.0
 */
public enum ReportingOutcome {
	// camps
	/**
	 * 
	 */
	NOT_YET_SOLVED,

	/**
	 * 
	 */
	FALSE_ALLARM,

	/**
	 * 
	 */
	INTERVENTION_OF_LAW_ENFORCEMENT,

	/**
	 * 
	 */
	THE_SUSPICIOUS_PERSON_HAS_MOVED_AWAY,

	/**
	 * 
	 */
	FIRE_EXTINGUISHED;

	// methods
	/**
	 * Get an Italian string representation of the reporting outcome
	 * 
	 * @return a string representation of this enum
	 */
	@Override
	public String toString() {
		switch (this) {
		case NOT_YET_SOLVED:
			return "Non ancora risolto";
		case FALSE_ALLARM:
			return "Falso allarme";
		case INTERVENTION_OF_LAW_ENFORCEMENT:
			return "Intervento delle forze dell'ordine";
		case THE_SUSPICIOUS_PERSON_HAS_MOVED_AWAY:
			return "La persona sospetta si è allontanata";
		case FIRE_EXTINGUISHED:
			return "Incendio estinto";
		default:
			return null;
		}
	}

	/**
	 * create an array with the different reporting outcome
	 * 
	 * @return strings array with the different reporting outcome
	 */
	public static String[] createArrayReportingsOutcome() {
		ReportingOutcome[] arrayReportingsOutcome = ReportingOutcome.values();
		String[] stringArrayReportingsOutcome = new String[arrayReportingsOutcome.length];
		int count = 0;

		for (ReportingOutcome reportingOutcome : arrayReportingsOutcome) {
			stringArrayReportingsOutcome[count++] = reportingOutcome.toString();
		}

		return stringArrayReportingsOutcome;
	}

	/**
	 * Convert the Italian string in input in the corresponding reporting
	 * outcome
	 * 
	 * @param reportingOutcome
	 *            is the <code>String</code> to convert
	 * @return <code>ReportingOutcome</code> is the corresponding
	 *         <code>ReportingOutcome</code> enum
	 */
	public static ReportingOutcome stringToEnum(String reportingOutcome) {
		switch (reportingOutcome) {
		case "Non ancora risolto":
			return NOT_YET_SOLVED;
		case "Falso allarme":
			return FALSE_ALLARM;
		case "Intervento delle forze dell'ordine":
			return INTERVENTION_OF_LAW_ENFORCEMENT;
		case "La persona sospetta si è allontanata":
			return THE_SUSPICIOUS_PERSON_HAS_MOVED_AWAY;
		case "Incendio estinto":
			return ReportingOutcome.FIRE_EXTINGUISHED;
		default:
			return null;
		}
	}
}