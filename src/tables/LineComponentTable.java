package tables;

import java.util.ArrayList;
import java.util.HashMap;

public class LineComponentTable {

	private HashMap<String, ArrayList<String>> lineComponents;

	public LineComponentTable(HashMap<String, ArrayList<String>> lineComponents) {
		// TODO Auto-generated constructor stub
		this.lineComponents = lineComponents;
	}

	public String getLabel(String line) {
		return this.lineComponents.get(line).get(0);
	}

	public String getOperation(String line) {
		return this.lineComponents.get(line).get(1);
	}

	public String getOperand(String line) {
		return this.lineComponents.get(line).get(2);
	}
}
