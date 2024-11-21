package hmwk05;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @author Russell Ferguson
 * @date 2/12/2015
 */

/*
 * Write a "Pizza" class. The pizza class has three data fields.
 * 
 * 	integer representing the number of inches
 * 	boolean indicating whether it is a
 * 	thin crust String that lists the toppings.
 * 
 * Write the following methods:
 * 	A constructor that takes the three fields as arguments
 * 	A "noarg" constructor that calls the first constructor and sets the
 * 	inches to 9" the crust to thin and the toppings to "Cheese"
 * 	Setters for all fields
 * 	Getters for all fields
 * 	toString that prints the three fields in a nice
 * 		format compareTo<Pizza>. The comparison is first done on the size, Ties in
 * 		size are based on the crust, and remaining ties are broken based on the
 * 		String. 
 * 
 * The input program should read the data, put it into an array, sort
 * the array, and print out the sorted array. There may be up to 20 records. The
 * file is "Tab Delimited" and looks like the following
 */

public class hmwk05 {
	public static void main(String[] args){
		final int MAX_RECORD = 20;
		int foundMax = 0;
		Pizza[] pizzaArray = new Pizza[MAX_RECORD];
		//org.eclipse.debug.core.DebugException: com.sun.jdi.ClassNotLoadedException: Type has not been loaded occurred while retrieving component type of array.
		//Eclipse is not loading my Pizza object class? WHY
		//WHY IS EVERYTHING NULL
		//OH, NEVERMIND,GOT IT AHAHAHA
		//read the file, put into array
		
		try{
			Scanner inputFile = new Scanner(new File("Tab Delimited.txt"));
			int i = 0;
			while((i < MAX_RECORD) && inputFile.hasNext()){
				pizzaArray[i] = new Pizza();
				//Oh nevermind I actually have to instantiate every object in every index number
				//Frustration ahoy
				pizzaArray[i].setInches(inputFile.nextInt());
				pizzaArray[i].setCrust(inputFile.next());
				pizzaArray[i].setToppings(inputFile.nextLine());
				i+=1;
			}
			inputFile.close();
			foundMax = i;
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}
		//sort
		int i = 0;
		while(i < foundMax){
			int j = (i+1);
			Pizza tempPizza = new Pizza();
			tempPizza = pizzaArray[i];
			Pizza currentLeast = pizzaArray[i];
			int currentLeastIndex = -1;
			
			//scan for least valued pizza ahead of i, relative to i
			while(j < foundMax){
				if(currentLeast.compareTo(pizzaArray[j])>0){
					//Other Pizza is smaller, therefore its the current least
					currentLeast = pizzaArray[j];
					currentLeastIndex = j;
					j++;
				}else if(currentLeast.compareTo(pizzaArray[j])<0){
					//skip to next one down
					j++;
				}else if(currentLeast.compareTo(pizzaArray[j])==0){
					//skip to next one down
					currentLeast = pizzaArray[j];
					currentLeastIndex = j;
					j++;
				}
			}
			
			if(currentLeastIndex!=-1){
				//aka we actually found a pizza less than pizza at index i
				//swap!
				pizzaArray[i] = currentLeast;
				pizzaArray[currentLeastIndex] = tempPizza;
			}
			
			i++;
		}
		//We have found the current most less than Pizza ahead of This Pizza
		//print
		i=0;
		while(i < foundMax){
			System.out.println(pizzaArray[i].toString());
			i++;
		}
	}
}
