/* -------- SOLVED -------- */

/* The prime factors of 13195 are 5,7,13, and 29.
 * 
 * What is the largest prime factor of a given number N? 
 * 
 * INPUT: T, integer 10 <= N <= 10^12 */

import java.util.Scanner;

public class Euler003 {
	
	/* If n is prime, returns n. Else returns a prime factor of n. Assumes n%2 != 0 and n%3 != 0. */
	public static long isPrime(long n, long start) {
		/* All primes other than 2 and 3 are of the form 6k+1 or 6k-1. Moreover, we need not check factors greater than
		 * sqrt(n). */
		if (start > 5) {
			while (start % 6 != 5) start--;
		}
		for (long i = Math.max(5,start); i * i <= n; i+=6) {
			if (n % i == 0) return i;
			if (n % (i+2) == 0) return i+2;
		}
		return n;
	}

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
        int nTests = s.nextInt();
        for(int i = 0; i < nTests; i++) {
            long n = s.nextLong();
            if (n <= 1) {
            	System.out.println(n);
            	continue;
            }
            
            while (n % 2 == 0) n /= 2;
            if (n == 1) {
            	System.out.println(2);
            	continue;
            }
            while (n % 3 == 0) n /= 3;
            if (n == 1) {
            	System.out.println(3);
            	continue;
            }
            
            long max = isPrime(n,5);
            long primeFactor = max;
            while (n != primeFactor) {
            	if (primeFactor > max) max = primeFactor;
            	n /= primeFactor;
            	primeFactor = isPrime(n,primeFactor);
            }
            System.out.println(Math.max(max, primeFactor));
        }
        s.close();
	}

}
