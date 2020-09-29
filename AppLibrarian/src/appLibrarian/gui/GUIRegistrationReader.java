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
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import appLibrarian.Proxy;
import dataModel.Classification;
import dataModel.User;
import utils.Directory;
import utils.Utility;

public class GUIRegistrationReader extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JTextField textFieldPhone;
	private JTextField textFieldName;
	private JTextField textFieldSurname;
	private JTextField textFieldID;
	private JTextField textFieldEmail;
	private final Dimension frameDimension = new Dimension(1010, 700);
	private JLabel lblErrorName;
	private JLabel lblErrorSurname;
	private JLabel lblErrorID;
	private JLabel lblErrorEmail;
	private JLabel lblErrorPhone;
	private JComboBox<String> comboBoxClassroom;
	private JComboBox<String> comboBoxDepartment;
	private JLabel lblClassroom;
	private JLabel lblDepartment;

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
		setTitle("Registrazione lettore");
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
		URL imgLogo = getClass().getResource(Directory.PATH_IMG_LOGO_APPLIBRARIAN);
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
		panelGridFields.setPreferredSize(new Dimension(10, 300));
		panelFieldsNorth.add(panelGridFields, BorderLayout.NORTH);
		GridBagLayout gbl_panelGridFields = new GridBagLayout();
		gbl_panelGridFields.columnWidths = new int[] { 101, 187, 115, 187, 111, 0 };
		gbl_panelGridFields.rowHeights = new int[] { 26, 26, 26, 26, 26, 26, 26, 20, 20, 0 };
		gbl_panelGridFields.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panelGridFields.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panelGridFields.setLayout(gbl_panelGridFields);

		JLabel lblName = new JLabel("Nome:");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblName.insets = new Insets(0, 0, 5, 5);
		gbc_lblName.gridx = 0;
		gbc_lblName.gridy = 0;
		panelGridFields.add(lblName, gbc_lblName);

		lblClassroom = new JLabel("Classe:");
		lblClassroom.setVisible(false);

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
		GridBagConstraints gbc_textFieldName = new GridBagConstraints();
		gbc_textFieldName.anchor = GridBagConstraints.NORTH;
		gbc_textFieldName.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldName.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldName.gridx = 1;
		gbc_textFieldName.gridy = 0;
		panelGridFields.add(textFieldName, gbc_textFieldName);
		textFieldName.setColumns(5);

		textFieldSurname = new JTextField();
		textFieldSurname.setMinimumSize(new Dimension(300, 22));
		textFieldSurname.setPreferredSize(new Dimension(300, 22));
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

		JLabel lblSurname = new JLabel("Cognome:");
		lblSurname.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblSurname = new GridBagConstraints();
		gbc_lblSurname.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblSurname.insets = new Insets(0, 0, 5, 5);
		gbc_lblSurname.gridx = 2;
		gbc_lblSurname.gridy = 0;
		panelGridFields.add(lblSurname, gbc_lblSurname);
		textFieldSurname.setColumns(5);
		GridBagConstraints gbc_textFieldSurname = new GridBagConstraints();
		gbc_textFieldSurname.fill = GridBagConstraints.BOTH;
		gbc_textFieldSurname.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldSurname.gridx = 3;
		gbc_textFieldSurname.gridy = 0;
		panelGridFields.add(textFieldSurname, gbc_textFieldSurname);

		lblErrorName = new JLabel("");
		lblErrorName.setPreferredSize(new Dimension(293, 26));
		lblErrorName.setForeground(Color.RED);
		lblErrorName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblErrorName = new GridBagConstraints();
		gbc_lblErrorName.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblErrorName.anchor = GridBagConstraints.NORTH;
		gbc_lblErrorName.insets = new Insets(0, 0, 5, 5);
		gbc_lblErrorName.gridwidth = 2;
		gbc_lblErrorName.gridx = 0;
		gbc_lblErrorName.gridy = 1;
		panelGridFields.add(lblErrorName, gbc_lblErrorName);

		textFieldPhone = new JTextField();
		textFieldPhone.setMinimumSize(new Dimension(400, 22));
		textFieldPhone.setPreferredSize(new Dimension(400, 22));
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

		lblErrorSurname = new JLabel("");
		lblErrorSurname.setPreferredSize(new Dimension(293, 26));
		lblErrorSurname.setForeground(Color.RED);
		lblErrorSurname.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblErrorSurname = new GridBagConstraints();
		gbc_lblErrorSurname.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblErrorSurname.anchor = GridBagConstraints.NORTH;
		gbc_lblErrorSurname.insets = new Insets(0, 0, 5, 5);
		gbc_lblErrorSurname.gridwidth = 2;
		gbc_lblErrorSurname.gridx = 2;
		gbc_lblErrorSurname.gridy = 1;
		panelGridFields.add(lblErrorSurname, gbc_lblErrorSurname);

		JLabel lblID = new JLabel("Codice fiscale:");
		lblID.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblID = new GridBagConstraints();
		gbc_lblID.anchor = GridBagConstraints.NORTHWEST;
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
		gbc_lblPhone.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblPhone.insets = new Insets(0, 0, 5, 5);
		gbc_lblPhone.gridx = 2;
		gbc_lblPhone.gridy = 2;
		panelGridFields.add(lblPhone, gbc_lblPhone);
		textFieldPhone.setColumns(5);
		GridBagConstraints gbc_textFieldPhone = new GridBagConstraints();
		gbc_textFieldPhone.fill = GridBagConstraints.BOTH;
		gbc_textFieldPhone.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldPhone.gridx = 3;
		gbc_textFieldPhone.gridy = 2;
		panelGridFields.add(textFieldPhone, gbc_textFieldPhone);

		lblErrorID = new JLabel("");
		lblErrorID.setPreferredSize(new Dimension(293, 26));
		lblErrorID.setForeground(Color.RED);
		lblErrorID.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblErrorID = new GridBagConstraints();
		gbc_lblErrorID.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblErrorID.anchor = GridBagConstraints.NORTH;
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
		gbc_lblErrorPhone.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblErrorPhone.anchor = GridBagConstraints.NORTH;
		gbc_lblErrorPhone.insets = new Insets(0, 0, 5, 5);
		gbc_lblErrorPhone.gridwidth = 2;
		gbc_lblErrorPhone.gridx = 2;
		gbc_lblErrorPhone.gridy = 3;
		panelGridFields.add(lblErrorPhone, gbc_lblErrorPhone);

		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblEmail = new GridBagConstraints();
		gbc_lblEmail.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblEmail.insets = new Insets(0, 0, 5, 5);
		gbc_lblEmail.gridx = 0;
		gbc_lblEmail.gridy = 4;
		panelGridFields.add(lblEmail, gbc_lblEmail);

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

		JLabel lblRole = new JLabel("Inquadramento:");
		lblRole.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblRole = new GridBagConstraints();
		gbc_lblRole.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblRole.insets = new Insets(0, 0, 5, 5);
		gbc_lblRole.gridx = 2;
		gbc_lblRole.gridy = 4;
		panelGridFields.add(lblRole, gbc_lblRole);

		JComboBox<Classification> comboBoxRole = new JComboBox<Classification>();
		comboBoxRole.setFont(new Font("Tahoma", Font.PLAIN, 16));
		comboBoxRole
				.setModel(new DefaultComboBoxModel<Classification>(new Classification[] { Classification.ADMINISTRATOR,
						Classification.STUDENT, Classification.TEACHER, Classification.TECHNICIAN }));
		GridBagConstraints gbc_comboBoxRole = new GridBagConstraints();
		gbc_comboBoxRole.anchor = GridBagConstraints.NORTHWEST;
		gbc_comboBoxRole.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxRole.gridx = 3;
		gbc_comboBoxRole.gridy = 4;
		panelGridFields.add(comboBoxRole, gbc_comboBoxRole);

		comboBoxRole.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean bln = ((Classification) comboBoxRole.getSelectedItem()).equals(Classification.STUDENT);
				lblClassroom.setVisible(bln);
				comboBoxClassroom.setVisible(bln);
				lblDepartment.setVisible(bln);
				comboBoxDepartment.setVisible(bln);
			}
		});

		lblErrorEmail = new JLabel("");
		lblErrorEmail.setPreferredSize(new Dimension(293, 26));
		lblErrorEmail.setForeground(Color.RED);
		lblErrorEmail.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblErrorEmail = new GridBagConstraints();
		gbc_lblErrorEmail.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblErrorEmail.anchor = GridBagConstraints.NORTH;
		gbc_lblErrorEmail.insets = new Insets(0, 0, 5, 5);
		gbc_lblErrorEmail.gridwidth = 2;
		gbc_lblErrorEmail.gridx = 0;
		gbc_lblErrorEmail.gridy = 5;
		panelGridFields.add(lblErrorEmail, gbc_lblErrorEmail);
		lblClassroom.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblClassroom = new GridBagConstraints();
		gbc_lblClassroom.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblClassroom.insets = new Insets(0, 0, 5, 5);
		gbc_lblClassroom.gridx = 0;
		gbc_lblClassroom.gridy = 6;
		panelGridFields.add(lblClassroom, gbc_lblClassroom);

		comboBoxClassroom = new JComboBox<String>();
		comboBoxClassroom.setVisible(false);
		comboBoxClassroom.setFont(new Font("Tahoma", Font.PLAIN, 16));
		comboBoxClassroom.setModel(new DefaultComboBoxModel<String>(new String[] { "1", "2", "3", "4", "5" }));
		GridBagConstraints gbc_comboBoxClassroom = new GridBagConstraints();
		gbc_comboBoxClassroom.anchor = GridBagConstraints.NORTH;
		gbc_comboBoxClassroom.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxClassroom.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxClassroom.gridx = 1;
		gbc_comboBoxClassroom.gridy = 6;
		panelGridFields.add(comboBoxClassroom, gbc_comboBoxClassroom);

		comboBoxDepartment = new JComboBox<String>();
		comboBoxDepartment.setPreferredSize(new Dimension(140, 22));
		comboBoxDepartment.setVisible(false);

		lblDepartment = new JLabel("Sezione:");
		lblDepartment.setVisible(false);
		lblDepartment.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblDepartment = new GridBagConstraints();
		gbc_lblDepartment.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblDepartment.insets = new Insets(0, 0, 5, 5);
		gbc_lblDepartment.gridx = 2;
		gbc_lblDepartment.gridy = 6;
		panelGridFields.add(lblDepartment, gbc_lblDepartment);
		comboBoxDepartment.setFont(new Font("Tahoma", Font.PLAIN, 16));
		comboBoxDepartment.setModel(new DefaultComboBoxModel<String>(new String[] { "A", "B", "C" }));
		GridBagConstraints gbc_comboBoxDepartment = new GridBagConstraints();
		gbc_comboBoxDepartment.anchor = GridBagConstraints.WEST;
		gbc_comboBoxDepartment.fill = GridBagConstraints.VERTICAL;
		gbc_comboBoxDepartment.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxDepartment.gridx = 3;
		gbc_comboBoxDepartment.gridy = 6;
		panelGridFields.add(comboBoxDepartment, gbc_comboBoxDepartment);

		JLabel lblText = new JLabel("Per proseguire tutti i campi devono essere compilati correttamente.");
		lblText.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblText = new GridBagConstraints();
		gbc_lblText.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblText.insets = new Insets(0, 0, 5, 5);
		gbc_lblText.gridwidth = 4;
		gbc_lblText.gridx = 0;
		gbc_lblText.gridy = 7;
		panelGridFields.add(lblText, gbc_lblText);

		JLabel lblAutoGeneratedPassword = new JLabel(
				"Nota: La password sar\u00E0 generata automaticamente dal sistema e verr\u00E0 notificata all'utente tramite mail.");
		lblAutoGeneratedPassword.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblAutoGeneratedPassword = new GridBagConstraints();
		gbc_lblAutoGeneratedPassword.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblAutoGeneratedPassword.gridwidth = 5;
		gbc_lblAutoGeneratedPassword.gridx = 0;
		gbc_lblAutoGeneratedPassword.gridy = 8;
		panelGridFields.add(lblAutoGeneratedPassword, gbc_lblAutoGeneratedPassword);

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
						&& lblErrorEmail.getText().length() == 0) {
					lblError.setVisible(false);
					Classification classification = (Classification) comboBoxRole.getSelectedItem();
					String year_class = null;
					if (classification.equals(Classification.STUDENT)) {
						year_class = comboBoxClassroom.getSelectedItem().toString() + "-"
								+ comboBoxDepartment.getSelectedItem().toString();
					}
					User newUser = new User(textFieldName.getText(), textFieldSurname.getText(), textFieldID.getText(),
							textFieldEmail.getText(), classification, year_class, textFieldPhone.getText(), null, true,
							null, 5);
					String response = Proxy.instance().readerRegistration(newUser);
					if (response != null) {
						if (response.equals("Operation completed")) {
							JOptionPane.showMessageDialog(null,
									"L'utente è stato registrato, è ora possibile effettuare il login!",
									"Registrazione", JOptionPane.INFORMATION_MESSAGE);
							dispose();
						} else {
							JOptionPane.showMessageDialog(null,
									"Non è stato possibile registrare l'utente! I dati non sono corretti!", "Errore!",
									JOptionPane.ERROR_MESSAGE);
						}
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
