package hmwk12;
import java.util.Scanner;
/**
 * Author: Russell Ferguson
 * Instructor: Professor R. Brown
 * Class: CSC 184 Spring '14 MWF
 * Date: 4/16/14
 */
public class Hmwk12 {

    public static void main(String[] args) {
        double PI = Math.PI;
        Scanner keyInput = new Scanner(System.in);
        int userIterations = 0;
        //I tried long type iterations throughout the program and I actually
        //managed to freeze my computer after about of 30 secs of calculations.
        //AAAAAAAAAAAAAAAAAHAHAHAHAHAHA. NO, WAIT, THAT'S NOT FUNNY.
        //I'm not even gonna try it a 5th time. Keep it Int!
        
        System.out.println("The double-point-precision value of pi (to 15 decimals), is: "+PI);
        //System.out.printf("%1.30f",PI);
        //It's all 0's past the 15th decimal!
        
        do{
            System.out.println("Enter a positive integer number of iterations you want to use to calculate pi through the Leibniz Series: ");
            System.out.println("The greater the number, the more accurate a calculation of PI will be produced.");
            userIterations = keyInput.nextInt();
        }while(userIterations<=0);
        
        double leibnizPI = LeibnizCalculation(userIterations);
        System.out.println();
        System.out.println("Again, the absolute value of pi (up to 15 decimals) is: "+PI);
        //System.out.printf("%1.30f",PI);
        //Redacted because it serves no point. See line 29.       
        System.out.println("The double-point-precision value of pi as calculated by the Leibniz Series, based on your iterations: "+leibnizPI);
        System.out.println();
        System.out.println("The difference of the two numbers (pi - Leibniz) is: " +(PI-leibnizPI));
        System.out.println();
        System.out.println("Or rather, the difference up to the 15th significant decimal digit: ");
        System.out.printf("%1.15f", (PI-leibnizPI));
        System.out.println();

    }
    /* *Leibniz Series formulae for approximating PI*
            PI     1     1     1     1     1
           ---- = --- - --- + --- - --- + --- - . . .
            4      1     3     5     7     9
    
        Iteration: 0     1     2     3     4    . . .
    */
    
    public static double LeibnizCalculation(int iterations){
        double x=1.0;
        for(int i=1;i<iterations; i++){
            // Remember that == is neccessary for computation conditions in if statements
            // Because it's absolute. It's TRUE.
            // == TRUE
            // != FALSE
            // = Assignments, and sometimes Comparisons, depending on context...?
            if(i%2==0){
                x+=((1.0)/((2.0*i)+1.0));
            }else{
                x-=((1.0)/((2.0*i)+1.0));
            }
        }
        return (4.0*x);
    }
}
