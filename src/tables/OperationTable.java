package tables;

import java.util.HashMap;

public class OperationTable {

	private HashMap<String, String> sicTable;

	public OperationTable(HashMap<String, String> sicTable) {
		// TODO Auto-generated constructor stub
		this.sicTable = sicTable;
	}

	public String getCode(String operation) {
		return sicTable.get(operation);
	}

}
