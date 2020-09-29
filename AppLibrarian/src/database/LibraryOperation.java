package database;
import java.sql.SQLException;
import java.util.HashMap;

import dataModel.Command;
import database.DbManager;
import utils.Operation;

/**
 * This class provides methods to handle library operations
 *
 * @author Marco Di Capua
 * @author Mattia Lo Schiavo
 * @author Filippo Pelosi
 * @author Riccardo Zorzi
 * @version 1.0
 */
@SuppressWarnings("unchecked")
public class LibraryOperation {

	/**
	 * Execute the update books request
	 * 
	 * @param command
	 *            a command with the page number requested
	 * @return a Command with the outcome of the operation and a list with the
	 *         updated books
	 * @throws ClassNotFoundException
	 *             if the Book or the Driver classes could not be found
	 * @throws SQLException
	 *             if an SQL occurs while executing the request
	 */
	public static Command updateBooks(Command command) throws ClassNotFoundException, SQLException {
		HashMap<String, Object> resultMap = (HashMap<String, Object>) DbManager.instance()
				.selectData(Operation.LIBRARY_UPDATE_BOOKS, command);
		return new Command("Operation completed", resultMap);
	}

	/**
	 * Execute the update bookings request
	 * 
	 * @param command
	 *            a command with the page number requested
	 * @return a Command with the outcome of the operation and a list with the
	 *         updated bookings
	 * @throws ClassNotFoundException
	 *             if the Booking or the Driver classes could not be found
	 * @throws SQLException
	 *             if an SQL occurs while executing the request
	 */
	public static Command updateBookings(Command command) throws ClassNotFoundException, SQLException {
		HashMap<String, Object> resultMap = (HashMap<String, Object>) DbManager.instance()
				.selectData(Operation.LIBRARY_UPDATE_BOOKINGS, command);
		return new Command("Operation completed", resultMap);
	}

	/**
	 * Execute the update loans request
	 * 
	 * @param command
	 *            a command with the page number requested
	 * @return a Command with the outcome of the operation and a list with the
	 *         updated loans
	 * @throws ClassNotFoundException
	 *             if the Loan or the Driver classes could not be found
	 * @throws SQLException
	 *             if an SQL occurs while executing the request
	 */
	public static Command updateLoans(Command command) throws ClassNotFoundException, SQLException {
		HashMap<String, Object> resultMap = (HashMap<String, Object>) DbManager.instance()
				.selectData(Operation.LIBRARY_UPDATE_LOANS, command);
		return new Command("Operation completed", resultMap);
	}

	/**
	 * Execute the filter books request
	 * 
	 * @param command
	 *            a Command with the filters
	 * @return a Command with the outcome of the operation and a list with the
	 *         filtered books
	 * @throws ClassNotFoundException
	 *             if the Book or the Driver classes could not be found
	 * @throws SQLException
	 *             if an SQL occurs while executing the request
	 */
	public static Command booksFilter(Command command) throws ClassNotFoundException, SQLException {
		HashMap<String, Object> resultMap = (HashMap<String, Object>) DbManager.instance()
				.selectData(Operation.LIBRARY_BOOKS_FILTER, command);
		return new Command("Operation completed", resultMap);
	}

	/**
	 * Execute the filter bookings request
	 * 
	 * @param command
	 *            a Command with the filters
	 * @return a Command with the outcome of the operation and a list with the
	 *         filtered bookings
	 * @throws ClassNotFoundException
	 *             if the Booking or the Driver classes could not be found
	 * @throws SQLException
	 *             if an SQL occurs while executing the request
	 */
	public static Command bookingsFilter(Command command) throws ClassNotFoundException, SQLException {
		HashMap<String, Object> resultMap = (HashMap<String, Object>) DbManager.instance()
				.selectData(Operation.LIBRARY_BOOKINGS_FILTER, command);
		return new Command("Operation completed", resultMap);
	}

	/**
	 * Execute the filter loans request
	 * 
	 * @param command
	 *            a Command with the filters
	 * @return a Command with the outcome of the operation and a list with the
	 *         filtered loans
	 * @throws ClassNotFoundException
	 *             if the Loan or the Driver classes could not be found
	 * @throws SQLException
	 *             if an SQL occurs while executing the request
	 */
	public static Command loansFilter(Command command) throws ClassNotFoundException, SQLException {
		HashMap<String, Object> resultMap = (HashMap<String, Object>) DbManager.instance()
				.selectData(Operation.LIBRARY_LOANS_FILTER, command);
		return new Command("Operation completed", resultMap);
	}

	/**
	 * Execute the filter overwhelming loans request
	 * 
	 * @param command
	 *            a Command with the filters
	 * @return a Command with the outcome of the operation and a list with the
	 *         filtered overwhelming loans
	 * @throws ClassNotFoundException
	 *             if the Loans or the Driver classes could not be found
	 * @throws SQLException
	 *             if an SQL occurs while executing the request
	 */
	public static Command overwhelmingLoansFilter(Command command) throws ClassNotFoundException, SQLException {
		HashMap<String, Object> resultMap = (HashMap<String, Object>) DbManager.instance()
				.selectData(Operation.LIBRARY_OVERWHELMING_LOANS_FILTER, command);
		return new Command("Operation completed", resultMap);
	}

	/**
	 * Execute the filter history loans user request
	 * 
	 * @param command
	 *            a Command with the filters
	 * @return a Command with the outcome of the operation and a list with the
	 *         filtered history loans user
	 * @throws ClassNotFoundException
	 *             if the Loan or the Driver classes could not be found
	 * @throws SQLException
	 *             if an SQL occurs while executing the request
	 */
	public static Command historyLoansUserFilter(Command command) throws ClassNotFoundException, SQLException {
		HashMap<String, Object> resultMap = (HashMap<String, Object>) DbManager.instance()
				.selectData(Operation.LIBRARY_HISTORY_LOANS_USER_FILTER, command);
		return new Command("Operation completed", resultMap);
	}

	/**
	 * Execute the absolute rank request
	 * 
	 * @param command
	 *            a Command with the filters
	 * @return a Command with the outcome of the operation and a list with the
	 *         absolute rank
	 * @throws ClassNotFoundException
	 *             if the Book or the Driver classes could not be found
	 * @throws SQLException
	 *             if an SQL occurs while executing the request
	 */
	public static Command absoluteRank(Command command) throws ClassNotFoundException, SQLException {
		HashMap<String, Object> resultMap = (HashMap<String, Object>) DbManager.instance()
				.selectData(Operation.LIBRARY_ABSOLUTE_RANK, command);
		return new Command("Operation completed", resultMap);
	}

	/**
	 * Execute the category rank request
	 * 
	 * @param command
	 *            a Command with the category
	 * @return a Command with the outcome of the operation and a list with the
	 *         category rank
	 * @throws ClassNotFoundException
	 *             if the Book or the Driver classes could not be found
	 * @throws SQLException
	 *             if an SQL occurs while executing the request
	 */
	public static Command categoryRank(Command command) throws ClassNotFoundException, SQLException {
		HashMap<String, Object> resultMap = (HashMap<String, Object>) DbManager.instance()
				.selectData(Operation.LIBRARY_CATEGORY_RANK, command);
		return new Command("Operation completed", resultMap);
	}

	/**
	 * Execute the classification rank request
	 * 
	 * @param command
	 *            a Command with the classification
	 * @return a Command with the outcome of the operation and a list with the
	 *         classification rank
	 * @throws ClassNotFoundException
	 *             if the Book or the Driver classes could not be found
	 * @throws SQLException
	 *             if an SQL occurs while executing the request
	 */
	public static Command classificationRank(Command command) throws ClassNotFoundException, SQLException {
		HashMap<String, Object> resultMap = (HashMap<String, Object>) DbManager.instance()
				.selectData(Operation.LIBRARY_CLASSIFICATION_RANK, command);
		return new Command("Operation completed", resultMap);
	}

	/**
	 * Execute the all overwhelming loans request
	 * @param command
	 *            a Command with the filters 
	 * @return a Command with the outcome of the operation and a list with the
	 *         overwhelming loans
	 * @throws ClassNotFoundException
	 *             if the Loan or the Driver classes could not be found
	 * @throws SQLException
	 *             if an SQL occurs while executing the request
	 */
	public static Command allOverwhelmingLoans(Command command) throws ClassNotFoundException, SQLException {
		HashMap<String, Object> resultMap = (HashMap<String, Object>) DbManager.instance()
				.selectData(Operation.LIBRARY_ALL_OVERWHELMING_LOANS, command);
		return new Command("Operation completed", resultMap);
	}
}
