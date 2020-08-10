/* -------- UNSOLVED -------- */

/* Consider the fraction, n/d, where n and d are positive integers. If n < d and GCD(n,d) = 1, it is called a 
 * reduced proper fraction.
 *
 * If we list the set of reduced proper fractions for d <= 8 in ascending order of size, we get:
 *
 * 1/8, 1/7, 1/6, 1/5, 1/4, 2/7, 1/3, 3/8, 2/5, 3/7, 1/2, 4/7, 3/5, 5/8, 2/3, 5/7, 3/4, 4/5, 5/6, 6/7, 7/8
 *
 * It can be seen that there are 3 fractions between 1/3 and 1/2.
 *
 * How many fractions lie between 1/(A+1) and 1/A in the sorted set of reduced proper fractions with denominator
 * less than or equal to D?
 *
 * INPUTS: 1 < D < 2x10^6, 1 < A <= 100, space separated A D */

import java.util.Scanner;

public class Euler073 {
	
	/* Thoughts/approach: take mediants until denominator exceeds D? */

	/* Given that n1/d1 < n2/d2 and are Farey neighbors, return count of rationals between them. */
	public static int search(int n1, int d1, int n2, int d2, int D) {
		if (d1+d2 > D) return 0;
		System.out.println((n1+n2) + "/" + (d1+d2));
		return 1 + search(n1,d1,n1+n2,d1+d2,D) + search(n1+n2,d1+d2,n2,d2,D);
	}
	
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		String[] inputs = s.nextLine().split(" ");
		int A = Integer.parseInt(inputs[0]);
		int D = Integer.parseInt(inputs[1]);
		System.out.println(search(1, A+1, 1, A, D));
		s.close();
	}
}
