

//Russell Ferguson
//CSC100 MWF 10AM
//R. Brown

//HMWK09
/*Assignment*/
/*/
 * Write a Java program that uses a do{...}while structure to prompt such as the following.
 * Enter a zero or positive number, or enter a negative number to quit: 
 * If the user enters a non-negative number print the number and the square root.  Exit the loop when the number is negative.
 * I suggest that you use the printf function for this assignment.
 * All input and output must be to the console.
/*/

import java.util.Scanner;

public class Hmwk09 {
	public static void main(String[] args) {
		
		Scanner inputStream = new Scanner(System.in);
		double input;
		double inputRoot;
		System.out.println("Enter any positive number or 0. It will print out your number, and it's square root.");
		do {
			input = inputStream.nextDouble();
			inputRoot = Math.pow(input, 0.5);
			System.out.printf("Your number: " + input + " ");
			System.out.printf(" The number's square root: " + inputRoot);
			System.out.printf("");
		} while (input >= 0);
		System.out.println("");
		System.out.println("This program is now ended.");
	}
}
