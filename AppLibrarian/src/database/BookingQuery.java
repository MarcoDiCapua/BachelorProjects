package database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import dataModel.Booking;
import utils.Operation;

/**
 * This class provides methods to prepare queries about bookings table
 *
 * @author Marco Di Capua
 * @author Mattia Lo Schiavo
 * @author Filippo Pelosi
 * @author Riccardo Zorzi
 * @version 1.0
 */
public class BookingQuery implements PreparableQuery {
	// camps
	private String sqlCode;
	private String operation;
	private PreparedStatement statement;
	private Booking booking;

	/**
	 * Create a new BookingQuery object with the informations given
	 * 
	 * @param sqlCode
	 *            the SQL code to execute
	 * @param operation
	 *            the booking operation to execute
	 * @param booking
	 *            the booking that contains the informations necessary to prepare
	 *            the query
	 */
	public BookingQuery(String sqlCode, String operation, Booking booking) {
		super();
		this.sqlCode = sqlCode;
		this.operation = operation;
		this.statement = null;
		this.booking = booking;
	}

	/**
	 * Prepare the BookingQuery object with the informations in the booking object
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
			case Operation.BOOKING_ADD:
				this.prepareAddStatement();
				break;
			case Operation.BOOKING_DELETE:
				this.prepareDeleteStatement();
				break;
			}
		}
		return statement;
	}

	private void prepareDeleteStatement() throws SQLException {
		statement.setString(1, this.booking.getUser().getFiscalCode());
		statement.setString(2, this.booking.getBook().getIsbn());
	}

	private void prepareAddStatement() throws SQLException {
		statement.setString(1, this.booking.getUser().getFiscalCode());
		statement.setString(2, this.booking.getBook().getIsbn());
		statement.setDate(3, new Date(this.booking.getBookingDate().getTimeInMillis()));
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
	 * @return the booking
	 */
	public Booking getBooking() {
		return booking;
	}

	/**
	 * @param booking
	 *            the booking to set
	 */
	public void setBooking(Booking booking) {
		this.booking = booking;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BookingQuery [sqlCode=" + sqlCode + ", operation=" + operation + ", statement=" + statement
				+ ", booking=" + booking + "]";
	}

}
