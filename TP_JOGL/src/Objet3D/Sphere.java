package Objet3D;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

public class Sphere extends JFrame implements GLEventListener
{

	GLU glu = new GLU();
	private float X = 0.0f;
	private float Y = 0.0f;
	private float Z = 0.0f;
	private float angle = 0.0f;
	private float angleX = 0.0f;
	private float angleY = 0.0f;
	private float angleZ = 0.0f;
	private int tex[] = new int[6];
	private Texture earth;
	private int sphereID;
	private String texture = "EarthMap.jpg";
	
	
	public Sphere(int width, int height){
		super( "Sphere OpenGL ! ! ! " ) ;
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
		final FPSAnimator animator = new FPSAnimator(canvas, 60, true);
		animator.start() ;
		
		
		this.addKeyListener(new KeyListener(){
			@Override
			public void keyTyped(KeyEvent e)
			{
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyPressed(KeyEvent e)
			{
				switch(e.getKeyCode()){
					case KeyEvent.VK_UP:
						angleX -= 1f;
						break;
					case KeyEvent.VK_DOWN:
						angleX += 1f;
						break;
					case KeyEvent.VK_LEFT:
						angleZ -= 1f;
						break;
					case KeyEvent.VK_RIGHT:
						angleZ += 1f;
						break;	
				}
			}

			@Override
			public void keyReleased(KeyEvent e)
			{
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	public Sphere(int width, int height, String texture){
		super( "Sphere OpenGL ! ! ! " ) ;
		GLProfile profil = GLProfile.get(GLProfile.GL2);
		GLCapabilities capabilities = new GLCapabilities(profil);
		
		GLCanvas canvas = new GLCanvas(capabilities);
		canvas.addGLEventListener(this);
		canvas.setSize(width, height);
		
		this.getContentPane().add(canvas);
		
		this.setSize(width, height) ;
		this.texture = texture;
		this.setLocationRelativeTo(null) ;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE) ;
		this.setVisible(true);
		
		
		//Création et instanciation du FPSAnimator
		final FPSAnimator animator = new FPSAnimator(canvas, 60, true);
		animator.start() ;
		
		
		this.addKeyListener(new KeyListener(){
			@Override
			public void keyTyped(KeyEvent e)
			{
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyPressed(KeyEvent e)
			{
				switch(e.getKeyCode()){
					case KeyEvent.VK_UP:
						angleX -= 1f;
						break;
					case KeyEvent.VK_DOWN:
						angleX += 1f;
						break;
					case KeyEvent.VK_LEFT:
						angleZ -= 1f;
						break;
					case KeyEvent.VK_RIGHT:
						angleZ += 1f;
						break;	
				}
			}

			@Override
			public void keyReleased(KeyEvent e)
			{
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	@Override
	public void display(GLAutoDrawable drawable)
	{
		//Récupérons notre objet OpenGL
		GL2 gl = drawable.getGL().getGL2();
		//On efface le buffer couleur (ce qu’on affiche) et le buffer de profondeur
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT) ;
		gl.glLoadIdentity();
		gl.glTranslatef(0f, 0f, -6f);
		gl.glRotatef(-90f, 1f, 0f, 0f);
		gl.glRotatef(angleX, 1f, 0f, 0f);
		gl.glRotatef(angleY, 0f, 1f, 0f);
		gl.glRotatef(angleZ, 0f, 0f, 1f);
		//angle += 0.5f;
		//gl.glRotatef(angle, 1f, 1f, 1f);
		
		float[] specularColor = {1.0f, 1.0f, 1.0f, 1.0f};
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPECULAR, specularColor, 0);

		gl.glCallList(sphereID);	
		
		
		GLUquadric sphere = glu.gluNewQuadric();
		glu.gluQuadricDrawStyle(sphere, GLU.GLU_FILL);
		glu.gluQuadricNormals(sphere, GLU.GLU_SMOOTH);
		glu.gluSphere(sphere, 1.5f, 50, 50);
		glu.gluDeleteQuadric(sphere);
		
		
		//Pour forcer l’éxécution des différentes instructions OpenGL
		gl.glFlush();
	}
	
	

	@Override
	public void dispose(GLAutoDrawable arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void init(GLAutoDrawable drawable)
	{
		GL2 gl = drawable.getGL().getGL2();
		gl.glClearColor(0, 0, 0, 0);
		gl.glEnable(GL2.GL_LIGHTING);
		gl.glEnable(GL2.GL_LIGHT0);
		gl.glEnable(GL2.GL_RESCALE_NORMAL);
		gl.glEnable(GL2.GL_DEPTH_TEST);
		gl.glEnable(GL2.GL_TEXTURE_2D);
		try {
				earth = TextureIO.newTexture (new File("data/"+texture), true);
		}catch (IOException e) {
				javax.swing.JOptionPane.showMessageDialog(null, e);
		}
		GLUquadric sphere = glu.gluNewQuadric();
		glu.gluQuadricDrawStyle(sphere, GLU.GLU_FILL);
		glu.gluQuadricTexture(sphere, true);
		glu.gluQuadricNormals(sphere, GLU.GLU_SMOOTH);
		sphereID = gl.glGenLists(1);
		gl.glNewList(sphereID, GL2.GL_COMPILE);
		earth.enable(gl);
		earth.bind(gl);
		glu.gluSphere(sphere, 1.5f, 50, 50);
		earth.disable(gl);
		gl.glEndList() ;
		glu.gluDeleteQuadric(sphere);
	}

	@Override
	public void reshape (GLAutoDrawable drawable , int x , int y , int width , int height){
		final GL2 gl = drawable.getGL().getGL2();
		if (height <= 0) height = 1;
		final float h = (float) width/(float) height;
		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL2.GL_PROJECTION );
		gl.glLoadIdentity() ;
		glu.gluPerspective(45.0f, h, 1.0, 20.0);
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();
		gl.glEnable(GL2.GL_DEPTH_TEST);
	}
}
