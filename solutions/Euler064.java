/* -------- SOLVED -------- */

/* All square roots are periodic when written as continued fractions and can be written in the form:
 *
 * 		sqrt(N) = a0 + 1/(a1 + 1/(a2 + 1/(a3 + ...)))
 *
 * For example, let us consider sqrt(23):
 *
 *		sqrt(23) = 4 + sqrt(23)-4 = 4 + 1/ (1/ (sqrt(23)-4)) = 4 + 1/(1 + (sqrt(23) - 3)/7)
 *
 * If we continue we would get the following expansion:
 *
 *		sqrt(23) = 4 + 1/(1 + 1/(3 + 1/(1 + 1/(8 + ...))))
 *
 * The process can be summarised as follows:
 * 		a0 = 4 		1/(sqrt(23)-4) = (sqrt(23)+4)/7 = 1 + (sqrt(23)-3)/7
 * 		a1 = 1		7/(sqrt(23)-3) = 7(sqrt(23)+3)/14 = 3 + (sqrt(23)-3)/2
 *		a2 = 3		2/(sqrt(23)-3) = 2(sqrt(23)+3)/14 = 1 + (sqrt(23)-4)/7
 *		a3 = 1		7/(sqrt(23)-4) = 7(sqrt(23)+3)/7 = 8 + sqrt(23)-4
 *		a4 = 8		1/(sqrt(23)-4) = (sqrt(23)+4)/7 = 1 + (sqrt(23)-3)/7
 *		REPEAT (a4 and a0 same)
 *
 * It can be seen that the sequence is repeating. For conciseness, we use the notation sqrt(23)=[4;(1,3,1,8)],
 * to indicate that the block (1,3,1,8) repeats indefinitely.
 *
 * The first ten continued fraction representations of (irrational) square roots are:
 *
 * 		sqrt(2) = [1;(2)] 			period=1
 *		sqrt(3) = [1;(1,2)] 		period=2
 *		sqrt(5) = [2;(4)]			period=1
 *		sqrt(6) = [2;(2,4)]			period=2
 *		sqrt(7) = [2;(1,1,1,4)]		period=4
 *		sqrt(8) = [2;(1,4)]			period=2
 *		sqrt(10) = [3;(6)]			period=1
 *		sqrt(11) = [3;(3,6)]		period=2
 *		sqrt(12) = [3;(2,6)]		period=2
 *		sqrt(13) = [3;(1,1,1,1,6)]	period=5
 *
 * Exactly four continued fractions, for x<=13, have an odd period. How many continued fractions for x<=N have
 * an odd period? 
 *
 * INPUTS: 10 <= N <= 30000 */

import java.util.Scanner;
import java.util.Arrays;

public class Euler064 {
	
	/* Thoughts/approach: The problem description is a bit long-winded. Basically, we are using continued
	 * fractions to approximate square roots, and we represent continued fractions using this [a0;(a1,...,ak)]
	 * format, where a0 is floor(sqrt(x)) and a1,...,ak is the repeating sequence of terms in the continued
	 * fraction. Given x, we start with a0 to approximate sqrt(x), then rationalize the denominator a bunch
	 * of times. At each step, we have an intermediate fraction of the form m/(sqrt(x) - n); once m and n
	 * repeat, we have a cycle. Rationalizing gives m(sqrt(x)+n)/(x-n^2). We want to write as 
	 * a+(sqrt(x) - b)/p. I'm thinking we can have a rationalize function that takes in (a,b) to rationalize
	 * something of the form a/(sqrt(n) - b), and returns (a,b,c) where a + (sqrt(n) - b)/c is the
	 * rationalized form. Then we continue with (b,c). Each step is essentially a new approximation.
	 *
	 * Note that all cycles start right after the first a0 approximation. */

	/* Continue fraction x/(sqrt(n) - y) to the form a + (sqrt(n)-b)/c, where b is largest integer <= a0.
	 * Return int[] {a,b,c}. */
	public static int[] rationalize(int x, int y, int n, int a0) {
		int a = 0;
		int b = y;
		int c = (n-y*y)/x;
		/* Now we have expression of form 0 + (sqrt(n) + b)/c. Subtract b from p until -a0 <= b < 0, and
		 * increment a while doing so (essentially subtracting c/c big 1). */
		int diff = b+a0;
		a = diff/c;
		b -= a*c;
		b *= -1;
		return new int[] {a,b,c};
	}

	/* Return cycle of sqrt(n). a0 is initial approximation. */
	public static int findCycle(int n, int a0) {
		/* At the start of the first iteration, we have a0 + (sqrt(n) - a0). So we have to continue
	 	 * 1/(sqrt(n) - a0) first. */
		int[] prev = rationalize(1, a0, n, a0);
		int m1 = prev[1];
		int m2 = prev[2];
		int count = 0;
		while (true) {
			count++;
			/* If (a,b,c) correspond to a + (sqrt(n)-b)/c from previous iteration, we now want to
			 * continue c/(sqrt(n) - b). */
			int[] next = rationalize(prev[2], prev[1], n, a0);
			/* If we reach the point where b and c repeat, cycle must reset. */
			if (next[1] == m1 && next[2] == m2) break;
			prev = Arrays.copyOf(next,3);
		}
		return count;
	}
	
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int N = Integer.parseInt(s.nextLine());
		int ans = 0;
		for (int n = 2; n <= N; n++) {
			int a0 = (int) Math.sqrt(n);
			if (a0*a0 != n && findCycle(n,a0)%2 == 1) ans++;
		}
		System.out.println(ans);
		s.close();
	}
}
