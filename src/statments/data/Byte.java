package statments.data;

import utility.InstructionHandler;

public class Byte {

	public boolean isByte(String line) {
		return InstructionHandler.getOperation(line).equals("byte") ? true : false;
	}

	public int eval(String line) {
		String operand = InstructionHandler.getOperand(line);
		if (InstructionHandler.isByteLiteral(operand)) {
			return InstructionHandler.getLiteralLen(operand);
		}
		throw new RuntimeException("." + "Not Valid literal :");

	}
}
