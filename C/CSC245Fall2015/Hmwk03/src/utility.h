/*
 * Utility.h
 *
 *  Created on: Dec 3, 2015
 *      Author: Russell Ferguson
 */

#ifndef UTILITY_H_
#define UTILITY_H_

char* trim (char* str, char junk);
char* trim (char* str, char junkLeft, char junkRight);
char* rtrim(char* str, char junk);
char* ltrim(char* str, char junk);

void swap(double &a, double &b);
bool isSubstring(char* p, const char* substring);


#endif /* UTILITY_H_ */
