package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.Font;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import enums.City;
import enums.District;
import logic.User;

/**
 * Class that creates the modify user interface
 * 
 * @author Marco Di Capua
 * @author Filippo Pelosi
 * @author Riccardo Zorzi
 * @version 1.0
 */
public class GUIModifyProfile extends JFrame {
	// camps
	private static final long serialVersionUID = 5709532360861185329L;
	private JPanel contentPane;
	private JTextField textField_Name;
	private JTextField textField_UserId;
	private JTextField textField_Email;
	private JTextField textField_Surname;
	private JSpinner spnLatitude;
	private JSpinner spnLongitude;
	private JComboBox<String> cmbDistrict;
	private JComboBox<String> cmbCity;
	private JLabel lblIncorrectCoordinates;
	private JLabel lblInvalidEmail;
	private JLabel lblInvalidUserId;
	private final Dimension frameDimension = new Dimension(660, 390);
	private static GUIModifyProfile frame;

	// Constructor
	/**
	 * create the modify user interface
	 */
	public GUIModifyProfile() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(".\\images\\icona-watchNeighbors.png"));
		setAlwaysOnTop(true);
		setResizable(false);
		setTitle("Modifica profilo");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds((int) ((screenSize.getWidth() - frameDimension.getWidth()) / 2),
				(int) ((screenSize.getHeight() - frameDimension.getHeight()) / 2), frameDimension.width,
				frameDimension.height);
		paintInterface();
		fillInFields();
	}

	// Methods
	private void paintInterface() {
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		JPanel northPanel = paintNorthPanel();
		contentPane.add(northPanel, BorderLayout.NORTH);
		JPanel centralPanel = paintCentralPanel();
		contentPane.add(centralPanel, BorderLayout.CENTER);

		JPanel southPanel = paintSouthPanel();
		contentPane.add(southPanel, BorderLayout.SOUTH);
	}

	private JPanel paintNorthPanel() {
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(150, 200));
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Dati utente",
				TitledBorder.LEADING, TitledBorder.TOP, new Font("Tahoma", Font.PLAIN, 16), new Color(0, 0, 0)));
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 53, 146, 58, 4, 315, 0 };
		gbl_panel.rowHeights = new int[] { 23, 22, 22, 20, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 2.0, 2.0, 2.0, 2.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JLabel lblName = new JLabel("Nome:");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.anchor = GridBagConstraints.WEST;
		gbc_lblName.insets = new Insets(0, 0, 5, 5);
		gbc_lblName.gridx = 0;
		gbc_lblName.gridy = 0;
		panel.add(lblName, gbc_lblName);

		textField_Name = new JTextField();
		textField_Name.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textField_Name.setColumns(10);
		GridBagConstraints gbc_textField_Name = new GridBagConstraints();
		gbc_textField_Name.anchor = GridBagConstraints.WEST;
		gbc_textField_Name.insets = new Insets(0, 0, 5, 5);
		gbc_textField_Name.gridx = 1;
		gbc_textField_Name.gridy = 0;
		panel.add(textField_Name, gbc_textField_Name);

		JLabel lblSurname = new JLabel("Cognome:");
		lblSurname.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblSurname = new GridBagConstraints();
		gbc_lblSurname.anchor = GridBagConstraints.WEST;
		gbc_lblSurname.insets = new Insets(0, 0, 5, 5);
		gbc_lblSurname.gridwidth = 2;
		gbc_lblSurname.gridx = 2;
		gbc_lblSurname.gridy = 0;
		panel.add(lblSurname, gbc_lblSurname);

		textField_Surname = new JTextField();
		textField_Surname.setColumns(10);
		textField_Surname.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_textField_Surname = new GridBagConstraints();
		gbc_textField_Surname.anchor = GridBagConstraints.WEST;
		gbc_textField_Surname.insets = new Insets(0, 0, 5, 0);
		gbc_textField_Surname.gridx = 4;
		gbc_textField_Surname.gridy = 0;
		panel.add(textField_Surname, gbc_textField_Surname);

		JLabel lblUserId = new JLabel("UserId:");
		lblUserId.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblUserId = new GridBagConstraints();
		gbc_lblUserId.anchor = GridBagConstraints.WEST;
		gbc_lblUserId.insets = new Insets(0, 0, 5, 5);
		gbc_lblUserId.gridx = 0;
		gbc_lblUserId.gridy = 1;
		panel.add(lblUserId, gbc_lblUserId);

		textField_UserId = new JTextField();
		textField_UserId.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textField_UserId.setColumns(10);
		GridBagConstraints gbc_textField_UserId = new GridBagConstraints();
		gbc_textField_UserId.anchor = GridBagConstraints.WEST;
		gbc_textField_UserId.insets = new Insets(0, 0, 5, 5);
		gbc_textField_UserId.gridx = 1;
		gbc_textField_UserId.gridy = 1;
		panel.add(textField_UserId, gbc_textField_UserId);

		lblInvalidUserId = new JLabel("UserId non valido! Inserire un altro UserId!");
		lblInvalidUserId.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblInvalidUserId.setVisible(false);
		lblInvalidUserId.setForeground(Color.RED);
		GridBagConstraints gbc_lblInvalidUserId = new GridBagConstraints();
		gbc_lblInvalidUserId.anchor = GridBagConstraints.WEST;
		gbc_lblInvalidUserId.insets = new Insets(0, 0, 5, 0);
		gbc_lblInvalidUserId.gridwidth = 3;
		gbc_lblInvalidUserId.gridx = 2;
		gbc_lblInvalidUserId.gridy = 1;
		panel.add(lblInvalidUserId, gbc_lblInvalidUserId);

		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblEmail = new GridBagConstraints();
		gbc_lblEmail.anchor = GridBagConstraints.WEST;
		gbc_lblEmail.insets = new Insets(0, 0, 5, 5);
		gbc_lblEmail.gridx = 0;
		gbc_lblEmail.gridy = 2;
		panel.add(lblEmail, gbc_lblEmail);

		textField_Email = new JTextField();
		textField_Email.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textField_Email.setColumns(15);
		GridBagConstraints gbc_textField_Email = new GridBagConstraints();
		gbc_textField_Email.anchor = GridBagConstraints.WEST;
		gbc_textField_Email.insets = new Insets(0, 0, 5, 5);
		gbc_textField_Email.gridwidth = 2;
		gbc_textField_Email.gridx = 1;
		gbc_textField_Email.gridy = 2;
		panel.add(textField_Email, gbc_textField_Email);

		lblInvalidEmail = new JLabel("Mail non valida! Inserire un altro indirizzo mail!");
		lblInvalidEmail.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblInvalidEmail.setVisible(false);
		lblInvalidEmail.setForeground(Color.RED);
		GridBagConstraints gbc_lblInvalidEmail = new GridBagConstraints();
		gbc_lblInvalidEmail.anchor = GridBagConstraints.WEST;
		gbc_lblInvalidEmail.insets = new Insets(0, 0, 5, 0);
		gbc_lblInvalidEmail.gridwidth = 2;
		gbc_lblInvalidEmail.gridx = 3;
		gbc_lblInvalidEmail.gridy = 2;
		panel.add(lblInvalidEmail, gbc_lblInvalidEmail);

		JLabel lblWarning = new JLabel("\u00C8 necessario compilare tutti i campi prima di poter procedere.");
		lblWarning.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblWarning = new GridBagConstraints();
		gbc_lblWarning.anchor = GridBagConstraints.WEST;
		gbc_lblWarning.gridwidth = 5;
		gbc_lblWarning.gridx = 0;
		gbc_lblWarning.gridy = 3;
		panel.add(lblWarning, gbc_lblWarning);

		textField_UserId.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (!verifyUserId(textField_UserId.getText()) || !verifyBiographicalData(textField_UserId.getText())) {
					lblInvalidUserId.setVisible(true);
				} else {
					lblInvalidUserId.setVisible(false);
				}
			}
		});

		textField_Email.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (!verifyEmail(textField_Email.getText())) {
					lblInvalidEmail.setVisible(true);
				} else {
					lblInvalidEmail.setVisible(false);
				}
			}
		});

		return panel;
	}

	private JPanel paintCentralPanel() {
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(250, 160));
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Geolocalizzazione",
				TitledBorder.LEADING, TitledBorder.TOP, new Font("Tahoma", Font.PLAIN, 16), new Color(0, 0, 0)));
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 75, 19, 34, 30, 89, 140, 109, 0 };
		gbl_panel.rowHeights = new int[] { 22, 22, 21, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 2.0, 2.0, 2.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JLabel lblLatitude = new JLabel("Latitudine:");
		lblLatitude.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblLatitude = new GridBagConstraints();
		gbc_lblLatitude.anchor = GridBagConstraints.WEST;
		gbc_lblLatitude.insets = new Insets(0, 0, 5, 5);
		gbc_lblLatitude.gridx = 0;
		gbc_lblLatitude.gridy = 0;
		panel.add(lblLatitude, gbc_lblLatitude);

		spnLatitude = new JSpinner(
				new SpinnerNumberModel(new Double(0.0), new Double(0.0), new Double(10.0), new Double(0.1)));
		spnLatitude.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_spnLatitude = new GridBagConstraints();
		gbc_spnLatitude.fill = GridBagConstraints.BOTH;
		gbc_spnLatitude.insets = new Insets(0, 0, 5, 5);
		gbc_spnLatitude.gridwidth = 2;
		gbc_spnLatitude.gridx = 1;
		gbc_spnLatitude.gridy = 0;
		panel.add(spnLatitude, gbc_spnLatitude);

		JLabel lblLongitude = new JLabel("Longitudine:");
		lblLongitude.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblLongitude = new GridBagConstraints();
		gbc_lblLongitude.anchor = GridBagConstraints.WEST;
		gbc_lblLongitude.insets = new Insets(0, 0, 5, 5);
		gbc_lblLongitude.gridx = 4;
		gbc_lblLongitude.gridy = 0;
		panel.add(lblLongitude, gbc_lblLongitude);

		spnLongitude = new JSpinner(
				new SpinnerNumberModel(new Double(0.0), new Double(0.0), new Double(20.0), new Double(0.1)));
		spnLongitude.setMinimumSize(new Dimension(60, 22));
		spnLongitude.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_spnLongitude = new GridBagConstraints();
		gbc_spnLongitude.anchor = GridBagConstraints.WEST;
		gbc_spnLongitude.fill = GridBagConstraints.VERTICAL;
		gbc_spnLongitude.insets = new Insets(0, 0, 5, 5);
		gbc_spnLongitude.gridx = 5;
		gbc_spnLongitude.gridy = 0;
		panel.add(spnLongitude, gbc_spnLongitude);

		JLabel lblSelectCity = new JLabel("Seleziona citt\u00E0:");
		lblSelectCity.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblSelectCity = new GridBagConstraints();
		gbc_lblSelectCity.anchor = GridBagConstraints.WEST;
		gbc_lblSelectCity.insets = new Insets(0, 0, 5, 5);
		gbc_lblSelectCity.gridwidth = 2;
		gbc_lblSelectCity.gridx = 0;
		gbc_lblSelectCity.gridy = 1;
		panel.add(lblSelectCity, gbc_lblSelectCity);

		cmbCity = new JComboBox<String>(new DefaultComboBoxModel<String>(City.createCitiesArray()));
		cmbCity.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_cmbCity = new GridBagConstraints();
		gbc_cmbCity.anchor = GridBagConstraints.WEST;
		gbc_cmbCity.insets = new Insets(0, 0, 5, 5);
		gbc_cmbCity.gridwidth = 3;
		gbc_cmbCity.gridx = 2;
		gbc_cmbCity.gridy = 1;
		panel.add(cmbCity, gbc_cmbCity);

		JLabel lblSelectDistrict = new JLabel("Seleziona quartiere:");
		lblSelectDistrict.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblSelectDistrict = new GridBagConstraints();
		gbc_lblSelectDistrict.anchor = GridBagConstraints.WEST;
		gbc_lblSelectDistrict.insets = new Insets(0, 0, 5, 5);
		gbc_lblSelectDistrict.gridx = 5;
		gbc_lblSelectDistrict.gridy = 1;
		panel.add(lblSelectDistrict, gbc_lblSelectDistrict);

		cmbDistrict = new JComboBox<String>(new DefaultComboBoxModel<String>(District.arrayDistricts()));
		cmbDistrict.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_cmbDistrict = new GridBagConstraints();
		gbc_cmbDistrict.anchor = GridBagConstraints.WEST;
		gbc_cmbDistrict.insets = new Insets(0, 0, 5, 0);
		gbc_cmbDistrict.gridx = 6;
		gbc_cmbDistrict.gridy = 1;
		panel.add(cmbDistrict, gbc_cmbDistrict);

		lblIncorrectCoordinates = new JLabel("Le coordinate e il quartiere non corrispondo!");
		lblIncorrectCoordinates.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblIncorrectCoordinates.setVisible(false);
		lblIncorrectCoordinates.setForeground(Color.RED);
		GridBagConstraints gbc_lblIncorrectCoordinates = new GridBagConstraints();
		gbc_lblIncorrectCoordinates.anchor = GridBagConstraints.WEST;
		gbc_lblIncorrectCoordinates.fill = GridBagConstraints.VERTICAL;
		gbc_lblIncorrectCoordinates.insets = new Insets(0, 0, 0, 5);
		gbc_lblIncorrectCoordinates.gridwidth = 6;
		gbc_lblIncorrectCoordinates.gridx = 0;
		gbc_lblIncorrectCoordinates.gridy = 2;
		panel.add(lblIncorrectCoordinates, gbc_lblIncorrectCoordinates);

		return panel;
	}

	private JPanel paintSouthPanel() {
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(250, 35));
		panel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		JLabel lblWariningWrongData = new JLabel("Correggere i dati errati prima di procedere!");
		lblWariningWrongData.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel.add(lblWariningWrongData);
		lblWariningWrongData.setVisible(false);
		lblWariningWrongData.setForeground(Color.RED);

		JButton btnModifyPassword = new JButton("Cambia password");
		btnModifyPassword.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnModifyPassword.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GUIModifyPassword.main();
			}
		});
		panel.add(btnModifyPassword);

		JButton btnConfirm = new JButton("Conferma modifiche!");
		btnConfirm.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel.add(btnConfirm);
		btnConfirm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!District.stringToEnum((String) cmbDistrict.getSelectedItem())
						.belongs((double) spnLatitude.getValue(), (double) spnLongitude.getValue())) {
					lblIncorrectCoordinates.setVisible(true);
				} else {
					lblIncorrectCoordinates.setVisible(false);
				}

				if (!verifyBiographicalData(textField_Name.getText())
						|| !verifyBiographicalData(textField_Surname.getText()) || lblIncorrectCoordinates.isVisible()
						|| lblInvalidEmail.isVisible() || lblInvalidUserId.isVisible()) {
					lblWariningWrongData.setVisible(true);
				} else {
					User.userLogged.modifyUser(textField_Name.getText(), textField_Surname.getText(),
							textField_UserId.getText(), textField_Email.getText(), (double) spnLatitude.getValue(),
							(double) spnLongitude.getValue(),
							District.stringToEnum(cmbDistrict.getSelectedItem().toString()),
							City.stringToEnum(cmbCity.getSelectedItem().toString()));
					JOptionPane.showMessageDialog(frame, "Il profilo è stato modificato!", "Attenzione",
							JOptionPane.INFORMATION_MESSAGE);
					frame.dispose();
				}
			}
		});

		return panel;
	}

	private static boolean verifyBiographicalData(String data) {
		return data.length() != 0;
	}

	private static boolean verifyEmail(String email) {
		return !(email.length() < 8 || !email.contains(".") || !email.contains("@") || email.contains(" "));
	}

	private static boolean verifyUserId(String userId) {
		for (int i = 0; i < User.users.size(); i++) {
			if (userId.equals(User.users.get(i).getUserId().toString())) {
				return false;
			}
		}

		return userId.trim().length() != 0;
	}

	private void fillInFields() {
		textField_Name.setText(User.userLogged.getName());
		textField_Surname.setText(User.userLogged.getSurname());
		textField_UserId.setText(User.userLogged.getUserId());
		textField_Email.setText(User.userLogged.getEmail());
		spnLatitude.setValue(User.userLogged.getLatitude());
		spnLongitude.setValue(User.userLogged.getLongitude());
		cmbDistrict.setSelectedItem(User.userLogged.getDistrict().toString());
		cmbCity.setSelectedItem(User.userLogged.getCity().toString());
	}

	/**
	 * create and set visible the modify user interface
	 */
	public static void main() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new GUIModifyProfile();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}