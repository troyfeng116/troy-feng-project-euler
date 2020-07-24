/* -------- UNSOLVED -------- */

/* Consider quadratic Diophantine equations of the form:
 *
 *		x^2 - Dy^2 = 1
 *
 * For example, when D=13, the minimal solution in x is 649^2 - 13*180^2 = 1. It can be assumed that there 
 * are no solutions in positive integers when D is square.
 *
 * By finding minimal solutions in x for D=2,3,5,6,7, we obtain the following:
 *
 *		3^2 - 2*2^2 = 1
 *		7^2 - 3*4^2 = 1
 *		9^2 - 5*4^2 = 1
 *		5^2 - 6*2^2 = 1
 *		3^2 - 7*2^2 = 1
 *
 * Hence, by considering minimal solutions in x for D<=7, the largest x is obtained when D=5.
 *
 * Find the value of D<=N in minimal solutions of x for which the largest value of x is obtained.
 *
 * INPUTS: 7 <= D <= 10^4 */

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class Euler066 {
	
	/* Thoughts/approach: we can implement a quick brute force solution that loops over y until 1+Dy^2 is
	 * a perfect square. After doing so, we see the max minimal solution for D <= 100 is x = 1766319049,
	 * and it's no surprise we time out for bigger N. 
	 *
	 * I guess problems 64 and 65 come right before 66 for a reason, as the class of Diophantine equations
	 * of the form x^2 - Dy^2 = 1 is called Pell's equation, which is related very closely to square root 
	 * continued fractions (which makes sense, as x/y approximates sqrt(D)). In fact, solutions to Pell's
	 * equation for a fixed D must be (x,y) such that x/y is a term in the convergents of the continued
	 * fraction of sqrt(D).
	 *
	 * For each D, we will need one function to find the cycle of sqrt(n)'s continued fraction, and then 
	 * one function to generate numerators using those continued fraction terms until we find (x,y) 
	 * satisfying Pell's equation for D. */

	/* Given x,y representing x/(sqrt(d)-y), continue fraction. Add next a to list, return (b,c)
	 * after continuing to a + (sqrt(d)-b)/c and add new a to cycle.*/
	public static int[] continueFrac(int x, int y, int d, int a0, List<Integer> cycle) {
		int a = 0;
		int b = y;
		int c = (d-y*y)/x;
		int diff = b+a0;
		a = diff/c;
		b -= a*c;
		b *= -1;
		cycle.add(a);
		return new int[] {b,c};
	}

	/* If sqrt(d) can be written as continued fraction [a0;(a1,...,ak)], return a1,...,ak in list. */
	public static List<Integer> findCycle(int d) {
		List<Integer> ans = new ArrayList<Integer>();
		int a0 = (int) Math.sqrt(d);
		int[] prev = continueFrac(1,a0,d,a0,ans);
		int f1 = prev[0];
		int f2 = prev[1];
		while (true) {
			int[] next = continueFrac(prev[1],prev[0],d,a0,ans);
			if (next[0] == f1 && next[1] == f2) {
				ans.remove(ans.size()-1);
				break;
			}
			prev[0] = next[0];
			prev[1] = next[1];
		}
		return ans;
	}

	public static boolean isPerfectSquare(long x) {
		long s = (long) Math.sqrt(x);
		return s*s==x;
	}

	/* First, find cycle of continued frac for sqrt(d). Then, use recurrence relation for continued fractions.
	 * The n'th numerator p_n is given by p_n = a_n*(p_{n-1}) + p_{n-2}, and same for denominator a_n. We 
	 * have p_0 = a0 and p_1 = a0*a1+1, and q_0 = 1 and q_1 = a1. */
	public static long minimalSolution(int d) {
		List<Integer> cycle = findCycle(d);
		//for (int i: cycle) System.out.print(i + " ");
		//System.out.println();
		int len = cycle.size();
		/* Numerator and denominator two terms ago (a0 and 1, respectively). */
		long x2 = (long) Math.sqrt(d);
		long y2 = 1;
		/* Numerator and denominator one term ago (a0*a1+1 and a1, respectively). */
		long x1 = x2*cycle.get(0)+1;
		long y1 = cycle.get(0);
		/* Current numerator and denominator. */
		long x = x1;
		long y = y1;
		int n = 2;
		while (x*x - d*y*y != 1) {
			//System.out.println(x + "/" + y);
			int i = n%len-1;
			if (i < 0) i += len;
			int a = cycle.get(i);
			//System.out.println("n=" + n + " a=" + a);
			x = a*x1+x2;
			y = a*y1+y2;
			x2 = x1;
			y2 = y1;
			x1 = x;
			y1 = y;
			n++;
		}
		return x;
	}
	
	public static void main(String[] args) {
		//System.out.println(minimalSolution(13));
		//System.exit(0);
		Scanner s = new Scanner(System.in);
		int N = Integer.parseInt(s.nextLine());
		long max = 0;
		long ans = 0;
		for (int d = 2; d <= N; d++) {
			if (isPerfectSquare(d)) continue;
			long res = minimalSolution(d);
			if (res > max) {
				max = res;
				ans = d;
			}
		}
		System.out.println(ans);
		//System.out.println(max);
		s.close();
	}
}
