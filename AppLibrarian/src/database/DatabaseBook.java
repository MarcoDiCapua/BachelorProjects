package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import dataModel.Category;
import dataModel.Book;
import dataModel.Language;
import utils.Operation;

/**
 * This class provides methods to handle queries about books table and return
 * the selected informations
 *
 * @author Marco Di Capua
 * @author Mattia Lo Schiavo
 * @author Filippo Pelosi
 * @author Riccardo Zorzi
 * @version 1.0
 */
public class DatabaseBook {
	/**
	 * Executes the insert book query
	 * 
	 * @param executor
	 *            the executor that execute the query
	 * @param book
	 *            the book informations
	 * @throws SQLException
	 *             if an SQL error occurs while executing the query
	 */
	public static void insertBook(QueryExecutor executor, Book book) throws SQLException {
		BookQuery insertBook = new BookQuery(PredefinedSQLCode.addBook, Operation.BOOK_ADD, book);
		executor.executeUpdate(insertBook);
		close(insertBook);
	}

	/**
	 * Executes the insert author query
	 * 
	 * @param executor
	 *            the executor that execute the query
	 * @param book
	 *            the book informations with the author to insert
	 * @throws SQLException
	 *             if an SQL error occurs while executing the query
	 */
	public static void insertAuthor(QueryExecutor executor, Book book) throws SQLException {
		BookQuery insertAuthor = new BookQuery(PredefinedSQLCode.addAuthor, "Insert author", book);
		executor.executeUpdate(insertAuthor);
		close(insertAuthor);
	}

	/**
	 * Executes the insert write query
	 * 
	 * @param executor
	 *            the executor that execute the query
	 * @param book
	 *            the book informations
	 * @throws SQLException
	 *             if an SQL error occurs while executing the query
	 */
	public static void insertWrite(QueryExecutor executor, Book book) throws SQLException {
		BookQuery insertWrite = new BookQuery(PredefinedSQLCode.addWrite, "Insert write", book);
		executor.executeUpdate(insertWrite);
		close(insertWrite);
	}

	/**
	 * Executes the delete book query
	 * 
	 * @param executor
	 *            the executor that execute the query
	 * @param book
	 *            the book informations
	 * @return true if the delete operation completed successfully, false otherwise
	 * @throws SQLException
	 *             if an SQL error occurs while executing the query
	 */
	public static boolean deleteBook(QueryExecutor executor, Book book) throws SQLException {
		BookQuery deleteBook = new BookQuery(PredefinedSQLCode.removeBook, Operation.BOOK_REMOVE, book);
		boolean bln = executor.executeDelete(deleteBook);
		close(deleteBook);
		return bln;
	}

	/**
	 * Executes the delete author query
	 * 
	 * @param executor
	 *            the executor that execute the query
	 * @param book
	 *            the book with the author to delete
	 * @return true if the delete operation completed successfully, false otherwise
	 */
	public static boolean deleteAuthor(QueryExecutor executor, Book book) {
		BookQuery deleteAuthor = new BookQuery(PredefinedSQLCode.deleteAuthor, "Delete author", book);
		return executor.executeDelete(deleteAuthor);
	}

	/**
	 * Returns an array with the informations needed to send an email to the first
	 * user of the booking queue. The informations are: user email, booking date,
	 * book title, book ISBN, user fiscal code
	 * 
	 * @param executor
	 *            the executor that execute the query
	 * @param book
	 *            the book to select booking queue
	 * @return an array with the informations needed to send an email to the first
	 *         user of the booking queue. The informations are: user email, booking
	 *         date, book title, book ISBN, user fiscal code
	 * @throws SQLException
	 *             if an SQL error occurs while executing the query
	 */
	public static String[] userBookingBook(QueryExecutor executor, Book book) throws SQLException {
		BookQuery selectUserBookingBook = new BookQuery(PredefinedSQLCode.selectUserBookingBook,
				"Select user booking book", book);
		ResultSet resultSet = executor.executeQuery(selectUserBookingBook);
		String[] data = null;
		if (resultSet.next()) {
			data = new String[5];
			data[0] = resultSet.getString("email");
			data[1] = new SimpleDateFormat("dd/MM/yyyy").format(resultSet.getDate("booking_date"));
			data[2] = resultSet.getString("title");
			data[3] = resultSet.getString("isbn");
			data[4] = resultSet.getString("fiscal_code");
		}
		close(selectUserBookingBook);
		return data;
	}

	/**
	 * Returns an array with the informations needed to send an email to the first
	 * user of the booking queue. The informations are: user email, booking date,
	 * book title, book ISBN, user fiscal code
	 * 
	 * @param executor
	 *            the executor that execute the query
	 * @param book
	 *            the book to select booking queue
	 * @return an array with the informations needed to send an email to the first
	 *         user of the booking queue. The informations are: user email, booking
	 *         date, book title, book ISBN, user fiscal code
	 * @throws SQLException
	 *             if an SQL error occurs while executing the query
	 */
	public static ArrayList<String[]> usersBookingBook(QueryExecutor executor, Book book) throws SQLException {
		BookQuery selectUsersBookingBook = new BookQuery(PredefinedSQLCode.selectUsersBookingBook,
				"Select user booking book", book);
		ResultSet resultSet = executor.executeQuery(selectUsersBookingBook);
		ArrayList<String[]> list = new ArrayList<String[]>();
		while (resultSet.next()) {
			String[] array = new String[5];
			array[0] = resultSet.getString("email");
			array[1] = new SimpleDateFormat("dd/MM/yyyy").format(resultSet.getDate("booking_date"));
			array[2] = resultSet.getString("title");
			array[3] = resultSet.getString("isbn");
			array[4] = resultSet.getString("fiscal_code");
		}
		close(selectUsersBookingBook);
		return list;
	}

	/**
	 * Convert the ResultSet with the books informations in an ArrayList of Book
	 * objects
	 * 
	 * @param queryExecutor
	 *            the executor that execute the query to select books informations
	 * @param rs
	 *            the ResultSet with the books informations
	 * @return an ArrayList with all the books informations
	 * @throws SQLException
	 *             if an SQL error occurs while executing the query
	 */
	public static ArrayList<Book> resultSetToBooksArray(QueryExecutor queryExecutor, ResultSet rs) throws SQLException {
		ArrayList<Book> books = new ArrayList<Book>();
		while (rs.next()) {
			LibraryQuery allAuthorsQuery = new LibraryQuery(PredefinedSQLCode.selectBookAuthors,
					"Select authors for book", 0, rs.getString("isbn"));
			ResultSet rsAuthors = queryExecutor.executeQuery(allAuthorsQuery);
			String authors = "";
			boolean notLast = rsAuthors.next();
			while (notLast) {
				authors = authors + rsAuthors.getString("name") + " " + rsAuthors.getString("surname");
				notLast = rsAuthors.next();
				String separator = notLast ? ", " : "";
				authors = authors + separator;
			}
			Book book = new Book(rs.getString("isbn"), rs.getString("title"), authors, rs.getString("publishing_house"),
					rs.getInt("publication_year"), rs.getInt("reprint_year"),
					Category.valueOf(rs.getString("category")), Language.valueOf(rs.getString("language")),
					rs.getString("bookcase"), 0);
			BookQuery countBookings = new BookQuery(PredefinedSQLCode.countBookings, "Count bookings for book", book);
			ResultSet rsCountBookings = queryExecutor.executeQuery(countBookings);
			rsCountBookings.next();
			book.setBookings(rsCountBookings.getInt(1));
			books.add(book);
			close(allAuthorsQuery);
			close(countBookings);
		}
		return books;
	}

	private static void close(PreparableQuery query) throws SQLException {
		if (query != null) {
			query.close();
		}
	}

}
