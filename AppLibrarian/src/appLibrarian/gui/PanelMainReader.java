package appLibrarian.gui;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import appLibrarian.Proxy;
import dataModel.Login;
import utils.Directory;

/**
 * This class create the main reader interface panel
 *
 * @author Marco Di Capua
 * @author Mattia Lo Schiavo
 * @author Filippo Pelosi
 * @author Riccardo Zorzi
 * @version 1.0
 */
public class PanelMainReader extends JPanel {
	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel
	 */
	public PanelMainReader() {
		super();
		URL imgBookedBookList = getClass().getResource(Directory.PATH_IMG_BOOKED_BOOK_LIST);
		URL imgDeleteBooking = getClass().getResource(Directory.PATH_IMG_DELETE_BOOKING);
		URL imgLoanedBooks = getClass().getResource(Directory.PATH_IMG_LOANED_BOOKS_LIST);
		URL imgHistory = getClass().getResource(Directory.PATH_IMG_HISTORY_LOANS);
		URL imgModifyUser = getClass().getResource(Directory.PATH_IMG_DELETE_USER);
		URL imgUserGuide = getClass().getResource(Directory.PATH_IMG_USER_GUIDE);
		URL imgLogout = getClass().getResource(Directory.PATH_IMG_LOGOUT);
		setBorder(null);
		setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);

		JPanel panel = new JPanel();
		panel.setBorder(null);
		scrollPane.setViewportView(panel);
		panel.setLayout(new BorderLayout(0, 0));

		JPanel panelMenuIcons = new JPanel();
		panel.add(panelMenuIcons, BorderLayout.CENTER);
		URL imgFindBook = getClass().getResource(Directory.PATH_IMG_FIND_BOOK);
		URL imgBackBook = getClass().getResource(Directory.PATH_IMG_BACK_BOOK);
		GridBagLayout gbl_panelMenuIcons = new GridBagLayout();
		gbl_panelMenuIcons.columnWidths = new int[] { 100, 300, 50, 113, 50, 117, 50, 113, 50, 117, 50, 113, 50, 117,
				50, 300, 0 };
		gbl_panelMenuIcons.rowHeights = new int[] { 30, 334, 351, 0 };
		gbl_panelMenuIcons.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panelMenuIcons.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panelMenuIcons.setLayout(gbl_panelMenuIcons);

		JLabel lblBackBook = new JLabel("");
		lblBackBook.setPreferredSize(new Dimension(300, 340));
		lblBackBook.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblBackBook.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				GUIMainLibrarian.frame.getContentPane().removeAll();
				GUIMainLibrarian.frame.getContentPane().add(new PanelBackBook());
				((JPanel) GUIMainLibrarian.frame.getContentPane()).revalidate();
				GUIMainLibrarian.frame.setTitle("Restituisci libro");
				GUIMainLibrarian.frame.repaint();
			}
		});

		JLabel lblLoanBook = new JLabel("");
		lblLoanBook.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblLoanBook.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				GUIMainLibrarian.frame.getContentPane().removeAll();
				GUIMainLibrarian.frame.getContentPane().add(new PanelLoanBook());
				((JPanel) GUIMainLibrarian.frame.getContentPane()).revalidate();
				GUIMainLibrarian.frame.setTitle("Cerca libro");
				GUIMainLibrarian.frame.repaint();
			}
		});
		lblLoanBook.setIcon(new ImageIcon(imgFindBook));
		lblLoanBook.setPreferredSize(new Dimension(300, 340));
		GridBagConstraints gbc_lblLoanBook = new GridBagConstraints();
		gbc_lblLoanBook.anchor = GridBagConstraints.WEST;
		gbc_lblLoanBook.fill = GridBagConstraints.VERTICAL;
		gbc_lblLoanBook.insets = new Insets(0, 0, 5, 5);
		gbc_lblLoanBook.gridx = 1;
		gbc_lblLoanBook.gridy = 1;
		panelMenuIcons.add(lblLoanBook, gbc_lblLoanBook);
		lblBackBook.setIcon(new ImageIcon(imgBackBook));
		lblBackBook.setPreferredSize(new Dimension(300, 300));
		GridBagConstraints gbc_lblBackBook = new GridBagConstraints();
		gbc_lblBackBook.anchor = GridBagConstraints.WEST;
		gbc_lblBackBook.fill = GridBagConstraints.VERTICAL;
		gbc_lblBackBook.insets = new Insets(0, 0, 5, 5);
		gbc_lblBackBook.gridwidth = 3;
		gbc_lblBackBook.gridx = 3;
		gbc_lblBackBook.gridy = 1;
		panelMenuIcons.add(lblBackBook, gbc_lblBackBook);

		JLabel lblReaderHistoryLoans = new JLabel("");
		lblReaderHistoryLoans.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				GUIMainLibrarian.frame.getContentPane().removeAll();
				GUIMainLibrarian.frame.getContentPane().add(new PanelReaderHistoryLoans());
				((JPanel) GUIMainLibrarian.frame.getContentPane()).revalidate();
				GUIMainLibrarian.frame.setTitle("Cronologia prestiti");
				GUIMainLibrarian.frame.repaint();
			}
		});

		JLabel lblReaderLoanedBooks = new JLabel("");
		lblReaderLoanedBooks.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				GUIMainLibrarian.frame.getContentPane().removeAll();
				GUIMainLibrarian.frame.getContentPane().add(new PanelReaderLoanedBooks());
				((JPanel) GUIMainLibrarian.frame.getContentPane()).revalidate();
				GUIMainLibrarian.frame.setTitle("Lista libri in prestito");
				GUIMainLibrarian.frame.repaint();
			}
		});

		JLabel lblDeleteBooking = new JLabel("");
		lblDeleteBooking.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblDeleteBooking.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				GUIMainLibrarian.frame.getContentPane().removeAll();
				GUIMainLibrarian.frame.getContentPane().add(new PanelDeleteBooking());
				((JPanel) GUIMainLibrarian.frame.getContentPane()).revalidate();
				GUIMainLibrarian.frame.setTitle("Cancella prenotazione");
				GUIMainLibrarian.frame.repaint();
			}
		});

		JLabel lblReaderBookedBookList = new JLabel("");
		lblReaderBookedBookList.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblReaderBookedBookList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				GUIMainLibrarian.frame.getContentPane().removeAll();
				GUIMainLibrarian.frame.getContentPane().add(new PanelReaderBookedBooksList());
				((JPanel) GUIMainLibrarian.frame.getContentPane()).revalidate();
				GUIMainLibrarian.frame.setTitle("Lista libri prenotati");
				GUIMainLibrarian.frame.repaint();
			}
		});
		lblReaderBookedBookList.setIcon(new ImageIcon(imgBookedBookList));
		lblReaderBookedBookList.setPreferredSize(new Dimension(300, 340));
		GridBagConstraints gbc_lblReaderBookedBookList = new GridBagConstraints();
		gbc_lblReaderBookedBookList.anchor = GridBagConstraints.WEST;
		gbc_lblReaderBookedBookList.fill = GridBagConstraints.VERTICAL;
		gbc_lblReaderBookedBookList.insets = new Insets(0, 0, 5, 5);
		gbc_lblReaderBookedBookList.gridwidth = 3;
		gbc_lblReaderBookedBookList.gridx = 7;
		gbc_lblReaderBookedBookList.gridy = 1;
		panelMenuIcons.add(lblReaderBookedBookList, gbc_lblReaderBookedBookList);
		lblDeleteBooking.setIcon(new ImageIcon(imgDeleteBooking));
		lblDeleteBooking.setPreferredSize(new Dimension(300, 340));
		GridBagConstraints gbc_lblDeleteBooking = new GridBagConstraints();
		gbc_lblDeleteBooking.anchor = GridBagConstraints.WEST;
		gbc_lblDeleteBooking.fill = GridBagConstraints.VERTICAL;
		gbc_lblDeleteBooking.insets = new Insets(0, 0, 5, 5);
		gbc_lblDeleteBooking.gridwidth = 3;
		gbc_lblDeleteBooking.gridx = 11;
		gbc_lblDeleteBooking.gridy = 1;
		panelMenuIcons.add(lblDeleteBooking, gbc_lblDeleteBooking);
		lblReaderLoanedBooks.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblReaderLoanedBooks.setIcon(new ImageIcon(imgLoanedBooks));
		lblReaderLoanedBooks.setPreferredSize(new Dimension(300, 340));
		GridBagConstraints gbc_lblReaderLoanedBooks = new GridBagConstraints();
		gbc_lblReaderLoanedBooks.anchor = GridBagConstraints.WEST;
		gbc_lblReaderLoanedBooks.fill = GridBagConstraints.VERTICAL;
		gbc_lblReaderLoanedBooks.insets = new Insets(0, 0, 5, 0);
		gbc_lblReaderLoanedBooks.gridx = 15;
		gbc_lblReaderLoanedBooks.gridy = 1;
		panelMenuIcons.add(lblReaderLoanedBooks, gbc_lblReaderLoanedBooks);
		lblReaderHistoryLoans.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblReaderHistoryLoans.setIcon(new ImageIcon(imgHistory));
		lblReaderHistoryLoans.setPreferredSize(new Dimension(300, 340));
		GridBagConstraints gbc_lblReaderHistoryLoans = new GridBagConstraints();
		gbc_lblReaderHistoryLoans.anchor = GridBagConstraints.EAST;
		gbc_lblReaderHistoryLoans.fill = GridBagConstraints.VERTICAL;
		gbc_lblReaderHistoryLoans.insets = new Insets(0, 0, 0, 5);
		gbc_lblReaderHistoryLoans.gridwidth = 3;
		gbc_lblReaderHistoryLoans.gridx = 1;
		gbc_lblReaderHistoryLoans.gridy = 2;
		panelMenuIcons.add(lblReaderHistoryLoans, gbc_lblReaderHistoryLoans);

		JLabel lblDeleteUser = new JLabel("");
		lblDeleteUser.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblDeleteUser.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int result = JOptionPane.showConfirmDialog(null, "Vuoi davvero eliminare l'utente?", "Elimina utente",
						JOptionPane.OK_CANCEL_OPTION);
				if (result == JOptionPane.OK_OPTION) {
					String response = Proxy.instance().deleteUser(Login.getUserReaderLogged());
					if (response != null) {
						if (response.equals("Operation completed")) {
							JOptionPane.showMessageDialog(null, "L'utente è stato cancellato",
									"Cancellazione avvenuta!", JOptionPane.INFORMATION_MESSAGE);
							GUIMainLibrarian.frame.getContentPane().removeAll();
							GUIMainLibrarian.frame.getContentPane().add(new PanelMainReader());
							((JPanel) GUIMainLibrarian.frame.getContentPane()).revalidate();
							GUIMainLibrarian.frame.setTitle("Menu lettore");
							GUIMainLibrarian.frame.repaint();
						} else {
							JOptionPane.showMessageDialog(null,
									"Non è stato possibile cancellare l'utente! (Errore: " + response + ")", "Errore!",
									JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}
		});
		lblDeleteUser.setIcon(new ImageIcon(imgModifyUser));
		lblDeleteUser.setPreferredSize(new Dimension(300, 340));
		GridBagConstraints gbc_lblDeleteUser = new GridBagConstraints();
		gbc_lblDeleteUser.anchor = GridBagConstraints.WEST;
		gbc_lblDeleteUser.fill = GridBagConstraints.VERTICAL;
		gbc_lblDeleteUser.insets = new Insets(0, 0, 0, 5);
		gbc_lblDeleteUser.gridwidth = 3;
		gbc_lblDeleteUser.gridx = 5;
		gbc_lblDeleteUser.gridy = 2;
		panelMenuIcons.add(lblDeleteUser, gbc_lblDeleteUser);

		JLabel lblUserGuide = new JLabel("");
		lblUserGuide.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblUserGuide.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				GUIGuideReader.main(GUIMainLibrarian.frame);
			}
		});
		lblUserGuide.setIcon(new ImageIcon(imgUserGuide));
		lblUserGuide.setPreferredSize(new Dimension(300, 340));
		GridBagConstraints gbc_lblUserGuide = new GridBagConstraints();
		gbc_lblUserGuide.anchor = GridBagConstraints.WEST;
		gbc_lblUserGuide.fill = GridBagConstraints.VERTICAL;
		gbc_lblUserGuide.insets = new Insets(0, 0, 0, 5);
		gbc_lblUserGuide.gridwidth = 3;
		gbc_lblUserGuide.gridx = 9;
		gbc_lblUserGuide.gridy = 2;
		panelMenuIcons.add(lblUserGuide, gbc_lblUserGuide);

		JLabel lblLogout = new JLabel("");
		lblLogout.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblLogout.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Login.setUserReaderLogged(null);
				String response = Proxy.instance().logoutReader();
				if (response != null) {
					if (response.equals("Operation completed")) {
						JOptionPane.showMessageDialog(null, "Logout effettuato!", "Logout!",
								JOptionPane.INFORMATION_MESSAGE);
						GUIMainLibrarian.frame.getContentPane().removeAll();
						GUIMainLibrarian.frame.getContentPane().add(new PanelMainLibrarian());
						((JPanel) GUIMainLibrarian.frame.getContentPane()).revalidate();
						GUIMainLibrarian.frame.setTitle("Menu principale");
						GUIMainLibrarian.frame.repaint();
					}
				}
			}
		});
		lblLogout.setIcon(new ImageIcon(imgLogout));
		lblLogout.setPreferredSize(new Dimension(300, 340));
		GridBagConstraints gbc_lblLogout = new GridBagConstraints();
		gbc_lblLogout.anchor = GridBagConstraints.WEST;
		gbc_lblLogout.fill = GridBagConstraints.VERTICAL;
		gbc_lblLogout.gridwidth = 3;
		gbc_lblLogout.gridx = 13;
		gbc_lblLogout.gridy = 2;
		panelMenuIcons.add(lblLogout, gbc_lblLogout);

		JPanel panelLogo = new JPanel();
		panelLogo.setBorder(null);
		panelLogo.setPreferredSize(new Dimension(10, 280));
		panel.add(panelLogo, BorderLayout.NORTH);
		panelLogo.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

		JLabel labelLogo = new JLabel("");
		labelLogo.setBorder(null);
		URL imgLogo = getClass().getResource(Directory.PATH_IMG_LOGO_APPLIBRARIAN);
		labelLogo.setIcon(new ImageIcon(imgLogo));
		panelLogo.add(labelLogo);
	}

}
