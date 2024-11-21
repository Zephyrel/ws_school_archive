package CSC318HW1;
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

public class CSC318HW1 {
	public static void main(String[] args) throws Exception{
		PrintWriter outpt;
		outpt = new PrintWriter (new File("NewsBoyOut.txt"));
		int i = 0, day = 0;
		cstats stats;
		newsboy joe;
		dmdproc wantPaper;
		int dmd;	//the demand today
		int dmdYesterday;
		double totalProfit;
		
		System.out.println("\nFirst Ordering Policy for Sim: Order 16 newspapers every day.\n");
		
		//loop is for newsboy behavior configurations
		for(i=0;i<3;i++) {
			//initializa simulation objects
			stats = new cstats();
			joe = new newsboy(i);
			wantPaper = new dmdproc();
			dmd = 0;	//the demand
			dmdYesterday = wantPaper.dmdToday(); //(gen a random number for the demand yesterday on day 1)
			totalProfit = 0;
			
			//main sim loop, prints, and gathers a section of stats to an output txt file
			for(day=1; day<1001;day++) {
				if(day!=1)dmdYesterday=dmd;
				// generate the demand for today
				dmd = wantPaper.dmdToday();
				//set demand for the newsboy, run behavior configuration depending on mode as defined by loop
				joe.setDemand(dmdYesterday);
				//collect profits into the statistics
				stats.setprofit(joe.getProfit());
				//order for the next day
				if(day==1 && i>0)joe.order(17);
				else joe.order(0);// 0 is default ordering schema
				
				//print outs to console and txtfile
				if(day>=495 && day<= 505) {
					System.out.println("For day:\t"+day+"\tDemand:\t "+dmd+"\tSold:\t"+joe.getSold());
					System.out.printf("Profit:\t\t%.2f\tOrdered: " + joe.getOrdered()+"\n", joe.getProfit());
					outpt.println("For day:\t"+day+"\tDemand Today:\t"+dmd+"\tSold:\t"+joe.getSold());
					outpt.println("Profit:\t "+joe.getProfit()+"\tOrdered:\t" + joe.getOrdered());
				}
				//add to sum of total profit made so far with this ordering policy in this set time period
				totalProfit+=joe.getProfit();
			}
			
			System.out.println("\nFinal day stats:");
			System.out.println("For day:\t"+day+"\tDemand:\t "+dmd+"\tSold:\t"+joe.getSold());
			System.out.printf("Profit:\t\t%.2f\tOrdered: " + joe.getOrdered()+"\n", joe.getProfit());
			
			System.out.println("**********Statistics for 1000 Days of Sales**********");
			System.out.printf("Total Profit over period: %.2f\t", totalProfit);
			System.out.println("Average profit:\t"+stats.getAverage());
			System.out.println("Variance:\t"+stats.getVar()+"\tStd. Dev:\t" +stats.getStdev());
			System.out.println("Count:\t"+stats.getCount());
			
			//set a new textfile for the next mode's records to be written to
			if(i<2) {
				outpt = new PrintWriter (new File("NewsBoyOut"+(i+1)+".txt"));
				System.out.println("\nReset simulation, moving to the next ordering procedure.\n");
			}
			if(i==0)System.out.println("\nNext Ordering Policy: Order the amount of demand of yesterday.\n");
			if(i==1)System.out.println("\nNext Ordering Policy: Order the amount of demand of yesterday, minus one.\n");
		}
	}
}

class newsboy{
	private int mode;	//reordering policy
	private int demand;	//papers demanded by customers Yesterday
	private int ordered;//papers ordered by newsboy
	private int bought;	//papers bought by customers
	private int sold;	//papers sold to customers by newsbow
	private double profit;
	
	//constructor. mode is a switch for the reordering policy
	public newsboy(int mode) {
		this.mode=mode;
		demand=0;
		if(this.mode==0)ordered=16;
		else ordered = 17;
		bought=0;
		sold=0;
		profit=0.0;
	}
	public int order(int x) {
		if(x!=0) {
			//override, just to keep the amount ordered always a specific number on day 1
			ordered = x;
		}
		if(mode==0) {
			//order 16 every day
			ordered=16;
		}else if(mode==1) {
			//order what the demand was yesterday
			ordered = demand;
		}else if(mode==2) {
			//order what the demand was yesterday - 1
			ordered = demand-1;
		}
		return ordered;
	}
	private void behavior() {
		//demand is set before calling this, and ordered is initiated to a static 16 for the
		//first day in mode 0, static 17 for mode 1 and 2 since it is is the most likely demand with a 5/12 chance.
		bought=ordered; //newsboy buys papers
		if(demand>=bought)sold=bought; //sell all papers bought for customers, to customers
		else sold=demand; //sell to the demand and save the rest of the papers for sell back
		profit=1.00*sold-0.35*bought+((bought-sold)*0.05);
			
	}
	public void setDemand(int x){
		demand=x;
		behavior();
	}
	public double getProfit() {	return profit;	}
	public double getSold()	{	return sold;	}
	public int getOrdered()	{	return ordered;	}
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

class dmdproc{	//rolls a random number and returns the number of papers demanded today to be set for the newsboy
	private int demand;
	public dmdproc() {	demand = 0;	}
	public int dmdToday() {
		int x;
		x=(int)(Math.random()*120);
		if(x<=10)demand=15;
		else if(x<=20)demand=16;
		else if(x<=70)demand=17;
		else if(x<=90)demand=18;
		else if(x<=110)demand=19;
		else if (x<=120)demand=20;
		return demand;
	}
}
