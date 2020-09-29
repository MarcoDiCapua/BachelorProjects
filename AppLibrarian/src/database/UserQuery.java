package database;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import dataModel.User;
import utils.Operation;

/**
 * This class provides methods to prepare queries about users table
 *
 * @author Marco Di Capua
 * @author Mattia Lo Schiavo
 * @author Filippo Pelosi
 * @author Riccardo Zorzi
 * @version 1.0
 */
public class UserQuery implements PreparableQuery {
	// camps
	private String sqlCode;
	private String operation;
	private PreparedStatement statement;
	private User user;
	private int offset;

	/**
	 * Create a new UserQuery object with the informations given
	 * 
	 * @param sqlCode
	 *            the SQL code to execute
	 * @param operation
	 *            the user operation to execute
	 * @param user
	 *            the user that contains the informations necessary to prepare the
	 *            query
	 * @param offset
	 *            the query offset
	 */
	public UserQuery(String sqlCode, String operation, User user, int offset) {
		super();
		this.sqlCode = sqlCode;
		this.operation = operation;
		this.statement = null;
		this.user = user;
		this.offset = offset;
	}

	/**
	 * Create a new UserQuery object with the informations given
	 * 
	 * @param sqlCode
	 *            the SQL code to execute
	 * @param operation
	 *            the user operation to execute
	 * @param user
	 *            the user that contains the informations necessary to prepare the
	 *            query
	 */
	public UserQuery(String sqlCode, String operation, User user) {
		super();
		this.sqlCode = sqlCode;
		this.operation = operation;
		this.statement = null;
		this.user = user;
	}

	/**
	 * Prepare the UserQuery object with the informations in the user object
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
			case Operation.USER_DELETE:
				this.prepareSetFiscalCodeStatement();
				break;
			case Operation.USER_REGISTRATION:
				this.prepareRegistrationStatement();
				break;
			case "Select user from fiscal code":
				this.prepareSetFiscalCodeStatement();
				break;
			case "Activate profile":
				this.prepareActivateProfileStatement();
				break;
			case Operation.USER_MODIFY_EMAIL:
				this.prepareModifyEmailStatement();
				break;
			case Operation.USER_MODIFY_CLASSIFICATION:
				this.prepareModifyClassificationStatement();
				break;
			case Operation.USER_MODIFY_TELEPHONE:
				this.prepareModifyTelephoneNumberStatement();
				break;
			case Operation.USER_MODIFY_PASSWORD:
				this.prepareModifyPasswordStatement();
				break;
			case "Verify activation":
				this.prepareSetFiscalCodeStatement();
				break;
			case "Get counter":
				this.prepareSetFiscalCodeStatement();
				break;
			case Operation.USER_UPDATE_COUNTER:
				this.prepareUpdateCounterStatement();
				break;
			case Operation.USER_ACTIVATE_PROFILE:
				this.prepareActivateProfileStatement();
				break;
			case "Select all users":
				break;
			case Operation.BOOKING_BOOKINGS_USER:
				this.prepareSetFiscalCodeStatement();
				break;
			case Operation.LOAN_LOANS_USER:
				this.prepareSetFiscalCodeStatement();
				break;
			case Operation.LOAN_HISTORY_LOANS_USER:
				this.prepareLoansHistoryStatement();
				break;
			case "Count user history loans":
				this.prepareSetFiscalCodeStatement();
				break;
			case Operation.USER_RESET_PASSWORD:
				this.prepareResetPasswordStatement();
				break;
			case "Select user overwhelming loans":
				this.prepareSetFiscalCodeStatement();
				break;
			case "Select user from FC":
				this.prepareSetFiscalCodeStatement();
				break;
			}
		}
		return statement;
	}

	private void prepareLoansHistoryStatement() throws SQLException {
		statement.setString(1, this.user.getFiscalCode());
		statement.setBigDecimal(2, new BigDecimal(25 * (this.offset - 1)));
	}

	private void prepareSetFiscalCodeStatement() throws SQLException {
		statement.setString(1, this.user.getFiscalCode());
	}

	private void prepareModifyPasswordStatement() throws SQLException {
		statement.setString(1, this.user.getPassword());
		statement.setString(2, this.user.getFiscalCode());
	}

	private void prepareModifyTelephoneNumberStatement() throws SQLException {
		statement.setString(1, this.user.getTelephoneNumber());
		statement.setString(2, this.user.getFiscalCode());
	}

	private void prepareUpdateCounterStatement() throws SQLException {
		statement.setInt(1, this.user.getCounter());
		statement.setString(2, this.user.getFiscalCode());
	}

	private void prepareModifyEmailStatement() throws SQLException {
		statement.setString(1, this.user.getEmail());
		statement.setString(2, this.user.getFiscalCode());
	}

	private void prepareActivateProfileStatement() throws SQLException {
		statement.setString(1, null);
		statement.setString(2, this.user.getFiscalCode());
	}

	private void prepareResetPasswordStatement() throws SQLException {
		statement.setString(1, this.user.getActivationCode());
		statement.setString(2, this.user.getPassword());
		statement.setString(3, this.user.getFiscalCode());
	}

	private void prepareModifyClassificationStatement() throws SQLException {
		statement.setString(1, this.user.getClassification().name());
		statement.setString(2, this.user.getYear_class());
		statement.setString(3, this.user.getFiscalCode());
	}

	private void prepareRegistrationStatement() throws SQLException {
		statement.setString(1, this.user.getName());
		statement.setString(2, this.user.getSurname());
		statement.setString(3, this.user.getFiscalCode());
		statement.setString(4, this.user.getTelephoneNumber());
		statement.setString(5, this.user.getEmail());
		statement.setString(6, this.user.getClassification().name());
		statement.setString(7, this.user.getYear_class());
		statement.setString(8, this.user.getActivationCode());
		statement.setString(9, this.user.getPassword());
		statement.setBoolean(10, this.user.isTemporaneyPassword());
		statement.setInt(11, 5);
	}

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

	/* 
	 * 
	 */
	@Override
	public String toString() {
		return "UserQuery [sqlCode=" + sqlCode + ", operation=" + operation + ", statement=" + statement + "]";
	}

}
