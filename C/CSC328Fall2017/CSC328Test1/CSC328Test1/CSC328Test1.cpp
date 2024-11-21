#include<Windows.h>
#include<gl/glut.h>
#include<stdlib.h>
#include<math.h>
#include<conio.h>
#include<stdio.h>
#include<iostream>
#include<iomanip>
using namespace std;


/*
Author:		Russell Ferguson Jr
Class:		CSC328
Instructor:	Kent Pickett
Homework 3
*/

//globals
float x[];
float y[];
int nDraws = 3;
int pointsPerDraw[] = { 4, 2, 3 };
int drawType[] = { GL_LINES, GL_LINE_STRIP, GL_POLYGON };
float rColor[] = { 1.0, 0.0, 0.0 }, gColor[] = { 0.0, 1.0, 0.0 }, bColor[] = { 0.0, 0.0, 1.0 };
float Rotate;
float xScale, yScale;
float xTrans, yTrans;

int frame;
//draw functions
void drawsAllIcons(float[], float[], int, int[], int[], float[], float[], float[], float, float, float, float, float);

void init(void);
void RenderScene(void);
//load everything
void loadIcon(float[], float[]);

void SetupRC(void);
void timerFunction(int);

//main loop
int main(int argc, char* *argv) {
	//window header name
	char header[] = "CSC328 Test 1 Take Home";

	glutInit(&argc, argv);
	glutInitDisplayMode(GLUT_DOUBLE | GLUT_RGB);
	//size of window and position on monitor
	glutInitWindowSize(560, 440);
	glutInitWindowPosition(140, 20);
	SetupRC();
	//create and loop for window
	glutCreateWindow(header);
	glutDisplayFunc(RenderScene);
	glutTimerFunc(30, timerFunction, 1);

	glutMainLoop();

	return 0;
}
//main render method
void RenderScene(void) {
	float xdel = 0.25;

	float x[22]; //square X-coords
	float y[22]; //square Y-coords
	
	cout << "in renderscene" << endl;

	glColor3f(1.0, 1.0, 1.0);
	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	glViewport(0, 0, 540, 440);	//Window size
	glOrtho(-15.0, 15.0, -15.0, 15.0, 1.0, -1.0); //Camera location/scene size
	glClear(GL_COLOR_BUFFER_BIT);
	loadIcon(x, y); //load data points for polyman/polywoman
	
	//refreshes each frame to make sure they don't draw over each other each frame
	//like games do when you're noclipping out of bounds in a void space
	drawsAllIcons(x, y, nDraws, pointsPerDraw, drawType, rColor, gColor, bColor, Rotate, xScale, yScale, xTrans, yTrans);
	//flush the buffer, no memory leaks here
	glFlush();
	glEnd();
	glutSwapBuffers();
	return;
}
//load points that make up polyman and polywoman's body
void loadIcon(float x[], float y[]) {
	//square, centered at 0,0, Red
	//redraw at new center 5.0, 5.0
	//scale to 2
	x[0] = 1.0;		y[0] = 1.0;
	x[1] = 1.0;		y[1] = -1.0;
	x[2] = -1.0;	y[2] = -1.0;
	x[3] = -1.0;	y[3] = 1.0;
	//line points for square
	x[4] = 0.0;		y[4] = 2.0;
	x[5] = 0.0;	y[5] = -2.0;
	
	//Trapezoid, centered at 0, 0, Green
	//Redraw at -5.0, -5.0
	//scale to 2
	x[6] = 1.0;		y[6] = 1.0;
	x[7] = 1.5;		y[7] = -1.0;
	x[8] = -1.5;	y[8] = -1.0;
	x[9] = -1.0;	y[9] = 1.0;
	//Trapezoid Line Points
	x[10] = 0.0;	y[10] = 2.0;
	x[11] = 0.0;	y[11] = -2.0;

	//Square on Trapezoid, centered at 0, 0
	//Square: Red. Trapezoid: Green
	x[12] = 1.0;	y[12] = 1.0;
	x[13] = 1.0;	y[13] = 0.0;
	x[14] = -1.0;	y[14] = 0.0;
	x[15] = -1.0;	y[15] = 1.0;
	//Trapezoid: Green
	x[16] = 1.5;	y[16] = 0.0;
	x[17] = 2.0;	y[17] = -1.0;
	x[18] = -2.0;	y[18] = -1.0;
	x[19] = -1.5;	y[19] = 0.0;
	//Line Points
	x[20] = 0.0;	y[20] = 3.0;
	x[21] = 0.0;	y[21] = -3.0;
	

}
//draw functions
void drawsAllIcons(float x[], float y[], int nDraws, int pointsPerDraw[], int drawType[], float rColor[], float gColor[], float bColor[], float rotate, float xScale, float yScale, float xTrans, float yTrans) {
	int i = 0;
	cout << "in drawsAllIcons" << endl;
	//lines
	glMatrixMode(GL_MODELVIEW);
	glLoadIdentity();
	
	glColor3f(rColor[0], rColor[1], rColor[2]);
	glBegin(drawType[i]);
	for (int j = 4; j < 6; j++) {
		glVertex2f((2)*((x[j] * cos(rotate)) - (y[j] * sin(rotate))), (2)*((x[j] * sin(rotate)) + (y[j] * cos(rotate))));
	}
	glEnd();

	//translate copy
	glColor3f(rColor[0], rColor[1], rColor[2]);
	glBegin(drawType[i]);
	for (int j = 4; j < 6; j++) {
		glVertex2f((2)*((x[j] * cos(rotate)) - (y[j] * sin(rotate))) + 5.0, (2)*((x[j] * sin(rotate)) + (y[j] * cos(rotate))) + 5.0);
	}
	glEnd();
	//trapezoid
	glColor3f(gColor[0], gColor[1], gColor[2]);
	glBegin(drawType[i]);
	for (int j = 10; j < 12; j++) {
		glVertex2f((2)*((x[j] * cos(rotate)) - (y[j] * sin(rotate))), (2)*((x[j] * sin(rotate)) + (y[j] * cos(rotate))));
	}
	glEnd();
	//translate
	glColor3f(gColor[0], gColor[1], gColor[2]);
	glBegin(drawType[i]);
	for (int j = 10; j < 12; j++) {
		glVertex2f((2)*((x[j] * cos(rotate)) - (y[j] * sin(rotate))) - 5.0, (2)*((x[j] * sin(rotate)) + (y[j] * cos(rotate))) - 5.0);
	}
	glEnd();
	//square on trapezoid
	glColor3f(gColor[0], gColor[1], gColor[2]);
	glBegin(drawType[i]);
	for (int j = 20; j < 22; j++) {
		glVertex2f((2)*((x[j] * cos(rotate)) - (y[j] * sin(rotate))), (2)*((x[j] * sin(rotate)) + (y[j] * cos(rotate))));
	}
	glEnd();

	
	/////////////////////////////////////////////////////
	i++; //now GL_LINE_STRIPS
	/////////////////////////////////////////////////////
	
	//square icon
	glColor3f(rColor[0], rColor[1], rColor[2]);
	glBegin(drawType[i]);
	for (int j = 0; j < 4; j++) {
		glVertex2f((2)*((x[j] * cos(rotate)) - (y[j] * sin(rotate))), (2)*((x[j] * sin(rotate)) + (y[j] * cos(rotate))));
	}
	glVertex2f((2)*((x[0] * cos(rotate)) - (y[0] * sin(rotate))), (2)*((x[0] * sin(rotate)) + (y[0] * cos(rotate))));
	glEnd();

	//translate copy
	glColor3f(rColor[0], rColor[1], rColor[2]);
	glBegin(drawType[i]);
	for (int j = 0; j < 4; j++) {
	glVertex2f((2)*((x[j] * cos(rotate)) - (y[j] * sin(rotate)))+5.0, (2)*((x[j] * sin(rotate)) + (y[j] * cos(rotate))) + 5.0);
	}
	glVertex2f((2)*((x[0] * cos(rotate)) - (y[0] * sin(rotate)))+5.0, (2)*((x[0] * sin(rotate)) + (y[0] * cos(rotate))) + 5.0);
	glEnd();

	//trapezoid
	glColor3f(gColor[0], gColor[1], gColor[2]);
	glBegin(drawType[i]);
	for (int j = 6; j < 10; j++) {
	glVertex2f((2)*((x[j] * cos(rotate)) - (y[j] * sin(rotate))), (2)*((x[j] * sin(rotate)) + (y[j] * cos(rotate))));
	}
	glVertex2f((2)*((x[6] * cos(rotate)) - (y[6] * sin(rotate))), (2)*((x[6] * sin(rotate)) + (y[6] * cos(rotate))));
	glEnd();
	//translate
	glColor3f(gColor[0], gColor[1], gColor[2]);
	glBegin(drawType[i]);
	for (int j = 6; j < 10; j++) {
	glVertex2f((2)*((x[j] * cos(rotate)) - (y[j] * sin(rotate))) - 5.0, (2)*((x[j] * sin(rotate)) + (y[j] * cos(rotate))) - 5.0);
	}
	glVertex2f((2)*((x[6] * cos(rotate)) - (y[6] * sin(rotate))) - 5.0, (2)*((x[6] * sin(rotate)) + (y[6] * cos(rotate))) - 5.0);
	glEnd();

	//square on trapezoid
	glColor3f(rColor[0], rColor[1], rColor[2]);
	glBegin(drawType[i]);
	for (int j = 12; j < 16; j++) {
	glVertex2f((2)*((x[j] * cos(rotate)) - (y[j] * sin(rotate))), (2)*((x[j] * sin(rotate)) + (y[j] * cos(rotate))));
	}
	glVertex2f((2)*((x[12] * cos(rotate)) - (y[12] * sin(rotate))), (2)*((x[12] * sin(rotate)) + (y[12] * cos(rotate))));
	glEnd();
	//trapezoid part
	glColor3f(gColor[0], gColor[1], gColor[2]);
	glBegin(drawType[i]);
	for (int j = 16; j < 20; j++) {
	glVertex2f((2)*((x[j] * cos(rotate)) - (y[j] * sin(rotate))), (2)*((x[j] * sin(rotate)) + (y[j] * cos(rotate))));
	}
	glVertex2f((2)*((x[16] * cos(rotate)) - (y[16] * sin(rotate))), (2)*((x[16] * sin(rotate)) + (y[16] * cos(rotate))));
	glEnd();
	
	/////////////////////////////////////////////////////
	i++; //now GL_POLYGON
	/////////////////////////////////////////////////////
	glColor3f(rColor[0], rColor[1], rColor[2]);
	glBegin(drawType[i]);
	glVertex2f((2)*((x[1] * cos(rotate)) - (y[1] * sin(rotate))), (2)*((x[1] * sin(rotate)) + (y[1] * cos(rotate))));
	glVertex2f((2)*((x[2] * cos(rotate)) - (y[2] * sin(rotate))), (2)*((x[2] * sin(rotate)) + (y[2] * cos(rotate))));
	glVertex2f((2)*((x[4] * cos(rotate)) - (y[4] * sin(rotate))), (2)*((x[4] * sin(rotate)) + (y[4] * cos(rotate))));
	glEnd();
	
	//translate copy
	glColor3f(rColor[0], rColor[1], rColor[2]);
	glBegin(drawType[i]);
	glVertex2f((2)*((x[1] * cos(rotate)) - (y[1] * sin(rotate))) + 5.0, (2)*((x[1] * sin(rotate)) + (y[1] * cos(rotate))) + 5.0);
	glVertex2f((2)*((x[2] * cos(rotate)) - (y[2] * sin(rotate))) + 5.0, (2)*((x[2] * sin(rotate)) + (y[2] * cos(rotate))) + 5.0);
	glVertex2f((2)*((x[4] * cos(rotate)) - (y[4] * sin(rotate))) + 5.0, (2)*((x[4] * sin(rotate)) + (y[4] * cos(rotate))) + 5.0);
	glEnd();
	//trapezoid
	glColor3f(gColor[0], gColor[1], gColor[2]);
	glBegin(drawType[i]);
	glVertex2f((2)*((x[7] * cos(rotate)) - (y[7] * sin(rotate))), (2)*((x[7] * sin(rotate)) + (y[7] * cos(rotate))));
	glVertex2f((2)*((x[8] * cos(rotate)) - (y[8] * sin(rotate))), (2)*((x[8] * sin(rotate)) + (y[8] * cos(rotate))));
	glVertex2f((2)*((x[10] * cos(rotate)) - (y[10] * sin(rotate))), (2)*((x[10] * sin(rotate)) + (y[10] * cos(rotate))));
	glEnd();
	//translate
	glColor3f(gColor[0], gColor[1], gColor[2]);
	glBegin(drawType[i]);
	glVertex2f((2)*((x[7] * cos(rotate)) - (y[7] * sin(rotate))) - 5.0, (2)*((x[7] * sin(rotate)) + (y[7] * cos(rotate))) - 5.0);
	glVertex2f((2)*((x[8] * cos(rotate)) - (y[8] * sin(rotate))) - 5.0, (2)*((x[8] * sin(rotate)) + (y[8] * cos(rotate))) - 5.0);
	glVertex2f((2)*((x[10] * cos(rotate)) - (y[10] * sin(rotate))) - 5.0, (2)*((x[10] * sin(rotate)) + (y[10] * cos(rotate))) - 5.0);
	glEnd();
	/*
	//square on trapezoid
	glColor3f(rColor[0], rColor[1], rColor[2]);
	glBegin(drawType[i]);
	glVertex2f((2)*((x[12] * cos(rotate)) - (y[12] * sin(rotate))) , (2)*((x[12] * sin(rotate)) + (y[12] * cos(rotate))) );
	glVertex2f((2)*((x[15] * cos(rotate)) - (y[15] * sin(rotate))) , (2)*((x[15] * sin(rotate)) + (y[15] * cos(rotate))) );
	glVertex2f((2)*((x[20] * cos(rotate)) - (y[20] * sin(rotate))) , (2)*((x[20] * sin(rotate)) + (y[20] * cos(rotate))) );
	glEnd();
	*/
	glFlush();
	glEnd();
	Rotate+=0.01;
	return;
}

void SetupRC(void) {
	glClearColor(0.0, 0.0, 1.0, 1.0);
	return;
}

void timerFunction(int value) { //animation handled here
	switch (frame) {
	case 1:	//they both towards stage center
		
		break;
	}
	glutPostRedisplay();
	glutTimerFunc(30, timerFunction, 1);
}