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
import dataModel.Login;
import utils.Directory;
import utils.Utility;

/**
 * This class create the modify librarian interface
 *
 * @author Marco Di Capua
 * @author Mattia Lo Schiavo
 * @author Filippo Pelosi
 * @author Riccardo Zorzi
 * @version 1.0
 */
public class GUIModifyLibrarian extends JDialog {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldPhone;
	private final Dimension frameDimension = new Dimension(1010, 650);
	private JLabel lblError;
	private JLabel lblErrorPhone;
	private JLabel lblErrorEmail;
	private JLabel lblErrorConfirmEmail;
	private JTextField textFieldConfirmEmail;
	private JTextField textFieldEmail;
	private static GUIModifyLibrarian dialog;

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
					dialog = new GUIModifyLibrarian(frame);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private GUIModifyLibrarian(JFrame frame) {
		super(frame, true);
		setResizable(false);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds((int) ((screenSize.getWidth() - frameDimension.getWidth()) / 2),
				(int) ((screenSize.getHeight() - frameDimension.getHeight()) / 2), frameDimension.width,
				frameDimension.height);
		setTitle("Modifica profilo bibliotecario");
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
		panelFieldsNorth.setPreferredSize(new Dimension(775, 250));
		panelFields.add(panelFieldsNorth, BorderLayout.NORTH);
		panelFieldsNorth.setLayout(new BorderLayout(0, 0));

		JPanel panelGridFields = new JPanel();
		panelGridFields.setBorder(new TitledBorder(null, "Modifica utente", TitledBorder.LEADING, TitledBorder.TOP,
				new Font("Tahoma", Font.BOLD, 18), new Color(0, 0, 0)));
		panelGridFields.setPreferredSize(new Dimension(10, 250));
		panelFieldsNorth.add(panelGridFields, BorderLayout.CENTER);
		GridBagLayout gbl_panelGridFields = new GridBagLayout();
		gbl_panelGridFields.columnWidths = new int[] { 124, 170, 115, 175, 0 };
		gbl_panelGridFields.rowHeights = new int[] { 30, 26, 26, 26, 26, 20, 0 };
		gbl_panelGridFields.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panelGridFields.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panelGridFields.setLayout(gbl_panelGridFields);

		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblEmail = new GridBagConstraints();
		gbc_lblEmail.anchor = GridBagConstraints.WEST;
		gbc_lblEmail.insets = new Insets(0, 0, 5, 5);
		gbc_lblEmail.gridx = 0;
		gbc_lblEmail.gridy = 1;
		panelGridFields.add(lblEmail, gbc_lblEmail);

		textFieldEmail = new JTextField();
		textFieldEmail.setFont(new Font("Tahoma", Font.PLAIN, 16));

		textFieldEmail.setColumns(5);
		GridBagConstraints gbc_textFieldEmail = new GridBagConstraints();
		gbc_textFieldEmail.anchor = GridBagConstraints.NORTH;
		gbc_textFieldEmail.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldEmail.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldEmail.gridx = 1;
		gbc_textFieldEmail.gridy = 1;
		panelGridFields.add(textFieldEmail, gbc_textFieldEmail);

		textFieldEmail.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String email = textFieldEmail.getText().trim();
				textFieldEmail.setText(email);
				String confirmEmail = textFieldConfirmEmail.getText().trim();
				textFieldConfirmEmail.setText(confirmEmail);
				String errorEmail = Utility.verifyNotEmptyField(email) + Utility.verifyLettersAndNumbers(email)
						+ Utility.verifyEmail(email) + Utility.verifyConfirmEmail(email, confirmEmail);
				lblErrorEmail.setText(errorEmail.split("-")[0]);
			}
		});

		JLabel lblPhone = new JLabel("Telefono:");
		lblPhone.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblPhone = new GridBagConstraints();
		gbc_lblPhone.anchor = GridBagConstraints.WEST;
		gbc_lblPhone.insets = new Insets(0, 0, 5, 5);
		gbc_lblPhone.gridx = 2;
		gbc_lblPhone.gridy = 1;
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
		gbc_textFieldPhone.gridx = 3;
		gbc_textFieldPhone.gridy = 1;
		panelGridFields.add(textFieldPhone, gbc_textFieldPhone);

		lblErrorEmail = new JLabel("");
		lblErrorEmail.setPreferredSize(new Dimension(354, 26));
		lblErrorEmail.setForeground(Color.RED);
		lblErrorEmail.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblErrorEmail = new GridBagConstraints();
		gbc_lblErrorEmail.anchor = GridBagConstraints.NORTH;
		gbc_lblErrorEmail.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblErrorEmail.insets = new Insets(0, 0, 5, 5);
		gbc_lblErrorEmail.gridwidth = 2;
		gbc_lblErrorEmail.gridx = 0;
		gbc_lblErrorEmail.gridy = 2;
		panelGridFields.add(lblErrorEmail, gbc_lblErrorEmail);

		lblErrorPhone = new JLabel("");
		lblErrorPhone.setPreferredSize(new Dimension(354, 26));
		lblErrorPhone.setForeground(Color.RED);
		lblErrorPhone.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblErrorPhone = new GridBagConstraints();
		gbc_lblErrorPhone.anchor = GridBagConstraints.NORTH;
		gbc_lblErrorPhone.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblErrorPhone.insets = new Insets(0, 0, 5, 0);
		gbc_lblErrorPhone.gridwidth = 2;
		gbc_lblErrorPhone.gridx = 2;
		gbc_lblErrorPhone.gridy = 2;
		panelGridFields.add(lblErrorPhone, gbc_lblErrorPhone);

		JLabel lblConfirmEmail = new JLabel("Conferma email:");
		lblConfirmEmail.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblConfirmEmail = new GridBagConstraints();
		gbc_lblConfirmEmail.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblConfirmEmail.insets = new Insets(0, 0, 5, 5);
		gbc_lblConfirmEmail.gridx = 0;
		gbc_lblConfirmEmail.gridy = 3;
		panelGridFields.add(lblConfirmEmail, gbc_lblConfirmEmail);

		textFieldConfirmEmail = new JTextField();
		textFieldConfirmEmail.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textFieldConfirmEmail.setColumns(5);
		GridBagConstraints gbc_textFieldConfirmEmail = new GridBagConstraints();
		gbc_textFieldConfirmEmail.anchor = GridBagConstraints.NORTH;
		gbc_textFieldConfirmEmail.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldConfirmEmail.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldConfirmEmail.gridx = 1;
		gbc_textFieldConfirmEmail.gridy = 3;
		panelGridFields.add(textFieldConfirmEmail, gbc_textFieldConfirmEmail);

		textFieldConfirmEmail.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String email = textFieldEmail.getText().trim();
				textFieldEmail.setText(email);
				String confirmEmail = textFieldConfirmEmail.getText().trim();
				textFieldConfirmEmail.setText(confirmEmail);
				String errorConfirmEmail = Utility.verifyNotEmptyField(confirmEmail)
						+ Utility.verifyLettersAndNumbers(confirmEmail) + Utility.verifyEmail(confirmEmail)
						+ Utility.verifyConfirmEmail(email, confirmEmail);
				lblErrorConfirmEmail.setText(errorConfirmEmail.split("-")[0]);
			}
		});

		JLabel lblClassification = new JLabel("Inquadramento:");
		lblClassification.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblClassification = new GridBagConstraints();
		gbc_lblClassification.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblClassification.insets = new Insets(0, 0, 5, 5);
		gbc_lblClassification.gridx = 2;
		gbc_lblClassification.gridy = 3;
		panelGridFields.add(lblClassification, gbc_lblClassification);

		JComboBox<Classification> comboBoxClassification = new JComboBox<Classification>();
		comboBoxClassification.setFont(new Font("Tahoma", Font.PLAIN, 16));
		comboBoxClassification
				.setModel(new DefaultComboBoxModel<Classification>(new Classification[] { Classification.LIBRARIAN }));
		GridBagConstraints gbc_comboBoxClassification = new GridBagConstraints();
		gbc_comboBoxClassification.anchor = GridBagConstraints.NORTH;
		gbc_comboBoxClassification.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxClassification.insets = new Insets(0, 0, 5, 0);
		gbc_comboBoxClassification.gridx = 3;
		gbc_comboBoxClassification.gridy = 3;
		panelGridFields.add(comboBoxClassification, gbc_comboBoxClassification);

		lblErrorConfirmEmail = new JLabel("");
		lblErrorConfirmEmail.setPreferredSize(new Dimension(293, 26));
		lblErrorConfirmEmail.setForeground(Color.RED);
		lblErrorConfirmEmail.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblErrorConfirmEmail = new GridBagConstraints();
		gbc_lblErrorConfirmEmail.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblErrorConfirmEmail.anchor = GridBagConstraints.NORTH;
		gbc_lblErrorConfirmEmail.insets = new Insets(0, 0, 5, 5);
		gbc_lblErrorConfirmEmail.gridwidth = 2;
		gbc_lblErrorConfirmEmail.gridx = 0;
		gbc_lblErrorConfirmEmail.gridy = 4;
		panelGridFields.add(lblErrorConfirmEmail, gbc_lblErrorConfirmEmail);

		JLabel lblText = new JLabel("Per proseguire tutti i campi devono essere compilati correttamente.");
		lblText.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblText = new GridBagConstraints();
		gbc_lblText.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblText.gridwidth = 4;
		gbc_lblText.gridx = 0;
		gbc_lblText.gridy = 5;
		panelGridFields.add(lblText, gbc_lblText);

		JPanel panelConfirm = new JPanel();
		panelConfirm.setPreferredSize(new Dimension(10, 80));
		panelFields.add(panelConfirm, BorderLayout.SOUTH);

		JButton btnModifyPassword = new JButton("Modifica password");
		btnModifyPassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GUIModifyPassword.main(GUIModifyLibrarian.dialog);
			}
		});
		btnModifyPassword.setFont(new Font("Tahoma", Font.PLAIN, 16));

		JButton buttonDeleteUser = new JButton("Cancella utente");
		buttonDeleteUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GUIDeleteUser.main(GUIModifyLibrarian.dialog);
			}
		});
		buttonDeleteUser.setFont(new Font("Tahoma", Font.PLAIN, 16));

		JButton btnConfirm = new JButton("Conferma modifiche");
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (lblErrorEmail.getText().length() == 0 && lblErrorConfirmEmail.getText().length() == 0
						&& lblErrorPhone.getText().length() == 0) {
					lblError.setVisible(false);
					if (!Login.getUserLogged().getTelephoneNumber().equals(textFieldPhone.getText())
							|| !Login.getUserLogged().getEmail().equals(textFieldEmail.getText())) {
						String responseTelephone = "";
						if (!Login.getUserLogged().getTelephoneNumber().equals(textFieldPhone.getText())) {
							Login.getUserLogged().setTelephoneNumber(textFieldPhone.getText());
							responseTelephone = Proxy.instance()
									.modifyTelephoneNumber(Login.getUserLogged().getTelephoneNumber());
							if (responseTelephone != null) {
								if (responseTelephone.equals("Operation completed")) {
									JOptionPane.showMessageDialog(null,
											"Il numero di telefono è stato modificato correttamente",
											"Numero di telefono modificato!", JOptionPane.INFORMATION_MESSAGE);
								} else {
									JOptionPane.showMessageDialog(null,
											"Non è stato possibile modificare il numero di telefono come richiesto! (Errore: "
													+ responseTelephone + ")",
											"Errore!", JOptionPane.ERROR_MESSAGE);
								}
							}
						}
						String[] responseEmail = new String[2];
						if (responseTelephone != null
								&& !Login.getUserLogged().getEmail().equals(textFieldEmail.getText())) {
							responseEmail = Proxy.instance().modifyEmail(textFieldEmail.getText());
							if (responseEmail[0] != null) {
								if (responseEmail[0].equals("Operation completed")) {
									String activationCode = responseEmail[1];
									int counter = 5;
									while (counter > 0) {
										JLabel lblInsertCode = new JLabel("Inserire il codice di attivazione:");
										lblInsertCode.setFont(new Font("Tahoma", Font.PLAIN, 16));
										JLabel lblInformation = new JLabel(
												"Il codice di attivazione è stato inviato alla mail inserita durante la modifica");
										lblInformation.setFont(new Font("Tahoma", Font.PLAIN, 13));
										JTextField textFieldCode = new JTextField();
										textFieldCode.setFont(new Font("Tahoma", Font.PLAIN, 16));
										JLabel lblcounter = new JLabel("Tentativi rimasti: " + counter);
										lblcounter.setFont(new Font("Tahoma", Font.PLAIN, 14));
										Object[] ob = { lblInsertCode, lblInformation, textFieldCode, lblcounter };
										int result = JOptionPane.showConfirmDialog(null, ob,
												"Validazione indirizzo mail", JOptionPane.OK_CANCEL_OPTION);
										if (result == JOptionPane.OK_OPTION) {
											if (activationCode.equals(textFieldCode.getText())) {
												String response = Proxy.instance().confirmModifyMail(true,
														Login.getUserLogged().getEmail());
												if (response != null && response.equals("Operation completed")) {
													Login.getUserLogged().setEmail(textFieldEmail.getText());
													JOptionPane.showMessageDialog(null,
															"L'indirizzo email è stato modificato come richiesto!",
															"Modifica email!", JOptionPane.INFORMATION_MESSAGE);
													break;
												}
											} else {
												counter--;
												JOptionPane.showMessageDialog(null, "Codice di attivazione errato!",
														"Errore!", JOptionPane.ERROR_MESSAGE);
											}
										} else {
											Proxy.instance().confirmModifyMail(false, Login.getUserLogged().getEmail());
											JOptionPane.showMessageDialog(null,
													"Operazione di modifca email annullata!", "Modifica email!",
													JOptionPane.INFORMATION_MESSAGE);
											fillInFields();
											break;
										}
									}
									if (counter == 0) {
										Proxy.instance().confirmModifyMail(false, Login.getUserLogged().getEmail());
										JOptionPane.showMessageDialog(null, "Operazione di modifca email annullata!",
												"Modifica email!", JOptionPane.INFORMATION_MESSAGE);
										fillInFields();
									}
								} else {
									JOptionPane
											.showMessageDialog(null,
													"Non è stato possibile modificare la mail come richiesto! (Errore: "
															+ responseEmail[0] + ")",
													"Errore!", JOptionPane.ERROR_MESSAGE);
								}
							}
						}
					} else {
						JOptionPane.showMessageDialog(null, "Nessun campo modificato!", "Nessuna modifica!",
								JOptionPane.INFORMATION_MESSAGE);
					}
				} else {
					lblError.setVisible(true);
				}
			}

		});
		btnConfirm.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelConfirm.setLayout(new FlowLayout(FlowLayout.CENTER, 150, 5));

		lblError = new JLabel(
				"Uno o pi\u00F9 campi non sono stati compilati correttamente e devono essere corretti per continuare!");
		panelConfirm.add(lblError);
		lblError.setVisible(false);
		lblError.setForeground(Color.RED);
		lblError.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelConfirm.add(btnModifyPassword);
		panelConfirm.add(buttonDeleteUser);
		panelConfirm.add(btnConfirm);

		fillInFields();

	}

	private void fillInFields() {
		textFieldEmail.setText(Login.getUserLogged().getEmail());
		textFieldConfirmEmail.setText(Login.getUserLogged().getEmail());
		textFieldPhone.setText(Login.getUserLogged().getTelephoneNumber());
	}

}
