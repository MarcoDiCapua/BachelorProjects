package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import dataModel.Booking;
import dataModel.Classification;
import dataModel.Loan;
import dataModel.Login;
import dataModel.User;
import utils.Operation;

/**
 * This class provides methods to handle queries about users table and return
 * the selected informations
 *
 * @author Marco Di Capua
 * @author Mattia Lo Schiavo
 * @author Filippo Pelosi
 * @author Riccardo Zorzi
 * @version 1.0
 */
public class DatabaseUser {
	/**
	 * Executes the login query
	 * 
	 * @param executor
	 *            the executor that execute the query
	 * @param login
	 *            the login informations
	 * @return a User object representing the user corresponding to the login
	 *         informations, if exists
	 * @throws SQLException
	 *             if an SQL error occurs while executing the query
	 */
	public static User loginUser(QueryExecutor executor, Login login) throws SQLException {
		String sqlCode = login.getType().equals("Librarian") ? PredefinedSQLCode.loginLibrarian
				: PredefinedSQLCode.loginReader;
		LoginQuery loginQuery = new LoginQuery(sqlCode, Operation.LOGIN_USER, login);
		ResultSet rs = executor.executeQuery(loginQuery);
		User user = null;
		if (rs.next()) {
			user = new User(rs.getString("name"), rs.getString("surname"), rs.getString("fiscal_code"),
					rs.getString("email"), Classification.valueOf(rs.getString("classification")),
					rs.getString("year_class"), rs.getString("telephone_number"), rs.getString("password"),
					rs.getBoolean("temporaney_pwd"), rs.getString("activation_code"), rs.getInt("counter"));
		}
		close(loginQuery);
		return user;
	}

	/**
	 * Executes the login reader from librarian account query
	 * 
	 * @param executor
	 *            the executor that execute the query
	 * @param login
	 *            the login informations
	 * @return a User object representing the reader user corresponding to the login
	 *         informations, if exists
	 * @throws SQLException
	 *             if an SQL error occurs while executing the query
	 */
	public static User loginFromLibrarian(QueryExecutor executor, Login login) throws SQLException {
		LoginQuery loginQuery = new LoginQuery(PredefinedSQLCode.loginUserFromLibrarian, Operation.LOGIN_FROM_LIBRARIAN,
				login);
		ResultSet rs = executor.executeQuery(loginQuery);
		User user = null;
		if (rs.next()) {
			user = new User(rs.getString("name"), rs.getString("surname"), rs.getString("fiscal_code"),
					rs.getString("email"), Classification.valueOf(rs.getString("classification")),
					rs.getString("year_class"), rs.getString("telephone_number"), rs.getString("password"),
					rs.getBoolean("temporaney_pwd"), rs.getString("activation_code"), rs.getInt("counter"));
		}
		close(loginQuery);
		return user;
	}

	/**
	 * Executes the registration user query
	 * 
	 * @param executor
	 *            the executor that execute the query
	 * @param user
	 *            the new user informations
	 * @throws SQLException
	 *             if an SQL error occurs while executing the query
	 */
	public static void insertUser(QueryExecutor executor, User user) throws SQLException {
		String sqlCode = user.getClassification().equals(Classification.LIBRARIAN)
				? PredefinedSQLCode.registrationLibrarian
				: PredefinedSQLCode.registrationUser;
		UserQuery insertUser = new UserQuery(sqlCode, Operation.USER_REGISTRATION, user);
		executor.executeUpdate(insertUser);
		close(insertUser);
	}

	/**
	 * Executes the modify classification query
	 * 
	 * @param executor
	 *            the executor that execute the query
	 * @param user
	 *            the user informations
	 * @throws SQLException
	 *             if an SQL error occurs while executing the query
	 */
	public static void modifyClassication(QueryExecutor executor, User user) throws SQLException {
		String sqlCode = user.getClassification().equals(Classification.LIBRARIAN)
				? PredefinedSQLCode.updateClassificationLibrarian
				: PredefinedSQLCode.updateClassification;
		UserQuery modifyClassification = new UserQuery(sqlCode, Operation.USER_MODIFY_CLASSIFICATION, user);
		executor.executeUpdate(modifyClassification);
		close(modifyClassification);
	}

	/**
	 * Executes the modify telephone number query
	 * 
	 * @param executor
	 *            the executor that execute the query
	 * @param user
	 *            the user informations
	 * @throws SQLException
	 *             if an SQL error occurs while executing the query
	 */
	public static void modifyTelephone(QueryExecutor executor, User user) throws SQLException {
		String sqlCode = user.getClassification().equals(Classification.LIBRARIAN)
				? PredefinedSQLCode.updateTelephoneNumberLibrarian
				: PredefinedSQLCode.updateTelephoneNumber;
		UserQuery modifyTelephone = new UserQuery(sqlCode, Operation.USER_MODIFY_TELEPHONE, user);
		executor.executeUpdate(modifyTelephone);
		close(modifyTelephone);
	}

	/**
	 * Executes the modify email query
	 * 
	 * @param executor
	 *            the executor that execute the query
	 * @param user
	 *            the user informations
	 * @throws SQLException
	 *             if an SQL error occurs while executing the query
	 */
	public static void modifyEmail(QueryExecutor executor, User user) throws SQLException {
		String sqlCode = user.getClassification().equals(Classification.LIBRARIAN)
				? PredefinedSQLCode.updateEmailLibrarian
				: PredefinedSQLCode.updateEmail;
		UserQuery modifyClassification = new UserQuery(sqlCode, Operation.USER_MODIFY_EMAIL, user);
		executor.executeUpdate(modifyClassification);
		close(modifyClassification);
	}

	/**
	 * Executes the modify activation counter query
	 * 
	 * @param executor
	 *            the executor that execute the query
	 * @param user
	 *            the user informations
	 * @throws SQLException
	 *             if an SQL error occurs while executing the query
	 */
	public static void updateCounter(QueryExecutor executor, User user) throws SQLException {
		String sqlCode = user.getClassification().equals(Classification.LIBRARIAN)
				? PredefinedSQLCode.updateCounterLibrarian
				: PredefinedSQLCode.updateCounter;
		UserQuery updateCounter = new UserQuery(sqlCode, Operation.USER_UPDATE_COUNTER, user);
		executor.executeUpdate(updateCounter);
		close(updateCounter);
	}

	/**
	 * Executes the activate profile query
	 * 
	 * @param executor
	 *            the executor that execute the query
	 * @param user
	 *            the user informations
	 * @throws SQLException
	 *             if an SQL error occurs while executing the query
	 */
	public static void activateProfile(QueryExecutor executor, User user) throws SQLException {
		String sqlCode = user.getClassification().equals(Classification.LIBRARIAN)
				? PredefinedSQLCode.activateUserLibrarian
				: PredefinedSQLCode.activateUser;
		UserQuery activateProfile = new UserQuery(sqlCode, Operation.USER_ACTIVATE_PROFILE, user);
		executor.executeUpdate(activateProfile);
		close(activateProfile);
	}

	/**
	 * Executes the user history loans query
	 * 
	 * @param executor
	 *            the executor that execute the query
	 * @param informations
	 *            the user informations end the page number requested
	 * @return an ArrayList with the user history loans
	 * @throws SQLException
	 *             if an SQL error occurs while executing the query
	 */
	public static HashMap<String, Object> historyLoansUser(QueryExecutor executor, HashMap<String, Object> informations) throws SQLException {
		UserQuery userHistoryLoansQuery = new UserQuery(PredefinedSQLCode.selectHistoryLoansUser,
				Operation.LOAN_HISTORY_LOANS_USER, (User) informations.get("UserLogged"),
				(int) informations.get("Page"));
		ResultSet rsuserHistoryLoans = executor.executeQuery(userHistoryLoansQuery);
		ArrayList<Loan> loansList = DatabaseLoan.resultSetToLoansArray(executor, rsuserHistoryLoans,
				(User) informations.get("UserLogged"));
		close(userHistoryLoansQuery);
		UserQuery countUserHistoryLoans = new UserQuery(PredefinedSQLCode.countHistoryLoansUser,
				"Count user history loans", (User) informations.get("UserLogged"));
		ResultSet rscountUserHistoryLoans = executor.executeQuery(countUserHistoryLoans);
		rscountUserHistoryLoans.next();
		int count = rscountUserHistoryLoans.getInt(1);
		countUserHistoryLoans.close();
		int pages = count % 25 == 0 ? count / 25 : count / 25 + 1;
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("Pages", pages);
		resultMap.put("List", loansList);
		return resultMap;
	}

	/**
	 * Executes the user history loans query
	 * 
	 * @param executor
	 *            the executor that execute the query
	 * @param userToDelete
	 *            the user informations
	 * @return true if the delete operation completed successfully, false otherwise
	 * @throws SQLException
	 *             if an SQL error occurs while executing the query
	 */
	public static boolean deleteUser(QueryExecutor executor, User userToDelete) throws SQLException {
		UserQuery deleteUser = new UserQuery(PredefinedSQLCode.deleteUser, Operation.USER_DELETE, userToDelete);
		boolean bln = executor.executeDelete(deleteUser);
		close(deleteUser);
		return bln;
	}

	/**
	 * Executes the user overwhelming loans query
	 * 
	 * @param executor
	 *            the executor that execute the query
	 * @param userReader
	 *            the user informations
	 * @return true if user reader has overwhelming loans, false otherwise
	 * @throws SQLException
	 *             if an SQL error occurs while executing the query
	 */
	public static boolean userOverwhelmingLoans(QueryExecutor executor, User userReader) throws SQLException {
		UserQuery overwhelmingLoansQuery = new UserQuery(PredefinedSQLCode.selectOverwhelmingLoansUser,
				"Select user overwhelming loans", userReader);
		ResultSet rsOverwhelmingLoans = executor.executeQuery(overwhelmingLoansQuery);
		boolean bln = rsOverwhelmingLoans.next();
		close(overwhelmingLoansQuery);
		return bln;
	}

	/**
	 * Executes the select user form fiscal code query query
	 * 
	 * @param executor
	 *            the executor that execute the query
	 * @param fiscalCode
	 *            the user fiscal code
	 * @return a User object with the informations of the user corresponding to the
	 *         given fiscal code, if exists
	 * @throws SQLException
	 *             if an SQL error occurs while executing the query
	 */
	public static User userFromFiscalCode(QueryExecutor executor, String fiscalCode) throws SQLException {
		UserQuery selectUserQuery = new UserQuery(PredefinedSQLCode.selectUserFromFiscalCode,
				"Select user from fiscal code",
				new User(null, null, fiscalCode, null, null, null, null, null, false, null, 0));
		ResultSet rsUser = executor.executeQuery(selectUserQuery);
		User user = null;
		if (rsUser.next()) {
			user = new User(rsUser.getString("name"), rsUser.getString("surname"), rsUser.getString("fiscal_code"),
					rsUser.getString("email"), Classification.valueOf(rsUser.getString("classification")),
					rsUser.getString("year_class"), rsUser.getString("telephone_number"), rsUser.getString("password"),
					rsUser.getBoolean("temporaney_pwd"), rsUser.getString("activation_code"), rsUser.getInt("counter"));
		}
		close(selectUserQuery);
		return user;
	}

	/**
	 * Executes the reset password query
	 * 
	 * @param executor
	 *            the executor that execute the query
	 * @param userToReset
	 *            the user to reset password
	 * @throws SQLException
	 *             if an SQL error occurs while executing the query
	 */
	public static void resetPassord(QueryExecutor executor, User userToReset) throws SQLException {
		String sqlCode = userToReset.getClassification().equals(Classification.LIBRARIAN)
				? PredefinedSQLCode.updateActivationCodeAndPasswordLibrarian
				: PredefinedSQLCode.updateActivationCodeAndPassword;
		UserQuery resetPassword = new UserQuery(sqlCode, Operation.USER_RESET_PASSWORD, userToReset);
		executor.executeUpdate(resetPassword);
		close(resetPassword);
	}

	/**
	 * Executes the modify password query
	 * 
	 * @param executor
	 *            the executor that execute the query
	 * @param userLogged
	 *            the user logged informations
	 * @throws SQLException
	 *             if an SQL error occurs while executing the query
	 */
	public static void modifyPassword(QueryExecutor executor, User userLogged) throws SQLException {
		String sqlCode = userLogged.getClassification().equals(Classification.LIBRARIAN)
				? PredefinedSQLCode.updatePasswordLibrarian
				: PredefinedSQLCode.updatePassword;
		UserQuery modifyPassword = new UserQuery(sqlCode, Operation.USER_MODIFY_PASSWORD, userLogged);
		executor.executeUpdate(modifyPassword);
		close(modifyPassword);
	}

	/**
	 * Executes the user bookings query
	 * 
	 * @param executor
	 *            the executor that execute the query
	 * @param user
	 *            the user informations
	 * @return an ArrayList with the user bookings
	 * @throws SQLException
	 *             if an SQL error occurs while executing the query
	 */
	public static ArrayList<Booking> bookingsUser(QueryExecutor executor, User user) throws SQLException {
		UserQuery bookingsUser = new UserQuery(PredefinedSQLCode.selectBookingsUser, Operation.BOOKING_BOOKINGS_USER,
				user);
		ResultSet rsBookings = executor.executeQuery(bookingsUser);
		ArrayList<Booking> userBookings = DatabaseBooking.resultSetToBookingsArray(executor, rsBookings, user);
		close(bookingsUser);
		return userBookings;
	}

	/**
	 * Executes the user loans query
	 * 
	 * @param executor
	 *            the executor that execute the query
	 * @param user
	 *            the user informations
	 * @return an ArrayList with the user loans
	 * @throws SQLException
	 *             if an SQL error occurs while executing the query
	 */
	public static ArrayList<Loan> loansUser(QueryExecutor executor, User user) throws SQLException {
		UserQuery loansUser = new UserQuery(PredefinedSQLCode.selectLoansUser, Operation.LOAN_LOANS_USER, user);
		ResultSet rsLoans = executor.executeQuery(loansUser);
		ArrayList<Loan> userLoan = DatabaseLoan.resultSetToLoansArray(executor, rsLoans, user);
		close(loansUser);
		return userLoan;
	}

	private static void close(PreparableQuery query) throws SQLException {
		if (query != null) {
			query.close();
		}
	}

}
