//============================================================================
// Name		: Hmwk03.cpp
// Author	: Russell Ferguson
// DATE		: December 3, 2015
// Class	: CSC245 High Level Programming Language II
// Professor: Professor E. Noyneart
//============================================================================

#include <stdio.h>
#include <stdlib.h>
#include <iostream>
#include <fstream>
#include <string.h>
#include <string>
#include "state.h"
#include "utility.h"
//using namespace std; nope

int main(int argc, char *argv[]) {
	bool argIN=(argc>1);
	int i=0;
	int j=0;
	int tmp=0;
	static const int MAXBUFFER=256;
	char line[MAXBUFFER]={0};
	int lineCount;
	if(argIN){
		lineCount=atoi(argv[1]);
	}else{
		lineCount=50;
	}
	STATE states[lineCount];
	//STATE* states[lineCount];	//In C++, variable length arrays are not legal, while C allows it
	//G++ allows it as an extension because it's legal in C, without being =pedantic about the C++ standard

	//set up input
	std::fstream input;
	input.open("input.txt");
	if(input.good()){
		std::cout<<"Input file opened: Success!\n"<<std::endl;
	}else{
		std::cerr<<"Input file opened: Failure!\n"<<std::endl;
	}
	//feed line and parse. Let's feed the example line through first.
	input.getline(line, MAXBUFFER-1);
	for(i=0;i<lineCount;i++){	//Parsing Done... except for one detail. I can't initialize and set an element for any one object in a dynamically sized array of objects!
		input.getline(line, MAXBUFFER-1);
		STATE tmpSTATE=parse(line);
		states[i].pop=tmpSTATE.pop;
		strcpy(states[i].city, tmpSTATE.city);
		strcpy(states[i].st, tmpSTATE.st);
	}
	i=0;
	//sort and print order by city, A-Z
	std::cout<<"Order by City:\n"<<std::endl;
	for(i=0;i<lineCount-1;i++){	//Bubble Sorting, or how to be inefficient and surprisingly tear my hair out over implementing such a relatively simple sorting method
		for(j=0;j<lineCount-1-i;j++){
			tmp=cmpCity(states[j], states[j+1]);	//If you swap the values of these to j+1 and j instead, you get Z-A!
			if(tmp>0){	//Swapping the operation to < also reverses the order to Z-A
				STATE swap=states[j];
				states[j]=states[j+1];
				states[j+1]=swap;
			}
		}
	}
	for(i=0;i<lineCount;i++){
		//char line[MAXBUFFER]={*toString(states[i])};
		//std::cout<<line<<std::endl;
		std::cout<<toString(states[i]);
	}
	tmp=0;
	//sort and print order by population, Greatest to Least
	std::cout<<"Order by Population:\n"<<std::endl;
	for(i=0;i<lineCount-1;i++){
		for(j=0;j<lineCount-1-i;j++){
			tmp=cmpPop(states[j], states[j+1]);		//As above, if you swap the values of these to j+1 and j instead, you get Least to Greatest!
			if(tmp<0){ //i>j, therefore
				STATE swap=states[j];
				states[j]=states[j+1];
				states[j+1]=swap;
			}
		}
	}
	for(i=0;i<lineCount;i++){
		std::cout<<toString(states[i]);
	}
	tmp=0;
	return 0;
}
