package appReader.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import dataModel.Loan;
import dataModel.Login;
import tableModels.LoanTableModel;
import utils.Directory;

/**
 * This class create the reader loaned books list panel
 *
 * @author Marco Di Capua
 * @author Mattia Lo Schiavo
 * @author Filippo Pelosi
 * @author Riccardo Zorzi
 * @version 1.0
 */
public class PanelReaderLoanedBooks extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTable tableBooks;

	/**
	 * Create the panel
	 */
	public PanelReaderLoanedBooks() {
		super();
		setBorder(null);
		setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		add(scrollPane, BorderLayout.CENTER);

		JPanel panel = new JPanel();
		panel.setBorder(null);
		scrollPane.setViewportView(panel);
		panel.setLayout(new BorderLayout(0, 0));

		JPanel panelButtons = new JPanel();
		panelButtons.setPreferredSize(new Dimension(10, 70));
		panel.add(panelButtons, BorderLayout.SOUTH);
		FlowLayout flowLayout = (FlowLayout) panelButtons.getLayout();
		flowLayout.setHgap(400);

		JButton btnMenu = new JButton("Menu");
		panelButtons.add(btnMenu);
		btnMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GUIMainReader.frame.getContentPane().removeAll();
				GUIMainReader.frame.getContentPane().add(new PanelMainReader());
				((JPanel) GUIMainReader.frame.getContentPane()).revalidate();
				GUIMainReader.frame.setTitle("Menu lettore");
				GUIMainReader.frame.repaint();
			}
		});
		btnMenu.setFont(new Font("Tahoma", Font.PLAIN, 16));
		JPanel panelLogo = new JPanel();
		panelLogo.setBorder(null);
		panelLogo.setPreferredSize(new Dimension(10, 500));
		panel.add(panelLogo, BorderLayout.NORTH);
		panelLogo.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

		JLabel lblLogo = new JLabel("");
		lblLogo.setHorizontalTextPosition(SwingConstants.LEADING);
		URL imgLogo = getClass().getResource(Directory.PATH_IMG_LOGO_APPREADER);
		lblLogo.setIcon(new ImageIcon(imgLogo));
		panelLogo.add(lblLogo);

		JPanel panelCenter = new JPanel();
		panelCenter.setPreferredSize(new Dimension(10, 90));
		panel.add(panelCenter, BorderLayout.CENTER);
		panelCenter.setLayout(new BorderLayout(0, 0));

		JPanel panelBooks = new JPanel();
		panelCenter.add(panelBooks, BorderLayout.NORTH);
		panelBooks.setPreferredSize(new Dimension(10, 107));

		tableBooks = new JTable();
		ArrayList<Loan> loans = Login.getUserLogged().getLoanBooks();
		if (loans != null) {
			LoanTableModel tm = new LoanTableModel(loans);
			tableBooks.setModel(tm);
			tableBooks.setRowSorter(new TableRowSorter<DefaultTableModel>(tm));
		}
		tableBooks.setFont(new Font("Tahoma", Font.PLAIN, 16));
		tableBooks.getTableHeader().setReorderingAllowed(false);
		tableBooks.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 16));

		panelBooks.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPaneBooks = new JScrollPane(tableBooks);
		scrollPaneBooks.setPreferredSize(new Dimension(10, 95));
		panelBooks.add(scrollPaneBooks, BorderLayout.CENTER);
	}
}
