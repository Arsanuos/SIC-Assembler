package fileHandler;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class ReadSicFile {

    private HashMap<String, String> sicTable;

	public ReadSicFile() {
        sicTable = new HashMap<String, String>();
    }

    public HashMap<String, String> readSicOperationFile(String fileName) {
        String line;
        try {
            Scanner scan = new Scanner(new File(fileName));
            while (scan.hasNext()) {
                line = scan.nextLine();
                line = line.toLowerCase();
                String operation = line.substring(0, line.indexOf(" "));
                String opCode = line.substring(line.indexOf(" "), line
                        .length());
                opCode = opCode.trim();
                sicTable.put(operation, opCode.toUpperCase());
            }
            scan.close();
        } catch (IOException e) {
            throw new RuntimeException("Failure in reading instruction file.");
        }
        return sicTable;
    }
    
    public HashMap<String, String> getSicTable() {
		return sicTable;
	}
}
