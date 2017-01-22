package CSV;

/**
 * Class acting as reader of Mars satellite data.
 * @author Lecointre
 *
 */
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

import CelestialObjects.Satellite;

public class MarsSatellites extends CSVReader {

    private long marsMass;

    public MarsSatellites(long mass) {
	this.marsMass = mass;
    }
    
    /**
     * Reads satellite data for Mars.
     * @author Lecointre
     */
    @Override
    public void readCSV() {
	BufferedReader br;
	try {
	    br = new BufferedReader(new FileReader(path + "satellitesmars.csv"));
	    String line = br.readLine();
	    while ((line = br.readLine()) != null) {
		String satelliteData[] = line.split(";");
		long radius = (long)Float.parseFloat(satelliteData[1]) * 1000;
		long distanceToPlanet = (long)Float.parseFloat(satelliteData[2]) * 1000;
		long orbitalSpeed = (long)Float.parseFloat(satelliteData[3]) * 1000;
		Satellite satellite = new Satellite(satelliteData[0], radius, distanceToPlanet, orbitalSpeed);
		celestialObjects.put(satelliteData[0], satellite);
	    }
	} catch (

	FileNotFoundException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

}
