package statments;

import resources.Resources;
import statments.data.Data;
import statments.data.DataIF;
import statments.directive.Directive;
import statments.directive.DirectiveIF;
import statments.operation.Operation;
import statments.operation.OperationIF;

public class Instruction implements InstructionIF {

	private Resources resources;
	private DirectiveIF directive;
	private OperationIF operation;
	private DataIF data;

	public Instruction(Resources resources) {
		// TODO Auto-generated constructor stub
		this.resources = resources;
		directive = new Directive(resources);
		operation = new Operation(resources);
		data = new Data(resources);
	}

	@Override
	public int eval(String line, String flag) {
		// TODO Auto-generated method stub
		if (flag == "normal") {
			return normalMode(line);
		} else if (flag.equalsIgnoreCase("start")) {
			if (directive.isStart(line)) {// good
				return directive.eval(line);
			}
			directive.setRepeatedStart();
			throw new RuntimeException(".there is no start statement");
		} else if (flag.equalsIgnoreCase("end")) {
			if (directive.isEnd(line)) {// good
				return directive.eval(line);
			}
			throw new RuntimeException(".there is no end statement");
		} else {
			throw new RuntimeException(".Unexpected parameter to eval in instruction");
		}
	}

	private int normalMode(String line) {
		if (directive.isDirective(line)) {// good
			return directive.eval(line);
		} else if (operation.isValidOperation(line)) {// good
			return operation.eval(line);
		} else if (data.isData(line)) {// good
			return data.eval(line);
		} else {
			throw new RuntimeException(".Undefined line at pass1: ");
		}
	}

	@Override
	public boolean wasLtorg() {
		// TODO Auto-generated method stub
		boolean temp = directive.wasLtorg();
		directive.setLtorg();
		return temp;
	}

	@Override
	public boolean isEQU(String line) {
		// TODO Auto-generated method stub
		return directive.isEQU(line);
	}

}
