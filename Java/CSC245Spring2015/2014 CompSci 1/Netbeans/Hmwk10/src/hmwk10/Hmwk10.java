package hmwk10;
//Program Objectives
//Write a program that simulates rolling die.  
//You will roll the die until you hit "snake eyes" which is a roll of 1 on each die.
//After the loop terminates display the total number of rolls, and the sum of all 
//of the dots for all rolls.  Also display the number of times that each 
//value from 2 through 12 was rolled along with the percentage of rolls.

//GIVEN: You are given the starting code below, modify this code to achieve the Program Objectives above.
//You will need to modify the given code to loop until snake eyes are hit:
//Implement the appropriate do...while() statement at the appropriate points in 
//the code to simulate continuous rolling until snake eyes (1 on each die).
//Add counter variables for values from 4 though 12.
//Convert the if statements into a switch statement and add in the values for 4-12.
//Update the printTotals method for rolls of 4 through 12.

//Don't forget to update rollCount and sumOfAllPips!
//Feel free to make any methods you want to help achieve this programming task.

/**
* Author: Russell Ferguson
* Instructor: Professor R. Brown
* Date: 4/8/14
**/

//I'm the king of agonizing copypasta
public class Hmwk10 {
    
    public static int die1;
    public static int die2;
    public static int rollCount = 0;
    public static int rollValue = 0;
    public static int sumOfAllPips = 0;

    public static int rolledTwo = 0;
    public static int rolledThree = 0;
    public static int rolledFour = 0;
    public static int rolledFive = 0;
    public static int rolledSix = 0;
    public static int rolledSeven = 0;
    public static int rolledEight = 0;
    public static int rolledNine = 0;
    public static int rolledTen = 0;
    public static int rolledEleven = 0;
    public static int rolledTwelve = 0;

public static void main(String[] args){

        do{              
            die1 = (int)(Math.random() * 6) + 1;
            die2 = (int)(Math.random() * 6) + 1;
            rollValue = die1 + die2;
            rollCount++;

            //convert the following to a switch to handle all values 2-12
            switch(rollValue){
                case 2: rolledTwo++; break;
                case 3: rolledThree++; break;
                case 4: rolledFour++; break;
                case 5: rolledFive++; break;
                case 6: rolledSix++; break;
                case 7: rolledSeven++; break;
                case 8: rolledEight++; break;
                case 9: rolledNine++; break;
                case 10: rolledTen++; break;
                case 11: rolledEleven++; break;
                case 12: rolledTwelve++; break;
                default: ;
            }
            //default does nothing.
            //Going through debugger, oddly enough, without those breaks, this
            //switch added one to every roll count above the one that was intended
            //i.e. if it rolled ten, it would increment rolledTen, rolledEleven, and rolledTwelve
            //Tried going to absolutes and using a boolean of some sort, but instead
            //opted to just break the switch every time the first one became true.
            //Very useful. If switch is supposed to be if-else, why continue when
            //it meets a condition? Interesting. (And a pain the butt)
            
            //Just add the rollValue every loop to the sum.
            //Short, sweet, nice.
            sumOfAllPips+=rollValue;
            
        }while(rolledTwo<1);

        //Can't put the public variable in their own parentheses by themselves
        //Just use number and multiplication asterisk.
        //Old, possible way I did it.
        //lol
        //sumOfAllPips=((2*rolledTwo)+(3*rolledThree)+(rolledFour*4)+(rolledFive*5)+(rolledSix*6)+(rolledSeven*7)+(rolledEight*8)+(rolledNine*9)+(rolledTen*10)+(rolledEleven*11)+(rolledTwelve*12));
        printTotals();
    
    }

    
    public static void printTotals(){
        
        System.out.print("\n");
        System.out.printf("rollCount = %d\n", rollCount);
        System.out.printf("sumOfAllPips = %d\n", sumOfAllPips);
        System.out.print("\n");
        System.out.printf("rolledTwo = %d      %3.0f%% \n", rolledTwo,    getPercentage(rolledTwo,rollCount) );
        System.out.printf("rolledThree = %d    %3.0f%% \n", rolledThree,  getPercentage(rolledThree,rollCount) );
        System.out.printf("rolledFour = %d      %3.0f%% \n", rolledFour,    getPercentage(rolledFour,rollCount) );
        System.out.printf("rolledFive = %d    %3.0f%% \n", rolledFive,  getPercentage(rolledFive,rollCount) );
        System.out.printf("rolledSix = %d      %3.0f%% \n", rolledSix,    getPercentage(rolledSix,rollCount) );
        System.out.printf("rolledSeven = %d    %3.0f%% \n", rolledSeven,  getPercentage(rolledSeven,rollCount) );
        System.out.printf("rolledEight = %d      %3.0f%% \n", rolledEight,    getPercentage(rolledEight,rollCount) );
        System.out.printf("rolledNine = %d    %3.0f%% \n", rolledNine,  getPercentage(rolledNine,rollCount) );
        System.out.printf("rolledTen = %d      %3.0f%% \n", rolledTen,    getPercentage(rolledTen,rollCount) );
        System.out.printf("rolledEleven = %d    %3.0f%% \n", rolledEleven,  getPercentage(rolledEleven,rollCount) );
        System.out.printf("rolledTwelve = %d      %3.0f%% \n", rolledTwelve,    getPercentage(rolledTwelve,rollCount) );
    }
    
    //keep this method as is
    public static double getPercentage(int itemCountIn, int rollCountIn){
        return (((double)itemCountIn/(double)rollCountIn)*100);
    }

    
}