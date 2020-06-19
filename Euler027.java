/* -------- SOLVED -------- */

/* Euler published the remarkable quadratic formula:
 * 
 * n^2 + n + 41
 * 
 * It turns out that the formula will produce 40 primes for the consecutive values n = 0 to 39. However, when 
 * n = 40, 40^2 + 40 + 41 is divisible by 41, and certainly when n=41, 41^2 + 41 + 41 is clearly divisible by 41.
 * 
 * Using computers, the incredible formula n^2 - 79 + 1601 was discovered, which produces 80 primes for the 
 * consecutive values 0 to 79. The product of the coefficients, -79 and 1601, is -126479.
 * 
 * Considering quadratics of the form:
 * 
 * n^2 + an + b , where |a|,|b| <= n
 * 
 * where |n| is the modulus/absolute value of n 
 * e.g. |11|=11 and |-4|=4
 * 
 * Find the coefficients, a and b, for the quadratic expression that produces the maximum number of primes 
 * for consecutive values of n, starting with 0.
 * 
 * Note: For this challenge you can assume solution to be unique. 
 * 
 * INPUT: One line with integer N, 42 <= N <= 2000. 
 * 
 * OUTPUT: print the value of a and b separated by space. 
 * 
 * ex. for N=42, output would be a=-1 and b=41, which yields 41 primes.*/

import java.util.Scanner;

public class Euler027 {
	
	/* Thoughts/approach: We have to test combinations of a and b where |a| <= N && |b| <= N such that:
	 * 
	 * b is prime (else n=0 would fail)
	 * a is odd (else n=1 would fail for odd b) 
	 * 
	 * Count consecutive primes for each combo and store max length. Probably precalculate. 
	 * 
	 * There's all sorts of optimizations that can still be done but... it works. */
	
	static boolean composite[];
	
	/* Given coefficients a and b, find number of consecutive primes that can be generated. */
	public static int findPrimes(int a, int b) {
		int ans = 0;
		while (ans*ans + ans*a + b > 0 && !composite[ans*ans + ans*a + b]) ans++;
		return ans;
	}
	
	public static void main(String[] args) {
		/* Sieve primes. Turns out 40000 is big enough for MAX_N of 2000. */
		composite = new boolean[40001];
		composite[0] = composite[1] = true;
		for (int i = 2; i <= 40000; i++) {
			if (!composite[i] && i <= 200) {
				for (int j = i*i; j <= 40000; j+=i) {
					composite[j] = true;
				}
			}
		}
		
		Scanner s = new Scanner(System.in);
		int n = Integer.parseInt(s.nextLine());
		int max = 0;
		int aAns = 0;
		int bAns = 0;
		/* Check all combinations of a and b with restrictions. */
		for (int a = 1; a <= n; a += 2) {
			for (int b = 2; b <= n; b++) {
				if (!composite[b]) {
					int n1 = findPrimes(a,b);
					int n2 = findPrimes(-a,b);
					int n3 = findPrimes(a,-b);
					int n4 = findPrimes(-a,-b);
					if (n1 > max) {
						max = n1;
						aAns = a;
						bAns = b;
					}
					if (n2 > max) {
						max = n2;
						aAns = -a;
						bAns = b;
					}
					if (n3 > max) {
						max = n3;
						aAns = a;
						bAns = -b;
					}
					if (n4 > max) {
						max = n4;
						aAns = -a;
						bAns = -b;
					}
				}
			}
		}
		System.out.println(aAns + " " + bAns);
		s.close();
	}
}
