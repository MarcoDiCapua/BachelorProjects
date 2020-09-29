package appReader;

import java.util.ArrayList;
import java.util.HashMap;

import dataModel.Category;
import appReader.Proxy;
import dataModel.Book;
import dataModel.Booking;
import dataModel.Classification;
import dataModel.Loan;

/**
 * An object of the <code>Library</code> class contains information about
 * library books, booking and loans
 *
 * @author Marco Di Capua
 * @author Mattia Lo Schiavo
 * @author Filippo Pelosi
 * @author Riccardo Zorzi
 * @version 1.0
 */
public class Library {

	private ArrayList<Book> books;
	private ArrayList<Loan> loanBooks;
	private ArrayList<Booking> bookingBooks;
	private static Library library = null;

	private Library() {
		super();
		this.books = new ArrayList<Book>();
		this.loanBooks = new ArrayList<Loan>();
		this.bookingBooks = new ArrayList<Booking>();
	}

	/**
	 * Add the book to the library
	 * 
	 * @param book
	 *            the book to add
	 * @return a string contains the outcome operation
	 */
	public String addBook(Book book) {
		return Proxy.instance().addBook(book);
	}

	/**
	 * Remove the book from the library
	 * 
	 * @param book
	 *            the book to remove
	 * @return a string contains the outcome operation
	 */
	public String removeBook(Book book) {
		return Proxy.instance().removeBook(book);
	}

	/**
	 * Update the library books with the 25 books at the given page number
	 * 
	 * @param page
	 *            the page number requested
	 * @return the total pages number of the library books
	 */
	public int updateBooks(int page) {
		return Proxy.instance().updateBooks(library, page);
	}

	/**
	 * Update the library bookings with the 25 books at the given page number
	 * 
	 * @param page
	 *            the page number requested
	 * @return the total pages number of the library bookings
	 */
	public int updateBookings(int page) {
		return Proxy.instance().updateBookings(library, page);
	}

	/**
	 * Update the library loans with the 25 books at the given page number
	 * 
	 * @param page
	 *            the page number requested
	 * @return the total pages number of the library loans
	 */
	public int updateLoans(int page) {
		return Proxy.instance().updateLoans(library, page);
	}

	/**
	 * Search the books corresponding to the inserted data in the library
	 * 
	 * @param title
	 *            the title to search
	 * @param page
	 *            the page number requested
	 * @param author
	 *            the author to search
	 * @param category
	 *            the category to search
	 * @return an HashMap with the books corresponding to the inserted data and the
	 *         total pages number
	 */
	public HashMap<String, Object> selectBooksFrom(int page, String title, String author, Category category) {
		return Proxy.instance().selectBooksFrom(page, title, author, category);
	}

	/**
	 * Search the bookings corresponding to the inserted data in the library
	 * 
	 * @param page
	 *            the page number requested
	 * @param title
	 *            the title to search
	 * @param author
	 *            the author to search
	 * @param category
	 *            the category to search
	 * @return an HashMap with the bookings corresponding to the inserted data and
	 *         the total pages number
	 */
	public HashMap<String, Object> selectBookingsFrom(int page, String title, String author, Category category) {
		return Proxy.instance().selectBookingsFrom(page, title, author, category);
	}

	/**
	 * Search the loans corresponding to the inserted data in the library
	 * 
	 * @param page
	 *            the page number requested
	 * @param title
	 *            the title to search
	 * @param author
	 *            the author to search
	 * @param category
	 *            the category to search
	 * @return an HashMap with the loans corresponding to the inserted data and the
	 *         total pages number
	 */
	public HashMap<String, Object> selectLoansFrom(int page, String title, String author, Category category) {
		return Proxy.instance().selectLoansFrom(page, title, author, category);
	}

	/**
	 * Search the overwhelming loans corresponding to the inserted data in the
	 * library
	 * 
	 * @param page
	 *            the page number requested
	 * @param title
	 *            the title to search
	 * @param author
	 *            the author to search
	 * @param category
	 *            the category to search
	 * @return an HashMap with the overwhelming loans corresponding to the inserted
	 *         data and the total pages number
	 */
	public HashMap<String, Object> selectOverwhelmingLoansFrom(int page, String title, String author,
			Category category) {
		return Proxy.instance().selectOverwhelmingLoansFrom(page, title, author, category);
	}

	/**
	 * Select the absolute rank of the most read books
	 * 
	 * @param page
	 *            the page number requested
	 * @return an HashMap with the absolute rank and the total pages number
	 */
	public HashMap<String, Object> absoluteRank(int page) {
		return Proxy.instance().absoluteRank(page);
	}

	/**
	 * Select the rank of the most read books with the category inserted
	 * 
	 * @param page
	 *            the page number requested
	 * @param category
	 *            the category to select
	 * @return an HashMap with the category rank and the total pages number
	 */
	public HashMap<String, Object> categoryRank(int page, Category category) {
		return Proxy.instance().categoryRank(page, category);
	}

	/**
	 * Select the rank of the most read books by users with the classification
	 * inserted
	 * 
	 * @param page
	 *            the page number requested
	 * @param classification
	 *            the classification to select
	 * @return an HashMap with the absolute classification and the total pages
	 *         number
	 */
	public HashMap<String, Object> classificationRank(int page, Classification classification) {
		return Proxy.instance().classificationRank(page, classification);
	}

	/**
	 * @return the books
	 */
	public ArrayList<Book> getBooks() {
		return books;
	}

	/**
	 * @param books
	 *            the books to set
	 */
	public void setBooks(ArrayList<Book> books) {
		this.books = books;
	}

	/**
	 * @return the loanBooks
	 */
	public ArrayList<Loan> getLoanBooks() {
		return loanBooks;
	}

	/**
	 * @param loanBooks
	 *            the loan books to set
	 */
	public void setLoanBooks(ArrayList<Loan> loanBooks) {
		this.loanBooks = loanBooks;
	}

	/**
	 * @return the booking books
	 */
	public ArrayList<Booking> getBookingBooks() {
		return bookingBooks;
	}

	/**
	 * @param bookingBooks
	 *            the booking books to set
	 */
	public void setBookingBooks(ArrayList<Booking> bookingBooks) {
		this.bookingBooks = bookingBooks;
	}

	/**
	 * Return the 25 overwhelming loans at the given page number
	 * 
	 * @param page
	 *            the page number requested
	 * @return the 25 overwhelming loan books and the total pages number
	 */
	public HashMap<String, Object> getOverwhelmingLoanBooks(int page) {
		return Proxy.instance().selectOverwhelmingLoan(page);
	}

	/**
	 * Instance a <code>Library</code> object
	 * 
	 * @return the <code>Library</code> object
	 */
	public static Library instance() {
		if (library == null) {
			library = new Library();
		}
		return library;
	}
}
