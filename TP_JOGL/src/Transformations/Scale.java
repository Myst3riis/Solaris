package Transformations;

import javax.swing.JFrame;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;

public class Scale extends JFrame implements GLEventListener
{
	private float scale = 0.95f;
	private float img = 1.0f;
	
	private boolean smaller = true;

	private float r;
	private float v;
	private float b;
	private float r2;
	private float v2;
	private float b2;
	private float r3;
	private float v3;
	private float b3;
	
	
	public Scale(int width, int height){
		super( "Scaling" ) ;
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
		
		getRandomColors();
		
		//Création et instanciation du FPSAnimator
		final FPSAnimator animator = new FPSAnimator(canvas, 100, true);
		animator.start() ;
	}
	
	@Override
	public void display(GLAutoDrawable drawable)
	{
		//Récupérons notre obje t OpenGL
		GL2 gl = drawable.getGL().getGL2();
		//On efface le buffer couleur (ce qu’on affiche) et le buffer de profondeur
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT) ;

		gl.glBegin(GL2.GL_TRIANGLES); // Indique que nous allons dessiner des triangles

		gl.glColor3f(r,v,b);
		gl.glVertex3f(-1f, 1f, 0.0f ); // Premier sommet
		
		gl.glColor3f(r2,v2,b2);
		gl.glVertex3f(-1f, -1f, 0.0f); // Deuxième sommet
		

		gl.glColor3f(r3,v3,b3);
		gl.glVertex3f(1f, -1f, 0.0f); // Troisième sommet
		gl.glEnd() ; // Indique que nous avons fini de dessiner des triangles
			
		
		if ((img < 1.0f && !smaller) || (scale > 0.25f && smaller)){
			gl.glScalef(scale,scale,0);
			img = img*scale;
		}
		if (img >= 1.0f) {
			scale = 0.95f;
			smaller = true;
		}
		
		if (img <= 0.25f){
			scale = 1.05f;
			smaller = false;
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
