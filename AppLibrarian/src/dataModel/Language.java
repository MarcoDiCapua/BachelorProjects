package dataModel;

/**
 * Book languages that can be used
 *
 * @author Marco Di Capua
 * @author Mattia Lo Schiavo
 * @author Filippo Pelosi
 * @author Riccardo Zorzi
 * @version 1.0
 */
public enum Language {
	/**
	 * Italian language
	 */
	ITALIAN("Italiano"),
	/**
	 * French language
	 */
	FRENCH("Francese"),
	/**
	 * German language
	 */
	GERMAN("Tedesco"),
	/**
	 * Spanish language
	 */
	SPANISH("Spagnolo"),
	/**
	 * Chinese language
	 */
	CHINESE("Cinese");

	// camps
	private String name;

	private Language(String name) {
		this.name = name;
	}

	/**
	 * Get an Italian string representation of the language
	 * 
	 * @return a string representation of the language
	 * 
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return this.name;
	}
}
