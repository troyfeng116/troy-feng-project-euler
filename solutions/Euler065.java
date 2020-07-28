/* -------- SOLVED -------- */

/* The square root of 2 can be written as an infinite continued fraction.
 *
 *		sqrt(2) = 1 + 1/(2 + 1/(2 + 1/(2 + ...)))
 *
 * The infinite continued fraction can be written as sqrt(2) = [1;(2)], where (2) indicates that 2 repeats 
 * ad infinitum. In a similar way, sqrt(23) = [4;(1,3,1,8)].
 *
 * It turns out that the sequence of partial values of continued fractions for square roots provide the 
 * best rational approximations. Let us consider the convergents for sqrt(2):
 *
 *		1 + 1/2 = 3/2
 *		1 + 1/(2 + 1/2) = 7/5
 * 		1 + 1/(2 + 1/(2 + 1/2)) = 17/12
 * 		1 + 1/(2 + 1/(2 + 1/(2 + 1/2))) = 41/29
 *
 * Hence the sequence of the first ten convergents for sqrt(2) are:
 *
 *		1, 3/2, 7/5, 17/12, 41/29, 99/70, 239/169, 577/408, 1393/985, 3363/2378, ...
 *
 * What is most surprising is that the important mathematical constant, e, can be written as:
 *	
 *		e = [2; 1,2,1, 1,4,1, 1,6,1, ..., 1,2k,1, ...]
 *
 * The first ten terms in the sequence of convergents for e are:
 *
 *		2, 3, 8/3, 11/4, 19/7, 87/32, 106/39, 193/71, 1264/465, 1457/536, ...
 *
 * The sum of digits in the numerator of the 10th convergent is 1 + 4 + 5 + 7 = 17.
 *
 * Find the sum of digits in the numerator of the N'th convergent of the continued fraction for e. 
 *
 * INPUTS: 1 <= N <= 30000 */

import java.util.Scanner;
import java.math.BigInteger;

public class Euler065 {
	
	/* Thoughts/approach: There's a nice recursive relation between consecutive numerators and denominators of
	 * continued fraction convergents. Let u_n denote n'th iteration of continued fraction with [a0,a1,...]
	 * Note u0 = a0. u1 = a0 + 1/a1 = (a0a1 + 1)/a1. And u2 = a0 + 1/(a1 + 1/a2) = (a2(a1a0+1)+a0)/(a2a1+1).
	 * Let p_n be the n'th numerator and q_n be the n'th denominator. If we keep expanding, and use induction, 
	 * we conclude the p_n = a_n * p_{n-1} + p_{n-2} and q_n = a_n * q_{n-1} + q_{n-2}, with p_0 = a0, 
	 * p_1 = a0*a1+1 and q_0 = 1, q_1 = a_1.
	 *
	 * For e, we're given a0 = 2, a1 = 1, a2 = 2, a3 = 1. Then, if k%3=0 or k%3=1, a_k = 1, else a_k = 2*(k/3+1). 
	 * Finally, we're gonna need BigInteger. */

	/* Given N, return sum of digits in numerator of N'th convergent of e. */
	public static int solution(int N) {
		/* Edge case: first two numerators are 2 and 3. */
		if (N <= 2) return N+1;
		BigInteger n2 = new BigInteger("2"); /* Numerator 2 terms ago. Initialized to a0 = 2 for e. */
		BigInteger n1 = new BigInteger("3"); /* Numerator 1 term ago. Initialized to a1*a0+1 = 3 for e. */
		BigInteger numerator = new BigInteger("3"); /* Current numerator term. */
		/* Generate k'th (zero-based) numerator using recursive relation. */
		for (int k = 2; k < N; k++) {
			BigInteger n = k%3==2 ? new BigInteger(Integer.toString(2*(k/3+1))) : BigInteger.ONE;
			numerator = n.multiply(n1).add(n2);
			n2 = n1;
			n1 = numerator;
		}
		String res = numerator.toString();
		int ans = 0;
		for (int i = 0; i < res.length(); i++) {
			ans += res.charAt(i)-48;
		}
		return ans;
	}
	
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int N = Integer.parseInt(s.nextLine());
		System.out.println(solution(N));
		s.close();
	}
}
