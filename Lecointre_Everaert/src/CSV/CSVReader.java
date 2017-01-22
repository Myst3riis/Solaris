package CSV;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Random;

import CelestialObjects.CelestialObject;
import CelestialObjects.Satellite;

/**
 * Abstract class containing method to read partial satellite data from csv file. <br/>
 * Directs child classes to correct file path of csv files. <br/>
 * Specifies cell seperator. <br/>
 * Contains data structure in which data read from csv is put.
 * 
 * @author Lecointre
 *
 */
public abstract class CSVReader {

    HashMap<String, CelestialObject> celestialObjects = new HashMap<String, CelestialObject>();
    
    protected String path = "info/";
    protected final String cellSeperator = ";";

    public abstract void readCSV();
    
    public CelestialObject getCelestialObject(String name) {
	return celestialObjects.get(name);
    }
    
    /**
     * Reads partial satellite data from csv files. Missing data is randomly generated in a way to maintain some scientific accuracy.
     * @param fullPath Path of csv file.
     * @param planetMass Mass of planet around which satellites orbit. This parameter is used to calculate different orbital speed of satellites.
     * @author Lecointre
     */
    public void readSatellites(String fullPath, long planetMass) {
	final double G = 6.67408 * Math.pow(10, -11); // Gravitational constant
	Random rand = new Random();
	BufferedReader br;
	try {
	    br = new BufferedReader(new FileReader(fullPath));
	    String line = br.readLine();
	    while ((line = br.readLine()) != null) {
		String satelliteData[] = line.split(";");
		String name = satelliteData[0];
		long radius = Math.abs(rand.nextLong()) % 2500000 + 5000; // Bounds?
		long distanceToPlanet = Long.valueOf(satelliteData[1]) * 1000; // distance converted from kilometers into meters
		long orbitalSpeed = (long) Math.sqrt(G * planetMass / distanceToPlanet); 
		Satellite satellite = new Satellite(name, radius, distanceToPlanet, orbitalSpeed);
		celestialObjects.put(name, satellite);
	    }
	} catch (FileNotFoundException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }
}
