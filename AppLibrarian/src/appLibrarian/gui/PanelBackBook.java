package appLibrarian.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import appLibrarian.Proxy;
import dataModel.Loan;
import dataModel.Login;
import tableModels.BackBookTableModel;
import tableModels.LoanTableModel;
import utils.Directory;

/**
 * This class create the back book panel
 *
 * @author Marco Di Capua
 * @author Mattia Lo Schiavo
 * @author Filippo Pelosi
 * @author Riccardo Zorzi
 * @version 1.0
 */
public class PanelBackBook extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTable tableBackBooks;
	private JTable tableLoans;

	/**
	 * Create the panel
	 */
	public PanelBackBook() {
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

		JPanel panelLoans = new JPanel();
		panelLoans.setPreferredSize(new Dimension(10, 237));
		panel.add(panelLoans, BorderLayout.SOUTH);

		panelLoans.setLayout(new BorderLayout(0, 0));

		tableLoans = new JTable();
		LoanTableModel tm = new LoanTableModel(new ArrayList<Loan>());
		tableLoans.setModel(tm);
		tableLoans.setRowSorter(new TableRowSorter<DefaultTableModel>(tm));
		tableLoans.setFont(new Font("Tahoma", Font.PLAIN, 16));
		tableLoans.getTableHeader().setReorderingAllowed(false);
		tableLoans.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 16));

		JPanel panelButtons = new JPanel();
		panelButtons.setPreferredSize(new Dimension(10, 100));
		FlowLayout fl_panelButtons = (FlowLayout) panelButtons.getLayout();
		fl_panelButtons.setVgap(10);
		fl_panelButtons.setHgap(500);
		panelLoans.add(panelButtons, BorderLayout.SOUTH);

		JButton btnMenu = new JButton("Menu");
		btnMenu.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GUIMainLibrarian.frame.getContentPane().removeAll();
				GUIMainLibrarian.frame.getContentPane().add(new PanelMainReader());
				((JPanel) GUIMainLibrarian.frame.getContentPane()).revalidate();
				GUIMainLibrarian.frame.setTitle("Menu lettore");
				GUIMainLibrarian.frame.repaint();
			}
		});
		panelButtons.add(btnMenu);

		JButton btnConfirm = new JButton("Restituisci!");
		btnConfirm.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tableLoans.getRowCount() != 0) {
					for (int i = 0; i < tableLoans.getRowCount(); i++) {
						Loan loanToReturn = ((LoanTableModel) tableLoans.getModel()).getLoanAtRow(i);
						String result = Proxy.instance().backLoan(loanToReturn);
						if (result != null) {
							if (result.equals("Operation completed")) {
								Login.getUserReaderLogged().getLoanBooks().remove(loanToReturn);
								JOptionPane.showMessageDialog(null,
										"La restituzione del libro con codice isbn: " + loanToReturn.getBook().getIsbn()
												+ " è stata correttamente registrata!",
										"Libro restituito!", JOptionPane.INFORMATION_MESSAGE);
							} else {
								JOptionPane.showMessageDialog(null,
										"A causa di un errore la restituzione del libro con codice isbn: "
												+ loanToReturn.getBook().getIsbn()
												+ " non è stata registrata correttamente!",
										"Errore", JOptionPane.ERROR_MESSAGE);
							}
						} else {
							break;
						}
					}
					GUIMainLibrarian.frame.getContentPane().removeAll();
					GUIMainLibrarian.frame.getContentPane().add(new PanelMainReader());
					((JPanel) GUIMainLibrarian.frame.getContentPane()).revalidate();
					GUIMainLibrarian.frame.setTitle("Menu lettore");
					GUIMainLibrarian.frame.repaint();
				}
			}
		});
		panelButtons.add(btnConfirm);

		JPanel panelTitle = new JPanel();
		panelLoans.add(panelTitle, BorderLayout.NORTH);
		panelTitle.setPreferredSize(new Dimension(10, 30));

		JLabel lblBooksToBack = new JLabel("Libri da restituire:");
		lblBooksToBack.setFont(new Font("Tahoma", Font.BOLD, 16));
		panelTitle.add(lblBooksToBack);
		JScrollPane scrollPaneLoans = new JScrollPane(tableLoans);
		scrollPaneLoans.setPreferredSize(new Dimension(469, 95));
		panelLoans.add(scrollPaneLoans);

		JPanel panelLogo = new JPanel();
		panelLogo.setBorder(null);
		panelLogo.setPreferredSize(new Dimension(10, 350));
		panel.add(panelLogo, BorderLayout.NORTH);
		panelLogo.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

		JLabel lblLogo = new JLabel("");
		lblLogo.setHorizontalTextPosition(SwingConstants.LEADING);
		URL imageLogoURL = getClass().getResource(Directory.PATH_IMG_LOGO_APPLIBRARIAN);
		lblLogo.setIcon(new ImageIcon(imageLogoURL));
		panelLogo.add(lblLogo);

		JPanel panelCenter = new JPanel();
		panelCenter.setPreferredSize(new Dimension(10, 90));
		panel.add(panelCenter, BorderLayout.CENTER);
		panelCenter.setLayout(new BorderLayout(0, 0));

		JPanel panelBooks = new JPanel();
		panelCenter.add(panelBooks, BorderLayout.NORTH);
		panelBooks.setPreferredSize(new Dimension(10, 107));

		tableBackBooks = new JTable();
		ArrayList<Loan> loans = Login.getUserReaderLogged().getLoanBooks();
		if (loans != null) {
			BackBookTableModel model = new BackBookTableModel(loans);
			tableBackBooks.setModel(model);
			tableBackBooks.setRowSorter(new TableRowSorter<DefaultTableModel>(model));
		}
		tableBackBooks.setFont(new Font("Tahoma", Font.PLAIN, 16));
		tableBackBooks.getTableHeader().setReorderingAllowed(false);
		tableBackBooks.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 16));

		tableBackBooks.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (tableBackBooks.columnAtPoint(arg0.getPoint()) != 0) {
					tableBackBooks.setValueAt(!(boolean) tableBackBooks.getValueAt(tableBackBooks.getSelectedRow(), 0),
							tableBackBooks.getSelectedRow(), 0);
				}
				if ((boolean) tableBackBooks.getValueAt(tableBackBooks.getSelectedRow(), 0)) {
					Loan selectedLoan = (Loan) ((BackBookTableModel) tableBackBooks.getModel())
							.getLoanAtRow(tableBackBooks.getSelectedRow());
					((LoanTableModel) tableLoans.getModel()).addLoan(selectedLoan);
				} else {
					Loan selectedLoan = ((BackBookTableModel) tableBackBooks.getModel())
							.getLoanAtRow(tableBackBooks.getSelectedRow());
					for (int i = 0; i < tableLoans.getRowCount(); i++) {
						if (((LoanTableModel) tableLoans.getModel()).getLoanAtRow(i).equals(selectedLoan)) {
							((LoanTableModel) tableLoans.getModel()).removeRow(i);
							break;
						}
					}
				}
			}
		});
		panelBooks.setLayout(new BorderLayout(0, 0));
		JScrollPane scrollPaneBooks = new JScrollPane(tableBackBooks);
		scrollPaneBooks.setPreferredSize(new Dimension(452, 95));
		panelBooks.add(scrollPaneBooks, BorderLayout.CENTER);
	}

}
