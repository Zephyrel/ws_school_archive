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

	Homework 2

*/

float theta = 0.0;
float scale1 = 1.0;
float dx = 7.0, dy = -3.0;
float thetaW = 0.0, dxW = -7.0, dyW = -3.0;
float thetaSq = 0.0, dxSq = -8.0, dySq = -3.0;
float thetaSqSword = 0.0, dxSqSword = -8.5, dySqSword = -3.0;
int frame = 1;
boolean polymanMouth = false , polywomanMouth = false;
boolean turnSquareman = false, turnPolywoman = false;
void init(void);
void RenderScene(void);
void loadIcon(float[], float[], float[], float[], float[], float[], float[], float[]);

void drawMan(float[], float[]);
void drawWoman(float[], float[]);
void drawSquare(float[], float[]);
void drawSquareSword(float[], float[]);

void setManTransform(void);
void setWomanTransform(void);
void setSquareTransform(void);
void setSqSwordTransform(void);

void SetupRC(void);
void timerFunction(int);

//main loop
int main(int argc, char* *argv) {
	//window header name
	char header[] = "Polyman and Polywoman Cutscene by Russell Ferguson";

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
	
	float polyMx[15]; //polyman X-coords
	float polyMy[15]; //polyman Y-coords
	float polyWx[15]; //polywoman X-coords
	float polyWy[15]; //polywoman Y-coords
	float sqMx[11]; //squareman X-coords
	float sqMy[11]; //squareman Y-coords
	float sqSwordX[8];
	float sqSwordY[8];

	cout << "in renderscene" << endl;

	glColor3f(1.0, 1.0, 1.0);
	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	glViewport(0, 0, 540, 440);	//Window size
	glOrtho(-7.0, 7.0, -7.0, 7.0, 1.0, -1.0); //Camera location/scene size
	loadIcon(polyMx, polyMy, polyWx, polyWy, sqMx, sqMy, sqSwordX, sqSwordY); //load data points for polyman/polywoman

	//refreshes each frame to make sure they don't draw over each other each frame
	//like games do when you're noclipping out of bounds in a void space
	glClear(GL_COLOR_BUFFER_BIT);
	glColor3f(1.0, 1.0, 1.0);

	//set the transformation for polyWoman, draw her
	setWomanTransform();
	drawWoman(polyWx, polyWy);
	//set the transformation for polyMan, draw him
	setManTransform();
	drawMan(polyMx, polyMy);
	//set the transformation for squareman, draw him
	setSquareTransform();
	drawSquare(sqMx, sqMy);
	setSqSwordTransform();
	drawSquareSword(sqSwordX, sqSwordY);

	//flush the buffer, no memory leaks here
	glFlush();
	glEnd();
	glutSwapBuffers();
	return;
}
//load points that make up polyman and polywoman's body
void loadIcon(float polyMx[], float polyMy[], float polyWx[], float polyWy[], float sqMx[], float sqMy[], float sqSwordX[], float sqSwordY[]) {
	//polyman body
	polyMx[0] = -0.625;	polyMy[0] = 0.625;
	polyMx[1] = 0.625;	polyMy[1] = 0.625;
	polyMx[2] = 1;		polyMy[2] = 0;
	polyMx[3] = 0.625;	polyMy[3] = -0.625;
	polyMx[4] = -0.625;	polyMy[4] = -0.625;
	polyMx[5] = -1;		polyMy[5] = 0;
	//polyman mouth
	polyMx[6] = -0.25;	polyMy[6] = 0; //Inner Corner, draw to [5] for mouth roof

	polyMx[14] = -0.775;	polyMy[14] = -0.375; //closed position, [4] for open
	//eye
	polyMx[7] = -0.25;	polyMy[7] = 0.375;
	//feet
	polyMx[8] = 0.25;	polyMy[8] = -0.625;
	polyMx[9] = 0.25;	polyMy[9] = -0.875;
	polyMx[10] = 0;		polyMy[10] = -0.875;
	polyMx[11] = -0.125;	polyMy[11] = -0.375;
	polyMx[12] = -0.125;	polyMy[12] = -0.875;
	polyMx[13] = -0.375;	polyMy[13] = -0.875;
	
	//polywoman
	//polywoman body
	polyWx[0] = 0.625;	polyWy[0] = 0.625;
	polyWx[1] = -0.625;	polyWy[1] = 0.625;
	polyWx[2] = -1;		polyWy[2] = 0;
	polyWx[3] = -0.625;	polyWy[3] = -0.625;
	polyWx[4] = 0.625;	polyWy[4] = -0.625;
	polyWx[5] = 1;		polyWy[5] = 0;
	//polywoman mouth
	polyWx[6] = 0.25;	polyWy[6] = 0; //Inner Corner, draw to [5] for mouth roof

	polyWx[14] = 0.775;	polyWy[14] = -0.375; //closed position, [4] for open
												 //eye
	polyWx[7] = 0.25;	polyWy[7] = 0.375;
	//feet
	polyWx[8] = -0.25;	polyWy[8] = -0.625;
	polyWx[9] = -0.25;	polyWy[9] = -0.875;
	polyWx[10] = 0;		polyWy[10] = -0.875;
	polyWx[11] = 0.125;	polyWy[11] = -0.375;
	polyWx[12] = 0.125;	polyWy[12] = -0.875;
	polyWx[13] = 0.375;	polyWy[13] = -0.875;

	if (turnPolywoman) {
		for (int i = 0; i < 14; i++) polyWx[i] = polyWx[i] * -1;
	}

	//squareMan body
	sqMx[0] = 0.8;		sqMy[0] = 0.0;
	sqMx[1] = 0.0;		sqMy[1] = 0.8;
	sqMx[2] = -0.8;		sqMy[2] = 0.0;
	sqMx[3] = 0.0;		sqMy[3] = -0.8;

	//eye
	sqMx[4] = 0.25;	sqMy[4] = 0.25;
	//feet
	sqMx[5] = -0.25;		sqMy[5] = -0.375;
	sqMx[6] = -0.25;		sqMy[6] = -0.875;
	sqMx[7] = 0;		sqMy[7] = -0.875;
	sqMx[8] = 0.125;	sqMy[8] = -0.375;
	sqMx[9] = 0.125;	sqMy[9] = -0.875;
	sqMx[10] = 0.375;	sqMy[10] = -0.875;

	//squareMan Sword
	sqSwordX[0] = -0.5;	sqSwordY[0] = -0.25;
	sqSwordX[1] = -0.5;	sqSwordY[1] = -0.3;
	sqSwordX[2] = 0.6;	sqSwordY[2] = -0.3;
	sqSwordX[3] = 0.6;	sqSwordY[3] = -0.25;

	sqSwordX[4] = -0.40;	sqSwordY[4] = -0.10;
	sqSwordX[5] = -0.35;	sqSwordY[5] = -0.10;
	sqSwordX[6] = -0.35;	sqSwordY[6] = -0.45;
	sqSwordX[7] = -0.40;	sqSwordY[7] = -0.45;

	if (turnSquareman) {
		for (int i = 0; i < 11; i++) {
			sqMx[i] = -1 * sqMx[i];
		}
		for (int i = 0; i < 8; i++) {
			sqSwordX[i] = -1 * sqSwordX[i];
		}
	}
	return;
}
//draw functions
void drawMan(float polyMx[], float polyMy[]) {
	int i;
	cout << "in drawMan" << endl;

	//polyman body
	glColor3f(255.0, 242.0, 0.0);
	glShadeModel(GL_FLAT);
	glBegin(GL_POLYGON);
	//draw top half
	for (i = 0; i <= 2; i++) {
		glVertex2f(polyMx[i], polyMy[i]);
	}
	glVertex2f(polyMx[5], polyMy[5]);
	glVertex2f(polyMx[0], polyMy[0]);
	glEnd();
	//draw bottom half
	glBegin(GL_POLYGON);
	//mouth
	if (!polymanMouth) { //false, draw kinda open mouth
		for (i = 2; i <= 4; i++) {
			glVertex2f(polyMx[i], polyMy[i]);
		}
		glVertex2f(polyMx[14], polyMy[14]);
		glVertex2f(polyMx[6], polyMy[6]);
		glVertex2f(polyMx[2], polyMy[2]);
		glEnd();
	}
	else {	//draw really open mouth
		for (i = 2; i <= 4; i++) {
			glVertex2f(polyMx[i], polyMy[i]);
		}
		glVertex2f(polyMx[6], polyMy[6]);
		glVertex2f(polyMx[2], polyMy[2]);
		glEnd();
	}
	for (i = 2; i <= 5; i++) {
		glVertex2f(polyMx[i], polyMy[i]);
	}
	glVertex2f(polyMx[5], polyMy[5]);
	glVertex2f(polyMx[2], polyMy[2]);
	glEnd();

	//draw feet
	glColor3f(255.0, 242.0, 0.0);
	glBegin(GL_LINE_STRIP);
	for (i = 8; i <= 10; i++) {
		glVertex2f(polyMx[i], polyMy[i]);
	}
	glEnd();
	glBegin(GL_LINE_STRIP);
	for (i = 11; i <= 13; i++) {
		glVertex2f(polyMx[i], polyMy[i]);
	}
	glEnd();
	//eye
	glColor3f(0.0, 0.0, 0.0);
	glBegin(GL_POLYGON);
	glVertex2f(polyMx[7] + 0.15, polyMy[7]);
	glVertex2f(polyMx[7], polyMy[7] + 0.15);
	glVertex2f(polyMx[7] - 0.15, polyMy[7]);
	glVertex2f(polyMx[7], polyMy[7] - 0.15);
	glVertex2f(polyMx[7] + 0.15, polyMy[7]);
	glEnd();
	return;
}

//draw Polywoman
void drawWoman(float polyWx[], float polyWy[]) {
	int i;

	//polyWoman body
	glColor3f(1.0,0.0,1.0);	//she's pink colored!
	glShadeModel(GL_FLAT);
	glBegin(GL_POLYGON);
	//draw top half body
	for (i = 0; i <= 2; i++) {
		glVertex2f(polyWx[i], polyWy[i]);
	}
	glVertex2f(polyWx[5], polyWy[5]);
	glVertex2f(polyWx[0], polyWy[0]);
	glEnd();

	glBegin(GL_POLYGON);
	if (!polywomanMouth) { //false, draw kinda open mouth
		for (i = 2; i <= 4; i++) {
			glVertex2f(polyWx[i], polyWy[i]);
		}
		glVertex2f(polyWx[14], polyWy[14]);
		glVertex2f(polyWx[6], polyWy[6]);
		glVertex2f(polyWx[2], polyWy[2]);
		glEnd();
	}
	else {	//draw really open mouth
		for (i = 2; i <= 4; i++) {
			glVertex2f(polyWx[i], polyWy[i]);
		}
		glVertex2f(polyWx[6], polyWy[6]);
		glVertex2f(polyWx[2], polyWy[2]);
		glEnd();
	}

	//feet
	glBegin(GL_LINE_STRIP);
	for (i = 8; i <= 10; i++) {
		glVertex2f(polyWx[i], polyWy[i]);
	}
	glEnd();
	glBegin(GL_LINE_STRIP);
	for (i = 11; i <= 13; i++) {
		glVertex2f(polyWx[i], polyWy[i]);
	}
	glEnd();
	
	//eye
	glColor3f(0.0, 0.0, 0.0);
	glBegin(GL_POLYGON);
	glVertex2f(polyWx[7] + 0.15, polyWy[7]);
	glVertex2f(polyWx[7], polyWy[7] + 0.15);
	glVertex2f(polyWx[7] - 0.15, polyWy[7]);
	glVertex2f(polyWx[7], polyWy[7] - 0.15);
	glVertex2f(polyWx[7] + 0.15, polyWy[7]);
	glEnd();
	
	return;
}

void drawSquare(float sqMx[], float sqMy[]) {
	int i;
	//squareman body
	glColor3f(0.0, 1.0, 0.0);	//she's pink colored!
	glShadeModel(GL_FLAT);
	glBegin(GL_POLYGON);
	//draw top half body
	for (i = 0; i <= 3; i++) {
		glVertex2f(sqMx[i], sqMy[i]);
	}
	glEnd();
	//feet
	glBegin(GL_LINE_STRIP);
	for (i = 5; i <= 7; i++) {
		glVertex2f(sqMx[i], sqMy[i]);
	}
	glEnd();
	glBegin(GL_LINE_STRIP);
	for (i = 8; i <= 10; i++) {
		glVertex2f(sqMx[i], sqMy[i]);
	}
	glEnd();
	//eye
	glColor3f(1.0, 0.0, 0.0);
	glShadeModel(GL_FLAT);
	glBegin(GL_POLYGON);
	glVertex2f(sqMx[4] + 0.15,	sqMy[4]);
	glVertex2f(sqMx[4],			sqMy[4] + 0.15);
	glVertex2f(sqMx[4] - 0.15,	sqMy[4]);
	glVertex2f(sqMx[4],			sqMy[4] - 0.15);
	glVertex2f(sqMx[4] + 0.15,	sqMy[4]);
	glEnd();
	return;
}

void drawSquareSword(float sqSwordX[], float sqSwordY[]) {
	int i;
	//eye
	glColor3f(1.0, 0.0, 0.0);
	glShadeModel(GL_FLAT);
	glBegin(GL_POLYGON);
	for (int i = 0; i < 4; i++) {
		glVertex2f(sqSwordX[i], sqSwordY[i]);
	}
	glVertex2f(sqSwordX[0], sqSwordY[0]);
	glEnd();
	glBegin(GL_POLYGON);
	for (int i = 4; i < 8; i++) {
		glVertex2f(sqSwordX[i], sqSwordY[i]);
	}
	glVertex2f(sqSwordX[4], sqSwordY[4]);
	glEnd();
	return;
}
//sword


//transformation for polyMan
void setManTransform(void) {

	cout << "in setManTransform" << endl;
	glMatrixMode(GL_MODELVIEW);
	glLoadIdentity();
	glTranslatef(dx, dy, 0.0);
	glRotatef(theta, 0.0, 0.0, 1.0);
	return;
}

void setMFeetTransform(void) {

	cout << "setMFeetTransform" << endl;
	glMatrixMode(GL_MODELVIEW);
	glLoadIdentity();
	glTranslatef(dx, dy, 0.0);
	glRotatef(theta, 0.0, 0.0, 1.0);
	return;
}
//transformation for polywoman
void setWomanTransform(void) {
	cout << "in setWomanTransform" << endl;
	glMatrixMode(GL_MODELVIEW);
	glLoadIdentity();
	glTranslatef(dxW, dyW, 0.0);
	glRotatef(thetaW, 0.0, 0.0, 1.0);
	return;
}

void setSquareTransform(void) {

	cout << "in setSquareTransform" << endl;
	glMatrixMode(GL_MODELVIEW);
	glLoadIdentity();
	glTranslatef(dxSq, dySq, 0.0);
	glRotatef(thetaSq, 0.0, 0.0, 1.0);
	return;
}

void setSqSwordTransform(void) {
		cout << "in setSwordTransform" << endl;
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		glTranslatef(dxSqSword, dySqSword, 0.0);
		glRotatef(thetaSqSword, 0.0, 0.0, 1.0);
		return;
}

void SetupRC(void) {
	glClearColor(0.0, 0.0, 1.0, 1.0);
	return;
}

void timerFunction(int value) { //animation handled here
	switch (frame) {
		case 1:	//they both towards stage center
			dx -= 0.15;
			dxW += 0.12;
			if (dx <= 3.0) { dx = 3.0; frame = 2; }
			break;
		case 2: //polyman jumps
			dy += 0.2;
			if (dy > 5.0) { dy = 5.0; frame = 3; }
			break;
		case 3: //polyman does 360 spin in air for style
			theta += 10.0;
			if (theta >= 360.0) { frame = 4; theta = 0.0; }
			break;
		case 4: //polyman falls back down
			dy -= 0.5;
			if (dy <= -3.0) { dy = -3.0; frame = 5; }
			break;
		case 5: //polywoman rocks back and forth
			dxW -= 0.10;
			thetaW -= 5.0;
			if (thetaW <= -45.0) { thetaW = -45.0; frame = 6; }
			break;
		case 6: //polywoman turns her head up from the bow, makes polywoman smile bigger
			dxW += 0.10;
			thetaW += 5.0;
			if (thetaW >= 0.0) { thetaW = 0; frame = 7; polywomanMouth = true; }
			break;
		case 7:	//Squareman runs in to polyman
			dxSq += 0.15;
			dxSqSword += 0.15;
			if (dxSq >= 1.25) { dxSq = 1.25; frame = 8; }
			break;
		case 8:	//Spin the sword before stabbing
			thetaSqSword += 20.0;
			if (thetaSqSword >= 360.0) { thetaSqSword = 0.0; frame = 9; }
			break;
		case 9: //STAB, polywoman stops smiling
			dxSqSword += 0.15;
			if (dxSqSword >= 2.0) { dxSqSword = 2.0; frame = 10; polywomanMouth = false; }
			break;
		case 10: //Pull back sword, slowly
			dxSqSword -= 0.01;
			if (dxSqSword <= 1.25) { dxSqSword = 1.25; frame = 11; }
			break;
		case 11: // Polyman rolls over dead
			theta += 5.0;
			if (theta >= 180.0) { theta = 180.0; frame = 12; turnSquareman = true; }
			break;
		case 12: //squareman turns, runs off stage to left
			dxSq -= 0.15;
			dxSqSword -= 0.15;
			if (dxSq <= -9.0) { dxSq = -9.0; frame = 13; polywomanMouth = true; turnPolywoman = true; }
			break;
		case 13: //polywoman turns, mouth open again, and chases
			dxW -= 0.15;
			if (dxW < -9.0) dxW = -9.0;
			break;
	}
	glutPostRedisplay();
	glutTimerFunc(30, timerFunction, 1);
}