package appReader.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import dataModel.Booking;
import dataModel.Login;
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

		JPanel panelButtons = new JPanel();
		panelButtons.setPreferredSize(new Dimension(10, 70));
		panel.add(panelButtons, BorderLayout.SOUTH);
		panelButtons.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JButton btnMenu = new JButton("Menu");
		btnMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GUIMainReader.frame.getContentPane().removeAll();
				GUIMainReader.frame.getContentPane().add(new PanelMainReader());
				((JPanel) GUIMainReader.frame.getContentPane()).revalidate();
				GUIMainReader.frame.setTitle("Menu lettore");
				GUIMainReader.frame.repaint();
			}
		});
		btnMenu.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelButtons.add(btnMenu);
		JPanel panelLogo = new JPanel();
		panelLogo.setBorder(null);
		panelLogo.setPreferredSize(new Dimension(10, 380));
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
		panelCenter.add(panelBooks, BorderLayout.NORTH);
		panelBooks.setPreferredSize(new Dimension(10, 187));

		tableBookings = new JTable();
		ArrayList<Booking> bookings = Login.getUserLogged().getBookingBooks();
		if (bookings != null) {
			BookingTableModel tm = new BookingTableModel(bookings);
			tableBookings.setModel(tm);
			tableBookings.setRowSorter(new TableRowSorter<DefaultTableModel>(tm));
		}
		tableBookings.setFont(new Font("Tahoma", Font.PLAIN, 16));
		tableBookings.getTableHeader().setReorderingAllowed(false);
		tableBookings.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 16));

		panelBooks.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPaneBooks = new JScrollPane(tableBookings);
		scrollPaneBooks.setPreferredSize(new Dimension(10, 10));
		panelBooks.add(scrollPaneBooks, BorderLayout.CENTER);
	}

}
