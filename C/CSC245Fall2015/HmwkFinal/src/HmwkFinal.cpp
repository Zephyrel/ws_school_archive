/*
 * HmwkFinal.cpp
 *
 *  Created on: Dec 16, 2015
 *      Author: Russell Ferguson
 */
#include <stdio.h>
#include <stdlib.h>
#include <string>
#include <string.h>
#include <algorithm>
#include <iostream>
#include <vector>
#include "Color.h"


int main(int argc, char *argv[]){
	//Create a vector of colors.  Also create the required iterator.
	std::vector<Color>col;
	std::vector<Color>::iterator it;

	//Read the color values from the keyboard until black is entered (0 0 0).  Store each color in the vector.
	bool black=false;
	bool found=false;
	unsigned int i=0;
	for(int j=0;!black;j++){
		Color colStream;
		unsigned int tmp=256;
		std::cout<<"Enter three values between 0 and 255 for Red, Green, and Blue to make a Color:"<<std::endl;
		while(!found){
			std::cin>>tmp;
			if(tmp>=0&&tmp<=255){
				colStream.red=(unsigned char)tmp;
				std::cout<<"Red is:\t\t"<<int(colStream.red)<<std::endl;
				found=true;
				tmp=256;
			}else{
				std::cout<<"That isn't between 0 and 255!"<<std::endl;
			}
		}
		found=false;
		while(!found){
			std::cin>>tmp;
			if(tmp>=0&&tmp<=255){
				colStream.green=(unsigned char)tmp;
				std::cout<<"Green is:\t"<<int(colStream.green)<<std::endl;
				found=true;
				tmp=256;
			}else{
				std::cout<<"That isn't between 0 and 255!"<<std::endl;
			}
		}
		found=false;
		while(!found){
			std::cin>>tmp;
			if(tmp>=0&&tmp<=255){
				colStream.blue=(unsigned char)tmp;
				std::cout<<"Blue is:\t"<<int(colStream.blue)<<std::endl;
				found=true;
				tmp=256;
			}else{
				std::cout<<"That isn't between 0 and 255!"<<std::endl;
			}
		}
		found=false;
		col.push_back(colStream);
		if(col[j].red==0x00&&col[j].green==0x00&&col[j].blue==0x00){
			black=true;
		}
	}
	//TODO:Sort the vector
	std::sort(col.begin(), col.end()); //I'm biting for time and can't play with algo's right now
	//TODO:Print the vector using the iterator.
	for(it=col.begin();it!=col.end();it++){
		Color tmp=col[i];
		std::cout<<"Color "<<i<<":\t"<<tmp<<std::endl;
		i++;
	}
	return 0;
}



