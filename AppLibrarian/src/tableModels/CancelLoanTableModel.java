package tableModels;

import java.util.ArrayList;
import java.util.Date;

import javax.swing.table.DefaultTableModel;

import dataModel.Category;
import dataModel.Loan;

/**
 * This class is a definition of DefaultTableModel that store in the cell all
 * the information needed to cancel a loan.
 * 
 * @author Marco Di Capua
 * @author Mattia Lo Schiavo
 * @author Filippo Pelosi
 * @author Riccardo Zorzi
 * @version 1.0
 */
public class CancelLoanTableModel extends DefaultTableModel {
	private static final long serialVersionUID = 1L;
	private static final String[] HEADER = { "Cancella", "Titolo", "Autori", "ISBN", "Categoria", "Data prestito",
			"Data restituzione" };
	private ArrayList<Loan> loansList;
	private Object[][] viewLoans;

	/**
	 * Create a new object <code>CancelLoanTableModel</code> with the informations
	 * contains in the loans list
	 * 
	 * @param loansList
	 *            the list contains the loans to be shown
	 */
	public CancelLoanTableModel(ArrayList<Loan> loansList) {
		super();
		this.loansList = loansList;
		this.viewLoans = new Object[loansList.size()][7];
		for (int i = 0; i < loansList.size(); i++) {
			viewLoans[i][0] = Boolean.FALSE;
			viewLoans[i][1] = loansList.get(i).getBook().getTitle();
			viewLoans[i][2] = loansList.get(i).getBook().getAuthors();
			viewLoans[i][3] = loansList.get(i).getBook().getIsbn();
			viewLoans[i][4] = loansList.get(i).getBook().getCategory();
			viewLoans[i][5] = loansList.get(i).getLoanDate().getTime();
			viewLoans[i][6] = loansList.get(i).getReturnDate().getTime();
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
		if (this.loansList == null) {
			return 0;
		} else {
			return loansList.size();
		}
	}

	/**
	 * Return the loan at the given row, if exists
	 * 
	 * @param row
	 *            the row whose loan is to be queried
	 * @return the loan at the given row
	 */
	public Loan getLoanAtRow(int row) {
		if (row > -1 && row < loansList.size()) {
			return this.loansList.get(row);
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
		Loan loan = this.loansList.get(row);
		switch (column) {
		case 0:
			return viewLoans[row][0];
		case 1:
			return loan.getBook().getTitle();
		case 2:
			return loan.getBook().getAuthors();
		case 3:
			return loan.getBook().getIsbn();
		case 4:
			return loan.getBook().getCategory();
		case 5:
			return loan.getLoanDate().getTime();
		case 6:
			return loan.getReturnDate().getTime();
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
		Loan loan = this.loansList.get(row);
		switch (column) {
		case 0:
			viewLoans[row][0] = value;
		case 1:
			viewLoans[row][1] = loan.getBook().getTitle();
		case 2:
			viewLoans[row][2] = loan.getBook().getAuthors();
		case 3:
			viewLoans[row][3] = loan.getBook().getIsbn();
		case 4:
			viewLoans[row][4] = loan.getBook().getCategory();
		case 5:
			viewLoans[row][5] = loan.getLoanDate().getTime();
		case 6:
			viewLoans[row][6] = loan.getReturnDate().getTime();
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
		case 6:
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
