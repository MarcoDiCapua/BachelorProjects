package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

/**
 * Class that creates the guide interface
 *  
 * @author Marco Di Capua
 * @author Filippo Pelosi
 * @author Riccardo Zorzi
 * @version 1.0
 */
public class GUIGuide extends JFrame {
	// camps
	private JPanel contentPane;
	private JLabel lblGuideTitle;
	private JLabel lblGuideText;
	private final String mainInterfaceGuide = "<html>All\u2019avvio del programma si aprir\u00E0 una schermata iniziale, contenente alcune tra le funzioni principali di esso."
			+ "<br>A sinistra \u00E8 collocata una mappa della citt\u00E0 fornita di appositi puntatori indicanti le segnalazioni precedentemente effettuate.  Puntando col cursore su uno di essi, si otterranno le informazioni principali della segnalazione."
			+ "<br>Cliccando invece col tasto sinistro, il puntatore si colorer\u00E0 di rosso e la segnalazione verr\u00E0 evidenziata nella parte inferiore della schermata all\u2019interno della tabella delle segnalazioni attive."
			+ "<br>\u00C8 possibile utilizzare lo zoom della mappa attraverso l\u2019apposita barra situata alla destra di essa.</html>";
	private final String userInterfaceGuide = "<html>Una volta effettuato il login dall\u2019 interfaccia principale, si aprir\u00E0 una nuova finestra simile alla precedente."
			+ "<br>Le funzioni della mappa posta sulla sinistra sono le medesime della mappa presente nell\u2019 interfaccia principale."
			+ "<br> Si ha inoltre la possibilit\u00E0 di cliccare all\u2019interno della mappa, ottenendo cos\u00EC automaticamente la latitudine e la longitudine della nuova segnalazione."
			+ "<br>Nella parte superiore della schermata \u00E8 stata predisposta una barra che ci fornir\u00E0 diverse opzioni:"
			+ "<br>\r\n\u2022\tin \u201CFile\u201D si trova l\u2019opzione di effettuare il logout del nostro profilo-utente oppure quella di uscire dal programma chiudendolo;"
			+ "<br>\r\n\u2022\tin \u201COpzioni\u201D si pu\u00F2 accedere alla modifica o alla cancellazione del nostro profilo e alla cronologia delle segnalazioni chiuse;"
			+ "<br>\r\n\u2022\tin \u201C?\u201D si accede a questa guida.\r\nSulla parte destra della finestra avremo dei campi da compilare riguardanti l\u2019inserimento di una nuova segnalazione."
			+ "<br>Una volta confermata la segnalazione, essa verr\u00E0 inserita all\u2019interno della mappa."
			+ "<br>Si ha inoltre l\u2019opzione di filtrare le segnalazioni presenti sulla mappa e sulla tabella delle segnalazioni attive (menzionata precedentemente)."
			+ "<br>Infine, nella parte inferiore della finestra, si trova la tabella delle segnalazioni attive che ha le stesse funzioni citate precedentemente, con l\u2019aggiunta della opzione di click con il tasto destro del mouse su una segnalazione, per accedere alla opzione di presa in carico o chiusura della segnalazione."
			+ "<br>L\u2019opzione di presa in carico di una segnalazione \u00E8 disponibile solo se essa non \u00E8 gi\u00E0 stata presa in carico,"
			+ "<br>mentre l\u2019opzione di chiusura della segnalazione solo all\u2019utente che ha preso in carico o ha aperto la segnalazione.</html>";
	private final String registrationInterfaceGuide = "<html>Cliccando su “Registrati cliccando qui!” (situato nell’interfaccia principale) e compilando correttamente tutti i campi, si aprirà una nuova finestra di dimensioni ridotte nella quale potrete inserire i vostri dati personali e creare il vostro profilo-utente."
			+ "<br>A sinistra comparirà nuovamente la mappa della città da noi selezionata con la stessa funzione di zoom citata precedentemente."
			+ "<br>Per facilitare l’utilizzo dell’applicazione, sarà possibile cliccare all’interno della mappa, ottenendo così automaticamente la latitudine e la longitudine dell’abitazione del nuovo utente.</html>";
	private final String modifyPofileInterfaceGuide = "<html>Cliccando su \u201CModifica profilo\u201D, presente all\u2019interno di \u201COpzioni\u201D, si aprir\u00E0 una nuova finestra che permetter\u00E0 di accedere ai nostri dati personali e di modificarli."
			+ "<br>Se si \u00E8 interessati solo alla modifica della propria password \u00E8 sufficiente cliccare sull\u2019apposito pulsante \u201CCambia password\u201D che comporter\u00E0 l\u2019apertura di una nuova finestra in cui sar\u00E0 possibile modificare i campi per inserire una nuova password.</html>";
	private final String histroryInterfaceGuide = "<html>Cliccando su cronologia segnalazioni, presente all\u2019interno di \u201COpzioni\u201D, si aprir\u00E0 una nuova finestra contenente una tabella con le segnalazioni chiuse dagli utenti, con i relativi dati.</html>";
	private final Dimension frameDimension = new Dimension(1200, 570);
	private static GUIGuide frame;
	private static final long serialVersionUID = 9184128601260695265L;

	// constructor
	/**
	 * Create the guide interface
	 */
	public GUIGuide() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(".\\images\\icona.png"));
		setTitle("Guida");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Toolkit mioToolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = mioToolkit.getScreenSize();
		setBounds((int) ((screenSize.getWidth() - frameDimension.getWidth()) / 2),
				(int) ((screenSize.getHeight() - frameDimension.getHeight()) / 2), (int) frameDimension.getWidth(),
				(int) frameDimension.getHeight());
		paintInterface();
	}

	// methods
	private void paintInterface() {
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
		panel.setLayout(new BorderLayout(0, 0));
		panel.add(tree, BorderLayout.CENTER);
		contentPane.add(panel, BorderLayout.WEST);

		tree.setModel(new DefaultTreeModel(new DefaultMutableTreeNode("JTree") {
			private static final long serialVersionUID = 8067009708690941644L;

			{
				DefaultMutableTreeNode node = new DefaultMutableTreeNode("Guida");
				node.add(new DefaultMutableTreeNode("Interfaccia principale"));
				node.add(new DefaultMutableTreeNode("Interfaccia registrazione"));
				node.add(new DefaultMutableTreeNode("Interfaccia utente"));
				node.add(new DefaultMutableTreeNode("Interfaccia cronologia"));
				node.add(new DefaultMutableTreeNode("Interfaccia modifica profilo"));
				add(node);
			}
		}));

		JPanel mainInterfacePanel = new JPanel();
		contentPane.add(mainInterfacePanel, BorderLayout.CENTER);
		GridBagLayout gbl_panel_Interfaccia_Princ = new GridBagLayout();
		gbl_panel_Interfaccia_Princ.columnWidths = new int[] { 892, 0 };
		gbl_panel_Interfaccia_Princ.rowHeights = new int[] { 37, 22, 134, 0 };
		gbl_panel_Interfaccia_Princ.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gbl_panel_Interfaccia_Princ.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		mainInterfacePanel.setLayout(gbl_panel_Interfaccia_Princ);

		lblGuideTitle = new JLabel("Guida interfaccia principale");
		lblGuideTitle.setVisible(false);
		lblGuideTitle.setFont(new Font("Tahoma", Font.BOLD, 18));
		GridBagConstraints gbc_lblGuideTitle = new GridBagConstraints();
		gbc_lblGuideTitle.anchor = GridBagConstraints.NORTH;
		gbc_lblGuideTitle.insets = new Insets(0, 0, 5, 0);
		gbc_lblGuideTitle.gridx = 0;
		gbc_lblGuideTitle.gridy = 1;
		mainInterfacePanel.add(lblGuideTitle, gbc_lblGuideTitle);

		lblGuideText = new JLabel();
		lblGuideText.setVisible(false);
		lblGuideText.setText(mainInterfaceGuide);
		lblGuideText.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblGuideText = new GridBagConstraints();
		gbc_lblGuideText.fill = GridBagConstraints.BOTH;
		gbc_lblGuideText.gridx = 0;
		gbc_lblGuideText.gridy = 2;
		mainInterfacePanel.add(lblGuideText, gbc_lblGuideText);

		ImageIcon leafIcon = new ImageIcon("images/libro_aperto.png");
		ImageIcon halfClosedBook = new ImageIcon("images/libro_semiaperto.png");
		ImageIcon closedBook = new ImageIcon("images/libro_chiuso.png");
		DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
		renderer.setLeafIcon(leafIcon);
		renderer.setClosedIcon(closedBook);
		renderer.setOpenIcon(halfClosedBook);
		tree.setCellRenderer(renderer);

		tree.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				TreePath treePath = tree.getPathForLocation(e.getX(), e.getY());
				if (treePath != null) {
					if (treePath.toString().equals("[JTree, Guida, Interfaccia principale]")) {
						lblGuideTitle.setText("Guida interfaccia principale");
						lblGuideText.setText(mainInterfaceGuide);

					} else if (treePath.toString().equals("[JTree, Guida, Interfaccia utente]")) {
						lblGuideTitle.setText("Guida interfaccia utente");
						lblGuideText.setText(userInterfaceGuide);

					} else if (treePath.toString().equals("[JTree, Guida, Interfaccia registrazione]")) {
						lblGuideTitle.setText("Guida interfaccia registrazione");
						lblGuideText.setText(registrationInterfaceGuide);

					} else if (treePath.toString().equals("[JTree, Guida, Interfaccia modifica profilo]")) {
						lblGuideTitle.setText("Guida interfaccia modifica profilo");
						lblGuideText.setText(modifyPofileInterfaceGuide);

					} else if (treePath.toString().equals("[JTree, Guida, Interfaccia cronologia]")) {
						lblGuideTitle.setText("Guida interfaccia cronologia");
						lblGuideText.setText(histroryInterfaceGuide);
					}

					lblGuideText.setVisible(true);
					lblGuideTitle.setVisible(true);
				}
			}
		});
	}

	/**
	 * Create and set visible the guide interface
	 */
	public static void main() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new GUIGuide();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
