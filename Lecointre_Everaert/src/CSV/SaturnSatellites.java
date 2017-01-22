package CSV;

/**
 * Class acting as reader of Saturn satellite data.
 * @author Lecointre
 *
 */
public class SaturnSatellites extends CSVReader {

    private long saturnMass;

    public SaturnSatellites(long mass) {
	this.saturnMass = mass;
    }

    /**
     * Reads satellite data for Saturn.
     * @author Lecointre
     */
    @Override
    public void readCSV() {
	readSatellites(path + "satellitessaturne.csv", saturnMass);
    }

}
