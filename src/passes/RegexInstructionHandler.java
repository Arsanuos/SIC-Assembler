package passes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import resources.Resources;
import tables.LineComponentTable;

public class RegexInstructionHandler {

	private final String regex = "^(\\s*)" 
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
	private HashMap<String, ArrayList<String>> lineComponents;
	private ArrayList<String> assemblyFile;

	public RegexInstructionHandler(Resources resources) {
		// TODO Auto-generated constructor stub
		assemblyFile = resources.getAssemblyFileInstruction();
		lineComponents = new HashMap<>();
		Pattern p = Pattern.compile(regex);
		for (int i = 0; i < assemblyFile.size(); i++) {
			String line = assemblyFile.get(i);
			Matcher m = p.matcher(line);
			// System.out.println(m.groupCount());
			if (m.matches()) {
				ArrayList<String> components = new ArrayList<>();
				components.add(m.group(2));
				components.add(m.group(4));
				components.add(m.group(6));
				lineComponents.put(line, components);
			} else {
				// error handler error
				// we will consider it as error in pass1 because it is an error
				// in instruction format
				resources.getErrorHandler().addErrorInPassOne(line, ".error in line format");
				resources.getErrorHandler().setFlagErrorInPassOne(true);

			}
		}
		LineComponentTable lineComponentsTable = new LineComponentTable(lineComponents);
		resources.setLineComponentsTable(lineComponentsTable);
	}

}
