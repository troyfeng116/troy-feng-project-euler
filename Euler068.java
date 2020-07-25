/* -------- UNSOLVED -------- */

/* Consider the following "magic" 3-gon ring, filled with the numbers 1 to 6, and each line adding to nine.
 *
 *		4
 *		 \
 *		  3
 *		 / \
 *		1---2---6
 *	   /
 *	  5
 *
 * Working clockwise, and starting from the group of three with the numerically lowest external node (4,3,2 
 * in this example), each solution can be described uniquely. For example, the above solution can be described
 * by the set: 4,3,2; 6,2,1; 5,1,3.
 *
 * It is possible to complete the ring with four different totals: 9, 10, 11, and 12. There are eight solutions
 * in total.
 *
 *	TOTAL 		SOLUTION SET
 *	9			4,2,3; 5,3,1; 6,1,2
 *	9			4,3,2; 6,2,1; 5,1,3
 *	10			2,3,5; 4,5,1; 6,1,3
 *	10			2,5,3; 6,5,3; 4,1,5
 *	11			1,4,6; 3,6,2; 5,2,4
 *	11			1,6,4; 5,4,2; 3,2,6
 *	12			1,5,6; 2,6,4; 3,4,5
 *	12			1,6,5; 3,5,4; 2,4,6
 *
 * By concatenating each group it is possible to form 9-digit strings; the strings for a 3-gon ring where 
 * total is 9 are 423531612 and 432621513.
 *
 * Given N, which represents the N-gon and the total S print all concatenated solution strings in alphabetical 
 * sorted order.
 *
 * Note: It is guaranteed that solution will exist for testcases.
 *
 * INPUTS: 3 <= N <= 10, S s.t. solution exists, space separated N S */

import java.util.Scanner;

public class Euler068 {
	
	/* Thoughts/approach: */
	
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int t = Integer.parseInt(s.nextLine());
		for (int t0 = 0; t0 < t; t0++) {
			
		}
		s.close();
	}
}
