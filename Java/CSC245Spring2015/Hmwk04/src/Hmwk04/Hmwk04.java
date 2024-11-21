package Hmwk04;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @author	Russell Ferguson
 * @date	2/11/2015
 */

/*
The class name for this assignment must be Hmwk. The file you submit must be named Hmwk.java
Read from a file called input.txt. Each line of the file contains the following information for one serving of food.
    Food name:  a string.  If it is more than one word it has a hard white space or _ in the blank.  
    Number of calories 
    Grams of fat
    Grams of Carbohydrate
    Grams of Protein
Build a public class for the Food. It must have a constructor that takes each of the fields as an argument.
Each field should have a getter and setter. Each of the numeric fields should be a non-negative number.
The setter should take the absolute value of each field which will wipe out the negative values.
IT IS IMPORTANT THAT THIS BE DONE IN THE SETTER.
Create a toString method (You might have to delay this part until next week if we don't get to it tonight).
Your Hmwk class should open the file and read the records.
It should create a food item. Then use the toString() method to print the food item.
*/
 
public class Hmwk04 {

	public static void main(String[] args){
		
		Food food = new Food();
		String name = "";
		double calories = 0;
		double fat = 0;
		double carbs = 0;
		double protein = 0;
		
		try{
			Scanner inputFile = new Scanner(new File("input.txt"));
			
			boolean endOfName = false;
			
			while(inputFile.hasNext()){
				name = inputFile.next();
				while(endOfName == false){
					try{
						calories = inputFile.nextDouble();
						endOfName = true;
					}catch(java.util.InputMismatchException e){
						name += " " + inputFile.next();
						endOfName = false;
					}
				}
			endOfName = false;
			//I tried to have it able to read the intended strings that happened to have whitespace instead of underscores inbetween the words
			//Got It! The below two lines are no longer necessary, was the old way of reading the Strings with underscores in them.
			//name = inputFile.next();
			//calories = inputFile.nextDouble();
			fat = inputFile.nextDouble();
			carbs = inputFile.nextDouble();
			protein = inputFile.nextDouble();
			food.setAll(name, calories, fat, carbs, protein);
			System.out.println(food.toString());
			}
			
			inputFile.close();
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}
	}
}