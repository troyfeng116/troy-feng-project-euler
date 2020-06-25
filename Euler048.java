/* -------- SOLVED -------- */

/* Consider the series,
 * 
 * 		1^1 + 2^2 + 3^3 + ... + 10^10 = 10405071317
 *  
 * Find the last ten digits of the series,
 *  
 * 		1^1 + 2^2 + 3^3 + ... + N^N
 * 
 * Note: you do not need to print leading zeros. Ex. for N=10, print 405071317.
 * 
 * INPUTS: 1 <= N <= 2x10^6 */

import java.util.Scanner;

public class Euler048 {
	
	/* Thoughts/approach: It seems we'll have to implement multiplication with digit arrays if we don't sell out
	 * and use Java's BigInteger class (which we won't). We can also use quick exponentiation to reduce the
	 * complexity of finding N^N down to O(log(n)) time. 
	 * 
	 * We need only preserve the last 10 digits of any multiplication. 
	 * 
	 * EDIT: I realized this is essentially modular exponentiation under mod 10^10. No need for arrays...
	 * The recursion in pow had many repeated results, but was impossible to memoize. This led to a lot of storage
	 * and time wasted using arrays. Still a bit thorny because of potential long overflow. */
	
	static final long MOD = 10000000000L;
	
	/* This quick exponentiation algorithm reduces runtime of calculating x^n from linear to logarithmic
	 * complexity. All done under mod 10^10. */
	public static long pow(int n1, int pow) {
		if (pow == 1) return n1;
		long n2 = pow(n1,pow/2) % MOD;
		long n3 = (mult(n2,n2)) % MOD;
		if (pow%2 == 0) return n3;
		return n3*n1 % MOD;
	}
	
	/* If n1,n2 < 10^10, long overflow is a problem for n1*n2. Need doubles to prevent. */
	public static long mult(long n1, long n2) {
		long x = (long) (((double) n1 * n2) / MOD);
		long ans = (n1*n2 - MOD*x);
		if (ans < 0) ans += MOD;
		return ans;
	}
	
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int n = Integer.parseInt(s.nextLine());
		long ans = 0;
		for (int i = 1; i <= n; i++) {
			if (i%10 == 0) /* Contributes only zeroes to last 10 digits. */;
			else {
				ans += pow(i,i);
				ans = ans % MOD;
			}
		}
		System.out.println(ans);
		s.close();
	}
}

