/* -------- UNSOLVED -------- */

/* Each character on a computer is assigned a unique code and the preferred standard is ASCII (American
 * Standard Code for Information Interchange). For example, uppercase A = 65, asterisk = 42, and 
 * lowercase k = 107.
 *
 * A modern encryption method is to take a text file, convert the bytes to ASCII, then XOR each byte with
 * a given value, taken from a secret key. The advantage with the XOR function is that using the same 
 * encryption key on the cipher text, restores the plain text; for example, 65 XOR 42 = 107, then 
 * 107 XOR 42 = 65.
 *
 * For unbreakable encryption, the key is the same length as the plain text message, and the key is made 
 * up of random bytes. The user would keep the encrypted message and the encryption key in different 
 * locations, and without both "halves", it is impossible to decrypt the message.
 *
 * Unfortunately, this method is impractical for most users, so the modified method is to use a password 
 * as a key. If the password is shorter than the message, which is likely, the key is repeated cyclically 
 * throughout the message. The balance for this method is using a sufficiently long password key for 
 * security, but short enough to be memorable.
 *
 * Your task has been made easy, as the encryption key consists of three lower case characters (a-z). 
 * Using the knowledge that the plain text must contain common English characters (a-z, A-Z, 0 - 9), 
 * brackets (), common symbols (;:,.'?-!) and spaces decrypt the message and find the key that is used to 
 * encrypt the message.
 *
 * Note: it is guaranteed that key is unique, is of size 3 and contains lower case english characters 
 * (a-z). 
 *
 * INPUTS: First line contains 80 <= N <= 1500, followed by line of  N space-separated integers denoting 
 * encoded ASCII values. */

import java.util.Scanner;

public class Euler059 {
	
	/* Thoughts/approach: */
	
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int n = Integer.parseInt(s.nextLine());
		for (int t0 = 0; t0 < t; t0++) {
			
		}
		s.close();
	}
}
