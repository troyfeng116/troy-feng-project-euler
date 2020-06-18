/* -------- SOLVED -------- */

/* A unit fraction contains 1 in the numerator. The decimal representation of the unit fractions with
 * denominators 2 to 10 are given:
 * 
 * 1/2 = 0.5
 * 1/3 = 0.(3)
 * 1/4 = 0.25
 * 1/5 = 0.2
 * 1/6 = 0.1(6)
 * 1/7 = 0.(142857)
 * 1/8 = 0.125
 * 1/9 = 0.(1)
 * 1/10 = 0.1
 * 
 * Where 0.1(6) means 0.166666... , and has a 1-digit recurring cycle. It can be seen that 1/7 has a 6-digit 
 * recurring cycle.
 * 
 * Find the value of smallest d < N for which 1/d contains the longest recurring cycle in its decimal fraction 
 * part. 
 * 
 * INPUTS: 1 <= T <= 1000, 4 <= N <= 10000 */

import java.util.Scanner;

public class Euler26 {
	
	/* Thoughts/approach: First, once a single digit repeats, the cycle restarts. So the biggest possible cycle
	 * (if it even exists) would be 10. EDIT: Never mind lol.
	 * 
	 * Writing out long division, the cycle restarts when we hit a remainder we've already seen before. So one
	 * approach might be to simulate long division...
	 * 
	 * Trivially, any N with factors of only 2 and/or 5 doesn't repeat. I think all others are non-terminating.
	 * 
	 * Since there are only N-1 possible remainders for N, the cycle of 1/N is at most N-1.
	 * 
	 * A little dive into long division of 1 by N: at each step, we had a remainder from the last digit. Append a
	 * digit from the dividend. For dividend of 1, we always append zero, which is like multiplying by 10. Then
	 * subtract as large a multiple of N as possible, which is the same as taking remainder mod p.
	 * 
	 * For any prime, if the cycle is k, then dividing 10^k by p should yield remainder 1. Moreover, for any P from
	 * 1 to P-1, we can't have any repeats. By Fermat's, 10^{p-1} mod p = 1.
	 * 
	 * Implementing the equivalent of long division for all numbers that don't divide 2 or5 up to 10000 would take
	 * too long probably. Will try it and see what happens. EDIT: it worked. There's ways to optimize further but
	 * it passes all tests, so... */
	
	
	static final int MAX_N = 10000;
	
	/* For each N from 4 to 10000, store smallest d such that 1/d has longest cycle. So cycles[n] holds the
	 * smallest d<n s.t. 1/d has the longest cycle out of numbers from 1 to N. */
	static int[] longestCycle;
	
	/* Basically do long division until a remainder is repeated (thus cycle repeats). Assumes n is divisible by
	 * neither 2 nor 5 (if it were, it would share cycle length with number that has same factors without 2s and
	 * 5s); thus, n is relatively prime to all powers of 10, meaning remainder must repeat at some point. */
	public static int findCycle (int n) {
		/* Do equiv of long division while tracking length, stop when remainder repeats. */
		boolean[] found = new boolean[n];
		int remainder = 1;
		while (remainder < n) remainder *= 10;
		remainder = remainder % n;
		int ans = 0;
		while (!found[remainder]) {
			if (remainder == 0) return 0;
			found[remainder] = true;
			remainder *= 10;
			remainder = remainder % n;
			ans++;
		}
		return ans;
	}
	
	public static void main(String[] args) {
		
		/* Precalculate all cycles up to MAX_N, storing answer for each N. */
		longestCycle = new int[MAX_N+1];
		longestCycle[1] = longestCycle[2] = 0;
		int longest = 0; /* Longest cycle found out of all d <= n. */
		int d = 2; /* The minimum d such that 1/d has longest cycle found thus far. */
		for (int n = 3; n < MAX_N; n++) {
			int cycle = findCycle(n);
			if (cycle > longest) {
				longest = cycle;
				d = n;
			}
			longestCycle[n+1] = d;
		}
		
		Scanner s = new Scanner(System.in);
		int t = Integer.parseInt(s.nextLine());
		for (int t0 = 0; t0 < t; t0++) {
			int n = Integer.parseInt(s.nextLine());
			System.out.println(longestCycle[n]);
		}
		s.close();
	}
}
