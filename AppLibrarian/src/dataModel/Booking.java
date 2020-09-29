package dataModel;

import java.io.Serializable;
import java.util.GregorianCalendar;

/**
 * An object of the <code>Booking</code> class contains booking information
 *
 * @author Marco Di Capua
 * @author Mattia Lo Schiavo
 * @author Filippo Pelosi
 * @author Riccardo Zorzi
 * @version 1.0
 */
public class Booking implements Serializable {
	// camps
	private static final long serialVersionUID = 1L;
	private Book book;
	private User user;
	private GregorianCalendar bookingDate;

	/**
	 * Create a new object <code>Booking</code> with the information in input
	 * 
	 * @param book
	 *            is the booking book
	 * @param user
	 *            is the booking user
	 * @param bookingDate
	 *            is the booking date
	 */
	public Booking(Book book, User user, GregorianCalendar bookingDate) {
		super();
		this.book = book;
		this.user = user;
		this.bookingDate = bookingDate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj != null & obj instanceof Booking) {
			Booking booking = (Booking) obj;
			return this.getBook().equals(booking.getBook()) && this.getUser().equals(booking.getUser());
		}
		return false;
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

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the bookingDate
	 */
	public GregorianCalendar getBookingDate() {
		return bookingDate;
	}

	/**
	 * @param bookingDate
	 *            the bookingDate to set
	 */
	public void setBookingDate(GregorianCalendar bookingDate) {
		this.bookingDate = bookingDate;
	}

}
