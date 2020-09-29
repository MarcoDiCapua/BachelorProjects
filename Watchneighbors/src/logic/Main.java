package logic;

import gui.GUIMain;

/**
 * This class contains the main method
 * 
 * @author Marco Di Capua
 * @author Filippo Pelosi
 * @author Riccardo Zorzi
 * @version 1.0
 */
public class Main {
	/**
	 * Main method
	 * 
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		User.createUsersList(); 
		Reporting.createReportingsLists();

		FileManager.createUsersFile(); 
		FileManager.createReportingsFile();
		GUIMain.main();
	}
}