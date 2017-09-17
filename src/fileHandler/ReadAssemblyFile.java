package fileHandler;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class ReadAssemblyFile {

	private ArrayList<String> FileAsItIS;
	private ArrayList<String> AssembleyCode;

	public ReadAssemblyFile() {
		// TODO Auto-generated constructor stub
		FileAsItIS = new ArrayList<>();
		AssembleyCode = new ArrayList<>();
	}

	public ArrayList<String> readAssemblyFile(String fileName) {
		String line;
		try {
			InputStream fis = new FileInputStream(fileName);
			InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
			BufferedReader br = new BufferedReader(isr);
			while ((line = br.readLine()) != null && !getOperation(line).equalsIgnoreCase("end")) {
				// comment line
				if (line.length() == 0) {
					continue;
				}
				if (line.charAt(0) == '.') {
					continue;
				}
				FileAsItIS.add(line);
				line = line.toLowerCase();
				// comment in instruction.
				AssembleyCode.add(line);
			}
			if (!(line == null)) {
				if (!(line.length() == 0 && line.charAt(0) == '.')) {
					FileAsItIS.add(line);
					line = line.toLowerCase();
					// comment in instruction.
					AssembleyCode.add(line);
				}
			}
			fis.close();
			isr.close();
			br.close();
		} catch (IOException e) {
			throw new RuntimeException("Failure in reading file.");
		}
		return AssembleyCode;
	}

	private String removeLabel(String line) {
		if (line.charAt(0) == ' ') {
			return line.trim();
		}
		line = line.trim();
		return line.substring(9, line.length());
	}

	private String removeComment(String line) {
		if (line.length() > 35) {
			line = line.substring(0, 35);
		}
		return line;
	}

	public ArrayList<String> getFileAsItIS() {
		return this.FileAsItIS;
	}

	public ArrayList<String> getAssembleyCode() {
		return AssembleyCode;
	}
	
	public String getOperation(String line) {
		try {
			return line.substring(9, 15).trim();
		} catch (Exception e) {
			String x = new String();
			for (int i = 9; i < line.length(); i++) {
				x += line.charAt(i);
			}
			return x.trim();
		}
	}

}
