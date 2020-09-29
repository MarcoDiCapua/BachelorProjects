package database;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import dataModel.Category;
import utils.Operation;

/**
 * This class provides methods to prepare queries about library operations
 *
 * @author Marco Di Capua
 * @author Mattia Lo Schiavo
 * @author Filippo Pelosi
 * @author Riccardo Zorzi
 * @version 1.0
 */
public class LibraryQuery implements PreparableQuery {
	// camps
	private String sqlCode;
	private String operation;
	private PreparedStatement statement;
	private String title;
	private String author;
	private Category category;
	private String userFC;
	private String parameter;
	private int offeset;

	/**
	 * Create a new LibraryQuery object with the informations given
	 * 
	 * @param sqlCode
	 *            the SQL code to execute
	 * @param operation
	 *            the library operation to execute
	 * @param offeset
	 *            the offset of the query
	 * @param title
	 *            the title necessary to prepare the query
	 * @param author
	 *            the author necessary to prepare the query
	 * @param category
	 *            the category necessary to prepare the query
	 * @param userFC
	 *            the user fiscal code necessary to prepare the query
	 */
	public LibraryQuery(String sqlCode, String operation, int offeset, String title, String author, Category category,
			String userFC) {
		super();
		this.sqlCode = sqlCode;
		this.operation = operation;
		this.statement = null;
		this.offeset = offeset;
		this.title = title;
		this.author = author;
		this.category = category;
		this.userFC = userFC;
	}

	/**
	 * Create a new LibraryQuery object with the informations given
	 * 
	 * @param sqlCode
	 *            the SQL code to execute
	 * @param operation
	 *            the library operation to execute
	 * @param offeset
	 *            the offset of the query
	 * @param parameter
	 *            the parameter that contains the information necessary to prepare
	 *            the query
	 */
	public LibraryQuery(String sqlCode, String operation, int offeset, String parameter) {
		super();
		this.sqlCode = sqlCode;
		this.operation = operation;
		this.offeset = offeset;
		this.statement = null;
		this.parameter = parameter;
	}

	/**
	 * Prepare the LibraryQuery object with the object informations according to the
	 * operation to execute
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
			case Operation.LIBRARY_BOOKS_FILTER:
				this.prepareSelectBooksStatement();
				break;
			case Operation.LIBRARY_BOOKINGS_FILTER:
				this.prepareSelectBooksStatement();
				break;
			case Operation.LIBRARY_LOANS_FILTER:
				this.prepareSelectBooksStatement();
				break;
			case Operation.LIBRARY_OVERWHELMING_LOANS_FILTER:
				this.prepareSelectBooksStatement();
				break;
			case Operation.LIBRARY_HISTORY_LOANS_USER_FILTER:
				this.prepareSelectHistoryLoansUserFiterStatement();
				break;
			case "Select book from isbn":
				this.prepareSetParameterStatment();
				break;
			case "Select user from fiscal code":
				this.prepareSetParameterStatment();
				break;
			case "Select authors for book":
				this.prepareSetParameterStatment();
				break;
			case Operation.LIBRARY_CATEGORY_RANK:
				this.prepareSetParameterAndOffsetStatment();
				break;
			case Operation.LIBRARY_CLASSIFICATION_RANK:
				this.prepareSetParameterAndOffsetStatment();
				break;
			case Operation.LIBRARY_UPDATE_BOOKS:
				this.prepareOffsetStatment();
				break;
			case Operation.LIBRARY_UPDATE_BOOKINGS:
				this.prepareOffsetStatment();
				break;
			case Operation.LIBRARY_UPDATE_LOANS:
				this.prepareOffsetStatment();
				break;
			case Operation.LIBRARY_ABSOLUTE_RANK:
				this.prepareOffsetStatment();
				break;
			case Operation.LIBRARY_ALL_OVERWHELMING_LOANS:
				this.prepareOffsetStatment();
				break;
			case "Count filter books":
				prepareCountFiterStatement();
				break;
			case "Count history user books":
				prepareCountHistoryUserFiterStatement();
				break;
			case "Set parameter":
				prepareSetParameterStatment();
				break;
			}
		}
		return statement;
	}

	private void prepareCountHistoryUserFiterStatement() throws SQLException {
		statement.setString(1, this.userFC);
		statement.setString(2, "%" + this.title.toLowerCase() + "%");
		String selectedCategory = this.category == null ? "%" : this.category.name();
		statement.setString(3, selectedCategory);
		statement.setString(4, "%" + this.author + "%");
		statement.setString(5, "%" + this.author + "%");
	}

	private void prepareCountFiterStatement() throws SQLException {
		statement.setString(1, "%" + this.title.toLowerCase() + "%");
		String selectedCategory = this.category == null ? "%" : this.category.name();
		statement.setString(2, selectedCategory);
		statement.setString(3, "%" + this.author + "%");
		statement.setString(4, "%" + this.author + "%");
	}

	private void prepareOffsetStatment() throws NumberFormatException, SQLException {
		statement.setBigDecimal(1, new BigDecimal(25 * (this.offeset - 1)));
	}

	private void prepareSelectHistoryLoansUserFiterStatement() throws SQLException {
		statement.setString(1, this.userFC);
		statement.setString(2, "%" + this.title.toLowerCase() + "%");
		String selectedCategory = this.category == null ? "%" : this.category.name();
		statement.setString(3, selectedCategory);
		statement.setString(4, "%" + this.author + "%");
		statement.setString(5, "%" + this.author + "%");
		statement.setBigDecimal(6, new BigDecimal(25 * (this.offeset - 1)));
	}

	private void prepareSelectBooksStatement() throws SQLException {
		statement.setString(1, "%" + this.title.toLowerCase() + "%");
		String selectedCategory = this.category == null ? "%" : this.category.name();
		statement.setString(2, selectedCategory);
		statement.setString(3, "%" + this.author + "%");
		statement.setString(4, "%" + this.author + "%");
		statement.setBigDecimal(5, new BigDecimal(25 * (this.offeset - 1)));
	}

	private void prepareSetParameterAndOffsetStatment() throws SQLException {
		statement.setString(1, this.parameter);
		statement.setBigDecimal(2, new BigDecimal(25 * (this.offeset - 1)));
	}

	private void prepareSetParameterStatment() throws SQLException {
		statement.setString(1, this.parameter);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see PreparableQuery#close()
	 */
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
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * @param author
	 *            the author to set
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * @return the category
	 */
	public Category getCategory() {
		return category;
	}

	/**
	 * @param category
	 *            the category to set
	 */
	public void setCategory(Category category) {
		this.category = category;
	}

	/**
	 * @return the parameter
	 */
	public String getParameter() {
		return parameter;
	}

	/**
	 * @param parameter
	 *            the parameter to set
	 */
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	/**
	 * @return the userFC
	 */
	public String getUserFC() {
		return userFC;
	}

	/**
	 * @param userFC
	 *            the userFC to set
	 */
	public void setUserFC(String userFC) {
		this.userFC = userFC;
	}

	/**
	 * @return the offeset
	 */
	public int getOffeset() {
		return offeset;
	}

	/**
	 * @param offeset
	 *            the offeset to set
	 */
	public void setOffeset(int offeset) {
		this.offeset = offeset;
	}

}
