package CSV;

/**
 * Class acting as reader of Juptier satellite data.
 * @author Lecointre
 *
 */
public class JupiterSatellites extends CSVReader {

    private long jupiterMass;

    public JupiterSatellites(long mass) {
	this.jupiterMass = mass;
    }
    
    /**
     * Reads satellite data for Jupiter.
     * @author Lecointre 
     */
    @Override
    public void readCSV() {
	readSatellites(path + "satellitesjupiter.csv", jupiterMass);
    }

}
