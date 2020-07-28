/* -------- SOLVED -------- */

/* A Pythagorean triplet is a set of three natural numbers, a < b < c, for which,
 * 
 * a^2 + b^2 = c^2
 * 
 * For example, 3 4 5.
 * 
 * Given N, check if there exists any Pythagorean triplet for which a + b + c = N.
 * 
 * Find maximum possible value of abc among all such Pythagorean triplets, if there is no such Pythagorean triplet 
 * print -1. 
 * 
 * INPUT: 1 <= T,N <= 3000, */

import java.util.Scanner;
public class Euler009 {
	
	/* Notes/observations:
	 * 
	 * Must have a + b > c. Since a+b+c = N, 2c < N so c < N/2. But c > N/3.
	 * So we need to test c from N/3 to N/2, and values of a,b such that a+b = N-c and a<b.
	 * After fixing a c between N/3 and N/2, a+b must add to N-c. a<b, but also <c. */

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int t = Integer.parseInt(s.nextLine());
		for (int i = 0; i < t; i++) {
			int n = Integer.parseInt(s.nextLine());
			int ans = -1;
			for (int c = n/3; c <= n/2; c++) {
				int rest = n-c;
				for (int a = 1; a <= rest/2; a++) {
					int b = rest - a;
					if (b >= c) ;
					else {
						if (a*a + b*b == c*c) {
							if (a*b*c > ans) ans = a*b*c;
						}
					}
				}
			}
			System.out.println(ans);
		}
		s.close();
	}

}
