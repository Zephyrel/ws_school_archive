/*
 * color.h
 *
 *  Created on: Dec 16, 2015
 *      Author: Russell Ferguson
 */
#include <stdio.h>
#include <stdlib.h>
#include <iostream>
#include <string>
#include <string.h>

#ifndef SRC_COLOR_H_
#define SRC_COLOR_H_

class Color{
	public:
		unsigned char red;
		unsigned char blue;
		unsigned char green;
		//Constructors
		Color(unsigned char r, unsigned char g, unsigned char b){
			//Default: White
			red=r;
			green=g;
			blue=b;
		};
		//Delegated default
		Color(){
			red=0xFF;
			green=0xFF;
			blue=0xFF;
		};
		//Destructor
		~Color(){};
		//Overload the << operator for output.
		friend std::ostream& operator<<(std::ostream& out, const Color& a);
		//Overload the == operator.  Colors are equal if all three values are equal
		friend bool operator== (const Color& a, const Color& b);
		//Overload the < operator.  Treat the color as one long integer in RGB order
		friend long int operator<(const Color& a, const Color& b);
		//Overload the + operator.  To add two colors, add their individual components, So add the two reds, the two greens, and two blues.  But make sure nothing is over 255.
		friend Color operator+(const Color& a, const Color& b);
};

//Methods
std::string printColor(Color c);


#endif /* SRC_COLOR_H_ */
