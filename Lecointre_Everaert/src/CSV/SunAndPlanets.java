package CSV;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import CelestialObjects.Planet;
import CelestialObjects.Sun;

/**
 * Class acting as reader of sun and planets data.
 * @author Lecointre
 *
 */
public class SunAndPlanets extends CSVReader {
    
    /**
     * Reads data from csv file for sun and planets. 
     * @author Lecointre
     */
    @Override
    public void readCSV() {
	BufferedReader br;
	try {
	    br = new BufferedReader(new FileReader(path + "soleiletplanetes.csv"));

	    // Read data for Sun
	    String line = br.readLine();
	    line = br.readLine();
	    String sunData[] = line.split(";");
	    Sun sun = new Sun(sunData[0], Long.parseLong(sunData[1]) * 1000, sunData[3]);
	    celestialObjects.put(sunData[0], sun);

	    // Read data for Planets
	    while ((line = br.readLine()) != null) {
		String planetData[] = line.split(";");
		long radius = (long)Float.parseFloat(planetData[1]) * 1000; // Convert km into m
		long distanceToSun = (long)Float.parseFloat(planetData[2]) * 1000; // Convert km into m
		long orbitalSpeed = (long)Float.parseFloat(planetData[4]) * 1000; // Convert km/s into m/s
		Planet planet = new Planet(planetData[0], radius, distanceToSun, planetData[3], orbitalSpeed, planetData[5]);
		celestialObjects.put(planetData[0], planet);
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
