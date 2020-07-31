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
import java.util.List;
import java.util.ArrayList;

public class Euler071 {
	
	/* Thoughts/approach: Ordering the rationals is a pretty interesting problem. See the following:
	 *
	 * 	https://en.wikipedia.org/wiki/Stern%E2%80%93Brocot_tree
	 *	https://en.wikipedia.org/wiki/Farey_sequence
	 *
	 * Of use to us in particular are the following facts:
	 * 1. All rationals <= 1 can be written as finite continued fractions [0;a_1,...,a_n]
	 * 2. Rational -> continued frac is bijective if we require a_n!=1 since [a_1,...,1] = [a1,...,a_{n-1}+1]
	 * 3. If p/q = [0;a_1,...,a_n + 1], then Farey neighbors are [0;a_1,...,a_n] and [0;a_1,...,a_{n-1}] */

	/* If a/b = [0;a_1,...,a_n], return those continued fraction coefficients [a_1,...,a_n]. */
	public static List<Integer> getContinuedFrac(int a, int b) {
		List<Integer> ans = new ArrayList<Integer>();
		/* Continue a/b. */
		while (a != 1) {
			int x = b/a;
			ans.add(x);
			b -= x*a;
			int temp = a;
			a = b;
			b = temp;
		}
		ans.add(b);
		return ans;
	}

	/* Given [0;a_1,...,a_n], restore rational p/q using recurrence relation for continued fractions:
	 * p_k = a_k * (p_{k-1}) + p_{k-2}, where p_0 = a_0 and p_1 = a_0*a_1+1.
	 * q_k = a_k * (q_{k-1}) + q_{k-2}, where q_0 = 1 and q_1 = a_1. */
	public static int[] undoContinuedFrac(List<Integer> frac) {
		int p2 = 0;
		int q2 = 1;
		int p1 = 1;
		int q1 = frac.get(0);
		int p = p1;
		int q = q1;
		for (int n = 1; n < frac.size(); n++) {
			p = frac.get(n)*p1+p2;
			q = frac.get(n)*q1+q2;
			p2 = p1;
			q2 = q1;
			p1 = p;
			q1 = q;
		}
		return new int[] {p,q};
	}
	
	public static void main(String[] args) {
		List<Integer> list = getContinuedFrac(17,61);
		for (int i: list) System.out.println(i);
		int[] test = undoContinuedFrac(list);
		System.out.println(test[0] + " " + test[1]);
		System.exit(0);
		Scanner s = new Scanner(System.in);
		int t = Integer.parseInt(s.nextLine());
		for (int t0 = 0; t0 < t; t0++) {
			String[] inputs = s.nextLine().split(" ");
			int a = Integer.parseInt(inputs[0]);
			int b = Integer.parseInt(inputs[1]);
			long N = Long.parseLong(inputs[2]);
		}
		s.close();
	}
}
