/* -------- SOLVED -------- */

/* Given is the function f(x) = floor(2^{b-x^2}) * 10^{-9} , the sequence u_n is defined by u_0 and u_{n+1} = f(u_n).
 * 
 * Find u_n + u_{n+1} for n=10^12 with given u_0 and b. Your answer would be considered correct if it has absolute error
 * not more than 10^{-8}. */

import java.util.*;

public class Euler197 {
	
	/* The sequence converges for any finite b and u_0. So to satisfy the given absolute error, we need only find
	 * the first many terms, not the 10^12'th term. */
	
	public static double recurse(double b, double n, int term, int stop) {
        if (term >= stop) return n;
        else {
            return recurse(b, Math.floor(Math.pow(2, b-n*n))/Math.pow(10,9), term+1, stop);
        }
    }

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        String input = s.nextLine();
        String[] nums = input.split(" ");
        double u0 = Double.parseDouble(nums[0]);
        double b = Double.parseDouble(nums[1]);
        double u1000 = recurse((double) b, (double) u0, 1, 1000);
        double u1001 = recurse((double) b, u1000, 1000, 1001);
        System.out.println(u1000 + u1001);
        s.close();
    }

}
