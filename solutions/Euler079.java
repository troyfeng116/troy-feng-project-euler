/* -------- SOLVED -------- */

/* A common security method used for online banking is to ask the user for three random characters from 
 * a passcode. For example, if the passcode was 531278, they may ask for the 2nd, 3rd, and 5th characters; 
 * the expected reply would be: 317.
 *
 * Given that the three characters are always asked for in order, analyse the file so as to determine the 
 * shortest possible secret passcode of unknown length.
 *
 * Assume all characters in your password are different. You're given T successful login attempts each 
 * containing 3 characters with ASCII codes from 33 to 126 both inclusive. You need to recover the 
 * shortest original password possible. If there are multiple choices, select the lexicographically 
 * smallest one.
 *
 * If something went wrong and the original password cannot be recovered, output SMTH WRONG. ex. 
5
SMH
TON
RNG
WRO
THG
 * should output SMTHWRONG, while
3
an0
n/.
.#a
 * should output SMTH WRONG.
 *
 * INPUTS: 1 <= T <= 3000 */

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class Euler079 {
	
	/* Thoughts/approach: Each T gives us the relative orders of 3 characters in the password. It seems
	 * that we might be able to approach this as a digraph/topsort problem. I.e. if u comes before v, then
	 * we add an edge (u,v) to the graph. Process all T inputs. Then run topological sort. When we add a 
	 * node with in-degree 0, we reset and keep picking the node with in-degree 0 with lowest ASCII. */

	static final String NONE = "SMTH WRONG";
	static final int numNodes = 126-33+1;

	/* Return true if the graph has no more nodes remaining. */
	public static boolean allEdgesGone(int[] indegree) {
		for (int v: indegree) {
			if (v >= 0) return false;
		}
		return true;
	}

	/* Given a digraph in adj matrix form and indegrees of each node, run topological sort. At each step,
	 * find the node with i) smallest ASCII value and ii) indegree 0. Instead of adding to queue for DFS
	 * as in normal topsort, add it to string, remove the node and its outbound edges from graph, and
	 * reset the loop to find the next node to add (to preserve smallest lexicographic order). */
	public static String findPassword(boolean[][] adj, int[] indegree) {
		String ans = "";
		while (!allEdgesGone(indegree)) {
			boolean foundIndegreeZero = false;
			/* Remove first (lexicographically) node with indegree 0 and all its outbound edges by
			 * decrementing indegrees. */
			for (int u = 0; u < indegree.length; u++) {
				if (indegree[u] == 0) {
					indegree[u]--;
					foundIndegreeZero = true;
					for (int v = 0; v < adj[u].length; v++) {
						if (adj[u][v]) indegree[v]--;
					}
					ans += (char)(u+33);
					break;
				}
			}
			/* If there are still nodes to be removed but none with indegree 0, topsort is impossible (i.e.
			 * there is a cycle, so retrieving password is impossible). */
			if (!foundIndegreeZero) break;
		}
		return allEdgesGone(indegree)? ans : NONE;
	}
	
	public static void main(String[] args) {
		/* Adjacency matrix: adj[u][v] is true iff there is an edge (u,v) in digraph. */
		boolean[][] adj = new boolean[numNodes][numNodes];
		/* indegree[v] holds the number of edges that end at v. Note that indegree[-1] means the node
		 * doesn't exist in the graph. */
		int[] indegree = new int[numNodes];
		Arrays.fill(indegree,-1);
		Scanner s = new Scanner(System.in);
		int t = Integer.parseInt(s.nextLine());
		/* Process inputs, fill adj and indegree. */
		for (int t0 = 0; t0 < t; t0++) {
			String input = s.nextLine();
			for (int i = 0; i < 2; i++) {
				int u = input.charAt(i)-33;
				int v = input.charAt(i+1)-33;
				/* Ignore multi-edges to preserve proper indegree (topsort needs DAG). */
				if (adj[u][v]) continue;
				adj[u][v] = true;
				indegree[u] = indegree[u]==-1? 0 : indegree[u];
				indegree[v] = indegree[v]==-1? 1 : indegree[v]+1;
			}
		}
		System.out.println(findPassword(adj,indegree));
		s.close();
	}
}
