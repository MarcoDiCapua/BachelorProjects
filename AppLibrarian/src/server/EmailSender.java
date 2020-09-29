package server;


import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import dataModel.Classification;
import dataModel.User;
import utils.Operation;

/**
 * This class defined a Thread that provides methods to send email
 *
 * @author Marco Di Capua
 * @author Mattia Lo Schiavo
 * @author Filippo Pelosi
 * @author Riccardo Zorzi
 * @version 1.0
 */
public class EmailSender extends Thread {
	// camps
	private static String userName;
	private static String password;
	private String type;
	private String newPassword;
	private User user;
	private String email;
	private String date;
	private String title;
	private String isbn;

	/**
	 * Create and start a new EmailSender object to send email that inform users
	 * about profile changes
	 * 
	 * @param type
	 *            the email type
	 * @param password
	 *            the new password of the account
	 * @param user
	 *            the user changed
	 */
	public EmailSender(String type, String password, User user) {
		super();
		this.type = type;
		this.newPassword = password;
		this.user = user;
		this.start();
	}

	/**
	 * Create and start a new EmailSender object to send email that inform users
	 * about books changes
	 * 
	 * @param type
	 *            the email type
	 * @param email
	 *            the user email
	 * @param date
	 *            the booking date
	 * @param title
	 *            the book title
	 * @param isbn
	 *            the book ISBN code
	 */
	public EmailSender(String type, String email, String date, String title, String isbn) {
		super();
		this.type = type;
		this.email = email;
		this.date = date;
		this.title = title;
		this.isbn = isbn;
		this.start();
	}

	/**
	 * Prepare and send the email corresponding to the type in the EmaiSender object
	 */
	@Override
	public void run() {
		try {
			Message msg = null;
			switch (this.type) {
			case Operation.USER_REGISTRATION:
				msg = prepareRegistrationEmail();
				break;
			case Operation.USER_REGISTRATION_FROM_LIBRARIAN:
				msg = prepareRegistrationFromLibrarianEmail();
				break;
			case Operation.USER_RESET_PASSWORD:
				msg = prepareResetPasswordEmail();
				break;
			case Operation.USER_MODIFY_CLASSIFICATION:
				msg = prepareModifyProfileEmail(this.type);
				break;
			case Operation.USER_MODIFY_EMAIL:
				msg = prepareModifyProfileEmail(this.type);
				break;
			case Operation.USER_MODIFY_TELEPHONE:
				msg = prepareModifyProfileEmail(this.type);
				break;
			case Operation.USER_MODIFY_PASSWORD:
				msg = prepareModifyProfileEmail(this.type);
				break;
			case "Book removed":
				msg = prepareBookRemovedEmail();
				break;
			case "Book returned":
				msg = prepareBookReturnedEmail();
				break;
			default:
				return;
			}
			Transport.send(msg, userName, password);
		} catch (MessagingException e) {
			System.out.println("Send email failed");
		}
	}

	private Message initializeMessage(String emailReceiver, String subject) throws MessagingException {
		String host = "smtp.office365.com";
		Properties props = System.getProperties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.port", 587);

		Session session = Session.getInstance(props);
		Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(userName));
		msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailReceiver, false));
		msg.setSubject(subject);
		return msg;
	}

	private Message prepareResetPasswordEmail() throws MessagingException {
		Message msg = initializeMessage(this.user.getEmail(), "Reset password profilo");
		msg.setText("@Automatic reply,"
				+ "La password e il codice attivazione del profilo sono stati resettati! Il tuo nuovo codice di attivazione è "
				+ this.user.getActivationCode() + " e la tua nuova password è " + this.newPassword);
		return msg;
	}

	private Message prepareRegistrationEmail() throws MessagingException {
		Message msg = initializeMessage(this.user.getEmail(), "Registrazione");
		String app = this.user.getClassification().equals(Classification.LIBRARIAN) ? "AppLibrarian" : "AppReader";
		msg.setText("@Automatic reply," + "La registrazione su " + app
				+ " è stata eseguita con successo il  codice di attivazione è " + this.user.getActivationCode());
		return msg;
	}

	private Message prepareRegistrationFromLibrarianEmail() throws MessagingException {
		Message msg = initializeMessage(this.user.getEmail(), "Registrazione");
		msg.setText("@Automatic reply," + "La registrazione su AppLibrarian è stata effettuata con successo, "
				+ "il codice di attivazione del tuo profilo è il seguente" + this.user.getActivationCode()
				+ " è possibile quindi accedere con la seguente password iniziale " + this.newPassword);
		return msg;
	}

	private Message prepareModifyProfileEmail(String type) throws MessagingException {
		Message msg = initializeMessage(this.user.getEmail(), "Modifica profilo");
		String text = null;
		switch (type) {
		case Operation.USER_MODIFY_CLASSIFICATION:
			text = "@Automatic reply," + "Il nuovo inquadramento  è " + this.user.getClassification();
			break;
		case Operation.USER_MODIFY_TELEPHONE:
			text = "@Automatic reply," + "Il nuovo numero di telefono  è " + this.user.getTelephoneNumber();
			break;
		case Operation.USER_MODIFY_PASSWORD:
			text = "@Automatic reply," + "La nuova password è " + this.newPassword;
			break;
		case Operation.USER_MODIFY_EMAIL:
			text = "@Automatic reply," + "Il codice per convalidare il nuovo indirizzo mail è "
					+ this.user.getActivationCode();
			break;
		}
		msg.setText(text);
		return msg;
	}

	private Message prepareBookReturnedEmail() throws MessagingException {
		Message msg = initializeMessage(this.email, "Libro disponibile");
		msg.setText("@Automatic reply" + "Il libro con codice isbn " + this.isbn + " e titolo " + this.title
				+ " prenotato il " + date + " è ora disponibile per la consegna. "
				+ "Il ritiro deve essere effettuato entro 7 giorni dalla notifica presente,"
				+ "inoltre è necessario soddisfare le scadenze e le modalità relative ad altri prestiti attivi");
		return msg;
	}

	private Message prepareBookRemovedEmail() throws MessagingException {
		Message msg = initializeMessage(this.email, "Prenotazione cancellata");
		msg.setText("@Automatic reply" + "La prenotazione relativa al libro con codice isbn " + this.isbn + " e titolo "
				+ this.title + " effettuata il " + date + " è stata cancellata a causa dell'irreperibilità del libro.");
		return msg;
	}

	/**
	 * Initialize the EmailSender with he given email and password that will be used
	 * to send email
	 * 
	 * @param email
	 *            the email used to send email
	 * @param emailPassword
	 *            the email password
	 */
	public static void initialize(String email, String emailPassword) {
		userName = email;
		password = emailPassword;
	}
}
