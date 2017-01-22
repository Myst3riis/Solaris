package CSV;
/**
 * Class acting as reader of Uranus satellite data.
 * @author Lecointre
 *
 */
public class UranusSatellites extends CSVReader {

    private long uranusMass;

    public UranusSatellites(long mass) {
	this.uranusMass = mass;
    }
    
    /**
     * Reads satellite data for Uranus.
     * @author Lecointre
     */
    @Override
    public void readCSV() {
	readSatellites(path + "satellitesuranus.csv", uranusMass);
    }

}
