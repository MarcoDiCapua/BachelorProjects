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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

import appLibrarian.Proxy;
import dataModel.Login;
import utils.Directory;
import utils.Utility;

/**
 * This class create the modify password interface
 *
 * @author Marco Di Capua
 * @author Mattia Lo Schiavo
 * @author Filippo Pelosi
 * @author Riccardo Zorzi
 * @version 1.0
 */
public class GUIModifyPassword extends JDialog {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPasswordField passwordFieldOldPassword;
	private JPasswordField passwordFieldNewPassword;
	private final Dimension frameDimension = new Dimension(1100, 335);
	private JPasswordField passwordFieldConfirmNewPassword;

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
					GUIModifyPassword dialogPassword = new GUIModifyPassword(dialog);
					dialogPassword.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private GUIModifyPassword(JDialog dialog) {
		super(dialog, true);
		setResizable(false);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds((int) ((screenSize.getWidth() - frameDimension.getWidth()) / 2),
				(int) ((screenSize.getHeight() - frameDimension.getHeight()) / 2), frameDimension.width,
				frameDimension.height);
		setTitle("Modifica password");
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
		panelFieldsNorth.setPreferredSize(new Dimension(775, 250));
		panelFields.add(panelFieldsNorth, BorderLayout.NORTH);
		panelFieldsNorth.setLayout(new BorderLayout(0, 0));

		JPanel panelGridFields = new JPanel();
		panelGridFields.setBorder(new TitledBorder(null, "Modifica password", TitledBorder.LEADING, TitledBorder.TOP,
				new Font("Tahoma", Font.BOLD, 18), new Color(0, 0, 0)));
		panelGridFields.setPreferredSize(new Dimension(10, 250));
		panelFieldsNorth.add(panelGridFields, BorderLayout.CENTER);
		GridBagLayout gbl_panelGridFields = new GridBagLayout();
		gbl_panelGridFields.columnWidths = new int[] { 0, 147, 180, 31, 30, 223, 0 };
		gbl_panelGridFields.rowHeights = new int[] { 20, 30, 26, 26, 26, 26, 26, 26, 30 };
		gbl_panelGridFields.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panelGridFields.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
		panelGridFields.setLayout(gbl_panelGridFields);

		JLabel lblErrorOldPassword = new JLabel("");
		lblErrorOldPassword.setForeground(Color.RED);
		lblErrorOldPassword.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblErrorOldPassword = new GridBagConstraints();
		gbc_lblErrorOldPassword.anchor = GridBagConstraints.NORTH;
		gbc_lblErrorOldPassword.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblErrorOldPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblErrorOldPassword.gridwidth = 3;
		gbc_lblErrorOldPassword.gridx = 2;
		gbc_lblErrorOldPassword.gridy = 3;
		panelGridFields.add(lblErrorOldPassword, gbc_lblErrorOldPassword);

		passwordFieldOldPassword = new JPasswordField();
		passwordFieldOldPassword.setFont(new Font("Tahoma", Font.PLAIN, 16));
		passwordFieldOldPassword.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String oldPwd = new String(passwordFieldOldPassword.getPassword());
				String errorOldPwd = Utility.verifyNotEmptyField(oldPwd) + Utility.verifyPasswordLength(oldPwd)
						+ Utility.verifyLettersAndNumbers(oldPwd);
				lblErrorOldPassword.setText(errorOldPwd.split("-")[0]);
			}
		});

		JLabel lblOldPassword = new JLabel("Vecchia password:");
		lblOldPassword.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblOldPassword = new GridBagConstraints();
		gbc_lblOldPassword.anchor = GridBagConstraints.WEST;
		gbc_lblOldPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblOldPassword.gridx = 1;
		gbc_lblOldPassword.gridy = 2;
		panelGridFields.add(lblOldPassword, gbc_lblOldPassword);
		passwordFieldOldPassword.setColumns(5);
		GridBagConstraints gbc_passwordFieldOldPassword = new GridBagConstraints();
		gbc_passwordFieldOldPassword.anchor = GridBagConstraints.NORTH;
		gbc_passwordFieldOldPassword.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordFieldOldPassword.insets = new Insets(0, 0, 5, 5);
		gbc_passwordFieldOldPassword.gridx = 2;
		gbc_passwordFieldOldPassword.gridy = 2;
		panelGridFields.add(passwordFieldOldPassword, gbc_passwordFieldOldPassword);

		JLabel lblNewPassword = new JLabel("Nuova password:");
		lblNewPassword.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblNewPassword = new GridBagConstraints();
		gbc_lblNewPassword.anchor = GridBagConstraints.WEST;
		gbc_lblNewPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewPassword.gridx = 1;
		gbc_lblNewPassword.gridy = 4;
		panelGridFields.add(lblNewPassword, gbc_lblNewPassword);

		JLabel lblErrorNewPassword = new JLabel("");
		lblErrorNewPassword.setForeground(Color.RED);
		lblErrorNewPassword.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblErrorNewPassword = new GridBagConstraints();
		gbc_lblErrorNewPassword.anchor = GridBagConstraints.NORTH;
		gbc_lblErrorNewPassword.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblErrorNewPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblErrorNewPassword.gridwidth = 3;
		gbc_lblErrorNewPassword.gridx = 2;
		gbc_lblErrorNewPassword.gridy = 5;
		panelGridFields.add(lblErrorNewPassword, gbc_lblErrorNewPassword);

		passwordFieldConfirmNewPassword = new JPasswordField();
		passwordFieldNewPassword = new JPasswordField();

		passwordFieldNewPassword.setFont(new Font("Tahoma", Font.PLAIN, 16));
		passwordFieldNewPassword.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String newPwd = new String(passwordFieldNewPassword.getPassword());
				String confirmNewPwd = new String(passwordFieldConfirmNewPassword.getPassword());
				String errorNewPwd = Utility.verifyNotEmptyField(newPwd) + Utility.verifyPasswordLength(newPwd)
						+ Utility.verifyLettersAndNumbers(newPwd)
						+ Utility.verifyConfirmPassword(newPwd, confirmNewPwd);
				lblErrorNewPassword.setText(errorNewPwd.split("-")[0]);

			}
		});
		passwordFieldNewPassword.setColumns(5);
		GridBagConstraints gbc_passwordFieldNewPassword = new GridBagConstraints();
		gbc_passwordFieldNewPassword.anchor = GridBagConstraints.NORTH;
		gbc_passwordFieldNewPassword.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordFieldNewPassword.insets = new Insets(0, 0, 5, 5);
		gbc_passwordFieldNewPassword.gridx = 2;
		gbc_passwordFieldNewPassword.gridy = 4;
		panelGridFields.add(passwordFieldNewPassword, gbc_passwordFieldNewPassword);

		JLabel lblConfirmNewPassword = new JLabel("Conferma password:");
		lblConfirmNewPassword.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblConfirmNewPassword = new GridBagConstraints();
		gbc_lblConfirmNewPassword.anchor = GridBagConstraints.WEST;
		gbc_lblConfirmNewPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblConfirmNewPassword.gridx = 1;
		gbc_lblConfirmNewPassword.gridy = 6;
		panelGridFields.add(lblConfirmNewPassword, gbc_lblConfirmNewPassword);

		JLabel lblErrorConfirmNewPassword = new JLabel("");
		lblErrorConfirmNewPassword.setForeground(Color.RED);
		lblErrorConfirmNewPassword.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblErrorConfirmNewPassword = new GridBagConstraints();
		gbc_lblErrorConfirmNewPassword.anchor = GridBagConstraints.NORTH;
		gbc_lblErrorConfirmNewPassword.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblErrorConfirmNewPassword.insets = new Insets(0, 0, 0, 5);
		gbc_lblErrorConfirmNewPassword.gridwidth = 3;
		gbc_lblErrorConfirmNewPassword.gridx = 2;
		gbc_lblErrorConfirmNewPassword.gridy = 7;
		panelGridFields.add(lblErrorConfirmNewPassword, gbc_lblErrorConfirmNewPassword);

		passwordFieldConfirmNewPassword.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String newPwd = new String(passwordFieldNewPassword.getPassword());
				String confirmNewPwd = new String(passwordFieldConfirmNewPassword.getPassword());
				String errorConfirmNewPwd = Utility.verifyNotEmptyField(confirmNewPwd)
						+ Utility.verifyPasswordLength(confirmNewPwd) + Utility.verifyLettersAndNumbers(confirmNewPwd)
						+ Utility.verifyConfirmPassword(newPwd, confirmNewPwd);
				lblErrorConfirmNewPassword.setText(errorConfirmNewPwd.split("-")[0]);

			}
		});
		passwordFieldConfirmNewPassword.setFont(new Font("Tahoma", Font.PLAIN, 16));
		passwordFieldConfirmNewPassword.setColumns(5);
		GridBagConstraints gbc_passwordFieldConfirmNewPassword = new GridBagConstraints();
		gbc_passwordFieldConfirmNewPassword.anchor = GridBagConstraints.NORTH;
		gbc_passwordFieldConfirmNewPassword.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordFieldConfirmNewPassword.insets = new Insets(0, 0, 5, 5);
		gbc_passwordFieldConfirmNewPassword.gridx = 2;
		gbc_passwordFieldConfirmNewPassword.gridy = 6;
		panelGridFields.add(passwordFieldConfirmNewPassword, gbc_passwordFieldConfirmNewPassword);

		JPanel panelConfirm = new JPanel();
		panelConfirm.setPreferredSize(new Dimension(10, 50));
		panelFields.add(panelConfirm, BorderLayout.SOUTH);
		panelConfirm.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		JLabel lblError = new JLabel("Correggere i dati errati per continuare!");
		lblError.setVisible(false);
		lblError.setForeground(Color.RED);
		lblError.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelConfirm.add(lblError);

		JButton btnConfirm = new JButton("Modifica!");
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (lblErrorOldPassword.getText().length() == 0 && lblErrorNewPassword.getText().length() == 0
						&& lblErrorConfirmNewPassword.getText().length() == 0) {
					lblError.setVisible(false);
					String response = Proxy.instance().modifyPassword(
							new String(passwordFieldOldPassword.getPassword()),
							new String(passwordFieldNewPassword.getPassword()));
					if (response != null) {
						if (response.equals("Operation completed")) {
							Login.getUserLogged().setPassword(new String(passwordFieldNewPassword.getPassword()));
							JOptionPane.showMessageDialog(null, "La password è stata modificata",
									"Password modificata!", JOptionPane.INFORMATION_MESSAGE);
							dispose();
						} else {
							JOptionPane.showMessageDialog(null,
									"Non è stato possibile modificare la password! (Errore: " + response + ")",
									"Errore!", JOptionPane.ERROR_MESSAGE);
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
