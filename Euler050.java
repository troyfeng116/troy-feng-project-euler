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

	/* Use randomized Miller-Rabin primality test to determine if a large odd n is prime. Pre-conditions:
	 * n is odd. k specifies number of repetitions.
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

	/* Given that n is prime, returns next prime larger than n. */
	public static int nextPrime(int n) {
		if (n == 2) return 3;
		int i = n+2;
		while (composite[i]) i+=2;
		return i;
	}

	/* Given that n is prime, returns largest prime smaller than n. */
	public static int prevPrime(int n) {
		if (n <= 2) return -1;
		if (n == 3) return 2;
		int i = n-2;
		while (composite[i]) i -= 2;
		return i;
	}

	/* Given a sum consisting of consecutive primes from start to end inclusive of length len, return
	 * an array containing the prime sum with the longest length. Recursively subtracts prime off both
	 * ends, prioritizing the larger end. */
	public static long[] getSum(long sum, int start, int end, int len) {
		if (start >= end) return new long[] {sum,1};
		if (isPrime(sum,5)) return new long[] {sum,len};
		int prev = prevPrime(end);
		long[] ans1 = getSum(sum-end, start, prev, len-1);
		int next = nextPrime(start);
		long[] ans2 = getSum(sum-start, next, end, len-1);
		if (ans1[1] < ans2[1]) return ans2;
		if (ans1[1] > ans2[1]) return ans1;
		return ans1[0] < ans2[0] ? ans1 : ans2;
	}
	
	public static void main(String[] args) {
		sieve(6000000);
		long testSum = 2+3+5+7;
		int count = 4;
		int lastPrime = 7;
		for (int i = 11; i < composite.length && testSum < MAX_N; i+=6) {
			if (!composite[i]) {
				count++;
				testSum += i;
				lastPrime = i;
			}
			if (i+2 < composite.length && !composite[i+2] && testSum < MAX_N) {
				count++;
				testSum += i+2;
				lastPrime = i+2;
			}
		}
		
		/*System.out.println("count: " + count + " sum: " + testSum + " last prime: " + lastPrime);
		System.out.println(pow(1000,1000,1007));
		for (int i = 0; i < 100000; i++) {
			if (!isPrime(100000000003L,5)) System.out.println("SHIT");
		}
		System.exit(0);*/
		
		Scanner s = new Scanner(System.in);
		int t = Integer.parseInt(s.nextLine());
		for (int t0 = 0; t0 < t; t0++) {
			long n = Long.parseLong(s.nextLine());
			long sum = 2;
			int curPrime = 2;
			int length = 1;
			while (sum + curPrime <= n) {
				curPrime = nextPrime(curPrime);
				sum += curPrime;
				length++;
				//System.out.println("added " + curPrime + " to get sum " + sum);
			}
			/* Right now, sum holds the largest sum of consecutive primes <= N, starting from 2 and ending
			 * with curPrime. */
			System.out.println("SUM: " + sum + " LENGTH: " + length);
			long[] ans = getSum(sum, 2, curPrime, length);
			System.out.println(ans[0] + " " + ans[1]);
		}
		s.close();
	}
}
