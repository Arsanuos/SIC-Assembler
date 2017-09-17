package tables;

import java.util.ArrayList;
import java.util.HashMap;

public class SymbolTable {

	private HashMap<String, String> symbolTable;
	private HashMap<String, String> symbolTableAsItIs;

	public SymbolTable(HashMap<String, String> symbolTable, HashMap<String, String> symbolTableAsItIs) {
		this.symbolTable = symbolTable;
		this.symbolTableAsItIs = symbolTableAsItIs;
	}

	public String getAddress(String operand) {
		return symbolTable.get(operand);
	}

	public ArrayList<ArrayList<String>> getData() {
		ArrayList<ArrayList<String>> data = new ArrayList<>();
		for (String key : symbolTableAsItIs.keySet()) {
			ArrayList<String> row = new ArrayList<>();
			row.add(key);
			row.add(Integer.toHexString(Integer.parseInt(symbolTableAsItIs.get(key), 2)));
			data.add(row);
		}
		return data;
	}

}
