package Transformations;

import javax.swing.JFrame;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;

public class Translations extends JFrame implements GLEventListener
{
	private float X = 0.0f;
	private float Y = 0.0f;
	private boolean directionX = false;
	private boolean directionY = false;

	private float r;
	private float v;
	private float b;
	private float r2;
	private float v2;
	private float b2;
	private float r3;
	private float v3;
	private float b3;
	
	
	public Translations(int width, int height){
		super( "Translations" ) ;
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
		
		//Cr�ation et instanciation du FPSAnimator

		
		getRandomColors();
		final FPSAnimator animator = new FPSAnimator(canvas, 20, true);
		animator.start() ;
	}
	
	@Override
	public void display(GLAutoDrawable drawable)
	{
		//R�cup�rons notre obje t OpenGL
		GL2 gl = drawable.getGL().getGL2();
		//On efface le buffer couleur (ce qu�on affiche) et le buffer de profondeur
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT) ;

		gl.glBegin(GL2.GL_TRIANGLES); // Indique que nous allons dessiner des triangles

		gl.glColor3f(r,v,b);
		gl.glVertex3f(-1f, 0.5f, 0.0f ); // Premier sommet
		
		gl.glColor3f(r2,v2,b2);
		gl.glVertex3f(-1f, -0.5f, 0.0f); // Deuxi�me sommet
		

		gl.glColor3f(r3,v3,b3);
		gl.glVertex3f(0f, -0.5f, 0.0f); // Troisi�me sommet
		gl.glEnd() ; // Indique que nous avons fini de dessiner des triangles
		
		float offsetX = 0.05f;
		
		if(X < 1.0f && !directionX){
			gl.glTranslatef(offsetX, 0.0f, 0.0f);
			X += offsetX;
		}
		
		if(X > 0.0f && directionX){
			gl.glTranslatef(-offsetX, 0.0f, 0.0f);
			X += -offsetX;
		}
		
		if (X >= 1.0f) {
			directionX = true;
			getRandomColors();
			
		}
		if(X <= 0.0f) {
			directionX = false;
			getRandomColors();
		}
		
		float offsetY = 0.05f;
		
		if(Y < 0.5f && !directionY){
			gl.glTranslatef(0.0f, offsetY, 0.0f);
			Y += offsetY;
		}
		
		if( Y > -0.5f && directionY){
			gl.glTranslatef(0.0f, -offsetY, 0.0f);
			Y += -offsetY;
		}
		
		if (Y >= 0.5f) {
			directionY = true;
			getRandomColors();
		}
		if(Y <= -0.5f) {
			directionY = false;
			getRandomColors();
		}
		
	}
	
	private void getRandomColors(){
		this.r = (float)Math.random();
		this.v = (float)Math.random();
		this.b = (float)Math.random();
		
		this.r2 = (float)Math.random();
		this.v2 = (float)Math.random();
		this.b2 = (float)Math.random();
		
		this.r3 = (float)Math.random();
		this.v3 = (float)Math.random();
		this.b3 = (float)Math.random();
	}

	@Override
	public void dispose(GLAutoDrawable drawable)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void init(GLAutoDrawable drawable)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height)
	{
		// TODO Auto-generated method stub

	}

}
