package statments.directive;

import utility.InstructionHandler;

public class End {

	private boolean found;

	public End() {
		// TODO Auto-generated constructor stub
		found = true;
	}

	public boolean isEnd(String line) {
		String operation = InstructionHandler.getOperation(line);
		return operation.equalsIgnoreCase("end") ? true : false;
	}

	public int eval(String line) {
		if (!found) {
			throw new RuntimeException(".End not found");
		}
		found = true;
		checkStartWithBlank(line);
		return 0;
	}

	private void checkStartWithBlank(String line) {
		String label = InstructionHandler.getLabel(line);
		if (label.length() != 0) {
			throw new RuntimeException("  .end statement must be without labels");
		}
	}
}
