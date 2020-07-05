/* -------- SOLVED -------- */

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
	/* Store the primes in order so that prime[k] holds the k'th prime (zero-based, i,e. prime[0] = 2). */
	static int[] prime;
	
	/* Sieve up to and including max. Also populate prime-by-prime storage in prime[]. */
	public static void sieve(int max) {
		composite = new boolean[max+1];
		prime = new int[max+1];
		int index = 0;
		for (int i = 2; i <= max; i++) {
			if (!composite[i]) {
				prime[index] = i;
				index++;
				if (i <= Math.sqrt(max)) {
					for (int j = i*i; j <= max; j+=i) {
						composite[j] = true;
					}
				}
			}
		}
	}

	/* Fast modular exponentiation. Return n^p % mod. */
	public static long pow(long n, long pow, long mod) {
		if (pow == 1) return n%mod;
		long sqrt = pow(n,pow/2,mod);
		long ans = mult(sqrt,sqrt,mod);
		if (pow%2 == 1) ans = mult(ans,n,mod);
		return ans%mod;
	}

	/* Return a*b % mod, preventing long overflow. */
	public static long mult(long a, long b, long mod) {
		long x = (long) (((double)a*b)/mod);
		long ans = (a*b-x*mod);
		return ans<0 ? ans+mod : ans;
	}

	/* Miller-Rabin primality test. k specifies number of repetitions.
	 * https://en.wikipedia.org/wiki/Miller%E2%80%93Rabin_primality_test */
	public static boolean isPrime(long n, int k) {
		if (n < 6000000) return !composite[(int) n];
		if (n%2 == 0 || n%3 == 0) return false;
		/* Write (n-1) as d*2^s, where d is odd. */
		int s = 0;
		long d = n-1;
		while (d%2==0) {
			s++;
			d/=2;
		}
		for (int rep = 0; rep < k; rep++) {
			boolean reset = false;
			long a = 2 + ((long) (Math.random()*(n-3)));
			long x = pow(a,d,n);
			if (x == 1 || x == n-1) continue;
			for (int i = 0; i < s-1; i++) {
				x = mult(x,x,n);
				if (x == n-1) {
					reset = true;
					break;
				}
			}
			if (reset) continue;
			else return false;
		}
		return true;
	}
	
	public static void main(String[] args) {
		/* Precomputed: the sum of all primes below 6000000 exceeds MAX_N. */
		sieve(6000000);

		Scanner s = new Scanner(System.in);
		int t = Integer.parseInt(s.nextLine());
		for (int t0 = 0; t0 < t; t0++) {
			long n = Long.parseLong(s.nextLine());
			long ansSum = 0;
			int ansLen = 0;

			long sum = 0;
			int curPrime = 0;
			int len = 0;
			
			/* Get preliminary ansSum and ansLen values, with chain starting from 2. */
			while (sum + prime[curPrime] <= n) {
				sum += prime[curPrime];
				curPrime++;
				len++;
				if (isPrime(sum,3)) {
					ansSum = sum;
					ansLen = len;
				}
			}

			/* Right now, sum holds the largest chain sum of consecutive primes <= N, starting from 2 and ending
			 * with curPrime. Increment the starting point prime by prime. */
			int lastPrime = curPrime;
			int startPrime = 1;
			/* While the longest possible length starting from startPrime is >= ansLen thus far: */
			while (lastPrime - startPrime + 1 >= ansLen) {
				curPrime = startPrime;
				sum = 0;
				len = 0;
				while (sum + prime[curPrime] <= n) {
					sum += prime[curPrime];
					curPrime++;
					len++;
					if (len >= ansLen) {
						if (isPrime(sum,3)) {
							if (len == ansLen) {
								ansSum = Math.min(sum, ansSum);
							}
							else {
								ansLen = len;
								ansSum = sum;
							}
						}
					}
				}
				lastPrime = curPrime;
				startPrime++;
			}
			System.out.println(ansSum + " " + ansLen);
		}
		s.close();
	}
}
