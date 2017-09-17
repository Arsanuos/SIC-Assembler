package fileHandler;

import java.util.ArrayList;

public interface TableWriterIF {

	public void printTable(ArrayList<String> columnNames, ArrayList<ArrayList<String>> data, String tableName);
}
