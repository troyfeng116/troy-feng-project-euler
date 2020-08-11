/* -------- SOLVED -------- */

/* It is possible to write five as a sum in exactly six different ways:
 *
 * 4+1
 * 3+2
 * 3+1+1
 * 2+2+1
 * 2+1+1+1
 * 1+1+1+1+1
 *
 * How many different ways can N be written as a sum of at least two positive integers?
 *
 * As answer can be large, print % (10^9+7).
 *
 * INPUTS: 1 <= T <= 100, 2 <= N <= 1000 */

import java.util.Scanner;

public class Euler076 {
	
	/* Thoughts/approach: Essentially, how many ways are there to make change for N cents using the set
	 * of coins {1,...,N-1}? Definitely DP. */

	static final int MOD = 1000000007;
	static final int MAX_N = 1000;
	/* numSums[k] holds the number of unordered integer partitions of k. */
	static long[] numSums;

	/* Find #ways to make change for 1000 using coins = {1,2,3,...,1000}, memoizing in numSums[]. Then 
	 * the number of unordered integer partitions for N is numSums[N]-1 (since we don't include the
	 * trivial partition of N itself). */
	public static void fillNumSums() {
		numSums = new long[MAX_N+1];
		numSums[0] = 1;
		for (int c = 1; c <= MAX_N; c++) {
			for (int k = c; k <= MAX_N; k++) {
				numSums[k] += numSums[k-c];
				numSums[k] %= MOD;
			}
		}
	}
	
	public static void main(String[] args) {
		fillNumSums();
		Scanner s = new Scanner(System.in);
		int t = Integer.parseInt(s.nextLine());
		for (int t0 = 0; t0 < t; t0++) {
			int N = Integer.parseInt(s.nextLine());
			System.out.println(numSums[N]-1);
		}
		s.close();
	}
}
