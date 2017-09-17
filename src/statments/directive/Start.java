package statments.directive;

import utility.InstructionHandler;

public class Start {

	private boolean reatedStart;

	public Start() {
		// TODO Auto-generated constructor stub
		reatedStart = false;
	}

	public boolean isStart(String line) {
		String operation = InstructionHandler.getOperation(line);
		return operation.equalsIgnoreCase("start") ? true : false;
	}

	public int eval(String line) {
		if (reatedStart) {
			throw new RuntimeException(".Repeated start statment");
		}
		reatedStart = true;
		return InstructionHandler.getValueOfOperand(line, InstructionHandler.getOperand(line));
	}

	public void setRepeatedStart() {
		this.reatedStart = true;
	}
}
