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
	 * Let x = n/d. How do we find rank(x) in F_D? Let q <= D, and let S_q denote the set of all Farey fractions 
	 * with denominator q <= D. There are floor(x*q) TOTAL possibly unreduced fractions with denominator q that
	 * are less than x. Out of those, we don't want to include non-reduced fractions: that is, we don't want
	 * to include SUM_{t<q, t|q} S_t. Thus, S_q = floor(x*q) - SUM_{t<q, t|q} S_t, where floor(x*q) is the count
	 * of all (including unreduced) fractions <= x and SUM{t<q,t|q} S_t is the count of unreduced fractions.
	 * Finally, rank(x) = SUM_{q:1->D} S_q. */

	/* Given n,d,D, return S such that for k<=D, S[k] holds the number of reduced fractions with denominator
	 * k that are <= n/d. In other words, return S_k from the description above. We do this using a sieve-like
	 * approach to deal with the SUM{t<q,t|q} S_t expression. Since S_q = floor(x*q) - SUM_{t<q,t|q} S_t, we
	 * initialize ans[q] to be floor(x*q). And for all of its factors t|q, we subtract ans[t] = S_t from
	 * ans[q] = S_q, using a bottom-up Sieve/DP-like algorithm. */
	public static int[] findCountLess(int n, int d, int D) {
		int[] ans = new int[D+1];
		for (int q = 1; q <= D; q++) {
			ans[q] = (int) ((double)n/d*q);
		}
		for (int t = 2; t <= D; t++) {
			for (int q = 2*t; q <= D; q+=t) {
				ans[q] -= ans[t];
			}
		}
		return ans;
	}

	/* Find rank(n/d) in F_D. */
	public static int rank(int n, int d, int D) {
		int total = (int) ((double)n/d*D);
		int[] S = findCountLess(n,d,D);
		int ans = 0;
		for (int i: S) ans += i;
		return ans;
	}
	
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		String[] inputs = s.nextLine().split(" ");
		int A = Integer.parseInt(inputs[0]);
		int D = Integer.parseInt(inputs[1]);
		System.out.println(rank(1,A,D) - rank(1,A+1,D) - 1);
		s.close();
	}
}
