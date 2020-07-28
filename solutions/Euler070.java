/* -------- SOLVED -------- */

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
	static long[] tenToThe;

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

	public static void fillTenToThe() {
		tenToThe = new long[10];
		tenToThe[0] = 1;
		for (int i = 1; i < tenToThe.length; i++) {
			tenToThe[i] = tenToThe[i-1]*10;
		}
	}

	/* Return count-of-digits representation of n. i.e. 13563 -> 0001102010. */
	public static long countOfDigits(int n) {
		long ans = 0;
		while (n != 0) {
			ans += tenToThe[n%10];
			n /= 10;
		}
		return ans;
	}

	/* Return true if digits of n and k are permutations of each other. */
	public static boolean isPermutation(int n, int k) {
		return countOfDigits(n) == countOfDigits(k);
	}

	/* Return 1 < n < N s.t. phi(n) is permutation of n, and n/phi(n) is minimized for n:(1,N). */
	public static int solution(int N) {
		int ans = 0;
		int minN = 1;
		int minPhi = 0;
		for (int n = 2; n < N; n++) {
			if (isPermutation(n, phi[n])) {
				if ((long) n*minPhi < (long) minN*phi[n]) {
					ans = n;
					minN = n;
					minPhi = phi[n];
				}
			}
		}
		return ans;
	}
	
	public static void main(String[] args) {
		fillTenToThe();
		Scanner s = new Scanner(System.in);
		int N = Integer.parseInt(s.nextLine());
		sieve(N);
		System.out.println(solution(N));
		s.close();
	}
}
