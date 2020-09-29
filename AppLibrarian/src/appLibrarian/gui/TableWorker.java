package appLibrarian.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import appLibrarian.Library;
import appLibrarian.Proxy;
import dataModel.Book;
import dataModel.Booking;
import dataModel.Category;
import dataModel.Classification;
import dataModel.Loan;
import dataModel.Login;
import tableModels.AllBookingsTableModel;
import tableModels.AllBooksTableModel;
import tableModels.AllLoansTableModel;
import tableModels.DeleteBookTableModel;
import tableModels.LoanBookTableModel;
import tableModels.LoanTableModel;
import tableModels.RankTableModel;
import utils.Operation;

/**
 * * This class is a definition of SwingWorker used to communicate with the
 * server and shown the request data in the JTable
 *
 * @author Marco Di Capua
 * @author Mattia Lo Schiavo
 * @author Filippo Pelosi
 * @author Riccardo Zorzi
 * @version 1.0
 */
@SuppressWarnings("unchecked")
public class TableWorker extends SwingWorker<ArrayList<?>, Void> {
	private JTable tableToUpdate;
	private JTable tableSelected;
	private String operation;
	private JComponent[] component;
	private JPanel panelPages;
	private String[] params;
	private int page;

	/**
	 *
	 * Create a TableWorker with the information in input
	 * 
	 * @param operation
	 *            the operation request
	 * @param page
	 *            the page number requested
	 * @param params
	 *            the parameters of the research
	 * @param tableToUpdate
	 *            the table to update with the new model
	 * @param tableSelected
	 *            the table with the already selected rows
	 * @param panelPages
	 *            the panel where insert the pages numbers
	 * @param components
	 *            the components to disable while processing the request
	 */
	public TableWorker(String operation, int page, String[] params, JTable tableToUpdate, JTable tableSelected,
			JPanel panelPages, JComponent... components) {
		super();
		this.tableToUpdate = tableToUpdate;
		this.tableSelected = tableSelected;
		this.panelPages = panelPages;
		this.operation = operation;
		this.params = params;
		this.page = page;
		this.component = new JComponent[components.length];
		tableToUpdate.setEnabled(false);
		if (tableSelected != null) {
			tableSelected.setEnabled(false);
		}
		int k = 0;
		for (JComponent jComponent : components) {
			component[k] = jComponent;
			jComponent.setEnabled(false);
			k++;
		}
	}

	/**
	 * Process the request
	 */
	@Override
	protected ArrayList<?> doInBackground() throws Exception {
		Category category = null;
		if (params != null && params.length > 2 && params[2] != null) {
			category = Category.valueOf(params[2]);
		}
		HashMap<String, Object> result = null;
		switch (this.operation) {
		case Operation.LIBRARY_ABSOLUTE_RANK:
			result = Library.instance().absoluteRank(page);
			this.page = (int) result.get("Pages");
			return (ArrayList<?>) result.get("List");
		case Operation.LIBRARY_ALL_OVERWHELMING_LOANS:
			result = Library.instance().getOverwhelmingLoanBooks(page);
			this.page = (int) result.get("Pages");
			return (ArrayList<?>) result.get("List");
		case Operation.LIBRARY_BOOKINGS_FILTER:
			result = Library.instance().selectBookingsFrom(page, params[0], params[1], category);
			this.page = (int) result.get("Pages");
			return (ArrayList<?>) result.get("List");
		case Operation.LIBRARY_BOOKS_FILTER:
			result = Library.instance().selectBooksFrom(page, params[0], params[1], category);
			this.page = (int) result.get("Pages");
			return (ArrayList<?>) result.get("List");
		case Operation.LIBRARY_CATEGORY_RANK:
			Category cat = null;
			if (params != null && params[0] != null) {
				cat = Category.valueOf(params[0]);
			}
			result = Library.instance().categoryRank(page, cat);
			this.page = (int) result.get("Pages");
			return (ArrayList<?>) result.get("List");
		case Operation.LIBRARY_CLASSIFICATION_RANK:
			Classification classification = null;
			if (params != null && params[0] != null) {
				classification = Classification.valueOf(params[0]);
			}
			result = Library.instance().classificationRank(page, classification);
			this.page = (int) result.get("Pages");
			return (ArrayList<?>) result.get("List");
		case Operation.LIBRARY_HISTORY_LOANS_USER_FILTER:
			result = Proxy.instance().selectHistoryLoansUserFrom(page, params[0], params[1], category,
					Login.getUserReaderLogged());
			this.page = (int) result.get("Pages");
			return (ArrayList<?>) result.get("List");
		case Operation.LIBRARY_LOANS_FILTER:
			result = Library.instance().selectLoansFrom(page, params[0], params[1], category);
			this.page = (int) result.get("Pages");
			return (ArrayList<?>) result.get("List");
		case Operation.LIBRARY_OVERWHELMING_LOANS_FILTER:
			result = Library.instance().selectOverwhelmingLoansFrom(page, params[0], params[1], category);
			this.page = (int) result.get("Pages");
			return (ArrayList<?>) result.get("List");
		case Operation.LIBRARY_UPDATE_BOOKS:
			this.page = Library.instance().updateBooks(page);
			return Library.instance().getBooks();
		case Operation.LIBRARY_UPDATE_BOOKINGS:
			this.page = Library.instance().updateBookings(page);
			return Library.instance().getBookingBooks();
		case Operation.LIBRARY_UPDATE_LOANS:
			this.page = Library.instance().updateLoans(page);
			return Library.instance().getLoanBooks();
		case Operation.LOAN_HISTORY_LOANS_USER:
			result = Proxy.instance().selectHistoryLoans(page);
			this.page = (int) result.get("Pages");
			return (ArrayList<?>) result.get("List");
		}
		return new ArrayList<Object>();
	}

	/**
	 * Update the interface with the result of the request
	 */
	@Override
	protected void done() {
		try {
			if (!isCancelled()) {
				ArrayList<?> a = get();
				if (a != null) {
					DefaultTableModel tm = new DefaultTableModel();
					if (tableToUpdate.getModel() instanceof RankTableModel) {
						tm = new RankTableModel((ArrayList<Book>) a);
					} else if (tableToUpdate.getModel() instanceof AllBookingsTableModel) {
						tm = new AllBookingsTableModel((ArrayList<Booking>) a);
					} else if (tableToUpdate.getModel() instanceof AllBooksTableModel) {
						tm = new AllBooksTableModel((ArrayList<Book>) a);
					} else if (tableToUpdate.getModel() instanceof AllLoansTableModel) {
						tm = new AllLoansTableModel((ArrayList<Loan>) a);
					} else if (tableToUpdate.getModel() instanceof DeleteBookTableModel) {
						tm = new DeleteBookTableModel((ArrayList<Book>) a);
						for (int i = 0; i < tableSelected.getRowCount(); i++) {
							for (int j = 0; j < tm.getRowCount(); j++) {
								if (tableSelected.getValueAt(i, 2).equals(tm.getValueAt(j, 3))) {
									((DeleteBookTableModel) tm).selectRow(j);
									break;
								}
							}
						}
					} else if (tableToUpdate.getModel() instanceof LoanBookTableModel) {
						tm = new LoanBookTableModel((ArrayList<Book>) a);
						for (int i = 0; i < tableSelected.getRowCount(); i++) {
							for (int j = 0; j < tm.getRowCount(); j++) {
								if (tableSelected.getValueAt(i, 2).equals(tm.getValueAt(j, 3))) {
									((LoanBookTableModel) tm).selectRow(j);
								}
							}
						}
					} else if (tableToUpdate.getModel() instanceof LoanTableModel) {
						tm = new LoanTableModel((ArrayList<Loan>) a);
					}
					tableToUpdate.setModel(tm);
					tableToUpdate.setRowSorter(new TableRowSorter<DefaultTableModel>(tm));
				}

				tableToUpdate.setEnabled(true);
				if (tableSelected != null) {
					tableSelected.setEnabled(true);
				}

				paintLabelPagesNumeber(this.panelPages);

				for (JComponent jComponent : this.component) {
					jComponent.setEnabled(true);
				}
			}
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}

	private void paintLabelPagesNumeber(JPanel panelPages) {
		panelPages.removeAll();
		int k = 1;
		TableWorker worker = this;
		while (this.page > 0) {
			JLabel lbl = new JLabel("" + k);
			lbl.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			lbl.setFont(new Font("Tahoma", Font.BOLD, 16));
			panelPages.add(lbl);
			k++;
			page--;
		}
		for (Component jComponent : panelPages.getComponents()) {
			JLabel lbl = (JLabel) jComponent;
			lbl.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseEntered(MouseEvent arg0) {
					lbl.setForeground(Color.BLUE);
				}

				@Override
				public void mouseExited(MouseEvent e) {
					lbl.setForeground(Color.BLACK);
				}

				@Override
				public void mouseClicked(MouseEvent arg0) {
					JComponent[] components = new JComponent[worker.getComponent().length
							+ panelPages.getComponents().length];
					int i = 0;
					for (JComponent comp : worker.getComponent()) {
						components[i] = comp;
						i++;
					}
					for (Component cmp : panelPages.getComponents()) {
						components[i] = (JComponent) cmp;
						i++;
					}
					TableWorker tableWorker = new TableWorker(worker.getOperation(), Integer.parseInt(lbl.getText()),
							worker.getParams(), worker.getTableToUpdate(), worker.getTableSelected(),
							worker.getPanelPages(), components);
					tableWorker.execute();
				}
			});
		}
		panelPages.repaint();
		panelPages.revalidate();
	}

	/**
	 * @return the tableToUpdate
	 */
	public JTable getTableToUpdate() {
		return tableToUpdate;
	}

	/**
	 * @param tableToUpdate
	 *            the tableToUpdate to set
	 */
	public void setTableToUpdate(JTable tableToUpdate) {
		this.tableToUpdate = tableToUpdate;
	}

	/**
	 * @return the tableSelected
	 */
	public JTable getTableSelected() {
		return tableSelected;
	}

	/**
	 * @param tableSelected
	 *            the tableSelected to set
	 */
	public void setTableSelected(JTable tableSelected) {
		this.tableSelected = tableSelected;
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
	 * @return the component
	 */
	public JComponent[] getComponent() {
		return component;
	}

	/**
	 * @param component
	 *            the component to set
	 */
	public void setComponent(JComponent[] component) {
		this.component = component;
	}

	/**
	 * @return the panelPages
	 */
	public JPanel getPanelPages() {
		return panelPages;
	}

	/**
	 * @param panelPages
	 *            the panelPages to set
	 */
	public void setPanelPages(JPanel panelPages) {
		this.panelPages = panelPages;
	}

	/**
	 * @return the params
	 */
	public String[] getParams() {
		return params;
	}

	/**
	 * @param params
	 *            the params to set
	 */
	public void setParams(String[] params) {
		this.params = params;
	}

	/**
	 * @return the page
	 */
	public int getPage() {
		return page;
	}

	/**
	 * @param page
	 *            the page to set
	 */
	public void setPage(int page) {
		this.page = page;
	}

}
