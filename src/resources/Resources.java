package resources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;

import errorHandler.ErrorHandler;
import tables.IntermediateFileTable;
import tables.LineComponentTable;
import tables.ListingFileTable;
import tables.OperationTable;
import tables.SymbolTable;

public class Resources extends Observable {
	// from fileFacade
	private ListingFileTable listingTable;
	private IntermediateFileTable intermediateTable;

	/*
	 * private WriteIntermediateFile intermediateFileWriter; private
	 * WriteListingFile listingFileWriter; private ReadAssemblyFile
	 * assemblyFileReader; private ReadSicFile sicFileReader;
	 */
	//
	// from sic.java
	private OperationTable opTable;// static object.
	private int inc;

	public int getInc() {
		return inc;
	}

	public void setInc(int inc) {
		this.inc = inc;
	}

	private SymbolTable symTable;
	private HashMap<String, String> symbolMap;
	private ArrayList<String> AssemblyFileInstruction;
	private ArrayList<String> fileAsItIs;
	private HashMap<String, String> operationCode;
	private ErrorHandler errorHandler;
	private HashMap<String, String> SymbolMapAsItIs;
	//
	// form passOne
	private int programLen;
	private HashMap<String, String> locationCounters;
	private ArrayList<String> addresses;
	private int maxLenOfLocCounter;
	private int curLocationCounter;
	private String progName;
	private String startingAddress;
	private ArrayList<String> literals;
	private HashMap<String, String> literalMap;
	private int k;

	// regex instructions
	private LineComponentTable lineComponentTable;

	public Resources() {
		// TODO Auto-generated constructor stub
		inc = 0;
		this.literalMap = new HashMap<>();
		k = 0;
		addresses = new ArrayList<>();
	}

	public ArrayList<String> getAddresses() {
		return addresses;
	}

	public void setAddresses(ArrayList<String> addresses) {
		this.addresses = addresses;
	}

	public LineComponentTable getLineComponentsTable() {
		return lineComponentTable;
	}

	public void setLineComponentsTable(LineComponentTable lineComponentTable) {
		this.lineComponentTable = lineComponentTable;
	}

	public ListingFileTable getListingTable() {
		return listingTable;
	}

	public void setListingTable(ListingFileTable listingTable) {
		this.listingTable = listingTable;
	}

	public IntermediateFileTable getIntermediateTable() {
		return intermediateTable;
	}

	public void setIntermediateTable(IntermediateFileTable intermediateTable) {
		this.intermediateTable = intermediateTable;
	}

	public OperationTable getOpTable() {
		return opTable;
	}

	public void setOpTable(OperationTable opTable) {
		this.opTable = opTable;
	}

	public SymbolTable getSymTable() {
		return symTable;
	}

	public void setSymTable(SymbolTable symTable) {
		this.symTable = symTable;
	}

	public ArrayList<String> getAssemblyFileInstruction() {
		return AssemblyFileInstruction;
	}

	public void setAssemblyFileInstruction(ArrayList<String> assemblyFileInstruction) {
		AssemblyFileInstruction = assemblyFileInstruction;
	}

	public ArrayList<String> getFileAsItIs() {
		return fileAsItIs;
	}

	public void setFileAsItIs(ArrayList<String> fileAsItIs) {
		this.fileAsItIs = fileAsItIs;
	}

	public HashMap<String, String> getOperationCode() {
		return operationCode;
	}

	public void setOperationCode(HashMap<String, String> operationCode) {
		this.operationCode = operationCode;
	}

	public ErrorHandler getErrorHandler() {
		return errorHandler;
	}

	public void setErrorHandler(ErrorHandler errorHandler) {
		this.errorHandler = errorHandler;
	}

	public int getProgramLen() {
		return programLen;
	}

	public void setProgramLen(int programLen) {
		this.programLen = programLen;
	}

	public HashMap<String, String> getLocationCounters() {
		return locationCounters;
	}

	public void setLocationCounters(HashMap<String, String> locationCounters) {
		this.locationCounters = locationCounters;
	}

	public int getMaxLenOfLocCounter() {
		return maxLenOfLocCounter;
	}

	public void setMaxLenOfLocCounter(int maxLenOfLocCounter) {
		this.maxLenOfLocCounter = maxLenOfLocCounter;
	}

	public String getProgName() {
		return progName;
	}

	public void setProgName(String progName) {
		this.progName = progName;
	}

	public String getStartingAddress() {
		return startingAddress;
	}

	public void setStartingAddress(String startingAddress) {
		this.startingAddress = startingAddress;
	}

	public ArrayList<String> getLiterals() {
		return literals;
	}

	public void setLiterals(ArrayList<String> literals) {
		this.literals = literals;
	}

	public int getCurLocationCounter() {
		return curLocationCounter;
	}

	public void setCurLocationCounter(int curLocationCounter) {
		this.curLocationCounter = curLocationCounter;
		this.setChanged();
		notifyObservers("no");
	}

	public HashMap<String, String> getLiteralMap() {
		return literalMap;
	}

	public void setLiteralMap(HashMap<String, String> literalMap) {
		this.literalMap = literalMap;
		this.setChanged();
		notifyObservers("yes");
	}

	public int getK() {
		return k;
	}

	public void setK(int k) {
		this.k = k;
	}

	public HashMap<String, String> getSymbolMap() {
		return symbolMap;
	}

	public void setSymbolMap(HashMap<String, String> symbolMap) {
		this.symbolMap = symbolMap;
	}

	public HashMap<String, String> getSymbolMapAsItIs() {
		return SymbolMapAsItIs;
	}

	public void setSymbolMapAsItIs(HashMap<String, String> symbolMapAsItIs) {
		SymbolMapAsItIs = symbolMapAsItIs;
	}

}
