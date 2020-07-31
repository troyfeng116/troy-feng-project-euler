/* -------- UNSOLVED -------- */

/* Consider the fraction, n/d, where n and d are positive integers. If n < d and GCD(n,d) = 1, it is called a 
 * reduced proper fraction.
 *
 * If we list the set of reduced proper fractions for d <= 8 in ascending order of size, we get:
 *
 * 1/8, 1/7, 1/6, 1/5, 1/4, 2/7, 1/3, 3/8, 2/5, 3/7, 1/2, 4/7, 3/5, 5/8, 2/3, 5/7, 3/4, 4/5, 5/6, 6/7, 7/8
 *
 * It can be seen that there are 21 elements in this set.
 *
 * How many elements would be contained in the set of reduced proper fractions for d <= N?
 *
 * INPUTS: 1 <= T <= 100000, 2 <= N <= 10^6 */

import java.util.Scanner;

public class Euler072 {
	
	/* Thoughts/approach: For each d >= 2, there are phi(d) rational numbers with d in the denominator, where
	 * phi is Euler's totient function. We can efficiently precompute phi(d) up to 10^6 using a modified
	 * prime sieve, since phi(n) = n * PROD_{p|n} (1 - 1/p). */

	static final int MAX_N;
	
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int t = Integer.parseInt(s.nextLine());
		for (int t0 = 0; t0 < t; t0++) {
			int n = Integer.parseInt(s.nextLine());
		}
		s.close();
	}
}
