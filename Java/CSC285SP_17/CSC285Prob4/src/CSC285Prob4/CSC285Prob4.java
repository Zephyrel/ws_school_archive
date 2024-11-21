package CSC285Prob4;
import java.io.*;
import java.util.*;

/*************************************
 *Author:	Russell Ferguson
 *Date:		February 8, 2016
 *Class:	CSC285 - Data Structures
 *Professor:Kent Pickett - Missouri Western State University
 ************************************/
public class CSC285Prob4{
	public static void main(String[] args){
		Student tmpStu = new Student();
		LinkManager<Student> AcademicClass = new LinkManager<Student>();
		Scanner input = new Scanner("Dummy message! If you're seeing this something has gone horribly wrong!"); //Gotta initialize it somehow.
		
		try{
			input = new Scanner(new File("input.txt"));
		}catch(FileNotFoundException e){
			System.err.println("Error: File not found! Stack Trace below.\n");
			e.printStackTrace();
		}
		 //Contains the table headline String
		String tableHeader = input.nextLine()+" \tTest Avg. \tStudent Class\n";
		String line = "--------------------------------------------------------------------------------------------------------------";
		String t = "";

		//Scan file and make student objects, and add them to AcademicClass
		while(input.hasNextLine()){	//DONE
			//Student ID
			t=input.next();
			tmpStu.StudentID=t.toCharArray();
			//Student Name
			t=input.next();
			while(!input.hasNextInt()){
				t+=" "+input.next();
			}
			tmpStu.StudentName=t.toCharArray();
			//Test Scores
			tmpStu.test1=input.nextInt();
			tmpStu.test2=input.nextInt();
			tmpStu.test3=input.nextInt();
			//new vars
			tmpStu.hours=input.nextInt();
			tmpStu.gpa=input.nextFloat();
			AddStudent(AcademicClass, tmpStu);
			tmpStu=new Student();	//must re-instantialize a new Student Object after every Student Object has been stored
			//Otherwise, when added it will "overwrite" the previous student objects in a sense
			//Each Student Object in the ArrayList will refer to the same tmpStu object
			//(aka the last read, single Student Record) if it is not reinstantialized
		}
		
		//DONE:Print as read
		System.out.println(line+'\n'+tableHeader+line);
		printRecords(AcademicClass);
		
		//DONE: Delete Students by StudentID 42P4 and 45A3 from ArrayList
		DeleteStudent(AcademicClass, "42P4");
		DeleteStudent(AcademicClass, "45A3");
		System.out.println('\n'+line+'\n'+tableHeader+line);
		printRecords(AcademicClass);
		
		//DONE: Add the following students to the ArrayList
		//StudentID	StudentName		Test 1	Test 2	Test3
		//67T4		Clouse B		80		75		98
		//45P5		Garrison J		75		78		72
		//89P0		Singer A		85		95		99
		tmpStu = new Student( "67T4".toCharArray(), "Clouse B  ".toCharArray(), 80, 75, 98, 102, (float)3.65);
		AddStudent(AcademicClass, tmpStu);
		System.out.println("\nStudent with the following information has been added: \n"+tmpStu.print());
		tmpStu = new Student ( "45P5".toCharArray(), "Garrison J".toCharArray(), 75, 78, 72, 39, (float)1.85);
		AddStudent(AcademicClass, tmpStu);
		System.out.println("\nStudent with the following information has been added: \n"+tmpStu.print());
		tmpStu = new Student ( "89P0".toCharArray(), "Singer A  ".toCharArray(), 85, 95, 99, 130, (float)3.87);
		AddStudent(AcademicClass, tmpStu);
		System.out.println("\nStudent with the following information has been added: \n"+tmpStu.print());
		
		//DONE: Sort in Ascending order of greatest to least by Test Avg
		//SortAsc(AcademicClass);
		SortAscStu(AcademicClass);
		System.out.println('\n'+line+'\n'+tableHeader+line);
		printRecords(AcademicClass);
		//Below: Part 2
		LinkManager<Double> doubleList = new LinkManager<Double>();
		double set[] = {2.3,1.5,15.6,0.9,11.2,8.9,12.4,51.2,-3.5};
		for(int i=0;i<set.length;i++){
			doubleList.addFirst(set[i]);
		}
		
		for(int i=0;i<doubleList.size()-1;i++){
			for(int j=1;j<doubleList.size()-i;j++){
				Double tmp = doubleList.get(j-1);
				Double tmp2 = doubleList.get(j);
				if(tmp<tmp2){
					doubleList.set(j-1,tmp2);
					doubleList.set(j, tmp);
				}
			}				
		}
		
		System.out.println("\n\nPrinting a sorted list of doubles");
		for(int i=0;i<set.length;i++){
			System.out.println(doubleList.get(i));
		}
		
		
		
	}
	
	//methods
	public static void AddStudent( LinkManager<Student> AcademicClass, Student Obj){	//This is a useless method but on the assignment.
		AcademicClass.addFirst(Obj);
		//System.out.println("\nStudent with the following information has been added: "+Obj.myprint());
	}
	public static void DeleteStudent( LinkManager<Student> AcademicClass, String StuID){
		for(int i=0;i<AcademicClass.size();i++){
			String s=new String(AcademicClass.get(i).StudentID);
			if(s.equals(StuID)){
				AcademicClass.remove(i);
			}
		}
		System.out.println("\nStudent with Student ID "+StuID+" has been deleted off the records.");
	}
	public static void SortAscStu( LinkManager<Student> AcademicClass){	//DONE
		//Bubble Sort Ascending (Greatest to Least)
		for(int i=0;i<AcademicClass.size()-1;i++){
			for(int j=1;j<AcademicClass.size()-i;j++){
				Student tmp = AcademicClass.get(j-1);
				Student tmp2 = AcademicClass.get(j);
				if(tmp.getAverage()<tmp2.getAverage()){
					AcademicClass.set(j-1,tmp2);
					AcademicClass.set(j, tmp);
				}
			}				
		}
	}
	public static void SortDescStu( LinkManager<Student> AcademicClass){	//DONE: This is new for Hmwk4, I just had to change the if minorly
		//Bubble Sort Ascending (Least to Greatest)
		for(int i=0;i<AcademicClass.size()-1;i++){
			for(int j=1;j<AcademicClass.size()-i;j++){
				Student tmp = AcademicClass.get(j-1);
				Student tmp2 = AcademicClass.get(j);
				if(tmp.getAverage()>tmp2.getAverage()){
					AcademicClass.set(j-1,tmp2);
					AcademicClass.set(j, tmp);
				}
			}				
		}
	}
	public static void printRecords(LinkManager<Student> AcademicClass){	//Prints whole table of records
		for(int i=0;i<AcademicClass.size();i++){
			Student tmpStu = new Student();
			tmpStu=AcademicClass.get(i);
			System.out.println(tmpStu.print());
		}
	}
}

class Student implements Comparable<Student>{
	char[] StudentID=new char[4];
	char[] StudentName=new char[10];
	int test1;
	int test2;
	int test3;
	int hours;
	float gpa;
	
	//Default Constructor
	public Student(){
		StudentID = "Dbug".toCharArray();
		test1=0;
		test2=0;
		test3=0;
		StudentName = "ERROR FAIL".toCharArray();
	}
	public Student(char[] ID, char[] name, int test1, int test2, int test3, int hours, float gpa){
		this.StudentID=ID;
		this.StudentName=name;
		this.test1=test1;
		this.test2=test2;
		this.test3=test3;
		this.hours=hours;
		this.gpa=gpa;
		this.gpa=this.recalcGPA();
	}
	//Not part of part 1 but this makes my life easier for sorting, so I'm implementing this
	@Override
	public int compareTo(Student a){
		int i=0;
		if((this.getAverage()-a.getAverage())>0.0){
			i=1;
		}else{
			i=-1;
		}
		return i;
	}
	public double getAverage(){	//returns a double-floating-point precision calculation of average test score
		return (double)(1.0/3.0)*(this.test1+this.test2+this.test3);
	}
	public String getLetter(){
		double x=this.getAverage();
		String s="";
		if(x>=100.0){ //Never used but just because
			s="A+";
		}else if(x>=90.0){
			s="A";
		}else if(x>=80.0){
			s="B";
		}else if(x>=70.0){
			s="C";
		}else if(x>=60.0){
			s="D";
		}else{
			s="F";
		}
		return s;
	}
	public String getStuClass(){
		int x=this.hours;
		String s="";
		if(x<=30){	//0-30
			s="FR";
		}else if(x<=60){	//31-60
			s="SO";
		}else if(x<=90){	//61-90
			s="JR";
		}else if(x>=91){	//91+
			s="SR";
		}
		return s;
	}
	public float recalcGPA(){
		//Hours for current class is set equal to 3
		return ((this.gpa*this.hours)+(3*this.gpa))/(hours+3);
	}
	public String print(){
		return String.format("%s \t\t%s     \t%d\t%d\t%d\t%d\t\t%1.2f\t%3.2f \t\t%s", new String(this.StudentID), new String(this.StudentName),
				this.test1, this.test2, this.test3, this.hours, this.gpa, this.getAverage(), this.getStuClass());
	}
}