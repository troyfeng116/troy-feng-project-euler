/* -------- SOLVED -------- */

/* The 5-digit number, 16807 = 7^5, is also a fifth power. Similarly, the 9-digit number, 134217728 = 8^9, 
 * is a ninth power.
 *
 * Given N, print the N-digit positive integers which are also an Nth power. 
 *
 * INPUTS: 1 <= N <= 19
 * OUTPUT each N-digit integer on new line in increasing order. */

import java.util.Scanner;

public class Euler063 {
	
	/* Thoughts/approach: simple loop over N-th powers within N-digit range should do. Even better, we won't
	 * have issues with long overflow, since Long.MAX_VALUE is quite close to 10^19 and 9^19 << 10^19. */

	static final int MAX_N = 19;
	static long[] tenToThe;

	/* Fill powers of ten up to 18. 10^19 will be stored as LONG.MAX_VALUE. */
	public static void fillTenToThe() {
		tenToThe = new long[MAX_N+1];
		tenToThe[0] = 1;
		for (int i = 1; i < MAX_N; i++) {
			tenToThe[i] = 10*tenToThe[i-1];
		}
		tenToThe[MAX_N] = Long.MAX_VALUE;
	}

	/* Return a^b, guaranteed no long overflow. */
	public static long pow(int a, int b) {
		if (b==1) return a;
		long half = pow(a,b/2);
		return b%2==0? half*half : half*half*a;
	}

	/* Given N, print all N-digit N-th powers. */
	public static void solution(int N) {
		int base = 1;
		while (pow(base,N) < tenToThe[N-1]) base++;
		long x;
		/* Check for long overflow and print solutions as base increments. */
		while (Long.MAX_VALUE/base >= (x=pow(base,N-1)) && (x*=base) < tenToThe[N]) {
			System.out.println(x);
			base++;
		}
	}
	
	public static void main(String[] args) {
		fillTenToThe();
		Scanner s = new Scanner(System.in);
		int N = Integer.parseInt(s.nextLine());
		solution(N);
		s.close();
	}
}
