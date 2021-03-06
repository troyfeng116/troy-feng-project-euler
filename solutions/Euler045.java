/* -------- SOLVED -------- */

/* Triangle, pentagonal, and hexagonal numbers are generated by the following formulae:
 * 
 * 		Triangle	T_n = n(n+1)/2		1,3,6,10,15,...
 * 		Pentagonal	P_n = n(3n-1)/2		1,5,12,22,35,...
 * 		Hexagonal	H_n = n(2n-1)		1,6,15,28,45,...
 * 
 * It can be verified that T_285 = P_165 = H_143 = 40755.
 * 
 * For this challenge you are given N, a, b, where a < b and a,b \in {3,5,6}
 * where 3 represents triangular numbers, 5 represents pentagonal numbers and 6 is hexagonal. It can be observed 
 * that all hexagonal numbers are triangular numbers so we'll handle only 2 kinds of queries:
 * 		N 3 5 --> find all numbers below N which are triangular as well as pentagonal
 * 		N 5 6 --> find all numbers below N which are pentagonal as well as hexagonal.
 * 
 * INPUT: Three space-separated integers N a b, 2 <= N <= 2x10^14, a<b \in {3,5,6}*/

import java.util.Scanner;

public class Euler045 {
	
	/* Thoughts/approach: We can check if a number is T/P/H in O(1). Hopefully brute forcing all P/H up to N and
	 * checking if T/H respectively will be fast enough...? It should be, since the loop will run in O(sqrt(n)) 
	 * which is on the order of at most a million iterations. */
	
	/* Quick check if x is triangular. */
	public static boolean isTriangular(long x) {
		long sqrt = (long) Math.sqrt(2*x);
		return sqrt * (sqrt+1) == 2*x;
	}
	
	/* Quick check if x is pentagonal using quadratic formula. */
	public static boolean isPentagonal(long x) {
		long y = 1+24*x;
		long sqrt = (long) Math.sqrt(y);
		return sqrt*sqrt == 1+24*x && (1+sqrt)%6 == 0;
	}
	
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		String[] inputs = s.nextLine().split(" ");
		long N = Long.parseLong(inputs[0]);
		int a = Integer.parseInt(inputs[1]);
		if (a == 3) {
			long x;
			/* For each pentagonal x < N, check if triangular. */
			for (int k = 1; (x = ((long) k * (3*k-1))/2) < N; k++) {
				if (isTriangular(x)) System.out.println(x);
			}
		}
		else {
			long x;
			/* For each hexagonal x < N, check if pentagonal. */
			for (int k = 1; (x = ((long) k * (2*k-1))) < N; k++) {
				if (isPentagonal(x)) System.out.println(x);
			}
		}
		s.close();
	}
}
