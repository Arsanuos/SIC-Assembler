package statments.directive;

import java.util.ArrayList;
import java.util.HashMap;

import passes.Literal;
import resources.Resources;
import utility.InstructionHandler;

public class Ltorg {

	public boolean isLtorg(String line) {
		String operation = InstructionHandler.getOperation(line);
		return operation.equalsIgnoreCase("ltorg") ? true : false;
	}

	public int eval(String line, Resources resources) {
		HashMap<String, String> literalMap = resources.getLiteralMap();
		ArrayList<String> literals = resources.getLiterals();
		ArrayList<String> assemblyFile = resources.getAssemblyFileInstruction();
		ArrayList<String> fileAsItIs = resources.getFileAsItIs();
		ArrayList<String> addresses = resources.getAddresses();
		ArrayList<String> errorInPass1 = resources.getErrorHandler().getErrorInPassOne();
		int k = resources.getK();
		int increasedBy = 0;
		int locationCounter = resources.getCurLocationCounter();
		// add ltorg itself.
		addresses.add(k, Integer.toHexString(locationCounter));
		errorInPass1.add(k, " ");
		k++;
		for (int i = 0; literals != null && i < literals.size(); i++) {
			String key = Literal.getBinaryRepresentationOfLiteral(line,literals.get(i));
			if (!literalMap.containsKey(key) && key != null) {
				// System.out.println("add: "+k);
				assemblyFile.add(k, editLiterals(literals.get(i)));
				fileAsItIs.add(k, editLiterals(literals.get(i)));
				literalMap.put(key, Integer.toHexString(locationCounter));
				increasedBy = Literal.getLiteralLen(line,literals.get(i));
				locationCounter += increasedBy;
				addresses.add(k, Integer.toHexString(locationCounter - increasedBy));
				errorInPass1.add(k, " ");
				k++;
			}
		}
		// addresses.remove(k - 1);
		resources.setK(k);
		resources.getErrorHandler().setErrorInPassOne(errorInPass1);
		resources.setAssemblyFileInstruction(assemblyFile);
		resources.setFileAsItIs(fileAsItIs);
		resources.setAddresses(addresses);
		resources.setCurLocationCounter(locationCounter);
		resources.setInc(increasedBy);
		resources.setLiteralMap(literalMap);
		return increasedBy;
	}

	private String editLiterals(String literal) {
		String space1 = "";// 1 space
		String space2 = "        ";// 7 space
		return space1 + "*" + space2 + literal;

	}
}
