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
	
	/* Thoughts/approach: we can implement a quick brute force solution that loops over y until 1+Dy^2 is
	 * a perfect square. */

	public static long isPerfectSquare(long x) {
		long s = (long) Math.sqrt(x);
		return s*s==x ? s : -1;
	}

	public static long minimalSolution(long d) {
		long y = 1;
		long x = 0;
		while ((x=isPerfectSquare(1 + d*y*y)) < 0) y++;
		return x;
	}
	
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int N = Integer.parseInt(s.nextLine());
		long max = 0;
		long ans = 0;
		for (int d = 2; d <= N; d++) {
			if (isPerfectSquare(d) > 0) continue;
			long res = minimalSolution(d);
			if (res > max) {
				max = res;
				ans = d;
			}
		}
		System.out.println(ans);
		s.close();
	}
}
