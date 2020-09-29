package tableModels;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.table.DefaultTableModel;

import dataModel.Book;
import dataModel.Booking;
import dataModel.Category;
import dataModel.User;

/**
 * This class is a definition of DefaultTableModel that store in the cell all
 * the information of a user booking. The rows shown by this model are 25 per
 * pages.
 * 
 * @author Marco Di Capua
 * @author Mattia Lo Schiavo
 * @author Filippo Pelosi
 * @author Riccardo Zorzi
 * @version 1.0
 */
public class BookingTableModel extends DefaultTableModel {
	private static final long serialVersionUID = 1L;
	private static final String[] HEADER = { "Titolo", "Autori", "ISBN", "Categoria", "Data prenotazione" };
	private ArrayList<Booking> bookings;

	/**
	 * Create a new object <code>BookingTableModel</code> with the informations
	 * contains in the bookings list
	 * 
	 * @param bookings
	 *            the list contains the bookings to be shown
	 */
	public BookingTableModel(ArrayList<Booking> bookings) {
		super();
		this.bookings = bookings;
	}

	/**
	 * Add a booking to the model
	 * 
	 * @param booking
	 *            the booking to add
	 */
	public void addBooking(Booking booking) {
		this.bookings.add(booking);
		int row = bookings.size() - 1;
		fireTableRowsInserted(row, row);
	}

	/**
	 * Create and add to the model a new booking with the given user and book, and
	 * the current date
	 * 
	 * @param book
	 *            the book to booking
	 * @param user
	 *            the user that book the book
	 */
	public void addBooking(Book book, User user) {
		Booking booking = new Booking(book, user, new GregorianCalendar());
		this.bookings.add(booking);
		int row = bookings.size() - 1;
		fireTableRowsInserted(row, row);
	}

	/**
	 * Remove the booking at the given row from the model
	 * 
	 * @param row
	 *            the row to remove
	 */
	public void removeRow(int row) {
		this.bookings.remove(row);
		fireTableRowsDeleted(row, row);
	}

	/**
	 * Return the booking at the given row, if exists
	 * 
	 * @param row
	 *            the row whose booking is to be queried
	 * @return the booking at the given row
	 */
	public Booking getBookingAtRow(int row) {
		return bookings.get(row);
	}

	/**
	 * Returns the number of columns in this data table
	 * 
	 * @return the number of columns in the model
	 */
	@Override
	public int getColumnCount() {
		return HEADER.length;
	}

	/**
	 * Returns the number of rows in this data table.
	 * 
	 * @return the number of rows in the model
	 */
	@Override
	public int getRowCount() {
		if (this.bookings == null) {
			return 0;
		} else {
			return bookings.size();
		}
	}

	/**
	 * Returns an attribute value for the cell at row and column.
	 * 
	 * @param row
	 *            the row whose value is to be queried
	 * @param column
	 *            the column whose value is to be queried
	 * @return the value Object at the specified cell
	 */
	@Override
	public Object getValueAt(int row, int column) {
		Booking booking = bookings.get(row);
		switch (column) {
		case 0:
			return booking.getBook().getTitle();
		case 1:
			return booking.getBook().getAuthors();
		case 2:
			return booking.getBook().getIsbn();
		case 3:
			return booking.getBook().getCategory();
		case 4:
			return booking.getBookingDate().getTime();
		}

		return null;
	}

	/**
	 * the class of the given column, or Object.class if the column doesn't exist
	 * 
	 * @param columnIndex
	 *            the column index
	 * @return the column class
	 */
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return String.class;
		case 1:
			return String.class;
		case 2:
			return String.class;
		case 3:
			return Category.class;
		case 4:
			return Date.class;
		}
		return Object.class;
	}

	/**
	 * Returns the column name
	 * 
	 * @param column
	 *            the column being queried
	 * @return column name or null if the column doesn't exist
	 */
	@Override
	public String getColumnName(int column) {
		if (column > -1 && column < HEADER.length) {
			return HEADER[column];
		} else {
			return null;
		}
	}

	/**
	 * Returns false regardless of parameter values.
	 * 
	 * @param rowIndex
	 *            the row whose value is to be queried
	 * @param columnIndex
	 *            the column whose value is to be queried
	 * @return false
	 */
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

}
