package appLibrarian.gui;

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

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import dataModel.Book;
import dataModel.Category;
import dataModel.Classification;
import tableModels.RankTableModel;
import utils.Directory;
import utils.Operation;

/**
 * This class create the books rank panel
 *
 * @author Marco Di Capua
 * @author Mattia Lo Schiavo
 * @author Filippo Pelosi
 * @author Riccardo Zorzi
 * @version 1.0
 */
public class PanelBooksRank extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTable tableRank;
	private JPanel panelPages;
	private JComboBox<Category> comboBoxCategory;
	private JComboBox<Classification> comboBoxRole;
	private static TableWorker worker = null;

	/**
	 * Create the panel
	 */
	public PanelBooksRank() {
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

		JButton button = new JButton("Menu");
		button.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelButtons.add(button);
		JPanel panelLogo = new JPanel();
		panelLogo.setBorder(null);
		panelLogo.setPreferredSize(new Dimension(10, 360));
		panel.add(panelLogo, BorderLayout.NORTH);
		panelLogo.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

		JLabel lblLogo = new JLabel("");
		lblLogo.setHorizontalTextPosition(SwingConstants.LEADING);
		URL imgLogo = getClass().getResource(Directory.PATH_IMG_LOGO_APPLIBRARIAN);
		lblLogo.setIcon(new ImageIcon(imgLogo));
		panelLogo.add(lblLogo);

		JPanel panelFilterBorder = new JPanel();
		panel.add(panelFilterBorder, BorderLayout.WEST);

		JPanel panelFilters = new JPanel();
		panelFilterBorder.add(panelFilters);
		panelFilters.setPreferredSize(new Dimension(300, 220));
		panelFilters.setBorder(new TitledBorder(null, "Filtri", TitledBorder.LEADING, TitledBorder.TOP,
				new Font("Tahoma", Font.BOLD, 18), new Color(0, 0, 0)));
		GridBagLayout gbl_panelFilters = new GridBagLayout();
		gbl_panelFilters.columnWidths = new int[] { 115, 21, 143, 0 };
		gbl_panelFilters.rowHeights = new int[] { 30, 26, 35, 26, 35, 26, 0 };
		gbl_panelFilters.columnWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panelFilters.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panelFilters.setLayout(gbl_panelFilters);

		comboBoxCategory = new JComboBox<Category>();
		comboBoxCategory.setEnabled(false);
		comboBoxCategory.setVisible(true);
		comboBoxCategory.setModel(new DefaultComboBoxModel<Category>(Category.values()));

		JLabel lblBestBooks = new JLabel("Assoluta:");
		lblBestBooks.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblBestBooks = new GridBagConstraints();
		gbc_lblBestBooks.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblBestBooks.insets = new Insets(0, 0, 5, 5);
		gbc_lblBestBooks.gridx = 0;
		gbc_lblBestBooks.gridy = 1;
		panelFilters.add(lblBestBooks, gbc_lblBestBooks);

		JCheckBox checkBoxCategory = new JCheckBox("");
		checkBoxCategory.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if (checkBoxCategory.isSelected()) {
					comboBoxCategory.setEnabled(true);
				} else {
					comboBoxCategory.setEnabled(false);
				}
			}
		});

		JCheckBox checkBoxBestBooks = new JCheckBox("");
		checkBoxBestBooks.setSelected(true);
		GridBagConstraints gbc_checkBoxBestBooks = new GridBagConstraints();
		gbc_checkBoxBestBooks.anchor = GridBagConstraints.NORTHWEST;
		gbc_checkBoxBestBooks.insets = new Insets(0, 0, 5, 5);
		gbc_checkBoxBestBooks.gridx = 1;
		gbc_checkBoxBestBooks.gridy = 1;
		panelFilters.add(checkBoxBestBooks, gbc_checkBoxBestBooks);

		JLabel lblCategory = new JLabel("Categoria:");
		lblCategory.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblCategory = new GridBagConstraints();
		gbc_lblCategory.anchor = GridBagConstraints.WEST;
		gbc_lblCategory.insets = new Insets(0, 0, 5, 5);
		gbc_lblCategory.gridx = 0;
		gbc_lblCategory.gridy = 3;
		panelFilters.add(lblCategory, gbc_lblCategory);
		GridBagConstraints gbc_checkBoxCategory = new GridBagConstraints();
		gbc_checkBoxCategory.anchor = GridBagConstraints.WEST;
		gbc_checkBoxCategory.insets = new Insets(0, 0, 5, 5);
		gbc_checkBoxCategory.gridx = 1;
		gbc_checkBoxCategory.gridy = 3;
		panelFilters.add(checkBoxCategory, gbc_checkBoxCategory);

		comboBoxCategory.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_comboBoxCategory = new GridBagConstraints();
		gbc_comboBoxCategory.anchor = GridBagConstraints.NORTH;
		gbc_comboBoxCategory.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxCategory.insets = new Insets(0, 0, 5, 0);
		gbc_comboBoxCategory.gridx = 2;
		gbc_comboBoxCategory.gridy = 3;
		panelFilters.add(comboBoxCategory, gbc_comboBoxCategory);

		JLabel lblRole = new JLabel("Inquadramento:");
		lblRole.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblRole = new GridBagConstraints();
		gbc_lblRole.anchor = GridBagConstraints.WEST;
		gbc_lblRole.insets = new Insets(0, 0, 0, 5);
		gbc_lblRole.gridx = 0;
		gbc_lblRole.gridy = 5;
		panelFilters.add(lblRole, gbc_lblRole);

		JCheckBox checkBoxRole = new JCheckBox("");
		checkBoxRole.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (checkBoxRole.isSelected()) {
					comboBoxRole.setEnabled(true);
				} else {
					comboBoxRole.setEnabled(false);
				}
			}
		});
		GridBagConstraints gbc_checkBoxRole = new GridBagConstraints();
		gbc_checkBoxRole.insets = new Insets(0, 0, 0, 5);
		gbc_checkBoxRole.gridx = 1;
		gbc_checkBoxRole.gridy = 5;
		panelFilters.add(checkBoxRole, gbc_checkBoxRole);

		comboBoxRole = new JComboBox<Classification>();
		comboBoxRole
				.setModel(new DefaultComboBoxModel<Classification>(new Classification[] { Classification.ADMINISTRATOR,
						Classification.STUDENT, Classification.TEACHER, Classification.TECHNICIAN }));
		comboBoxRole.setFont(new Font("Tahoma", Font.PLAIN, 16));
		comboBoxRole.setVisible(true);
		comboBoxRole.setEnabled(false);
		GridBagConstraints gbc_comboBoxRole = new GridBagConstraints();
		gbc_comboBoxRole.anchor = GridBagConstraints.NORTH;
		gbc_comboBoxRole.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxRole.gridx = 2;
		gbc_comboBoxRole.gridy = 5;
		panelFilters.add(comboBoxRole, gbc_comboBoxRole);

		ButtonGroup group = new ButtonGroup();
		group.add(checkBoxBestBooks);
		group.add(checkBoxCategory);
		group.add(checkBoxRole);
		ArrayList<Book> books = new ArrayList<Book>();
		RankTableModel tm = new RankTableModel(books);

		JPanel panelCenter = new JPanel();
		panelCenter.setPreferredSize(new Dimension(10, 480));
		panel.add(panelCenter, BorderLayout.CENTER);
		panelCenter.setLayout(new BorderLayout(0, 0));

		JPanel panelBooks = new JPanel();
		panelCenter.add(panelBooks, BorderLayout.NORTH);
		panelBooks.setPreferredSize(new Dimension(10, 498));

		tableRank = new JTable();
		tableRank.setModel(tm);
		tableRank.setRowSorter(new TableRowSorter<DefaultTableModel>(tm));
		tableRank.setFont(new Font("Tahoma", Font.PLAIN, 16));
		tableRank.getTableHeader().setReorderingAllowed(false);
		tableRank.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 16));

		panelBooks.setLayout(new BorderLayout(0, 0));
		panelPages = new JPanel();
		panelPages.setPreferredSize(new Dimension(10, 70));
		panelBooks.add(panelPages, BorderLayout.SOUTH);

		JScrollPane scrollPaneBooks = new JScrollPane(tableRank);
		scrollPaneBooks.setPreferredSize(new Dimension(452, 420));
		panelBooks.add(scrollPaneBooks, BorderLayout.CENTER);
		worker = new TableWorker(Operation.LIBRARY_ABSOLUTE_RANK, 1, null, tableRank, null, panelPages,
				checkBoxBestBooks, checkBoxCategory, checkBoxRole);
		worker.execute();

		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				worker.cancel(true);
				GUIMainLibrarian.frame.getContentPane().removeAll();
				GUIMainLibrarian.frame.getContentPane().add(new PanelMainAnalysis());
				((JPanel) GUIMainLibrarian.frame.getContentPane()).revalidate();
				GUIMainLibrarian.frame.setTitle("Menu analisi");
				GUIMainLibrarian.frame.repaint();
			}
		});

		comboBoxRole.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] params = new String[] { ((Classification) comboBoxRole.getSelectedItem()).name() };
				worker = new TableWorker(Operation.LIBRARY_CLASSIFICATION_RANK, 1, params, tableRank, null, panelPages,
						checkBoxBestBooks, checkBoxCategory, checkBoxRole);
				worker.execute();
			}
		});

		checkBoxBestBooks.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (checkBoxBestBooks.isSelected()) {
					worker = new TableWorker(Operation.LIBRARY_ABSOLUTE_RANK, 1, null, tableRank, null,
							panelPages, checkBoxBestBooks, checkBoxCategory, checkBoxRole);
					worker.execute();
				}
			}
		});

		comboBoxCategory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String[] params = new String[] { ((Category) comboBoxCategory.getSelectedItem()).name() };
				worker = new TableWorker(Operation.LIBRARY_CATEGORY_RANK, 1, params, tableRank, null, panelPages,
						checkBoxBestBooks, checkBoxCategory, checkBoxRole);
				worker.execute();
			}
		});

	}
}