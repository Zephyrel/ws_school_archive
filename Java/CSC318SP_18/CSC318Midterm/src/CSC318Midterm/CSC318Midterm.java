package CSC318Midterm;
import java.io.*;
import javax.swing.*;
import java.util.*;
import java.lang.*;
import java.math.*;

/*
 * Author:		Russell Ferguson Jr
 * Class:		CSC318 - Simulation and Modeling
 * Professor:	Kent Pickett
 * Date:		1/31/2018
 */

public class CSC318Midterm {
	public static void main(String[] args) throws Exception{
		PrintWriter outpt;
		outpt = new PrintWriter (new File("NewsBoyOut.txt"));
		int mode = 0, week = 0;
		cstats stats;
		mother mom;
		dmdproc wantCream;
		int dmd;	//the demand
		double totalProfit;
		
		//loop is for newsboy behavior configurations
		for(mode=0;mode<2;mode++) {
			//initialize simulation objects
			stats = new cstats();
			mom = new mother(mode);
			wantCream = new dmdproc();
			dmd = 0;	//the demand
			totalProfit = 0;
			int quarter = 1;
			//main sim loop, prints, and gathers a section of stats to an output txt file
			System.out.println("\nQuarter 1:\n");
			for(week=1; week<=52;week++) {
				if (week==14)		{	quarter = 2; System.out.println("\nQuarter 2:\n");}
				else if (week==27)	{	quarter = 3; System.out.println("\nQuarter 3:\n");}
				else if (week==40)	{	quarter = 4; System.out.println("\nQuarter 4:\n");};

				dmd = wantCream.dmdWeek(quarter);
				//set demand for the mom, run behavior configuration depending on mode as defined by loop
				mom.setDemand(dmd);
				//collect profits into the statistics
				stats.setprofit(mom.getProfit());
				//order for the next week
				if(week<14) quarter = 1;
				else if (week<27) quarter = 2;
				else if (week<40) quarter = 3;
				else if (week<53) quarter = 4;
				mom.order(mode, quarter);
				
				//print outs to console and txtfile
				if(week%1==0) {
					System.out.println("For week:\t"+week+"\tDemand:\t "+dmd+"\tBought:\t "+mom.getBought()+"\tSold:\t"+mom.getSold());
					System.out.printf("Profit:\t\t%.2f\tOrdered: " + mom.getOrdered()+"\tOrder Recieved Today: " + mom.getOrderReceived() +"\n", mom.getProfit());
					outpt.println("For week:\t"+week+"\tDemand:\t"+dmd+"\tSold:\t"+mom.getSold());
					outpt.println("Profit:\t "+mom.getProfit()+"\tOrdered:\t" + mom.getOrdered());
				}
				//add to sum of total profit made so far with this ordering policy in this set time period
				totalProfit+=mom.getProfit();
			}
			
			System.out.println("\nFinal week stats:");
			System.out.println("For week:\t"+52+"\tDemand:\t "+dmd+"\tSold:\t"+mom.getSold());
			System.out.printf("Profit:\t\t%.2f\tOrdered: " + mom.getOrdered()+"\n", mom.getProfit());
			
			System.out.println("**********Statistics for 52 weeks of Sales**********");
			System.out.printf("Total Profit over period: %.2f\t", totalProfit);
			System.out.println("Average profit:\t"+stats.getAverage());
			System.out.println("Variance:\t"+stats.getVar()+"\tStd. Dev:\t" +stats.getStdev());
			System.out.println("Count:\t"+stats.getCount());
			
			//set a new textfile for the next mode's records to be written to
			if(mode<1) {
				outpt = new PrintWriter (new File("MotherOut"+(mode+1)+".txt"));
				System.out.println("\nReset simulation, moving to the next ordering procedure.\n");
			}
			if(mode==0)System.out.println("\nNext Ordering Policy: My Ordering Policy with a Random Number Generator\n");

		}
	}
}

class mother{
	private int mode;	//reordering policy
	private int demand;	//papers demanded by customers
	private int ordered;//papers ordered by newsboy
	private int orderReceived;
	private int bought;	//papers bought by customers
	private int sold;	//papers sold to customers by newsboy
	private double profit;
	private double lastweekOrdered;
	private double waste;
	
	//constructor. mode is a switch for the reordering policy
	public mother(int mode) {
		this.mode=mode;
		demand=0;
		ordered=55;
		bought=0;
		sold=0;
		profit=0.0;
	}
	public int order(int mode, int quarter) {
		double x = Math.random();
		if(mode==0) {	//momma ordering method
			if(quarter==1 || quarter == 4) ordered = 50;
			else if(quarter==2 || quarter == 3) ordered = 60;
		}else {	//my ordering method
			if(quarter==1) ordered = 55;
			else if(quarter == 2) ordered = 60;
			else if(quarter==3) ordered = 70;
			else if(quarter==4) ordered = 50;
		}
		return ordered;
	}
	private void behavior() {
		//demand is set before calling this, and ordered is initiated to a static 16 for the
		//first day in mode 0, static 17 for mode 1 and 2 since it is is the most likely demand with a 5/12 chance.
			double x = Math.random();
			//mom buys ice cream
			if(x>=0.0 && x<=10) orderReceived=ordered-5;
			else if(x>10.0 && x<=80.0) orderReceived=ordered;
			else if(x>80.0 && x<=100.0) orderReceived=ordered+10;
			bought=orderReceived;
			if(demand>=bought)sold=bought; //sell all papers bought for customers, to customers
			else if(demand<bought)sold=demand;
			profit=(10.00*sold)-(7.00*bought);
			if(bought>sold)waste=((bought-sold)*7.00); //wasted money

	}
	public void setDemand(int x){
		demand=x;
		behavior();
	}
	public double getProfit() {	return profit;	}
	public int getBought() {	return bought;	}
	public int getSold()	{	return sold;	}
	public int getOrdered()	{	return ordered;	}
	public int getOrderReceived()	{	return orderReceived;	}
}

class cstats{ //calculates profit and holds getters for the variables in the equation
	private double profit;
	private double psum;
	
	private double avg;
	private double stdev;
	private double variance;
	private int count;
	public cstats() {
		profit = psum = avg = stdev = variance = 0; count = 0;
	}
	public void setprofit(double x) {
		profit=x;
		count++;
		psum+=profit;
		avg=psum/count;
		variance = Math.pow(Math.abs(psum-avg), 2)/count;
		stdev = Math.sqrt(variance);
		return;
	}
	public double getProfit(){	return profit;	}
	public double getAverage(){	return avg;	}
	public double getVar()	{	return variance;	}
	public double getStdev(){	return stdev;	}
	public int getCount()	{	return count;	}
}

class dmdproc{	//rolls a random number and returns the number of papers demanded today to be set for mom
	private int demand;
	public dmdproc() {	demand = 0;	}
	public int dmdWeek(int quarter) {
		int x;
		x=(int)(Math.random()*100);
		switch(quarter) {
		case 1:
			if ( x<=30)demand=40;
			else if(x<=50)demand=50;
			else if(x<=80)demand=60;
			else if(x<=90)demand=70;
			else if(x<=100)demand=80; break;
		case 2:
			if ( x<=15)demand=40;
			else if(x<=55)demand=50;
			else if(x<=80)demand=60;
			else if(x<=90)demand=70;
			else if(x<=100)demand=80; break;
		case 3:
			if ( x<=5)demand=40;
			else if(x<=15)demand=50;
			else if(x<=45)demand=60;
			else if(x<=85)demand=70;
			else if(x<=100)demand=80; break;
		case 4:
			if ( x<=40)demand=40;
			else if(x<=80)demand=50;
			else if(x<=90)demand=60;
			else if(x<=95)demand=70;
			else if(x<=100)demand=80; break;
		}
		return demand;
	}
}
