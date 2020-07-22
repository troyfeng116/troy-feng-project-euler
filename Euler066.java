/* -------- UNSOLVED -------- */

/* Consider quadratic Diophantine equations of the form:
 *
 *		x^2 - Dy^2 = 1
 *
 * For example, when D=13, the minimal solution in x is 649^2 - 13*180^2 = 1. It can be assumed that there 
 * are no solutions in positive integers when D is square.
 *
 * By finding minimal solutions in x for D=2,3,5,6,7, we obtain the following:
 *
 *		3^2 - 2*2^2 = 1
 *		7^2 - 3*4^2 = 1
 *		9^2 - 5*4^2 = 1
 *		5^2 - 6*2^2 = 1
 *		3^2 - 7*2^2 = 1
 *
 * Hence, by considering minimal solutions in x for D<=7, the largest x is obtained when D=5.
 *
 * Find the value of D<=N in minimal solutions of x for which the largest value of x is obtained.
 *
 * INPUTS: 7 <= D <= 10^4 */

import java.util.Scanner;

public class Euler066 {
	
	/* Thoughts/approach: */
	
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int t = Integer.parseInt(s.nextLine());
		for (int t0 = 0; t0 < t; t0++) {
			
		}
		s.close();
	}
}
