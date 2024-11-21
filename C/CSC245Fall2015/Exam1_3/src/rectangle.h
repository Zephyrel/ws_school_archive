/*
 * rectangle.h
 *
 *  Created on: Oct 24, 2015
 *      Author: Russell Ferguson
 */

#ifndef RECTANGLE_H_
#define RECTANGLE_H_

#include <string>

enum color{red,blue,green,yellow};

struct rectangle{
	double length;
	double width;
	color cl;
};
rectangle makeRectangle(double length, double width, color col);
void toString(rectangle rect);

#endif /* RECTANGLE_H_ */
