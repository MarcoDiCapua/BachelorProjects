package database;

import java.sql.SQLException;
import java.util.HashMap;

import dataModel.Command;
import dataModel.Book;
import dataModel.Booking;
import dataModel.Loan;
import dataModel.Login;
import dataModel.User;
import utils.Operation;

/**
 * This class is a database manager that manages the operations to be performed
 * on the database
 *
 * @author Marco Di Capua
 * @author Mattia Lo Schiavo
 * @author Filippo Pelosi
 * @author Riccardo Zorzi
 * @version 1.0
 */
public class DbManager {
	private static String url;
	private static String user;
	private static String password;
	private boolean canExecute = true;
	private static DbManager manager = null;

	private DbManager() {
		super();
	}

	/**
	 * Execute the insert operations
	 * 
	 * @param operation
	 *            the insert operation to execute
	 * @param object
	 *            the object to insert
	 * @throws ClassNotFoundException
	 *             if the User, Book, Booking, Loan or the Driver classes could not
	 *             be found
	 * @throws SQLException
	 *             if an SQL error occurs while executing the insert query
	 */
	public void insertData(String operation, Object object) throws ClassNotFoundException, SQLException {
		QueryExecutor executor = new QueryExecutor(url, user, password);
		switch (operation) {
		case Operation.USER_REGISTRATION:
			DatabaseUser.insertUser(executor, (User) object);
			break;
		case Operation.BOOK_ADD:
			DatabaseBook.insertBook(executor, (Book) object);
			break;
		case Operation.BOOKING_ADD:
			DatabaseBooking.insertBooking(executor, (Booking) object);
			break;
		case Operation.LOAN_ADD:
			DatabaseLoan.insertLoan(executor, (Loan) object);
			break;
		case "Insert author":
			DatabaseBook.insertAuthor(executor, (Book) object);
			break;
		case "Insert write":
			DatabaseBook.insertWrite(executor, (Book) object);
		}
		executor.getConnection().close();
	}

	/**
	 * Execute the update operations
	 * 
	 * @param operation
	 *            the update operation to execute
	 * @param object
	 *            the object to update
	 * @throws ClassNotFoundException
	 *             if the User, Loan or the Driver classes could not be found
	 * @throws SQLException
	 *             if an SQL error occurs while executing the update query
	 */
	public void updateData(String operation, Object object) throws SQLException, ClassNotFoundException {
		QueryExecutor executor = new QueryExecutor(url, user, password);
		switch (operation) {
		case Operation.USER_MODIFY_EMAIL:
			DatabaseUser.modifyEmail(executor, (User) object);
			break;
		case Operation.USER_MODIFY_CLASSIFICATION:
			DatabaseUser.modifyClassication(executor, (User) object);
			break;
		case Operation.USER_MODIFY_TELEPHONE:
			DatabaseUser.modifyTelephone(executor, (User) object);
			break;
		case Operation.USER_MODIFY_PASSWORD:
			DatabaseUser.modifyPassword(executor, (User) object);
			break;
		case Operation.USER_UPDATE_COUNTER:
			DatabaseUser.updateCounter(executor, (User) object);
			break;
		case Operation.USER_ACTIVATE_PROFILE:
			DatabaseUser.activateProfile(executor, (User) object);
			break;
		case Operation.USER_RESET_PASSWORD:
			DatabaseUser.resetPassord(executor, (User) object);
			break;
		case Operation.LOAN_BACK:
			DatabaseLoan.loanBack(executor, (Loan) object);
			break;
		}
		executor.getConnection().close();
	}

	/**
	 * Execute the delete operations
	 * 
	 * @param operation
	 *            the delete operation to execute
	 * @param object
	 *            the object to delete
	 * @return true if the delete operation completed successfully, false otherwise
	 * @throws ClassNotFoundException
	 *             if the User, Book, Booking, Loan or the Driver classes could not
	 *             be found
	 * @throws SQLException
	 *             if an SQL error occurs while executing the delete query
	 */
	public synchronized boolean deleteData(String operation, Object object)
			throws SQLException, ClassNotFoundException {
		try {
			while (!canExecute) {
				wait();
			}
			canExecute = false;
			boolean response = false;
			QueryExecutor executor = new QueryExecutor(url, user, password);
			switch (operation) {
			case Operation.USER_DELETE:
				response = DatabaseUser.deleteUser(executor, (User) object);
				break;
			case Operation.BOOK_REMOVE:
				response = DatabaseBook.deleteBook(executor, (Book) object);
				break;
			case Operation.BOOKING_DELETE:
				response = DatabaseBooking.deleteBooking(executor, (Booking) object);
				break;
			case Operation.LOAN_DELETE:
				response = DatabaseLoan.deleteLoan(executor, (Loan) object);
				break;
			case "Delete author":
				response = DatabaseBook.deleteAuthor(executor, (Book) object);
				break;
			}
			canExecute = true;
			notify();
			executor.getConnection().close();
			return response;
		} catch (InterruptedException e) {
			return false;
		}
	}

	/**
	 * Execute the select operations
	 * 
	 * @param operation
	 *            the select operation to execute
	 * @param object
	 *            the object with the informations to select
	 * @return an object with the informations selected by the query
	 * @throws ClassNotFoundException
	 *             if the User, Book, Command, Login, Loan or the Driver classes
	 *             could not be found
	 * @throws SQLException
	 *             if an SQL error occurs while executing the select query
	 */
	@SuppressWarnings("unchecked")
	public synchronized Object selectData(String operation, Object object) throws ClassNotFoundException, SQLException {
		try {
			while (!canExecute) {
				wait();
			}
			canExecute = false;
			Object response = null;
			QueryExecutor executor = new QueryExecutor(url, user, password);
			switch (operation) {
			case Operation.LOGIN_USER:
				response = DatabaseUser.loginUser(executor, (Login) object);
				break;
			case Operation.LOGIN_FROM_LIBRARIAN:
				response = DatabaseUser.loginFromLibrarian(executor, (Login) object);
				break;
			case Operation.LIBRARY_UPDATE_BOOKINGS:
				response = DatabaseLibrary.updateBookings(executor, (Command) object);
				break;
			case Operation.LIBRARY_UPDATE_BOOKS:
				response = DatabaseLibrary.updateBooks(executor, (Command) object);
				break;
			case Operation.LIBRARY_UPDATE_LOANS:
				response = DatabaseLibrary.updateLoans(executor, (Command) object);
				break;
			case Operation.LIBRARY_BOOKS_FILTER:
				response = DatabaseLibrary.booksFilter(executor, (Command) object);
				break;
			case Operation.LIBRARY_BOOKINGS_FILTER:
				response = DatabaseLibrary.bookingsFilter(executor, (Command) object);
				break;
			case Operation.LIBRARY_LOANS_FILTER:
				response = DatabaseLibrary.loansFiter(executor, (Command) object);
				break;
			case Operation.LIBRARY_OVERWHELMING_LOANS_FILTER:
				response = DatabaseLibrary.overwhelmingLoansFilter(executor, (Command) object);
				break;
			case Operation.LIBRARY_HISTORY_LOANS_USER_FILTER:
				response = DatabaseLibrary.historyLoansUserFilter(executor, (Command) object);
				break;
			case Operation.LIBRARY_ABSOLUTE_RANK:
				response = DatabaseLibrary.absoluteRank(executor, (Command) object);
				break;
			case Operation.LIBRARY_CATEGORY_RANK:
				response = DatabaseLibrary.categoryRank(executor, (Command) object);
				break;
			case Operation.LIBRARY_CLASSIFICATION_RANK:
				response = DatabaseLibrary.classificationRank(executor, (Command) object);
				break;
			case Operation.LIBRARY_ALL_OVERWHELMING_LOANS:
				response = DatabaseLibrary.allOverwhelmingLoans(executor, (Command) object);
				break;
			case Operation.LOAN_HISTORY_LOANS_USER:
				response = DatabaseUser.historyLoansUser(executor, ((HashMap<String, Object>) object));
				break;
			case Operation.BOOKING_BOOKINGS_USER:
				response = DatabaseUser.bookingsUser(executor, (User) object);
				break;
			case Operation.LOAN_LOANS_USER:
				response = DatabaseUser.loansUser(executor, (User) object);
				break;
			case "Select all authors":
				response = DatabaseLibrary.allAuthors(executor);
				break;
			case "Select user booking book":
				response = DatabaseBook.userBookingBook(executor, (Book) object);
				break;
			case "Select users booking book":
				response = DatabaseBook.usersBookingBook(executor, (Book) object);
				break;
			case "Control already loan":
				response = DatabaseLoan.controlAlreadyLoan(executor, (Loan) object);
				break;
			case "Select user overwhelming loans":
				response = DatabaseUser.userOverwhelmingLoans(executor, (User) object);
				break;
			case "Select user from fiscal code":
				response = DatabaseUser.userFromFiscalCode(executor, (String) object);
				break;
			case "Control loan":
				response = DatabaseLoan.controlLoan(executor, (Loan) object);
			}
			canExecute = true;
			notify();
			executor.getConnection().close();
			return response;
		} catch (InterruptedException e) {
			return null;
		}
	}

	/**
	 * Returns the DbManager object
	 * 
	 * @return the DbManager object
	 */
	public static DbManager instance() {
		if (manager == null) {
			manager = new DbManager();
		}
		return manager;
	}

	/**
	 * Initialize the DbManager with the database informations
	 * 
	 * @param dbUrl
	 *            the database URL
	 * @param dbUser
	 *            the database user
	 * @param dbPassword
	 *            the database password
	 */
	public static void initalize(String dbUrl, String dbUser, String dbPassword) {
		url = dbUrl;
		user = dbUser;
		password = dbPassword;
	}
}
