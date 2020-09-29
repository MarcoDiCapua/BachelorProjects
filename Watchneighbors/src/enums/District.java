package enums;

/**
 * An Enum keeps district informations
 *
 * @author Marco Di Capua
 * @author Filippo Pelosi
 * @author Riccardo Zorzi
 * @version 1.0
 */
public enum District {
	// camps
	/**
	 * 
	 */
	NORTH_WEST,

	/**
	 * 
	 */
	NORTH_EAST,

	/**
	 * 
	 */
	SOUTH_WEST,

	/**
	 * 
	 */
	SOUTH_EAST;

	private static final String PATH_IMG_COMO_NORTH_WEST = ".\\images\\Como_nord_ovest.png";
	private static final String PATH_IMG_COMO_NORTH_EAST = ".\\images\\Como_nord_est.png";
	private static final String PATH_IMG_COMO_SOUTH_WEST = ".\\images\\Como_sud_ovest.png";
	private static final String PATH_IMG_COMO_SOUTH_EAST = ".\\images\\Como_sud_est.png";
	private static final String PATH_IMG_LECCO_NORTH_WEST = ".\\images\\Lecco_nord_ovest.png";
	private static final String PATH_IMG_LECCO_NORTH_EAST = ".\\images\\Lecco_nord_est.png";
	private static final String PATH_IMG_LECCO_SOUTH_WEST = ".\\images\\Lecco_sud_ovest.png";
	private static final String PATH_IMG_LECCO_SOUTH_EAST = ".\\images\\Lecco_sud_est.png";
	private static final String PATH_IMG_VARESE_NORTH_WEST = ".\\images\\Varese_nord_ovest.png";
	private static final String PATH_IMG_VARESE_NORTH_EAST = ".\\images\\Varese_nord_est.png";
	private static final String PATH_IMG_VARESE_SOUTH_WEST = ".\\images\\Varese_sud_ovest.png";
	private static final String PATH_IMG_VARESE_SOUTH_EAST = ".\\images\\Varese_sud_est.png";
	private static final double MAX_LATITUDE = 10.0;
	private static final double MAX_LONGITUDE = 20.0;

	/**
	 * Get the image path corresponding to the input district
	 * 
	 * @param city
	 *            is a String representation of the city of the district
	 * @return a string representation of the image path
	 */
	public String getPath(String city) {
		if (city.equals("Como")) {
			switch (this) {
			case NORTH_WEST:
				return PATH_IMG_COMO_NORTH_WEST;
			case NORTH_EAST:
				return PATH_IMG_COMO_NORTH_EAST;
			case SOUTH_WEST:
				return PATH_IMG_COMO_SOUTH_WEST;
			default:
				return PATH_IMG_COMO_SOUTH_EAST;
			}
		} else if (city.equals("Lecco")) {
			switch (this) {
			case NORTH_WEST:
				return PATH_IMG_LECCO_NORTH_WEST;
			case NORTH_EAST:
				return PATH_IMG_LECCO_NORTH_EAST;
			case SOUTH_WEST:
				return PATH_IMG_LECCO_SOUTH_WEST;
			default:
				return PATH_IMG_LECCO_SOUTH_EAST;
			}
		} else if (city.equals("Varese")) {
			switch (this) {
			case NORTH_WEST:
				return PATH_IMG_VARESE_NORTH_WEST;
			case NORTH_EAST:
				return PATH_IMG_VARESE_NORTH_EAST;
			case SOUTH_WEST:
				return PATH_IMG_VARESE_SOUTH_WEST;
			default:
				return PATH_IMG_VARESE_SOUTH_EAST;
			}
		}

		return null;
	}

	/**
	 * Get an Italian string representation of the district
	 * 
	 * @return a string representation of the district
	 */
	@Override
	public String toString() {
		switch (this) {
		case NORTH_WEST:
			return "Nord-ovest";
		case NORTH_EAST:
			return "Nord-est";
		case SOUTH_WEST:
			return "Sud-ovest";
		case SOUTH_EAST:
			return "Sud-est";
		default:
			return null;
		}
	}

	/**
	 * Verify if the input coordinates belongs to the district
	 * 
	 * @param latitude
	 *            is the latitude of the point
	 * 
	 * @param longitude
	 *            is the longitude of the point
	 * 
	 * @return true if the coordinates belongs to the district, false otherwise
	 */
	public boolean belongs(double latitude, double longitude) {
		switch (this) {
		case NORTH_WEST:
			if (latitude <= (MAX_LATITUDE / 2.0) && longitude <= (MAX_LONGITUDE / 2.0)) {
				return true;
			}
			return false;
		case NORTH_EAST:
			if (latitude <= (MAX_LATITUDE / 2.0) && longitude > (MAX_LONGITUDE / 2.0)) {
				return true;
			}
			return false;
		case SOUTH_WEST:
			if (latitude > (MAX_LATITUDE / 2.0) && longitude <= (MAX_LONGITUDE / 2.0)) {
				return true;
			}
			return false;
		case SOUTH_EAST:
			if (latitude > (MAX_LATITUDE / 2.0) && longitude > (MAX_LONGITUDE / 2.0)) {
				return true;
			}
			return false;
		default:
			return false;
		}
	}

	/**
	 * Verify if the latitude in input is between MAX_LATITUDE and MIN_LATITUDE
	 * 
	 * @param latitude
	 *            is the latitude that need to be verified
	 * @return latitude in input if it is between MAX_LATITUDE and MIN_LATITUDE,
	 *         MAX_LATITUDE if it is higher than MAX_LATITUDE or MIN_LATITUDE if
	 *         it is lower than MIN_LATITUDE
	 */
	public double verifyLatitude(double latitude) {
		if (this.equals(NORTH_WEST) || this.equals(NORTH_EAST)) {
			if (latitude > (MAX_LATITUDE / 2.0)) {
				return (MAX_LATITUDE / 2.0);
			} else if (latitude < 0.0) {
				return 0.0;
			}
		} else if (this.equals(SOUTH_WEST) || this.equals(SOUTH_EAST)) {
			if (latitude < (MAX_LATITUDE / 2.0)) {
				return (MAX_LATITUDE / 2.0);
			} else if (latitude > MAX_LATITUDE) {
				return MAX_LATITUDE;
			}
		}
		return latitude;
	}

	/**
	 * Verify if the longitude in input is between MAX_LONGITUDE and
	 * MIN_LONGITUDE
	 * 
	 * @param longitude
	 *            is the longitude that need to be verified
	 * @return longitude in input if it is between MAX_LATITUDE and
	 *         MIN_LONGITUDE, MAX_LONGITUDE if it is higher than MAX_LONGITUDE
	 *         or MIN_LONGITUDE if it is lower than MIN_LONGITUDE
	 */
	public double verifyLongitude(double longitude) {
		if (this.equals(NORTH_WEST) || this.equals(SOUTH_WEST)) {
			if (longitude > (MAX_LONGITUDE / 2.0)) {
				return (MAX_LONGITUDE / 2.0);
			} else if (longitude < 0.0) {
				return 0.0;
			}
		} else if (this.equals(NORTH_EAST) || this.equals(SOUTH_EAST)) {
			if (longitude > MAX_LONGITUDE) {
				return MAX_LONGITUDE;
			} else if (longitude < (MAX_LONGITUDE / 2.0)) {
				return (MAX_LONGITUDE / 2.0);
			}
		}
		return longitude;
	}

	/**
	 * Convert the Italian string in input in the corresponding district
	 * 
	 * @param district
	 *            is the <code>String</code> to convert
	 * @return <code>District</code> is the corresponding <code>District</code>
	 *         enum
	 */
	public static District stringToEnum(String district) {
		switch (district) {
		case "Nord-ovest":
			return NORTH_WEST;
		case "Nord-est":
			return NORTH_EAST;
		case "Sud-ovest":
			return SOUTH_WEST;
		case "Sud-est":
			return SOUTH_EAST;
		default:
			return null;
		}
	}

	/**
	 * Create an array with the different district
	 * 
	 * @return strings array with the different district
	 */
	public static String[] arrayDistricts() {
		District[] arrayDistricts = District.values();
		String[] stringsArrayDistricts = new String[arrayDistricts.length];
		int count = 0;
		for (District district : arrayDistricts) {
			stringsArrayDistricts[count++] = district.toString();
		}
		return stringsArrayDistricts;
	}

	/**
	 * Get a string representation of the district which the input coordinates
	 * belongs
	 * 
	 * @param latitudine
	 *            is the latitude of the point
	 * @param longitudine
	 *            is the longitude of the point
	 * @return a string representation of the district which belongs the
	 *         coordinates
	 */
	public static String districtCoordinates(double latitudine, double longitudine) {
		if (latitudine <= (MAX_LATITUDE / 2.0) && longitudine <= (MAX_LONGITUDE / 2.0)) {
			return "Nord-ovest";
		} else if (latitudine <= (MAX_LATITUDE / 2.0) && longitudine > (MAX_LONGITUDE / 2.0)) {
			return "Nord-est";
		} else if (latitudine > (MAX_LATITUDE / 2.0) && longitudine > (MAX_LONGITUDE / 2.0)) {
			return "Sud-est";
		} else if (latitudine > (MAX_LATITUDE / 2.0) && longitudine <= (MAX_LONGITUDE / 2.0)) {
			return "Sud-ovest";
		} else {
			return null;
		}
	}
}