package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import dataModel.Login;
import utils.Operation;

/**
 * This class provides methods to prepare queries about login operations
 *
 * @author Marco Di Capua
 * @author Mattia Lo Schiavo
 * @author Filippo Pelosi
 * @author Riccardo Zorzi
 * @version 1.0
 */
public class LoginQuery implements PreparableQuery {

	private String sqlCode;
	private PreparedStatement statement;
	private String operation;
	private Login login;

	/**
	 * Create a new LoginQuery object with the informations given
	 * 
	 * @param sqlCode
	 *            the SQL code to execute
	 * @param operation
	 *            the login operation to execute
	 * @param login
	 *            the Login object that contains the informations necessary to
	 *            prepare the query
	 */
	public LoginQuery(String sqlCode, String operation, Login login) {
		super();
		this.sqlCode = sqlCode;
		this.statement = null;
		this.operation = operation;
		this.login = login;
	}

	/**
	 * Prepare the LoginQuery object with the informations in the login object
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
			case Operation.LOGIN_USER:
				this.prepareLoginStatement();
				break;
			case Operation.LOGIN_FROM_LIBRARIAN:
				this.prepareLoginFromUserStatement();
				break;
			}
		}
		return statement;
	}

	private void prepareLoginFromUserStatement() throws SQLException {
		statement.setString(1, login.getUserId());
	}

	private void prepareLoginStatement() throws SQLException {
		statement.setString(1, login.getUserId());
		statement.setString(2, login.getPassword());
	}

	@Override
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
	 * @return the login
	 */
	public Login getLogin() {
		return login;
	}

	/**
	 * @param login
	 *            the login to set
	 */
	public void setLogin(Login login) {
		this.login = login;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LoginQuery [sqlCode=" + sqlCode + ", statement=" + statement + ", login=" + login + "]";
	}

}
