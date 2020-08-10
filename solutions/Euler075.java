/* -------- UNSOLVED -------- */

/* It turns out that 12 cm is the smallest length of wire that can be bent to form an integer sided 
 * right angle triangle in exactly one way, but there are many more examples.
 *
 * 12: (3,4,5)
 * 24: (6,8,10)
 * 30: (5,12,13)
 * 36: (9,12,15)
 * 40: (8,15,17)
 * 48: (12,16,20)
 *
 * In contrast, some lengths of wire, like 20 cm, cannot be bent to form an integer sided right angle 
 * triangle, and other lengths allow more than one solution to be found; for example, using 120 cm it 
 * is possible to form exactly three different integer sided right angle triangles.
 * 120: (30,40,50), (20,48,52), (24,45,51)
 *
 * Given that L is the length of the wire, for how many values of L<=N can exactly one integer sided 
 * right angle triangle be formed?
 *
 * INPUTS: 1 <= T <= 10^5, 12 <= N <= 5x10^6 */

import java.util.Scanner;

public class Euler075 {
	
	/* Thoughts/approach: */
	
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int t = Integer.parseInt(s.nextLine());
		for (int t0 = 0; t0 < t; t0++) {
			
		}
		s.close();
	}
}
