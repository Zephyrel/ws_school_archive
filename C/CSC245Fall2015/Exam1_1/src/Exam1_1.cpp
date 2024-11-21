//============================================================================
// Name        : Exam1_1.cpp
// Author      : Russell Ferguson
//	CSC245 Fall15 Midterm Pt1
//============================================================================
#include <iostream>
using namespace std;
void print(double a, double b);
void swap(double &a, double &b);
int main(){
	double a=0;
	double b=0;
	cout<<"Enter a number please: ";
	cin>>a;
	cout<<"Enter another number, please: ";
	cin>>b;
	print(a,b);
	swap(a,b);
	print(a,b);
	return 0;
}
void print(double a, double b){
	cout<<"First: "<<a<<", Second: "<<b<<endl;
}
void swap(double &a, double &b){
	double tmp=a;
	a=b;
	b=tmp;
}
