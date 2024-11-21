package CSC285Prob5_Pt2;

import java.io.*;
import java.util.*;

import CSC285Prob5_Pt2.LinkManager;

/*
 * Russell Ferguson
 * March 24, 2017
 * CSC285 Data Structures - Kent Pickett
 */
public class CSC285Prob5_Pt2 {
	static double a[][]=	{{1, 2},
				{3, 4}};
	static double b[][]=	{{6, 6},
				{8, 8}};
	static double c[][]=	{{1, 2},
				{2, 1}};
	public static void main(String[] args){
		LinkManager<Character> oprtrStack = new LinkManager<Character>();
		LinkManager<double[][]> oprndStack = new LinkManager<double[][]>();
		
		char[] expr1 = "2*A-3*((B-2*C)/(A+3)-B*3)".toCharArray();
		
		for(int i = 0; i<expr1.length;i++){
			if(Character.isDigit(expr1[i])){
				double tmp = Double.parseDouble(String.valueOf(expr1[i]));
				double[][] tmp2 = {{tmp,tmp}, {tmp,tmp}};
				oprndStack.push(tmp2);
			}else if(Character.isAlphabetic(expr1[i])){
				oprndStack.push(getVal(expr1[i]));
			}else if(expr1[i]=='('){
				oprtrStack.push(expr1[i]);
			}else if(expr1[i]==')'){
				while(oprtrStack.peek()!='('){
					evalExpr(oprndStack, oprtrStack);
				}
				oprtrStack.pop();
			}else{;
				int oprior = getPrio(expr1[i]);
				while(oprtrStack.peek()!=null && oprior <= getPrio(oprtrStack.peek())){
					evalExpr(oprndStack, oprtrStack);
				}
				oprtrStack.push(expr1[i]);
			}
		}
		while(oprndStack.size()>1){
			evalExpr(oprndStack, oprtrStack);
		}
		double[][] End = oprndStack.peek();
		System.out.printf("Result:\n\n%f\t%f\n%f\t%f", End[0][0], End[0][1], End[1][0], End[1][1]);
	}
	public static double[][] eval(char oprtr, double[][] oper1, double[][] oper2){
		double w=-9999;
		double x=-9999;
		double y=-9999;
		double z=-9999;
		switch (oprtr){
			case '*':	w=oper1[0][0]*oper2[0][0];
						x=oper1[0][1]*oper2[0][1];
						y=oper1[1][0]*oper2[1][0];
						z=oper1[1][1]*oper2[1][1];
						break;
						
			case '/':	if(oper2[0][0]!=0 && oper2[0][1]!=0 && oper2[1][0]!=0 && oper2[1][1]!=0){
							w=oper1[0][0]/oper2[0][0];
							x=oper1[0][1]/oper2[0][1];
							y=oper1[1][0]/oper2[1][0];
							z=oper1[1][1]/oper2[1][1];
						}else{
							System.out.println("Attempted divide by zero not allowed! Will now exit!"); System.exit(-1);
						};break;
			case '+': 	w=oper1[0][0]+oper2[0][0];
						x=oper1[0][1]+oper2[0][1];
						y=oper1[1][0]+oper2[1][0];
						z=oper1[1][1]+oper2[1][1];
						break;
			case '-': 	w=oper1[0][0]-oper2[0][0];
						x=oper1[0][1]-oper2[0][1];
						y=oper1[1][0]-oper2[1][0];
						z=oper1[1][1]-oper2[1][1];
						break;
		}
		double[][] result = {{w,x},{y,z}};
		if(result[0][0] == -9999){System.out.println("This is a bad operator! Will now exit!"); System.exit(-2);}
		return result;
	}
	
	public static void evalExpr(LinkManager<double[][]> oprnds, LinkManager<Character> oprtrs){
		double[][] a, b, c;
		char operator = oprtrs.pop();
		a=oprnds.pop();
		b=oprnds.pop();
		c = eval(operator, b, a);
		oprnds.push(c);
		return;
	}
	//gets array constant and returns it
	public static double[][] getVal(Character chara){
		double[][] result = {{0,0},{0,0}};
		switch(chara){
			case 'A': result = a; break;
			case 'B': result = b; break;
			case 'C': result = c; break;
		}
		if(result[0][0] == 0){System.out.println("Bad Operand Char! Will now exit!"); System.exit(-3);}
		return result;
	}
	
	//gets priority value from operator
	public static int getPrio(Character chara){
		int result = -9999;
		switch(chara){
			case ')': result = 99; break;
			case '*': result = 2; break;
			case '/': result = 2; break;
			case '+': result = 1; break;
			case '-': result = 1; break;
			case '(': result =-99; break;
		}
		if(result == -9999){System.out.printf("You passed a bad/nonexistent character for priority determination! %s Will now exit!", chara); System.exit(-7);} 
		return result;
	}
}
