/* -------- SOLVED -------- */

/* Starting with 1 and spiralling anticlockwise in the following way, a square spiral with side length 7 is 
 * formed.
 *
 *		37 36 35 34 33 32 31
 *		38 17 16 15 14 13 30
 *		39 18  5  4  3 12 29
 *		40 19  6  1  2 11 28
 *		41 20  7  8  9 10 27
 *		42 21 22 23 24 25 26
 *		43 44 45 46 47 48 49
 *
 * It is interesting to note that the odd squares lie along the bottom right diagonal, but what is more 
 * interesting is that 8 out of the 13 numbers lying along both diagonals are prime; that is, a ratio of 
 * 8/13 ~ 62%.
 *
 * If one complete new layer is wrapped around the spiral above, a square spiral with side length 9 will 
 * be formed. If this process is continued, what is the side length of the square spiral for which the 
 * ratio of primes along both diagonals first falls below N%? 
 *
 * INPUTS: 8 <= N <= 60 */

import java.util.Scanner;

public class Euler058 {
	
	/* Thoughts/approach: I remember there was another Project Euler question involving the sums of numbers
	 * along the diagonals. It seems that we might have to loop pretty far, and a simple sieve won't do;
	 * we'll have to implement a fast primality test like Miller-Rabin in addition to a sieve.
	 *
	 * For any (odd) side length n, we can find the four numbers on the corners of the n x n square pretty
	 * quickly (bottom left is n^2). We then have to check the primality of each of those four corners.
	 * Trivially, the bottom left corner can't be prime. */

	/* Return a^pow % mod. */
	public static long pow(long a, long pow, long mod) {
		if (pow == 1) return a%mod;
		long half = pow(a,pow/2,mod);
		long ans = mult(half,half,mod);
		if (pow%2 == 1) ans = mult(ans,a,mod);
		return ans%mod;
	}

	/* Return a*b % mod. */
	public static long mult(long a, long b, long mod) {
		long x = (long) (((double) a*b)/mod);
		long ans = (a*b-x*mod);
		return ans<0 ? ans+mod : ans;
	}

	/* Miller-Rabin primality test. */
	public static boolean isPrime(long n, int k) {
		if (n%2 == 0 || n%3 == 0 || n%5 == 0) return false;
		long d = n-1;
		int s = 0;
		while (d%2 == 0) {
			d/=2;
			s++;
		}
		for (int iter = 0; iter < k; iter++) {
			boolean witness = false;
			long a = 2 + ((long) (Math.random() * (n-3)));
			long x = pow(a,d,n);
			if (x == 1 || x == n-1) continue;
			for (int i = 0; i < s-1; i++) {
				x = mult(x,x,n);
				if (x == n-1) {
					witness = true;
					break;
				}
			}
			if (witness) continue;
			else return false;
		}
		return true;
	}

	/* Return first side length for which ratio of number of primes on diagonals to total numbers on diagonals
	 * falls below N/100. */
	public static int solution(int N) {
		/* Base case for side length = 3: 5 total on diagonals and 3 primes. */
		int numPrimes = 3;
		int numTotal = 5;
		long sideLen = 3;
		while (numPrimes*100 >= N*numTotal) {
			sideLen+=2;
			long bottomLeft = sideLen*sideLen;
			long side1 = bottomLeft - (sideLen-1);
			long side2 = side1 - (sideLen-1);
			long side3 = side2 - (sideLen-1);
			if (isPrime(side1,5)) numPrimes++;
			if (isPrime(side2,5)) numPrimes++;
			if (isPrime(side3,5)) numPrimes++;
			numTotal+=4;
		}
		return (int) sideLen;
	}
	
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int n = Integer.parseInt(s.nextLine());
		int ans = solution(n);
		System.out.println(ans);
		s.close();
	}
}
