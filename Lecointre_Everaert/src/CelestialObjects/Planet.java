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
	int front = Integer.parseInt(massData[0]);
	double ten = Double.parseDouble(massData[1]);
	double exponent = Double.parseDouble(massData[2]);
	this.mass = (long) (front * Math.pow(ten, exponent - 11)); // Take 11 off here and add it to the G constant exponent to bypass long.MAX_VALUE
	//System.out.println(this.mass);
    }

    protected long distanceToSun; // m

    public long getDistanceToSun() {
	return distanceToSun;
    }
    
}
