package CSV;

public abstract class CSVReader {
	
	protected final String path = "info/";
	protected final String cellSeperator = ";";
	
	public abstract void readCSV();

}
