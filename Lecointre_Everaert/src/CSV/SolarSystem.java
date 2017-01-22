package CSV;

import java.util.HashMap;

import CelestialObjects.CelestialObject;
import CelestialObjects.Planet;

/**
 * Class acting as reader csv data.
 * @author Lecointre
 *
 */
public class SolarSystem {

    private SunAndPlanets sap;
    private JupiterSatellites js;
    private MarsSatellites ms;
    private NeptuneSatellites ns;
    private PlutoSatellites ps;
    private SaturnSatellites ss;
    private EarthSatellites es;
    private UranusSatellites us;
    
    /**
     * Reads complete data set for all celestial objects in the solar system.
     * @author Lecointre
     */
    public SolarSystem() {
	
	sap = new SunAndPlanets();
	sap.readCSV();

	Planet jupiter = (Planet) sap.getCelestialObject("Jupiter");
	this.js = new JupiterSatellites(jupiter.getMass());
	js.readCSV();

	Planet mars = (Planet) sap.getCelestialObject("Mars");
	this.ms = new MarsSatellites(mars.getMass());
	ms.readCSV();

	Planet neptune = (Planet) sap.getCelestialObject("Neptune");
	this.ns = new NeptuneSatellites(neptune.getMass());
	ns.readCSV();

	Planet pluto = (Planet) sap.getCelestialObject("Pluton");
	this.ps = new PlutoSatellites(pluto.getMass());
	ps.readCSV();

	Planet saturn = (Planet) sap.getCelestialObject("Saturne");
	this.ss = new SaturnSatellites(saturn.getMass());
	ss.readCSV();

	Planet earth = (Planet) sap.getCelestialObject("Terre");
	this.es = new EarthSatellites(earth.getMass());
	es.readCSV();

	Planet uranus = (Planet) sap.getCelestialObject("Uranus");
	this.us = new UranusSatellites(uranus.getMass());
	us.readCSV();
    }

    public HashMap<String, CelestialObject> sunAndPlanets() {
	return sap.celestialObjects;
    }

    public HashMap<String, CelestialObject> jupiterSatellites() {
	return js.celestialObjects;
    }
    
    public HashMap<String, CelestialObject> marsSatellites() {
	return ms.celestialObjects;
    }
    
    public HashMap<String, CelestialObject> neptuneSatellites() {
	return ns.celestialObjects;
    }
    
    public HashMap<String, CelestialObject> plutoSatellites() {
	return ps.celestialObjects;
    }
    
    public HashMap<String, CelestialObject> saturnSatellites() {
	return ss.celestialObjects;
    }
    
    public HashMap<String, CelestialObject> earthSatellites() {
	return es.celestialObjects;
    }
    
    public HashMap<String, CelestialObject> uranusSatellites() {
	return us.celestialObjects;
    }
}
