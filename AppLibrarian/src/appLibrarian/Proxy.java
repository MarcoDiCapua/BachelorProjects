package appLibrarian;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;

import appLibrarian.gui.GUILogin;
import appLibrarian.gui.GUIMainLibrarian;
import dataModel.Book;
import dataModel.Booking;
import dataModel.Category;
import dataModel.Classification;
import dataModel.Command;
import dataModel.Loan;
import dataModel.Login;
import dataModel.User;
import utils.Operation;

/**
 * This class contains informations and methods used to communicate with
 * ServerSchoolLib
 *
 * @author Marco Di Capua
 * @author Mattia Lo Schiavo
 * @author Filippo Pelosi
 * @author Riccardo Zorzi
 * @version 1.0
 */
@SuppressWarnings("unchecked")
public class Proxy {
	// camps
	private Socket mySocket;
	private ObjectOutputStream outputStream;
	private ObjectInputStream inputStream;
	private InetAddress address;
	private int port;
	private static Proxy proxy = null;

	private Proxy(InetAddress addr, int port) {
		super();
		this.address = addr;
		this.port = port;
	}

	/**
	 * Initialize the proxy object
	 * 
	 * @return true if the initialization is completed successfully, false otherwise
	 */
	public boolean initializeProxy() {
		try {
			mySocket = new Socket(address, port);
			outputStream = new ObjectOutputStream(mySocket.getOutputStream());
			inputStream = new ObjectInputStream(mySocket.getInputStream());
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	/**
	 * Send to the server the login information
	 * 
	 * @param operation
	 *            the operation used to process the login data
	 * @param login
	 *            the object <code>Login</code> with the user login information
	 * @return an <code>User</code> object that contains the user information if the
	 *         login data are correct, or null if there isn't a user corresponding
	 *         to the login data
	 */
	public User login(String operation, Login login) {
		Command command = sendCommand(operation, login);
		String response = command.getOperation();
		if (response.equals("Operation completed")) {
			return (User) command.getData();
		} else if (response.equals("An user was already logged")) {
			JOptionPane.showMessageDialog(null, "Un utente era già loggato con questo account ed è stato disconesso.",
					"Avviso", JOptionPane.INFORMATION_MESSAGE);
			return (User) command.getData();
		} else if (response.equals("User not exist")) {
			JOptionPane.showMessageDialog(null, "Non esiste nessun utente registrato con i dati inseriti.", "Avviso",
					JOptionPane.INFORMATION_MESSAGE);
			return null;
		} else {
			JOptionPane.showMessageDialog(null,
					"A causa di un errore non è stato possibile effettuare il login! (Codice errore: " + response + ")",
					"Errore", JOptionPane.ERROR_MESSAGE);
			return null;
		}
	}

	/**
	 * Communicate to the server that the user reader logged has logged out
	 * 
	 * @return a string contains the outcome operation
	 */
	public String logoutReader() {
		Command command = sendCommand(Operation.USER_LOGOUT_READER, null);
		if (command != null) {
			return command.getOperation();
		}
		return null;
	}

	/**
	 * Communicate to the server that the user logged has logged out
	 * 
	 * @return a string contains the outcome operation
	 */
	public String logout() {
		Command command = sendCommand(Operation.USER_LOGOUT, null);
		if (command != null) {
			return command.getOperation();
		}
		return null;
	}

	/**
	 * Send to the server a new user to registered
	 * 
	 * @param newUser
	 *            the new user to registered
	 * @return a string contains the outcome operation
	 */
	public String readerRegistration(User newUser) {
		Command command = sendCommand(Operation.USER_REGISTRATION_FROM_LIBRARIAN, newUser);
		if (command != null) {
			return command.getOperation();
		}
		return null;
	}

	/**
	 * Send to the server the userId, if exists, associated with the account to
	 * reset password
	 * 
	 * @param userId
	 *            the userId associated with the account to reset
	 * @return true if the operation is completed successfully, false otherwise
	 */
	public boolean resetPasswordUser(String userId) {
		Command command = sendCommand(Operation.USER_RESET_PASSWORD, userId);
		String response = command.getOperation();
		if (response.equals("Operation completed")) {
			return true;
		} else if (response.equals("User not active")) {
			JOptionPane.showMessageDialog(null,
					"Il profilo relativo all'userId inserito non è ancora stato attivato! Questa funzione è disponibile solo per profili attivati!",
					"Errore", JOptionPane.ERROR_MESSAGE);
			return false;
		} else {
			JOptionPane.showMessageDialog(null,
					"A causa di un errore non è stato possibile resettare la password! (Codice errore: " + response
							+ ")",
					"Errore", JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}

	/**
	 * Send to the server the request to activate the user logged
	 * 
	 * @return a string contains the outcome operation
	 */
	public String activateUser() {
		Command command = sendCommand(Operation.USER_ACTIVATE_PROFILE, null);
		if (command != null) {
			return command.getOperation();
		}
		return null;
	}

	/**
	 * Send to the server a new user to registered
	 * 
	 * @param newUser
	 *            the new user to registered
	 * @return a string contains the outcome operation
	 */
	public String registration(User newUser) {
		Command command = sendCommand(Operation.USER_REGISTRATION, newUser);
		if (command != null) {
			return command.getOperation();
		}
		return null;
	}

	/**
	 * Send to the server the request to modify the user logged email
	 * 
	 * @param newEmail
	 *            the new email to set
	 * @return a strings array contains the outcome operation in the first position
	 *         and then new email in the second operation if the operation is
	 *         completed successfully
	 */
	public String[] modifyEmail(String newEmail) {
		Command command = sendCommand(Operation.USER_MODIFY_EMAIL, newEmail);
		if (command != null) {
			String[] response = new String[] { command.getOperation(), (String) command.getData() };
			return response;
		}
		return null;
	}

	/**
	 * Communicate to the server if the new email activation is completed
	 * successfully
	 * 
	 * @param bln
	 *            the outcome of the activation process
	 * @param oldEmail
	 *            the old email to set if the activation process isn't completed
	 *            successfuly
	 * @return a string contains the outcome operation
	 */
	public String confirmModifyMail(boolean bln, String oldEmail) {
		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("Response", bln);
		data.put("Old email", oldEmail);
		Command command = sendCommand(Operation.USER_CONFIRM_MODIFY_EMAIL, data);
		if (command != null) {
			return command.getOperation();
		}
		return null;
	}

	/**
	 * 
	 * 
	 * @param user
	 * @return
	 */
	/**
	 * Send to the server the request to modify the user logged classification
	 * 
	 * @param classification
	 *            the new classification to set
	 * @param year_class
	 *            the new year and class to set
	 * @return a string contains the outcome operation
	 */
	public String modifyClassification(Classification classification, String year_class) {
		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("Classification", classification);
		data.put("Year_class", year_class);
		Command command = sendCommand(Operation.USER_MODIFY_CLASSIFICATION, data);
		if (command != null) {
			return command.getOperation();
		}
		return null;
	}

	/**
	 * Send to the server the request to modify the user logged password
	 * 
	 * @param oldPassword
	 *            inserted by the user
	 * @param newPassword
	 *            the new password to set if the old password is correct
	 * @return a string contains the outcome operation
	 */
	public String modifyPassword(String oldPassword, String newPassword) {
		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("Old password", oldPassword);
		data.put("New password", newPassword);
		Command command = sendCommand(Operation.USER_MODIFY_PASSWORD, data);
		if (command != null) {
			return command.getOperation();
		}
		return null;
	}

	/**
	 * Send to the server the request to modify the user logged telephone number
	 * 
	 * @param newTelephoneNumber
	 *            the new telephone number to set
	 * @return a string contains the outcome operation
	 */
	public String modifyTelephoneNumber(String newTelephoneNumber) {
		Command command = sendCommand(Operation.USER_MODIFY_TELEPHONE, newTelephoneNumber);
		if (command != null) {
			return command.getOperation();
		}
		return null;
	}

	/**
	 * Send to the server the request to update the user logged counter
	 * 
	 * @param newCounter
	 *            the new counter to set
	 * @return a string contains the outcome operation
	 */
	public String updateCounter(int newCounter) {
		Command command = sendCommand(Operation.USER_UPDATE_COUNTER, newCounter);
		if (command != null) {
			return command.getOperation();
		}
		return null;
	}

	/**
	 * Send to the server the request to delete the user logged
	 * 
	 * @param user
	 *            the user to delete
	 * @return a string contains the outcome operation
	 */
	public String deleteUser(User user) {
		Command command = sendCommand(Operation.USER_DELETE, user.getFiscalCode());
		if (command != null) {
			return command.getOperation();
		}
		return null;
	}

	/**
	 * Send to the server the request to remove the book from the library
	 * 
	 * @param book
	 *            the book to remove
	 * @return a string contains the outcome operation
	 */
	public String removeBook(Book book) {
		Command command = sendCommand(Operation.BOOK_REMOVE, book);
		if (command != null) {
			return command.getOperation();
		}
		return null;
	}

	/**
	 * Send to the server the request to add the book to the library
	 * 
	 * @param book
	 *            the book to add
	 * @return a string contains the outcome operation
	 */
	public String addBook(Book book) {
		Command command = sendCommand(Operation.BOOK_ADD, book);
		if (command != null) {
			return command.getOperation();
		}
		return null;
	}

	/**
	 * Send to the server the request to registered a new booking
	 * 
	 * @param bookingBook
	 *            the new booking to registered
	 * @return a string contains the outcome operation
	 */
	public String bookingBook(Booking bookingBook) {
		Command command = sendCommand(Operation.BOOKING_ADD, bookingBook);
		if (command != null) {
			return command.getOperation();
		}
		return null;
	}

	/**
	 * Send to the server the request to delete a booking
	 * 
	 * @param booking
	 *            the booking to delete
	 * @return a string contains the outcome operation
	 */
	public String deleteBooking(Booking booking) {
		Command command = sendCommand(Operation.BOOKING_DELETE, booking);
		if (command != null) {
			return command.getOperation();
		}
		return null;
	}

	/**
	 * Send to the server the request to registered a new loan
	 * 
	 * @param loan
	 *            the new loan to registered
	 * @return a string contains the outcome operation
	 */
	public String loanBook(Loan loan) {
		Command command = sendCommand(Operation.LOAN_ADD, loan);
		if (command != null) {
			return command.getOperation();
		}
		return null;
	}

	/**
	 * Send to the server the request to registered a return
	 * 
	 * @param loanToReturn
	 *            the loan to return
	 * @return a string contains the outcome operation
	 */
	public String backLoan(Loan loanToReturn) {
		Command command = sendCommand(Operation.LOAN_BACK, loanToReturn);
		if (command != null) {
			return command.getOperation();
		}
		return null;
	}

	/**
	 * Send to the server the request to delete a loan
	 * 
	 * @param loanToCancel
	 *            the loan to cancel
	 * @return a string contains the outcome operation
	 */
	public String cancelLoan(Loan loanToCancel) {
		Command command = sendCommand(Operation.LOAN_DELETE, loanToCancel);
		if (command != null) {
			return command.getOperation();
		}
		return null;
	}

	/**
	 * Send to the server the request to search the books corresponding to the
	 * inserted data in the library
	 * 
	 * @param page
	 *            the page number requested
	 * @param title
	 *            the title to search
	 * @param author
	 *            the author to search
	 * @param category
	 *            the category to search
	 * @return an HashMap with the books corresponding to the inserted data and the
	 *         pages number
	 */
	public HashMap<String, Object> selectBooksFrom(int page, String title, String author, Category category) {
		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("Page", page);
		data.put("Title", title);
		data.put("Author", author);
		data.put("Category", category);
		Command command = sendCommand(Operation.LIBRARY_BOOKS_FILTER, data);
		if (command != null) {
			String response = command.getOperation();
			if (response != null) {
				if (response.equals("Operation completed")) {
					return (HashMap<String, Object>) command.getData();
				} else {
					JOptionPane.showMessageDialog(null,
							"A causa di un errore non è stato possibile selezionare i libri! (Codice errore: "
									+ response + ")",
							"Errore", JOptionPane.ERROR_MESSAGE);
					return null;
				}
			}
		}
		return null;
	}

	/**
	 * Send to the server the request to search the bookings corresponding to the
	 * inserted data in the library
	 * 
	 * @param page
	 *            the page number requested
	 * @param title
	 *            the title to search
	 * @param author
	 *            the author to search
	 * @param category
	 *            the category to search
	 * @return an HashMap with the bookings corresponding to the inserted data and
	 *         the pages number
	 */
	public HashMap<String, Object> selectBookingsFrom(int page, String title, String author, Category category) {
		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("Page", page);
		data.put("Title", title);
		data.put("Author", author);
		data.put("Category", category);
		Command command = sendCommand(Operation.LIBRARY_BOOKINGS_FILTER, data);
		if (command != null) {
			String response = command.getOperation();
			if (response != null) {
				if (response.equals("Operation completed")) {
					return (HashMap<String, Object>) command.getData();
				} else {
					JOptionPane.showMessageDialog(null,
							"A causa di un errore non è stato possibile selezionare i libri! (Codice errore: "
									+ response + ")",
							"Errore", JOptionPane.ERROR_MESSAGE);
					return null;
				}
			}
		}
		return null;
	}

	/**
	 * Send to the server the request to search the loans corresponding to the
	 * inserted data in the library
	 * 
	 * @param page
	 *            the page number requested
	 * @param title
	 *            the title to search
	 * @param author
	 *            the author to search
	 * @param category
	 *            the category to search
	 * @return an HashMap with the loans corresponding to the inserted data and the
	 *         pages number
	 */
	public HashMap<String, Object> selectLoansFrom(int page, String title, String author, Category category) {
		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("Page", page);
		data.put("Title", title);
		data.put("Author", author);
		data.put("Category", category);
		Command command = sendCommand(Operation.LIBRARY_LOANS_FILTER, data);
		if (command != null) {
			String response = command.getOperation();
			if (response != null) {
				if (response.equals("Operation completed")) {
					return (HashMap<String, Object>) command.getData();
				} else {
					JOptionPane.showMessageDialog(null,
							"A causa di un errore non è stato possibile selezionare i libri! (Codice errore: "
									+ response + ")",
							"Errore", JOptionPane.ERROR_MESSAGE);
					return null;
				}
			}
		}
		return null;
	}

	/**
	 * Send to the server the request to search the overwhelming loans corresponding
	 * to the inserted data
	 * 
	 * @param page
	 *            the page number requested
	 * @param title
	 *            the title to search
	 * @param author
	 *            the author to search
	 * @param category
	 *            the category to search
	 * @return an HashMap with the overwhelming loans corresponding to the inserted
	 *         data and the pages number
	 */
	public HashMap<String, Object> selectOverwhelmingLoansFrom(int page, String title, String author,
			Category category) {
		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("Page", page);
		data.put("Title", title);
		data.put("Author", author);
		data.put("Category", category);
		Command command = sendCommand(Operation.LIBRARY_OVERWHELMING_LOANS_FILTER, data);
		if (command != null) {
			String response = command.getOperation();
			if (response != null) {
				if (response.equals("Operation completed")) {
					return (HashMap<String, Object>) command.getData();
				} else {
					JOptionPane.showMessageDialog(null,
							"A causa di un errore non è stato possibile selezionare i libri! (Codice errore: "
									+ response + ")",
							"Errore", JOptionPane.ERROR_MESSAGE);
					return null;
				}
			}
		}
		return null;
	}

	/**
	 * Send to the server the request to search the history user loans corresponding
	 * to the inserted data
	 * 
	 * @param page
	 *            the page number requested
	 * @param title
	 *            the title to search
	 * @param author
	 *            the author to search
	 * @param category
	 *            the category to search
	 * @param user
	 *            the user to search
	 * @return an HashMap with the history user loans corresponding to the inserted
	 *         data and the pages number
	 */
	public HashMap<String, Object> selectHistoryLoansUserFrom(int page, String title, String author, Category category,
			User user) {
		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("Page", page);
		data.put("Title", title);
		data.put("Author", author);
		data.put("Category", category);
		data.put("User", user);
		Command command = sendCommand(Operation.LIBRARY_HISTORY_LOANS_USER_FILTER, data);
		if (command != null) {
			String response = command.getOperation();
			if (response != null) {
				if (response.equals("Operation completed")) {
					return (HashMap<String, Object>) command.getData();
				} else {
					JOptionPane.showMessageDialog(null,
							"A causa di un errore non è stato possibile selezionare i libri! (Codice errore: "
									+ response + ")",
							"Errore", JOptionPane.ERROR_MESSAGE);
					return null;
				}
			}
		}
		return null;
	}

	/**
	 * Send to the server the request to select the history loans of the user reader
	 * logged
	 * 
	 * @param page
	 *            the page number requested
	 * @return an HashMap with the history loans and the pages number
	 */
	public HashMap<String, Object> selectHistoryLoans(int page) {
		Command command = sendCommand(Operation.LOAN_HISTORY_LOANS_USER, page);
		if (command != null) {
			String response = command.getOperation();
			if (response != null) {
				if (response.equals("Operation completed")) {
					return (HashMap<String, Object>) command.getData();
				} else {
					JOptionPane.showMessageDialog(null,
							"A causa di un errore non è stato possibile ottenere i prestiti! (Codice errore: "
									+ response + ")",
							"Errore", JOptionPane.ERROR_MESSAGE);
					return null;
				}
			}
		}
		return null;
	}

	/**
	 * Update the library books
	 * 
	 * @param library
	 *            the library to update
	 * @param page
	 *            the page number requested
	 * @return the pages number
	 */
	public int updateBooks(Library library, int page) {
		Command command = sendCommand(Operation.LIBRARY_UPDATE_BOOKS, page);
		if (command != null) {
			String response = command.getOperation();
			if (response != null) {
				if (response.equals("Operation completed")) {
					HashMap<String, Object> resultMap = (HashMap<String, Object>) command.getData();
					library.setBooks((ArrayList<Book>) resultMap.get("List"));
					return (int) resultMap.get("Pages");
				} else {
					JOptionPane.showMessageDialog(null,
							"A causa di un errore non è stato possibile aggiornare i libri della libreria! (Codice errore: "
									+ response + ")",
							"Errore", JOptionPane.ERROR_MESSAGE);
					return 0;
				}
			}
		}
		return 0;
	}

	/**
	 * Update the library bookings
	 * 
	 * @param library
	 *            the library to update
	 * @param page
	 *            the page number requested
	 * @return the pages number
	 */
	public int updateBookings(Library library, int page) {
		Command command = sendCommand(Operation.LIBRARY_UPDATE_BOOKINGS, page);
		if (command != null) {
			String response = command.getOperation();
			if (response != null) {
				if (response.equals("Operation completed")) {
					HashMap<String, Object> resultMap = (HashMap<String, Object>) command.getData();
					library.setBookingBooks((ArrayList<Booking>) resultMap.get("List"));
					return (int) resultMap.get("Pages");
				} else {
					JOptionPane.showMessageDialog(null,
							"A causa di un errore non è stato possibile aggiornare le prenotazioni della libreria! (Codice errore: "
									+ response + ")",
							"Errore", JOptionPane.ERROR_MESSAGE);
					return 0;
				}
			}
		}
		return 0;
	}

	/**
	 * Update the library loans
	 * 
	 * @param library
	 *            the library to update
	 * @param page
	 *            the page number requested
	 * @return the pages number
	 */
	public int updateLoans(Library library, int page) {
		Command command = sendCommand(Operation.LIBRARY_UPDATE_LOANS, page);
		if (command != null) {
			String response = command.getOperation();
			if (response != null) {
				if (response.equals("Operation completed")) {
					HashMap<String, Object> resultMap = (HashMap<String, Object>) command.getData();
					library.setLoanBooks((ArrayList<Loan>) resultMap.get("List"));
					return (int) resultMap.get("Pages");
				} else {
					JOptionPane.showMessageDialog(null,
							"A causa di un errore non è stato possibile aggiornare i prestiti della libreria! (Codice errore: "
									+ response + ")",
							"Errore", JOptionPane.ERROR_MESSAGE);
					return 0;
				}
			}
		}
		return 0;
	}

	/**
	 * Send to the server the request to select all the overwhelming loans
	 * 
	 * @param page
	 *            the page number requested
	 * @return an HashMap with the overwhelming loans and the pages number
	 */
	public HashMap<String, Object> selectOverwhelmingLoan(int page) {
		Command command = sendCommand(Operation.LIBRARY_ALL_OVERWHELMING_LOANS, page);
		if (command != null) {
			String response = command.getOperation();
			if (response != null) {
				if (response.equals("Operation completed")) {
					return (HashMap<String, Object>) command.getData();
				} else {
					JOptionPane.showMessageDialog(null,
							"A causa di un errore non è stato possibile aggiornare i prestiti sconfinanti della libreria! (Codice errore: "
									+ response + ")",
							"Errore", JOptionPane.ERROR_MESSAGE);
					return null;
				}
			}
		}
		return null;
	}

	/**
	 * Send to the server the request to select the absolute rank of the most read
	 * books
	 * 
	 * @param page
	 *            the page number requested
	 * @return an HashMap with the absolute rank and the pages number
	 */
	public HashMap<String, Object> absoluteRank(int page) {
		Command command = sendCommand(Operation.LIBRARY_ABSOLUTE_RANK, page);
		if (command != null) {
			String response = command.getOperation();
			if (response != null) {
				if (response.equals("Operation completed")) {
					return (HashMap<String, Object>) command.getData();
				} else {
					JOptionPane.showMessageDialog(null,
							"A causa di un errore non è stato possibile aggiornare la classifica dei libri più letti della libreria! (Codice errore: "
									+ response + ")",
							"Errore", JOptionPane.ERROR_MESSAGE);
					return null;
				}
			}
		}
		return null;
	}

	/**
	 * Send to the server the request to select the rank of the most read books with
	 * the category inserted
	 * 
	 * @param page
	 *            the page number requested
	 * @param category
	 *            the category to search
	 * @return an HashMap with the category rank and the pages number
	 */
	public HashMap<String, Object> categoryRank(int page, Category category) {
		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("Page", page);
		data.put("Category", category);
		Command command = sendCommand(Operation.LIBRARY_CATEGORY_RANK, data);
		if (command != null) {
			String response = command.getOperation();
			if (response != null) {
				if (response.equals("Operation completed")) {
					return (HashMap<String, Object>) command.getData();
				} else {
					JOptionPane.showMessageDialog(null,
							"A causa di un errore non è stato possibile aggiornare la classifica dei libri più letti della libreria per l'inquadramento selezionato! (Codice errore: "
									+ response + ")",
							"Errore", JOptionPane.ERROR_MESSAGE);
					return null;
				}
			}
		}
		return null;
	}

	/**
	 * Send to the server the request to select the rank of the most read books by
	 * users with the classification inserted
	 * 
	 * @param page
	 *            the page number requested
	 * @param classification
	 *            the classification to search
	 * @return an HashMap with the classification rank and the pages number
	 */
	public HashMap<String, Object> classificationRank(int page, Classification classification) {
		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("Page", page);
		data.put("Classification", classification);
		Command command = sendCommand(Operation.LIBRARY_CLASSIFICATION_RANK, data);
		if (command != null) {
			String response = command.getOperation();
			if (response != null) {
				if (response.equals("Operation completed")) {
					return (HashMap<String, Object>) command.getData();
				} else {
					JOptionPane.showMessageDialog(null,
							"A causa di un errore non è stato possibile aggiornare la classifica dei libri più letti della libreria per la classificazione selezionata! (Codice errore: "
									+ response + ")",
							"Errore", JOptionPane.ERROR_MESSAGE);
					return null;
				}
			}
		}
		return null;
	}

	/**
	 * Close the communication with the server
	 */
	public void stop() {
		try {
			outputStream.writeObject(new Command(Operation.END, null));
			inputStream.close();
			outputStream.close();
			mySocket.close();
		} catch (IOException e) {
		}
	}

	private synchronized Command sendCommand(String operation, Object data) {
		Command command = new Command(operation, data);
		try {
			outputStream.writeObject(command);
			Command cmd = (Command) inputStream.readObject();
			String response = cmd.getOperation();
			if (response.equals(Operation.FORCED_LOGOUT_LIBRARIAN)) {
				JOptionPane.showMessageDialog(null,
						"Un utente si è appena loggato con questo account da un atro pc, è stato effettuato il logout.",
						"Logout forzato", JOptionPane.INFORMATION_MESSAGE);
				Login.setUserReaderLogged(null);
				Login.setUserLogged(null);
				GUIMainLibrarian.close();
				GUILogin.main();
				return null;
			} else if (response.equals(Operation.FORCED_LOGOUT_READER)) {
				JOptionPane.showMessageDialog(null,
						"Un utente si è appena loggato con questo account da un atro pc, è stato effettuato il logout.",
						"Logout forzato", JOptionPane.INFORMATION_MESSAGE);
				Login.setUserReaderLogged(null);
				GUIMainLibrarian.close();
				GUIMainLibrarian.main();
				return null;
			} else {
				return cmd;
			}
		} catch (IOException | ClassNotFoundException e) {
			printException(e);
			return new Command(null, null);
		}
	}

	/**
	 * Instance a Proxy object
	 * 
	 * @return the PFroxy object
	 */
	public static Proxy instance() {
		if (proxy == null) {
			try {
				BufferedReader brf = new BufferedReader(new FileReader(new File("config.txt")));
				String host = brf.readLine();
				InetAddress addr = InetAddress.getByName(host);
				int port = Integer.parseInt(brf.readLine());
				brf.close();
				proxy = new Proxy(addr, port);
			} catch (NumberFormatException | IOException e) {
				JOptionPane.showMessageDialog(null,
						"Impossibile trovare il file config.txt! Il file è stato rimosso o spostato. Il programma verrà chiuso",
						"Errore!", JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			}
		}
		return proxy;
	}

	private static void printException(Exception e) {
		JOptionPane.showMessageDialog(null,
				"Errore di comunicazione con il server impossibile ontinuare l'esecuzione! L'operazione richiesta potrebbe non essere stata eseguita correttamente! Il programma verrà chiuso! (Errore: "
						+ e.getMessage() + ")",
				"Errore", JOptionPane.ERROR_MESSAGE);
		System.exit(0);
	}

}