/* -------- UNSOLVED -------- */

/* Consider the fraction, a/b, where a and b are positive integers. If a < b and GCD(a,b) = 1, it is called a 
 * reduced proper fraction.
 *
 * If we list the set of reduced proper fractions for d <= 8, (where d is the denominator) in ascending order 
 * of size, we get:
 *
 * 1/8, 1/7, 1/6, 1/5, 1/4, 2/7, 1/3, 3/8, 2/5, 3/7, 1/2, 4/7, 3/5, 5/8, 2/3, 5/7, 3/4, 4/5, 5/6, 6/7, 7/8
 *
 * It can be seen that 2/5 is the fraction immediately to the left of 3/7.
 *
 * By listing the set of reduced proper fractions for d <= N in ascending order of size, find the numerator 
 * and denominator of the fraction immediately to the left of a/b. 
 *
 * INPUTS: 1 <= T <= 100, each line contains space separated a b N, 1 <= a < b <= 10^9, GCD(a,b) = 1, 
 * b < N <= 10^15 */

import java.util.Scanner;

public class Euler071 {
	
	/* Thoughts/approach: */
	
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int t = Integer.parseInt(s.nextLine());
		for (int t0 = 0; t0 < t; t0++) {
			
		}
		s.close();
	}
}
