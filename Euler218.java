/* -------- SOLVED -------- */

/* Consider the right angled triangle with sides 7, 24 and 25. The area of this triangle is 84, which is divisible 
 * by the perfect numbers 6 and 28.
 * 
 * Moreover it is a primitive right angled triangle as gcd(a,b)=1 and gcd(b,c)=1.
 * 
 * Also c is a perfect square.
 * 
 * We will call a right angled triangle perfect if
 * 		it is a primitive right angled triangle
 * 		its hypotenuse is a perfect square
 * 
 * We will call a right angled triangle super-perfect if
 * 		it is a perfect right angled triangle and
 * 		its area is a multiple of the perfect numbers 6 and 28.
 * 
 * How many perfect right-angled triangles with c <= n exist that are not super-perfect? */

/* Input given as 1<=q<=100000, 25<=n<=2*10^18 */

import java.util.*;

public class Euler218 {
	
	/* All perfect right triangles with c>=25 are super-perfect. */

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int q = s.nextInt();
        for (int i = 0; i < q; i++) {
            System.out.println(0);
        }
        s.close();
    }

}
