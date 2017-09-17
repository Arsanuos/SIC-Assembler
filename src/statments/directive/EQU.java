package statments.directive;

import java.util.ArrayList;
import java.util.HashMap;

import expression.Expression;
import passes.Literal;
import resources.Resources;
import utility.InstructionHandler;

public class EQU {

	private Resources resources;

	public EQU(Resources resources) {
		// TODO Auto-generated constructor stub
		this.resources = resources;
	}

	public boolean isEQU(String line) {
		String operation = InstructionHandler.getOperation(line);
		return operation.equalsIgnoreCase("equ") ? true : false;
	}

	public void eval(String line) {
		ArrayList<String> fileAsItIs = resources.getFileAsItIs();
		String labelAsItIs = InstructionHandler.getLabel(fileAsItIs.get(resources.getK()));
		String operand = getOperand(line);
		String label = InstructionHandler.getLabel(line);
		HashMap<String, String> symbols = resources.getSymbolMap();
		HashMap<String, String> symbolsAsItIs = resources.getSymbolMapAsItIs();
		HashMap<String, String> literals = resources.getLiteralMap();
		Expression exp = new Expression();
		if (operand != "-1" && InstructionHandler.isSymbol(line)) {
			try {
				int val = InstructionHandler.getValueOfOperand(line, operand);
				symbols.put(label, Integer.toBinaryString(val));
				symbolsAsItIs.put(labelAsItIs, Integer.toBinaryString(val));
				return;
			} catch (Exception e) {
				String address = symbols.get(operand);
				if (address != null) {
					symbols.put(label, address);
					symbolsAsItIs.put(labelAsItIs, address);
					return;
				}
				if (operand.equalsIgnoreCase("*")) {
					address = Integer.toBinaryString(resources.getCurLocationCounter());
					symbols.put(label, address);
					symbolsAsItIs.put(labelAsItIs, address);
					return;
				}
				if (Literal.isLiteral(line,operand)) {
					address = literals.get(Literal.getBinaryRepresentationOfLiteral(line,operand));
					if (address != null) {
						String bin = Integer.toBinaryString(Integer.parseUnsignedInt(address, 16));
						symbols.put(label, bin);
						symbolsAsItIs.put(labelAsItIs, bin);
						return;
					}
				}

				// binary return
				address = exp.eval(line, operand, resources);
				symbols.put(label, address);
				symbolsAsItIs.put(labelAsItIs, address);
				return;
				//throw new RuntimeException(".Not intialized operand.");
			}
		}
		throw new RuntimeException(".Not valid EQU directive.");
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
