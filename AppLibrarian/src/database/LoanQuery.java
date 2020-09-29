package database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import dataModel.Loan;
import utils.Operation;

/**
 * This class provides methods to prepare queries about loans table
 *
 * @author Marco Di Capua
 * @author Mattia Lo Schiavo
 * @author Filippo Pelosi
 * @author Riccardo Zorzi
 * @version 1.0
 */
public class LoanQuery implements PreparableQuery {

	private String sqlCode;
	private String operation;
	private PreparedStatement statement;
	private Loan loan;

	/**
	 * Create a new LoanQuery object with the informations given
	 * 
	 * @param sqlCode
	 *            the SQL code to execute
	 * @param operation
	 *            the loan operation to execute
	 * @param loan
	 *            the loan that contains the informations necessary to prepare the
	 *            query
	 */
	public LoanQuery(String sqlCode, String operation, Loan loan) {
		super();
		this.sqlCode = sqlCode;
		this.operation = operation;
		this.statement = null;
		this.loan = loan;
	}

	/**
	 * Prepare the LoanQuery object with the informations in the loan object
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
			case Operation.LOAN_ADD:
				this.prepareAddStatement();
				break;
			case Operation.LOAN_BACK:
				this.prepareBackBookStatement();
				break;
			case Operation.LOAN_DELETE:
				this.prepareControlAlreadyLoanStatement();
				break;
			case "Control loan":
				this.prepareControlLoanStatement();
				break;
			case "Control already loan":
				this.prepareControlAlreadyLoanStatement();
				break;
			}
		}
		return statement;
	}

	private void prepareControlAlreadyLoanStatement() throws SQLException {
		statement.setString(1, this.loan.getUser().getFiscalCode());
		statement.setString(2, this.loan.getBook().getIsbn());
	}

	private void prepareControlLoanStatement() throws SQLException {
		statement.setString(1, this.loan.getUser().getFiscalCode());
		statement.setString(2, this.loan.getBook().getIsbn());
		statement.setDate(3, new Date(this.loan.getLoanDate().getTimeInMillis()));
	}

	private void prepareBackBookStatement() throws SQLException {
		statement.setDate(1, new Date(this.loan.getReturnDate().getTimeInMillis()));
		statement.setString(2, this.loan.getUser().getFiscalCode());
		statement.setString(3, this.loan.getBook().getIsbn());
	}

	private void prepareAddStatement() throws SQLException {
		statement.setString(1, this.loan.getBook().getIsbn());
		statement.setString(2, this.loan.getUser().getFiscalCode());
		statement.setDate(3, new Date(this.loan.getLoanDate().getTimeInMillis()));
		statement.setDate(4, new Date(this.loan.getReturnDate().getTimeInMillis()));
		statement.setBoolean(5, this.loan.isReturned());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see database.PreparableQuery#close()
	 */
	@Override
	public void close() throws SQLException {
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
	 * @return the loan
	 */
	public Loan getLoan() {
		return loan;
	}

	/**
	 * @param loan
	 *            the loan to set
	 */
	public void setLoan(Loan loan) {
		this.loan = loan;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LoanQuery [sqlCode=" + sqlCode + ", operation=" + operation + ", statement=" + statement + ", loan="
				+ loan + "]";
	}
}
