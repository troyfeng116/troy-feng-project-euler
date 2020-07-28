/* -------- SOLVED -------- */

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
	 * get pretty big, we'll use BigInteger to avoid headache. As String.length() is not efficient, we can
	 * write a more efficient method to count the digits of a BigInteger.
	 *
	 * As a side note, continued fractions in general are super interesting: 
	 * https://en.wikipedia.org/wiki/Continued_fraction */

	/* An efficient way to count the digits of a BigInteger. BigInteger.bitLength() is much faster than
	 * String.length(), and BigInteger.pow is also quite fast. Note log2(n) = log10(n)/log10(2). Thus,
	 * log2(n) * log_10(2) = log_10(n) -> bitLength * log(10)/log(2) ~ digits. */
	public static int numDigits(BigInteger n) {
		double convert = Math.log(2) / Math.log(10);
  		int digits = (int) (convert * n.bitLength() + 1);
  		if (BigInteger.TEN.pow(digits-1).compareTo(n) > 0) {
  			return digits-1;
  		}
  		return digits;
	}
	
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int n = Integer.parseInt(s.nextLine());
		BigInteger a = BigInteger.ONE;
		BigInteger b = BigInteger.ONE;
		BigInteger twoConst = new BigInteger("2");
		for (int iteration = 0; iteration <= n; iteration++) {
			if (numDigits(a) > numDigits(b)) {
				System.out.println(iteration);
			}
			a = a.add(b.multiply(twoConst));
			b = a.subtract(b);
		}

		s.close();
	}
}
