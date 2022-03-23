package cwang_hw2;

import java.util.Random;	// Needed for Random object

/**
 * The CaesarCipher class implements Encryptor class and encrypts or decrypts
 * the input password and then return it.
 */
public class CaesarCipher implements Encryptor {

    private int shift;
    private static final int OFFSET_MIN = 32;
    private static final int OFFSET_MAX = 126;

	/**
	 * Constructor.
	 */
	public CaesarCipher() {
		shift = getShift();
	}

	/**
	 * The encrypt function returns the encrypted password.
	 *
	 * @param s The string to encrypt.
	 * @return the encrypted password
	 */
	@Override
	public String encrypt(String s) {
		return encryptDecrypt(s, true);
	}

	/**
	 * The encrypt function returns the decrypted password.
	 *
	 * @param s The string to encrypt.
	 * @return the decrypted password
	 */
	@Override
	public String decrypt(String s) {
		return encryptDecrypt(s, false);
	}

	/**
	 * Create and return the shift number.
	 *
	 * @return the shift number
	 */
	private static int getShift() {
		Random r = new Random();
		int low = 1;
		int high = OFFSET_MAX - OFFSET_MIN;
		return r.nextInt(high - low) + low;
	}

	/**
	 * The encryptDecrypt function encrypts or decrypts input password and
	 * return it.
	 *
	 * @param s input password.
	 * @param encrypt needed to encrypt or decrypt.
	 * @return The encrypted or decrypted password
	 * @throws IllegalArgumentException The argument is not legal
	 */
	private String encryptDecrypt(String s, boolean encrypt)
			throws IllegalArgumentException {
		StringBuilder sb = new StringBuilder();

		// Convert String to char
		for (char c : s.toCharArray()) {
			int indx = c, cpos;
			if (!isPositionInRange(indx))
			      throw new IllegalArgumentException("String to be encrypted " +
						  "has unrecognized character " + c);

			if (encrypt) {
				cpos = indx + shift;
				if (cpos > OFFSET_MAX)
					cpos = OFFSET_MIN + (cpos - OFFSET_MAX);
			} else {
				cpos = indx - shift;
				if (cpos < OFFSET_MIN)
					cpos = OFFSET_MAX - (OFFSET_MIN - cpos);	
			}
			sb.append((char)cpos);
		}
		return sb.toString();		
	}

	/**
	 * Return if indx is in the range.
	 *
	 * @param indx digital.
	 * @return if indx is in the range
	 */
	private boolean isPositionInRange(int indx) {
		return indx >= OFFSET_MIN && indx <= OFFSET_MAX;
	}
}
