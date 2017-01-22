package Solaris;

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

public class Saturn extends JFrame implements GLEventListener, KeyListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private float object_angle = 0.0f;
	
	private float zoomFactor = 1f;

	private float eyeX = 0;
	private float eyeY = 0;
	private float eyeZ = 0;

	private float POV_orientation = 0;
	private float POV_speed = 1f;
	private float POV_rotation_speed = 10f;

	private float lx = 0;
	private float lz = -1;

	private GLU glu = new GLU();

	
	
	private int saturnID;
	private int saturnRingID;
	
	public Saturn(int width, int height)
	{
		super("Saturn");
		GLProfile profil = GLProfile.get(GLProfile.GL2);
		GLCapabilities capabilities = new GLCapabilities(profil);

		GLCanvas canvas = new GLCanvas(capabilities);
		canvas.addGLEventListener(this);
		canvas.addKeyListener(this);
		canvas.setSize(width, height);

		this.getContentPane().add(canvas);

		this.setSize(this.getContentPane().getPreferredSize());
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(false);
		canvas.requestFocusInWindow();

		// Creation et instanciation du FPSAnimator
		final FPSAnimator animator = new FPSAnimator(canvas, 25, true);
		animator.start();
	}

	@Override
	public void display(GLAutoDrawable drawable)
	{
		object_angle += 1.0f;
		// Recuperons notre objet OpenGL
		GL2 gl = drawable.getGL().getGL2();
		// On efface le buffer couleur (ce qu'on affiche) et le buffer de
		// profondeur
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
		glu.gluPerspective(45.0f, 1, 1.0, 500.0);
		glu.gluLookAt(eyeX, eyeY, eyeZ, eyeX + lx, 0.0f, eyeZ + lz, 0, 1, 0);
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();

		// SATURN
		gl.glTranslatef(0.0f, 0.0f, -zoomFactor*100.0f);
		gl.glRotatef(-45.0f, 1.0f, 0.0f, 0.0f);//ROTATION POUR METTRE LE SOLEIL "DROIT"
		gl.glRotatef(object_angle, 0, 1, 1); // ROTATION SUR SOI MEME
		gl.glCallList(saturnID);
		
		//SATURN RINGS
		gl.glLoadIdentity();
		gl.glTranslatef(0.0f, 0.0f, -zoomFactor*100.0f);
		gl.glRotatef(-45.0f, 1.0f, 0.0f, 0.0f);//ROTATION POUR METTRE LE SOLEIL "DROIT"
		gl.glRotatef(object_angle, 0, 1, 1); // ROTATION SUR SOI MEME
		gl.glCallList(saturnRingID);

		gl.glFlush();

		gl.glLoadIdentity();
		float[] specularColor =
		{ 1.0f, 1.0f, 1.0f, 0.0f };
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPECULAR, specularColor, 0);

	}

	@Override
	public void dispose(GLAutoDrawable arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void init(GLAutoDrawable drawable)
	{
		// Recuperons notre objet OpenGL
		GL2 gl = drawable.getGL().getGL2();
		// Fixons la couleur par defaut de glClear
		gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		gl.glEnable(GL2.GL_LIGHTING);
		gl.glEnable(GL2.GL_LIGHT0);
		gl.glEnable(GL2.GL_RESCALE_NORMAL);
		gl.glEnable(GL2.GL_DEPTH_TEST);
		gl.glEnable(GL2.GL_TEXTURE_2D);
		gl.glDepthFunc(GL2.GL_LEQUAL);
		gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);

		
		saturnID = planet(gl,"SaturnMap.jpg",zoomFactor*2.1f); // Saturn
		saturnRingID = ring(gl,"SaturnRing.png",zoomFactor*2.1f*2);//
		
		
	}

	private int planet(GL2 gl, String texture, float size)
	{
		try
		{
			Texture tex = TextureIO.newTexture(new File("data/"+texture), true);
			GLUquadric planet = glu.gluNewQuadric();
			glu.gluQuadricDrawStyle(planet, GLU.GLU_FILL);
			glu.gluQuadricTexture(planet, true);
			glu.gluQuadricNormals(planet, GLU.GLU_SMOOTH);

			int planetID = gl.glGenLists(1);
			gl.glNewList(planetID, GL2.GL_COMPILE);

			tex.bind(gl);
			glu.gluSphere(planet, size, 50, 50);

			gl.glEndList();
			glu.gluDeleteQuadric(planet);
			return planetID;
		}
		catch (IOException e)
		{
			javax.swing.JOptionPane.showMessageDialog(null, e);
			return 0;
		}
	}
	private int ring(GL2 gl, String texture, float size)
	{
		try
		{
			Texture tex = TextureIO.newTexture(new File("data/"+texture), true);
			GLUquadric planet = glu.gluNewQuadric();
			glu.gluQuadricDrawStyle(planet, GLU.GLU_FILL);
			glu.gluQuadricTexture(planet, true);
			glu.gluQuadricNormals(planet, GLU.GLU_SMOOTH);
			glu.gluQuadricOrientation(planet, GLU.GLU_INSIDE);

			int planetID = gl.glGenLists(1);
			gl.glNewList(planetID, GL2.GL_COMPILE);

			tex.bind(gl);
			glu.gluDisk(planet, 0, size, 50, 1000);

			gl.glEndList();
			glu.gluDeleteQuadric(planet);
			return planetID;
		}
		catch (IOException e)
		{
			javax.swing.JOptionPane.showMessageDialog(null, e);
			return 0;
		}
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height)
	{
		final GL2 gl = drawable.getGL().getGL2();
		if (height <= 0)
			height = 1;
		final float h = (float) width / (float) height;
		gl.glViewport(0, 0, width, height);

	}

	@Override
	public void keyTyped(KeyEvent e)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e)
	{

		switch (e.getKeyCode())
		{
			case KeyEvent.VK_LEFT:
				POV_orientation -= POV_rotation_speed;
				lx = (float) Math.sin(Math.toRadians(POV_orientation));
				lz = (float) -Math.cos(Math.toRadians(POV_orientation));
				break;
			case KeyEvent.VK_RIGHT:
				POV_orientation += POV_rotation_speed;
				lx = (float) Math.sin(Math.toRadians(POV_orientation));
				lz = (float) -Math.cos(Math.toRadians(POV_orientation));
				break;
			case KeyEvent.VK_UP:
				eyeX += lx * POV_speed;
				eyeZ += lz * POV_speed;
				break;
			case KeyEvent.VK_DOWN:
				eyeX -= lx * POV_speed;
				eyeZ -= lz * POV_speed;
				break;
			case KeyEvent.VK_BACK_SPACE:
				eyeX = 0;
				eyeY = 0;
				eyeZ = 0;
				lx = 0;
				lz = -1;
				break;
			default:
				break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		// TODO Auto-generated method stub

	}

}
