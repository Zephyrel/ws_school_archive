package CSC318Test2;
import java.io.*;
import javax.swing.*;
import java.util.*;
import java.lang.*;
import java.math.*;

/*
 * Author:		Russell Ferguson Jr
 * Class:		CSC318 - Simulation and Modeling
 * Professor:	Kent Pickett
 * Date:		3/26/2018
 */

//	Calculate:
//	Average cars waiting in line on NS Road (every light cycle?)
//	Average cars waiting in line on EW Road	(every light cycle?)
//	Average time waiting in line on NS Road (seconds)
//	Average time waiting in line on EW Road (seconds)

public class CSC318Test2 {
	public static void main(String[] args) throws Exception{
		//arrival rate 1 car / 30 sec
		Road ewCars = new Road();		
		//arrival rate 1 car / 20 sec
		Road nsCars = new Road();
		stats ewWaitTime = new stats();
		stats nsWaitTime = new stats();
		stats ewWaitCars = new stats();
		stats nsWaitCars = new stats();
		int endHour = 1000;
		long bigTime = 0;	//bigTime in simulation
		int smallTime = 0;	//300sec per light cycle
		
		//let's fill up the roads with say, 20 cars apiece
		int nsStart = 20;
		int ewStart = 20;
		for(int i=0; i<ewStart;i++) {
			ewCars.addFirst(new Car(0));
		}
		for(int j=0; j<nsStart;j++) {
			nsCars.addFirst(new Car(1));
		}
		
		for(int hour = 1; hour <= endHour; hour++) {
			//12 light cycles per hour
			for(int i = 0; i < 12; i++) {
				smallTime=0;
				//roll for a 50% of oncoming NS traffic this light cycle
				boolean oncoming = false;
				double x=(Math.random()*100);
				if(x < 50.0 && x >= 0) {			oncoming = true;
				}else if(x >= 50.00 && x <= 100.0)	oncoming = false;
				
				while(smallTime<300) {
					if(bigTime%30==0) ewCars.add(new Car(0)); //add car every 30 sec
					if(bigTime%180==0) {	//add... 2/3 car every min. We'll add 2 cars every 3 min.
						nsCars.add(new Car(1)); 
						nsCars.add(new Car(1));
					}
					//the first car in the queue that was going is gone and no longer here, remove

					if(!ewCars.isEmpty())if(ewCars.getFirst().isGoing()==2) ewCars.removeFirst();
					if(!nsCars.isEmpty())if(nsCars.getFirst().isGoing()==2) nsCars.removeFirst();
					
					//EW Green
					if(smallTime<=120) {
						//STAT CALL
						//get nsCarsWaiting stat and track
						if(smallTime==0 && !nsCars.isEmpty()) {
							if(nsCars.getFirst().isWaiting) nsWaitCars.setTracking(nsCars.size());
							else if(nsCars.getFirst().isGoing()>0) nsWaitCars.setTracking(nsCars.size()-1); //in case there's a car still "going"
						}else if(smallTime==121 && nsCars.isEmpty()) nsWaitCars.setTracking(0);
						
						//STAT CALL
						//if there's nobody still crossing intersection from NS light
						//set the first car in the queue to go, get wait time for stat, otherwise wait;
						if(!nsCars.isEmpty()) {
							if(nsCars.getFirst().isWaiting) {
								if(!ewCars.isEmpty()) {
									ewCars.setGo();
									ewWaitTime.setTracking(ewCars.getFirst().getWaitTime());
								}
							}
						}else {
							if(!ewCars.isEmpty()) {
								ewCars.setGo();
								ewWaitTime.setTracking(ewCars.getFirst().getWaitTime());
							}
						}
						
						
					//NS L-Turn Green, getPath=0
					}else if(smallTime<=150) {
						//STAT CALL
						//get ewCarsWaiting stat and track
						if(smallTime==121 && !ewCars.isEmpty()) {
							if(ewCars.getFirst().isWaiting) ewWaitCars.setTracking(ewCars.size());
							else if(ewCars.getFirst().isGoing()>0) ewWaitCars.setTracking(ewCars.size()-1); //in case there's a car still "going"
						}else if(smallTime==121 && ewCars.isEmpty()) ewWaitCars.setTracking(0);
						
						//STAT CALL
						//if there's nobody still crossing intersection from EW light
						//set the first car in the queue to go, get wait time for stat, otherwise wait;
						if(!ewCars.isEmpty()) {
							if(ewCars.getFirst().isWaiting) {
								if(!nsCars.isEmpty()) {
									nsCars.setGo();
									nsWaitTime.setTracking(nsCars.getFirst().getWaitTime());
								}
							}
							//else if(ewCars.getFirst().isGoing()==1 && ewCars.getFirst().getPath()==1)

						//if there is a car from ew still turning north, don't go, don't set oncoming true;
						}else{
							if(!nsCars.isEmpty()) {
								nsCars.setGo();
								nsWaitTime.setTracking(nsCars.getFirst().getWaitTime());
							}
						}
						
						
					//NS Green
					}else if(smallTime<300) {
						//STAT CALL
						//if there's nobody still crossing intersection from EW light
						//set the first car in the queue to go, get wait time for stat, otherwise wait;
						//Are they turning? Check for Oncoming, then go
						if(!oncoming) {
							if(!nsCars.isEmpty()) {
								nsCars.setGo();
								nsWaitTime.setTracking(nsCars.getFirst().getWaitTime());
							}
						//Are they going straight?
						}else if(oncoming) {
							//for car going north, not turning
							if(!nsCars.isEmpty()) {
								if(nsCars.getFirst().getPath()==1) {
									nsCars.setGo();
									nsWaitTime.setTracking(nsCars.getFirst().getWaitTime());
								}
							}
							//if planning to turn and oncoming true, wait til next green light cycle
						}
					}
					//progress all time;
					ewCars.progressTime();
					nsCars.progressTime();
					smallTime++;
					bigTime++;
					//System.out.println(bigTime);
				}
			}
			//System.out.println("\nHour: "+hour+"\n");
			//END OF HOUR
			//bigTime = 0;
		}
		//END OF SIM
		System.out.println("\nHour: "+bigTime/(3600));
		System.out.println("\nFinal stats over Sim Time of "+bigTime+" seconds;");
		System.out.println("**********Statistics for 1000 Traffic Sim Hours**********");
		System.out.printf("EW Avg Cars Waiting:\t%.8f per Light Cycle\tOver %d count of data points.\n", ewWaitCars.getAverage(), ewWaitCars.getCount());
		System.out.printf("NS Avg Cars Waiting:\t%.8f per Light Cycle\tOver %d count of data points.\n", nsWaitCars.getAverage(), nsWaitCars.getCount());
		System.out.printf("EW Avg Time Waiting:\t%.8f secs\t\tOver %d count of data points.\n", ewWaitTime.getAverage(), ewWaitTime.getCount());
		System.out.printf("NS Avg Time Waiting:\t%.8f secs\t\tOver %d count of data points.\n", nsWaitTime.getAverage(), nsWaitTime.getCount());


	}
}
class Road extends LinkManager<Car>{
	
	//every car gets a second added to its wait time if its waiting
	public void progressTime() {
		for(int i = 0; i<size();i++) {
			Car tmp;
			tmp = get(i);
			tmp.progressTime();
			set(i, tmp);
		}
	}
	public void setGo() {
		Car tmp = getFirst();
		tmp.setGo();
		set(0, tmp);
	}
}

class Car {
	
	//0 or 1 for EW and NS respectively, use for genning
	private int road;
	//0 or 1 for the two paths you can take coming from a road
	private int path;
	private int going;
	//increment every sec it's waiting
	private int waitTime;
	//time spent traveling compared to the average Poisson distributed time to travel in direction
	boolean isWaiting;
	private int travelTime;
	
	//Time taken to travel Path 1:
	//EW = Turn South	70%
	//NS = Turning West	20%
	private int pathTime1;
	
	//EW = Turn North	30%
	//NS = Go North		80%
	private int pathTime2;
	
	//CONSTRUCTOR
	public Car(int Road) {
		road = Road;
		if(road==0) {
			pathTime1 = 5;
			pathTime2 = 8;
		}else if(road==1) {
			pathTime1 = 10;
			pathTime2 = 4;
		}
		going = 0;
		isWaiting = true;
		path = genPath();
	}
	
	public int getPath() {
		return path;
	}
	public int getWaitTime() {
		return waitTime;
	}
	public void progressTime() {
		if(isWaiting) waitTime++;
		else travelTime++;
	}
	private int getTravelTime() {
		return travelTime++;
	}
	//Sets isWaiting to false
	public void setGo() {
		isWaiting = false;
	}
	//is the car going or gone?
	public int isGoing() {
		going = 0;
		if(road==0) {
			if(path==0) {
				if(travelTime<=pathTime1) {
					going = 1;
				}else going = 2;
			}else if(path==1) {
				if(travelTime<=pathTime2) {
					going = 1;
				}else going = 2;
			}
		}else if(road==1) {
			if(path==0) {
				if(travelTime<=pathTime1) {
					going = 1;
				}else going = 2;

			}else if(path==1) {
				if(travelTime<=pathTime2) {
					going = 1;
				}else going = 2;

			}
		}
		if(isWaiting)going=0;
		return going;
	}
	
	//RNG PATH GENNING
	private int genPath() {
		double x = 0;
		int y = -1;
		x=(Math.random()*100);
		//EW
		if(road==0) {
			if(x < 70.0 && x >= 0) {
				y=0;
			}else if(x >= 70.00 && x <= 100.0) {
				y=1;
			}
		//NS
		}else if(road==1) {
			if(x < 20.0 && x >= 0) {
				y=0;
			}else if(x >= 20.00 && x <= 100.0) {
				y=1;
			}
		}
		if(y==-1) {
			Error e = new Error();
			System.err.println("Error genning a path for a car.");
			e.printStackTrace();
			System.exit(-1);
		}
		return y;
	}
	
}

class stats{ ///used to track and calc stats
	private double track;
	private double trackSum;
	
	private double avg;
	private double stdev;
	private double variance;
	private int count;
	public stats() {
		avg = stdev = variance = 0; count = 0;
	}
	
	public void setTracking(double x) {
		track=x;
		count++;
		trackSum+=track;
		avg=trackSum/count;
		variance = Math.pow(Math.abs(trackSum-avg), 2)/count;
		stdev = Math.sqrt(variance);
		return;
	}
	public double getTracked(){	return track;	}
	public double getAverage(){	return avg;	}
	public double getVar()	{	return variance;	}
	public double getStdev(){	return stdev;	}
	public int getCount()	{	return count;	}
}