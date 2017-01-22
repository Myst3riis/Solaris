package CelestialObjects;

/**
 * Abstract class representing celestial objects.
 * @author Lecointre
 *
 */
public abstract class CelestialObject {
    
    protected String name;
    protected long radius; // m
    protected int numberOfSatellites;
    protected long orbitalSpeed; // m/s
    protected long mass; // kg
    
    public long getOrbitalSpeed() {
        return orbitalSpeed;
    }

    public String getName() {
        return name;
    }
    
    public long getRadius() {
        return radius;
    }
    
    public int getNumberOfSatellites() {
        return numberOfSatellites;
    }

    public long getMass() {
        return mass;
    }

}
