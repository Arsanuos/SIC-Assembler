package passes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import utility.InstructionHandler;

public class Literal {

	public static boolean isLiteral(String line, String operand) {
		if (line.charAt(0) != '*') {
			String regex = "(=x'([A-Fa-f0-9]{1,14})')" + "|" + "(=c'((.){1,15})')" + "|" + "=(-?(\\d){1,5})" + "|"
					+ "=((\\*){1})" + "|" + "=((\\*){1}(\\d){0,14})";
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(operand);
			boolean out = false;
			if (m.matches()) {
				out = true;
			} else {
				out = false;
			}
			return out;
		} else {
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
	}

	public static String getLiteral(String line, String operand, int lineNum) {
		String regex1 = "(=?(x'(([A-Fa-f0-9]{1,14}))'))";
		Pattern p1 = Pattern.compile(regex1);
		Matcher m1 = p1.matcher(operand);
		if (m1.matches()) {
			for (int i = 0; i < m1.groupCount(); i++) {
				print(m1.group(i));
			}
			getLiteralLen(line, operand);
			return m1.group(2);// in format x'0123a'
		}
		String regex2 = "(=?(c'(((.){1,15}))'))";
		Pattern p2 = Pattern.compile(regex2);
		Matcher m2 = p2.matcher(operand);
		if (m2.matches()) {
			for (int i = 0; i < m2.groupCount(); i++) {
				print(m2.group(i));
			}
			return m2.group(2);// in format c'data'
		}
		String regex3 = "=?(-?(\\d){1,5})";
		Pattern p3 = Pattern.compile(regex3);
		Matcher m3 = p3.matcher(operand);
		if (m3.matches()) {
			for (int i = 0; i < m3.groupCount(); i++) {
				print(m3.group(i));
			}
			return m3.group(1);// in format -1234
		}

		String regex4 = "=?((\\*){1})";
		Pattern p4 = Pattern.compile(regex4);
		Matcher m4 = p4.matcher(operand);
		if (m4.matches()) {
			for (int i = 0; i < m4.groupCount(); i++) {
				print(m4.group(i));
			}
			return m4.group(1) + lineNum;// in format *
		}
		return null;
	}

	public static String getBinaryRepresentationOfLiteral(String line, String operand) {
		String regex1 = "(=?x'(([A-Fa-f0-9]{1,14}))')";
		Pattern p1 = Pattern.compile(regex1);
		Matcher m1 = p1.matcher(operand);
		if (m1.matches()) {
			for (int i = 0; i < m1.groupCount(); i++) {
				print(m1.group(i));
			}
			return Integer.toBinaryString(Integer.parseInt(m1.group(2), 16));
		}
		String regex2 = "(=?c'(((.){1,15}))')";
		Pattern p2 = Pattern.compile(regex2);
		Matcher m2 = p2.matcher(operand);
		if (m2.matches()) {
			for (int i = 0; i < m2.groupCount(); i++) {
				print(m2.group(i));
			}
			return Integer.toBinaryString(getAscii(m2.group(2)));
		}
		String regex3 = "=?(-?(\\d){1,5})";
		Pattern p3 = Pattern.compile(regex3);
		Matcher m3 = p3.matcher(operand);
		if (m3.matches()) {
			for (int i = 0; i < m3.groupCount(); i++) {
				print(m3.group(i));
			}
			return Integer.toBinaryString(Integer.parseInt(m3.group(1), 10));
		}

		String regex4 = "=?((\\*){1}(\\d){0,14})";
		Pattern p4 = Pattern.compile(regex4);
		Matcher m4 = p4.matcher(operand);
		if (m4.matches()) {
			for (int i = 0; i < m4.groupCount(); i++) {
				print(m4.group(i));
			}
			return Integer.toBinaryString(getAscii(m4.group(1)));// in
																	// format
																	// *
		}
		return null;
	}

	public static int getLiteralLen(String line, String operand) {
		String regex1 = "(=?x'(([A-Fa-f0-9]{1,14}))')";
		Pattern p1 = Pattern.compile(regex1);
		Matcher m1 = p1.matcher(operand);
		if (m1.matches()) {
			for (int i = 0; i < m1.groupCount(); i++) {
				print(m1.group(i));
			}
			if (m1.group(2).length() % 2 != 0) {
				throw new RuntimeException(".Wrong literal Len.");
			}
			return m1.group(2).length() / 2; // every hex number stored in
												// half byte.//length
												// without quotes or x
		}
		String regex2 = "(=?c'(((.){1,15}))')";
		Pattern p2 = Pattern.compile(regex2);
		Matcher m2 = p2.matcher(operand);
		if (m2.matches()) {
			for (int i = 0; i < m2.groupCount(); i++) {
				print(m2.group(i));
			}
			return m2.group(2).length();// length without quotes or c
		}
		String regex3 = "=?(-?((\\d){1,5}))";
		Pattern p3 = Pattern.compile(regex3);
		Matcher m3 = p3.matcher(operand);
		if (m3.matches()) {
			for (int i = 0; i < m3.groupCount(); i++) {
				print(m3.group(i));
			}
			return 3;// this group doesn't have sign
		}
		return 1;
	}

	private static int getAscii(String cliteral) {
		int ascii = 0;
		for (int i = 0; i < cliteral.length(); i++) {
			ascii += cliteral.charAt(i);
		}
		return ascii;
	}

	public static void print(Object x) {
		// System.out.println(x);
	}

	public static void main(String[] args) {
		System.out.println(isLiteral("*        54", "54"));
	}

}
