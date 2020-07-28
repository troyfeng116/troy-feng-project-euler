/* -------- SOLVED -------- */

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
 * Using the knowledge that the plain text must contain common English characters (a-z, A-Z, 0-9), 
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
	
	/* Thoughts/approach: First some ASCII values:
	 *
	 *		SPACE 32
	 * 		! 33
	 *		' 39
	 *		() 40/41
	 *		, 44
	 *		- 45
	 * 		. 46
	 *		: 58
	 *		; 59
	 *		? 63
	 *		0-9 48-57
	 *		A-Z 65-90
	 *		a-z 97-122
	 *
	 * So we have a 3-character key. The first character decrypts the 1st, 4th, 7th, ... chars in the 
	 * message. The second decrypts the 2nd, 5th, 8th, ... And the third decrypts the 3rd, 6th, 9th, ...
	 * Each of the three chars in the key must be [a-z], or 97-122. Let k be the 1st char in the key, so
	 * 97 <= k <= 122. We are given that there is a unique key, so there must be one k:[97,122] such that
	 * k XOR 1st, 4th, 7th,... all fall within range of one of the allowed characters. */

	/* isLegal[k] is true iff k is a legal ascii value for a decrypted message. */
	static boolean[] isLegal;

	/* Set the legal chars. */
	public static void fillLegalChars() {
		isLegal = new boolean[256];
		isLegal[32] = true;
		isLegal[33] = true;
		isLegal[39] = true;
		isLegal[40] = true;
		isLegal[41] = true;
		isLegal[44] = true;
		isLegal[45] = true;
		isLegal[46] = true;
		isLegal[58] = true;
		isLegal[59] = true;
		isLegal[63] = true;
		for (int i = 48; i <= 57; i++) isLegal[i] = true;
		for (int i = 65; i <= 90; i++) isLegal[i] = true;
		for (int i = 97; i <= 122; i++) isLegal[i] = true;
	}

	/* Return 3-char string corresponding to unique key that can decrypt input values. */
	public static String solution(int[] input) {
		String ans = "";
		for (int start = 0; start < 3; start++) {
			int c = 97;
			while (c <= 122) {
				boolean success = true;
				for (int i = start; i < input.length; i+=3) {
					int decrypted = c^input[i];
					if (!isLegal[decrypted]) {
						success = false;
						break;
					}
				}
				if (success) break;
				c++;
			}
			ans += (char) c;
		}
		return ans;
	}
	
	public static void main(String[] args) {
		fillLegalChars();
		Scanner s = new Scanner(System.in);
		int n = Integer.parseInt(s.nextLine());
		String[] inputs = s.nextLine().split(" ");
		int[] asciiInputs = new int[n];
		for (int i = 0; i < n; i++) {
			asciiInputs[i] = Integer.parseInt(inputs[i]);
		}
		System.out.println(solution(asciiInputs));
		s.close();
	}
}
