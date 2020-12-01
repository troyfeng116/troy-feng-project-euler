/* -------- SOLVED -------- */

/*
The minimal path sum in the 5x5 matrix below, by starting in any cell in the left column and finishing in any 
cell in the right column, and only moving up, down, and right, is indicated by asterisks. The sum is equal 
to 994.

131  673 234  *103 *18
*201 *96 *342 965  150
630  803 746  422  111
537  699 497  121  956
805  732 524  37   331

Find the minimum path sum in given matrix.

INPUTS: 1 <= N <= 1000, N lines of N space-separated entries, 1 <= matrix entries <= 10^9
*/

import java.util.Scanner;
import java.util.Arrays;

public class Euler082 {
    /*
     * Thoughts/approach: DP. dp[i][j] = min(dp[i][j-1], dp[i-1][j], dp[i+1][j]) +
     * matrix[i][j] = minimal path to (i,j). Need to be careful about filling dp;
     * once top down and once bottom up. Moving down from top row could give minimal
     * path to second row andmoving up from bottom row could give minimal path to
     * secont-to-last row, etc.
     */

    private static long solution(int[][] matrix) {
        int N = matrix.length;
        long[][] dp = new long[N][N];
        // First column is minimum starting point.
        for (int r = 0; r < N; r++) {
            dp[r][0] = matrix[r][0];
        }
        for (int c = 1; c < N; c++) {
            // Move right
            for (int r = 0; r < N; r++) {
                dp[r][c] = dp[r][c - 1] + matrix[r][c];
            }
            // Top-down
            for (int r = 1; r < N; r++) {
                dp[r][c] = Math.min(dp[r][c], dp[r - 1][c] + matrix[r][c]);
            }
            // Bottom-down
            for (int r = N - 2; r >= 0; r--) {
                dp[r][c] = Math.min(dp[r][c], dp[r + 1][c] + matrix[r][c]);
            }
        }
        // Min of right-most row
        long min = dp[0][N - 1];
        for (int r = 1; r < N; r++) {
            min = Math.min(min, dp[r][N - 1]);
        }
        return min;
    }

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int N = Integer.parseInt(s.nextLine());
        int[][] matrix = new int[N][N];
        for (int row = 0; row < N; row++) {
            String[] input = s.nextLine().split(" ");
            for (int col = 0; col < N; col++) {
                matrix[row][col] = Integer.parseInt(input[col]);
            }
        }
        System.out.println(N == 1 ? matrix[0][0] : solution(matrix));
        s.close();
    }
}
