package database;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import dataModel.Command;
import dataModel.Booking;
import dataModel.Loan;
import dataModel.User;
import database.DbManager;
import server.EmailSender;
import server.LoanControllerManager;
import utils.Operation;

/**
 * This class provides methods to handle booking operations
 *
 * @author Marco Di Capua
 * @author Mattia Lo Schiavo
 * @author Filippo Pelosi
 * @author Riccardo Zorzi
 * @version 1.0
 */
public class BookingOperation {
	private static final int MAX_NUM_BOOKINGS = 10;

	/**
	 * Execute the add booking request
	 * 
	 * @param command
	 *            a Command with the booking informations
	 * @param user
	 *            the user reader that book the book
	 * @return a Command with the outcome of the operation
	 * @throws ClassNotFoundException
	 *             if the Booking or the Driver classes could not be found
	 * @throws SQLException
	 *             if an SQL occurs while executing the request
	 */
	public static Command add(Command command, User user) throws ClassNotFoundException, SQLException {
		Booking booking = (Booking) command.getData();
		if (user.getBookingBooks().size() < MAX_NUM_BOOKINGS) {
			boolean found = false;
			for (Booking bookingUser : user.getBookingBooks()) {
				if (bookingUser.equals(booking)) {
					found = true;
					break;
				}
			}
			if (found) {
				return new Command("Book already booked!", null);
			} else {
				DbManager.instance().insertData(Operation.BOOKING_ADD, booking);
				user.getBookingBooks().add(booking);
				String[] firstUserBookingQueue = (String[]) DbManager.instance().selectData("Select user booking book",
						booking.getBook());
				if (firstUserBookingQueue[4].equals(user.getFiscalCode()) && booking.getBook().getBookings() == 0) {
					String date = new SimpleDateFormat("dd/MM/yyyy").format(booking.getBookingDate().getTime());
					new EmailSender("Book returned", user.getEmail(), date, booking.getBook().getTitle(),
							booking.getBook().getIsbn());
					LoanControllerManager.instance()
							.addController(new Loan(booking.getBook(), user, booking.getBookingDate(), null, false));
				}
				return new Command("Operation completed", null);
			}
		} else {
			return new Command("Max number bookings!", null);
		}
	}

	/**
	 * Execute the delete booking request
	 * 
	 * @param command
	 *            a Command with the booking informations
	 * @param user
	 *            the user reader that book the book
	 * @return a Command with the outcome of the operation
	 * @throws ClassNotFoundException
	 *             if the Booking or the Driver classes could not be found
	 * @throws SQLException
	 *             if an SQL occurs while executing the request
	 */
	public static Command delete(Command command, User user) throws ClassNotFoundException, SQLException {
		Booking bookingToDelete = (Booking) command.getData();
		String[] firstUserQueue = (String[]) DbManager.instance().selectData("Select user booking book",
				bookingToDelete.getBook());
		if (DbManager.instance().deleteData(Operation.BOOKING_DELETE, bookingToDelete)) {
			for (Booking bookingUser : user.getBookingBooks()) {
				if (bookingUser.equals(bookingToDelete)) {
					user.getBookingBooks().remove(bookingUser);
					break;
				}
			}
			if (firstUserQueue[4].equals(user.getFiscalCode()) && (bookingToDelete.getBook().getBookings() - 1) != 0) {
				String[] bookingQueue = (String[]) DbManager.instance().selectData("Select user booking book",
						bookingToDelete.getBook());
				new EmailSender("Book returned", bookingQueue[0], bookingQueue[1], bookingQueue[2], bookingQueue[3]);
				LoanControllerManager.instance().removeController(
						new Loan(bookingToDelete.getBook(), user, bookingToDelete.getBookingDate(), null, false));
			}
			return new Command("Operation completed", null);
		} else {
			return new Command("Operation falied", null);
		}
	}
}
