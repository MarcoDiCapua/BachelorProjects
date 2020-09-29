

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import utils.Utility;

/**
 * Create the main server interface
 *
 * @author Marco Di Capua
 * @author Mattia Lo Schiavo
 * @author Filippo Pelosi
 * @author Riccardo Zorzi
 * @version 1.0
 */
public class GUIMainServer extends JFrame {
	// camps
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private final Dimension frameDimension = new Dimension(700, 350);
	private JTextField textFieldDbPort;
	private JTextField textFieldDbName;
	private JTextField textFieldDbUsername;
	private JPasswordField textFieldDbPwd;
	private JTextField textFieldEmail;
	private JPasswordField textFieldPwdEmail;
	private JLabel lblErrorPwdEmail;
	private JLabel lblErrorEmail;
	private JLabel lblErrorPwdDb;
	private JLabel lblErrorDbUsername;
	private JLabel lblErrorDbName;
	private JLabel lblErrorPort;
	private JLabel lblDbAddress;
	private JTextField textFieldDbAddress;
	private JLabel lblErrorDbAddress;
	private static JFrame frame;

	/**
	 * Launch the main server interface.
	 */
	public static void main() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new GUIMainServer();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private GUIMainServer() {
		setTitle("Inizializzazione server");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds((int) ((screenSize.getWidth() - frameDimension.getWidth()) / 2),
				(int) ((screenSize.getHeight() - frameDimension.getHeight()) / 2), frameDimension.width,
				frameDimension.height);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 144, 249, 260, 0 };
		gbl_contentPane.rowHeights = new int[] { 26, 26, 26, 26, 26, 26, 26, 20, 20, 29, 0 };
		gbl_contentPane.columnWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		textFieldDbAddress = new JTextField();
		textFieldDbAddress.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String dbAddress = textFieldDbAddress.getText().trim();
				textFieldDbAddress.setText(dbAddress);
				String errorDbAddress = Utility.verifyNotEmptyField(dbAddress)
						+ Utility.verifyLettersAndNumbers(dbAddress);
				lblErrorDbAddress.setText(errorDbAddress.split("-")[0]);
			}
		});

		lblDbAddress = new JLabel("Indirizzo database:");
		lblDbAddress.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblDbAddress = new GridBagConstraints();
		gbc_lblDbAddress.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblDbAddress.insets = new Insets(0, 0, 5, 5);
		gbc_lblDbAddress.gridx = 0;
		gbc_lblDbAddress.gridy = 0;
		contentPane.add(lblDbAddress, gbc_lblDbAddress);
		textFieldDbAddress.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textFieldDbAddress.setColumns(10);
		GridBagConstraints gbc_textFieldDbAddress = new GridBagConstraints();
		gbc_textFieldDbAddress.anchor = GridBagConstraints.NORTH;
		gbc_textFieldDbAddress.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldDbAddress.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldDbAddress.gridx = 1;
		gbc_textFieldDbAddress.gridy = 0;
		contentPane.add(textFieldDbAddress, gbc_textFieldDbAddress);

		lblErrorDbAddress = new JLabel("");
		lblErrorDbAddress.setForeground(Color.RED);
		lblErrorDbAddress.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblErrorDbAddress = new GridBagConstraints();
		gbc_lblErrorDbAddress.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblErrorDbAddress.insets = new Insets(0, 0, 5, 0);
		gbc_lblErrorDbAddress.gridx = 2;
		gbc_lblErrorDbAddress.gridy = 0;
		contentPane.add(lblErrorDbAddress, gbc_lblErrorDbAddress);

		JLabel lblDbPort = new JLabel("Porta database:");
		lblDbPort.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblDbPort = new GridBagConstraints();
		gbc_lblDbPort.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblDbPort.insets = new Insets(0, 0, 5, 5);
		gbc_lblDbPort.gridx = 0;
		gbc_lblDbPort.gridy = 1;
		contentPane.add(lblDbPort, gbc_lblDbPort);

		textFieldDbPort = new JTextField();
		textFieldDbPort.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				String port = textFieldDbPort.getText().trim();
				textFieldDbPort.setText(port);
				String errorPort = Utility.verifyNotEmptyField(port) + Utility.verifyNumber(port);
				lblErrorPort.setText(errorPort.split("-")[0]);
			}
		});
		textFieldDbPort.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_textFieldDbPort = new GridBagConstraints();
		gbc_textFieldDbPort.anchor = GridBagConstraints.NORTH;
		gbc_textFieldDbPort.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldDbPort.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldDbPort.gridx = 1;
		gbc_textFieldDbPort.gridy = 1;
		contentPane.add(textFieldDbPort, gbc_textFieldDbPort);
		textFieldDbPort.setColumns(10);

		lblErrorPort = new JLabel("");
		lblErrorPort.setForeground(Color.RED);
		lblErrorPort.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblErrorPort = new GridBagConstraints();
		gbc_lblErrorPort.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblErrorPort.insets = new Insets(0, 0, 5, 0);
		gbc_lblErrorPort.gridx = 2;
		gbc_lblErrorPort.gridy = 1;
		contentPane.add(lblErrorPort, gbc_lblErrorPort);

		JLabel lblDbName = new JLabel("Nome database:");
		lblDbName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblDbName = new GridBagConstraints();
		gbc_lblDbName.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblDbName.insets = new Insets(0, 0, 5, 5);
		gbc_lblDbName.gridx = 0;
		gbc_lblDbName.gridy = 2;
		contentPane.add(lblDbName, gbc_lblDbName);

		textFieldDbName = new JTextField();
		textFieldDbName.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String name = textFieldDbName.getText().trim();
				textFieldDbName.setText(name);
				String errorName = Utility.verifyNotEmptyField(name) + Utility.verifyLettersAndNumbers(name);
				lblErrorDbName.setText(errorName.split("-")[0]);
			}
		});
		textFieldDbName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textFieldDbName.setColumns(10);
		GridBagConstraints gbc_textFieldDbName = new GridBagConstraints();
		gbc_textFieldDbName.anchor = GridBagConstraints.NORTH;
		gbc_textFieldDbName.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldDbName.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldDbName.gridx = 1;
		gbc_textFieldDbName.gridy = 2;
		contentPane.add(textFieldDbName, gbc_textFieldDbName);

		lblErrorDbName = new JLabel("");
		lblErrorDbName.setForeground(Color.RED);
		lblErrorDbName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblErrorDbName = new GridBagConstraints();
		gbc_lblErrorDbName.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblErrorDbName.insets = new Insets(0, 0, 5, 0);
		gbc_lblErrorDbName.gridx = 2;
		gbc_lblErrorDbName.gridy = 2;
		contentPane.add(lblErrorDbName, gbc_lblErrorDbName);

		JLabel lblDbUsername = new JLabel("Username database:");
		lblDbUsername.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblDbUsername = new GridBagConstraints();
		gbc_lblDbUsername.anchor = GridBagConstraints.WEST;
		gbc_lblDbUsername.insets = new Insets(0, 0, 5, 5);
		gbc_lblDbUsername.gridx = 0;
		gbc_lblDbUsername.gridy = 3;
		contentPane.add(lblDbUsername, gbc_lblDbUsername);

		textFieldPwdEmail = new JPasswordField();
		textFieldPwdEmail.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String emailPwd = new String(textFieldPwdEmail.getPassword());
				textFieldPwdEmail.setText(emailPwd);
				String errorEmailPwd = Utility.verifyNotEmptyField(emailPwd)
						+ Utility.verifyLettersAndNumbers(emailPwd);
				lblErrorPwdEmail.setText(errorEmailPwd.split("-")[0]);
			}
		});

		textFieldDbUsername = new JTextField();
		textFieldDbUsername.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String username = textFieldDbUsername.getText().trim();
				textFieldDbUsername.setText(username);
				String errorUSername = Utility.verifyNotEmptyField(username)
						+ Utility.verifyLettersAndNumbers(username);
				lblErrorDbUsername.setText(errorUSername.split("-")[0]);
			}
		});
		textFieldDbUsername.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textFieldDbUsername.setColumns(10);
		GridBagConstraints gbc_textFieldDbUsername = new GridBagConstraints();
		gbc_textFieldDbUsername.anchor = GridBagConstraints.NORTH;
		gbc_textFieldDbUsername.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldDbUsername.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldDbUsername.gridx = 1;
		gbc_textFieldDbUsername.gridy = 3;
		contentPane.add(textFieldDbUsername, gbc_textFieldDbUsername);

		textFieldDbPwd = new JPasswordField();
		textFieldDbPwd.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String dbPassword = new String(textFieldDbPwd.getPassword());
				textFieldDbPwd.setText(dbPassword);
				String errorDbPassword = Utility.verifyNotEmptyField(dbPassword)
						+ Utility.verifyLettersAndNumbers(dbPassword);
				lblErrorPwdDb.setText(errorDbPassword.split("-")[0]);
			}
		});

		lblErrorDbUsername = new JLabel("");
		lblErrorDbUsername.setForeground(Color.RED);
		lblErrorDbUsername.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblErrorDbUsername = new GridBagConstraints();
		gbc_lblErrorDbUsername.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblErrorDbUsername.insets = new Insets(0, 0, 5, 0);
		gbc_lblErrorDbUsername.gridx = 2;
		gbc_lblErrorDbUsername.gridy = 3;
		contentPane.add(lblErrorDbUsername, gbc_lblErrorDbUsername);

		JLabel lblDbPwd = new JLabel("Password database:");
		lblDbPwd.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblDbPwd = new GridBagConstraints();
		gbc_lblDbPwd.anchor = GridBagConstraints.WEST;
		gbc_lblDbPwd.insets = new Insets(0, 0, 5, 5);
		gbc_lblDbPwd.gridx = 0;
		gbc_lblDbPwd.gridy = 4;
		contentPane.add(lblDbPwd, gbc_lblDbPwd);
		textFieldDbPwd.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textFieldDbPwd.setColumns(10);
		GridBagConstraints gbc_textFieldDbPwd = new GridBagConstraints();
		gbc_textFieldDbPwd.anchor = GridBagConstraints.NORTH;
		gbc_textFieldDbPwd.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldDbPwd.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldDbPwd.gridx = 1;
		gbc_textFieldDbPwd.gridy = 4;
		contentPane.add(textFieldDbPwd, gbc_textFieldDbPwd);

		lblErrorPwdDb = new JLabel("");
		lblErrorPwdDb.setForeground(Color.RED);
		lblErrorPwdDb.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblErrorPwdDb = new GridBagConstraints();
		gbc_lblErrorPwdDb.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblErrorPwdDb.insets = new Insets(0, 0, 5, 0);
		gbc_lblErrorPwdDb.gridx = 2;
		gbc_lblErrorPwdDb.gridy = 4;
		contentPane.add(lblErrorPwdDb, gbc_lblErrorPwdDb);

		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblEmail = new GridBagConstraints();
		gbc_lblEmail.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblEmail.insets = new Insets(0, 0, 5, 5);
		gbc_lblEmail.gridx = 0;
		gbc_lblEmail.gridy = 5;
		contentPane.add(lblEmail, gbc_lblEmail);

		textFieldEmail = new JTextField();
		textFieldEmail.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String email = textFieldEmail.getText().trim();
				textFieldEmail.setText(email);
				String errorEmail = Utility.verifyNotEmptyField(email) + Utility.verifyEmail(email);
				lblErrorEmail.setText(errorEmail.split("-")[0]);
			}
		});
		textFieldEmail.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textFieldEmail.setColumns(10);
		GridBagConstraints gbc_textFieldEmail = new GridBagConstraints();
		gbc_textFieldEmail.anchor = GridBagConstraints.NORTH;
		gbc_textFieldEmail.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldEmail.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldEmail.gridx = 1;
		gbc_textFieldEmail.gridy = 5;
		contentPane.add(textFieldEmail, gbc_textFieldEmail);

		lblErrorEmail = new JLabel("");
		lblErrorEmail.setForeground(Color.RED);
		lblErrorEmail.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblErrorEmail = new GridBagConstraints();
		gbc_lblErrorEmail.anchor = GridBagConstraints.NORTH;
		gbc_lblErrorEmail.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblErrorEmail.insets = new Insets(0, 0, 5, 0);
		gbc_lblErrorEmail.gridx = 2;
		gbc_lblErrorEmail.gridy = 5;
		contentPane.add(lblErrorEmail, gbc_lblErrorEmail);

		JLabel lblPwdEmail = new JLabel("Password email:");
		lblPwdEmail.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblPwdEmail = new GridBagConstraints();
		gbc_lblPwdEmail.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblPwdEmail.insets = new Insets(0, 0, 5, 5);
		gbc_lblPwdEmail.gridx = 0;
		gbc_lblPwdEmail.gridy = 6;
		contentPane.add(lblPwdEmail, gbc_lblPwdEmail);
		textFieldPwdEmail.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textFieldPwdEmail.setColumns(10);
		GridBagConstraints gbc_textFieldPwdEmail = new GridBagConstraints();
		gbc_textFieldPwdEmail.anchor = GridBagConstraints.NORTH;
		gbc_textFieldPwdEmail.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldPwdEmail.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldPwdEmail.gridx = 1;
		gbc_textFieldPwdEmail.gridy = 6;
		contentPane.add(textFieldPwdEmail, gbc_textFieldPwdEmail);

		lblErrorPwdEmail = new JLabel("");
		lblErrorPwdEmail.setForeground(Color.RED);
		lblErrorPwdEmail.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblErrorPwdEmail = new GridBagConstraints();
		gbc_lblErrorPwdEmail.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblErrorPwdEmail.insets = new Insets(0, 0, 5, 0);
		gbc_lblErrorPwdEmail.gridx = 2;
		gbc_lblErrorPwdEmail.gridy = 6;
		contentPane.add(lblErrorPwdEmail, gbc_lblErrorPwdEmail);

		JLabel lblAllFields = new JLabel("Tutti i campi devono essere compilati correttamente per proseguire!");
		lblAllFields.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblAllFields = new GridBagConstraints();
		gbc_lblAllFields.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblAllFields.insets = new Insets(0, 0, 5, 0);
		gbc_lblAllFields.gridwidth = 3;
		gbc_lblAllFields.gridx = 0;
		gbc_lblAllFields.gridy = 8;
		contentPane.add(lblAllFields, gbc_lblAllFields);

		JButton btnStartServer = new JButton("Avvia server");
		btnStartServer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (lblErrorDbName.getText().length() == 0 && lblErrorDbUsername.getText().length() == 0
						&& lblErrorEmail.getText().length() == 0 && lblErrorPort.getText().length() == 0
						&& lblErrorPwdDb.getText().length() == 0 && lblErrorPwdEmail.getText().length() == 0) {
					String url = "jdbc:postgresql://" + textFieldDbAddress.getText() + ":" + textFieldDbPort.getText()
							+ "/" + textFieldDbName.getText();
					frame.dispose();
					ServerMain.initializationServer(url, textFieldDbUsername.getText(),
							new String(textFieldDbPwd.getPassword()), textFieldEmail.getText(),
							new String(textFieldPwdEmail.getPassword()));
				}
			}
		});
		btnStartServer.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_btnStartServer = new GridBagConstraints();
		gbc_btnStartServer.anchor = GridBagConstraints.NORTHEAST;
		gbc_btnStartServer.gridx = 2;
		gbc_btnStartServer.gridy = 9;
		contentPane.add(btnStartServer, gbc_btnStartServer);
		fillFields();
	}

	private void fillFields() {
		textFieldDbAddress.setText("127.0.0.1");
		textFieldDbName.setText("libraryDb");
		textFieldDbPort.setText("5432");
		textFieldDbPwd.setText("admin");
		textFieldDbUsername.setText("postgres");
		textFieldEmail.setText("fpelosi@studenti.uninsubria.it");
		textFieldPwdEmail.setText("Sedia123tavolo+");

	}
}
