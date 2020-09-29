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
import javax.swing.JTextField;

import appLibrarian.Proxy;
import dataModel.Login;
import dataModel.User;
import utils.Directory;
import utils.Operation;

/**
 * This class create the main librarian interface panel
 *
 * @author Marco Di Capua
 * @author Mattia Lo Schiavo
 * @author Filippo Pelosi
 * @author Riccardo Zorzi
 * @version 1.0
 */
public class PanelMainLibrarian extends JPanel {
	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel
	 */
	public PanelMainLibrarian() {
		super();
		URL imgAllBooksList = getClass().getResource(Directory.PATH_IMG_ALL_BOOKS_LIST);
		URL imgSelectUser = getClass().getResource(Directory.PATH_IMG_SELECT_USER);
		URL imgModifyUser = getClass().getResource(Directory.PATH_IMG_MODIFY_USER);
		URL imgUserGuide = getClass().getResource(Directory.PATH_IMG_USER_GUIDE);
		URL imgLogout = getClass().getResource(Directory.PATH_IMG_LOGOUT);
		URL imgAnalysis = getClass().getResource(Directory.PATH_IMG_ANALYSIS);
		URL imgReaderRegistation = getClass().getResource(Directory.PATH_IMG_NEW_USER);
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
		URL imgAddBook = getClass().getResource(Directory.PATH_IMG_ADD_BOOK);
		GridBagLayout gbl_panelMenuIcons = new GridBagLayout();
		gbl_panelMenuIcons.columnWidths = new int[] { 100, 300, 50, 125, 50, 125, 50, 125, 50, 125, 50, 125, 50, 125,
				50, 300, 0 };
		gbl_panelMenuIcons.rowHeights = new int[] { 30, 340, 340, 0 };
		gbl_panelMenuIcons.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panelMenuIcons.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panelMenuIcons.setLayout(gbl_panelMenuIcons);
		URL imgDeleteBook = getClass().getResource(Directory.PATH_IMG_DELETE_BOOK);

		JLabel lblAddBook = new JLabel("");
		lblAddBook.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblAddBook.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				GUIAddBook.main(GUIMainLibrarian.frame);
			}
		});
		lblAddBook.setToolTipText("");
		lblAddBook.setIcon(new ImageIcon(imgAddBook));
		lblAddBook.setPreferredSize(new Dimension(300, 340));
		GridBagConstraints gbc_lblAddBook = new GridBagConstraints();
		gbc_lblAddBook.anchor = GridBagConstraints.WEST;
		gbc_lblAddBook.fill = GridBagConstraints.VERTICAL;
		gbc_lblAddBook.insets = new Insets(0, 0, 5, 5);
		gbc_lblAddBook.gridx = 1;
		gbc_lblAddBook.gridy = 1;
		panelMenuIcons.add(lblAddBook, gbc_lblAddBook);

		JLabel lblAllBooksList = new JLabel("");
		lblAllBooksList.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblAllBooksList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				GUIMainLibrarian.frame.getContentPane().removeAll();
				GUIMainLibrarian.frame.getContentPane().add(new PanelAllBooksList());
				((JPanel) GUIMainLibrarian.frame.getContentPane()).revalidate();
				GUIMainLibrarian.frame.setTitle("Lista di tutti i libri");
				GUIMainLibrarian.frame.repaint();
			}
		});

		JLabel lblDeleteBook = new JLabel("");
		lblDeleteBook.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblDeleteBook.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				GUIMainLibrarian.frame.getContentPane().removeAll();
				GUIMainLibrarian.frame.getContentPane().add(new PanelDeleteBook());
				((JPanel) GUIMainLibrarian.frame.getContentPane()).revalidate();
				GUIMainLibrarian.frame.setTitle("Elimina libro");
				GUIMainLibrarian.frame.repaint();
			}
		});
		lblDeleteBook.setIcon(new ImageIcon(imgDeleteBook));
		lblDeleteBook.setPreferredSize(new Dimension(300, 340));
		GridBagConstraints gbc_lblDeleteBook = new GridBagConstraints();
		gbc_lblDeleteBook.anchor = GridBagConstraints.WEST;
		gbc_lblDeleteBook.fill = GridBagConstraints.VERTICAL;
		gbc_lblDeleteBook.insets = new Insets(0, 0, 5, 5);
		gbc_lblDeleteBook.gridwidth = 3;
		gbc_lblDeleteBook.gridx = 3;
		gbc_lblDeleteBook.gridy = 1;
		panelMenuIcons.add(lblDeleteBook, gbc_lblDeleteBook);
		lblAllBooksList.setIcon(new ImageIcon(imgAllBooksList));
		lblAllBooksList.setPreferredSize(new Dimension(300, 340));
		GridBagConstraints gbc_lblAllBooksList = new GridBagConstraints();
		gbc_lblAllBooksList.anchor = GridBagConstraints.WEST;
		gbc_lblAllBooksList.fill = GridBagConstraints.VERTICAL;
		gbc_lblAllBooksList.insets = new Insets(0, 0, 5, 5);
		gbc_lblAllBooksList.gridwidth = 3;
		gbc_lblAllBooksList.gridx = 7;
		gbc_lblAllBooksList.gridy = 1;
		panelMenuIcons.add(lblAllBooksList, gbc_lblAllBooksList);

		JLabel lblReaderRegistration = new JLabel("");
		lblReaderRegistration.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				GUIRegistrationReader.main(GUIMainLibrarian.frame);

			}
		});
		lblReaderRegistration.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblReaderRegistration.setIcon(new ImageIcon(imgReaderRegistation));
		lblReaderRegistration.setPreferredSize(new Dimension(300, 340));
		GridBagConstraints gbc_lblReaderRegistration = new GridBagConstraints();
		gbc_lblReaderRegistration.anchor = GridBagConstraints.WEST;
		gbc_lblReaderRegistration.fill = GridBagConstraints.VERTICAL;
		gbc_lblReaderRegistration.insets = new Insets(0, 0, 5, 5);
		gbc_lblReaderRegistration.gridwidth = 3;
		gbc_lblReaderRegistration.gridx = 11;
		gbc_lblReaderRegistration.gridy = 1;
		panelMenuIcons.add(lblReaderRegistration, gbc_lblReaderRegistration);

		JLabel lblUserInterface = new JLabel("");
		lblUserInterface.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblUserInterface.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				JLabel lblID = new JLabel("Inserire il codice fiscale dell'utente:");
				JTextField textFieldID = new JTextField();
				Object[] ob = { lblID, textFieldID };
				int result = JOptionPane.showConfirmDialog(null, ob, "Selezione utente", JOptionPane.OK_CANCEL_OPTION);
				if (result == JOptionPane.OK_OPTION) {
					User user = null;
					user = Proxy.instance().login(Operation.LOGIN_FROM_LIBRARIAN,
							new Login(textFieldID.getText(), null, null));
					if (user != null) {
						Login.setUserReaderLogged(user);
						GUIMainLibrarian.frame.getContentPane().removeAll();
						GUIMainLibrarian.frame.getContentPane().add(new PanelMainReader());
						((JPanel) GUIMainLibrarian.frame.getContentPane()).revalidate();
						GUIMainLibrarian.frame.setTitle("Menu lettore");
						GUIMainLibrarian.frame.repaint();
					} else {
						JOptionPane.showMessageDialog(null, "L'utente non esiste o c'è stato un problema", "Errore!",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		lblUserInterface.setIcon(new ImageIcon(imgSelectUser));
		lblUserInterface.setPreferredSize(new Dimension(300, 340));
		GridBagConstraints gbc_lblUserInterface = new GridBagConstraints();
		gbc_lblUserInterface.anchor = GridBagConstraints.WEST;
		gbc_lblUserInterface.fill = GridBagConstraints.VERTICAL;
		gbc_lblUserInterface.insets = new Insets(0, 0, 5, 0);
		gbc_lblUserInterface.gridx = 15;
		gbc_lblUserInterface.gridy = 1;
		panelMenuIcons.add(lblUserInterface, gbc_lblUserInterface);

		JLabel lblAnalysis = new JLabel("");
		lblAnalysis.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				GUIMainLibrarian.frame.getContentPane().removeAll();
				GUIMainLibrarian.frame.getContentPane().add(new PanelMainAnalysis());
				((JPanel) GUIMainLibrarian.frame.getContentPane()).revalidate();
				GUIMainLibrarian.frame.setTitle("Menu analisi");
				GUIMainLibrarian.frame.repaint();

			}
		});
		lblAnalysis.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblAnalysis.setIcon(new ImageIcon(imgAnalysis));
		lblAnalysis.setPreferredSize(new Dimension(300, 340));
		GridBagConstraints gbc_lblAnalysis = new GridBagConstraints();
		gbc_lblAnalysis.anchor = GridBagConstraints.EAST;
		gbc_lblAnalysis.fill = GridBagConstraints.VERTICAL;
		gbc_lblAnalysis.insets = new Insets(0, 0, 0, 5);
		gbc_lblAnalysis.gridwidth = 3;
		gbc_lblAnalysis.gridx = 1;
		gbc_lblAnalysis.gridy = 2;
		panelMenuIcons.add(lblAnalysis, gbc_lblAnalysis);

		JLabel lblLogout = new JLabel("");
		lblLogout.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblLogout.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Login.setUserLogged(null);
				Proxy.instance().logout();
				GUILogin.main();
				GUIMainLibrarian.frame.dispose();
			}
		});

		JLabel lblUserGuide = new JLabel("");
		lblUserGuide.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblUserGuide.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				GUIGuideLibrarian.main(GUIMainLibrarian.frame);
			}
		});

		JLabel lblModifyUser = new JLabel("");
		lblModifyUser.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblModifyUser.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				GUIModifyLibrarian.main(GUIMainLibrarian.frame);
			}
		});
		lblModifyUser.setIcon(new ImageIcon(imgModifyUser));
		lblModifyUser.setPreferredSize(new Dimension(300, 340));
		GridBagConstraints gbc_lblModifyUser = new GridBagConstraints();
		gbc_lblModifyUser.anchor = GridBagConstraints.WEST;
		gbc_lblModifyUser.fill = GridBagConstraints.VERTICAL;
		gbc_lblModifyUser.insets = new Insets(0, 0, 0, 5);
		gbc_lblModifyUser.gridwidth = 3;
		gbc_lblModifyUser.gridx = 5;
		gbc_lblModifyUser.gridy = 2;
		panelMenuIcons.add(lblModifyUser, gbc_lblModifyUser);
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
