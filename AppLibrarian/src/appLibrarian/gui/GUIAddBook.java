package appLibrarian.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.net.URL;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSpinner.NumberEditor;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import appLibrarian.Library;
import dataModel.Book;
import dataModel.Category;
import dataModel.Language;
import utils.Directory;
import utils.Utility;

/**
 * This class create the add new book interface
 *
 * @author Marco Di Capua
 * @author Mattia Lo Schiavo
 * @author Filippo Pelosi
 * @author Riccardo Zorzi
 * @version 1.0
 */
public class GUIAddBook extends JDialog {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldTitle;
	private JTextField textFieldIsbn;
	private JTextField textFieldPublisher;
	private final Dimension frameDimension = new Dimension(1100, 505);
	private JLabel lblErrorPublisher;
	private JLabel lblErrorIsbn;
	private JLabel lblErrorTitle;
	private JSpinner spinnerReprintYear;
	private static JLabel lblAuthorsText;
	private static GUIAddBook dialog;

	/**
	 * Launch the dialog interface
	 * 
	 * @param frame
	 *            the frame from which the dialog is displayed
	 */
	public static void main(GUIMainLibrarian frame) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					dialog = new GUIAddBook(frame);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private GUIAddBook(GUIMainLibrarian frame) {
		super(frame, true);
		setResizable(false);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds((int) ((screenSize.getWidth() - frameDimension.getWidth()) / 2),
				(int) ((screenSize.getHeight() - frameDimension.getHeight()) / 2), frameDimension.width,
				frameDimension.height);
		setTitle("Aggiungi libro");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBorder(null);
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		contentPane.add(scrollPane);

		JPanel panel = new JPanel();
		panel.setBorder(null);
		scrollPane.setViewportView(panel);
		panel.setLayout(new BorderLayout(0, 0));

		JPanel panelLogo = new JPanel();
		panelLogo.setPreferredSize(new Dimension(300, 10));
		panel.add(panelLogo, BorderLayout.WEST);
		panelLogo.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel labelLogo = new JLabel("");
		URL imgLogo = getClass().getResource(Directory.PATH_IMG_MINI_LOGO);
		labelLogo.setIcon(new ImageIcon(imgLogo));
		panelLogo.add(labelLogo);

		JPanel panelFields = new JPanel();
		panel.add(panelFields, BorderLayout.CENTER);
		panelFields.setLayout(new BorderLayout(0, 0));

		JPanel panelFieldsNorth = new JPanel();
		panelFieldsNorth.setPreferredSize(new Dimension(775, 340));
		panelFields.add(panelFieldsNorth, BorderLayout.CENTER);
		panelFieldsNorth.setLayout(new BorderLayout(0, 0));

		JPanel panelGridFields = new JPanel();
		panelGridFields.setBorder(new TitledBorder(null, "Dati libro", TitledBorder.LEADING, TitledBorder.TOP,
				new Font("Tahoma", Font.BOLD, 18), new Color(0, 0, 0)));
		panelGridFields.setPreferredSize(new Dimension(10, 340));
		panelFieldsNorth.add(panelGridFields, BorderLayout.CENTER);
		GridBagLayout gbl_panelGridFields = new GridBagLayout();
		gbl_panelGridFields.columnWidths = new int[] { 73, 56, 110, 120, 354, 0 };
		gbl_panelGridFields.rowHeights = new int[] { 26, 29, 26, 26, 26, 26, 26, 26, 26, 26, 0 };
		gbl_panelGridFields.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panelGridFields.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
		panelGridFields.setLayout(gbl_panelGridFields);

		JLabel lblTitle = new JLabel("Titolo:");
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblTitle = new GridBagConstraints();
		gbc_lblTitle.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblTitle.insets = new Insets(0, 0, 5, 5);
		gbc_lblTitle.gridx = 0;
		gbc_lblTitle.gridy = 1;
		panelGridFields.add(lblTitle, gbc_lblTitle);

		textFieldTitle = new JTextField();
		textFieldTitle.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textFieldTitle.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				String title = textFieldTitle.getText().trim();
				textFieldTitle.setText(title);
				String errorTitle = Utility.verifyNotEmptyField(title);
				lblErrorTitle.setText(errorTitle.split("-")[0]);
			}
		});
		textFieldTitle.setColumns(5);
		GridBagConstraints gbc_textFieldTitle = new GridBagConstraints();
		gbc_textFieldTitle.anchor = GridBagConstraints.NORTH;
		gbc_textFieldTitle.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldTitle.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldTitle.gridwidth = 2;
		gbc_textFieldTitle.gridx = 2;
		gbc_textFieldTitle.gridy = 1;
		panelGridFields.add(textFieldTitle, gbc_textFieldTitle);

		lblErrorTitle = new JLabel("");
		lblErrorTitle.setPreferredSize(new Dimension(354, 26));

		lblErrorTitle.setForeground(Color.RED);
		lblErrorTitle.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblErrorTitle = new GridBagConstraints();
		gbc_lblErrorTitle.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblErrorTitle.anchor = GridBagConstraints.NORTH;
		gbc_lblErrorTitle.insets = new Insets(0, 0, 5, 0);
		gbc_lblErrorTitle.gridx = 4;
		gbc_lblErrorTitle.gridy = 1;
		panelGridFields.add(lblErrorTitle, gbc_lblErrorTitle);

		JLabel lblAuthor = new JLabel("Autore:");
		lblAuthor.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblAuthor = new GridBagConstraints();
		gbc_lblAuthor.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblAuthor.insets = new Insets(0, 0, 5, 5);
		gbc_lblAuthor.gridx = 0;
		gbc_lblAuthor.gridy = 2;
		panelGridFields.add(lblAuthor, gbc_lblAuthor);

		JButton btnAddAuthors = new JButton("+");
		btnAddAuthors.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GUIAddAuthor.main(GUIAddBook.dialog);
			}
		});
		btnAddAuthors.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_btnAddAuthors = new GridBagConstraints();
		gbc_btnAddAuthors.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnAddAuthors.insets = new Insets(0, 0, 5, 5);
		gbc_btnAddAuthors.gridx = 1;
		gbc_btnAddAuthors.gridy = 2;
		panelGridFields.add(btnAddAuthors, gbc_btnAddAuthors);

		lblAuthorsText = new JLabel("Per aggiungere un autore clicca sul bottone \"+\"");
		lblAuthorsText.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblAuthorsText = new GridBagConstraints();
		gbc_lblAuthorsText.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblAuthorsText.insets = new Insets(0, 0, 5, 0);
		gbc_lblAuthorsText.gridwidth = 3;
		gbc_lblAuthorsText.gridx = 2;
		gbc_lblAuthorsText.gridy = 2;
		panelGridFields.add(lblAuthorsText, gbc_lblAuthorsText);

		JLabel lblIsbn = new JLabel("ISBN:");
		lblIsbn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblIsbn = new GridBagConstraints();
		gbc_lblIsbn.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblIsbn.insets = new Insets(0, 0, 5, 5);
		gbc_lblIsbn.gridx = 0;
		gbc_lblIsbn.gridy = 3;
		panelGridFields.add(lblIsbn, gbc_lblIsbn);

		textFieldIsbn = new JTextField();
		textFieldIsbn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textFieldIsbn.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String isbn = textFieldIsbn.getText().trim();
				textFieldIsbn.setText(isbn);
				String errorIsbn = Utility.verifyNotEmptyField(isbn) + Utility.verifyNumber(isbn);
				lblErrorIsbn.setText(errorIsbn.split("-")[0]);
			}
		});
		textFieldIsbn.setColumns(5);
		GridBagConstraints gbc_textFieldIsbn = new GridBagConstraints();
		gbc_textFieldIsbn.anchor = GridBagConstraints.NORTH;
		gbc_textFieldIsbn.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldIsbn.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldIsbn.gridwidth = 2;
		gbc_textFieldIsbn.gridx = 2;
		gbc_textFieldIsbn.gridy = 3;
		panelGridFields.add(textFieldIsbn, gbc_textFieldIsbn);

		lblErrorIsbn = new JLabel("");
		lblErrorIsbn.setPreferredSize(new Dimension(354, 26));
		lblErrorIsbn.setForeground(Color.RED);
		lblErrorIsbn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblErrorIsbn = new GridBagConstraints();
		gbc_lblErrorIsbn.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblErrorIsbn.anchor = GridBagConstraints.NORTH;
		gbc_lblErrorIsbn.insets = new Insets(0, 0, 5, 0);
		gbc_lblErrorIsbn.gridx = 4;
		gbc_lblErrorIsbn.gridy = 3;
		panelGridFields.add(lblErrorIsbn, gbc_lblErrorIsbn);

		JLabel lblCategory = new JLabel("Categoria:");
		lblCategory.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblCategory = new GridBagConstraints();
		gbc_lblCategory.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblCategory.insets = new Insets(0, 0, 5, 5);
		gbc_lblCategory.gridx = 0;
		gbc_lblCategory.gridy = 4;
		panelGridFields.add(lblCategory, gbc_lblCategory);

		JComboBox<Category> comboBoxCategory = new JComboBox<Category>();
		comboBoxCategory.setModel(new DefaultComboBoxModel<Category>(Category.values()));
		comboBoxCategory.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_comboBoxCategory = new GridBagConstraints();
		gbc_comboBoxCategory.anchor = GridBagConstraints.NORTH;
		gbc_comboBoxCategory.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxCategory.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxCategory.gridwidth = 2;
		gbc_comboBoxCategory.gridx = 2;
		gbc_comboBoxCategory.gridy = 4;
		panelGridFields.add(comboBoxCategory, gbc_comboBoxCategory);

		JLabel lblLanguage = new JLabel("Lingua:");
		lblLanguage.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblLanguage = new GridBagConstraints();
		gbc_lblLanguage.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblLanguage.insets = new Insets(0, 0, 5, 5);
		gbc_lblLanguage.gridx = 0;
		gbc_lblLanguage.gridy = 5;
		panelGridFields.add(lblLanguage, gbc_lblLanguage);

		JComboBox<Language> comboBoxLanguage = new JComboBox<Language>();
		comboBoxLanguage.setModel(new DefaultComboBoxModel<Language>(Language.values()));
		comboBoxLanguage.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_comboBoxLanguage = new GridBagConstraints();
		gbc_comboBoxLanguage.anchor = GridBagConstraints.NORTH;
		gbc_comboBoxLanguage.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxLanguage.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxLanguage.gridwidth = 2;
		gbc_comboBoxLanguage.gridx = 2;
		gbc_comboBoxLanguage.gridy = 5;
		panelGridFields.add(comboBoxLanguage, gbc_comboBoxLanguage);

		textFieldPublisher = new JTextField();
		textFieldPublisher.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textFieldPublisher.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String publisher = textFieldPublisher.getText().trim();
				textFieldPublisher.setText(publisher);
				String errorPublisher = Utility.verifyNotEmptyField(publisher);
				lblErrorPublisher.setText(errorPublisher.split("-")[0]);
			}
		});

		JLabel lblPublisher = new JLabel("Casa editrice:");
		lblPublisher.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblPublisher = new GridBagConstraints();
		gbc_lblPublisher.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblPublisher.insets = new Insets(0, 0, 5, 5);
		gbc_lblPublisher.gridwidth = 2;
		gbc_lblPublisher.gridx = 0;
		gbc_lblPublisher.gridy = 6;
		panelGridFields.add(lblPublisher, gbc_lblPublisher);
		textFieldPublisher.setColumns(5);
		GridBagConstraints gbc_textFieldPublisher = new GridBagConstraints();
		gbc_textFieldPublisher.anchor = GridBagConstraints.NORTH;
		gbc_textFieldPublisher.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldPublisher.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldPublisher.gridwidth = 2;
		gbc_textFieldPublisher.gridx = 2;
		gbc_textFieldPublisher.gridy = 6;
		panelGridFields.add(textFieldPublisher, gbc_textFieldPublisher);

		lblErrorPublisher = new JLabel("");
		lblErrorPublisher.setPreferredSize(new Dimension(354, 26));
		lblErrorPublisher.setForeground(Color.RED);
		lblErrorPublisher.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblErrorPublisher = new GridBagConstraints();
		gbc_lblErrorPublisher.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblErrorPublisher.insets = new Insets(0, 0, 5, 0);
		gbc_lblErrorPublisher.gridx = 4;
		gbc_lblErrorPublisher.gridy = 6;
		panelGridFields.add(lblErrorPublisher, gbc_lblErrorPublisher);

		JSpinner spinnerPublicationYaer = new JSpinner();
		spinnerPublicationYaer.setFont(new Font("Tahoma", Font.PLAIN, 16));
		spinnerPublicationYaer.setModel(new SpinnerNumberModel(new GregorianCalendar().get(Calendar.YEAR), 1900,
				new GregorianCalendar().get(Calendar.YEAR), 1));
		spinnerPublicationYaer.setEditor(new NumberEditor(spinnerPublicationYaer, "#"));
		spinnerPublicationYaer.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				spinnerReprintYear.setModel(new SpinnerNumberModel(new GregorianCalendar().get(Calendar.YEAR),
						(int) spinnerPublicationYaer.getValue(), new GregorianCalendar().get(Calendar.YEAR), 1));
			}
		});

		JLabel lblPublicationYear = new JLabel("Anno pubblicazione:");
		lblPublicationYear.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblPublicationYear = new GridBagConstraints();
		gbc_lblPublicationYear.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblPublicationYear.insets = new Insets(0, 0, 5, 5);
		gbc_lblPublicationYear.gridwidth = 2;
		gbc_lblPublicationYear.gridx = 0;
		gbc_lblPublicationYear.gridy = 7;
		panelGridFields.add(lblPublicationYear, gbc_lblPublicationYear);
		GridBagConstraints gbc_spinnerPublicationYaer = new GridBagConstraints();
		gbc_spinnerPublicationYaer.anchor = GridBagConstraints.NORTH;
		gbc_spinnerPublicationYaer.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinnerPublicationYaer.insets = new Insets(0, 0, 5, 5);
		gbc_spinnerPublicationYaer.gridwidth = 2;
		gbc_spinnerPublicationYaer.gridx = 2;
		gbc_spinnerPublicationYaer.gridy = 7;
		panelGridFields.add(spinnerPublicationYaer, gbc_spinnerPublicationYaer);

		JLabel lblReprintYear = new JLabel("Anno ristampa:");
		lblReprintYear.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblReprintYear = new GridBagConstraints();
		gbc_lblReprintYear.anchor = GridBagConstraints.NORTH;
		gbc_lblReprintYear.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblReprintYear.insets = new Insets(0, 0, 5, 5);
		gbc_lblReprintYear.gridwidth = 2;
		gbc_lblReprintYear.gridx = 0;
		gbc_lblReprintYear.gridy = 8;
		panelGridFields.add(lblReprintYear, gbc_lblReprintYear);

		spinnerReprintYear = new JSpinner();
		spinnerReprintYear.setFont(new Font("Tahoma", Font.PLAIN, 16));
		spinnerReprintYear.setModel(new SpinnerNumberModel(new GregorianCalendar().get(Calendar.YEAR),
				new GregorianCalendar().get(Calendar.YEAR), new GregorianCalendar().get(Calendar.YEAR), 1));
		spinnerReprintYear.setEditor(new NumberEditor(spinnerReprintYear, "#"));
		GridBagConstraints gbc_spinnerReprintYear = new GridBagConstraints();
		gbc_spinnerReprintYear.anchor = GridBagConstraints.NORTH;
		gbc_spinnerReprintYear.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinnerReprintYear.insets = new Insets(0, 0, 5, 5);
		gbc_spinnerReprintYear.gridwidth = 2;
		gbc_spinnerReprintYear.gridx = 2;
		gbc_spinnerReprintYear.gridy = 8;
		panelGridFields.add(spinnerReprintYear, gbc_spinnerReprintYear);

		JLabel lblShelf = new JLabel("Scaffale:");
		lblShelf.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblShelf = new GridBagConstraints();
		gbc_lblShelf.anchor = GridBagConstraints.WEST;
		gbc_lblShelf.insets = new Insets(0, 0, 5, 5);
		gbc_lblShelf.gridx = 0;
		gbc_lblShelf.gridy = 9;
		panelGridFields.add(lblShelf, gbc_lblShelf);

		JComboBox<String> comboBoxShelfLetter = new JComboBox<String>();
		comboBoxShelfLetter.setModel(new DefaultComboBoxModel<String>(new String[] { "A", "B", "C", "D", "E", "F" }));
		comboBoxShelfLetter.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_comboBoxShelfLetter = new GridBagConstraints();
		gbc_comboBoxShelfLetter.anchor = GridBagConstraints.NORTH;
		gbc_comboBoxShelfLetter.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxShelfLetter.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxShelfLetter.gridx = 2;
		gbc_comboBoxShelfLetter.gridy = 9;
		panelGridFields.add(comboBoxShelfLetter, gbc_comboBoxShelfLetter);

		JComboBox<String> comboBoxShelfNumber = new JComboBox<String>();
		comboBoxShelfNumber.setModel(new DefaultComboBoxModel<String>(new String[] { "1", "2", "3", "4", "5" }));
		comboBoxShelfNumber.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_comboBoxShelfNumber = new GridBagConstraints();
		gbc_comboBoxShelfNumber.anchor = GridBagConstraints.NORTH;
		gbc_comboBoxShelfNumber.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxShelfNumber.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxShelfNumber.gridx = 3;
		gbc_comboBoxShelfNumber.gridy = 9;
		panelGridFields.add(comboBoxShelfNumber, gbc_comboBoxShelfNumber);

		JLabel lblNumBooks = new JLabel("Quantit\u00E0 libri:");
		lblNumBooks.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblNumBooks = new GridBagConstraints();
		gbc_lblNumBooks.anchor = GridBagConstraints.NORTH;
		gbc_lblNumBooks.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNumBooks.insets = new Insets(0, 0, 0, 5);
		gbc_lblNumBooks.gridwidth = 2;
		gbc_lblNumBooks.gridx = 0;
		gbc_lblNumBooks.gridy = 10;
		panelGridFields.add(lblNumBooks, gbc_lblNumBooks);

		JSpinner spnNumBooks = new JSpinner();
		spnNumBooks.setModel(new SpinnerNumberModel(1, 1, 100, 1));
		spnNumBooks.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_spnNumBooks = new GridBagConstraints();
		gbc_spnNumBooks.anchor = GridBagConstraints.NORTH;
		gbc_spnNumBooks.fill = GridBagConstraints.HORIZONTAL;
		gbc_spnNumBooks.insets = new Insets(0, 0, 0, 5);
		gbc_spnNumBooks.gridwidth = 2;
		gbc_spnNumBooks.gridx = 2;
		gbc_spnNumBooks.gridy = 10;
		panelGridFields.add(spnNumBooks, gbc_spnNumBooks);

		JPanel panelConfirm = new JPanel();
		panelConfirm.setPreferredSize(new Dimension(10, 70));
		panelFields.add(panelConfirm, BorderLayout.SOUTH);
		panelConfirm.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 20));

		JLabel lblError = new JLabel(
				"Uno o pi\u00F9 campi non sono stati compilati correttamente e devono essere corretti per continuare!");
		panelConfirm.add(lblError);
		lblError.setVisible(false);
		lblError.setForeground(Color.RED);
		lblError.setFont(new Font("Tahoma", Font.PLAIN, 16));

		JButton btnConfirm = new JButton("Aggiungi!");
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (lblErrorTitle.getText().length() == 0 && (lblAuthorsText.getText().length() != 0
						&& !lblAuthorsText.getText().equals("Per aggiungere un autore clicca sul bottone \"+\""))
						&& lblErrorIsbn.getText().length() == 0 && lblErrorPublisher.getText().length() == 0) {
					lblError.setVisible(false);
					String shelf = comboBoxShelfLetter.getSelectedItem().toString() + " - "
							+ comboBoxShelfNumber.getSelectedItem().toString();
					int count = (int) spnNumBooks.getValue();
					int isbnCode = Integer.parseInt(textFieldIsbn.getText());
					String isbn = String.format("%013d", isbnCode);
					while (count > 0) {
						Book book = new Book(isbn, textFieldTitle.getText(), lblAuthorsText.getText(),
								textFieldPublisher.getText(), (int) spinnerPublicationYaer.getValue(),
								(int) spinnerReprintYear.getValue(), (Category) comboBoxCategory.getSelectedItem(),
								(Language) comboBoxLanguage.getSelectedItem(), shelf, 0);
						String response = Library.instance().addBook(book);
						if (response != null) {
							if (response.equals("Operation completed")) {
								JOptionPane.showMessageDialog(null, "Il libro è stato aggiunto alla libreria!",
										"Aggiunta libro", JOptionPane.INFORMATION_MESSAGE);
								dispose();
							} else {
								JOptionPane.showMessageDialog(null,
										"Non è stato possibile aggiungere il libro alla libreria! (Errore: " + response
												+ ")",
										"Errore", JOptionPane.INFORMATION_MESSAGE);
							}
						} else {
							break;
						}
						isbn = String.format("%013d", ++isbnCode);
						count--;
					}
				} else {
					lblError.setVisible(true);
				}
			}
		});
		btnConfirm.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelConfirm.add(btnConfirm);

	}

	public static void setAuthor(String authors) {
		lblAuthorsText.setText(authors);
	}
}
