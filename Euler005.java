/* -------- SOLVED -------- */

/* 2520 is the smallest number that can be divided by each of the numbers from 1 to 10 without any remainder.
 * 
 * What is the smallest positive number that is evenly divisible (divisible with no remainder) by all of the 
 * numbers from 1 to N? 
 * 
 * INPUT: 1 <= T <= 10, 1 <= N <= 40. */

import java.util.Scanner;

public class Euler005 {
	
	/* Approach: for each integer i <= N, check if gcd(i,N) == i. If not, multiply by i/gcd. Else continue. */
	
	public static int gcd(int a, int b) {
		if (a == 0) return b;
		if (b == 0) return a;
		if (a == b) return a;
		if (a > b) return gcd(b, a%b);
		return gcd(b%a, a);
	}

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int t = s.nextInt();
		for (int i = 0; i < t; i++) {
			int ans = 1;
			int n = s.nextInt();
			for (int num = 1; num <= n; num++) {
				int gcd = gcd(num,ans);
				if (gcd == num) ;
				else {
					ans *= num/gcd;
				}
			}
			System.out.println(ans);
		}
		s.close();
	}

}
