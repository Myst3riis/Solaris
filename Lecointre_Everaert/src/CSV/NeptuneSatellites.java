package CSV;

/**
 * Class acting as reader of Neptune satellite data.
 * @author Lecointre
 *
 */
public class NeptuneSatellites extends CSVReader {

    private long neptuneMass;

    public NeptuneSatellites(long mass) {
	this.neptuneMass = mass;
    }
    
    /**
     * Reads satellite data for Neptune.
     * @author Lecointre
     */
    @Override
    public void readCSV() {
	readSatellites(path + "satellitesneptune.csv", neptuneMass);
    }

}
