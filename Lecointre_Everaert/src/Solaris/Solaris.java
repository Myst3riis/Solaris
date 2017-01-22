package Solaris;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
import CelestialObjects.Satellite;
import CelestialObjects.Sun;

public class Solaris extends JFrame implements GLEventListener, KeyListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private float object_angle = 0.0f;
	private float sat_angle = 0.0f;
	
	private float sizeFactor = 1f;
	private float distanceFactor = 1f;

	private float eyeX = 0;
	private float eyeY = 0;
	private float eyeZ = 0;

	private float POV_orientation = 0;
	private float POV_speed = 250f;
	private float POV_rotation_speed = 10f;

	private float lx = 0;
	private float lz = -1;

	private int moveSpeed = 1;
	
	private GLU glu = new GLU();

	private int sunID;
	private int mercuryID;
	private int venusID;
	private int earthID;
	private int marsID;
	private int jupiterID;
	private int saturnID;
	private int saturnRingID;
	private int uranusID;
	private int uranusRingID;
	private int neptuneID;
	private int neptuneRingID;
	private List<Integer> asteroidIDs = new ArrayList<Integer>();
	private int nbAsteroids = 200;

	private HashMap<String, CelestialObject> sunAndPlanets;
	private CelestialObjectAdapter coa;

	private Sun sun;
	private Planet mercury;
	private Planet venus;
	private Planet mars;
	private Planet saturn;
	private Planet earth;
	private Planet jupiter;
	private Planet uranus;
	private Planet neptune;
	private HashMap<String, CelestialObject> earthSatellites;
	private HashMap<String, CelestialObject> jupiterSatellites;
	private HashMap<String, CelestialObject> marsSatellites;
	private HashMap<String, CelestialObject> saturnSatellites;
	private HashMap<String, CelestialObject> uranusSatellites;
	private HashMap<String, CelestialObject> neptuneSatellites;
	
	private List<Integer> earthSatellitesIDs   = new ArrayList<Integer>(); 
	private List<Integer> jupiterSatellitesIDs = new ArrayList<Integer>(); 
	private List<Integer> marsSatellitesIDs    = new ArrayList<Integer>(); 
	private List<Integer> saturnSatellitesIDs  = new ArrayList<Integer>(); 
	private List<Integer> uranusSatellitesIDs  = new ArrayList<Integer>(); 
	private List<Integer> neptuneSatellitesIDs = new ArrayList<Integer>(); 
	        
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
		this.setResizable(true);
		canvas.requestFocusInWindow();

		// Creation et instanciation du FPSAnimator
		FPSAnimator animator = new FPSAnimator(canvas, 25, true);
		animator.start();
		
	}
	/**
	 * 
	 */
	@Override
	public void display(GLAutoDrawable drawable)
	{

		
		object_angle += 1.0f;
		sat_angle += 1.0f;
		// Recuperons notre objet OpenGL
		GL2 gl = drawable.getGL().getGL2();
		// On efface le buffer couleur (ce qu'on affiche) et le buffer de
		// profondeur
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
		glu.gluPerspective(45.0f, 1, 1.0, 8000000000.0);
		glu.gluLookAt(eyeX, eyeY, eyeZ, eyeX + lx, 0.0f, eyeZ + lz, 0, 1, 0);
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();

		float distance = 200f;
		
		// SUN
		displaySun(gl, distance);		
		////PLANETS
		displayPlanet(gl, distance, distanceFactor*(coa.adaptDistance(mercury)+ coa.adaptRadius(sun)), moveSpeed*coa.adaptOrbitalSpeed(mercury), mercuryID, false, 0);				// MERCURY
		displayPlanet(gl, distance, distanceFactor*(coa.adaptDistance(venus)  + coa.adaptRadius(sun)), moveSpeed*coa.adaptOrbitalSpeed(venus),   venusID,   false, 0);				// VENUS
		displayPlanet(gl, distance, distanceFactor*(coa.adaptDistance(earth)  + coa.adaptRadius(sun)), moveSpeed*coa.adaptOrbitalSpeed(earth),   earthID,   false, 0);				// TERRE
		displayPlanet(gl, distance, distanceFactor*(coa.adaptDistance(mars)   + coa.adaptRadius(sun)), moveSpeed*coa.adaptOrbitalSpeed(mars),    marsID,    false, 0);				// MARS
		displayPlanet(gl, distance, distanceFactor*(coa.adaptDistance(jupiter)+ coa.adaptRadius(sun)), moveSpeed*coa.adaptOrbitalSpeed(jupiter), jupiterID, false, 0);				// JUPITER
		displayPlanet(gl, distance, distanceFactor*(coa.adaptDistance(saturn) + coa.adaptRadius(sun)), moveSpeed*coa.adaptOrbitalSpeed(saturn),  saturnID,  true,  saturnRingID);	// SATURNE
		displayPlanet(gl, distance, distanceFactor*(coa.adaptDistance(uranus) + coa.adaptRadius(sun)), moveSpeed*coa.adaptOrbitalSpeed(uranus),  uranusID,  true,  uranusRingID);	// URANUS
		displayPlanet(gl, distance, distanceFactor*(coa.adaptDistance(neptune)+ coa.adaptRadius(sun)), moveSpeed*coa.adaptOrbitalSpeed(neptune), neptuneID, true,  neptuneRingID);	// NEPTUNE
		
		////ASTEROID BELT
		float distanceToCenter = 40;
		float speed = 0;
		float angle = 0;
		
		for(int i = 0; i<nbAsteroids ;i++){
			float J = distanceFactor*(coa.adaptDistance(jupiter)+ coa.adaptRadius(sun));
			float M = distanceFactor*(coa.adaptDistance(mars)+ coa.adaptRadius(sun));
			distanceToCenter = random( M+(J-M)/3 , M+(2*(J-M))/3 );
			speed = random(0.5f, 1f);
			displayAsteroid(gl, distance, distanceToCenter, speed, asteroidIDs.get(i));
		}
		
		////SATELLITES
		int eSatellite = 0;
		for(Iterator<String> it = earthSatellites.keySet().iterator(); it.hasNext();){
			Satellite obj = (Satellite) earthSatellites.get(it.next());
			displaySatellite(gl,distance, distanceFactor*(coa.adaptDistance(earth)+coa.adaptRadius(sun)), distanceFactor*obj.getDistanceToPlanet(),moveSpeed*coa.adaptOrbitalSpeed(earth) ,moveSpeed*obj.getOrbitalSpeed(),earthSatellitesIDs.get(eSatellite));
			eSatellite++;
		}
		/*
		int mSatellite = 0;
		for(Iterator<String> it = marsSatellites.keySet().iterator(); it.hasNext();){
			Satellite obj = (Satellite) marsSatellites.get(it.next());
			displaySatellite(gl,distance, distanceFactor*(coa.adaptDistance(mars)+coa.adaptRadius(sun)), distanceFactor*obj.getDistanceToPlanet(), moveSpeed*obj.getOrbitalSpeed(),marsSatellitesIDs.get(mSatellite));
			mSatellite++;
		}
		
		int jSatellite = 0;
		for(Iterator<String> it = jupiterSatellites.keySet().iterator(); it.hasNext();){
			Satellite obj = (Satellite) jupiterSatellites.get(it.next());
			displaySatellite(gl,distance, distanceFactor*(coa.adaptDistance(jupiter)+coa.adaptRadius(sun)), distanceFactor*obj.getDistanceToPlanet(), moveSpeed*obj.getOrbitalSpeed(),jupiterSatellitesIDs.get(jSatellite));
			jSatellite++;
		}
		
		int sSatellite = 0;
		for(Iterator<String> it = saturnSatellites.keySet().iterator(); it.hasNext();){
			Satellite obj = (Satellite) saturnSatellites.get(it.next());
			displaySatellite(gl,distance, distanceFactor*(coa.adaptDistance(saturn)+coa.adaptRadius(sun)), distanceFactor*obj.getDistanceToPlanet(), moveSpeed*obj.getOrbitalSpeed(),saturnSatellitesIDs.get(sSatellite));
			sSatellite++;
		}
		
		int uSatellite = 0;
		for(Iterator<String> it = uranusSatellites.keySet().iterator(); it.hasNext();){
			Satellite obj = (Satellite) uranusSatellites.get(it.next());
			displaySatellite(gl,distance, distanceFactor*(coa.adaptDistance(uranus)+coa.adaptRadius(sun)), distanceFactor*obj.getDistanceToPlanet(), moveSpeed*obj.getOrbitalSpeed(),uranusSatellitesIDs.get(uSatellite));
			uSatellite++;
		}
		
		int nSatellite = 0;
		for(Iterator<String> it = neptuneSatellites.keySet().iterator(); it.hasNext();){
			Satellite obj = (Satellite) neptuneSatellites.get(it.next());
			displaySatellite(gl,distance, distanceFactor*(coa.adaptDistance(neptune)+coa.adaptRadius(sun)), distanceFactor*obj.getDistanceToPlanet(), moveSpeed*obj.getOrbitalSpeed(),neptuneSatellitesIDs.get(nSatellite));
			nSatellite++;
		}
		 */
		gl.glFlush();

		gl.glLoadIdentity();
		float[] specularColor = { 1.0f, 1.0f, 1.0f, 0.0f };
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPECULAR, specularColor, 0);

	}

	private void displaySun(GL2 gl, float distance){
		gl.glTranslatef(0.0f, 0.0f, -distance);
		//gl.glRotatef(-90.0f, 1.0f, 0.0f, 0.0f);//ROTATION POUR METTRE LE SOLEIL "DROIT"
		gl.glRotatef(moveSpeed*object_angle, 0, 0, 1); // ROTATION SUR SOI MEME
		gl.glCallList(sunID);
	}
	
	private void displayPlanet(GL2 gl, float distance, float distanceToSun, float speed, int ID, boolean rings, int ringID){
		gl.glLoadIdentity();
		gl.glRotatef((float)moveSpeed*speed*object_angle, 0, 0, 1);//ROTATION AUTOUR DU SOLEIL
		gl.glTranslatef(0, distanceToSun, 0);//DISTANCE AU SOLEIL
		gl.glTranslatef(0.0f, 0.0f, -distance);//SE METTRE SUR LE MEME PLAN QUE LE SOLEIL
		//gl.glRotatef(object_angle, 1, 1, 1);// ROTATION SUR SOI MEME
		gl.glCallList(ID);
		if (rings) displayRings(gl, distance, distanceToSun, speed, ringID);
	}
	
	private void displayRings(GL2 gl, float distance, float distanceToSun, float speed, int ID){
		gl.glLoadIdentity();
		gl.glRotatef((float)moveSpeed*speed*object_angle, 0, 0, 1);//ROTATION AUTOUR DU SOLEIL
		gl.glTranslatef(0, distanceToSun, 0);//DISTANCE AU SOLEIL
		gl.glTranslatef(0.0f, 0.0f, -distance);//SE METTRE SUR LE MEME PLAN QUE LE SOLEIL
		//gl.glRotatef(object_angle, 1, 1, 1);// ROTATION SUR SOI MEME
		gl.glCallList(ID);
	}
	
	private void displaySatellite(GL2 gl, float distance, float distanceToSun, float distanceToPlanet, float planetSpeed, float speed, int ID){
		/*gl.glLoadIdentity();
		gl.glRotatef((float)moveSpeed*speed*object_angle, 0, 0, 1);//ROTATION AUTOUR DU SOLEIL
		gl.glTranslatef(0, distanceToSun, 0);//DISTANCE AU SOLEIL
		gl.glRotatef(moveSpeed*object_angle, 0, 0, 1);//ROTATION DE LA LUNE AUTOUR DE LA TERRE
		gl.glTranslatef(0, distanceToPlanet, 0);//DISTANCE A LA PLANETE
		gl.glTranslatef(0.0f, 0.0f, -distance);
		gl.glCallList(ID);*/
		
		
		distanceToPlanet = 300f;
		gl.glLoadIdentity();
		gl.glRotatef((float)moveSpeed*planetSpeed*object_angle, 0, 0, 1);//ROTATION AUTOUR DU SOLEIL
		gl.glTranslatef(0, distanceToSun, 0);//DISTANCE AU SOLEIL
		gl.glRotatef(moveSpeed*speed*object_angle, 0, 0, 1);//ROTATION DE LA LUNE AUTOUR DE LA TERRE
		gl.glTranslatef(0, distanceToPlanet, 0);//DISTANCE A LA PLANETE
		gl.glTranslatef(0.0f, 0.0f, -distance);
		gl.glCallList(ID);
		/*
		distanceToPlanet = 300f;
		gl.glLoadIdentity();
		//gl.glRotatef(moveSpeed*speed*sat_angle, 0, 0, 1);//ROTATION DE LA LUNE AUTOUR DE LA TERRE
		gl.glTranslatef(0, distanceToPlanet, 0);//DISTANCE A LA PLANETE
		gl.glRotatef((float)moveSpeed*planetSpeed*object_angle, 0, 0, 1);//ROTATION AUTOUR DU SOLEIL
		gl.glTranslatef(0, distanceToSun, 0);//DISTANCE AU SOLEIL
		gl.glTranslatef(0.0f, 0.0f, -distance);
		gl.glCallList(ID);
		 */
	}
	
	private void displayAsteroid(GL2 gl, float distance, float distanceToCenter, float speed, int ID){
		gl.glLoadIdentity();
		gl.glRotatef((float)speed*object_angle, 0, 0, 1);//ROTATION AUTOUR DU SOLEIL
		gl.glTranslatef(0, distanceToCenter, 0);//DISTANCE AU SOLEIL
		gl.glTranslatef(0.0f, 0.0f, -distance);//SE METTRE SUR LE MEME PLAN QUE LE SOLEIL
		gl.glCallList(ID);
	}
	
	@Override
	public void dispose(GLAutoDrawable arg0)
	{
		// TODO Auto-generated method stub

	}

	
	/**
	 * 
	 */
	@Override
	public void init(GLAutoDrawable drawable)
	{
		// Recuperons notre objet OpenGL
		GL2 gl = drawable.getGL().getGL2();
		// Fixons la couleur par defaut de glClear
		gl.glClearColor(1.0f, 1.0f, 0.0f, 1.0f);
		gl.glEnable(GL2.GL_LIGHTING);
		gl.glEnable(GL2.GL_LIGHT0);
		gl.glEnable(GL2.GL_RESCALE_NORMAL);
		gl.glEnable(GL2.GL_DEPTH_TEST);
		gl.glEnable(GL2.GL_TEXTURE_2D);		
		gl.glDepthFunc(GL2.GL_LEQUAL);
		gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);
		
		coa = new CelestialObjectAdapter();
		
		
		SolarSystem ss = new SolarSystem();
		
		sunAndPlanets = ss.sunAndPlanets();
		earthSatellites = ss.earthSatellites();
		marsSatellites = ss.marsSatellites();
		jupiterSatellites = ss.jupiterSatellites();
		saturnSatellites = ss.saturnSatellites();
		uranusSatellites = ss.uranusSatellites();
		neptuneSatellites = ss.neptuneSatellites();
		float sssss = 250;
		for(Iterator<String> it = earthSatellites.keySet().iterator(); it.hasNext();){
			Satellite obj = (Satellite) earthSatellites.get(it.next());
			//earthSatellitesIDs.add(satellite(gl,"MoonMap.jpg",obj.getRadius()));
			earthSatellitesIDs.add(satellite(gl,"MoonMap.jpg",sssss));
		}
		
		for(Iterator<String> it = marsSatellites.keySet().iterator(); it.hasNext();){
			Satellite obj = (Satellite) marsSatellites.get(it.next());
			//marsSatellitesIDs.add(satellite(gl,obj.getRadius()));
			marsSatellitesIDs.add(satellite(gl,sssss));
		}
		
		for(Iterator<String> it = jupiterSatellites.keySet().iterator(); it.hasNext();){
			Satellite obj = (Satellite) jupiterSatellites.get(it.next());
			//jupiterSatellitesIDs.add(satellite(gl,obj.getRadius()));
			jupiterSatellitesIDs.add(satellite(gl,sssss));
		}
		
		for(Iterator<String> it = saturnSatellites.keySet().iterator(); it.hasNext();){
			Satellite obj = (Satellite) saturnSatellites.get(it.next());
			//saturnSatellitesIDs.add(satellite(gl,obj.getRadius()));
			saturnSatellitesIDs.add(satellite(gl,sssss));
		}
		
		for(Iterator<String> it = uranusSatellites.keySet().iterator(); it.hasNext();){
			Satellite obj = (Satellite) uranusSatellites.get(it.next());
			//uranusSatellitesIDs.add(satellite(gl,obj.getRadius()));
			uranusSatellitesIDs.add(satellite(gl,sssss));
		}
		
		for(Iterator<String> it = neptuneSatellites.keySet().iterator(); it.hasNext();){
			Satellite obj = (Satellite) neptuneSatellites.get(it.next());
			//neptuneSatellitesIDs.add(satellite(gl,obj.getRadius()));
			neptuneSatellitesIDs.add(satellite(gl,"MoonMap.jpg",sssss));
		}
		
		sun = (Sun) sunAndPlanets.get("Soleil");
		mercury = (Planet) sunAndPlanets.get("Mercure");
		venus = (Planet) sunAndPlanets.get("V�nus");
		earth = (Planet) sunAndPlanets.get("Terre");
		mars = (Planet) sunAndPlanets.get("Mars");
		jupiter = (Planet) sunAndPlanets.get("Jupiter");
		saturn = (Planet) sunAndPlanets.get("Saturne");
		uranus = (Planet) sunAndPlanets.get("Uranus");
		neptune = (Planet) sunAndPlanets.get("Neptune");	

		sunID = planet(gl,"SunMap.jpg",sizeFactor*coa.adaptRadius(sun)); // Sun
		mercuryID = planet(gl,"MercuryMap.jpg",sizeFactor*coa.adaptRadius(mercury)); // Mercury
		venusID =  planet(gl,"VenusMap.jpg",sizeFactor*coa.adaptRadius(venus)); // Venus
		earthID = planet(gl,"EarthMap.jpg",sizeFactor*coa.adaptRadius(earth)); // Earth
		marsID = planet(gl,"MarsMap.jpg",sizeFactor*coa.adaptRadius(mars)); // Mars
		jupiterID = planet(gl, "JupiterMap.jpg",sizeFactor*coa.adaptRadius(jupiter));// Jupiter
		
		saturnID = planet(gl,"SaturnMap.jpg",sizeFactor*coa.adaptRadius(saturn)); // Saturn
		saturnRingID = rings(gl,"SaturnRing.png",sizeFactor*coa.adaptRadius(saturn)*2); // Saturn Rings
		
		uranusID = planet(gl,"UranusMap.jpg",sizeFactor*coa.adaptRadius(uranus)); // Uranus
		uranusRingID = rings(gl,"UranusRing.png",sizeFactor*coa.adaptRadius(uranus)*2); // Uranus Rings
		
		neptuneID = planet(gl,"NeptuneMap.jpg",sizeFactor*coa.adaptRadius(neptune)); // Neptune
		neptuneRingID = rings(gl,"NeptuneRing.png",sizeFactor*coa.adaptRadius(neptune)*2); // Neptune Rings
		
		
		float size=0;
		for(int i=0; i < nbAsteroids ;i++){
			float M = sizeFactor*coa.adaptRadius(mars);
			size = random( M/2, M/3 );
			asteroidIDs.add(asteroid(gl,size)); // Saturn
		}
		
		
	}

	/**
	 * 
	 * @param gl
	 * @param texture
	 * @param size
	 * @return
	 */
	private int planet(GL2 gl, String texture, float size)
	{
		try
		{
			Texture tex = TextureIO.newTexture(new File("data/"+texture), true);
			GLUquadric planet = glu.gluNewQuadric();
			glu.gluQuadricDrawStyle(planet, GLU.GLU_FILL);
			glu.gluQuadricTexture(planet, true);
			glu.gluQuadricNormals(planet, GLU.GLU_SMOOTH);
			glu.gluQuadricOrientation(planet, GLU.GLU_OUTSIDE);

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
	/**
	 * 
	 * @param gl
	 * @param size
	 * @return
	 */
	private int satellite(GL2 gl, float size)
	{
		GLUquadric satellite = glu.gluNewQuadric();
		glu.gluQuadricDrawStyle(satellite, GLU.GLU_FILL);
		glu.gluQuadricNormals(satellite, GLU.GLU_SMOOTH);
		glu.gluQuadricOrientation(satellite, GLU.GLU_OUTSIDE);

		int satelliteID = gl.glGenLists(1);
		gl.glNewList(satelliteID, GL2.GL_COMPILE);

		glu.gluSphere(satellite, size, 50, 50);

		gl.glEndList();
		glu.gluDeleteQuadric(satellite);
		return satelliteID;
	}
	
	/**
	 * 
	 * @param gl
	 * @param texture
	 * @param size
	 * @return
	 */
	private int satellite(GL2 gl, String texture, float size)
	{
		try
		{
			Texture tex = TextureIO.newTexture(new File("data/"+texture), true);
			GLUquadric satellite = glu.gluNewQuadric();
			glu.gluQuadricDrawStyle(satellite, GLU.GLU_FILL);
			glu.gluQuadricTexture(satellite, true);
			glu.gluQuadricNormals(satellite, GLU.GLU_SMOOTH);
			glu.gluQuadricOrientation(satellite, GLU.GLU_OUTSIDE);

			int satelliteID = gl.glGenLists(1);
			gl.glNewList(satelliteID, GL2.GL_COMPILE);

			tex.bind(gl);
			glu.gluSphere(satellite, size, 50, 50);

			gl.glEndList();
			glu.gluDeleteQuadric(satellite);
			return satelliteID;
		}
		catch (IOException e)
		{
			javax.swing.JOptionPane.showMessageDialog(null, e);
			return 0;
		}
	}
	
	/**
	 * 
	 * @param gl
	 * @param texture
	 * @param size
	 * @return
	 */
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

	/**
	 * 
	 * @param gl
	 * @param size
	 * @return
	 */
	private int asteroid(GL2 gl, float size)
	{
		//Texture tex = TextureIO.newTexture(new File("data/"+texture), true);
		GLUquadric planet = glu.gluNewQuadric();
		glu.gluQuadricDrawStyle(planet, GLU.GLU_FILL);
		glu.gluQuadricNormals(planet, GLU.GLU_SMOOTH);
		glu.gluQuadricOrientation(planet, GLU.GLU_OUTSIDE);

		int planetID = gl.glGenLists(1);
		gl.glNewList(planetID, GL2.GL_COMPILE);

		//tex.bind(gl);
		glu.gluSphere(planet, size, 50, 50);

		gl.glEndList();
		glu.gluDeleteQuadric(planet);
		return planetID;
		
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

