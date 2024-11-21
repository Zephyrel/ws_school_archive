/*
 * state.cpp
 *
 *  Created on: Dec 3, 2015
 *      Author: Russell Ferguson
 */

#include <stdio.h>
#include <cstdlib>
#include <stdlib.h>
#include <string.h>
#include <string>
#include <iostream>
#include <fstream>
#include "utility.h"
#include "state.h"

STATE parse(const char *line){ // takes a line from the input file and returns a state struct
	//Example line:
	//Arizona | Phoenix, Arizona | 6.731 million people
	//Vermont | Montpelier, Vermont | 626562 people
	STATE st;
	char tmp1[256];
	char* p=tmp1;
	float pop=0;
	strcpy(tmp1, line);
	const char mil[]={"million"};
	bool isMillion=false;
	//parse state
	p=rtrim(p, '|');
	p=rtrim(p, '|');
	p=rtrim(p, ' ');
	strcpy(st.st, p);
	//parse city
	strcpy(tmp1, line);
	p=trim(tmp1, '|');
	p=trim(p, ' ', ',');
	strcpy(st.city, p);
	//parse population
	strcpy(tmp1, line);
	p=ltrim(tmp1, '|');
	p=ltrim(p, '|');
	p=trim(p, ' ');
	//6.371 million
	//626562
	if(isSubstring(p+strlen(p)-strlen(mil), mil)){
		p=rtrim(p, ' ');
		pop=atof(p);
		isMillion=true;
	}else{
		pop=atof(p);
	}

	if(isMillion){
		pop=pop*1000000;
	}
	st.pop=int(pop);
	return st;
}
std::string toString(STATE a){	//returns a string representing the state in a nice format.  Consider sprintf
	char s[256];
	sprintf(s, "%20s,\t%20s\t|%i\n", a.city, a.st, a.pop);
	//printf(s, "%s %s \t %li\n", a.city, a.st, a.pop);
	return std::string(s);
}

//Compares. Negative means ordered before, zero means no change, positive means ordered after.
int cmpCity(STATE a, STATE b){ // returns a negative, zero, or positive integer std::string::compare
	int i;
	i=std::string(a.city).compare(std::string(b.city));
	return i;
}
int cmpPop(STATE a, STATE b){
	int i;
	i=a.pop-b.pop;
	return i;
}
int cmpState(STATE a, STATE b){
	int i;
	i=std::string(a.st).compare(std::string(b.st));
	return i;
}
