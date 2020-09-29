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
import java.net.URL;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import dataModel.Category;
import tableModels.AllBooksTableModel;
import utils.Directory;
import dataModel.Book;
import utils.Operation;
import appReader.gui.GUIMainReader;
import appReader.gui.TableWorker;

/**
 * This class create the all books list panel
 *
 * @author Marco Di Capua
 * @author Mattia Lo Schiavo
 * @author Filippo Pelosi
 * @author Riccardo Zorzi
 * @version 1.0
 */
public class PanelAllBooksList extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField textFieldTitle;
	private JTable tableBooks;
	private JTextField textFieldAuthor;
	private JComboBox<Category> comboBoxCategory;
	private JPanel panelPages;
	private JCheckBox checkBoxCategory;
	private static TableWorker worker = null;

	/**
	 * Create the panel
	 */
	public PanelAllBooksList() {
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
		panelButtons.setPreferredSize(new Dimension(10, 60));
		panel.add(panelButtons, BorderLayout.SOUTH);
		panelButtons.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JButton button = new JButton("Menu");
		button.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelButtons.add(button);
		JPanel panelLogo = new JPanel();
		panelLogo.setBorder(null);
		panelLogo.setPreferredSize(new Dimension(10, 370));
		panel.add(panelLogo, BorderLayout.NORTH);
		panelLogo.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

		JLabel lblLogo = new JLabel("");
		lblLogo.setHorizontalTextPosition(SwingConstants.LEADING);
		URL imgLogo = getClass().getResource(Directory.PATH_IMG_LOGO_APPREADER);
		lblLogo.setIcon(new ImageIcon(imgLogo));
		panelLogo.add(lblLogo);

		JPanel panelFiltersBorder = new JPanel();
		panel.add(panelFiltersBorder, BorderLayout.WEST);

		JPanel panelFilters = new JPanel();
		panelFiltersBorder.add(panelFilters);
		panelFilters.setPreferredSize(new Dimension(300, 220));
		panelFilters.setBorder(new TitledBorder(null, "Filtri", TitledBorder.LEADING, TitledBorder.TOP,
				new Font("Tahoma", Font.BOLD, 18), new Color(0, 0, 0)));
		GridBagLayout gbl_panelFilters = new GridBagLayout();
		gbl_panelFilters.columnWidths = new int[] { 73, 73, 141, 0 };
		gbl_panelFilters.rowHeights = new int[] { 26, 35, 26, 35, 26, 0 };
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
		comboBoxCategory.setEnabled(false);
		comboBoxCategory.setVisible(true);
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
		AllBooksTableModel model = new AllBooksTableModel(books);

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

		panelBooks.setLayout(new BorderLayout(0, 0));
		panelPages = new JPanel();
		panelPages.setPreferredSize(new Dimension(10, 70));
		panelBooks.add(panelPages, BorderLayout.SOUTH);

		JScrollPane scrollPaneBooks = new JScrollPane(tableBooks);
		scrollPaneBooks.setPreferredSize(new Dimension(10, 420));
		panelBooks.add(scrollPaneBooks, BorderLayout.CENTER);

		worker = new TableWorker(Operation.LIBRARY_UPDATE_BOOKS, 1, null, tableBooks, null, panelPages,
				checkBoxCategory, textFieldAuthor, textFieldTitle);
		worker.execute();

		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				worker.cancel(true);
				GUIMainReader.frame.getContentPane().removeAll();
				GUIMainReader.frame.getContentPane().add(new PanelMainReader());
				((JPanel) GUIMainReader.frame.getContentPane()).revalidate();
				GUIMainReader.frame.setTitle("Menu principale");
				GUIMainReader.frame.repaint();
			}
		});

		textFieldTitle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String[] params = { textFieldTitle.getText(), textFieldAuthor.getText(), null };
				if (checkBoxCategory.isSelected()) {
					params[2] = ((Category) comboBoxCategory.getSelectedItem()).name();
				}
				worker = new TableWorker(Operation.LIBRARY_BOOKS_FILTER, 1, params, tableBooks, null, panelPages,
						checkBoxCategory, textFieldTitle, textFieldAuthor);
				worker.execute();
			}
		});

		textFieldAuthor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] params = { textFieldTitle.getText(), textFieldAuthor.getText(), null };
				if (checkBoxCategory.isSelected()) {
					params[2] = ((Category) comboBoxCategory.getSelectedItem()).name();
				}
				worker = new TableWorker(Operation.LIBRARY_BOOKS_FILTER, 1, params, tableBooks, null, panelPages,
						checkBoxCategory, textFieldTitle, textFieldAuthor);
				worker.execute();
			}
		});

		comboBoxCategory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String[] params = { textFieldTitle.getText(), textFieldAuthor.getText(),
						((Category) comboBoxCategory.getSelectedItem()).name() };
				worker = new TableWorker(Operation.LIBRARY_BOOKS_FILTER, 1, params, tableBooks, null, panelPages,
						checkBoxCategory, textFieldTitle, textFieldAuthor);
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
					worker = new TableWorker(Operation.LIBRARY_BOOKS_FILTER, 1, params, tableBooks, null, panelPages,
							checkBoxCategory, textFieldTitle, textFieldAuthor);
					worker.execute();
				}
			}

		});
	}

}
