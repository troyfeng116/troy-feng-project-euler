/* -------- UNSOLVED -------- */

/* It is well known that if the square root of a natural number is not an integer, then it is irrational.
 * The decimal expansion of such square roots is infinite without any repeating pattern at all.
 *
 * The square root of two is 1.41421356237309504880..., and the digital sum of the first one hundred 
 * decimal digits is 475.
 *
 * For the first N natural numbers, find the total of the digital sums of the first P digits for all the 
 * irrational square roots x such that x <= N. 
 *
 * INPUTS: either 1 <= N <= 1000 and 1 <= P <= 1000, or 1 <= N <= 100 and 1 <= P <= 10000, new lines */

import java.util.Scanner;
import java.math.BigInteger;

public class Euler080 {

	/*
	 * Thoughts/approach: Python. :(
	 *
	 * Put BigInteger class to use. To avoid decimals, take sqrt(N * 10^(2P)) to get
	 * first P digits. Note that sqrt(a*b) = sqrt(a) * sqrt(b): memoize all sqrts
	 * bottom up. Store extra digits to avoid rounding errors when multiplying sqrt.
	 * Since at most 9 factors (2^9), about 5-6 extra digits memoized should do.
	 * 
	 * This solution BARELY passes TC #9 on HackerRank.
	 */

	// smallestFactor[k] = smallest non-one positive factor of k.
	static int[] smallestFactor;

	private static void sieve(int N) {
		smallestFactor = new int[N + 1];
		for (int i = 2; i <= N; i++) {
			if (smallestFactor[i] == 0) {
				smallestFactor[i] = i;
				for (int j = i + i; j <= N; j += i) {
					if (smallestFactor[j] == 0)
						smallestFactor[j] = i;
				}
			}
		}
	}

	private static boolean isSquare(int N) {
		int rt = (int) Math.sqrt(N);
		return rt * rt == N;
	}

	// Java 8 does not have a sqrt() method for BigInteger :/
	// Credit:
	// https://stackoverflow.com/questions/4407839/how-can-i-find-the-square-root-of-a-java-biginteger
	private static BigInteger sqrt(BigInteger x) {
		BigInteger div = BigInteger.ZERO.setBit(x.bitLength() / 2);
		BigInteger div2 = div;
		// Loop until we hit the same value twice in a row, or wind up alternating.
		for (;;) {
			BigInteger y = div.add(x.divide(div)).shiftRight(1);
			if (y.equals(div) || y.equals(div2))
				return y;
			div2 = div;
			div = y;
		}
	}

	// Given N is prime, calculate first P digits of sqrt(N)
	private static BigInteger calcRoot(int N, int P) {
		BigInteger ans = new BigInteger(Integer.toString(N));
		ans = ans.multiply(BigInteger.TEN.pow(2 * P + 12));
		ans = sqrt(ans);
		return new BigInteger(ans.toString().substring(0, P + 6));
	}

	private static long solution(int N, int P) {
		// dp[i] = first (P+10) digits of sqrt(N)
		BigInteger[] dp = new BigInteger[N + 1];
		for (int i = 2; i <= N; i++) {
			if (isSquare(i)) {
				dp[i] = new BigInteger(Integer.toString((int) Math.sqrt(i)));
			} else if (smallestFactor[i] == i) {
				dp[i] = calcRoot(i, P);
			} else {
				BigInteger ans = BigInteger.ONE;
				int num = i;
				while (num > 1) {
					ans = ans.multiply(dp[smallestFactor[num]]);
					num /= smallestFactor[num];
				}
				dp[i] = new BigInteger(ans.toString().substring(0, P + 6));
			}
		}

		long ans = 0;
		for (int i = 2; i <= N; i++) {
			if (!isSquare(i)) {
				String s = dp[i].toString();
				for (int j = 0; j < P; j++) {
					ans += s.charAt(j) - '0';
				}
			}
		}
		return ans;
	}

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int N = Integer.parseInt(s.nextLine());
		int P = Integer.parseInt(s.nextLine());
		sieve(N);
		System.out.println(solution(N, P));
		s.close();
	}
}
