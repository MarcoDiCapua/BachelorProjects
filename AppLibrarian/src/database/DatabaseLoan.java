package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import dataModel.Book;
import dataModel.Classification;
import dataModel.Loan;
import dataModel.User;
import utils.Operation;

/**
 * This class provides methods to handle queries about loans table and return
 * the selected informations
 *
 * @author Marco Di Capua
 * @author Mattia Lo Schiavo
 * @author Filippo Pelosi
 * @author Riccardo Zorzi
 * @version 1.0
 */
public class DatabaseLoan {
	/**
	 * Executes the registration loan query
	 * 
	 * @param executor
	 *            the executor that execute the query
	 * @param loan
	 *            the loan informations
	 * @throws SQLException
	 *             if an SQL error occurs while executing the query
	 */
	public static void insertLoan(QueryExecutor executor, Loan loan) throws SQLException {
		LoanQuery insertLoan = new LoanQuery(PredefinedSQLCode.addLoan, Operation.LOAN_ADD, loan);
		executor.executeUpdate(insertLoan);
		close(insertLoan);
	}

	/**
	 * Executes the delete loan query
	 * 
	 * @param executor
	 *            the executor that execute the query
	 * @param loanToCancel
	 *            the loan informations
	 * @return true if the delete operation completed successfully, false otherwise
	 * @throws SQLException
	 *             if an SQL error occurs while executing the query
	 */
	public static boolean deleteLoan(QueryExecutor executor, Loan loanToCancel) throws SQLException {
		LoanQuery deleteLoan = new LoanQuery(PredefinedSQLCode.deleteLoan, Operation.LOAN_DELETE, loanToCancel);
		boolean bln = executor.executeDelete(deleteLoan);
		close(deleteLoan);
		return bln;
	}

	/**
	 * Executes the back loan query
	 * 
	 * @param executor
	 *            the executor that execute the query
	 * @param loanToReturn
	 *            the loan informations
	 * @throws SQLException
	 *             if an SQL error occurs while executing the query
	 */
	public static void loanBack(QueryExecutor executor, Loan loanToReturn) throws SQLException {
		LoanQuery loanBack = new LoanQuery(PredefinedSQLCode.returnLoan, Operation.LOAN_BACK, loanToReturn);
		executor.executeUpdate(loanBack);
		close(loanBack);
	}

	/**
	 * Executes the control already loan query
	 * 
	 * @param executor
	 *            the executor that execute the query
	 * @param loan
	 *            the loan informations
	 * @return true if the has already loan the book, false otherwise
	 * @throws SQLException
	 *             if an SQL error occurs while executing the query
	 */
	public static boolean controlAlreadyLoan(QueryExecutor executor, Loan loan) throws SQLException {
		LoanQuery controlAlreadyLoaned = new LoanQuery(PredefinedSQLCode.controlAlreadyLoan, "Control already loan",
				loan);
		ResultSet resultAlreadyLoaned = executor.executeQuery(controlAlreadyLoaned);
		boolean bln = resultAlreadyLoaned.next();
		close(controlAlreadyLoaned);
		return bln;
	}

	/**
	 * Executes the control loan query
	 * 
	 * @param executor
	 *            the executor that execute the query
	 * @param loan
	 *            the loan informations
	 * @return true if there is a loan, false otherwise
	 * @throws SQLException
	 *             if an SQL error occurs while executing the query
	 */
	public static boolean controlLoan(QueryExecutor executor, Loan loan) throws SQLException {
		LoanQuery controlLoan = new LoanQuery(PredefinedSQLCode.controlLoan, "Control loan", loan);
		ResultSet resultControlLoan = executor.executeQuery(controlLoan);
		boolean bln = resultControlLoan.next();
		close(controlLoan);
		return bln;
	}

	/**
	 * Convert the ResultSet with the loans informations in an ArrayList of Loan
	 * objects
	 * 
	 * @param queryExecutor
	 *            the executor that execute the query to select loans informations
	 * @param rsLoans
	 *            the ResultSet with the loans informations
	 * @param user
	 *            the loan user
	 * @return an ArrayList with all the loans informations
	 * @throws SQLException
	 *             if an SQL error occurs while executing the query
	 */
	public static ArrayList<Loan> resultSetToLoansArray(QueryExecutor queryExecutor, ResultSet rsLoans, User user)
			throws SQLException {
		ArrayList<Loan> loans = new ArrayList<Loan>();
		User userLoan = user;
		while (rsLoans.next()) {
			LibraryQuery selectBook = new LibraryQuery(PredefinedSQLCode.selectBookFromISBN, "Select book from isbn",
					0, rsLoans.getString("id_book"));
			ResultSet rsBook = queryExecutor.executeQuery(selectBook);
			ArrayList<Book> bookArray = DatabaseBook.resultSetToBooksArray(queryExecutor, rsBook);
			Book book = null;
			if (bookArray.size() > 0) {
				book = bookArray.get(0);
			}
			GregorianCalendar loanDate = new GregorianCalendar();
			loanDate.setTime(rsLoans.getDate("loan_date"));
			GregorianCalendar returnDate = new GregorianCalendar();
			returnDate.setTime(rsLoans.getDate("return_date"));

			if (userLoan == null) {
				LibraryQuery selectUser = new LibraryQuery(PredefinedSQLCode.selectUserFromFiscalCode,
						"Select user from fiscal code", 0, rsLoans.getString("id_user"));
				ResultSet rsUsers = queryExecutor.executeQuery(selectUser);
				if (rsUsers.next()) {
					userLoan = new User(rsUsers.getString("name"), rsUsers.getString("surname"),
							rsUsers.getString("fiscal_code"), rsUsers.getString("email"),
							Classification.valueOf(rsUsers.getString("classification")),
							rsUsers.getString("year_class"), rsUsers.getString("telephone_number"),
							rsUsers.getString("password"), rsUsers.getBoolean("temporaney_pwd"),
							rsUsers.getString("activation_code"), rsUsers.getInt("counter"));
				}
				close(selectUser);
			}
			if (book != null && userLoan != null) {
				loans.add(new Loan(book, userLoan, loanDate, returnDate, rsLoans.getBoolean("returned")));
			}
			userLoan = user;
			close(selectBook);
		}
		return loans;
	}

	private static void close(PreparableQuery query) throws SQLException {
		if (query != null) {
			query.close();
		}
	}

}
