package utils;

/**
 * This class contains strings representing the operations accepted by the
 * server
 *
 * @author Marco Di Capua
 * @author Mattia Lo Schiavo
 * @author Filippo Pelosi
 * @author Riccardo Zorzi
 * @version 1.0
 */
public class Operation {
	/**
	 * Login operation
	 */
	public static final String LOGIN_USER = "Login-User";
	/**
	 * Login reader from librarian user operation
	 */
	public static final String LOGIN_FROM_LIBRARIAN = "Login-User from librarian";
	/**
	 * User registration operation
	 */
	public static final String USER_REGISTRATION = "User-Registration";
	/**
	 * User reader registration from AppLibrarian operation
	 */
	public static final String USER_REGISTRATION_FROM_LIBRARIAN = "User-Registration from librarian";
	/**
	 * Reset user password operation
	 */
	public static final String USER_RESET_PASSWORD = "User-Reset password";
	/**
	 * Modify user email operation
	 */
	public static final String USER_MODIFY_EMAIL = "User-Modify email";
	/**
	 * Confirm modify user email operation
	 */
	public static final String USER_CONFIRM_MODIFY_EMAIL = "User-Confirm modify email";
	/**
	 * Modify classification operation
	 */
	public static final String USER_MODIFY_CLASSIFICATION = "User-Modify classification";
	/**
	 * Modify telephone number operation
	 */
	public static final String USER_MODIFY_TELEPHONE = "User-Modify telephone number";
	/**
	 * Modify password operation
	 */
	public static final String USER_MODIFY_PASSWORD = "User-Modify password";
	/**
	 * Update counter operation
	 */
	public static final String USER_UPDATE_COUNTER = "User-Update counter";
	/**
	 * Activate user profile operation
	 */
	public static final String USER_ACTIVATE_PROFILE = "User-Activate profile";
	/**
	 * delete user operation
	 */
	public static final String USER_DELETE = "User-Delete";
	/**
	 * Logout user reader operation
	 */
	public static final String USER_LOGOUT_READER = "User-Logout reader";
	/**
	 * Logout user operation
	 */
	public static final String USER_LOGOUT = "User-Logout";
	/**
	 * Add book operation
	 */
	public static final String BOOK_ADD = "Book-Add";
	/**
	 * Remove book operation
	 */
	public static final String BOOK_REMOVE = "Book-Remove";
	/**
	 * Add booking operation
	 */
	public static final String BOOKING_ADD = "Booking-Add";
	/**
	 * Delete booking operation
	 */
	public static final String BOOKING_DELETE = "Booking-Delete";
	/**
	 * Bookings user operation
	 */
	public static final String BOOKING_BOOKINGS_USER = "Booking-Bookings user";
	/**
	 * Add loan operation
	 */
	public static final String LOAN_ADD = "Loan-Add";
	/**
	 * Back loan operation
	 */
	public static final String LOAN_BACK = "Loan-Back";
	/**
	 * Delete loan operation
	 */
	public static final String LOAN_DELETE = "Loan-Delete";
	/**
	 * Loans user operation
	 */
	public static final String LOAN_LOANS_USER = "Loan-Loans user";
	/**
	 * History loans user operation
	 */
	public static final String LOAN_HISTORY_LOANS_USER = "Loan-History loans user";
	/**
	 * Update library books operation
	 */
	public static final String LIBRARY_UPDATE_BOOKS = "Library-Update books";
	/**
	 * Update library bookings operation
	 */
	public static final String LIBRARY_UPDATE_BOOKINGS = "Library-Update bookings";
	/**
	 * Update library loans operation
	 */
	public static final String LIBRARY_UPDATE_LOANS = "Library-Update loans";
	/**
	 * Library books filter
	 */
	public static final String LIBRARY_BOOKS_FILTER = "Library-Books filter";
	/**
	 * Library bookings filter
	 */
	public static final String LIBRARY_BOOKINGS_FILTER = "Library-Bookings filter";
	/**
	 * Library loans filter
	 */
	public static final String LIBRARY_LOANS_FILTER = "Library-Loans filter";
	/**
	 * Library overwhelming loans filter
	 */
	public static final String LIBRARY_OVERWHELMING_LOANS_FILTER = "Library-Overwhelming loans filter";
	/**
	 * Library user history loans filter
	 */
	public static final String LIBRARY_HISTORY_LOANS_USER_FILTER = "Library-History loans user filter";
	/**
	 * Library absolute rank operation
	 */
	public static final String LIBRARY_ABSOLUTE_RANK = "Library-Absolute rank";
	/**
	 * Library category rank operation
	 */
	public static final String LIBRARY_CATEGORY_RANK = "Library-Category rank";
	/**
	 * Library classification rank operation
	 */
	public static final String LIBRARY_CLASSIFICATION_RANK = "Library-Classification rank";
	/**
	 * Library all overwhelming loans
	 */
	public static final String LIBRARY_ALL_OVERWHELMING_LOANS = "Library-All overwhelming loans";
	/**
	 * Forced logout operation
	 */
	public static final String FORCED_LOGOUT = "Forced logout";
	/**
	 * Forced logout librarian user operation
	 */
	public static final String FORCED_LOGOUT_LIBRARIAN = "Forced logout librarian";
	/**
	 * Forced logout reader user operation
	 */
	public static final String FORCED_LOGOUT_READER = "Forced logout reader";
	/**
	 * Login category operation
	 */
	public static final String LOGIN = "Login";
	/**
	 * User category operation
	 */
	public static final String USER = "User";
	/**
	 * Book category operation
	 */
	public static final String BOOK = "Book";
	/**
	 * Booking category operation
	 */
	public static final String BOOKING = "Booking";
	/**
	 * Loan category operation
	 */
	public static final String LOAN = "Loan";
	/**
	 * Library category operation
	 */
	public static final String LIBRARY = "Library";
	/**
	 * End operation
	 */
	public static final String END = "End";
}
