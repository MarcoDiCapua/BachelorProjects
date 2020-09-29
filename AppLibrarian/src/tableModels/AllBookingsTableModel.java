package tableModels;

import java.util.ArrayList;
import java.util.Date;

import javax.swing.table.DefaultTableModel;

import dataModel.Booking;
import dataModel.Category;

/**
 * This class is a definition of DefaultTableModel that store in the cell all
 * bookings information. The rows shown by this model are 25 per pages.
 * 
 * @author Marco Di Capua
 * @author Mattia Lo Schiavo
 * @author Filippo Pelosi
 * @author Riccardo Zorzi
 * @version 1.0
 */
public class AllBookingsTableModel extends DefaultTableModel {
	private static final long serialVersionUID = 1L;
	private static final String[] HEADER = { "Cod. Fiscale", "Nome", "Cognome", "Titolo", "Autori", "ISBN", "Categoria",
			"Data prenotazione" };
	private ArrayList<Booking> bookings;

	/**
	 * Create a new object <code>AllBookingsTableModel</code> with the informations
	 * contains in the bookings list
	 * 
	 * @param bookings
	 *            the list contains the bookings to be shown
	 */
	public AllBookingsTableModel(ArrayList<Booking> bookings) {
		super();
		this.bookings = bookings;
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
			return booking.getUser().getFiscalCode();
		case 1:
			return booking.getUser().getName();
		case 2:
			return booking.getUser().getSurname();
		case 3:
			return booking.getBook().getTitle();
		case 4:
			return booking.getBook().getAuthors();
		case 5:
			return booking.getBook().getIsbn();
		case 6:
			return booking.getBook().getCategory();
		case 7:
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
			return String.class;
		case 4:
			return String.class;
		case 5:
			return String.class;
		case 6:
			return Category.class;
		case 7:
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
