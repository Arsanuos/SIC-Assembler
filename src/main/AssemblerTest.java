package main;

import assembler.AssemblerFactory;
import assembler.ModesIF;

public class AssemblerTest {

	public static void main(String[] args) {
		ModesIF sicModeAssembler = AssemblerFactory.getAssembler("SIC-Example.txt", "SIC");
		sicModeAssembler.generateObjectCode();
	}
}
