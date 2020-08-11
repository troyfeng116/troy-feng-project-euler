/* -------- UNSOLVED -------- */

/* It turns out that 12 cm is the smallest length of wire that can be bent to form an integer sided 
 * right angle triangle in exactly one way, but there are many more examples.
 *
 * 12: (3,4,5)
 * 24: (6,8,10)
 * 30: (5,12,13)
 * 36: (9,12,15)
 * 40: (8,15,17)
 * 48: (12,16,20)
 *
 * In contrast, some lengths of wire, like 20 cm, cannot be bent to form an integer sided right angle 
 * triangle, and other lengths allow more than one solution to be found; for example, using 120 cm it 
 * is possible to form exactly three different integer sided right angle triangles.
 * 120: (30,40,50), (20,48,52), (24,45,51)
 *
 * Given that L is the length of the wire, for how many values of L<=N can exactly one integer sided 
 * right angle triangle be formed?
 *
 * INPUTS: 1 <= T <= 10^5, 12 <= N <= 5x10^6 */

import java.util.Scanner;

public class Euler075 {
	
	/* Thoughts/approach: bijective primitive Pythagorean triple generation using Euclid's formula:
	 * a = m^2 - n^2, b = 2mn, c = m^2 + n^2, m,n coprime and not both odd. Then increment multiples of
	 * (a+b+c). */

	static final int MAX_N = 5000000;
	/* numTriples[L] holds the number of integer right triangles with perimeter L. */
	static int[] numTriples;
	/* ans[N] holds the number of L<=N where exactly one integer right triangle can be made. */

	public static int gcd(int a, int b) {
		if (a == 0) return b;
		if (b == 0) return a;
		if (a == b) return a;
		return gcd(b,a%b);
	}

	public static void fillNumTriples() {
		numTriples = new int[MAX_N+1];
		for (int m = 1; m*m < MAX_N/2; m++) {
			for (int n = m%2==0? 1:2; n < m; n+=2) {
				if (gcd(m,n) == 1) {
					int p = m*m-n*n + 2*m*n + m*m+n*n;
					if (p <= MAX_N) {
						for (int l = p; l <= MAX_N; l+=p) {
							numTriples[l]++;
						}
					}
				}
			}
		}
	}
	
	public static void main(String[] args) {
		fillNumTriples();
		Scanner s = new Scanner(System.in);
		int t = Integer.parseInt(s.nextLine());
		for (int t0 = 0; t0 < t; t0++) {
			int N = Integer.parseInt(s.nextLine());
			int count = 0;
			for (int L = 12; L <= N; L += 2) {
				if (numTriples[L] == 1) count++;
			}
			System.out.println(count);
		}
		s.close();
	}
}
