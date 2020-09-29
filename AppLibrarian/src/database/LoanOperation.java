package database;
import java.sql.SQLException;
import java.util.GregorianCalendar;
import java.util.HashMap;

import dataModel.Command;
import dataModel.Booking;
import dataModel.Loan;
import dataModel.User;
import database.DbManager;
import server.EmailSender;
import server.LoanControllerManager;
import utils.Operation;

/**
 * This class provides methods to handle loan operations
 *
 * @author Marco Di Capua
 * @author Mattia Lo Schiavo
 * @author Filippo Pelosi
 * @author Riccardo Zorzi
 * @version 1.0
 */
@SuppressWarnings("unchecked")
public class LoanOperation {
	// camps
	private static final int MAX_NUM_BOOKINGS = 10;
	private static final int MAX_NUM_LOANS = 5;

	/**
	 * Execute the add loan request
	 * 
	 * @param command
	 *            a Command with the loan informations
	 * @param userReader
	 *            the user reader that loan the book
	 * @return a Command with the outcome of the operation
	 * @throws ClassNotFoundException
	 *             if the Loan or the Driver classes could not be found
	 * @throws SQLException
	 *             if an SQL occurs while executing the request
	 */
	public static Command add(Command command, User userReader) throws ClassNotFoundException, SQLException {
		Loan loan = (Loan) command.getData();
		Booking booking = new Booking(loan.getBook(), loan.getUser(), loan.getLoanDate());
		boolean alreadyBooked = false;
		for (Booking bookingUser : userReader.getBookingBooks()) {
			if (bookingUser.equals(booking)) {
				alreadyBooked = true;
				break;
			}
		}
		if (!alreadyBooked) {
			if (userReader.getBookingBooks().size() < MAX_NUM_BOOKINGS) {
				DbManager.instance().insertData(Operation.BOOKING_ADD,
						new Booking(loan.getBook(), loan.getUser(), loan.getLoanDate()));
			} else {
				return new Command("Max number bookings", null);
			}
		}
		boolean alreadyLoan = (boolean) DbManager.instance().selectData("Control already loan", loan);
		if (!alreadyLoan) {
			String[] firstUserQueue = (String[]) DbManager.instance().selectData("Select user booking book",
					loan.getBook());
			if (firstUserQueue != null) {
				if (firstUserQueue[4].equals(userReader.getFiscalCode())) {
					boolean numLoansOk = userReader.getLoanBooks().size() < MAX_NUM_LOANS;
					boolean hasOverwhelmingLoans = (boolean) DbManager.instance()
							.selectData("Select user overwhelming loans", userReader);
					if (numLoansOk && !hasOverwhelmingLoans) {
						DbManager.instance().insertData(Operation.LOAN_ADD, loan);
						userReader.getLoanBooks().add(loan);
						LoanControllerManager.instance().removeController(loan);
						return new Command("Operation completed", null);
					} else {
						return new Command("Violated constraints", null);
					}
				} else {
					return new Command("Not first user", null);
				}
			} else {
				return new Command("No user booking book", null);
			}
		} else {
			return new Command("Book already loan", null);
		}
	}

	/**
	 * Execute the back loan request
	 * 
	 * @param command
	 *            a Command with the loan informations
	 * @param userReaderLogged
	 *            the user reader that back the loan
	 * @return a Command with the outcome of the operation
	 * @throws ClassNotFoundException
	 *             if the Loan or the Driver classes could not be found
	 * @throws SQLException
	 *             if an SQL occurs while executing the request
	 */
	public static Command back(Command command, User userReaderLogged) throws ClassNotFoundException, SQLException {
		Loan loanToReturn = (Loan) command.getData();
		loanToReturn.setReturnDate(new GregorianCalendar());
		Booking bookingToDelete = new Booking(loanToReturn.getBook(), loanToReturn.getUser(),
				loanToReturn.getLoanDate());
		if (DbManager.instance().deleteData(Operation.BOOKING_DELETE, bookingToDelete)) {
			for (Booking bookingUser : userReaderLogged.getBookingBooks()) {
				if (bookingUser.equals(bookingToDelete)) {
					userReaderLogged.getBookingBooks().remove(bookingUser);
					break;
				}
			}
			if ((loanToReturn.getBook().getBookings() - 1) != 0) {
				String[] userData = (String[]) DbManager.instance().selectData("Select user booking book",
						loanToReturn.getBook());
				if (userData != null) {
					new EmailSender("Book returned", userData[0], userData[1], userData[2], userData[3]);
					LoanControllerManager.instance()
							.addController(new Loan(loanToReturn.getBook(),
									new User("", "", userData[4], "", null, "", "", "", false, "", 0),
									loanToReturn.getReturnDate(), null, false));
				}
			}
			DbManager.instance().updateData(Operation.LOAN_BACK, loanToReturn);
			for (Loan loanUser : userReaderLogged.getLoanBooks()) {
				if (loanUser.equals(loanToReturn)) {
					userReaderLogged.getLoanBooks().remove(loanUser);
					break;
				}
			}
			return new Command("Operation completed", null);
		} else {
			return new Command("Operation failed", null);
		}
	}

	/**
	 * Execute the delete loan request
	 * 
	 * @param command
	 *            a Command with the loan informations
	 * @param userReaderLogged
	 *            the user reader that loan the book
	 * @return a Command with the outcome of the operation
	 * @throws ClassNotFoundException
	 *             if the Loan or the Driver classes could not be found
	 * @throws SQLException
	 *             if an SQL occurs while executing the request
	 */
	public static Command delete(Command command, User userReaderLogged) throws ClassNotFoundException, SQLException {
		Loan loanToCancel = (Loan) command.getData();
		Booking bookingToDelete = new Booking(loanToCancel.getBook(), loanToCancel.getUser(),
				loanToCancel.getLoanDate());
		if (DbManager.instance().deleteData(Operation.BOOKING_DELETE, bookingToDelete)) {
			for (Booking bookingUser : userReaderLogged.getBookingBooks()) {
				if (bookingUser.equals(bookingToDelete)) {
					userReaderLogged.getBookingBooks().remove(bookingUser);
					break;
				}
			}
			if ((loanToCancel.getBook().getBookings() - 1) != 0) {
				String[] userData = (String[]) DbManager.instance().selectData("Select user booking book",
						loanToCancel.getBook());
				if (userData != null) {
					new EmailSender("Book returned", userData[0], userData[1], userData[2], userData[3]);
					LoanControllerManager.instance()
							.addController(new Loan(loanToCancel.getBook(),
									new User("", "", userData[4], "", null, "", "", "", false, "", 0),
									loanToCancel.getReturnDate(), null, false));
				}
			}
			if (DbManager.instance().deleteData(Operation.LOAN_DELETE, loanToCancel)) {
				for (Loan loanUser : userReaderLogged.getLoanBooks()) {
					if (loanUser.equals(loanToCancel)) {
						userReaderLogged.getLoanBooks().remove(loanUser);
						break;
					}
				}
				return new Command("Operation completed", null);
			} else {
				return new Command("Operation failed", null);
			}
		} else {
			return new Command("Operation failed", null);
		}
	}

	/**
	 * Execute the history loans user request
	 * 
	 * @param command
	 *            a command with the page number requested
	 * @param userLogged
	 *            the user reader logged
	 * @return a Command with the outcome of the operation, a list of the history
	 *         loans user and the total pages
	 * @throws ClassNotFoundException
	 *             if the Loan or the Driver classes could not be found
	 * @throws SQLException
	 *             if an SQL occurs while executing the request
	 */
	public static Command historyLoansUser(Command command, User userLogged)
			throws ClassNotFoundException, SQLException {
		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("Page", (int) command.getData());
		data.put("UserLogged", userLogged);
		HashMap<String, Object> result = (HashMap<String, Object>) DbManager.instance()
				.selectData(Operation.LOAN_HISTORY_LOANS_USER, data);
		return new Command("Operation completed", result);
	}
}
