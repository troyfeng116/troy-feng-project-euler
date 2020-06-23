/* -------- UNSOLVED -------- */

/* We shall say that an n-digit number is pandigital if it makes use of all the digits 1 to n exactly once. 
 * For example, 2143 is a 4-digit pandigital and is also prime.
 * 
 * What is the largest n-digit pandigital prime <= N? If there is none, print -1. 
 * 
 * INPUT: 1 <= T <= 10^5, 10 <= N <= 10^10-1 */

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Euler041 {
	
	/* Thoughts/approach: Brute force would be to run through all permutations and check if each is prime. But
	 * that would definitely time out even with memoization with the 10^10 cap on N.
	 * 
	 * What's nice is that all pandigitals are divisible by 3 EXCEPT 4-pandigitals and 7-pandigitals, because
	 * the sums of the digits of any other pandigital is divisible by 3, so those numbers themselves are also
	 * divisible by 3. So it seems we only need to check permutations of 1-4 and 1-7, which is much more
	 * feasible. Finally, we can store tables of the largest 4- and 7-digit primes below each 4- and 7-digit
	 * integer. */
	
	/* Store pandigital primes in table. */
	static Set<Integer> table;
	
	/* If there is a next lexicographical permutation of p, generate it and return true. */
	public static boolean nextPermutation(int[] p) {
		int n = p.length;
		int k = n-2;
		while (k >= 0 && p[k] > p[k+1]) k--;
		if (k < 0) return false;
		int l = n-1;
		while (p[l] < p[k]) l--;
		int temp = p[k];
		p[k] = p[l];
		p[l] = temp;
		for (int i = k+1; i <= (k+n)/2; i++) {
			temp = p[i];
			p[i] = p[n-i+k];
			p[n-i+k] = temp;
		}
		return true;
	}
	
	/* Convert in-order array representation of digits to integer. */
	public static int toInt(int[] arr) {
		int ans = 0;
		for (int i = 0; i < arr.length; i++) {
			ans = ans*10 + arr[i];
		}
		return ans;
	}
	
	/* O(sqrt(n)) primality test given that n is not divisible by 2 or 3. */
	public static boolean isPrime(int n) {
		for (int i = 5; i*i <= n; i+=6) {
			if (n % i == 0 || n % (i+2) == 0) return false;
		}
		return true;
	}
	
	public static void main(String[] args) {
		table = new HashSet<Integer>();
		/* Precalculate 4-digit pandigital primes. */
		int[] p4 = new int[] {1,2,3,4};
		while (nextPermutation(p4)) {
			if (p4[3] % 2 != 0) {
				int num = toInt(p4);
				if (isPrime(num)) table.add(num);
			}
		}
		/* Precalulate 7-digit pandigital primes. */
		int[] p7 = new int[] {1,2,3,4,5,6,7};
		int num = toInt(p7);
		if (isPrime(num)) table.add(num);
		while (nextPermutation(p7)) {
			if (p7[6] % 2 != 0) {
				num = toInt(p7);
				if (isPrime(num)) table.add(num);
			}
		}
		
		/* To get answer for N where N is a four-digit integer <= 4321, look up fourDigits[k-1000]. */
		int[] fourDigits = new int[3322];
		fourDigits[0] = -1;
		for (int k = 1001; k <= 4321; k++) {
			if (table.contains(k)) fourDigits[k-1000] = k;
			else fourDigits[k-1000] = fourDigits[k-1001];
		}
		
		/* To get answer for N where N is a seven-digit integer <= 7654321, look up sevenDigits[k-1000000]. */
		int[] sevenDigits = new int[6654322];
		sevenDigits[0] = fourDigits[3321];
		for (int k = 1000001; k <= 7654321; k++) {
			if (table.contains(k)) sevenDigits[k-1000000] = k;
			else sevenDigits[k-1000000] = sevenDigits[k-1000001];
		}
		
		Scanner s = new Scanner(System.in);
		int t = Integer.parseInt(s.nextLine());
		for (int t0 = 0; t0 < t; t0++) {
			long n = Long.parseLong(s.nextLine());
			if (n < 1000) System.out.println(-1);
			else if (n <= 4321) System.out.println(fourDigits[(int) n-1000]);
			else if (n < 1000000) System.out.println(fourDigits[3321]);
			else if (n <= 7654321) System.out.println(sevenDigits[(int) n-1000000]);
			else System.out.println(sevenDigits[6654321]);
		}
		s.close();
	}
}
