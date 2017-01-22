package CelestialObjects;

/**
 * Class representing planets.
 * @author Lecointre
 *
 */
public class Planet extends CelestialObject {

    public Planet(String name, long radius, long distanceToSun, String numberOfSatellites, long orbitalSpeed, String mass) {
	this.name = name;
	this.radius = radius;
	this.distanceToSun = distanceToSun;
	this.numberOfSatellites = Integer.parseInt(numberOfSatellites);
	this.orbitalSpeed = orbitalSpeed; 
	String massData[] = mass.split("-");
	this.mass = (long) (Integer.parseInt(massData[0])
		* Math.pow(Double.parseDouble(massData[1]), Double.parseDouble(massData[2])));
    }

    protected long distanceToSun; // m

    public long getDistanceToSun() {
	return distanceToSun;
    }
    
}
