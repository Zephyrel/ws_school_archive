//============================================================================
// Name        : Exam1_3.cpp
// Author      : Russell Ferguson
// Version     :
// Copyright   : Your copyright notice
// Description : Hello World in C++, Ansi-style
//============================================================================
/*Write a struct that will hold a rectangle.  The rectangle has the following fields:

    Length, a real number
    Width, a real number
    color, a bit field.  It should hold values 0 through 3.  (size it appropriately)
        If you are turning this in late, make this field an enumerated field with the values red, blue, green, and yellow.

The struct must be in a properly formatted .h/.cpp file module.   The module should have two methods

    makeRectangle:  Returns a rectangle.  It takes two doubles and an unsigned character as parameters
    toString:  Returns a string class with a string representing the rectangle.  It doesn't need to be pretty.

The main method should create a rectangle and print it.  There does not need to be input from a file.*/

#include <iostream>
#include "rectangle.h"
using namespace std;

int main(){
	double length=4.5;
	double width=3.25;
	color col=red;
	rectangle rect=makeRectangle(length, width, col);
	//char info[1024]={toString(rect)};
	//cout<<info<<endl;
	toString(rect);
	return 0;
}
