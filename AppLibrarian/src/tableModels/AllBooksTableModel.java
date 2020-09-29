package tableModels;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import dataModel.Book;
import dataModel.Category;

/**
 * This class is a definition of DefaultTableModel that store in the cell all
 * books information. The rows shown by this model are 25 per pages.
 * 
 * @author Marco Di Capua
 * @author Mattia Lo Schiavo
 * @author Filippo Pelosi
 * @author Riccardo Zorzi
 * @version 1.0
 */
public class AllBooksTableModel extends DefaultTableModel {
	private static final long serialVersionUID = 1L;
	private static final String[] HEADER = { "Titolo", "Autori", "ISBN", "Categoria", "Lingua", "Casa editrice",
			"Anno pubblicazione", "Anno ristampa", "Scaffale" };
	private ArrayList<Book> books;

	/**
	 * Create a new object <code>AlBooksTableModel</code> with the informations
	 * contains in the books list
	 * 
	 * @param books
	 *            the list contains the books to be shown
	 */
	public AllBooksTableModel(List<Book> books) {
		super();
		this.books = new ArrayList<Book>(books);

	}

	/**
	 * Add a book to the model
	 * 
	 * @param book
	 *            the book to add
	 */
	public void addBook(Book book) {
		this.books.add(book);
		int row = books.size() - 1;
		fireTableRowsInserted(row, row);
	}

	/**
	 * Remove the book at the given row from the model
	 * 
	 * @param row
	 *            the row to remove
	 */
	public void removeRow(int row) {
		this.books.remove(row);
		fireTableRowsDeleted(row, row);
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
			return this.books.size();
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
		if (row > -1 && row < books.size()) {
			return books.get(row);
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
		Book book = books.get(row);
		switch (column) {
		case 0:
			return book.getTitle();
		case 1:
			return book.getAuthors();
		case 2:
			return book.getIsbn();
		case 3:
			return book.getCategory();
		case 4:
			return book.getLanguage();
		case 5:
			return book.getPublishingHouse();
		case 6:
			return book.getPublicationYear();
		case 7:
			return book.getReprintYear();
		case 8:
			return book.getBookcase();
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
			return String.class;
		case 5:
			return String.class;
		case 6:
			return String.class;
		case 7:
			return String.class;
		case 8:
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
