/* -------- UNSOLVED -------- */

/* The primes 3, 7, 109, and 673, are quite remarkable. By taking any two primes and concatenating them 
 * in any order the result will always be prime. For example, taking 7 and 109, both 7109 and 1097 are prime.
 * The sum of these four primes, 792, represents the lowest sum for a set of four primes with this property.
 *
 * Find the sums of all sets of K primes for which any two primes (prime < N) concatenate to produce another 
 * prime. Print the sums in sorted order.
 *
 * INPUTS: 100 <= N <= 20000, 3 <= K <= 5, space separated N K */

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Euler060 {
	
	/* Thoughts/approach: It seems we're gonna need Miller-Rabin again, as we'll be evaluating primality of
	 * 10-digit primes. There are 2262 primes less than 20000. So running 5 nested loops for all combinations
	 * of 5 primes is probably not gonna work. However, if we find TWO primes that work, we could then try
	 * to extend those two primes to sets of three primes. Once we find three primes, we could try all sets
	 * of four primes with those three primes. Once we find four, we then extend to five. We would have to
	 * keep a list of primes that work so far. When trying to extend a set S of k primes, consider the k+1'th
	 * prime p. For all S[i], p concat S[i] and S[i] concat p must be prime for us to be able to extend S to
	 * include p.
	 *
	 * As for concatenation of two integers, string concatenation would work but perhaps counting digits and
	 * adding n1*10^numDigits to n2 might be better? 
	 *
	 * */

	static final int MAX_N = 20000;
	static boolean[] composite;
	/* prime[k] retrieves the k'th prime (0-based). i.e. prime[0] = 2, prime[1] = 3, ... */
	static int[] prime;

	/* Sieve primes up to upTo, and fill in-order mapping of k to k'th prime in prime[]. */
	public static void sieve(int upTo) {
		composite = new boolean[upTo+1];
		int count = 0;
		for (int i = 2; i <= upTo; i++) {
			if (!composite[i]) {
				count++;
				if (i <= Math.sqrt(upTo)) {
					for (int j = i*i; j <= upTo; j+=i) {
						composite[j] = true;
					}
				}
			}
		}
		prime = new int[count];
		int i = 0;
		for (int x = 2; x <= upTo; x++) {
			if (!composite[x]) {
				prime[i] = x;
				i++;
			}
		}
	}

	/* Return n^pow % mod. */
	public static long pow(long n, long pow, long mod) {
		if (pow == 1) return n%mod;
		long half = pow(n, pow/2, mod);
		long ans = mult(half,half,mod);
		if (pow%2 == 1) ans = mult(ans, n, mod);
		return ans%mod;
	}

	/* Return a*b % mod. */
	public static long mult(long a, long b, long mod) {
		long x = (long) (((double)a*b)/mod);
		long ans = (a*b-x*mod);
		return ans<0? ans+mod : ans;
	}

	/* Miller-Rabin primality test. Note that for n < 3,215,031,751, it is enough and deterministic to test 
	 * a = 2, 3, 5, and 7
	 * ( https://en.wikipedia.org/wiki/Miller%E2%80%93Rabin_primality_test ) */
	public static boolean isPrime(long n) {
		if (n <= MAX_N) return !composite[(int) n];
		if (n%3 == 0 || n%5 == 0) return false;
		long d = n-1;
		int s = 0;
		while (d%2 == 0) {
			s++;
			d/=2;
		}
		for (int iter = 0; iter < 4; iter++) {
			int a = prime[iter];
			long x = pow(a,d,n);
			if (x == 1 || x == n-1) continue;
			boolean witness = false;
			for (int i = 0; i < s-1; i++) {
				x = mult(x,x,n);
				if (x == n-1) {
					witness = true;
					break;
				}
			}
			if (witness) continue;
			return false;
		}
		//System.out.println(n + " is prime");
		return true;
	}

	/* Return concatenation of n1 and n2. */
	public static long concat(int n1, int n2) {
		return Long.parseLong(Integer.toString(n1) + Integer.toString(n2));
	}

	/* Given list of primes so far, number of primes used so far, sum of primes used so far, an index start 
	 * at which to start testing next prime (from prime[start]), max N, target number of primes K, and list 
	 * of sums of good K primes, extend solution. */
	public static void extend(List<Integer> soFar, int used, int sum, int start, int N, int K, List<Integer> ans) {
		if (used == K) {
			ans.add(sum);
			/*for (int i = 0; i < soFar.size(); i++) System.out.print(soFar.get(i) + " ");
			System.out.println();*/
			return;
		}
		for (int i = start; i < prime.length && prime[i] < N; i++) {
			int p = prime[i];
			boolean works = true;
			for (int j = 0; j < used; j++) {
				int toTest = soFar.get(j);
				if (!isPrime(concat(p,toTest)) || !isPrime(concat(toTest,p))) {
					works = false;
					break;
				}
			}
			if (works) {
				soFar.add(p);
				extend(soFar, used+1, sum+p, i+1, N, K, ans);
				soFar.remove(used);
			}
		}
	}

	public static List<Integer> solution(int N, int K) {
		List<Integer> ans = new ArrayList<Integer>();
		extend(new ArrayList<Integer>(), 0, 0, 1, N, K, ans);
		return ans;
	}
	
	public static void main(String[] args) {
		sieve(MAX_N);
		System.out.println(prime.length);
		System.out.println(isPrime(17));
		System.out.println(isPrime(101));
		System.out.println(isPrime(97*97));
		//System.exit(0);

		Scanner s = new Scanner(System.in);
		String[] inputs = s.nextLine().split(" ");
		int N = Integer.parseInt(inputs[0]);
		int K = Integer.parseInt(inputs[1]);
		List<Integer> res = solution(N,K);
		Collections.sort(res);
		for (int i = 0; i < res.size(); i++) System.out.println(res.get(i));
		System.out.println(res.size());
		s.close();
	}
}
