/* -------- UNSOLVED -------- */

/* It is possible to show that the square root of two can be expressed as an infinite continued fraction.
 * 
 * sqrt{2} = 1 + 1/(2 + (1/(2 + 1/(2 + 1/(2 + ...))))) = 1.414213...
 * 
 * By expanding this for the first four iterations, we get:
 *
 *		1 + 1/2 = 3/2 = 1.5
 * 		1 + 1/(2 + 1/2) = 7/5 = 1.4
 * 		1 + 1/(2 + 1/(2 + 1/2)) = 17/12 = 1.41666...
 * 		1 + 1/(2 + 1/(2 + 1/(2 + 1/2))) = 41/29 = 1.41379...
 *
 * The next three expansions are 99/70, 239/169, and 577/408, but the eighth expansion, 1393/985, is the 
 * first example where the number of digits in the numerator exceeds the number of digits in the denominator.
 * 
 * Given N, in the first N expansions, print the iteration numbers where the fractions contain a numerator 
 * with more digits than denominator.
 *
 * INPUTS: 8 <= N <= 10^4 */

import java.util.Scanner;
import java.math.BigInteger;

public class Euler057 {
	
	/* Thoughts/approach: Suppose that at the k'th iteration, we have some fraction a/b. The (k+1)th iteration
	 * is equal to 1 + 1/(1 + a/b) = 1 + 1/((a+b)/b) = 1 + b/(a+b) = (a+2b)/(a+b). So if we start with a=1 and
	 * b=1, and iterate by a+=2b and b+=a, we'll generate the sequence of numerator and denominator without
	 * having to actuall deal with any fractions and GCDs and whatnot. As the numerator and denominator can
	 * get pretty big, we'll use BigInteger to avoid headache.
	 *
	 * As a side note, continued fractions in general are super interesting: 
	 * https://en.wikipedia.org/wiki/Continued_fraction */
	
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int n = Integer.parseInt(s.nextLine());
		BigInteger a = BigInteger.ONE;
		BigInteger b = BigInteger.ONE;
		BigInteger twoConst = new BigInteger("2");
		for (int iteration = 0; iteration <= n; iteration++) {
			if (a.toString().length() > b.toString().length()) {
				System.out.println(iteration);
			}
			a = a.add(b.multiply(twoConst));
			b = a.subtract(b);
		}

		s.close();
	}
}
