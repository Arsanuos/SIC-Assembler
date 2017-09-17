package statments.operation;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import passes.Literal;
import resources.Resources;
import utility.InstructionHandler;

public class Operation implements OperationIF {

	private Resources resources;
	private ArrayList<String> literals;

	public Operation(Resources resources) {
		// TODO Auto-generated constructor stub
		this.resources = resources;
		literals = new ArrayList<>();
	}

	@Override
	public boolean isValidOperation(String line) {
		// TODO Auto-generated method stub
		return resources.getOperationCode().containsKey(InstructionHandler.getOperation(line));
	}

	@Override
	public int eval(String line) {
		// TODO Auto-generated method stub
		String operand = InstructionHandler.getOperand(line);
		String operation = InstructionHandler.getOperation(line);
		ArrayList<String> fileAsItIs = resources.getFileAsItIs();
		if (isValidOperation(line)) {
			if (handleValidOperation(line)) {
				if (Literal.isLiteral(line, operand) && !operation.equalsIgnoreCase("rsub")) {
					// throw exception to handle x'' literals such that it
					// contains even number of char
					Literal.getLiteralLen(line, operand);
					String lineAsItIs = fileAsItIs.get(resources.getK());
					String operandAsItIs = InstructionHandler.getOperand(lineAsItIs);
					String litName = Literal.getLiteral(line, operandAsItIs, resources.getK());
					literals.add(litName);
					resources.setLiterals(literals);
				}
				if (!operation.equalsIgnoreCase("rsub") && Literal.isLiteral(line, operand) == false
						&& (operand.contains("'") || operand.contains("=") || numberWithoutEqual(operand))) {
					throw new RuntimeException("invalid literal.");
				}
				return 3;
			}
		}
		throw new RuntimeException(".not valid Opeation: ");
	}

	private boolean handleValidOperation(String line) {
		String operation = InstructionHandler.getOperation(line);
		if (operation.equalsIgnoreCase("rsub")) {
			if (InstructionHandler.getOperand(line) == null
					|| InstructionHandler.getOperand(line).equalsIgnoreCase("0")) {
				return true;
			} else {
				throw new RuntimeException("." + "Strange instruction : ");
			}
		} else {
			if (!resources.getOperationCode().containsKey(InstructionHandler.getOperand(line))
					&& InstructionHandler.getOperand(line) != null
					&& !InstructionHandler.getOperand(line).equalsIgnoreCase("0")) {
				return true;
			} else {
				throw new RuntimeException("." + "Strange instruction : ");
			}
		}
	}

	private boolean numberWithoutEqual(String operand) {
		String regex = "(-?(\\d){1,5})";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(operand);
		boolean out = false;
		if (m.matches()) {
			out = true;
		} else {
			out = false;
		}
		return out;
	}

}
