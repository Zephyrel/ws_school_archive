package CSC285Prob5_Pt2;

import java.io.*;
import java.util.*;

public class CSC285Prob5_Pt2 {
	public static void main(String[] args){
		int a[][]=	{{2, 3},
					{1, 0}};
		int b[][]=	{{0, 3},
					{5, 4}};
		int c[][]=	{{1, 0},
					{0, 0}};

		String string = "2*A-3*((B-2*C)/(A+3)-B*3)";
		char[] expr = string.toCharArray();

		int subCount=0;
		String[] subExpr=new String[string.length()]; 
		
		//Scanner input = new Scanner(string);
				
		for(int i=0; i<expr.length;i++){	//i is index for express
			for(int j=0; i<expr.length;j++){	//j is index for subExpress to put in expressions enclosed in parentheses
				if(Character.isDigit(expr[i])){
					
				}
			}
		}
	}
	public static int[][] eval(String express){
		int a[][]=	{{2, 3},
					{1, 0}};
		int b[][]=	{{0, 3},
					{5, 4}};
		int c[][]=	{{1, 0},
					{0, 0}};

		char[] arrays = {'A','B','C'};
		char[] oprtr={'*','/','+','-','(',')','\0'};
		int[] priority={2,2,1,1,99,-99,-100};
		
		char[] expr = express.toCharArray();
		int x=0;
		int y=0;
		int[][] oper1={{},{}};
		int[][] oper2={{},{}};
		int[][] oper3={{},{}};
		
		for(int i=0;i<expr.length;i++){
			
		}
		return oper3;
	}
}

class Opernd{
	
	Node nextNode;
	int array[][]=	{{0,0},
					{0,0}};
}

Class Oprtr{


