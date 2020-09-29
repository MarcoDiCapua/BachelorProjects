package appReader.gui;

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

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import utils.Directory;
import utils.Utility;
import appReader.Proxy;
import dataModel.Classification;
import dataModel.User;

/**
 * This class create the registration new user interface
 *
 * @author Marco Di Capua
 * @author Mattia Lo Schiavo
 * @author Filippo Pelosi
 * @author Riccardo Zorzi
 * @version 1.0
 */
public class GUIRegistrationReader extends JDialog {
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JTextField textFieldName;
	private JTextField textFieldSurname;
	private JTextField textFieldID;
	private JTextField textFieldPhone;
	private JTextField textFieldEmail;
	private JPasswordField passwordFieldPassword;
	private final Dimension frameDimension = new Dimension(1010, 700);
	private JLabel lblErrorName;
	private JLabel lblErrorSurname;
	private JLabel lblErrorID;
	private JLabel lblErrorPhone;
	private JLabel lblErrorEmail;

	/**
	 * Launch the dialog interface
	 * 
	 * @param frame
	 *            the frame from which the dialog is displayed
	 */
	public static void main(JFrame frame) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIRegistrationReader dialog = new GUIRegistrationReader(frame);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private GUIRegistrationReader(JFrame frame) {
		super(frame, true);
		setResizable(false);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds((int) ((screenSize.getWidth() - frameDimension.getWidth()) / 2),
				(int) ((screenSize.getHeight() - frameDimension.getHeight()) / 2), frameDimension.width,
				frameDimension.height);
		setTitle("Registrazione");
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
		panelLogo.setBorder(null);
		panelLogo.setPreferredSize(new Dimension(10, 280));
		panel.add(panelLogo, BorderLayout.NORTH);
		panelLogo.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

		JLabel lblLogo = new JLabel("");
		URL imgLogo = getClass().getResource(Directory.PATH_IMG_LOGO_APPREADER);
		lblLogo.setIcon(new ImageIcon(imgLogo));
		lblLogo.setPreferredSize(new Dimension(1000, 280));
		panelLogo.add(lblLogo);

		JPanel panelFields = new JPanel();
		panel.add(panelFields, BorderLayout.CENTER);
		panelFields.setLayout(new BorderLayout(0, 0));

		JPanel panelFieldsNorth = new JPanel();
		panelFieldsNorth.setPreferredSize(new Dimension(775, 300));
		panelFields.add(panelFieldsNorth, BorderLayout.CENTER);
		panelFieldsNorth.setLayout(new BorderLayout(0, 0));

		JPanel panelGridFields = new JPanel();
		panelGridFields.setBorder(new TitledBorder(null, "Dati utente", TitledBorder.LEADING, TitledBorder.TOP,
				new Font("Tahoma", Font.BOLD, 18), new Color(0, 0, 0)));
		panelFieldsNorth.add(panelGridFields, BorderLayout.CENTER);
		GridBagLayout gbl_panelGridFields = new GridBagLayout();
		gbl_panelGridFields.columnWidths = new int[] { 115, 173, 74, 36, 173, 0 };
		gbl_panelGridFields.rowHeights = new int[] { 26, 26, 26, 26, 26, 26, 26, 31, 26, 20, 0, 0 };
		gbl_panelGridFields.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panelGridFields.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		panelGridFields.setLayout(gbl_panelGridFields);

		textFieldSurname = new JTextField();
		textFieldSurname.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textFieldSurname.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String surname = textFieldSurname.getText().trim();
				textFieldSurname.setText(surname);
				String errorSurname = Utility.verifyNotEmptyField(surname) + Utility.verifyName(surname);
				lblErrorSurname.setText(errorSurname.split("-")[0]);
			}
		});

		textFieldName = new JTextField();
		textFieldName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textFieldName.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String name = textFieldName.getText().trim();
				textFieldName.setText(name);
				String errorName = Utility.verifyNotEmptyField(name) + Utility.verifyName(name);
				lblErrorName.setText(errorName.split("-")[0]);

			}
		});

		JLabel lblName = new JLabel("Nome:");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.anchor = GridBagConstraints.WEST;
		gbc_lblName.insets = new Insets(0, 0, 5, 5);
		gbc_lblName.gridx = 0;
		gbc_lblName.gridy = 0;
		panelGridFields.add(lblName, gbc_lblName);
		GridBagConstraints gbc_textFieldName = new GridBagConstraints();
		gbc_textFieldName.anchor = GridBagConstraints.NORTH;
		gbc_textFieldName.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldName.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldName.gridx = 1;
		gbc_textFieldName.gridy = 0;
		panelGridFields.add(textFieldName, gbc_textFieldName);
		textFieldName.setColumns(5);

		JLabel lblSurname = new JLabel("Cognome:");
		lblSurname.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblSurname = new GridBagConstraints();
		gbc_lblSurname.anchor = GridBagConstraints.WEST;
		gbc_lblSurname.insets = new Insets(0, 0, 5, 5);
		gbc_lblSurname.gridx = 2;
		gbc_lblSurname.gridy = 0;
		panelGridFields.add(lblSurname, gbc_lblSurname);
		textFieldSurname.setColumns(5);
		GridBagConstraints gbc_textFieldSurname = new GridBagConstraints();
		gbc_textFieldSurname.anchor = GridBagConstraints.NORTH;
		gbc_textFieldSurname.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldSurname.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldSurname.gridwidth = 2;
		gbc_textFieldSurname.gridx = 3;
		gbc_textFieldSurname.gridy = 0;
		panelGridFields.add(textFieldSurname, gbc_textFieldSurname);

		lblErrorName = new JLabel("");
		lblErrorName.setPreferredSize(new Dimension(293, 26));
		lblErrorName.setForeground(Color.RED);
		lblErrorName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblErrorName = new GridBagConstraints();
		gbc_lblErrorName.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblErrorName.insets = new Insets(0, 0, 5, 5);
		gbc_lblErrorName.gridwidth = 2;
		gbc_lblErrorName.gridx = 0;
		gbc_lblErrorName.gridy = 1;
		panelGridFields.add(lblErrorName, gbc_lblErrorName);

		lblErrorSurname = new JLabel("");
		lblErrorSurname.setPreferredSize(new Dimension(293, 26));

		lblErrorSurname.setForeground(Color.RED);
		lblErrorSurname.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblErrorSurname = new GridBagConstraints();
		gbc_lblErrorSurname.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblErrorSurname.insets = new Insets(0, 0, 5, 0);
		gbc_lblErrorSurname.gridwidth = 3;
		gbc_lblErrorSurname.gridx = 2;
		gbc_lblErrorSurname.gridy = 1;
		panelGridFields.add(lblErrorSurname, gbc_lblErrorSurname);

		JLabel lblID = new JLabel("Codice fiscale:");
		lblID.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblID = new GridBagConstraints();
		gbc_lblID.anchor = GridBagConstraints.WEST;
		gbc_lblID.insets = new Insets(0, 0, 5, 5);
		gbc_lblID.gridx = 0;
		gbc_lblID.gridy = 2;
		panelGridFields.add(lblID, gbc_lblID);

		textFieldID = new JTextField();
		textFieldID.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textFieldID.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String id = textFieldID.getText().trim();
				textFieldID.setText(id);
				String errorId = Utility.verifyNotEmptyField(id) + Utility.verifyFiscalCodeLength(id)
						+ Utility.verifyUppercaseLettersAndNumbers(id);
				lblErrorID.setText(errorId.split("-")[0]);
			}
		});
		textFieldID.setColumns(5);
		GridBagConstraints gbc_textFieldID = new GridBagConstraints();
		gbc_textFieldID.anchor = GridBagConstraints.NORTH;
		gbc_textFieldID.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldID.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldID.gridx = 1;
		gbc_textFieldID.gridy = 2;
		panelGridFields.add(textFieldID, gbc_textFieldID);

		JLabel lblPhone = new JLabel("Telefono:");
		lblPhone.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblPhone = new GridBagConstraints();
		gbc_lblPhone.anchor = GridBagConstraints.WEST;
		gbc_lblPhone.insets = new Insets(0, 0, 5, 5);
		gbc_lblPhone.gridx = 2;
		gbc_lblPhone.gridy = 2;
		panelGridFields.add(lblPhone, gbc_lblPhone);

		textFieldPhone = new JTextField();
		textFieldPhone.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textFieldPhone.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String phone = textFieldPhone.getText().trim();
				textFieldPhone.setText(phone);
				String errorPhone = Utility.verifyNotEmptyField(phone) + Utility.verifyTelephoneLength(phone)
						+ Utility.verifyNumber(phone);
				lblErrorPhone.setText(errorPhone.split("-")[0]);

			}
		});
		textFieldPhone.setColumns(5);
		GridBagConstraints gbc_textFieldPhone = new GridBagConstraints();
		gbc_textFieldPhone.anchor = GridBagConstraints.NORTH;
		gbc_textFieldPhone.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldPhone.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldPhone.gridwidth = 2;
		gbc_textFieldPhone.gridx = 3;
		gbc_textFieldPhone.gridy = 2;
		panelGridFields.add(textFieldPhone, gbc_textFieldPhone);

		lblErrorID = new JLabel("");
		lblErrorID.setPreferredSize(new Dimension(293, 26));
		lblErrorID.setForeground(Color.RED);
		lblErrorID.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblErrorID = new GridBagConstraints();
		gbc_lblErrorID.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblErrorID.insets = new Insets(0, 0, 5, 5);
		gbc_lblErrorID.gridwidth = 2;
		gbc_lblErrorID.gridx = 0;
		gbc_lblErrorID.gridy = 3;
		panelGridFields.add(lblErrorID, gbc_lblErrorID);

		lblErrorPhone = new JLabel("");
		lblErrorPhone.setPreferredSize(new Dimension(293, 26));
		lblErrorPhone.setForeground(Color.RED);
		lblErrorPhone.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblErrorPhone = new GridBagConstraints();
		gbc_lblErrorPhone.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblErrorPhone.insets = new Insets(0, 0, 5, 0);
		gbc_lblErrorPhone.gridwidth = 3;
		gbc_lblErrorPhone.gridx = 2;
		gbc_lblErrorPhone.gridy = 3;
		panelGridFields.add(lblErrorPhone, gbc_lblErrorPhone);

		JLabel lblErrorPassword = new JLabel("");
		lblErrorPassword.setPreferredSize(new Dimension(293, 26));
		lblErrorPassword.setForeground(Color.RED);
		lblErrorPassword.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblErrorPassword = new GridBagConstraints();
		gbc_lblErrorPassword.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblErrorPassword.insets = new Insets(0, 0, 5, 0);
		gbc_lblErrorPassword.gridwidth = 3;
		gbc_lblErrorPassword.gridx = 2;
		gbc_lblErrorPassword.gridy = 5;
		panelGridFields.add(lblErrorPassword, gbc_lblErrorPassword);

		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblEmail = new GridBagConstraints();
		gbc_lblEmail.anchor = GridBagConstraints.WEST;
		gbc_lblEmail.insets = new Insets(0, 0, 5, 5);
		gbc_lblEmail.gridx = 0;
		gbc_lblEmail.gridy = 4;
		panelGridFields.add(lblEmail, gbc_lblEmail);

		passwordFieldPassword = new JPasswordField();
		passwordFieldPassword.setFont(new Font("Tahoma", Font.PLAIN, 16));
		passwordFieldPassword.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String pwd = new String(passwordFieldPassword.getPassword());
				String errorPwd = Utility.verifyNotEmptyField(pwd) + Utility.verifyPasswordLength(pwd)
						+ Utility.verifyLettersAndNumbers(pwd);
				lblErrorPassword.setText(errorPwd.split("-")[0]);

			}
		});

		textFieldEmail = new JTextField();
		textFieldEmail.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textFieldEmail.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String email = textFieldEmail.getText().trim();
				textFieldEmail.setText(email);
				String errorEmail = Utility.verifyNotEmptyField(email) + Utility.verifyLettersAndNumbers(email)
						+ Utility.verifyEmail(email);
				lblErrorEmail.setText(errorEmail.split("-")[0]);
			}
		});
		textFieldEmail.setColumns(5);
		GridBagConstraints gbc_textFieldEmail = new GridBagConstraints();
		gbc_textFieldEmail.anchor = GridBagConstraints.NORTH;
		gbc_textFieldEmail.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldEmail.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldEmail.gridx = 1;
		gbc_textFieldEmail.gridy = 4;
		panelGridFields.add(textFieldEmail, gbc_textFieldEmail);

		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblPassword = new GridBagConstraints();
		gbc_lblPassword.anchor = GridBagConstraints.EAST;
		gbc_lblPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblPassword.gridx = 2;
		gbc_lblPassword.gridy = 4;
		panelGridFields.add(lblPassword, gbc_lblPassword);
		passwordFieldPassword.setColumns(5);
		GridBagConstraints gbc_passwordFieldPassword = new GridBagConstraints();
		gbc_passwordFieldPassword.anchor = GridBagConstraints.NORTH;
		gbc_passwordFieldPassword.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordFieldPassword.insets = new Insets(0, 0, 5, 0);
		gbc_passwordFieldPassword.gridwidth = 2;
		gbc_passwordFieldPassword.gridx = 3;
		gbc_passwordFieldPassword.gridy = 4;
		panelGridFields.add(passwordFieldPassword, gbc_passwordFieldPassword);

		lblErrorEmail = new JLabel("");
		lblErrorEmail.setPreferredSize(new Dimension(293, 26));
		lblErrorEmail.setForeground(Color.RED);
		lblErrorEmail.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblErrorEmail = new GridBagConstraints();
		gbc_lblErrorEmail.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblErrorEmail.insets = new Insets(0, 0, 5, 5);
		gbc_lblErrorEmail.gridwidth = 2;
		gbc_lblErrorEmail.gridx = 0;
		gbc_lblErrorEmail.gridy = 5;
		panelGridFields.add(lblErrorEmail, gbc_lblErrorEmail);

		JLabel lblClassroom = new JLabel("Classe:");
		lblClassroom.setVisible(false);
		lblClassroom.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblClassroom = new GridBagConstraints();
		gbc_lblClassroom.anchor = GridBagConstraints.WEST;
		gbc_lblClassroom.insets = new Insets(0, 0, 5, 5);
		gbc_lblClassroom.gridx = 0;
		gbc_lblClassroom.gridy = 8;
		panelGridFields.add(lblClassroom, gbc_lblClassroom);

		JLabel lblRole = new JLabel("Inquadramento:");
		lblRole.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblRole = new GridBagConstraints();
		gbc_lblRole.anchor = GridBagConstraints.WEST;
		gbc_lblRole.insets = new Insets(0, 0, 5, 5);
		gbc_lblRole.gridx = 0;
		gbc_lblRole.gridy = 6;
		panelGridFields.add(lblRole, gbc_lblRole);

		JComboBox<String> comboBoxClassroom = new JComboBox<String>();
		comboBoxClassroom.setVisible(false);
		comboBoxClassroom.setModel(new DefaultComboBoxModel<String>(new String[] { "1", "2", "3", "4", "5" }));
		comboBoxClassroom.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_comboBoxClassroom = new GridBagConstraints();
		gbc_comboBoxClassroom.anchor = GridBagConstraints.NORTH;
		gbc_comboBoxClassroom.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxClassroom.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxClassroom.gridx = 1;
		gbc_comboBoxClassroom.gridy = 8;
		panelGridFields.add(comboBoxClassroom, gbc_comboBoxClassroom);

		JLabel lblDepartment = new JLabel("Sezione:");
		lblDepartment.setVisible(false);
		lblDepartment.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblDepartment = new GridBagConstraints();
		gbc_lblDepartment.anchor = GridBagConstraints.WEST;
		gbc_lblDepartment.insets = new Insets(0, 0, 5, 5);
		gbc_lblDepartment.gridx = 2;
		gbc_lblDepartment.gridy = 8;
		panelGridFields.add(lblDepartment, gbc_lblDepartment);

		JComboBox<String> comboBoxDepartment = new JComboBox<String>();
		comboBoxDepartment.setVisible(false);
		comboBoxDepartment.setModel(new DefaultComboBoxModel<String>(new String[] { "A", "B", "C" }));
		comboBoxDepartment.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_comboBoxDepartment = new GridBagConstraints();
		gbc_comboBoxDepartment.anchor = GridBagConstraints.NORTH;
		gbc_comboBoxDepartment.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxDepartment.insets = new Insets(0, 0, 5, 0);
		gbc_comboBoxDepartment.gridx = 3;
		gbc_comboBoxDepartment.gridy = 8;
		panelGridFields.add(comboBoxDepartment, gbc_comboBoxDepartment);

		JComboBox<Classification> comboBoxClassification = new JComboBox<Classification>();
		comboBoxClassification.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean bln = ((Classification) comboBoxClassification.getSelectedItem())
						.equals(Classification.STUDENT);
				lblClassroom.setVisible(bln);
				comboBoxClassroom.setVisible(bln);
				lblDepartment.setVisible(bln);
				comboBoxDepartment.setVisible(bln);
			}
		});
		comboBoxClassification.setFont(new Font("Tahoma", Font.PLAIN, 16));
		comboBoxClassification
				.setModel(new DefaultComboBoxModel<Classification>(new Classification[] { Classification.ADMINISTRATOR,
						Classification.STUDENT, Classification.TEACHER, Classification.TECHNICIAN }));
		GridBagConstraints gbc_comboBoxClassification = new GridBagConstraints();
		gbc_comboBoxClassification.anchor = GridBagConstraints.NORTH;
		gbc_comboBoxClassification.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxClassification.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxClassification.gridx = 1;
		gbc_comboBoxClassification.gridy = 6;
		panelGridFields.add(comboBoxClassification, gbc_comboBoxClassification);

		JLabel lblText = new JLabel("Per proseguire tutti i campi devono essere compilati correttamente.");
		lblText.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblText = new GridBagConstraints();
		gbc_lblText.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblText.gridwidth = 6;
		gbc_lblText.gridx = 0;
		gbc_lblText.gridy = 10;
		panelGridFields.add(lblText, gbc_lblText);

		JPanel panelConfirm = new JPanel();
		panelConfirm.setPreferredSize(new Dimension(10, 40));
		panelFields.add(panelConfirm, BorderLayout.SOUTH);
		panelConfirm.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));

		JLabel lblError = new JLabel(
				"Uno o pi\u00F9 campi non sono stati compilati correttamente e devono essere corretti per continuare!");
		lblError.setVisible(false);
		lblError.setForeground(Color.RED);
		lblError.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelConfirm.add(lblError);

		JButton btnConfirm = new JButton("Registrati!");
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (lblErrorName.getText().length() == 0 && lblErrorSurname.getText().length() == 0
						&& lblErrorID.getText().length() == 0 && lblErrorPhone.getText().length() == 0
						&& lblErrorEmail.getText().length() == 0 && lblErrorPassword.getText().length() == 0) {
					lblError.setVisible(false);
					Classification classification = (Classification) comboBoxClassification.getSelectedItem();
					String year_class = null;
					if (classification.equals(Classification.STUDENT)) {
						year_class = comboBoxClassroom.getSelectedItem().toString() + "-"
								+ comboBoxDepartment.getSelectedItem().toString();
					}
					User newUser = new User(textFieldName.getText(), textFieldSurname.getText(), textFieldID.getText(),
							textFieldEmail.getText(), classification, year_class, textFieldPhone.getText(),
							new String(passwordFieldPassword.getPassword()), false, null, 5);

					if (Proxy.instance().registration(newUser).equals("Operation completed")) {
						JOptionPane.showMessageDialog(null,
								"Sei stato registrato, è ora possibile effettuare il login!", "Registrazione",
								JOptionPane.INFORMATION_MESSAGE);
						dispose();
					} else {
						JOptionPane.showMessageDialog(null,
								"C'è un problema nei dati, non è stato possibile effettuare la registrazione!",
								"Errore!", JOptionPane.ERROR_MESSAGE);
					}
				} else {
					lblError.setVisible(true);
				}
			}
		});
		btnConfirm.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelConfirm.add(btnConfirm);
	}
}
