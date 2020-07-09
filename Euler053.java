/* -------- UNSOLVED -------- */

/* There are exactly ten ways of selecting three from five:
 *
 * 123, 124, 125, 134, 135, 145, 234, 235, 245, 345
 * 
 * In combinatorics, we use the notation, {5 CHOOSE 3} = 10. In general,
 *
 * {n CHOOSE r} = n! / (r! * (n-r)!)
 *
 * It is not until n=23, that a value exceeds one-million:
 * 
 * {23 CHOOSE 10} = 11440066
 * 
 * How many, not necessarily distinct, values of {n CHOOSE r}, for n <= N, are greater than K? 
 * 
 * INPUTS: 2 <= N <= 1000, 1 <= K <= 10^18, space-separated N K */

import java.util.Scanner;

public class Euler053 {
	
	/* Thoughts/approach: we can use basic combinatorics properties to simplify this quite a bit. First, if
	 * {n CHOOSE r} > K, then {(n+1) CHOOSE r} > K. This means we need not calculate massive binomial
	 * combinatorics. In addition, {n CHOOSE r} = {n CHOOSE (n-r)} (trivially). This symmetry will definitely 
	 * be useful as well, as we need only loop r up to n/2.
	 *
	 * Those who have studied binomial theorem and Pascal's triangle are probably familiar with the identity
	 * {n CHOOSE r} + {n CHOOSE (r+1)} = {(n+1) CHOOSE (r+1)}; this can also be proved quickly from the
	 * factorial definition. So we can probably devise a Fibonnaci-esque approach to generating {n CHOOSE r}.
	 *
	 * Finally, as Long.MAX_VALUE is around 9x10^18, I think we'll be able to detect and avoid long overflow 
	 * as we go. */
	
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		String[] inputs = s.nextLine().split(" ");
		int N = Integer.parseInt(inputs[0]);
		long K = Integer.parseInt(inputs[1]);
		
		s.close();
	}
}
