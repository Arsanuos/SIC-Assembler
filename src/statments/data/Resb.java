package statments.data;

import utility.InstructionHandler;

public class Resb {

	public boolean isResb(String line) {
		return InstructionHandler.getOperation(line).equals("resb") ? true : false;
	}

	public int eval(String line) {
		int increasedBy = InstructionHandler.getValueOfOperand(line, InstructionHandler.getOperand(line));
		if (increasedBy == 0 || increasedBy > 10000) {
			throw new RuntimeException("." + "Invalid operand value: ");
		}
		return increasedBy;
	}
}
