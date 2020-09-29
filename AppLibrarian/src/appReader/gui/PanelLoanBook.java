package appReader.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import appReader.Proxy;
import dataModel.Book;
import dataModel.Booking;
import dataModel.Category;
import dataModel.Login;
import tableModels.BookingTableModel;
import tableModels.LoanBookTableModel;
import utils.Directory;
import utils.Operation;

/**
 * This class create the loan books panel
 *
 * @author Marco Di Capua
 * @author Mattia Lo Schiavo
 * @author Filippo Pelosi
 * @author Riccardo Zorzi
 * @version 1.0
 */
public class PanelLoanBook extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField textFieldTitle;
	private JTextField textFieldAuthor;
	private JTable tableBooks;
	private JTable tableLoans;
	private JComboBox<Category> comboBoxCategory;
	private JPanel panelPages;
	private JCheckBox checkBoxCategory;
	private static TableWorker worker = null;

	/**
	 * Create the panel
	 */
	public PanelLoanBook() {
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
		panelLoans.setPreferredSize(new Dimension(10, 240));
		panel.add(panelLoans, BorderLayout.SOUTH);

		panelLoans.setLayout(new BorderLayout(0, 0));

		tableLoans = new JTable();
		BookingTableModel tm = new BookingTableModel(new ArrayList<Booking>());
		tableLoans.setModel(tm);
		tableLoans.setRowSorter(new TableRowSorter<DefaultTableModel>(tm));
		tableLoans.setFont(new Font("Tahoma", Font.PLAIN, 16));
		tableLoans.getTableHeader().setReorderingAllowed(false);
		tableLoans.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 16));

		JPanel panelButtons = new JPanel();
		panelButtons.setPreferredSize(new Dimension(10, 50));
		FlowLayout fl_panelButtons = (FlowLayout) panelButtons.getLayout();
		fl_panelButtons.setHgap(400);
		panelLoans.add(panelButtons, BorderLayout.SOUTH);

		JButton btnMenu = new JButton("Menu");
		btnMenu.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelButtons.add(btnMenu);

		JButton btnConfirm = new JButton("Prenota!");
		btnConfirm.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tableLoans.getRowCount() != 0) {
					for (int i = 0; i < tableLoans.getRowCount(); i++) {
						Book bookToBook = ((BookingTableModel) tableLoans.getModel()).getBookingAtRow(i).getBook();
						Booking newBooking = new Booking(bookToBook, Login.getUserLogged(), new GregorianCalendar());
						String result = Proxy.instance().bookingBook(newBooking);
						if (result.equals("Operation completed")) {
							Login.getUserLogged().getBookingBooks().add(newBooking);
							JOptionPane.showMessageDialog(null,
									"Il libro con codice isbn: " + bookToBook.getIsbn()
											+ " è stato correttamente prenotato!",
									"Prenotazione effettuata!", JOptionPane.INFORMATION_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(null,
									"A causa di un errore il libro con codice isbn: " + bookToBook.getIsbn()
											+ " non è stato prenotato! (Errore: " + result + ")",
									"Errore", JOptionPane.ERROR_MESSAGE);
						}
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

		JLabel lblBooksToLoan = new JLabel("Le tue prenotazioni:");
		lblBooksToLoan.setFont(new Font("Tahoma", Font.BOLD, 16));
		panelTitle.add(lblBooksToLoan);
		JScrollPane scrollPaneLoans = new JScrollPane(tableLoans);
		scrollPaneLoans.setPreferredSize(new Dimension(469, 400));
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

		JPanel panelFiltersBorder = new JPanel();
		panel.add(panelFiltersBorder, BorderLayout.WEST);

		JPanel panelFilters = new JPanel();
		panelFiltersBorder.add(panelFilters);
		panelFilters.setPreferredSize(new Dimension(300, 240));
		panelFilters.setBorder(new TitledBorder(null, "Filtri", TitledBorder.LEADING, TitledBorder.TOP,
				new Font("Tahoma", Font.BOLD, 18), new Color(0, 0, 0)));
		GridBagLayout gbl_panelFilters = new GridBagLayout();
		gbl_panelFilters.columnWidths = new int[] { 73, 0, 161, 0 };
		gbl_panelFilters.rowHeights = new int[] { 26, 45, 26, 45, 26, 0 };
		gbl_panelFilters.columnWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panelFilters.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panelFilters.setLayout(gbl_panelFilters);

		JLabel lblTitle = new JLabel("Titolo:");
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblTitle = new GridBagConstraints();
		gbc_lblTitle.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblTitle.insets = new Insets(0, 0, 5, 5);
		gbc_lblTitle.gridx = 0;
		gbc_lblTitle.gridy = 0;
		panelFilters.add(lblTitle, gbc_lblTitle);

		textFieldTitle = new JTextField();
		textFieldTitle.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_textFieldTitle = new GridBagConstraints();
		gbc_textFieldTitle.anchor = GridBagConstraints.NORTH;
		gbc_textFieldTitle.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldTitle.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldTitle.gridx = 2;
		gbc_textFieldTitle.gridy = 0;
		panelFilters.add(textFieldTitle, gbc_textFieldTitle);
		textFieldTitle.setColumns(10);

		JLabel lblAuthor = new JLabel("Autore:");
		lblAuthor.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblAuthor = new GridBagConstraints();
		gbc_lblAuthor.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblAuthor.insets = new Insets(0, 0, 5, 5);
		gbc_lblAuthor.gridx = 0;
		gbc_lblAuthor.gridy = 2;
		panelFilters.add(lblAuthor, gbc_lblAuthor);

		textFieldAuthor = new JTextField();
		textFieldAuthor.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textFieldAuthor.setColumns(10);
		GridBagConstraints gbc_textFieldAuthor = new GridBagConstraints();
		gbc_textFieldAuthor.anchor = GridBagConstraints.NORTH;
		gbc_textFieldAuthor.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldAuthor.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldAuthor.gridx = 2;
		gbc_textFieldAuthor.gridy = 2;
		panelFilters.add(textFieldAuthor, gbc_textFieldAuthor);

		JLabel lblCategory = new JLabel("Categoria:");
		lblCategory.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblCategory = new GridBagConstraints();
		gbc_lblCategory.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblCategory.insets = new Insets(0, 0, 0, 5);
		gbc_lblCategory.gridx = 0;
		gbc_lblCategory.gridy = 4;
		panelFilters.add(lblCategory, gbc_lblCategory);

		comboBoxCategory = new JComboBox<Category>();
		comboBoxCategory.setVisible(true);
		comboBoxCategory.setEnabled(false);
		comboBoxCategory.setModel(new DefaultComboBoxModel<Category>(Category.values()));

		checkBoxCategory = new JCheckBox("");
		GridBagConstraints gbc_checkBoxCategory = new GridBagConstraints();
		gbc_checkBoxCategory.insets = new Insets(0, 0, 0, 5);
		gbc_checkBoxCategory.gridx = 1;
		gbc_checkBoxCategory.gridy = 4;
		panelFilters.add(checkBoxCategory, gbc_checkBoxCategory);
		comboBoxCategory.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_comboBoxCategory = new GridBagConstraints();
		gbc_comboBoxCategory.anchor = GridBagConstraints.NORTH;
		gbc_comboBoxCategory.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxCategory.gridx = 2;
		gbc_comboBoxCategory.gridy = 4;
		panelFilters.add(comboBoxCategory, gbc_comboBoxCategory);
		ArrayList<Book> books = new ArrayList<Book>();
		LoanBookTableModel model = new LoanBookTableModel(books);

		JPanel panelCenter = new JPanel();
		panelCenter.setPreferredSize(new Dimension(10, 480));
		panel.add(panelCenter, BorderLayout.CENTER);
		panelCenter.setLayout(new BorderLayout(0, 0));

		JPanel panelBooks = new JPanel();
		panelCenter.add(panelBooks, BorderLayout.NORTH);
		panelBooks.setPreferredSize(new Dimension(10, 498));

		tableBooks = new JTable();
		tableBooks.setModel(model);
		tableBooks.setRowSorter(new TableRowSorter<DefaultTableModel>(model));
		tableBooks.setFont(new Font("Tahoma", Font.PLAIN, 16));
		tableBooks.getTableHeader().setReorderingAllowed(false);
		tableBooks.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 16));

		tableBooks.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (tableBooks.columnAtPoint(arg0.getPoint()) != 0) {
					tableBooks.setValueAt(!(boolean) tableBooks.getValueAt(tableBooks.getSelectedRow(), 0),
							tableBooks.getSelectedRow(), 0);
				}
				if ((boolean) tableBooks.getValueAt(tableBooks.getSelectedRow(), 0)) {
					Book selectedBook = ((LoanBookTableModel) tableBooks.getModel())
							.getBookAtRow(tableBooks.getSelectedRow());
					((BookingTableModel) tableLoans.getModel()).addBooking(selectedBook, Login.getUserLogged());
				} else {
					String isbnSelectedBook = (String) tableBooks.getValueAt(tableBooks.getSelectedRow(), 3);
					for (int i = 0; i < tableLoans.getRowCount(); i++) {
						if (tableLoans.getValueAt(i, 2).toString().equals(isbnSelectedBook)) {
							((BookingTableModel) tableLoans.getModel()).removeRow(i);
							break;
						}
					}
				}
			}
		});

		panelBooks.setLayout(new BorderLayout(0, 0));
		panelPages = new JPanel();
		panelPages.setPreferredSize(new Dimension(10, 70));
		panelBooks.add(panelPages, BorderLayout.SOUTH);

		JScrollPane scrollPaneBooks = new JScrollPane(tableBooks);
		scrollPaneBooks.setPreferredSize(new Dimension(10, 420));
		panelBooks.add(scrollPaneBooks, BorderLayout.CENTER);

		worker = new TableWorker(Operation.LIBRARY_UPDATE_BOOKS, 1, null, tableBooks, tableLoans, panelPages,
				checkBoxCategory, textFieldTitle, textFieldAuthor, btnConfirm);
		worker.execute();

		btnMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				worker.cancel(true);
				GUIMainReader.frame.getContentPane().removeAll();
				GUIMainReader.frame.getContentPane().add(new PanelMainReader());
				((JPanel) GUIMainReader.frame.getContentPane()).revalidate();
				GUIMainReader.frame.setTitle("Menu lettore");
				GUIMainReader.frame.repaint();
			}
		});

		textFieldTitle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] params = { textFieldTitle.getText(), textFieldAuthor.getText(), null };
				if (checkBoxCategory.isSelected()) {
					params[2] = ((Category) comboBoxCategory.getSelectedItem()).name();
				}
				worker = new TableWorker(Operation.LIBRARY_BOOKS_FILTER, 1, params, tableBooks, tableLoans, panelPages,
						checkBoxCategory, textFieldTitle, textFieldAuthor, btnConfirm);
				worker.execute();
			}
		});

		textFieldAuthor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] params = { textFieldTitle.getText(), textFieldAuthor.getText(), null };
				if (checkBoxCategory.isSelected()) {
					params[2] = ((Category) comboBoxCategory.getSelectedItem()).name();
				}
				worker = new TableWorker(Operation.LIBRARY_BOOKS_FILTER, 1, params, tableBooks, tableLoans, panelPages,
						checkBoxCategory, textFieldTitle, textFieldAuthor, btnConfirm);
				worker.execute();
			}
		});

		comboBoxCategory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] params = { textFieldTitle.getText(), textFieldAuthor.getText(),
						((Category) comboBoxCategory.getSelectedItem()).name() };
				worker = new TableWorker(Operation.LIBRARY_BOOKS_FILTER, 1, params, tableBooks, tableLoans, panelPages,
						checkBoxCategory, textFieldTitle, textFieldAuthor, btnConfirm);
				worker.execute();
			}
		});

		checkBoxCategory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (checkBoxCategory.isSelected()) {
					comboBoxCategory.setEnabled(true);
				} else {
					comboBoxCategory.setEnabled(false);
					String[] params = { textFieldTitle.getText(), textFieldAuthor.getText(), null };
					worker = new TableWorker(Operation.LIBRARY_BOOKS_FILTER, 1, params, tableBooks, tableLoans,
							panelPages, checkBoxCategory, textFieldTitle, textFieldAuthor, btnConfirm);
					worker.execute();
				}
			}
		});
	}

}
