package server;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import dataModel.Command;
import utils.Operation;

/**
 * This class contains informations and methods used to communicate with client
 *
 * @author Marco Di Capua
 * @author Mattia Lo Schiavo
 * @author Filippo Pelosi
 * @author Riccardo Zorzi
 * @version 1.0
 */
public class Skeleton {
	private Socket socket;
	private ObjectInputStream inputStream;
	private ObjectOutputStream outputStream;
	private String forcedLogout;

	/**
	 * Create a new skeleton object used to handle the communications with client
	 * 
	 * @param socket
	 *            the socket used to communicate with client
	 * @throws IOException
	 *             if an I/O error occurs while creating the communication streams
	 */
	public Skeleton(Socket socket) throws IOException {
		super();
		this.socket = socket;
		this.inputStream = new ObjectInputStream(socket.getInputStream());
		this.outputStream = new ObjectOutputStream(socket.getOutputStream());
		this.forcedLogout = null;
	}

	/**
	 * Send the given command to the client
	 * 
	 * @param command
	 *            the command to send
	 * @throws IOException
	 *             if an I/O error occurs while sending command to client
	 */
	public void sendCommand(Command command) throws IOException {
		this.outputStream.writeObject(command);
	}

	/**
	 * Wait to receive a command from the client
	 * 
	 * @return the received command
	 * @throws ClassNotFoundException
	 *             if Command class could not be found
	 * @throws IOException
	 *             if an I/O error occurs while receiving command
	 */
	public Command getCommand() throws ClassNotFoundException, IOException {
		Command command = (Command) inputStream.readObject();
		if (forcedLogout != null) {
			return new Command(Operation.FORCED_LOGOUT, forcedLogout);
		} else {
			return command;
		}
	}

	/**
	 * Close the communication with client
	 * 
	 * @throws IOException
	 *             if an I/O error occurs while closing skeleton
	 */
	public void end() throws IOException {
		this.inputStream.close();
		this.outputStream.close();
		System.out.println("Closed connection with: " + socket.getInetAddress());
		this.socket.close();
	}

	/**
	 * @return the fiscal code of the user to force logout
	 */
	public String getForcedLogout() {
		return forcedLogout;
	}

	/**
	 * @param forcedLogout
	 *            the fiscal code of the user to logout to set
	 */
	public void setForcedLogout(String forcedLogout) {
		this.forcedLogout = forcedLogout;
	}

}
