package Objet3D;


public class Objet3D
{
	public static void main(String[] args){
		//Dice de = new Dice(600,600);
		
		
		//Sphere sphere = new Sphere(600,600);
		Sphere earth = new Sphere(600,600,"EarthMap.jpg");
		Sphere moon = new Sphere(600,600,"MoonMap.jpg");
		Sphere mars = new Sphere(600,600, "MarsMap.jpg");
		Sphere neptune = new Sphere(600,600, "Neptune.jpg");
		Sphere jupiter = new Sphere(600,600, "JupiterMap.jpg");
		
		
		//EnlightedDice deLumineux = new EnlightedDice(600,600);
	}
}
