/* -------- SOLVED -------- */

/* The sum of the primes below 10 is 2+3+5+7 = 17.
 * 
 * Find the sum of all the primes not greater than given N.
 * 
 * INPUT: 1 <= T <= 10^4, 1 <= N <= 10^6 */

import java.util.Scanner;

public class Euler010 {
	
	/* Thoughts/approach: Hashmap? Possibly between k and k'th prime.
	 * 
	 * For each new prime, sum increases. Bracket by prime?
	 * 
	 * Sieve of Eratosthenes. */
	
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		boolean[] composite = new boolean[1000001];
		composite[0] = composite[1] = true;
		for (int i = 2; i*i < 1000001; i++) {
			if (!composite[i]) {
				for (int j = i*i; j < 1000001; j+=i) {
					composite[j] = true;
				}
			}
		}
		long[] sums = new long[1000001];
		long sum = 0;
		for (int i = 0; i < 1000001; i++) {
			if (!composite[i]) sum += i;
			sums[i] = sum;
		}
		int t = Integer.parseInt(s.nextLine());
		for (int i = 0; i < t; i++) {
			int n = Integer.parseInt(s.nextLine());
			System.out.println(sums[n]);
		}
		s.close();
	}
}
