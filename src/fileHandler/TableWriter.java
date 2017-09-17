package fileHandler;

import java.io.PrintWriter;
import java.util.ArrayList;

public class TableWriter implements TableWriterIF {

	private static TableWriter instancePrinter;
	private boolean active;
	private PrintWriter printWriter;

	private TableWriter(boolean active, PrintWriter printWriter) {
		this.active = active;
		this.printWriter = printWriter;
	}

	@Override
	public void printTable(ArrayList<String> columnNames, ArrayList<ArrayList<String>> data, String tableName) {
		if (tableName == null || !this.active) {
			return;
		}
		printWriter.println();
		printWriter.println();
		printWriter.println(tableName + ":");
		data.add(0, columnNames);
		ArrayList<Integer> width = findMaxWidth(data);
		for (int i = 0; i < data.size(); i++) {
			printLine(width);
			printWriter.print("| ");
			printRow(width, data.get(i), columnNames.size());
		}
		printLine(width);
		printWriter.println();
	}

	private ArrayList<Integer> findMaxWidth(ArrayList<ArrayList<String>> data) {
		ArrayList<Integer> width = new ArrayList<Integer>();
		int max = 0;
		for (int i = 0; i < data.get(0).size(); i++) {
			for (int j = 0; j < data.size(); j++) {
				if (i < data.get(j).size() && data.get(j).get(i) != null && max < data.get(j).get(i).length()) {
					max = data.get(j).get(i).length();
				}
			}
			width.add(max + 2);
			max = 0;
		}
		return width;
	}

	private void printLine(ArrayList<Integer> width) {
		printWriter.print("+");
		for (int i = 0; i < width.size(); i++) {
			for (int j = 0; j < width.get(i); j++) {
				printWriter.print("-");
			}
			printWriter.print("+");
		}
		printWriter.println();
	}

	private void printRow(ArrayList<Integer> width, ArrayList<String> row, int columnNumber) {
		for (int i = 0; i < columnNumber; i++) {
			if (i < row.size()) {
				printWriter.print(row.get(i));
				for (int j = 0; j < width.get(i) - row.get(i).length() - 1; j++) {
					printWriter.print(" ");
				}
				printWriter.print("| ");
			} else {
				for (int j = 0; j < width.get(i) - 1; j++) {
					printWriter.print(" ");
				}
				printWriter.print("| ");
			}
		}
		printWriter.println();
	}

	public static TableWriter getInstance(boolean active, PrintWriter printWriter) {
		if (instancePrinter == null) {
			// make it true to make it active and print
			instancePrinter = new TableWriter(active, printWriter);
		}
		return instancePrinter;
	}

}
