/* -------- UNSOLVED -------- */

/* Euler's Totient function, phi(n) [sometimes called the phi function], is used to determine the number of
 * numbers less than n which are relatively prime to n. For example, as 1,2,4,5,7, and 8, are all less than 
 * nine and relatively prime to nine, phi(9) = 6.
 *
 *		n 		Coprime 		phi(n)	n/phi(n)
 *		2		1				1		2
 *		3		1,2				2		1.5
 *		4		1,3				2		2
 *		5		1,2,3,4			4		1.25
 *		6		1,5				2		3
 *		7		1,2,3,4,5,6		6		1.1666...
 *		8		1,3,5,7			4		2
 *		9		1,2,4,5,7,8		6		1.5
 *		10		1,3,7,9			4		2.5
 *
 * It can be seen that n=6 produces a maximum n/phi(n) for n < 10. Find the value of n<N for which phi(n) is
 * maximum. In case of multiple answers, print the minimum. 
 *
 * INPUTS: 1 <= T <= 1000, 3 <= N <= 10^18 */

import java.util.Scanner;

public class Euler069 {
	
	/* Thoughts/approach: Euler's Totient function has a lot of useful properties:
	 *		https://en.wikipedia.org/wiki/Euler%27s_totient_function
	 * 
	 * phi is multiplicative over coprimes. And if p is prime, phi(p^k) = p^k - p^{k-1} = p^k(1 - 1/p). These
	 * two facts lead us to Euler's product formula: phi(n) = n * PROD_{p|n} (1 - 1/p), where p|n denotes the
	 * set of distinct primes that divide p. It follows that n/phi(n) = 1/(PROD_{p|n} (1 - 1/p)), meaning we 
	 * want to minimize the PROD_{p|n} (1 - 1/p), which means we want to maximize the distinct prime factors 
	 * of n while minimizing n. The smallest n maximizing {p|n} are 2, 2*3, 2*3*5, 2*3*5*7, 2*3*5*7*11,..... T
	 * hat is, the solution for N is the largest product of consecutive primes < N, starting from 2; there are 
	 * no smaller numbers with more distinct prime factors. */

	static final long MAX_N = (long) 1E18;
	static boolean[] composite;
	/* productPrimes[k] stores the product of the first k (zero-based) primes, i.e. productPrimes[2] = 30. */
	static long[] productPrimes;

	/* Sieve primes until product exceeds MAX_N. */
	public static void sieve() {
		composite = new boolean[200];
		productPrimes = new long[100];
		long product = 1;
		int index = 0;
		for (int i = 2; i < 500 && product <= MAX_N/i; i++) {
			if (!composite[i]) {
				product *= i;
				productPrimes[index] = product;
				index++;
				for (int j = i*i; j < 500; j+=i) {
					composite[j] = true;
				}
			}
		}
	}
	
	public static void main(String[] args) {
		sieve();
		Scanner s = new Scanner(System.in);
		int t = Integer.parseInt(s.nextLine());
		for (int t0 = 0; t0 < t; t0++) {
			long n = Long.parseLong(s.nextLine());
			int i = 0;
			while (productPrimes[i+1] != 0 && productPrimes[i+1] < n) i++;
			System.out.println(productPrimes[i]);
		}
		s.close();
	}
}
