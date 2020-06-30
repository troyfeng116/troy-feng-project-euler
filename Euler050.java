/* -------- UNSOLVED -------- */

/* The prime 41, can be written as the sum of six consecutive primes:
 * 
 * 		41 = 2 + 3 + 5 + 7 + 11 + 13
 * 
 * This is the longest sum of consecutive primes that adds to a prime below one-hundred.
 * 
 * The longest sum of consecutive primes below one-thousand that adds to a prime, contains 21 terms, and is 
 * equal to 953.
 * 
 * Which prime, <= N, can be written as the sum of the most consecutive primes?
 * 
 * Note: You have to print prime as well as the length of consecutive chain whose sum is prime. If there are
 * more than 1 such primes, print the least. 
 * 
 * INPUTS: 1 <= T <= 10, 2 <= N <= 10^12 */

import java.util.Scanner;

public class Euler050 {
	
	/* Thoughts/approach: Simple sieving won't cut it since N <= 10^12. We probably need another fast primality
	 * test, like Miller-Rabin.
	 * 
	 * To test the waters, I first sieved primes and calculated sums of consecutive primes until the sum exceeded
	 * MAX_N 10^12. The sum of the first 379324 primes exceeds MAX_N; the 379324th prime is 5477083, which is the
	 * point at which we'll stop sieving. */
	
	final static long MAX_N = 1000000000000L;
	static boolean[] composite;
	
	/* Sieve up to and including max. */
	public static void sieve(int max) {
		composite = new boolean[max+1];
		for (int i = 2; i <= max; i++) {
			if (!composite[i] && i <= Math.sqrt(max)) {
				for (int j = i*i; j <= max; j+=i) {
					composite[j] = true;
				}
			}
		}
	}
	
	public static void main(String[] args) {
		sieve(6000000);
		long sum = 2+3+5+7;
		int count = 4;
		int lastPrime = 7;
		for (int i = 11; i < composite.length && sum < MAX_N; i+=6) {
			if (!composite[i]) {
				count++;
				sum += i;
				lastPrime = i;
			}
			if (i+2 < composite.length && !composite[i+2] && sum < MAX_N) {
				count++;
				sum += i+2;
				lastPrime = i+2;
			}
		}
		System.out.println("count: " + count + " sum: " + sum + " last prime: " + lastPrime);
		System.exit(0);
		
		Scanner s = new Scanner(System.in);
		int t = Integer.parseInt(s.nextLine());
		for (int t0 = 0; t0 < t; t0++) {
			
		}
		s.close();
	}
}
