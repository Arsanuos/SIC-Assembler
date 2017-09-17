package statments.data;

import utility.InstructionHandler;

public class Word {

	public boolean isWord(String line) {
		return InstructionHandler.getOperation(line).equals("word") ? true : false;
	}

	public int eval(String line) {
		if (checkWordOperand(line, InstructionHandler.getOperand(line))) {
			return 3;
		}
		throw new RuntimeException("strange data word ");
	}

	private boolean checkWordOperand(String line, String operand) {
		try {
			int x = Integer.parseInt(operand, 10);
			if (x > 10000 || x < -10000)// max 4 decimal digts
			{
				throw new RuntimeException("not valid decimal value");
			}
			return true;
		} catch (Exception e) {
			throw new RuntimeException(".Not valid operand ");
		}

	}
}
