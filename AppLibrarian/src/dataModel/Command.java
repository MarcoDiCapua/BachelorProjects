package dataModel;

import java.io.Serializable;

/**
 * An object of the <code>Command</code> class keeps command information
 *
 * @author Marco Di Capua
 * @author Mattia Lo Schiavo
 * @author Filippo Pelosi
 * @author Riccardo Zorzi
 * @version 1.0
 */
public class Command implements Serializable {
	// camps
	private static final long serialVersionUID = 1L;
	private String operation;
	private Object data;

	/**
	 * Create a new object <code>Command</code> with the operation and data in input
	 * 
	 * @param operation
	 *            the command operation
	 * @param data
	 *            the command data that will be processed
	 */
	public Command(String operation, Object data) {
		super();
		this.operation = operation;
		this.data = data;
	}

	/**
	 * @return the operation
	 */
	public String getOperation() {
		return operation;
	}

	/**
	 * @param operation
	 *            the operation to set
	 */
	public void setOperation(String operation) {
		this.operation = operation;
	}

	/**
	 * @return the data
	 */
	public Object getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(Object data) {
		this.data = data;
	}

}
