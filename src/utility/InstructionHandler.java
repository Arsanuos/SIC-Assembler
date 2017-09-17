package utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import passes.Literal;

public class InstructionHandler {

	private static int startingAddress;

	public InstructionHandler() {
		startingAddress = 0;
	}

	public static boolean commentLine(String line) {
		return line.charAt(0) == '.' ? true : false;
	}

	public static boolean isSymbol(String line) {
		String label = line.substring(0, 8);
		if (label.trim().length() == 0) {
			return false;
		} else {
			if (label.charAt(0) == ' ') {
				throw new RuntimeException(".invalid instruction if start" + " invalid instruction if start");
			}
		}
		return true;

	}

	public static String getLabel(String line) {
		return line.substring(0, 8).trim();
	}

	public static String getOperation(String line) {

		if (line.charAt(9) == ' ') {
			throw new RuntimeException();
		}
		try {
			if (line.charAt(16) == ' ') {
			} else {
				String l = line.substring(9).trim();
				if (isLiteral(l))
					return l;
			}

			return line.substring(9, 15).trim();
		} catch (Exception e) {
			String x = new String();
			for (int i = 9; i < line.length(); i++) {
				x += line.charAt(i);
			}
			return x.trim();
		}
	}

	public static boolean isLiteral(String operand) {
		String regex = "(=?x'([A-Fa-f0-9]{1,14})')" + "|" + "(=?c'((.){1,15})')" + "|" + "=?(-?(\\d){1,5})" + "|"
				+ "=?((\\*){1})" + "|" + "=?((\\*){1}(\\d){0,14})";
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

	public static String getOperand(String line) {
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
			return "0";
		}
	}

	public static int getValueOfOperand(String line, String operand) {
		try {
			// hexa operand if 0x exist else will be integer
			if (line.contains("0x")) {
				startingAddress = Integer.parseUnsignedInt(operand, 16);
				return startingAddress;
			}
			return Integer.parseUnsignedInt(operand);
		} catch (Exception e) {
			throw new RuntimeException(".Can't get the value of the operand of this line: " + line);
		}
	}

	public static boolean isByteLiteral(String operand) {
		String regex = "(=?x'([A-Fa-f0-9]{0,14})')" + "|" + "(=?c'((.){0,15})')";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(operand);
		if (m.matches()) {
			return true;
		} else {
			return false;
		}
	}

	public static int getLiteralLen(String operand) {
		if (isByteLiteral(operand)) {
			String regex1 = "(=?x'(([A-Fa-f0-9]{0,14}))')";
			Pattern p1 = Pattern.compile(regex1);
			Matcher m1 = p1.matcher(operand);
			if (m1.matches()) {
				for (int i = 0; i < m1.groupCount(); i++) {
					print(m1.group(i));
				}
				int len = m1.group(2).length();
				if (len % 2 != 0) {
					throw new RuntimeException();
				}
				return m1.group(2).length() / 2; // every hex number stored in
													// half byte.//length
													// without quotes or x
			}
			String regex2 = "(=?c'(((.){0,15}))')";
			Pattern p2 = Pattern.compile(regex2);
			Matcher m2 = p2.matcher(operand);
			if (m2.matches()) {
				for (int i = 0; i < m2.groupCount(); i++) {
					print(m2.group(i));
				}
				return m2.group(2).length();// length without quotes or c
			}
		}
		return 0;
	}

	public static void print(Object x) {
		// System.out.println(x);
	}

}
