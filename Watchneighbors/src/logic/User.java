package logic;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import enums.City;
import enums.District;

/**
 * An object of the User class keeps user information
 * 
 * @author Marco Di Capua
 * @author Filippo Pelosi
 * @author Riccardo Zorzi
 * @version 1.0
 */
public class User {
	// camps
	private String name;
	private String surname;
	private String email;
	private String userId;
	private String password;
	private City city;
	private District district;
	private double latitude;
	private double longitude;

	/**
	 * ArrayList of objects <code>User</code> contains the registered users
	 */
	public static ArrayList<User> users;

	/**
	 * Object <code>User</code> corresponding to the logged user
	 */
	public static User userLogged = null;

	// constructors
	/**
	 * Create a new object <code>User</code> with the information in input
	 * 
	 * @param name
	 *            is the user name
	 * @param surname
	 *            is the user surname
	 * @param email
	 *            is the user email
	 * @param userId
	 *            is the user Id
	 * @param password
	 *            is the user password
	 * @param city
	 *            is the user <code>City</code>
	 * @param district
	 *            is the user <code>Distrct</code>
	 * @param latitude
	 *            is the user latitude
	 * @param longitude
	 *            is the user longitude
	 */
	public User(String name, String surname, String userId, String email, String password, City city, District district,
			double latitude, double longitude) {
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.userId = userId;
		this.password = password;
		this.city = city;
		this.district = district;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	/**
	 * Create a new object <code>User</code> with the informations in the file
	 * row in input
	 * 
	 * @param fileRow
	 *            is the file .csv row
	 */
	public User(String fileRow) {
		String[] tok = fileRow.split(";");

		this.name = tok[0];
		this.surname = tok[1];
		this.email = tok[2];
		this.userId = tok[3];
		this.password = tok[4];
		this.city = City.stringToEnum(tok[5]);
		this.district = District.stringToEnum(tok[6]);
		this.latitude = Double.parseDouble(tok[7]);
		this.longitude = Double.parseDouble(tok[8]);
	}

	// metodi
	/**
	 * Get the user name
	 * 
	 * @return a String representation of the user name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the user name
	 * 
	 * @param name
	 *            is the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get the user surname
	 * 
	 * @return a String representation of the user surname
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * Set the user surname
	 * 
	 * @param surname
	 *            is the surname to set
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}

	/**
	 * Get the user email
	 * 
	 * @return a String representation of the user email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Set the user email
	 * 
	 * @param email
	 *            is the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Get the user Id
	 * 
	 * @return a String representation of the user Id
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * Set the user Id
	 * 
	 * @param userId
	 *            is the Id to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * Get the user password
	 * 
	 * @return a String representation of the user password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Set the user password
	 * 
	 * @param password
	 *            is the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Get the user city
	 * 
	 * @return an object <code>City</code> representing the user city
	 */
	public City getCity() {
		return city;
	}

	/**
	 * Set the user city
	 * 
	 * @param city
	 *            is the object <code>City</code> to set
	 */
	public void setCity(City city) {
		this.city = city;
	}

	/**
	 * Get the user district
	 * 
	 * @return an object <code>District</code> representing the user district
	 */
	public District getDistrict() {
		return district;
	}

	/**
	 * Set the user district
	 * 
	 * @param district
	 *            is the object <code>District</code> to set
	 */
	public void setDistrict(District district) {
		this.district = district;
	}

	/**
	 * Get the user latitude
	 * 
	 * @return a double representing the user latitude
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * Set the user latitude
	 * 
	 * @param latitude
	 *            is the latitude to set
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	/**
	 * Get the user longitude
	 * 
	 * @return a double representing the user longitude
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * Set the user longitude
	 * 
	 * @param longitude
	 *            is the longitude to set
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	/**
	 * Get a String representation of the object <code>User</code>
	 * 
	 * @return a String representation of the <code>User</code>
	 */
	@Override
	public String toString() {
		return this.getName() + ";" + this.getSurname() + ";" + this.getEmail() + ";" + this.getUserId() + ";"
				+ this.getPassword() + ";" + this.getCity() + ";" + this.getDistrict() + ";" + this.getLatitude() + ";"
				+ this.getLongitude();
	}

	/**
	 * Delete user from ArrayList and update file .csv
	 */
	public void deleteUser() {
		users.remove(this);
		JOptionPane.showMessageDialog(null, "L'utente " + this.getUserId() + " è stato cancellato.",
				"Utente cancellato", JOptionPane.INFORMATION_MESSAGE);
		FileManager.writeUsersFile();
	}

	/**
	 * Modify <code>User</code> informations with the informations in input and
	 * update file .csv
	 * 
	 * @param name
	 *            is the user name
	 * @param surname
	 *            is the user surname
	 * @param email
	 *            is the user email
	 * @param userId
	 *            is the user Id
	 * @param city
	 *            is the user city
	 * @param district
	 *            is the user district
	 * @param latitude
	 *            is the user latitude
	 * @param longitude
	 *            is the user longitude
	 */
	public void modifyUser(String name, String surname, String userId, String email, double latitude, double longitude,
			District district, City city) {
		this.setName(name);
		this.setSurname(surname);
		this.setUserId(userId);
		this.setEmail(email);
		this.setLatitude(latitude);
		this.setLongitude(longitude);
		this.setDistrict(district);
		this.setCity(city);
		FileManager.writeUsersFile();
	}

	/**
	 * Modify user password with the String in input and update file .csv
	 * 
	 * @param password
	 *            is the password to set
	 */
	public void modifyPassword(String password) {
		this.setPassword(encryptPassword(password));
		FileManager.writeUsersFile();
	}

	/**
	 * Verify if the password in input is equals to the user password
	 * 
	 * @param passwordEntered
	 *            is the char array that contains the entered password
	 * @return true if passwords are equal, false otherwise
	 */
	public boolean verifyPassword(char[] passwordEntered) {
		return this.password.equals(encryptPassword(new String(passwordEntered)));
	}

	/**
	 * Create user ArrayList
	 */
	public static void createUsersList() {
		users = new ArrayList<User>();
	}

	/**
	 * Create and add to the ArrayList a <code>User</code> with the informations
	 * in the file row in input
	 * 
	 * @param fileRow
	 *            is the file .csv row
	 */
	public static void loadUser(String fileRow) {
		users.add(new User(fileRow));
	}

	/**
	 * Verify if exists a <code>User</code> with user Id and password equal to
	 * the values in input
	 * 
	 * @param userIdEntered
	 *            is the user Id
	 * @param password
	 *            is the char array that contains the entered password
	 * @return true if exists a <code>User</code> with user Id and password
	 *         equal to the values in input, false otherwise
	 */
	public static boolean verifyData(String userIdEntered, char[] password) {
		for (User tmp : users) {
			if (tmp.getUserId().equals(userIdEntered)
					&& tmp.getPassword().equals(encryptPassword(new String(password)))) {
				userLogged = tmp;
				return true;
			}
		}
		return false;
	}

	/**
	 * Create and add to the ArrayList a new <code>User</code> with the
	 * informations in input, and update file .csv and user logged
	 * 
	 * @param name
	 *            is the user name
	 * @param surname
	 *            is the user surname
	 * @param email
	 *            is the user email
	 * @param userId
	 *            is the user Id
	 * @param password
	 *            is the user password
	 * @param city
	 *            is the user city
	 * @param district
	 *            is the user district
	 * @param latitude
	 *            is the user latitude
	 * @param longitude
	 *            longitude
	 */
	public static void newUserRegistration(String name, String surname, String userId, String email, String password,
			City city, District district, double latitude, double longitude) {
		User newUser = new User(name, surname, userId, email, encryptPassword(password), city, district,
				Utility.round(latitude), Utility.round(longitude));
		users.add(newUser);
		FileManager.writeUsersFile();
		userLogged = newUser;
	}

	// method to encrypt a String representing the user password

	private static String encryptPassword(String password) {
		try {
			MessageDigest m;
			m = MessageDigest.getInstance("MD5");
			m.update(password.getBytes());
			return String.format("%032x", new BigInteger(1, m.digest()));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}
}