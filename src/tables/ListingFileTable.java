package tables;

import java.util.ArrayList;
import InstructionMode.Sic;
import resources.Resources;

public class ListingFileTable {

	private ArrayList<String> assemblyFile;
	private int maxLenOfLocCounter;
	private ArrayList<String> locationCounters;
	private ArrayList<String> errors;

	// with object code.
	public ListingFileTable(Resources resources) {
		this.maxLenOfLocCounter = resources.getMaxLenOfLocCounter();
		this.locationCounters = resources.getAddresses();
		this.assemblyFile = resources.getFileAsItIs();
		this.errors = resources.getErrorHandler().getErrorInPassTwo();
	}

	public ArrayList<String> getAssemblyFile() {
		return assemblyFile;
	}

	public int getMaxLenOfLocCounter() {
		return maxLenOfLocCounter;
	}

	public ArrayList<String> getLocationCounters() {
		return locationCounters;
	}

	public ArrayList<String> getObjectCode() {
		return Sic.getInstance().getOpCode();
	}

	public ArrayList<String> getErrors() {
		return errors;
	}
}
