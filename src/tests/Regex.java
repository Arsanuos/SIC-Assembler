package tests;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fileHandler.ReadAssemblyFile;

public class Regex {

	public static void main(String[] args) {

		ReadAssemblyFile file = new ReadAssemblyFile();
		ArrayList<String> f = file.readAssemblyFile("SIC-Example.txt");
		// label operation operand comment
		String pattern = "(^[A-Za-z][A-Za-z0-9_]*)?" + "((\\s)+)" + "[A-Za-z]+" + "((\\s)+)" + "([A-Za-z0-9_]+)?"
				+ "((\\s)+)" + "(\\w+)?";

		String line = "^(\\s*)" 
				+ "([A-Za-z0-9]{0,8}\\b)?"
				+ "(\\s+)"
				+ "([A-Za-z]{2,6}\\b)" 
				+ "(\\s*)"
				+ "(([A-Za-z0-9,x|X]{0,18}\\b)"
				+ "|" 
				+ "([x'[A-Fa-f0-9]']{0,18})"
				+ "|"
				+ "([c'[A-Za-z]']{0,18}))?"
				+ "(\\s*)"
				+ "([A-Za-z0-9]{0,31}\\b)?" 
				+ "(\\s*)";

		Pattern p = Pattern.compile(line);
		for (int i = 0; i < f.size(); i++) {
			Matcher m = p.matcher(f.get(i));
			// System.out.println(m.groupCount());
			if (m.matches()) {
				// System.out.println();
				// System.out.println();
				// System.out.println();
				 //System.out.println("ture " + f.get(i));
				// System.out.println();
				// System.out.println();
				// System.out.println();
				for (int j = 0; j < m.groupCount(); j++) {
					 System.out.println("num " + j + " " + m.group(j));
				}
				 System.out.println();
				 System.out.println();
				 System.out.println();
			} else {
				System.out.println("false " + f.get(i));
			}
		}

	}

	private void test() {
		while (true) {
			Scanner scanner = new Scanner(System.in);
			String input = scanner.next();
			String pattern = "^[A-Za-z0-9\\._-]+@(gmail|yahoo|hotmail)\\.com$";
			// http://www.google.com
			pattern = "^(http://)?(www\\.)?[A-Za-z]+\\.(com|net|org)$";

			Pattern p = Pattern.compile(pattern);

			Matcher m = p.matcher(input);
			if (m.matches()) {
				System.out.println("true");
				continue;
			}
			System.out.println("false");
		}
	}
}
