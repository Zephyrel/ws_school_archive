
package hmwk14;

import java.util.Scanner;
import java.io.*;
/**
 * Author: Russell Ferguson
 * Instructor: Professor R. Brown
 * Date: 4/22/14
 */
public class Hmwk14 {

    public static void main(String[] args){
        double[] scoresArray = new double[20];
        int n = readScores(scoresArray);
        //n defines the limit for which scores are processed
        printScores(scoresArray, n);
        double minScore = getLow(scoresArray, n);
        double maxScore = getHigh(scoresArray, n);
        double averageScores = getAverage(scoresArray, n, minScore, maxScore);
        printStatistics(minScore, maxScore, averageScores);
    }
    
    public static int readScores(double[] scoresIn){
        int rv = 0;
        try{
            
            Scanner inputFile = new Scanner(new FileReader("src/Hmwk14/scores.txt"));
        
            for(int i = 0; i < scoresIn.length; i++){
                //scoresIn.length is 20 long, period. No if's, and's, for's, or butts.
                scoresIn[i] = 0;
                if(inputFile.hasNext()){
                    rv++;
                    scoresIn[i] = inputFile.nextDouble();
                }
            }
            
        }catch(FileNotFoundException ex){
            System.out.println(ex.toString());
        }
        return rv;
    }
    
    public static void printScores(double[] scoresIn, int n){
        for(int i = 0; i < n; i++){
            System.out.printf("Score " + (i+1) + " -- %3.3f", scoresIn[i]);
            System.out.println();
        }
            
    }
    
    public static double getLow(double[] scoresIn, int n){
        double rv = scoresIn[0];
        //initializes the return value to the first value of the array
            for(int i = 0; i < n; i++){
                if(scoresIn[i]<rv){
                    //reads array number by number, changes rv if the number is less
                    //than the current return value
                    rv=scoresIn[i];
                }
            }
        return rv;
    }
    
    public static double getHigh(double[] scoresIn, int n){
        double rv = scoresIn[0];
        //initializes the return value to the first value of the array
            for(int i = 0; i < n; i++){
                if(scoresIn[i]>rv){
                    //Same concept in reverse
                    //Changes rv if greater.
                    rv = scoresIn[i];
                }
            }
        return rv;
    }
    //getAverage minus high and low, divided by 2
    public static double getAverage(double[] scoresIn, int n, double lowNumber, double hiNumber){
        double sum = 0;
        for(int i = 0; i < n; i++){
            sum += scoresIn[i];
        }
        double average = (sum-(lowNumber+hiNumber))/(n-2);
        return average;
    }
    
    public static void printStatistics(double min, double max, double average){
        System.out.printf("The lowest score is %3.3f", min);
        System.out.println();
        System.out.printf("The highest score is %3.3f", max);
        System.out.println();        
        System.out.printf("The average of the scores minus the highest and lowest is %3.3f", average);
        System.out.println();        
        //I originally had these prints in their respective methods until I read
        //over the requirements again and had to cut and paste them in a method
        //instead that did the printing.
    }
}

/*A file named "scores.txt" in the default directory stores test scores as real numbers.  
They are a single number on each line.

You are to write a program that will read up to 20 test scores from the file.
There may be fewer than 20 scores, or there may be more.  
You are to read the scores into an array.  If there are fewer than 20 entries, 
store the numbers at the start of the array and do all processing on the numbers 
that are actually read.  If there are more than 20 entries, only process the first 20.

After reading in the scores, your program should do the following:

Print the list of scores.  The list should be printed in the following format.  
The output should be appropriate for normal humans (not computer programmers).
Therefore the first entry should be Score 1, not Score 0.

               Score 1 -- 80.3

    Print the number of entries read.
    Print the highest score
    Print the lowest score
    Print the average of all of the scores, but delete the lowest and highest scores from the list.  

(Hint:  Sum the array and then subtract the highest and lowest scores from the sum.  
Then divide the adjusted sum by n-2.)

The main() method should contain only the declarations needed (for the array size, 
n, max, min, average, and the array itself).  It should contain only method 
calls after that.  You should have methods for reading, finding the minimum and 
maximum, finding the average, and printing the entire array.  You should write 
one method that will print all of the statistics.  It should have min, max, 
and average as parameters.  You do not need to pass this method the array itself.  
Make sure each of the results are appropriately labeled.*/