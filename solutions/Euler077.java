/* -------- UNSOLVED -------- */

/* It is possible to write ten as the sum of primes in exactly five different ways:
 *
 * 7+3
 * 5+5
 * 5+3+2
 * 3+3+2+2
 * 2+2+2+2+2
 *
 * You are given N, in how many ways can N be written as sum of 1 or more primes?
 *
 * INPUTS: 1 <= T <= 100, 2 <= N <= 1000 */

import java.util.Scanner;

public class Euler077 {
	
	/* Thoughts/approach: Exactly the same as the last one, but the set of "coins" is now only the primes.
	 * Question is: how many ways are there to make change for N using coins={p:<=N}? */

	static final int MAX_N = 1000;
	/* composite[k] = true iff k is composite. */
	static boolean[] composite;
	/* prime[k] holds the k'th prime (zero-based). i.e. prime[0] = 2, prime[1] = 3, etc. */
	static int[] prime;
	/* numSums[k] holds the number of ways to write k as the sum of 1+ primes. */
	static int[] numSums;

	/* Sieve primes up to 1000. Then fill prime[] with indexing of those primes. */
	public static void sieve() {
		composite = new boolean[MAX_N+1];
		int count = 0;
		for (int i = 2; i*i <= MAX_N; i++) {
			if (!composite[i]) {
				count++;
				for (int j = i*i; j<=MAX_N; j+=i) {
					composite[j] = true;
				}
			}
		}
		prime = new int[count];
		int index = 0;
		for (int i = 2; i <= MAX_N; i++) {
			if (!composite[i]) {
				prime[index] = i;
				index++;
			}
		}
	}

	/* Compute #ways to write 1000 as sum of prime "coins", memoizing in numSums. */
	
	
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int t = Integer.parseInt(s.nextLine());
		for (int t0 = 0; t0 < t; t0++) {
			int N = Integer.parseInt(s.nextLine());
		}
		s.close();
	}
}
