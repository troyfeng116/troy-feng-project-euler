/* -------- SOLVED -------- */

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
import java.util.List;
import java.util.ArrayList;

public class Euler074 {
	
	/* Thoughts/approach: definitely memoize the factorials. It seems that memoizing chain lengths 
	 * might be rather useful as well. Perhaps precalculate all chain lengths up to MAX_N, and use
	 * recurring chains whenever possible? 
	 *
	 * We are told that there are only 4 numbers that loop back to themselves: 145, 169, 871, and 872
	 * (which is kinda cool). This means that every number will eventually enter one of those four
	 * number's loops. For example, 69 enters 169's loop at 1454. Not only that, but we know that
	 * 363600 also enters 169's loop at 1454. So for each starting number, we keep finding its digit
	 * factorial sum until we either i) enter a 145/169/871/872 loop or ii) hit a chain we have already
	 * previously found. Moreover, while we haven't encountered i) or ii), we can increment all the new
	 * chains we find on the way. Once we hit i) or ii), we add the length of that found chain to all
	 * the new chains. */

	static final int MAX_N = 1000000;
	/* factorial[k] = k!, where k <= 9. */
	static int[] factorial;
	/* chain[k] returns the length of the digit factorial chain of k. */
	static int[] chain;

	public static void fillFactorial() {
		factorial = new int[10];
		factorial[0] = 1;
		for (int i = 1; i <= 9; i++) {
			factorial[i] = factorial[i-1]*i;
		}
	}

	/* Return sum of factorials of digits of n. */
	public static int digitFactorialSum(int n) {
		int ans = 0;
		while (n != 0) {
			ans += factorial[n%10];
			n /= 10;
		}
		return ans;
	}

	/* Fill chain[] with lengths of chains of all k <= MAX_N. */
	public static void computeChainLengths() {
		/* Largest possible factorial digit sum is for 999999. */
		chain = new int[6*factorial[9]+1];
		/* Fill in the chains given to us in problem description. */
		chain[145] = 1;
		chain[169] = chain[363601] = chain[1454] = 3;
		chain[871] = chain[45361] = 2;
		chain[872] = chain[45362] = 2;
		/* After playing with inputs, discovered 40585 is also a self-loop. */
		chain[1] = 1;
		chain[2] = 1;
		chain[40585] = 1;
		for (int k = 0; k <= MAX_N; k++) {
			if (chain[k] == 0) {
				List<Integer> newChain = new ArrayList<Integer>();
				int term = k;
				while (chain[term] == 0) {
					newChain.add(term);
					for (int t: newChain) chain[t]++;
					term = digitFactorialSum(term);
				}
				for (int t: newChain) chain[t] += chain[term];
			}
		}
	}
	
	public static void main(String[] args) {
		fillFactorial();
		/* Precompute chain lengths of all k <= MAX_N. */
		computeChainLengths();
		Scanner s = new Scanner(System.in);
		int t = Integer.parseInt(s.nextLine());
		for (int t0 = 0; t0 < t; t0++) {
			String[] inputs = s.nextLine().split(" ");
			int N = Integer.parseInt(inputs[0]);
			int L = Integer.parseInt(inputs[1]);
			/* Print in correct format (space separated, no space at end). */
			boolean found = false;
			int prev = 0;
			for (int k = 0; k <= N; k++) {
				if (chain[k] == L) {
					if (!found) {
						found = true;
						prev = k;
					}
					else {
						System.out.print(prev + " ");
						prev = k;
					}
				}
			}
			if (!found) System.out.println("-1");
			else System.out.println(prev);
		}
		s.close();
	}
}
