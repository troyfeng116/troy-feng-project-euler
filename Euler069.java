/* -------- UNSOLVED -------- */

/* Euler's Totient function, phi(n) [sometimes called the phi function], is used to determine the number of
 * numbers less than n which are relatively prime to n. For example, as 1,2,4,5,7, and 8, are all less than 
 * nine and relatively prime to nine, phi(9) = 6.
 *
 *		n 		Coprime 		phi(n)	n/phi(n)
 *		2		1				1		2
 *		3		1,2				2		1.5
 *		4		1,3				2		2
 *		5		1,2,3,4			4		1.25
 *		6		1,5				2		3
 *		7		1,2,3,4,5,6		6		1.1666...
 *		8		1,3,5,7			4		2
 *		9		1,2,4,5,7,8		6		1.5
 *		10		1,3,7,9			4		2.5
 *
 * It can be seen that n=6 produces a maximum n/phi(n) for n < 10. Find the value of n<N for which phi(n) is
 * maximum. In case of multiple answers, print the minimum. 
 *
 * INPUTS: 1 <= T <= 1000, 3 <= N <= 10^18 */

import java.util.Scanner;

public class Euler069 {
	
	/* Thoughts/approach: */
	
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int t = Integer.parseInt(s.nextLine());
		for (int t0 = 0; t0 < t; t0++) {
			
		}
		s.close();
	}
}
