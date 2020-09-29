package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import enums.City;
import enums.District;
import logic.User;
import logic.Utility;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Class that creates the registration interface
 * 
 * @author Marco Di Capua
 * @author Filippo Pelosi
 * @author Riccardo Zorzi
 * @version 1.0
 */
public class GUIRegistration extends JFrame {
	// camps
	private JPasswordField passwordField_ConfirmPassword;
	private JPasswordField passwordField_Password;
	private JTextField textField_Name;
	private JTextField textField_UserId;
	private JTextField textField_Email;
	private JTextField textField_Surname;
	private JSpinner spnLatitude;
	private JSpinner spnLongitude;
	private JComboBox<String> cmbDistrict;
	private JComboBox<String> cmbCity;
	private JLabel lblIncorrectCoordinates;
	private JLabel lblDifferentPasswords;
	private JLabel lblInvalidPassword;
	private JLabel lblInvalidEmail;
	private JLabel lblInvalidUserid;
	private JPanel westPanel;
	private JScrollPane scrollPane;
	private JSlider slider;
	private JLabel lblMap;
	private JPanel mapPanel;
	private final Dimension frameDimension = new Dimension(1555, 500);
	private static int mouseStartX;
	private static int mouseStartY;
	private static GUIRegistration frame;
	private static JLabel lblHouse = new JLabel(new ImageIcon(".\\images\\segnaposto_casa.png"));
	private static final Dimension POSITION_MARK_DIMENSION = new Dimension(31, 31);
	private static final Dimension MAP_DIMENSION = new Dimension(500, 250);
	private static final int PIXELS_PER_DEGREE = 25;
	private static final long serialVersionUID = -1501502911097623907L;

	// constructors
	/**
	 * Create registration interface
	 */
	public GUIRegistration() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(".\\images\\icona-watchNeighbors.png"));
		setAlwaysOnTop(true);
		setTitle("Registrazione");
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				GUIMain.enableFrame();
				frame.dispose();
			}
		});

		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		setBounds((int) ((screenSize.getWidth() - frameDimension.getWidth()) / 2),
				(int) ((screenSize.getHeight() - frameDimension.getHeight()) / 2), (int) frameDimension.getWidth(),
				(int) frameDimension.getHeight());
		paintInterface();
	}

	// methods
	private void paintInterface() {
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		JScrollPane scrollPaneInterface = new JScrollPane();
		contentPane.add(scrollPaneInterface, BorderLayout.CENTER);
		JPanel interfacePanel = new JPanel();
		scrollPaneInterface.setViewportView(interfacePanel);
		interfacePanel.setLayout(new BorderLayout(0, 0));

		JPanel eastPanel = new JPanel();
		eastPanel.setPreferredSize(new Dimension(775, 10));
		eastPanel.setLayout(new BorderLayout(0, 0));
		interfacePanel.add(eastPanel, BorderLayout.EAST);

		JPanel northEastPanel = new JPanel();
		northEastPanel.setPreferredSize(new Dimension(775, 450));
		eastPanel.add(northEastPanel, BorderLayout.NORTH);
		northEastPanel.setLayout(new BorderLayout(0, 0));
		JPanel northPanel = paintNorthPanel();
		northEastPanel.add(northPanel, BorderLayout.NORTH);
		JPanel centralPanel = paintCentralPanel();
		northEastPanel.add(centralPanel, BorderLayout.CENTER);

		JPanel southPanel = paintSouthPanel();
		northEastPanel.add(southPanel, BorderLayout.SOUTH);

		westPanel = new JPanel();
		westPanel.setLayout(null);
		westPanel.setPreferredSize(new Dimension(730, 10));
		interfacePanel.add(westPanel, BorderLayout.WEST);

		JLabel lblNorth = new JLabel("Nord");
		lblNorth.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNorth.setBounds(330, 30, 51, 17);
		westPanel.add(lblNorth);

		JLabel lblSouth = new JLabel("Sud");
		lblSouth.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblSouth.setBounds(338, 388, 36, 16);
		westPanel.add(lblSouth);

		JLabel lblWest = new JLabel("Ovest");
		lblWest.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblWest.setBounds(10, 205, 51, 16);
		westPanel.add(lblWest);

		JLabel lblEast = new JLabel("Est");
		lblEast.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblEast.setBounds(670, 205, 30, 22);
		westPanel.add(lblEast);

		paintMapPanel();
		scrollPane = new JScrollPane(mapPanel);
		scrollPane.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollPane.setSize(new Dimension(MAP_DIMENSION.width + 62, MAP_DIMENSION.height + 62));
		scrollPane.setPreferredSize(new Dimension(MAP_DIMENSION.width + 62, MAP_DIMENSION.height + 62));
		scrollPane.setMinimumSize(new Dimension(MAP_DIMENSION.width + 62, MAP_DIMENSION.height + 62));
		scrollPane.setMaximumSize(new Dimension(MAP_DIMENSION.width + 62, MAP_DIMENSION.height + 62));
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBorder(null);
		scrollPane.setBounds(73, 60, MAP_DIMENSION.width + 62, MAP_DIMENSION.height + 62);
		westPanel.add(scrollPane);

		mapPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				mouseStartX = e.getX();
				mouseStartY = e.getY();
			}

			@Override
			public void mouseClicked(MouseEvent evt) {
				insertCoordinates(evt.getX(), evt.getY());
				lblHouse.setBounds((int) Math.floor(evt.getX()) - (POSITION_MARK_DIMENSION.width / 2),
						(int) Math.floor(evt.getY()) - POSITION_MARK_DIMENSION.height, POSITION_MARK_DIMENSION.width,
						POSITION_MARK_DIMENSION.height);
				lblHouse.setVisible(true);
				mapPanel.setComponentZOrder(lblHouse, getComponentCount() - 1);
			}
		});

		mapPanel.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				JViewport viewPort = scrollPane.getViewport();
				Point viewPortPoint = viewPort.getViewPosition();
				viewPortPoint.translate(mouseStartX - e.getX(), mouseStartY - e.getY());
				mapPanel.scrollRectToVisible(new Rectangle(viewPortPoint, viewPort.getSize()));
			}

		});

		slider = new JSlider(10, 20, 10);
		slider.setOrientation(SwingConstants.VERTICAL);
		slider.setBounds(701, 145, 22, 234);
		westPanel.add(slider);
		slider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				try {
					lblHouse.setVisible(false);
					Double zoom = slider.getValue() / 10.0;
					BufferedImage sourceBuffer;

					if (cmbCity.getSelectedItem().equals("Como")) {
						sourceBuffer = ImageIO.read(new File(".\\images\\Como_Registrazione.png"));
					} else if (cmbCity.getSelectedItem().equals("Lecco")) {
						sourceBuffer = ImageIO.read(new File(".\\images\\Lecco_Registrazione.png"));
					} else {
						sourceBuffer = ImageIO.read(new File(".\\images\\Varese_Registrazione.png"));
					}

					BufferedImage destinationBuffer = new BufferedImage((int) (sourceBuffer.getWidth() * zoom),
							(int) (sourceBuffer.getHeight() * zoom), BufferedImage.TYPE_INT_RGB);
					Graphics2D graphics2D = destinationBuffer.createGraphics();
					AffineTransform attribute = AffineTransform.getScaleInstance(zoom, zoom);
					graphics2D.drawRenderedImage(sourceBuffer, attribute);
					ImageIcon imageIcon = new ImageIcon(destinationBuffer);

					lblMap.setIcon(imageIcon);
					lblMap.setSize(imageIcon.getIconWidth(), imageIcon.getIconHeight());
					mapPanel.setPreferredSize(new Dimension(lblMap.getSize().width + 62, lblMap.getSize().height + 62));

				} catch (IOException e) {
					JOptionPane.showMessageDialog(null,
							"Impossibie caricare la mappa! Il file potrebbe essere stato rimosso o spostato!",
							"Errore!", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		JLabel lblIncreaseZoom = new JLabel("+");
		lblIncreaseZoom.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblIncreaseZoom.setBounds(699, 117, 25, 30);
		westPanel.add(lblIncreaseZoom);

		JLabel lblDecreaseZoom = new JLabel("-");
		lblDecreaseZoom.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblDecreaseZoom.setBounds(703, 377, 13, 21);
		westPanel.add(lblDecreaseZoom);
	}

	private void paintMapPanel() {
		mapPanel = new JPanel();
		mapPanel.setBorder(null);
		mapPanel.setMinimumSize(new Dimension(MAP_DIMENSION.width + 62, MAP_DIMENSION.height + 62));
		mapPanel.setPreferredSize(new Dimension(MAP_DIMENSION.width + 62, MAP_DIMENSION.height + 62));
		mapPanel.setLayout(null);
		lblHouse.setVisible(false);
		mapPanel.add(lblHouse);
		lblMap = new JLabel(new ImageIcon(".\\images\\Como_Registrazione.png"));
		lblMap.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblMap.setBounds(POSITION_MARK_DIMENSION.width, POSITION_MARK_DIMENSION.height, MAP_DIMENSION.width,
				MAP_DIMENSION.height);
		mapPanel.add(lblMap);
	}

	private JPanel paintNorthPanel() {
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(150, 250));
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Dati utente",
				TitledBorder.LEADING, TitledBorder.TOP, new Font("Tahoma", Font.BOLD, 18), new Color(0, 0, 0)));
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 147, 145, 47, 74, 265, 0 };
		gbl_panel.rowHeights = new int[] { 23, 22, 23, 22, 26, 20, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 2.0, 2.0, 2.0, 2.0, 2.0, 2.0, Double.MIN_VALUE };
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
		textField_Name.setColumns(10);
		textField_Name.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_textFieldName = new GridBagConstraints();
		gbc_textFieldName.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldName.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldName.gridx = 1;
		gbc_textFieldName.gridy = 0;
		panel.add(textField_Name, gbc_textFieldName);

		JLabel lblSurname = new JLabel("Cognome:");
		lblSurname.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblSurname = new GridBagConstraints();
		gbc_lblSurname.anchor = GridBagConstraints.WEST;
		gbc_lblSurname.insets = new Insets(0, 0, 5, 5);
		gbc_lblSurname.gridx = 3;
		gbc_lblSurname.gridy = 0;
		panel.add(lblSurname, gbc_lblSurname);

		textField_Surname = new JTextField();
		textField_Surname.setMinimumSize(new Dimension(140, 22));
		textField_Surname.setPreferredSize(new Dimension(140, 22));
		textField_Surname.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textField_Surname.setColumns(10);
		GridBagConstraints gbc_textFieldSurname = new GridBagConstraints();
		gbc_textFieldSurname.anchor = GridBagConstraints.WEST;
		gbc_textFieldSurname.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldSurname.gridx = 4;
		gbc_textFieldSurname.gridy = 0;
		panel.add(textField_Surname, gbc_textFieldSurname);

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
		GridBagConstraints gbc_textFieldUserId = new GridBagConstraints();
		gbc_textFieldUserId.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldUserId.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldUserId.gridx = 1;
		gbc_textFieldUserId.gridy = 1;
		panel.add(textField_UserId, gbc_textFieldUserId);

		lblInvalidUserid = new JLabel("UserId non valido! Inserire un altro UserId!");
		lblInvalidUserid.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblInvalidUserid.setVisible(false);
		lblInvalidUserid.setForeground(Color.RED);
		GridBagConstraints gbc_lblInvalidUserId = new GridBagConstraints();
		gbc_lblInvalidUserId.anchor = GridBagConstraints.WEST;
		gbc_lblInvalidUserId.insets = new Insets(0, 0, 5, 0);
		gbc_lblInvalidUserId.gridwidth = 2;
		gbc_lblInvalidUserId.gridx = 3;
		gbc_lblInvalidUserId.gridy = 1;
		panel.add(lblInvalidUserid, gbc_lblInvalidUserId);

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
		textField_Email.setColumns(10);
		GridBagConstraints gbc_textFieldEmail = new GridBagConstraints();
		gbc_textFieldEmail.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldEmail.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldEmail.gridx = 1;
		gbc_textFieldEmail.gridy = 2;
		panel.add(textField_Email, gbc_textFieldEmail);

		lblInvalidEmail = new JLabel("Mail non valida! Inserire un altro indirzzo mail!");
		lblInvalidEmail.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblInvalidEmail.setVisible(false);
		lblInvalidEmail.setForeground(Color.RED);
		GridBagConstraints gbc_lblInvaidEmail = new GridBagConstraints();
		gbc_lblInvaidEmail.anchor = GridBagConstraints.WEST;
		gbc_lblInvaidEmail.insets = new Insets(0, 0, 5, 0);
		gbc_lblInvaidEmail.gridwidth = 2;
		gbc_lblInvaidEmail.gridx = 3;
		gbc_lblInvaidEmail.gridy = 2;
		panel.add(lblInvalidEmail, gbc_lblInvaidEmail);

		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblPassword = new GridBagConstraints();
		gbc_lblPassword.anchor = GridBagConstraints.WEST;
		gbc_lblPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblPassword.gridx = 0;
		gbc_lblPassword.gridy = 3;
		panel.add(lblPassword, gbc_lblPassword);

		passwordField_Password = new JPasswordField();
		passwordField_Password.setFont(new Font("Tahoma", Font.PLAIN, 16));
		passwordField_Password.setColumns(14);
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField.insets = new Insets(0, 0, 5, 5);
		gbc_passwordField.gridx = 1;
		gbc_passwordField.gridy = 3;
		panel.add(passwordField_Password, gbc_passwordField);

		lblInvalidPassword = new JLabel("Password non valida! Inserire almeno 7 caratteri!");
		lblInvalidPassword.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblInvalidPassword.setVisible(false);
		lblInvalidPassword.setForeground(Color.RED);
		GridBagConstraints gbc_lblInvalidPassword = new GridBagConstraints();
		gbc_lblInvalidPassword.anchor = GridBagConstraints.WEST;
		gbc_lblInvalidPassword.insets = new Insets(0, 0, 5, 0);
		gbc_lblInvalidPassword.gridwidth = 2;
		gbc_lblInvalidPassword.gridx = 3;
		gbc_lblInvalidPassword.gridy = 3;
		panel.add(lblInvalidPassword, gbc_lblInvalidPassword);

		JLabel lblConfirmPassword = new JLabel("Conferma password:");
		lblConfirmPassword.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblConfirmPassword = new GridBagConstraints();
		gbc_lblConfirmPassword.anchor = GridBagConstraints.WEST;
		gbc_lblConfirmPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblConfirmPassword.gridx = 0;
		gbc_lblConfirmPassword.gridy = 4;
		panel.add(lblConfirmPassword, gbc_lblConfirmPassword);

		passwordField_ConfirmPassword = new JPasswordField();
		passwordField_ConfirmPassword.setFont(new Font("Tahoma", Font.PLAIN, 16));
		passwordField_ConfirmPassword.setColumns(14);
		GridBagConstraints gbc_passwordFieldConfirmPassword = new GridBagConstraints();
		gbc_passwordFieldConfirmPassword.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordFieldConfirmPassword.insets = new Insets(0, 0, 5, 5);
		gbc_passwordFieldConfirmPassword.gridx = 1;
		gbc_passwordFieldConfirmPassword.gridy = 4;
		panel.add(passwordField_ConfirmPassword, gbc_passwordFieldConfirmPassword);

		lblDifferentPasswords = new JLabel("La password non corrisponde alla precedente!");
		lblDifferentPasswords.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblDifferentPasswords.setVisible(false);
		lblDifferentPasswords.setForeground(Color.RED);
		GridBagConstraints gbc_lblDifferentPassword = new GridBagConstraints();
		gbc_lblDifferentPassword.anchor = GridBagConstraints.WEST;
		gbc_lblDifferentPassword.insets = new Insets(0, 0, 5, 0);
		gbc_lblDifferentPassword.gridwidth = 2;
		gbc_lblDifferentPassword.gridx = 3;
		gbc_lblDifferentPassword.gridy = 4;
		panel.add(lblDifferentPasswords, gbc_lblDifferentPassword);

		JLabel lblWarning = new JLabel("\u00C8 necessario compilare tutti i campi per procedere.");
		lblWarning.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblWarning = new GridBagConstraints();
		gbc_lblWarning.anchor = GridBagConstraints.WEST;
		gbc_lblWarning.insets = new Insets(0, 0, 0, 5);
		gbc_lblWarning.gridwidth = 4;
		gbc_lblWarning.gridx = 0;
		gbc_lblWarning.gridy = 5;
		panel.add(lblWarning, gbc_lblWarning);

		textField_UserId.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (!verifyBiographicalData(textField_UserId.getText()) || !verifyUserId(textField_UserId.getText())) {
					lblInvalidUserid.setVisible(true);
				} else {
					lblInvalidUserid.setVisible(false);
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

		passwordField_Password.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (!verifyPassword(new String(passwordField_Password.getPassword()))) {
					lblInvalidPassword.setVisible(true);
				} else {
					lblInvalidPassword.setVisible(false);
				}
			}
		});

		passwordField_ConfirmPassword.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (!verifyConfirmPassword(new String(passwordField_Password.getPassword()),
						new String(passwordField_ConfirmPassword.getPassword()))) {
					lblDifferentPasswords.setVisible(true);
				} else {
					lblDifferentPasswords.setVisible(false);
				}
			}
		});

		return panel;
	}

	private JPanel paintCentralPanel() {
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(250, 150));
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Geolocalizzazione",
				TitledBorder.LEADING, TitledBorder.TOP, new Font("Tahoma", Font.BOLD, 18), new Color(0, 0, 0)));
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 75, 22, 25, 50, 34, 40, 15, 113, 109, 0 };
		gbl_panel.rowHeights = new int[] { 24, 22, 20, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
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
		gbc_spnLatitude.fill = GridBagConstraints.HORIZONTAL;
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
		gbc_lblLongitude.gridwidth = 3;
		gbc_lblLongitude.gridx = 4;
		gbc_lblLongitude.gridy = 0;
		panel.add(lblLongitude, gbc_lblLongitude);

		spnLongitude = new JSpinner(
				new SpinnerNumberModel(new Double(0.0), new Double(0.0), new Double(20.0), new Double(0.1)));
		spnLongitude.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_spnLongitude = new GridBagConstraints();
		gbc_spnLongitude.anchor = GridBagConstraints.WEST;
		gbc_spnLongitude.insets = new Insets(0, 0, 5, 5);
		gbc_spnLongitude.gridx = 7;
		gbc_spnLongitude.gridy = 0;
		panel.add(spnLongitude, gbc_spnLongitude);

		JLabel lblSelectCity = new JLabel("Seleziona città:");
		lblSelectCity.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblSelectCity = new GridBagConstraints();
		gbc_lblSelectCity.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblSelectCity.insets = new Insets(0, 0, 5, 5);
		gbc_lblSelectCity.gridwidth = 2;
		gbc_lblSelectCity.gridx = 0;
		gbc_lblSelectCity.gridy = 1;
		panel.add(lblSelectCity, gbc_lblSelectCity);

		cmbCity = new JComboBox<String>(new DefaultComboBoxModel<String>(City.createCitiesArray()));
		cmbCity.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				slider.setValue(slider.getMinimum());
				if (cmbCity.getSelectedItem().toString().equals("Como")) {
					lblMap.setIcon(new ImageIcon(".\\images\\Como_Registrazione.png"));
				} else if (cmbCity.getSelectedItem().toString().equals("Lecco")) {
					lblMap.setIcon(new ImageIcon(".\\images\\Lecco_Registrazione.png"));
				} else if (cmbCity.getSelectedItem().toString().equals("Varese")) {
					lblMap.setIcon(new ImageIcon(".\\images\\Varese_Registrazione.png"));
				}
			}
		});
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
		gbc_lblSelectDistrict.gridwidth = 2;
		gbc_lblSelectDistrict.gridx = 6;
		gbc_lblSelectDistrict.gridy = 1;
		panel.add(lblSelectDistrict, gbc_lblSelectDistrict);

		cmbDistrict = new JComboBox<String>(new DefaultComboBoxModel<String>(District.arrayDistricts()));
		cmbDistrict.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_cmbDistrict = new GridBagConstraints();
		gbc_cmbDistrict.anchor = GridBagConstraints.WEST;
		gbc_cmbDistrict.insets = new Insets(0, 0, 5, 0);
		gbc_cmbDistrict.gridx = 8;
		gbc_cmbDistrict.gridy = 1;
		panel.add(cmbDistrict, gbc_cmbDistrict);

		lblIncorrectCoordinates = new JLabel("Le coordinate e il quartiere non corrispondo!");
		lblIncorrectCoordinates.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblIncorrectCoordinates.setVisible(false);
		lblIncorrectCoordinates.setForeground(Color.RED);
		GridBagConstraints gbc_lblIncorrectCoordinates = new GridBagConstraints();
		gbc_lblIncorrectCoordinates.anchor = GridBagConstraints.WEST;
		gbc_lblIncorrectCoordinates.insets = new Insets(0, 0, 0, 5);
		gbc_lblIncorrectCoordinates.gridwidth = 8;
		gbc_lblIncorrectCoordinates.gridx = 0;
		gbc_lblIncorrectCoordinates.gridy = 2;
		panel.add(lblIncorrectCoordinates, gbc_lblIncorrectCoordinates);

		return panel;
	}

	private JPanel paintSouthPanel() {
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(250, 45));
		panel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		JLabel lblWrongData = new JLabel("\u00C8 necessario correggere i dati errati prima di poter procedere!");
		lblWrongData.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel.add(lblWrongData);
		lblWrongData.setVisible(false);
		lblWrongData.setForeground(Color.RED);

		JButton btnRegistration = new JButton("Registrati!");
		btnRegistration.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel.add(btnRegistration);
		btnRegistration.addActionListener(new ActionListener() {
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
						|| lblDifferentPasswords.isVisible() || lblInvalidEmail.isVisible()
						|| lblInvalidPassword.isVisible() || lblInvalidUserid.isVisible()) {
					lblWrongData.setVisible(true);
				} else {
					User.newUserRegistration(textField_Name.getText(), textField_Surname.getText(),
							textField_UserId.getText(), textField_Email.getText(),
							new String(passwordField_ConfirmPassword.getPassword()),
							City.stringToEnum(cmbCity.getSelectedItem().toString()),
							District.stringToEnum((cmbDistrict.getSelectedItem().toString())),
							(double) spnLatitude.getValue(), (double) spnLongitude.getValue());
					frame.dispose();
					GUIMain.closeFrame();
					GUIUser.main();
				}
			}
		});

		return panel;
	}

	private void insertCoordinates(int x, int y) {
		double zoom = slider.getValue() / 10.0;
		double longitude = Utility.round((Math.floor(x) - POSITION_MARK_DIMENSION.width) / (zoom * PIXELS_PER_DEGREE));
		double latitude = Utility.round((Math.floor(y) - POSITION_MARK_DIMENSION.height) / (zoom * PIXELS_PER_DEGREE));

		if (longitude > 20.0) {
			longitude = 20.0;
		} else if (longitude < 0.0) {
			longitude = 0.0;
		}
		if (latitude > 10.0) {
			latitude = 10.0;
		} else if (latitude < 0.0) {
			latitude = 0.0;
		}

		spnLatitude.setValue(latitude);
		spnLongitude.setValue(longitude);
		cmbDistrict.setSelectedItem(District.districtCoordinates(latitude, longitude));
	}

	private static boolean verifyBiographicalData(String data) {
		return data.length() != 0;
	}

	private static boolean verifyEmail(String email) {
		return !(email.length() < 8 || !email.contains(".") || !email.contains("@") || email.contains(" "));
	}

	private static boolean verifyPassword(String password) {
		return password.length() > 6;
	}

	private static boolean verifyConfirmPassword(String confirmPassword, String password) {
		return confirmPassword.equals(password);
	}

	private static boolean verifyUserId(String userId) {
		for (int i = 0; i < User.users.size(); i++) {
			if (userId.equals(User.users.get(i).getUserId().toString())) {
				return false;
			}
		}

		return userId.trim().length() != 0;
	}

	/**
	 * Create and set visible registration interface
	 */
	public static void main() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new GUIRegistration();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}