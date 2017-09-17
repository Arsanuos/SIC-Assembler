package fileHandler;

import java.util.ArrayList;
import java.util.HashMap;

import tables.IntermediateFileTable;
import tables.ListingFileTable;

public class FileFacade {

	private ListingFileTable listingTable;
	private IntermediateFileTable intermediateTable;
	private WriteIntermediateFile intermediateFileWriter;
	private WriteListingFile listingFileWriter;
	private ReadAssemblyFile assemblyFileReader;
	private ReadSicFile sicFileReader;

	public FileFacade() {
		// TODO Auto-generated constructor stub
	}

	public ArrayList<String> readAssemblyFile(String fileName) {
		assemblyFileReader = new ReadAssemblyFile();
		ArrayList<String> assemblyFile = assemblyFileReader.readAssemblyFile(fileName);
		return assemblyFile;
	}

	public HashMap<String, String> readSicOperationFile(String fileName) {
		sicFileReader = new ReadSicFile();
		HashMap<String, String> sicTable = sicFileReader.readSicOperationFile(fileName);
		return sicTable;
	}

	public boolean writeIntermediateFile(IntermediateFileTable intermediateTable) {
		this.intermediateTable = intermediateTable;
		intermediateFileWriter = new WriteIntermediateFile(this.intermediateTable);
		return intermediateFileWriter.writeIntermediateFile();
	}

	public boolean writeListingFile(ListingFileTable listingTable) {
		this.listingTable = listingTable;
		this.listingFileWriter = new WriteListingFile(this.listingTable);
		return listingFileWriter.writeListingFile();
	}

	public HashMap<String, String> getSicTable() {
		return this.sicFileReader.getSicTable();
	}

	public ArrayList<String> getAssemblyFile() {
		return this.assemblyFileReader.getAssembleyCode();
	}

	public ArrayList<String> getFileAsItIS() {
		return this.assemblyFileReader.getFileAsItIS();
	}
}
