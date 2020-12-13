/* -------- SOLVED -------- */

/*
By counting carefully it can be seen that a rectangular grid measuring 3 by 2 contains eighteen rectangles:

https://www.hackerrank.com/contests/projecteuler/challenges/euler085/problem

For each testcase an integer target would be given. Consider all the rectangular grids such that the number 
of rectangles contained in the grid is nearest to target. Out of all such rectangular grids output the area 
of the rectangular grid having the largest area.

INPUTS: 1 <= T <= 10^4, 1 <= target <= 2 x 10^6
*/

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.HashMap;

public class Euler085 {

    /*
     * Thoughts/approach: Given mxn rectangle, if we pick any two unique points on
     * the width and any two unique points on the height, that will give us one
     * unique rectangle. So there are (m C 2) * (n C 2) rectangles in a mxn
     * rectangle. Let R denote the number of subrectangles in a mxn rectangle. We
     * can iterate over m and n until R exceeds target, storing R in a list. We will
     * also hash R to an integer denoting the largest area corresponding to R. This
     * will all be precomputed and memoized. Then, for each input target, we will
     * run binary search to find the closest R to the target and return the hashed
     * area.
     */

    static final int MAX_TARGET = 2000000;
    // Memoize precalculations. table.get(R) = max area m*n s.t. mxn rectangle has R
    // subrectangles
    static Map<Integer, Integer> table;
    // Maintain list of all R values (# subrectangles). Will stop computing once R
    // >= MAX_TARGET.
    static List<Integer> list;

    private static void fillTableAndList() {
        table = new HashMap<>();
        list = new ArrayList<>();
        // Once m = n = 53, that 53x53 rectangle would have 2047761 subrectangles, just
        // over MAX_TARGET. However, a 1 by 1999 rectangle also stays under MAX_TARGET.
        // So m will loop to 53, and n will loop from m to 2000.
        for (int m = 1; m <= 53; m++) {
            for (int n = m; n <= 2000; n++) {
                int mC2 = m * (m + 1) / 2;
                int nC2 = n * (n + 1) / 2;
                int x = mC2 * nC2;
                if (!table.containsKey(x))
                    list.add(x);
                table.put(x, Math.max(table.getOrDefault(x, 0), m * n));
                if (x >= MAX_TARGET)
                    break;
            }
        }
        Collections.sort(list);
    }

    // Return x or y where x,y \in list, whichever is closest to target. If
    // equidistant, tiebreaker is whichever yields larger area (check table)
    private static int closest(int x, int y, int target) {
        int dx = Math.abs(x - target);
        int dy = Math.abs(y - target);
        if (dx < dy)
            return x;
        if (dx > dy)
            return y;
        if (table.get(x) > table.get(y))
            return x;
        return y;
    }

    // Binary search: return element in list s.t. | element - target | is minimal.
    private static int search(int target) {
        int n = list.size();
        if (target <= list.get(0))
            return list.get(0);
        if (target >= list.get(n - 1))
            return list.get(n - 1);
        int l = 0, r = n - 1, m = 0;
        while (l <= r) {
            m = (l + r) / 2;
            if (target < list.get(m)) {
                if (m > 0 && target > list.get(m - 1)) {
                    return closest(list.get(m - 1), list.get(m), target);
                }
                r = m - 1;
            } else if (target > list.get(m)) {
                if (m < n - 1 && target < list.get(m + 1)) {
                    return closest(list.get(m), list.get(m + 1), target);
                }
                l = m + 1;
            } else
                return list.get(m);
        }
        return list.get(m);
    }

    private static int solution(int target) {
        return table.get(search(target));
    }

    public static void main(String[] args) {
        fillTableAndList();
        Scanner s = new Scanner(System.in);
        int t = Integer.parseInt(s.nextLine());
        for (int t0 = 0; t0 < t; t0++) {
            int target = Integer.parseInt(s.nextLine());
            System.out.println(solution(target));
        }
        s.close();
    }
}
