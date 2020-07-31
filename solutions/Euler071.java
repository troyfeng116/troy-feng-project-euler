/* -------- SOLVED -------- */

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
	 * 3. If p/q = [0;a_1,...,a_n + 1], then Farey neighbors are [0;a_1,...,a_n] and [0;a_1,...,a_{n-1}]
	 *
	 * EDIT: the above only applies if p/q is in a Farey sequence of order q. Another useful tool is that
	 * if a/b has neighbors x/y and w/z, then a/b is the mediant of x/y and w/z. That is, a/b = (x+w)/(y+z).
	 * So if we keep finding mediants and kinda binary searching as we go until y or z exceeds N, I think
	 * we'll end up with the L and R Farey neighbors of a/b.
	 *
	 * At any step, we have neighbors x/y and w/z, such that x/y < p/q < w/z. Then the next term between
	 * x/y and w/z is (x+w)/(y+z). If p/q is less than mediant, search between x/y and mediant. Else if
	 * greater, search between mediant and w/z. Else if equal, take mediant between x/y and w/z until
	 * denominator exceeds N. Once y or z exceeds N, we stop. */

	/* Given a/b and N, find left neighbor of a/b in Farey sequence of order N. */
	public static long[] findLeftNeighbor(int a, int b, long N) {
		/* Start with 0/1 and 1/1 as L and R neighbors. */
		long x = 0;
		long y = 1;
		long w = 1;
		long z = 1;
		/* While denominator of next mediant is less than N */
		while (y+z <= N) {
			/* Mediant is p/q = (x+w)/(y+z) */
			long p = x+w;
			long q = y+z;
			/* If x/y < a/b < p/q, search with L = x/y and R = p/q */
			if (a*q < p*b) {
				w = p;
				z = q;
			}
			/* If p/q < a/b < w/z, search with L = p/q and R = w/z */
			else if (a*q > p*b) {
				x = p;
				y = q;
			}
			/* If mediant p/q = a/b, keep taking mediant of x/y and a/b until y is about to exceed N. */
			else {
				long factor = (N-y)/b;
				x+=factor*a;
				y+=factor*b;
				return new long[] {x,y};
			}
		}
		return new long[] {x,y};
	}

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int t = Integer.parseInt(s.nextLine());
		for (int t0 = 0; t0 < t; t0++) {
			String[] inputs = s.nextLine().split(" ");
			int a = Integer.parseInt(inputs[0]);
			int b = Integer.parseInt(inputs[1]);
			long N = Long.parseLong(inputs[2]);
			long[] res = findLeftNeighbor(a,b,N);
			System.out.println(res[0] + " " + res[1]);
		}
		s.close();
	}
}
