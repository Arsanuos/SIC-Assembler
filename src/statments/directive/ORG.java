package statments.directive;

import java.util.HashMap;

import passes.Literal;
import resources.Resources;
import utility.InstructionHandler;

public class ORG {

	private int tempLocationCounter;
	private Resources resources;

	public ORG(Resources resources) {
		// TODO Auto-generated constructor stub
		this.tempLocationCounter = 0;
		this.resources = resources;
	}

	public boolean isOrg(String line) {
		String operation = InstructionHandler.getOperation(line);
		return operation.equalsIgnoreCase("org") ? true : false;
	}

	public void eval(String line) {
		String operand = getOperand(line);
		if (operand != "-1") {
			try {
				// number.
				int val = InstructionHandler.getValueOfOperand(line, operand);
				tempLocationCounter = resources.getCurLocationCounter();
				resources.setCurLocationCounter(val);
			} catch (Exception e) {
				// symbol or literal.
				HashMap<String, String> symbols = resources.getSymbolMap();
				HashMap<String, String> literals = resources.getLiteralMap();
				String address = symbols.get(operand);
				if (address != null) {
					tempLocationCounter = resources.getCurLocationCounter();
					int locationCounter = Integer.parseUnsignedInt(address, 2);
					resources.setCurLocationCounter(locationCounter);
					return;
				}
				if (operand.equalsIgnoreCase("*")) {
					tempLocationCounter = resources.getCurLocationCounter();
					return;
				}
				if (Literal.isLiteral(line,operand)) {
					address = literals.get(Literal.getBinaryRepresentationOfLiteral(line,operand));
					if (address != null) {
						tempLocationCounter = resources.getCurLocationCounter();
						int locationCounter = Integer.parseUnsignedInt(address, 2);
						resources.setCurLocationCounter(locationCounter);
						return;
					}
				}
				throw new RuntimeException(".Not valid operand.");
			}
		} else {
			resources.setCurLocationCounter(tempLocationCounter);
		}
	}

	public String getOperand(String line) {
		String operand;
		try {
			if (line.length() >= 35) {
				operand = line.substring(17, 35);
			} else {
				operand = line.substring(17, line.length());
			}
			if (operand.charAt(0) == ' ') {
				return null;
			}
			if (operand.length() >= 3 && operand.charAt(0) == '0' && operand.charAt(1) == 'x') {
				// operand is address not a label.
				if (line.length() >= 35) {
					operand = line.substring(19, 35);
				} else {
					operand = line.substring(19, line.length());
				}
			}
			// address is optional in start
			operand = operand.trim();
			if (operand.length() == 0) {
				return "0";
			}
			return operand.trim();
		} catch (Exception e) {
			return "-1";
		}
	}

}
