//============================================================================
// Name        : Exam1_2.cpp
// Author      : Russell Ferguson
// Version     :
// Copyright   : Your copyright notice
// Description : Hello World in C++, Ansi-style
//============================================================================
#include <iostream>
#include <fstream>
#include <stdlib.h>
using namespace std;

int main() {
	static const int YEAR=2015;
	static const int MAXBUFFER=256;
	char line[MAXBUFFER];
	int readYear=0;
	int yearDifference=0;
	bool isNegative=false;
	//Could be 5 for year sizes of 4 digits plus one null
	//but I don't want to risk it for big year numbers
	char lineYear[128]={0};
	fstream input;
	input.open("file.txt");
	int i=0;
	int j=0;
	// i is line index
	input.getline(line, MAXBUFFER-1);
	//cout<<line<<endl;
	if(line[i]=='-'){
		isNegative=true;
		i++;
	}
	while(isdigit(line[i])){
		lineYear[j]=line[i];
		i++;
		j++;
	}
	readYear=readYear+atoi(lineYear);
	if(isNegative==true){
		yearDifference=YEAR+readYear;
	}else{
		yearDifference=YEAR-readYear;
	}
	cout<<"The difference between the current year and the read year is: "<<yearDifference<<endl;

	return 0;
}
