package appReader.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import utils.Directory;
import utils.Guide;

/**
 * This class create the reader guide interface
 *
 * @author Marco Di Capua
 * @author Mattia Lo Schiavo
 * @author Filippo Pelosi
 * @author Riccardo Zorzi
 * @version 1.0
 */
public class GUIGuideReader extends JDialog {
	private JPanel contentPane;
	private JLabel lblGuideTitle;
	private JLabel lblGuideText;
	private static GUIGuideReader dialog;
	private final Dimension frameDimension = new Dimension(1200, 570);
	private static final long serialVersionUID = 9184128601260695265L;

	private GUIGuideReader(JFrame frame) {
		super(frame, true);
		setTitle("Guida menu lettore");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Toolkit myToolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = myToolkit.getScreenSize();
		setBounds((int) ((screenSize.getWidth() - frameDimension.getWidth()) / 2),
				(int) ((screenSize.getHeight() - frameDimension.getHeight()) / 2), (int) frameDimension.getWidth(),
				(int) frameDimension.getHeight());
		paintInterface();
	}

	private void paintInterface() {

		URL imgOpenedBook = getClass().getResource(Directory.PATH_IMG_OPENED_BOOK);
		URL imgClosedBook = getClass().getResource(Directory.PATH_IMG_CLOSED_BOOK);
		URL imgOpeningBook = getClass().getResource(Directory.PATH_IMG_OPENING_BOOK);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JTree tree = new JTree();
		tree.setFont(new Font("Tahoma", Font.PLAIN, 16));
		tree.setShowsRootHandles(true);
		tree.setRootVisible(false);
		tree.setPreferredSize(new Dimension(300, 64));

		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(350, 10));
		panel.setLayout(new BorderLayout(0, 0));
		panel.add(tree, BorderLayout.CENTER);
		contentPane.add(panel, BorderLayout.WEST);

		tree.setModel(new DefaultTreeModel(new DefaultMutableTreeNode("JTree") {
			private static final long serialVersionUID = 8067009708690941644L;

			{
				DefaultMutableTreeNode node = new DefaultMutableTreeNode("Guida menu lettore");
				node.add(new DefaultMutableTreeNode("Interfaccia principale"));
				node.add(new DefaultMutableTreeNode("Interfaccia ricerca libro"));
				node.add(new DefaultMutableTreeNode("Interfaccia restituisci libro"));
				node.add(new DefaultMutableTreeNode("Interfaccia elenco prenotazioni"));
				node.add(new DefaultMutableTreeNode("Interfaccia cancella prenotazione"));
				node.add(new DefaultMutableTreeNode("Interfaccia elenco prestiti"));
				node.add(new DefaultMutableTreeNode("Interfaccia cronologia prestiti"));
				this.add(node);
			}
		}));

		JPanel mainInterfacePanel = new JPanel();
		contentPane.add(mainInterfacePanel, BorderLayout.CENTER);

		lblGuideTitle = new JLabel("Guida interfaccia principale");
		lblGuideTitle.setVisible(false);
		mainInterfacePanel.setLayout(new BorderLayout(0, 0));
		lblGuideTitle.setFont(new Font("Tahoma", Font.BOLD, 18));
		mainInterfacePanel.add(lblGuideTitle, BorderLayout.NORTH);

		lblGuideText = new JLabel();
		lblGuideText.setVisible(false);
		lblGuideText.setText(Guide.mainInterfaceReaderGuide);
		lblGuideText.setFont(new Font("Tahoma", Font.PLAIN, 16));
		mainInterfacePanel.add(lblGuideText);

		ImageIcon leafIcon = new ImageIcon(imgOpenedBook);
		ImageIcon openingBook = new ImageIcon(imgOpeningBook);
		ImageIcon closedBook = new ImageIcon(imgClosedBook);
		DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
		renderer.setLeafIcon(leafIcon);
		renderer.setClosedIcon(closedBook);
		renderer.setOpenIcon(openingBook);
		tree.setCellRenderer(renderer);

		tree.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				TreePath treePath = tree.getPathForLocation(e.getX(), e.getY());
				if (treePath != null) {
					if (treePath.toString().equals("[JTree, Guida menu bibliotecario, Interfaccia principale]")) {
						lblGuideTitle.setText("Guida interfaccia principale");
						lblGuideText.setText(Guide.mainInterfaceReaderGuide);

					} else if (treePath.toString().equals("[JTree, Guida menu lettore, Interfaccia ricerca libro]")) {
						lblGuideTitle.setText("Guida interfaccia ricerca libro");
						lblGuideText.setText(Guide.loanBookGuide);

					} else if (treePath.toString()
							.equals("[JTree, Guida menu lettore, Interfaccia restituisci libro")) {
						lblGuideTitle.setText("Guida interfaccia restituisci libro");
						lblGuideText.setText(Guide.backBookGuide);

					} else if (treePath.toString()
							.equals("[JTree, Guida menu lettore, Interfaccia elenco prenotazioni]")) {
						lblGuideTitle.setText("Guida interfaccia elenco prenotazioni");
						lblGuideText.setText(Guide.readerBookedBooksListGuide);

					} else if (treePath.toString()
							.equals("[JTree, Guida menu lettore, Interfaccia cancella prenotazione]")) {
						lblGuideTitle.setText("Guida interfaccia cancella prenotazione");
						lblGuideText.setText(Guide.deleteBookingGuide);

					} else if (treePath.toString().equals("[JTree, Guida menu lettore, Interfaccia elenco prestiti]")) {
						lblGuideTitle.setText("Guida interfaccia elenco prestiti");
						lblGuideText.setText(Guide.readerLoanedBooksListGuide);
					} else if (treePath.toString()
							.equals("[JTree, Guida menu lettore, Interfaccia cronologia prestiti]")) {
						lblGuideTitle.setText("Guida interfaccia cronologia prestiti");
						lblGuideText.setText(Guide.histroyLoansGuide);
					}

					lblGuideText.setVisible(true);
					lblGuideTitle.setVisible(true);
				}
			}
		});
	}

	/**
	 * Launch the dialog interface
	 * 
	 * @param frame
	 *            the frame from which the dialog is displayed
	 */
	public static void main(JFrame frame) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					dialog = new GUIGuideReader(frame);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
