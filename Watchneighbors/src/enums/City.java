package enums;

/**
 * An Enum keeps city informations
 *
 * @author Marco Di Capua
 * @author Filippo Pelosi
 * @author Riccardo Zorzi
 * @version 1.0
 */

public enum City {
	// camps
	/**
	 * 
	 */
	COMO,

	/**
	 * 
	 */
	LECCO,

	/**
	 * 
	 */
	VARESE;

	private static final String PATH_IMG_COMO = ".\\images\\Como.png";
	private static final String PATH_IMG_LECCO = ".\\images\\Lecco.png";
	private static final String PATH_IMG_VARESE = ".\\images\\Varese.png";

	// methods
	/**
	 * Get the image path of the city
	 * 
	 * @return a string representation of the image path
	 */
	public String getPath() {
		switch (this) {
		case COMO:
			return PATH_IMG_COMO;
		case LECCO:
			return PATH_IMG_LECCO;
		case VARESE:
			return PATH_IMG_VARESE;
		default:
			return null;
		}
	}

	/**
	 * Get an Italian string representation of the city
	 * 
	 * @return a string representation of the city
	 */
	public String toString() {
		switch (this) {
		case COMO:
			return "Como"; // conversione da tipo Enumerativo a stringa
		case LECCO:
			return "Lecco";
		case VARESE:
			return "Varese";
		default:
			return null;
		}
	}

	/**
	 * Convert the Italian string in input in the corresponding city
	 * 
	 * @param city
	 *            is the <code>String</code> to convert
	 * @return <code>City</code> is the corresponding <code>City</code> enum
	 */
	public static City stringToEnum(String city) {
		switch (city) {
		case "Como":
			return COMO;
		case "Lecco":
			return LECCO;
		case "Varese":
			return VARESE;
		default:
			return null;
		}
	}

	/**
	 * create an array with the different city
	 * 
	 * @return strings array with the different city
	 */
	public static String[] createCitiesArray() {

		City[] citiesArray = City.values();
		String[] stringsCitiesArray = new String[citiesArray.length];
		int count = 0;
		for (City city : citiesArray) {
			stringsCitiesArray[count++] = city.toString();
		}
		return stringsCitiesArray;
	}

}