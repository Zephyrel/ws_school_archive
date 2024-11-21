/*
Russell Ferguson
CSC 184, MWF 10:00-10:50
HMWK 04 - Chapter 2
*/

// x = (-b (+/-) sqrt(b^2-4ac)) / (2a)
import java.util.*;

public class Hmwk04{

     public static void main(String []args) {
         Scanner TypeInput = new Scanner(System.in);
         System.out.println("Quadratic Equation:  x = (-b {+/or/-} sqrt(b^2-4ac)) / (2a)");
         System.out.println("Type in the values for variables a, b, and c");
         System.out.println("4*a*c must be less than b^2, or else an error will be returned.");
         System.out.println("Provide the input for variable b");
         Double b = TypeInput.nextDouble();
         System.out.println("Provide the input for variable a");
         Double a = TypeInput.nextDouble();
         System.out.println("Provide the input for variable c");
         Double c = TypeInput.nextDouble();
         Double EquationMinus = ((-b - Math.pow(Math.pow(b, 2) - (4*a*c), 0.5)) / (2*a));
         Double EquationPlus = ((-b + Math.pow(Math.pow(b, 2) - (4*a*c), 0.5)) / (2*a));
         if (Math.pow(b, 2) < (4*a*c)) {
            // Return error message because 4*a*c is greater than b^2, resulting in trying to take the sqare root of a negative number, which isn't possible and results in a error.
            System.out.println("ERROR: Cannot solve because b^2 is less than (4*a*c), resulting in attempting the square root of a negative number, which isn't possible without the use of imaginary number 'i'");
            } else {
            // Print out full equation neatly sorted out in parantheses and brackets keeping things clean.
            System.out.println("Quadratic Equation +: x = {(" + (-b) + ") + sqrt((" + b + "^2) - (4)(" + a +")" + "(" + c + "))} / {(2)(" + a + ")} = " + EquationPlus);
            System.out.println("Quadratic Equation -: x = {(" + (-b) + ") - sqrt((" + b + "^2) - (4)(" + a +")" + "(" + c + "))} / {(2)(" + a + ")} = " + EquationMinus);
            System.out.println("Answer with +: " + EquationPlus);
            System.out.println("Answer with -: " + EquationMinus);
         }
     }
}
     