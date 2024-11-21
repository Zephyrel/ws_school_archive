package CSC285Prob5;

import java.io.*;
import java.util.*;

/*
 * Russell Ferguson
 * March 22, 2017
 * CSC285 Data Structures - Kent Pickett
 */
public class CSC285Prob5 {
	public static void main(String[] args){

		LinkManager<Character> oprtrStack = new LinkManager<Character>();
		LinkManager<Double> oprndStack = new LinkManager<Double>();
		
		char[] expr1 = "A@(2*(A-C*D))+(9*B/(2*C+1)-B*3)+E%(F-A)".toCharArray();
		char[] expr2 = "B*(3@(A-D)%(B-C@D))+4@D*2".toCharArray();
		
		for(int i = 0; i<expr1.length;i++){
			if(Character.isDigit(expr1[i])){ //is a digit
				oprndStack.push(Double.parseDouble(String.valueOf(expr1[i])));
			}else if(Character.isAlphabetic(expr1[i])){  //is a letter variable
				oprndStack.push(getVal(expr1[i]));
			}else if(expr1[i]=='('){  //beginning of () set expr
				oprtrStack.push(expr1[i]);
			}else if(expr1[i]==')'){  //end of () set expr, evaluate, pop ( at end
				while(oprtrStack.peek()!='('){
					evalExpr(oprndStack, oprtrStack);
				}
				oprtrStack.pop();
			}else{
				int oprior = getPrio(expr1[i]);
				while(oprtrStack.peek()!=null && oprior <= getPrio(oprtrStack.peek())){
					evalExpr(oprndStack, oprtrStack);
				}
				oprtrStack.push(expr1[i]);
			}
		}
		//eval the rest of the stuff on the stack until one operand remains
		while(oprndStack.size()>1){
			evalExpr(oprndStack, oprtrStack);
		}
		System.out.println("The result of Expression 1 is:\t"+oprndStack.getFirst());
		
		//reinitialize stacks to an empty slate
		oprtrStack = new LinkManager<Character>();
		oprndStack = new LinkManager<Double>();
		
		for(int i = 0; i<expr2.length;i++){
			if(Character.isDigit(expr2[i])){ //is a digit
				oprndStack.push(Double.parseDouble(String.valueOf(expr2[i])));
			}else if(Character.isAlphabetic(expr2[i])){ //is a letter variable
				oprndStack.push(getVal(expr2[i]));
			}else if(expr2[i]=='('){ //beginning of () set expr
				oprtrStack.push(expr2[i]);
			}else if(expr2[i]==')'){ //end of () set expr, evaluate, pop ( at end
				while(oprtrStack.peek()!='('){
					evalExpr(oprndStack, oprtrStack);
				}
				oprtrStack.pop();
			}else{ //An operator
				int oprior = getPrio(expr2[i]);
				while(oprtrStack.peek()!=null && oprior <= getPrio(oprtrStack.peek())){
					evalExpr(oprndStack, oprtrStack);
				}
				oprtrStack.push(expr2[i]);
			}
		}
		while(oprndStack.size()>1){
			evalExpr(oprndStack, oprtrStack);
		}
		System.out.println("The result of Expression 2 is:\t"+oprndStack.getFirst());
	}
	
	//evaluates a single operator between two operands
	public static double eval(char oprtr, double oper1, double oper2){
		double result = -9999;
		switch (oprtr){
			case '@':
				if(oper2<0){System.out.printf("\nAttempted negative exponential operation with %d, not allowed! Will return first operand, %d!\n", oper2, oper1); result=oper1;}
				else if(oper2==0){result=1;}
				else{result = Math.pow(oper1, oper2);}; break;
			case '*': result = oper1*oper2; break;
			case '/': if(oper2!=0){	result=oper1/oper2;}else{System.out.println("Attempted divide by zero not allowed! Will now exit!"); System.exit(-1);} break;
			case '%': result = oper1%oper2; break;
			case '+': result = oper1+oper2; break;
			case '-': result = oper1-oper2; break;
		}
		if(result == -9999){System.out.printf("\nThis is a bad operator! %s Will now exit!", oprtr); System.exit(-2);}
		return result;
	}
	//operates on an expression by popping off stack, and pushes the operand result on the stack
	public static void evalExpr(LinkManager<Double> oprnds, LinkManager<Character> oprtrs){
			double a, b, c;
			char operator = oprtrs.pop();
			a=oprnds.pop();
			b=oprnds.pop();
			c = eval(operator, b, a);
			oprnds.push(c);
			return;
	}
	//returns a value depending on the character
	public static double getVal(Character chara){
		int result = -9999;
		switch(chara){
			case 'A': result = 8; break;
			case 'B': result = 12; break;
			case 'C': result = 2; break;
			case 'D': result = 3; break;
			case 'E': result = 15; break;
			case 'F': result = 4; break;
		}
		if(result == -9999){System.out.println("Bad Operand Char! Will now exit!"); System.exit(-3);}
		return result;
	}
	//returns a priority value from a character representing an operation
	public static int getPrio(Character chara){
		int result = -9999;
		switch(chara){
			case ')': result = 99; break;
			case '@': result = 3; break;
			case '%': result = 2; break;
			case '/': result = 2; break;
			case '*': result = 2; break;
			case '+': result = 1; break;
			case '-': result = 1; break;
			case '(': result =-99; break;
		}
		if(result == -9999){System.out.printf("You passed a bad/nonexistent character for priority determination! %s Will now exit!", chara); System.exit(-7);} 
		return result;
	}
}
