package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import enums.City;
import enums.District;
import enums.ReportingReason;
import logic.FileManager;
import logic.Reporting;
import logic.User;
import logic.Utility;

/**
 * Class that creates the main interface
 * 
 * @author Marco Di Capua
 * @author Filippo Pelosi
 * @author Riccardo Zorzi
 * @version 1.0
 */
public class GUIMain extends JFrame {
	// camps
	private JPanel mapPanel;
	private JLabel lblMap;
	private JTable table;
	private JComboBox<String> cmbSelectCity;
	private JComboBox<String> cmbSelectDistrict;
	private JComboBox<String> cmbSelectReportingReason;
	private JSlider slider;
	private final String[] HEADER = { "Città", "Quartiere", "IdSegnalazione", "UserId", "Lat utente", "Long utente",
			"Lat segnalazione", "Long segnalazione", "Data e ora", "Motivo segnalazione", "Stato segnalazione",
			"Presa in carico", "Utente incaricato" };
	private static GUIMain frame;
	private static JLabel lblReportingDetails = new JLabel();
	private static int mouseStartX;
	private static int mouseStartY;
	private static final Dimension POSITION_MARK_DIMENSION = new Dimension(31, 31);
	private static final Dimension MAP_DIMENSION = new Dimension(1000, 500);
	private static final String PATH_IMG_RED_POSITION_MARK = ".\\images\\segnaposto_rosso.png";
	private static final String PATH_IMG_BLUE_POSITION_MARK = ".\\images\\segnaposto_blu.png";
	private static final int PIXELS_PER_DEGREE = 50;

	private static final long serialVersionUID = 1377958810656035583L;

	// constructor
	/**
	 * Create main interface
	 */
	public GUIMain() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(".\\images\\icona-watchNeighbors.png"));
		setExtendedState(Frame.MAXIMIZED_BOTH);
		setTitle("WatchNeighbors");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Reporting.reportings = Reporting.reportingComo;
		paintMenu();
		paintInterface();
		paintReportings(cmbSelectDistrict.getSelectedItem().toString(), 1.0,
				cmbSelectReportingReason.getSelectedItem().toString());
	}

	// methods
	private void paintMenu() {
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		JMenu mnFile = new JMenu("File");
		mnFile.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		menuBar.add(mnFile);
		JMenuItem mntmExit = new JMenuItem("Esci");
		mntmExit.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mntmExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Il programma verrà chiuso, arrivederci!", "Avviso di chiusura",
						JOptionPane.INFORMATION_MESSAGE);
				System.exit(0);
			}
		});
		mnFile.add(mntmExit);

		JMenu mnOptions = new JMenu("Opzioni");
		mnOptions.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		menuBar.add(mnOptions);
		JMenuItem mntmReportingsHistory = new JMenuItem("Cronologia segnalazioni");
		mntmReportingsHistory.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mntmReportingsHistory.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GUIHistory.main();
			}
		});
		mnOptions.add(mntmReportingsHistory);

		JMenu menu = new JMenu("?");
		menu.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		menuBar.add(menu);
		JMenuItem mntmGuide = new JMenuItem("Guida");
		mntmGuide.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mntmGuide.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				GUIGuide.main();
			}
		});
		menu.add(mntmGuide);

	}

	private void paintInterface() {
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		JPanel interfacePanel = new JPanel();
		scrollPane.setViewportView(interfacePanel);
		interfacePanel.setLayout(new BorderLayout(0, 0));

		lblReportingDetails.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblReportingDetails.setBackground(Color.LIGHT_GRAY);
		lblReportingDetails.setOpaque(true);
		lblReportingDetails.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblReportingDetails.setVisible(false);
		interfacePanel.add(lblReportingDetails);
		interfacePanel.setComponentZOrder(lblReportingDetails, getComponentCount() - 1);
		JPanel northPanel = paintNorthPanel();

		interfacePanel.add(northPanel, BorderLayout.NORTH);
		JPanel centralPanel = paintCenterPanel();
		interfacePanel.add(centralPanel, BorderLayout.CENTER);
	}

	private JPanel paintNorthPanel() {
		JPanel panel = new JPanel();
		panel.setBackground(new Color(30, 144, 255));
		panel.setPreferredSize(new Dimension(10, 50));
		panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		JLabel lblWatchneighbors = new JLabel("WATCHNEIGHBORS");
		lblWatchneighbors.setFont(new Font("Tahoma", Font.BOLD, 30));
		panel.add(lblWatchneighbors);

		return panel;
	}

	private JPanel paintCenterPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(0, 0));
		JPanel southPanel = paintSouthPanel();
		panel.add(southPanel, BorderLayout.SOUTH);
		JPanel mapPanel = paintPanelMap();
		panel.add(mapPanel, BorderLayout.WEST);
		JPanel centralPanel = paintCentralPanel();
		panel.add(centralPanel, BorderLayout.CENTER);

		return panel;
	}

	private JPanel paintPanelMap() {
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(1180, 620));
		panel.setLayout(null);

		JLabel lblNorth = new JLabel("Nord");
		lblNorth.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNorth.setBounds(583, 27, 53, 20);
		panel.add(lblNorth);
		JLabel lblSouth = new JLabel("Sud");
		lblSouth.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblSouth.setBounds(590, 640, 43, 20);
		panel.add(lblSouth);

		JLabel lblWest = new JLabel("Ovest");
		lblWest.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblWest.setBounds(10, 330, 51, 20);
		panel.add(lblWest);
		JLabel lblEast = new JLabel("Est");
		lblEast.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblEast.setBounds(1139, 330, 33, 20);
		panel.add(lblEast);

		paintMapPanel();
		JScrollPane scrollPaneMap = paintScrollPaneMap();
		panel.add(scrollPaneMap);
		paintSlider();
		panel.add(slider);
		JLabel lblDecreaseZoom = new JLabel("-");
		lblDecreaseZoom.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblDecreaseZoom.setBounds(1153, 635, 13, 21);
		panel.add(lblDecreaseZoom);

		JLabel lblIncreaseZoom = new JLabel("+");
		lblIncreaseZoom.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblIncreaseZoom.setBounds(1147, 370, 25, 30);
		panel.add(lblIncreaseZoom);

		return panel;
	}

	private void paintMapPanel() {
		mapPanel = new JPanel();
		mapPanel.setBorder(null);
		mapPanel.setMinimumSize(new Dimension(MAP_DIMENSION.width + 62, MAP_DIMENSION.height + 62));
		mapPanel.setPreferredSize(new Dimension(MAP_DIMENSION.width + 62, MAP_DIMENSION.height + 62));
		mapPanel.setLayout(null);
		lblMap = new JLabel(new ImageIcon(City.stringToEnum("Como").getPath()));
		lblMap.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblMap.setBounds(POSITION_MARK_DIMENSION.width, POSITION_MARK_DIMENSION.height, MAP_DIMENSION.width,
				MAP_DIMENSION.height);

		mapPanel.add(lblMap);
		setPositionsMarkEvents();
	}

	private JScrollPane paintScrollPaneMap() {
		JScrollPane scrollPane = new JScrollPane(mapPanel);
		scrollPane.setBorder(null);
		scrollPane.setSize(new Dimension(MAP_DIMENSION.width + 65, MAP_DIMENSION.height + 65));
		scrollPane.setMaximumSize(new Dimension(MAP_DIMENSION.width + 65, MAP_DIMENSION.height + 65));
		scrollPane.setMinimumSize(new Dimension(MAP_DIMENSION.width + 65, MAP_DIMENSION.height + 65));
		scrollPane.setPreferredSize(new Dimension(MAP_DIMENSION.width + 65, MAP_DIMENSION.height + 65));
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

		scrollPane.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
		scrollPane.setBounds(73, 60, MAP_DIMENSION.width + 65, MAP_DIMENSION.height + 65);
		mapPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				mouseStartX = e.getX();
				mouseStartY = e.getY();
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

		return scrollPane;
	}

	private void paintSlider() {
		slider = new JSlider(10, 20, 10);
		slider.setOrientation(SwingConstants.VERTICAL);
		slider.setBounds(1150, 400, 22, 234);
		slider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				try {
					Double zoom = slider.getValue() / 10.0;
					BufferedImage sourceBuffer;

					if (cmbSelectDistrict.getSelectedItem().equals("Tutti")) {
						sourceBuffer = ImageIO.read(
								new File(City.stringToEnum(cmbSelectCity.getSelectedItem().toString()).getPath()));
					} else {
						sourceBuffer = ImageIO
								.read(new File(District.stringToEnum(cmbSelectDistrict.getSelectedItem().toString())
										.getPath(cmbSelectCity.getSelectedItem().toString())));
					}

					BufferedImage destinationBuffer = new BufferedImage((int) (sourceBuffer.getWidth() * zoom),
							(int) (sourceBuffer.getHeight() * zoom), BufferedImage.TYPE_INT_RGB);
					Graphics2D graphics2d = destinationBuffer.createGraphics();
					AffineTransform attribute = AffineTransform.getScaleInstance(zoom, zoom);
					graphics2d.drawRenderedImage(sourceBuffer, attribute);
					ImageIcon imageIcon = new ImageIcon(destinationBuffer);

					lblMap.setIcon(imageIcon);
					lblMap.setSize(imageIcon.getIconWidth(), imageIcon.getIconHeight());
					mapPanel.setPreferredSize(new Dimension(lblMap.getSize().width + 62, lblMap.getSize().height + 62));
					paintReportings(cmbSelectDistrict.getSelectedItem().toString(), zoom,
							cmbSelectReportingReason.getSelectedItem().toString());

				} catch (IOException e) {
					JOptionPane.showMessageDialog(null,
							"Impossibie caricare la mappa! Il file potrebbe essere stato rimosso o spostato!",
							"Errore!", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}

	private JPanel paintCentralPanel() {
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(570, 10));
		panel.setLayout(new BorderLayout(0, 0));
		JPanel eastPanel = new JPanel();
		eastPanel.setPreferredSize(new Dimension(570, 400));
		panel.add(eastPanel, BorderLayout.EAST);
		eastPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JPanel loginPanel = paintLoginPanel();
		eastPanel.add(loginPanel);
		JPanel filterReportingsPanel = paintfilterReportingsPanel();
		eastPanel.add(filterReportingsPanel);

		return panel;
	}

	private JPanel paintLoginPanel() {
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(560, 110));
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Login", TitledBorder.LEADING,
				TitledBorder.TOP, new Font("Tahoma", Font.BOLD, 18), new Color(0, 0, 0)));
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 99, 116, 82, 116, 97, 0 };
		gbl_panel.rowHeights = new int[] { 25, 20, 20, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JLabel lblName = new JLabel("Nome Utente:");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.anchor = GridBagConstraints.WEST;
		gbc_lblName.insets = new Insets(0, 0, 5, 5);
		gbc_lblName.gridx = 0;
		gbc_lblName.gridy = 0;
		panel.add(lblName, gbc_lblName);

		JTextField textFieldName = new JTextField();
		textFieldName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textFieldName.setColumns(10);
		GridBagConstraints gbc_textFieldName = new GridBagConstraints();
		gbc_textFieldName.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldName.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldName.gridx = 1;
		gbc_textFieldName.gridy = 0;
		panel.add(textFieldName, gbc_textFieldName);

		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblPassword = new GridBagConstraints();
		gbc_lblPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblPassword.gridx = 2;
		gbc_lblPassword.gridy = 0;
		panel.add(lblPassword, gbc_lblPassword);

		JPasswordField password = new JPasswordField();
		password.setFont(new Font("Tahoma", Font.PLAIN, 16));
		password.setColumns(14);
		GridBagConstraints gbc_password = new GridBagConstraints();
		gbc_password.fill = GridBagConstraints.HORIZONTAL;
		gbc_password.insets = new Insets(0, 0, 5, 5);
		gbc_password.gridx = 3;
		gbc_password.gridy = 0;
		panel.add(password, gbc_password);

		JButton btnAccess = new JButton("Accedi");
		btnAccess.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_btnAccess = new GridBagConstraints();
		gbc_btnAccess.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnAccess.insets = new Insets(0, 0, 5, 0);
		gbc_btnAccess.gridx = 4;
		gbc_btnAccess.gridy = 0;
		panel.add(btnAccess, gbc_btnAccess);

		JLabel lblFeedbackLogin = new JLabel("Nome utente o password errati! Impossibile effettuare login!");
		lblFeedbackLogin.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblFeedbackLogin.setVisible(false);
		lblFeedbackLogin.setForeground(Color.RED);
		GridBagConstraints gbc_lblFeedbackLogin = new GridBagConstraints();
		gbc_lblFeedbackLogin.anchor = GridBagConstraints.WEST;
		gbc_lblFeedbackLogin.insets = new Insets(0, 0, 5, 5);
		gbc_lblFeedbackLogin.gridwidth = 4;
		gbc_lblFeedbackLogin.gridx = 0;
		gbc_lblFeedbackLogin.gridy = 1;
		panel.add(lblFeedbackLogin, gbc_lblFeedbackLogin);

		JLabel lblRegistration = new JLabel("Sei un nuovo utente? Registrati cliccando qui!");
		lblRegistration.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblRegistration.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblRegistration = new GridBagConstraints();
		gbc_lblRegistration.anchor = GridBagConstraints.WEST;
		gbc_lblRegistration.insets = new Insets(0, 0, 0, 5);
		gbc_lblRegistration.gridwidth = 3;
		gbc_lblRegistration.gridx = 0;
		gbc_lblRegistration.gridy = 2;
		panel.add(lblRegistration, gbc_lblRegistration);

		btnAccess.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!FileManager.usersFileExists()) {
					JOptionPane.showMessageDialog(null,
							"Impossibie trovare file utenti! È neccessario registrarsi con un nuovo account!",
							"Errore!", JOptionPane.ERROR_MESSAGE);
				} else {
					if (User.verifyData(textFieldName.getText(), password.getPassword())) {
						lblFeedbackLogin.setVisible(false);
						frame.dispose();
						GUIUser.main();
					} else {
						lblFeedbackLogin.setVisible(true);
					}
				}

			}
		});

		lblRegistration.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.setEnabled(false);
				GUIRegistration.main();
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				lblRegistration.setForeground(Color.BLUE);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				lblRegistration.setForeground(Color.BLACK);
			}
		});

		return panel;
	}

	private JPanel paintfilterReportingsPanel() {
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Fitra segnalazioni", TitledBorder.LEADING, TitledBorder.TOP,
				new Font("Tahoma", Font.BOLD, 18), new Color(0, 0, 0)));
		panel.setPreferredSize(new Dimension(560, 130));
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 221, 150, 0 };
		gbl_panel.rowHeights = new int[] { 26, 26, 26, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		cmbSelectCity = new JComboBox<String>(new DefaultComboBoxModel<String>(City.createCitiesArray()));
		cmbSelectCity.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_cmbSelectCity = new GridBagConstraints();
		gbc_cmbSelectCity.anchor = GridBagConstraints.NORTHWEST;
		gbc_cmbSelectCity.insets = new Insets(0, 0, 5, 0);
		gbc_cmbSelectCity.gridx = 1;
		gbc_cmbSelectCity.gridy = 0;
		panel.add(cmbSelectCity, gbc_cmbSelectCity);

		cmbSelectCity.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				slider.setValue(slider.getMinimum());
				cmbSelectDistrict.setSelectedIndex(0);
				for (int i = 0; i < Reporting.reportings.size(); i++) {
					Reporting.reportings.get(i).getPositionMark().setVisible(false);
				}

				if (cmbSelectCity.getSelectedItem().toString().equals("Como")) {
					Reporting.reportings = Reporting.reportingComo;
				} else if (cmbSelectCity.getSelectedItem().toString().equals("Lecco")) {
					Reporting.reportings = Reporting.reportingLecco;
				} else if (cmbSelectCity.getSelectedItem().toString().equals("Varese")) {
					Reporting.reportings = Reporting.reportingVarese;
				}
				setPositionsMarkEvents();
				lblMap.setIcon(new ImageIcon(City.stringToEnum(cmbSelectCity.getSelectedItem().toString()).getPath()));
				paintReportings("Tutti", 1.0, cmbSelectReportingReason.getSelectedItem().toString());
			}
		});

		JLabel lblSelectCity = new JLabel("Seleziona citt\u00E0:");
		lblSelectCity.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblSelectCity = new GridBagConstraints();
		gbc_lblSelectCity.anchor = GridBagConstraints.WEST;
		gbc_lblSelectCity.insets = new Insets(0, 0, 5, 5);
		gbc_lblSelectCity.gridx = 0;
		gbc_lblSelectCity.gridy = 0;
		panel.add(lblSelectCity, gbc_lblSelectCity);

		JLabel lblSelectDistrict = new JLabel("Seleziona quartiere:");
		lblSelectDistrict.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblSelectDistrict = new GridBagConstraints();
		gbc_lblSelectDistrict.anchor = GridBagConstraints.WEST;
		gbc_lblSelectDistrict.insets = new Insets(0, 0, 5, 5);
		gbc_lblSelectDistrict.gridx = 0;
		gbc_lblSelectDistrict.gridy = 1;
		panel.add(lblSelectDistrict, gbc_lblSelectDistrict);

		cmbSelectDistrict = new JComboBox<String>(new DefaultComboBoxModel<String>(
				new String[] { "Tutti", "Nord-ovest", "Nord-est", "Sud-ovest", "Sud-est" }));

		cmbSelectDistrict.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_cmbSelectDistrict = new GridBagConstraints();
		gbc_cmbSelectDistrict.anchor = GridBagConstraints.NORTHWEST;
		gbc_cmbSelectDistrict.insets = new Insets(0, 0, 5, 0);
		gbc_cmbSelectDistrict.gridx = 1;
		gbc_cmbSelectDistrict.gridy = 1;
		panel.add(cmbSelectDistrict, gbc_cmbSelectDistrict);

		cmbSelectDistrict.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				slider.setValue(slider.getMinimum());
				if (cmbSelectDistrict.getSelectedItem().toString().equals("Tutti")) {
					paintReportings("Tutti", 1.0, cmbSelectReportingReason.getSelectedItem().toString());
					lblMap.setIcon(
							new ImageIcon(City.stringToEnum(cmbSelectCity.getSelectedItem().toString()).getPath()));
				} else {
					lblMap.setIcon(new ImageIcon(District.stringToEnum(cmbSelectDistrict.getSelectedItem().toString())
							.getPath(cmbSelectCity.getSelectedItem().toString())));
					paintReportings(cmbSelectDistrict.getSelectedItem().toString(), 1.0,
							cmbSelectReportingReason.getSelectedItem().toString());
					verifyReportingsNumber(cmbSelectDistrict.getSelectedItem().toString());
				}
			}
		});

		JLabel lblSelectReportingReason = new JLabel("Seleziona motivo segnalazione:");
		lblSelectReportingReason.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblSelectReportingReason = new GridBagConstraints();
		gbc_lblSelectReportingReason.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblSelectReportingReason.insets = new Insets(0, 0, 0, 5);
		gbc_lblSelectReportingReason.gridx = 0;
		gbc_lblSelectReportingReason.gridy = 2;
		panel.add(lblSelectReportingReason, gbc_lblSelectReportingReason);

		cmbSelectReportingReason = new JComboBox<String>(new DefaultComboBoxModel<String>(
				new String[] { "Tutti", "Furto", "Incendio", "Omicidio", "Scippo", "Allarme", "Persona sospetta" }));
		cmbSelectReportingReason.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_cmbSelectReportingReason = new GridBagConstraints();
		gbc_cmbSelectReportingReason.anchor = GridBagConstraints.NORTHWEST;
		gbc_cmbSelectReportingReason.gridx = 1;
		gbc_cmbSelectReportingReason.gridy = 2;
		panel.add(cmbSelectReportingReason, gbc_cmbSelectReportingReason);

		cmbSelectReportingReason.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				slider.setValue(slider.getMinimum());
				paintReportings(cmbSelectDistrict.getSelectedItem().toString(), 1.0,
						cmbSelectReportingReason.getSelectedItem().toString());
			}
		});

		return panel;
	}

	private JPanel paintSouthPanel() {
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(500, 210));
		panel.setLayout(new BorderLayout(0, 0));
		JPanel writingReportingPanel = paintWritingReportingPanel();
		panel.add(writingReportingPanel, BorderLayout.NORTH);
		JPanel tablePanel = paintTablePanel();
		panel.add(tablePanel, BorderLayout.CENTER);

		return panel;
	}

	private JPanel paintWritingReportingPanel() {
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(10, 30));
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		JLabel lblReportings = new JLabel("Segnalazioni");
		lblReportings.setFont(new Font("Tahoma", Font.BOLD, 18));
		panel.add(lblReportings);

		return panel;
	}

	private JPanel paintTablePanel() {
		table = new JTable();
		table.setFont(new Font("Tahoma", Font.PLAIN, 16));
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		paintTable("Tutti", "Tutti");

		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(0, 0));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 16));
		JScrollPane scrollPaneTable = new JScrollPane(table);
		panel.add(scrollPaneTable);

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent evt) {
				int row = Integer.parseInt((String) table.getValueAt(table.rowAtPoint(evt.getPoint()), 2)) - 1;
				Reporting.reportings.get(row).getPositionMark().setIcon(new ImageIcon(PATH_IMG_RED_POSITION_MARK));
				mapPanel.setComponentZOrder(Reporting.reportings.get(row).getPositionMark(), getComponentCount() - 1);
				for (int i = 0; i < Reporting.reportings.size(); i++) {
					if (i != row) {
						Reporting.reportings.get(i).getPositionMark()
								.setIcon(new ImageIcon(PATH_IMG_BLUE_POSITION_MARK));
					}
				}
			}
		});

		return panel;
	}

	private void paintTable(String district, String reportingStatus) {
		String[][] tableMatrix = null;
		if (district.equals("Tutti")) {
			tableMatrix = Reporting.createReportingsMatrix(reportingStatus);
		} else {
			tableMatrix = Reporting.createReportingsMatrix(District.stringToEnum(district), reportingStatus);
		}

		DefaultTableModel tm = new DefaultTableModel(tableMatrix, HEADER) {
			private static final long serialVersionUID = 2571873481982529181L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table.setModel(tm);
	}

	private void paintReportings(String district, double zoom, String reportingStatus) {
		paintTable(cmbSelectDistrict.getSelectedItem().toString(), reportingStatus);
		for (int i = 0; i < Reporting.reportings.size(); i++) {
			if (district.equals("Tutti")) {
				Reporting.reportings.get(i).getPositionMark().setIcon(new ImageIcon(PATH_IMG_BLUE_POSITION_MARK));
				Reporting.reportings.get(i).getPositionMark().setVisible(true);

				Reporting.reportings.get(i).getPositionMark()
						.setBounds(
								(int) (zoom
										* (Utility.round(Reporting.reportings.get(i).getReportingLongitude()
												* PIXELS_PER_DEGREE))
										- (POSITION_MARK_DIMENSION.width / 2) + POSITION_MARK_DIMENSION.width),
								(int) (zoom * (Utility.round(
										(Reporting.reportings.get(i).getReportingLatitude() * PIXELS_PER_DEGREE)))),
								POSITION_MARK_DIMENSION.width, POSITION_MARK_DIMENSION.height);
				mapPanel.add(Reporting.reportings.get(i).getPositionMark());
				mapPanel.setComponentZOrder(Reporting.reportings.get(i).getPositionMark(), getComponentCount() - 1);

			} else if (Reporting.reportings.get(i).getDistrict().equals(District.stringToEnum(district))) {
				Reporting.reportings.get(i).getPositionMark().setVisible(true);
				int longitudeDegreesToSubtract = 0;
				int latitudeDegreesToSubtract = 0;

				switch (District.stringToEnum(district)) {
				case NORTH_WEST:
					break;
				case NORTH_EAST:
					longitudeDegreesToSubtract = 10;
					break;
				case SOUTH_WEST:
					latitudeDegreesToSubtract = 5;
					break;
				default:
					longitudeDegreesToSubtract = 10;
					latitudeDegreesToSubtract = 5;
					break;
				}

				Reporting.reportings.get(i).getPositionMark().setLocation(
						(int) (zoom
								* (Utility.round((Reporting.reportings.get(i).getReportingLongitude()
										- longitudeDegreesToSubtract) * PIXELS_PER_DEGREE * 2))
								- (POSITION_MARK_DIMENSION.width / 2) + POSITION_MARK_DIMENSION.width),
						(int) (zoom * (Utility
								.round((Reporting.reportings.get(i).getReportingLatitude() - latitudeDegreesToSubtract)
										* PIXELS_PER_DEGREE * 2))));
			} else {
				Reporting.reportings.get(i).getPositionMark().setVisible(false);
			}

			if (!reportingStatus.equals("Tutti") && !Reporting.reportings.get(i).getReportingReason()
					.equals(ReportingReason.stringToEnum(reportingStatus))) {
				Reporting.reportings.get(i).getPositionMark().setVisible(false);
			}
		}
	}

	private void verifyReportingsNumber(String district) {
		int count = 0;
		for (int i = 0; i < Reporting.reportings.size(); i++) {
			if (Reporting.reportings.get(i).getDistrict().toString().equals(district)) {
				count++;
			}
		}
		if (count == 0) {
			JOptionPane.showMessageDialog(null, "Nessuna segnalazione per il quartiere selezionato!", "Attenzione!",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private void setPositionsMarkEvents() {
		for (int i = 0; i < Reporting.reportings.size(); i++) {
			Reporting.reportings.get(i).getPositionMark().addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent evt) {
					int index = Integer.parseInt(evt.getComponent().getName()) - 1;
					for (int i = 0; i < table.getRowCount(); i++) {
						if (Integer.parseInt((String) table.getValueAt(i, 2)) - 1 == index) {
							table.setRowSelectionInterval(i, i);
							break;
						}
					}

					Reporting.reportings.get(index).getPositionMark()
							.setIcon(new ImageIcon(PATH_IMG_RED_POSITION_MARK));

					for (int i = 0; i < Reporting.reportings.size(); i++) {
						if (i != index)
							Reporting.reportings.get(i).getPositionMark()
									.setIcon(new ImageIcon(PATH_IMG_BLUE_POSITION_MARK));
					}
				}

				@Override
				public void mouseEntered(MouseEvent evt) {
					int index = Integer.parseInt(evt.getComponent().getName()) - 1;
					lblReportingDetails.setText("<html>IdSegnalazione: "
							+ Reporting.reportings.get(index).getReportingId() + "<br>UserId: "
							+ Reporting.reportings.get(index).getUserId() + "<br>Data e ora: "
							+ Utility.timeStampToString(Reporting.reportings.get(index).getTimeStamp())
							+ "<br>Motivo segnalazione: "
							+ Reporting.reportings.get(index).getReportingReason().toString() + "<br>Presa in carico: "
							+ Utility.booleanToString(Reporting.reportings.get(index).getInCharge()) + "</html>");

					lblReportingDetails.setVisible(true);
					lblReportingDetails.setBounds(
							Reporting.reportings.get(index).getPositionMark().getLocation().x + 87,
							Reporting.reportings.get(index).getPositionMark().getLocation().y, 275, 110);
				}

				@Override
				public void mouseExited(MouseEvent evt) {
					lblReportingDetails.setVisible(false);
				}
			});
		}

	}

	/**
	 * Enable main interface
	 */
	public static void enableFrame() {
		frame.setEnabled(true);
	}

	/**
	 * Close main interface
	 */
	public static void closeFrame() {
		frame.dispose();
	}

	/**
	 * Create and set visible main interface
	 */
	public static void main() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new GUIMain();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}