package tableModels;

import java.util.ArrayList;
import java.util.Date;

import javax.swing.table.DefaultTableModel;

import dataModel.Booking;
import dataModel.Category;

/**
 * This class is a definition of DefaultTableModel that store in the cell all
 * the information needed to delete a booking.
 * 
 * @author Marco Di Capua
 * @author Mattia Lo Schiavo
 * @author Filippo Pelosi
 * @author Riccardo Zorzi
 * @version 1.0
 */
public class DeleteBookingTableModel extends DefaultTableModel {
	private static final long serialVersionUID = 1L;
	private static final String[] HEADER = { "Elimina", "Titolo", "Autori", "ISBN", "Categoria", "Data prenotazione" };
	private ArrayList<Booking> bookingsList;
	private Object[][] viewBookings;

	/**
	 * Create a new object <code>DeleteBookingTableModel</code> with the
	 * informations contains in the bookings list
	 * 
	 * @param bookingsList
	 *            the list contains the bookings to be shown
	 */
	public DeleteBookingTableModel(ArrayList<Booking> bookingsList) {
		super();
		this.bookingsList = bookingsList;
		this.viewBookings = new Object[bookingsList.size()][6];
		for (int i = 0; i < bookingsList.size(); i++) {
			viewBookings[i][0] = Boolean.FALSE;
			viewBookings[i][1] = bookingsList.get(i).getBook().getTitle();
			viewBookings[i][2] = bookingsList.get(i).getBook().getAuthors();
			viewBookings[i][3] = bookingsList.get(i).getBook().getIsbn();
			viewBookings[i][4] = bookingsList.get(i).getBook().getCategory();
			viewBookings[i][5] = bookingsList.get(i).getBookingDate();
		}
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
		if (this.viewBookings == null) {
			return 0;
		} else {
			return viewBookings.length;
		}
	}

	/**
	 * Return the booking at the given row, if exists
	 * 
	 * @param row
	 *            the row whose booking is to be queried
	 * @return the booking at the given row
	 */
	public Booking getBookingAtRow(int row) {
		if (row > -1 && row < viewBookings.length) {
			return this.bookingsList.get(row);
		} else {
			return null;
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
		Booking booking = this.bookingsList.get(row);
		switch (column) {
		case 0:
			return viewBookings[row][0];
		case 1:
			return booking.getBook().getTitle();
		case 2:
			return booking.getBook().getAuthors();
		case 3:
			return booking.getBook().getIsbn();
		case 4:
			return booking.getBook().getCategory();
		case 5:
			return booking.getBookingDate().getTime();
		}

		return null;
	}

	/**
	 * Sets the object value for the cell at column and row. Value is the new value.
	 * This method will generate a tableChanged notification.
	 * 
	 * @param value
	 *            the new value
	 * @param row
	 *            the row whose value is to be changed
	 * @param column
	 *            the column whose value is to be changed
	 */
	@Override
	public void setValueAt(Object value, int row, int column) {
		Booking booking = this.bookingsList.get(row);
		switch (column) {
		case 0:
			viewBookings[row][0] = value;
		case 1:
			viewBookings[row][1] = booking.getBook().getTitle();
		case 2:
			viewBookings[row][2] = booking.getBook().getAuthors();
		case 3:
			viewBookings[row][3] = booking.getBook().getIsbn();
		case 4:
			viewBookings[row][4] = booking.getBook().getCategory();
		case 5:
			viewBookings[row][5] = booking.getBookingDate().getTime();
		}
		fireTableRowsUpdated(row, row);
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
			return Boolean.class;
		case 1:
			return String.class;
		case 2:
			return String.class;
		case 3:
			return String.class;
		case 4:
			return Category.class;
		case 5:
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
	 * Returns true for all the cells at the first column, false otherwise.
	 * 
	 * @param rowIndex
	 *            the row whose value is to be queried
	 * @param columnIndex
	 *            the column whose value is to be queried
	 * @return true if column is equal to 0, false otherwise
	 */
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return columnIndex == 0;
	}

}
