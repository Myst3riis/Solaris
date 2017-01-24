package Solaris;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
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

public class Solaris extends JFrame implements GLEventListener, KeyListener, MouseListener, MouseMotionListener, MouseWheelListener{

    /**
     * Ground Control to Major Tom Ground Control to Major Tom Take your protein
     * pills and put your helmet on Ground Control to Major Tom (ten, nine,
     * eight, seven, six) Commencing countdown, engines on (five, four, three)
     * Check ignition and may God's love be with you (two, one, liftoff)
     */

    private static final long serialVersionUID = 1L;

    private float object_angle = 0.0f;
    private float sat_angle = 0.0f;

    private float eyeX = -1555;// 0;
    private float eyeY = 0;// 0;
    private float eyeZ = 239;// 0;

    private float POV_orientation = 75f;// 0;
    private float POV_speed = 10f;
    private float POV_rotation_speed = 5f;

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

    private int earthSatellitesIDs;
    private int jupiterSatellitesIDs;
    private int marsSatellitesIDs;
    private int saturnSatellitesIDs;
    private int uranusSatellitesIDs;
    private int neptuneSatellitesIDs;

    private int skyboxID;

////////////
	private float phi = 0;
	private float theta = 0;
	private float[] direction = new float[3];
	private int m_x;
	private int m_y;
	private boolean move = false;
//////////////
    /**
     * Creates the window
     * 
     * @param width
     *            : int - width of window
     * @param height
     *            : int - height of window
     */
    public Solaris(int width, int height) {
	super("Solar System");
	GLProfile profil = GLProfile.get(GLProfile.GL2);
	GLCapabilities capabilities = new GLCapabilities(profil);

	GLCanvas canvas = new GLCanvas(capabilities);
	canvas.addGLEventListener(this);
	canvas.addKeyListener(this);
	canvas.addMouseListener(this);
	canvas.addMouseMotionListener(this);
	canvas.addMouseWheelListener(this);
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
     * @author Flavien Everaert - Thomas Lecointre
     */
    @Override
    public void display(GLAutoDrawable drawable) {
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
	lx = (float) Math.sin(Math.toRadians(POV_orientation));
	lz = (float) -Math.cos(Math.toRadians(POV_orientation));
	glu.gluLookAt(eyeX + direction[0], eyeY+ direction[1], eyeZ+ direction[2], eyeX + lx, 0.0f, eyeZ + lz, 0, 0, 1);
	gl.glMatrixMode(GL2.GL_MODELVIEW);
	//gl.glLoadIdentity();

	float distance = 200f;

	// SKYBOX
	displaySkybox(gl, distance, skyboxID);

	// SUN
	displaySun(gl, distance, sunID);

	//// PLANETS
	displayPlanet(gl, distance, coa.adaptDistance(mercury) + coa.adaptRadius(sun),
		moveSpeed * coa.adaptOrbitalSpeed(mercury), mercuryID, false, 0, 0); // MERCURY
	displayPlanet(gl, distance, coa.adaptDistance(venus) + coa.adaptRadius(sun),
		moveSpeed * coa.adaptOrbitalSpeed(venus), venusID, false, 0, 0); // VENUS
	displayPlanet(gl, distance, coa.adaptDistance(earth) + coa.adaptRadius(sun),
		moveSpeed * coa.adaptOrbitalSpeed(earth), earthID, false, 0, 0); // TERRE
	displayPlanet(gl, distance, coa.adaptDistance(mars) + coa.adaptRadius(sun),
		moveSpeed * coa.adaptOrbitalSpeed(mars), marsID, false, 0, 0); // MARS
	displayPlanet(gl, distance, coa.adaptDistance(jupiter) + coa.adaptRadius(sun),
		moveSpeed * coa.adaptOrbitalSpeed(jupiter), jupiterID, false, 0, 0); // JUPITER
	displayPlanet(gl, distance, coa.adaptDistance(saturn) + coa.adaptRadius(sun),
		moveSpeed * coa.adaptOrbitalSpeed(saturn), saturnID, true, saturnRingID, 5f); // SATURNE
	displayPlanet(gl, distance, coa.adaptDistance(uranus) + coa.adaptRadius(sun),
		moveSpeed * coa.adaptOrbitalSpeed(uranus), uranusID, true, uranusRingID, 80f); // URANUS
	displayPlanet(gl, distance, coa.adaptDistance(neptune) + coa.adaptRadius(sun),
		moveSpeed * coa.adaptOrbitalSpeed(neptune), neptuneID, true, neptuneRingID, 5f); // NEPTUNE

	//// ASTEROID BELT
	float distanceToCenter = 40;
	float speed = 0;
	//float angle = 0;

	for (int i = 0; i < nbAsteroids; i++) {
	    float J = coa.adaptDistance(jupiter) + coa.adaptRadius(sun);
	    float M = coa.adaptDistance(mars) + coa.adaptRadius(sun);
	    distanceToCenter = random(M + (J - M) / 3, M + (2 * (J - M)) / 3);
	    speed = random(0.5f, 1f);
	    displayAsteroid(gl, distance, distanceToCenter, speed, asteroidIDs.get(i));
	}

	//// SATELLITES

	displaySatellites(gl, earthSatellitesIDs, earthSatellites, earth, distance);
	displaySatellites(gl, marsSatellitesIDs, earthSatellites, mars, distance);
	displaySatellites(gl, jupiterSatellitesIDs, jupiterSatellites, jupiter, distance);
	displaySatellites(gl, saturnSatellitesIDs, saturnSatellites, saturn, distance);
	displaySatellites(gl, uranusSatellitesIDs, uranusSatellites, uranus, distance);
	displaySatellites(gl, neptuneSatellitesIDs, neptuneSatellites, neptune, distance);

	// displaySatellites(gl, plutoSatellitesIDs, plutoSatellites, pluto, distance);
	gl.glFlush();

	gl.glLoadIdentity();
	float[] specularColor = { 1.0f, 1.0f, 1.0f, 0.0f };
	gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, specularColor, 0);
    }

    /**
     * Displays satellites
     * 
     * @author Lecointre
     * @param gl
     *            : GL2
     * @param ID
     *            Starting id of gl list.
     * @param satellites
     *            Hashmap used for satellite iteration.
     * @param planet
     *            Planet used as reference for placing satellites.
     * @param distance
     *            Global satellite placement in relation to starting camera
     *            position.
     */
    private void displaySatellites(GL2 gl, int ID, HashMap<String, CelestialObject> satellites, Planet planet,
	    float distance) {
	int i = ID;
	for (Iterator<String> it = satellites.keySet().iterator(); it.hasNext();) {
	    Satellite satellite = (Satellite) satellites.get(it.next());
	    gl.glLoadIdentity();
	    float distanceToPlanet = coa.adaptRadius(planet) + coa.adaptDistance(satellite);
	    float speed = coa.adaptOrbitalSpeed(satellite);
	    float planetSpeed = coa.adaptOrbitalSpeed(planet);
	    float distanceToSun = coa.adaptDistance(planet) + coa.adaptRadius(sun);
	    gl.glRotatef((float) moveSpeed * planetSpeed * object_angle, 0, 0, 1);
	    gl.glTranslatef(0, distanceToSun, 0);
	    gl.glRotatef(moveSpeed * speed * object_angle, 0, 0, 1);
	    gl.glTranslatef(0, distanceToPlanet, 0);
	    gl.glTranslatef(0.0f, 0.0f, -distance);
	    gl.glCallList(i);
	    i++;
	}
    }

    /**
     * Display skybox (sphere)
     * 
     * @author Flavien Everaert
     * @param gl
     *            : GL2
     * @param distance
     *            : float - distance to origin
     */
    private void displaySkybox(GL2 gl, float distance, int ID) {
	gl.glLoadIdentity();
	gl.glTranslatef(0.0f, 0.0f, -distance);
	gl.glRotatef(-90.0f, 1.0f, 0.0f, 0.0f);// ROTATION POUR METTRE LE SOLEIL "DROIT"
	gl.glRotatef((moveSpeed*object_angle)/100, 0, 0, 1); // ROTATION SUR SOI MEME
	gl.glCallList(ID);
    }

    /**
     * Display sun
     * 
     * @author Flavien Everaert
     * @param gl
     *            : GL2
     * @param distance
     *            : float - distance to origin
     */
    private void displaySun(GL2 gl, float distance, int ID) {
	gl.glLoadIdentity();
	gl.glTranslatef(0.0f, 0.0f, -distance);
	// gl.glRotatef(-90.0f, 0.0f, 0.0f, 0.0f);//ROTATION POUR METTRE LE SOLEIL "DROIT"
	gl.glRotatef(moveSpeed * object_angle, 0, 0, 1); // ROTATION SUR SOI MEME
	gl.glCallList(ID);
    }

    /**
     * Display planet
     * 
     * @author Flavien Everaert
     * @param gl
     *            : GL2
     * @param distance
     *            : float - distance to origin
     * @param distanceToSun
     *            : float - distance to sun
     * @param speed
     *            : float - planet speed
     * @param ID
     *            : int - planet ID
     * @param rings
     *            : boolean - does this planet have rings ?
     * @param ringID
     *            : int - rings ID (if rings there are)
     */
    private void displayPlanet(GL2 gl, float distance, float distanceToSun, float speed, int ID, boolean rings,
	    int ringID, float angle) {
	gl.glLoadIdentity();
	gl.glRotatef((float) moveSpeed * speed * object_angle, 0, 0, 1);// ROTATION AUTOUR DU SOLEIL
	gl.glTranslatef(0, distanceToSun, 0);// DISTANCE AU SOLEIL
	gl.glTranslatef(0.0f, 0.0f, -distance);// SE METTRE SUR LE MEME PLAN QUE LE SOLEIL
	// gl.glRotatef(object_angle, 1, 1, 1);// ROTATION SUR SOI MEME
	gl.glCallList(ID);
	if (rings)
	    displayRings(gl, distance, distanceToSun, speed, ringID, angle);
    }

    /**
     * Display rings
     * 
     * @author Flavien Everaert
     * @param gl
     *            : GL2
     * @param distance
     *            : float - distance to origin
     * @param distanceToSun
     *            : float - distance to sun
     * @param speed
     *            : float - speed of planet concerned by rings
     * @param ID
     *            : int - rings ID
     */
    private void displayRings(GL2 gl, float distance, float distanceToSun, float speed, int ID, float angle) {
	gl.glLoadIdentity();
	gl.glRotatef((float) moveSpeed * speed * object_angle, 0, 0, 1);// ROTATION AUTOUR DU SOLEIL
	gl.glTranslatef(0, distanceToSun, 0);// DISTANCE AU SOLEIL
	gl.glTranslatef(0.0f, 0.0f, -distance);// SE METTRE SUR LE MEME PLAN QUE LE SOLEIL
	gl.glRotatef(angle, 0, 1, 0);// ROTATION SUR SOI MEME
	gl.glCallList(ID);
    }

    /**
     * Displays asteroid
     * 
     * @author Flavien Everaert
     * @param gl
     *            : GL2
     * @param distance
     *            : float - distance to origin
     * @param distanceToCenter
     *            : float - distance to Sun
     * @param speed
     *            : float - asteroid speed
     * @param ID
     *            : int - asteroid ID
     */
    private void displayAsteroid(GL2 gl, float distance, float distanceToCenter, float speed, int ID) {
	gl.glLoadIdentity();
	gl.glRotatef((float) speed * object_angle, 0, 0, 1);// ROTATION AUTOUR DU SOLEIL
	gl.glTranslatef(0, distanceToCenter, 0);// DISTANCE AU SOLEIL
	gl.glTranslatef(0.0f, 0.0f, -distance);// SE METTRE SUR LE MEME PLAN QUE LE SOLEIL
	gl.glCallList(ID);
    }

    @Override
    public void dispose(GLAutoDrawable arg0) {
	// TODO Auto-generated method stub

    }

    /**
     * This is Ground Control to Major Tom You've really made the grade And the
     * papers want to know whose shirts you wear Now it's time to leave the
     * capsule if you dare "This is Major Tom to Ground Control I'm stepping
     * through the door And I'm floating in a most peculiar way And the stars
     * look very different today For here Am I sitting in a tin can Far above
     * the world Planet Earth is blue And there's nothing I can do
     */

    /**
     * @author Flavien Everaert
     */
    @Override
    public void init(GLAutoDrawable drawable) {
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

	coa = new CelestialObjectAdapter();

	SolarSystem ss = new SolarSystem();

	sunAndPlanets = ss.sunAndPlanets();
	earthSatellites = ss.earthSatellites();
	marsSatellites = ss.marsSatellites();
	jupiterSatellites = ss.jupiterSatellites();
	saturnSatellites = ss.saturnSatellites();
	uranusSatellites = ss.uranusSatellites();
	neptuneSatellites = ss.neptuneSatellites();

	sun = (Sun) sunAndPlanets.get("Soleil");
	mercury = (Planet) sunAndPlanets.get("Mercure");
	venus = (Planet) sunAndPlanets.get("Vénus");
	earth = (Planet) sunAndPlanets.get("Terre");
	mars = (Planet) sunAndPlanets.get("Mars");
	jupiter = (Planet) sunAndPlanets.get("Jupiter");
	saturn = (Planet) sunAndPlanets.get("Saturne");
	uranus = (Planet) sunAndPlanets.get("Uranus");
	neptune = (Planet) sunAndPlanets.get("Neptune");

	sunID = planet(gl, "SunMap.jpg", coa.adaptRadius(sun)); // Sun
	mercuryID = planet(gl, "MercuryMap.jpg", coa.adaptRadius(mercury)); // Mercury
	venusID = planet(gl, "VenusMap.jpg", coa.adaptRadius(venus)); // Venus
	earthID = planet(gl, "EarthMap.jpg", coa.adaptRadius(earth)); // Earth
	marsID = planet(gl, "MarsMap.jpg", coa.adaptRadius(mars)); // Mars
	jupiterID = planet(gl, "JupiterMap.jpg", coa.adaptRadius(jupiter));// Jupiter

	saturnID = planet(gl, "SaturnMap.jpg", coa.adaptRadius(saturn)); // Saturn
	saturnRingID = rings(gl, "SaturnRing.png", coa.adaptRadius(saturn) * 2); // Saturn Rings

	uranusID = planet(gl, "UranusMap.jpg", coa.adaptRadius(uranus)); // Uranus
	uranusRingID = rings(gl, "UranusRing.png", coa.adaptRadius(uranus) * 2); // Uranus Rings

	neptuneID = planet(gl, "NeptuneMap.jpg", coa.adaptRadius(neptune)); // Neptune Rings

	skyboxID = skybox(gl, "skysphere.png", 1200000); // SKYBOX
	
	earthSatellitesIDs = satellites(gl, "MoonMap.jpg", earthSatellites);
	marsSatellitesIDs = satellites(gl, "MoonMap.jpg", marsSatellites);
	jupiterSatellitesIDs = satellites(gl, "MoonMap.jpg", jupiterSatellites);
	saturnSatellitesIDs = satellites(gl, "MoonMap.jpg", saturnSatellites);
	uranusSatellitesIDs = satellites(gl, "MoonMap.jpg", uranusSatellites);
	neptuneSatellitesIDs = satellites(gl, "MoonMap.jpg", neptuneSatellites);
	// plutoSatellitesIDs = satellites(gl, "MoonMap.jpg", plutoSatellites);

	float size = 0;
	for (int i = 0; i < nbAsteroids; i++) {
	    float M = coa.adaptRadius(mars);
	    size = random(M / 2, M / 3);
	    asteroidIDs.add(asteroid(gl, size)); // Saturn
	}

    }

    /**
     * Creates a planet
     * 
     * @author Flavien Everaert
     * @param gl
     *            : GL2
     * @param texture
     *            : String - planet texture
     * @param size
     *            : float - planet size
     * @return int - planet ID
     */
    private int planet(GL2 gl, String texture, float size) {
	try {
	    Texture tex = TextureIO.newTexture(new File("data/" + texture), true);
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
	} catch (IOException e) {
	    javax.swing.JOptionPane.showMessageDialog(null, e);
	    return 0;
	}
    }
    
    /**
     * Creates a list of satellites.
     * @author Lecointre
     * @param gl GL2
     * @param texture Texture file applied to gluSpehere.
     * @param satellites Hashmap used for satellite iteration.
     * @return satellites glList id.
     */
    private int satellites(GL2 gl, String texture, HashMap<String, CelestialObject> satellites) {
	try {
	    Texture tex = TextureIO.newTexture(new File("data/" + texture), true);

	    int satelliteID = gl.glGenLists(satellites.size());
	    int satelliteIndexer = satelliteID;
	    for (Iterator<String> it = satellites.keySet().iterator(); it.hasNext();) {
		GLUquadric satellite = glu.gluNewQuadric();
		glu.gluQuadricDrawStyle(satellite, GLU.GLU_FILL);
		glu.gluQuadricTexture(satellite, true);
		glu.gluQuadricNormals(satellite, GLU.GLU_SMOOTH);
		glu.gluQuadricOrientation(satellite, GLU.GLU_OUTSIDE);
		Satellite obj = (Satellite) satellites.get(it.next());
		gl.glNewList(satelliteIndexer, GL2.GL_COMPILE);
		tex.bind(gl);
		glu.gluSphere(satellite, coa.adaptRadius(obj), 25, 25);
		gl.glEndList();
		glu.gluDeleteQuadric(satellite);
		satelliteIndexer++;
	    }
	    return satelliteID;
	} catch (IOException e) {
	    javax.swing.JOptionPane.showMessageDialog(null, e);
	    return 0;
	}
    }

    /**
     * Creates rings
     * 
     * @author Flavien Everaert
     * @param gl
     *            : GL2
     * @param texture
     *            : String - rings texture
     * @param size
     *            : float - the "outside" size of the rigs
     * @return int - the rings ID
     */
    private int rings(GL2 gl, String texture, float size) {
	try {
	    Texture tex = TextureIO.newTexture(new File("data/" + texture), true);
	    GLUquadric ring = glu.gluNewQuadric();
	    glu.gluQuadricDrawStyle(ring, GLU.GLU_FILL);
	    glu.gluQuadricTexture(ring, true);
	    glu.gluQuadricNormals(ring, GLU.GLU_SMOOTH);
	    glu.gluQuadricOrientation(ring, GLU.GLU_OUTSIDE);

	    int ringID = gl.glGenLists(1);
	    gl.glNewList(ringID, GL2.GL_COMPILE);

	    tex.bind(gl);
	    glu.gluDisk(ring, size / 2, size, 50, 50);

	    gl.glEndList();
	    glu.gluDeleteQuadric(ring);
	    return ringID;
	} catch (IOException e) {
	    javax.swing.JOptionPane.showMessageDialog(null, e);
	    return 0;
	}
    }

    /**
     * Creates an asteroid
     * 
     * @author Flavien Everaert
     * @param gl
     *            : GL2
     * @param size
     *            : size of the asteroid
     * @return int - the asteroid ID
     */
    private int asteroid(GL2 gl, float size) {
	GLUquadric planet = glu.gluNewQuadric();
	glu.gluQuadricDrawStyle(planet, GLU.GLU_FILL);
	glu.gluQuadricNormals(planet, GLU.GLU_SMOOTH);
	glu.gluQuadricOrientation(planet, GLU.GLU_OUTSIDE);

	int planetID = gl.glGenLists(1);
	gl.glNewList(planetID, GL2.GL_COMPILE);

	glu.gluSphere(planet, size, 50, 50);

	gl.glEndList();
	glu.gluDeleteQuadric(planet);
	return planetID;

    }

    /**
     * Creates the skybox (giant sphere)
     * 
     * @author Flavien Everaert
     * @param gl
     *            : GL2
     * @param texture
     *            : String - texture to map to the sphere
     * @param size
     *            : float - size of the sphere
     * @return int - the sphere ID
     */
    private int skybox(GL2 gl, String texture, float size) {
	try {
	    Texture tex = TextureIO.newTexture(new File("data/" + texture), true);
	    GLUquadric planet = glu.gluNewQuadric();
	    glu.gluQuadricDrawStyle(planet, GLU.GLU_FILL);
	    glu.gluQuadricTexture(planet, true);
	    glu.gluQuadricNormals(planet, GLU.GLU_SMOOTH);
	    glu.gluQuadricOrientation(planet, GLU.GLU_INSIDE);

	    int planetID = gl.glGenLists(1);
	    gl.glNewList(planetID, GL2.GL_COMPILE);

	    tex.bind(gl);
	    glu.gluSphere(planet, size, 50, 50);

	    gl.glEndList();
	    glu.gluDeleteQuadric(planet);
	    return planetID;
	} catch (IOException e) {
	    javax.swing.JOptionPane.showMessageDialog(null, e);
	    return 0;
	}
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
	final GL2 gl = drawable.getGL().getGL2();
	if (height <= 0)
	    height = 1;
	final float h = (float) width / (float) height;
	gl.glViewport(0, 0, width, height);

    }

    /**
     * Though I'm past one hundred thousand miles I'm feeling very still And I
     * think my spaceship knows which way to go Tell my wife I love her very
     * much she knows Ground Control to Major Tom Your circuit's dead, there's
     * something wrong Can you hear me, Major Tom? Can you hear me, Major Tom?
     * Can you hear me, Major Tom? Can you "Here am I floating 'round my tin can
     * Far above the moon Planet Earth is blue And there's nothing I can do"
     */

    @Override
    public void keyTyped(KeyEvent e) {
	// TODO Auto-generated method stub

    }

    @Override
    public void keyPressed(KeyEvent e) {

	switch (e.getKeyCode()) {
	case KeyEvent.VK_LEFT:
	    break;
	case KeyEvent.VK_RIGHT:
	    break;
	case KeyEvent.VK_UP:
		POV_orientation += POV_rotation_speed;
		lx = (float) Math.sin(Math.toRadians(POV_orientation));
		lz = (float) -Math.cos(Math.toRadians(POV_orientation));
	    break;
	case KeyEvent.VK_DOWN:
		POV_orientation -= POV_rotation_speed;
		lx = (float) Math.sin(Math.toRadians(POV_orientation));
		lz = (float) -Math.cos(Math.toRadians(POV_orientation));
	    break;
	case KeyEvent.VK_BACK_SPACE:
	    eyeX = -1555;
	    eyeY = 0;
	    eyeZ = 239;
	    POV_orientation = 75;
	    lx = 0;
	    lz = -1;
	    break;
	default:
	    break;
	}
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
    
    
    
    
    @Override
    public void mousePressed(MouseEvent e) {
	m_x = e.getXOnScreen();
	m_y = e.getYOnScreen();
	move = true;
	}
    
    @Override
	public void mouseReleased(MouseEvent arg0) {
	move = false;
	}
    
	@Override
	public void mouseDragged(MouseEvent e) {
	if (move) {
	    phi = m_x - e.getX();
	    theta = m_y - e.getY();
	    if(phi > 90) phi = 90;
	    if(phi < -90) phi = -90;
	    if(theta > 360) theta -= 360;
	    if(theta < 0) theta += 360;
	    direction[0] = (float) (Math.cos(Math.toRadians(phi)) * Math.sin(Math.toRadians(theta)));
	    direction[1] = (float) Math.sin(Math.toRadians(phi));
	    direction[2] = (float) (Math.cos(Math.toRadians(phi)) * Math.cos(Math.toRadians(theta)));
	}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
	}
    
	@Override
	public void mouseClicked(MouseEvent e)
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
		// TODO Auto-generated method stub
	}
    
	public void mouseWheelMoved(MouseWheelEvent e) {
    if (e.getWheelRotation() < 0) {
    	eyeX += lx * POV_speed;
	    eyeZ += lz * POV_speed;
    } else {
    	eyeX -= lx * POV_speed;
	    eyeZ -= lz * POV_speed;
    }
	}
    
    /**
     * Returns a random float between min and max
     * 
     * @author Flavien Everaert
     * @param min
     *            : float - min limit for random
     * @param max
     *            : float - max limit for random
     * @return float - the random number
     */
    public float random(float min, float max) {
	Random random = new Random();
	double range = max - min;
	double scaled = random.nextDouble() * range;
	double shifted = scaled + min;
	return (float) shifted;
    }



}
