package dataModel;

/**
 * User classification that can be used
 *
 * @author Marco Di Capua
 * @author Mattia Lo Schiavo
 * @author Filippo Pelosi
 * @author Riccardo Zorzi
 * @version 1.0
 */
public enum Classification {
	/**
	 * Administrator classification
	 */
	ADMINISTRATOR("Amministrativo"),
	/**
	 * Lbrarian classification
	 */
	LIBRARIAN("Bibliotecario"),
	/**
	 * Teacher classification
	 */
	TEACHER("Docente"),
	/**
	 * Technician classification
	 */
	TECHNICIAN("Tecnico"),
	/**
	 * Student classification
	 */
	STUDENT("Studente");

	// camps
	private String name;

	private Classification(String name) {
		this.name = name;
	}

	/**
	 * Get an Italian string representation of the classification
	 * 
	 * @return a string representation of the classification
	 * 
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return this.name;
	}
}
