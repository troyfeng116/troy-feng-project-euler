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
	 * larger now. 
	 *
	 * EDIT: I used the exact same approach as problem 76 and lo and behold, it worked without stack or
	 * heap space overflow or TLE. But the point of this problem was to research integer partition theory
	 * and pentagonal numbers, so I will do my due diligence. 
	 * 
	 * It really is a nice recurrence and derivation: 
	 * https://en.wikipedia.org/wiki/Pentagonal_number_theorem#Relation_with_partitions*/

	/* The recurrence is: p(n) = SUM_{k} (-1)^{k-1} * p(n-g_k), where g_k is the k'th generalized
	 * pentagonal number and k:REAL\{0}. That is, g_k = k(3k-1)/2, where k=1,-1,2,-2,... Note that
	 * if n < 0, p(n) = 0, so the sum is not infinite and can be discretely calculated. */

	/* p[k] holds the number of integer partitions for k. */
	static final int MAX_N = 60000;
	static final int MOD = 1000000007;
	static long[] p;

	/* Return k'th generatlized pentagonal number g_k. */
	public static int genPent(int k) {
		return k*(3*k-1)/2;
	}

	/* Precompute values of p[n] for n<=MAX_N using recurrence relation and memoize in p[]. */
	public static void fillP() {
		p = new long[MAX_N+1];
		p[0] = p[1] = 1;
		for (int n = 2; n <= MAX_N; n++) {
			long sum = 0;
			for (int k = 1; true; k++) {
				int g1 = genPent(k);
				if (g1 > n) break;
				sum += k%2==1? p[n-g1] : -1*p[n-g1];
				int g2 = genPent(-k);
				if (g2 > n) break;
				sum += k%2==1? p[n-g2] : -1*p[n-g2];
				sum %= MOD;
			}
			if (sum % MOD < 0) {
				System.out.println(sum);
				System.out.println(n);
				break;
			}
			System.out.println(sum);
			p[n] = sum % MOD;
		}
	}
	
	public static void main(String[] args) {
		//fillNumSums();
		fillP();
		System.exit(0);

		Scanner s = new Scanner(System.in);
		int t = Integer.parseInt(s.nextLine());
		for (int t0 = 0; t0 < t; t0++) {
			int N = Integer.parseInt(s.nextLine());
			//System.out.println(numSums[N]);
			System.out.println(p[N]);
		}
		s.close();
	}

/* -------- ALTERNATE SOLUTION -------- */

/* This is the exact same solution as in problem 76, but with a larger MAX_N. It turns out 60000 isn't
 * large enough to cause any sort of overflow. But the pentagonal number/integer partition theory solution
 * is much cooler. */
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
}
