/* -------- SOLVED -------- */

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
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Euler068 {
	
	/* Thoughts/approach: We have to place the numbers 1 through N in an N-gon ring such that each of the
	 * sequences of 3 integers (1 outer, 2 inner) add to S. I think I will represent the inner and outer rings
	 * as arrays of N integers. inner[0],...,inner[N-1] will be the inner ring in clockwise order. outer[i] 
	 * will be the outer ring corresponding to the two inner rings with inner[i] as the further node. I.e. 
	 * for N=5, inner[0], inner[1], and outer[0] will form a sum, inner[4], inner[0], and outer[4] will form a
	 * sum, etc. Finally, we can fill inner[] one element at a time, backtracking when we run out of numbers 
	 * to fill. 
	 *
	 * First, we place inner[0]. Then, we attempt to place all numbers greater than inner[0] into the inner 
	 * ring. Placing each additional inner[i] must fix outer[i-1]. */

	/* Given a complete inner and outer ring, find the minimum outer node, and concatenate all triples into
	 * a result string in format specified in problem statement. */
	public static String generateString(int[] inner, int[] outer) {
		int N = inner.length;
		int min = Integer.MAX_VALUE;
		int minIndex = 0;
		for (int i = 0; i < N; i++) {
			if (outer[i] < min) {
				min = outer[i];
				minIndex = i;
			}
		}
		String ans = "";
		for (int i = minIndex; i >= 0; i--) {
			if (i == N-1) ans += outer[i]+""+inner[0]+""+inner[i];
			else ans += outer[i]+""+inner[i+1]+""+inner[i];
		}
		for (int i = N-1; i > minIndex; i--) {
			if (i == N-1) ans += (outer[i]+""+inner[0]+""+inner[i]);
			else ans += (outer[i]+""+inner[i+1]+""+inner[i]);
		}
		return ans;
	}

	/* Attempt to fill i'th spot in inner ring. */
	public static void recurse(int S, int min, int[] inner, int i, int[] outer, boolean[] used, List<String> ans) {
		int N = inner.length;
		/* Base case: we've placed the entire inner ring and only need to place the last outer node */
		if (i == N) {
			int target = S-inner[i-1]-inner[0];
			if (target > 0 && target <= 2*N && !used[target]) {
				outer[N-1] = target;
				String result = generateString(inner, outer);
				ans.add(result);
				outer[N-1] = 0;
			}
			return;
		}
		/* For each value x still available to put in inner[i] */
		for (int x = min; x <= 2*N && x <= S-3; x++) {
			/* target is the value that must be placed in outer[i-1], since outer[i-1]+inner[i]+inner[i-1]=S */
			int target = S-inner[i-1]-x; 
			if (!used[x] && target != x && target > 0 && target <= 2*N && !used[target]) {
				inner[i] = x;
				outer[i-1] = target;
				used[x] = true;
				used[target] = true;
				recurse(S, min, inner, i+1, outer, used, ans);
				/* Backtrack. */
				inner[i] = 0;
				outer[i-1] = 0;
				used[x] = false;
				used[target] = false;
			}
		}
	}

	/* Given N and S, return set of Strings in format specified in problem statement. */
	public static List<String> solution(int N, int S) {
		int[] inner = new int[N];
		int[] outer = new int[N];
		boolean[] used = new boolean[2*N+1];
		List<String> ans = new ArrayList<String>();
		/* Place all possible minimum numbers in inner[0] and search for solutions. */
		for (int min = 1; min <= 2*N && min <= S-3; min++) {
			inner[0] = min;
			used[min] = true;
			recurse(S, min, inner, 1, outer, used, ans);
			used[min] = false;
		}
		return ans;
	}
	
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		String[] inputs = s.nextLine().split(" ");
		int N = Integer.parseInt(inputs[0]);
		int S = Integer.parseInt(inputs[1]);
		List<String> result = solution(N,S);
		Collections.sort(result);
		for (String str: result) System.out.println(str);
		s.close();
	}
}
