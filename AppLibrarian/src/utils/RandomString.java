package utils;
import java.util.Random;

/**
 * This class provides methods to create random String
 *
 * @author Marco Di Capua
 * @author Mattia Lo Schiavo
 * @author Filippo Pelosi
 * @author Riccardo Zorzi
 * @version 1.0
 */
public class RandomString {
	private static final String[] CHARACTERS = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N",
			"O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "a", "b", "c", "d", "e", "f", "g", "h", "i",
			"j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3",
			"4", "5", "6", "7", "8", "9" };

	/**
	 * Create a random String with all the alphabet upper and lower case character.
	 * The string length is 8 characters.
	 * 
	 * @return the random String
	 */
	public static String generate() {
		String generetedString = "";
		Random random = new Random();
		for (int i = 0; i < 8; i++) {
			generetedString = generetedString + CHARACTERS[random.nextInt(CHARACTERS.length)];
		}
		return generetedString;
	}
}
