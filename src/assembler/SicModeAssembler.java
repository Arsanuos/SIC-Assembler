package assembler;

import java.util.ArrayList;
import java.util.HashMap;
import InstructionMode.Sic;
import errorHandler.ErrorHandler;
import fileHandler.FileFacade;
import passes.PassOne;
import passes.PassTwo;
import resources.Resources;
import tables.IntermediateFileTable;
import tables.ListingFileTable;
import tables.OperationTable;
import utility.InstructionHandler;

public class SicModeAssembler implements ModesIF {

	private final String sicInstructionFileName = "SIC.txt";
	private String fileName;

	public SicModeAssembler(String fileName) {
		// TODO Auto-generated constructor stub
		this.fileName = fileName;
	}

	@Override
	public void generateObjectCode() {
		Resources resources = new Resources();
		ErrorHandler errorHandler = new ErrorHandler();
		resources.setErrorHandler(errorHandler);

		FileFacade fileFacade = new FileFacade();
		ArrayList<String> AssemblyFileInstruction = fileFacade.readAssemblyFile(fileName);
		resources.setAssemblyFileInstruction(AssemblyFileInstruction);

		HashMap<String, String> sicTable = fileFacade.readSicOperationFile(sicInstructionFileName);
		resources.setOperationCode(sicTable);

		OperationTable opTable = new OperationTable(sicTable);
		resources.setOpTable(opTable);
		resources.setFileAsItIs(fileFacade.getFileAsItIS());

		PassOne passOne = new PassOne(resources);
		resources.addObserver(passOne);
		passOne.passOne();

		IntermediateFileTable intermediateFileTable = new IntermediateFileTable(resources);
		resources.setIntermediateTable(intermediateFileTable);

		if (fileFacade.writeIntermediateFile(resources.getIntermediateTable())) {
			return;
		}

		String firstLine = fileFacade.getFileAsItIS().get(0);
		resources.setProgName(InstructionHandler.getLabel(firstLine));
		resources.setStartingAddress(resources.getLocationCounters().get(firstLine).toUpperCase());

		Sic.getInstance().setOpTable(resources.getOpTable());
		Sic.getInstance().setAssemblyFile(resources.getAssemblyFileInstruction());
		Sic.getInstance().setSymbolTable(resources.getSymTable());
		Sic.getInstance().setErrorHandler(resources.getErrorHandler());
		Sic.getInstance().setAssemblyFileAsItIs(resources.getFileAsItIs());
		Sic.getInstance().setProgName(resources.getProgName());
		Sic.getInstance().setLiteralMap(resources.getLiteralMap());
		Sic.getInstance().setAddressesList(resources.getAddresses());
		Sic.getInstance().generateObjectCode();
		//System.out.println(resources.getLocationCounters());

		PassTwo pt = new PassTwo(resources);
		pt.opCodeGeneration();

		ListingFileTable listingFileTable = new ListingFileTable(resources);
		resources.setListingTable(listingFileTable);

		if (fileFacade.writeListingFile(resources.getListingTable())) {
			return;
		}
	}
}
