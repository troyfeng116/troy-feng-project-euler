/* -------- SOLVED -------- */

/*
The smallest number expressible as the sum of a prime square, prime cube, and prime fourth power is 28.
In fact, there are exactly four numbers below fifty that can be expressed in such a way:

28 = 2^2 + 2^3 + 2^4
33 = 3^2 + 2^3 + 2^4
49 = 3^2 + 3^3 + 2^4
47 = 5^2 + 2^3 + 2^4

Given an integer N, find out how many numbers less than or equal to N are there that can be expressed
as a sum of a prime square, prime cube and prime fourth power.

INPUTS: 1 <= T <= 10^5, 1 <= N <= 10^7
*/

import java.util.Arrays;
import java.util.Scanner;
import java.util.TreeSet;

public class Euler087 {

    /*
     * Thoughts/approach: Generate all primes <= sqrt(MAX_N). Brute force all prime
     * power triples. Store triples in sorted TreeSet. Then, iterate from 1 to
     * MAX_N, updating cumulative count of triples as we go, and storing cumulative
     * counts in ans array. All about the precomputation
     */

    final static int MAX_N = (int) 1e7;
    static boolean[] isComposite;
    static int[] primes;
    static TreeSet<Integer> primePowerTriples;
    static int[] ans;

    private static void sieve() {
        isComposite = new boolean[(int) Math.ceil(Math.pow(MAX_N, 0.5)) + 1];
        int n = isComposite.length;
        int primeCount = 0;
        for (int i = 2; i < n; i++) {
            if (!isComposite[i]) {
                primeCount++;
                for (int j = 2 * i; j < n; j += i) {
                    isComposite[j] = true;
                }
            }
        }
        int index = 0;
        primes = new int[primeCount];
        for (int i = 2; i < isComposite.length; i++) {
            if (!isComposite[i]) {
                primes[index] = i;
                index++;
            }
        }
    }

    private static void computePrimePowerTriples() {
        primePowerTriples = new TreeSet<>();
        for (int i = 0; i < primes.length; i++) {
            for (int j = 0; j < primes.length && primes[j] < (int) Math.ceil(Math.pow(MAX_N, 0.34)); j++) {
                for (int k = 0; k < primes.length && primes[k] <= (int) Math.ceil(Math.pow(MAX_N, 0.25)); k++) {
                    int p = primes[i], q = primes[j], r = primes[k];
                    int targetNumber = (p * p) + (q * q * q) + (r * r * r * r);
                    if (targetNumber <= MAX_N)
                        primePowerTriples.add(targetNumber);
                }
            }
        }
    }

    private static void precalculateSolution() {
        ans = new int[MAX_N + 1];
        int smallest = primePowerTriples.first();
        int count = 0;
        for (int i = 0; i <= MAX_N; i++) {
            if (i >= smallest) {
                if (primePowerTriples.isEmpty())
                    ;
                else {
                    primePowerTriples.remove(smallest);
                    if (!primePowerTriples.isEmpty()) {
                        smallest = primePowerTriples.first();
                    }
                    count++;
                }
            }
            ans[i] = count;
        }
    }

    public static void main(String[] args) {
        sieve();
        computePrimePowerTriples();
        precalculateSolution();
        Scanner s = new Scanner(System.in);
        int t = Integer.parseInt(s.nextLine());
        for (int t0 = 0; t0 < t; t0++) {
            int N = Integer.parseInt(s.nextLine());
            System.out.println(ans[N]);
        }
        s.close();
    }
}
