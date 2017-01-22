package CSV;

/**
 * Class acting as reader of Pluto satellite data.
 * @author Lecointre
 *
 */
public class PlutoSatellites extends CSVReader {

    private long plutoMass;

    public PlutoSatellites(long mass) {
	this.plutoMass = mass;
    }

    /**
     * Reads satellite data for Pluto.
     * @author Lecointre
     */
    @Override
    public void readCSV() {
	readSatellites(path + "satellitespluton.csv", plutoMass);
    }

}
