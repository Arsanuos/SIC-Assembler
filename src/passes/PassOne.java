package passes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import errorHandler.ErrorHandler;
import resources.Resources;
import statments.Instruction;
import statments.InstructionIF;
import tables.SymbolTable;
import utility.InstructionHandler;

public class PassOne implements Observer {

	private int locationCounter;
	private HashMap<String, String> symbolTable;
	private HashMap<String, String> sicTable;
	private ArrayList<String> assemblyFile;
	private int programLen;
	private HashMap<String, String> locationCounters;
	private ArrayList<String> address;
	private int maxLenOfLocCounter;
	private int increasedBy;
	private ArrayList<String> fileAsItIs;
	private ErrorHandler error;
	private Resources resources;
	private HashMap<String, String> symbolTableAsItIs;
	private int startingAddress;
	private int i;
	InstructionIF instruction;

	public PassOne(Resources resources) {
		// TODO Auto-generated constructor stub
		this.resources = resources;
		this.fileAsItIs = resources.getFileAsItIs();
		this.increasedBy = 0;
		i = 0;
		this.assemblyFile = resources.getAssemblyFileInstruction();
		this.sicTable = resources.getOperationCode();
		locationCounter = 0;
		symbolTable = new HashMap<>();
		locationCounters = new HashMap<>();
		address = new ArrayList<>();
		maxLenOfLocCounter = 0;
		startingAddress = 0;
		symbolTableAsItIs = new HashMap<>();
		this.error = resources.getErrorHandler();
		instruction = new Instruction(resources);
	}

	public HashMap<String, String> passOne() {
		String line = null;
		for (i = 0; i < assemblyFile.size(); i++) {
			line = assemblyFile.get(i);
			increasedBy = 0;
			try {
				if (!InstructionHandler.commentLine(line)) {
					checkTabs(line);
					resources.setK(i);
					// System.out.println(i);
					if (i == 0) {
						locationCounter = instruction.eval(line, "start");
						String operand = InstructionHandler.getOperand(line);
						startingAddress = InstructionHandler.getValueOfOperand(line, operand);
						resources.setCurLocationCounter(locationCounter);
					} else if (i == assemblyFile.size() - 1) {
						int temp = i;
						instruction.eval(line, "end");
						addError(temp, " ");
						continue;
					} else {
						if (InstructionHandler.isSymbol(line) && !instruction.isEQU(line)) {
							handleSymbol(line, i);
							resources.setSymbolMap(symbolTable);
						}
						increasedBy = instruction.eval(line, "normal");
						if (instruction.wasLtorg()) {
							continue;
						}
						locationCounter += increasedBy;
						resources.setCurLocationCounter(locationCounter);
						this.programLen = locationCounter - startingAddress;
						isValidProgLen(line);
						updateMaxLenOfLocationCounters();
					}
					addError(i, " ");
				}
			} catch (RuntimeException e) {
				//e.printStackTrace();
				locationCounter += 0;
				String message = e.getMessage();
				if (message == null) {
					message = "  .Strange instruction: ";
				}
				error.addErrorInPassOne(fileAsItIs.get(i), message + "  " + fileAsItIs.get(i));
				address.add(" ");
			}
		}
		this.programLen = locationCounter - startingAddress;
		setData();
		return this.symbolTable;
	}

	private void isValidProgLen(String line) {
		if (programLen > Math.pow(2, 15)) {
			throw new RuntimeException("." + "memory out of bound: ");
			// error.addErrorInPassOne(fileAsItIs.get(i), message);
			// address.add(" ");
		}
	}

	private void updateMaxLenOfLocationCounters() {
		if (maxLenOfLocCounter < address.get(address.size() - 1).length()) {
			maxLenOfLocCounter = address.get(address.size() - 1).length();
		}

	}

	private void checkTabs(String line) {
		if (line.contains("\t")) {
			throw new RuntimeException(".Tabs in : ");
		}
	}

	public HashMap<String, String> getSymbolTable() {
		return symbolTable;
	}

	public int getProgramLen() {
		return programLen;
	}

	public ArrayList<String> getAddresses() {
		return address;
	}

	public HashMap<String, String> getLocationCounters() {
		return locationCounters;
	}

	public int getMaxLenOfLocCounter() {
		return maxLenOfLocCounter;
	}

	private void handleSymbol(String line, int i) {
		if (!sicTable.containsKey(InstructionHandler.getLabel(line))
				&& !isRegister(InstructionHandler.getLabel(line))) {
			if (!symbolTable.containsKey(InstructionHandler.getLabel(line))) {
				symbolTable.put(InstructionHandler.getLabel(line), Integer.toBinaryString(locationCounter));
				symbolTableAsItIs.put(InstructionHandler.getLabel(fileAsItIs.get(i)),
						Integer.toBinaryString(locationCounter));
			} else {
				throw new RuntimeException("." + "Repeated label");

			}
		} else {
			throw new RuntimeException("." + "Invalid label name:  ");
		}
	}

	private boolean isRegister(String s) {
		if (s.trim().length() == 1 && (s.trim().equalsIgnoreCase("a") || s.trim().equalsIgnoreCase("x")
				|| s.trim().equalsIgnoreCase("t") || s.trim().equalsIgnoreCase("b") || s.trim().equalsIgnoreCase("a")
				|| s.trim().equalsIgnoreCase("l") || s.trim().equalsIgnoreCase("s") || s.trim().equalsIgnoreCase("f")
				|| s.trim().equalsIgnoreCase("*"))) {
			return true;
		}
		return false;
	}

	private void setData() {
		resources.setAddresses(address);
		resources.setMaxLenOfLocCounter(getMaxLenOfLocCounter());
		resources.setLocationCounters(getLocationCounters());
		resources.setProgramLen(getProgramLen());
		SymbolTable symbolTable = new SymbolTable(this.symbolTable, this.symbolTableAsItIs);
		resources.setSymTable(symbolTable);
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		this.locationCounter = resources.getCurLocationCounter();
		// System.out.println(i + 1);
		String c = (String) arg;
		if (!c.equalsIgnoreCase("no")) {
			this.i = resources.getK() - 1;
		}
	}

	private void addError(int temp, String message) {
		error.addErrorInPassOne(fileAsItIs.get(temp), message);
		locationCounters.put(fileAsItIs.get(temp), Integer.toHexString(locationCounter - increasedBy));
		address.add(Integer.toHexString(locationCounter - increasedBy));
		// System.out.println(Integer.toHexString(locationCounter -
		// increasedBy));
		resources.setAddresses(address);
		this.locationCounter = resources.getCurLocationCounter();
		resources.setSymbolMap(symbolTable);
		resources.setSymbolMapAsItIs(this.symbolTableAsItIs);
		resources.setStartingAddress(address.get(0));
	}
}
