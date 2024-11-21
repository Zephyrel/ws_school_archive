/*
 * Color.cpp
 *
 *  Created on: Dec 16, 2015
 *      Author: Russell Ferguson
 */

#include "Color.h"

//Overload the << operator for output.
std::ostream& operator<<(std::ostream& out, const Color& a){	//Done
	std::cout<<printColor(a);
	return out;
}
//Overload the == operator.  Colors are equal if all three values are equal
bool operator== (const Color& a, const Color& b){	//Done
	if(a.red==b.red&&a.blue==b.blue&&a.green==b.green){
		return true;
	}else{
		return false;
	}
}
//Overload the < operator.  Treat the color as one long integer in RGB order
long int operator<(const Color& a, const Color& b){ //Done(???)
	long int tmp1=(int)b.red+(int)b.green+(int)b.blue;
	tmp1=tmp1-(int)a.red-(int)a.green-(int)a.blue;
	return tmp1;
}
//Overload the + operator.  To add two colors, add their individual components, So add the two reds, the two greens, and two blues.  But make sure nothing is over 255.
Color operator+(const Color a, const Color b){	//Done
	Color c;
	if((int(a.red)+int(b.red))>255){
		c.red=0xFF;
	}else{
		c.red=a.red+b.red;
	}
	if((int(a.blue)+int(b.blue))>255){
		c.blue=0xFF;
	}else{
		c.blue=a.blue+b.blue;
	}
	if((int(a.green)+int(b.green))>255){
		c.green=0xFF;
	}else{
		c.green=a.green+b.green;
	}
	return c;
}

//Methods
std::string printColor(Color c){ //Provided
    char temp [8];
    sprintf(temp,"#%02x%02x%02x",c.red, c.green, c.blue);
    return std::string(temp);
}
