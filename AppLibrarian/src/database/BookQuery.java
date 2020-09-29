package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import dataModel.Book;
import utils.Operation;

/**
 * This class provides methods to prepare queries about books table
 *
 * @author Marco Di Capua
 * @author Mattia Lo Schiavo
 * @author Filippo Pelosi
 * @author Riccardo Zorzi
 * @version 1.0
 */
public class BookQuery implements PreparableQuery {

	private String sqlCode;
	private String operation;
	private PreparedStatement statement;
	private Book book;

	/**
	 * Create a new BookQuery object with the informations given
	 * 
	 * @param sqlCode
	 *            the SQL code to execute
	 * @param operation
	 *            the book operation to execute
	 * @param book
	 *            the book that contains the informations necessary to prepare the
	 *            query
	 */
	public BookQuery(String sqlCode, String operation, Book book) {
		super();
		this.sqlCode = sqlCode;
		this.operation = operation;
		this.statement = null;
		this.book = book;
	}

	/**
	 * Prepare the BookQuery object with the informations in the book object
	 * according to the operation to execute
	 * 
	 * @param connection
	 *            the connection where the query will be execute
	 * @return a PreparedStatement ready to be execute
	 */
	@Override
	public PreparedStatement prepareStatement(Connection connection) throws SQLException {
		if (statement != null) {
			return statement;
		} else {
			this.statement = connection.prepareStatement(this.sqlCode);
			switch (this.getOperation()) {
			case Operation.BOOK_ADD:
				this.prepareAddBookStatement();
				break;
			case Operation.BOOK_REMOVE:
				this.prepareSetIsbnStatement();
				break;
			case "Insert author":
				this.prepareAuthorStatement();
				break;
			case "Insert write":
				this.prepareAddWriteStatement();
				break;
			case "Count bookings for book":
				this.prepareSetIsbnStatement();
				break;
			case "Select users booking book":
				this.prepareSetIsbnStatement();
				break;
			case "Select user booking book":
				this.prepareSelectUserBookingBookStatement();
				break;
			case "Delete bookings book":
				this.prepareSetIsbnStatement();
				break;
			case "Delete write":
				this.prepareSetIsbnStatement();
				break;
			case "Delete author":
				this.prepareAuthorStatement();
				break;
			}
		}
		return statement;
	}

	private void prepareSetIsbnStatement() throws SQLException {
		statement.setString(1, this.book.getIsbn());
	}

	private void prepareSelectUserBookingBookStatement() throws SQLException {
		statement.setString(1, this.book.getIsbn());
	}

	private void prepareAuthorStatement() throws SQLException {
		String author = this.book.getAuthors();
		int position = author.indexOf(" ");
		String name = author.substring(0, position);
		String surname = author.substring(position + 1);
		statement.setString(1, name);
		statement.setString(2, surname);
	}

	private void prepareAddWriteStatement() throws SQLException {
		prepareAuthorStatement();
		statement.setString(3, this.book.getIsbn());
	}

	private void prepareAddBookStatement() throws SQLException {
		statement.setString(1, this.book.getIsbn());
		statement.setString(2, this.book.getTitle());
		statement.setInt(3, this.book.getPublicationYear());
		statement.setInt(4, this.book.getReprintYear());
		statement.setString(5, this.book.getBookcase());
		statement.setString(6, this.book.getLanguage().name());
		statement.setString(7, this.book.getCategory().name());
		statement.setString(8, this.book.getPublishingHouse());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see PreparableQuery#close()
	 */
	@Override
	public void close() {
		if (this.statement != null) {
			try {
				this.statement.close();
			} catch (SQLException e) {
			}
		}
	}

	/**
	 * @return the sqlCode
	 */
	public String getSqlCode() {
		return sqlCode;
	}

	/**
	 * @param sqlCode
	 *            the sqlCode to set
	 */
	public void setSqlCode(String sqlCode) {
		this.sqlCode = sqlCode;
	}

	/**
	 * @return the operation
	 */
	public String getOperation() {
		return operation;
	}

	/**
	 * @param operation
	 *            the operation to set
	 */
	public void setOperation(String operation) {
		this.operation = operation;
	}

	/**
	 * @return the statement
	 */
	public PreparedStatement getStatement() {
		return statement;
	}

	/**
	 * @param statement
	 *            the statement to set
	 */
	public void setStatement(PreparedStatement statement) {
		this.statement = statement;
	}

	/**
	 * @return the book
	 */
	public Book getBook() {
		return book;
	}

	/**
	 * @param book
	 *            the book to set
	 */
	public void setBook(Book book) {
		this.book = book;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BookQuery [sqlCode=" + sqlCode + ", operation=" + operation + ", statement=" + statement + ", book="
				+ book + "]";
	}

}
