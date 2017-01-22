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

	private int distanceResizeFactor = (int) Math.pow(10, 9);
	private int speedFactor = 20000;
	private int radiusResizeFactor = 10000;

	/**
	 * Adapts celestial object's orbital speed.
	 * 
	 * @author Lecointre
	 * @param obj
	 *            Celestial object whose orbital speed needs adapting.
	 * @return Adapted orbital speed.
	 */
	public float adaptOrbitalSpeed(CelestialObject obj) {
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
			// ?
			return distance;
		}
		if (obj.getClass().getSimpleName().equals("Satellite")) {
			float distance = ((Satellite) obj).getDistanceToPlanet() / distanceResizeFactor;
			// ?
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
