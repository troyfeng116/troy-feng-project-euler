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
	/* Store the primes in order (prime[0]=2, prime[1]=3,...) */
	static int[] prime;
	
	/* Sieve up to and including max. */
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

	/* Use randomized Miller-Rabin primality test to determine if a large n is prime. k specifies number of repetitions.
	 * See https://en.wikipedia.org/wiki/Miller%E2%80%93Rabin_primality_test */
	public static boolean isPrime(long n, int k) {
		if (n < 6000000) return !composite[(int) n];
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

	/* Given a sum consisting of consecutive primes from start to end inclusive of length len, return
	 * a prime sum of consecutive primes within that sequence. */
	static int maxLen = Integer.MIN_VALUE;
	static long ansSum = Long.MAX_VALUE;
	public static void getSum(long sum, int start, int end, int len) {
		if (len < maxLen) return;
		if (isPrime(sum,5)) {
			if (len == maxLen) {
				ansSum = Math.min(sum, ansSum);
			}
			else if (len > maxLen) {
				maxLen = len;
				ansSum = sum;
			}
			return;
		}
		if (start >= end) {
			maxLen = 1;
			ansSum = prime[start];
		}
		getSum(sum-prime[end], start, end-1, len-1);
		getSum(sum-prime[start], start+1, end, len-1);
	}
	
	public static void main(String[] args) {
		sieve(6000000);
		long testSum = 0;
		int count = 0;
		for (; testSum <= MAX_N; count++) {
			testSum += prime[count];
		}
		
		System.out.println("count: " + count + " sum: " + testSum + " last prime: " + prime[count]);
		/*System.out.println(pow(1000,1000,1007));
		for (int i = 0; i < 100000; i++) {
			if (!isPrime(100000000003L,5)) System.out.println("SHIT");
		}
		System.exit(0);*/
		
		Scanner s = new Scanner(System.in);
		int t = Integer.parseInt(s.nextLine());
		for (int t0 = 0; t0 < t; t0++) {
			long n = Long.parseLong(s.nextLine());
			long sum = 0;
			int curPrime = 0;
			int length = 0;
			while (sum + prime[curPrime] <= n) {
				sum += prime[curPrime];
				curPrime++;
				length++;
				//System.out.println("added " + curPrime + " to get sum " + sum);
			}
			/* Right now, sum holds the largest sum of consecutive primes <= N, starting from 2 and ending
			 * with curPrime. */
			System.out.println("SUM: " + sum + " LENGTH: " + length);
			getSum(sum, 0, curPrime-1, length);
			System.out.println(ansSum + " " + maxLen);
		}
		s.close();
	}
}
