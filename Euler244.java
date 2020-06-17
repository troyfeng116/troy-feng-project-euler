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
	
	static Set<int[]> visited; // Track visited configs. No path after, just n*n visited configs.
	
	//static List<int[]> paths; // Store minimum paths, included after n*n terms.
	
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
		Queue<int[]> q = new LinkedList<int[]>();
		q.add(config);
		while (!q.isEmpty()) {
			int[] currentConfig = q.remove();
			int length = currentConfig.length - n*n;
			//System.out.println("length: " + length);
			//System.out.println("minimum: " + minimum);
			if (length >= minimum) break;
			int pos = indexOf(currentConfig, 87);
			int[] test = new int[] {pos-1, pos+1, pos-n, pos+n};
			//System.out.println("pos: " + pos);
			for (int i: test) {//= 0; i < n*n && (i == pos-1 || i == pos+1 || i == pos+n || i == pos-n); i++) {
				/* For each reachable position, test if target or add to queue. */
				if (reachable(pos, i)) {
					/*int[] newConfig = new int[n*n];
					for (int k = 0; k < n*n; k++)
						newConfig[k] = currentConfig[k];*/
					int[] newConfig = Arrays.copyOfRange(currentConfig, 0, n*n);
					
					/*System.out.print("newConfig before swap: ");
					for (int p = 0; p < n*n; p++)
						System.out.print(newConfig[p] + " ");
					System.out.println();*/
					
					newConfig[i] = (int) 'W';
					newConfig[pos] = currentConfig[i];
					
					/*System.out.print("newConfig after swap with " + i + ": ");
					for (int p = 0; p < n*n; p++)
						System.out.print(newConfig[p] + " ");
					System.out.println();*/
					
					if (!Arrays.equals(newConfig, target)) {
						if (visited.contains(newConfig)) ;
						else {
							visited.add(newConfig);
							int direction = getDirection(pos, i);
							int[] full = new int[n*n + length + 1];
							for (int x = 0; x < n*n; x++) {
								full[x] = newConfig[x];
							}
							for (int x = n*n; x < currentConfig.length; x++)
								full[x] = currentConfig[x];
							full[currentConfig.length] = direction;
							
							/*System.out.print("full after adding direction " + direction + ": ");
							for (int p = 0; p < full.length; p++)
								System.out.print(full[p] + " ");
							System.out.println();*/
							
							q.add(full);
							
						}
					}
					else {
						if (!found) {
							found = true;
							minimum = length+1;
						}
						int direction = getDirection(pos, i);
						//int[] path = new int[length+1];
						long pathSum = 0;
						for (int p = 0; p < length; p++) {
							pathSum = (pathSum * 243 + currentConfig[n*n+p]) % 100000007;
							//path[p] = currentConfig[n*n+p];
						}
						
						//path[length] = direction;
						pathSum = (pathSum * 243 + direction) % 100000007;
						sum += pathSum;
						//paths.add(path);
						//System.out.println("added path YES");
					}
		//P		
					/*if (Arrays.equals(newConfig, target)) {
						if (!found) {
							found = true;
							minimum = length+1;
						}
						int direction = getDirection(pos, i);
						int[] path = new int[length+1];
						for (int p = 0; p < length; p++)
							path[p] = currentConfig[n*n+p];
						path[length] = direction;
						paths.add(path);
						//System.out.println("added path YES");
					}
					else if (visited.contains(newConfig)) ;
					else {
						visited.add(newConfig);
						int direction = getDirection(pos, i);
						int[] full = new int[n*n + length + 1];
						for (int x = 0; x < n*n; x++) {
							full[x] = newConfig[x];
						}
						for (int x = n*n; x < currentConfig.length; x++)
							full[x] = currentConfig[x];
						full[currentConfig.length] = direction;*/
		//P				
						/*System.out.print("full after adding direction " + direction + ": ");
						for (int p = 0; p < full.length; p++)
							System.out.print(full[p] + " ");
						System.out.println();*/
						
		//P				/*q.add(full);*/
						
						//System.out.println("added to queue");
		//P			}
				}
			}
			//break;
		}
	}
	
	
	public static void main(String[] args) {
		visited = new HashSet<int[]>();
		//paths = new ArrayList<int[]>();
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
		
		long ans = 0;
		/*for (int i = 0; i < paths.size(); i++) {
			long checkSum = 0;
			for (int j = 0; j < paths.get(i).length; j++) {
				checkSum = (checkSum * 243 + paths.get(i)[j]) % 100000007;
				char c = (char) paths.get(i)[j];
				System.out.print(c + " ");
			}
			System.out.println();
			ans += checkSum;
		}*/
		//if (found) System.out.println("FOUNDFUCKYES");
		System.out.println(ans % 100000007);
		System.out.println(sum % 100000007);
		s.close();
	}
}
