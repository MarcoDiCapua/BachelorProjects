package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import dataModel.Book;
import dataModel.Booking;
import dataModel.Classification;
import dataModel.User;
import utils.Operation;

/**
 * This class provides methods to handle queries about bookings table and return
 * the selected informations
 *
 * @author Marco Di Capua
 * @author Mattia Lo Schiavo
 * @author Filippo Pelosi
 * @author Riccardo Zorzi
 * @version 1.0
 */
public class DatabaseBooking {
	/**
	 * Executes the registration booking query
	 * 
	 * @param executor
	 *            the executor that execute the query
	 * @param booking
	 *            the booking informations
	 * @throws SQLException
	 *             if an SQL error occurs while executing the query
	 */
	public static void insertBooking(QueryExecutor executor, Booking booking) throws SQLException {
		BookingQuery insertBooking = new BookingQuery(PredefinedSQLCode.addBooking, Operation.BOOKING_ADD, booking);
		executor.executeUpdate(insertBooking);
		close(insertBooking);
	}

	/**
	 * Executes the delete booking query
	 * 
	 * @param executor
	 *            the executor that execute the query
	 * @param bookingToCancel
	 *            the booking informations
	 * @return true if the delete operation completed successfully, false otherwise
	 * @throws SQLException
	 *             if an SQL error occurs while executing the query
	 */
	public static boolean deleteBooking(QueryExecutor executor, Booking bookingToCancel) throws SQLException {
		BookingQuery deleteBooking = new BookingQuery(PredefinedSQLCode.deleteBooking, Operation.BOOKING_DELETE,
				bookingToCancel);
		boolean bln = executor.executeDelete(deleteBooking);
		close(deleteBooking);
		return bln;
	}

	/**
	 * Convert the ResultSet with the bookings informations in an ArrayList of
	 * Booking objects
	 * 
	 * @param queryExecutor
	 *            the executor that execute the query to select bookings
	 *            informations
	 * @param rsBookings
	 *            the ResultSet with the bookings informations
	 * @param user
	 *            the booking user
	 * @return an ArrayList with all the bookings informations
	 * @throws SQLException
	 *             if an SQL error occurs while executing the query
	 */
	public static ArrayList<Booking> resultSetToBookingsArray(QueryExecutor queryExecutor, ResultSet rsBookings,
			User user) throws SQLException {
		ArrayList<Booking> bookings = new ArrayList<Booking>();
		User userBooking = user;
		while (rsBookings.next()) {
			LibraryQuery selectBook = new LibraryQuery(PredefinedSQLCode.selectBookFromISBN, "Select book from isbn",
					0, rsBookings.getString("id_book"));
			ResultSet rsBook = queryExecutor.executeQuery(selectBook);
			ArrayList<Book> bookArray = DatabaseBook.resultSetToBooksArray(queryExecutor, rsBook);
			Book book = null;
			if (bookArray.size() > 0) {
				book = bookArray.get(0);
			}
			GregorianCalendar calendar = new GregorianCalendar();
			calendar.setTime(rsBookings.getDate("booking_date"));
			if (userBooking == null) {
				LibraryQuery selectUser = new LibraryQuery(PredefinedSQLCode.selectUserFromFiscalCode,
						"Select user from fiscal code", 0, rsBookings.getString("id_user"));
				ResultSet rsUsers = queryExecutor.executeQuery(selectUser);
				userBooking = null;
				if (rsUsers.next()) {
					userBooking = new User(rsUsers.getString("name"), rsUsers.getString("surname"),
							rsUsers.getString("fiscal_code"), rsUsers.getString("email"),
							Classification.valueOf(rsUsers.getString("classification")),
							rsUsers.getString("year_class"), rsUsers.getString("telephone_number"),
							rsUsers.getString("password"), rsUsers.getBoolean("temporaney_pwd"),
							rsUsers.getString("activation_code"), rsUsers.getInt("counter"));
				}
				close(selectUser);
			}
			if (book != null && userBooking != null) {
				bookings.add(new Booking(book, userBooking, calendar));
			}
			close(selectBook);
			userBooking = user;
		}
		return bookings;
	}

	private static void close(PreparableQuery query) throws SQLException {
		if (query != null) {
			query.close();
		}
	}

}
