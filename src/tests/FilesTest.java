package tests;

import java.util.ArrayList;
import java.util.HashMap;

import fileHandler.ReadAssemblyFile;
import fileHandler.ReadSicFile;

public class FilesTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ReadAssemblyFile read = new ReadAssemblyFile();
		ArrayList<String> x = read.readAssemblyFile("Q1");
		for (int i = 0; i < x.size(); i++) {
			System.out.println(x.get(i));
		}
		ReadSicFile read2 = new ReadSicFile();
		HashMap<String, String> temp = read2.readSicOperationFile("SIC");
	}

}
