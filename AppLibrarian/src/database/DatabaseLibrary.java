package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import dataModel.Category;
import dataModel.Command;
import dataModel.Book;
import dataModel.Booking;
import dataModel.Classification;
import dataModel.Loan;
import dataModel.User;
import utils.Operation;

/**
 * This class provides methods to handle queries about library operations and
 * return the selected informations
 *
 * @author Marco Di Capua
 * @author Mattia Lo Schiavo
 * @author Filippo Pelosi
 * @author Riccardo Zorzi
 * @version 1.0
 */
@SuppressWarnings("unchecked")
public class DatabaseLibrary {

	/**
	 * Executes the update bookings query
	 * 
	 * @param executor
	 *            the executor that execute the query
	 * @param command
	 *            a command with the page number requested
	 * @return an HashMap with pages number and the 25 bookings requested
	 * @throws SQLException
	 *             if an SQL error occurs while executing the query
	 */
	public static HashMap<String, Object> updateBookings(QueryExecutor executor, Command command) throws SQLException {
		int offset = (int) command.getData();
		LibraryQuery updateBookings = new LibraryQuery(PredefinedSQLCode.selectAllBookings,
				Operation.LIBRARY_UPDATE_BOOKINGS, offset, null);
		ResultSet rsAllBookings = executor.executeQuery(updateBookings);
		ArrayList<Booking> bookingsUpdate = DatabaseBooking.resultSetToBookingsArray(executor, rsAllBookings, null);
		close(updateBookings);
		LibraryQuery countBookings = new LibraryQuery(PredefinedSQLCode.countAllBookings, "", 0, null);
		ResultSet rsCountBookings = executor.executeQuery(countBookings);
		rsCountBookings.next();
		int count = rsCountBookings.getInt(1);
		countBookings.close();
		int pages = count % 25 == 0 ? count / 25 : count / 25 + 1;
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("Pages", pages);
		resultMap.put("List", bookingsUpdate);
		return resultMap;
	}

	/**
	 * Executes the update books query
	 * 
	 * @param executor
	 *            the executor that execute the query
	 * @param command
	 *            a command with the page number requested
	 * @return an HashMap with pages number and the 25 books requested
	 * @throws SQLException
	 *             if an SQL error occurs while executing the query
	 */
	public static HashMap<String, Object> updateBooks(QueryExecutor executor, Command command) throws SQLException {
		int offset = (int) command.getData();
		LibraryQuery updateBooks = new LibraryQuery(PredefinedSQLCode.selectAllBooks, Operation.LIBRARY_UPDATE_BOOKS,
				offset, null);
		ResultSet rsBooks = executor.executeQuery(updateBooks);
		ArrayList<Book> booksUpdate = DatabaseBook.resultSetToBooksArray(executor, rsBooks);
		close(updateBooks);
		LibraryQuery countBooks = new LibraryQuery(PredefinedSQLCode.countBooks, "", 0, null);
		ResultSet rsCountBooks = executor.executeQuery(countBooks);
		rsCountBooks.next();
		int count = rsCountBooks.getInt(1);
		countBooks.close();
		int pages = count % 25 == 0 ? count / 25 : count / 25 + 1;
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("Pages", pages);
		resultMap.put("List", booksUpdate);
		return resultMap;
	}

	/**
	 * Executes the update loans query
	 * 
	 * @param executor
	 *            the executor that execute the query
	 * @param command
	 *            a command with the page number requested
	 * @return an HashMap with pages number and the 25 loans requested
	 * @throws SQLException
	 *             if an SQL error occurs while executing the query
	 */
	public static HashMap<String, Object> updateLoans(QueryExecutor executor, Command command) throws SQLException {
		int offset = (int) command.getData();
		LibraryQuery updateLoans = new LibraryQuery(PredefinedSQLCode.selectAllLoans, Operation.LIBRARY_UPDATE_LOANS,
				offset, null);
		ResultSet rsAllLoans = executor.executeQuery(updateLoans);
		ArrayList<Loan> loansUpdate = DatabaseLoan.resultSetToLoansArray(executor, rsAllLoans, null);
		close(updateLoans);
		LibraryQuery countLoans = new LibraryQuery(PredefinedSQLCode.countLoans, "", 0, null);
		ResultSet rsCountLoans = executor.executeQuery(countLoans);
		rsCountLoans.next();
		int count = rsCountLoans.getInt(1);
		countLoans.close();
		int pages = count % 25 == 0 ? count / 25 : count / 25 + 1;
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("Pages", pages);
		resultMap.put("List", loansUpdate);
		return resultMap;
	}

	/**
	 * Executes the overwhelming loans query
	 * 
	 * @param executor
	 *            the executor that execute the query
	 * @param command
	 *            a command with the page number requested
	 * @return an HashMap with pages number and the 25 overwhelming requested
	 * @throws SQLException
	 *             if an SQL error occurs while executing the query
	 */
	public static HashMap<String, Object> allOverwhelmingLoans(QueryExecutor executor, Command command)
			throws SQLException {
		int offset = (int) command.getData();
		LibraryQuery allOverwhelimingLoans = new LibraryQuery(PredefinedSQLCode.overwhelmingLoansQuery,
				Operation.LIBRARY_ALL_OVERWHELMING_LOANS, offset, null);
		ResultSet rsOverwhelmingLoans = executor.executeQuery(allOverwhelimingLoans);
		ArrayList<Loan> allOverwhelmingLoansList = DatabaseLoan.resultSetToLoansArray(executor, rsOverwhelmingLoans,
				null);
		close(allOverwhelimingLoans);
		LibraryQuery countLoans = new LibraryQuery(PredefinedSQLCode.countOverwhelmingLoans, "", 0, null);
		ResultSet rsCountLoans = executor.executeQuery(countLoans);
		rsCountLoans.next();
		int count = rsCountLoans.getInt(1);
		countLoans.close();
		int pages = count % 25 == 0 ? count / 25 : count / 25 + 1;
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("Pages", pages);
		resultMap.put("List", allOverwhelmingLoansList);
		return resultMap;
	}

	/**
	 * Executes the books filter query
	 * 
	 * @param executor
	 *            the executor that execute the query
	 * @param command
	 *            a Command with the filter informations
	 * @return an HashMap with pages number and the 25 books filtered requested
	 * @throws SQLException
	 *             if an SQL error occurs while executing the query
	 */
	public static HashMap<String, Object> booksFilter(QueryExecutor executor, Command command) throws SQLException {
		HashMap<String, Object> data = (HashMap<String, Object>) command.getData();
		int offset = (int) data.get("Page");
		String title = (String) data.get("Title");
		String author = (String) data.get("Author");
		Category categoryBook = (Category) data.get("Category");
		LibraryQuery booksFilter = new LibraryQuery(PredefinedSQLCode.booksFilter, Operation.LIBRARY_BOOKS_FILTER,
				offset, title, author, categoryBook, null);
		ResultSet rsBooks = executor.executeQuery(booksFilter);
		ArrayList<Book> booksFilterList = DatabaseBook.resultSetToBooksArray(executor, rsBooks);
		close(booksFilter);
		LibraryQuery countBooks = new LibraryQuery(PredefinedSQLCode.countFilterBooks, "Count filter books", 0, title,
				author, categoryBook, null);
		ResultSet rsCountBooks = executor.executeQuery(countBooks);
		ArrayList<Book> allBooksFilterList = DatabaseBook.resultSetToBooksArray(executor, rsCountBooks);
		int count = allBooksFilterList.size();
		countBooks.close();
		int pages = count % 25 == 0 ? count / 25 : count / 25 + 1;
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("Pages", pages);
		resultMap.put("List", booksFilterList);
		return resultMap;
	}

	/**
	 * Executes the bookings filter query
	 * 
	 * @param executor
	 *            the executor that execute the query
	 * @param command
	 *            a Command with the filter informations
	 * @return an HashMap with pages number and the 25 bookings filtered requested
	 * @throws SQLException
	 *             if an SQL error occurs while executing the query
	 */
	public static HashMap<String, Object> bookingsFilter(QueryExecutor executor, Command command) throws SQLException {
		HashMap<String, Object> bookingsMap = (HashMap<String, Object>) command.getData();
		int offset = (int) bookingsMap.get("Page");
		String bookingsTitle = (String) bookingsMap.get("Title");
		String bookingsAuthor = (String) bookingsMap.get("Author");
		Category bookingsCategory = (Category) bookingsMap.get("Category");
		LibraryQuery bookingsFilter = new LibraryQuery(PredefinedSQLCode.bookingsFilter,
				Operation.LIBRARY_BOOKINGS_FILTER, offset, bookingsTitle, bookingsAuthor, bookingsCategory, null);
		ResultSet rsBookingsFilter = executor.executeQuery(bookingsFilter);
		ArrayList<Booking> bookingsFilterList = DatabaseBooking.resultSetToBookingsArray(executor, rsBookingsFilter,
				null);
		close(bookingsFilter);
		LibraryQuery countBooks = new LibraryQuery(PredefinedSQLCode.countFilterBookings, "Count filter books", 0,
				bookingsTitle, bookingsAuthor, bookingsCategory, null);
		ResultSet rsCountBooks = executor.executeQuery(countBooks);
		ArrayList<Booking> allBookingsFilterList = DatabaseBooking.resultSetToBookingsArray(executor, rsCountBooks,
				null);
		int count = allBookingsFilterList.size();
		countBooks.close();
		int pages = count % 25 == 0 ? count / 25 : count / 25 + 1;
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("Pages", pages);
		resultMap.put("List", bookingsFilterList);
		return resultMap;
	}

	/**
	 * Executes the loans filter query
	 * 
	 * @param executor
	 *            the executor that execute the query
	 * @param command
	 *            a Command with the filter informations
	 * @return an HashMap with pages number and the 25 loans filtered requested
	 * @throws SQLException
	 *             if an SQL error occurs while executing the query
	 */
	public static HashMap<String, Object> loansFiter(QueryExecutor executor, Command command) throws SQLException {
		HashMap<String, Object> loansMap = (HashMap<String, Object>) command.getData();
		int offset = (int) loansMap.get("Page");
		String loansTitle = (String) loansMap.get("Title");
		String loansAuthor = (String) loansMap.get("Author");
		Category loansCategory = (Category) loansMap.get("Category");
		LibraryQuery loansFilter = new LibraryQuery(PredefinedSQLCode.loansFilter, Operation.LIBRARY_LOANS_FILTER,
				offset, loansTitle, loansAuthor, loansCategory, null);
		ResultSet rsLoanFilter = executor.executeQuery(loansFilter);
		ArrayList<Loan> loansFiterList = DatabaseLoan.resultSetToLoansArray(executor, rsLoanFilter, null);
		close(loansFilter);
		LibraryQuery countBooks = new LibraryQuery(PredefinedSQLCode.countFilterLoans, "Count filter books", 0,
				loansTitle, loansAuthor, loansCategory, null);
		ResultSet rsCountBooks = executor.executeQuery(countBooks);
		ArrayList<Loan> allLoansFiterList = DatabaseLoan.resultSetToLoansArray(executor, rsCountBooks, null);
		int count = allLoansFiterList.size();
		countBooks.close();
		int pages = count % 25 == 0 ? count / 25 : count / 25 + 1;
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("Pages", pages);
		resultMap.put("List", loansFiterList);
		return resultMap;
	}

	/**
	 * Executes the overwhelming loans filter query
	 * 
	 * @param executor
	 *            the executor that execute the query
	 * @param command
	 *            a Command with the filter informations
	 * @return an HashMap with pages number and the 25 overwhelming loans filtered
	 *         requested
	 * @throws SQLException
	 *             if an SQL error occurs while executing the query
	 */
	public static HashMap<String, Object> overwhelmingLoansFilter(QueryExecutor executor, Command command)
			throws SQLException {
		HashMap<String, Object> overwhelmingLoansMap = (HashMap<String, Object>) command.getData();
		int offset = (int) overwhelmingLoansMap.get("Page");
		String overwhelmingLoansTitle = (String) overwhelmingLoansMap.get("Title");
		String overwhelmingLoansAuthor = (String) overwhelmingLoansMap.get("Author");
		Category overwhelmingLoansCategory = (Category) overwhelmingLoansMap.get("Category");
		LibraryQuery overwhelmingLoansFilter = new LibraryQuery(PredefinedSQLCode.overwhelmingLoansFilter,
				Operation.LIBRARY_OVERWHELMING_LOANS_FILTER, offset, overwhelmingLoansTitle, overwhelmingLoansAuthor,
				overwhelmingLoansCategory, null);
		ResultSet rsOverwhelmingLoansFilter = executor.executeQuery(overwhelmingLoansFilter);
		ArrayList<Loan> overwhelmingLoansFilterList = DatabaseLoan.resultSetToLoansArray(executor,
				rsOverwhelmingLoansFilter, null);
		close(overwhelmingLoansFilter);
		LibraryQuery countBooks = new LibraryQuery(PredefinedSQLCode.countOverwhelmingFilterLoans, "Count filter books",
				0, overwhelmingLoansTitle, overwhelmingLoansAuthor, overwhelmingLoansCategory, null);
		ResultSet rsCountBooks = executor.executeQuery(countBooks);
		ArrayList<Loan> allOverwhelmingLoansFilterList = DatabaseLoan.resultSetToLoansArray(executor, rsCountBooks,
				null);
		int count = allOverwhelmingLoansFilterList.size();
		countBooks.close();
		int pages = count % 25 == 0 ? count / 25 : count / 25 + 1;
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("Pages", pages);
		resultMap.put("List", overwhelmingLoansFilterList);
		return resultMap;
	}

	/**
	 * Executes the history loans user filter query
	 * 
	 * @param executor
	 *            the executor that execute the query
	 * @param command
	 *            a Command with the filter informations
	 * @return an HashMap with pages number and the 25 history loans user filtered
	 *         requested
	 * @throws SQLException
	 *             if an SQL error occurs while executing the query
	 */
	public static HashMap<String, Object> historyLoansUserFilter(QueryExecutor executor, Command command)
			throws SQLException {
		HashMap<String, Object> historyLoansMap = (HashMap<String, Object>) command.getData();
		int offset = (int) historyLoansMap.get("Page");
		String historyLoansUserTitle = (String) historyLoansMap.get("Title");
		String historyLoansUserAuthor = (String) historyLoansMap.get("Author");
		Category historyLoansUserCategory = (Category) historyLoansMap.get("Category");
		User user = (User) historyLoansMap.get("User");
		LibraryQuery historyLoansUserFilter = new LibraryQuery(PredefinedSQLCode.historyLoansUserFilter,
				Operation.LIBRARY_HISTORY_LOANS_USER_FILTER, offset, historyLoansUserTitle, historyLoansUserAuthor,
				historyLoansUserCategory, user.getFiscalCode());
		ResultSet rsHistoryLoansUserFilter = executor.executeQuery(historyLoansUserFilter);
		ArrayList<Loan> historyLoansUserFilterList = DatabaseLoan.resultSetToLoansArray(executor,
				rsHistoryLoansUserFilter, user);
		close(historyLoansUserFilter);
		LibraryQuery countBooks = new LibraryQuery(PredefinedSQLCode.countHistoryLoansUserFilter,
				"Count history user books", 0, historyLoansUserTitle, historyLoansUserAuthor, historyLoansUserCategory,
				user.getFiscalCode());
		ResultSet rsCountBooks = executor.executeQuery(countBooks);
		ArrayList<Loan> allHistoryLoansUserFilterList = DatabaseLoan.resultSetToLoansArray(executor, rsCountBooks,
				user);
		int count = allHistoryLoansUserFilterList.size();
		countBooks.close();
		int pages = count % 25 == 0 ? count / 25 : count / 25 + 1;
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("Pages", pages);
		resultMap.put("List", historyLoansUserFilterList);
		return resultMap;
	}

	/**
	 * Executes the absolute rank query
	 * 
	 * @param executor
	 *            the executor that execute the query
	 * @param command
	 *            a command with the page number requested
	 * @return an HashMap with pages number and the 25 books of the absolute rank
	 *         requested
	 * @throws SQLException
	 *             if an SQL error occurs while executing the query
	 */
	public static HashMap<String, Object> absoluteRank(QueryExecutor executor, Command command) throws SQLException {
		int offset = (int) command.getData();
		LibraryQuery absoluteRank = new LibraryQuery(PredefinedSQLCode.absoluteRank, Operation.LIBRARY_ABSOLUTE_RANK,
				offset, null);
		ResultSet rsAbsoluteRank = executor.executeQuery(absoluteRank);
		ArrayList<Book> absoluteRankList = DatabaseBook.resultSetToBooksArray(executor, rsAbsoluteRank);
		close(absoluteRank);
		LibraryQuery countBooks = new LibraryQuery(PredefinedSQLCode.countAbsoluteRank, "", 0, null);
		ResultSet rsCountBooks = executor.executeQuery(countBooks);
		rsCountBooks.next();
		int count = rsCountBooks.getInt(2);
		countBooks.close();
		int pages = count % 25 == 0 ? count / 25 : count / 25 + 1;
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("Pages", pages);
		resultMap.put("List", absoluteRankList);
		return resultMap;
	}

	/**
	 * Executes the category rank query
	 * 
	 * @param executor
	 *            the executor that execute the query
	 * @param command
	 *            a Command with the category to select and the page number
	 *            requested
	 * @return an HashMap with pages number and the 25 books of the category rank
	 *         requested
	 * @throws SQLException
	 *             if an SQL error occurs while executing the query
	 */
	public static HashMap<String, Object> categoryRank(QueryExecutor executor, Command command) throws SQLException {
		HashMap<String, Object> map = (HashMap<String, Object>) command.getData();
		int offset = (int) map.get("Page");
		Category category = (Category) map.get("Category");
		LibraryQuery categoryRank = new LibraryQuery(PredefinedSQLCode.categoryRank, Operation.LIBRARY_CATEGORY_RANK,
				offset, category.name());
		ResultSet rsCategoryRank = executor.executeQuery(categoryRank);
		ArrayList<Book> categoryRankList = DatabaseBook.resultSetToBooksArray(executor, rsCategoryRank);
		close(categoryRank);
		LibraryQuery countBooks = new LibraryQuery(PredefinedSQLCode.countCategoryRank, "Set parameter", 0,
				category.name());
		ResultSet rsCountBooks = executor.executeQuery(countBooks);
		rsCountBooks.next();
		int count = rsCountBooks.getInt(2);
		countBooks.close();
		int pages = count % 25 == 0 ? count / 25 : count / 25 + 1;
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("Pages", pages);
		resultMap.put("List", categoryRankList);
		return resultMap;
	}

	/**
	 * Executes the classification rank query
	 * 
	 * @param executor
	 *            the executor that execute the query
	 * @param command
	 *            a Command with the classification selected and the page number
	 *            requested
	 * @return an HashMap with pages number and the 25 books of the classification
	 *         rank requested
	 * @throws SQLException
	 *             if an SQL error occurs while executing the query
	 */
	public static HashMap<String, Object> classificationRank(QueryExecutor executor, Command command)
			throws SQLException {
		HashMap<String, Object> map = (HashMap<String, Object>) command.getData();
		int offset = (int) map.get("Page");
		Classification classification = (Classification) map.get("Classification");
		LibraryQuery classificationRank = new LibraryQuery(PredefinedSQLCode.classificationRank,
				Operation.LIBRARY_CLASSIFICATION_RANK, offset, classification.name());
		ResultSet rsClassificationRank = executor.executeQuery(classificationRank);
		ArrayList<Book> classificationRankList = DatabaseBook.resultSetToBooksArray(executor, rsClassificationRank);
		close(classificationRank);
		LibraryQuery countBooks = new LibraryQuery(PredefinedSQLCode.countClassificationRank, "Set parameter", 0,
				classification.name());
		ResultSet rsCountBooks = executor.executeQuery(countBooks);
		System.out.println("done");
		rsCountBooks.next();
		int count = rsCountBooks.getInt(2);
		countBooks.close();
		int pages = count % 25 == 0 ? count / 25 : count / 25 + 1;
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("Pages", pages);
		resultMap.put("List", classificationRankList);
		return resultMap;
	}

	/**
	 * Executing the all authors query
	 * 
	 * @param executor
	 *            the executor that execute the query
	 * @return an ArrayList with all the authors
	 * @throws SQLException
	 *             if an SQL error occurs while executing the query
	 */
	public static ArrayList<String> allAuthors(QueryExecutor executor) throws SQLException {
		LibraryQuery allAuthorsQuery = new LibraryQuery(PredefinedSQLCode.selectAllAuthors, "Select all authors", 0,
				null);
		ResultSet rsAuthors = executor.executeQuery(allAuthorsQuery);
		ArrayList<String> allAuthors = new ArrayList<String>();
		while (rsAuthors.next()) {
			allAuthors.add(rsAuthors.getString("name") + " " + rsAuthors.getString("surname"));
		}
		close(allAuthorsQuery);
		return allAuthors;
	}

	private static void close(PreparableQuery query) throws SQLException {
		if (query != null) {
			query.close();
		}
	}

}
