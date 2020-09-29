package appLibrarian.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import appLibrarian.Proxy;
import dataModel.Booking;
import dataModel.Loan;
import dataModel.Login;
import tableModels.CancelLoanTableModel;
import tableModels.LoanTableModel;
import utils.Directory;

/**
 * This class create the reader loaned books list panel
 *
 * @author Marco Di Capua
 * @author Mattia Lo Schiavo
 * @author Filippo Pelosi
 * @author Riccardo Zorzi
 * @version 1.0
 */
public class PanelReaderLoanedBooks extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTable tableBooks;
	private JTable tableLoansToCancel;

	/**
	 * Create the panel
	 */
	public PanelReaderLoanedBooks() {
		super();
		setBorder(null);
		setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		add(scrollPane, BorderLayout.CENTER);

		JPanel panel = new JPanel();
		panel.setBorder(null);
		scrollPane.setViewportView(panel);
		panel.setLayout(new BorderLayout(0, 0));

		JPanel panelSouth = new JPanel();
		panelSouth.setPreferredSize(new Dimension(10, 237));
		panel.add(panelSouth, BorderLayout.SOUTH);
		panelSouth.setLayout(new BorderLayout(0, 0));

		JPanel panelButtons = new JPanel();
		panelButtons.setPreferredSize(new Dimension(10, 100));
		FlowLayout flowLayout = (FlowLayout) panelButtons.getLayout();
		flowLayout.setHgap(400);
		panelSouth.add(panelButtons, BorderLayout.SOUTH);

		JButton btnMenu = new JButton("Menu");
		panelButtons.add(btnMenu);
		btnMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GUIMainLibrarian.frame.getContentPane().removeAll();
				GUIMainLibrarian.frame.getContentPane().add(new PanelMainReader());
				((JPanel) GUIMainLibrarian.frame.getContentPane()).revalidate();
				GUIMainLibrarian.frame.setTitle("Menu lettore");
				GUIMainLibrarian.frame.repaint();
			}
		});
		btnMenu.setFont(new Font("Tahoma", Font.PLAIN, 16));

		JButton btnCancel = new JButton("Cancella!");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for (int i = 0; i < tableLoansToCancel.getRowCount(); i++) {
					Loan loanToCancel = ((LoanTableModel) tableLoansToCancel.getModel()).getLoanAtRow(i);
					String result = Proxy.instance().cancelLoan(loanToCancel);
					if (result != null) {
						if (result.equals("Operation completed")) {
							for (Loan loan : Login.getUserReaderLogged().getLoanBooks()) {
								if (loan.equals(loanToCancel)) {
									Login.getUserReaderLogged().getLoanBooks().remove(loan);
									break;
								}
							}
							Booking bookingToCanel = new Booking(loanToCancel.getBook(), loanToCancel.getUser(),
									loanToCancel.getLoanDate());
							for (Booking booking : Login.getUserReaderLogged().getBookingBooks()) {
								if (booking.equals(bookingToCanel)) {
									Login.getUserReaderLogged().getBookingBooks().remove(booking);
									break;
								}
							}
							JOptionPane
									.showMessageDialog(null,
											"Il prestito del libro con codice isbn: " + loanToCancel.getBook().getIsbn()
													+ " è stato elimnato!",
											"Libro eliminato", JOptionPane.INFORMATION_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(null,
									"A causa di un errore il prestito del libro con codice isbn: "
											+ loanToCancel.getBook().getIsbn() + " non è stato cancellato!",
									"Errore", JOptionPane.ERROR_MESSAGE);
						}
					} else {
						break;
					}
				}
				GUIMainLibrarian.frame.getContentPane().removeAll();
				GUIMainLibrarian.frame.getContentPane().add(new PanelMainReader());
				((JPanel) GUIMainLibrarian.frame.getContentPane()).revalidate();
				GUIMainLibrarian.frame.setTitle("Menu lettore");
				GUIMainLibrarian.frame.repaint();
			}
		});
		btnCancel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelButtons.add(btnCancel);

		JPanel panelText = new JPanel();
		panelSouth.add(panelText, BorderLayout.NORTH);

		JLabel lblLoansToCancel = new JLabel("Prestiti da annullare:");
		lblLoansToCancel.setFont(new Font("Tahoma", Font.BOLD, 16));
		panelText.add(lblLoansToCancel);

		JScrollPane scrollPaneTable = new JScrollPane();
		scrollPaneTable.setPreferredSize(new Dimension(10, 95));
		panelSouth.add(scrollPaneTable, BorderLayout.CENTER);

		tableLoansToCancel = new JTable();
		LoanTableModel tableModel = new LoanTableModel(new ArrayList<Loan>());
		tableLoansToCancel.setModel(tableModel);
		tableLoansToCancel.setRowSorter(new TableRowSorter<DefaultTableModel>(tableModel));
		tableLoansToCancel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		tableLoansToCancel.getTableHeader().setReorderingAllowed(false);
		tableLoansToCancel.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 16));

		scrollPaneTable.setViewportView(tableLoansToCancel);

		JPanel panelLogo = new JPanel();
		panelLogo.setBorder(null);
		panelLogo.setPreferredSize(new Dimension(10, 280));
		panel.add(panelLogo, BorderLayout.NORTH);
		panelLogo.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

		JLabel lblLogo = new JLabel("");
		lblLogo.setHorizontalTextPosition(SwingConstants.LEADING);
		URL imgLogo = getClass().getResource(Directory.PATH_IMG_LOGO_APPLIBRARIAN);
		lblLogo.setIcon(new ImageIcon(imgLogo));
		panelLogo.add(lblLogo);

		JPanel panelCenter = new JPanel();
		panelCenter.setPreferredSize(new Dimension(10, 90));
		panel.add(panelCenter, BorderLayout.CENTER);
		panelCenter.setLayout(new BorderLayout(0, 0));

		JPanel panelBooks = new JPanel();
		panelCenter.add(panelBooks, BorderLayout.NORTH);
		panelBooks.setPreferredSize(new Dimension(10, 107));

		tableBooks = new JTable();
		ArrayList<Loan> loans = Login.getUserReaderLogged().getLoanBooks();
		if (loans != null) {
			CancelLoanTableModel tm = new CancelLoanTableModel(loans);
			tableBooks.setModel(tm);
			tableBooks.setRowSorter(new TableRowSorter<DefaultTableModel>(tm));
		}
		tableBooks.setFont(new Font("Tahoma", Font.PLAIN, 16));
		tableBooks.getTableHeader().setReorderingAllowed(false);
		tableBooks.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 16));
		tableBooks.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (tableBooks.columnAtPoint(arg0.getPoint()) != 0) {
					boolean b = (boolean) tableBooks.getValueAt(tableBooks.getSelectedRow(), 0);
					tableBooks.setValueAt(!b, tableBooks.getSelectedRow(), 0);
				}
				if ((boolean) tableBooks.getValueAt(tableBooks.getSelectedRow(), 0)) {
					Loan selectedLoan = ((CancelLoanTableModel) tableBooks.getModel())
							.getLoanAtRow(tableBooks.getSelectedRow());
					((LoanTableModel) tableLoansToCancel.getModel()).addLoan(selectedLoan);
				} else {
					String isbnSelectedBook = (String) tableBooks.getValueAt(tableBooks.getSelectedRow(), 3);
					for (int i = 0; i < tableLoansToCancel.getRowCount(); i++) {
						if (tableLoansToCancel.getValueAt(i, 2).toString().equals(isbnSelectedBook)) {
							((LoanTableModel) tableLoansToCancel.getModel()).removeRow(i);
							break;
						}
					}
				}
			}
		});

		panelBooks.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPaneBooks = new JScrollPane(tableBooks);
		scrollPaneBooks.setPreferredSize(new Dimension(10, 95));
		panelBooks.add(scrollPaneBooks, BorderLayout.CENTER);
	}
}
