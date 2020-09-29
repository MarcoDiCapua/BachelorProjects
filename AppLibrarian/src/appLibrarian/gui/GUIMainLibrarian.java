package appLibrarian.gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import appLibrarian.Proxy;
import dataModel.Login;
import utils.Directory;

/**
 * This class create the main librarian frame
 *
 * @author Marco Di Capua
 * @author Mattia Lo Schiavo
 * @author Filippo Pelosi
 * @author Riccardo Zorzi
 * @version 1.0
 */
public class GUIMainLibrarian extends JFrame {
	private static final long serialVersionUID = 1L;
	public static GUIMainLibrarian frame;

	private GUIMainLibrarian() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				if (Login.getUserReaderLogged() != null) {
					Proxy.instance().logoutReader();
				}
				Proxy.instance().logout();
				Proxy.instance().stop();
				System.exit(0);
			}
		});
		setForeground(Color.WHITE);
		setTitle("Men\u00F9 principale");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		URL imgWindowIcon = getClass().getResource(Directory.PATH_IMG_WINDOW_ICON);
		setIconImage(new ImageIcon(imgWindowIcon).getImage());
		setExtendedState(Frame.MAXIMIZED_BOTH);
		getContentPane().add(new PanelMainLibrarian());
		((JPanel) getContentPane()).revalidate();
		repaint();
	}

	/**
	 * Launch the main librarian interface
	 */
	public static void main() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new GUIMainLibrarian();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Close the frame
	 */
	public static void close() {
		frame.dispose();
	}
}
