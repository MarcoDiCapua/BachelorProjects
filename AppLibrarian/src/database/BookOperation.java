package database;
import java.sql.SQLException;
import java.util.ArrayList;

import dataModel.Command;
import dataModel.Book;
import database.DbManager;
import server.EmailSender;
import utils.Operation;

/**
 * This class provides methods to handle book operations
 *
 * @author Marco Di Capua
 * @author Mattia Lo Schiavo
 * @author Filippo Pelosi
 * @author Riccardo Zorzi
 * @version 1.0
 */
@SuppressWarnings("unchecked")
public class BookOperation {
	/**
	 * Execute the add book request
	 * 
	 * @param command
	 *            a Command with the book informations
	 * @return a Command with the outcome of the operation
	 * @throws ClassNotFoundException
	 *             if the Book or the Driver classes could not be found
	 * @throws SQLException
	 *             if an SQL occurs while executing the request
	 */
	public static Command add(Command command) throws ClassNotFoundException, SQLException {
		Book book = (Book) command.getData();
		DbManager.instance().insertData(Operation.BOOK_ADD, book);
		ArrayList<String> allAuthors = (ArrayList<String>) DbManager.instance().selectData("Select all authors", null);
		String[] authors = book.getAuthors().split(", ");
		for (int i = 0; i < authors.length; i++) {
			book.setAuthors(authors[i]);
			boolean found = false;
			for (String author : allAuthors) {
				if (authors[i].equals(author)) {
					found = true;
					break;
				}
			}
			if (!found) {
				DbManager.instance().insertData("Insert author", book);
			}
			DbManager.instance().insertData("Insert write", book);
		}
		return new Command("Operation completed", null);
	}

	/**
	 * Execute the remove book request
	 * 
	 * @param command
	 *            a Command with the book informations
	 * @return a Command with the outcome of the operation
	 * @throws ClassNotFoundException
	 *             if the Book or the Driver classes could not be found
	 * @throws SQLException
	 *             if an SQL occurs while executing the request
	 */
	public static Command remove(Command command) throws ClassNotFoundException, SQLException {
		Book book = (Book) command.getData();
		if ((book.getBookings() - 1) != 0) {
			ArrayList<String[]> bookingQueue = (ArrayList<String[]>) DbManager.instance()
					.selectData("Select users booking book", book);
			for (int i = 0; i < bookingQueue.size(); i++) {
				String[] ingormationUserBooking = bookingQueue.get(i);
				new EmailSender("Book returned", ingormationUserBooking[0], ingormationUserBooking[1],
						ingormationUserBooking[2], ingormationUserBooking[3]);
			}
		}
		if (DbManager.instance().deleteData(Operation.BOOK_REMOVE, book)) {
			String[] bookAuthors = book.getAuthors().split(", ");
			for (int i = 0; i < bookAuthors.length; i++) {
				book.setAuthors(bookAuthors[i]);
				DbManager.instance().deleteData("Delete author", book);
			}
			return new Command("Operation completed", null);
		} else {
			return new Command("Operation failed", null);
		}
	}
}
