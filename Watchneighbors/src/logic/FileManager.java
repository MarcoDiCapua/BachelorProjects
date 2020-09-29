package logic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;

/**
 * Class that contains static method to execute operations on file .csv
 * 
 * @author Marco Di Capua
 * @author Filippo Pelosi
 * @author Riccardo Zorzi
 * @version 1.0
 */
public class FileManager {
	// camps
	private static File usersFile;
	private static File reportingsFile;

	// methods
	/**
	 * Create file "utenti.csv" and load users to ArrayList (if any)
	 */
	public static void createUsersFile() {
		usersFile = new File("Utenti.csv");
		if (usersFile.exists()) {
			readUsersFile();
		}
	}

	/**
	 * Create file "segnalazioni.csv" and load reporting to ArrayList (if any)
	 */
	public static void createReportingsFile() {
		reportingsFile = new File("Segnalazioni.csv");
		if (reportingsFile.exists()) {
			readReportingsFile();// caricamento segnalazioni da file
		}
	}

	/**
	 * Verify if file "utenti.csv" exists
	 * 
	 * @return true if file exists, false otherwise
	 */
	public static boolean usersFileExists() {
		return usersFile.exists();
	}

	/**
	 * Read file "utenti.csv" and create and save users (if any) in ArrayList 
	 */
	public static void readUsersFile() {
		try {
			BufferedReader brf = new BufferedReader(new FileReader(usersFile));
			String fileRow = brf.readLine();
			while (fileRow != null) {
				User.loadUser(fileRow);
				fileRow = brf.readLine();
			}
			brf.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Impossibie caricare il file Utenti.csv", "Errore!",
					JOptionPane.ERROR_MESSAGE);
		}

	}

	/**
	 * Write in file "utenti.csv" users in ArrayList
	 * 
	 */
	public static void writeUsersFile() {
		try {
			BufferedWriter bwf = new BufferedWriter(new FileWriter(usersFile));
			for (User user : User.users) {
				bwf.write(user.toString() + "\n");
			}
			bwf.close();

		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Impossibie salvare il file Utenti.csv", "Errore!",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Read file "segnalazioni.csv" and create and save reporting (if any) in ArrayList
	 */
	public static void readReportingsFile() {
		try {
			BufferedReader brf = new BufferedReader(new FileReader(reportingsFile));
			String fileRow = brf.readLine();
			while (fileRow != null) {
				Reporting.loadReporting(fileRow);
				fileRow = brf.readLine();
			}
			brf.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Impossibie caricare il file Segnalazioni.csv", "Errore!",
					JOptionPane.ERROR_MESSAGE);
		}

	}

	/**
	 * Write in file "segnalazioni.csv" reporting in ArrayList
	 */
	public static void writeReportingsFile() {
		try {
			BufferedWriter bwf = new BufferedWriter(new FileWriter(reportingsFile));
			for (Reporting reporting : Reporting.closedReporting) {
				bwf.write(reporting.toString() + "\n");
			}
			for (Reporting reporting : Reporting.reportingComo) {
				bwf.write(reporting.toString() + "\n");
			}
			for (Reporting reporting : Reporting.reportingLecco) {
				bwf.write(reporting.toString() + "\n");
			}
			for (Reporting reporting : Reporting.reportingVarese) {
				bwf.write(reporting.toString() + "\n");
			}
			bwf.close();

		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Impossibie salvare il file Segnalazioni.csv", "Errore!",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}