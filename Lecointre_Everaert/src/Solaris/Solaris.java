package Solaris;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

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

import CSV.SolarSystem;
import CelestialObjects.CelestialObject;
import CelestialObjects.Planet;

public class Solaris extends JFrame implements GLEventListener, KeyListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private float object_angle = 0.0f;
	
	private float sizeFactor = 0.5f;
	private float distanceFactor = 0.0000001f;

	private float eyeX = 0;
	private float eyeY = 0;
	private float eyeZ = 0;

	private float POV_orientation = 0;
	private float POV_speed = 1f;
	private float POV_rotation_speed = 10f;

	private float lx = 0;
	private float lz = -1;

	private int moveSpeed = 1; 
	
	private GLU glu = new GLU();

	private int sunID;
	private int mercuryID;
	private int venusID;
	private int earthID;
	private int moonID;
	private int marsID;
	private int jupiterID;
	private int saturnID;
	private int saturnRingID;
	private int uranusID;
	private int uranusRingID;
	private int neptuneID;
	private int neptuneRingID;
	private List<Integer> asteroidIDs = new ArrayList<Integer>();
	private int nbAsteroids = 100;
	
	public Solaris(int width, int height)
	{
		super("Solar System");
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
		FPSAnimator animator = new FPSAnimator(canvas, 25, true);
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

		float distance = 200f;
		
		// SUN
		displaySun(gl, distance);
		
/*		
		displayPlanet(gl, distance, distanceFactor*57, 4.7f, mercuryID, false, 0);				// MERCURY
		displayPlanet(gl, distance, distanceFactor*108, 3.5f, venusID, false, 0);				// VENUS
		displayPlanet(gl, distance, distanceFactor*149, 2.9f, earthID, false, 0);				// EARTH
		displaySatellite(gl, distance, distanceFactor*149,distanceFactor*5f, 2.9f, moonID);		// Moon
		displayPlanet(gl, distance, distanceFactor*227, 2.4f, marsID, false, 0);				// MARS
		displayPlanet(gl, distance, distanceFactor*778, 1.3f, jupiterID, false, 0);				// JUPITER
		displayPlanet(gl, distance, distanceFactor*1429, 0.9f, saturnID, true, saturnRingID);	// SATURN
		displayPlanet(gl, distance, distanceFactor*2871, 0.6f, uranusID, true, uranusRingID);	// URANUS
		displayPlanet(gl, distance, distanceFactor*4498, 0.5f, neptuneID, true, neptuneRingID);	// NEPTUNE
*/
		
		//////////////////////////////////////////////////////////////////
		CelestialObjectAdapter coa = new CelestialObjectAdapter();
		
		
		SolarSystem ss = new SolarSystem();
		HashMap<String, CelestialObject> sunAndPlanets = ss.sunAndPlanets();
		
		Planet mercury = (Planet) sunAndPlanets.get("Mercure");
		float mercuryDistanceToSun = coa.adaptDistance(mercury);
		float mercurySpeed = coa.adaptOrbitalSpeed(mercury);
		
		
		displayPlanet(gl, distance, distanceFactor*mercuryDistanceToSun, mercurySpeed, mercuryID, false, 0);				// MERCURY
		
		////////////////////////////////////////////////////////////////
		float distanceToCenter = 40;
		float speed = 0;
		float angle = 0;
		for(int i = 0; i<nbAsteroids ;i++){
			distanceToCenter = random(distanceFactor*400f, distanceFactor*500f);
			speed = random(0.5f, 1f);
			//angle = random(0.0f, 360.0f);
			//gl.glRotatef(angle, 0, 0, 1);//ROTATION AUTOUR DU SOLEIL
			displayAsteroid(gl, 200f, distanceToCenter, speed, asteroidIDs.get(i));
		}

		
		

		gl.glFlush();

		gl.glLoadIdentity();
		float[] specularColor = { 1.0f, 1.0f, 1.0f, 0.0f };
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPECULAR, specularColor, 0);

	}

	private void displaySun(GL2 gl, float distance){
		gl.glTranslatef(0.0f, 0.0f, -distance);
		gl.glRotatef(-90.0f, 1.0f, 0.0f, 0.0f);//ROTATION POUR METTRE LE SOLEIL "DROIT"
		gl.glRotatef(moveSpeed*object_angle, 0, 0, 1); // ROTATION SUR SOI MEME
		gl.glCallList(sunID);
	}
	
	private void displayPlanet(GL2 gl, float distance, float distanceToSun, float speed, int ID, boolean rings, int ringID){
		gl.glLoadIdentity();
		gl.glRotatef((float)moveSpeed*speed*object_angle, 0, 0, 1);//ROTATION AUTOUR DU SOLEIL
		gl.glTranslatef(0, distanceToSun, 0);//ANGLE ENTRE LA VUE DE DEPART ET LE SOLEIL (DISTANCE AU SOLEIL)
		gl.glTranslatef(0.0f, 0.0f, -distance);//SE METTRE SUR LE MEME PLAN QUE LE SOLEIL
		//gl.glRotatef(object_angle, 1, 1, 1);// ROTATION SUR SOI MEME
		gl.glCallList(ID);
		if (rings){
			displayRings(gl, distance, distanceToSun, speed, ringID);
		}
	}
	
	private void displayRings(GL2 gl, float distance, float distanceToSun, float speed, int ID){
		gl.glLoadIdentity();
		gl.glRotatef((float)moveSpeed*speed*object_angle, 0, 0, 1);//ROTATION AUTOUR DU SOLEIL
		gl.glTranslatef(0, distanceToSun, 0);//ANGLE ENTRE LA VUE DE DEPART ET LE SOLEIL (DISTANCE AU SOLEIL)
		gl.glTranslatef(0.0f, 0.0f, -distance);//SE METTRE SUR LE MEME PLAN QUE LE SOLEIL
		//gl.glRotatef(object_angle, 1, 1, 1);// ROTATION SUR SOI MEME
		gl.glCallList(ID);
	}
	
	private void displaySatellite(GL2 gl, float distance, float distanceToSun, float distanceToPlanet, float speed, int ID){
		gl.glLoadIdentity();
		gl.glRotatef((float)moveSpeed*speed*object_angle, 0, 0, 1);//ROTATION AUTOUR DU SOLEIL
		gl.glTranslatef(0, distanceToSun, 0);//ANGLE ENTRE LA VUE DE DEPART ET LE SOLEIL (DISTANCE AU SOLEIL)
		gl.glRotatef(moveSpeed*object_angle, 0, 0, 1);//ROTATION DE LA LUNE AUTOUR DE LA TERRE
		gl.glTranslatef(0, distanceToPlanet, 0);//ANGLE ENTRE LA VUE DE DEPART ET LE SOLEIL (DISTANCE AU SOLEIL)
		gl.glTranslatef(0.0f, 0.0f, -distance);
		gl.glCallList(ID);
	}
	
	private void displayAsteroid(GL2 gl, float distance, float distanceToCenter, float speed, int ID){
		gl.glLoadIdentity();
		gl.glRotatef((float)speed*object_angle, 0, 0, 1);//ROTATION AUTOUR DU SOLEIL
		gl.glTranslatef(0, distanceToCenter, 0);//ANGLE ENTRE LA VUE DE DEPART ET LE SOLEIL (DISTANCE AU SOLEIL)
		gl.glTranslatef(0.0f, 0.0f, -distance);//SE METTRE SUR LE MEME PLAN QUE LE SOLEIL
		gl.glCallList(ID);
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

		sunID = planet(gl,"SunMap.jpg",sizeFactor*5f); // Sun
		mercuryID = planet(gl,"MercuryMap.jpg",sizeFactor*1f); // Mercury
		venusID =  planet(gl,"VenusMap.jpg",sizeFactor*1.5f); // Venus
		earthID = planet(gl,"EarthMap.jpg",sizeFactor*1.5f); // Earth
		
		moonID = planet(gl,"MoonMap.jpg",sizeFactor*0.7f); // Moon
		
		marsID = planet(gl,"MarsMap.jpg",sizeFactor*1.3f); // Mars
		jupiterID = planet(gl, "JupiterMap.jpg",sizeFactor*2.3f);// Jupiter
		
		saturnID = planet(gl,"SaturnMap.jpg",sizeFactor*2.1f); // Saturn
		saturnRingID = rings(gl,"SaturnRing.png",sizeFactor*2.1f*2); // Saturn Rings
		
		uranusID = planet(gl,"UranusMap.jpg",sizeFactor*1.8f); // Uranus
		uranusRingID = rings(gl,"UranusRing.png",sizeFactor*2.1f*2); // Uranus Rings
		
		neptuneID = planet(gl,"NeptuneMap.jpg",sizeFactor*1.75f); // Neptune
		neptuneRingID = rings(gl,"NeptuneRing.png",sizeFactor*2.1f*2); // Neptune Rings
		
		float size=0;
		for(int i=0; i < nbAsteroids ;i++){
			size = random(0.2f, 0.4f);
			asteroidIDs.add(planet(gl,"MoonMap.jpg",size)); // Saturn
		}
		
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
	
	private int rings(GL2 gl, String texture, float size)
	{
		try
		{
			Texture tex = TextureIO.newTexture(new File("data/"+texture), true);
			GLUquadric ring = glu.gluNewQuadric();
			glu.gluQuadricDrawStyle(ring, GLU.GLU_FILL);
			glu.gluQuadricTexture(ring, true);
			glu.gluQuadricNormals(ring, GLU.GLU_SMOOTH);
			glu.gluQuadricOrientation(ring, GLU.GLU_OUTSIDE);

			int ringID = gl.glGenLists(1);
			gl.glNewList(ringID, GL2.GL_COMPILE);

			tex.bind(gl);
			glu.gluDisk(ring, 0, size, 50, 50);

			gl.glEndList();
			glu.gluDeleteQuadric(ring);
			return ringID;
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
				if(e.isControlDown()){/*
					POV_orientation -= POV_rotation_speed;
					lx = (float) -Math.sin(Math.toRadians(POV_orientation));
					lz = (float) Math.cos(Math.toRadians(POV_orientation));
				*/}else{
					POV_orientation -= POV_rotation_speed;
					lx = (float) Math.sin(Math.toRadians(POV_orientation));
					lz = (float) -Math.cos(Math.toRadians(POV_orientation));
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(e.isControlDown()){/*
					POV_orientation += POV_rotation_speed;
					lx = (float) -Math.sin(Math.toRadians(POV_orientation));
					lz = (float) Math.cos(Math.toRadians(POV_orientation));
				*/}else{
					POV_orientation += POV_rotation_speed;
					lx = (float) Math.sin(Math.toRadians(POV_orientation));
					lz = (float) -Math.cos(Math.toRadians(POV_orientation));
				}
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
			case KeyEvent.VK_PLUS:
				moveSpeed += 10;
				break;
			case KeyEvent.VK_MINUS:
				moveSpeed -= 10;
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
	
	public float random(float min, float max) {
		Random random = new Random();
		double range = max - min;
		double scaled = random.nextDouble() * range;
		double shifted = scaled + min;
		return (float)shifted; // == (rand.nextDouble() * (max-min)) + min;
	}

}
