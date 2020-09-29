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
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import appLibrarian.Proxy;
import dataModel.Booking;
import dataModel.Loan;
import dataModel.Login;
import tableModels.BookDeliveryTableModel;
import tableModels.BookingTableModel;
import utils.Directory;

/**
 * This class create the reader booked books list panel
 *
 * @author Marco Di Capua
 * @author Mattia Lo Schiavo
 * @author Filippo Pelosi
 * @author Riccardo Zorzi
 * @version 1.0
 */
public class PanelReaderBookedBooksList extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTable tableBookings;
	private JTable tableBooksRetired;

	/**
	 * Create the panel
	 */
	public PanelReaderBookedBooksList() {
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
		panelSouth.setPreferredSize(new Dimension(10, 317));
		panel.add(panelSouth, BorderLayout.SOUTH);
		panelSouth.setLayout(new BorderLayout(0, 0));

		JPanel panelButtons = new JPanel();
		panelButtons.setPreferredSize(new Dimension(10, 100));
		FlowLayout flowLayout = (FlowLayout) panelButtons.getLayout();
		flowLayout.setVgap(10);
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

		JButton btnRetire = new JButton("Consegna!");
		btnRetire.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < tableBooksRetired.getRowCount(); i++) {
					Booking bookingToLoan = ((BookingTableModel) tableBooksRetired.getModel()).getBookingAtRow(i);
					JLabel lblInsertDay = new JLabel("Selezionare il numero di giorni del prestito:");
					lblInsertDay.setFont(new Font("Tahoma", Font.PLAIN, 16));
					JSpinner spnSelectDay = new JSpinner(
							new SpinnerNumberModel(new Integer(30), new Integer(1), new Integer(30), new Integer(1)));
					spnSelectDay.setFont(new Font("Tahoma", Font.PLAIN, 16));
					Object[] ob = { lblInsertDay, spnSelectDay };
					int selectedOption = JOptionPane.showConfirmDialog(null, ob, "Inserire durata prestito",
							JOptionPane.OK_CANCEL_OPTION);
					GregorianCalendar returnDate = new GregorianCalendar();
					if (selectedOption == JOptionPane.OK_OPTION) {
						returnDate.add(Calendar.DAY_OF_MONTH, (int) spnSelectDay.getValue());
						Loan newLoan = new Loan(bookingToLoan.getBook(), Login.getUserReaderLogged(),
								new GregorianCalendar(), returnDate, false);
						String result = Proxy.instance().loanBook(newLoan);
						if (result != null) {
							if (result.equals("Operation completed")) {
								Login.getUserReaderLogged().getLoanBooks().add(newLoan);
								JOptionPane.showMessageDialog(null,
										"Il prestito del libro con codice isbn: " + bookingToLoan.getBook().getIsbn()
												+ " è stato correttamente registrato!",
										"Prestito effettuato!", JOptionPane.INFORMATION_MESSAGE);
							} else {
								JOptionPane.showMessageDialog(null,
										"A causa di un errore il prestito del libro con codice isbn: "
												+ bookingToLoan.getBook().getIsbn()
												+ " non è stato correttamente registrato! (Errore: " + result + ")",
										"Errore", JOptionPane.ERROR_MESSAGE);
							}
						}
					} else {
						JOptionPane.showMessageDialog(null,
								"Il prestito del libro con codice isbn: " + bookingToLoan.getBook().getIsbn()
										+ " è stato annullato!",
								"Prestito annullato!", JOptionPane.INFORMATION_MESSAGE);
					}
				}
				GUIMainLibrarian.frame.getContentPane().removeAll();
				GUIMainLibrarian.frame.getContentPane().add(new PanelMainReader());
				((JPanel) GUIMainLibrarian.frame.getContentPane()).revalidate();
				GUIMainLibrarian.frame.setTitle("Menu lettore");
				GUIMainLibrarian.frame.repaint();
			}
		});
		btnRetire.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelButtons.add(btnRetire);

		JPanel panelText = new JPanel();
		panelText.setPreferredSize(new Dimension(10, 30));
		panelSouth.add(panelText, BorderLayout.NORTH);

		JLabel lblBooksToRetire = new JLabel("Libri da ritirare:");
		lblBooksToRetire.setFont(new Font("Tahoma", Font.BOLD, 16));
		panelText.add(lblBooksToRetire);

		tableBooksRetired = new JTable();
		BookingTableModel tableModel = new BookingTableModel(new ArrayList<Booking>());
		tableBooksRetired.setModel(tableModel);
		tableBooksRetired.setRowSorter(new TableRowSorter<DefaultTableModel>(tableModel));
		tableBooksRetired.setFont(new Font("Tahoma", Font.PLAIN, 16));
		tableBooksRetired.getTableHeader().setReorderingAllowed(false);
		tableBooksRetired.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 16));

		JScrollPane scrollPaneTable = new JScrollPane(tableBooksRetired);
		scrollPaneTable.setPreferredSize(new Dimension(10, 178));
		panelSouth.add(scrollPaneTable, BorderLayout.CENTER);

		JPanel panelLogo = new JPanel();
		panelLogo.setBorder(null);
		panelLogo.setPreferredSize(new Dimension(10, 365));
		panel.add(panelLogo, BorderLayout.NORTH);
		panelLogo.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

		JLabel lblLogo = new JLabel("");
		lblLogo.setHorizontalTextPosition(SwingConstants.LEADING);
		URL imageLogoURL = getClass().getResource(Directory.PATH_IMG_LOGO_APPLIBRARIAN);
		lblLogo.setIcon(new ImageIcon(imageLogoURL));
		panelLogo.add(lblLogo);

		JPanel panelCenter = new JPanel();
		panelCenter.setPreferredSize(new Dimension(10, 180));
		panel.add(panelCenter, BorderLayout.CENTER);
		panelCenter.setLayout(new BorderLayout(0, 0));

		JPanel panelBooks = new JPanel();
		panelCenter.add(panelBooks, BorderLayout.NORTH);
		panelBooks.setPreferredSize(new Dimension(10, 187));

		tableBookings = new JTable();
		ArrayList<Booking> bookings = Login.getUserReaderLogged().getBookingBooks();
		if (bookings != null) {
			BookDeliveryTableModel tm = new BookDeliveryTableModel(bookings);
			tableBookings.setModel(tm);
			tableBookings.setRowSorter(new TableRowSorter<DefaultTableModel>(tm));
		}
		tableBookings.setFont(new Font("Tahoma", Font.PLAIN, 16));
		tableBookings.getTableHeader().setReorderingAllowed(false);
		tableBookings.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 16));
		tableBookings.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (tableBookings.columnAtPoint(arg0.getPoint()) != 0) {
					boolean b = (boolean) tableBookings.getValueAt(tableBookings.getSelectedRow(), 0);
					tableBookings.setValueAt(!b, tableBookings.getSelectedRow(), 0);
				}
				if ((boolean) tableBookings.getValueAt(tableBookings.getSelectedRow(), 0)) {
					Booking selectedBooking = ((BookDeliveryTableModel) tableBookings.getModel())
							.getBookingAtRow(tableBookings.getSelectedRow());
					((BookingTableModel) tableBooksRetired.getModel()).addBooking(selectedBooking);
				} else {
					String isbnSelectedBook = (String) tableBookings.getValueAt(tableBookings.getSelectedRow(), 3);
					for (int i = 0; i < tableBooksRetired.getRowCount(); i++) {
						if (tableBooksRetired.getValueAt(i, 2).toString().equals(isbnSelectedBook)) {
							((BookingTableModel) tableBooksRetired.getModel()).removeRow(i);
							break;
						}
					}
				}
			}
		});

		panelBooks.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPaneBooks = new JScrollPane(tableBookings);
		scrollPaneBooks.setPreferredSize(new Dimension(10, 10));
		panelBooks.add(scrollPaneBooks, BorderLayout.CENTER);
	}

}
