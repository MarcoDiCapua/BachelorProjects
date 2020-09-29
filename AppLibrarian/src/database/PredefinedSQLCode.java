package database;

/**
 * This class contains strings representing SQL code
 *
 * @author Marco Di Capua
 * @author Mattia Lo Schiavo
 * @author Filippo Pelosi
 * @author Riccardo Zorzi
 * @version 1.0
 */
public class PredefinedSQLCode {
	/**
	 * Delete user query
	 */
	public static final String deleteUser = "delete from users where fiscal_code = ?";
	/**
	 * Login user librarian query
	 */
	public static final String loginLibrarian = "select * from view_librarian where fiscal_code = ?  and password = ?";
	/**
	 * Login user reader query
	 */
	public static final String loginReader = "select * from users where fiscal_code = ?  and password = ? and classification != 'LIBRARIAN'";
	/**
	 * Login user reader from librarian account query
	 */
	public static final String loginUserFromLibrarian = "select * from users where fiscal_code = ? and classification != 'Librarian'";
	/**
	 * Registration user reader query
	 */
	public static final String registrationUser = "insert into users values (?,?,?,?,?,?,?,?,?,?,?)";
	/**
	 * Registration user librarian query
	 */
	public static final String registrationLibrarian = "insert into view_librarian values (?,?,?,?,?,?,?,?,?,?,?)";
	/**
	 * Update activation code and password query
	 */
	public static final String updateActivationCodeAndPassword = "update users set activation_code = ?, password = ? where fiscal_code = ?";
	/**
	 * Update classification query
	 */
	public static final String updateClassification = "update users set classification = ?, year_class = ? where fiscal_code = ?";
	/**
	 * Update activation counter query
	 */
	public static final String updateCounter = "update users set counter = ? where fiscal_code = ?";
	/**
	 * Update email query
	 */
	public static final String updateEmail = "update users set email = ? where fiscal_code = ?";
	/**
	 * Update password query
	 */
	public static final String updatePassword = "update users set password = ?, temporaney_pwd = false where fiscal_code = ?";
	/**
	 * Update telephone number query
	 */
	public static final String updateTelephoneNumber = "update users set telephone_number = ? where fiscal_code = ?";
	/**
	 * Activate user query
	 */
	public static final String activateUser = "update users set activation_code = ? where fiscal_code = ?";
	/**
	 * Update activation code and password librarian user query
	 */
	public static final String updateActivationCodeAndPasswordLibrarian = "update view_librarian set activation_code = ?, password = ? where fiscal_code = ?";
	/**
	 * Update classification librarian user query
	 */
	public static final String updateClassificationLibrarian = "update view_librarian set classification = ?, year_class = ? where fiscal_code = ?";
	/**
	 * Update activation counter librarian user query
	 */
	public static final String updateCounterLibrarian = "update view_librarian set counter = ? where fiscal_code = ?";
	/**
	 * Update email librarian user query
	 */
	public static final String updateEmailLibrarian = "update view_librarian set email = ? where fiscal_code = ?";
	/**
	 * Update password librarian user query
	 */
	public static final String updatePasswordLibrarian = "update view_librarian set password = ? where fiscal_code = ?";
	/**
	 * Update telephone number librarian user query
	 */
	public static final String updateTelephoneNumberLibrarian = "update view_librarian set telephone_number = ? where fiscal_code = ?";
	/**
	 * Activate librarian user query
	 */
	public static final String activateUserLibrarian = "update view_librarian set activation_code = ? where fiscal_code = ?";
	/**
	 * Select informations booking queue query
	 */
	public static final String selectUsersBookingBook = "select users.email, books.isbn, books.title, bookings.booking_date from users join bookings on users.fiscal_code = bookings.id_user join books on bookings.id_book = books.isbn where bookings.id_book = ?";
	/**
	 * Select first user information booking queue query
	 */
	public static final String selectUserBookingBook = "select users.fiscal_code, users.email, books.isbn, books.title, bookings.booking_date from users join bookings on users.fiscal_code = bookings.id_user join books on bookings.id_book = books.isbn where bookings.id_booking = (select min(id_booking) from bookings where bookings.id_book = ?)";
	/**
	 * Select user from fiscal code query
	 */
	public static final String selectUserFromFiscalCode = "select * from users where fiscal_code = ?";
	/**
	 * Select overwhelming loans user query
	 */
	public static final String selectOverwhelmingLoansUser = "select * from loans where id_user = ? and return_date < CURRENT_DATE and returned = false order by id_book asc";
	/**
	 * Insert new book query
	 */
	public static final String addBook = "insert into books values (?,?,?,?,?,?,?,?)";
	/**
	 * Remove book query
	 */
	public static final String removeBook = "delete from books where isbn = ?";
	/**
	 * Books filter query
	 */
	public static final String booksFilter = "select distinct books.isbn, books.title, books.publication_year, books.reprint_year, books.bookcase, books.language, books.category, books.publishing_house from books join write on write.id_book = books.isbn join authors on authors.id_aut = write.id_author where lower(title) like ? and books.category like ? and (authors.name like ? or authors.surname like ?) order by books.isbn asc limit 25 offset ?";
	/**
	 * Bookings filter query
	 */
	public static final String bookingsFilter = "select distinct books.isbn, bookings.id_book, bookings.id_user, bookings.booking_date from bookings join books on bookings.id_book = books.isbn join write on write.id_book = books.isbn join authors on authors.id_aut = write.id_author where lower(title) like ? and category like ? and (authors.name like ? or authors.surname like ?) order by books.isbn asc limit 25 offset ?";
	/**
	 * Loans filter query
	 */
	public static final String loansFilter = "select distinct books.isbn, loans.id_book, loans.id_user, loans.loan_date, loans.return_date, loans.returned from loans join books on loans.id_book = books.isbn join write on write.id_book = books.isbn join authors on authors.id_aut = write.id_author where loans.returned = false and lower(title) like ? and category like ? and (authors.name like ? or authors.surname like ?) order by books.isbn asc limit 25 offset ?";
	/**
	 * Overwhelming loans filter query
	 */
	public static final String overwhelmingLoansFilter = "select distinct books.isbn, loans.id_book, loans.id_user, loans.loan_date, loans.return_date, loans.returned from loans join books on loans.id_book = books.isbn join write on write.id_book = books.isbn join authors on authors.id_aut = write.id_author where return_date < CURRENT_DATE and loans.returned = false and lower(title) like ? and category like ? and (authors.name like ? or authors.surname like ?) order by books.isbn asc limit 25 offset ?";
	/**
	 * History loans filter query
	 */
	public static final String historyLoansUserFilter = "select distinct books.isbn, loans.id_book, loans.id_user, loans.loan_date, loans.return_date, loans.returned from loans join books on loans.id_book = books.isbn join write on write.id_book = books.isbn join authors on authors.id_aut = write.id_author where loans.id_user = ? and loans.returned = true and lower(title) like ? and category like ? and (authors.name like ? or authors.surname like ?) order by books.isbn asc limit 25 offset ?";
	/**
	 * Select all books filter query
	 */
	public static final String selectAllBooks = "select * from books order by isbn asc limit 25 offset ?";
	/**
	 * Select books from ISBN query
	 */
	public static final String selectBookFromISBN = "select * from books where isbn = ?";
	/**
	 * Count bookings query
	 */
	public static final String countBookings = "select count(*) from bookings where id_book = ?";
	/**
	 * Select all bookings query
	 */
	public static final String selectAllBookings = "select * from bookings order by id_book asc limit 25 offset ?";
	/**
	 * Select all loans query
	 */
	public static final String selectAllLoans = "select * from loans where returned = false order by id_book asc limit 25 offset ?";
	/**
	 * Add booking query
	 */
	public static final String addBooking = "insert into bookings(id_user, id_book, booking_date) values (?,?,?)";
	/**
	 * Delete booking query
	 */
	public static final String deleteBooking = "delete from bookings where id_user = ? and id_book = ?";
	/**
	 * Add loan query
	 */
	public static final String addLoan = "insert into loans values (?,?,?,?,?)";
	/**
	 * Return loan query
	 */
	public static final String returnLoan = "update loans set returned = true, return_date = ? where id_user = ? and id_book = ?";
	/**
	 * Overwhelming loans query
	 */
	public static final String overwhelmingLoansQuery = "select * from loans where return_date < CURRENT_DATE and returned = false order by loans.id_book asc limit 25 offset ?";
	/**
	 * Select authors book query
	 */
	public static final String selectBookAuthors = "select authors.name, authors.surname from write join authors on write.id_author = authors.id_aut where write.id_book = ?";
	/**
	 * Add author query
	 */
	public static final String addAuthor = "insert into authors (name, surname) values (?,?)";
	/**
	 * Add write query
	 */
	public static final String addWrite = "insert into write(id_author, id_book) select authors.id_aut, books.isbn from authors, books where authors.name = ? and authors.surname = ? and books.isbn = ?";
	/**
	 * Select all authors query
	 */
	public static final String selectAllAuthors = "select authors.name, authors.surname from authors";
	/**
	 * Select bookings user query
	 */
	public static final String selectBookingsUser = "select * from bookings where id_user = ? order by id_book asc";
	/**
	 * Select loans user query
	 */
	public static final String selectLoansUser = "select * from loans where id_user = ? and returned = false order by id_book asc";
	/**
	 * Select history loans user
	 */
	public static final String selectHistoryLoansUser = "select * from loans where id_user = ? and returned = true order by id_book asc limit 25 offset ?";
	/**
	 * Control if there is loan query
	 */
	public static final String controlLoan = "select * from loans where id_user = ? and id_book = ? and loan_date >= ?";
	/**
	 * Control if user has already loaned query
	 */
	public static final String controlAlreadyLoan = "select * from loans where id_user = ? and id_book = ? and returned = false";
	/**
	 * Delete loan query
	 */
	public static final String deleteLoan = "delete from loans where id_user = ? and id_book = ? and returned = false";
	/**
	 * Delete author
	 */
	public static final String deleteAuthor = "delete from authors where name = ? and surname = ? and id_aut not in (select distinct id_author from write)";
	/**
	 * Absolute rank query
	 */
	public static final String absoluteRank = "select books.isbn, books.title, books.publication_year, books.reprint_year, books.bookcase, books.language, books.category, books.publishing_house from loans join books on loans.id_book = books.isbn group by books.isbn order by count(*) desc limit 25 offset ?";
	/**
	 * Category rank
	 */
	public static final String categoryRank = "select books.isbn, books.title, books.publication_year, books.reprint_year, books.bookcase, books.language, books.category, books.publishing_house from loans join books on loans.id_book = books.isbn where books.category like ? group by books.isbn order by count(*) desc limit 25 offset ?";
	/**
	 * Classification rank
	 */
	public static final String classificationRank = "select books.isbn, books.title, books.publication_year, books.reprint_year, books.bookcase, books.language, books.category, books.publishing_house from loans join books on loans.id_book = books.isbn join users on loans.id_user = users.fiscal_code where users.classification = ? group by books.isbn order by count(*) desc limit 25 offset ?";
	/**
	 * Count books
	 */
	public static final String countBooks = "select count(*) from books";
	/**
	 * Count history loans user
	 */
	public static final String countHistoryLoansUser = "select count(*) from loans where id_user = ? and returned = true";
	/**
	 * Count all bookings
	 */
	public static final String countAllBookings = "select count(*) from bookings";
	/**
	 * Count all loans
	 */
	public static final String countLoans = "select count(*) from loans where returned = false ";
	/**
	 * Count filter books
	 */
	public static final String countFilterBooks = "select distinct books.isbn, books.title, books.publication_year, books.reprint_year, books.bookcase, books.language, books.category, books.publishing_house from books join write on write.id_book = books.isbn join authors on authors.id_aut = write.id_author where lower(title) like ? and books.category like ? and (authors.name like ? or authors.surname like ?) order by books.isbn";
	/**
	 * Count filter bookings
	 */
	public static final String countFilterBookings = "select distinct books.isbn, bookings.id_book, bookings.id_user, bookings.booking_date from bookings join books on bookings.id_book = books.isbn join write on write.id_book = books.isbn join authors on authors.id_aut = write.id_author where lower(title) like ? and category like ? and (authors.name like ? or authors.surname like ?) order by books.isbn";
	/**
	 * Count filter loans
	 */
	public static final String countFilterLoans = "select distinct books.isbn, loans.id_book, loans.id_user, loans.loan_date, loans.return_date, loans.returned from loans join books on loans.id_book = books.isbn join write on write.id_book = books.isbn join authors on authors.id_aut = write.id_author where loans.returned = false and lower(title) like ? and category like ? and (authors.name like ? or authors.surname like ?) order by books.isbn";
	/**
	 * Count overwhelming filter loans
	 */
	public static final String countOverwhelmingFilterLoans = "select distinct books.isbn, loans.id_book, loans.id_user, loans.loan_date, loans.return_date, loans.returned from loans join books on loans.id_book = books.isbn join write on write.id_book = books.isbn join authors on authors.id_aut = write.id_author where return_date < CURRENT_DATE and loans.returned = false and lower(title) like ? and category like ? and (authors.name like ? or authors.surname like ?) order by books.isbn";
	/**
	 * Count history loans user filter
	 */
	public static final String countHistoryLoansUserFilter = "select distinct books.isbn, loans.id_book, loans.id_user, loans.loan_date, loans.return_date, loans.returned from loans join books on loans.id_book = books.isbn join write on write.id_book = books.isbn join authors on authors.id_aut = write.id_author where loans.id_user = ? and loans.returned = true and lower(title) like ? and category like ? and (authors.name like ? or authors.surname like ?) order by books.isbn";
	/**
	 * Count absolute rank
	 */
	public static final String countAbsoluteRank = "select books.isbn, count(*) from loans join books on loans.id_book = books.isbn group by books.isbn";
	/**
	 * Count category rank
	 */
	public static final String countCategoryRank = "select books.isbn, count(*) from loans join books on loans.id_book = books.isbn where books.category like ? group by books.isbn";
	/**
	 * Count classification rank
	 */
	public static final String countClassificationRank = "select books.isbn, count(*) from loans join books on loans.id_book = books.isbn join users on loans.id_user = users.fiscal_code where users.classification = ? group by books.isbn";
	/**
	 * Count overwhelming loans
	 */
	public static final String countOverwhelmingLoans = "select count(*) from loans where return_date < CURRENT_DATE and returned = false";
}
