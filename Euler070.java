/* -------- UNSOLVED -------- */

/* Euler's Totient function, phi(n) [sometimes called the phi function], is used to determine the number of 
 * positive numbers less than or equal to n which are relatively prime to n. For example, as 1,2,4,5,7, and 8
 *  are all less than nine and relatively prime to nine, phi(9) = 6.
 *
 * The number 1 is considered to be relatively prime to every positive number, so phi(1)=1. Interestingly, 
 * phi(87109) = 79180, and it can be seen that 87109 is a permutation of 79180.
 *
 * Find the value of n, where 1 < n < N, for which phi(n) is a permutation of n and the ratio phi(n)/n 
 * produces a minimum.
 *
 * INPUTS: 100 <= N <= 10^7 */

import java.util.Scanner;

public class Euler070 {
	
	/* Thoughts/approach: */
	
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int N = Integer.parseInt(s.nextLine());
		
		s.close();
	}
}
