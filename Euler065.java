/* -------- UNSOLVED -------- */

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
	
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int N = Integer.parseInt(s.nextLine());
		int n2 = 2; /* Numerator 2 terms ago. Initialized to a0 = 2 for e convergent. */
		int n1 = 3; /* Numerator 1 term ago. Initialized to a1*a0+1 = 3 for e convergent. */
		int numerator = 3; /* Current numerator term. */
		for (int k = 2; k < N; k++) {
			int n = k%3==2 ? 2*(k/3+1) : 1;
			numerator = n*n1 + n2;
			n2 = n1;
			n1 = numerator;
		}
		System.out.println(numerator);
		s.close();
	}
}
