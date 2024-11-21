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
float theta = 0.0, dx = 0.0, dy = 0.0;
float Time = 0.0;

int keyframe = 1;
boolean polymanMouth = false, polywomanMouth = false; //false = small mouth, true = big open mouth
boolean walkPolyman = true, walkPolywoman = true, walkSquareman = true; //determines whether walk animation on feet are on or off

boolean gLight;
double dxWalkM = 0, dyWalkM = 0; //position of feets relevant to origin/acnhor point of the respective characters.

void init(void);
void RenderScene(void);
//load everything
void loadIcon(float[], float[], float[][3], float[][3]);
//draw functions
void drawMan(float[], float[], float[][3], float[][3], float[]);
void drawManFeet(float[], float[]);
void drawLightsource(float[], float[]);
void drawBackground();
void calcNormalVector(float[], float[], float[], float *, float *, float *);
//transformations for characters
void setManTransform(void);


//transformations for characters' feet
void setManFeetTransform(void);


void SetupRC(void);
void timerFunction(int);

//main loop
int main(int argc, char* *argv) {
	//window header name
	char header[] = "Homework 6: Polyman Dancing in the Lights";

	glutInit(&argc, argv);
	glutInitDisplayMode(GLUT_DOUBLE | GLUT_RGB | GLUT_DEPTH);
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
	float fColor[15][3];
	float nVector[15][3];

	float ambientLight[] = { 1.0, 1.0, 1.0, 0.5 }; //strong green ambient light
	float diffuseLight[] = { 1.0, 1.0, 1.0, 0.5 }; //reflective lighting
	float specularLight[] = { 1.0, 1.0, 1.0, 0.5 };	//specular
	float lightPos[] = { 4.0, 5.0, 2.0, 1.0 }; //light position
	float specRef[] = { 1.0, 1.0, 1.0, 1.0 };
	float spotDir[] = { -4.0, -5.0, -0.5 };

	float ambientLight1[] = { 1.0, 1.0, 1.0, 1.0 }; //strong red ambient light
	float diffuseLight1[] = { 1.0, 1.0, 1.0, 1.0 }; //reflective lighting
	float specularLight1[] = { 1.0, 1.0, 1.0, 1.0 };	//specular
	float lightPos1[] = { -4.0, 5.0, 2.0, 1.0 }; //light position
	float spotDir1[] = { 4.0, -5.0, -0.5 };

	cout << "in renderscene" << endl;

	glColor3f(1.0, 1.0, 1.0);
	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	glViewport(0, 0, 540, 440);	//Window size
	glOrtho(-7.0, 7.0, -7.0, 7.0, -7.0, 7.0); //Camera location/scene size

	loadIcon(polyMx, polyMy, fColor, nVector); //load data points for polyman/polywoman

											   //refreshes each frame to make sure they don't draw over each other each frame
											   //like games do when you're noclipping out of bounds in a void space
											   //enable lighting
	glEnable(GL_DEPTH_TEST);
	glEnable(GL_LIGHTING);
	glEnable(GL_CULL_FACE);
	glFrontFace(GL_CW);

	//set the lights
	glMatrixMode(GL_MODELVIEW);
	glLoadIdentity();
	glLightfv(GL_LIGHT0, GL_POSITION, lightPos);
	glLightfv(GL_LIGHT0, GL_DIFFUSE, diffuseLight);
	glLightfv(GL_LIGHT0, GL_AMBIENT, ambientLight);
	glLightfv(GL_LIGHT0, GL_SPECULAR, specularLight);
	glLightf(GL_LIGHT0, GL_SPOT_CUTOFF, 45.0);
	glLightf(GL_LIGHT0, GL_SPOT_EXPONENT, 100.0);
	glLightfv(GL_LIGHT0, GL_SPOT_DIRECTION, spotDir);
	//glEnable(GL_LIGHT0); //green

	glLightfv(GL_LIGHT1, GL_POSITION, lightPos1);
	glLightfv(GL_LIGHT1, GL_DIFFUSE, diffuseLight1);
	glLightfv(GL_LIGHT1, GL_AMBIENT, ambientLight1);
	glLightfv(GL_LIGHT1, GL_SPECULAR, specularLight1);
	glLightf(GL_LIGHT1, GL_SPOT_CUTOFF, 45.0);
	glLightf(GL_LIGHT1, GL_SPOT_EXPONENT, 100.0);
	glLightfv(GL_LIGHT1, GL_SPOT_DIRECTION, spotDir1);
	//glEnable(GL_LIGHT1); red

	//Set the material
	glEnable(GL_COLOR_MATERIAL);
	glColorMaterial(GL_FRONT_AND_BACK, GL_AMBIENT_AND_DIFFUSE);
	glMaterialfv(GL_FRONT_AND_BACK, GL_SPECULAR, specRef);
	glMateriali(GL_FRONT_AND_BACK, GL_SHININESS, 100);
	glEnable(GL_COLOR_MATERIAL);
	glClearColor(0.5, 0.5, 0.5, 1.0);

	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	//glColor3f(1.0, 1.0, 1.0);

	drawBackground();

	//set the transformation for polyMan, draw him
	setManTransform();
	drawMan(polyMx, polyMy, fColor, nVector, lightPos);
	setManFeetTransform();
	drawManFeet(polyMx, polyMy);
	float orbColor[] = { 0.0, 0.0, 0.0 };
	if (gLight) orbColor[1] = 3.0;
	else orbColor[1] = 0.0;
	//draw Lightsource
	glFlush();
	drawLightsource(lightPos, orbColor);
	glFlush();

	orbColor[1] = 0.0;
	if (!gLight) orbColor[0] = 3.0;
	else orbColor[0] = 0.0;


	drawLightsource(lightPos1, orbColor);
	//flush the buffer, no memory leaks here
	glFlush();
	glEnd();
	glutSwapBuffers();
	return;
}
//load points that make up polyman and polywoman's body
void loadIcon(float polyMx[], float polyMy[], float fColor[][3], float nVector[][3]) {
	float p1[3], p2[3], p3[3], nVecX, nVecY, nVecZ;
	//load color to yellow
	for (int i = 0; i < 14; i++) {
		fColor[i][0] = 255.0; fColor[i][1] = 242.0; fColor[i][2] = 0.0;
	}

	//front polyMz = 0.0
	//back polyMz = -0.5

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

	//load normal

	p1[0] = polyMx[0];	p1[1] = polyMy[0];	p1[2] = 0;
	p2[0] = polyMx[1];	p2[1] = polyMy[1];	p2[2] = 0;
	p3[0] = polyMx[2];	p3[1] = polyMy[2];	p3[2] = 0;

	nVector[0][0] = 0.0; nVector[0][1] = 0.0; nVector[0][2] = 1.0;
	calcNormalVector(p1, p2, p3, &nVecX, &nVecY, &nVecZ);
	cout << nVecX << nVecY << nVecZ << endl;
	nVector[0][0] = nVecX;	nVector[0][1] = nVecY;	nVector[0][2] = nVecZ;

	return;
}
//draw functions
void drawMan(float polyMx[], float polyMy[], float fColor[][3], float nVector[][3], float lightPos[]) {
	int i;
	cout << "in drawMan" << endl;

	//polyman body
	glColor3f(3.0, 3.0, 0.0);
	glShadeModel(GL_SMOOTH);
	glBegin(GL_POLYGON);
	glEnable(GL_NORMALIZE);
	glNormal3f(nVector[0][0], nVector[0][1], nVector[0][2]);

	//draw top half - front face

	glVertex3f(polyMx[6], polyMy[6], 0.0);
	glVertex3f(polyMx[5], polyMy[5], 0.0);
	for (i = 0; i <= 2; i++) {
		glVertex3f(polyMx[i], polyMy[i], 0.0);
	}
	glEnd();
	for (i = 0; i < 4; i++) {
		glBegin(GL_POLYGON);
		glVertex3f(polyMx[i], polyMy[i], 0.0);
		glVertex3f(polyMx[i], polyMy[i], -0.5);
		glVertex3f(polyMx[i + 1], polyMy[i + 1], -0.5);
		glVertex3f(polyMx[i + 1], polyMy[i + 1], 0.0);

		glEnd();
	}
	glBegin(GL_POLYGON);
	glVertex3f(polyMx[5], polyMy[5], 0.0);
	glVertex3f(polyMx[5], polyMy[5], -0.5);
	glVertex3f(polyMx[0], polyMy[0], -0.5);
	glVertex3f(polyMx[0], polyMy[0], 0.0);
	glEnd();
	//back face
	glBegin(GL_POLYGON);

	for (i = 2; i >= 0; i--) {
		glVertex3f(polyMx[i], polyMy[i], -0.5);
	}
	glVertex3f(polyMx[5], polyMy[5], -0.5);
	glVertex3f(polyMx[6], polyMy[6], -0.5);
	glEnd();


	//draw bottom half
	//mouth
	if (!polymanMouth) { //false, draw kinda open mouth
						 //chin
		glBegin(GL_POLYGON);
		glVertex3f(polyMx[14], polyMy[14], 0.0);
		glVertex3f(polyMx[14], polyMy[14], -0.5);
		glVertex3f(polyMx[4], polyMy[4], -0.5);
		glVertex3f(polyMx[4], polyMy[4], 0.0);
		glEnd();
		//mouth
		glColor3f(0.5, 0.0, 0.0);
		glBegin(GL_POLYGON);
		glVertex3f(polyMx[5], polyMy[5], -0.5);
		glVertex3f(polyMx[5], polyMy[5], 0.0);
		glVertex3f(polyMx[6], polyMy[6], 0.0);
		glVertex3f(polyMx[6], polyMy[6], -0.5);
		glEnd();
		glBegin(GL_POLYGON);
		glVertex3f(polyMx[14], polyMy[14], 0.0);
		glVertex3f(polyMx[14], polyMy[14], -0.5);
		glVertex3f(polyMx[6], polyMy[6], -0.5);
		glVertex3f(polyMx[6], polyMy[6], 0.0);
		glEnd();

		glColor3f(3.0, 3.0, 0.0);
		//back and front end of face
		glBegin(GL_POLYGON);
		for (i = 2; i < 5; i++) {
			glVertex3f(polyMx[i], polyMy[i], 0.0);
		}
		glVertex3f(polyMx[14], polyMy[14], 0.0);
		glVertex3f(polyMx[6], polyMy[6], 0.0);
		glEnd();
		glBegin(GL_POLYGON);
		glVertex3f(polyMx[6], polyMy[6], -0.5);
		glVertex3f(polyMx[14], polyMy[14], -0.5);
		for (i = 4; i > 1; i--) {
			glVertex3f(polyMx[i], polyMy[i], -0.5);
		}
		glEnd();
	}
	else {	//draw really open mouth
		for (i = 2; i <= 4; i++) {
			glVertex3f(polyMx[i], polyMy[i], -0.5);
		}
		glVertex3f(polyMx[6], polyMy[6], -0.5);
		glEnd();
		glBegin(GL_POLYGON);
		for (i = 2; i <= 4; i++) {
			glVertex3f(polyMx[i], polyMy[i], 0);
		}
		glVertex3f(polyMx[6], polyMy[6], 0);
		glEnd();
	}

	//draw feet in own function
	//eye
	glColor3f(0.0, 0.0, 0.0);
	glBegin(GL_POLYGON);
	glVertex3f(polyMx[7] + 0.15, polyMy[7], 0.0001);
	glVertex3f(polyMx[7], polyMy[7] - 0.15, 0.0001);
	glVertex3f(polyMx[7] - 0.15, polyMy[7], 0.0001);
	glVertex3f(polyMx[7], polyMy[7] + 0.15, 0.0001);
	glEnd();

	glBegin(GL_POLYGON);
	glVertex3f(polyMx[7] + 0.15, polyMy[7], -0.5001);
	glVertex3f(polyMx[7], polyMy[7] + 0.15, -0.5001);
	glVertex3f(polyMx[7] - 0.15, polyMy[7], -0.5001);
	glVertex3f(polyMx[7], polyMy[7] - 0.15, -0.5001);
	glEnd();

	return;
}
void drawManFeet(float polyMx[], float polyMy[]) {
	glColor3f(3.0, 3.0, 0.0);
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

void drawLightsource(float xyzLight[], float rgbLight[]) {

	glMatrixMode(GL_MODELVIEW);
	glLoadIdentity();
	glTranslatef(xyzLight[0], xyzLight[1], xyzLight[2]);
	glColor3f(rgbLight[0], rgbLight[1], rgbLight[2]);
	glutSolidSphere(0.5, 10, 10);
	return;

}

void drawBackground() {
	glShadeModel(GL_SMOOTH);
	glColor3f(1.0, 1.0, 1.0);
	glBegin(GL_POLYGON);
	glVertex3f(-5.0, 5.0, -2.0);
	glVertex3f(5.0, 5.0, -2.0);
	glVertex3f(5.0, 0.0, -2.0);
	glVertex3f(-5.0, 0.0, -2.0);
	glEnd();
	glColor3f(2.0, 2.0, 2.0);
	glBegin(GL_POLYGON);
	glVertex3f(-5.0, 0.0, -2.0);
	glVertex3f(5.0, 0.0, -2.0);
	glVertex3f(5.0, -3.0, 2.0);
	glVertex3f(-5.0, -3.0, 2.0);
	glEnd();

}

void calcNormalVector(float fVert1[], float fVert2[], float fVert3[], float *fNormal1x, float *fNormal1y, float *fNormal1z) {

	float Qx, Qy, Qz, Px, Py, Pz, denom;
	Qx = fVert2[0] - fVert1[0];
	Qy = fVert2[1] - fVert1[1];
	Qz = fVert2[2] - fVert1[2];
	Px = fVert3[0] - fVert1[0];
	Py = fVert3[1] - fVert1[1];
	Pz = fVert3[2] - fVert1[2];
	*fNormal1x = Py*Qz - Pz*Qy;
	*fNormal1y = Pz*Qx - Px*Qz;
	*fNormal1z = Px*Qy - Py*Qx;
}
//transformation for Polyman
void setManTransform(void) {

	cout << "in setManTransform" << endl;
	glMatrixMode(GL_MODELVIEW);
	glLoadIdentity();
	glTranslatef(dx, dy, -0.5);
	glRotatef(theta, 1.0, 1.0, 1.0);
	return;
}

void setManFeetTransform(void) {

	cout << "setMFeetTransform" << endl;
	glMatrixMode(GL_MODELVIEW);
	glLoadIdentity();
	glTranslatef(dx + dxWalkM, dy + dyWalkM, -0.5);
	glRotatef(theta, 1.0, 1.0, 1.0);
	return;
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

//run all walk animations
void allStepping() {
	polymanStepping();
}
void timerFunction(int value) { //animation handled here
	Time++;
	/*if (Time < 151.0) { glEnable(GL_LIGHT0); glDisable(GL_LIGHT1); gLight = true; }
	else { glDisable(GL_LIGHT0); glEnable(GL_LIGHT1); gLight = false; }
	if (Time > 300.0) { Time = 0.0; }*/
	switch (keyframe) {
	case 1:	//they both move towards stage center
			//theta += 10.0;
		dx -= 0.2;
		if (dx < -4.0)keyframe++;
		break;
	case 2:
		dx += 0.2;
		if (dx > 4.0) keyframe++;
		break;
	case 3:
		dx -= 0.2;
		if (dx <= 0.0) { keyframe++; dx = 0.0; }
		break;
	case 4:
		dy += 0.2;
		theta += 10.0;
		if (theta >= 180.0) keyframe++;
		break;
	case 5:
		dy -= 0.2;
		theta += 10.0;
		if (theta >= 360.0) { keyframe = 1; dy = 0.0; theta = 0.0; }
		break;
	}
	glutPostRedisplay();
	glutTimerFunc(30, timerFunction, 1);
}