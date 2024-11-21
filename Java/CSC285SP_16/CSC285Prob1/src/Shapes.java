<<<<<<< HEAD:Java/CSC285Prob1/src/Shapes.java
import java.io.*;
import java.util.*;
import java.math.*;

/*/
 *Author:	Russell Ferguson
 *Date:		February 2, 2016
 *Class:	CSC285 - Data Structures
 *Professor:Kent Pickett - Missouri Western State University
 */
public class Shapes{

	public static void main(String[] args){
		Solids MySolids[] =	{new Cubes(4,4,4), new Cubes(8,8,8), new Spheres(6,6,6), new Spheres(3,3,3),new Cones(5,6,6),
							new Cones(3,12,12), new Bricks(3,6,9), new Bricks(2,4,6), new TCone(5,6,3), new TCone(8,4,6)};
		final int arrayLength=MySolids.length;
		int index=0;	//For targeting the shape with min/max surface area
		//Print each solid
		System.out.println("Printing All Solids...\n----------------------------------------------");
		for(int i=0;i<arrayLength;i++){
			MySolids[i].printSolid();
		}
		System.out.println("\nPrinting All Solids' Area and Volumes only...\n-----------------------------------------------");
		//Calc and print total area and total volume for all solids
		for(int i=0;i<arrayLength;i++){
			MySolids[i].printVolArea();
		}
		
		//Find and Print object with Max Area
		System.out.println("\nFinding Solid with Maximum Surface Area...\n-----------------------------------------------");
		for(int i=0;i<arrayLength;i++){
			if(MySolids[i].compareTo(MySolids[index])>0.0){
				index=i;
			}
		}
		System.out.println("The Solid in the Array of Solids with the Max Surface Areas is at Index: "+index);
		MySolids[index].printSolid();
		
		//Find and Print Object with Min Area
		System.out.println("\nFinding Solid with Minimum Surface Area...\n-----------------------------------------------");
		for(int i=0;i<arrayLength;i++){
			if(MySolids[i].compareTo(MySolids[index])<0.0){
				index=i;
			}
		}
		System.out.println("\nThe Solid in the Array of Solids with the Min Surface Areas is at Index: "+index);
		MySolids[index].printSolid();
		
		//Sort the array MySolids based on area, and print. Going to use bubble sort
		System.out.println("\nPrinting All Solids, Sorted Greatest Surface Area to Least...\n----------------------------------------------");
		for(int i=0;i<arrayLength-1;i++){
			for(int j=1;j<arrayLength-i;j++){
				if(MySolids[j-1].area<MySolids[j].area){
					Solids tmp=MySolids[j-1];
					MySolids[j-1]=MySolids[j];
					MySolids[j]=tmp;
				}
			}
		}
		//Print each solid
		for(int i=0;i<arrayLength;i++){
			MySolids[i].printSolid();
		}
	}
}	
abstract class Solids{
	//basic measurement vars 
	protected int len;	//I'd rather name these a, b, c
	protected int wid;
	protected int hig;
	protected double area; //surface area
	protected double volume;

	//methods - calculating, "getters", print.
	abstract public void calcVol();	//Depending on the solid, calculates differently. Abstract.
	abstract public void calcArea();	//Same as above, but Surface Area instead. Abstract.
	/*public double getVol(){	//Getter for Volume. Not needed.
		return volume;
	}
	public double getArea(){ //Getter for SurfArea. Not needed.
		return area;
	}*/
	abstract public void printSolid(); //name, major dimensions, area/volume. Abstract.
	abstract public void printVolArea(); //prints shape, surface area and volume. Abstract.
	public double compareTo(Solids A){ //Compares Surface Area of this Solid, to a second Solid
		double compare=this.area-A.area;
		return compare;
	}
	public void swap(Solids A, Solids B){
	Solids tmp=A;
	A=B;
	B=tmp;
	}
}	
//Solids subclasses
class Cubes extends Solids{	//Done
	//Side, Side, Side
	public Cubes(int i, int j, int k){
		len=i;
		hig=j;
		wid=k;
		calcVol();
		calcArea();
	}
	@Override
	public void calcVol() {
		volume=len*len*len;
	}
	@Override
	public void calcArea() {
		area=6*(len*len);
	}
	@Override
	public void printSolid() {
		System.out.printf("Cube:\t\tSide Length:\t%3d\t\t\t\t\t\tVolume: %3.4f  \tSurface Area: %3.4f\n", len, volume, area);
	}
	@Override
	public void printVolArea(){
		System.out.printf("Cube:\t\tVolume: %3.4f  \tSurface Area: %3.4f\n", volume, area);
	}
}
class Spheres extends Solids{ //Done
	//(radius, radius, radius)
	public Spheres(int i, int j, int k){
		len=i;
		hig=j;
		wid=k;
		calcVol();
		calcArea();
	}
	@Override
	public void calcVol(){
		volume=(4.0/3.0)*(Math.PI)*(len*len*len);
	}
	@Override
	public void calcArea(){
		area=4.0*Math.PI*(len*len);
	}
	@Override
	public void printSolid(){
		System.out.printf("Sphere:\t\tRadius: \t%3d \t\t\t\t\t\tVolume: %3.4f   \tSurface Area: %3.4f\n", len, volume, area);
	}
	@Override
	public void printVolArea(){
		System.out.printf("Sphere:\t\tVolume: %3.4f  \tSurface Area: %3.4f\n", volume, area);
	}
}
class Cones extends Solids{ //Done
	//(radius, height, height)
	public Cones(int i, int j, int k){
		len=i;
		hig=j;
		wid=k;
		calcVol();
		calcArea();
	}
	@Override
	public void calcVol() {
		volume=(1.0/3.0)*Math.PI*(len*len)*hig;
	}
	@Override
	public void calcArea() {
		area=Math.PI*len*(len+Math.sqrt((hig*hig)+(len*len)));
	}
	@Override
	public void printSolid() {
		System.out.printf("Cones:\t\tRadius: \t%3d\tHeight:%3d\t\t\t\tVolume: %3.4f  \tSurface Area: %3.4f\n", len, hig, volume, area);
	}
	@Override
	public void printVolArea(){
		System.out.printf("Cones:\t\tVolume: %3.4f  \tSurface Area: %3.4f\n", volume, area);
	}
}
class Bricks extends Solids{ //Done
	//length, height, width
	public Bricks(int i, int j, int k){
		len=i;
		hig=j;
		wid=k;
		calcVol();
		calcArea();
	}
	@Override
	public void calcVol() {
		volume=len*hig*wid;
	}
	@Override
	public void calcArea() {
		area=2*(len*wid)+2*(len*hig)+2*(wid*hig);
	}
	@Override
	public void printSolid() {
		System.out.printf("Parallelipiped:\tLength: \t%3d\tHeight:%3d\tWidth:%3d\t\tVolume: %3.4f  \tSurface Area: %3.4f\n", len, hig, wid, volume, area);
	}
	@Override
	public void printVolArea(){
		System.out.printf("Paralellipiped:\tVolume: %3.4f  \tSurface Area: %3.4f\n", volume, area);
	}
}
class TCone extends Solids{	//Done
	//radius1, height, radius2
	public TCone(int i, int j, int k){
		len=i;
		hig=j;
		wid=k;
		calcVol();
		calcArea();
	}
	@Override
	public void calcVol() {
		volume=(1.0/3.0)*(Math.PI)*(((len*len)+(len*wid)+(wid*wid))*hig);
	}
	@Override
	public void calcArea() {
		area=(Math.PI)*(Math.sqrt((hig*hig)+((len*len)-(wid*wid))*((len*len)-(wid*wid))))*(len+wid)+(len*len)+(wid*wid);
	}
	@Override
	public void printSolid() {
		System.out.printf("Truncated Cone:\tRadius (Base):\t%3d\tHeight:%3d\tRadius (Top):%3d\tVolume: %3.4f  \tSurface Area: %3.4f\n", len, hig, wid, volume, area);
	}
	@Override
	public void printVolArea(){
		System.out.printf("Truncated Cone:\tVolume: %3.4f  \tSurface Area: %3.4f\n", volume, area);
	}
=======
import java.io.*;
import java.util.*;
import java.math.*;

/*/
 *Author:	Russell Ferguson
 *Date:		February 2, 2016
 *Class:	CSC285 - Data Structures
 *Professor:Kent Pickett - Missouri Western State University
 */
public class Shapes{

	public static void main(String[] args){
		Solids MySolids[] =	{new Cubes(4,4,4), new Cubes(8,8,8), new Spheres(6,6,6), new Spheres(3,3,3),new Cones(5,6,6),
							new Cones(3,12,12), new Bricks(3,6,9), new Bricks(2,4,6), new TCone(5,6,3), new TCone(8,4,6)};
		final int arrayLength=MySolids.length;
		int index=0;	//For targeting the shape with min/max surface area
		//Print each solid
		System.out.println("Printing All Solids...\n----------------------------------------------");
		for(int i=0;i<arrayLength;i++){
			MySolids[i].printSolid();
		}
		System.out.println("\nPrinting All Solids' Area and Volumes only...\n-----------------------------------------------");
		//Calc and print total area and total volume for all solids
		for(int i=0;i<arrayLength;i++){
			MySolids[i].printVolArea();
		}
		
		//Find and Print object with Max Area
		System.out.println("\nFinding Solid with Maximum Surface Area...\n-----------------------------------------------");
		for(int i=0;i<arrayLength;i++){
			if(MySolids[i].compareTo(MySolids[index])>0.0){
				index=i;
			}
		}
		System.out.println("The Solid in the Array of Solids with the Max Surface Areas is at Index: "+index);
		MySolids[index].printSolid();
		
		//Find and Print Object with Min Area
		System.out.println("\nFinding Solid with Minimum Surface Area...\n-----------------------------------------------");
		for(int i=0;i<arrayLength;i++){
			if(MySolids[i].compareTo(MySolids[index])<0.0){
				index=i;
			}
		}
		System.out.println("\nThe Solid in the Array of Solids with the Min Surface Areas is at Index: "+index);
		MySolids[index].printSolid();
		
		//Sort the array MySolids based on area, and print. Going to use bubble sort
		System.out.println("\nPrinting All Solids, Sorted Greatest Surface Area to Least...\n----------------------------------------------");
		for(int i=0;i<arrayLength-1;i++){
			for(int j=1;j<arrayLength-i;j++){
				if(MySolids[j-1].area<MySolids[j].area){
					Solids tmp=MySolids[j-1];
					MySolids[j-1]=MySolids[j];
					MySolids[j]=tmp;
				}
			}
		}
		//Print each solid
		for(int i=0;i<arrayLength;i++){
			MySolids[i].printSolid();
		}
	}
}	
abstract class Solids{
	//basic measurement vars 
	protected int len;	//I'd rather name these a, b, c
	protected int wid;
	protected int hig;
	protected double area; //surface area
	protected double volume;

	//methods - calculating, "getters", print.
	abstract public void calcVol();	//Depending on the solid, calculates differently. Abstract.
	abstract public void calcArea();	//Same as above, but Surface Area instead. Abstract.
	/*public double getVol(){	//Getter for Volume. Not needed.
		return volume;
	}
	public double getArea(){ //Getter for SurfArea. Not needed.
		return area;
	}*/
	abstract public void printSolid(); //name, major dimensions, area/volume. Abstract.
	abstract public void printVolArea(); //prints shape, surface area and volume. Abstract.
	public double compareTo(Solids A){ //Compares Surface Area of this Solid, to a second Solid
		double compare=this.area-A.area;
		return compare;
	}
	public void swap(Solids A, Solids B){
	Solids tmp=A;
	A=B;
	B=tmp;
	}
}	
//Solids subclasses
class Cubes extends Solids{	//Done
	//Side, Side, Side
	public Cubes(int i, int j, int k){
		len=i;
		hig=j;
		wid=k;
		calcVol();
		calcArea();
	}
	@Override
	public void calcVol() {
		volume=len*len*len;
	}
	@Override
	public void calcArea() {
		area=6*(len*len);
	}
	@Override
	public void printSolid() {
		System.out.printf("Cube:\t\tSide Length:\t%3d\t\t\t\t\t\tVolume: %3.4f  \tSurface Area: %3.4f\n", len, volume, area);
	}
	@Override
	public void printVolArea(){
		System.out.printf("Cube:\t\tVolume: %3.4f  \tSurface Area: %3.4f\n", volume, area);
	}
}
class Spheres extends Solids{ //Done
	//(radius, radius, radius)
	public Spheres(int i, int j, int k){
		len=i;
		hig=j;
		wid=k;
		calcVol();
		calcArea();
	}
	@Override
	public void calcVol(){
		volume=(4.0/3.0)*(Math.PI)*(len*len*len);
	}
	@Override
	public void calcArea(){
		area=4.0*Math.PI*(len*len);
	}
	@Override
	public void printSolid(){
		System.out.printf("Sphere:\t\tRadius: \t%3d \t\t\t\t\t\tVolume: %3.4f   \tSurface Area: %3.4f\n", len, volume, area);
	}
	@Override
	public void printVolArea(){
		System.out.printf("Sphere:\t\tVolume: %3.4f  \tSurface Area: %3.4f\n", volume, area);
	}
}
class Cones extends Solids{ //Done
	//(radius, height, height)
	public Cones(int i, int j, int k){
		len=i;
		hig=j;
		wid=k;
		calcVol();
		calcArea();
	}
	@Override
	public void calcVol() {
		volume=(1.0/3.0)*Math.PI*(len*len)*hig;
	}
	@Override
	public void calcArea() {
		area=Math.PI*len*(len+Math.sqrt((hig*hig)+(len*len)));
	}
	@Override
	public void printSolid() {
		System.out.printf("Cones:\t\tRadius: \t%3d\tHeight:%3d\t\t\t\tVolume: %3.4f  \tSurface Area: %3.4f\n", len, hig, volume, area);
	}
	@Override
	public void printVolArea(){
		System.out.printf("Cones:\t\tVolume: %3.4f  \tSurface Area: %3.4f\n", volume, area);
	}
}
class Bricks extends Solids{ //Done
	//length, height, width
	public Bricks(int i, int j, int k){
		len=i;
		hig=j;
		wid=k;
		calcVol();
		calcArea();
	}
	@Override
	public void calcVol() {
		volume=len*hig*wid;
	}
	@Override
	public void calcArea() {
		area=2*(len*wid)+2*(len*hig)+2*(wid*hig);
	}
	@Override
	public void printSolid() {
		System.out.printf("Parallelipiped:\tLength: \t%3d\tHeight:%3d\tWidth:%3d\t\tVolume: %3.4f  \tSurface Area: %3.4f\n", len, hig, wid, volume, area);
	}
	@Override
	public void printVolArea(){
		System.out.printf("Paralellipiped:\tVolume: %3.4f  \tSurface Area: %3.4f\n", volume, area);
	}
}
class TCone extends Solids{	//Done
	//radius1, height, radius2
	public TCone(int i, int j, int k){
		len=i;
		hig=j;
		wid=k;
		calcVol();
		calcArea();
	}
	@Override
	public void calcVol() {
		volume=(1.0/3.0)*(Math.PI)*(((len*len)+(len*wid)+(wid*wid))*hig);
	}
	@Override
	public void calcArea() {
		area=(Math.PI)*(Math.sqrt((hig*hig)+((len*len)-(wid*wid))*((len*len)-(wid*wid))))*(len+wid)+(len*len)+(wid*wid);
	}
	@Override
	public void printSolid() {
		System.out.printf("Truncated Cone:\tRadius (Base):\t%3d\tHeight:%3d\tRadius (Top):%3d\tVolume: %3.4f  \tSurface Area: %3.4f\n", len, hig, wid, volume, area);
	}
	@Override
	public void printVolArea(){
		System.out.printf("Truncated Cone:\tVolume: %3.4f  \tSurface Area: %3.4f\n", volume, area);
	}
>>>>>>> 6d347bdd09aff18be649bee9af0def901790d93d:Java/CSC285Spring2016/CSC285Prob1/src/Shapes.java
}