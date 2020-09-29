package dataModel;

/**
 * Book categories that can be used
 *
 * @author Marco Di Capua
 * @author Mattia Lo Schiavo
 * @author Filippo Pelosi
 * @author Riccardo Zorzi
 * @version 1.0
 */
public enum Category {
	/**
	 * Biology category
	 */
	BIOLOGY("Biologia"),
	/**
	 * Chemistry category
	 */
	CHEMISTRY("Chimica"),
	/**
	 * Rights category
	 */
	RIGHTS("Diritto"),
	/**
	 * Economic category
	 */
	ECONOMIC("Economia"),
	/**
	 * Philosophy category
	 */
	PHILOSOPHY("Filosofia"),
	/**
	 * Computer science category
	 */
	COMPUTER_SCIENCE("Informatica"),
	/**
	 * Engineering category
	 */
	ENGINEERING("Ingegneria"),
	/**
	 * Languages category
	 */
	LANGUAGES("Lingue"),
	/**
	 * Mathematics category
	 */
	MATHEMATICS("Matematica"),
	/**
	 * Medicine category
	 */
	MEDICINE("Medicina"),
	/**
	 * Psychology category
	 */
	PSYCHOLOGY("Psicologia");

	private String name;

	private Category(String name) {
		this.name = name;
	}

	/**
	 * Get an Italian string representation of the category
	 * 
	 * @return a string representation of the category
	 * 
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return this.name;
	}
}
