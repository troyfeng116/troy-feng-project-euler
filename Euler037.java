/* -------- SOLVED -------- */

/* The number 3797 has an interesting property. Being prime itself, it is possible to continuously remove 
 * digits from left to right, and remain prime at each stage: 3797, 797, 97, and 7. Similarly we can work 
 * from right to left: 3797, 379, 37, and 3.
 * 
 * Find the sum of primes that are both truncatable from left to right and right to left below N.
 * 
 * NOTE: 2, 3, 5, and 7 are not considered to be truncatable primes. 
 * 
 * INPUT: 100 <= N <= 10^6. */

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Euler037 {
	
	/* Thoughts/approach: Sieving and then brute forcing from 11 to N would work. The problem kind of lends
	 * itself to memoization/recursion though; we can store whether each prefix and suffix is L and/or R
	 * truncatable. So for 3797, we would recurse to 379, 37, and 3, thus memoizing all of them as L-truncatable.
	 * Similarly, 3797, 797, 97, and 7 would all be memoized as R-truncatable. 
	 * 
	 * L-truncate := remove first leftmost digit. R-truncate := remove rightmost digit. 
	 * 
	 * EDIT: After completing the problem, brute forcing from 11 to n definitely worked more than efficient
	 * enough, but there's so few truncatable primes we would probably generate them by checking permutations
	 * of possible digits. */
	
	static final int MAX_N = 1000000;
	static boolean[] composite;
	static Set<Integer> LTrunc;
	static Set<Integer> RTrunc;
	
	public static boolean truncatable(int n) {
		return leftTrunc(n) && rightTrunc(n);
	}
	
	public static boolean leftTrunc(int n) {
		if (composite[n]) return false;
		if (n < 10) {
			LTrunc.add(n);
			return true;
		}
		if (LTrunc.contains(n)) return true;
		int divisor = 1;
		while (divisor*10 <= n) {
			divisor *= 10;
		}
		if (leftTrunc(n % divisor)) {
			LTrunc.add(n);
			return true;
		}
		return false;
	}
	
	public static boolean rightTrunc(int n) {
		if (composite[n]) return false;
		if (n < 10) {
			RTrunc.add(n);
			return true;
		}
		if (RTrunc.contains(n)) return true;
		if (rightTrunc(n/10)) {
			RTrunc.add(n);
			return true;
		}
		return false;
	}
	
	public static void main(String[] args) {
		composite = new boolean[MAX_N+1];
		composite[0] = composite[1] = true;
		for (int i=2; i<=MAX_N; i++) {
			if (!composite[i] && i<=1000) {
				for (int j=i*i; j<=MAX_N; j+=i) {
					composite[j] = true;
				}
			}
		}
		
		LTrunc = new HashSet<Integer>();
		RTrunc = new HashSet<Integer>();
		
		Scanner s = new Scanner(System.in);
		int n = Integer.parseInt(s.nextLine());
		int ans = 0;
		for (int i = 11; i < n; i+=6) {
			if (truncatable(i)) ans += i;
			if (i+2 < n && truncatable(i+2)) ans += i+2;
		}
		System.out.println(ans);
		s.close();
	}
}
