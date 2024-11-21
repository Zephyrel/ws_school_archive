package hmwk16;
import java.util.Scanner;
/**
* Author: Russell Ferguson
* Instructor: Professor R. Brown
* Course: CSC 184
* Date: 4/15/14
**/

//Warming up the brain with some extra credit.
public class Hmwk16 {
    
    public static void main(String[] args) {
        Scanner keyInput = new Scanner(System.in);
        int pyramidSize;
        do{
            System.out.println("Enter a number between 1 and 15 for the size of the pyramid.");
            pyramidSize = keyInput.nextInt();
        }while((pyramidSize<=1) || (pyramidSize>=15));
        System.out.println("The size of your pyramid is: " + pyramidSize);
        
        drawPyramid(pyramidSize);
    }
    
    public static void drawPyramid(int size){
        for(int i = 1; i < (size+1); i++){
            //left side
            for(int j = (size); j > 1; j--){
                if(j>i){
                    System.out.print("   ");
                } else if(j<=i){
                    System.out.printf("%2d"+" ", j);
                }
            }
            //Center column and Right Side
            for(int j = 1; j <= (size); j++){
                if(j>i){
                    System.out.print("   ");
                } else if(j<=i){
                    System.out.printf("%2d"+" ", j);
                }
            }
            //Start the new line, print nothing.
            System.out.println();
        }
    }
}