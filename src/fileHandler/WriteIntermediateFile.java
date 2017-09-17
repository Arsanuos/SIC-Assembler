package fileHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import tables.IntermediateFileTable;
import tables.SymbolTable;

public class WriteIntermediateFile {

	private ArrayList<String> assemblyFile;
	private int maxLenOfLocCounter;
	private ArrayList<String> locationCounters;
	private SymbolTable symbolTable;
	private ArrayList<String> errors;
	private PrintWriter printWriter;

	// without object code
	public WriteIntermediateFile(IntermediateFileTable intermediateFile) {
		this.maxLenOfLocCounter = intermediateFile.getMaxLenOfLocCounter();
		this.locationCounters = intermediateFile.getLocationCounters();
		this.assemblyFile = intermediateFile.getAssemblyFile();
		this.symbolTable = intermediateFile.getSymbolTable();
		this.errors = intermediateFile.getErrors();
	}

	public boolean writeIntermediateFile() {
		File intermediateFile = new File(System.getProperty("user.dir") + File.separator + "intermediateFile.txt");
		boolean errorFlag = false;
		try {
			printWriter = new PrintWriter(intermediateFile);
			for (int i = 0; i < assemblyFile.size(); i++) {
				if (getOperation(assemblyFile.get(i)).equalsIgnoreCase("end")) {
					String hexLocation = "";
					for (int j = hexLocation.length() - 1; j < this.maxLenOfLocCounter; j++) {
						hexLocation = " " + hexLocation;
					}
					if (errors.get(i).equals(" ")) {
						printWriter.println(hexLocation + " " + assemblyFile.get(i));
					} else {
						printWriter.println(hexLocation + " " + assemblyFile.get(i));
					}
				} else if (errors.get(i).equals(" ")) {
					String hexLocation = this.locationCounters.get(i);
					for (int j = hexLocation.length() - 1; j < this.maxLenOfLocCounter; j++) {
						hexLocation = "0" + hexLocation;
					}
					printWriter.write(hexLocation + " ");
					printWriter.println(assemblyFile.get(i));
				} else {
					printWriter.println(errors.get(i));
					errorFlag = true;
				}
			}
			if (!errors.get(errors.size() - 1).equalsIgnoreCase(" ")) {
				printWriter.println(this.errors.get(errors.size() - 1));
				errorFlag = true;
			}
			writeSymbolTable();
			printWriter.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return errorFlag;
	}

	private void writeSymbolTable() {

		TableWriter tableWriter = TableWriter.getInstance(true, printWriter);
		ArrayList<String> columnNames = new ArrayList<>();
		columnNames.add("Symbol Name");
		columnNames.add("Address(in hex)");
		tableWriter.printTable(columnNames, this.symbolTable.getData(), "SymbolTable");

	}

	public String getOperation(String line) {
		try {
			return line.substring(9, 15).trim();
		} catch (Exception e) {
			String x = new String();
			for (int i = 9; i < line.length(); i++) {
				x += line.charAt(i);
			}
			return x.trim();
		}
	}

}
