package appReader.gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import appReader.Proxy;
import dataModel.Login;
import utils.Directory;

/**
 * This class create the main reader frame
 *
 * @author Marco Di Capua
 * @author Mattia Lo Schiavo
 * @author Filippo Pelosi
 * @author Riccardo Zorzi
 * @version 1.0
 */
public class GUIMainReader extends JFrame {
	private static final long serialVersionUID = 1L;
	public static GUIMainReader frame;

	private GUIMainReader() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				if (Login.getUserLogged() != null) {
					Proxy.instance().logout();
				}
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
		getContentPane().add(new PanelMainReader());
		((JPanel) getContentPane()).revalidate();
		repaint();
	}

	/**
	 * Launch the main reader interface
	 */
	public static void main() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new GUIMainReader();
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
		if(frame != null) {
			frame.dispose();
		}
		
	}
}
