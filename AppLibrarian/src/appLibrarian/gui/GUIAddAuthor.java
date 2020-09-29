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

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import utils.Directory;
import utils.Utility;

/**
 * This class create the add authors interface
 *
 * @author Marco Di Capua
 * @author Mattia Lo Schiavo
 * @author Filippo Pelosi
 * @author Riccardo Zorzi
 * @version 1.0
 */
public class GUIAddAuthor extends JDialog {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private final Dimension frameDimension = new Dimension(1110, 355);
	private JTextField textFieldAuthorName;
	private JLabel lblErrorAuthorName;
	private JTextField textFieldAuthorSurname;

	/**
	 * Launch the dialog interface
	 * 
	 * @param dialog
	 *            the dialog from which the dialog is displayed
	 */
	public static void main(JDialog dialog) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIAddAuthor dialogAdd = new GUIAddAuthor(dialog);
					dialogAdd.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private GUIAddAuthor(JDialog frame) {
		super(frame, true);
		setResizable(false);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds((int) ((screenSize.getWidth() - frameDimension.getWidth()) / 2),
				(int) ((screenSize.getHeight() - frameDimension.getHeight()) / 2), frameDimension.width,
				frameDimension.height);
		setTitle("Aggiungi autori");
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
		URL imageLogoURL = getClass().getResource(Directory.PATH_IMG_MINI_LOGO);
		labelLogo.setIcon(new ImageIcon(imageLogoURL));
		panelLogo.add(labelLogo);

		JPanel panelFields = new JPanel();
		panel.add(panelFields, BorderLayout.CENTER);
		panelFields.setLayout(new BorderLayout(0, 0));

		JPanel panelFieldsNorth = new JPanel();
		panelFieldsNorth.setPreferredSize(new Dimension(775, 200));
		panelFields.add(panelFieldsNorth, BorderLayout.NORTH);
		panelFieldsNorth.setLayout(new BorderLayout(0, 0));

		JPanel panelGridFields = new JPanel();
		panelGridFields.setBorder(new TitledBorder(null, "Aggiungi gli autori del libro", TitledBorder.LEADING,
				TitledBorder.TOP, new Font("Tahoma", Font.BOLD, 18), new Color(0, 0, 0)));
		panelGridFields.setPreferredSize(new Dimension(10, 250));
		panelFieldsNorth.add(panelGridFields, BorderLayout.CENTER);
		GridBagLayout gbl_panelGridFields = new GridBagLayout();
		gbl_panelGridFields.columnWidths = new int[] { 1, 35, 102, 45, 204, 30, 354, 0 };
		gbl_panelGridFields.rowHeights = new int[] { 30, 26, 26, 26, 0, 26, 0 };
		gbl_panelGridFields.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panelGridFields.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panelGridFields.setLayout(gbl_panelGridFields);

		JLabel lblAuthorName = new JLabel("Nome:");
		lblAuthorName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblAuthorName = new GridBagConstraints();
		gbc_lblAuthorName.anchor = GridBagConstraints.WEST;
		gbc_lblAuthorName.insets = new Insets(0, 0, 5, 5);
		gbc_lblAuthorName.gridx = 2;
		gbc_lblAuthorName.gridy = 1;
		panelGridFields.add(lblAuthorName, gbc_lblAuthorName);

		textFieldAuthorName = new JTextField();
		textFieldAuthorName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textFieldAuthorName.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String author = textFieldAuthorName.getText().trim();
				textFieldAuthorName.setText(author);
				String errorAuthor = Utility.verifyNotEmptyField(author) + Utility.verifyName(author);
				lblErrorAuthorName.setText(errorAuthor.split("-")[0]);
			}
		});
		textFieldAuthorName.setColumns(5);
		GridBagConstraints gbc_textFieldAuthorName = new GridBagConstraints();
		gbc_textFieldAuthorName.anchor = GridBagConstraints.NORTH;
		gbc_textFieldAuthorName.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldAuthorName.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldAuthorName.gridx = 4;
		gbc_textFieldAuthorName.gridy = 1;
		panelGridFields.add(textFieldAuthorName, gbc_textFieldAuthorName);
		gbc_textFieldAuthorName.anchor = GridBagConstraints.NORTH;
		gbc_textFieldAuthorName.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldAuthorName.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldAuthorName.gridx = 6;
		gbc_textFieldAuthorName.gridy = 2;

		lblErrorAuthorName = new JLabel("");
		lblErrorAuthorName.setPreferredSize(new Dimension(354, 26));
		lblErrorAuthorName.setForeground(Color.RED);
		lblErrorAuthorName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblErrorAuthorName = new GridBagConstraints();
		gbc_lblErrorAuthorName.anchor = GridBagConstraints.WEST;
		gbc_lblErrorAuthorName.insets = new Insets(0, 0, 5, 0);
		gbc_lblErrorAuthorName.gridx = 6;
		gbc_lblErrorAuthorName.gridy = 1;
		panelGridFields.add(lblErrorAuthorName, gbc_lblErrorAuthorName);

		JLabel lblErrorAuthorSurname = new JLabel("");
		lblErrorAuthorSurname.setPreferredSize(new Dimension(354, 26));
		lblErrorAuthorSurname.setForeground(Color.RED);
		lblErrorAuthorSurname.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblErrorAuthorSurname = new GridBagConstraints();
		gbc_lblErrorAuthorSurname.anchor = GridBagConstraints.WEST;
		gbc_lblErrorAuthorSurname.insets = new Insets(0, 0, 5, 0);
		gbc_lblErrorAuthorSurname.gridx = 6;
		gbc_lblErrorAuthorSurname.gridy = 2;
		panelGridFields.add(lblErrorAuthorSurname, gbc_lblErrorAuthorSurname);

		JLabel lblAuthorSurname = new JLabel("Cognome:");
		lblAuthorSurname.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblAuthorSurname = new GridBagConstraints();
		gbc_lblAuthorSurname.anchor = GridBagConstraints.WEST;
		gbc_lblAuthorSurname.insets = new Insets(0, 0, 5, 5);
		gbc_lblAuthorSurname.gridx = 2;
		gbc_lblAuthorSurname.gridy = 2;
		panelGridFields.add(lblAuthorSurname, gbc_lblAuthorSurname);

		textFieldAuthorSurname = new JTextField();
		textFieldAuthorSurname.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				String author = textFieldAuthorName.getText().trim();
				textFieldAuthorName.setText(author);
				String errorAuthor = Utility.verifyNotEmptyField(author);
				lblErrorAuthorSurname.setText(errorAuthor.split("-")[0]);
			}
		});
		textFieldAuthorSurname.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textFieldAuthorSurname.setColumns(5);
		GridBagConstraints gbc_textFieldAuthorSurname = new GridBagConstraints();
		gbc_textFieldAuthorSurname.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldAuthorSurname.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldAuthorSurname.gridx = 4;
		gbc_textFieldAuthorSurname.gridy = 2;
		panelGridFields.add(textFieldAuthorSurname, gbc_textFieldAuthorSurname);

		JLabel lblAddedAuthorsDisplayer = new JLabel("");
		lblAddedAuthorsDisplayer.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblAddedAuthorsDisplayer = new GridBagConstraints();
		gbc_lblAddedAuthorsDisplayer.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblAddedAuthorsDisplayer.gridwidth = 3;
		gbc_lblAddedAuthorsDisplayer.gridx = 4;
		gbc_lblAddedAuthorsDisplayer.gridy = 4;
		panelGridFields.add(lblAddedAuthorsDisplayer, gbc_lblAddedAuthorsDisplayer);

		JLabel lblAddedAuthors = new JLabel("Autori inseriti:");
		lblAddedAuthors.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblAddedAuthors = new GridBagConstraints();
		gbc_lblAddedAuthors.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblAddedAuthors.insets = new Insets(0, 0, 5, 5);
		gbc_lblAddedAuthors.gridx = 2;
		gbc_lblAddedAuthors.gridy = 4;
		panelGridFields.add(lblAddedAuthors, gbc_lblAddedAuthors);

		JPanel panelConfirm = new JPanel();
		panelConfirm.setPreferredSize(new Dimension(10, 120));
		panelFields.add(panelConfirm, BorderLayout.SOUTH);
		panelConfirm.setLayout(new FlowLayout(FlowLayout.LEFT, 135, 20));

		JLabel lblError = new JLabel("Nota: Cliccando sul pulsante \"Aggiungi!\" potrai aggiungere un nuovo autore.");
		lblError.setForeground(Color.BLACK);
		lblError.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelConfirm.add(lblError);

		JButton btnConfirm = new JButton("Conferma!");
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				String authors = lblAddedAuthorsDisplayer.getText();
				GUIAddBook.setAuthor(authors);
			}
		});

		JButton btnCancel = new JButton("Annulla!");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelConfirm.add(btnCancel);

		JButton btnAdd = new JButton("Aggiungi!");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (lblErrorAuthorName.getText().length() == 0 && lblErrorAuthorSurname.getText().length() == 0) {
					String authorAddedName = textFieldAuthorName.getText();
					String authorAddedSurname = textFieldAuthorSurname.getText();
					String separator = lblAddedAuthorsDisplayer.getText().length() == 0 ? "" : ", ";
					lblAddedAuthorsDisplayer.setText(lblAddedAuthorsDisplayer.getText() + separator + authorAddedName
							+ " " + authorAddedSurname);
					textFieldAuthorName.setText("");
					textFieldAuthorSurname.setText("");
				}
			}
		});
		btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelConfirm.add(btnAdd);
		btnConfirm.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelConfirm.add(btnConfirm);
	}
}
