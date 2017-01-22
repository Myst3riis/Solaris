package Solaris;

import java.util.HashMap;

import CSV.SolarSystem;
import CelestialObjects.CelestialObject;
import CelestialObjects.Planet;

public class Launcher {

	/**
	 * @author weber
	 * @param args Parameters of the launcher
	 */
	public static void main(String[] args) {
		//SolarisOriginal solarisOriginal = new SolarisOriginal(1000, 1000);
		Solaris solaris = new Solaris(1000,1000);
		//Saturn saturn = new Saturn(600,600);
		//Asteroids asteroids = new Asteroids(600,600);
		/*
		SolarSystem solaris = new SolarSystem();
		
		CelestialObjectAdapter coa = new CelestialObjectAdapter();
		SolarSystem ss = new SolarSystem();
		HashMap<String, CelestialObject> sunAndPlanets = ss.sunAndPlanets();
		Planet mercury = (Planet) sunAndPlanets.get("Mercure");
		float mercuryDistanceToSun = coa.adaptDistance(mercury);
		float mercurySpeed = coa.adaptOrbitalSpeed(mercury);
		System.out.println(mercury.getDistanceToSun());
		System.out.println(mercuryDistanceToSun);
		*/
		
	}

}
