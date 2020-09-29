package dataModel;

import java.io.Serializable;
import java.util.GregorianCalendar;

/**
 * An object of the <code>Loan</code> class contains loan information
 *
 * @author Marco Di Capua
 * @author Mattia Lo Schiavo
 * @author Filippo Pelosi
 * @author Riccardo Zorzi
 * @version 1.0
 */
public class Loan implements Serializable {
	// camps
	private static final long serialVersionUID = 1L;
	private Book book;
	private User user;
	private GregorianCalendar loanDate;
	private GregorianCalendar returnDate;
	private boolean returned;

	/**
	 * Create a new object <code>Loan</code> with the information in input
	 * 
	 * @param book
	 *            is the loan book
	 * @param user
	 *            is the loan user
	 * @param loanDate
	 *            is the loan date
	 * @param returnDate
	 *            is the return date
	 * @param returned
	 *            true if the book is returned, false otherwise
	 */
	public Loan(Book book, User user, GregorianCalendar loanDate, GregorianCalendar returnDate, boolean returned) {
		super();
		this.book = book;
		this.user = user;
		this.loanDate = loanDate;
		this.returnDate = returnDate;
		this.returned = returned;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof Loan) {
			Loan loan = (Loan) obj;
			return this.getBook().equals(loan.getBook()) && this.getUser().equals(loan.getUser())
					&& this.getLoanDate().equals(loan.getLoanDate())
					&& this.getReturnDate().equals(loan.getReturnDate());
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
	 * @return the loanDate
	 */
	public GregorianCalendar getLoanDate() {
		return loanDate;
	}

	/**
	 * @param loanDate
	 *            the loanDate to set
	 */
	public void setLoanDate(GregorianCalendar loanDate) {
		this.loanDate = loanDate;
	}

	/**
	 * @return the returnDate
	 */
	public GregorianCalendar getReturnDate() {
		return returnDate;
	}

	/**
	 * @param returnDate
	 *            the returnDate to set
	 */
	public void setReturnDate(GregorianCalendar returnDate) {
		this.returnDate = returnDate;
	}

	/**
	 * @return the returned
	 */
	public boolean isReturned() {
		return returned;
	}

	/**
	 * @param returned
	 *            the returned to set
	 */
	public void setReturned(boolean returned) {
		this.returned = returned;
	}

}
