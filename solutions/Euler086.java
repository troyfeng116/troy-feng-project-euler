/* -------- SOLVED -------- */

/* 
A spider, S, sits in one corner of a cuboid room, measuring 6 by 5 by 3, and a fly, F, sits in the opposite 
corner. By travelling on the surfaces of the room the shortest "straight line" distance from S to F is 10 
and the path is shown on the diagram.

https://www.hackerrank.com/contests/projecteuler/challenges/euler086/problem

However, there are up to three "shortest" path candidates for any given cuboid and the shortest route doesn't
always have integer length.

By considering all cuboid rooms with integer dimensions, up to a maximum size of M by M by M, there are exactly 
2060 cuboids for which the shortest route has integer length when M=100; the number of solutions is 1975 when
M=99.

There will be T testcases. For each case, an integer M will be given. For each case, print the number of 
cuboids with integer dimensions up to a maximum of M by M by M such that the shortest route is an integer.

An A by B by C dimension cuboid is up to a maximum of M by M by M only if A,B,C <= M.

INPUTS: 1 <= T <= 10^4, 1 <= M <= 4x10^5
*/

import java.util.Scanner;

public class Euler086 {

    /*
     * Thoughts/approach: Classic shortest path from corner to corner problem. If we
     * unfold the box, we are looking for the now straight-line distance from the
     * starting corner to the top right corner on an unfolded 2D plane. WLOG, let A
     * be side on X axis, B be side on Y, C be side on Z. Then to get from corner to
     * corner, we would start from the origin and traverse the hypotenuse of a right
     * triangle with legs of length A and (B+C). So we are counting the unique
     * ordered triplets (A,B,C) where A,B,C <= M s.t. sqrt(A^2 + (B+C)^2) is an
     * integer, AKA Pythagorean triples! Euclid's Pythagorean Triple generating
     * algorithm will do nicely.
     * 
     * Euclid's algorithm will give us all primitive (p,q,r) triples. We will have
     * to count the number of ways to split the legs (p,q) into the form (a, b+c) or
     * (b+c, a), where a >= b >= c for uniqueness and to minimize the distance from
     * corner to corner. This way, a and (b+c) will be as close to each other as
     * possible, thus forming the minimal hypotenuse.
     */

    private static final int MAX_M = 400000;
    // numCuboids[M] = num cuboids with integral path with max(A,B,C) = M.
    private static long[] numCuboids;
    // cumulativeNumCuboids[M] = num cuboids with integral path with A,B,C <= M.
    private static long[] cumulativeNumCuboids;

    private static int gcd(int a, int b) {
        if (a == 0)
            return b;
        if (b == 0)
            return a;
        return gcd(b, a % b);
    }

    // Return num ways to split (p,q) into (A, B+C) s.t. A >= B >= C
    private static int tripletCombinations(int p, int q) {
        if (2 * p < q)
            return 0;
        if (p >= q)
            return q / 2;
        // B = p, C = q-p through q odd ? (q+1)/2 : q/2.
        return p - (q - 1) / 2;
    }

    private static void preCalculate() {
        numCuboids = new long[MAX_M + 1];
        // To ensure we catch all legs <= MAX_M, we need m^2 up to about 3*MAX_M. Not
        // too sure why lol, it doesn't pass TCs for up to 2*MAX_M...
        for (int m = 1; m * m <= 3 * MAX_M; m++) {
            for (int n = m % 2 == 0 ? 1 : 2; n < m; n += 2) {
                if (gcd(m, n) == 1) {
                    int p = m * m - n * n;
                    int q = 2 * m * n;
                    // p and q are legs of primitive triples, so k*p and k*q will form all
                    // non-primitive legs.
                    // Let k * p = A, add num ways to split q into B+C s.t. B < A and C < A
                    for (int k = 1; k * p <= MAX_M; k++) {
                        numCuboids[k * p] += tripletCombinations(k * p, k * q);
                    }
                    // Let k * q = A, add num ways to split p into B+C s.t. B < A and C < A
                    for (int k = 1; k * q <= MAX_M; k++) {
                        numCuboids[k * q] += tripletCombinations(k * q, k * p);
                    }
                }
            }
        }
        cumulativeNumCuboids = new long[MAX_M + 1];
        long cSum = 0;
        for (int M = 1; M <= MAX_M; M++) {
            cSum += numCuboids[M];
            cumulativeNumCuboids[M] = cSum;
        }
    }

    public static void main(String[] args) {
        preCalculate();
        Scanner s = new Scanner(System.in);
        int t = Integer.parseInt(s.nextLine());
        for (int t0 = 0; t0 < t; t0++) {
            int M = Integer.parseInt(s.nextLine());
            System.out.println(cumulativeNumCuboids[M]);
        }
        s.close();
    }
}
