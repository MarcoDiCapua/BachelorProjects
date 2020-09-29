package server;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;

import dataModel.Command;
import dataModel.Login;
import dataModel.User;
import database.BookOperation;
import database.BookingOperation;
import database.LibraryOperation;
import database.LoanOperation;
import database.QueryExecutor;
import database.UserOperation;
import utils.Operation;

/**
 * This class is a definition of a thread that handle a client requests.
 *
 * @author Marco Di Capua
 * @author Mattia Lo Schiavo
 * @author Filippo Pelosi
 * @author Riccardo Zorzi
 * @version 1.0
 */
public class ThreadServer extends Thread {
	private Skeleton skeleton;
	private User userLogged;
	private User userReaderLogged;

	/**
	 * Create and start the new thread
	 * 
	 * @param socket
	 *            the socket used to communicate with the client
	 * @throws IOException
	 *             if an I/O error occurs while processing requests
	 * @throws SQLException
	 *             if an SQL error occurs while communicate with database
	 * @throws ClassNotFoundException
	 *             if could not be found a class
	 */
	public ThreadServer(Socket socket) throws IOException, SQLException, ClassNotFoundException {
		super();
		this.userLogged = null;
		this.userReaderLogged = null;
		this.skeleton = new Skeleton(socket);
		start();
	}

	/**
	 * Start to handle client request
	 */
	@Override
	public void run() {
		while (true) {
			try {
				Command command = skeleton.getCommand();
				if (command.getOperation().equals(Operation.END)) {
					close();
					break;
				}
				Command cmd = handleCommand(command);
				skeleton.sendCommand(cmd);
			} catch (ClassNotFoundException | IOException e) {
				System.err.println("Fatal error! Exception: " + e.getMessage());
				close();
				break;
			}
		}
	}

	private Command handleCommand(Command command) throws IOException, ClassNotFoundException {
		try {
			switch (command.getOperation().split("-")[0]) {
			case Operation.LOGIN:
				return selectLoginOperation(command);
			case Operation.USER:
				return selectUserOperation(command);
			case Operation.BOOK:
				return selectBookOperation(command);
			case Operation.LIBRARY:
				return selectLibraryOperation(command);
			case Operation.LOAN:
				return selectLoanOperation(command);
			case Operation.BOOKING:
				return selectBookingOperation(command);
			case Operation.FORCED_LOGOUT:
				return forcedLogout((String) command.getData());
			}
			return new Command("Operation not supported", null);
		} catch (SQLException ex) {
			QueryExecutor.printSQLException(ex);
			if (ex.getSQLState().equals("23505")) {
				return new Command("Duplicated key value", null);
			} else {
				return new Command(ex.getSQLState(), null);
			}
		}
	}

	private Command selectLoginOperation(Command command) throws IOException, ClassNotFoundException {
		String operation = command.getOperation();
		User newUser = null;
		Command cmd = null;
		try {
			switch (operation) {
			case Operation.LOGIN_USER:
				cmd = UserOperation.login(command);
				newUser = (User) cmd.getData();
				this.userLogged = newUser;
				this.userReaderLogged = userLogged;
				break;
			case Operation.LOGIN_FROM_LIBRARIAN:
				cmd = UserOperation.loginFromLibrarian(command);
				newUser = (User) cmd.getData();
				this.userReaderLogged = newUser;
				break;
			}
			String loginResult = cmd.getOperation();
			if (newUser != null) {
				loginResult = LoginController.instance().add(((Login) command.getData()).getUserId(), this.skeleton);
			}
			return new Command(loginResult, newUser);
		} catch (SQLException ex) {
			if (operation.equals(Operation.LOGIN_FROM_LIBRARIAN)) {
				this.userReaderLogged = null;
			} else {
				this.userLogged = null;
			}
			QueryExecutor.printSQLException(ex);
			return new Command(ex.getSQLState(), null);
		}
	}

	private Command selectUserOperation(Command command) throws ClassNotFoundException, IOException, SQLException {
		switch (command.getOperation()) {
		case Operation.USER_REGISTRATION:
			return UserOperation.registration(command);
		case Operation.USER_REGISTRATION_FROM_LIBRARIAN:
			return UserOperation.registationFromLibrarian(command);
		case Operation.USER_RESET_PASSWORD:
			return UserOperation.resetPassword(command);
		case Operation.USER_MODIFY_EMAIL:
			return UserOperation.modifyEmail(command, userLogged);
		case Operation.USER_CONFIRM_MODIFY_EMAIL:
			return UserOperation.confirmModifyEmail(command, userLogged);
		case Operation.USER_MODIFY_CLASSIFICATION:
			return UserOperation.modifyClassification(command, userLogged);
		case Operation.USER_MODIFY_TELEPHONE:
			return UserOperation.modifyTelephone(command, userLogged);
		case Operation.USER_MODIFY_PASSWORD:
			return UserOperation.modifyPassword(command, userLogged);
		case Operation.USER_UPDATE_COUNTER:
			return UserOperation.updateCounter(command, userLogged);
		case Operation.USER_ACTIVATE_PROFILE:
			return UserOperation.activateProfile(userLogged);
		case Operation.USER_DELETE:
			User userToDelete = null;
			if (((String) command.getData()).equals(userLogged.getFiscalCode())) {
				userToDelete = this.userLogged;
			} else if (((String) command.getData()).equals(userReaderLogged.getFiscalCode())) {
				userToDelete = this.userReaderLogged;
			}
			Command cmd = UserOperation.delete(userToDelete);
			if (cmd.getOperation().equals("Operation completed")) {
				LoginController.instance().remove(userToDelete.getFiscalCode());
				if (((String) command.getData()).equals(userLogged.getFiscalCode())) {
					this.userLogged = null;
				} else if (((String) command.getData()).equals(userReaderLogged.getFiscalCode())) {
					this.userReaderLogged = null;
				}
			}
			return cmd;
		case Operation.USER_LOGOUT_READER:
			LoginController.instance().remove(userReaderLogged.getFiscalCode());
			this.userReaderLogged = null;
			return new Command("Operation completed", null);
		case Operation.USER_LOGOUT:
			LoginController.instance().remove(userLogged.getFiscalCode());
			this.userLogged = null;
			return new Command("Operation completed", null);
		}
		return new Command("Operation not supported", null);
	}

	private Command selectBookOperation(Command command) throws ClassNotFoundException, IOException, SQLException {
		switch (command.getOperation()) {
		case Operation.BOOK_ADD:
			return BookOperation.add(command);
		case Operation.BOOK_REMOVE:
			return BookOperation.remove(command);
		}
		return new Command("Operation not supported", null);
	}

	private Command selectBookingOperation(Command command) throws ClassNotFoundException, IOException, SQLException {
		switch (command.getOperation()) {
		case Operation.BOOKING_ADD:
			return BookingOperation.add(command, userReaderLogged);
		case Operation.BOOKING_DELETE:
			return BookingOperation.delete(command, userReaderLogged);
		}
		return new Command("Operation not supported", null);
	}

	private Command selectLoanOperation(Command command) throws ClassNotFoundException, IOException, SQLException {
		switch (command.getOperation()) {
		case Operation.LOAN_ADD:
			return LoanOperation.add(command, userReaderLogged);
		case Operation.LOAN_BACK:
			return LoanOperation.back(command, userReaderLogged);
		case Operation.LOAN_DELETE:
			return LoanOperation.delete(command, userReaderLogged);
		case Operation.LOAN_HISTORY_LOANS_USER:
			return LoanOperation.historyLoansUser(command, userReaderLogged);
		}
		return new Command("Operation not supported", null);
	}

	private Command selectLibraryOperation(Command command) throws ClassNotFoundException, IOException, SQLException {
		switch (command.getOperation()) {
		case Operation.LIBRARY_UPDATE_BOOKS:
			return LibraryOperation.updateBooks(command);
		case Operation.LIBRARY_UPDATE_BOOKINGS:
			return LibraryOperation.updateBookings(command);
		case Operation.LIBRARY_UPDATE_LOANS:
			return LibraryOperation.updateLoans(command);
		case Operation.LIBRARY_BOOKS_FILTER:
			return LibraryOperation.booksFilter(command);
		case Operation.LIBRARY_BOOKINGS_FILTER:
			return LibraryOperation.bookingsFilter(command);
		case Operation.LIBRARY_LOANS_FILTER:
			return LibraryOperation.loansFilter(command);
		case Operation.LIBRARY_OVERWHELMING_LOANS_FILTER:
			return LibraryOperation.overwhelmingLoansFilter(command);
		case Operation.LIBRARY_HISTORY_LOANS_USER_FILTER:
			return LibraryOperation.historyLoansUserFilter(command);
		case Operation.LIBRARY_ABSOLUTE_RANK:
			return LibraryOperation.absoluteRank(command);
		case Operation.LIBRARY_CATEGORY_RANK:
			return LibraryOperation.categoryRank(command);
		case Operation.LIBRARY_CLASSIFICATION_RANK:
			return LibraryOperation.classificationRank(command);
		case Operation.LIBRARY_ALL_OVERWHELMING_LOANS:
			return LibraryOperation.allOverwhelmingLoans(command);
		}
		return new Command("Operation not supported", null);
	}

	private void close() {
		if (userLogged != null) {
			LoginController.instance().remove(userLogged.getFiscalCode());
		}
		if (userReaderLogged != null) {
			LoginController.instance().remove(userReaderLogged.getFiscalCode());
		}
		try {
			this.skeleton.end();
		} catch (IOException e) {
		}
	}

	private Command forcedLogout(String fiscalCode) throws IOException {
		if (userReaderLogged != null && userReaderLogged.getFiscalCode().equals(fiscalCode)) {
			this.userReaderLogged = null;
			return new Command("Forced logout reader", null);
		}
		if (userLogged != null && userLogged.getFiscalCode().equals(fiscalCode)) {
			this.userLogged = null;
			if (this.userReaderLogged != null) {
				LoginController.instance().remove(userReaderLogged.getFiscalCode());
				this.userReaderLogged = null;
			}
			return new Command(Operation.FORCED_LOGOUT_LIBRARIAN, null);
		}
		return new Command(null, null);
	}

}
