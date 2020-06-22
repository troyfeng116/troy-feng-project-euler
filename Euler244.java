/* -------- UNSOLVED: 44.44/100 --------*/

/* Based on Fifteen Puzzle. In this case, it's NxN board. (N^2-1)/2 red tiles, N^2/2 blue tiles.
 * 
 * A move L,U,R,D means you move the white tile to the other way. (i.e. L means you switch white with tile
 * to the right).
 * 
 * For a move sequence m1m2...mk, checksum is defined as: checksum0 = 0, checksumi = (checksum{i-1} + mi) mod 100000007,
 * where mi is the ASCII value of the mi \in [LURD].
 * 
 * Given starting config S and end E, find all shortest paths from S to E. Find sum of all checksums for paths of
 * minimum length.
 * 
 * 
 * CONSTRAINTS: 2 <= N <= 4. Input given as N, then N lines for S, then N lines for E. 
 * 
 * L = 76, R = 82, U = 85, D = 68 
 * 
 * Sample: 
 * 
4
WRBB
RRBB
RRBB
RRBB
RRBB
RBBB
RWRB
RRBB

 * should return 91440058.
*/

//import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
//import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

public class Euler244 {
	
	/* Approach: BFS. Once a solution is found, stop all other solutions that exceed its steps.
	 * 
	 * BFS takes: current config as array of chars, current white index, length of path, and target. 
	 * 
	 * In general, a config will be of length NxN + 1, and config(0) will be the length of the current config. 
	 * When adding next to queue, increment length. 
	 * Store visited configs. */
	
	/* NOTES: 
	 * 		bi-directional search?
	 * 		How to store paths without creating new "full" array?
	 * 		Store configs as ints? (supports 16 digits...) */
	
	static class Entry {
		int[] config; /* Configuration of tiles. */
		//int[] path; /* Sequence of L|U|R|D to reach config. */
		int length;
		int checkSum;
		
		public Entry(int[] config, int length, int checkSum) {
			this.config = config;
			this.length = length;
			this.checkSum = checkSum;
		}
	}
	
	static final int MOD = 100000007;
	
	static Set<int[]> visited; // Track visited configs. No path after, just n*n visited configs.
		
	static int minimum; // Store minimum steps.
	
	static int n; // Store dimension globally.
	
	static boolean found = false;
	
	static long sum = 0;
	
	/* Check if pos can be swapped with nextPos in board. If true, swaps them. */
	public static boolean reachable(int pos, int next) {
		if (pos == next) return false;
		int p1 = pos+1; /* RIGHT */
		int p2 = pos-1; /* LEFT */
		int p3 = pos+n; /* DOWN */
		int p4 = pos-n; /* UP */
		/* Top left corner. */
		if (pos == 0) return next == p1 || next == p3;
		/* Top right corner. */
		if (pos == n-1) return next == p2 || next == p3;
		/* Bottom left corner. */
		if (pos == n*n-n) return next == p1 || next == p4;
		/* Bottom right corner. */
		if (pos == n*n-1) return next == p2 || next == p4;
		/* Top edge. */
		if (pos < n ) return next == p1 || next == p2 || next == p3;
		/* Bottom edge. */
		if (pos > n*n-n ) return next == p1 || next == p2 || next == p4;
		/* Left edge. */
		if (pos % n == 0) return next == p1 || next == p3 || next == p4;
		/* Right edge. */
		if (pos % n == n-1) return next == p2 || next == p3 || next == p4;
		/* Middle. */
		return next == p1 || next == p2 || next == p3 || next == p4;
	}
	
	/* Given n dimension of board, return direction of shift from pos to next. */
	public static int getDirection(int pos, int next) {
		if (next == pos+1) return (int) 'L';
		if (next == pos-1) return (int) 'R';
		if (next == pos+n) return (int) 'U';
		return (int) 'D';
	}
	
	/* IndexOf helper. Only searches first n*n elements corresponding to board. */
	public static int indexOf(int[] arr, int target) {
		for (int i = 0; i < n*n; i++) {
			if (arr[i] == target) return i;
		}
		return -1;
	}
	
	/* BFS. */
	/* Pseudocode: While queue not empty, remove head of queue. Add all reachable non visited configs from that head to queue.
	 * Visited tracks configs (length n). Paths tracks paths (length minimum).
	 * In queue, stored are configs reached by unique paths. First n*n elements are the config. Following indices store the path. */
	public static void bfs(int[] config, int[] target) {
		Queue<Entry> q = new LinkedList<Entry>();
		Entry first = new Entry(config, 0, 0);
		q.add(first);
		while (!q.isEmpty()) {
			Entry e = q.remove();
			int length = e.length;
			int[] currentConfig = e.config;
			
			if (length >= minimum) break;
			int pos = indexOf(currentConfig, 87); /* ALERT: CAN REDUCE RUNTIME HERE */
			int[] test = new int[] {pos-1, pos+1, pos-n, pos+n};
			//System.out.println("pos: " + pos);
			for (int i: test) {//= 0; i < n*n && (i == pos-1 || i == pos+1 || i == pos+n || i == pos-n); i++) {
				/* For each reachable position, test if target or add to queue. */
				if (reachable(pos, i)) {
					/*int[] newConfig = new int[n*n];
					for (int k = 0; k < n*n; k++)
						newConfig[k] = currentConfig[k];*/
					int[] newConfig = Arrays.copyOf(currentConfig, currentConfig.length);
					
					/*System.out.print("newConfig before swap: ");
					for (int p = 0; p < n*n; p++)
						System.out.print(newConfig[p] + " ");
					System.out.println();*/
					
					newConfig[pos] = newConfig[i];
					newConfig[i] = (int) 'W';
					int direction = getDirection(pos, i);
					int newCheckSum = (int) ((((long) e.checkSum * 243) + direction) % MOD);
					
					/*System.out.print("newConfig after swap with " + i + ": ");
					for (int p = 0; p < n*n; p++)
						System.out.print(newConfig[p] + " ");
					System.out.println();*/
					
					if (!Arrays.equals(newConfig, target)) {
						if (visited.contains(newConfig)) ;
						else {
							visited.add(newConfig);
							
							
							/*int[] full = new int[n*n + length + 1];
							for (int x = 0; x < n*n; x++) {
								full[x] = newConfig[x];
							}
							for (int x = n*n; x < currentConfig.length; x++)
								full[x] = currentConfig[x];
							full[currentConfig.length] = direction;*/
							
							/*System.out.print("full after adding direction " + direction + ": ");
							for (int p = 0; p < full.length; p++)
								System.out.print(full[p] + " ");
							System.out.println();*/
							
							q.add(new Entry(newConfig, length+1, newCheckSum));
						}
					}
					else {
						if (!found) {
							found = true;
							minimum = length+1;
						}
						//int[] path = new int[length+1];
						sum += newCheckSum;
						sum = sum % MOD;
					}
				}
			}
		}
	}
	
	
	public static void main(String[] args) {
		visited = new HashSet<int[]>();
		minimum = Integer.MAX_VALUE;
		Scanner s = new Scanner(System.in);
		n = Integer.parseInt(s.nextLine());
		/* Read and store start. */
		int[] start = new int[n*n];
		for (int i = 0; i < n; i++) {
			String sInput = s.nextLine();
			for (int j = 0; j < n; j++) {
				start[i*n+j] = (int) sInput.charAt(j);
			}
		}
		//for (int i: start) System.out.print(i + " ");
		visited.add(start);
		/* Read and store end. */
		int[] end = new int[n*n];
		for (int i = 0; i < n; i++) {
			String eInput = s.nextLine();
			for (int j = 0; j < n; j++) {
				end[i*n+j] = (int) eInput.charAt(j);
			}
		}
		//for (int i: end) System.out.print(i + " ");
		bfs(start,end);
		System.out.println(sum);
		s.close();
	}
}
