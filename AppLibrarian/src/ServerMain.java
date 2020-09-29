import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

import database.DbManager;
import server.EmailSender;
import server.ThreadServer;

/**
 * This class contains the main method to execute the application
 *
 * @author Marco Di Capua
 * @author Mattia Lo Schiavo
 * @author Filippo Pelosi
 * @author Riccardo Zorzi
 * @version 1.0
 */
public class ServerMain {

	/**
	 * Main method, launch the application
	 * 
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		System.out.println("Initialisation server");
		GUIMainServer.main();
	}

	/**
	 * Initialize the server components and start to wait for connections
	 * 
	 * @param dbUrl
	 *            the database URL
	 * @param dbUser
	 *            the database user
	 * @param dbPassword
	 *            the database password
	 * @param email
	 *            the email address used to send communications to users
	 * @param emailPassword
	 *            the email password
	 */
	public static void initializationServer(String dbUrl, String dbUser, String dbPassword, String email,
			String emailPassword) {
		EmailSender.initialize(email, emailPassword);
		DbManager.initalize(dbUrl, dbUser, dbPassword);
		try {
			ServerSocket serverSocket = new ServerSocket(8080);
			System.out.println("Server ready!");
			try {
				while (true) {
					Socket socket = serverSocket.accept();
					try {
						System.out.println("Accepted connection with: " + socket.getInetAddress());
						new ThreadServer(socket);
					} catch (IOException | ClassNotFoundException | SQLException e) {
						System.err.println("Exception! Closed connection with: " + socket.getInetAddress() + " ("
								+ e.getMessage() + ")");
						socket.close();
					}
				}
			} finally {
				serverSocket.close();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}

