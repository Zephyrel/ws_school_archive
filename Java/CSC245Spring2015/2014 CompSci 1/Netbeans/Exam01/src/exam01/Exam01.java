package exam01;

/**
 *Russell Ferguson 
 *CSC 184
 *Exam01 
 *February 14, 2014
 */

//Easy Peasy Lemon Squeezy
import java.util.Scanner;
public class Exam01 {

    public static void main(String[] args) {
        
        /*Prompt the user to enter the length of a rectangle
        *Get the user input for the length
        *Prompt the user to enter the width of a rectangle
        *Get the user input for the width
        *Calculate the area using the formula length times width
        *Calculate the perimeter of the rectangle using the formula two times the length plus two times the width
        *Print the length and width that the user input
        *Print the area
        *Print the perimeter
        */
        
        double l;
        double w;
        double area;
        double perimeter;    
        System.out.println("Enter the length \"l\" of your rectangle:");
            Scanner input = new Scanner(System.in);
            l = input.nextDouble();
            System.out.println("Your length = " + l);
        System.out.println("Enter the width \"w\" of your rectangle:");
            w = input.nextDouble();
            System.out.println("Your width = " + w);
        area = l*w;
        perimeter = 2*(l+w);
            System.out.println("Area = " + l + " * " + w + " = " + area);
            System.out.println("Perimeter = 2*(" + l + ") + 2*(" + w + ") = " + perimeter);
    }
}
