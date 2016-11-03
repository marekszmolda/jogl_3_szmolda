package org.yourorghere;





import com.sun.opengl.util.Animator;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;
import java.util.Scanner;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;


public class Marekszmolda implements GLEventListener {

    //statyczne pola okre?laj?ce rotacj? wokó? osi X i Y
    private static float xrot = 0.0f, yrot = 0.0f;

    public static float ambientLight[] = {0.3f, 0.3f, 0.3f, 1.0f};//swiat?o otaczajšce
    public static float diffuseLight[] = {0.7f, 0.7f, 0.7f, 1.0f};//?wiat?o rozproszone
    public static float specular[] = {1.0f, 1.0f, 1.0f, 1.0f}; //?wiat?o odbite
    public static float lightPos[] = {0.0f, 150.0f, 150.0f, 1.0f};//pozycja ?wiat?a

    public static void main(String[] args) {
        Frame frame = new Frame("Simple JOGL Application");
        GLCanvas canvas = new GLCanvas();

        canvas.addGLEventListener(new Marekszmolda());
        frame.add(canvas);
        frame.setSize(640, 480);
        final Animator animator = new Animator(canvas);
        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                // Run this on another thread than the AWT event queue to
                // make sure the call to Animator.stop() completes before
                // exiting
                new Thread(new Runnable() {

                    public void run() {
                        animator.stop();
                        System.exit(0);
                    }
                }).start();
            }
        });

        //Obs?uga klawiszy strza?ek
        frame.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    xrot -= 3.0f;
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    xrot += 3.0f;
                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    yrot += 3.0f;
                }
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    yrot -= 3.0f;
                }
                if (e.getKeyChar() == 'q') {
                    ambientLight = new float[]{ambientLight[0] - 0.1f, ambientLight[0] - 0.1f, ambientLight[0] - 0.1f, 1.0f};
                }
                if (e.getKeyChar() == 'w') {
                    ambientLight = new float[]{ambientLight[0] + 0.1f, ambientLight[0] + 0.1f, ambientLight[0] + 0.1f, 1.0f};
                }
                if (e.getKeyChar() == 'a') {
                    diffuseLight = new float[]{diffuseLight[0] - 0.1f, diffuseLight[0] - 0.1f, diffuseLight[0] - 0.1f, diffuseLight[0] - 0.1f};
                }
                if (e.getKeyChar() == 's') {
                    diffuseLight = new float[]{diffuseLight[0] + 0.1f, diffuseLight[0] + 0.1f, diffuseLight[0] + 0.1f, diffuseLight[0] + 0.1f};
                }
                if (e.getKeyChar() == 'z') {
                    specular = new float[]{specular[0] - 0.1f, specular[0] - 0.1f, specular[0] - 0.1f, specular[0] - 0.1f};
                }
                if (e.getKeyChar() == 'x') {
                    specular = new float[]{specular[0] + 0.1f, specular[0] + 0.1f, specular[0] + 0.1f, specular[0] + 0.1f};
                }
                if (e.getKeyChar() == 'k') {
                    lightPos = new float[]{lightPos[0] - 0.1f, lightPos[0] - 0.1f, lightPos[0] - 0.1f, lightPos[0] - 0.1f};
                }
                if (e.getKeyChar() == 'l') {
                    lightPos = new float[]{lightPos[0] + 0.1f, lightPos[0] + 0.1f, lightPos[0] + 0.1f, lightPos[0] + 0.1f};
                }
            }

            public void keyReleased(KeyEvent e) {
            }

            public void keyTyped(KeyEvent e) {
            }

        });

        // Center frame
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        animator.start();
    }

    public void init(GLAutoDrawable drawable) {
        // Use debug pipeline
        // drawable.setGL(new DebugGL(drawable.getGL()));

        GL gl = drawable.getGL();
        System.err.println("INIT GL IS: " + gl.getClass().getName());

        // Enable VSync
        gl.setSwapInterval(1);

        // Setup the drawing area and shading mode
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glShadeModel(GL.GL_SMOOTH); // try setting this to GL_FLAT and see what happens.

        //wy??czenie wewn?trzych stron prymitywów
        gl.glEnable(GL.GL_CULL_FACE);

        //(czwarty parametr okre?la odleg?o?? ?ród?a:
        //0.0f-niesko?czona; 1.0f-okre?lona przez pozosta?e parametry)
        gl.glEnable(GL.GL_LIGHTING); //uaktywnienie o?wietlenia
        //ustawienie parametrów ?ród?a ?wiat?a nr. 0
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_AMBIENT, ambientLight, 0); //swiat?o otaczaj?ce
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_DIFFUSE, diffuseLight, 0); //?wiat?o rozproszone
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_SPECULAR, specular, 0); //?wiat?o odbite
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, lightPos, 0); //pozycja ?wiat?a
        gl.glEnable(GL.GL_LIGHT0); //uaktywnienie ?ród?a ?wiat?a nr. 0
        gl.glEnable(GL.GL_COLOR_MATERIAL); //uaktywnienie ?ledzenia kolorów
        //kolory b?d? ustalane za pomoc? glColor
        gl.glColorMaterial(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE);
        //Ustawienie jasno?ci i odblaskowo?ci obiektów
        float specref[] = {1.0f, 1.0f, 1.0f, 1.0f}; //parametry odblaskowo?ci
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, specref, 0);

        gl.glMateriali(GL.GL_FRONT, GL.GL_SHININESS, 128);

        gl.glEnable(GL.GL_DEPTH_TEST);
        // Setup the drawing area and shading mode
        gl.glShadeModel(GL.GL_SMOOTH); // try setting this to GL_FLAT and see what happens.
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL gl = drawable.getGL();
        GLU glu = new GLU();

        if (height <= 0) { // avoid a divide by zero error!

            height = 1;
        }
        final float h = (float) width / (float) height;
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(45.0f, h, 1.0, 20.0);
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    public void createShape(GL gl, float px, float py, float pz, float size, float odbicie) {
        float kat, x, y;
        gl.glBegin(GL.GL_TRIANGLE_FAN);
        gl.glVertex3f(px, py, pz); //?rodek
        if (odbicie == -1) {
            for (kat = 0.0f; kat > (-2.0f * Math.PI);
                    kat -= (Math.PI / 32.0f)) {
                x = size * (float) Math.sin(kat);
                y = size * (float) Math.cos(kat);
                gl.glVertex3f(x, py, y); //kolejne punkty
            }
        } else {
            for (kat = 0.0f; kat < (2.0f * Math.PI);
                    kat += (Math.PI / 32.0f)) {
                x = size * (float) Math.sin(kat);
                y = size * (float) Math.cos(kat);
                gl.glVertex3f(x, py, y); //kolejne punkty
            }
        }
        gl.glEnd();

    }

    public void createTriangle(GL gl, float x1, float y1, float x2, float y2, float x3, float y3, float z) {
        gl.glBegin(GL.GL_TRIANGLES);
        gl.glVertex3f(x1, y1, z);
        gl.glVertex3f(x2, y2, z);
        gl.glVertex3f(x3, y3, z);
        gl.glEnd();
    }

    public void createCone(GL gl, float px, float py, float pz, float size) {
        float kat, x, y;

        float xx, yy, katt;
        gl.glBegin(GL.GL_TRIANGLE_FAN);
        gl.glColor3f(1.0f, 0.0f, 0.0f);

        gl.glVertex3f(0.0f, 2.0f, 0.0f); //?rodek
        for (katt = 0.0f; katt < (2.0f * Math.PI);
                katt += (Math.PI / 32.0f)) {
            xx = 1.0f * (float) Math.sin(katt);
            yy = 1.0f * (float) Math.cos(katt);
            gl.glVertex3f(xx, -2.0f, yy); //kolejne punkty
        }
        gl.glEnd();

        gl.glBegin(GL.GL_TRIANGLE_FAN);
        gl.glVertex3f(px, py, pz); //?rodek
        for (kat = 0.0f; kat < (2.0f * Math.PI);
                kat += (Math.PI / 32.0f)) {
            x = size * (float) Math.sin(kat);
            y = size * (float) Math.cos(kat);
            gl.glVertex3f(x, -py, y); //kolejne punkty
        }
        gl.glEnd();
    }

    public void sierpin(GL gl, float x1, float y1, float x2, float y2, float x3, float y3, int depth) {
        float x12 = (x1 + x2) / 2.0f, y12 = (y1 + y2) / 2.0f,
                x13 = (x1 + x3) / 2.0f, y13 = (y1 + y3) / 2.0f,
                x23 = (x2 + x3) / 2.0f, y23 = (y2 + y3) / 2.0f;

        Random rd = new Random();
        float c1 = rd.nextFloat();
        float c2 = rd.nextFloat();
        float c3 = rd.nextFloat();
        if (depth == 1) {
            gl.glBegin(GL.GL_TRIANGLES);
            gl.glColor3f(1 / depth, 1 / depth, 1 / depth);
            gl.glVertex3f(x1, y1, 5.0f);
            gl.glVertex3f(x2, y2, 5.0f);
            gl.glVertex3f(x3, y3, 5.0f);
            gl.glEnd();
        } else {
            sierpin(gl, x1, y1, x12, y12, x13, y13, depth - 1);
        }
    }

    public void display(GLAutoDrawable drawable) {
//Tworzenie obiektu
        GL gl = drawable.getGL();
//Czyszczenie przestrzeni 3D przed utworzeniem kolejnej klatki
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        //Resetowanie macierzy transformacji
        gl.glLoadIdentity();

        gl.glTranslatef(0.0f, 0.0f, -6.0f); //przesuni?cie o 6 jednostek
        gl.glRotatef(xrot, 1.0f, 0.0f, 0.0f); //rotacja wokó? osi X
        gl.glRotatef(yrot, 0.0f, 1.0f, 0.0f); //rotacja wokó? osi Y
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_AMBIENT, ambientLight, 0); //swiat?o otaczaj?ce
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_DIFFUSE, diffuseLight, 0); //?wiat?o rozproszone
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_SPECULAR, specular, 0); //?wiat?o odbite

        gl.glBegin(GL.GL_QUADS);
        gl.glBegin(GL.GL_QUADS);
//?ciana przednia
        gl.glColor3f(1.0f, 1.0f, 1.0f);
        gl.glNormal3f(0.0f, 0.0f, 1.0f);
        gl.glVertex3f(-1.0f, -1.0f, 1.0f);
        gl.glVertex3f(1.0f, -1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, 1.0f);
//sciana tylnia
        gl.glColor3f(0.0f, 1.0f, 1.0f);
        gl.glNormal3f(0.0f, 0.0f, -1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
//?ciana lewa
        gl.glColor3f(1.0f, 1.0f, 0.0f);
        gl.glNormal3f(-1.0f, 0.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glVertex3f(-1.0f, -1.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
//?ciana prawa
        gl.glColor3f(1.0f, 0.0f, 1.0f);
        gl.glNormal3f(1.0f, 0.0f, 0.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glVertex3f(1.0f, 1.0f, 1.0f);
        gl.glVertex3f(1.0f, -1.0f, 1.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
//?ciana dolna
        gl.glColor3f(0.0f, 1.0f, 0.0f);
        gl.glNormal3f(0.0f, -1.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, 1.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glVertex3f(1.0f, -1.0f, 1.0f);
//?ciana górna
        gl.glColor3f(0.0f, 0.0f, 1.0f);
        gl.glNormal3f(0.0f, 1.0f, 0.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glVertex3f(-1.0f, 1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, 1.0f);
        gl.glEnd();

        //Wykonanie wszystkich operacji znajduj?cych si? w buforze
        gl.glFlush();
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }
}