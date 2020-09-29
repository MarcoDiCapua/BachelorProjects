package dataModel;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * An object of the <code>User</code> class contains user information
 *
 * @author Marco Di Capua
 * @author Mattia Lo Schiavo
 * @author Filippo Pelosi
 * @author Riccardo Zorzi
 * @version 1.0
 */
public class User implements Serializable {
	// camps
	private static final long serialVersionUID = 1L;
	private String name;
	private String surname;
	private String fiscalCode;
	private String email;
	private Classification classification;
	private String year_class;
	private String telephoneNumber;
	private String password;
	private boolean isTemporaneyPassword;
	private String activationCode;
	private int counter;
	private ArrayList<Loan> loanBooks;
	private ArrayList<Booking> bookingBooks;

	/**
	 * Create a new object <code>User</code> with the information in input
	 * 
	 * @param name
	 *            is the user name
	 * @param surname
	 *            is the user surname
	 * @param fiscalCode
	 *            is the user fiscal code
	 * @param email
	 *            is the user email
	 * @param classification
	 *            is the user classification
	 * @param year_class
	 *            is the user year and class
	 * @param telephoneNumber
	 *            is the user telephone number
	 * @param password
	 *            is the user password
	 * @param isTemporaneyPassword
	 *            true if the user password is temporary, false otherwise
	 * @param activationCode
	 *            is the activation code
	 * @param counter
	 *            is the activation counter
	 * @param loanBooks
	 *            is the list with the user loans
	 * @param bookingBooks
	 *            is the list with the user bookings
	 */
	public User(String name, String surname, String fiscalCode, String email, Classification classification,
			String year_class, String telephoneNumber, String password, boolean isTemporaneyPassword,
			String activationCode, int counter, ArrayList<Loan> loanBooks, ArrayList<Booking> bookingBooks) {
		super();
		this.name = name;
		this.surname = surname;
		this.fiscalCode = fiscalCode;
		this.email = email;
		this.classification = classification;
		this.year_class = year_class;
		this.telephoneNumber = telephoneNumber;
		this.password = password;
		this.isTemporaneyPassword = isTemporaneyPassword;
		this.activationCode = activationCode;
		this.counter = counter;
		this.loanBooks = loanBooks;
		this.bookingBooks = bookingBooks;
	}

	/**
	 * Create a new object <code>User</code> with the information in input
	 * 
	 * @param name
	 *            is the user name
	 * @param surname
	 *            is the user surname
	 * @param fiscalCode
	 *            is the user fiscal code
	 * @param email
	 *            is the user email
	 * @param classification
	 *            is the user classification
	 * @param year_class
	 *            is the user year and class
	 * @param telephoneNumber
	 *            is the user telephone number
	 * @param password
	 *            is the user password
	 * @param isTemporaneyPassword
	 *            true if the user password is temporary, false otherwise
	 * @param activationCode
	 *            is the activation code
	 * @param counter
	 *            is the activation counter
	 */
	public User(String name, String surname, String fiscalCode, String email, Classification classification,
			String year_class, String telephoneNumber, String password, boolean isTemporaneyPassword,
			String activationCode, int counter) {
		super();
		this.name = name;
		this.surname = surname;
		this.fiscalCode = fiscalCode;
		this.email = email;
		this.classification = classification;
		this.year_class = year_class;
		this.telephoneNumber = telephoneNumber;
		this.password = password;
		this.isTemporaneyPassword = isTemporaneyPassword;
		this.activationCode = activationCode;
		this.counter = counter;
		this.loanBooks = new ArrayList<Loan>();
		this.bookingBooks = new ArrayList<Booking>();
	}

	/**
	 * Encrypt user password
	 */
	public void encryptPassword() {
		if (this.getPassword().length() == 32) {
			return;
		}
		try {
			MessageDigest m = MessageDigest.getInstance("MD5");
			m.update(this.getPassword().getBytes());
			this.setPassword(String.format("%032x", new BigInteger(1, m.digest())));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof User) {
			User user = (User) obj;
			return this.getFiscalCode().equals(user.getFiscalCode());
		}
		return false;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the surname
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * @param surname
	 *            the surname to set
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}

	/**
	 * @return the fiscalCode
	 */
	public String getFiscalCode() {
		return fiscalCode;
	}

	/**
	 * @param fiscalCode
	 *            the fiscalCode to set
	 */
	public void setFiscalCode(String fiscalCode) {
		this.fiscalCode = fiscalCode;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the classification
	 */
	public Classification getClassification() {
		return classification;
	}

	/**
	 * @param classification
	 *            the classification to set
	 */
	public void setClassification(Classification classification) {
		this.classification = classification;
	}

	/**
	 * @return the year_class
	 */
	public String getYear_class() {
		return year_class;
	}

	/**
	 * @param year_class
	 *            the year_class to set
	 */
	public void setYear_class(String year_class) {
		this.year_class = year_class;
	}

	/**
	 * @return the telephoneNumber
	 */
	public String getTelephoneNumber() {
		return telephoneNumber;
	}

	/**
	 * @param telephoneNumber
	 *            the telephoneNumber to set
	 */
	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the isTemporaneyPassword
	 */
	public boolean isTemporaneyPassword() {
		return isTemporaneyPassword;
	}

	/**
	 * @param isTemporaneyPassword
	 *            the isTemporaneyPassword to set
	 */
	public void setTemporaneyPassword(boolean isTemporaneyPassword) {
		this.isTemporaneyPassword = isTemporaneyPassword;
	}

	/**
	 * @return the activationCode
	 */
	public String getActivationCode() {
		return activationCode;
	}

	/**
	 * @param activationCode
	 *            the activationCode to set
	 */
	public void setActivationCode(String activationCode) {
		this.activationCode = activationCode;
	}

	/**
	 * @return the counter
	 */
	public int getCounter() {
		return counter;
	}

	/**
	 * @param counter
	 *            the counter to set
	 */
	public void setCounter(int counter) {
		this.counter = counter;
	}

	/**
	 * @return the loanBooks
	 */
	public ArrayList<Loan> getLoanBooks() {
		return loanBooks;
	}

	/**
	 * @param loanBooks
	 *            the loanBooks to set
	 */
	public void setLoanBooks(ArrayList<Loan> loanBooks) {
		this.loanBooks = loanBooks;
	}

	/**
	 * @return the bookingBooks
	 */
	public ArrayList<Booking> getBookingBooks() {
		return bookingBooks;
	}

	/**
	 * @param bookingBooks
	 *            the bookingBooks to set
	 */
	public void setBookingBooks(ArrayList<Booking> bookingBooks) {
		this.bookingBooks = bookingBooks;
	}
}
