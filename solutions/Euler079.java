/* -------- UNSOLVED -------- */

/* A common security method used for online banking is to ask the user for three random characters from 
 * a passcode. For example, if the passcode was 531278, they may ask for the 2nd, 3rd, and 5th characters; 
 * the expected reply would be: 317.
 *
 * Given that the three characters are always asked for in order, analyse the file so as to determine the 
 * shortest possible secret passcode of unknown length.
 *
 * Assume all characters in your password are different. You're given T successful login attempts each 
 * containing 3 characters with ASCII codes from 33 to 126 both inclusive. You need to recover the 
 * shortest original password possible. If there are multiple choices, select the lexicographically 
 * smallest one.
 *
 * If something went wrong and the original password cannot be recovered, output SMTH WRONG.
 *
 * INPUTS: 1 <= T <= 3000 */

import java.util.Scanner;

public class Euler079 {
	
	/* Thoughts/approach: */
	
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int t = Integer.parseInt(s.nextLine());
		for (int t0 = 0; t0 < t; t0++) {
			
		}
		s.close();
	}
}
