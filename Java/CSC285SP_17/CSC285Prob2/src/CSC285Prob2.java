import java.io.*;
import java.util.*;

/*************************************
 *Author:	Russell Ferguson
 *Date:		February 6, 2017
 *Class:	CSC285 - Data Structures
 *Professor:Kent Pickett - Missouri Western State University
 ************************************/
public class CSC285Prob2{
	public static void main(String[] args){
		Student tmpStu = new Student();
		ArrayList<Student> AcademicClass = new ArrayList<Student>();
		Scanner input = new Scanner("Dummy message! If you're seeing this something has gone horribly wrong!"); //Gotta initialize it somehow.
		
		try{
			input = new Scanner(new File("input.txt"));
		}catch(FileNotFoundException e){
			System.err.println("Error: File not found! Stack Trace below.\n");
			e.printStackTrace();
		}
		 //Contains the table headline String
		String tableHeader = input.nextLine()+" \tTest Avg.\n";
		String line = "-----------------------------------------------------------------";
		String t = "";

		//Scan file and make student objects, and add them to AcademicClass
		while(input.hasNextLine()){	//DONE
			int i = 0;	//tmp number to hold test scores
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
			//System.out.println(tmpStu.myToString());
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
		tmpStu = new Student( "67T4".toCharArray(), 80, 75, 98, "Clouse B  ".toCharArray());
		AddStudent(AcademicClass, tmpStu);
		System.out.println("\nStudent with the following information has been added: \n"+tmpStu.toString());
		tmpStu = new Student ( "45P5".toCharArray(), 75, 78, 72, "Garrison J".toCharArray());
		AddStudent(AcademicClass, tmpStu);
		System.out.println("\nStudent with the following information has been added: \n"+tmpStu.toString());
		tmpStu = new Student ( "89P0".toCharArray(), 85, 95, 99, "Singer A  ".toCharArray());
		AddStudent(AcademicClass, tmpStu);
		System.out.println("\nStudent with the following information has been added: \n"+tmpStu.toString());
		
		//DONE: Sort in Ascending order of greatest to least by Test Avg
		SortAsc(AcademicClass);
		System.out.println('\n'+line+'\n'+tableHeader+line);
		printRecords(AcademicClass);
	}
	
	//methods
	public static void AddStudent( ArrayList<Student> AcademicClass, Student Obj){	//This is a useless method but on the assignment.
		AcademicClass.add(Obj);
		//System.out.println("\nStudent with the following information has been added: "+Obj.myToString());
	}
	public static void DeleteStudent( ArrayList<Student> AcademicClass, String StuID){
		for(int i=0;i<AcademicClass.size();i++){
			String s="";
			for(int j=0;j<AcademicClass.get(i).StudentID.length;j++){
				s+=AcademicClass.get(i).StudentID[j];
			}
			if(s.equals(StuID)){
				AcademicClass.remove(i);
			}
		}
		System.out.println("\nStudent with Student ID "+StuID+" has been deleted off the records.");
	}
	public static void SortAsc( ArrayList<Student> AcademicClass){	//DONE
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
	public static void printRecords(ArrayList<Student> AcademicClass){	//Prints whole table of records
		for(int i=0;i<AcademicClass.size();i++){
			Student tmpStu = new Student();
			tmpStu=AcademicClass.get(i);
			System.out.println(tmpStu.toString());
		}
	}
}

class Student implements Comparable<Student>{
	char[] StudentID=new char[4];
	int test1;
	int test2;
	int test3;
	char[] StudentName=new char[10];
	
	//Default Constructor
	public Student(){
		StudentID = "Dbug".toCharArray();
		test1=0;
		test2=0;
		test3=0;
		StudentName = "ERROR FAIL".toCharArray();
	}
	public Student(char[] ID, int test1, int test2, int test3, char[] name){
		this.StudentID=ID;
		this.test1=test1;
		this.test2=test2;
		this.test3=test3;
		this.StudentName=name;
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
	@Override
	public String toString(){
		String s="";	//ID
		String s2="";	//StuName
		for(int i=0;i<StudentID.length;i++){
			s+=StudentID[i];	//Read char array, store string
		}
		for(int i=0;i<StudentName.length;i++){	//Read char array, store string
			s2+=StudentName[i];
		}
		return String.format("%s \t\t%s   \t%d \t%d \t%d \t %3.3f %s", s, s2, this.test1, this.test2, this.test3, this.getAverage(), this.getLetter());
	}
}
