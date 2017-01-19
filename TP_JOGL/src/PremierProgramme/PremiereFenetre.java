package PremierProgramme;

import javax.swing.JFrame;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;

public class PremiereFenetre extends JFrame implements GLEventListener
{

	public PremiereFenetre (int width, int height){
	super( "Ma première fenêtre OpenGL ! ! ! " ) ;
	GLProfile profil = GLProfile.get(GLProfile.GL2);
	GLCapabilities capabilities = new GLCapabilities(profil);
	
	GLCanvas canvas = new GLCanvas(capabilities);
	canvas.addGLEventListener(this);
	canvas.setSize(width, height);
	
	this.getContentPane().add(canvas);
	
	this.setSize(width, height) ;
	this.setLocationRelativeTo(null) ;
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE) ;
	this.setVisible(true);
	
	//Création et instanciation du FPSAnimator
	final FPSAnimator animator = new FPSAnimator(canvas, 5, true);
	animator.start() ;
	}
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void display (GLAutoDrawable drawable){
	//Récupérons notre objet OpenGL
	GL2 gl = drawable.getGL().getGL2();
	//On efface le buffer couleur (ce qu’on affiche) et le buffer de profondeur
	gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT) ;
	
	/* * *
	* Écrire notre code de dessin  ici
	*/
	/*
	 * EXEMPLE 1
	gl.glBegin(GL2.GL_TRIANGLES); // Indique que nous allons dessiner des triangles
	gl.glVertex3f(0f, 1f, 0.0f ); // Premier sommet
	gl.glVertex3f(-1f, -1f, 0.0f); // Deuxième sommet
	gl.glVertex3f(1f, -1f, 0.0f); // Troisième sommet
	gl.glEnd() ; // Indique que nous avons fini de dessiner des triangles
	*/
	/*
	 * EXEMPLE 2
	gl.glBegin(GL2.GL_TRIANGLES);
	//Premier Triangle
	gl.glVertex3f(0f, 1f, 0.0f);
	gl.glVertex3f(0f, 0f, 0.0f);
	gl.glVertex3f(-1f, 0f, 0.0f);
	//Deuxieme Triangle
	gl.glVertex3f(0.5f, 0f, 0.0f);
	gl.glVertex3f(0f, -1f, 0.0f);
	gl.glVertex3f(1f, -1f, 0.0f);
	gl.glEnd();
	*/
	/*
	 * EXEMPLE 3
	gl.glLineWidth(10f);
	gl.glBegin(GL2.GL_LINE_LOOP);
	gl.glVertex3f(-0.75f, 0.75f, 0.0f);
	gl.glVertex3f(0.75f, 0.75f, 0.0f);
	gl.glVertex3f(0.75f, -0.75f, 0.0f);
	gl.glVertex3f(-0.75f, -0.75f, 0.0f);
	gl.glEnd();
	*/
	/* 
	 * EXEMPLE 4
	gl.glBegin(GL2.GL_TRIANGLE_FAN);
	gl.glVertex3f(-0.33f, 0.98f, 0.0f);
	gl.glVertex3f(0.33f, 0.98f, 0.0f);
	gl.glVertex3f(0.98f, 0.33f, 0.0f);
	gl.glVertex3f(0.98f, -0.33f, 0.0f);
	gl.glVertex3f(0.33f, -0.98f, 0.0f);
	gl.glVertex3f(-0.33f, -0.98f, 0.0f);
	gl.glVertex3f(-0.98f, -0.33f, 0.0f);
	gl.glVertex3f(-0.98f, 0.33f, 0.0f);
	gl.glEnd();
	*/
	/*
	 * EXEMPLE 5
	gl.glBegin(GL2.GL_TRIANGLE_FAN);
	gl.glColor3f(255f,0f,0f);
	gl.glVertex3f(0.0f, 0.0f, 0.0f);
	gl.glColor3f(0f,0f,255f);
	gl.glVertex3f(-0.33f, 0.98f, 0.0f);
	gl.glVertex3f(0.33f, 0.98f, 0.0f);
	gl.glVertex3f(0.98f, 0.33f, 0.0f);
	gl.glVertex3f(0.98f, -0.33f, 0.0f);
	gl.glVertex3f(0.33f, -0.98f, 0.0f);
	gl.glVertex3f(-0.33f, -0.98f, 0.0f);
	gl.glVertex3f(-0.98f, -0.33f, 0.0f);
	gl.glVertex3f(-0.98f, 0.33f, 0.0f);
	gl.glVertex3f(-0.33f, 0.98f, 0.0f);
	gl.glEnd();
	*/

	float r = (float)Math.random();
	float v = (float)Math.random();
	float b = (float)Math.random();
	gl.glBegin(GL2.GL_TRIANGLE_FAN);
	gl.glColor3f(r,v,b);
	gl.glVertex3f(0.0f, 0.0f, 0.0f);
	
	float r1 = (float)Math.random();
	float v1 = (float)Math.random();
	float b1 = (float)Math.random();
	gl.glColor3f(r1,v1,b1);
	gl.glVertex3f(-0.33f, 0.98f, 0.0f);

	r = (float)Math.random();
	v = (float)Math.random();
	b = (float)Math.random();
	gl.glColor3f(r,v,b);
	gl.glVertex3f(0.33f, 0.98f, 0.0f);

	r = (float)Math.random();
	v = (float)Math.random();
	b = (float)Math.random();
	gl.glColor3f(r,v,b);
	gl.glVertex3f(0.98f, 0.33f, 0.0f);

	r = (float)Math.random();
	v = (float)Math.random();
	b = (float)Math.random();
	gl.glColor3f(r,v,b);
	gl.glVertex3f(0.98f, -0.33f, 0.0f);

	r = (float)Math.random();
	v = (float)Math.random();
	b = (float)Math.random();
	gl.glColor3f(r,v,b);
	gl.glVertex3f(0.33f, -0.98f, 0.0f);

	r = (float)Math.random();
	v = (float)Math.random();
	b = (float)Math.random();
	gl.glColor3f(r,v,b);
	gl.glVertex3f(-0.33f, -0.98f, 0.0f);

	r = (float)Math.random();
	v = (float)Math.random();
	b = (float)Math.random();
	gl.glColor3f(r,v,b);
	gl.glVertex3f(-0.98f, -0.33f, 0.0f);

	r = (float)Math.random();
	v = (float)Math.random();
	b = (float)Math.random();
	gl.glColor3f(r,v,b);
	gl.glVertex3f(-0.98f, 0.33f, 0.0f);

	gl.glColor3f(r1,v1,b1);
	gl.glVertex3f(-0.33f, 0.98f, 0.0f);
	
	gl.glEnd();
	
	//Pour forcer l’éxécution des différentes instructions OpenGL
	gl.glFlush();
	}
	
	
	@Override
	public void dispose(GLAutoDrawable drawable)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void init(GLAutoDrawable drawable){
		//Récupérons notre objet OpenGL
		GL2 gl = drawable.getGL().getGL2() ;
		//Fixons la couleur par défaut deglClear
		gl.glClearColor(0.392f, 0.929f, 0.584f, 1.0f) ;
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height)
	{
		// TODO Auto-generated method stub

	}

}
