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
Test 3
*/

float scale1 = 1.0;
float theta = 0.0, dx = 0.0, dy = 0.0;
float theta2 = 0.0;

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
void loadIcon(float[], float[], float[], float[][3], float[][3]);
//draw functions
void drawIcon(float[], float[], float[], float[][3], float[][3], float[]);

void drawLightsource(float[]);
void calcNormalVector(float[], float[], float[], float *, float *, float *);
//transformations for characters
void setTransform(void);

void SetupRC(void);
void timerFunction(int);

//main loop
int main(int argc, char* *argv) {
	//window header name
	char header[] = "CSC328 Test 3";

	glutInit(&argc, argv);
	glutInitDisplayMode(GLUT_DOUBLE|GLUT_RGB|GLUT_DEPTH);
	//size of window and position on monitor
	glutInitWindowSize(1000, 1000);
	glutInitWindowPosition(0,0);
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
	//Pyramid co-ords
	float Px[6] = { 0.0, 2.0, 2.0, -2.0, -2.0, 0.0 };
	float Py[6] = { 5.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
	float Pz[6] = { 0.0, -2.0, 2.0, 2.0, -2.0, 0.0 };

	float fColor[6][3];
	float nVector[6][3];

	//light0
	float light[] = { 1.0, 0.0, 0.0, 1.0 }; //strong white ambient light
	float lightPos[] = { -7.0, 8.0, 4.0, 1.0 }; //light position
	float spotDir[] = { 7.0, -8.0, -4.0};
	//light1
	float light1[] = { 0.0, 1.0, 0.0, 1.0 }; //strong white ambient light
	float lightPos1[] = { 7.0, 8.0, 4.0, 1.0 }; //light position
	float spotDir1[] = { -7.0, -8.0, -4.0 };

	float specRef[] = { 1.0, 1.0, 1.0, 1.0 };

	cout << "in renderscene" << endl;

	glColor3f(1.0, 1.0, 1.0);
	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	glViewport(0, 0, 540, 440);	//Window size
	glOrtho(-15.0, 15.0, -15.0, 15.0, 15.0, -15.0); //Camera location/scene size

	loadIcon(Px, Py, Pz, fColor, nVector); //load data points for polyman/polywoman

															   //refreshes each frame to make sure they don't draw over each other each frame
															   //like games do when you're noclipping out of bounds in a void space
	
															   //enable lighting
	//stand on the axis perpendicular to the polygon
	//enter the points clockwise or counter clockwise
	glEnable(GL_DEPTH_TEST);
	glEnable(GL_LIGHTING);
	glEnable(GL_CULL_FACE);
	glFrontFace(GL_CW);

	//set the lights
	glMatrixMode(GL_MODELVIEW);
	glLoadIdentity();
	glLightfv(GL_LIGHT0, GL_POSITION, lightPos);
	glLightfv(GL_LIGHT0, GL_DIFFUSE, light);
	glLightfv(GL_LIGHT0, GL_AMBIENT, light);
	glLightfv(GL_LIGHT0, GL_SPECULAR, light);
	glLightf(GL_LIGHT0, GL_SPOT_CUTOFF, 95.0);
	glLightf(GL_LIGHT0, GL_SPOT_EXPONENT, 15.0);
	glLightfv(GL_LIGHT0, GL_SPOT_DIRECTION, spotDir);
	glEnable(GL_LIGHT0);

	glMatrixMode(GL_MODELVIEW);
	glLoadIdentity();
	glLightfv(GL_LIGHT1, GL_POSITION, lightPos1);
	glLightfv(GL_LIGHT1, GL_DIFFUSE, light1);
	glLightfv(GL_LIGHT1, GL_AMBIENT, light1);
	glLightfv(GL_LIGHT1, GL_SPECULAR, light1);
	glLightf(GL_LIGHT1, GL_SPOT_CUTOFF, 92.0);
	glLightf(GL_LIGHT1, GL_SPOT_EXPONENT, 15.0);
	glLightfv(GL_LIGHT1, GL_SPOT_DIRECTION, spotDir1);
	glEnable(GL_LIGHT1);

	//Set the material
	glEnable(GL_COLOR_MATERIAL);
	glColorMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE);
	glMaterialfv(GL_FRONT, GL_SPECULAR, specRef);
	glMateriali(GL_FRONT, GL_SHININESS, 100);
	glEnable(GL_COLOR_MATERIAL);

	glClearColor(0.5, 0.5, 0.5, 1.0);
	glClear(GL_COLOR_BUFFER_BIT|GL_DEPTH_BUFFER_BIT);
	//glColor3f(1.0, 1.0, 1.0);

	//set the transformation for polyMan, draw him
	setTransform();
	drawIcon(Px, Py, Pz, fColor, nVector, lightPos);
	//draw Lightsource
	glFlush();
	drawLightsource(lightPos);
	glFlush();
	drawLightsource(lightPos1);
	glFlush();
	//set the transformation for polyWoman, draw her


	//flush the buffer, no memory leaks here
	glFlush();
	glEnd();
	glutSwapBuffers();
	return;
}
//load points that make up polyman and polywoman's body
void loadIcon(float px[], float py[], float pz[], float fColor[][3], float nVector[][3]) {
	float p1[6], p2[3], p3[3], nVecX, nVecY, nVecZ;

	//points of px, py, pz defined globally
	/*float Px[6] = { 0.0, 2.0, 2.0, -2.0, -2.0, 0.0 };
	float Py[6] = { 5.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
	float Pz[6] = { 0.0, -2.0, 2.0, 2.0, -2.0, 0.0 };*/

	//point 0, 2, 3 is front face

	//load colors in order: right, front, left, back, bottom
	fColor[0][0] = 0.0; fColor[0][1] = 0.0; fColor[0][2] = 1.0;
	fColor[1][0] = 1.0; fColor[1][1] = 0.0; fColor[1][2] = 0.0;
	fColor[2][0] = 0.0; fColor[2][1] = 0.0; fColor[2][2] = 1.0;
	fColor[3][0] = 1.0; fColor[3][1] = 0.0; fColor[3][2] = 0.0;
	fColor[4][0] = 0.0; fColor[4][1] = 1.0; fColor[4][2] = 0.0;

	//load normal

	p1[0] = px[0];	p1[1] = py[0];	p1[2] = pz[0];
	p2[0] = px[2];	p2[1] = py[2];	p2[2] = pz[2];
	p3[0] = px[3];	p3[1] = py[3];	p3[2] = pz[3];

	nVector[0][0] = 0.0; nVector[0][1] = 0.0; nVector[0][2] = 1.0;
	calcNormalVector(p1, p2, p3, &nVecX, &nVecY, &nVecZ);
	cout << nVecX << nVecY << nVecZ << endl;
	nVector[0][0] = nVecX;	nVector[0][1] = nVecY;	nVector[0][2] = nVecZ;

	return;
}
//draw functions
void drawIcon(float px[], float py[], float pz[], float fColor[][3], float nVector[][3], float lightPos[]) {
	int i;
	cout << "in drawIcon" << endl;
	glColor3f(0.0, 0.0, 1.0);

	glBegin(GL_POLYGON);
	glEnable(GL_NORMALIZE);
	i = 1;
	while (i < 4) {
		if(i!=1)	glBegin(GL_POLYGON);
		glVertex3f(px[0], py[0], pz[0]); //from the top!
		glVertex3f(px[i], py[i], pz[i]);
		glVertex3f(px[i + 1], py[i + 1], pz[i + 1]);
		glVertex3f(px[0], py[0], pz[0]);
		glEnd();
		i++;
		if (i % 2 == 0) { glColor3f(1.0, 0.0, 0.0); } //red
		else { glColor3f(0.0, 0.0, 1.0); }	//blue
	}
	//backside
	glBegin(GL_POLYGON);
	glVertex3f(px[0], py[0], pz[0]); //from the top!
	glVertex3f(px[1], py[1], pz[1]);
	glVertex3f(px[4], py[4], pz[4]);
	glVertex3f(px[0], py[0], pz[0]);
	glEnd();
	//bottom
	glColor3f(0.0, 1.0, 0.0);
	glBegin(GL_POLYGON);
	for (i = 1; i < 5; i++) {
		glVertex3f(px[i], py[i], pz[i]);
	}
	glVertex3f(px[1], py[1], pz[1]);
	glEnd();

	return;
}

void drawLightsource(float xyzLight[]) {

	glMatrixMode(GL_MODELVIEW);
	glLoadIdentity();
	glTranslatef(xyzLight[0], xyzLight[1], xyzLight[2]);
	glColor3f(1.0, 1.0, 1.0);
	glutSolidSphere(0.5, 10, 10);
	return;

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
//transformation for Pyramid
void setTransform(void) {

	cout << "in setTransform" << endl;
	glMatrixMode(GL_MODELVIEW);
	glLoadIdentity();
	glTranslatef(dx, dy, -0.5);
	glRotatef(theta, 0.0, 1.0, 0.0);
	glRotatef(theta2, 1.0, 1.0, 1.0);
	return;
}

void SetupRC(void) {
	glClearColor(0.0, 0.0, 1.0, 1.0);
	return;
}

void timerFunction(int value) { //animation handled here
	switch (frame) {
	case 1:	//they both move towards stage center
		theta += 2.0;
		theta2 += 2.0;
		if((theta>=0 && theta< 90) || (theta>=180 && theta <270)){ //odd
			glEnable(GL_LIGHT1);
			glDisable(GL_LIGHT0);
		}
		else if ((theta >= 90 && theta < 180) || (theta >= 270 && theta < 360)) {
			glEnable(GL_LIGHT0);
			glDisable(GL_LIGHT1);
		}
		break;
	}
	glutPostRedisplay();
	glutTimerFunc(30, timerFunction, 1);
}