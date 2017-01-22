package CSV;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import CelestialObjects.Satellite;

/**
 * Class acting as reader of Earth satellite data.
 * @author Lecointre
 *
 */
public class EarthSatellites extends CSVReader {

    private long earthMass;

    public EarthSatellites(long mass) {
	this.earthMass = mass;
    }

    /**
     * Reads satellite data for Earth.
     * 
     * @author Lecointre
     */
    @Override
    public void readCSV() {
	BufferedReader br;
	try {
	    br = new BufferedReader(new FileReader(path + "satellitesterre.csv"));
	    String line = br.readLine();
	    line = br.readLine();
	    String satelliteData[] = line.split(";");
	    long radius = (long)Float.parseFloat(satelliteData[1]) * 1000;
	    long distanceToPlanet = (long)Float.parseFloat(satelliteData[2]) * 1000;
	    long orbitalSpeed = (long)Float.parseFloat(satelliteData[3]) * 1000;
	    Satellite satellite = new Satellite(satelliteData[0], radius, distanceToPlanet, orbitalSpeed);
	    celestialObjects.put(satelliteData[0], satellite);
	} catch (

	FileNotFoundException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

}
