package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * This interface imposes methods necessary to prepare and close a query
 *
 * @author Marco Di Capua
 * @author Mattia Lo Schiavo
 * @author Filippo Pelosi
 * @author Riccardo Zorzi
 * @version 1.0
 */
public interface PreparableQuery {
	/**
	 * Prepares the query with all the informations needed
	 * 
	 * @param connection
	 *            the connection where the query will be execute
	 * @return a PreparedStatement ready to be execute
	 * @throws SQLException
	 *             if an SQL error occurs while preparing the query
	 */
	public PreparedStatement prepareStatement(Connection connection) throws SQLException;

	/**
	 * Closes the query
	 * 
	 * @throws SQLException
	 *             if an SQL error occurs while closing the query
	 */
	public void close() throws SQLException;
}
