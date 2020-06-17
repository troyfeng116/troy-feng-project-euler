/* -------- SOLVED -------- */

/* Find minimal sum of N x N array from top left to bottom right, with only R or D moves. */

import java.util.*;
	
public class Euler81 {
	
/* ------- FIRST ATTEMPT: Dynamic programming with steps. I think it's O(n^4); timed out on big tests. -------- */
	
	/*static long ans = Long.MAX_VALUE;
	static long count = 0;
	
	public static void stepDown(int[][] arr, long[][] minimums, int i, int j) {
		if (i == arr.length - 1) ;
		else {
			long current = minimums[i][j];
			int n1 = arr[i+1][j];
			if (current + n1 >= ans) ;
			else if (current + n1 >= minimums[i+1][j]) ;
			else {
				minimums[i+1][j] = current+n1;
				aux(arr, minimums, i+1, j);
			}
		}
	}
	
	public static void stepRight(int[][] arr, long[][] minimums, int i, int j) {
		if (j == arr.length - 1) ;
		else {
			long current = minimums[i][j];
			int n1 = arr[i][j+1];
			if (current + n1 >= ans) ;
			else if (current + n1 >= minimums[i][j+1]) ;
			else {
				minimums[i][j+1] = current+n1;
				aux(arr, minimums, i, j+1);
			}
		}
	}
	
	public static void aux(int[][] arr, long[][] minimums, int i, int j) {
		count++;
		if (i == arr.length-1 && j == arr.length-1) {
			if (minimums[i][j] <= ans) ans = minimums[i][j];
		}
		else {
			if (Math.random() <= 0.5) {
				stepRight(arr, minimums, i, j);
				stepDown(arr, minimums, i, j);
			}
			else {
				stepDown(arr, minimums, i, j);
				stepRight(arr, minimums, i, j);
			}
		}
	}
	
	public static void printArray(long[][] arr) {
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[0].length; j++) {
				System.out.print(arr[i][j] + " ");
			}
			System.out.println();
		}
	}

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int n = Integer.parseInt(s.nextLine());
		int[][] arr = new int[n][n];
		long[][] minimums = new long[n][n];
		for (int i = 0; i < n; i++) {
			String[] inputs = s.nextLine().split(" ");
			for (int j = 0; j < n; j++) {
				arr[i][j] = Integer.parseInt(inputs[j]);
				minimums[i][j] = Long.MAX_VALUE;
			}
		}
		minimums[0][0] = arr[0][0];
		aux(arr, minimums, 0, 0);
		System.out.println(ans);
		System.out.println(count);
		printArray(minimums);
		s.close();
	}*/
	
/* -------- SECOND ATTEMPT: Dynamic programming but with one O(n^2) pass. -------- */
	
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int n = Integer.parseInt(s.nextLine());
		long[][] mins = new long[n][n];
		
		/* Read first row, storing sums. */
		String[] inputs1 = s.nextLine().split(" ");
		mins[0][0] = Integer.parseInt(inputs1[0]);
		for (int i = 1; i < n; i++)
			mins[0][i] = Integer.parseInt(inputs1[i]) + mins[0][i-1];
		
		/* Read second row, tracking minimum sum. */
		for (int i = 1; i < n; i++) {
			String[] inputs2 = s.nextLine().split(" ");
			mins[i][0] = mins[i-1][0] + Integer.parseInt(inputs2[0]);
			for (int j = 1; j < n; j++) {
				int entry = Integer.parseInt(inputs2[j]);
				mins[i][j] = mins[i-1][j]<=mins[i][j-1] ? mins[i-1][j]+entry : mins[i][j-1]+entry;
			}
		}
		System.out.println(mins[n-1][n-1]);
		s.close();
	}
	
}
