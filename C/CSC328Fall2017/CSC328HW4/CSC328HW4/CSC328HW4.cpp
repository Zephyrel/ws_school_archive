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

float scale1 = 1.0;
float theta = 0.0, dx = 7.0, dy = -3.0;
float thetaW = 0.0, dxW = -7.0, dyW = -3.0;

int frame = 1;
boolean polymanMouth = false, polywomanMouth = false; //false = small mouth, true = big open mouth
boolean turnPolyman = false, turnPolywoman = false; //are they horizontally flipped from their original orientation?
boolean walkPolyman = true, walkPolywoman = true, walkSquareman = true; //determines whether walk animation on feet are on or off

double dxWalkM = 0, dyWalkM = 0; //position of feets relevant to origin/acnhor point of the respective characters.
double dxWalkW = 0, dyWalkW = 0;
double dxWalkS = 0, dyWalkS = 0;

void init(void);
void RenderScene(void);
//load everything
void loadIcon(float[], float[], float[], float[]);
//draw functions
void drawMan(float[], float[]);
void drawManFeet(float[], float[]);
void drawWoman(float[], float[]);
void drawWomanFeet(float[], float[]);

//transformations for characters
void setManTransform(void);
void setWomanTransform(void);

//transformations for characters' feet
void setManFeetTransform(void);
void setWomanFeetTransform(void);

void SetupRC(void);
void timerFunction(int);

//main loop
int main(int argc, char* *argv) {
	//window header name
	char header[] = "Homework 4: Squareman Cutscene by Russell Ferguson";

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


	cout << "in renderscene" << endl;

	glColor3f(1.0, 1.0, 1.0);
	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	glViewport(0, 0, 540, 440);	//Window size
	glOrtho(-7.0, 7.0, -7.0, 7.0, 2.0, -2.0); //Camera location/scene size
	
	loadIcon(polyMx, polyMy, polyWx, polyWy); //load data points for polyman/polywoman

	//refreshes each frame to make sure they don't draw over each other each frame
	//like games do when you're noclipping out of bounds in a void space
	glClear(GL_COLOR_BUFFER_BIT);
	glColor3f(1.0, 1.0, 1.0);
	
	//set the transformation for polyMan, draw him
	setManTransform();
	drawMan(polyMx, polyMy);
	setManFeetTransform();
	drawManFeet(polyMx, polyMy);

	//set the transformation for polyWoman, draw her
	setWomanTransform();
	drawWoman(polyWx, polyWy);
	setWomanFeetTransform();
	drawWomanFeet(polyWx, polyWy);

	//flush the buffer, no memory leaks here
	glFlush();
	glEnd();
	glutSwapBuffers();
	return;
}
//load points that make up polyman and polywoman's body
void loadIcon(float polyMx[], float polyMy[], float polyWx[], float polyWy[]) {
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
	/*
	if (turnPolywoman) {
		for (int i = 0; i < 14; i++) polyWx[i] = polyWx[i] * -1;
	}

	if (turnPolyman) {
		for (int i = 0; i < 11; i++) {
			sqMx[i] = -1 * sqMx[i];
		}
		for (int i = 0; i < 8; i++) {
			sqSwordX[i] = -1 * sqSwordX[i];
		}
	}*/
	return;
}
//draw functions
void drawMan(float polyMx[], float polyMy[]) {
	int i;
	cout << "in drawMan" << endl;

	//polyman body
	glColor3f(255.0, 242.0, 0.0);
	glShadeModel(GL_FLAT);
	glBegin(GL_LINE_STRIP);
	//draw top half
	glVertex3f(polyMx[6], polyMy[6], -0.5);
	glVertex3f(polyMx[5], polyMy[5], -0.5);
	for (i = 0; i <= 2; i++) {
		glVertex3f(polyMx[i], polyMy[i], -0.5);
	}
	glEnd();
	glBegin(GL_LINE_STRIP);
	glVertex3f(polyMx[6], polyMy[6], 0.0);
	glVertex3f(polyMx[5], polyMy[5], 0.0);
	for (i = 0; i <= 2; i++) {
		glVertex3f(polyMx[i], polyMy[i], 0.0);
	}
	glEnd();
	for (i = 0; i < 7; i++) {
		glBegin(GL_LINE_STRIP);
		glVertex3f(polyMx[i], polyMy[i], -0.5);
		glVertex3f(polyMx[i], polyMy[i], 0.0);
		glEnd();
	}
	//draw bottom half
	glBegin(GL_LINE_STRIP);
	//mouth
	if (!polymanMouth) { //false, draw kinda open mouth
		for (i = 2; i <= 4; i++) {
			glVertex3f(polyMx[i], polyMy[i], -0.5);
		}
		
		glVertex3f(polyMx[14], polyMy[14], -0.5);
		glVertex3f(polyMx[6], polyMy[6], -0.5);
		glEnd();
		glBegin(GL_LINE_STRIP);
		for (i = 2; i <= 4; i++) {
			glVertex3f(polyMx[i], polyMy[i], 0.0);
		}
		glVertex3f(polyMx[14], polyMy[14], 0.0);
		glVertex3f(polyMx[6], polyMy[6], 0.0);
		glEnd();
	}
	else {	//draw really open mouth
		for (i = 2; i <= 4; i++) {
			glVertex3f(polyMx[i], polyMy[i], -0.5);
		}
		glVertex3f(polyMx[6], polyMy[6], -0.5);
		glEnd();
		glBegin(GL_LINE_STRIP);
		for (i = 2; i <= 4; i++) {
			glVertex3f(polyMx[i], polyMy[i], 0);
		}
		glVertex3f(polyMx[6], polyMy[6], 0);
		glEnd();
	}

	//draw feet in own function
	//eye
	glBegin(GL_LINE_STRIP);
	glVertex3f(polyMx[7] + 0.15, polyMy[7], 0.0);
	glVertex3f(polyMx[7], polyMy[7] + 0.15, 0.0);
	glVertex3f(polyMx[7] - 0.15, polyMy[7], 0.0);
	glVertex3f(polyMx[7], polyMy[7] - 0.15, 0.0);
	glVertex3f(polyMx[7] + 0.15, polyMy[7], 0.0);
	glEnd();
	return;
}
void drawManFeet(float polyMx[], float polyMy[]) {
	glColor3f(255.0, 242.0, 0.0);
	glBegin(GL_LINE_STRIP);
	for (int i = 8; i <= 10; i++) {
		glVertex3f(polyMx[i], polyMy[i], -0.25);
	}
	glEnd();
	glBegin(GL_LINE_STRIP);
	for (int i = 11; i <= 13; i++) {
		glVertex3f(polyMx[i], polyMy[i], -0.25);
	}
	glEnd();
}

//draw Polywoman
void drawWoman(float polyWx[], float polyWy[]) {
	int i;
	cout << "in drawMan" << endl;

	//polywoman body
	glColor3f(1.0, 0.0, 1.0);	//She's Pink!
	glShadeModel(GL_FLAT);
	glBegin(GL_LINE_STRIP);
	//draw top half
	glVertex3f(polyWx[6], polyWy[6], -0.5);
	glVertex3f(polyWx[5], polyWy[5], -0.5);
	for (i = 0; i <= 2; i++) {
		glVertex3f(polyWx[i], polyWy[i], -0.5);
	}
	glEnd();
	glBegin(GL_LINE_STRIP);
	glVertex3f(polyWx[6], polyWy[6], 0.0);
	glVertex3f(polyWx[5], polyWy[5], 0.0);
	for (i = 0; i <= 2; i++) {
		glVertex3f(polyWx[i], polyWy[i], 0.0);
	}
	glEnd();
	for (i = 0; i < 7; i++) {
		glBegin(GL_LINE_STRIP);
		glVertex3f(polyWx[i], polyWy[i], -0.5);
		glVertex3f(polyWx[i], polyWy[i], 0.0);
		glEnd();
	}
	//draw bottom half
	glBegin(GL_LINE_STRIP);
	//mouth
	if (!polymanMouth) { //false, draw kinda open mouth
		for (i = 2; i <= 4; i++) {
			glVertex3f(polyWx[i], polyWy[i], -0.5);
		}

		glVertex3f(polyWx[14], polyWy[14], -0.5);
		glVertex3f(polyWx[6], polyWy[6], -0.5);
		glEnd();
		glBegin(GL_LINE_STRIP);
		for (i = 2; i <= 4; i++) {
			glVertex3f(polyWx[i], polyWy[i], 0.0);
		}
		glVertex3f(polyWx[14], polyWy[14], 0.0);
		glVertex3f(polyWx[6], polyWy[6], 0.0);
		glEnd();
	}
	else {	//draw really open mouth
		for (i = 2; i <= 4; i++) {
			glVertex3f(polyWx[i], polyWy[i], -0.5);
		}
		glVertex3f(polyWx[6], polyWy[6], -0.5);
		glEnd();
		glBegin(GL_LINE_STRIP);
		for (i = 2; i <= 4; i++) {
			glVertex3f(polyWx[i], polyWy[i], 0);
		}
		glVertex3f(polyWx[6], polyWy[6], 0);
		glEnd();
	}

	//draw feet in own function
	//eye
	glBegin(GL_LINE_STRIP);
	glVertex3f(polyWx[7] + 0.15, polyWy[7], 0.0);
	glVertex3f(polyWx[7], polyWy[7] + 0.15, 0.0);
	glVertex3f(polyWx[7] - 0.15, polyWy[7], 0.0);
	glVertex3f(polyWx[7], polyWy[7] - 0.15, 0.0);
	glVertex3f(polyWx[7] + 0.15, polyWy[7], 0.0);
	glEnd();
	return;
}

void drawWomanFeet(float polyWx[], float polyWy[]) {
	glColor3f(1.0, 0.0, 1.0);
	glBegin(GL_LINE_STRIP);
	for (int i = 8; i <= 10; i++) {
		glVertex3f(polyWx[i], polyWy[i], 0.5);
	}
	glEnd();
	glBegin(GL_LINE_STRIP);
	for (int i = 11; i <= 13; i++) {
		glVertex3f(polyWx[i], polyWy[i], 0.0);
	}
	glEnd();

}


//transformation for Polyman
void setManTransform(void) {

	cout << "in setManTransform" << endl;
	glMatrixMode(GL_MODELVIEW);
	glLoadIdentity();
	glTranslatef(dx, dy, -0.5);
	glRotatef(theta, 0.0, 1.0, 1.0);
	return;
}

void setManFeetTransform(void) {

	cout << "setMFeetTransform" << endl;
	glMatrixMode(GL_MODELVIEW);
	glLoadIdentity();
	glTranslatef(dx + dxWalkM, dy + dyWalkM, -0.5);
	glRotatef(theta, 0.0, 1.0, 1.0);
	return;
}
//transformation for polywoman
void setWomanTransform(void) {
	cout << "in setWomanTransform" << endl;
	glMatrixMode(GL_MODELVIEW);
	glLoadIdentity();
	glTranslatef(dxW, dyW, 0.5);
	glRotatef(thetaW, 0.0, 1.0, 1.0);
	return;
}

void setWomanFeetTransform(void) {

	cout << "setWomanFeetTransform" << endl;
	glMatrixMode(GL_MODELVIEW);
	glLoadIdentity();
	glTranslatef(dxW + dxWalkW, dyW + dyWalkW, 0.5);
	glRotatef(thetaW, 0.0, 1.0, 1.0);
}

void SetupRC(void) {
	glClearColor(0.0, 0.0, 1.0, 1.0);
	return;
}

//walk animation functions for polyman, polywoman, squareman
void polymanStepping() {
	if (walkPolyman) {
		if (dyWalkM <= dy + 3.0) { //Zero out and balance with the initial dy position of -3 for all characters
			dyWalkM += 0.1;	//step up
		}
		else if (dyWalkM >= dy + 3.0) {
			dyWalkM -= 0.1;	//step down
		}
	}
}
void polywomanStepping() {
	if (walkPolywoman) {
		if (dyWalkW <= dyW + 3.0) {
			dyWalkW += 0.1;	//step up
		}
		else if (dyWalkW >= dyW + 3.0) {
			dyWalkW -= 0.1;	//step down
		}
	}
}

//run all walk animations
void allStepping() {
	polymanStepping();
	polywomanStepping();
}
void timerFunction(int value) { //animation handled here
	switch (frame) {
		case 1:	//they both move towards stage center
			dx -= 0.15;
			dxW += 0.15;
			allStepping();
			if (dx <= 3.0) { dx = 3.0; frame = 2; walkPolyman = false; dyWalkM = 0; }
			break;
		case 2: //polyman jumps
			dy += 0.2;
			allStepping();
			if (dy > 5.0) { dy = 5.0; frame = 3; }
			break;
		case 3: //polyman does 360 spin in air for style
			theta += 10.0;
			allStepping();
			if (theta >= 360.0) { frame = 4; theta = 0.0; }
			break;
		case 4: //polyman falls back down
			dy -= 0.5;
			allStepping();
			if (dy <= -3.0) { dy = -3.0; frame = 5; walkPolyman = true; dyWalkM = 0; }
			break;
		case 5: //polywoman nods her head
			thetaW -= 5.0;
			allStepping();
			if (thetaW <= -45.0) { thetaW = -45.0; frame = 6; }
			break;
		case 6: //polywoman turns her head up from the bow, makes polyman smile bigger
			thetaW += 5.0;
			allStepping();
			if (thetaW >= 0.0) { thetaW = 0; frame = 7; polymanMouth = true; }
			break;
		case 7: //they both exit stage, polyman to the left, polywoman to the right
			dxW += 0.15;
			dx -= 0.15;
			allStepping();
			if (dxW < -9.0) dxW = -9.0;
			break;
	}
	glutPostRedisplay();
	glutTimerFunc(30, timerFunction, 1);
}