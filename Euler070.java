/* -------- UNSOLVED -------- */

/* Euler's Totient function, phi(n) [sometimes called the phi function], is used to determine the number of 
 * positive numbers less than or equal to n which are relatively prime to n. For example, as 1,2,4,5,7, and 8
 *  are all less than nine and relatively prime to nine, phi(9) = 6.
 *
 * The number 1 is considered to be relatively prime to every positive number, so phi(1)=1. Interestingly, 
 * phi(87109) = 79180, and it can be seen that 87109 is a permutation of 79180.
 *
 * Find the value of n, where 1 < n < N, for which phi(n) is a permutation of n and the ratio phi(n)/n 
 * produces a minimum.
 *
 * INPUTS: 100 <= N <= 10^7 */

import java.util.Scanner;

public class Euler070 {
	
	/* Thoughts/approach: we can find all values of phi(n) using a modified prime sieve. Then I suppose we
	 * loop over [1,N] again, finding values n such that n and phi(n) are permutations, and storing n
	 * corresponding to minimum n/phi(n). */

	/* phi[k] stores phi(k). */
	static int[] phi;

	/* Modified Sieve of Eratosthenes. Since phi(n) = n * PROD{p|n} (1 - 1/p), if we sieve over all primes
	 * less than N and multiply all multiples of primes by 1-1/p, we will end up with a table of Euler's
	 * Totient function values. */
	public static void sieve(int N) {
		phi = new int[N+1];
		for (int i = 2; i <= N; i++) {
			phi[i] = i;
		}
		for (int i = 2; i <= N; i++) {
			/* If i is prime, sieve over its multiples. */
			if (phi[i] == i) {
				/* phi(p) = p-1. */
				phi[i]--;
				/* Multiply all multiples of p by (1-1/p). */
				for (int j = 2*i; j <= N; j+=i) {
					phi[j] /= i;
					phi[j] *= (i-1);
				}
			}
		}
	}

	
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int N = Integer.parseInt(s.nextLine());
		sieve(N);
		//for (int i: phi) System.out.println(i);
		System.out.println("done");
		s.close();
	}
}
