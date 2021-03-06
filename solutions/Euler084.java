/* -------- UNSOLVED -------- */

/* 
In the game, Monopoly, the standard board is set up in the following way:

GO    A1 CC1 A2 T1 R1 B1 CH1 B2  B3  JAIL
H2  |                               |  C1
T2  |                               | U1
H1  |                               | C2
CH3 |                               | C3
R4  |                               | R2
G3  |                               | D1
CC3 |                               | CC2
G2  |                               | D2
G1  |                               | D3
G2J   F3 U2  F2 F1 R2 E3 E2  CH2 E1   FP

https://www.hackerrank.com/contests/projecteuler/challenges/euler084/problem

A player starts on the GO square and adds the scores on two 6-sided dice to determine the number of squares they advance in a 
clockwise direction. Without any further rules we would expect to visit each square with equal probability: 2.5%. However, 
landing on G2J (Go To Jail), CC (community chest), and CH (chance) changes this distribution.

G2J and one card from each of CC and CH order the player to go directly to jail.

At the beginning of the game, the CC and CH cards are shuffled. When a player lands on CC or CH they take a card from the 
top of the respective pile and, after following the instructions, it is returned to the bottom of the pile. There are 
sixteen cards in each pile, but for the purpose of this problem we are only concerned with cards that order a movement; 
any instruction not concerned with movement will be ignored and the player will remain on the CC/CH square.

- Community Chest (2/16 cards):
    1. Advance to GO
    2. Go to JAIL
- Chance (10/16 cards):
    1. Advance to GO
    2. Go to JAIL
    3. Go to C1
    4. Go to E3
    5. Go to H2
    6. Go to R1
    7. Go to next R (railway company)
    8. Go to next R
    9. Go to next U (utility company)
    10. Go back 3 squares.

The heart of this problem concerns the likelihood of visiting a particular square. That is, the probability of finishing 
at that square after a roll. For this reason it should be clear that, with the exception of G2J for which the probability 
of finishing on it is zero, the CH squares will have the lowest probabilities, as 5/8 request a movement to another square, 
and it is the final square that the player finishes at on each roll that we are interested in. We shall make no 
distinction between "Just Visiting" and being sent to JAIL, and we shall also ignore the rule about requiring a double 
to "get out of jail", assuming that they pay to get out on their next turn.

Statistically it can be shown that the three most popular squares, in order, are JAIL (5.90%), E3 (3.19%) and GO (3.11%).

If, instead of using two 6-sided dice, two N-sided dice are used, find the K most popular squares in order.

INPUTS: 4 <= N <= 40, 3 <= K <= 40
*/

import java.util.Scanner;

public class Euler084 {

    /* Thoughts/approach: */

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int t = Integer.parseInt(s.nextLine());
        for (int t0 = 0; t0 < t; t0++) {

        }
        s.close();
    }
}
