/*
 * utility.cpp
 *
 *  Created on: Dec 3, 2015
 *      Author: Russell Ferguson
 */

#include <stdio.h>
#include <string.h>
#include <string>
#include "utility.h"

/* Trims the char[] by the chars on each side 'til it hits
 * the desired char. Designed by Russell Ferguson, starring fun with pointers!
 */
char* trim(char* str, char junk){
	char* p=str;
	p=rtrim(str, junk);
	p=ltrim(str, junk);
	return p;
}
/* Trims the char[] by the chars on each side 'til it hits
 * the desired char. Designed by Russell Ferguson, starring fun with pointers!
 */
char* trim (char* str, char junkLeft, char junkRight){
	//Though rtrim/ltrim a pointer, they also manipulate what is pointed to.
	//Their returns are more for nesting in other functions, such as printf.
	char* p=str;
	p=rtrim(str, junkRight);
	p=ltrim(str, junkLeft);
	return p;
}
/* Trims the char[] by the chars on the right 'til it hits
 * the desired char. Designed by Russell Ferguson, starring fun with pointers!
 */
char* rtrim(char* str, char junk){
	bool trimmed=false;
	char* original = str + strlen(str); //pointer to the end of the char[]
	while((original!=str)&&!trimmed){
		if(*original==junk){
			*(--original+1)='\0';
			trimmed=true;
		}else{
			*(--original + 1) = '\0';
		}
	}
	return str;
}
/* Trims the char[] by the chars on the left 'til it hits
 * the desired char. Designed by Russell Ferguson, starring fun with pointers!
 */
char* ltrim(char* str, char junk){
	char* original = str;
	bool trimmed = false;
	while(*original+1!='\0'&&!trimmed){
		if(*original==junk){
			*(++original-1)='\0';
			trimmed=true;
			str=original;
		}else{
			++original;
			*(original-1)='\0';
		}
	}
	return original;
}

void swap(double &a, double &b){
	double tmp=a;
	a=b;
	b=tmp;
}
/* Given a pointer to a char in an array of chars, and a substring
 * Scans starting at the char pointed to, and returns true if
 * substring is found there.
 */
bool isSubstring(char* p, const char* substring){
	bool isSub=false;
	const char* a=substring;
	char* b=p;
	for(unsigned int i=0;!isSub&&(i<=(strlen(substring)));i++){
		if(*b==*a){
			b++;
			a++;
			if(b==p+strlen(substring)){
				isSub=true;
			}
		}else{
			isSub=false;
		}
	}
	return isSub;
}




