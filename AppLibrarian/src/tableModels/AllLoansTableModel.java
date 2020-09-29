package tableModels;

import java.util.ArrayList;
import java.util.Date;

import javax.swing.table.DefaultTableModel;

import dataModel.Category;
import dataModel.Loan;

/**
 * This class is a definition of DefaultTableModel that store in the cell all
 * loans information. The rows shown by this model are 25 per pages.
 * 
 * @author Marco Di Capua
 * @author Mattia Lo Schiavo
 * @author Filippo Pelosi
 * @author Riccardo Zorzi
 * @version 1.0
 */
public class AllLoansTableModel extends DefaultTableModel {
	private static final long serialVersionUID = 1L;
	private static final String[] HEADER = { "Cod. Fiscale", "Nome", "Cognome", "Titolo", "Autori", "ISBN", "Categoria",
			"Data prestito", "Data restituzione" };
	private ArrayList<Loan> loans;

	/**
	 * Create a new object <code>AllLoansTableModel</code> with the informations
	 * contains in the loans list
	 * 
	 * @param loans
	 *            the list contains the loans to be shown
	 */
	public AllLoansTableModel(ArrayList<Loan> loans) {
		super();
		this.loans = loans;
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
		if (this.loans == null) {
			return 0;
		} else {
			return loans.size();
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
		Loan loan = loans.get(row);
		switch (column) {
		case 0:
			return loan.getUser().getFiscalCode();
		case 1:
			return loan.getUser().getName();
		case 2:
			return loan.getUser().getSurname();
		case 3:
			return loan.getBook().getTitle();
		case 4:
			return loan.getBook().getAuthors();
		case 5:
			return loan.getBook().getIsbn();
		case 6:
			return loan.getBook().getCategory();
		case 7:
			return loan.getLoanDate().getTime();
		case 8:
			return loan.getReturnDate().getTime();
		}

		return null;
	}

	/**
	 * the class of the given column, or Object.class if the column doesn't exist
	 * 
	 * @param columnIndex
	 *            the column index
	 * 
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
		case 8:
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
