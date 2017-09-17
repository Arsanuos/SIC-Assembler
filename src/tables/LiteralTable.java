package tables;

import java.util.HashMap;

public class LiteralTable {

	private HashMap<String, String> literal;
	
	public LiteralTable() {
		// TODO Auto-generated constructor stub
	}

	public String getLiteral(String binaryValueOfLiteral) {
		return literal.get(binaryValueOfLiteral);
	}

	public void setLiteral(HashMap<String, String> literal) {
		this.literal = literal;
	}

}
