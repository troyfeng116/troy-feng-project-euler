/* -------- SOLVED -------- */

/* Let p(n) represent the number of different ways in which n coins can be separated into piles. For 
 * example, five coins can separated into piles in exactly seven different ways, so p(5) = 7.
 *		OOOOO
 *		OOOO   O
 *		OOO   OO
 *		OOO   O   O
 *		OO   OO   O
 *		OO   O   O   O
 *		O   O   O   O   O
 *
 * How many different ways can  coins be separated into piles?
 *
 * As answer can be large, print % (10^9+7).
 *
 * INPUTS: 1 <= T <= 100, 2 <= N <= 6x10^4 */

import java.util.Scanner;

public class Euler078 {
	
	/* Thoughts/approach: This is still the coin sums/integer partition problem, but MAX_N is significantly
	 * larger now. */

	static final int MAX_N = 60000;
	static final int MOD = 1000000007;
	static long[] numSums;

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
			System.out.println(numSums[N]);
		}
		s.close();
	}
}
