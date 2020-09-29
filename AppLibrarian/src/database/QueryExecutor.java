package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class provides methods to execute SQL queries
 *
 * @author Marco Di Capua
 * @author Mattia Lo Schiavo
 * @author Filippo Pelosi
 * @author Riccardo Zorzi
 * @version 1.0
 */
public class QueryExecutor {
	private Connection connection;

	/**
	 * Create a new QueryExecutor object, that can execute queries against a
	 * database with the informations in input
	 * 
	 * @param url
	 *            the database URL
	 * @param usr
	 *            the database user
	 * @param pwd
	 *            the database password
	 * @throws ClassNotFoundException
	 *             if the Driver class could not be found
	 * @throws SQLException
	 *             if an SQL occurs while connecting to the database
	 */
	public QueryExecutor(String url, String usr, String pwd) throws SQLException, ClassNotFoundException {
		Class.forName("org.postgresql.Driver");
		this.connection = DriverManager.getConnection(url, usr, pwd);
	}

	/**
	 * @return the database connection
	 */
	public Connection getConnection() {
		return connection;
	}

	/**
	 * Execute the query in the given PreparableQuery against the database
	 * 
	 * @param query
	 *            the PreparableQuery to execute
	 * @return a ResultSet with the query result
	 * @throws SQLException
	 *             if an SQL error occurs while executing the query
	 */
	public ResultSet executeQuery(PreparableQuery query) throws SQLException {
		return query.prepareStatement(connection).executeQuery();
	}

	/**
	 * Execute the update in the given PreparableQuery against the database
	 * 
	 * @param update
	 *            the PreparableQuery to execute
	 * @throws SQLException
	 *             if an SQL error occurs while executing the query
	 */
	public void executeUpdate(PreparableQuery update) throws SQLException {
		update.prepareStatement(connection).executeUpdate();
	}

	/**
	 * Execute the delete in the given PreparableQuery against the database. If an
	 * SQL exception occurs while executing the query all the changes made will be
	 * undoes
	 * 
	 * @param delete
	 *            the PreparableQuery to execute
	 * @return true if the operation completed successfully, false otherwise
	 */
	public boolean executeDelete(PreparableQuery delete) {
		try {
			connection.setAutoCommit(false);
			delete.prepareStatement(connection).executeUpdate();
			return true;
		} catch (SQLException e) {
			QueryExecutor.printSQLException(e);
			try {
				System.err.print("Transaction roll back");
				connection.rollback();
			} catch (SQLException ex) {
			}
		} finally {
			try {
				connection.commit();
				connection.setAutoCommit(true);
			} catch (SQLException e) {
			}
		}
		return false;
	}

	/**
	 * Prints the main informations of the given SQL exception
	 * 
	 * @param ex
	 *            the SQLException
	 */
	public static void printSQLException(SQLException ex) {
		System.err.println("SQLState:" + ex.getSQLState());
		System.err.println("Error code:" + ex.getErrorCode());
		System.err.println("Message:" + ex.getMessage());
	}

}
