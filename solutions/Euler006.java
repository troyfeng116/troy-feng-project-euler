/* -------- SOLVED -------- */

/* The sum of the squares of the first ten natural numbers is, 1^2 + 2^2 + 3^2 + ... + 10^2 = 385. The square of the 
 * sum of the first ten natural numbers is, (1+2+...+10)^2 = 55^2 = 3025. Hence the absolute difference between the 
 * sum of the squares of the first ten natural numbers and the square of the sum is 3025 - 385 = 2640.
 * 
 * Find the absolute difference between the sum of the squares of the first N natural numbers and the square of the 
 * sum.
 * 
 * INPUT: 1 <= T <= 10^4, 1 <= N <= 10^4 */

import java.math.BigInteger;
import java.util.Scanner;

public class Euler006 {
	
	/* Approach (easy): use sum of squares formula and Gauss's formula. 
	 * 
	 * Less cheap approach: 2 times product of all distinct pairs. Since it's integers from 1->N, should be a clean O(n) 
	 * approach. */	
	
	/* The cheap approach. */
	public static BigInteger diff1(BigInteger n) {
		BigInteger sumSquares = n.multiply(n.add(BigInteger.ONE)).multiply(n.add(n).add(BigInteger.ONE)).divide(new BigInteger("6"));
		BigInteger sum = n.multiply(n.add(BigInteger.ONE)).divide(new BigInteger("2"));
		BigInteger squareSum = sum.multiply(sum);
		return squareSum.subtract(sumSquares);
	}
	
	
	/* Less cheap but slower approach. Use half of pyramid of all combos (i,j) where 1 <=i,j <= N. */
	public static BigInteger diff2(BigInteger n) {
		BigInteger ans = BigInteger.ZERO;
		BigInteger twoConst = BigInteger.ONE.add(BigInteger.ONE);
		for (BigInteger i = n; i.compareTo(twoConst) >= 0; i = i.subtract(BigInteger.ONE)) {
			ans = ans.add(i.multiply(i).multiply(i.subtract(BigInteger.ONE)).divide(twoConst));
		}
		return ans.add(ans);
	}
	
	
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int t = Integer.parseInt(s.nextLine());
		for (int i = 0; i < t; i++) {
			String input = s.nextLine();
			BigInteger n = new BigInteger(input);
			System.out.println(diff1(n));
			//System.out.println(diff2(n));
		}
		s.close();
	}

}
