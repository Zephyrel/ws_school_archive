/*
 * rectangle.cpp
 *
 *  Created on: Oct 24, 2015
 *      Author: Russell Ferguson
 */
#include <iostream>
#include "rectangle.h"
using namespace std;

rectangle makeRectangle(double length, double width, color col){
	rectangle rect;
	rect.length=length;
	rect.width=width;
	rect.cl=col;
	return rect;
}

void toString(rectangle recta){
	//I'm pretty sure now after a good while beating my head against this
	//Not only is returning an array of chars a pain in the butt
	//And I don't know, maybe I would have to do a malloc to do so? I'd have to free it later. The malloc would have to be done
	//I'd have to pass the rectangle object by value, and all it's little variables I think are also passed by value
	//The toString method returns an array of chars, which itself would be a pointer to a matrix of variables, to
	//something local to this function?
	//And when I want to say, string[]=toString(rect)
	//string[] will attempt to load values to store and point to
	//Only to get pointers because I'm returning an array of chars, a set of pointers
	//AAAUUUUUGH I GIVE UP I DON'T HAVE TIME FOR THIS
	//WE DIDN'T MESS AROUND ABUNCH WITH MALLOC
	cout<<"The length is:\t"<<recta.length<<endl;
	cout<<"The width is:\t"<<recta.width<<endl;
	cout<<"The area is:\t"<<(recta.length*recta.width)<<endl;
	switch(recta.cl){
		case red:
			cout<<"The color is:\tRed"<<endl;
			break;
		case blue:
			cout<<"The color is:\tBlue"<<endl;
			break;
		case green:
			cout<<"The color is:\tGreen"<<endl;
			break;
		case yellow:
			cout<<"The color is:\tYellow"<<endl;
			break;
		default:
			cout<<"The color is:\tUnknown"<<endl;
			break;
	}

}

