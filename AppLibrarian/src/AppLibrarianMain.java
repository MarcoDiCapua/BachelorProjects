

import javax.swing.JOptionPane;

import appLibrarian.Proxy;
import appLibrarian.gui.GUILogin;

/**
 * This class contains the main method to execute the application
 *
 * @author Marco Di Capua
 * @author Mattia Lo Schiavo
 * @author Filippo Pelosi
 * @author Riccardo Zorzi
 * @version 1.0
 */
public class AppLibrarianMain {

	/**
	 * Main method, launch the application
	 * 
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		Proxy proxy = Proxy.instance();
		if (!proxy.initializeProxy()) {
			JOptionPane.showMessageDialog(null, "Impossibile collegarsi al server! Il programma verrà chiuso",
					"Errore!", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
		GUILogin.main();
	}
}