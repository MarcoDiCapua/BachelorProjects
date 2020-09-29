package appLibrarian.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import appLibrarian.Proxy;
import dataModel.Login;
import dataModel.User;
import utils.Directory;
import utils.Operation;
import utils.Utility;

/**
 * This class create the login interface
 *
 * @author Marco Di Capua
 * @author Mattia Lo Schiavo
 * @author Filippo Pelosi
 * @author Riccardo Zorzi
 * @version 1.0
 */
public class GUILogin extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldID;
	private JPasswordField passwordFieldPassword;
	private final Dimension frameDimension = new Dimension(1100, 335);
	private static GUILogin frame;

	private GUILogin() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				Proxy.instance().stop();
				System.exit(0);
			}
		});
		setResizable(false);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds((int) ((screenSize.getWidth() - frameDimension.getWidth()) / 2),
				(int) ((screenSize.getHeight() - frameDimension.getHeight()) / 2), frameDimension.width,
				frameDimension.height);
		setTitle("Login");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		URL imgWindowIcon = getClass().getResource(Directory.PATH_IMG_WINDOW_ICON);
		setIconImage(new ImageIcon(imgWindowIcon).getImage());
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
		panelFieldsNorth.setPreferredSize(new Dimension(775, 220));
		panelFields.add(panelFieldsNorth, BorderLayout.NORTH);
		panelFieldsNorth.setLayout(new BorderLayout(0, 0));

		JPanel panelGridFields = new JPanel();
		panelGridFields.setBorder(new TitledBorder(null, "Dati utente", TitledBorder.LEADING, TitledBorder.TOP,
				new Font("Tahoma", Font.BOLD, 18), new Color(0, 0, 0)));
		panelGridFields.setPreferredSize(new Dimension(10, 200));
		panelFieldsNorth.add(panelGridFields, BorderLayout.NORTH);
		GridBagLayout gbl_panelGridFields = new GridBagLayout();
		gbl_panelGridFields.columnWidths = new int[] { 101, 165, 145, 145, 0 };
		gbl_panelGridFields.rowHeights = new int[] { 0, 22, 22, 22, 22, 20, 20, 0 };
		gbl_panelGridFields.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panelGridFields.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panelGridFields.setLayout(gbl_panelGridFields);

		JLabel lblErrorID = new JLabel("");
		lblErrorID.setVisible(true);

		JLabel lblID = new JLabel("Codice fiscale:");
		lblID.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblID = new GridBagConstraints();
		gbc_lblID.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblID.insets = new Insets(0, 0, 5, 5);
		gbc_lblID.gridx = 0;
		gbc_lblID.gridy = 1;
		panelGridFields.add(lblID, gbc_lblID);

		textFieldID = new JTextField();
		textFieldID.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textFieldID.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
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
		gbc_textFieldID.gridy = 1;
		panelGridFields.add(textFieldID, gbc_textFieldID);
		lblErrorID.setForeground(Color.RED);
		lblErrorID.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblErrorID = new GridBagConstraints();
		gbc_lblErrorID.gridwidth = 4;
		gbc_lblErrorID.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblErrorID.insets = new Insets(0, 0, 5, 5);
		gbc_lblErrorID.gridx = 0;
		gbc_lblErrorID.gridy = 2;
		panelGridFields.add(lblErrorID, gbc_lblErrorID);

		JLabel lblErrorPassword = new JLabel("");
		lblErrorPassword.setVisible(true);
		lblErrorPassword.setForeground(Color.RED);
		lblErrorPassword.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblErrorPassword = new GridBagConstraints();
		gbc_lblErrorPassword.gridwidth = 4;
		gbc_lblErrorPassword.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblErrorPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblErrorPassword.gridx = 0;
		gbc_lblErrorPassword.gridy = 4;
		panelGridFields.add(lblErrorPassword, gbc_lblErrorPassword);

		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblPassword = new GridBagConstraints();
		gbc_lblPassword.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblPassword.gridx = 0;
		gbc_lblPassword.gridy = 3;
		panelGridFields.add(lblPassword, gbc_lblPassword);

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
		passwordFieldPassword.setColumns(5);
		GridBagConstraints gbc_passwordFieldPassword = new GridBagConstraints();
		gbc_passwordFieldPassword.anchor = GridBagConstraints.NORTH;
		gbc_passwordFieldPassword.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordFieldPassword.insets = new Insets(0, 0, 5, 5);
		gbc_passwordFieldPassword.gridx = 1;
		gbc_passwordFieldPassword.gridy = 3;
		panelGridFields.add(passwordFieldPassword, gbc_passwordFieldPassword);

		JLabel lblNewUser = new JLabel("Sei un nuovo utente? Registrati cliccando qui!");
		lblNewUser.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				GUIRegistrationLibrarian.main(GUILogin.frame);
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				lblNewUser.setForeground(Color.BLUE);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				lblNewUser.setForeground(Color.BLACK);
			}
		});

		lblNewUser.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblNewUser.setForeground(Color.BLACK);
		lblNewUser.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblNewUser = new GridBagConstraints();
		gbc_lblNewUser.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblNewUser.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewUser.gridwidth = 3;
		gbc_lblNewUser.gridx = 0;
		gbc_lblNewUser.gridy = 5;
		panelGridFields.add(lblNewUser, gbc_lblNewUser);

		JLabel lblForgottenPassword = new JLabel(
				"Hai dimenticato la tua password? Clicca qui per ottenerne una nuova!");
		lblForgottenPassword.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				JLabel lblInsertUserId = new JLabel("Inserire l'userId associato al profilo:");
				lblInsertUserId.setFont(new Font("Tahoma", Font.PLAIN, 16));
				JLabel lblInformation = new JLabel(
						"Verranno inviati un codice di attivazione e una nuova password alla mail inserita durante la registrazione");
				lblInformation.setFont(new Font("Tahoma", Font.PLAIN, 13));
				JTextField textFieldUserId = new JTextField();
				textFieldUserId.setFont(new Font("Tahoma", Font.PLAIN, 16));
				Object[] ob = { lblInsertUserId, lblInformation, textFieldUserId };
				int result = JOptionPane.showConfirmDialog(null, ob, "Reset password", JOptionPane.OK_CANCEL_OPTION);
				if (result == JOptionPane.OK_OPTION) {
					if (Proxy.instance().resetPasswordUser(textFieldUserId.getText())) {
						JOptionPane.showMessageDialog(null,
								"Password resettata! Il codice di attivazione e una nuova password sono stati inviati alla mail inserita durante la registrazione",
								"Password resettata!", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				lblForgottenPassword.setForeground(Color.BLUE);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				lblForgottenPassword.setForeground(Color.BLACK);
			}
		});
		lblForgottenPassword.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblForgottenPassword.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblForgottenPassword = new GridBagConstraints();
		gbc_lblForgottenPassword.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblForgottenPassword.gridwidth = 4;
		gbc_lblForgottenPassword.gridx = 0;
		gbc_lblForgottenPassword.gridy = 6;
		panelGridFields.add(lblForgottenPassword, gbc_lblForgottenPassword);

		JPanel panelConfirm = new JPanel();
		panelConfirm.setPreferredSize(new Dimension(10, 80));
		panelFields.add(panelConfirm, BorderLayout.SOUTH);
		panelConfirm.setLayout(new FlowLayout(FlowLayout.RIGHT, 15, 20));

		JLabel lblError = new JLabel(
				"Uno o pi\u00F9 campi non sono stati compilati correttamente e devono essere corretti per continuare!");
		lblError.setVisible(false);
		lblError.setForeground(Color.RED);
		lblError.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelConfirm.add(lblError);

		JButton btnConfirm = new JButton("Accedi!");
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (lblErrorID.getText().length() == 0 && lblErrorPassword.getText().length() == 0) {
					Login login = new Login(textFieldID.getText().trim(),
							new String(passwordFieldPassword.getPassword()), "Librarian");
					User user = Proxy.instance().login(Operation.LOGIN_USER, login);
					Login.setUserLogged(user);
					if (user == null) {
						lblError.setVisible(true);
					} else if (user.getActivationCode() != null) {
						int counter = user.getCounter();
						while (counter > 0) {
							JLabel lblInsertCode = new JLabel("Inserire il codice di attivazione:");
							lblInsertCode.setFont(new Font("Tahoma", Font.PLAIN, 16));
							JLabel lblInformation = new JLabel(
									"Il codice di attivazione è stato inviato alla mail inserita durante la registrazione");
							lblInformation.setFont(new Font("Tahoma", Font.PLAIN, 13));
							JTextField textFieldCode = new JTextField();
							textFieldCode.setFont(new Font("Tahoma", Font.PLAIN, 16));
							JLabel lblcounter = new JLabel("Tentativi rimasti: " + counter);
							lblcounter.setFont(new Font("Tahoma", Font.PLAIN, 14));
							Object[] ob = { lblInsertCode, lblInformation, textFieldCode, lblcounter };
							int result = JOptionPane.showConfirmDialog(null, ob, "Attivazione profilo",
									JOptionPane.OK_CANCEL_OPTION);
							if (result == JOptionPane.OK_OPTION) {
								if (user.getActivationCode().equals(textFieldCode.getText())) {
									String response = Proxy.instance().activateUser();
									if (response != null && response.equals("Operation completed")) {
										GUIMainLibrarian.main();
										dispose();
										break;
									}
								} else {
									counter--;
									user.setCounter(counter);
									Proxy.instance().updateCounter(user.getCounter());
									JOptionPane.showMessageDialog(null, "Codice di attivazione errato!", "Errore!",
											JOptionPane.ERROR_MESSAGE);
								}
							} else {
								break;
							}
						}
						if (counter == 0) {
							JOptionPane.showMessageDialog(null,
									"Nessun tentativo rimasto! L'utente è stato cancellato!", "Utente cancellato!",
									JOptionPane.INFORMATION_MESSAGE);
						}
					} else {
						GUIMainLibrarian.main();
						dispose();
					}
				}
			}
		});
		btnConfirm.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelConfirm.add(btnConfirm);
	}

	/**
	 * Launch the login interface
	 */
	public static void main() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUILogin frame = new GUILogin();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
