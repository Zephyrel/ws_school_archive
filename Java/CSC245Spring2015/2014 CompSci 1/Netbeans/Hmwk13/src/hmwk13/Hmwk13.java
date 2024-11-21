package Hmwk13;

import java.util.Scanner;
/**
 * Author: Russell Ferguson
 * Instructor: Professor R. Brown
 * Date: 4/8/14
 */
public class Hmwk13{
    //AKA fun with methods.
    //All methods to be used in this class are contained within said class.
    //But main is the method that runs, and uses and calls all these
    //other methods and functions within the class.
    public static void main(String[] args) {
        double length = getSide("length");
        double width = getSide("width");
        //These are the strings that get sent to getSide to print and ask for.
        double area = calcArea(width,length);
        //Sends these two variables, as returned to the length and width
        //variables by getSide(), to calcArea to calculate the area with.
        printRectangleInfo(width, length, area);
        //And finish with a print final output, summing your input as well.
    }

    public static double getSide(String sideIn){
        Scanner input = new Scanner(System.in);
        double rv;
        do{
            System.out.printf("How long is the %s of the rectangle? ", sideIn);
            //The %s prints the first string that is sent to it, which is 
            //defined as the input arg sideIn, which reads "length" or "width" depending
            //on the input arg when/where the method is called
            rv = input.nextDouble();
            
        }while(rv < 0.0);
        return rv;
    }

    public static double calcArea(double widthIn, double lengthIn){
            double rv = lengthIn * widthIn;
            return rv;
            //Returns this value to be used by whatever variable or method is calling to it,
            //being that this method is to be a double-type floating point number.
    }

    public static void printRectangleInfo(double widthIn, double lengthIn, double areaIn){
        System.out.printf("A rectangle with a length of %2.2f and a width of %2.2f would have an area of %2.2f squared.", widthIn, lengthIn, areaIn);
        //Each subsequent %2.2f will call the next float/input as defined
        //%f means print the next float input
        //%2.2f formats it as two digits, point, two digits
        System.out.println("");
    }

}