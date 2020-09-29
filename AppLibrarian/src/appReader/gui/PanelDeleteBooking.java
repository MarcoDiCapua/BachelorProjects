package appReader.gui;

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

import appReader.Proxy;
import dataModel.Booking;
import dataModel.Login;
import tableModels.BookingTableModel;
import tableModels.DeleteBookingTableModel;
import utils.Directory;

/**
 * This class create the delete books panel
 *
 * @author Marco Di Capua
 * @author Mattia Lo Schiavo
 * @author Filippo Pelosi
 * @author Riccardo Zorzi
 * @version 1.0
 */
public class PanelDeleteBooking extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTable tableDeleteBookings;
	private JTable tableBookings;

	/**
	 * Create the panel
	 */
	public PanelDeleteBooking() {
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

		JPanel panelLoans = new JPanel();
		panelLoans.setPreferredSize(new Dimension(10, 317));
		panel.add(panelLoans, BorderLayout.SOUTH);

		panelLoans.setLayout(new BorderLayout(0, 0));

		tableBookings = new JTable();
		BookingTableModel tm = new BookingTableModel(new ArrayList<Booking>());
		tableBookings.setModel(tm);
		tableBookings.setRowSorter(new TableRowSorter<DefaultTableModel>(tm));
		tableBookings.setFont(new Font("Tahoma", Font.PLAIN, 16));
		tableBookings.getTableHeader().setReorderingAllowed(false);
		tableBookings.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 16));

		JPanel panelButtons = new JPanel();
		panelButtons.setPreferredSize(new Dimension(10, 100));
		FlowLayout fl_panelButtons = (FlowLayout) panelButtons.getLayout();
		fl_panelButtons.setHgap(400);
		panelLoans.add(panelButtons, BorderLayout.SOUTH);

		JButton btnMenu = new JButton("Menu");
		btnMenu.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GUIMainReader.frame.getContentPane().removeAll();
				GUIMainReader.frame.getContentPane().add(new PanelMainReader());
				((JPanel) GUIMainReader.frame.getContentPane()).revalidate();
				GUIMainReader.frame.setTitle("Menu lettore");
				GUIMainReader.frame.repaint();
			}
		});
		panelButtons.add(btnMenu);

		JButton btnConfirm = new JButton("Cancella!");
		btnConfirm.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < tableBookings.getRowCount(); i++) {
					Booking bookingToDelete = ((BookingTableModel) tableBookings.getModel()).getBookingAtRow(i);
					String result = Proxy.instance().deleteBooking(bookingToDelete);
					if (result != null) {
						if (result.equals("Operation completed")) {
							Login.getUserLogged().getBookingBooks().remove(bookingToDelete);
							JOptionPane.showMessageDialog(null,
									"La prenotazione del libro con codice isbn: " + bookingToDelete.getBook().getIsbn()
											+ " è stata correttamente cancellata!",
									"Prenotazione cancellata!", JOptionPane.INFORMATION_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(null,
									"A causa di un errore la prenotazione del libro con codice isbn: "
											+ bookingToDelete.getBook().getIsbn() + " non è stata cancellata!",
									"Errore", JOptionPane.ERROR_MESSAGE);
						}
					} else {
						break;
					}
				}
				GUIMainReader.frame.getContentPane().removeAll();
				GUIMainReader.frame.getContentPane().add(new PanelMainReader());
				((JPanel) GUIMainReader.frame.getContentPane()).revalidate();
				GUIMainReader.frame.setTitle("Menu lettore");
				GUIMainReader.frame.repaint();
			}
		});
		panelButtons.add(btnConfirm);

		JPanel panelTitle = new JPanel();
		panelLoans.add(panelTitle, BorderLayout.NORTH);
		panelTitle.setPreferredSize(new Dimension(10, 30));

		JLabel lblBookingToDelete = new JLabel("Prenotazioni da annullare:");
		lblBookingToDelete.setFont(new Font("Tahoma", Font.BOLD, 16));
		panelTitle.add(lblBookingToDelete);
		JScrollPane scrollPaneLoans = new JScrollPane(tableBookings);
		scrollPaneLoans.setPreferredSize(new Dimension(10, 178));
		panelLoans.add(scrollPaneLoans);

		JPanel panelLogo = new JPanel();
		panelLogo.setBorder(null);
		panelLogo.setPreferredSize(new Dimension(10, 280));
		panel.add(panelLogo, BorderLayout.NORTH);
		panelLogo.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

		JLabel lblLogo = new JLabel("");
		lblLogo.setHorizontalTextPosition(SwingConstants.LEADING);
		URL imageLogoURL = getClass().getResource(Directory.PATH_IMG_LOGO_APPREADER);
		lblLogo.setIcon(new ImageIcon(imageLogoURL));
		panelLogo.add(lblLogo);

		JPanel panelCenter = new JPanel();
		panelCenter.setPreferredSize(new Dimension(10, 180));
		panel.add(panelCenter, BorderLayout.CENTER);
		panelCenter.setLayout(new BorderLayout(0, 0));

		JPanel panelBooks = new JPanel();
		panelCenter.add(panelBooks);
		panelBooks.setPreferredSize(new Dimension(10, 187));

		tableDeleteBookings = new JTable();
		ArrayList<Booking> bookings = Login.getUserLogged().getBookingBooks();
		if (bookings != null) {
			DeleteBookingTableModel model = new DeleteBookingTableModel(bookings);
			tableDeleteBookings.setModel(model);
			tableDeleteBookings.setRowSorter(new TableRowSorter<DefaultTableModel>(model));
		}
		tableDeleteBookings.setFont(new Font("Tahoma", Font.PLAIN, 16));
		tableDeleteBookings.getTableHeader().setReorderingAllowed(false);
		tableDeleteBookings.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 16));

		tableDeleteBookings.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (tableDeleteBookings.columnAtPoint(arg0.getPoint()) != 0) {
					tableDeleteBookings.setValueAt(
							!(boolean) tableDeleteBookings.getValueAt(tableDeleteBookings.getSelectedRow(), 0),
							tableDeleteBookings.getSelectedRow(), 0);
				}
				if ((boolean) tableDeleteBookings.getValueAt(tableDeleteBookings.getSelectedRow(), 0)) {
					Booking selectedBooking = ((DeleteBookingTableModel) tableDeleteBookings.getModel())
							.getBookingAtRow(tableDeleteBookings.getSelectedRow());
					((BookingTableModel) tableBookings.getModel()).addBooking(selectedBooking);
				} else {
					String isbnSelectedBook = (String) tableDeleteBookings
							.getValueAt(tableDeleteBookings.getSelectedRow(), 3);
					for (int i = 0; i < tableBookings.getRowCount(); i++) {
						if (tableBookings.getValueAt(i, 2).toString().equals(isbnSelectedBook)) {
							((BookingTableModel) tableBookings.getModel()).removeRow(i);
							break;
						}
					}
				}
			}
		});

		panelBooks.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPaneBooks = new JScrollPane(tableDeleteBookings);
		scrollPaneBooks.setPreferredSize(new Dimension(452, 178));
		panelBooks.add(scrollPaneBooks, BorderLayout.CENTER);
	}
}
