/*	Russell Ferguson
 *	October 4, 2015
 *	CSC 245 - Professor E. Noyneart
 *
 *	This program must read from the file "lotto.txt"  it must be in the root folder of your project. You must open the file and catch any exceptions.

	A "pick" in the Missouri Lottery consists of 6 numbers.  The numbers are from 1 through 44, and no number may be repeated within a pick.
	The file lotto.txt contains all the winning numbers since the Lottery took its current form in 1998.  There are three columns.
	The columns are delimited by tab characters ("\t").  We don't care about the date or the amount,
	so you need to extract only the middle column. Empty rows should be discarded.

	I do not want you to use any string class operations, nor should you you any advanced c_string functions.
	I want your program to do manual processing of strings.
	This generally means iterating through strings and stopping at '\0' or in some cases inserting a \0 manually.
	You may use the following functions:

    strcpy or strncpy from <string.h>
    atoi from <stdlib.h>
    isdigit from <ctypes.h>  You may use other functions from ctype.h.

	Your program should count how often each number is picked.
	I suggest that you set up a count array with 45 positions instead of 44.
	The 0 subscript will be wasted.  After you have read all of the numbers and counted them, print out the following information:

    A table showing how often each number was picked.
    A list of the number or numbers that were picked most often.
    A list of the number or numbers that were picked least often.
    The average number of times each number was picked.  Note that this will be a real number.
    Print the numbers that occurred the Math.floor and Math.ceil of the number.
 *
 */
#include <iostream>
#include <fstream>
#include <stdlib.h>
#include <ctype.h>
#include <string.h>
#include <math.h>
using namespace std;

//Prototype a few things up here, work on them down at the bottom of the code
//hasSymbol checks for a certain char
bool hasSymbol(const char s[], char symbol);
int printCount(const int *count);
int printMostLeast(const int *count);
int printAverage(const int *count, const int totalCount);
//Heck yeah global variables
const int MAXCOUNT=45;

int main(){
	//First, we want our file, and an array of some number of ints to count how much any numbers comes up
	fstream input;
	//input.open("lotto.txt");
	input.open("lottoWithErrorsForOveracheivers.txt");
	if(input.good()){
		cout << "Input file opened: Success!" << endl;
	}else{
		cerr << "Input file opened: Failure!" << endl;
	}
	//fstream input ("lottoWithErrorsForOverachievers.txt");
	//initialize all to 0. If you don't, you get garbage everywhere.
	int count[MAXCOUNT] = {0};
	//Counts the number of lines scanned
	int totalCount = 0;
	static const int MAX = 1024;
	char line[MAX];
	//
	int i = 0;
	while(!input.eof()){
		input.getline(line, MAX - 1);
		if(hasSymbol(line, '\t')){
			//cout << "Debug hasSymbol if entered!" << endl;
			//while the char i is not null increment til we find a tabspace, and til we find data in this line
			while(!(line[i]=='\0')){
				//cout << "Debug: While loop entered!" << endl;
				if(line[i]=='\t'){
					int j = 0;
					int tempInt = 0;
					char temp[3]={0};
					i++;
					for(int dataCount=0; dataCount<6; dataCount++){
						//cout << "For Loop entered!" << endl;
						while(isdigit(line[i])&&(j<2)){
							//cout << "isdigit loop entered!" << endl;
							if((line[i]==' ')|(line[i]=='-')){
								temp[j]=' ';
							}
							temp[j]=line[i];
							j++;
							i++;
						}
						if((line[i]==' ')|(line[i]=='-')){
							i++;
						}
						j=0;
						//cout << "temp is: " << temp << endl; debug
						tempInt = atoi(temp);
						//cout << "tempInt is: " << tempInt << endl; debug
						count[tempInt]++;
						//cout << "count[tempInt] is: " << count[tempInt] << endl; debug
						totalCount++;
					}
				}
				i++;
			}
		}
		//Set the line index back to 0!
		i=0;
	}
	//close the input, no memory leaks here!
	printCount(count);
	printMostLeast(count);
	printAverage(count, totalCount);
	input.close();
	cout << "Done!" << endl;
	return 0;
}

//strcpy or strncpy from <string.h> - copies a string into a string
//atoi from <stdlib.h> - turns a char* into a integer
//isdigit from <ctypes.h>  You may use other functions from ctype.h.

bool hasSymbol(const char *s, char symbol){
	bool hasSymbol = false;
	int i = 0;
	while(s[i] != '\0' && !hasSymbol){
		hasSymbol = (s[i] == symbol);
		i++;
	}
	return hasSymbol;
}

int printCount(const int *count){
	//Print the table
	int i = 1;
	cout<<"The Count for Each Lotto Number:"<<endl;
	while(i<45){
		cout<<"Number: "<<i<<"	Count: "<<count[i]<<endl;
		i++;
	}
	return 0;
}

int printMostLeast(const int *count){
	//Get most picked and print
	//range holds the min count found and the max count found
	//foundMAX holds the single largest count of the entire bunch
	int range[2]={0};
	range[0]=count[1];
	int i=0;
	for(int j = 1;j<MAXCOUNT;j++){
		//Get max count in
		if(count[j]>range[1]){
			range[1] = count[j];
		}
		//min count in
		if(count[j]<range[0]){
			range[0] = count[j];
		}
	}

	cout<<"\nThe numbers that were picked the most were: "<<endl;
	//cout<<countMAX[0]<<"\twith a count of:"<<temp<<endl;
	//Go down from the largest count by 1, check for all index numbers that would equal it, then print.
	//Alter variable i in the for statement to determine how many lines wanted with numbers in them
	//i<5 gets us 5 lines stating index number or numbers with their count
	for(int j=range[1];(j>=range[0]&&(i<5));j--){
		bool foundNumber = false;
		for(int a=1;a<MAXCOUNT;a++){
			if(count[a]==j){
				cout<<a<<", ";
				foundNumber = true;
			}
		}
		if(foundNumber){
			cout<<"\twith a count of: "<<j<<endl;
			i++;
		}
	}
	i=0;
	cout<<"\nThe numbers that were picked the least were: "<<endl;
		//cout<<countMAX[0]<<"\twith a count of:"<<temp<<endl;
		//Go down from the largest count by 1, check for all index numbers that would equal it, then print.
		for(int j=range[0];(j<=range[1]&&(i<5));j++){
			bool foundNumber = false;
			for(int a=1;a<MAXCOUNT;a++){
				if(count[a]==j){
					cout<<a<<", ";
					foundNumber = true;
				}
			}
			if(foundNumber){
				cout<<"\twith a count of: "<<j<<endl;
				i++;
			}
		}
		return 0;

	return 0;
}

int printAverage(const int *count, const int totalCount){
	cout<<"\nTotal Count is: "<<totalCount<<endl;
	for(int i=1;i<MAXCOUNT;i++){
		double avg=((double)count[i]/(double(totalCount)/double(MAXCOUNT-1.0)));
		//There NEEDS to be a space between floor and the number it's taking. Same for ceil. They aren't functions. Apparently.
		int fl=floor (avg);
		int c=ceil (avg);
		//cout<<"Number: "<<i<<"\tAverage: "<<avg<<"\tFloor: "<<fl<<"\tCeil: "<<c<<endl;
		printf("Number: %i\tAverage: %1.6f\tFloor: %1i\tCeil: %1i\n", i, avg, fl, c);
	}
	return 0;
}
