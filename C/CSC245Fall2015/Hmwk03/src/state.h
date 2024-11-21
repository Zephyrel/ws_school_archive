/*
 * state.h
 *
 *  Created on: Dec 3, 2015
 *      Author: Russell Ferguson
 */


#ifndef STATE_H_
#define STATE_H_

struct STATE{
	char st[128];
	char city[128];
	unsigned int pop;
	//Constructor
	STATE(){
		strcpy(st, "Debug");
		strcpy(city, "Debug");
		pop=34404; //"ERROR"
	}
	//Destructor
	~STATE(){};
};

STATE parse(const char *line); // takes a line from the input file and returns a state struct
std::string toString(STATE st); //returns a string representing the state in a nice format.  Consider sprintf
int cmpCity(STATE a, STATE b); // returns a negative, zero, or positive integer std::string::compare
int cmpPop(STATE a, STATE b);
int cmpState(STATE a, STATE b);

#endif /* STATE_H_ */
