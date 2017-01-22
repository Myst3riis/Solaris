package CelestialObjects;

/**
 * Class representing the Sun.
 * @author Lecointre
 *
 */
public class Sun extends CelestialObject {

    public Sun(String name, long radius, String numberOfSatellites) {
	this.name = name;
	this.radius = radius/5;
	this.numberOfSatellites = Integer.parseInt(numberOfSatellites);
	this.orbitalSpeed = 0;
	this.mass = (long) (2 * Math.pow(10, 30));
    }

    private final long distanceToSun = 0; // m
    
    public long getDistanceToSun() {
        return distanceToSun;
    }
    
}
