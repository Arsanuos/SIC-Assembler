package statments.data;

import utility.InstructionHandler;

public class Resw {

	public boolean isResw(String line) {
		return InstructionHandler.getOperation(line).equals("resw") ? true : false;
	}

	public int eval(String line) {
		int val = InstructionHandler.getValueOfOperand(line, InstructionHandler.getOperand(line));
		int increasedBy = 3 * val;
		if (increasedBy == 0 || val > 10000) {
			throw new RuntimeException("." + "Invalid operand value: ");
		}
		return increasedBy;
	}
}
