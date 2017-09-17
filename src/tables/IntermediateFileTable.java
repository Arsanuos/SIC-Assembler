package tables;

import java.util.ArrayList;
import resources.Resources;

public class IntermediateFileTable {

	private ArrayList<String> assemblyFile;
	private int maxLenOfLocCounter;
	private ArrayList<String> locationCounters;
	private SymbolTable symbolTable;
	private ArrayList<String> errors;

	public IntermediateFileTable(Resources resources) {
		// TODO Auto-generated constructor stub
		this.maxLenOfLocCounter = resources.getMaxLenOfLocCounter();
		this.locationCounters = resources.getAddresses();
		this.assemblyFile = resources.getFileAsItIs();
		this.symbolTable = resources.getSymTable();
		this.errors = resources.getErrorHandler().getErrorInPassOne();
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

	public SymbolTable getSymbolTable() {
		return symbolTable;
	}

	public ArrayList<String> getErrors() {
		return errors;
	}
}
