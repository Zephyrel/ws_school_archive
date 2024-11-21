package CSC318HW2;

import java.io.*;
import javax.swing.*;
import java.util.*;
import java.lang.*;
import java.math.*;

/*
 * Author:		Russell Ferguson Jr
 * Class:		CSC318 - Simulation and Modeling
 * Professor:	Kent Pickett
 * Date:		2/14/2018
 */

public class CSC318HW2 {

	public static void main(String[] args) {
			
		//Paired Rates for Dying and Not Growing for Drought, Moderate, and Heavy Rainfall, by Tree Age
		double rain1[] = {0.1, 0.7, 0.05, 0.01, 0.02, 0.02};
		double rain2[] = {0.1, 0.75, 0.05, 0.02, 0.03, 0.03};
		double rain3[] = {0.3, 0.6, 0.05, 0.03, 0.03, 0.03};
		double rain4[] = {0.35, 0.65, 0.05, 0.01, 0.04, 0.04};
		
		//beetles killer effect rate on trees by age, dependant on if the weather was Drought, Moderate, or Heavy Rainfall
		double beetle1[] = {0.10, 0.05, 0.0};
		double beetle2[] = {0.15, 0.05, 0.0};
		double beetle3[] = {0.30, 0.10, 0.02};
		double beetle4[] = {0.30, 0.10, 0.02};
		
		//forest fire rate of killing, also based on rainfall
		double fire1[] = {0.15, 0.10, 0.05};
		double fire2[] = {0.18, 0.12, 0.07};
		double fire3[] = {0.22, 0.15, 0.10};
		double fire4[] = {0.30, 0.20, 0.15};
		
		//1 million trees total, 5 to an acre
		
		int treesHarvestedTotal = 0;
		int treesKilledTotal = 0;
		cstats statsOne;
		cstats statsTwo;
		cstats statsThree;
		cstats statsFour;
		treeGroup one = new treeGroup(400000, rain1, beetle1, fire1, 1);
		treeGroup two = new treeGroup(300000, rain2, beetle2, fire2, 1);
		treeGroup three = new treeGroup(200000, rain3, beetle3, fire3, 1);
		treeGroup four = new treeGroup(100000, rain4, beetle4, fire4, 1);
		for(int policy = 0; policy<3;policy++) {
			statsOne = new cstats();
			statsTwo = new cstats();
			statsThree = new cstats();
			statsFour = new cstats();
			boolean seeding = true;
			int year = 0;
			for(year = 0;year<100;year++) {
				//event order: rain, beetle, fire, age, harvest, plant
				rainProc rain = new rainProc(seeding);
				int treesKilled = 0;
				
				treesKilled += one.rainEvent(rain);
				treesKilled += two.rainEvent(rain);
				treesKilled += three.rainEvent(rain);
				treesKilled += four.rainEvent(rain);
				
				treesKilled += one.beetleEvent(rain);
				treesKilled += two.beetleEvent(rain);
				treesKilled += three.beetleEvent(rain);
				treesKilled += four.beetleEvent(rain);
				
				treesKilled += one.fireEvent(rain);
				treesKilled += two.fireEvent(rain);
				treesKilled += three.fireEvent(rain);
				treesKilled += four.fireEvent(rain);
				//total trees harvested so far, get stats for trees that are growing per age group
				treesHarvestedTotal += four.getGrowingTrees();
				treesKilledTotal += treesKilled;
				
				statsOne.setTreesGrown(one.getGrowingTrees());
				statsTwo.setTreesGrown(two.getGrowingTrees());
				statsThree.setTreesGrown(three.getGrowingTrees());
				statsFour.setTreesGrown(four.getGrowingTrees());
				//age
				four.setGrowingTrees(three.getGrowingTrees());
				three.setGrowingTrees(two.getGrowingTrees());
				two.setGrowingTrees(one.getGrowingTrees());
				//replant trees killed
				one.setGrowingTrees(treesKilled);

			}
			//print final statistics
			System.out.println("**********Statistics for 100 years of Growing Trees**********");
			System.out.printf("Total Trees Harvested over period: %d\n", treesHarvestedTotal);
			System.out.println("Average One Year Olds Grown:\t"+statsOne.getAverage());
			System.out.println("Average Two Year Olds Grown:\t"+statsTwo.getAverage());
			System.out.println("Average Three Year Olds Grown:\t"+statsThree.getAverage());
			System.out.println("Average Four Year Olds Grown:\t"+statsFour.getAverage());
			System.out.println("Aged 1 Variance:\t"+statsOne.getVar()+"\tStd. Dev:\t" +statsOne.getStdev());
			System.out.println("Aged 2 Variance:\t"+statsTwo.getVar()+"\tStd. Dev:\t" +statsTwo.getStdev());
			System.out.println("Aged 3 Variance:\t"+statsThree.getVar()+"\tStd. Dev:\t" +statsThree.getStdev());
			System.out.println("Aged 4 Variance:\t"+statsFour.getVar()+"\tStd. Dev:\t" +statsFour.getStdev());
			System.out.println("Count:\t"+year);
			//define policy for next simulation run
			//Spray all 3 year old trees which will make them invulnerable to the beetle
			if(policy==0) {
				treesKilledTotal = 0;
				one = new treeGroup(400000, rain1, beetle1, fire1, 1);
				two = new treeGroup(300000, rain2, beetle2, fire2, 1);
				three = new treeGroup(200000, rain3, beetle3, fire3, 0);
				four = new treeGroup(100000, rain4, beetle4, fire4, 1);
				System.out.println("Next Simulation with New Policy: 3 Year Old Trees immune to beetles.");
			//spray 50% of 3 year old trees and 50% of 4 year old trees
			}else if(policy==1) {
				treesKilledTotal = 0;
				one = new treeGroup(400000, rain1, beetle1, fire1, 1);
				two = new treeGroup(300000, rain2, beetle2, fire2, 1);
				three = new treeGroup(200000, rain3, beetle3, fire3, 0.5);
				four = new treeGroup(100000, rain4, beetle4, fire4, 0.5);
				System.out.println("Next Simulation with New Policy: 3 and 4 Year Old Trees 50% immune to beetles.");
			}
		}
	}
}

class treeGroup {
	
	//beetle trees are trees invulnerable to the beetle Event
	private int growingTrees;
	private int notGrowingTrees;
	private int killed;
	
	//rate of events effect
	private double[] rainRates;
	private double[] beetleKill;
	private double[] fireKill;
	//percentage of trees vulnerable to Beetle Event
	private double beetleVuln;
	
	public treeGroup(int n, double[] rainRates, double[] beetleKill, double fireKill[], double beetleVuln) {
		growingTrees = n;
		this.rainRates = rainRates;
		this.beetleKill = beetleKill;
		this.fireKill = fireKill;
		this.beetleVuln = beetleVuln;
	}
	
	//getters
	public int getGrowingTrees() 		{	return growingTrees;	}
	public int getNotGrowingTrees() 		{	return notGrowingTrees;	}
	
	//setters
	//trees are added to a group either by aging or planting
	public void addTrees(int n)			{	growingTrees += n;	}
	public void setGrowingTrees(int n) 	{	growingTrees = n + notGrowingTrees; notGrowingTrees = 0;	}
	
	//events, in order: Rain, Seeding, Beetles, Fire
	public int rainEvent(rainProc rain) {
		int n = growingTrees;
		if(rain.getRain() >=0 && rain.getRain() < 4) {
			//kill, figure out what isn't growing, subtract from growing population
			growingTrees	=	(int)(growingTrees - growingTrees*rainRates[0]);
			notGrowingTrees =	(int)(growingTrees * rainRates[1]);
			growingTrees	-=	notGrowingTrees;
		}else if(rain.getRain()>=4 && rain.getRain() < 11) {
			growingTrees	=	(int)(growingTrees - growingTrees*rainRates[2]);
			notGrowingTrees =	(int)(growingTrees * rainRates[3]);
			growingTrees	-=	notGrowingTrees;
		}else if(rain.getRain() > 10) {
			growingTrees	=	(int)(growingTrees - growingTrees*rainRates[4]);
			notGrowingTrees =	(int)(growingTrees * rainRates[5]);
			growingTrees	-=	notGrowingTrees;
		}
		//return trees killed, the difference between totak trees before and after event
		killed += n - growingTrees - notGrowingTrees;
		return n - growingTrees - notGrowingTrees;
	}
	
	public int beetleEvent(rainProc rain) {
		int n = growingTrees+notGrowingTrees;
		
		if(rain.getRain() >=0 && rain.getRain() < 4) {
			growingTrees =		(int)(growingTrees - growingTrees * beetleKill[0] * beetleVuln);
			notGrowingTrees =	(int)(notGrowingTrees - notGrowingTrees  * beetleKill[0] * beetleVuln);
			
		}else if(rain.getRain()>=4 && rain.getRain() < 11) {
			growingTrees =		(int)(growingTrees - growingTrees * beetleKill[1] * beetleVuln);
			notGrowingTrees =	(int)(notGrowingTrees - notGrowingTrees  * beetleKill[1] * beetleVuln);
			
		}else if(rain.getRain() > 10) {
			growingTrees =		(int)(growingTrees - growingTrees * beetleKill[2] * beetleVuln);
			notGrowingTrees =	(int)(notGrowingTrees - notGrowingTrees * beetleKill[2] * beetleVuln);
		}
		//returns amount of growingTrees killed by beetles
		killed += n - growingTrees - notGrowingTrees;
		return n - growingTrees - notGrowingTrees;
	}
	
	public int fireEvent(rainProc rain) {
		int n = growingTrees + notGrowingTrees;
		double rand = Math.random();
		
		if(rain.getRain() >=0 && rain.getRain() < 4) {
			//does a fire happen? if so, start fire and kill trees
			if(rand<=rain.getFireProb()) {
				growingTrees =		(int)(growingTrees * fireKill[0]);
				notGrowingTrees =	(int)(notGrowingTrees * fireKill[0]);
			}
		}else if(rain.getRain()>=4 && rain.getRain() < 11) {
			if(rand<=rain.getFireProb()) {
				growingTrees =		(int)(growingTrees * fireKill[1]);
				notGrowingTrees =	(int)(notGrowingTrees * fireKill[1]);
			}
			
		}else if(rain.getRain() > 10) {
			if(rand<=rain.getFireProb()) {
				growingTrees =		(int)(growingTrees * fireKill[2]);
				notGrowingTrees =	(int)(notGrowingTrees * fireKill[2]);
			}
		}
		//return trees killed
		killed += n - growingTrees - notGrowingTrees;
		return n - growingTrees - notGrowingTrees;
	}

}


class cstats{ //calculates profit and holds getters for the variables in the equation
	private double treesGrown;
	private double psum;
	
	private double avg;
	private double stdev;
	private double variance;
	private int count;
	public cstats() {
		treesGrown = psum = avg = stdev = variance = 0; count = 0;
	}
	public void setTreesGrown(double x) {
		//this is verbatim from the code example but something about this seems wrong
		//But I can't quite place my fingers on it
		treesGrown=x;
		count++;
		psum+=treesGrown;
		avg=psum/count;
		variance = Math.pow(Math.abs(psum-avg), 2)/count;
		stdev = Math.sqrt(variance);
		return;
	}
	public double getTreesGrown(){	return treesGrown;	}
	public double getAverage(){	return avg;	}
	public double getVar()	{	return variance;	}
	public double getStdev(){	return stdev;	}
	public int getCount()	{	return count;	}
}

class rainProc{
	//generate rain inches this year, and resulting probability of fire and seeding/no seeding occurance if seeding is going to be used
	private int rain;
	private double fireProb;
	
	//constructor
	public rainProc(boolean seeded) {
		int x =(int)(Math.random()*100);
		if(!seeded) {
			if(x<=1)				{	rain = 1;	}
			else if(x>1 && x<=6)	{	rain = 2;	}
			else if(x>6 && x<=11)	{	rain = 3;	}
			else if(x>11 && x<=14)	{	rain = 4;	}
			else if(x>14 && x<=24)	{	rain = 5;	}
			else if(x>24 && x<=39)	{	rain = 6;	}
			else if(x>39 && x<=59)	{	rain = 7;	}
			else if(x>59 && x<=73)	{	rain = 8;	}
			else if(x>73 && x<=83)	{	rain = 9;	}
			else if(x>83 && x<=93)	{	rain = 10;	}
			else if(x>93 && x<=98)	{	rain = 11;	}
			else if(x>98 && x<=100)	{	rain = 12;	}
		}else if(seeded) {
			if(x<=1)				{	rain = 1;	}
			else if(x>1 && x<=2)	{	rain = 2;	}
			else if(x>2 && x<=3)	{	rain = 3;	}
			else if(x>3 && x<=5)	{	rain = 4;	}
			else if(x>5 && x<=15)	{	rain = 5;	}
			else if(x>15 && x<=25)	{	rain = 6;	}
			else if(x>25 && x<=45)	{	rain = 7;	}
			//This, chance for rain = 8  has been changed from 10% to 20%, as the
			//HW paper erroenously has all these percentages add up to 90% total, not 100%
			else if(x>45 && x<=65)	{	rain = 8;	}	
			else if(x>65 && x<=75)	{	rain = 9;	}
			else if(x>75 && x<=85)	{	rain = 10;	}
			else if(x>85 && x<=95)	{	rain = 11;	}
			else if(x>95 && x<=100)	{	rain = 12;	}
		}
		fireProb = calcFireProb(rain);
	}
	
	private double calcFireProb(int n) {
		double x = 0;
		switch(n) {
			case 1: x = 0.9;	break;
			case 2: x = 0.85;	break;
			case 3: x = 0.65;	break;
			case 4: x = 0.55;	break;
			case 5: x = 0.45;	break;
			case 6: x = 0.30;	break;
			case 7: x = 0.15;	break;
			case 8: x = 0.10;	break;
			case 9: x = 0.05;	break;
			case 10: x = 0.03;	break;
			case 11: x = 0.02;	break;
			case 12: x = 0.01;	break;
		}
		return x;
	}
	public int getRain()		{	return rain;	}
	public double getFireProb()	{	return fireProb;}
}
