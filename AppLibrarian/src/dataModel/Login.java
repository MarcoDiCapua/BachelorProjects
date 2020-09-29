package dataModel;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * An object of the <code>Login</code> class contains user userId and password
 *
 * @author Marco Di Capua
 * @author Mattia Lo Schiavo
 * @author Filippo Pelosi
 * @author Riccardo Zorzi
 * @version 1.0
 */
public class Login implements Serializable {
	// camps
	private static final long serialVersionUID = 1L;
	private String userId;
	private String password;
	private String type;
	private static User userLogged = null;
	private static User userReaderLogged = null;

	/**
	 * Create a new object <code>Login</code> with the information in input
	 * 
	 * @param userId
	 *            the user userId
	 * @param password
	 *            the user password
	 * @param classification
	 *            the user classification
	 */
	public Login(String userId, String password, String classification) {
		super();
		this.userId = userId;
		this.password = password;
		this.type = classification;
	}

	/**
	 * Encrypt login password
	 */
	public void encryptPassword() {
		try {
			MessageDigest m = MessageDigest.getInstance("MD5");
			m.update(this.getPassword().getBytes());
			this.setPassword(String.format("%032x", new BigInteger(1, m.digest())));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the userLogged
	 */
	public static User getUserLogged() {
		return userLogged;
	}

	/**
	 * @param userLogged
	 *            the userLogged to set
	 */
	public static void setUserLogged(User userLogged) {
		Login.userLogged = userLogged;
	}

	/**
	 * @return the userReaderLogged
	 */
	public static User getUserReaderLogged() {
		return userReaderLogged;
	}

	/**
	 * @param userReaderLogged
	 *            the userReaderLogged to set
	 */
	public static void setUserReaderLogged(User userReaderLogged) {
		Login.userReaderLogged = userReaderLogged;
	}

}
