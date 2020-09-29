package tableModels;

import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import dataModel.Book;
import dataModel.Category;

public class LoanBookTableModel extends DefaultTableModel {

	/**
	 * This is a definition of DefaultTableModel that store in the cell all the
	 * information needed to loan a book. The rows shown by this model are 25 per
	 * pages.
	 * 
	 * @author Marco Di Capua
	 * @author Mattia Lo Schiavo
	 * @author Filippo Pelosi
	 * @author Riccardo Zorzi
	 * @version 1.0
	 */
	private static final long serialVersionUID = 1L;

	private static final String[] HEADER = { "Prenota", "Titolo", "Autori", "ISBN", "Categoria", "Disponibilità",
			"Prenotazioni" };
	private ArrayList<Book> booksList;
	private Object[][] books;

	/**
	 * Create a new object <code>LoanBookTableModel</code> with the informations
	 * contains in the books list
	 * 
	 * @param booksList
	 *            the list contains the books to be shown
	 */
	public LoanBookTableModel(ArrayList<Book> booksList) {
		super();
		this.booksList = booksList;
		this.books = new Object[booksList.size()][7];
		for (int i = 0; i < booksList.size(); i++) {
			books[i][0] = Boolean.FALSE;
			books[i][1] = booksList.get(i).getTitle();
			books[i][2] = booksList.get(i).getAuthors();
			books[i][3] = booksList.get(i).getIsbn();
			books[i][4] = booksList.get(i).getCategory();
			books[i][5] = booksList.get(i).getBookings() == 0 ? "Sì" : "No";
			books[i][6] = booksList.get(i).getBookings();
		}
	}

	/**
	 * Set true the check box at the given row
	 * 
	 * @param row
	 *            the row to select
	 */
	public void selectRow(int row) {
		books[row][0] = Boolean.TRUE;
		fireTableRowsUpdated(row, row);
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
		if (this.books == null) {
			return 0;
		} else {
			return books.length;
		}
	}

	/**
	 * Return the book at the given row, if exists
	 * 
	 * @param row
	 *            the row whose book is to be queried
	 * @return the book at the given row
	 */
	public Book getBookAtRow(int row) {
		if (row > -1 && row < booksList.size()) {
			return this.booksList.get(row);
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
		Book book = this.booksList.get(row);
		switch (column) {
		case 0:
			return books[row][0];
		case 1:
			return book.getTitle();
		case 2:
			return book.getAuthors();
		case 3:
			return book.getIsbn();
		case 4:
			return book.getCategory();
		case 5:
			return book.getBookings() == 0 ? "Sì" : "No";
		case 6:
			return book.getBookings();
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
		Book book = this.booksList.get(row);
		switch (column) {
		case 0:
			books[row][0] = value;
		case 1:
			books[row][1] = book.getTitle();
		case 2:
			books[row][2] = book.getAuthors();
		case 3:
			books[row][3] = book.getIsbn();
		case 4:
			books[row][4] = book.getCategory();
		case 5:
			books[row][5] = book.getBookings() == 0 ? "Sì" : "No";
		case 6:
			books[row][6] = book.getBookings();
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
			return String.class;
		case 6:
			return String.class;

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
