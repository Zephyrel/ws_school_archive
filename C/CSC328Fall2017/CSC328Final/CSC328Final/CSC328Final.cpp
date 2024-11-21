#include<Windows.h>
#include<SOIL2.h>
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
float theta = 0.0, dx = -5.0, dy = -1.0, dz = 0.0;		//Polyman translation vars
float thetaW = 1.0, dxW = -5.0, dyW = -1.0, dzW = 0.0;	//Polywoman translation vars

float bezierTimer = 0.0;
float bezierTimer2 = 0.0;
float Time = 0.0;
int keyframe = 1;

boolean polymanMouth = false, polywomanMouth = false; //false = small mouth, true = big open mouth
boolean walkPolyman = true, walkPolywoman = true, walkSquareman = true; //determines whether walk animation on feet are on or off

boolean gLight;
double dxWalkM = 0, dyWalkM = 0, dzWalkM = 0; //position of feets relevant to origin/acnhor point of the respective characters.
double dxWalkW = 0, dyWalkW = 0, dzWalkW = 0;

void init(void);
void RenderScene(void);
//load everything
void loadIcon(float[], float[], float[][3], float[][3], float[], float[], float[][3], float[][3]);
//draw functions
void drawMan(float[], float[], float[][3], float[][3], float[]);
void drawManFeet(float[], float[]);
void drawWoman(float[], float[], float[][3], float[][3], float[]);
void drawWomanFeet(float[], float[]);
void drawLightsource(float[], float[]);

void loadTree(float[], float[], float[][3], float[][3]);
void drawTree(float[], float[], float[][3], float[][3], float[]);
void setTreeTransform(void);
void drawBackground();


void calcNormalVector(float[], float[], float[], float *, float *, float *);
//transformations for characters
void setManTransform(void);
void setWomanTransform(void);

//transformations for characters' feet
void setManFeetTransform(void);
void setWomanFeetTransform(void);
void SetupRC(void);
void timerFunction(int);

GLuint moonTexture;


//main loop
int main(int argc, char* *argv) {
	//window header name
	char header[] = "Homework 6: Polyman Dancing in the Lights";

	glutInit(&argc, argv);
	glutInitDisplayMode(GLUT_DOUBLE | GLUT_RGB | GLUT_DEPTH);
	//size of window and position on monitor
	glutInitWindowSize(1440, 1080);
	glutInitWindowPosition(140, 20);
	SetupRC();
	//create and loop for window
	glutCreateWindow(header);
	
	
	moonTexture = SOIL_load_OGL_texture("moon.png", SOIL_LOAD_AUTO, SOIL_CREATE_NEW_ID, SOIL_FLAG_POWER_OF_TWO | SOIL_FLAG_INVERT_Y);
	if (!moonTexture) {
		printf("soil failed to load moon.png\n"); exit(0);
	}
	
	/*
	glEnable(GL_TEXTURE_2D);
	/*glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP);
	glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP);
	glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
	glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
	*/

	glutDisplayFunc(RenderScene);
	glutTimerFunc(60, timerFunction, 1);

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
	float fColorW[15][3];
	float nVectorW[15][3];

	float tx[21], ty[21];
	float fColorT[21][3];
	float nVectorT[21][3];

	float ambientLight[] = { 0.0, 1.0, 1.0, 0.5 }; //strong cyan ambient light
	float diffuseLight[] = { 0.0, 1.0, 1.0, 0.5 }; //reflective lighting
	float specularLight[] = { 0.0, 1.0, 1.0, 0.5 };	//specular
	float lightPos[] = { 6.0, 2.0, 2.0, 1.0 }; //light position
	float specRef[] = { 1.0, 1.0, 1.0, 1.0 };
	float spotDir[] = { -4.0, -3.0, -0.5 };

	float ambientLight1[] = { 1.0, 0.0, 0.0, 1.0 }; //strong red ambient light
	float diffuseLight1[] = { 1.0, 0.0, 0.0, 1.0 }; //reflective lighting
	float specularLight1[] = { 1.0, 0.0, 0.0, 1.0 };	//specular
	float lightPos1[] = { 3.5, 0.0, 2.0, 1.0 }; //light position
	float spotDir1[] = { -1.5, -1.0, -0.5 };

	cout << "in renderscene" << endl;

	glColor3f(1.0, 1.0, 1.0);
	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	glViewport(0, 0, 1440, 1080);	//Window size
	glOrtho(-10.0, 10.0, -10.0, 10.0, -10.0, 10.0); //Camera location/scene size

	loadIcon(polyMx, polyMy, fColor, nVector, polyWx, polyWy, fColorW, nVectorW); //load data points for polyman/polywoman
	loadTree(tx, ty, fColorT, nVectorT);
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
	glMateriali(GL_FRONT_AND_BACK, GL_SHININESS, 128);
	glEnable(GL_COLOR_MATERIAL);
	glClearColor(0.5, 0.5, 0.5, 1.0);

	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	glColor3f(1.0, 1.0, 1.0);

	
	drawBackground();

	//set the transformation for polyMan, draw him
	setManTransform();
	drawMan(polyMx, polyMy, fColor, nVector, lightPos);
	setManFeetTransform();
	drawManFeet(polyMx, polyMy);
	
	setWomanTransform();
	drawWoman(polyWx, polyWy, fColorW, nVectorW, lightPos);
	setWomanFeetTransform();
	drawWomanFeet(polyWx, polyWy);

	setTreeTransform();
	drawTree(tx, ty, fColorT, nVectorT, lightPos);


	float orbColor[] = { 0.0, 0.0, 0.0 };
	if (gLight) { orbColor[1] = 3.0; orbColor[2] = 3.0; }
	else { orbColor[1] = 0.0; orbColor[2] = 0.0; }
	//draw Lightsource
	glFlush();
	drawLightsource(lightPos, orbColor);
	glFlush();

	orbColor[1] = 0.0; orbColor[2] = 0.0;
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
void loadIcon(float polyMx[], float polyMy[], float fColor[][3], float nVector[][3], float polyWx[], float polyWy[], float fColorW[][3], float nVectorW[][3]) {
	float p1[3], p2[3], p3[3], nVecX, nVecY, nVecZ;
	//load color to yellow
	for (int i = 0; i < 14; i++) {
		fColor[i][0] = 3.0; fColor[i][1] = 3.0; fColor[i][2] = 0.0;
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

	/*******************
	
		POLYWOMAN
	
	********************/


	float p1W[3], p2W[3], p3W[3], nVecXW, nVecYW, nVecZW;
	//load color to pink
	for (int i = 0; i < 14; i++) {
		fColor[i][0] = 3.0; fColor[i][1] = 0.0; fColor[i][2] = 3.0;
	}

	//front polyWz = 0.0
	//back polyWz = -0.5

	//polywoman body
	polyWx[0] = 0.625;	polyWy[0] = 0.625;
	polyWx[1] = -0.625;	polyWy[1] = 0.625;
	polyWx[2] = -1;		polyWy[2] = 0;
	polyWx[3] = -0.625;	polyWy[3] = -0.625;
	polyWx[4] = 0.625;	polyWy[4] = -0.625;
	polyWx[5] = 1;		polyWy[5] = 0;
	//polyman mouth
	polyWx[6] = 0.25;	polyWy[6] = 0; //Inner Corner, draw to [5] for mouth roof

	polyWx[14] = 0.775;	polyWy[14] = -0.375; //closed position, [4] for open
												 //eye
	polyWx[7] = 0.25;	polyWy[7] = 0.375;
	//feet
	polyWx[8] = -0.25;	polyWy[8] = -0.625;
	polyWx[9] = -0.25;	polyWy[9] = -0.875;
	polyWx[10] = -0;	polyWy[10] = -0.875;
	polyWx[11] = 0.125;	polyWy[11] = -0.375;
	polyWx[12] = 0.125;	polyWy[12] = -0.875;
	polyWx[13] = 0.375;	polyWy[13] = -0.875;

	//load normal

	p1W[0] = polyWx[0];	p1W[1] = polyWy[0];	p1W[2] = 0;
	p2W[0] = polyWx[1];	p2W[1] = polyWy[1];	p2W[2] = 0;
	p3W[0] = polyWx[2];	p3W[1] = polyWy[2];	p3W[2] = 0;

	nVectorW[0][0] = 0.0; nVectorW[0][1] = 0.0; nVectorW[0][2] = 1.0;
	calcNormalVector(p1W, p2W, p3W, &nVecXW, &nVecYW, &nVecZW);
	cout << nVecXW << nVecYW << nVecZW << endl;
	nVectorW[0][0] = nVecXW;	nVectorW[0][1] = nVecYW;	nVectorW[0][2] = nVecZW;



	return;
}

void loadTree(float tx[], float ty[], float fColor[][3], float nVector[][3]) {
	/************
		TREE
	*************/
	float p1[3], p2[3], p3[3], nVecX, nVecY, nVecZ;
	//load color to brown, then green for pine tree leaves, then yellow for star
	for (int i = 0; i < 4; i++) {
		fColor[i][0] = 2.0; fColor[i][1] = 1.0; fColor[i][2] = 0.3;
	}
	for (int i = 4; i < 15; i++) {
		fColor[i][0] = 0.0; fColor[i][1] = 2.0; fColor[i][2] = 0.5;
	}
	for (int i = 15; i < 21; i++) {
		fColor[i][0] = 4.0; fColor[i][1] = 4.0; fColor[i][2] = 0.0;
	}



	//tree trunk
	tx[0] = -0.25;	ty[0] = 0.4;
	tx[1] = 0.25;	ty[1] = 0.4;
	tx[2] = 0.25;	ty[2] = 0;
	tx[3] = -0.25;	ty[3] = 0;
	
	
	//tree, starts at the top
	tx[4] = 0.0;	ty[4] = 2.0;

	tx[5] = 0.5;	ty[5] = 1.6;
	tx[6] = 0.3;	ty[6] = 1.6;
	tx[7] = 0.625;	ty[7] = 1.0;
	tx[8] = 0.3;	ty[8] = 1.0;
	tx[9] = 0.75;	ty[9] = 0.4;
	
	tx[10] = -0.75;	ty[10] = 0.4;
	tx[11] = -0.3;	ty[11] = 1.0;
	tx[12] = -0.625;	ty[12] = 1.0;
	tx[13] = -0.3;	ty[13] = 1.6;
	tx[14] = -0.5;	ty[14] = 1.6;
	
	//hexagram star
	tx[15] = 0.0;	ty[15] = 2.4;
	tx[16] = 0.3;	ty[16] = 1.7;
	tx[17] = -0.3;	ty[17] = 1.7;

	tx[18] = 0.0;	ty[18] = 1.4;
	tx[19] = -0.3;	ty[19] = 2.1;
	tx[20] = 0.3;	ty[20] = 2.1;

	//load normal

	p1[0] = tx[0];	p1[1] = ty[0];	p1[2] = 0;
	p2[0] = tx[1];	p2[1] = ty[1];	p2[2] = 0;
	p3[0] = tx[2];	p3[1] = ty[2];	p3[2] = 0;

	nVector[0][0] = 0.0; nVector[0][1] = 0.0; nVector[0][2] = 1.0;
	calcNormalVector(p1, p2, p3, &nVecX, &nVecY, &nVecZ);
	cout << nVecX << nVecY << nVecZ << endl;
	nVector[0][0] = nVecX;	nVector[0][1] = nVecY;	nVector[0][2] = nVecZ;

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

void drawWoman(float polyWx[], float polyWy[], float fColor[][3], float nVector[][3], float lightPos[]) {
	int i;
	cout << "in drawWoman" << endl;

	//polyman body
	glColor3f(3.0, 0.0, 3.0);
	glShadeModel(GL_SMOOTH);
	//glBegin(GL_POLYGON);
	glEnable(GL_NORMALIZE);
	glNormal3f(nVector[0][0], nVector[0][1], nVector[0][2]);
	
	//draw top half - front face
	glBegin(GL_POLYGON);
	for (i = 2; i >= 0; i--) {
		glVertex3f(polyWx[i], polyWy[i], 0.0);
	}
	glVertex3f(polyWx[5], polyWy[5], 0.0);
	glVertex3f(polyWx[6], polyWy[6], 0.0);
	glEnd();

	for (i = 3; i >= 0; i--) {
		glBegin(GL_POLYGON);
		glVertex3f(polyWx[i + 1], polyWy[i + 1], 0.0);
		glVertex3f(polyWx[i + 1], polyWy[i + 1], -0.5);
		glVertex3f(polyWx[i], polyWy[i], -0.5);
		glVertex3f(polyWx[i], polyWy[i], 0.0);

		glEnd();
	}
	glBegin(GL_POLYGON);
	glVertex3f(polyWx[0], polyWy[0], 0.0);
	glVertex3f(polyWx[0], polyWy[0], -0.5);
	glVertex3f(polyWx[5], polyWy[5], -0.5);
	glVertex3f(polyWx[5], polyWy[5], 0.0);
	glEnd();
	//back face
	glBegin(GL_POLYGON);

	glVertex3f(polyWx[6], polyWy[6], -0.5);
	glVertex3f(polyWx[5], polyWy[5], -0.5);
	for (i = 0; i <= 2; i++) {
		glVertex3f(polyWx[i], polyWy[i], -0.5);
	}
	glEnd();
	
	
	//draw bottom half
	//mouth
	if (!polywomanMouth) { //false, draw kinda open mouth
		
		//chin
		glBegin(GL_POLYGON);
		glVertex3f(polyWx[14], polyWy[14], 0.0);
		glVertex3f(polyWx[14], polyWy[14], -0.5);
		glVertex3f(polyWx[4], polyWy[4], -0.5);
		glVertex3f(polyWx[4], polyWy[4], 0.0);
		glEnd();
		//mouth
		glColor3f(0.5, 0.0, 0.0);

		glBegin(GL_POLYGON);
		glVertex3f(polyWx[6], polyWy[6], -0.5);
		glVertex3f(polyWx[6], polyWy[6], 0.0);
		glVertex3f(polyWx[5], polyWy[5], 0.0);
		glVertex3f(polyWx[5], polyWy[5], -0.5);
		glEnd();

		glBegin(GL_POLYGON);
		glVertex3f(polyWx[6], polyWy[6], 0.0);
		glVertex3f(polyWx[6], polyWy[6], -0.5);
		glVertex3f(polyWx[14], polyWy[14], -0.5);
		glVertex3f(polyWx[14], polyWy[14], 0.0);
		glEnd();

		glColor3f(3.0, 0.0, 3.0);
		//back and front end of face
		glBegin(GL_POLYGON);
		glVertex3f(polyWx[6], polyWy[6], 0.0);
		glVertex3f(polyWx[14], polyWy[14], 0.0);
		for (i = 4; i > 1; i--) {
			glVertex3f(polyWx[i], polyWy[i], 0.0);
		}
		
		glEnd();
		glBegin(GL_POLYGON);

		for (i = 2; i < 5; i++) {
			glVertex3f(polyWx[i], polyWy[i], -0.5);
		}
		glVertex3f(polyWx[14], polyWy[14], -0.5);
		glVertex3f(polyWx[6], polyWy[6], -0.5);
		glEnd();
	} else {	//draw really open mouth
		for (i = 2; i <= 4; i++) {
			glVertex3f(polyWx[i], polyWy[i], -0.5);
		}
		glVertex3f(polyWx[6], polyWy[6], -0.5);
		glEnd();
		glBegin(GL_POLYGON);
		for (i = 2; i <= 4; i++) {
			glVertex3f(polyWx[i], polyWy[i], 0);
		}
		glVertex3f(polyWx[6], polyWy[6], 0);
		glEnd();
	}
	
	//draw feet in own function
	//eye
	glColor3f(0.0, 0.0, 0.0);
	glBegin(GL_POLYGON);
	glVertex3f(polyWx[7] + 0.15, polyWy[7], 0.0001);
	glVertex3f(polyWx[7], polyWy[7] - 0.15, 0.0001);
	glVertex3f(polyWx[7] - 0.15, polyWy[7], 0.0001);
	glVertex3f(polyWx[7], polyWy[7] + 0.15, 0.0001);
	glEnd();
	
	glBegin(GL_POLYGON);
	glVertex3f(polyWx[7] + 0.15, polyWy[7], -0.5001);
	glVertex3f(polyWx[7], polyWy[7] + 0.15, -0.5001);
	glVertex3f(polyWx[7] - 0.15, polyWy[7], -0.5001);
	glVertex3f(polyWx[7], polyWy[7] - 0.15, -0.5001);
	glEnd();

	return;
}

void drawTree(float TX[], float TY[], float fColor[][3], float nVector[][3], float lightPos[]) {
	int i;
	cout << "in drawTree" << endl;

	//Tree Trunk
	glColor3f(2.0, 1.0, 0.3);
	glShadeModel(GL_SMOOTH);
	glBegin(GL_POLYGON);
	glEnable(GL_NORMALIZE);
	glNormal3f(nVector[0][0], nVector[0][1], nVector[0][2]);

	for (i = 0; i < 4; i++) {
		glVertex3f(TX[i], TY[i], 0.0);
	}
	glEnd();
	
	//Tree
	glColor3f(0.0, 2.0, 0.5);
	glBegin(GL_POLYGON);
	for (i = 4; i < 15; i++) {
		glVertex3f(TX[i], TY[i], 0.05);
	}
	glEnd();

	//Star
	glColor3f(4.0, 4.0, 0.0);
	glBegin(GL_POLYGON);
	for (i = 15; i < 18; i++) {
		glVertex3f(TX[i], TY[i], 0.1);
	}
	glEnd();

	glBegin(GL_POLYGON);
	for (i = 18; i < 21; i++) {
		glVertex3f(TX[i], TY[i], 0.1);
	}
	glEnd();
	return;
}

void drawManFeet(float polyMx[], float polyMy[]) {
	glColor3f(3.0, 3.0, 0.0);
	glBegin(GL_LINE_STRIP);
	for (int i = 8; i <= 10; i++) {
		glVertex3f(polyMx[i], polyMy[i], dz-0.25);
	}
	glEnd();
	glBegin(GL_LINE_STRIP);
	for (int i = 11; i <= 13; i++) {
		glVertex3f(polyMx[i], polyMy[i], dz-0.25);
	}
	glEnd();
}

void drawWomanFeet(float polyWx[], float polyWy[]) {
	glColor3f(3.0, 0.0, 3.0);
	glBegin(GL_LINE_STRIP);
	for (int i = 8; i <= 10; i++) {
		glVertex3f(polyWx[i], polyWy[i], dzW-0.25);
	}
	glEnd();
	glBegin(GL_LINE_STRIP);
	for (int i = 11; i <= 13; i++) {
		glVertex3f(polyWx[i], polyWy[i], dzW-0.25);
	}
	glEnd();
}


void drawLightsource(float xyzLight[], float rgbLight[]) {

	glMatrixMode(GL_MODELVIEW);
	glLoadIdentity();
	glTranslatef(xyzLight[0], xyzLight[1], xyzLight[2]);
	glColor3f(rgbLight[0], rgbLight[1], rgbLight[2]);

	//glBindTexture(GL_TEXTURE_2D, moonTexture);
	glutSolidSphere(0.5, 10, 10);
	return;

}

void drawBackground() {
	glShadeModel(GL_SMOOTH);
	glColor3f(0.001, 0.1, 0.5);
	glBegin(GL_POLYGON);				//sky
	glVertex3f(-9.999, 9.999, -8.0);
	glVertex3f(9.999, 9.999, -8.0);
	glVertex3f(9.999, -9.999, -8.0);
	glVertex3f(-9.999, -9.999, -8.0);
	glEnd();
	glShadeModel(GL_SMOOTH);			//ground
	glColor3f(0.6, 1.0, 1.0);
	glBegin(GL_POLYGON);
	glVertex3f(-10.0, -2.0, -8.0);
	glVertex3f(10.0, -2.0, -8.0);
	glVertex3f(10.0, -10.0, 8.0);
	glVertex3f(-10.0, -10.0, 8.0);
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
	glTranslatef(dx, dy, dz);
	glRotatef(theta, 1.0, 1.0, 1.0);
	return;
}

void setManFeetTransform(void) {

	cout << "setMFeetTransform" << endl;
	glMatrixMode(GL_MODELVIEW);
	glLoadIdentity();
	glTranslatef(dx + dxWalkM, dy + dyWalkM, dz + dzWalkM);
	glRotatef(theta, 1.0, 1.0, 1.0);
	return;
}

void setWomanTransform(void) {

	cout << "in setManTransform" << endl;
	glMatrixMode(GL_MODELVIEW);
	glLoadIdentity();
	glTranslatef(dxW, dyW, dzW);
	glRotatef(thetaW, 1.0, 0.0, 1.0);
	return;
}

void setWomanFeetTransform(void) {

	cout << "setMFeetTransform" << endl;
	glMatrixMode(GL_MODELVIEW);
	glLoadIdentity();
	glTranslatef(dxW + dxWalkW, dyW + dyWalkW, dzW + dzWalkW);
	glRotatef(thetaW, 1.0, 0.0, 1.0);
	return;
}

void setTreeTransform(void) {

	cout << "in setTreeTransform" << endl;
	glMatrixMode(GL_MODELVIEW);
	glLoadIdentity();
	glTranslatef(5.0, -3.0, -2.5);
	glScalef(4.0, 4.0, 4.0);
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

void polywomanStepping() {
	if (walkPolyman) {
		if (dyWalkW <= dy + 3.0) { //Zero out and balance with the initial dy position of -3 for all characters
			dyWalkW += 0.1;	//step up
		}
		else if (dyWalkW >= dy + 3.0) {
			dyWalkW -= 0.1;	//step down
		}
	}
}

//bezier curve for 2 points "Start" and "End"
void bezierMove(float* dx, float* dy, float* dz, float dxStart, float dyStart, float dzStart, float dxEnd, float dyEnd, float dzEnd, float* bezTimer, float timeframe) {
	//bezTimer is a pointer to either bezierTimer or bezierTimer2 for polyman and polywoman respectively, that ranges from 0 to 1 with all decimals inbetween
	*dx = (1.0 - *bezTimer) * dxStart + (*bezTimer * dxEnd);
	*dy = (1.0 - *bezTimer) * dyStart + (*bezTimer * dyEnd);
	*dz = (1.0 - *bezTimer) * dzStart + (*bezTimer * dzEnd);

	*bezTimer += (1 / timeframe);	//This is so the bezier curve goes at a constant rate depending on how fast/slow i.e. the timeframe
									//is set to.

}


//run all walk animations
void allStepping() {
	polymanStepping();
	polywomanStepping();
}
void timerFunction(int value) { //animation handled here
	Time++;
	if (Time < 151.0) { glEnable(GL_LIGHT0); glDisable(GL_LIGHT1); gLight = true; }
	else { glDisable(GL_LIGHT0); glEnable(GL_LIGHT1); gLight = false; }
	if (Time > 300.0) { Time = 0.0; }
	switch (keyframe) {
	case 1:	//they both move towards stage center
		bezierMove(&dx, &dy, &dz, -5.0, -1.0, 0.0, 0.0, -1.0, 0.0, &bezierTimer, 20);
		bezierMove(&dxW, &dyW, &dzW, -5.0, -1.0, 0.0, 0.0, -1.0, 0.0, &bezierTimer2, 20);
		if (bezierTimer >= 1.0) {
			keyframe = 2; bezierTimer = 0.0; bezierTimer2 = 0.0; walkPolyman = TRUE; walkPolywoman = TRUE; dy = -1.0; dyW = -1.0;  dx = 0.0; dxW = 0.0;
		}
	case 2:
		dx += 0.2;
		dxW += 0.2;
		if (dx >= 5.0) {
			keyframe = 3; bezierTimer = 0.0; bezierTimer2 = 0.0; walkPolyman = FALSE; walkPolywoman = FALSE; 
		}
	case 3:
		bezierMove(&dx, &dy, &dz, 5.0, -1.0, 0.0, 2.0, -1.0, 0.0, &bezierTimer, 20);
		bezierMove(&dxW, &dyW, &dzW, 5.0, -1.0, 0.0, 2.0, -1.0, 0.0, &bezierTimer2, 20);
		if (bezierTimer >= 1.0) {
			keyframe = 4; bezierTimer = 0.0; bezierTimer2 = 0.0; walkPolyman = TRUE; walkPolywoman = TRUE; dy = -1.0; dyW = -1.0;  dx = 0.0; dxW = 0.0;
		}
	case 4:
		dx -= 0.2;
		dxW -= 0.2;
		if (dx <= -11.0) {
			keyframe = 1; bezierTimer = 0.0; bezierTimer2 = 0.0; walkPolyman = TRUE; walkPolywoman = TRUE; 
		}
	}
	glutPostRedisplay();
	glutTimerFunc(30, timerFunction, 1);
}