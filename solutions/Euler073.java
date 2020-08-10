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
	
	/* Thoughts/approach: Initial approach was to take mediants until denominator exceeds D. This throws a
	 * stack overflow error for small A and large D.
	 *
	 * It would be helpful if we knew the number of terms of F_D less than 1/(A+1) and the number of terms
	 * less than 1/A. In Farey sequences, the number of irreducible fractions p/q <= x where q <= D is called
	 * rank(x). Our final answer should be rank(1/A) - rank(1/(A+1)) - 1.
	 *
	 * Let x = n/d. How do we find rank(x)? Let q <= d, and let S_q denote the set of all Farey fractions 
	 * with denominator q <= d. There are floor(x*q) TOTAL possibly unreduced fractions with denominator q that
	 * are less than x. Out of those, we don't want to include non-reduced fractions: that is, we don't want
	 * to include SUM_{t<q, t|q} S_t. Thus, S_q = floor(x*q) - SUM_{t<q, t|q} S_t, where floor(x*q) is the count
	 * of all (including unreduced) fractions <= x and SUM{t<q,t|q} S_t is the count of unreduced fractions.
	 * Finally, rank(x) = SUM_{q:1->d} S_q. */

	/* Given that n1/d1 < n2/d2 and are Farey neighbors, return count of rationals between them. */
	public static int search(int n1, int d1, int n2, int d2, int D) {
		if (d1+d2 > D) return 0;
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
