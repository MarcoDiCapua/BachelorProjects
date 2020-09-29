package tableModels;

import java.util.ArrayList;
import java.util.Date;

import javax.swing.table.DefaultTableModel;

import dataModel.Booking;
import dataModel.Category;

/**
 * This class is a definition of DefaultTableModel that store in the cell all
 * the information needed to delivery a book.
 * 
 * @author Marco Di Capua
 * @author Mattia Lo Schiavo
 * @author Filippo Pelosi
 * @author Riccardo Zorzi
 * @version 1.0
 */
public class BookDeliveryTableModel extends DefaultTableModel {
	private static final long serialVersionUID = 1L;
	private static final String[] HEADER = { "Consegna", "Titolo", "Autori", "ISBN", "Categoria", "Data prenotazione" };
	private ArrayList<Booking> bookings;
	private Object[][] bookingsView;

	/**
	 * Create a new object <code>BookDeliveryTableModel</code> with the informations
	 * contains in the bookings list
	 * 
	 * @param bookings
	 *            the list contains the bookings to be shown
	 */
	public BookDeliveryTableModel(ArrayList<Booking> bookings) {
		super();
		this.bookings = bookings;
		this.bookingsView = new Object[bookings.size()][6];
		for (int i = 0; i < bookings.size(); i++) {
			bookingsView[i][0] = Boolean.FALSE;
			bookingsView[i][1] = bookings.get(i).getBook().getTitle();
			bookingsView[i][2] = bookings.get(i).getBook().getAuthors();
			bookingsView[i][3] = bookings.get(i).getBook().getIsbn();
			bookingsView[i][4] = bookings.get(i).getBook().getCategory();
			bookingsView[i][5] = bookings.get(i).getBookingDate().getTime();
		}
	}

	/**
	 * Return the booking at the given row, if exists
	 * 
	 * @param row
	 *            the row whose book is to be queried
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
		if (row > -1 && column > -1 && row < bookingsView.length && column < bookingsView[0].length) {
			return bookingsView[row][column];
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
		Booking booking = this.bookings.get(row);
		switch (column) {
		case 0:
			bookingsView[row][0] = value;
		case 1:
			bookingsView[row][1] = booking.getBook().getTitle();
		case 2:
			bookingsView[row][2] = booking.getBook().getAuthors();
		case 3:
			bookingsView[row][3] = booking.getBook().getIsbn();
		case 4:
			bookingsView[row][4] = booking.getBook().getCategory();
		case 5:
			bookingsView[row][5] = booking.getBookingDate().getTime();
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
