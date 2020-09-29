package database;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import dataModel.Command;
import dataModel.Booking;
import dataModel.Classification;
import dataModel.Loan;
import dataModel.Login;
import dataModel.User;
import database.DbManager;
import server.EmailSender;
import utils.Operation;
import utils.RandomString;

/**
 * This class provides methods to handle user operations
 *
 * @author Marco Di Capua
 * @author Mattia Lo Schiavo
 * @author Filippo Pelosi
 * @author Riccardo Zorzi
 * @version 1.0
 */
@SuppressWarnings("unchecked")
public class UserOperation {
	/**
	 * Execute the login request
	 * 
	 * @param command
	 *            the command with the login operation and data
	 * @return a Command with the outcome of the operation and the user
	 *         corresponding to the login data, if exists
	 * @throws ClassNotFoundException
	 *             if the Login or the Driver classes could not be found
	 * @throws SQLException
	 *             if an SQL occurs while executing the request
	 */
	public static Command login(Command command) throws ClassNotFoundException, SQLException {
		Login login = (Login) command.getData();
		login.encryptPassword();
		User newUser = (User) DbManager.instance().selectData(Operation.LOGIN_USER, login);
		if (newUser != null) {
			if (!newUser.getClassification().equals(Classification.LIBRARIAN)) {
				ArrayList<Booking> bookingsUser = (ArrayList<Booking>) DbManager.instance()
						.selectData(Operation.BOOKING_BOOKINGS_USER, newUser);
				ArrayList<Loan> loansUser = (ArrayList<Loan>) DbManager.instance().selectData(Operation.LOAN_LOANS_USER,
						newUser);
				newUser.setBookingBooks(bookingsUser);
				newUser.setLoanBooks(loansUser);
			}
			return new Command("Operation completed", newUser);
		} else {
			return new Command("User not exist", null);
		}
	}

	/**
	 * Execute the login request from a librarian account
	 * 
	 * @param command
	 *            the command with the login operation and data
	 * @return a Command with the outcome of the operation and the user
	 *         corresponding to the login data, if exists
	 * @throws ClassNotFoundException
	 *             if the Login or the Driver classes could not be found
	 * @throws SQLException
	 *             if an SQL occurs while executing the request
	 */
	public static Command loginFromLibrarian(Command command) throws ClassNotFoundException, SQLException {
		Login login = (Login) command.getData();
		User newUser = (User) (User) DbManager.instance().selectData(Operation.LOGIN_FROM_LIBRARIAN, login);
		if (newUser != null) {
			ArrayList<Booking> bookingsUser = (ArrayList<Booking>) DbManager.instance()
					.selectData(Operation.BOOKING_BOOKINGS_USER, newUser);
			ArrayList<Loan> loansUser = (ArrayList<Loan>) DbManager.instance().selectData(Operation.LOAN_LOANS_USER,
					newUser);
			newUser.setBookingBooks(bookingsUser);
			newUser.setLoanBooks(loansUser);
			return new Command("Operation completed", newUser);
		} else {
			return new Command("User not exist", null);
		}
	}

	/**
	 * Execute the registration request
	 * 
	 * @param command
	 *            the command with the new user informations
	 * @return a Command with the outcome of the operation
	 * @throws ClassNotFoundException
	 *             if the User or the Driver classes could not be found
	 * @throws SQLException
	 *             if an SQL occurs while executing the request
	 */
	public static Command registration(Command command) throws ClassNotFoundException, SQLException {
		User user = (User) command.getData();
		String pwdUser = new String(user.getPassword());
		user.encryptPassword();
		user.setActivationCode(RandomString.generate());
		DbManager.instance().insertData(Operation.USER_REGISTRATION, user);
		new EmailSender(Operation.USER_REGISTRATION, pwdUser, user);
		return new Command("Operation completed", null);
	}

	/**
	 * Execute the registration request from a librarian account
	 * 
	 * @param command
	 *            the command with the new user information
	 * @return a Command with the outcome of the operation
	 * @throws ClassNotFoundException
	 *             if the User or the Driver classes could not be found
	 * @throws SQLException
	 *             if an SQL occurs while executing the request
	 */
	public static Command registationFromLibrarian(Command command) throws ClassNotFoundException, SQLException {
		User userRegistration = (User) command.getData();
		userRegistration.setActivationCode(RandomString.generate());
		String newPwd = RandomString.generate();
		userRegistration.setPassword(newPwd);
		String pwdUsr = new String(userRegistration.getPassword());
		userRegistration.encryptPassword();
		userRegistration.setTemporaneyPassword(true);
		DbManager.instance().insertData(Operation.USER_REGISTRATION, userRegistration);
		new EmailSender(Operation.USER_REGISTRATION_FROM_LIBRARIAN, pwdUsr, userRegistration);
		return new Command("Operation completed", null);
	}

	/**
	 * Execute the reset password request
	 * 
	 * @param command
	 *            the command with the userId corresponding to the account to reset
	 * @return a Command with the outcome of the operation
	 * @throws ClassNotFoundException
	 *             if the User or the Driver classes could not be found
	 * @throws SQLException
	 *             if an SQL occurs while executing the request
	 */
	public static Command resetPassword(Command command) throws ClassNotFoundException, SQLException {
		String userId = (String) command.getData();
		User userToReset = (User) DbManager.instance().selectData("Select user from fiscal code", userId);
		if (userToReset != null) {
			if (userToReset.getActivationCode() == null) {
				String newPassword = RandomString.generate();
				userToReset.setPassword(newPassword);
				String pwd = new String(userToReset.getPassword());
				userToReset.encryptPassword();
				String newActivationCode = RandomString.generate();
				userToReset.setActivationCode(newActivationCode);
				DbManager.instance().updateData(Operation.USER_RESET_PASSWORD, userToReset);
				new EmailSender(Operation.USER_RESET_PASSWORD, pwd, userToReset);
				return new Command("Operation completed", null);
			} else {
				return new Command("User not active", null);
			}
		} else {
			return new Command("User not exist", null);
		}
	}

	/**
	 * Execute the modify email request
	 * 
	 * @param command
	 *            the command with the new email
	 * @param userLogged
	 *            the user logged to modify
	 * @return a Command with the outcome of the operation and the new activation
	 *         code
	 */
	public static Command modifyEmail(Command command, User userLogged) {
		String newEmail = (String) command.getData();
		String code = RandomString.generate();
		userLogged.setEmail(newEmail);
		userLogged.setActivationCode(code);
		new EmailSender(Operation.USER_MODIFY_EMAIL, null, userLogged);
		return new Command("Operation completed", userLogged.getActivationCode());
	}

	/**
	 * Execute the confirm modify email request
	 * 
	 * @param command
	 *            the command with the outcome of the activation procedure and the
	 *            old user email
	 * @param userLogged
	 *            the user logged to modify
	 * @return a Command with the outcome of the operation
	 *
	 * @throws ClassNotFoundException
	 *             if the User or the Driver classes could not be found
	 * @throws SQLException
	 *             if an SQL occurs while executing the request
	 */
	public static Command confirmModifyEmail(Command command, User userLogged)
			throws ClassNotFoundException, SQLException {
		HashMap<String, Object> mapEmail = (HashMap<String, Object>) command.getData();
		boolean response = (boolean) mapEmail.get("Response");
		if (response) {
			DbManager.instance().updateData(Operation.USER_MODIFY_EMAIL, userLogged);
			return new Command("Operation completed", null);
		} else {
			String oldEmail = (String) mapEmail.get("Old email");
			userLogged.setEmail(oldEmail);
			userLogged.setActivationCode(null);
			return new Command("Operation canceled", null);
		}
	}

	/**
	 * Execute the modify classification request
	 * 
	 * @param command
	 *            the command with the new classification and year and class
	 * @param userLogged
	 *            the user logged to modify
	 * @return a Command with the outcome of the operation
	 *
	 * @throws ClassNotFoundException
	 *             if the User or the Driver classes could not be found
	 * @throws SQLException
	 *             if an SQL occurs while executing the request
	 */
	public static Command modifyClassification(Command command, User userLogged)
			throws ClassNotFoundException, SQLException {
		HashMap<String, Object> mapData = (HashMap<String, Object>) command.getData();
		Classification newClassification = (Classification) mapData.get("Classification");
		userLogged.setClassification(newClassification);
		String newYear_class = (String) mapData.get("Year_class");
		userLogged.setYear_class(newYear_class);
		DbManager.instance().updateData(Operation.USER_MODIFY_CLASSIFICATION, userLogged);
		new EmailSender(Operation.USER_MODIFY_CLASSIFICATION, null, userLogged);
		return new Command("Operation completed", null);
	}

	/**
	 * Execute the modify telephone number request
	 * 
	 * @param command
	 *            the command with the new telephone number
	 * @param userLogged
	 *            the user logged to modify
	 * @return a Command with the outcome of the operation
	 *
	 * @throws ClassNotFoundException
	 *             if the User or the Driver classes could not be found
	 * @throws SQLException
	 *             if an SQL occurs while executing the request
	 */
	public static Command modifyTelephone(Command command, User userLogged)
			throws ClassNotFoundException, SQLException {
		String newTelephone = (String) command.getData();
		userLogged.setTelephoneNumber(newTelephone);
		DbManager.instance().updateData(Operation.USER_MODIFY_TELEPHONE, userLogged);
		new EmailSender(Operation.USER_MODIFY_TELEPHONE, null, userLogged);
		return new Command("Operation completed", null);
	}

	/**
	 * Execute the modify password request
	 * 
	 * @param command
	 *            the command with the new password
	 * @param userLogged
	 *            the user logged to modify
	 * @return a Command with the outcome of the operation
	 *
	 * @throws ClassNotFoundException
	 *             if the User or the Driver classes could not be found
	 * @throws SQLException
	 *             if an SQL occurs while executing the request
	 */
	public static Command modifyPassword(Command command, User userLogged) throws ClassNotFoundException, SQLException {
		HashMap<String, String> passwordsMap = (HashMap<String, String>) command.getData();
		String oldPwdInserted = passwordsMap.get("Old password");
		String pwdNew = passwordsMap.get("New password");
		String actualPassword = new String(userLogged.getPassword());
		userLogged.setPassword(oldPwdInserted);
		userLogged.encryptPassword();
		if (userLogged.getPassword().equals(actualPassword)) {
			userLogged.setPassword(pwdNew);
			String pwdNotEncrypted = new String(userLogged.getPassword());
			userLogged.encryptPassword();
			DbManager.instance().updateData(Operation.USER_MODIFY_PASSWORD, userLogged);
			new EmailSender(Operation.USER_MODIFY_PASSWORD, pwdNotEncrypted, userLogged);
			return new Command("Operation completed", null);
		} else {
			return new Command("Wrong password", null);
		}
	}

	/**
	 * Execute the update counter request
	 * 
	 * @param command
	 *            the command with the new counter
	 * @param userLogged
	 *            the user logged to modify
	 * @return a Command with the outcome of the operation
	 *
	 * @throws ClassNotFoundException
	 *             if the User or the Driver classes could not be found
	 * @throws SQLException
	 *             if an SQL occurs while executing the request
	 */
	public static Command updateCounter(Command command, User userLogged) throws ClassNotFoundException, SQLException {
		int newCounter = (int) command.getData();
		if (newCounter == 0) {
			DbManager.instance().deleteData(Operation.USER_DELETE, userLogged);
		} else {
			userLogged.setCounter(newCounter);
			DbManager.instance().updateData(Operation.USER_UPDATE_COUNTER, userLogged);
		}
		return new Command("Operation completed", null);
	}

	/**
	 * Execute the activate profile request
	 * 
	 * @param userLogged
	 *            the user logged to activate
	 * @return a Command with the outcome of the operation
	 *
	 * @throws ClassNotFoundException
	 *             if the User or the Driver classes could not be found
	 * @throws SQLException
	 *             if an SQL occurs while executing the request
	 */
	public static Command activateProfile(User userLogged) throws ClassNotFoundException, SQLException {
		DbManager.instance().updateData(Operation.USER_ACTIVATE_PROFILE, userLogged);
		return new Command("Operation completed", null);
	}

	/**
	 * Execute the modify password request
	 * 
	 * @param user
	 *            the user logged to delete
	 * @return a command with the outcome of the operation
	 * @throws ClassNotFoundException
	 *             if the User or the Driver classes could not be found
	 * @throws SQLException
	 *             if an SQL occurs while executing the request
	 */
	public static Command delete(User user) throws ClassNotFoundException, SQLException {
		if (DbManager.instance().deleteData(Operation.USER_DELETE, user)) {
			return new Command("Operation completed", null);
		} else {
			return new Command("Operation failed", null);
		}
	}

}
