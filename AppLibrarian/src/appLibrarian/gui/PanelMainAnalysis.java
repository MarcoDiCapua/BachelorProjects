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
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import utils.Directory;

/**
 * This class create the main analysis panel
 *
 * @author Marco Di Capua
 * @author Mattia Lo Schiavo
 * @author Filippo Pelosi
 * @author Riccardo Zorzi
 * @version 1.0
 */
public class PanelMainAnalysis extends JPanel {
	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel
	 */
	public PanelMainAnalysis() {
		super();
		URL imgUserGuide = getClass().getResource(Directory.PATH_IMG_USER_GUIDE);
		URL imgMenuLibrarian = getClass().getResource(Directory.PATH_IMG_BACK_MENU);
		URL imgBooksRank = getClass().getResource(Directory.PATH_IMG_BOOKS_RANK);
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
		URL imgLoanedBooksList = getClass().getResource(Directory.PATH_IMG_LOANED_BOOKS_LIST);
		URL imgBookedBookList = getClass().getResource(Directory.PATH_IMG_BOOKED_BOOK_LIST);
		GridBagLayout gbl_panelMenuIcons = new GridBagLayout();
		gbl_panelMenuIcons.columnWidths = new int[] { 400, 300, 70, 300, 70, 300, 0 };
		gbl_panelMenuIcons.rowHeights = new int[] { 30, 344, 340, 0 };
		gbl_panelMenuIcons.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panelMenuIcons.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panelMenuIcons.setLayout(gbl_panelMenuIcons);

		JLabel lblAllBookedBookList = new JLabel("");
		lblAllBookedBookList.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblAllBookedBookList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				GUIMainLibrarian.frame.getContentPane().removeAll();
				GUIMainLibrarian.frame.getContentPane().add(new PanelAllBookedBooksList());
				((JPanel) GUIMainLibrarian.frame.getContentPane()).revalidate();
				GUIMainLibrarian.frame.setTitle("Lista di tutti i libri prenotati");
				GUIMainLibrarian.frame.repaint();
			}
		});

		JLabel lblLoanedBooksList = new JLabel("");
		lblLoanedBooksList.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblLoanedBooksList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				GUIMainLibrarian.frame.getContentPane().removeAll();
				GUIMainLibrarian.frame.getContentPane().add(new PanelLoanedBooksList());
				((JPanel) GUIMainLibrarian.frame.getContentPane()).revalidate();
				GUIMainLibrarian.frame.setTitle("Lista di tutti i libri in prestito");
				GUIMainLibrarian.frame.repaint();

			}
		});
		lblLoanedBooksList.setIcon(new ImageIcon(imgLoanedBooksList));

		lblLoanedBooksList.setPreferredSize(new Dimension(300, 340));
		GridBagConstraints gbc_lblLoanedBooksList = new GridBagConstraints();
		gbc_lblLoanedBooksList.anchor = GridBagConstraints.WEST;
		gbc_lblLoanedBooksList.fill = GridBagConstraints.VERTICAL;
		gbc_lblLoanedBooksList.insets = new Insets(0, 0, 5, 5);
		gbc_lblLoanedBooksList.gridx = 1;
		gbc_lblLoanedBooksList.gridy = 1;
		panelMenuIcons.add(lblLoanedBooksList, gbc_lblLoanedBooksList);
		lblAllBookedBookList.setIcon(new ImageIcon(imgBookedBookList));
		lblAllBookedBookList.setPreferredSize(new Dimension(300, 340));
		GridBagConstraints gbc_lblAllBookedBookList = new GridBagConstraints();
		gbc_lblAllBookedBookList.anchor = GridBagConstraints.WEST;
		gbc_lblAllBookedBookList.fill = GridBagConstraints.VERTICAL;
		gbc_lblAllBookedBookList.insets = new Insets(0, 0, 5, 5);
		gbc_lblAllBookedBookList.gridx = 3;
		gbc_lblAllBookedBookList.gridy = 1;
		panelMenuIcons.add(lblAllBookedBookList, gbc_lblAllBookedBookList);

		JLabel lblBooksRank = new JLabel("");
		lblBooksRank.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblBooksRank.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				GUIMainLibrarian.frame.getContentPane().removeAll();
				GUIMainLibrarian.frame.getContentPane().add(new PanelBooksRank());
				((JPanel) GUIMainLibrarian.frame.getContentPane()).revalidate();
				GUIMainLibrarian.frame.setTitle("Classifica libri");
				GUIMainLibrarian.frame.repaint();
			}
		});
		lblBooksRank.setIcon(new ImageIcon(imgBooksRank));

		lblBooksRank.setPreferredSize(new Dimension(300, 340));
		GridBagConstraints gbc_lblBooksRank = new GridBagConstraints();
		gbc_lblBooksRank.anchor = GridBagConstraints.WEST;
		gbc_lblBooksRank.fill = GridBagConstraints.VERTICAL;
		gbc_lblBooksRank.insets = new Insets(0, 0, 5, 0);
		gbc_lblBooksRank.gridx = 5;
		gbc_lblBooksRank.gridy = 1;
		panelMenuIcons.add(lblBooksRank, gbc_lblBooksRank);

		URL imgOverwhelmingLoans = getClass().getResource(Directory.PATH_IMG_OVERWHELMING_LOANS);

		JLabel lblGuideAnalysis = new JLabel("");
		lblGuideAnalysis.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblGuideAnalysis.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				GUIGuideAnalysis.main(GUIMainLibrarian.frame);
			}
		});

		JLabel lblOverwhelmingLoans = new JLabel("");
		lblOverwhelmingLoans.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblOverwhelmingLoans.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				GUIMainLibrarian.frame.getContentPane().removeAll();
				GUIMainLibrarian.frame.getContentPane().add(new PanelOverwhelmingLoansList());
				((JPanel) GUIMainLibrarian.frame.getContentPane()).revalidate();
				GUIMainLibrarian.frame.setTitle("Prestiti sconfinanti");
				GUIMainLibrarian.frame.repaint();
			}
		});
		lblOverwhelmingLoans.setIcon(new ImageIcon(imgOverwhelmingLoans));
		lblOverwhelmingLoans.setPreferredSize(new Dimension(300, 340));
		GridBagConstraints gbc_lblOverwhelmingLoans = new GridBagConstraints();
		gbc_lblOverwhelmingLoans.anchor = GridBagConstraints.WEST;
		gbc_lblOverwhelmingLoans.fill = GridBagConstraints.VERTICAL;
		gbc_lblOverwhelmingLoans.insets = new Insets(0, 0, 0, 5);
		gbc_lblOverwhelmingLoans.gridx = 1;
		gbc_lblOverwhelmingLoans.gridy = 2;
		panelMenuIcons.add(lblOverwhelmingLoans, gbc_lblOverwhelmingLoans);
		lblGuideAnalysis.setIcon(new ImageIcon(imgUserGuide));
		lblGuideAnalysis.setPreferredSize(new Dimension(300, 340));
		GridBagConstraints gbc_lblGuideAnalysis = new GridBagConstraints();
		gbc_lblGuideAnalysis.anchor = GridBagConstraints.WEST;
		gbc_lblGuideAnalysis.fill = GridBagConstraints.VERTICAL;
		gbc_lblGuideAnalysis.insets = new Insets(0, 0, 0, 5);
		gbc_lblGuideAnalysis.gridx = 3;
		gbc_lblGuideAnalysis.gridy = 2;
		panelMenuIcons.add(lblGuideAnalysis, gbc_lblGuideAnalysis);

		JLabel lblMenuLibrarian = new JLabel("");
		lblMenuLibrarian.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				GUIMainLibrarian.frame.getContentPane().removeAll();
				GUIMainLibrarian.frame.getContentPane().add(new PanelMainLibrarian());
				((JPanel) GUIMainLibrarian.frame.getContentPane()).revalidate();
				GUIMainLibrarian.frame.setTitle("Menu principale");
				GUIMainLibrarian.frame.repaint();

			}
		});
		lblMenuLibrarian.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblMenuLibrarian.setIcon(new ImageIcon(imgMenuLibrarian));
		lblMenuLibrarian.setPreferredSize(new Dimension(300, 340));
		GridBagConstraints gbc_lblMenuLibrarian = new GridBagConstraints();
		gbc_lblMenuLibrarian.anchor = GridBagConstraints.WEST;
		gbc_lblMenuLibrarian.fill = GridBagConstraints.VERTICAL;
		gbc_lblMenuLibrarian.gridx = 5;
		gbc_lblMenuLibrarian.gridy = 2;
		panelMenuIcons.add(lblMenuLibrarian, gbc_lblMenuLibrarian);

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
