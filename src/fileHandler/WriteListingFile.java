package fileHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import tables.ListingFileTable;
import utility.InstructionHandler;

public class WriteListingFile {

	private ArrayList<String> assemblyFile;
	private int maxLenOfLocCounter;
	private ArrayList<String> locationCounters;
	private ArrayList<String> objectCode;
	private ArrayList<String> errors;

	// with object code.
	public WriteListingFile(ListingFileTable listingFile) {
		this.maxLenOfLocCounter = listingFile.getMaxLenOfLocCounter();
		this.locationCounters = listingFile.getLocationCounters();
		this.assemblyFile = listingFile.getAssemblyFile();
		this.objectCode = listingFile.getObjectCode();
		this.errors = listingFile.getErrors();
	}

	public boolean writeListingFile() {
		File intermediateFile = new File(System.getProperty("user.dir") + File.separator + "ListingFile.txt");
		int k = 0;
		boolean errorFlag = false;
		try {
			PrintWriter printWriter = new PrintWriter(intermediateFile);
			for (int i = 0; i < assemblyFile.size(); i++) {
				if (InstructionHandler.getOperation(assemblyFile.get(i)).equalsIgnoreCase("end")) {
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
					String printed = this.locationCounters.get(i);
					for (int j = printed.length() - 1; j < this.maxLenOfLocCounter; j++) {
						printed = "0" + printed;
					}
					printed += "  ";
					printed += assemblyFile.get(i);
					if (!this.objectCode.get(i).equals(" ") && !this.objectCode.get(i).equals("/")) {
						String space = new String();
						// line may contains a comment so its length may be 66
						for (int t = printed.length() - 1; t <= 40; t++) {
							space += " ";
						}
						printed += space;
						printWriter.print(printed);
						int p1 = 0, p2 = 6;
						if (objectCode.get(i).length() > 6) {
							printed = this.objectCode.get(i).substring(p1, p2);
							printWriter.println(printed);
							p1 = p2;
							p2 += 6;
							space = "";
							for (int j = 0; j < 38 + maxLenOfLocCounter; j++) {
								space += " ";
							}
							while (p1 < objectCode.get(i).length() && p2 < objectCode.get(i).length()) {
								printed = this.objectCode.get(i).substring(p1, p2);
								printWriter.println(space + printed);
								p1 = p2;
								p2 += 6;
							}
							printed = this.objectCode.get(i).substring(p1, objectCode.get(i).length());
							printWriter.println(space + printed);
						} else {
							printWriter.println(this.objectCode.get(i));
						}
					} else {
						printWriter.println(printed);
					}
				} else {
					printWriter.println(errors.get(i));
					errorFlag = true;
				}
			}
			if (!errors.get(errors.size() - 1).equalsIgnoreCase(" ")) {
				String hexLocation = "";
				for (int j = hexLocation.length() - 1; j < this.maxLenOfLocCounter; j++) {
					hexLocation = " " + hexLocation;
				}
				printWriter.println(this.errors.get(errors.size() - 1));
			}
			printWriter.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return errorFlag;
	}

	private String adjustLen(String line) {
		for (int i = line.length() - 1; i < 40; i++) {
			line = line + " ";
		}
		return line;
	}

	private String addZero(String hexLocation) {
		for (int j = hexLocation.length() - 1; j < this.maxLenOfLocCounter; j++) {
			hexLocation = "0" + hexLocation;
		}
		return hexLocation;
	}

	/*
	 * String printed = new String(); String hexLocation =
	 * this.locationCounters.get(i); hexLocation = addZero(hexLocation); printed
	 * = hexLocation + "  " + assemblyFile.get(i) + "  "; printed =
	 * adjustLen(printed); if (!errors.containsKey(assemblyFile.get(i))) { if
	 * (assemblyFile.get(i).toLowerCase().contains("start") ||
	 * assemblyFile.get(i).toLowerCase().contains( "end")) {
	 * printWriter.println(printed + "  "); } else { printWriter.println(printed
	 * + objectCode.get(k) + "  "); k++; } } else {
	 * printWriter.println(errors.get(assemblyFile.get(i)) + "  "); errorFlag =
	 * true; }
	 */
}
