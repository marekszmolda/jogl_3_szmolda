package org.yourorghere;

import com.sun.opengl.util.Animator;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.media.opengl.GL;
import static javax.media.opengl.GL.GL_FRONT;
import static javax.media.opengl.GL.GL_SHININESS;
import static javax.media.opengl.GL.GL_SPECULAR;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

/**
 * Blajda.java <BR>
 * author: Brian Paul (converted to Java by Ron Cemer and Sven Goethel)
 * <P>
 *
 * This version is equal to Brian Paul's version 1.2 1999/10/21
 */
public class Marekszmolda implements GLEventListener {

    //statyczne pola okre?aj?e rotacj? wok? osi X i Y
    private static float xrot = 0.0f, yrot = 0.0f;

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
        //Obs?ga klawiszy strza?k
        frame.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    xrot -= 1.0f;
                }

                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    xrot += 1.0f;
                }

                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    yrot += 1.0f;
                }

                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    yrot -= 1.0f;
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
        gl.glShadeModel(GL.GL_SMOOTH);
        //wy?czenie wewn?rzych stron prymityw?
        gl.glEnable(GL.GL_CULL_FACE);// try setting this to GL_FLAT and see what happens.

        float ambientLight[] = {0.3f, 0.3f, 0.3f, 1.0f};//swiat? otaczaj?e
        float diffuseLight[] = {0.7f, 0.7f, 0.7f, 1.0f};//?iat? rozproszone
        float specular[] = {1.0f, 1.0f, 1.0f, 1.0f}; //?iat? odbite
        float lightPos[] = {0.0f, 150.0f, 150.0f, 1.0f};//pozycja ?iat?
//(czwarty parametr okre?a odleg?? ???:
//0.0f-niesko?zona; 1.0f-okre?ona przez pozosta? parametry)
        gl.glEnable(GL.GL_LIGHTING); //uaktywnienie o?ietlenia
//ustawienie parametr? ??? ?iat? nr. 0
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_AMBIENT, ambientLight, 0); //swiat? otaczaj?e
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_DIFFUSE, diffuseLight, 0); //?iat? rozproszone
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_SPECULAR, specular, 0); //?iat? odbite
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, lightPos, 0); //pozycja ?iat?
        gl.glEnable(GL.GL_LIGHT0); //uaktywnienie ??? ?iat? nr. 0
        gl.glEnable(GL.GL_COLOR_MATERIAL); //uaktywnienie ?edzenia kolor?
//kolory b?? ustalane za pomoc? glColor
        gl.glColorMaterial(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE);
//Ustawienie jasno?i i odblaskowo?i obiekt?
        float specref[] = {1.0f, 1.0f, 1.0f, 1.0f}; //parametry odblaskowo?i
        gl.glMaterialfv(GL_FRONT, GL_SPECULAR, specref,0);
        gl.glMateriali(GL_FRONT, GL_SHININESS, 128);
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

    // public void drawTriangleFan(float xsr, float ysr, float r, GL gl) {
    //   float kat;
    //  gl.glBegin(GL.GL_TRIANGLE_FAN);
    //  gl.glVertex3f(xsr, ysr, -6.0f);
    //for (kat = 0.0f; kat < (2.0f * Math.PI);
    //      kat += (Math.PI / 32.0f)) {
    //float x = r * (float) Math.sin(kat) + xsr;
    //float y = r * (float) Math.cos(kat) + ysr;
    //gl.glVertex3f(x, y, -6.0f);
    //}
    //}
    public void display(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();

        // Clear the drawing area
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        // Reset the current matrix to the "identity"
        gl.glLoadIdentity();
        gl.glTranslatef(0.0f, 0.0f, -6.0f); //przesuni?ie o 6 jednostek
        gl.glRotatef(xrot, 1.0f, 0.0f, 0.0f); //rotacja wok? osi X
        gl.glRotatef(yrot, 0.0f, 1.0f, 0.0f); //rotacja wok? osi Y

        //gl.glBegin(GL.GL_TRIANGLES);
        //pierwszy bok          
      //  gl.glColor3f(1.0f, 0.0f, 0.0f);
      // gl.glVertex3f(-1.0f, -1.0f, 1.0f);
       // gl.glVertex3f(1.0f, -1.0f, 1.0f);
       // gl.glVertex3f(0.0f, 1.0f, 0.0f);
//drugi bok
       // gl.glColor3f(0.0f, 1.0f, 0.0f);
       // gl.glVertex3f(0.0f, 1.0f, 0.0f);
       // gl.glVertex3f(1.0f, -1.0f, -1.0f);
       // gl.glVertex3f(-1.0f, -1.0f, -1.0f);
//trzeci bok
       // gl.glColor3f(1.0f, 1.0f, 0.0f);
       // gl.glVertex3f(0.0f, 1.0f, 0.0f);
       // gl.glVertex3f(1.0f, -1.0f, 1.0f);
       // gl.glVertex3f(1.0f, -1.0f, -1.0f);

//czwarty bok
       // gl.glColor3f(1.0f, 2.0f, 0.0f);
        //gl.glVertex3f(0.0f, 1.0f, 0.0f);
       // gl.glVertex3f(-1.0f, -1.0f, -1.0f);
       // gl.glVertex3f(-1.0f, -1.0f, 1.0f);
      //  gl.glEnd();

        gl.glBegin(GL.GL_QUADS);
//?iana dolna
        gl.glColor3f(1.0f, 0.0f, 1.0f);
        gl.glVertex3f(-1.0f, -1.0f, 1.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glVertex3f(1.0f, -1.0f, 1.0f);

//?iana przednia
        gl.glColor3f(1.0f, 0.0f, 0.0f);
       gl.glVertex3f(-1.0f, -1.0f, 1.0f);
       gl.glVertex3f(1.0f, -1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, 1.0f);
         gl.glVertex3f(-1.0f, 1.0f, 1.0f);
//sciana tylnia
         gl.glColor3f(0.0f, 1.0f, 0.0f);
         gl.glVertex3f(-1.0f, 1.0f, -1.0f);
         gl.glVertex3f(1.0f, 1.0f, -1.0f);
         gl.glVertex3f(1.0f, -1.0f, -1.0f);
         gl.glVertex3f(-1.0f, -1.0f, -1.0f);
//?iana lewa
         gl.glColor3f(0.0f, 0.0f, 1.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glVertex3f(-1.0f, -1.0f, 1.0f);
         gl.glVertex3f(-1.0f, 1.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
//?iana prawa
         gl.glColor3f(1.0f, 1.0f, 0.0f);
         gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glVertex3f(1.0f, 1.0f, 1.0f);
        gl.glVertex3f(1.0f, -1.0f, 1.0f);
         gl.glVertex3f(1.0f, -1.0f, -1.0f);
//?iana dolna
         gl.glColor3f(1.0f, 0.0f, 1.0f);
        gl.glVertex3f(-1.0f, -1.0f, 1.0f);
         gl.glVertex3f(-1.0f, -1.0f, -1.0f);
         gl.glVertex3f(1.0f, -1.0f, -1.0f);
         gl.glVertex3f(1.0f, -1.0f, 1.0f);
// sciana gorna
           gl.glColor3f(1.0f, 1.0f, 1.0f);
         gl.glVertex3f(-1.0f, 1.0f, 1.0f);        
        gl.glVertex3f(1.0f, 1.0f, 1.0f);          
         gl.glVertex3f(1.0f, 1.0f, -1.0f); 
         gl.glVertex3f(-1.0f, 1.0f, -1.0f); 
        gl.glEnd();

        // float x, y, kat;
        // gl.glBegin(GL.GL_TRIANGLE_FAN);
        // gl.glVertex3f(0.0f, 0.0f, -6.0f); //?rodek
        // for (kat = 0.0f; kat < (2.0f * Math.PI);
        //   kat += (Math.PI / 32.0f)) {
        //  x = 2.5f * (float) Math.sin(kat);
        //  y = 2.5f * (float) Math.cos(kat);
        //   gl.glVertex3f(x, y, -8.0f); //kolejne punkty
        // gl.glEnd();
        // drawTriangleFan((float) 0.0, (float) 0.0, (float) 0.3, gl);
        // gl.glEnd();
        gl.glFlush();

    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }
}