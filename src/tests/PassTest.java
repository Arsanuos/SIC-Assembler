//package tests;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.Set;
//
//import InstructionMode.Sic;
//import fileHandler.ReadAssemblyFile;
//import fileHandler.ReadSicFile;
//import fileHandler.WriteListingFile;
//import passes.InstructionHandler;
//import passes.PassOne;
//import passes.PassTwo;
//import tables.OperationTable;
//import tables.SymbolTable;
//
//public class PassTest {
//
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		ReadSicFile read = new ReadSicFile();
//		ReadAssemblyFile asm = new ReadAssemblyFile();
//		ArrayList<String> a = asm.readAssemblyFile("Q1");
//		HashMap<String, String> h = read.readSicOperationFile("SIC.txt");
//		PassOne p = new PassOne(h, a);
//		HashMap<String, String> m = p.passOne();
//		InstructionHandler IH = new InstructionHandler();
//
//		WriteListingFile writeListingFile = new WriteListingFile(asm.getFileAsItIS(), p.getMaxLenOfLocCounter(),
//				p.getLocationCounters());
//		writeListingFile.writeListingFile();
//		OperationTable o = new OperationTable(h);
//		SymbolTable st = new SymbolTable(p.getSymbolTable());
//		Sic sic = new Sic(o, a, st);
//		sic.generateObjectCode();
//		String L = asm.getFileAsItIS().get(0);
//		PassTwo pt = new PassTwo(sic.getOpCode(), IH.getLabel(L), IH.getOperand(L), Integer.toString(p.getProgramLen()),
//				p.getAddresses(), p.getMaxLenOfLocCounter(), a);
//		pt.opCodeGeneration();
//		Set c = m.keySet();
//		Iterator i = c.iterator();
//		while (i.hasNext()) {
//			String lab = (String) i.next();
//			System.out.println(lab + "  " + Integer.parseUnsignedInt(m.get(lab), 2));
//		}
//
//	}
//}
