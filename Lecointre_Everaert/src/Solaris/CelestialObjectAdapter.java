package Solaris;

import CelestialObjects.CelestialObject;
import CelestialObjects.Planet;
import CelestialObjects.Satellite;

/**
 * Class acting as an adapter of celestial objects for OpenGL 3D scene.
 * 
 * @author Lecointre
 *
 */
public class CelestialObjectAdapter {

    private float distanceResizeFactor = (int) Math.pow(10, 12);
    private float speedFactor = 10000;
    private float radiusResizeFactor = 1000000;

    /**
     * Adapts celestial object's orbital speed.
     * 
     * @author Lecointre
     * @param obj
     *            Celestial object whose orbital speed needs adapting.
     * @return Adapted orbital speed.
     */
    public float adaptOrbitalSpeed(CelestialObject obj) {
	
	if(obj.getClass().getSimpleName().equals("Satellite"))
	    return (obj.getOrbitalSpeed() * 100) / speedFactor;
	return obj.getOrbitalSpeed() / speedFactor;
	
    }

    /**
     * Adapts celestial object's distance.
     * 
     * @author Lecointre
     * @param obj
     *            Celestial object whose distance, to the celestial object it
     *            orbits around, needs adapting.
     * @return Adapted distance.
     */
    public float adaptDistance(CelestialObject obj) {
	if (obj.getClass().getSimpleName().equals("Sun")) {
	    return 0;
	}
	if (obj.getClass().getSimpleName().equals("Planet")) {
	    float distance = ((Planet) obj).getDistanceToSun() / distanceResizeFactor;
	    return distance;
	}
	if (obj.getClass().getSimpleName().equals("Satellite")) {
	    float distance = ((Satellite) obj).getDistanceToPlanet() * 50 / distanceResizeFactor;
	    return distance;
	}
	return 0;
    }

    /**
     * Adapts celestial object's radius.
     * 
     * @author Lecointre
     * @param obj
     *            Celestial object whose radius needs adapting.
     * @return
     */
    public float adaptRadius(CelestialObject obj) {
	if (obj.getClass().getSimpleName().equals("Sun")) {
	    return obj.getRadius() / (5 * radiusResizeFactor); // Sun is really too big proportionally to the other planets...
	}

	return obj.getRadius() / radiusResizeFactor;
    }

    /**
     * Increments speed factor.
     * 
     * @author Lecointre
     */
    public void incrementResizeFactor() {
	speedFactor++;
    }

    /**
     * Decrements speed factor as long as actual resize factor is positive.
     * 
     * @author Lecointre
     */
    public void decrementResizeFactor() {
	if (speedFactor > 0) {
	    speedFactor--;
	}
    }
}
