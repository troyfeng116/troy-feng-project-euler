/* -------- UNSOLVED -------- */

/* The 5-digit number, 16807 = 7^5, is also a fifth power. Similarly, the 9-digit number, 134217728 = 8^9, 
 * is a ninth power.
 *
 * Given N, print the N-digit positive integers which are also an Nth power. 
 *
 * INPUTS: 1 <= N <= 19
 * OUTPUT each N-digit integer on new line in increasing order. */

import java.util.Scanner;

public class Euler063 {
	
	/* Thoughts/approach: simple loop over N-th powers within N-digit range should do. Even better, we won't
	 * have issues with long overflow, since Long.MAX_VALUE is quite close to 10^19 and 9^19 << 10^19. */
	
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int n = Integer.parseInt(s.nextLine());
		
		s.close();
	}
}
