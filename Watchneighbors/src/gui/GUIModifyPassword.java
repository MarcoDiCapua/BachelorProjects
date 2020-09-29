package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import logic.User;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

/**
 * Class that creates the modify password interface
 * 
 * @author Marco Di Capua
 * @author Filippo Pelosi
 * @author Riccardo Zorzi
 * @version 1.0
 */
public class GUIModifyPassword extends JFrame {
	// campi
	private static final long serialVersionUID = 878459941820654445L;
	private JPanel contentPane;
	private Dimension frameDimension = new Dimension(400, 178);
	private static GUIModifyPassword frame;

	// constructor
	/**
	 * Create modify password interface
	 */
	public GUIModifyPassword() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(".\\images\\icona-watchNeighbors.png"));
		setAlwaysOnTop(true);
		setResizable(false);
		setTitle("Modifica password");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds((int) ((screenSize.getWidth() - frameDimension.getWidth()) / 2),
				(int) ((screenSize.getHeight() - frameDimension.getHeight()) / 2), frameDimension.width,
				frameDimension.height);
		paintInterface();
	}

	// methods
	private void paintInterface() {
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 150, 200 };
		gbl_contentPane.rowHeights = new int[] { 22, 22, 22, 22, 22 };
		gbl_contentPane.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		JLabel lblOldPassword = new JLabel("Inserire vecchia password:");
		lblOldPassword.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblOldPassword = new GridBagConstraints();
		gbc_lblOldPassword.anchor = GridBagConstraints.WEST;
		gbc_lblOldPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblOldPassword.gridx = 0;
		gbc_lblOldPassword.gridy = 0;
		contentPane.add(lblOldPassword, gbc_lblOldPassword);

		JPasswordField oldPassword = new JPasswordField();
		oldPassword.setFont(new Font("Tahoma", Font.PLAIN, 16));
		oldPassword.setPreferredSize(new Dimension(120, 22));
		GridBagConstraints gbc_oldPassword = new GridBagConstraints();
		gbc_oldPassword.fill = GridBagConstraints.HORIZONTAL;
		gbc_oldPassword.insets = new Insets(0, 0, 5, 0);
		gbc_oldPassword.gridx = 1;
		gbc_oldPassword.gridy = 0;
		contentPane.add(oldPassword, gbc_oldPassword);

		JLabel lblNewPassword = new JLabel("Inserire nuova password:");
		lblNewPassword.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblNewPassword = new GridBagConstraints();
		gbc_lblNewPassword.anchor = GridBagConstraints.WEST;
		gbc_lblNewPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewPassword.gridx = 0;
		gbc_lblNewPassword.gridy = 1;
		contentPane.add(lblNewPassword, gbc_lblNewPassword);

		JPasswordField newPassword = new JPasswordField();
		newPassword.setFont(new Font("Tahoma", Font.PLAIN, 16));
		newPassword.setPreferredSize(new Dimension(120, 22));
		GridBagConstraints gbc_newPassword = new GridBagConstraints();
		gbc_newPassword.fill = GridBagConstraints.HORIZONTAL;
		gbc_newPassword.insets = new Insets(0, 0, 5, 0);
		gbc_newPassword.gridx = 1;
		gbc_newPassword.gridy = 1;
		contentPane.add(newPassword, gbc_newPassword);

		JLabel lblConfirmPassword = new JLabel("Conferma password:");
		lblConfirmPassword.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblConfirmPassword = new GridBagConstraints();
		gbc_lblConfirmPassword.anchor = GridBagConstraints.WEST;
		gbc_lblConfirmPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblConfirmPassword.gridx = 0;
		gbc_lblConfirmPassword.gridy = 2;
		contentPane.add(lblConfirmPassword, gbc_lblConfirmPassword);

		JPasswordField confirmPassword = new JPasswordField();
		confirmPassword.setFont(new Font("Tahoma", Font.PLAIN, 16));
		confirmPassword.setPreferredSize(new Dimension(120, 22));
		GridBagConstraints gbc_confirmPassword = new GridBagConstraints();
		gbc_confirmPassword.insets = new Insets(0, 0, 5, 0);
		gbc_confirmPassword.fill = GridBagConstraints.HORIZONTAL;
		gbc_confirmPassword.gridx = 1;
		gbc_confirmPassword.gridy = 2;
		contentPane.add(confirmPassword, gbc_confirmPassword);

		JLabel lblError = new JLabel("");
		lblError.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblError.setVisible(false);
		lblError.setForeground(Color.RED);
		GridBagConstraints gbc_lblError = new GridBagConstraints();
		gbc_lblError.anchor = GridBagConstraints.WEST;
		gbc_lblError.insets = new Insets(0, 0, 0, 5);
		gbc_lblError.gridx = 0;
		gbc_lblError.gridy = 3;
		contentPane.add(lblError, gbc_lblError);

		JButton btnConfirm = new JButton("conferma");
		btnConfirm.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnConfirm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				lblError.setVisible(false);
				if (!User.userLogged.verifyPassword(oldPassword.getPassword())) {
					lblError.setText("Password errata!");
					lblError.setVisible(true);
				}
				if (!verifyPassword(new String(newPassword.getPassword()))) {
					lblError.setText("La password deve essere di almeno 7 caratteri!");
					lblError.setVisible(true);
				}

				if (!verifyConfirmPassword(new String(newPassword.getPassword()),
						new String(confirmPassword.getPassword()))) {
					lblError.setText("Le due password sono diverse!");
					lblError.setVisible(true);
				}
				if (!lblError.isVisible()) {
					User.userLogged.modifyPassword(new String(newPassword.getPassword()));
					JOptionPane.showMessageDialog(frame, "La password è stata modificata con successo!",
							"Password modificata", JOptionPane.INFORMATION_MESSAGE);
					frame.dispose();
				}
			}
		});

		GridBagConstraints gbc_btnConferma = new GridBagConstraints();
		gbc_btnConferma.anchor = GridBagConstraints.EAST;
		gbc_btnConferma.gridx = 1;
		gbc_btnConferma.gridy = 3;
		contentPane.add(btnConfirm, gbc_btnConferma);
	}

	private static boolean verifyPassword(String password) {
		return password.length() > 6;
	}

	private static boolean verifyConfirmPassword(String password, String confermaPassword) {
		return confermaPassword.equals(password);
	}

	/**
	 * Create and set visible modify password interface
	 */
	public static void main() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new GUIModifyPassword();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}