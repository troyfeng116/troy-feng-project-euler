/* -------- UNSOLVED -------- */

/*
In the 5x5 matrix below, the minimal path sum from the top left to the bottom right, by moving left, 
right, up, and down, is indicated in asterisks and is equal to 2297.

*131 673 *234 *103 *18
*201 *96 *342 965  *150
630  803 746  *422 *111
537  699 497  *121 956
805  732 524  *37  *331

Find the minimum path sum in given matrix.

INPUTS: 1 <= N <= 700, N lines of N space-separated entries, 1 <= matrix entries <= 10^9
*/

import java.util.Scanner;
import java.util.Arrays;

public class Euler083 {

    /*
     * Thoughts/approach: Dijkstra's shortest path algorithm. Compress input into
     * array of N^2 elements. Then from node u, there is an edge (u,v) for v = u+1,
     * u-1, u+N, and u-N (barring edges/corners) with weight v.
     */

    private static int findMin(long[] dist, boolean[] removed) {
        long min = Long.MAX_VALUE;
        int index = -1;
        for (int i = 0; i < dist.length; i++) {
            if (!removed[i] && dist[i] < min) {
                min = dist[i];
                index = i;
            }
        }
        return index;
    }

    private static void djikstra(long[] dist, int[] arr, boolean[] removed, int N) {
        int u;
        while ((u = findMin(dist, removed)) >= 0) {
            System.out.println("REMOVED " + u);
            removed[u] = true;
            // For each neighbor v of u that hasn't been removed, check if dist can be
            // updated
            if (u % N != 0 && !removed[u - 1]) {
                dist[u - 1] = Math.min(dist[u] + arr[u - 1], dist[u - 1]);
            }
            if (u % N != N - 1 && !removed[u + 1]) {
                dist[u + 1] = Math.min(dist[u] + arr[u + 1], dist[u + 1]);
            }
            if (u >= N && !removed[u - N]) {
                dist[u - N] = Math.min(dist[u] + arr[u - N], dist[u - N]);
            }
            if (u < N * (N - 1) && !removed[u + N]) {
                dist[u + N] = Math.min(dist[u] + arr[u + N], dist[u + N]);
            }
        }
    }

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int N = Integer.parseInt(s.nextLine());
        int[] arr = new int[N * N];
        for (int row = 0; row < N; row++) {
            String[] input = s.nextLine().split(" ");
            for (int col = 0; col < N; col++) {
                arr[row * N + col] = Integer.parseInt(input[col]);
            }
        }
        long[] dist = new long[N * N];
        boolean[] removed = new boolean[N * N];
        Arrays.fill(dist, Long.MAX_VALUE);
        dist[0] = arr[0];
        djikstra(dist, arr, removed, N);
        System.out.println(dist[N * N - 1]);
        s.close();
    }
}
