/* -------- UNSOLVED: 90/100 -------- */

/* For some positive integers k, there exists an integer partition of the form 4^t = 2^t + k,
 * where 4^t, 2^t and k are all positive integers and t is a real number.
 * 
 * The first two such partitions are 4^1 = 2^1 + 2 and 4^1.5849625... = 2^1.5849625... + 6.
 * 
 * Partitions where t is also an integer are called "perfect."
 * 
 * For any m>1 let P(m) be the proportion of such partitions that are perfect with k <= m.
 * 
 * Thus P(6) = 1/2. 
 * 
 * Find the smallest m for which P(m) < a/b. */

/* Input: q queries, a and b separated by space. 1 <= q <= 3x10^5, 1 <= a < b <= 10^18  */

import java.math.BigInteger;
import java.util.Scanner;

public class Euler207 {
	
	/* 4^t is integer iff 2^t is integer. So # perfect partitions is the greatest power of two less than or equal to m.
	 * Moreover, total # partitions is the number of rectangular numbers less than or equal to m. That is, k has an
	 * integer partition 4^t - 2^t iff k is rectangular. */
	
	public static void main(String[] args) {
        
    /* Using BigInteger helped solve more of the test cases by avoiding long overflow. 
     * 
     * The only place causing timeout (I think) is the t / (2^t - 1) < 1 / b while loop. So memoizing might help?
     * 
     * Tried memoizing powers of two and minimizing BigInteger object creation and passed 80/100. The while loop
     * still runs up to 65 times to find #perfect... */
        
        /* (k,v) = (k, 2*(2^k-1)). For a = 1, b = 10^18, t = 65 So we need only precalculate up to k=65. */
        BigInteger[] table = new BigInteger[66];
        table[0] = BigInteger.ONE;
        BigInteger twoConst = BigInteger.ONE.add(BigInteger.ONE);
        BigInteger twoPower = twoConst;
        for (int k = 1; k <= 65; k++) {
        	twoPower = twoPower.multiply(twoConst);
        	table[k] = twoPower.subtract(twoConst);
        }
        Scanner s = new Scanner(System.in);
        int q1 = Integer.parseInt(s.nextLine());
        for (int i = 0; i < q1; i++) {
            String[] inputs = s.nextLine().split(" ");
            BigInteger a = new BigInteger(inputs[0]);
            BigInteger b = new BigInteger(inputs[1]);
            if (a.longValue() <= 100000000000L && a.longValue() * 30000000 >= b.longValue()) {
            	long a1 = a.longValue();
                long b1 = b.longValue();
                double cutoff = (a1*1.0)/b1;
                System.out.println("Target: " + a1 + "/" + b1 + " = " + cutoff);
                int t = 1; //Number of perfect partitions.
                if (b1/a1 >= 10000) t = 16;
                if (b1/a1 >= 100000000) t = 30;
                if (b1/a1 >= 1000000000000L) t = 45;
                while (b1 * t >=  a1 * table[t].longValue()) {
                    t++;
                }
                System.out.println("t = " + t);
                //long start = (long) (t / cutoff);
                //System.out.println("Start = " + start);
                //long total = (long) Math.pow(2, t) - 1;
                long start = (long) (t * b1 / a1) + 1;
                //System.out.println(start);
                //long gate = (long) Math.pow(2,t+1);
                //System.out.println("Total tested from " + start + " until " + gate);
                /*while (t*b1 >= start*a1) {
                    start++;
                }*/
                System.out.println("Total found: " + start);
                System.out.println((long) start * (start + 1));
                System.out.println("Result: " + (t + 0.0)/start);
                continue;
            }
            System.out.println("Target: " + a.toString() + "/" + b.toString());
            int t = 1; //Number of perfect partitions.
            if (b.divide(a).compareTo(new BigInteger("10000")) >= 0) t = 16;
            if (b.divide(a).compareTo(new BigInteger("100000000")) >= 0) t = 30;
            if (b.divide(a).compareTo(new BigInteger("1000000000000")) >= 0) t = 45;
            if (b.divide(a).compareTo(new BigInteger("10000000000000000")) >= 0) t = 55;
            //BigInteger pow2 = twoConst;
            /* While a/b < t/(2*(2^t - 1)) */
            while (b.multiply(BigInteger.valueOf(t)).compareTo(a.multiply(table[Integer.valueOf(t)])) >= 0) {//(a.multiply(pow2.subtract(BigInteger.ONE).multiply(twoConst))) >= 0) {
                t++;
                //pow2 = pow2.multiply(twoConst);
            }
            System.out.println("t = " + t);
            //long start = (long) (t / cutoff);
            //System.out.println("Start = " + start);
            //long total = (long) Math.pow(2, t) - 1;
            BigInteger start = BigInteger.valueOf(t).multiply(b).divide(a).add(BigInteger.ONE);
            //System.out.println(start);
            /*while (t.multiply(b).compareTo(start.multiply(a)) >= 0) {
                    //Double.compare((t+0.0)/start, cutoff) >= 0) {
                start = start.add(BigInteger.ONE);
            }*/
            System.out.println("Total found: " + start.toString());
            System.out.println(start.multiply(start.add(BigInteger.ONE)).toString());
           // System.out.println("Result: " + (t + 0.0)/start);
            
        }
        s.close();
    }
	
	
	
	

/* FIRST ATTEMPT: WORKED FOR VALUES THAT DIDN'T EXCEED LONG MAX. */
	public static void FIRST_ATTEMPT() {
        Scanner s = new Scanner(System.in);
        int q = Integer.parseInt(s.nextLine());
        for (int i = 0; i < q; i++) {
            String[] inputs = s.nextLine().split(" ");
            long a = Long.parseLong(inputs[0]);
            long b = Long.parseLong(inputs[1]);
            double cutoff = (a*1.0)/b;
            System.out.println("Target: " + a + "/" + b + " = " + cutoff);
            long t = 1; //Number of perfect partitions.
            long pow2 = 2;
            while (b * t >=  a * 2 * (pow2 - 1)) {
                t++;
                pow2 *= 2;
            }
            System.out.println("t = " + t);
            //long start = (long) (t / cutoff);
            //System.out.println("Start = " + start);
            //long total = (long) Math.pow(2, t) - 1;
            long start = (long) (t * b / a);
            //System.out.println(start);
            //long gate = (long) Math.pow(2,t+1);
            //System.out.println("Total tested from " + start + " until " + gate);
            while (t*b >= start*a) {
                    //Double.compare((t+0.0)/start, cutoff) >= 0) {
                start++;
            }
            System.out.println("Total found: " + start);
            System.out.println((long) start * (start + 1));
            System.out.println("Result: " + (t + 0.0)/start);
            
        }
        s.close();
	}

}
