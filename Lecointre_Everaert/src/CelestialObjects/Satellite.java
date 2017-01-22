package CelestialObjects;

/**
 * Class representing satellites of planets.
 * @author Lecointre
 *
 */
public class Satellite extends CelestialObject {
    
    public Satellite(String name, long radius, long distanceToPlanet, long orbitalSpeed) {
	this.name = name;
	this.radius = radius;
	this.distanceToPlanet = distanceToPlanet;
	this.numberOfSatellites = 0;
	this.orbitalSpeed = orbitalSpeed;
    }

    private long distanceToPlanet; // m

    public long getDistanceToPlanet() {
	return distanceToPlanet;
    }

}
