/* -------- SOLVED -------- */

/* Starting with the number 1 and moving to the right in a clockwise direction a 5 by 5 spiral is 
 * formed as follows:
 * 
 *		21 22 23 24 25
 *		20  7  8  9 10
 *		19  6  1  2 11
 *		18  5  4  3 12
 *		17 16 15 14 13
 *
 * It can be verified that the sum of the numbers on the diagonals is 101.
 * 
 * What is the sum of the numbers on the diagonals in a N x N, (N is odd) spiral formed in the same 
 * way? As the sum will be huge you have to print the result mod (10^9 + 7). 
 * 
 * INPUTS: 1 <= T <= 10^5, 1 <= N < 10^18, N odd. */

import java.util.Scanner;

public class Euler28 {
	
	/* Thoughts/approach: Definitely DP. Might be closed form?
	 * 
	 * After playing around, it turns out the top-left index of each NxN square is N^2 (working it out
	 * adds to something like 4N^2 - 4N + 1 which is cool). Then, the three other corners are evenly
	 * spaced by N-1.
	 * 
	 * If we write N as 2k-1, we get a nice closed form that works any N'th odd except 1 (k=1).
	 * 
	 * For any N=2k-1 and k!=1, the sum of the corners of the NxN square is 16k^2 - 28k + 16.
	 * 
	 * So we're looking for SUM_{1->k} (16k^2 - 28k + 16), for which we can write closed-form expression
	 * using sum of squares and Gauss's. We have to watch out for long overflow, and remember that the
	 * expression doesn't hold for k=1, so we need to subtract 16, add 28, subtract 16, and then add 1 at
	 * the end. */
	
	final static long MOD = 1000000007;
	
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int t = Integer.parseInt(s.nextLine());
		for (int t0 = 0; t0 < t; t0++) {
			long n = Long.parseLong(s.nextLine());
			long k = (n+1)/2;
			long ans = 0;
			
			/* Since MOD^2 is on the same bit order as Long.MAX_VALUE, we need to prevent long overflow at
			 * literally every step. (BigInteger would've made this so much cleaner...) */
			
			/* 16 * (k(k+1)(2k+1)) / 6 term. */
			if (k % 3 == 0) {
				ans += (8 * ((((k/3) % MOD) * ((((k+1) % MOD) * ((2*k+1) % MOD)) % MOD)) % MOD)) % MOD;
			}
			else if ((k+1) % 3 == 0) {
				ans += (8 * (((((k+1)/3) % MOD) * (((k % MOD) * ((2*k+1) % MOD)) % MOD)) % MOD)) % MOD;
			}
			else ans += (8 * (((((2*k+1)/3) % MOD) * ((((k+1) % MOD) * (k % MOD)) % MOD)) % MOD)) % MOD;
			
			/* - (28 * k * (k+1) / 2) term */
			ans -= (14 * (((k % MOD) * ((k+1) % MOD)) % MOD)) % MOD;
			if (ans < 0) ans += MOD;
			else ans = ans % MOD;
			
			/* 16k term. */
			ans += ((k % MOD) * 16) % MOD;

			/* Adjust, since formula doesn't hold for k=1. So subtract 16, add 28, subtract 16. Then add 1 for
			 * k=1. */
			ans -= 3;
			if (ans < 0) ans += MOD;
			
			System.out.println(ans % MOD);
		}
		s.close();
	}
}