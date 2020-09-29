package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import logic.Reporting;

/**
 * Class that creates the reportings history interface
 * 
 * @author Marco Di Capua
 * @author Filippo Pelosi
 * @author Riccardo Zorzi
 * @version 1.0
 */
public class GUIHistory extends JFrame {
	// camps
	private static final long serialVersionUID = 1252990811588955696L;
	private final String[] HEADER = { "Città", "Quartiere", "UserId", "Lat utente", "Long utente", "Lat segnalazione",
			"Long segnalazione", "Data e ora", "Motivo segnalazione", "Stato segnalazione", "Esito segnalazione",
			"Presa in carico", "Utente incaricato" };

	// constructor
	/**
	 * Class that creates the reporting history interface
	 */
	public GUIHistory() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(".\\images\\icona-watchNeighbors.png"));
		setTitle("Cronologia segnalazioni");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setExtendedState(Frame.MAXIMIZED_BOTH);
		paintInterface();
	}

	// methods
	private void paintInterface() {
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		JPanel interfacePanel = new JPanel();
		scrollPane.setViewportView(interfacePanel);
		interfacePanel.setLayout(new BorderLayout(0, 0));

		JPanel northPanel = paintNorthPanel();
		interfacePanel.add(northPanel, BorderLayout.NORTH);
		JPanel centralPanel = paintCentralPanel();
		interfacePanel.add(centralPanel, BorderLayout.CENTER);
	}

	private JPanel paintNorthPanel() {
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(10, 30));
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		JLabel lblReportingsHistory = new JLabel("Cronologia segnalazioni");
		lblReportingsHistory.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel.add(lblReportingsHistory);

		return panel;
	}

	private JPanel paintCentralPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(0, 0));
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel.add(scrollPane, BorderLayout.CENTER);
		JTable table = new JTable();
		table.setFont(new Font("Tahoma", Font.PLAIN, 16));
		String[][] tableMatrix = Reporting.createClosedReportingsMatrix();
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 16));

		DefaultTableModel tm = new DefaultTableModel(tableMatrix, HEADER) {
			private static final long serialVersionUID = 2974226497246804262L;

			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table.setModel(tm);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(table);

		return panel;
	}

	/**
	 * Create and set visible the reporting history interface
	 */
	public static void main() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIHistory frame = new GUIHistory();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}