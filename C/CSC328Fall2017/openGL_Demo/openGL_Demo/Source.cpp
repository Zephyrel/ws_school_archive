#include<Windows.h>
#include<GL/glut.H>
#include<stdlib.h>
#include<math.h>

/*******************************************\
|											|
|	Title:		CSC328 HW1					|
|	Author:		Russell Ferguson Jr			|
|	Professor:	Kent Pickett				|
|											|
\*******************************************/

void init(void);
void RenderScene(void);
void SetupRC(void);

int main(int argc, char* *argv) {
	//Window Title
	char header[] = "Graphs g(x) = x^3 + x^2 - 20x (In Red), h(x) = 10cos(x)+3 (In Blue), and f(x) = g(x)-h(x) (In Pink) by Russell Ferguson";
	glutInit(&argc, argv);
	glutInitDisplayMode(GLUT_SINGLE | GLUT_RGBA);
	glutInitWindowSize(1024, 720);	//Initialize size
	glutInitWindowPosition(0, 0);	//Initialize Position
	glutCreateWindow(header);		//Create Window
	glutDisplayFunc(RenderScene);
	SetupRC();
	glutMainLoop();
	return 0;
}

void SetupRC(void) {
	glClearColor(0.6f, 0.4f, 0.70f, 1.0f);	//Sets the background color of the draw
	return;
}

void RenderScene(void) {
	double x, y, z, xdel = 0.25;
	glClear(GL_COLOR_BUFFER_BIT);
	glLoadIdentity();
	glColor3f(1.0, 1.0, 1.0);						//set current color drawing to white
	glViewport(32, 32, 960, 656);					//sets viewport positioning and height/width of space used.
	glOrtho(-10.0, 10.0, -50.0, 50.0, 1.0, -1.0);	//Establish the clipping volume in user units

	//drawing the graph's x axis and y-axis, and placing tick markers
	glBegin(GL_LINES);
	//x-axis
	glVertex2f(-10.0, 0.0);
	glVertex2f(10.0, 0.0);
	//x-axis tick marks
	for (x = -10.0; x <= 10.0; x += 1.0) {
		glVertex2f(x, 0.0);
		glVertex2f(x, 0.5);
	};
	//y-axis
	glVertex2f(0.0, -50.0);
	glVertex2f(0.0, 50.0);
	//y-axis tick marks
	for (y = -50.0; y <= 50.0; y += 1.0) {
		glVertex2f(-0.05, y);
		glVertex2f(0.05, y);
	};
	glEnd();
	//function drawing below
	//First function g(x)
	glColor3f(1.0, 0.0, 0.0);					//Set Color to Red
	glBegin(GL_LINE_STRIP);
	for (x = -10.0; x <= 10.0; x += xdel) {
		y = x*x*x + x*x - (20.0*x);
		glVertex2f(x, y);
	};
	glEnd();
	//Second function h(x)
	glColor3f(0.0, 0.0, 1.0);					//Set Color to Blue
	glBegin(GL_LINE_STRIP);
	for (x = -10.0; x <= 10.0; x += xdel) {
		y = 10 * cos(x) + 3;
		glVertex2f(x, y);
	};
	glEnd();
	//Third Function, f(x)
	glColor3f(1.0, 0.0, 1.0);
	glBegin(GL_LINE_STRIP);						//Set Color to Pink
	for (x = -10.0; x <= 10.0; x += xdel) {
		y = x*x*x + x*x - (20.0*x);
		z = 10 * cos(x) + 3;
		glVertex2f(x, y-z);
	};
	glEnd();
	glFlush();
	return;
};