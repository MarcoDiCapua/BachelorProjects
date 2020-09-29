package server;
import java.util.HashMap;

/**
 * This class provides methods to keep track of the userId of the users logged
 * and force the logout of a user when the same user log from another client
 *
 * @author Marco Di Capua
 * @author Mattia Lo Schiavo
 * @author Filippo Pelosi
 * @author Riccardo Zorzi
 * @version 1.0
 */
public class LoginController {
	// camps
	private HashMap<String, Skeleton> usersLogged;
	private static LoginController controller = null;

	private LoginController() {
		super();
		this.usersLogged = new HashMap<String, Skeleton>();
	}

	/**
	 * Remove from the LoginController the given fiscal code, if exists
	 * 
	 * @param fiscalCode
	 *            the fiscal code to remove
	 */
	public void remove(String fiscalCode) {
		usersLogged.remove(fiscalCode);
	}

	/**
	 * Add the given fiscal code and skeleton to the LoginController. If the given
	 * fiscal code is already logged force the logout of the user from the client
	 * 
	 * @param fiscalCode
	 *            the fiscal code to add
	 * @param skeleton
	 *            the skeleton to add that will be used to communicate with client
	 * @return a string representing the outcome of the operation
	 */
	public synchronized String add(String fiscalCode, Skeleton skeleton) {
		String str = "Operation completed";
		if (usersLogged.containsKey(fiscalCode)) {
			usersLogged.get(fiscalCode).setForcedLogout(fiscalCode);
			usersLogged.remove(fiscalCode);
			str = "An user was already logged";
		}
		usersLogged.put(fiscalCode, skeleton);
		return str;
	}

	/**
	 * Returns the LoginController object
	 * 
	 * @return the LoginController object
	 */
	public static LoginController instance() {
		if (controller == null) {
			controller = new LoginController();
		}
		return controller;
	}
}
