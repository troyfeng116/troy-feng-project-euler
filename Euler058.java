/* -------- UNSOLVED -------- */

/* Starting with 1 and spiralling anticlockwise in the following way, a square spiral with side length 7 is 
 * formed.
 *
 *		37 36 35 34 33 32 31
 *		38 17 16 15 14 13 30
 *		39 18  5  4  3 12 29
 *		40 19  6  1  2 11 28
 *		41 20  7  8  9 10 27
 *		42 21 22 23 24 25 26
 *		43 44 45 46 47 48 49
 *
 * It is interesting to note that the odd squares lie along the bottom right diagonal, but what is more 
 * interesting is that 8 out of the 13 numbers lying along both diagonals are prime; that is, a ratio of 
 * 8/13 ~ 62%.
 *
 * If one complete new layer is wrapped around the spiral above, a square spiral with side length 9 will 
 * be formed. If this process is continued, what is the side length of the square spiral for which the 
 * ratio of primes along both diagonals first falls below N%? 
 *
 * INPUTS: 8 <= N <= 60 */

import java.util.Scanner;

public class Euler058 {
	
	/* Thoughts/approach: I remember there was another Project Euler question involving the sums of numbers
	 * along the diagonals. It seems that we might have to loop pretty far, and a simple sieve won't do;
	 * chances are we'll have to implement a fast primality test like Miller-Rabin.
	 *
	 * For any (odd) side length n, we can find the four numbers on the corners of the n x n square pretty
	 * quickly (bottom left is n^2). We then have to check the primality of each of those four corners.
	 * Trivially, the bottom left corner can't be prime. */
	
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int n = Integer.parseInt(s.nextLine());
		
		s.close();
	}
}
