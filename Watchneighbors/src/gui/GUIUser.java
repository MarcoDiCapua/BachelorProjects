package gui;

/**
 * Class that creates the user interface
 *  
 * @author Marco Di Capua
 * @author Filippo Pelosi
 * @author Riccardo Zorzi
 * @version 1.0
 */
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
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import enums.City;
import enums.District;
import enums.ReportingOutcome;
import enums.ReportingReason;
import enums.ReportingStatus;
import logic.FileManager;
import logic.Reporting;
import logic.User;
import logic.Utility;

public class GUIUser extends JFrame {
	// camps
	private int row = -1;
	private JTable table;
	private JPanel mapPanel;
	private JLabel lblMap;
	private JSpinner spnLatitude;
	private JSpinner spnLongitude;
	private JComboBox<String> cmbSelectCity;
	private JComboBox<String> cmbDistrict;
	private JComboBox<String> cmbSelectDistrict;
	private JComboBox<String> cmbSelectReportingReason;
	private JSlider slider;
	private final String[] HEADER = { "Città", "Quartiere", "IdSegnalazione", "UserId", "Lat utente", "Long utente",
			"Lat segnalazione", "Long segnalazione", "Data e ora", "Motivo segnalazione", "Stato segnalazione",
			"Presa in carico", "Utente incaricato" };
	private static int mouseStartX;
	private static int mouseStartY;
	private static GUIUser frame;
	private static JLabel lblReportingDetails = new JLabel();
	private static final Dimension POSITION_MARK_DIMENSION = new Dimension(31, 31);
	private static final Dimension MAP_DIMENSION = new Dimension(1000, 500);
	private static final String PATH_IMG_RED_POSITION_MARK = ".\\images\\segnaposto_rosso.png";
	private static final String PATH_IMG_BLUE_POSTION_MARK = ".\\images\\segnaposto_blu.png";
	private static JLabel lblNewReporting = new JLabel(new ImageIcon(PATH_IMG_RED_POSITION_MARK));
	private static final int PIXELS_PER_DEGREE = 50;
	private static final long serialVersionUID = 4541212737110017447L;

	// constructors
	/**
	 * Create user interface
	 */
	public GUIUser() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(".\\images\\icona-watchNeighbors.png"));
		setTitle("WatchNeighbors");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(Frame.MAXIMIZED_BOTH);
		lblMap = new JLabel(new ImageIcon(User.userLogged.getCity().getPath()));
		if (User.userLogged.getCity().equals(City.COMO)) {
			Reporting.reportings = Reporting.reportingComo;
		} else if (User.userLogged.getCity().equals(City.LECCO)) {
			Reporting.reportings = Reporting.reportingLecco;
		} else {
			Reporting.reportings = Reporting.reportingVarese;
		}

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
				JOptionPane.showMessageDialog(null, "Il programma verrà chiuso, arrivederci!", "Arrivederci",
						JOptionPane.INFORMATION_MESSAGE);
				System.exit(0);
			}
		});

		JMenuItem mntmLogout = new JMenuItem("Logout");
		mntmLogout.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mntmLogout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				User.userLogged = null;
				JOptionPane.showMessageDialog(null, "Logout avvenuto con successo", "Logout",
						JOptionPane.INFORMATION_MESSAGE);
				GUIMain.main();
				frame.dispose();
			}
		});

		mnFile.add(mntmLogout);
		mnFile.add(mntmExit);
		JMenu mnOptions = new JMenu("Opzioni");
		mnOptions.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		menuBar.add(mnOptions);
		JMenuItem mntmModifyProfile = new JMenuItem("Modifica profilo");
		mntmModifyProfile.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mntmModifyProfile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GUIModifyProfile.main();
			}
		});
		mnOptions.add(mntmModifyProfile);

		JMenuItem mntmDeleteProfile = new JMenuItem("Cancella profilo");
		mntmDeleteProfile.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mntmDeleteProfile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JLabel lblPassword = new JLabel("Inserire password per confermare:");
				JPasswordField password = new JPasswordField();
				Object[] ob = { lblPassword, password };
				int result = JOptionPane.showConfirmDialog(null, ob, "Cancellazione profilo",
						JOptionPane.OK_CANCEL_OPTION);
				if (result == JOptionPane.OK_OPTION) {
					if (User.userLogged.verifyPassword(password.getPassword())) {
						User.userLogged.deleteUser();
						User.userLogged = null;
						GUIMain.main();
						frame.dispose();
					} else {
						JOptionPane.showMessageDialog(null, "Password errata!", "Errore!", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		mnOptions.add(mntmDeleteProfile);

		JMenuItem mntmReportingsHistory = new JMenuItem("Cronologia segnalazioni");
		mntmReportingsHistory.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mntmReportingsHistory.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GUIHistory.main();
			}
		});
		mnOptions.add(mntmReportingsHistory);

		JMenu mnGuide = new JMenu("?");
		mnGuide.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		menuBar.add(mnGuide);

		JMenuItem mntmGuide = new JMenuItem("Guida");
		mntmGuide.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mntmGuide.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				GUIGuide.main();
			}
		});
		mnGuide.add(mntmGuide);

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
		JPanel centralPanel = paintCentralPanel();
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

	private JPanel paintCentralPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(0, 0));
		JPanel southPanel = paintSouthPanel();
		panel.add(southPanel, BorderLayout.SOUTH);
		JPanel panelMap = paintPanelMap();
		panel.add(panelMap, BorderLayout.WEST);
		JPanel centralPanel = paintPanelCentral();
		panel.add(centralPanel, BorderLayout.CENTER);

		return panel;
	}

	private JPanel paintPanelMap() {
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(1180, 620));
		panel.setLayout(null);
		JLabel lblNorth = new JLabel("Nord");
		lblNorth.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNorth.setBounds(583, 27, 51, 17);
		panel.add(lblNorth);

		JLabel lblSouth = new JLabel("Sud");
		lblSouth.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblSouth.setBounds(590, 640, 36, 16);
		panel.add(lblSouth);
		JLabel lblWest = new JLabel("Ovest");
		lblWest.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblWest.setBounds(10, 330, 51, 16);
		panel.add(lblWest);

		JLabel lblEast = new JLabel("Est");
		lblEast.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblEast.setBounds(1139, 330, 30, 22);
		panel.add(lblEast);
		paintMapPanel();
		JScrollPane scrollPaneMap = paintScrollPaneMap();
		panel.add(scrollPaneMap);
		paintSlider();
		panel.add(slider);

		JLabel lblIncreaseZoom = new JLabel("+");
		lblIncreaseZoom.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblIncreaseZoom.setBounds(1147, 370, 25, 30);
		panel.add(lblIncreaseZoom);

		JLabel lblDecreaseZoom = new JLabel("-");
		lblDecreaseZoom.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblDecreaseZoom.setBounds(1153, 635, 13, 21);
		panel.add(lblDecreaseZoom);

		return panel;
	}

	private void paintMapPanel() {
		mapPanel = new JPanel();
		mapPanel.setBorder(null);
		mapPanel.setMinimumSize(new Dimension(MAP_DIMENSION.width + 62, MAP_DIMENSION.height + 62));
		mapPanel.setPreferredSize(new Dimension(MAP_DIMENSION.width + 62, MAP_DIMENSION.height + 62));
		mapPanel.setLayout(null);
		lblNewReporting.setVisible(false);
		mapPanel.add(lblNewReporting);
		lblMap.setBorder(new LineBorder(new Color(0, 0, 0)));

		lblMap.setBounds(POSITION_MARK_DIMENSION.width, POSITION_MARK_DIMENSION.height, MAP_DIMENSION.width,
				MAP_DIMENSION.height);
		mapPanel.add(lblMap);
		for (int i = 0; i < Reporting.reportings.size(); i++) {
			setPositionMarkEvents(Reporting.reportings.get(i));
		}

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

			@Override
			public void mouseClicked(MouseEvent evt) {
				insertCoordinates(evt.getX(), evt.getY());
				lblNewReporting.setBounds((int) Math.floor(evt.getX()) - (POSITION_MARK_DIMENSION.width / 2),
						(int) Math.floor(evt.getY()) - POSITION_MARK_DIMENSION.height, POSITION_MARK_DIMENSION.width,
						POSITION_MARK_DIMENSION.height);
				lblNewReporting.setVisible(true);
				mapPanel.setComponentZOrder(lblNewReporting, getComponentCount() - 1);
				for (int i = 0; i < Reporting.reportings.size(); i++) {
					Reporting.reportings.get(i).getPositionMark().setIcon(new ImageIcon(PATH_IMG_BLUE_POSTION_MARK));
				}
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
				lblNewReporting.setVisible(false);
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

	private JPanel paintPanelCentral() {
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(390, 10));
		panel.setLayout(new BorderLayout(0, 0));
		JPanel reportingPanel = new JPanel();
		reportingPanel.setPreferredSize(new Dimension(410, 400));
		panel.add(reportingPanel, BorderLayout.EAST);
		reportingPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JPanel newReportingPanel = paintNewReportingPanel();
		reportingPanel.add(newReportingPanel);
		JPanel filterReportingPanel = paintFilterReportingPanel();
		reportingPanel.add(filterReportingPanel);

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
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 16));

		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(0, 0));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollPaneTable = new JScrollPane(table);
		panel.add(scrollPaneTable);

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent evt) {
				row = Integer.parseInt((String) table.getValueAt(table.rowAtPoint(evt.getPoint()), 2)) - 1;
				if (evt.isPopupTrigger()) {
					table.setRowSelectionInterval(row, row);
					createPopupMenu(evt.getX(), evt.getY(), row);
				} else {
					lblNewReporting.setVisible(false);
					Reporting.reportings.get(row).getPositionMark().setIcon(new ImageIcon(PATH_IMG_RED_POSITION_MARK));
					mapPanel.setComponentZOrder(Reporting.reportings.get(row).getPositionMark(),
							getComponentCount() - 1);
					for (int i = 0; i < Reporting.reportings.size(); i++) {
						if (i != row) {
							Reporting.reportings.get(i).getPositionMark()
									.setIcon(new ImageIcon(PATH_IMG_BLUE_POSTION_MARK));
						}
					}
				}
			}
		});

		return panel;
	}

	private JPanel paintNewReportingPanel() {
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(385, 265));
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Nuova segnalazione",
				TitledBorder.LEADING, TitledBorder.TOP, new Font("Tahoma", Font.BOLD, 18), new Color(0, 0, 0)));
		GridBagLayout gbl_panelNewReporting = new GridBagLayout();
		gbl_panelNewReporting.columnWidths = new int[] { 145, 82, 97 };
		gbl_panelNewReporting.rowHeights = new int[] { 22, 22, 22, 22, 22, 22, 25 };
		gbl_panelNewReporting.columnWeights = new double[] { 0.0, 0.0, 5.0 };
		gbl_panelNewReporting.rowWeights = new double[] { 5.0, 5.0, 5.0, 5.0, 5.0, 5.0, 5.0 };
		panel.setLayout(gbl_panelNewReporting);

		JLabel lblReportingLatitude = new JLabel("Latitudine segnalazione:");
		lblReportingLatitude.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblReportingLatitude = new GridBagConstraints();
		gbc_lblReportingLatitude.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblReportingLatitude.insets = new Insets(0, 0, 5, 5);
		gbc_lblReportingLatitude.gridx = 0;
		gbc_lblReportingLatitude.gridy = 0;
		panel.add(lblReportingLatitude, gbc_lblReportingLatitude);

		spnLatitude = new JSpinner(
				new SpinnerNumberModel(new Double(0.0), new Double(0.0), new Double(10.0), new Double(0.1)));
		spnLatitude.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_spnLatitude = new GridBagConstraints();
		gbc_spnLatitude.fill = GridBagConstraints.HORIZONTAL;
		gbc_spnLatitude.insets = new Insets(0, 0, 5, 5);
		gbc_spnLatitude.gridx = 1;
		gbc_spnLatitude.gridy = 0;
		panel.add(spnLatitude, gbc_spnLatitude);

		JLabel lblReportingLongitude = new JLabel("Longitudine segnalazione:");
		lblReportingLongitude.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblReportingLongitude = new GridBagConstraints();
		gbc_lblReportingLongitude.anchor = GridBagConstraints.WEST;
		gbc_lblReportingLongitude.insets = new Insets(0, 0, 5, 5);
		gbc_lblReportingLongitude.gridx = 0;
		gbc_lblReportingLongitude.gridy = 1;
		panel.add(lblReportingLongitude, gbc_lblReportingLongitude);

		spnLongitude = new JSpinner(
				new SpinnerNumberModel(new Double(0.0), new Double(0.0), new Double(20.0), new Double(0.1)));
		spnLongitude.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_spnLongitude = new GridBagConstraints();
		gbc_spnLongitude.fill = GridBagConstraints.HORIZONTAL;
		gbc_spnLongitude.insets = new Insets(0, 0, 5, 5);
		gbc_spnLongitude.gridx = 1;
		gbc_spnLongitude.gridy = 1;
		panel.add(spnLongitude, gbc_spnLongitude);

		JLabel lblDistrict = new JLabel("Quartiere:");
		lblDistrict.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblDistrict = new GridBagConstraints();
		gbc_lblDistrict.anchor = GridBagConstraints.WEST;
		gbc_lblDistrict.insets = new Insets(0, 0, 5, 5);
		gbc_lblDistrict.gridx = 0;
		gbc_lblDistrict.gridy = 2;
		panel.add(lblDistrict, gbc_lblDistrict);

		cmbDistrict = new JComboBox<String>(new DefaultComboBoxModel<String>(District.arrayDistricts()));
		cmbDistrict.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_cmbDistrict = new GridBagConstraints();
		gbc_cmbDistrict.anchor = GridBagConstraints.WEST;
		gbc_cmbDistrict.insets = new Insets(0, 0, 5, 0);
		gbc_cmbDistrict.gridwidth = 2;
		gbc_cmbDistrict.gridx = 1;
		gbc_cmbDistrict.gridy = 2;
		panel.add(cmbDistrict, gbc_cmbDistrict);

		JLabel lblReportingReason = new JLabel("Motivo segnalazione:");
		lblReportingReason.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblReportingReason = new GridBagConstraints();
		gbc_lblReportingReason.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblReportingReason.insets = new Insets(0, 0, 5, 5);
		gbc_lblReportingReason.gridx = 0;
		gbc_lblReportingReason.gridy = 3;
		panel.add(lblReportingReason, gbc_lblReportingReason);

		JComboBox<String> cmbReportingReason = new JComboBox<String>(
				new DefaultComboBoxModel<String>(ReportingReason.createArrayReportingsReason()));
		cmbReportingReason.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_cmbReportingReason = new GridBagConstraints();
		gbc_cmbReportingReason.anchor = GridBagConstraints.WEST;
		gbc_cmbReportingReason.insets = new Insets(0, 0, 5, 0);
		gbc_cmbReportingReason.gridwidth = 2;
		gbc_cmbReportingReason.gridx = 1;
		gbc_cmbReportingReason.gridy = 3;
		panel.add(cmbReportingReason, gbc_cmbReportingReason);

		JLabel lblReportingStatus = new JLabel("Stato segnalazione:");
		lblReportingStatus.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblReportingStatus = new GridBagConstraints();
		gbc_lblReportingStatus.anchor = GridBagConstraints.WEST;
		gbc_lblReportingStatus.insets = new Insets(0, 0, 5, 5);
		gbc_lblReportingStatus.gridx = 0;
		gbc_lblReportingStatus.gridy = 4;
		panel.add(lblReportingStatus, gbc_lblReportingStatus);

		JComboBox<String> cmbReportingStatus = new JComboBox<String>(
				new DefaultComboBoxModel<String>(ReportingStatus.createReportingsStatusArray()));
		cmbReportingStatus.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_cmbReportingStatus = new GridBagConstraints();
		gbc_cmbReportingStatus.anchor = GridBagConstraints.WEST;
		gbc_cmbReportingStatus.insets = new Insets(0, 0, 5, 0);
		gbc_cmbReportingStatus.gridwidth = 2;
		gbc_cmbReportingStatus.gridx = 1;
		gbc_cmbReportingStatus.gridy = 4;
		panel.add(cmbReportingStatus, gbc_cmbReportingStatus);

		JLabel lblInCharge = new JLabel("In carico:");
		lblInCharge.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblInCharge = new GridBagConstraints();
		gbc_lblInCharge.anchor = GridBagConstraints.WEST;
		gbc_lblInCharge.insets = new Insets(0, 0, 5, 5);
		gbc_lblInCharge.gridx = 0;
		gbc_lblInCharge.gridy = 5;
		panel.add(lblInCharge, gbc_lblInCharge);

		JComboBox<String> cmbInCharge = new JComboBox<String>(
				new DefaultComboBoxModel<String>(new String[] { "Sì", "No" }));
		cmbInCharge.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_cmbInCharge = new GridBagConstraints();
		gbc_cmbInCharge.anchor = GridBagConstraints.WEST;
		gbc_cmbInCharge.insets = new Insets(0, 0, 5, 0);
		gbc_cmbInCharge.gridx = 1;
		gbc_cmbInCharge.gridy = 5;
		panel.add(cmbInCharge, gbc_cmbInCharge);

		JButton btnConfirm = new JButton("Conferma");
		btnConfirm.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_btnConfirm = new GridBagConstraints();
		gbc_btnConfirm.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnConfirm.gridx = 2;
		gbc_btnConfirm.gridy = 6;
		panel.add(btnConfirm, gbc_btnConfirm);

		btnConfirm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!District.stringToEnum(cmbDistrict.getSelectedItem().toString())
						.belongs((double) spnLatitude.getValue(), (double) spnLongitude.getValue())) {
					JOptionPane.showMessageDialog(null, "Le coordinate non corrispondono al quartiere selezionato!",
							"Attenzione!", JOptionPane.WARNING_MESSAGE);
				} else {
					String userInCharge = " ";
					if ((cmbInCharge.getSelectedItem().toString()).equals("Sì")) {
						userInCharge = User.userLogged.getUserId();
					}

					Reporting newReporting = Reporting.createNewReporting(
							City.stringToEnum(cmbSelectCity.getSelectedItem().toString()),
							District.stringToEnum(cmbDistrict.getSelectedItem().toString()),
							(double) spnLatitude.getValue(), (double) spnLongitude.getValue(),
							ReportingReason.stringToEnum(cmbReportingReason.getSelectedItem().toString()),
							ReportingStatus.stringToEnum(cmbReportingStatus.getSelectedItem().toString()),
							cmbInCharge.getSelectedItem().toString(), userInCharge);
					newReporting.getPositionMark().setIcon(new ImageIcon(PATH_IMG_BLUE_POSTION_MARK));
					paintTable("Tutti", cmbSelectReportingReason.getSelectedItem().toString());

					setPositionMarkEvents(newReporting);
					mapPanel.add(newReporting.getPositionMark());
					mapPanel.setComponentZOrder(newReporting.getPositionMark(), getComponentCount() - 1);
					paintReportings(cmbSelectDistrict.getSelectedItem().toString(), slider.getValue() / 10.0,
							cmbSelectReportingReason.getSelectedItem().toString());
					lblNewReporting.setVisible(false);
					JOptionPane.showMessageDialog(null, "La segnalazione è stata inserita con successo!",
							"Segnalazione inserita!", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		return panel;
	}

	private JPanel paintFilterReportingPanel() {
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Filtra segnalazioni", TitledBorder.LEADING, TitledBorder.TOP,
				new Font("Tahoma", Font.BOLD, 18), new Color(0, 0, 0)));
		panel.setPreferredSize(new Dimension(395, 130));
		panel.setLayout(null);

		JLabel lblSelectCity = new JLabel("Seleziona citt\u00E0:");
		lblSelectCity.setBounds(12, 27, 106, 20);
		lblSelectCity.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel.add(lblSelectCity);

		JLabel lblSelectDistrict = new JLabel("Seleziona quartiere:");
		lblSelectDistrict.setBounds(12, 58, 140, 20);
		lblSelectDistrict.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel.add(lblSelectDistrict);

		JLabel lblSelezionaMotivoSegnalazione = new JLabel("Seleziona motivo segnalazione:");
		lblSelezionaMotivoSegnalazione.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblSelezionaMotivoSegnalazione.setBounds(12, 91, 221, 20);
		panel.add(lblSelezionaMotivoSegnalazione);

		cmbSelectReportingReason = new JComboBox<String>(new DefaultComboBoxModel<String>(
				new String[] { "Tutti", "Furto", "Incendio", "Omicidio", "Scippo", "Allarme", "Persona sospetta" }));
		cmbSelectReportingReason.setFont(new Font("Tahoma", Font.PLAIN, 16));
		cmbSelectReportingReason.setBounds(238, 91, 150, 26);
		panel.add(cmbSelectReportingReason);
		cmbSelectReportingReason.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				paintReportings(cmbSelectDistrict.getSelectedItem().toString(), 1.0,
						cmbSelectReportingReason.getSelectedItem().toString());
			}
		});

		cmbSelectDistrict = new JComboBox<String>(new DefaultComboBoxModel<String>(
				new String[] { "Tutti", "Nord-ovest", "Nord-est", "Sud-ovest", "Sud-est" }));
		cmbSelectDistrict.setBounds(238, 55, 109, 26);
		cmbSelectDistrict.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel.add(cmbSelectDistrict);

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

		cmbSelectCity = new JComboBox<String>(new DefaultComboBoxModel<String>(City.createCitiesArray()));
		cmbSelectCity.setBounds(238, 24, 78, 26);
		cmbSelectCity.setFont(new Font("Tahoma", Font.PLAIN, 16));
		cmbSelectCity.setSelectedItem(User.userLogged.getCity().toString());
		panel.add(cmbSelectCity);
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
				for (int i = 0; i < Reporting.reportings.size(); i++) {
					setPositionMarkEvents(Reporting.reportings.get(i));
				}

				lblMap.setIcon(new ImageIcon(City.stringToEnum(cmbSelectCity.getSelectedItem().toString()).getPath()));
				paintReportings("Tutti", 1.0, cmbSelectReportingReason.getSelectedItem().toString());
			}
		});

		return panel;
	}

	private void paintTable(String district, String reportingReason) {
		String[][] tableMatrix = null;
		if (district.equals("Tutti")) {
			tableMatrix = Reporting.createReportingsMatrix(reportingReason);
		} else {
			tableMatrix = Reporting.createReportingsMatrix(District.stringToEnum(district), reportingReason);
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

	private void createPopupMenu(int x, int y, int row) {
		JPopupMenu popup = new JPopupMenu();
		JMenuItem popupTakingInCharge = new JMenuItem("Prenderti carico della segnalazione");
		JMenuItem popupCloseReporting = new JMenuItem("Chiudi segnalazione");
		popup.add(popupTakingInCharge);
		popup.add(popupCloseReporting);
		popup.show(table, x, y);
		popupTakingInCharge.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (Reporting.reportings.get(row).getUserInCharge().equals(" ")) {
					Reporting.reportings.get(row).setUserInCharge(User.userLogged.getUserId());
					Reporting.reportings.get(row).setInCharge(true);
					paintTable(cmbSelectDistrict.getSelectedItem().toString(),
							cmbSelectReportingReason.getSelectedItem().toString());
					FileManager.writeReportingsFile();
					JOptionPane.showMessageDialog(null, "Ti sei preso carico della segnalazione!", "Presa in carico",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, "Questa segnalazione è già stata presa in carico!",
							"Attenzione!", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});

		popupCloseReporting.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (User.userLogged.getUserId().equals(Reporting.reportings.get(row).getUserInCharge())
						|| User.userLogged.getUserId().equals(Reporting.reportings.get(row).getUserId())) {
					String outcome = (String) JOptionPane.showInputDialog(frame, "Scegliere esito segnalazione:",
							"Esito segnalazione", JOptionPane.INFORMATION_MESSAGE, null,
							ReportingOutcome.createArrayReportingsOutcome(),
							ReportingOutcome.createArrayReportingsOutcome()[0]);
					if (outcome != null && !outcome.equals("Non ancora risolto")) {
						Reporting.reportings.get(row).setOutcomeReporting(ReportingOutcome.stringToEnum(outcome));
						Reporting.reportings.get(row).closeReporting();
						paintTable(cmbSelectDistrict.getSelectedItem().toString(),
								cmbSelectReportingReason.getSelectedItem().toString());
					}
				} else {
					JOptionPane.showMessageDialog(null, "Non puoi chiuedere questa segnalazione!", "Attenzione!",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
	}

	private void insertCoordinates(int x, int y) {
		double zoom = slider.getValue() / 10.0;
		double longitude = 0.0;
		double latitude = 0.0;

		if (cmbSelectDistrict.getSelectedItem().toString().equals("Tutti")) {
			longitude = Utility.round((Math.floor(x) - POSITION_MARK_DIMENSION.width) / (zoom * PIXELS_PER_DEGREE));
			latitude = Utility.round((Math.floor(y) - POSITION_MARK_DIMENSION.height) / (zoom * PIXELS_PER_DEGREE));

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

		} else {
			int longitudeDegreesToSubtract = 0;
			int latitudeDegreesToSubtract = 0;
			District district = District.stringToEnum(cmbSelectDistrict.getSelectedItem().toString());

			switch (district) {
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

			longitude = Utility.round((Math.floor(x) - POSITION_MARK_DIMENSION.width) / (zoom * PIXELS_PER_DEGREE * 2))
					+ longitudeDegreesToSubtract;
			latitude = Utility.round((Math.floor(y) - POSITION_MARK_DIMENSION.height) / (zoom * PIXELS_PER_DEGREE * 2))
					+ latitudeDegreesToSubtract;
			latitude = district.verifyLatitude(latitude);
			longitude = district.verifyLongitude(longitude);
		}

		spnLatitude.setValue(latitude);
		spnLongitude.setValue(longitude);
		cmbDistrict.setSelectedItem(District.districtCoordinates(latitude, longitude));
	}

	private void paintReportings(String district, double zoom, String reportingReason) {
		paintTable(cmbSelectDistrict.getSelectedItem().toString(), reportingReason);
		for (int i = 0; i < Reporting.reportings.size(); i++) {
			if (district.equals("Tutti")) {
				Reporting.reportings.get(i).getPositionMark().setIcon(new ImageIcon(PATH_IMG_BLUE_POSTION_MARK));
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
			if (!reportingReason.equals("Tutti") && !Reporting.reportings.get(i).getReportingReason()
					.equals(ReportingReason.stringToEnum(reportingReason))) {
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

	private void setPositionMarkEvents(Reporting reporting) {
		reporting.getPositionMark().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent evt) {
				int index = Integer.parseInt(evt.getComponent().getName()) - 1;
				for (int i = 0; i < table.getRowCount(); i++) {
					if (Integer.parseInt((String) table.getValueAt(i, 2)) - 1 == index) {
						table.setRowSelectionInterval(i, i);
						break;
					}
				}

				Reporting.reportings.get(index).getPositionMark().setIcon(new ImageIcon(PATH_IMG_RED_POSITION_MARK));
				lblNewReporting.setVisible(false);

				for (int i = 0; i < Reporting.reportings.size(); i++) {
					if (i != index)
						Reporting.reportings.get(i).getPositionMark()
								.setIcon(new ImageIcon(PATH_IMG_BLUE_POSTION_MARK));
				}
			}

			@Override
			public void mouseEntered(MouseEvent evt) {
				int index = Integer.parseInt(evt.getComponent().getName()) - 1;
				lblReportingDetails.setText("<html>IdSegnalazione: " + Reporting.reportings.get(index).getReportingId()
						+ "<br>UserId: " + Reporting.reportings.get(index).getUserId() + "<br>Data e ora: "
						+ Utility.timeStampToString(Reporting.reportings.get(index).getTimeStamp())
						+ "<br>Motivo segnalazione: " + Reporting.reportings.get(index).getReportingReason().toString()
						+ "<br>Presa in carico: "
						+ Utility.booleanToString(Reporting.reportings.get(index).getInCharge()) + "</html>");

				lblReportingDetails.setVisible(true);
				lblReportingDetails.setBounds(Reporting.reportings.get(index).getPositionMark().getLocation().x + 87,
						Reporting.reportings.get(index).getPositionMark().getLocation().y, 275, 110);
			}

			@Override
			public void mouseExited(MouseEvent evt) {
				lblReportingDetails.setVisible(false);
			}
		});

	}

	/**
	 * Create and set visible user interface
	 */
	public static void main() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new GUIUser();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}