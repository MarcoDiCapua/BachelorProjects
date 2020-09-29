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

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

import appReader.Proxy;
import dataModel.Login;
import utils.Directory;
import utils.Utility;

/**
 * This class create the delete user interface
 *
 * @author Marco Di Capua
 * @author Mattia Lo Schiavo
 * @author Filippo Pelosi
 * @author Riccardo Zorzi
 * @version 1.0
 */
public class GUIDeleteUser extends JDialog {
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JPasswordField passwordField;
	private JPasswordField passwordFieldVerify;
	private JLabel lblErrorConfirmPassword;
	private JLabel lblErrorPassword;
	private final Dimension frameDimension = new Dimension(1100, 355);

	/**
	 * Launch the dialog interface
	 * 
	 * @param frame
	 *            the JDialog from which the dialog is displayed
	 */
	public static void main(JDialog frame) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIDeleteUser dialog = new GUIDeleteUser(frame);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private GUIDeleteUser(JDialog frame) {
		super(frame, true);
		setResizable(false);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds((int) ((screenSize.getWidth() - frameDimension.getWidth()) / 2),
				(int) ((screenSize.getHeight() - frameDimension.getHeight()) / 2), frameDimension.width,
				frameDimension.height);
		setTitle("Elimina utente");
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
		panelFieldsNorth.setPreferredSize(new Dimension(600, 200));
		panelFields.add(panelFieldsNorth, BorderLayout.NORTH);
		panelFieldsNorth.setLayout(new BorderLayout(0, 0));

		JPanel panelGridFields = new JPanel();
		panelGridFields.setBorder(new TitledBorder(null, "Elimina utente", TitledBorder.LEADING, TitledBorder.TOP,
				new Font("Tahoma", Font.BOLD, 18), new Color(0, 0, 0)));
		panelGridFields.setPreferredSize(new Dimension(10, 175));
		panelFieldsNorth.add(panelGridFields, BorderLayout.NORTH);
		GridBagLayout gbl_panelGridFields = new GridBagLayout();
		gbl_panelGridFields.columnWidths = new int[] { 147, 185, 0 };
		gbl_panelGridFields.rowHeights = new int[] { 26, 26, 20, 26, 20, 0 };
		gbl_panelGridFields.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		gbl_panelGridFields.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panelGridFields.setLayout(gbl_panelGridFields);

		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblPassword = new GridBagConstraints();
		gbc_lblPassword.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblPassword.gridx = 0;
		gbc_lblPassword.gridy = 1;
		panelGridFields.add(lblPassword, gbc_lblPassword);
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		passwordField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String pwd = new String(passwordField.getPassword());
				String vpwd = new String(passwordFieldVerify.getPassword());
				String errorPwd = Utility.verifyNotEmptyField(pwd) + Utility.verifyPasswordLength(pwd)
						+ Utility.verifyLettersAndNumbers(pwd) + Utility.verifyConfirmPassword(pwd, vpwd);
				lblErrorPassword.setText(errorPwd.split("-")[0]);
			}
		});
		passwordField.setColumns(5);
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.anchor = GridBagConstraints.NORTH;
		gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField.insets = new Insets(0, 0, 5, 0);
		gbc_passwordField.gridx = 1;
		gbc_passwordField.gridy = 1;
		panelGridFields.add(passwordField, gbc_passwordField);

		passwordFieldVerify = new JPasswordField();

		passwordFieldVerify.setFont(new Font("Tahoma", Font.PLAIN, 16));
		passwordFieldVerify.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String pwd = new String(passwordField.getPassword());
				String vpwd = new String(passwordFieldVerify.getPassword());
				String errorVpwd = Utility.verifyNotEmptyField(vpwd) + Utility.verifyPasswordLength(vpwd)
						+ Utility.verifyLettersAndNumbers(vpwd) + Utility.verifyConfirmPassword(pwd, vpwd);
				lblErrorConfirmPassword.setText(errorVpwd.split("-")[0]);
			}
		});

		lblErrorPassword = new JLabel("");
		lblErrorPassword.setForeground(Color.RED);
		lblErrorPassword.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblErrorPassword = new GridBagConstraints();
		gbc_lblErrorPassword.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblErrorPassword.insets = new Insets(0, 0, 5, 0);
		gbc_lblErrorPassword.gridwidth = 2;
		gbc_lblErrorPassword.gridx = 0;
		gbc_lblErrorPassword.gridy = 2;
		panelGridFields.add(lblErrorPassword, gbc_lblErrorPassword);

		JLabel lblConfrimPassword = new JLabel("Conferma password:");
		lblConfrimPassword.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblConfrimPassword = new GridBagConstraints();
		gbc_lblConfrimPassword.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblConfrimPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblConfrimPassword.gridx = 0;
		gbc_lblConfrimPassword.gridy = 3;
		panelGridFields.add(lblConfrimPassword, gbc_lblConfrimPassword);
		passwordFieldVerify.setColumns(5);
		GridBagConstraints gbc_passwordFieldVerify = new GridBagConstraints();
		gbc_passwordFieldVerify.anchor = GridBagConstraints.NORTH;
		gbc_passwordFieldVerify.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordFieldVerify.insets = new Insets(0, 0, 5, 0);
		gbc_passwordFieldVerify.gridx = 1;
		gbc_passwordFieldVerify.gridy = 3;
		panelGridFields.add(passwordFieldVerify, gbc_passwordFieldVerify);

		lblErrorConfirmPassword = new JLabel("");
		lblErrorConfirmPassword.setForeground(Color.RED);
		lblErrorConfirmPassword.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblErrorConfirmPassword = new GridBagConstraints();
		gbc_lblErrorConfirmPassword.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblErrorConfirmPassword.gridwidth = 2;
		gbc_lblErrorConfirmPassword.gridx = 0;
		gbc_lblErrorConfirmPassword.gridy = 4;
		panelGridFields.add(lblErrorConfirmPassword, gbc_lblErrorConfirmPassword);

		JPanel panelConfirm = new JPanel();
		panelConfirm.setPreferredSize(new Dimension(600, 80));
		panelFields.add(panelConfirm, BorderLayout.SOUTH);
		panelConfirm.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 20));

		JLabel lblError = new JLabel("Correggere i dati errati per continuare!");
		lblError.setVisible(false);
		lblError.setForeground(Color.RED);
		lblError.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelConfirm.add(lblError);

		JButton btnConfirm = new JButton("Elimina!");
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (lblErrorPassword.getText().length() == 0 && lblErrorConfirmPassword.getText().length() == 0) {
					lblError.setVisible(false);
					String response = Proxy.instance().deleteUser(Login.getUserLogged());
					if (response != null) {
						if (response.equals("Operation completed")) {
							JOptionPane.showMessageDialog(null, "L'utente è stato cancellato",
									"Cancellazione avvenuta!", JOptionPane.INFORMATION_MESSAGE);
							Login.setUserLogged(null);
							dispose();
							GUILogin.main();
						} else {
							JOptionPane.showMessageDialog(null,
									"Non è stato possibile cancellare l'utente! (Errore: " + response + ")", "Errore!",
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
