/* -------- UNSOLVED -------- */

/* The number 145 is well known for the property that the sum of the factorial of its digits is equal 
 * to 145:
 *
 * 1! + 4! + 5! = 1 + 24 + 120 = 145
 *
 * Perhaps less well known is 169, in that it produces the longest chain of numbers that link back to 
 * 169; it turns out that there are only three such loops that exist:
 *
 * 169 -> 363601 -> 1454 -> 169
 * 871 -> 45361 -> 871
 * 872 -> 45362 -> 872
 *
 * It is not difficult to prove that EVERY starting number will eventually get stuck in a loop. For 
 * example,
 *
 * 69 -> 363600 -> 1454 -> 169 -> 363601 (-> 1454)
 * 78 -> 45360 -> 871 -> 45361 (-> 871)
 * 540 -> 145 (-> 145)
 *
 * Starting with 69 produces a chain of five non-repeating terms, but the longest non-repeating chain 
 * with a starting number below one million is sixty terms.
 *
 * For a given length L and limit N print all the integers <= N which have chain length L.
 *
 * INPUTS: 1 <= T <= 10, 10 <= N <= 1000000, 1 <= L <= 60, space separated N L
 * OUTPUT: Print the integers separated by space for each testcase. Where there are no such number for 
 * a given L, print -1. */

import java.util.Scanner;

public class Euler074 {
	
	/* Thoughts/approach: definitely memoize the factorials. It seems that memoizing chain lengths 
	 * might be rather useful as well. Perhaps precalculate all chain lengths up to MAX_N, and use
	 * recurring chains whenever possible? */

	/* factorial[k] = k!, where k <= 9. */
	static int[] factorial;

	public static void fillFactorial() {
		factorial = new int[10];
		factorial[0] = 1;
		for (int i = 1; i <= 9; i++) {
			factorial[i] = factorial[i-1]*i;
		}
	}
	
	public static void main(String[] args) {
		fillFactorial();
		Scanner s = new Scanner(System.in);
		int t = Integer.parseInt(s.nextLine());
		for (int t0 = 0; t0 < t; t0++) {
			String[] inputs = s.nextLine().split(" ");
			int N = Integer.parseInt(inputs[0]);
			int L = Integer.parseInt(inputs[1]);
		}
		s.close();
	}
}
