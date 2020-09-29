package utils;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

/**
 * This class contains methods to verify the user data input
 *
 * @author Marco Di Capua
 * @author Mattia Lo Schiavo
 * @author Filippo Pelosi
 * @author Riccardo Zorzi
 * @version 1.0
 */
public class Utility {

	/**
	 * Verify if the data inserted is empty.
	 * 
	 * @param field
	 *            the user input data
	 * @return a string representing an error if the data is empty, or an empty
	 *         string otherwise
	 */
	public static String verifyNotEmptyField(String field) {
		if (field.length() == 0) {
			return "Il campo è vuoto!-";
		} else {
			return "";
		}
	}

	/**
	 * Verify if the name inserted is correct. A name is considered correct if it
	 * contains an arbitrary number of characters of the alphabet in upper or lower
	 * case
	 * 
	 * @param field
	 *            the user input name
	 * @return a string representing an error if the name isn't correct, or an empty
	 *         string otherwise
	 */
	public static String verifyName(String field) {
		if (field.matches("[a-zA-Z_\\s]+")) {
			return "";
		} else {
			return "Uno o più caratteri non sono validi!-";
		}
	}

	/**
	 * Verify if the length of the telephone number inserted is correct. A telephone
	 * number length is considered correct if it is between 8 and 10
	 * 
	 * @param field
	 *            the user input telephone number
	 * @return a string representing an error if the telephone number isn't correct,
	 *         or an empty string otherwise
	 */
	public static String verifyTelephoneLength(String field) {
		if (field.length() < 11 && field.length() > 7) {
			return "";
		} else {
			return "Numero di telefono non valido!-";
		}
	}

	/**
	 * Verify if the length of the ISBN code inserted is correct. A ISBN code length
	 * is considered correct if it is equals to 13
	 * 
	 * @param field
	 *            the user input ISBN code
	 * @return a string representing an error if the ISBN code isn't correct, or an
	 *         empty string otherwise
	 */
	public static String verifyIsbnLength(String field) {
		if (field.length() == 13) {
			return "";
		} else {
			return "Codice ISBN non valido!-";
		}
	}

	/**
	 * Verify if the numeric data inserted is correct. A numeric data is considered
	 * correct if it contains only numbers
	 * 
	 * @param field
	 *            the user input numeric data
	 * @return a string representing an error if the numeric data isn't correct, or
	 *         an empty string otherwise
	 */
	public static String verifyNumber(String field) {
		if (field.matches("[0-9]+")) {
			return "";
		} else {
			return "Uno o più caratteri non sono numeri!-";
		}
	}

	/**
	 * Verify if the alphanumeric data inserted is correct. A alphanumeric data is
	 * considered correct if it contains only numbers and characters of the alphabet
	 * in upper or lower case
	 * 
	 * @param field
	 *            the user input alphanumeric data
	 * @return a string representing an error if the alphanumeric data isn't
	 *         correct, or an empty string otherwise
	 */
	public static String verifyLettersAndNumbers(String field) {
		if (field.matches("[a-zA-Z0-9_\\p{Punct}]+")) {
			return "";
		} else {
			return "Caratteri errati!-";
		}
	}

	/**
	 * Verify if the alphanumeric data inserted is correct. A alphanumeric data is
	 * considered correct if it contains only numbers and characters of the alphabet
	 * in upper case
	 * 
	 * @param field
	 *            the user input alphanumeric data
	 * @return a string representing an error if the alphanumeric data isn't
	 *         correct, or an empty string otherwise
	 */
	public static String verifyUppercaseLettersAndNumbers(String field) {
		if (field.matches("[A-Z0-9]+")) {
			return "";
		} else {
			return "Caratteri errati!-";
		}
	}

	/**
	 * Verify if the length of the fiscal code inserted is correct. A fiscal code
	 * length is considered correct if it is equals to 16
	 * 
	 * @param field
	 *            the user input fiscal code
	 * @return a string representing an error if the fiscal code isn't correct, or
	 *         an empty string otherwise
	 */
	public static String verifyFiscalCodeLength(String field) {
		if (field.length() == 16) {
			return "";
		} else {
			return "Lunghezza del codice fiscale errata!-";
		}
	}

	/**
	 * Verify if the length of the password inserted is correct. A password length
	 * is considered correct if it is bigger than 7
	 * 
	 * @param password
	 *            the user input password
	 * @return a string representing an error if the password isn't correct, or an
	 *         empty string otherwise
	 */
	public static String verifyPasswordLength(String password) {
		if (password.length() > 7) {
			return "";
		} else {
			return "Password troppo corta!-";
		}
	}

	/**
	 * Verify if the email inserted is correct.
	 * 
	 * @param email
	 *            the user input fiscal code
	 * @return a string representing an error if the email isn't correct, or an
	 *         empty string otherwise
	 */
	public static String verifyEmail(String email) {
		try {
			InternetAddress emailAddr = new InternetAddress(email);
			emailAddr.validate();
		} catch (AddressException ex) {
			return "Email non valida!-";
		}
		return "";
	}

	/**
	 * Verify if the two passwords given are equals.
	 * 
	 * @param password
	 *            the first user input password
	 * @param confirmPassword
	 *            the second user input password
	 * @return a string representing an error if the two passwords are different, or
	 *         an empty string otherwise
	 */
	public static String verifyConfirmPassword(String password, String confirmPassword) {
		if (confirmPassword.equals(password)) {
			return "";
		} else {
			return "Le due password non coincidono!";
		}
	}

	/**
	 * Verify if the two mails given are equals.
	 * 
	 * @param email
	 *            the first user input email
	 * @param confirmEmail
	 *            the second user input email
	 * @return a string representing an error if the two mails are different, or an
	 *         empty string otherwise
	 */
	public static String verifyConfirmEmail(String email, String confirmEmail) {
		if (email.equals(confirmEmail)) {
			return "";
		} else {
			return "Le due email non coincidono!";
		}
	}
}
