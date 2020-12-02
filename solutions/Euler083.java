/* -------- SOLVED -------- */

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
import java.util.PriorityQueue;
import java.util.Comparator;

public class Euler083 {

    /*
     * Thoughts/approach: Dijkstra's shortest path algorithm. Compress input into
     * array of N^2 elements. Then from node u, there is an edge (u,v) for v = u+1,
     * u-1, u+N, and u-N (barring edges/corners) with weight v. Start Dijkstra's
     * from index 0 and dist[0] = arr[0].
     */

    private static void dijkstra(long[] dist, int[] arr, int N) {
        // Min heap: (k,v) = (dist[u], u) with priority k = dist[u]
        PriorityQueue<Pair> pq = new PriorityQueue<>(new PairComparator());
        pq.offer(new Pair(arr[0], 0));
        boolean[] visited = new boolean[N * N];
        while (!pq.isEmpty()) {
            int u = pq.poll().second;
            visited[u] = true;
            // For each neighbor v of u that hasn't been visited, check if dist[v] can be
            // updated
            if (u % N != 0 && !visited[u - 1]) {
                if (dist[u] + arr[u - 1] < dist[u - 1]) {
                    dist[u - 1] = dist[u] + arr[u - 1];
                    pq.offer(new Pair(dist[u - 1], u - 1));
                }
            }
            if (u % N != N - 1 && !visited[u + 1]) {
                if (dist[u] + arr[u + 1] < dist[u + 1]) {
                    dist[u + 1] = dist[u] + arr[u + 1];
                    pq.offer(new Pair(dist[u + 1], u + 1));
                }
            }
            if (u >= N && !visited[u - N]) {
                if (dist[u] + arr[u - N] < dist[u - N]) {
                    dist[u - N] = dist[u] + arr[u - N];
                    pq.offer(new Pair(dist[u - N], u - N));
                }
            }
            if (u < N * (N - 1) && !visited[u + N]) {
                if (dist[u] + arr[u + N] < dist[u + N]) {
                    dist[u + N] = dist[u] + arr[u + N];
                    pq.offer(new Pair(dist[u + N], u + N));
                }
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
        Arrays.fill(dist, Long.MAX_VALUE);
        dist[0] = arr[0];
        dijkstra(dist, arr, N);
        System.out.println(dist[N * N - 1]);
        s.close();
    }
}

// (k,v) = (dist, index)
class Pair {
    long first;
    int second;

    public Pair(long f, int s) {
        first = f;
        second = s;
    }
}

class PairComparator implements Comparator<Pair> {
    @Override
    public int compare(Pair x, Pair y) {
        return x.first < y.first ? -1 : x.first > y.first ? 1 : 0;
    }
}