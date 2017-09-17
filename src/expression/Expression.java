package expression;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import passes.Literal;
import resources.Resources;
import tables.SymbolTable;

public class Expression {

	ScriptEngineManager scm;
	ScriptEngine jsEngine;

	public Expression() {
		// TODO Auto-generated constructor stub
		scm = new ScriptEngineManager();
		jsEngine = scm.getEngineByName("JavaScript");
	}

	public boolean isValidExpression(String operand) {
		if (operand.charAt(0) == '-') {
			// -label+label
			String regex = "-(\\b[A-Za-z0-9]\\b)\\+(\\b[A-Za-z0-9]\\b)";
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(operand);
			if (m.matches()) {
				for (int i = 0; i < m.groupCount(); i++) {
					System.out.println(m.group(i));
				}
				return true;
			}
		} else {
			String regex = "(\\b[A-Za-z0-9]\\b)-(\\b[A-Za-z0-9]\\b)";
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(operand);
			if (m.matches()) {
				for (int i = 0; i < m.groupCount(); i++) {
					System.out.println(m.group(i));
				}
				return true;
			}
		}
		return false;
	}

	public String eval(String line, String exp, Resources resources) {
		String[] symbols = exp.split("[-+]");
		HashMap<String, String> sym = resources.getSymbolMap();
		HashMap<String, String> lit = resources.getLiteralMap();
		for (int i = 0; i < symbols.length; i++) {
			if (symbols[i] != "" && sym.get(symbols[i]) != null) {
				exp = exp.replaceAll(symbols[i], "" + Integer.parseUnsignedInt(sym.get(symbols[i]), 2));
			} else if (Literal.isLiteral(line,symbols[i])) {
				String binaryOfliteral = Literal.getBinaryRepresentationOfLiteral(line,symbols[i]);
				if (lit.containsKey(binaryOfliteral)) {
					exp = exp.replaceAll(symbols[i], "" + Integer.parseUnsignedInt(lit.get(binaryOfliteral), 16));
				}
			}
		}
		try {
			String result = "" + jsEngine.eval(exp);
			return Integer.toBinaryString(Integer.parseUnsignedInt(result));
		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(".wrong expression: ");
		}
	}
}
